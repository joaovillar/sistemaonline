package com.jornada.server.service;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServiceConteudoProgramatico;
import com.jornada.server.classes.ConteudoProgramaticoServer;
import com.jornada.shared.classes.ConteudoProgramatico;

public class GWTServiceConteudoProgramaticoImpl extends RemoteServiceServlet implements GWTServiceConteudoProgramatico {


	private static final long serialVersionUID = 3912070639094359182L;

	@Override
	public int Adicionar(ConteudoProgramatico conteudo) {		
		return ConteudoProgramaticoServer.Adicionar(conteudo);			
	}
	
	public ArrayList<ConteudoProgramatico> getConteudoProgramaticoPelaDisciplina(int idConteudoProgramatico) {		
		return ConteudoProgramaticoServer.getConteudoProgramaticos(idConteudoProgramatico);
	}	
	
	public ArrayList<ConteudoProgramatico> getConteudoProgramaticos(String strFilter) {		
		return ConteudoProgramaticoServer.getConteudoProgramaticos(strFilter);
	}
	
	public ArrayList<ConteudoProgramatico> getConteudoProgramaticos() {		
		return ConteudoProgramaticoServer.getConteudoProgramaticos();
	}	
	
	public boolean deleteConteudoProgramaticoRow(int id_conteudo_programatico) {
		return ConteudoProgramaticoServer.deleteConteudoProgramaticoRow(id_conteudo_programatico);
	}
	
	public boolean updateConteudoProgramaticoRow(ConteudoProgramatico conteudoProgramatico) {
		return ConteudoProgramaticoServer.updateConteudoProgramaticoRow(conteudoProgramatico);
	}
	
	/**
	 * Quick fix to this is in the RPC implementation at server side override method checkPermutationStrongName() with empty implementation.
	 */
	@Override
	protected void checkPermutationStrongName() throws SecurityException {
	    return;
	}


}
