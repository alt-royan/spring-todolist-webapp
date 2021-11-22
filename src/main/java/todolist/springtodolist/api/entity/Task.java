package todolist.springtodolist.api.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Data
@Table(name="tasks")
public class Task{

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    @Id
    private String id;
    @NotEmpty
    private String title="";
    @NotEmpty
    private String description="";
    private Boolean completed=false;
    private Date createdAt;
    private Date updatedAt;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}

