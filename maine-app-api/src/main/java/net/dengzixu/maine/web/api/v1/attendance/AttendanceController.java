package net.dengzixu.maine.web.api.v1.attendance;

import net.dengzixu.maine.entity.Task;
import net.dengzixu.maine.entity.bo.AttendanceCreateBasicBO;
import net.dengzixu.maine.entity.bo.TakeByCodeBO;
import net.dengzixu.maine.entity.vo.GenerateCodeVO;
import net.dengzixu.maine.entity.vo.TaskVO;
import net.dengzixu.maine.exception.common.TokenExpiredException;
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

import java.util.LinkedList;
import java.util.List;

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

        attendanceService.createTaskBasic(attendanceCreateBasicBO.title(), attendanceCreateBasicBO.description(), userID);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("创建成功"));
    }

    @GetMapping({"/{taskId}/info", "/{taskId}/info/basic"})
    public ResponseEntity<APIResponseMap> info(@PathVariable() Long taskId) {
        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("success", attendanceService.getTaskBasicInfo(taskId)));
    }

    @PostMapping("/{taskID}/take/web")
    public ResponseEntity<APIResponseMap> takeByWeb(@RequestHeader("Authorization") String authorization,
                                                    @PathVariable() Long taskID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);


        attendanceService.webTake(taskID, userID);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("已完成考勤"));
    }

    @PostMapping("/{taskID}/take/code/generate")
    public ResponseEntity<APIResponseMap> generateTakeCode(@RequestHeader("Authorization") String authorization,
                                                           @PathVariable() Long taskID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);

        String generatedCode = attendanceService.generateCode(taskID, userID, 3600);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", new GenerateCodeVO(generatedCode)));
    }

    @PostMapping("/take/code")
    public ResponseEntity<APIResponseMap> takeByCode(@RequestHeader("Authorization") String authorization,
                                                     @Validated @RequestBody TakeByCodeBO takeByCodeBO,
                                                     BindingResult bindingResult) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        userService.validate(userID);

        // 数据校验
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(APIResponseMap.FAILED(-1, bindingResult.getFieldError()));
        }
        attendanceService.codeTake(takeByCodeBO.code(), userID);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("已完成考勤"));
    }

    @GetMapping("/task/list")
    public ResponseEntity<APIResponseMap> taskList(@RequestHeader("Authorization") String authorization) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        userService.validate(userID);


        List<Task> taskList = attendanceService.getTaskListByUserID(userID);

        List<TaskVO> taskVOList = new LinkedList<>();

        taskList.forEach(item -> {
            // 不显示状态不正常的任务
            if (item.getStatus().equals(0) || item.getStatus().equals(1)) {
                taskVOList.add(new TaskVO(item.getId(), item.getTitle(),
                        item.getDescription(), item.getUserId(), item.getStatus()));
            }
        });

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", taskVOList));
    }
}
