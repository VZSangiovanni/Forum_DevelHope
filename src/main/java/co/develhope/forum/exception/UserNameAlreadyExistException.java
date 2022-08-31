package co.develhope.forum.exception;

public class UserNameAlreadyExistException extends RuntimeException{

    private String userName;


    public UserNameAlreadyExistException(String userName){
        super("User Name already exist");
        this.userName = userName;
    }



}
