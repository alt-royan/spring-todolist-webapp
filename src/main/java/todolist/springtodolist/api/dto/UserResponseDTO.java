package todolist.springtodolist.api.dto;

import lombok.Getter;
import lombok.Setter;
import todolist.springtodolist.api.entity.Role;
import todolist.springtodolist.api.entity.Status;
import todolist.springtodolist.api.entity.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String login;
    private Status status;
    private Role role;

    private List<TaskResponseDTO> tasks;

    public static UserResponseDTO of(User user) {
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.id= user.getId();
        userResponseDTO.login=user.getLogin();
        userResponseDTO.status=user.getStatus();
        userResponseDTO.role=user.getRole();
        userResponseDTO.tasks=user.getTasks().stream().map(TaskResponseDTO::of).toList();
        return userResponseDTO;
    }

    public static List<UserResponseDTO> ofList(Iterable<User> userList){
        return StreamSupport.stream(userList.spliterator(), false).map(UserResponseDTO::of).collect(Collectors.toList());
    }
}
