package co.develhope.forum.services;

import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.model.User;
import co.develhope.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RestorePasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    public BaseResponse requestPasswordCode(String username) {
        User user = userRepository.findByName(username);
        if (user == null) return new BaseResponse("User Not Found");
        user.setResetPasswordCode(UUID.randomUUID().toString());

        return null;
    }

}
