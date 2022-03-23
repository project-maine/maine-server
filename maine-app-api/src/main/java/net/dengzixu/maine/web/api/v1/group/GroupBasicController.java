package net.dengzixu.maine.web.api.v1.group;

import net.dengzixu.maine.Group;
import net.dengzixu.maine.entity.vo.group.GroupInfoVO;
import net.dengzixu.maine.exception.common.TokenExpiredException;
import net.dengzixu.maine.model.APIResponseMap;
import net.dengzixu.maine.service.GroupService;
import net.dengzixu.maine.service.UserService;
import net.dengzixu.maine.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/group")
public class GroupBasicController {
    private static final Logger logger = LoggerFactory.getLogger(GroupBasicController.class);

    private final UserService userService;
    private final GroupService groupService;
    private final JWTUtils jwtUtils;

    @Autowired
    public GroupBasicController(UserService userService, GroupService groupService, JWTUtils jwtUtils) {
        this.userService = userService;
        this.groupService = groupService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/{groupID}/info")
    public ResponseEntity<APIResponseMap> info(@PathVariable("groupID") Long groupID) {

        Group group = groupService.getByID(groupID);

        // BeanCopy
        GroupInfoVO groupInfoVO = new GroupInfoVO(group.getId(), group.getName(),
                group.getDescription(), group.getUserID(), group.getStatus(),
                group.getCreateTime(), group.getModifyTime());

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", groupInfoVO));
    }

    @PostMapping("/{groupID}/join")
    public ResponseEntity<APIResponseMap> join(@RequestHeader("Authorization") String authorization,
                                               @PathVariable("groupID") Long groupID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        userService.validate(userID);

        groupService.join(groupID, userID);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED(""));
    }

    @PostMapping("/{groupID}/leave")
    public ResponseEntity<APIResponseMap> leave(@RequestHeader("Authorization") String authorization,
                                                @PathVariable("groupID") Long groupID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        userService.validate(userID);

        groupService.leave(groupID, userID);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED(""));
    }
}
