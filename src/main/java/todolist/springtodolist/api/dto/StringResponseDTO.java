package todolist.springtodolist.api.dto;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StringResponseDTO {
    public StringResponseDTO(String message, HttpStatus status) {
        this.message = message;
        this.status = status.toString();
    }

    private String message;
    private String status;
}
