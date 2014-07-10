package com.jornada.client.ambiente.aluno;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainMenu;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;

public class ComponenteAlunoOcorrencia extends Composite {
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	VerticalPanel vPanelMain = new VerticalPanel(); 
	VerticalPanel vPanel1 = new VerticalPanel(); 
	VerticalPanel vPanel2 = new VerticalPanel(); 
	
    private static ComponenteAlunoOcorrencia uniqueInstance;
	
	public static ComponenteAlunoOcorrencia getInstance(){
		if(uniqueInstance==null){
			uniqueInstance = new ComponenteAlunoOcorrencia();
		}
		return uniqueInstance;
	}
	
	private ComponenteAlunoOcorrencia() {		
		
		
		String strTitle = txtConstants.alunoAmbienteOcorrencia();
		String strImageAddress = "images/ocorrencia_128.png";
		String strText = txtConstants.alunoAmbienteOcorrenciaCompText1();
		strText+=txtConstants.alunoAmbienteOcorrenciaCompText2();
		strText+=txtConstants.alunoAmbienteOcorrenciaCompText3();
//		strText+="- ATópicos.";
		
		MainViewComponent mainViewComponent = new MainViewComponent(strTitle, strImageAddress, strText);	
		mainViewComponent.flexTable.addClickHandler(new addClickHandler());		
	
		initWidget(mainViewComponent);

	}
	

	
	private class addClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			MainMenu.isFirstEventFire=true;
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_OCORRENCIA);
			
		}		
	}
	
	
	
	
	

	
	
	

}
