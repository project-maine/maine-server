package net.dengzixu.maine.web.api.v1.user;

import net.dengzixu.constant.enums.GroupStatus;
import net.dengzixu.constant.enums.UserStatus;
import net.dengzixu.maine.entity.User;
import net.dengzixu.maine.entity.vo.user.UserListVO;
import net.dengzixu.maine.entity.vo.user.UserVO;
import net.dengzixu.maine.exception.common.TokenExpiredException;
import net.dengzixu.maine.model.APIResponseMap;
import net.dengzixu.maine.service.AdminService;
import net.dengzixu.maine.service.UserService;
import net.dengzixu.maine.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;
    private final AdminService adminService;
    private final JWTUtils jwtUtils;

    @Autowired
    public UserController(UserService userService, AdminService adminService, JWTUtils jwtUtils) {
        this.userService = userService;
        this.adminService = adminService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/list")
    public ResponseEntity<APIResponseMap> liseUser(@RequestHeader("Authorization") String authorization) {
        long id = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        adminService.validate(id);


        List<User> userList = userService.getAllUser();

        UserListVO userListVO = new UserListVO(new LinkedList<>());

        userList.stream()
                .filter(i -> !i.getStatus().equals(UserStatus.DELETED.value()))
                .forEach(item -> {
                    userListVO.userList().add(new UserVO(item.getId(),
                            item.getName(),
                            item.getPasswordStatus(),
                            item.getPhone(),
                            item.getPhoneStatus(),
                            item.getEmail(),
                            item.getEmailStatus(),
                            item.getStatus(),
                            item.getCreateTime()));
                });


        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", userListVO));
    }

    @RequestMapping(value = "/{taskID}/{operate}", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<APIResponseMap> modifyStatus(@RequestHeader("Authorization") String authorization,
                                                       @PathVariable Long taskID,
                                                       @PathVariable String operate) {

        long id = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        adminService.validate(id);

        switch (operate) {
            case "ban" -> userService.changeStatus(taskID, UserStatus.BANNED.value());
            case "delete" -> userService.changeStatus(taskID, UserStatus.DELETED.value());
            case "unban", "pass" -> userService.changeStatus(taskID, UserStatus.DEFAULT.value());
            default -> {
                return ResponseEntity.status(400).body(APIResponseMap.FAILED(-20, "未知操作"));
            }
        }


        return ResponseEntity.ok(APIResponseMap.SUCCEEDED(""));
    }
}
