package todolist.springtodolist.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import todolist.springtodolist.api.entity.User;
import todolist.springtodolist.api.exception.UserNotFoundException;
import todolist.springtodolist.api.service.UserService;


/*@RestController
@RequestMapping(value = "/api/v1")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public Iterable<User> allUsers(){
        return userService.getAll();
    }


    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Integer id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") Integer id) throws UserNotFoundException {
        userService.deleteUser(id);
        return "User "+ id+" successfully deleted";

    }



}*/
