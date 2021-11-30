package todolist.springtodolist.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import todolist.springtodolist.api.entity.Status;
import todolist.springtodolist.api.entity.Task;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Getter
@ApiModel(description = "Response for a task")
public class TaskDTO {

    @ApiModelProperty(example ="402840817d7268cb017d726c52460000")
    private String id;
    @ApiModelProperty(example ="test_title")
    private String title;
    @ApiModelProperty(example ="descr")
    private String description;
    @ApiModelProperty(example ="false")
    private Boolean completed;
    @ApiModelProperty(example ="2021-11-30T19:57:21.345+00:00")
    private Date createdAt;
    @ApiModelProperty(example ="2021-11-30T19:57:21.345+00:00")
    private Date updatedAt;
    @ApiModelProperty(example ="12")
    private  Long userId;
    @ApiModelProperty(example ="ACTIVE")
    private Status status;

    public static TaskDTO of(Task task) {
        TaskDTO taskDTO =new TaskDTO();
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

    public static List<TaskDTO> ofList(Iterable<Task> taskList){
        return StreamSupport.stream(taskList.spliterator(), false).map(TaskDTO::of).collect(Collectors.toList());
    }
}
