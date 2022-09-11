package co.develhope.forum.dto.response;

public class SignUpActivationDTO extends BaseResponse{

    private String activationCode;

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}
