	package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;

public interface GWTServiceUsuarioAsync {

	public void AdicionarUsuario(Usuario usuario, AsyncCallback<String> callback);	
	public void updateUsuarioRow(Usuario usuario, AsyncCallback<String> callback);	
	public void atualizarSenha(int idUsuario, String senha, AsyncCallback<Boolean> callback);
	public void deleteUsuarioRow(int id_usuario, AsyncCallback<Boolean> callback);	
	public void importarUsuariosUsandoExcel(String strFileName, AsyncCallback<ArrayList<Usuario>> callback);
	public void getUsuarios(AsyncCallback<ArrayList<Usuario>> callback);	
	public void getUsuarioPeloId(int idUsuario, AsyncCallback<Usuario> callback);
	public void getUsuarios(String strFilter, AsyncCallback<ArrayList<Usuario>> callback);
	public void getUsuarios(String strDBField, String strFilter, AsyncCallback<ArrayList<Usuario>> callback);	
	public void getAlunosPorCurso(int idCurso, String strFiltroUsuario, AsyncCallback<ArrayList<Usuario>> callback);
	public void getAlunosPorCurso(int idCurso, AsyncCallback<ArrayList<Usuario>> callback);	
	public void getUsuariosPorCursoAmbientePai(Usuario usuarioPai, int idCurso, AsyncCallback<ArrayList<Usuario>> callback);
	public void getUsuariosPorTipoUsuario(int id_tipo_usuario, String strFilter, AsyncCallback<ArrayList<Usuario>> callback);	
	public void getUsuariosPorTipoUsuario(int id_tipo_usuario, AsyncCallback<ArrayList<Usuario>> callback);
	public void getFilhoDoPaiAmbientePais(Usuario usuarioPai, AsyncCallback<ArrayList<Usuario>> callback);		
	public void getTipoUsuarios(AsyncCallback<ArrayList<TipoUsuario>> callbackGetTipoUsuarios);
	public void associarPaisAoAluno(int id_aluno, ArrayList<String> list_id_pais, AsyncCallback<Boolean> callback);
	public void getTodosOsPaisDoAluno(int id_aluno, AsyncCallback<ArrayList<Usuario>> callback);	

}
