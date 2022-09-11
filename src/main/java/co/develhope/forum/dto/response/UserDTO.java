package co.develhope.forum.dto.response;

public class UserDTO extends BaseResponse{

    private int id;
    private String userName;



    public UserDTO() {
    }

    public UserDTO(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
