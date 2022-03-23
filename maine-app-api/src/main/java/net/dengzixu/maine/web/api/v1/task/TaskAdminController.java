package net.dengzixu.maine.web.api.v1.task;

import net.dengzixu.maine.constant.enums.TaskStatus;
import net.dengzixu.maine.entity.Task;
import net.dengzixu.maine.entity.TaskSettingItem;
import net.dengzixu.maine.entity.bo.CreateTaskBO;
import net.dengzixu.maine.entity.dto.ParticipantDTO;
import net.dengzixu.maine.entity.vo.*;
import net.dengzixu.maine.exception.common.TokenExpiredException;
import net.dengzixu.maine.model.APIResponseMap;
import net.dengzixu.maine.service.TaskService;
import net.dengzixu.maine.service.UserService;
import net.dengzixu.maine.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/v1/task/admin")
public class TaskAdminController {
    private static final Logger logger = LoggerFactory.getLogger(TaskAdminController.class);

    private final TaskService taskService;
    private final UserService userService;

    private final JWTUtils jwtUtils;

    @Autowired
    public TaskAdminController(TaskService taskService, UserService userService, JWTUtils jwtUtils) {
        this.taskService = taskService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/task/create")
    public ResponseEntity<APIResponseMap> createTask(@RequestHeader("Authorization") String authorization,
                                                     @Validated @RequestBody CreateTaskBO createTaskBO,
                                                     BindingResult bindingResult) {
        final long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(APIResponseMap.FAILED(-1, bindingResult.getFieldError()));
        }

        TaskSettingItem taskSettingItem = new TaskSettingItem();
        taskSettingItem.setAllowGroups(createTaskBO.taskSettingBO().allowGroupList());

        taskService.createTask(createTaskBO.title(), createTaskBO.description(), createTaskBO.endTime(),
                userID, taskSettingItem);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED(""));
    }

    @GetMapping("/task/list")
    public ResponseEntity<APIResponseMap> taskList(@RequestHeader("Authorization") String authorization) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);

        userService.validate(userID);


        List<Task> taskList = taskService.getTaskListByUserID(userID);

        List<TaskVO> taskVOList = new LinkedList<>();

        taskList.stream()
                .filter(task -> !task.getStatus().equals(TaskStatus.BANNED.value()))
                .filter(task -> !task.getStatus().equals(TaskStatus.DELETED.value()))
                .forEach(item -> taskVOList.add(new TaskVO(item.getId(), item.getTitle(),
                        item.getDescription(), item.getUserId(), item.getStatus())));

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", taskVOList));
    }

    @PostMapping("/{taskID}/code/generate")
    public ResponseEntity<APIResponseMap> generateTakeCode(@RequestHeader("Authorization") String authorization,
                                                           @PathVariable() Long taskID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);

        String generatedCode = taskService.generateCode(taskID, userID, 3600);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", new GenerateCodeVO(generatedCode)));
    }

    @PostMapping("/{taskID}/close")
    public ResponseEntity<APIResponseMap> closeTask(@RequestHeader("Authorization") String authorization,
                                                    @PathVariable() Long taskID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);

        taskService.modifyTaskStatus(taskID, userID, TaskStatus.CLOSED.value());

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("成功"));
    }

    @RequestMapping(value = "/{taskID}/delete", method = {RequestMethod.DELETE, RequestMethod.POST})
    public ResponseEntity<APIResponseMap> deleteTask(@RequestHeader("Authorization") String authorization,
                                                     @PathVariable() Long taskID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);

        taskService.modifyTaskStatus(taskID, userID, TaskStatus.DELETED.value());

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("成功"));
    }

    @GetMapping("/{taskID}/participant/list")
    public ResponseEntity<APIResponseMap> getParticipantList(@RequestHeader("Authorization") String authorization,
                                                       @PathVariable() Long taskID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);


        List<ParticipantDTO> participantList = taskService.getParticipantList(taskID);

        ParticipantListVO participantListVO = new ParticipantListVO(new LinkedList<>());

        // BeanCopy
        participantList.forEach(item -> participantListVO.participantVOList().add(new ParticipantVO(item.userID(),
                item.userName(),
                item.takeTime())));

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", participantListVO));
    }
}
