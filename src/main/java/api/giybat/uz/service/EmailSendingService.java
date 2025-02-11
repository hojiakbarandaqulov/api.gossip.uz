package api.giybat.uz.service;

import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.enums.ProfileRole;
import api.giybat.uz.util.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailSendingService {

    @Value("${spring.mail.username}")
    private String fromAccount;

    @Value("${server.domain}")
    private String serverDomain;

    private final JavaMailSender mailSender;

    public EmailSendingService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRegistrationEmail(String email, Integer profileId, AppLanguage lang) {
        String subject = "Complete registration";
        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "    <style>\n" +
                "        .button:hover {\n" +
                "            background-color: #dd4444;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Complete registration verification</h1>\n" +
                "<p>Please click to button for completing registration: <a style=\"padding: 10px 30px;\n" +
                "display: inline-block;\n" +
                "text-decoration: none;\n" +
                "collapse: white;\n" +
                "background-color:  indianred;\" href=\"%s/api/v1/auth/registration/verification/%s?lang=%s\"\n" +
                "target=\"_blank\"> Click\n" +
                "    there</a></p>\n" +
                "</body>\n" +
                "</html>";
        body = String.format(body, serverDomain, JwtUtil.encode(email,profileId),lang);
//        System.out.println(JwtUtil.encode(profileId));
        sendMimeEmail(email, subject, body);

    }

    private void sendMimeEmail(String email, String subject, String body) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            msg.setFrom(fromAccount);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body, true);
            CompletableFuture.runAsync(() -> {
                mailSender.send(msg);
            });
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendSimpleEmail(String email, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAccount);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        mailSender.send(simpleMailMessage);

    }
}
