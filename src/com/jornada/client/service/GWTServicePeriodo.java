package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.Usuario;

@RemoteServiceRelativePath("GWTServicePeriodo")
public interface GWTServicePeriodo  extends RemoteService {

	public int AdicionarPeriodo(Periodo periodo);
	public ArrayList<Periodo> getPeriodosPeloCurso(int idCurso);
	public ArrayList<Periodo> getPeriodosPeloCursoAmbienteProfessor(Usuario usuario, int idCurso);	
	public ArrayList<Periodo> getPeriodos(String strFilter);
	public ArrayList<Periodo> getPeriodos();
	public ArrayList<Curso> getCursos();
	public boolean deletePeriodoRow(int id_periodo); 
	public boolean updatePeriodoRow(Periodo periodo);
	public String adicionarPeriodoString(Periodo periodo);    
	
	
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static GWTServicePeriodoAsync instance;
		public static GWTServicePeriodoAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServicePeriodo.class);
			}
			return instance;
		}
	}


    
	
}
