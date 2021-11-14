/*
package todolist.springtodolist.api.entity;

import org.hibernate.annotations.GenericGenerator;
import todolist.springtodolist.api.dto.TaskModel;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Task{

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    @Id
    private String id;
    private String title;
    private String description;
    private Boolean completed=false;
    private Date createdAt;
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public static Task entityOf(TaskModel taskModel){
        Task task =new Task();
        task.setId(taskModel.getId());
        task.setTitle(taskModel.getTitle());
        task.setDescription(taskModel.getDescription());
        task.setCompleted(taskModel.getCompleted());
        task.setCreatedAt(taskModel.getCreatedAt());
        task.setUpdatedAt(taskModel.getUpdatedAt());
        return task;
    }

    public Task() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
*/
