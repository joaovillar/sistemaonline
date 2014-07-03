package com.jornada.client.ambiente.professor;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainView;


public class TelaInicialProfessor extends Composite {

	ComponenteAvaliacao componenteAvaliacao;
	ComponenteNotas componenteNotas;
	ComponenteTopico componenteTopico;
	ComponenteOcorrencia componenteOcorrencia;
	ComponenteVisualizarCursoProfessor componenteVisualizarCursoProfessor;
	ComponenteDiarioProfessor componenteDiarioProfessor;
	
	VerticalPanel verticalPanel;
	
	Grid grid;
	
	MainView mainView;
	
	
	private static TelaInicialProfessor uniqueInstance;
	public static TelaInicialProfessor getInstance(MainView mainView){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialProfessor(mainView);
		}
		
		return uniqueInstance;
	}	

	private TelaInicialProfessor(MainView mainView) {
		
		this.mainView = mainView;
		
		componenteAvaliacao = ComponenteAvaliacao.getInstance(mainView);
		componenteNotas = ComponenteNotas.getInstance(mainView);
		componenteTopico = ComponenteTopico.getInstance(mainView);
		componenteOcorrencia = ComponenteOcorrencia.getInstance(mainView);
		componenteVisualizarCursoProfessor = new ComponenteVisualizarCursoProfessor();
		componenteDiarioProfessor = ComponenteDiarioProfessor.getInstance();
	
		verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSize("100%", "100%");
		
		grid = new Grid(2,3);
		grid.setCellSpacing(3);
		grid.setCellPadding(3);
		int row=0;
		grid.setWidget(row, 0, componenteTopico);
		grid.setWidget(row, 1, componenteAvaliacao);
		grid.setWidget(row++, 2, componenteNotas);
		grid.setWidget(row, 0, componenteOcorrencia);
		grid.setWidget(row, 1, componenteVisualizarCursoProfessor);
		grid.setWidget(row, 2, componenteDiarioProfessor);
		

		
		verticalPanel.add(grid);
		
		initWidget(verticalPanel);
		
		
	}

}
