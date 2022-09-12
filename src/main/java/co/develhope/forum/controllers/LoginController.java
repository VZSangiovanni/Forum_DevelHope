package co.develhope.forum.controllers;

import co.develhope.forum.configuration.security.PublicEndpoint;
import co.develhope.forum.configuration.util.Constants;
import co.develhope.forum.dto.response.LoginDTO;
import co.develhope.forum.model.User;
import co.develhope.forum.repositories.UserRepository;
import co.develhope.forum.services.CustomUserDetailsService;
import co.develhope.forum.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class LoginController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServices userServices;


    @PostMapping
    @PublicEndpoint
    @RequestMapping("/login")
    public ResponseEntity<Void> login (@RequestBody LoginDTO loginDTO) {
        if (loginDTO == null) return null;
        String jwt = userServices.checkUserCredentials(loginDTO.getUserName(), loginDTO.getUserPassword());
        if (jwt != null){
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.put(Constants.X_AUTHENTICATION_HEADER, Arrays.asList("Bearer " + jwt));
            User user = userRepository.findByName(loginDTO.getUserName());// only for test
            System.out.println(user); // only for test
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        /*User userFromDB = userRepository.findByName(loginDTO.getUserName());
        if (userFromDB != null && passwordEncoder.matches(loginDTO.getUserPassword(), userFromDB.getUserPassword())
                && userFromDB.getActive()) {
            System.out.println(userFromDB.toString());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            System.out.println(userFromDB.toString());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }*/
    }


}
