package com.jornada.client.ambiente.aluno.hierarquia;

import com.jornada.client.MainView;
import com.jornada.client.classes.hierarquia.MpHierarquiaCurso;
import com.jornada.client.service.GWTServiceHierarquiaCurso;

public class HierarquiaCursoAluno extends MpHierarquiaCurso{
	
	public HierarquiaCursoAluno(MainView mainView){		
		populateTree(mainView);
	}		
	
	public void populateTree(MainView mainView){
		mpPanelLoading.setVisible(true);
		GWTServiceHierarquiaCurso.Util.getInstance().getHierarquiaCursosAmbienteAluno(mainView.getUsuarioLogado(), callBackListaCursos);
	}


	
}


