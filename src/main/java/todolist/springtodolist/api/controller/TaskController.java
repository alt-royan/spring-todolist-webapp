package todolist.springtodolist.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import todolist.springtodolist.api.entity.Task;
import todolist.springtodolist.api.exception.TaskNotFoundException;
import todolist.springtodolist.api.exception.UserNotFoundException;
import todolist.springtodolist.api.model.TaskModel;
import todolist.springtodolist.api.service.TaskService;

import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/users/{userId}/tasks")
    public Stream<TaskModel> allTasks(@PathVariable("userId") Integer userId){
        return taskService.getAll(userId);
    }

    @GetMapping("/users/*/tasks/{taskId}")
    public TaskModel allTasks(@PathVariable("taskId") String taskId) throws TaskNotFoundException {
        return taskService.getTask(taskId);
    }

    @PostMapping("/users/{userId}/tasks")
    public TaskModel createTask(@PathVariable("userId") Integer userId, @RequestBody Task task) throws UserNotFoundException {
        return taskService.saveTask(task, userId);
    }

    @DeleteMapping("/users/*/tasks/{taskId}")
    public String deleteTask(@PathVariable("taskId") String taskId) throws TaskNotFoundException {
        return taskService.deleteTask(taskId);
    }
}
