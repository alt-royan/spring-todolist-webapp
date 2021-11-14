package todolist.springtodolist.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RegistrationRequestDTO {
    @NotEmpty
    private String login;

    @NotEmpty
    private String password;
}
