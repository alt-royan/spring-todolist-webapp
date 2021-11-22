package todolist.springtodolist.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todolist.springtodolist.api.dto.TaskResponseDTO;
import todolist.springtodolist.api.dto.UserResponseDTO;
import todolist.springtodolist.api.exception.TaskNotFoundException;
import todolist.springtodolist.api.exception.UserNotFoundException;
import todolist.springtodolist.api.service.TaskService;
import todolist.springtodolist.api.service.UserService;

import java.util.List;


@RestController
@RequestMapping(value = "/admin/")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;


    //Get list of all users
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> allUsers(){
        return ResponseEntity.ok(UserResponseDTO.ofList(userService.getAll()));
    }


    //Get full info about any user
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) throws UserNotFoundException {
       return ResponseEntity.ok(UserResponseDTO.of(userService.getUserById(id)));
    }

    //Get any user taskList
    @GetMapping("/users/{id}/tasks")
    public ResponseEntity<List<TaskResponseDTO>> getUsersTasks(@PathVariable Long id) throws UserNotFoundException {
        return ResponseEntity.ok(TaskResponseDTO.ofList(taskService.getAll(id)));
    }


    //Get any task by id
    @GetMapping("/tasks/{task_id}")
    public ResponseEntity<TaskResponseDTO> getUsersTasks(@PathVariable("task_id") String task_id) throws TaskNotFoundException {
        return ResponseEntity.ok(TaskResponseDTO.of(taskService.getTask(task_id)));
    }

    //Delete any user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) throws UserNotFoundException {
        userService.markUserDel(id);
        return ResponseEntity.ok("User id:"+ id +" was deleted");
    }

}
