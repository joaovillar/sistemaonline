package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Nota;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimAluno;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimAnual;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimDisciplina;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimNotas;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimPeriodo;

public interface GWTServiceNotaAsync {

	public void Adicionar(Nota object, AsyncCallback<Boolean> callback);
	public void updateRow(Nota object, AsyncCallback<Boolean> callback);
	public void getNotaPelaAvaliacao(int idNota, int idAvaliacao, AsyncCallback<ArrayList<Nota>> callback);
	public void getBoletimNotasPorAlunoPorCurso(int idCurso, int idTipoUsuario, int idUsuario, AsyncCallback<String[][]> callback);
	public void getBoletimPeriodo(int idCurso, int idPeriodo, AsyncCallback<ArrayList<ArrayList<String>>> callback);
	public void getExcelBoletimPeriodo(int idCurso, int idPeriodo, AsyncCallback<String> callback);
	public void getRelatorioBoletimDisciplina(int idCurso, int idPeriodo, int idDisciplina, AsyncCallback<ArrayList<ArrayList<String>>> callback);
	public void getExcelBoletimDisciplina(int idCurso, int idPeriodo, int idDisciplina, AsyncCallback<String> callback);
	public void getBoletimAnual(int idCurso, AsyncCallback<ArrayList<ArrayList<String>>> callback);
	public void getExcelBoletimAnual(int idCurso, AsyncCallback<String> callback);
	public void getNotasAluno(int idCurso, int idTipoUsuario, int idUsuario, AsyncCallback<ArrayList<ArrayList<String>>> callback);
	public void getBoletimNotas(int idCurso, AsyncCallback<ArrayList<ArrayList<String>>> callback);
	public void getExcelBoletimNotas(int idCurso, AsyncCallback<String> callback);
	public void getBoletimAluno(int idCurso, int idAluno, AsyncCallback<ArrayList<ArrayList<String>>> callback);
	public void getExcelBoletimAluno(ArrayList<TableMultipleBoletimAluno> listTableMBD, AsyncCallback<String> callback);
	public void getExcelBoletimAluno(int idCurso, int idAluno, AsyncCallback<String> callback);
	public void getExcelBoletimDisciplina(ArrayList<TableMultipleBoletimDisciplina> listTableMBD, AsyncCallback<String> callback);
	public void getExcelBoletimPeriodo(ArrayList<TableMultipleBoletimPeriodo> listTableMBD, AsyncCallback<String> callback);
	public void getExcelBoletimAnual(ArrayList<TableMultipleBoletimAnual> listTableMBD, AsyncCallback<String> callback);
	public void getExcelBoletimNotas(ArrayList<TableMultipleBoletimNotas> listTableMBD, AsyncCallback<String> callback);
	public void getHistoricoAluno(int idCurso, int idAluno, AsyncCallback<ArrayList<ArrayList<String>>> callback);
	public void getExcelHistoricoAluno(int idCurso, int idAluno, AsyncCallback<String> callback);
	public void getMultipleExcelHistoricoAluno(ArrayList<TableMultipleBoletimAluno> listTableMBD, AsyncCallback<String> callback);

	

}
