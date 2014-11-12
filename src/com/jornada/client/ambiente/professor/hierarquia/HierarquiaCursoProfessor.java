package com.jornada.client.ambiente.professor.hierarquia;

import com.jornada.client.MainView;
import com.jornada.client.classes.hierarquia.MpHierarquiaCurso;
import com.jornada.client.service.GWTServiceHierarquiaCurso;

public class HierarquiaCursoProfessor extends MpHierarquiaCurso{
	
	public HierarquiaCursoProfessor(MainView mainView){		
	    super(mainView);
		populateTree(mainView);
	}		
	
	public void populateTree(MainView mainView){
		mpPanelLoading.setVisible(true);
		GWTServiceHierarquiaCurso.Util.getInstance().getHierarquiaCursosAmbienteProfessor(mainView.getUsuarioLogado(), true, callBackListaCursos);
	}
	
}


