package co.develhope.forum.dto.response;

public class UserDTO extends BaseResponse{

    private int id;
    private String username;

    public UserDTO() {
    }

    public UserDTO(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }
}
