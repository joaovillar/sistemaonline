package com.jornada.server.service;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServiceTopico;
import com.jornada.server.classes.TopicoServer;
import com.jornada.shared.classes.Topico;

public class GWTServiceTopicoImpl extends RemoteServiceServlet implements GWTServiceTopico {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6025243781664719984L;

	public int Adicionar(Topico topico) {		
		return TopicoServer.Adicionar(topico);		
	}
	
	public ArrayList<Topico> getTopicoPeloConteudoProgramatico(int idConteudoProgramatico) {		
		return TopicoServer.getTopicos(idConteudoProgramatico);
	}	
	
	public ArrayList<Topico> getConteudoProgramaticos(String strFilter) {		
		return TopicoServer.getTopicos(strFilter);
	}
	
	public ArrayList<Topico> getTopicos() {		
		return TopicoServer.getTopicos();
	}	
	
	public boolean deleteTopicoRow(int id_topico) {
		return TopicoServer.deleteTopicoRow(id_topico);
	}
	
	public boolean updateTopicoRow(Topico topico) {
		return TopicoServer.updateTopicoRow(topico);
	}

	
	@Override
	public ArrayList<Topico> getTopicos(String strFilter) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	
	/**
	 * Quick fix to this is in the RPC implementation at server side override method checkPermutationStrongName() with empty implementation.
	 */
	@Override
	protected void checkPermutationStrongName() throws SecurityException {
	    return;
	}




}
