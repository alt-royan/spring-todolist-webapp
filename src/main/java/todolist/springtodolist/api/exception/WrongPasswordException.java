package todolist.springtodolist.api.exception;

public class WrongPasswordException extends Exception{

    public WrongPasswordException(){
        super("Wrong login or password");
    }
}
