package net.dengzixu.maine.service;

import net.dengzixu.maine.entity.Task;
import net.dengzixu.maine.entity.TaskSettingItem;
import net.dengzixu.maine.entity.dto.ParticipantDTO;
import net.dengzixu.maine.entity.dto.TakeRecordDTO;
import net.dengzixu.maine.entity.dto.TaskInfoDTO;
import net.dengzixu.maine.entity.dto.TokenDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {
    /**
     * 创建基本考勤任务
     *
     * @param title       标题
     * @param description 描述
     * @param userID      创建者 user ID
     */
    @Deprecated
    void createTaskBasic(String title, String description, Long userID);

    /**
     * 创建考勤任务
     *
     * @param title           任务名称
     * @param description     任务描述
     * @param userID          创建者 User ID
     * @param taskSettingItem 考勤设置
     */
    void createTask(String title, String description, LocalDateTime endTime,
                    Long userID, TaskSettingItem taskSettingItem);

    /**
     * 获取考勤任务基本信息
     *
     * @param taskID Task ID
     * @return Task
     */
    @Deprecated
    Task getTaskBasicInfo(Long taskID);

    /**
     * 获取考勤任务信息
     *
     * @param taskID Task ID
     * @return TaskInfoDTO
     */
    TaskInfoDTO getTaskInfo(Long taskID);

    /**
     * 获取用户创建的所有考勤任务
     *
     * @param userID 用户 ID
     * @return List<Task>s
     */
    List<Task> getTaskListByUserID(Long userID);

    /**
     * 参加考勤
     *
     * @param taskID Task ID
     * @param userID 参加考勤的 User ID
     */
    void take(Long taskID, Long userID, String token);

    /**
     * 生成考勤码
     *
     * @param taskID Task ID
     * @param userID 生成考勤码 User ID，考勤码仅允许考勤创建者生成
     * @return 考勤码
     */
    String generateCode(Long taskID, Long userID, Integer ttl);

    /**
     * 考勤码 参加考勤
     *
     * @param code       考勤码
     * @param takeUserID 参加考勤的 User ID
     */
    void codeTake(String code, Long takeUserID);

    /**
     * 修改任务状态
     *
     * @param taskID Task ID
     * @param userID 修改者 User ID
     * @param status 修改后的状态
     */
    void modifyTaskStatus(Long taskID, Long userID, Integer status);

    /**
     * 判断 Task 的状态
     *
     * @param taskID Task ID
     * @return Task
     */
    Task validateAndGet(Long taskID);

    /**
     * 获取 Task 的参与者
     *
     * @param taskID Task ID
     * @return List<User>
     */
    List<ParticipantDTO> getParticipantList(Long taskID);

    /**
     * 生成考勤用 Token
     *
     * @return Token
     */
    String generateToken(TokenDTO tokenDTO);

    /**
     * 根据考勤码获取 Task
     *
     * @param code 考勤码
     * @return Task
     */
    Task getTaskByTakeCode(String code);

    /**
     * 获取考勤记录
     *
     * @param userID 用户 ID
     * @return List<TakeRecordDTO>
     */
    List<TakeRecordDTO> getTakeRecord(Long userID);
}
