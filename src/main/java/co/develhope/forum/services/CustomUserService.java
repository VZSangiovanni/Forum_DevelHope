package co.develhope.forum.services;


import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.model.User;
import co.develhope.forum.repositories.RoleRepository;
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
    private RoleRepository roleRepository;

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

 //Role Management
 
    public BaseResponse updateModerator(String username) {
        User user = userRepository.findByName(username);
        if (user == null) return new BaseResponse("User Not Found");
        if (user.getRoles().contains("ROLE_MOD")) return new BaseResponse("User already has this role");
        roleRepository.updateModerator(user.getUsername());
        return new BaseResponse(BaseResponse.StatusEnum.OK,"User " + user.getUsername() + " is now a Moderator");
    }

    public BaseResponse updateAdmin(String username) {
        User user = userRepository.findByName(username);
        if (user == null) return new BaseResponse("User Not Found");
        if (user.getRoles().contains("ROLE_ADMIN")) return new BaseResponse("User already has this role");
        roleRepository.updateAdmin(user.getUsername());
        return new BaseResponse(BaseResponse.StatusEnum.OK,"User " + user.getUsername() + " is now an Admin");
    }

    public BaseResponse updateUser(String username) {
        User user = userRepository.findByName(username);
        if (user == null) return new BaseResponse("User Not Found");
        if (user.getRoles().contains("ROLE_USER")) return new BaseResponse("User already has this role");
        roleRepository.updateUser(user.getUsername());
        return new BaseResponse(BaseResponse.StatusEnum.OK,"User " + user.getUsername() + " is now a User");
    }


// User CRUD

    public boolean deleteUser(String username) {

        int deleteCount = userRepository.deleteUser(username);

        return deleteCount == 1;

    }


}
