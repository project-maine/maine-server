package net.dengzixu.maine.service.impl;

import net.dengzixu.maine.Group;
import net.dengzixu.maine.constant.enums.GroupStatus;
import net.dengzixu.maine.entity.dataobject.GroupNumberDO;
import net.dengzixu.maine.entity.dataobject.JoinedGroupDO;
import net.dengzixu.maine.entity.dto.GroupNumberDTO;
import net.dengzixu.maine.entity.dto.JoinedGroupDTO;
import net.dengzixu.maine.exception.group.*;
import net.dengzixu.maine.mapper.group.GroupMapper;
import net.dengzixu.maine.mapper.group.GroupNumberMapper;
import net.dengzixu.maine.service.GroupService;
import net.dengzixu.maine.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    private static final SnowFlake snowFlake = new SnowFlake(0, 0);

    private final GroupMapper groupMapper;
    private final GroupNumberMapper groupNumberMapper;

    @Autowired
    public GroupServiceImpl(GroupMapper groupMapper, GroupNumberMapper groupNumberMapper) {
        this.groupMapper = groupMapper;
        this.groupNumberMapper = groupNumberMapper;
    }

    @Override
    public void create(String name, String description, Long userID) {
        groupMapper.create(snowFlake.nextId(), name, description, userID);
    }

    @Override
    public Group getByID(Long id) {
        return this.getAndValidate(id);
    }

    @Override
    public List<Group> getList(Long userID) {
        return groupMapper.getListByUserID(userID);
    }

    @Override
    public void join(Long groupID, Long userID) {
        Group group = getByID(groupID);

        if (null == group) {
            throw new GroupNotFoundException();
        }

        if (groupNumberMapper.isUserInGroup(groupID, userID, true) > 0) {
            throw new UserAlreadyJoinGroupException();
        }

        groupNumberMapper.add(groupID, userID);
    }

    @Override
    public void leave(Long groupID, Long userID) {
        if (groupNumberMapper.isUserInGroup(groupID, userID, false) < 1) {
            throw new UserNotJoinGroupException();
        }

        groupNumberMapper.modifyStatus(groupID, userID, 1);
    }

    @Override
    public List<GroupNumberDTO> getGroupNumberList(Long groupID, Long userID) {

        List<GroupNumberDO> groupNumberDOList = groupNumberMapper.getGroupNumberList(groupID, userID);

        List<GroupNumberDTO> groupNumberDTOList = new LinkedList<>();

        groupNumberDOList.forEach(item -> {
            groupNumberDTOList.add(new GroupNumberDTO() {{
                setUserID(item.getUserID());
                setUserName(item.getUserName());
                setJoinTime(item.getCreateTime());
            }});
        });

        return groupNumberDTOList;
    }

    @Override
    public void open(Long groupID, Long userID) {
        Group group = getAndValidate(groupID);

        if (!group.getUserID().equals(userID)) {
            throw new GroupNotFoundException();
        } else if (group.getStatus().equals(GroupStatus.DEFAULT.value())) {
            throw new GroupAlreadyOpenException();
        }

        groupMapper.modifyGroupStatus(groupID, GroupStatus.DEFAULT.value());
    }

    @Override
    public void close(Long groupID, Long userID) {
        Group group = getAndValidate(groupID);

        if (!group.getUserID().equals(userID)) {
            throw new GroupNotFoundException();
        } else if (group.getStatus().equals(GroupStatus.CLOSED.value())) {
            throw new GroupAlreadyCloseException();
        }

        groupMapper.modifyGroupStatus(groupID, GroupStatus.CLOSED.value());
    }

    @Override
    public void delete(Long groupID, Long userID) {
        Group group = getAndValidate(groupID);

        if (!group.getUserID().equals(userID)) {
            throw new GroupNotFoundException();
        }

        groupMapper.modifyGroupStatus(groupID, GroupStatus.DELETED.value());
    }

    @Override
    public List<JoinedGroupDTO> getJoinedGroupList(Long userID) {
        List<JoinedGroupDO> joinedGroupDOList = groupNumberMapper.getJoinedGroupList(userID);

        List<JoinedGroupDTO> joinedGroupDTOList = new LinkedList<>();

        joinedGroupDOList.forEach(item -> {
            joinedGroupDTOList.add(new JoinedGroupDTO(item.getGroupID(),
                    item.getGroupName(),
                    item.getGroupDescription(),
                    item.getGroupStatus(),
                    item.getJoinTime()));
        });

        return joinedGroupDTOList;
    }

    @Override
    public Group getAndValidate(Long groupID) {
        Group group = groupMapper.getByID(groupID);

        if (null == group ||
                group.getStatus().equals(GroupStatus.DELETED.value())) {
            throw new GroupNotFoundException();
        }

        return group;
    }
}
