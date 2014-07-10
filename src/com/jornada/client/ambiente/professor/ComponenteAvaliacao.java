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

public class ComponenteAvaliacao extends Composite {
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	VerticalPanel vPanelMain = new VerticalPanel(); 
	VerticalPanel vPanel1 = new VerticalPanel(); 
	VerticalPanel vPanel2 = new VerticalPanel(); 
	MainView mainView;
	
    private static ComponenteAvaliacao uniqueInstance;
	
	public static ComponenteAvaliacao getInstance(MainView mainView){
		if(uniqueInstance==null){
			uniqueInstance = new ComponenteAvaliacao(mainView);
		}
		return uniqueInstance;
	}
	
	private ComponenteAvaliacao(MainView mainView) {		
		
		this.mainView=mainView;

		String strImageAddress = "images/options-icon-blue-128.png";
		
		String strTitle = txtConstants.professorAmbienteAvaliacao();
		String strText = txtConstants.professorAmbienteAvaliacaoCompText1();
		strText+=txtConstants.professorAmbienteAvaliacaoCompText2();

		
		MainViewComponent mainViewComponent = new MainViewComponent(strTitle, strImageAddress, strText);	
		mainViewComponent.flexTable.addClickHandler(new addClickHandler());		
	
		initWidget(mainViewComponent);

	}
	

	
	private class addClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			//mainView.openCadastroCurso();
			MainMenu.isFirstEventFire=true;
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_AVALIACAO);
			
		}
		
	}
	
	
	
	
	

	
	
	

}
