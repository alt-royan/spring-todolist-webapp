package todolist.springtodolist.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "Response for user information")
public class UserResponseDTO {
    @ApiModelProperty(example ="12")
    private Long id;
    @ApiModelProperty(example ="test_user")
    private String login;
    @ApiModelProperty(example ="ACTIVE")
    private Status status;
    @ApiModelProperty(example ="USER")
    private Role role;


    public static UserResponseDTO of(User user) {
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.id= user.getId();
        userResponseDTO.login=user.getLogin();
        userResponseDTO.status=user.getStatus();
        userResponseDTO.role=user.getRole();
        return userResponseDTO;
    }

    public static List<UserResponseDTO> ofList(Iterable<User> userList){
        return StreamSupport.stream(userList.spliterator(), false).map(UserResponseDTO::of).collect(Collectors.toList());
    }
}
