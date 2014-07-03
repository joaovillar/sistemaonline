package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jornada.shared.classes.Topico;

@RemoteServiceRelativePath("GWTServiceTopico")
public interface GWTServiceTopico  extends RemoteService {

	public int Adicionar(Topico topico);
	public ArrayList<Topico> getTopicoPeloConteudoProgramatico(int idConteudoProgramatico);
	public ArrayList<Topico> getTopicos(String strFilter);
	public ArrayList<Topico> getTopicos();
	public boolean deleteTopicoRow(int id_topico); 
	public boolean updateTopicoRow(Topico topico);
	
	
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static GWTServiceTopicoAsync instance;
		public static GWTServiceTopicoAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServiceTopico.class);
			}
			return instance;
		}
	}	
	
}
