package todolist.springtodolist.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ApiModel(description = "Request for authentication")
public class AuthRequestDTO {
    @NotEmpty
    @ApiModelProperty(example = "test_user", required = true)
    private String login;

    @NotEmpty
    @ApiModelProperty(example = "test_user_pass", required = true)
    private String password;
}
