package com.jornada.client.ambiente.coordenador;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainView;

public class TelaInicialAdminEscolaCurso extends Composite {

	ComponenteCurso componenteCurso;
	ComponentePeriodo componentePeriodo;
	ComponenteDisciplina componenteDisciplina;
	ComponenteConteudoProgramatico componenteConteudoProgramatico;
	ComponenteTopico componenteTopico;
//	ComponenteUsuario componenteUsuarios;	
//	ComponenteComunicados componenteComunicados;
//	ComponenteOcorrencia componenteOcorrencia;
//	ComponenteVisualizarCurso componenteVisualizarCurso;
//	ComponenteDiarioCoordenador componenteDiarioCoordenador;


	VerticalPanel verticalPanel;
	HorizontalPanel horizontalPanel_1;
	HorizontalPanel horizontalPanel_2;
	HorizontalPanel horizontalPanel_3;	
	
	Grid grid;
	
	MainView mainView;
	
	
	private static TelaInicialAdminEscolaCurso uniqueInstance;
	public static TelaInicialAdminEscolaCurso getInstance(MainView mainView){		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialAdminEscolaCurso(mainView);
		}		
		return uniqueInstance;
	}	

	private TelaInicialAdminEscolaCurso(MainView mainView) {
		
		this.mainView = mainView;
		
		componenteCurso = ComponenteCurso.getInstance(mainView);
		componentePeriodo = ComponentePeriodo.getInstance(mainView);
		componenteDisciplina = ComponenteDisciplina.getInstance(mainView);
		componenteConteudoProgramatico = ComponenteConteudoProgramatico.getInstance(mainView);
		componenteTopico = ComponenteTopico.getInstance(mainView);
//		componenteUsuarios = ComponenteUsuario.getInstance(mainView);				
//		componenteComunicados = ComponenteComunicados.getInstance(mainView);
//		componenteOcorrencia = ComponenteOcorrencia.getInstance(mainView);
//		componenteVisualizarCurso = new ComponenteVisualizarCurso();
//		componenteDiarioCoordenador = ComponenteDiarioCoordenador.getInstance();

	
		verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSize("100%", "100%");
		
		grid = new Grid(2, 3);
		grid.setCellSpacing(3);
		grid.setCellPadding(3);
		grid.setWidget(0, 0, componenteCurso);
		grid.setWidget(0, 1, componentePeriodo);
		grid.setWidget(0, 2, componenteDisciplina);
		grid.setWidget(1, 0, componenteConteudoProgramatico);
		grid.setWidget(1, 1, componenteTopico);
//		grid.setWidget(1, 2, componenteUsuarios);
//		grid.setWidget(2, 0, componenteComunicados);
//		grid.setWidget(2, 1, componenteOcorrencia);
//		grid.setWidget(2, 2, componenteDiarioCoordenador);
//		grid.setWidget(3, 0, componenteVisualizarCurso);
		
		verticalPanel.add(grid);
		
		initWidget(verticalPanel);
		
		
	}

}
