package todolist.springtodolist.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import todolist.springtodolist.api.dto.TaskUpdateRequestDTO;
import todolist.springtodolist.api.entity.Status;
import todolist.springtodolist.api.entity.Task;
import todolist.springtodolist.api.entity.User;
import todolist.springtodolist.api.exception.TaskNotFoundException;
import todolist.springtodolist.api.exception.UserNotFoundException;
import todolist.springtodolist.api.repository.TaskRepository;
import todolist.springtodolist.api.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;


    /**Admin section**/

    //Get all tasks by user id
    public List<Task> getAll(Long userId){
        return taskRepository.findAllByUser_Id(userId);
    }

    //Actually delete task
    public String deleteTask(String id, Long user_id) throws TaskNotFoundException {
        try{
            taskRepository.deleteByIdAndUser_Id(id, user_id);
            return id;
        }catch(EmptyResultDataAccessException ex){
            throw new TaskNotFoundException(id);
        }
    }

    //Get task only by its id
    public Task getTask(String id) throws TaskNotFoundException {
        return taskRepository.findById(id).orElseThrow(()-> new TaskNotFoundException(id));
    }

    /**User section**/

    //Get task if it exists by its user
    public Task getTask(String id, Long user_id) throws TaskNotFoundException {
        return taskRepository.findByIdAndUser_Id(id, user_id).orElseThrow(()-> new TaskNotFoundException(id));
    }

    //Save new task
    public Task saveNewTask(Task task, Long userId) throws UserNotFoundException {
        User userEntity = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(userId));
        task.setUser(userEntity);
        task.setCreatedAt(new Date());
        task.setUpdatedAt(new Date());
        task.setStatus(Status.ACTIVE);
        return taskRepository.save(task);
    }

    //Get all deleted tasks
    public List<Task> getAllDeletedTasks(Long userId){
        return taskRepository.findAllByUser_IdAndStatus(userId, Status.DELETE);
    }

    //Get all active tasks
    public List<Task> getAllActiveTasks(Long userId){
        return taskRepository.findAllByUser_IdAndStatus(userId, Status.ACTIVE);
    }

    //Get task by id if it's active
    public Task getActiveTask(String id, Long user_id) throws TaskNotFoundException {
        return taskRepository.findByIdAndUser_IdAndStatus(id, user_id, Status.ACTIVE).orElseThrow(()-> new TaskNotFoundException(id));
    }

    //Get all tasks by user id and completely
    public List<Task> getAllByCompletely(Long userId, boolean complete){
        return getAllActiveTasks(userId).stream().
                filter(task->task.getCompleted()==complete).collect(Collectors.toList());
    }

    //Mark task deleted but not actually delete it
    public String markTaskAsDel(String id, Long user_id) throws TaskNotFoundException {
        Task task= taskRepository.findByIdAndUser_Id(id, user_id).filter(t->t.getStatus()==Status.ACTIVE).orElseThrow(()->new TaskNotFoundException(id));
        task.setStatus(Status.DELETE);
        taskRepository.save(task);
        return id;
    }

    public Task activateTask(String task_id, Long user_id) throws TaskNotFoundException {
        Task task= taskRepository.findByIdAndUser_Id(task_id, user_id).orElseThrow(()->new TaskNotFoundException(task_id));
        task.setStatus(Status.ACTIVE);
        return taskRepository.save(task);
    }

    //Update task by id
    public Task updateTask(TaskUpdateRequestDTO task, String task_id, Long user_id) throws TaskNotFoundException {
        Task taskForUpdate =taskRepository.findByIdAndUser_IdAndStatus(task_id, user_id, Status.ACTIVE).
                orElseThrow(()->new TaskNotFoundException(task_id));
        if (task.getTitle()!=null) {
            taskForUpdate.setTitle(task.getTitle());
        }
        if (task.getDescription()!=null) {
            taskForUpdate.setDescription(task.getDescription());
        }
        if(task.getCompleted()!=null){
            taskForUpdate.setCompleted(task.getCompleted());
        }
        taskForUpdate.setUpdatedAt(new Date());
        return taskRepository.save(taskForUpdate);
    }

}
