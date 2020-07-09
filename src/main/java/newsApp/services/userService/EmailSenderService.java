package newsApp.services.userService;

import newsApp.models.userModels.NUser;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


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
        String body = "Reset your password via this link:\r\n" + contextPath + "/user/changePassword?token=" + token + "\n\nNote: link will be valid for 4 hours";

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
