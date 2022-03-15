package net.dengzixu.maine.web.api.v1.task;

import net.dengzixu.maine.entity.bo.AttendanceCreateBasicBO;
import net.dengzixu.maine.entity.bo.TakeByCodeBO;
import net.dengzixu.maine.entity.dto.TaskInfoDTO;
import net.dengzixu.maine.entity.vo.GenerateCodeVO;
import net.dengzixu.maine.entity.vo.TaskInfoVO;
import net.dengzixu.maine.entity.vo.TaskSettingVO;
import net.dengzixu.maine.exception.common.TokenExpiredException;
import net.dengzixu.maine.model.APIResponseMap;
import net.dengzixu.maine.service.TaskService;
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
@RequestMapping("/v1/task")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;
    private final UserService userService;

    private final JWTUtils jwtUtils;

    @Autowired
    public TaskController(TaskService taskService, UserService userService, JWTUtils jwtUtils) {
        this.taskService = taskService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/create/basic")
    @Deprecated
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

        taskService.createTaskBasic(attendanceCreateBasicBO.title(), attendanceCreateBasicBO.description(), userID);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("创建成功"));
    }

    @GetMapping({"/{taskId}/info/basic"})
    @Deprecated
    public ResponseEntity<APIResponseMap> info(@PathVariable() Long taskId) {
        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("success", taskService.getTaskBasicInfo(taskId)));
    }

    @GetMapping("/{taskId}/info")
    public ResponseEntity<APIResponseMap> infoAdvance(@PathVariable() Long taskId) {
        TaskInfoDTO taskInfoDTO = taskService.getTaskInfo(taskId);

        TaskInfoVO taskInfoVO = new TaskInfoVO(taskInfoDTO.getId(), taskInfoDTO.getTitle(), taskInfoDTO.getDescription(),
                taskInfoDTO.getUserId(), taskInfoDTO.getStatus(), taskInfoDTO.getEndTime(), taskInfoDTO.getCreateTime(),
                new TaskSettingVO(taskInfoDTO.getTaskSettingItem().getAllowGroups()));


        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", taskInfoVO));
    }

    @PostMapping("/{taskID}/take/web")
    public ResponseEntity<APIResponseMap> takeByWeb(@RequestHeader("Authorization") String authorization,
                                                    @PathVariable() Long taskID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);


        taskService.webTake(taskID, userID);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("已完成考勤"));
    }

    @PostMapping("/{taskID}/take/code/generate")
    public ResponseEntity<APIResponseMap> generateTakeCode(@RequestHeader("Authorization") String authorization,
                                                           @PathVariable() Long taskID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);

        String generatedCode = taskService.generateCode(taskID, userID, 3600);

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
        taskService.codeTake(takeByCodeBO.code(), userID);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("已完成考勤"));
    }
}
