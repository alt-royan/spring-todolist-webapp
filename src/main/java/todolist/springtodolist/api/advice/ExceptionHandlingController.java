package todolist.springtodolist.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import todolist.springtodolist.api.dto.ErrorDTO;
import todolist.springtodolist.api.exception.*;

@ControllerAdvice
public class ExceptionHandlingController {

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDTO UserAlreadyExistError(UserAlreadyExistException ex){
        return new ErrorDTO(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO UserNotFoundError(UserNotFoundException ex){
        return new ErrorDTO(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO TaskNotFoundError(TaskNotFoundException ex){
        return new ErrorDTO(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO WrongPasswordError(WrongPasswordException ex){
        return new ErrorDTO(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(JwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO JwtAuthenticationError(JwtAuthenticationException ex){
        return new ErrorDTO(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }
}
