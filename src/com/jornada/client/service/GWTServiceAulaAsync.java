package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Aula;

public interface GWTServiceAulaAsync {

	public void getAulas(AsyncCallback<ArrayList<Aula>> callback);	
	public void getAulas(int idDisciplina, AsyncCallback<ArrayList<Aula>> callback);
	
}
