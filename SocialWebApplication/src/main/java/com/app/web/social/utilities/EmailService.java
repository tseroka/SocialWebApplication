package com.app.web.social.utilities;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.app.web.social.model.temp.EmailMessage;

@Service
public class EmailService 
{

	@Autowired
    private JavaMailSender mailSender;
 
    public void sendEmail(EmailMessage message) {

        MimeMessagePreparator preparator = getMessagePreparator(message);
 
        try {
            mailSender.send(preparator);
            System.out.println("Message Sent successfuly");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
 
    private MimeMessagePreparator getMessagePreparator(final EmailMessage message) {
 
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
 
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom("socialwebapplication@gmail.com");
                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(message.getEmail()));
                mimeMessage.setText(message.getContent());
                mimeMessage.setSubject(message.getSubject());
            }
        };
        return preparator;
    }
	
	
}
