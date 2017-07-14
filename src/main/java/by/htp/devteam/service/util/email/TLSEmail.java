package by.htp.devteam.service.util.email;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import by.htp.devteam.util.ConfigProperty;

import static by.htp.devteam.service.util.ConstantValue.*;

/**
 * Send Email using SMTP with TLS Authentication 
 * @author julia
 *
 */
public final class TLSEmail {
	/**
	   Outgoing Mail (SMTP) Server
	   requires TLS or SSL: smtp.gmail.com (use authentication)
	   Use Authentication: Yes
	   Port for TLS/STARTTLS: 587
	   @param toEmail receiver
	   @param body message's body
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void sendEmail(final String toEmail, final String body) throws UnsupportedEncodingException, MessagingException {
		final String fromEmail = ConfigProperty.INSTANCE.getStringValue(CONFIG_EMAIL_SMTP_EMAIL); //requires valid gmail id
		final String password = ConfigProperty.INSTANCE.getStringValue(CONFIG_EMAIL_SMTP_PASSWORD); // correct password for gmail id
		
		Properties props = new Properties();
		props.put("mail.smtp.host", ConfigProperty.INSTANCE.getStringValue(CONFIG_EMAIL_SMTP_HOST)); //SMTP Host
		props.put("mail.smtp.port", ConfigProperty.INSTANCE.getStringValue(CONFIG_EMAIL_SMTP_PORT)); //TLS Port
		props.put("mail.smtp.auth", ConfigProperty.INSTANCE.getStringValue(CONFIG_EMAIL_SMTP_AUTH)); //enable authentication
		props.put("mail.smtp.starttls.enable", ConfigProperty.INSTANCE.getStringValue(CONFIG_EMAIL_SMTP_SMARTTLS_ENABLE)); //enable STARTTLS
		
             //create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		
		Email.sendEmail(session, toEmail,ConfigProperty.INSTANCE.getStringValue(CONFIG_EMAIL_SUBJECT), body);
		
	}
}
