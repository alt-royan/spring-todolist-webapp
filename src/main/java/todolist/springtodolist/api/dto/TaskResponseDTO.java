package todolist.springtodolist.api.dto;

import lombok.Getter;
import todolist.springtodolist.api.entity.Status;
import todolist.springtodolist.api.entity.Task;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Getter
public class TaskResponseDTO {

    private String id;
    private String title;
    private String description;
    private Boolean completed;
    private Date createdAt;
    private Date updatedAt;
    private  Long userId;
    private Status status;

    public static TaskResponseDTO of(Task task) {
        TaskResponseDTO taskDTO =new TaskResponseDTO();
        taskDTO.id=task.getId();
        taskDTO.title=task.getTitle();
        taskDTO.description= task.getDescription();
        taskDTO.completed=task.getCompleted();
        taskDTO.createdAt=task.getCreatedAt();
        taskDTO.updatedAt=task.getUpdatedAt();
        taskDTO.userId=task.getUser().getId();
        taskDTO.status=task.getStatus();
        return taskDTO;
    }

    public static List<TaskResponseDTO> ofList(Iterable<Task> taskList){
        return StreamSupport.stream(taskList.spliterator(), false).map(TaskResponseDTO::of).collect(Collectors.toList());
    }
}
