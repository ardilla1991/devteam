package by.htp.devteam.service.util.email;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static by.htp.devteam.service.util.ConstantValue.*;

/**
 * Send email without authentication. Class generate correct headers, body and send email
 * @author julia
 *
 */
final class Email {
	/**
	 * Utility method to send simple HTML email
	 * 
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void sendEmail(Session session, String toEmail, String subject, String body) throws MessagingException, UnsupportedEncodingException{

		MimeMessage msg = new MimeMessage(session);
		// set message headers
		msg.addHeader("Content-type", "text/HTML; charset=" + CONFIG_EMAIL_CHARSET);
		msg.addHeader("format", "flowed");
		msg.addHeader("Content-Transfer-Encoding", "8bit");

		msg.setFrom(new InternetAddress(CONFIG_EMAIL_SITE_EMAIL, "NoReply-JD"));

		msg.setReplyTo(InternetAddress.parse(CONFIG_EMAIL_SITE_EMAIL, false));

		msg.setSubject(subject, CONFIG_EMAIL_CHARSET);

		msg.setText(body, CONFIG_EMAIL_CHARSET);

		msg.setSentDate(new Date());

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

		Transport.send(msg);

	}
}
