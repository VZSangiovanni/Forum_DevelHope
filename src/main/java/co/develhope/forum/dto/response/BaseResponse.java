package co.develhope.forum.dto.response;

public class BaseResponse {
    private StatusEnum status = StatusEnum.OK;
    private String errorMessage;

    public BaseResponse(String errorMessage){
        if(!(errorMessage == null || errorMessage.length() == 0)){
            this.status = StatusEnum.KO;
        }
        this.errorMessage = errorMessage;
    }

    public enum StatusEnum{
        OK, KO
    }

    public BaseResponse() {
        this.status = status;
    }

    public BaseResponse(StatusEnum status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}