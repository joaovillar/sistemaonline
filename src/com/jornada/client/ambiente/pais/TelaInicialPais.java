package com.jornada.client.ambiente.pais;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainView;

public class TelaInicialPais extends Composite {

	ComponenteAgendaPais componenteAgendaPais;
	ComponenteNotasPais componenteNotasPais;
	ComponenteComunicadoPais componenteComunicadoPais; 
	ComponenteVisualizarCursoPais componenteVisualizarCursoPais; 
	ComponentePaisOcorrencia componentePaisOcorrencia; 
	ComponenteDiarioPais componenteDiarioPais;

	VerticalPanel verticalPanel;
	
	Grid grid;
	
	MainView mainView;
	
	
	private static TelaInicialPais uniqueInstance;
	
	public static TelaInicialPais getInstance(MainView mainView){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialPais(mainView);
		}
		
		return uniqueInstance;
	}	

	private TelaInicialPais(MainView mainView) {
		
		this.mainView = mainView;
		
		componenteAgendaPais = ComponenteAgendaPais.getInstance(mainView);
		componenteComunicadoPais = ComponenteComunicadoPais.getInstance();		
		componenteNotasPais = ComponenteNotasPais.getInstance(mainView);
		componentePaisOcorrencia = ComponentePaisOcorrencia.getInstance();
		componenteVisualizarCursoPais = new ComponenteVisualizarCursoPais();
		componenteDiarioPais = ComponenteDiarioPais.getInstance();
	
		verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSize("100%", "100%");
		
		grid = new Grid(2,3);
		grid.setCellSpacing(3);
		grid.setCellPadding(3);

		int row=0;
		grid.setWidget(row, 0, componenteComunicadoPais);
		grid.setWidget(row, 1, componenteAgendaPais);
		grid.setWidget(row++, 2, componenteNotasPais);
		grid.setWidget(row, 0, componenteVisualizarCursoPais);
		grid.setWidget(row, 1, componentePaisOcorrencia);
		grid.setWidget(row, 2, componenteDiarioPais);
		
		verticalPanel.add(grid);
		
		initWidget(verticalPanel);
		
		
	}

}
