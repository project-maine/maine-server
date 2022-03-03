package net.dengzixu.maine.service.impl;

import net.dengzixu.maine.entity.Task;
import net.dengzixu.maine.exception.attendance.AttendanceAlreadyTakeException;
import net.dengzixu.maine.exception.task.TaskCodeErrorException;
import net.dengzixu.maine.exception.task.TaskNotFoundException;
import net.dengzixu.maine.mapper.TaskCodeMapper;
import net.dengzixu.maine.mapper.TaskMapper;
import net.dengzixu.maine.mapper.TaskRecordMapper;
import net.dengzixu.maine.service.AttendanceService;
import net.dengzixu.maine.utils.RandomGenerator;
import net.dengzixu.maine.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceServiceImpl.class);

    private final TaskMapper taskMapper;
    private final TaskRecordMapper taskRecordMapper;
    private final TaskCodeMapper taskCodeMapper;

    private final RedisUtils redisUtils;

    @Autowired
    public AttendanceServiceImpl(TaskMapper taskMapper,
                                 TaskRecordMapper taskRecordMapper,
                                 TaskCodeMapper taskCodeMapper,
                                 RedisUtils redisUtils) {
        this.taskMapper = taskMapper;
        this.taskRecordMapper = taskRecordMapper;
        this.taskCodeMapper = taskCodeMapper;
        this.redisUtils = redisUtils;
    }

    @Override
    public void createTaskBasic(String title, String description, Long userID) {
        taskMapper.addTask(title, description, userID);
    }

    @Override
    public Task getTaskBasicInfo(Long taskID) {
        Task task = taskMapper.getTask(taskID);

        if (null == task) {
            throw new TaskNotFoundException();
        }

        return task;
    }


    @Override
    public void webTake(Long taskID, Long takeUserID) {
        this.take(taskID, takeUserID, TakeType.WEB);
    }

    @Override
    public String generateCode(Long taskID, Long userID, Integer ttl) {
        String generatedCode = RandomGenerator.nextTaskCode();
        String redisKey = "task:code:" + generatedCode;

        // 判断Code是否重复，重复就再生成一个
        while (redisUtils.hasKey(redisKey)) {
            // 如归恰巧 考勤码和 Task ID 重复了... 算了 没有这种巧合
//            if (taskID.equals(Long.parseLong(redisUtils.getKey(redisKey)))){
//                break;
//            }
            generatedCode = RandomGenerator.nextTaskCode();
            redisKey = "task:code:" + generatedCode;
        }

        // Code 存入 Redis
        redisUtils.setKey(redisKey, String.valueOf(taskID), ttl);

        return generatedCode;
    }

    @Override
    public void codeTake(String code, Long takeUserID) {
        // 根据考勤码获取考勤任务
//        TaskCode taskCode = taskCodeMapper.get(code, false);
        String redisKey = "task:code:" + code;

        // 判断考勤码是否存在
        if (!redisUtils.hasKey(redisKey)) {
            throw new TaskCodeErrorException();
        }

        long taskID = Long.parseLong(redisUtils.getKey(redisKey));

        // 进行考勤
        this.take(taskID, takeUserID, TakeType.CODE);
    }

    private void take(Long taskID, Long takeUserID, TakeType takeType) {
        // 判断是否已经参加过考勤了
        if (null != taskRecordMapper.getAttendanceTaskByIDAndUserID(taskID, takeUserID)) {
            throw new AttendanceAlreadyTakeException();
        }

        // 写入考勤记录
        taskRecordMapper.addRecord(taskID, takeUserID);
    }

    private enum TakeType {
        CODE, WEB;
    }
}
