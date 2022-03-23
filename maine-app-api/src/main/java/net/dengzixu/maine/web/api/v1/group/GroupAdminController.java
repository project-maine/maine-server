package net.dengzixu.maine.web.api.v1.group;

import net.dengzixu.maine.Group;
import net.dengzixu.maine.constant.enums.GroupStatus;
import net.dengzixu.maine.entity.bo.group.GroupAddBO;
import net.dengzixu.maine.entity.dto.GroupNumberDTO;
import net.dengzixu.maine.entity.dto.JoinedGroupDTO;
import net.dengzixu.maine.entity.vo.group.*;
import net.dengzixu.maine.exception.common.TokenExpiredException;
import net.dengzixu.maine.model.APIResponseMap;
import net.dengzixu.maine.service.GroupService;
import net.dengzixu.maine.service.UserService;
import net.dengzixu.maine.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/v1/group/admin")
public class GroupAdminController {
    private static final Logger logger = LoggerFactory.getLogger(GroupAdminController.class);

    private final UserService userService;
    private final GroupService groupService;
    private final JWTUtils jwtUtils;

    @Autowired
    public GroupAdminController(UserService userService, GroupService groupService, JWTUtils jwtUtils) {
        this.userService = userService;
        this.groupService = groupService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponseMap> add(@RequestHeader("Authorization") String authorization,
                                              @RequestBody GroupAddBO groupAddBO,
                                              BindingResult bindingResult) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        userService.validate(userID);

        // 数据校验
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(APIResponseMap.FAILED(-1, bindingResult.getFieldError()));
        }

        groupService.create(groupAddBO.name(), groupAddBO.description(), userID);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED(""));
    }

    @GetMapping("/list")
    public ResponseEntity<APIResponseMap> list(@RequestHeader("Authorization") String authorization) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        userService.validate(userID);

        List<Group> groupList = groupService.getList(userID);

        GroupInfoListVO groupInfoListVO = new GroupInfoListVO(new LinkedList<>());

        groupList.stream()
                .filter(item -> !item.getStatus().equals(GroupStatus.BANNED.value()))
                .filter(item -> !item.getStatus().equals(GroupStatus.DELETED.value())).forEach(item -> {
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

    @GetMapping("{groupID}/numbers")
    public ResponseEntity<APIResponseMap> getNumberList(@RequestHeader("Authorization") String authorization,
                                                        @PathVariable Long groupID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        userService.validate(userID);

        List<GroupNumberDTO> groupNumberDTOList = groupService.getGroupNumberList(groupID, userID);

        GroupNumberListVO groupNumberListVO = new GroupNumberListVO(new LinkedList<>());

        groupNumberDTOList.forEach(item -> {
            groupNumberListVO.groupNumberVOList().add(new GroupNumberVO(item.getUserID(), item.getUserName(), item.getJoinTime()));
        });

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", groupNumberListVO));
    }

    @PostMapping("{groupID}/open")
    public ResponseEntity<APIResponseMap> open(@RequestHeader("Authorization") String authorization,
                                               @PathVariable Long groupID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        userService.validate(userID);

        groupService.open(groupID, userID);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED(""));
    }

    @PostMapping("{groupID}/close")
    public ResponseEntity<APIResponseMap> close(@RequestHeader("Authorization") String authorization,
                                                @PathVariable Long groupID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        userService.validate(userID);

        groupService.close(groupID, userID);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED(""));
    }

    @RequestMapping(value = "{groupID}/delete", method = {RequestMethod.DELETE, RequestMethod.POST})
    public ResponseEntity<APIResponseMap> delete(@RequestHeader("Authorization") String authorization,
                                                 @PathVariable Long groupID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        userService.validate(userID);

        groupService.delete(groupID, userID);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED(""));
    }

    @GetMapping("/joined/list")
    public ResponseEntity<APIResponseMap> joinedList(@RequestHeader("Authorization") String authorization) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        userService.validate(userID);

        List<JoinedGroupDTO> joinedGroupDTOList = groupService.getJoinedGroupList(userID);

        JoinedGroupListVO joinedGroupListVO = new JoinedGroupListVO(new LinkedList<>());

        joinedGroupDTOList
                .stream()
                .filter(item -> !item.groupStatus().equals(GroupStatus.BANNED.value()))
                .filter(item -> !item.groupStatus().equals(GroupStatus.DELETED.value()))
                .forEach(item -> {
                    joinedGroupListVO.joinedGroupList().add(new JoinedGroupVO(item.groupID(),
                            item.groupName(),
                            item.groupDescription(),
                            item.groupStatus(),
                            item.joinTime()));
                });

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", joinedGroupListVO));

    }
}
