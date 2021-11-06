package todolist.springtodolist.api.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(Integer id){
        super("Could not find user "+ id);
    }
}
