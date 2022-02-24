package net.dengzixu.maine.web.api.v1.auth;

import net.dengzixu.maine.entity.User;
import net.dengzixu.maine.entity.bo.UserLoginBO;
import net.dengzixu.maine.entity.bo.UserRegisterBO;
import net.dengzixu.maine.entity.bo.UserSMSLoginBO;
import net.dengzixu.maine.entity.bo.UserSendSMSBO;
import net.dengzixu.maine.model.APIResultMap;
import net.dengzixu.maine.service.CommonService;
import net.dengzixu.maine.service.UserService;
import net.dengzixu.maine.utils.JWTUtils;
import net.dengzixu.maine.web.api.v1.user.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/passport/v1")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final CommonService commonService;

    private final JWTUtils jwtUtils;

    @Autowired
    public AuthController(UserService userService, CommonService commonService, JWTUtils jwtUtils) {
        this.userService = userService;
        this.commonService = commonService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register/phone")
    public ResponseEntity<Map<String, Object>> registerByPhone(@Validated @RequestBody UserRegisterBO userRegisterBO,
                                                               BindingResult bindingResult) {
        // 数据校验
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(APIResultMap.ERROR(-1, bindingResult.getFieldError().getDefaultMessage()));
        }

        userService.registerByPhone(userRegisterBO.getName(),
                userRegisterBO.getPassword(),
                userRegisterBO.getPhone());

        return ResponseEntity.ok(APIResultMap.SUCCESS(""));
    }

    @PostMapping("/auth/password")
    public ResponseEntity<Map<String, Object>> authByPassword(@Validated @RequestBody UserLoginBO userLoginBO,
                                                              BindingResult bindingResult) {
        // 数据校验
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(APIResultMap.ERROR(-1, bindingResult.getFieldError().getDefaultMessage()));
        }

        User loginUser = userService.loginByPhoneAndPassword(userLoginBO.getPhone(), userLoginBO.getPassword());

        return ResponseEntity.ok(APIResultMap.SUCCESS("", jwtUtils.encode(loginUser.getId())));
    }

    @PostMapping("/auth/sms")
    public ResponseEntity<Map<String, Object>> authBySMSCode(@Validated @RequestBody UserSMSLoginBO userSMSLoginBO,
                                                             BindingResult bindingResult) {
        // 数据校验
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(APIResultMap.ERROR(-1, bindingResult.getFieldError().getDefaultMessage()));
        }

        User user = userService.loginBySMSCode(userSMSLoginBO.getPhone(), userSMSLoginBO.getCode());

        return ResponseEntity.ok(APIResultMap.SUCCESS("", jwtUtils.encode(user.getId())));
    }

    @PostMapping("/auth/sms/send-sms")
    public ResponseEntity<Map<String, Object>> sendSMSCode(@Validated @RequestBody UserSendSMSBO userSendSMSBO,
                                                           BindingResult bindingResult) {
        // 数据校验
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(APIResultMap.ERROR(-1, bindingResult.getFieldError().getDefaultMessage()));
        }

        commonService.sendSMS(userSendSMSBO.getPhone());

        return ResponseEntity.ok(APIResultMap.SUCCESS(""));
    }
}
