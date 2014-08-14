package com.jornada.server.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServiceEmail;
import com.jornada.server.classes.EmailServer;

public class GWTServiceEmailImpl extends RemoteServiceServlet implements GWTServiceEmail {

	private static final long serialVersionUID = 1L;

	@Override
	public HashMap<String, String> getTeachersEmailList() {
		return EmailServer.getTeachersEmail();
	}

	@Override
	public Boolean sendEmail(ArrayList<String> emailList, String subject,
			String content) {
		return EmailServer.sendEmail(emailList, subject, content);
	}

}
