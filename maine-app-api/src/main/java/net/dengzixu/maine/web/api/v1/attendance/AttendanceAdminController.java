package net.dengzixu.maine.web.api.v1.attendance;

import net.dengzixu.maine.exception.common.TokenExpiredException;
import net.dengzixu.maine.model.APIResponseMap;
import net.dengzixu.maine.service.AttendanceService;
import net.dengzixu.maine.service.UserService;
import net.dengzixu.maine.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/attendance/admin")
public class AttendanceAdminController {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceAdminController.class);

    private final AttendanceService attendanceService;
    private final UserService userService;

    private final JWTUtils jwtUtils;

    @Autowired
    public AttendanceAdminController(AttendanceService attendanceService, UserService userService, JWTUtils jwtUtils) {
        this.attendanceService = attendanceService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/{taskID}/close")
    public ResponseEntity<APIResponseMap> closeTask(@RequestHeader("Authorization") String authorization,
                                                    @PathVariable() Long taskID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);

        attendanceService.modifyTaskStatus(taskID, userID, 1);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("成功"));
    }
}
