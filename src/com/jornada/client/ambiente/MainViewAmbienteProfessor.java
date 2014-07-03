package com.jornada.client.ambiente;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.jornada.client.MainMenu;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;

public class MainViewAmbienteProfessor extends Composite {
	

	public MainViewAmbienteProfessor(){
		
		TextConstants txtConstants = GWT.create(TextConstants.class);
		
		String strTitle = txtConstants.professorAmbiente();
		
		String strImageAddress = "images/professor_128.png";
		
		String strText = txtConstants.professorAmbienteCompText1();
		strText+=txtConstants.professorAmbienteCompText2();
		strText+=txtConstants.professorAmbienteCompText3();
		strText+=txtConstants.professorAmbienteCompText4();
		strText+=txtConstants.professorAmbienteCompText5();
		strText+=txtConstants.professorAmbienteCompText6();
		
		MainViewComponent mainViewComponent = new MainViewComponent(strTitle,strImageAddress,strText);
		mainViewComponent.flexTable.addClickHandler(new addClickHandler());		
		
		initWidget(mainViewComponent);			
		
	}	
	
	
	private class addClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR);			
		}		
	}	

}
