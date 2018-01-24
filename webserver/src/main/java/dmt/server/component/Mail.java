package dmt.server.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/**
 * @author Marco Romagnolo
 */
@Component
public class Mail {

    @Autowired
    private MailSender mailSender;

    @Value("${smtp.mail.bcc}")
    private String bcc;

    @Value("${smtp.mail.from}")
    private String from;

    public void send(String to, String subject, String body)  throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        if(!bcc.isEmpty())message.setBcc(bcc);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

}
