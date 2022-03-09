package net.dengzixu.maine.web.api.v1.attendance;

import net.dengzixu.maine.entity.User;
import net.dengzixu.maine.entity.vo.UserInfoVO;
import net.dengzixu.maine.exception.common.TokenExpiredException;
import net.dengzixu.maine.model.APIResponseMap;
import net.dengzixu.maine.service.AttendanceService;
import net.dengzixu.maine.service.UserService;
import net.dengzixu.maine.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

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

    @PostMapping("/{taskID}/delete")
    public ResponseEntity<APIResponseMap> deleteTask(@RequestHeader("Authorization") String authorization,
                                                     @PathVariable() Long taskID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);

        attendanceService.modifyTaskStatus(taskID, userID, 2);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("成功"));
    }

    @GetMapping("/{taskID}/takers")
    public ResponseEntity<APIResponseMap> getTaskTaker(@RequestHeader("Authorization") String authorization,
                                                       @PathVariable() Long taskID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);


        List<User> userList = attendanceService.getTakerListByTaskID(taskID);

        // BeanCopy
        List<UserInfoVO> userInfoVOList = new LinkedList<>();

        userList.forEach(item -> {
            UserInfoVO userInfoVO = new UserInfoVO();

            BeanUtils.copyProperties(item, userInfoVO);
            userInfoVOList.add(userInfoVO);
        });


        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", userInfoVOList));
    }
}
