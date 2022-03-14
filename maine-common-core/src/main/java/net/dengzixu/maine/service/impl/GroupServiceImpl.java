package net.dengzixu.maine.service.impl;

import net.dengzixu.maine.Group;
import net.dengzixu.maine.exception.group.GroupNotFoundException;
import net.dengzixu.maine.mapper.GroupMapper;
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

    @Autowired
    public GroupServiceImpl(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
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

}
