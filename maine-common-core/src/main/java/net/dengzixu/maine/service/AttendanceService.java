package net.dengzixu.maine.service;

import net.dengzixu.maine.entity.Task;

public interface AttendanceService {
    /**
     * 创建基本考勤任务
     *
     * @param title       标题
     * @param description 描述
     * @param userID      创建者 user ID
     */
    void createTaskBasic(String title, String description, Long userID);

    /**
     * 获取考勤任务基本信息
     *
     * @param taskID Task ID
     * @return Task
     */
    Task getTaskBasicInfo(Long taskID);

    /**
     * Web端 参加考勤
     *
     * @param taskID     Task ID
     * @param takeUserID 参加考勤的 User ID
     */
    void webTake(Long taskID, Long takeUserID);

    /**
     * 考勤码 参加考勤
     *
     * @param code       考勤码
     * @param takeUserID 参加考勤的 User ID
     */
    void codeTake(String code, Long takeUserID);
}
