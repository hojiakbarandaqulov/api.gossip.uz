package api.giybat.uz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSendingService {

    @Value("${spring.mail.username}")
    private String fromAccount;

    private final JavaMailSender mailSender;

    public EmailSendingService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRegistrationEmail(String email,Integer profileId){
        String subject = "Complete the Registration Email"+profileId;
        String body = " Registration Email. Please lick to link: http://localhost:8080/api/v1/registration/verification/"+profileId;
        sendEmail(email,subject,body);

    }

    private void sendEmail(String email, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAccount);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        mailSender.send(simpleMailMessage);

    }
}
