package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.Usuario;

@RemoteServiceRelativePath("GWTServiceDisciplina")
public interface GWTServiceDisciplina  extends RemoteService {

	public int AdicionarDisciplina(Disciplina disciplina);
	public int AdicionarDisciplina(Integer[] instPeriodos, Disciplina disciplina);
	public ArrayList<Disciplina> getDisciplinasPeloPeriodo(int idPeriodo);
	public ArrayList<Disciplina> getDisciplinasPeloPeriodo(int idPeriodo, String strSearch);
	public ArrayList<Disciplina> getDisciplinas(String strFilter);
	public ArrayList<Disciplina> getDisciplinasAssociadosAoProfessor(int idPeriodo, int idUsuario);
	public ArrayList<Disciplina> getDisciplinasPeloPeriodoAmbienteProfessor(Usuario usuario, int idPeriodo);
	
	public ArrayList<Disciplina> getDisciplinas();
	public ArrayList<Periodo> getPeriodos();
	public boolean deleteDisciplinaRow(int id_disciplina); 
	public boolean updateDisciplinaRow(Disciplina disciplina);
	public boolean updateDisciplinaComIdProfessor(int id_professor, ArrayList<String> list_id_disciplina);
	
	
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static GWTServiceDisciplinaAsync instance;
		public static GWTServiceDisciplinaAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServiceDisciplina.class);
			}
			return instance;
		}
	}	
	
}
