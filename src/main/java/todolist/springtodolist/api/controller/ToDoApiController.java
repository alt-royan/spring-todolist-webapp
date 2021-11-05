package todolist.springtodolist.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todolist.springtodolist.api.entity.User;
import todolist.springtodolist.api.exception.UserAlreadyExistException;
import todolist.springtodolist.api.model.UserModel;
import todolist.springtodolist.api.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ToDoApiController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserModel> allUsers(){
        return null;
    }

    @PostMapping("/users")
    public ResponseEntity register(@RequestBody User user){
        try {
            return ResponseEntity.ok(userService.registerUser(user));
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
