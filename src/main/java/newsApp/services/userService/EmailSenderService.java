package newsApp.services.userService;

import lombok.extern.log4j.Log4j2;
import newsApp.models.userModels.NUser;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class EmailSenderService {
    private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String subject, String body,NUser nUser){
        mailSender.send(constructEmail(subject, body, nUser));
    }



    private SimpleMailMessage constructEmail(String subject, String body, NUser user){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(System.getProperty("support.email"));
        return email;
    }

}
