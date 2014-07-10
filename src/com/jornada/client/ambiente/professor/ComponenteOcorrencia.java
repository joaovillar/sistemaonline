package com.jornada.client.ambiente.professor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainMenu;
import com.jornada.client.MainView;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;

public class ComponenteOcorrencia extends Composite {
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	VerticalPanel vPanelMain = new VerticalPanel(); 
	VerticalPanel vPanel1 = new VerticalPanel(); 
	VerticalPanel vPanel2 = new VerticalPanel(); 
	MainView mainView;
	
    private static ComponenteOcorrencia uniqueInstance;
	
	public static ComponenteOcorrencia getInstance(MainView mainView){
		if(uniqueInstance==null){
			uniqueInstance = new ComponenteOcorrencia(mainView);
		}
		return uniqueInstance;
	}
	
	private ComponenteOcorrencia(MainView mainView) {		
		
		this.mainView=mainView;

		
		String strImageAddress = "images/ocorrencia_128.png";
		
		String strTitle = txtConstants.professorAmbienteOcorrencia();		
		String strText = txtConstants.professorAmbienteOcorrenciaCompText1();
		strText+=txtConstants.professorAmbienteOcorrenciaCompText2();

		
		MainViewComponent mainViewComponent = new MainViewComponent(strTitle, strImageAddress, strText);	
		mainViewComponent.flexTable.addClickHandler(new addClickHandler());		
	
		initWidget(mainViewComponent);

	}
	

	
	private class addClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			//mainView.openCadastroCurso();
			MainMenu.isFirstEventFire=true;
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_OCORRENCIA);
			
		}
		
	}
	
	
	
	
	

	
	
	

}
