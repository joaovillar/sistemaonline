	package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.list.UsuarioErroImportar;
import com.jornada.shared.classes.relatorio.usuario.ProfessorDisciplinaRelatorio;
import com.jornada.shared.classes.usuario.UsuarioNomeID;

public interface GWTServiceUsuarioAsync {

	public void AdicionarUsuario(Usuario usuario, AsyncCallback<String> callback);	
	public void updateUsuarioRow(Usuario usuario, AsyncCallback<String> callback);	
	public void atualizarSenha(int idUsuario, String senha, boolean forcarPrimeiroLogin, AsyncCallback<Boolean> callback);
	public void deleteUsuarioRow(int id_usuario, AsyncCallback<Boolean> callback);
	public void gerarExcelUsuario(AsyncCallback<String> asyncCallback);
	public void importarUsuariosUsandoExcel(String strFileName, AsyncCallback<ArrayList<UsuarioErroImportar>> callback);
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
//	public void getPagePrint(String strHtmlPage, AsyncCallback<String> callback);
	public void getPaisPorCurso(int idCurso, String strFilterResp, String strFilterName, AsyncCallback<ArrayList<Usuario>> callback);
	public void getTodosPais(String strFilterResp, String strFilterName, AsyncCallback<ArrayList<Usuario>> callback);
    public void getAlunosTodosOuPorCurso(int idCurso, int idUnidade, boolean showAluno, boolean showPais, boolean showProfessor, AsyncCallback<ArrayList<UsuarioNomeID>> callback);
    public void getCoordenadoresAdministradoresNomeId(int idUnidade, AsyncCallback<ArrayList<UsuarioNomeID>> callback);
    public void getTodosUsuarios(AsyncCallback<ArrayList<Usuario>> callback);
    public void getProfessoresDisciplinas(AsyncCallback<ArrayList<ProfessorDisciplinaRelatorio>> callback);
    public void getExcelProfessoresDisciplinas(AsyncCallback<String> callback);
		

}
