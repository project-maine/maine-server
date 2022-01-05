package net.dengzixu.maine.web.api.v1.user;

import net.dengzixu.maine.entity.User;
import net.dengzixu.maine.entity.vo.UserInfoVO;
import net.dengzixu.maine.model.APIResultMap;
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

import java.util.Map;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final CommonService commonService;

    private final JWTUtils jwtUtils;

    @Autowired
    public UserController(UserService userService, CommonService commonService, JWTUtils jwtUtils) {
        this.userService = userService;
        this.commonService = commonService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getUserInfo(@RequestHeader("Authorization") String authorization) {
        long id = jwtUtils.decode(authorization);

        if (-1 == id) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(APIResultMap.ERROR(-1, "用户身份过期,请重新登录"));
        }

        User user = userService.getUserByID(id);

        UserInfoVO userInfoVO = new UserInfoVO();

        BeanUtils.copyProperties(user, userInfoVO);


        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(APIResultMap.SUCCESS("", userInfoVO));
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<Map<String, Object>> getUserInfoByID(@PathVariable("id") Long id) {
        User user = userService.getUserByID(id);

        UserInfoVO userInfoVO = new UserInfoVO();

        BeanUtils.copyProperties(user, userInfoVO);


        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(APIResultMap.SUCCESS("", userInfoVO));
    }
}
