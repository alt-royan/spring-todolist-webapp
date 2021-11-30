package todolist.springtodolist.api.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todolist.springtodolist.api.dto.StringResponseDTO;
import todolist.springtodolist.api.dto.TaskUpdateRequestDTO;
import todolist.springtodolist.api.dto.TaskDTO;
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
@RequestMapping("/api")
@Api(tags = "Tasks", description = "Task operations")
public class UserController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @ApiOperation(value = "Get task list", notes = "You can get both all tasks and filter them by completion and also get deleted tasks")
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> allTasks(@RequestHeader("Authorization") String bearer,
                                                  @RequestParam(name="completed", required = false) @ApiParam(name = "Completed") Boolean completed,
                                                  @RequestParam(name="deleted", required = false) @ApiParam(name = "Deleted") Boolean deleted){
        User user= getUserFromBearer(bearer);
        if (completed==null) {
            if (deleted!=null && deleted) {
                return ResponseEntity.ok(TaskDTO.ofList(taskService.getAllDeletedTasks(user.getId())));
            }
            return ResponseEntity.ok(TaskDTO.ofList(taskService.getAllActiveTasks(user.getId())));
        }
        return ResponseEntity.ok(TaskDTO.ofList(taskService.getAllByCompletely(user.getId(), completed)));
    }


    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<TaskDTO> getTask(@RequestHeader("Authorization") String bearer,
                                           @PathVariable("taskId") @ApiParam(name = "Task id") String task_id) throws TaskNotFoundException {
        User user= getUserFromBearer(bearer);
        return ResponseEntity.ok(TaskDTO.of(taskService.getActiveTask(task_id, user.getId())));
    }

    @ApiOperation(value = "Create new task", notes = "Title and description are required. Completed is false by default.")
    @PostMapping("/tasks")
    @ApiImplicitParam(
            required = true,
            name = "Task",
            dataTypeClass = TaskUpdateRequestDTO.class,
            paramType = "body"
    )
    public ResponseEntity<TaskDTO> createTask(@RequestHeader("Authorization") String bearer, @RequestBody @Valid @ApiParam(hidden = true, name = "Task") Task task) throws UserNotFoundException {
        User user= getUserFromBearer(bearer);
        return ResponseEntity.ok(TaskDTO.of(taskService.saveNewTask(task, user.getId())));

    }

    @ApiOperation(value = "Delete task", notes = "Delete any of your tasks by it's id.")
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<StringResponseDTO> deleteTask(@RequestHeader("Authorization") String bearer,
                                                        @ApiParam(name = "Task id") @PathVariable("taskId") String taskId) throws TaskNotFoundException {
        User user= getUserFromBearer(bearer);
        return ResponseEntity.ok(new StringResponseDTO("Task id:"+ taskService.markTaskAsDel(taskId, user.getId()) +" was deleted", HttpStatus.OK));
    }

    @ApiOperation(value = "Update task", notes = "You can update title, description, completed or activate deleted task. You can also update something one, parameters are not required.")
    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@RequestHeader("Authorization") String bearer,
                                              @PathVariable("taskId") @ApiParam(name = "Task id") String taskId,
                                              @RequestBody(required = false) @ApiParam(name = "Task", value = "Parameters for updating task") TaskUpdateRequestDTO taskDTO,
                                              @RequestParam(name = "activate", required = false) @ApiParam(name="Activate",value = "Activate deleted task") Boolean activate) throws TaskNotFoundException{
        User user= getUserFromBearer(bearer);
        if(activate!=null && activate)
        {
            taskService.activateTask(taskId, user.getId());
        }
        if(taskDTO!=null) {
           taskService.updateTask(taskDTO, taskId, user.getId());
        }
        return ResponseEntity.ok(TaskDTO.of(taskService.getTask(taskId, user.getId())));
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

