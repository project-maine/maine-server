package net.dengzixu.maine.service;

import net.dengzixu.maine.entity.Task;

import java.util.List;

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
     * 获取用户创建的所有考勤任务
     *
     * @param userID 用户 ID
     * @return List<Task>s
     */
    List<Task> getTaskListByUserID(Long userID);

    /**
     * Web端 参加考勤
     *
     * @param taskID     Task ID
     * @param takeUserID 参加考勤的 User ID
     */
    void webTake(Long taskID, Long takeUserID);

    /**
     * 生成考勤码
     *
     * @param taskID Task ID
     * @param userID 生成考勤码 User ID，考勤码仅允许考勤创建者生成
     * @return 考勤码
     */
    String generateCode(Long taskID, Long userID, Integer tll);

    /**
     * 考勤码 参加考勤
     *
     * @param code       考勤码
     * @param takeUserID 参加考勤的 User ID
     */
    void codeTake(String code, Long takeUserID);
}
