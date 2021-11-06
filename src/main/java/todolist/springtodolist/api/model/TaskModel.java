package todolist.springtodolist.api.model;

import todolist.springtodolist.api.entity.Task;

import java.util.Date;

public class TaskModel{

    private String id;
    private String title;
    private String description;
    private Boolean completed;
    private Date createdAt;
    private Date updatedAt;
    private Integer user_id;

    public static TaskModel modelOf(Task task){
        TaskModel taskModel =new TaskModel();
        taskModel.setId(task.getId());
        taskModel.setTitle(task.getTitle());
        taskModel.setDescription(task.getDescription());
        taskModel.setCompleted(task.getCompleted());
        taskModel.setCreatedAt(task.getCreatedAt());
        taskModel.setUpdatedAt(task.getUpdatedAt());
        taskModel.setUser_id(task.getUser().getId());
        return taskModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
