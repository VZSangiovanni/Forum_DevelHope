package co.develhope.forum.controllers;



import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.dto.response.SignUpActivationDTO;
import co.develhope.forum.model.User;
import co.develhope.forum.services.SignUpService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.PublicEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class SignUpController {

    //Metodi Rest (Getmapping, Postmapping etc.etc)
    //creare un DTO per fare le response e le request
    @Autowired
    SignUpService signUpService;

    @PublicEndpoint
   @GetMapping("/getUserName")
    public BaseResponse getUserName(@RequestParam String userName){
      return signUpService.checkedUserName(userName);
   }
@PublicEndpoint
    @GetMapping("/getUserEmail")
    public BaseResponse getUserEmail(@RequestParam String userEmail){
        return signUpService.checkedUserEmail(userEmail);
    }

    @PublicEndpoint
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse createUser (@RequestBody User user) {
        return signUpService.createUser(user);
    }

    @PublicEndpoint
    @PostMapping("/activation")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BaseResponse activationUser(@RequestBody SignUpActivationDTO signUpActivationDTO){
       return signUpService.activeUser(signUpActivationDTO);
    }

}
