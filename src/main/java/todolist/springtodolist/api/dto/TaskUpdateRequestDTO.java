package todolist.springtodolist.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel(description = "Request for task updating")
public class TaskUpdateRequestDTO {

    @ApiModelProperty(example ="new title")
    private String title;
    @ApiModelProperty(example ="new description")
    private String description;
    @ApiModelProperty(example ="false")
    private Boolean completed;
}
