package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Comunicado;

public interface GWTServiceComunicadoAsync {

	
	public void AdicionarComunicado(Comunicado object, AsyncCallback<Boolean> callback);
	public void AtualizarComunicado(Comunicado object, AsyncCallback<Boolean> callback);
	public void deleteComunicadoRow(int id_comunicado, AsyncCallback<Boolean> callback);		
	public void getComunicados(AsyncCallback<ArrayList<Comunicado>> callback);	
	public void getComunicados(String strFilter, AsyncCallback<ArrayList<Comunicado>> callback);
	public void getComunicadosExterno(String strFilter, AsyncCallback<ArrayList<Comunicado>> callback);
	public void getComunicadosInterno(String strFilter, AsyncCallback<ArrayList<Comunicado>> callback);
	
}
