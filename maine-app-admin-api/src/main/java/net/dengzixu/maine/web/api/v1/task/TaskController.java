package net.dengzixu.maine.web.api.v1.task;

import net.dengzixu.constant.enums.TaskStatus;
import net.dengzixu.maine.entity.Task;
import net.dengzixu.maine.entity.vo.task.TaskListVO;
import net.dengzixu.maine.entity.vo.task.TaskVO;
import net.dengzixu.maine.exception.common.TokenExpiredException;
import net.dengzixu.maine.model.APIResponseMap;
import net.dengzixu.maine.service.AdminService;
import net.dengzixu.maine.service.TaskService;
import net.dengzixu.maine.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskController {
    private final TaskService taskService;
    private final AdminService adminService;
    private final JWTUtils jwtUtils;

    @Autowired
    public TaskController(TaskService taskService, AdminService adminService, JWTUtils jwtUtils) {
        this.taskService = taskService;
        this.adminService = adminService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/list")
    public ResponseEntity<APIResponseMap> listTasks(@RequestHeader("Authorization") String authorization) {
        long id = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        adminService.validate(id);

        List<Task> taskList = taskService.getAllTask();

        TaskListVO taskListVO = new TaskListVO(new LinkedList<>());
        taskList.stream()
                .filter(i -> !i.getStatus().equals(TaskStatus.DELETED.value()))
                .forEach(item -> {
                    taskListVO.taskVOList().add(new TaskVO(item.getId(),
                            item.getTitle(),
                            item.getDescription(),
                            item.getUserId(),
                            item.getStatus()));
                });

        return ResponseEntity.ok(APIResponseMap.SUCCEEDED("", taskListVO));
    }

    @RequestMapping(value = "/{taskID}/{operate}", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<APIResponseMap> modifyStatus(@RequestHeader("Authorization") String authorization,
                                                       @PathVariable Long taskID,
                                                       @PathVariable String operate) {

        long id = jwtUtils.decode(authorization).orElseThrow(TokenExpiredException::new);
        adminService.validate(id);

        switch (operate) {
            case "ban" -> taskService.modifyTaskStatus(taskID, TaskStatus.BANNED.value());
            case "delete" -> taskService.modifyTaskStatus(taskID, TaskStatus.DELETED.value());
            case "unban", "pass" -> taskService.modifyTaskStatus(taskID, TaskStatus.DEFAULT.value());
            default -> {
                return ResponseEntity.status(400).body(APIResponseMap.FAILED(-20, "未知操作"));
            }
        }


        return ResponseEntity.ok(APIResponseMap.SUCCEEDED(""));
    }
}
