package todolist.springtodolist.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import todolist.springtodolist.api.dto.StringResponseDTO;
import todolist.springtodolist.api.dto.TaskDTO;
import todolist.springtodolist.api.dto.UserResponseDTO;
import todolist.springtodolist.api.exception.TaskNotFoundException;
import todolist.springtodolist.api.exception.UserNotFoundException;
import todolist.springtodolist.api.service.TaskService;
import todolist.springtodolist.api.service.UserService;

import java.util.List;


@RestController
@RequestMapping(value = "/api/admin/")
@Api(tags = "Admin part", description = "Operations for test, statistics and etc. You must be ADMIN to use this part.")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;


    //Get list of all users
    @ApiOperation(value = "All users", notes = "Get list of all users")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> allUsers(@RequestHeader("Authorization") String bearer){
        return ResponseEntity.ok(UserResponseDTO.ofList(userService.getAll()));
    }


    //Get full info about any user
    @ApiOperation(value = "Get user", notes = "Get information about any user by his id.")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@RequestHeader("Authorization") String bearer, @ApiParam(name = "User Id") @PathVariable Long id) throws UserNotFoundException {
       return ResponseEntity.ok(UserResponseDTO.of(userService.getUserById(id)));
    }

    //Get any task by id
    @ApiOperation(value = "Get user's task", notes = "Get all user's task by his id.")
    @GetMapping("users/{user_id}/tasks")
    public ResponseEntity<List<TaskDTO>> getUsersTask(@RequestHeader("Authorization") String bearer,
                                               @PathVariable("user_id") @ApiParam(name = "User id") Long user_id) {
        return ResponseEntity.ok(TaskDTO.ofList(taskService.getAll(user_id)));
    }

    //Get any task by id
    @ApiOperation(value = "Get task", notes = "Get any task by it's id.")
    @GetMapping("/tasks/{task_id}")
    public ResponseEntity<TaskDTO> getTaskById(@RequestHeader("Authorization") String bearer,
                                               @PathVariable("task_id") @ApiParam(name = "Task id") String task_id) throws TaskNotFoundException {
        return ResponseEntity.ok(TaskDTO.of(taskService.getTask(task_id)));
    }

    //Delete any user
    @ApiOperation(value = "Ban user", notes = "Mark user as deleted")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<StringResponseDTO> banUser(@RequestHeader("Authorization") String bearer,
                                          @ApiParam(name = "User id") @PathVariable("id") Long id) throws UserNotFoundException {
        userService.markUserDel(id);
        return ResponseEntity.ok(new StringResponseDTO("User id:"+ id +" was deleted", HttpStatus.OK));
    }

    @PutMapping("/users/{id}")
    @ApiOperation(value = "Activate user", notes = "Mark user as active. Parameter activate must be true")
    public ResponseEntity<StringResponseDTO> activateUser(@RequestHeader("Authorization") String bearer,
                                               @PathVariable("id") @ApiParam(name = "User id") Long id,
                                               @RequestParam("activate") @ApiParam(name = "Activate", required = true) String activate){
        userService.activateUser(id);
        return ResponseEntity.ok(new StringResponseDTO("User id:"+ id +" was activated", HttpStatus.OK));
    }

}
