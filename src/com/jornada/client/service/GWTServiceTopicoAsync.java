package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Topico;

public interface GWTServiceTopicoAsync {

	public void Adicionar(Topico topico, AsyncCallback<Integer> callback);
	
	public void getTopicoPeloConteudoProgramatico(int topico, AsyncCallback<ArrayList<Topico>> callback);
	
	public void getTopicos(String strFilter, AsyncCallback<ArrayList<Topico>> callback);
	
	public void getTopicos(AsyncCallback<ArrayList<Topico>> callback);
	
	public void deleteTopicoRow(int id_topico, AsyncCallback<Boolean> callback);
	
	public void updateTopicoRow(Topico topico, AsyncCallback<Boolean> callback);

}
