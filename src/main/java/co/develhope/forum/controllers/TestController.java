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

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    CustomUserService customUserService;


    @GetMapping("/default-deny")
    public void defaultDenyEndpoint() {
        System.out.println("Default deny. This endpoint cannot be reached");
    }

    @PublicEndpoint
    @GetMapping("/public-endpoint")
    public void publicEndpoint() {
        System.out.println("This endpoint can be reached without authentication header");
    }

    @ZeroSecurity
    @GetMapping("/no-role-endpoint")
    public void noRoleEndpoint() {
        System.out.println("This endpoint can be reached with any role, but require authentication. Authenticated user is " + AuthenticationContext.get().getUsername());
    }

    @RoleSecurity(value = {"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/role-evaluated-endpoint")
    public void roleCheckEndpoint() {
        System.out.println("This endpoint can be reached only by authenticated users with ROLE_USER or ROLE_ADMIN. Authenticated user is " + AuthenticationContext.get().getUsername());
    }


    @ZeroSecurity
    @GetMapping("/find-user-by-id/{userID}")
    public User findById (@PathVariable int userID){
        return customUserService.findByID(userID);
    }

    @ZeroSecurity
    @DeleteMapping("delete-self")
    public BaseResponse deleteSelf() {

        AuthenticationContext.Principal principal = AuthenticationContext.get();

        if (customUserService.deleteUser(principal.getUsername())) {

            return new BaseResponse();

        } else {

            return new BaseResponse("Delete failed");
        }


    }


    @RoleSecurity(value = {"ROLE_FOUNDER"})
    @DeleteMapping("delete-user")
    public BaseResponse deleteUser(@RequestBody DeleteUserDTO deleteUserDTO) {
        if (customUserService.deleteUser(deleteUserDTO.getUsername())) {

            return new BaseResponse();

        } else {

            return new BaseResponse("Delete failed");
        }

    }


}
