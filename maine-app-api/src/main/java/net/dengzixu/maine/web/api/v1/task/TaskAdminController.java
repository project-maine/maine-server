package net.dengzixu.maine.web.api.v1.task;

import net.dengzixu.maine.entity.Task;
import net.dengzixu.maine.entity.TaskSettingItem;
import net.dengzixu.maine.entity.User;
import net.dengzixu.maine.entity.bo.CreateTaskBO;
import net.dengzixu.maine.entity.vo.TaskVO;
import net.dengzixu.maine.entity.vo.UserInfoVO;
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

        taskList.forEach(item -> {
            // 不显示状态不正常的任务
            if (item.getStatus().equals(0) || item.getStatus().equals(1)) {
                taskVOList.add(new TaskVO(item.getId(), item.getTitle(),
                        item.getDescription(), item.getUserId(), item.getStatus()));
            }
        });

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", taskVOList));
    }

    @PostMapping("/{taskID}/close")
    public ResponseEntity<APIResponseMap> closeTask(@RequestHeader("Authorization") String authorization,
                                                    @PathVariable() Long taskID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);

        taskService.modifyTaskStatus(taskID, userID, 1);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("成功"));
    }

    @PostMapping("/{taskID}/delete")
    public ResponseEntity<APIResponseMap> deleteTask(@RequestHeader("Authorization") String authorization,
                                                     @PathVariable() Long taskID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);

        taskService.modifyTaskStatus(taskID, userID, 2);

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("成功"));
    }

    @GetMapping("/{taskID}/participant/list")
    public ResponseEntity<APIResponseMap> getTaskTaker(@RequestHeader("Authorization") String authorization,
                                                       @PathVariable() Long taskID) {
        long userID = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        userService.validate(userID);


        List<User> userList = taskService.getTakerListByTaskID(taskID);

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
