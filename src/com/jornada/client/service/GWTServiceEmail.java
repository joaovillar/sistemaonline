package com.jornada.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("GWTServiceEmail")
public interface GWTServiceEmail extends RemoteService {
	
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
