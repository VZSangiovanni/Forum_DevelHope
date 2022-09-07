package co.develhope.forum.controllers;


import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.model.UserModel;
import co.develhope.forum.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    //Metodi Rest (Getmapping, Postmapping etc.etc)
    //creare un DTO per fare le response e le request
    @Autowired
    UserServices userServices;

   @GetMapping("/getUserName")
    public BaseResponse getUserName(@RequestParam String userName){
      return userServices.checkedUserName(userName);
   }

    @GetMapping("/getUserEmail")
    public BaseResponse getUserEmail(@RequestParam String userEmail){
        return userServices.checkedUserEmail(userEmail);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse createUser (@RequestBody UserModel userModel) {
        return userServices.createUser(userModel);
    }

}
