package net.dengzixu.maine.service;

import net.dengzixu.maine.Group;
import net.dengzixu.maine.entity.dto.GroupNumberDTO;

import java.util.List;

public interface GroupService {
    /**
     * 创建小组
     *
     * @param name        小组名称
     * @param description 小组描述
     * @param userID      创建者 User ID
     */
    void create(String name, String description, Long userID);

    /**
     * 通过 ID 获取小组信息
     *
     * @param id 小组 ID
     * @return Group
     */
    Group getByID(Long id);

    /**
     * 加入小组
     *
     * @param groupID 小组 ID
     * @param userID  用户 ID
     */
    void join(Long groupID, Long userID);

    /**
     * 退出小组
     *
     * @param groupID 小组 ID
     * @param userID  用户 ID
     */
    void leave(Long groupID, Long userID);

    /**
     * 获取小组成员
     *
     * @param groupID 小组 ID
     * @param userID  创建者 ID
     * @return List<GroupNumberDTO>
     */
    List<GroupNumberDTO> getGroupNumberList(Long groupID, Long userID);

    /**
     * 删除小组
     *
     * @param groupID 小组 ID
     * @param userID  创建者 ID
     */
    void delete(Long groupID, Long userID);
}
