package co.develhope.forum.services;


import co.develhope.forum.model.User;
import co.develhope.forum.repositories.UserRepository;
import it.pasqualecavallo.studentsmaterial.authorization_framework.service.UserDetails;
import it.pasqualecavallo.studentsmaterial.authorization_framework.service.UserService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.utils.BCryptPasswordEncoder;
import it.pasqualecavallo.studentsmaterial.authorization_framework.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CustomUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;


    public UserDetails checkUserCredentials(String username, String password) {
        if (userRepository == null) {
            Assert.notNull(userRepository, "userRepository is null. Define a UserRepository implementation as a Spring Bean");
        }
        User userFromBD = userRepository.findByName(username);
        if (userFromBD != null && passwordEncoder.matches(password, userFromBD.getPassword())
                && userFromBD.getActive()) {
            System.out.println(userFromBD);// Only for test
            return userFromBD;
        }
        return null;
    }


    public boolean deleteUser(String username) {

        int deleteCount = userRepository.deleteUser(username);

        return deleteCount == 1;


    }


}
