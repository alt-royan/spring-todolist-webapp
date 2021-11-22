package todolist.springtodolist.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todolist.springtodolist.api.dto.TaskUpdateRequestDTO;
import todolist.springtodolist.api.dto.TaskResponseDTO;
import todolist.springtodolist.api.entity.Task;
import todolist.springtodolist.api.entity.User;
import todolist.springtodolist.api.exception.TaskNotFoundException;
import todolist.springtodolist.api.exception.UserNotFoundException;
import todolist.springtodolist.api.security.JwtProvider;
import todolist.springtodolist.api.service.TaskService;
import todolist.springtodolist.api.service.UserService;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping()
public class UserController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponseDTO>> allTasks(@RequestHeader("Authorization") String bearer,
                                                         @RequestParam(name="completed", required = false) Boolean completed,
                                                         @RequestParam(name="deleted", required = false) Boolean deleted){
        User user= getUserFromBearer(bearer);
        if (completed==null) {
            if (deleted!=null && deleted) {
                return ResponseEntity.ok(TaskResponseDTO.ofList(taskService.getAllDeletedTasks(user.getId())));
            }
            return ResponseEntity.ok(TaskResponseDTO.ofList(taskService.getAllActiveTasks(user.getId())));
        }
        return ResponseEntity.ok(TaskResponseDTO.ofList(taskService.getAllByCompletely(user.getId(), completed)));
    }

    @GetMapping("/tasks/{task_id}")
    public ResponseEntity<TaskResponseDTO> getTask(@RequestHeader("Authorization") String bearer, @PathVariable("task_id") String task_id) throws TaskNotFoundException {
        User user= getUserFromBearer(bearer);
        return ResponseEntity.ok(TaskResponseDTO.of(taskService.getActiveTask(task_id, user.getId())));
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponseDTO> createTask(@RequestHeader("Authorization") String bearer, @RequestBody @Valid Task task) throws UserNotFoundException {
        User user= getUserFromBearer(bearer);
        return ResponseEntity.ok(TaskResponseDTO.of(taskService.saveNewTask(task, user.getId())));

    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(@RequestHeader("Authorization") String bearer, @PathVariable("taskId") String taskId) throws TaskNotFoundException {
        User user= getUserFromBearer(bearer);
        return ResponseEntity.ok("Task id:"+ taskService.markTaskAsDel(taskId, user.getId()) +" was deleted");
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponseDTO> updateTask(@RequestHeader("Authorization") String bearer,
                                      @PathVariable("taskId") String taskId,
                                      @RequestBody(required = false) TaskUpdateRequestDTO taskDTO,
                                      @RequestParam(name = "activate", required = false) Boolean activate) throws TaskNotFoundException{
        User user= getUserFromBearer(bearer);
        if(activate!=null && activate)
        {
            taskService.activateTask(taskId, user.getId());
        }
        if(taskDTO!=null) {
           taskService.updateTask(taskDTO, taskId, user.getId());
        }
        return ResponseEntity.ok(TaskResponseDTO.of(taskService.getTask(taskId, user.getId())));
    }

    private String getTokenFromHeader(String bearer){
        if (bearer!=null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    private User getUserFromBearer(String bearer){
        return userService.getUserById(jwtProvider.getUserIdFromToken(getTokenFromHeader(bearer)));
    }
}

