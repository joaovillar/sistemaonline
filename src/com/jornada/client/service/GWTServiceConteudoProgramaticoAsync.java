package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.ConteudoProgramatico;

public interface GWTServiceConteudoProgramaticoAsync {

	public void Adicionar(ConteudoProgramatico conteudoProgramatico, AsyncCallback<Integer> callback);
	
	public void getConteudoProgramaticoPelaDisciplina(int idConteudoProgramatico, AsyncCallback<ArrayList<ConteudoProgramatico>> callback);
	
	public void getConteudoProgramaticos(String strFilter, AsyncCallback<ArrayList<ConteudoProgramatico>> callback);
	
	public void getConteudoProgramaticos(AsyncCallback<ArrayList<ConteudoProgramatico>> callback);
	
//	public void getPeriodos(AsyncCallback<ArrayList<Periodo>> callback);
	
	public void deleteConteudoProgramaticoRow(int id_conteudo_programatico, AsyncCallback<Boolean> callback);
	
	public void updateConteudoProgramaticoRow(ConteudoProgramatico conteudoProgramatico, AsyncCallback<Boolean> callback);

}
