package co.develhope.forum.controllers;

import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.dto.response.RequestPasswordDTO;
import co.develhope.forum.dto.response.RestorePasswordDTO;
import co.develhope.forum.services.RestorePasswordService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.PublicEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
public class ResetPasswordController {

    @Autowired
    private RestorePasswordService restorePasswordService;

    @PublicEndpoint
    @PostMapping("/request-password-code")
    public BaseResponse requestPasswordCode(@RequestBody RequestPasswordDTO requestPasswordDTO) {
        return restorePasswordService.requestPasswordCode(requestPasswordDTO.getUsername());
    }

    @PublicEndpoint
    @PostMapping("/restore-password")
    public BaseResponse restorePassword(@RequestBody RestorePasswordDTO restorePasswordDTO) {
        return restorePasswordService.restorePassword(restorePasswordDTO);
    }
}