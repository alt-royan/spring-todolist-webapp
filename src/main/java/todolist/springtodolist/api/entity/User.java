package todolist.springtodolist.api.entity;


import lombok.Data;
import org.apache.catalina.LifecycleState;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Task> tasks;
}
