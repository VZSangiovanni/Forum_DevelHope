package co.develhope.forum.services;

import co.develhope.forum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * DISABLED FOR TESTING PURPOSES
 */

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendActivationEmail(User user) {
        SimpleMailMessage sms = new SimpleMailMessage();
        sms.setTo(user.getUserEmail());
        sms.setFrom("develhopetest87@gmail.com");
        sms.setReplyTo("develhopetest87@gmail.com");
        sms.setSubject("Benvenuto " + user.getUserFirstName());
        sms.setText("Benvenuto nel Forum DevelHope, il tuo codice di attivazione è: " + user.getUserActivationCode());
        javaMailSender.send(sms);
    }

    public void sendResetPasswordCodeEmail(User user) {
        SimpleMailMessage sms = new SimpleMailMessage();
        sms.setTo(user.getUserEmail());
        sms.setFrom("develhopetest87@gmail.com");
        sms.setReplyTo("develhopetest87@gmail.com");
        sms.setSubject("Salve " + user.getUserFirstName());
        sms.setText("Hai richiesto una nuova password, per impostarla inserisci questo codice: " + user.getResetPasswordCode());
        javaMailSender.send(sms);
    }
}