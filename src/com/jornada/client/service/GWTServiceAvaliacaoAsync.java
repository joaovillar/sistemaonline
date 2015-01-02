package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Avaliacao;
import com.jornada.shared.classes.CursoAvaliacao;
import com.jornada.shared.classes.TipoAvaliacao;
import com.jornada.shared.classes.boletim.AvaliacaoNota;

public interface GWTServiceAvaliacaoAsync {
    
    public void AdicionarAvaliacao(Avaliacao object, AsyncCallback<String> callback);
    public void updateRow(Avaliacao object, AsyncCallback<String> callback);	
	public void deleteRow(int id_avaliacao, AsyncCallback<Boolean> callback);	
	public void getAvaliacaoPelaDisciplina(int idDisciplina, AsyncCallback<ArrayList<Avaliacao>> callback);	
	public void getAvaliacaoPeloCurso(int idCurso, AsyncCallback<ArrayList<CursoAvaliacao>> callback);
	public void getTipoAvaliacao(AsyncCallback<ArrayList<TipoAvaliacao>> callback);
	public void getAvaliacaoNotaPeriodoDisciplina(int idUsuario, int idCurso, String strNomePeriodo, String strNomeDisciplina, AsyncCallback<ArrayList<AvaliacaoNota>> callback);
	public void getHeaderRelatorioBoletimDisciplina(int idCurso, int idPeriodo, int idDisciplina, AsyncCallback<ArrayList<String>> callback);
	public void getAvaliacaoNotaPeriodoDisciplina(int idUsuario, int idCurso, String strNomePeriodo, String strNomeDisciplina, int idAvaliacao, AsyncCallback<ArrayList<AvaliacaoNota>> callback);
	public void getAvaliacaoNota(int idUsuario, int idCurso, String strDisciplina, AsyncCallback<ArrayList<AvaliacaoNota>> callback);	

}
