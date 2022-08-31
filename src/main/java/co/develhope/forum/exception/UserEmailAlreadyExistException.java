package co.develhope.forum.exception;

public class UserEmailAlreadyExistException  extends RuntimeException{

    private String userEmail;

    public UserEmailAlreadyExistException(String userEmail){
        super("User Email already exist");
        this.userEmail = userEmail;
    }

}
