package todolist.springtodolist.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import todolist.springtodolist.api.exception.*;

@ControllerAdvice
public class ExceptionHandlingController {

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String UserAlreadyExistError(UserAlreadyExistException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String UserNotFoundError(UserNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String TaskNotFoundError(TaskNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String WrongPasswordError(WrongPasswordException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(JwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String JwtAuthenticationError(JwtAuthenticationException ex){
        return ex.getMessage();
    }
}
