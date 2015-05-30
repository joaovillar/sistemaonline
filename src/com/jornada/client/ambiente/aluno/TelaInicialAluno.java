package com.jornada.client.ambiente.aluno;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainView;

public class TelaInicialAluno extends Composite {

	ComponenteAgendaAluno componenteAgendaAluno;
	ComponenteNotasAluno componenteNotasAluno;
	ComponenteComunicadoAluno componenteComunicadoAluno; 
	ComponenteVisualizarCursoAluno componenteVisualizarCursoAluno;
	ComponenteAlunoOcorrencia componenteAlunoOcorrencia;
//	ComponenteDiarioAluno componenteDiarioAluno;
	ComponentePresencaAluno componentePresencaAluno;

	VerticalPanel verticalPanel;

	
	Grid grid;
	
	MainView mainView;
	
	
	private static TelaInicialAluno uniqueInstance;
	public static TelaInicialAluno getInstance(MainView mainView){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialAluno(mainView);
		}
		
		return uniqueInstance;
	}	

	private TelaInicialAluno(MainView mainView) {
		
		this.mainView = mainView;
		
		componenteAgendaAluno = ComponenteAgendaAluno.getInstance();
		componenteNotasAluno = ComponenteNotasAluno.getInstance();
		componenteComunicadoAluno = ComponenteComunicadoAluno.getInstance();
		componenteVisualizarCursoAluno = new ComponenteVisualizarCursoAluno();
		componenteAlunoOcorrencia = ComponenteAlunoOcorrencia.getInstance();
//		componenteDiarioAluno = ComponenteDiarioAluno.getInstance();
		componentePresencaAluno = ComponentePresencaAluno.getInstance();
	
		verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSize("100%", "100%");
		
		grid = new Grid(2,3);
		grid.setCellSpacing(3);
		grid.setCellPadding(3);

		int row=0;
		grid.setWidget(row, 0, componenteComunicadoAluno);
		grid.setWidget(row, 1, componenteAgendaAluno);
		grid.setWidget(row++, 2, componenteNotasAluno);
		grid.setWidget(row, 0, componenteVisualizarCursoAluno);
		grid.setWidget(row, 1, componenteAlunoOcorrencia);
//		grid.setWidget(row++, 2, componenteDiarioAluno);
		grid.setWidget(row++, 2, componentePresencaAluno);
		
		verticalPanel.add(grid);
		
		initWidget(verticalPanel);
		
		
	}

}
