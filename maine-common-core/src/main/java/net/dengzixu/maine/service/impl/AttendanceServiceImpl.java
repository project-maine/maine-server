package net.dengzixu.maine.service.impl;

import net.dengzixu.maine.mapper.AttendanceMapper;
import net.dengzixu.maine.service.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceServiceImpl.class);

    private final AttendanceMapper attendanceMapper;

    @Autowired
    public AttendanceServiceImpl(AttendanceMapper attendanceMapper) {
        this.attendanceMapper = attendanceMapper;
    }

    @Override
    public void createBasic(String title, String description, Long userID) {
        attendanceMapper.addTask(title, description, userID);
    }

    @Override
    public void webTake(Long taskID, Long takeUserID) {
        attendanceMapper.addRecord(taskID, takeUserID);
    }
}
