package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Avaliacao;
import com.jornada.shared.classes.CursoAvaliacao;
import com.jornada.shared.classes.TipoAvaliacao;

public interface GWTServiceAvaliacaoAsync {

	
	public void AdicionarAvaliacao(Avaliacao object, AsyncCallback<Boolean> callback);
	public void updateRow(Avaliacao object, AsyncCallback<Boolean> callback);	
	public void deleteRow(int id_avaliacao, AsyncCallback<Boolean> callback);	
	public void getAvaliacaoPeloConteudoProgramatico(int topico, AsyncCallback<ArrayList<Avaliacao>> callback);	
	public void getAvaliacaoPeloCurso(int idCurso, String locale, AsyncCallback<ArrayList<CursoAvaliacao>> callback);
	public void getTipoAvaliacao(AsyncCallback<ArrayList<TipoAvaliacao>> callback);	

	
}
