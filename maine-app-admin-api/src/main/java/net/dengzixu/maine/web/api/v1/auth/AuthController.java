package net.dengzixu.maine.web.api.v1.auth;

import net.dengzixu.maine.entity.Admin;
import net.dengzixu.maine.entity.bo.AdminLoginBO;
import net.dengzixu.maine.model.APIResponseMap;
import net.dengzixu.maine.service.AdminService;
import net.dengzixu.maine.utils.JWTUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final AdminService adminService;
    private final JWTUtils jwtUtils;


    public AuthController(AdminService adminService, JWTUtils jwtUtils) {
        this.adminService = adminService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponseMap> login(@RequestBody AdminLoginBO adminLoginBO,
                                                BindingResult bindingResult) {
        // 数据校验
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(APIResponseMap.FAILED(-1, bindingResult.getFieldError()));
        }


        Admin admin = adminService.login(adminLoginBO.name(), adminLoginBO.password());

        String token = jwtUtils.encode(admin.getId());

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", token));
    }
}
