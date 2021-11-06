package todolist.springtodolist.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import todolist.springtodolist.api.exception.UserAlreadyExistException;

@ControllerAdvice
public class UserAlreadyExistAdvice {

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String UserAlreadyExistHandler(UserAlreadyExistException ex){
        return ex.getMessage();
    }
}
