package net.dengzixu.maine.service.impl;

import net.dengzixu.maine.entity.Task;
import net.dengzixu.maine.exception.attendance.AttendanceAlreadyTakeException;
import net.dengzixu.maine.exception.task.TaskNotFoundException;
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

    @Autowired
    public AttendanceServiceImpl(TaskMapper taskMapper, TaskRecordMapper taskRecordMapper) {
        this.taskMapper = taskMapper;
        this.taskRecordMapper = taskRecordMapper;
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
        if (null != taskRecordMapper.getAttendanceTaskByIDAndUserID(taskID, takeUserID)) {
            throw new AttendanceAlreadyTakeException();
        }

        taskRecordMapper.addRecord(taskID, takeUserID);
    }
}
