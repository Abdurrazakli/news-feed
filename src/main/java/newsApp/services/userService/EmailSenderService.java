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

    public void sendEmail(String contextPath, String token,NUser nUser){
        mailSender.send(constructMailMessage(contextPath,token,nUser));
    }


    private SimpleMailMessage constructMailMessage(String contextPath, String token, NUser user){
        final String subject = "Reset Password";
        String body = "Reset your password via this link:\r\n" + contextPath + "/user/change-password?token=" + token + "\n\nNote: link will be valid for 4 hours";
        log.debug("Mail body: " + body);
        return constructEmail(subject,body,user);
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
