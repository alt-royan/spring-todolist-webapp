package todolist.springtodolist.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import todolist.springtodolist.api.exception.UserNotFoundException;

@ControllerAdvice
public class UserNotFountAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String UserNotFoundHandler(UserNotFoundException ex){
        return ex.getMessage();
    }
}
