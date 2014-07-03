package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Ocorrencia;
import com.jornada.shared.classes.OcorrenciaAluno;


public interface GWTServiceOcorrenciaAsync {
	
	public void AdicionarOcorrencia(Ocorrencia object, AsyncCallback<Boolean> callback);
	public void AtualizarOcorrencia(Ocorrencia object, AsyncCallback<Boolean> callback);
	public void AtualizarPaiCiente(OcorrenciaAluno object, AsyncCallback<Boolean> callback);
	public void deleteOcorrenciaRow(int id_ocorrencia, AsyncCallback<Boolean> callback);		
	public void getOcorrencias(AsyncCallback<ArrayList<Ocorrencia>> callback);	
	public void getOcorrencias(String strFilter, AsyncCallback<ArrayList<Ocorrencia>> callback);
	public void getOcorrenciasPeloConteudoProgramatico(int idConteudoProgramatico, AsyncCallback<ArrayList<Ocorrencia>> callback);
	public void getOcorrenciasPeloAluno(int idAluno, String locale, AsyncCallback<ArrayList<OcorrenciaAluno>> callback);
	
}
