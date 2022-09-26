package co.develhope.forum.controllers;

import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.services.CustomUserService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.RoleSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {


    @Autowired
    private CustomUserService customUserService;


    @RoleSecurity(value = {"ROLE_ADMIN", "ROLE_FOUNDER"})
    @PutMapping("/promote/mod/{username}")
    public BaseResponse promoteToMod(@PathVariable String username) {
        return customUserService.updateModerator(username);
    }

    @RoleSecurity(value = {"ROLE_FOUNDER"})
    @PutMapping("/promote/admin/{username}")
    public BaseResponse promoteToAdmin(@PathVariable String username) {
        return customUserService.updateAdmin(username);
    }

    @RoleSecurity(value = {"ROLE_FOUNDER"})
    @PutMapping("/promote/user/{username}")
    public BaseResponse demoteToUser(@PathVariable String username) {
        return customUserService.updateUser(username);
    }



}
