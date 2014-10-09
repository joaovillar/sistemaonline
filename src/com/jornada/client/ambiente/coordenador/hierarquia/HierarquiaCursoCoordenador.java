package com.jornada.client.ambiente.coordenador.hierarquia;

import com.jornada.client.MainView;
import com.jornada.client.classes.hierarquia.MpHierarquiaCurso;
import com.jornada.client.service.GWTServiceHierarquiaCurso;

public class HierarquiaCursoCoordenador extends MpHierarquiaCurso{
	
	public HierarquiaCursoCoordenador(MainView mainView){		
	    super(mainView);
		populateTree(mainView);
	}		
	
	public void populateTree(MainView mainView){
//		mpPanelLoading.setVisible(true);
	    gridSearch.getRowFormatter().setVisible(LOADING_POSITION, true);
		GWTServiceHierarquiaCurso.Util.getInstance().getHierarquiaCursos(callBackListaCursos);
	}


	
}


