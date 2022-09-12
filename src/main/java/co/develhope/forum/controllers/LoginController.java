package co.develhope.forum.controllers;

import co.develhope.forum.dto.response.LoginDTO;
import co.develhope.forum.model.User;
import co.develhope.forum.repositories.UserRepository;
import co.develhope.forum.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<Void> login (@RequestBody LoginDTO loginDTO) {
        if (loginDTO == null) return null;

        User userFromDB = userRepository.findByName(loginDTO.getUserName());
        if (userFromDB != null && passwordEncoder.matches(loginDTO.getUserPassword(), userFromDB.getUserPassword()) && userFromDB.getActive()) {
            System.out.println(userFromDB.toString());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            System.out.println(userFromDB.toString());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }



    }


}
