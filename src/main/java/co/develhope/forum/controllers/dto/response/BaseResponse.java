package co.develhope.forum.controllers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
        OK, KO;
    }


}
