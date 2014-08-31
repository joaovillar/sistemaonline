package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.UnidadeEscola;

public interface GWTServiceUnidadeEscolaAsync {

	public void getUnidadeEscolas(AsyncCallback<ArrayList<UnidadeEscola>> callback);

}
