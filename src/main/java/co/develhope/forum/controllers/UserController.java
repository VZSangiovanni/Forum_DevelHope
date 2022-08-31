package co.develhope.forum.controllers;


import co.develhope.forum.controllers.dto.response.BaseResponse;
import co.develhope.forum.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
