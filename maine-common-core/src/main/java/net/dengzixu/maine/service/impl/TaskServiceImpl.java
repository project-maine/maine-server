package net.dengzixu.maine.service.impl;

import net.dengzixu.maine.constant.enums.TaskStatus;
import net.dengzixu.maine.entity.*;
import net.dengzixu.maine.entity.dataobject.ParticipantDO;
import net.dengzixu.maine.entity.dataobject.TakeRecordDO;
import net.dengzixu.maine.entity.dataobject.TaskSettingDO;
import net.dengzixu.maine.entity.dto.ParticipantDTO;
import net.dengzixu.maine.entity.dto.TakeRecordDTO;
import net.dengzixu.maine.entity.dto.TaskInfoDTO;
import net.dengzixu.maine.entity.dto.TokenDTO;
import net.dengzixu.maine.exception.BusinessException;
import net.dengzixu.maine.exception.task.*;
import net.dengzixu.maine.mapper.task.TaskCodeMapper;
import net.dengzixu.maine.mapper.task.TaskMapper;
import net.dengzixu.maine.mapper.task.TaskRecordMapper;
import net.dengzixu.maine.mapper.task.TaskSettingMapper;
import net.dengzixu.maine.service.TaskService;
import net.dengzixu.maine.utils.RandomGenerator;
import net.dengzixu.maine.utils.RedisUtils;
import net.dengzixu.maine.utils.SerializeUtils;
import net.dengzixu.maine.utils.SnowFlake;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskMapper taskMapper;
    private final TaskRecordMapper taskRecordMapper;
    private final TaskCodeMapper taskCodeMapper;
    private final TaskSettingMapper taskSettingMapper;

    private final RedisUtils redisUtils;

    @Autowired
    public TaskServiceImpl(TaskMapper taskMapper,
                           TaskRecordMapper taskRecordMapper,
                           TaskCodeMapper taskCodeMapper,
                           TaskSettingMapper taskSettingMapper,
                           RedisUtils redisUtils) {
        this.taskMapper = taskMapper;
        this.taskRecordMapper = taskRecordMapper;
        this.taskCodeMapper = taskCodeMapper;
        this.taskSettingMapper = taskSettingMapper;
        this.redisUtils = redisUtils;
    }

    @Override
    public void createTaskBasic(String title, String description, Long userID) {
//        taskMapper.addTask(new SnowFlake(0, 0).nextId(), title, description, userID);
        logger.error("不应调用此接口");
    }

    @Override
    @Transactional
    public void createTask(String title, String description, LocalDateTime endTime,
                           Long userID, TaskSettingItem taskSettingItem) {
        Long taskID = new SnowFlake(0, 0).nextId();

        taskMapper.addTask(taskID, title, description, endTime.toString(), userID);

        try {
            byte[] taskSettingItemBytes = SerializeUtils.serialize(taskSettingItem);
            taskSettingMapper.addSetting(taskID, taskSettingItemBytes);
        } catch (IOException e) {
            logger.error("序列化失败", e);
            throw new BusinessException("内部错误", 500);
        }
    }

    @Override
    @Deprecated
    public Task getTaskBasicInfo(Long taskID) {
        return this.validateAndGet(taskID);
    }

    @Override
    public TaskInfoDTO getTaskInfo(Long taskID) {
        TaskInfoDTO taskInfoDTO = new TaskInfoDTO();

        Task task = taskMapper.getTask(taskID);

        if (null == task) {
            throw new TaskNotFoundException();
        }

        // BeanCopy
        taskInfoDTO.setId(taskID);
        taskInfoDTO.setTitle(task.getTitle());
        taskInfoDTO.setDescription(task.getDescription());
        taskInfoDTO.setUserId(task.getUserId());
        taskInfoDTO.setStatus(task.getStatus());
        taskInfoDTO.setEndTime(task.getEndTime());
        taskInfoDTO.setCreateTime(task.getCreateTime());


        TaskSettingDO taskSettingDO = taskSettingMapper.getSetting(taskID);

        try {
            TaskSettingItem taskSettingItem = SerializeUtils.deserialize(taskSettingDO.getSetting());
            taskInfoDTO.setTaskSettingItem(taskSettingItem);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return taskInfoDTO;
    }

    @Override
    public List<Task> getTaskListByUserID(Long userID) {
        return taskMapper.getTaskListByUserID(userID);
    }

    @Override
    public void take(Long taskID, Long takeUserID, String token) {
        this._take(taskID, takeUserID, token);
    }

    @Override
    public String generateCode(Long taskID, Long userID, Integer ttl) {
        String generatedCode = RandomGenerator.nextTaskCode();

        Task task = this.validateAndGet(taskID);

        if (task.getStatus().equals(1)) {
            throw new TaskAlreadyClosed();
        }

        // 判断当前 Task 是否已经生成了 Code
        // 如果已经生成过并且未过期，就返回这个 Code
        TaskCode taskCode = taskCodeMapper.getByTaskID(taskID, false);
        if (null != taskCode) {
            return taskCode.getCode();
        }

        // 判断验证码是否重复
        while ((taskCode = taskCodeMapper.getByCode(generatedCode, false)) != null
                && !taskCode.getTaskID().equals(taskID)) {
            generatedCode = RandomGenerator.nextTaskCode();
        }

        taskCodeMapper.add(taskID, generatedCode, ttl);

        return generatedCode;
    }

    @Override
    public void codeTake(String code, Long takeUserID) {
        // 根据考勤码获取考勤任务
        String redisKey = "task:code:" + code;

        // 判断考勤码是否存在
        TaskCode taskCode = taskCodeMapper.getByCode(code, false);

        if (null == taskCode) {
            throw new TaskCodeErrorException();
        }

        long taskID = taskCode.getTaskID();

        // 进行考勤
        this._take(taskID, takeUserID, "");
    }

    @Override
    public void modifyTaskStatus(Long taskID, Long userID, Integer status) {
        Task task = this.validateAndGet(taskID);

        if (!task.getUserId().equals(userID)) {
            throw new TaskNotFoundException();
        }

        taskMapper.modifyTaskStatus(taskID, status);
    }

    @Override
    public Task validateAndGet(Long taskID) {
        // 判断任务状态
        Task task = taskMapper.getTask(taskID);

        if (null == task ||
                task.getStatus().equals(TaskStatus.DELETED.value()) ||
                task.getStatus().equals(TaskStatus.BANNED.value())) {
            throw new TaskNotFoundException();
        }

        return task;
    }

    @Override
    public List<ParticipantDTO> getParticipantList(Long taskID) {
        this.validateAndGet(taskID);

        List<ParticipantDO> participantDOList = taskRecordMapper.getParticipantListByTaskID(taskID);

        List<ParticipantDTO> participantDTOList = new ArrayList<>();

        participantDOList.forEach(item -> {
            ParticipantDTO participantDTO = new ParticipantDTO(item.getUserID(), item.getUserName(), item.getTakeTime());
            participantDTOList.add(participantDTO);
        });
        return participantDTOList;
    }

    @Override
    public String generateToken(TokenDTO tokenDTO) {

        String token = UUID.randomUUID().toString();

        try {
            byte[] bytes = SerializeUtils.serialize(tokenDTO);

            redisUtils.setKey("task:token:" + token, Base64.encodeBase64String(bytes), 5 * 60);
        } catch (IOException e) {
            logger.error("序列化发生错误", e);
            throw new BusinessException("系统错误", 500);
        }

        return token;
    }

    @Override
    public Task getTaskByTakeCode(String code) {
        TaskCode taskCode = taskCodeMapper.getByCode(code, false);

        if (null == taskCode) {
            throw new TaskNotFoundException();
        }

        return this.validateAndGet(taskCode.getTaskID());
    }

    @Override
    public List<TakeRecordDTO> getTakeRecord(Long userID) {

        List<TakeRecordDO> takeRecordDOList = taskRecordMapper.listTakeRecord(userID);

        List<TakeRecordDTO> takeRecordDTOList = new LinkedList<>();

        takeRecordDOList.forEach(item -> {
            takeRecordDTOList.add(new TakeRecordDTO(item.getTaskID(),
                    item.getRecordStatus(),
                    item.getRecordCreateTime(),
                    item.getTaskTitle(),
                    item.getTaskDescription(),
                    item.getTaskStatus()));
        });

        return takeRecordDTOList;
    }

    @Transactional
    public void _take(Long taskID, Long userID, String token) {
        String base64TokenDTO = redisUtils.getKey("task:token:" + token);

        // 判断 Token 是否存在
        if (StringUtils.isBlank(base64TokenDTO)) {
            logger.warn("找不到Token: {}", token);
            throw new InvalidTokenException();
        }

        // 反序列化Token
        TokenDTO tokenDTO;
        try {
            tokenDTO = SerializeUtils.deserialize(Base64.decodeBase64(base64TokenDTO));
        } catch (IOException | ClassNotFoundException e) {
            logger.error("反序列化发生错误", e);
            throw new BusinessException("系统错误", 500);
        }

        // 验证 Token 是否有效
        if (!tokenDTO.taskID().equals(taskID) ||
                !tokenDTO.userID().equals(userID)) {
            logger.warn("Token 信息错误，Token UserID: {}，实际 UserID: {}，Token TaskID: {}，实际 TaskID:{}",
                    tokenDTO.userID(), userID, tokenDTO.taskID(), taskID);
            throw new InvalidTokenException();
        }

        // 校验时间戳
        if ((System.currentTimeMillis() - tokenDTO.timestamp()) > 5 * 60 * 1000) {
            logger.warn("Token[{}] 已过期", token);
            throw new InvalidTokenException();
        }


        // 判断任务状态
        Task task = validateAndGet(taskID);

        // 判断是否已经参加过考勤了
        if (null != taskRecordMapper.getRecordByTaskIDAndUserID(taskID, userID)) {
            throw new TaskAlreadyTakeException();
        }

        // 全部通过后删除Token
        redisUtils.deleteKey("task:token:" + token);


        // 写入考勤记录
        taskRecordMapper.addRecord(taskID, userID);
    }
}
