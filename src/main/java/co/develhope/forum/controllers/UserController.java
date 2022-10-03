package co.develhope.forum.controllers;

import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.dto.response.DeleteUserDTO;
import co.develhope.forum.model.User;
import co.develhope.forum.services.CustomUserService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.PublicEndpoint;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.RoleSecurity;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.ZeroSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomUserService customUserService;

    @RoleSecurity(value = {"ROLE_MOD", "ROLE_ADMIN", "ROLE_FOUNDER"})
    @PutMapping("/ban-user/{username}")
    public BaseResponse banUser(@RequestParam boolean banned, @PathVariable String username) {
        return customUserService.banUser(banned, username);
    }

    @ZeroSecurity
    @GetMapping("/find-user-by-id/{userID}")
    public User findById (@PathVariable int userID){
        return customUserService.findByID(userID);
    }

    @ZeroSecurity
    @DeleteMapping("/delete-self")
    public BaseResponse deleteSelf() {
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        if (customUserService.deleteUser(principal.getUsername())) {
            return new BaseResponse();
        } else {
            return new BaseResponse("Delete failed");
        }
    }

    @RoleSecurity(value = {"ROLE_FOUNDER"})
    @DeleteMapping("/delete-user")
    public BaseResponse deleteUser(@RequestBody DeleteUserDTO deleteUserDTO) {
        if (customUserService.deleteUser(deleteUserDTO.getUsername())) {
            return new BaseResponse();
        } else {
            return new BaseResponse("Delete failed");
        }
    }

    @ZeroSecurity
    @GetMapping("/read-self")
    public User readSelf() {
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        return customUserService.readUser(principal.getUsername());
    }

    @RoleSecurity(value = {"ROLE_FOUNDER", "ROLE_ADMIN", "ROLE_MOD"})
    @GetMapping("/read-users")
    public List<Map<String, Object>> readUsers() {
        return customUserService.findAll();
    }
}