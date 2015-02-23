package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Nota;

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
	public void getExcelBoletimAluno(int idCurso, int idAluno, AsyncCallback<String> callback);

}
