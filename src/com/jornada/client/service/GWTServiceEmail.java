package com.jornada.client.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jornada.shared.classes.Disciplina;

@RemoteServiceRelativePath("GWTServiceEmail")
public interface GWTServiceEmail extends RemoteService {
	
	
	public HashMap<String,String> getTeachersEmailList();
	public Boolean sendEmail(ArrayList<String> emailList, String subject, String content);
	
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static GWTServiceEmailAsync instance;
		public static GWTServiceEmailAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServiceEmail.class);
			}
			return instance;
		}
	}

}
