package net.dengzixu.maine.web.api.v1.attendance;

import net.dengzixu.maine.entity.bo.AttendanceCreateBasicBO;
import net.dengzixu.maine.exception.common.TokenExpiredException;
import net.dengzixu.maine.mapper.TaskRecordMapper;
import net.dengzixu.maine.model.APIResponseMap;
import net.dengzixu.maine.service.AttendanceService;
import net.dengzixu.maine.service.UserService;
import net.dengzixu.maine.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/attendance")
public class AttendanceController {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    private final AttendanceService attendanceService;
    private final UserService userService;

    private final JWTUtils jwtUtils;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, UserService userService, JWTUtils jwtUtils) {
        this.attendanceService = attendanceService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/create/basic")
    public ResponseEntity<APIResponseMap> create(@RequestHeader("Authorization") String authorization,
                                                 @Validated @RequestBody AttendanceCreateBasicBO attendanceCreateBasicBO,
                                                 BindingResult bindingResult) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        userService.validate(userID);

        // 数据校验
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(APIResponseMap.FAILED(-1, bindingResult.getFieldError()));
        }

        attendanceService.createBasic(attendanceCreateBasicBO.title(), attendanceCreateBasicBO.description(), userID);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("创建成功"));
    }

    @PostMapping("/take/web/{id}")
    public ResponseEntity<APIResponseMap> webTake(@RequestHeader("Authorization") String authorization,
                                                  @PathVariable(name = "id") Long taskID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        attendanceService.webTake(taskID, userID);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("已完成考勤"));
    }
}
