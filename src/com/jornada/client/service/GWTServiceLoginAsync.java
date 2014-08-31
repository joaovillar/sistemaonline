package com.jornada.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Usuario;


public interface GWTServiceLoginAsync {
		
//	public void authenticateUser(String login, String password, AsyncCallback<Usuario> callback);
//	public void login(String login, String password, AsyncCallback<String> callback);

	public void loginServer(String login, String password, AsyncCallback<Usuario> callBack);    
	public void loginFromSessionServer(AsyncCallback<Usuario> callBack);     
	public void isSessionStillActive(Usuario usuario, AsyncCallback<Boolean> callBack);
//	public void changePassword(String name, String newPassword, AsyncCallback<Boolean> callBack); 
	@SuppressWarnings("rawtypes")
	public void logout(AsyncCallback callBack);	
	
}
