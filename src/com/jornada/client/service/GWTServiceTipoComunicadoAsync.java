package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.TipoComunicado;

public interface GWTServiceTipoComunicadoAsync {

	
	public void getTipoComunicados(AsyncCallback<ArrayList<TipoComunicado>> callback);	
	
}
