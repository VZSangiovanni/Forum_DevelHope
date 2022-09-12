package co.develhope.forum.services;

import co.develhope.forum.configuration.util.BCryptPasswordEncoder;
import co.develhope.forum.configuration.util.JwtUtils;
import co.develhope.forum.model.User;
import co.develhope.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;


    public String checkUserCredentials(String username, String password) {
        if (userRepository == null) {
            Assert.notNull(userRepository, "userRepository is null. Define a UserRepository implementation as a Spring Bean");
        }
        User userFromBD = userRepository.findByName(username);
        if (userFromBD != null && passwordEncoder.matches(password, userFromBD.getUserPassword())
                && userFromBD.getActive()) {
            return jwtUtils.generateAccessToken(userFromBD.getUserName(), userFromBD.getUserRoles());
        }
        return null;
    }

}
