package net.dengzixu.maine.service.impl;

import net.dengzixu.maine.entity.Task;
import net.dengzixu.maine.entity.TaskCode;
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

import java.util.List;

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
    public List<Task> getTaskListByUserID(Long userID) {
        return taskMapper.getTaskListByUserID(userID);
    }

    @Override
    public void webTake(Long taskID, Long takeUserID) {
        this.take(taskID, takeUserID, TakeType.WEB);
    }

    @Override
    public String generateCode(Long taskID, Long userID, Integer ttl) {
        String generatedCode = RandomGenerator.nextTaskCode();

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
//        if (!redisUtils.hasKey(redisKey)) {
//            throw new TaskCodeErrorException();
//        }
        TaskCode taskCode = taskCodeMapper.getByCode(code, false);

        if (null == taskCode) {
            throw new TaskCodeErrorException();
        }

//        long taskID = Long.parseLong(redisUtils.getKey(redisKey));

        long taskID = taskCode.getTaskID();

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
