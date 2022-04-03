package net.dengzixu.maine.web.api.v1.group;

import net.dengzixu.constant.enums.GroupStatus;
import net.dengzixu.maine.Group;
import net.dengzixu.maine.entity.vo.group.GroupInfoListVO;
import net.dengzixu.maine.entity.vo.group.GroupInfoVO;
import net.dengzixu.maine.exception.common.TokenExpiredException;
import net.dengzixu.maine.model.APIResponseMap;
import net.dengzixu.maine.service.AdminService;
import net.dengzixu.maine.service.GroupService;
import net.dengzixu.maine.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/v1/group")
public class GroupController {
    private final GroupService groupService;
    private final AdminService adminService;
    private final JWTUtils jwtUtils;


    @Autowired
    public GroupController(GroupService groupService, AdminService adminService, JWTUtils jwtUtils) {
        this.groupService = groupService;
        this.adminService = adminService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/list")
    public ResponseEntity<APIResponseMap> listGroups(@RequestHeader("Authorization") String authorization) {
        long id = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        adminService.validate(id);


        List<Group> groupList = groupService.listAllGroup();

        GroupInfoListVO groupInfoListVO = new GroupInfoListVO(new LinkedList<>());

        groupList.stream()
                .filter(i -> !i.getStatus().equals(GroupStatus.DELETED.value()))
                .forEach(item -> {
                    groupInfoListVO.groupInfoVOList().add(new GroupInfoVO(item.getId(),
                            item.getName(),
                            item.getDescription(),
                            item.getUserID(),
                            item.getStatus(),
                            item.getCreateTime(),
                            item.getModifyTime()));
                });


        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", groupInfoListVO));
    }
}
