package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Nota;

public interface GWTServiceNotaAsync {

	public void Adicionar(Nota object, AsyncCallback<Boolean> callback);
	public void updateRow(Nota object, AsyncCallback<Boolean> callback);
	public void getNotaPelaAvaliacao(int idNota, int idAvaliacao, AsyncCallback<ArrayList<Nota>> callback);
	public void getBoletimNotasPorAlunoPorCurso(int idCurso, int idTipoUsuario, int idUsuario, AsyncCallback<String[][]> callback);
	
}
