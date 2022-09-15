package co.develhope.forum.controllers;


import co.develhope.forum.model.User;
import co.develhope.forum.repositories.UserRepository;
import co.develhope.forum.services.CustomUserService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.PublicEndpoint;
import it.pasqualecavallo.studentsmaterial.authorization_framework.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class LoginController {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private CustomUserService customUserService;

   /* @Deprecated
    @PublicEndpoint
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> login (@RequestParam MultiValueMap<String, String> params) {
        String jws = customUserService.checkUserCredentials(params.getFirst("username"), params.getFirst("password"));
        User user = userRepository.findByName(params.getFirst("username"));// for test only

        if (jws != null){
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.put(Constants.X_AUTHENTICATION_HEADER, Arrays.asList("Bearer " + jws));
            System.out.println(user.toString());//for test only
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }*/


}
