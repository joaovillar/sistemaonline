package com.jornada.client.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GWTServiceEmailAsync {

	public void getTeachersEmailList(AsyncCallback<HashMap<String,String>> callback);
	
	public void sendEmail(ArrayList<String> emailList, String subject, String content, AsyncCallback<Boolean> callback);
}
