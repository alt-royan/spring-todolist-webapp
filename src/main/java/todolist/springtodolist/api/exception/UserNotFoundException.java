package todolist.springtodolist.api.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("Could not find user "+ id);
    }

    public UserNotFoundException(String login){
        super("Could not find user "+ login);
    }
}
