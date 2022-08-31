package co.develhope.forum.advice;


import co.develhope.forum.controllers.dto.response.BaseResponse;
import co.develhope.forum.exception.UserEmailAlreadyExistException;
import co.develhope.forum.exception.UserNameAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value ={UserEmailAlreadyExistException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse userEmailError(UserEmailAlreadyExistException e){
      return new BaseResponse(e.getMessage());
    }



}
