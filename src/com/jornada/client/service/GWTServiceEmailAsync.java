package com.jornada.client.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Comunicado;

public interface GWTServiceEmailAsync {

	public void getUsersIdlList(AsyncCallback<HashMap<String, Integer>> callback);

    public void sendMailByUserId(ArrayList<Integer> userList, String subject, String content, AsyncCallback<Boolean> callback);

    public void getEmailListByUserId(ArrayList<Integer> userIdList, AsyncCallback<ArrayList<String>> callback);

    public void getComunicadoEmailList(Comunicado comunicado, AsyncCallback<ArrayList<String>> callback);

    public void sendEmailParaAlunosPaisProfessores(int idTipoComunicado, ArrayList<Integer> listUser, String subject, String content, String strFileAddress, String strFileName, AsyncCallback<String> callback);

    public void getEmails(ArrayList<String> listIdUsuarios, AsyncCallback<String> callback);
}
