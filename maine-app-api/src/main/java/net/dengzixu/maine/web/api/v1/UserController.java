package net.dengzixu.maine.web.api.v1;

import net.dengzixu.maine.model.APIResultMap;
import net.dengzixu.maine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login() {
        return ResponseEntity.ok(APIResultMap.SUCCESS("success", userService.login()));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
