package net.dengzixu.maine.service.impl;

import net.dengzixu.maine.mapper.GroupMapper;
import net.dengzixu.maine.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupMapper groupMapper;

    @Autowired
    public GroupServiceImpl(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    @Override
    public void create(Long userID, String name, String description) {
        groupMapper.create(userID, name, description);
    }
}
