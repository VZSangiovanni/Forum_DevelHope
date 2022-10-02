package co.develhope.forum.services;

import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.dto.response.RequestPasswordDTO;
import co.develhope.forum.dto.response.RestorePasswordDTO;
import co.develhope.forum.model.User;
import co.develhope.forum.repositories.ForumRepository;
import co.develhope.forum.repositories.UserRepository;
import it.pasqualecavallo.studentsmaterial.authorization_framework.utils.BCryptPasswordEncoder;
import it.pasqualecavallo.studentsmaterial.authorization_framework.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RestorePasswordService {

    private static final Logger log = LoggerFactory.getLogger(ForumRepository.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private NotificationService notificationService;

    public BaseResponse requestPasswordCode(String username) {
        try {

            User user = userRepository.findByName(username);
            if (user == null) return new BaseResponse("User Not Found");
            String passwordCode = user.setResetPasswordCode(UUID.randomUUID().toString());
            userRepository.setPasswordCode(passwordCode, username);
            //notificationService.sendResetPasswordCodeEmail(user); //Disable for test
            return new BaseResponse(BaseResponse.StatusEnum.OK, "Reset password code sent, check your mail");
        }catch (Exception e){
            log.error("ERROR", e);
            return null;
        }
    }

    public BaseResponse restorePassword(RestorePasswordDTO restorePasswordDTO) {
        User user = userRepository.findByResetPasswordCode(restorePasswordDTO.getResetPasswordCode());
        if (user == null) return new BaseResponse("User Not Found");
        user.setPassword(passwordEncoder.encode(restorePasswordDTO.getNewPassword()));
        userRepository.resetPassword(user.getPassword(), user.getResetPasswordCode());
        String nullPasswordCode = user.setResetPasswordCode(null);
        userRepository.setPasswordCode(nullPasswordCode, user.getUsername());
        return new BaseResponse(BaseResponse.StatusEnum.OK, "Password changed successfully");
    }

}
