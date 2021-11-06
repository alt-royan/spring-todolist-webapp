package todolist.springtodolist.api.exception;

public class TaskNotFoundException extends Exception{

    public TaskNotFoundException(String id){
        super("Could not find task "+ id);
    }
}
