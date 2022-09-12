package co.develhope.forum.services;

import co.develhope.forum.configuration.util.JwtTokenUtil;
import co.develhope.forum.model.User;
import co.develhope.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    public String checkUserCredentials(String username, String password) {
        if (userRepository == null) {
            Assert.notNull(userRepository, "userRepository is null. Define a UserRepository implementation as a Spring Bean");
        }
        User userFromBD = userRepository.findByName(username);
        if (userFromBD != null && passwordEncoder.matches(password, userFromBD.getUserPassword())
                && userFromBD.getActive()) {
            return jwtTokenUtil.generateAccessToken(userFromBD.getUserName(), userFromBD.getUserRoles());
        }
        return null;
    }

}
