package com.jornada.client.ambiente.pais.hierarquia;

import com.jornada.client.MainView;
import com.jornada.client.classes.hierarquia.MpHierarquiaCurso;
import com.jornada.client.service.GWTServiceHierarquiaCurso;

public class HierarquiaCursoPais extends MpHierarquiaCurso{
	
	public HierarquiaCursoPais(MainView mainView){		
	    super(mainView);
		populateTree(mainView);
	}		
	
	public void populateTree(MainView mainView){
		mpPanelLoading.setVisible(true);
		GWTServiceHierarquiaCurso.Util.getInstance().getHierarquiaCursosAmbientePais(mainView.getUsuarioLogado(), callBackListaCursos);
	}
	
}


