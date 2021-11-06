package todolist.springtodolist.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import todolist.springtodolist.api.entity.Task;
import todolist.springtodolist.api.entity.User;
import todolist.springtodolist.api.exception.TaskNotFoundException;
import todolist.springtodolist.api.exception.UserNotFoundException;
import todolist.springtodolist.api.model.TaskModel;
import todolist.springtodolist.api.repository.TaskRepository;
import todolist.springtodolist.api.repository.UserRepository;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    //Save new task
    public TaskModel saveTask(Task task, Integer userId) throws UserNotFoundException {
        try {
            User userEntity = userRepository.findById(userId).get();
            task.setUser(userEntity);
            task.setCreatedAt(new Date());
            task.setUpdatedAt(new Date());
            return TaskModel.modelOf(taskRepository.save(task));
        }catch (NoSuchElementException e){
            throw new UserNotFoundException(userId);
        }
    }

    //Get one task by id
    public TaskModel getTask(String id) throws TaskNotFoundException {
        return taskRepository.findById(id).map(TaskModel::modelOf).orElseThrow(()-> new TaskNotFoundException(id));
    }

    //Get all tasks by user id
    public Stream<TaskModel> getAll(Integer userId){
        return StreamSupport.stream(taskRepository.findAllByUser_Id(userId).spliterator(), false).map(TaskModel::modelOf);
    }

    //Delete task by id
    public String deleteTask(String id) throws TaskNotFoundException {
        try{
            taskRepository.deleteById(id);
            return id;
        }catch(EmptyResultDataAccessException ex){
            throw new TaskNotFoundException(id);
        }
    }

}
