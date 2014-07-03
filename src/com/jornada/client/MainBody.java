package com.jornada.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.ambiente.MainViewAmbienteAluno;
import com.jornada.client.ambiente.MainViewAmbienteCoordenador;
import com.jornada.client.ambiente.MainViewAmbientePais;
import com.jornada.client.ambiente.MainViewAmbienteProfessor;


public class MainBody extends Composite {

	
	private static MainBody uniqueInstance;
	
	MainViewAmbientePais ambientePais = new MainViewAmbientePais();
//	MainViewComunicadosEscola comunicadosEscola;
	MainViewAmbienteProfessor ambienteProfessor = new MainViewAmbienteProfessor();
	MainViewAmbienteAluno ambienteAluno = new MainViewAmbienteAluno();
	MainViewNotasEscolares notasEscolares = new MainViewNotasEscolares();
	MainViewAmbienteCoordenador ambienteCoordenador;

	//PopupPanel1 pop1 = new PopupPanel1();
	
	VerticalPanel verticalPanel;
	HorizontalPanel horizontalPanel_1;
	HorizontalPanel horizontalPanel_2;
	HorizontalPanel horizontalPanel_3;	
	
	Grid grid;
	
	MainView mainView;
	
	
	public static MainBody getInstance(MainView mainView){
		if(uniqueInstance==null){
			uniqueInstance = new MainBody(mainView);
		}
		return uniqueInstance;
	}

	private MainBody(MainView mainView) {
		
		this.mainView = mainView;
		
		
		
//		adminEscola = new MainViewAdminEscola(mainView);
		ambienteCoordenador = MainViewAmbienteCoordenador.getInstance(mainView);
//		comunicadosEscola = MainViewComunicadosEscola.getInstance(mainView);
	
		verticalPanel = new VerticalPanel();
//		verticalPanel.setBorderWidth(2);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSize("100%", "100%");
		
		grid = new Grid(2, 2);
//		grid.setSize("100%", "100%");
		grid.setCellSpacing(3);
		grid.setCellPadding(3);
		grid.setWidget(0, 0, ambienteCoordenador);
		grid.setWidget(0, 1, ambienteProfessor);
		grid.setWidget(1, 0, ambienteAluno);		
		grid.setWidget(1, 1, ambientePais);
		
		ambienteCoordenador.setSize("500px", "150px");
		ambienteProfessor.setSize("500px", "150px");
		ambienteAluno.setSize("500px", "150px");
		ambientePais.setSize("500px", "150px");
		
		

//		grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);		
//		grid.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
//		grid.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);	
//		grid.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_LEFT);
//		
//		grid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);		
//		grid.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_MIDDLE);
//		grid.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);
//		grid.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_MIDDLE);		
		
		verticalPanel.add(grid);		
		
//		FadeAnimation fadeAnimation = new FadeAnimation(verticalPanel.getElement());
//		fadeAnimation.setOpacity(0.0);
//		verticalPanel.setVisible(true);
//		fadeAnimation.fade(1000, 1.0);	
		
		
		initWidget(verticalPanel);

		
		
	}

}
