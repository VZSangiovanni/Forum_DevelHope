package co.develhope.forum.exception;

public class UserNameAlreadyExistException extends RuntimeException{

    private final String userName;

    public UserNameAlreadyExistException(String userName){
        super("User Name already exist");
        this.userName = userName;
    }
}