package net.dengzixu.maine.web.api.v1.user;

import net.dengzixu.constant.enums.UserStatus;
import net.dengzixu.maine.entity.User;
import net.dengzixu.maine.entity.vo.user.UserListVO;
import net.dengzixu.maine.entity.vo.user.UserVO;
import net.dengzixu.maine.model.APIResponseMap;
import net.dengzixu.maine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public ResponseEntity<APIResponseMap> liseUser() {
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
}
