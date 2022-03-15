package net.dengzixu.maine.service.impl;

import net.dengzixu.maine.Group;
import net.dengzixu.maine.exception.group.GroupNotFoundException;
import net.dengzixu.maine.exception.group.UserAlreadyJoinException;
import net.dengzixu.maine.mapper.GroupMapper;
import net.dengzixu.maine.mapper.GroupNumberMapper;
import net.dengzixu.maine.service.GroupService;
import net.dengzixu.maine.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Group group = groupMapper.getByID(id);

        if (null == group) {
            throw new GroupNotFoundException();
        }

        return group;
    }

    @Override
    public void join(Long groupID, Long userID) {
        Group group = getByID(groupID);

        if (null == group) {
            throw new GroupNotFoundException();
        }

        if (groupNumberMapper.isUserInGroup(groupID, userID) > 0) {
            throw new UserAlreadyJoinException();
        }

        groupNumberMapper.add(groupID, userID);
    }

}
