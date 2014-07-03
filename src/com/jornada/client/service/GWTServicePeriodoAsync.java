package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.Usuario;

public interface GWTServicePeriodoAsync {

	public void AdicionarPeriodo(Periodo periodo, AsyncCallback<Integer> callback);	
	public void getPeriodosPeloCurso(int idCurso, AsyncCallback<ArrayList<Periodo>> callback);
	public void getPeriodosPeloCursoAmbienteProfessor(Usuario usuario, int idCurso, AsyncCallback<ArrayList<Periodo>> callback);	
	public void getPeriodos(String strFilter, AsyncCallback<ArrayList<Periodo>> callback);	
	public void getPeriodos(AsyncCallback<ArrayList<Periodo>> callback);	
	public void getCursos(AsyncCallback<ArrayList<Curso>> callback);	
	public void deletePeriodoRow(int id_periodo, AsyncCallback<Boolean> callback);	
	public void updatePeriodoRow(Periodo periodo, AsyncCallback<Boolean> callback);

}
