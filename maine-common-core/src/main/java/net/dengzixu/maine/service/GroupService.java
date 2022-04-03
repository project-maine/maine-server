package net.dengzixu.maine.service;

import net.dengzixu.maine.Group;
import net.dengzixu.maine.entity.dto.GroupNumberDTO;
import net.dengzixu.maine.entity.dto.JoinedGroupDTO;

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
     * 获取小组列表
     *
     * @param userID 创建者 ID
     * @return List<Group>
     */
    List<Group> getList(Long userID);

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
     * 开启小组
     *
     * @param groupID 小组 ID
     * @param userID  创建者 ID
     */
    void open(Long groupID, Long userID);

    /**
     * 关闭小组
     *
     * @param groupID 小组 ID
     * @param userID  创建者 ID
     */
    void close(Long groupID, Long userID);

    /**
     * 删除小组
     *
     * @param groupID 小组 ID
     * @param userID  创建者 ID
     */
    void delete(Long groupID, Long userID);

    /**
     * 修改小组状态
     *
     * @param groupID 小组 ID
     * @param status  状态
     */
    void changeStatus(Long groupID, Integer status);

    /**
     * 获取加入的小组列表
     *
     * @param userID 用户 ID
     * @return List<JoinedGroupDTO>
     */
    List<JoinedGroupDTO> getJoinedGroupList(Long userID);

    Group getAndValidate(Long groupID);

    List<Group> listAllGroup();
}
