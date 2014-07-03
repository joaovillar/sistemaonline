package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Usuario;

public interface GWTServiceHierarquiaCursoAsync {

	public void getHierarquiaCursos(AsyncCallback<ArrayList<Curso>> callback);
	public void getHierarquiaCursosAmbientePais(Usuario usuarioPais, AsyncCallback<ArrayList<Curso>> callback);
	public void getHierarquiaCursosAmbienteAluno(Usuario usuarioAluno, AsyncCallback<ArrayList<Curso>> callback);	
	public void getHierarquiaCursosAmbienteProfessor(Usuario usuarioProfessor, AsyncCallback<ArrayList<Curso>> callback);	

}
