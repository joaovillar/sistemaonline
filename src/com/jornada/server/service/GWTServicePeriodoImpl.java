package com.jornada.server.service;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServicePeriodo;
import com.jornada.server.classes.PeriodoServer;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;

public class GWTServicePeriodoImpl extends RemoteServiceServlet implements	GWTServicePeriodo {

	private static final long serialVersionUID = 3912070639094359182L;

	
    @Override
    public String adicionarPeriodoString(Periodo periodo) {
        return PeriodoServer.AdicionarPeriodoString(periodo);
    }
	   
	@Override
	public int AdicionarPeriodo(Periodo periodo) {		
		return PeriodoServer.AdicionarPeriodo(periodo);			
	}
	
	public ArrayList<Periodo> getPeriodosPeloCurso(int idCurso) {		
		return PeriodoServer.getPeriodosPeloCurso(idCurso);
	}	
	
	public ArrayList<Periodo> getPeriodosPeloCursoAmbienteProfessor(Usuario usuario, int idCurso) {		
		switch(usuario.getIdTipoUsuario()){
			case TipoUsuario.ADMINISTRADOR : return PeriodoServer.getPeriodosPeloCurso(idCurso);
			case TipoUsuario.COORDENADOR : return PeriodoServer.getPeriodosPeloCurso(idCurso);
			case TipoUsuario.PROFESSOR : return PeriodoServer.getPeriodosPeloCursoAmbienteProfessor(usuario, idCurso);
			default : return null;
		}		
	}	
	
	public ArrayList<Periodo> getPeriodos(String strFilter) {		
		return PeriodoServer.getPeriodos(strFilter);
	}
	
	public ArrayList<Periodo> getPeriodos() {		
		return PeriodoServer.getPeriodos();
	}	
	
	public boolean deletePeriodoRow(int id_periodo) {
		return PeriodoServer.deletePeriodoRow(id_periodo);
	}
	
	public boolean updatePeriodoRow(Periodo periodo) {
		return PeriodoServer.updatePeriodoRow(periodo);
	}
	
	public ArrayList<Curso> getCursos(){		
		return PeriodoServer.getCursos();		
	}
	
	
	/**
	 * Quick fix to this is in the RPC implementation at server side override method checkPermutationStrongName() with empty implementation.
	 */
	@Override
	protected void checkPermutationStrongName() throws SecurityException {
	    return;
	}




}
