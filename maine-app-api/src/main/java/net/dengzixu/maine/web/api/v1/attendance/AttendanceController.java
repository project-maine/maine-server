package net.dengzixu.maine.web.api.v1.attendance;

import net.dengzixu.maine.entity.bo.AttendanceCreateBO;
import net.dengzixu.maine.exception.common.TokenExpiredException;
import net.dengzixu.maine.model.APIResultMap;
import net.dengzixu.maine.service.AttendanceService;
import net.dengzixu.maine.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/attendance")
public class AttendanceController {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    private final AttendanceService attendanceService;

    private final JWTUtils jwtUtils;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, JWTUtils jwtUtils) {
        this.attendanceService = attendanceService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestHeader("Authorization") String authorization,
                                                      @Validated @RequestBody AttendanceCreateBO attendanceCreateBO,
                                                      BindingResult bindingResult) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        // 数据校验
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(APIResultMap.ERROR(-1, bindingResult.getFieldError().getDefaultMessage()));
        }

        attendanceService.createBasic(attendanceCreateBO.title(), attendanceCreateBO.description(), userID);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(APIResultMap.SUCCESS("创建成功"));
    }
}
