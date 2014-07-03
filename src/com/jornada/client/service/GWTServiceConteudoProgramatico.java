package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jornada.shared.classes.ConteudoProgramatico;

@RemoteServiceRelativePath("GWTServiceConteudoProgramatico")
public interface GWTServiceConteudoProgramatico  extends RemoteService {

	public int Adicionar(ConteudoProgramatico conteudoProgramatico);
	public ArrayList<ConteudoProgramatico> getConteudoProgramaticoPelaDisciplina(int idConteudoProgramatico);
	public ArrayList<ConteudoProgramatico> getConteudoProgramaticos(String strFilter);
	public ArrayList<ConteudoProgramatico> getConteudoProgramaticos();
	public boolean deleteConteudoProgramaticoRow(int id_conteudo_programatico); 
	public boolean updateConteudoProgramaticoRow(ConteudoProgramatico conteudoProgramatico);
	
	
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static GWTServiceConteudoProgramaticoAsync instance;
		public static GWTServiceConteudoProgramaticoAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServiceConteudoProgramatico.class);
			}
			return instance;
		}
	}	
	
}
