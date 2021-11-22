package todolist.springtodolist.api.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TaskUpdateRequestDTO {

    private String title;
    private String description;
    private Boolean completed;
}
