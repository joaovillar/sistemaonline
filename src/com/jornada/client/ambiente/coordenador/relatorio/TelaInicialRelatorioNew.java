package com.jornada.client.ambiente.coordenador.relatorio;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainView;



public class TelaInicialRelatorioNew extends Composite {

	ComponenteRelatorioBoletim componenteRelatorioBoletim;
	ComponenteRelatorioUsuario componenteRelatorioUsuario;
//	ComponenteDisciplina componenteDisciplina;
//	ComponenteConteudoProgramatico componenteConteudoProgramatico;
//	ComponenteTopico componenteTopico;



	VerticalPanel verticalPanel;
	HorizontalPanel horizontalPanel_1;
	HorizontalPanel horizontalPanel_2;
	HorizontalPanel horizontalPanel_3;	
	
	Grid grid;
	
	MainView mainView;
	
	
	private static TelaInicialRelatorioNew uniqueInstance;
	public static TelaInicialRelatorioNew getInstance(MainView mainView){		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialRelatorioNew(mainView);
		}		
		return uniqueInstance;
	}	

	private TelaInicialRelatorioNew(MainView mainView) {
		
		this.mainView = mainView;
		
		componenteRelatorioBoletim = ComponenteRelatorioBoletim.getInstance(mainView);
		componenteRelatorioUsuario = ComponenteRelatorioUsuario.getInstance(mainView);
//		componenteDisciplina = ComponenteDisciplina.getInstance(mainView);
//		componenteConteudoProgramatico = ComponenteConteudoProgramatico.getInstance(mainView);
//		componenteTopico = ComponenteTopico.getInstance(mainView);


	
		verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSize("100%", "100%");
		
		grid = new Grid(1, 2);
		grid.setCellSpacing(3);
		grid.setCellPadding(3);
		grid.setWidget(0, 0, componenteRelatorioBoletim);
		grid.setWidget(0, 1, componenteRelatorioUsuario);
//		grid.setWidget(0, 1, componentePeriodo);
//		grid.setWidget(0, 2, componenteDisciplina);
//		grid.setWidget(1, 0, componenteConteudoProgramatico);
//		grid.setWidget(1, 1, componenteTopico);
//		grid.setWidget(1, 2, componenteUsuarios);
//		grid.setWidget(2, 0, componenteComunicados);
//		grid.setWidget(2, 1, componenteOcorrencia);
//		grid.setWidget(2, 2, componenteDiarioCoordenador);
//		grid.setWidget(3, 0, componenteVisualizarCurso);
		
		verticalPanel.add(grid);
		
		initWidget(verticalPanel);
		
		
	}

}
