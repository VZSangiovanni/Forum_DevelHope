package co.develhope.forum.controllers;


import co.develhope.forum.model.User;
import co.develhope.forum.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




/**
 * NB controller only for testing purposes
 */
@RestController
@RequestMapping(value = "/api")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @PostMapping("/send-text")
    public void send(@RequestBody User user) {
        notificationService.sendActivationEmail(user);
    }


}
