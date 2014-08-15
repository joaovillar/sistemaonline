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

public class EmailFrameWork {

	public void sendMail(ArrayList<String> emailList, String subject,
			String content) {

		final String username = "paisonline.ci.suporte@gmail.com";
		final String password = "paisonline.ricardo";

		String emails = "";
		boolean first = true;

		for (String email : emailList) {
			if (first) {
				emails = email;
				first = false;
			} else {
				emails = emails + ", " + email;
			}
		}

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
					InternetAddress.parse(emails));
			message.setSubject(subject);
			message.setContent(content, "text/html; charset=utf-8");
			Transport.send(message);
			System.out.println("Deu certo");

		} catch (MessagingException e) {
			System.out.println("deu tudo errado");
			throw new RuntimeException(e);
		}
	}
}
