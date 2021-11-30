package todolist.springtodolist.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ApiModel(description = "Response for any error")
public class ErrorDTO {
    @ApiModelProperty(example = "NOT_FOUND")
    private String error;
    @ApiModelProperty(example = "404")
    private int status;
    @ApiModelProperty(example = "Could not find user test_user")
    private String errorMassage;

    public ErrorDTO(HttpStatus httpStatus, String errorMassage) {
        this.error = httpStatus.name();
        this.status = httpStatus.value();
        this.errorMassage=errorMassage;
    }
}
