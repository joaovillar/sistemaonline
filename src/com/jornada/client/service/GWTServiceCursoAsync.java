package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Usuario;

public interface GWTServiceCursoAsync {

	public void AdicionarCurso(Curso curso, AsyncCallback<Integer> callback);
	public void AdicionarCursoTemplate(int idCursoTemplate, Integer[] idCursosImportarAluno, Curso curso, AsyncCallback<Boolean> callback);	
	public void updateCursoRow(Curso curso, AsyncCallback<String> callback);	
	public void deleteCursoRow(int id_curso, AsyncCallback<Boolean> callback);	
	public void getCursos(AsyncCallback<ArrayList<Curso>> callback);	
	public void getCursos(Boolean status, AsyncCallback<ArrayList<Curso>> callback);
	public void getCursosPorPaiAmbientePais(Usuario usuario, AsyncCallback<ArrayList<Curso>> callback);
	public void getCursosPorAlunoAmbienteAluno(Usuario usuario, AsyncCallback<ArrayList<Curso>> callback);
	public void getCursosAmbienteProfessor(Usuario usuario, Boolean status, AsyncCallback<ArrayList<Curso>> callback);
	public void getCursos(String strFilter,  Boolean status, AsyncCallback<ArrayList<Curso>> callback);	
	public void getTodosOsAlunosDoCurso(int id_curso, AsyncCallback<ArrayList<Usuario>> callback);	
	public void associarAlunosAoCurso(int id_curso, ArrayList<Integer> list_id_alunos, AsyncCallback<Boolean> callback);
	public void AdicionarCursoString(Curso curso, AsyncCallback<String> callback);
	

}
