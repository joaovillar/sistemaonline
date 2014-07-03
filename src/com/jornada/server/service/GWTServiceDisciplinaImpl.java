package com.jornada.server.service;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServiceDisciplina;
import com.jornada.server.classes.DisciplinaServer;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;

public class GWTServiceDisciplinaImpl extends RemoteServiceServlet implements GWTServiceDisciplina {

	private static final long serialVersionUID = 3912070639094359182L;

	@Override
	public int AdicionarDisciplina(Disciplina disciplina) {		
		return DisciplinaServer.AdicionarDisciplina(disciplina);			
	}
	
	public ArrayList<Disciplina> getDisciplinasPeloPeriodo(int idPeriodo) {		
		return DisciplinaServer.getDisciplinasPeloPeriodo(idPeriodo);
	}	
	
	public ArrayList<Disciplina> getDisciplinasPeloPeriodo(int idPeriodo, String strFilter) {		
		return DisciplinaServer.getDisciplinasPeloPeriodo(idPeriodo, strFilter);
	}		
	
	public ArrayList<Disciplina> getDisciplinasAssociadosAoProfessor(int idPeriodo, int idUsuario) {		
		return DisciplinaServer.getDisciplinasAssociadosAoProfessor(idPeriodo, idUsuario);
	}
	
	public ArrayList<Disciplina> getDisciplinasPeloPeriodoAmbienteProfessor(Usuario usuario, int idPeriodo) {
		
		switch(usuario.getIdTipoUsuario()){
			case TipoUsuario.ADMINISTRADOR: return DisciplinaServer.getDisciplinasPeloPeriodo(idPeriodo);
			case TipoUsuario.COORDENADOR: return DisciplinaServer.getDisciplinasPeloPeriodo(idPeriodo);
			case TipoUsuario.PROFESSOR: return DisciplinaServer.getDisciplinasAssociadosAoProfessor(idPeriodo, usuario.getIdUsuario());
			default : return null;
		}

	}	
	
	public ArrayList<Disciplina> getDisciplinas(String strFilter) {		
		return DisciplinaServer.getDisciplinas(strFilter);
	}
	
	public ArrayList<Disciplina> getDisciplinas() {		
		return DisciplinaServer.getDisciplinas();
	}	
	
	public boolean deleteDisciplinaRow(int id_disciplina) {
		return DisciplinaServer.deleteDisciplinaRow(id_disciplina);
	}
	
	public boolean updateDisciplinaRow(Disciplina disciplina) {
		return DisciplinaServer.updateDisciplinaRow(disciplina);
	}
	
	public boolean updateDisciplinaComIdProfessor(int id_professor, ArrayList<String> list_id_disciplina) {
		return DisciplinaServer.updateDisciplinaComIdProfessor(id_professor, list_id_disciplina);
	}	
	
	public ArrayList<Periodo> getPeriodos(){		
		return DisciplinaServer.getPeriodos();		
	}
	
	
	/**
	 * Quick fix to this is in the RPC implementation at server side override method checkPermutationStrongName() with empty implementation.
	 */
	@Override
	protected void checkPermutationStrongName() throws SecurityException {
	    return;
	}


}
