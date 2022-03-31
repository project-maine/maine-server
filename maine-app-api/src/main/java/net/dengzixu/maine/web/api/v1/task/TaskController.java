package net.dengzixu.maine.web.api.v1.task;

import net.dengzixu.constant.enums.TaskStatus;
import net.dengzixu.maine.entity.Task;
import net.dengzixu.maine.entity.bo.task.AttendanceCreateBasicBO;
import net.dengzixu.maine.entity.bo.task.GenerateTokenBO;
import net.dengzixu.maine.entity.bo.task.TakeByCodeBO;
import net.dengzixu.maine.entity.dto.TakeRecordDTO;
import net.dengzixu.maine.entity.dto.TaskInfoDTO;
import net.dengzixu.maine.entity.vo.task.*;
import net.dengzixu.maine.exception.common.TokenExpiredException;
import net.dengzixu.maine.model.APIResponseMap;
import net.dengzixu.maine.service.TaskService;
import net.dengzixu.maine.service.UserService;
import net.dengzixu.maine.utils.JWTUtils;
import net.dengzixu.maine.utils.SignUtils;
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
@RequestMapping("/v1/task")
public class TaskController {
    public static final String secretKey = "gor29QnNoDcrNH2AnuQkZNGQnDFBZSCc";

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

    @PostMapping("/{taskID}/take")
    public ResponseEntity<APIResponseMap> takeByWeb(@RequestHeader("Authorization") String authorization,
                                                    @PathVariable(name = "taskID") Long taskID,
                                                    @RequestParam(name = "token") String token) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);


        taskService.take(taskID, userID, token);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED(""));
    }

    @RequestMapping(value = "/token/generate", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<APIResponseMap> generateToken(@RequestHeader("Authorization") String authorization,
                                                        @RequestParam(name = "sign") String sign,
                                                        @Validated @RequestBody GenerateTokenBO generateTokenBO,
                                                        BindingResult bindingResult) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);

        // 数据校验
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(APIResponseMap.FAILED(-10, "参数错误"));
        }

        // 教研签名
        String calculatedSign = SignUtils.sign(generateTokenBO.toTreeMap(), secretKey);

        if (!calculatedSign.equals(sign)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(APIResponseMap.FAILED(-10, "签名错误"));
        }

        String token = taskService.generateToken(generateTokenBO.toTokenDTO());

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", null, token));
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

    @PostMapping("/code/info")
    public ResponseEntity<APIResponseMap> codeInfo(@Validated @RequestBody TakeByCodeBO takeByCodeBO,
                                                   BindingResult bindingResult) {
        // 数据校验
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(APIResponseMap.FAILED(-1, bindingResult.getFieldError()));
        }

        Task task = taskService.getTaskByTakeCode(takeByCodeBO.code());

        TaskVO taskVO = new TaskVO(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getUserId(),
                task.getStatus());

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", taskVO));
    }

    @GetMapping("/history")
    public ResponseEntity<APIResponseMap> history(@RequestHeader("Authorization") String authorization) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        userService.validate(userID);

        List<TakeRecordDTO> takeRecordDTOList = taskService.getTakeRecord(userID);

        HistoryListVO historyListVO = new HistoryListVO(new LinkedList<>());

        takeRecordDTOList.stream()
                .filter(i -> !TaskStatus.BANNED.value().equals(i.getTaskStatus()))
                .filter(i -> !TaskStatus.DELETED.value().equals(i.getTaskStatus()))
                .forEach(item -> {
                    historyListVO.historyVOList().add(new HistoryVO(item.getSerialID(),
                            item.getTaskID(),
                            item.getRecordStatus(),
                            item.getRecordCreateTime(),
                            item.getTaskTitle(),
                            item.getTaskDescription(),
                            item.getTaskStatus()));
                });
        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", historyListVO));
    }
}
