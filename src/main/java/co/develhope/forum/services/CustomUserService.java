package co.develhope.forum.services;


import co.develhope.forum.dao.SignUpDAO;
import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.dto.response.UserDTO;
import co.develhope.forum.model.User;
import co.develhope.forum.repositories.UserRepository;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import it.pasqualecavallo.studentsmaterial.authorization_framework.service.UserDetails;
import it.pasqualecavallo.studentsmaterial.authorization_framework.service.UserService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.utils.BCryptPasswordEncoder;
import it.pasqualecavallo.studentsmaterial.authorization_framework.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CustomUserService implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SignUpDAO signUpDAO;

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

    /**
     * sistema invalidazione token, auto loggato, rieffetuare autenticazione con il token nuovo
     */

    public BaseResponse updateEmail(String username, String newEmail) {
        if (!signUpDAO.checkUserEmailExist(newEmail)){
            userRepository.updateUserEmailQuery(username,newEmail);
            return new BaseResponse("The email has been modified");
        } else {
            return new BaseResponse("This email is already in use");
        }
    }
}
