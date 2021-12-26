package net.dengzixu.maine.web.api.v1;

import net.dengzixu.maine.entity.User;
import net.dengzixu.maine.entity.bo.UserLoginBO;
import net.dengzixu.maine.entity.bo.UserRegisterBO;
import net.dengzixu.maine.entity.bo.UserSMSLoginBO;
import net.dengzixu.maine.entity.bo.UserSendSMSBO;
import net.dengzixu.maine.entity.vo.UserLoginVO;
import net.dengzixu.maine.exception.user.PhoneAlreadyUsedException;
import net.dengzixu.maine.exception.user.SMSCodeErrorException;
import net.dengzixu.maine.exception.user.UserNotFoundException;
import net.dengzixu.maine.model.APIResultMap;
import net.dengzixu.maine.service.CommonService;
import net.dengzixu.maine.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/v1/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final CommonService commonService;

    @Autowired
    public UserController(UserService userService, CommonService commonService) {
        this.userService = userService;
        this.commonService = commonService;
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

        try {
            userService.registerByPhone(userRegisterBO.getName(),
                    userRegisterBO.getPassword(),
                    userRegisterBO.getPhone());
        } catch (PhoneAlreadyUsedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(APIResultMap.ERROR(-1, e.getMessage()));
        }


        return ResponseEntity.ok(APIResultMap.SUCCESS(""));
    }

    @PostMapping("/login/password")
    public ResponseEntity<Map<String, Object>> loginByPhoneAndPassword(@Validated @RequestBody UserLoginBO userLoginBO,
                                                                       BindingResult bindingResult) {
        // 数据校验
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(APIResultMap.ERROR(-1, bindingResult.getFieldError().getDefaultMessage()));
        }

        try {
            User loginUser = userService.loginByPhoneAndPassword(userLoginBO.getPhone(), userLoginBO.getPassword());

            // BeanCopy
            UserLoginVO userLoginVO = new UserLoginVO();
            BeanUtils.copyProperties(loginUser, userLoginVO);

            return ResponseEntity.ok(APIResultMap.SUCCESS("success", userLoginVO));
        } catch (UserNotFoundException e) {
            logger.error("[{}] 登录失败，原因: {}", userLoginBO.getPhone(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIResultMap.ERROR(-1, e.getMessage()));
        }
    }

    @PostMapping("login/sms")
    public ResponseEntity<Map<String, Object>> loginBySMSCode(@Validated @RequestBody UserSMSLoginBO userSMSLoginBO,
                                                              BindingResult bindingResult) {
        // 数据校验
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(APIResultMap.ERROR(-1, bindingResult.getFieldError().getDefaultMessage()));
        }

        try {
            User user = userService.loginBySMSCode(userSMSLoginBO.getPhone(), userSMSLoginBO.getCode());

            // BeanCopy
            UserLoginVO userLoginVO = new UserLoginVO();
            BeanUtils.copyProperties(user, userLoginVO);

            return ResponseEntity.ok(APIResultMap.SUCCESS("", userLoginVO));
        } catch (SMSCodeErrorException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIResultMap.ERROR(-1, e.getMessage()));
        }
    }

    @PostMapping("login/sms/send-sms")
    public ResponseEntity<Map<String, Object>> sendLoginSMSCode(@Validated @RequestBody UserSendSMSBO userSendSMSBO,
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
