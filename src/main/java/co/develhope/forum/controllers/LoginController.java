package co.develhope.forum.controllers;

import co.develhope.forum.configuration.security.HierarchicalSecurity;
import co.develhope.forum.configuration.security.PublicEndpoint;
import co.develhope.forum.configuration.util.Constants;
import co.develhope.forum.model.User;
import co.develhope.forum.repositories.UserRepository;
import co.develhope.forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class LoginController {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserService userService;

    @PublicEndpoint
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> login (@RequestParam MultiValueMap<String, String> params) {
        String jwt = userService.checkUserCredentials(params.getFirst("username"), params.getFirst("password"));
        User user = userRepository.findByName(params.getFirst("username"));// for test only

        if (jwt != null){
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.put(Constants.X_AUTHENTICATION_HEADER, Arrays.asList("Bearer " + jwt));
            System.out.println(user.toString());//for test only
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


}
