package com.app.web.social.utilities;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailUtility 
{

	
	public void send(String toEmail, String subject, String body)
	{
		String smtpHostServer = "smtp.gmail.com";
		    
		 Properties props = System.getProperties();

		 props.put("smtp.gmail.com", smtpHostServer);

	     Session session = Session.getInstance(props, null);
		    
		sendEmail(session, toEmail, subject, body);
	}
	
	public void sendEmail(Session session, String toEmail, String subject, String body){
		try
	    {
	      MimeMessage msg = new MimeMessage(session);
	      //set message headers
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setFrom(new InternetAddress("noReply@swa.com", "noReply-swa"));

	      msg.setReplyTo(InternetAddress.parse("noReply@swa.com", false));

	      msg.setSubject(subject, "UTF-8");

	      msg.setText(body, "UTF-8");

	      msg.setSentDate(new Date());

	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
	      System.out.println("Email is ready");
    	  Transport.send(msg);  

	      System.out.println("Email sent successfully");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}
}
