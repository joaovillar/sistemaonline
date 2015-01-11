package com.jornada.client.ambiente.professor;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainView;


public class TelaInicialProfessor extends Composite {

	ComponenteAvaliacao compAvaliacao;
	ComponenteNotas compNotas;
	ComponenteTopico compTopico;
	ComponenteComunicadoProfessor compComunicadoProfessor;
	ComponenteOcorrencia compOcorrencia;
	ComponenteVisualizarCursoProfessor compVisualizarCursoProfessor;
	ComponenteDiarioProfessor compDiarioProfessor;
	ComponenteRelatoriosProfessor compRelatoriosProfessor;
	
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
		
		compAvaliacao = ComponenteAvaliacao.getInstance(mainView);
		compNotas = ComponenteNotas.getInstance(mainView);
		compTopico = ComponenteTopico.getInstance(mainView);
		compComunicadoProfessor = ComponenteComunicadoProfessor.getInstance();
		compOcorrencia = ComponenteOcorrencia.getInstance(mainView);
		compVisualizarCursoProfessor = new ComponenteVisualizarCursoProfessor();
		compDiarioProfessor = ComponenteDiarioProfessor.getInstance();
		compRelatoriosProfessor = ComponenteRelatoriosProfessor.getInstance(mainView);
	
		verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSize("100%", "100%");
		
		grid = new Grid(3,3);
		grid.setCellSpacing(3);
		grid.setCellPadding(3);
		int row=0;
		grid.setWidget(row, 0, compComunicadoProfessor); grid.setWidget(row, 1, compDiarioProfessor);grid.setWidget(row++, 2, compOcorrencia);
		grid.setWidget(row, 0, compTopico);grid.setWidget(row, 1, compAvaliacao);grid.setWidget(row++, 2, compNotas);
		grid.setWidget(row, 0, compVisualizarCursoProfessor);grid.setWidget(row, 1, compRelatoriosProfessor);
		
		

		
		verticalPanel.add(grid);
		
		initWidget(verticalPanel);
		
		
	}

}
