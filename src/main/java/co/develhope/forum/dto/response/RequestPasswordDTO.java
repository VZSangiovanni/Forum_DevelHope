package co.develhope.forum.dto.response;

public class RequestPasswordDTO extends BaseResponse{

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
