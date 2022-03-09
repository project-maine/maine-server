package net.dengzixu.maine.web.api.v1.user;

import net.dengzixu.maine.entity.User;
import net.dengzixu.maine.entity.vo.UserInfoVO;
import net.dengzixu.maine.exception.common.TokenExpiredException;
import net.dengzixu.maine.model.APIResponseMap;
import net.dengzixu.maine.service.CommonService;
import net.dengzixu.maine.service.UserService;
import net.dengzixu.maine.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final JWTUtils jwtUtils;

    @Autowired
    public UserController(UserService userService, JWTUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/info")
    public ResponseEntity<APIResponseMap> getUserInfo(@RequestHeader("Authorization") String authorization) {
        long id = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        // 验证用户状态
        userService.validate(id);

        User user = userService.getUserByID(id);

        UserInfoVO userInfoVO = new UserInfoVO();

        BeanUtils.copyProperties(user, userInfoVO);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", userInfoVO));
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<APIResponseMap> getUserInfoByID(@PathVariable("id") Long id) {
        User user = userService.getUserByID(id);

        UserInfoVO userInfoVO = new UserInfoVO();

        BeanUtils.copyProperties(user, userInfoVO);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(APIResponseMap.SUCCEEDED("", userInfoVO));
    }
}
