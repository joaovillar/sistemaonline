package com.jornada.server.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServiceEmail;
import com.jornada.server.classes.EmailServer;
import com.jornada.server.classes.utility.MpUtilServer;
import com.jornada.server.framework.EmailFrameWork;
import com.jornada.shared.classes.Comunicado;

public class GWTServiceEmailImpl extends RemoteServiceServlet implements GWTServiceEmail {

	private static final long serialVersionUID = 1L;

	@Override
	public HashMap<String, Integer> getUsersIdlList() {
		return EmailServer.getUsersIdlList();
	}

	@Override
    public Boolean sendMailByUserId(ArrayList<Integer> userList, String subject, String content) {
        return EmailServer.sendOcorrenciaPorEmail(userList, subject, content);
	}

	@Override
	public ArrayList<String> getEmailListByUserId(ArrayList<Integer> userIdList) {
		return EmailServer.getEmailListByUserId(userIdList);
	}

	@Override
	public ArrayList<String> getComunicadoEmailList(Comunicado comunicado) {
		return EmailServer.getComunicadoEmailList(comunicado);
	}

	public String sendEmailParaAlunosPaisProfessores(int idTipoComunicado, ArrayList<Integer> listUser, String subject, String content, String strFileAddress, String strFileName){	    
	    
	    String status = "";
	    
	    int idComunicado = EmailServer.adicionarEmail(idTipoComunicado, subject, content, strFileAddress);
	    
	    for (int i = 0; i < listUser.size(); i++) {
	        int idUser = listUser.get(i);	                
	        EmailServer.adicionarRelacionamentoEmail(idComunicado, idUser);            
        }
	    
	    status = EmailServer.sendEmailAlunosPaisProfessores(listUser, subject, content, strFileAddress, strFileName);
	    
	    
	    return status;
	}
	
	
	
	public String getEmails(ArrayList<String> listIdUsuarios){
	    
	    String strEmails = EmailServer.getEmails(listIdUsuarios);
	    String[] arrayEmail = strEmails.split(", ");	    
	    ArrayList<String> listEmails = new ArrayList<String>();
	    
	    for (int i = 0; i < arrayEmail.length; i++) {
            listEmails.add(arrayEmail[i]);
        }
	    
	    ArrayList<String> listEmailsSplit = MpUtilServer.getMaxTestCaseQueryRowsAllowed(listEmails, EmailFrameWork.MAX_LIMIT_SEND_WEB);
	    
	    strEmails="";
	    for (int i = 0; i < listEmailsSplit.size(); i++) {
            strEmails = strEmails + "====================Quebra de linha a cada "+ EmailFrameWork.MAX_LIMIT_SEND_WEB+"====================<br>";
            strEmails = strEmails + listEmailsSplit.get(i);
            strEmails = strEmails + "<br>";
        }
	    strEmails = strEmails.replace(",", ", ");
	    
	    return strEmails;
	}
	
	
	
}
