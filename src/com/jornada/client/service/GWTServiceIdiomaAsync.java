package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Idioma;

public interface GWTServiceIdiomaAsync {

	public void getIdiomas(AsyncCallback<ArrayList<Idioma>> callback);
	public void atualizarIdiomaUsuario(int idUsuario, String strLocale, AsyncCallback<Boolean> callback);

}
