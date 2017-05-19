package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Aula;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.TipoPresenca;
import com.jornada.shared.classes.presenca.PresencaUsuarioAula;
import com.jornada.shared.classes.presenca.PresencaUsuarioDisciplina;
import com.jornada.shared.classes.presenca.PresencaUsuarioDisciplinaAluno;
import com.jornada.shared.classes.presenca.PresencaUsuarioPeriodo;

public interface GWTServicePresencaAsync {
		
	
	public void AdicionarNovaPresenca(Aula aula, AsyncCallback<String> callback);
	public void updatePresencaRow(int idAula, int idUsuario, int idTipoPresenca, AsyncCallback<Boolean> callback);
	public void getAlunos(int idCurso, AsyncCallback<ArrayList<PresencaUsuarioAula>> callback);
	public void getAlunos(int idCurso, String strAluno, AsyncCallback<ArrayList<PresencaUsuarioAula>> callback);
	public void getListaPresencaAlunos(int idCurso, int idDisciplina, AsyncCallback<ArrayList<PresencaUsuarioAula>> callback);
	public void getListaPresencaAlunosArrayList(int idCurso, int idDisciplina, AsyncCallback<ArrayList<ArrayList<String>>> callback);
	public void getPresencaAluno(int idUsuario, int idCurso, AsyncCallback<ArrayList<Periodo>> callback);	
	public void getTipoPresencas(AsyncCallback<ArrayList<TipoPresenca>> callback);
    public void getAlunosDisciplina(int idCurso, int idDisciplina, AsyncCallback<ArrayList<PresencaUsuarioDisciplina>> callback);
    public void getAlunosPeriodo(int idCurso, int idPeriodo, AsyncCallback<ArrayList<PresencaUsuarioPeriodo>> callback);
    public void AdicionarFaltaDisciplina(ArrayList<PresencaUsuarioDisciplina> listPresencaUsuarioDisciplina, AsyncCallback<String> callback);
    public void getPresencaUsuarioDisciplinaAluno(int idUsuario, int idCurso, AsyncCallback<ArrayList<PresencaUsuarioDisciplinaAluno>> callback);
    
    public void AdicionarFaltaPeriodo(ArrayList<PresencaUsuarioPeriodo> listPresencaUsuarioPeriodo, AsyncCallback<String> callback);

}
