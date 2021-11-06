package todolist.springtodolist.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import todolist.springtodolist.api.entity.User;
import todolist.springtodolist.api.exception.UserAlreadyExistException;
import todolist.springtodolist.api.exception.UserNotFoundException;
import todolist.springtodolist.api.model.UserModel;
import todolist.springtodolist.api.service.UserService;

import java.util.stream.Stream;


@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public Stream<UserModel> allUsers(){
        return userService.getAll();
    }

    @PostMapping("/users")
    public UserModel register(@RequestBody User user) throws UserAlreadyExistException {
        return userService.saveUser(user);
    }

    @GetMapping("/users/{id}")
    public UserModel getUser(@PathVariable Integer id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") Integer id) throws UserNotFoundException {
        userService.deleteUser(id);
        return "User "+ id+" successfully deleted";

    }



}
