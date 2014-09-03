package com.jornada.server.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServiceEmail;
import com.jornada.server.classes.EmailServer;
import com.jornada.shared.classes.Comunicado;

public class GWTServiceEmailImpl extends RemoteServiceServlet implements
		GWTServiceEmail {

	private static final long serialVersionUID = 1L;

	@Override
	public HashMap<String, Integer> getUsersIdlList() {
		return EmailServer.getUsersIdlList();
	}

	@Override
    public Boolean sendMailByUserId(ArrayList<Integer> userList, String subject, String content) {
        return EmailServer.sendMailByUserId(userList, subject, content);
	}

	@Override
	public ArrayList<String> getEmailListByUserId(ArrayList<Integer> userIdList) {
		return EmailServer.getEmailListByUserId(userIdList);
	}

	@Override
	public ArrayList<String> getComucidadoEmailList(Comunicado comunicado) {
		return EmailServer.getComunicadoEmailList(comunicado);
	}

}
