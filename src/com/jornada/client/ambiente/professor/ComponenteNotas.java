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

public class ComponenteNotas extends Composite {
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	VerticalPanel vPanelMain = new VerticalPanel(); 
	VerticalPanel vPanel1 = new VerticalPanel(); 
	VerticalPanel vPanel2 = new VerticalPanel(); 
	MainView mainView;
	
    private static ComponenteNotas uniqueInstance;
	
	public static ComponenteNotas getInstance(MainView mainView){
		if(uniqueInstance==null){
			uniqueInstance = new ComponenteNotas(mainView);
		}
		return uniqueInstance;
	}
	
	private ComponenteNotas(MainView mainView) {		
		
		this.mainView=mainView;

		String strImageAddress = "images/Test-paper-256.png";
		
		String strTitle = txtConstants.professorAmbienteNotas();
		String strText = txtConstants.professorAmbienteNotasCompText1();
		strText+=txtConstants.professorAmbienteNotasCompText2();
//		strText+="- ATópicos.";
		
		MainViewComponent mainViewComponent = new MainViewComponent(strTitle, strImageAddress, strText);	
		mainViewComponent.flexTable.addClickHandler(new addClickHandler());		
	
		initWidget(mainViewComponent);

	}
	

	
	private class addClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			//mainView.openCadastroCurso();
			MainMenu.isFirstEventFire=true;
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_NOTA);
			
		}
		
	}
	
	
	
	
	

	
	
	

}
