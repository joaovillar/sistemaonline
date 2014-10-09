package com.jornada.client.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jornada.shared.classes.Comunicado;

@RemoteServiceRelativePath("GWTServiceEmail")
public interface GWTServiceEmail extends RemoteService {

	public HashMap<String, Integer> getUsersIdlList();

    public Boolean sendMailByUserId(ArrayList<Integer> userList, String subjsect, String content);

	public ArrayList<String> getEmailListByUserId(ArrayList<Integer> userIdList);

	public ArrayList<String> getComunicadoEmailList(Comunicado comunicado);
	
	public String sendEmailParaAlunosPaisProfessores(int idTipoComunicado, ArrayList<Integer> listUser, String subject, String content, String strFileAddress, String strFileName);
	
	public String getEmails(ArrayList<String> listIdUsuarios);

	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static GWTServiceEmailAsync instance;

		public static GWTServiceEmailAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(GWTServiceEmail.class);
			}
			return instance;
		}
	}

}
