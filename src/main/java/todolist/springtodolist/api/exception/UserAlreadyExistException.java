package todolist.springtodolist.api.exception;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String login){
        super("User with login '"+login+"' already exist");
    }
}
