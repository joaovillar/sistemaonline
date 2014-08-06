package com.jornada.server.framework;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.jornada.server.classes.EmailServer;
import com.jornada.shared.classes.Usuario;

public class EmailFrameWork {

	public void sendMail(ArrayList<Usuario> users) {
		final String username = "paisonline.ci.suporte@gmail.com";
		final String password = "paisonline.ricardo";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(EmailServer.getUserEmail(users)));
			message.setSubject("Meu primeiro email");
			message.setContent("<br>Primeiro Email</br>",
					"text/html; charset=utf-8");
			Transport.send(message);
			System.out.println("Deu certo");

		} catch (MessagingException e) {
			System.out.println("deu tudo errado");
			throw new RuntimeException(e);
		}
	}
}
