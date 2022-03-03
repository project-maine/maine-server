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

    @Autowired
    public AttendanceServiceImpl(TaskMapper taskMapper,
                                 TaskRecordMapper taskRecordMapper,
                                 TaskCodeMapper taskCodeMapper) {
        this.taskMapper = taskMapper;
        this.taskRecordMapper = taskRecordMapper;
        this.taskCodeMapper = taskCodeMapper;
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
    public void codeTake(String code, Long takeUserID) {
        // 根据考勤码获取考勤任务
        TaskCode taskCode = taskCodeMapper.get(code, false);

        // 判断考勤码是否有效
        if (null == taskCode) {
            throw new TaskCodeErrorException();
        }

        // 进行考勤
        this.take(taskCode.getTaskID(), takeUserID, TakeType.CODE);
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
