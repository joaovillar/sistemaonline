package com.jornada.client.ambiente.pais;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainMenu;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;

public class ComponentePaisOcorrencia extends Composite {
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	VerticalPanel vPanelMain = new VerticalPanel(); 
	VerticalPanel vPanel1 = new VerticalPanel(); 
	VerticalPanel vPanel2 = new VerticalPanel(); 
	
    private static ComponentePaisOcorrencia uniqueInstance;
	
	public static ComponentePaisOcorrencia getInstance(){
		if(uniqueInstance==null){
			uniqueInstance = new ComponentePaisOcorrencia();
		}
		return uniqueInstance;
	}
	
	private ComponentePaisOcorrencia() {		
		
		
		String strTitle = txtConstants.paisAmbienteOcorrenciaAluno();
		String strImageAddress = "images/ocorrencia_128.png";
		String strText = txtConstants.paisAmbienteOcorrenciaCompText2();
		strText+=txtConstants.paisAmbienteOcorrenciaCompText3();
		strText+=txtConstants.paisAmbienteOcorrenciaCompText4();
//		strText+="- ATópicos.";
		
		MainViewComponent mainViewComponent = new MainViewComponent(strTitle, strImageAddress, strText);	
		mainViewComponent.flexTable.addClickHandler(new addClickHandler());		
	
		initWidget(mainViewComponent);

	}
	

	
	private class addClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_OCORRENCIA);
			
		}		
	}
	
	
	
	
	

	
	
	

}
