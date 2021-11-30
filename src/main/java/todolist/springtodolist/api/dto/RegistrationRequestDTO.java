package todolist.springtodolist.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ApiModel(description = "Request for registration")
public class RegistrationRequestDTO {

    @NotEmpty
    @ApiModelProperty(example ="user_test", required = true )
    private String login;

    @NotEmpty
    @ApiModelProperty(example ="user_test_pass", required = true )
    private String password;
}
