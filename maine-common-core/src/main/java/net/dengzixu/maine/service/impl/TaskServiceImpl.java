package net.dengzixu.maine.service.impl;

import net.dengzixu.constant.enums.enums.RecordStatus;
import net.dengzixu.constant.enums.enums.TaskStatus;
import net.dengzixu.maine.entity.Task;
import net.dengzixu.maine.entity.TaskCode;
import net.dengzixu.maine.entity.TaskSetting;
import net.dengzixu.maine.entity.TaskSettingItem;
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
import net.dengzixu.maine.service.GroupService;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskMapper taskMapper;
    private final TaskRecordMapper taskRecordMapper;
    private final TaskCodeMapper taskCodeMapper;
    private final TaskSettingMapper taskSettingMapper;
    private final GroupService groupService;

    private final RedisUtils redisUtils;

    @Autowired
    public TaskServiceImpl(TaskMapper taskMapper,
                           TaskRecordMapper taskRecordMapper,
                           TaskCodeMapper taskCodeMapper,
                           TaskSettingMapper taskSettingMapper,
                           GroupService groupService,
                           RedisUtils redisUtils) {
        this.taskMapper = taskMapper;
        this.taskRecordMapper = taskRecordMapper;
        this.taskCodeMapper = taskCodeMapper;
        this.taskSettingMapper = taskSettingMapper;
        this.groupService = groupService;
        this.redisUtils = redisUtils;
    }

    @Override
    public void createTaskBasic(String title, String description, Long userID) {
//        taskMapper.addTask(new SnowFlake(0, 0).nextId(), title, description, userID);
        logger.error("?????????????????????");
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
            logger.error("???????????????", e);
            throw new BusinessException("????????????", 500);
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


        TaskSetting taskSetting = taskSettingMapper.getSetting(taskID);

        try {
            TaskSettingItem taskSettingItem = SerializeUtils.deserialize(taskSetting.getSetting());
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

        // ???????????? Task ????????????????????? Code
        // ?????????????????????????????????????????????????????? Code
        TaskCode taskCode = taskCodeMapper.getByTaskID(taskID, false);
        if (null != taskCode) {
            return taskCode.getCode();
        }

        // ???????????????????????????
        while ((taskCode = taskCodeMapper.getByCode(generatedCode, false)) != null
                && !taskCode.getTaskID().equals(taskID)) {
            generatedCode = RandomGenerator.nextTaskCode();
        }

        taskCodeMapper.add(taskID, generatedCode, ttl);

        return generatedCode;
    }

    @Override
    public void codeTake(String code, Long takeUserID) {
        // ?????????????????????????????????
        String redisKey = "task:code:" + code;

        // ???????????????????????????
        TaskCode taskCode = taskCodeMapper.getByCode(code, false);

        if (null == taskCode) {
            throw new TaskCodeErrorException();
        }

        long taskID = taskCode.getTaskID();

        // ????????????
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
        // ??????????????????
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

        return taskRecordMapper.getParticipantListByTaskID(taskID);
    }

    @Override
    public String generateToken(TokenDTO tokenDTO) {

        String token = UUID.randomUUID().toString();

        try {
            byte[] bytes = SerializeUtils.serialize(tokenDTO);

            redisUtils.setKey("task:token:" + token, Base64.encodeBase64String(bytes), 5 * 60);
        } catch (IOException e) {
            logger.error("?????????????????????", e);
            throw new BusinessException("????????????", 500);
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
        return taskRecordMapper.listTakeRecord(userID);
    }

    @Transactional
    public void _take(Long taskID, Long userID, String token) {
        String base64TokenDTO = redisUtils.getKey("task:token:" + token);

        // ?????? Token ????????????
        if (StringUtils.isBlank(base64TokenDTO)) {
            logger.warn("?????????Token: {}", token);
            throw new InvalidTokenException();
        }

        // ????????????Token
        TokenDTO tokenDTO;
        try {
            tokenDTO = SerializeUtils.deserialize(Base64.decodeBase64(base64TokenDTO));
        } catch (IOException | ClassNotFoundException e) {
            logger.error("????????????????????????", e);
            throw new BusinessException("????????????", 500);
        }

        // ?????? Token ????????????
        if (!tokenDTO.taskID().equals(taskID) ||
                !tokenDTO.userID().equals(userID)) {
            logger.warn("Token ???????????????Token UserID: {}????????? UserID: {}???Token TaskID: {}????????? TaskID:{}",
                    tokenDTO.userID(), userID, tokenDTO.taskID(), taskID);
            throw new InvalidTokenException();
        }

        // ?????? Token ?????????
        if ((System.currentTimeMillis() - tokenDTO.timestamp()) > 5 * 60 * 1000) {
            logger.warn("Token[{}] ?????????", token);
            throw new InvalidTokenException();
        }

        // ??????????????????
        Task task = validateAndGet(taskID);

        TaskSetting taskSetting = taskSettingMapper.getSetting(task.getId());

        // ??????????????????
        TaskSettingItem taskSettingItem;
        try {
            taskSettingItem = SerializeUtils.deserialize(taskSetting.getSetting());
        } catch (IOException | ClassNotFoundException e) {
            logger.error("??????????????????", e);
            throw new BusinessException("????????????", 500);
        }

        List<Long> allowGroupsIDList = taskSettingItem.getAllowGroups();

        if (null != allowGroupsIDList && allowGroupsIDList.size() > 0) {
            var joinedGroupDTOList = groupService.getJoinedGroupList(userID);

            var x = joinedGroupDTOList.stream()
                    .filter(item -> allowGroupsIDList.contains(item.getGroupID())).toList();

            if (x.size() == 0) {
                throw new GroupLimitException();
            }
        }

        // ????????????????????????????????????
        if (null != taskRecordMapper.getRecordByTaskIDAndUserID(taskID, userID)) {
            throw new TaskAlreadyTakeException();
        }

        // ????????????????????????
        Integer status = RecordStatus.DEFAULT.value();
        LocalDateTime taskEndTime = LocalDateTime.parse(task.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if (taskEndTime.isBefore(LocalDateTime.now())) {
            status = RecordStatus.LATE.value();
        }

        // ?????????????????????Token
        redisUtils.deleteKey("task:token:" + token);


        // ??????????????????
        taskRecordMapper.addRecord(UUID.randomUUID().toString(), taskID, userID, status);
    }
}
