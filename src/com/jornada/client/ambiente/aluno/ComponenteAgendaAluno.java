package com.jornada.client.ambiente.aluno;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.jornada.client.MainMenu;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;

public class ComponenteAgendaAluno extends Composite {

	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
    private static ComponenteAgendaAluno uniqueInstance;
	
	public static ComponenteAgendaAluno getInstance(){
		if(uniqueInstance==null){
			uniqueInstance = new ComponenteAgendaAluno();
		}
		return uniqueInstance;
	}	
	
	private  ComponenteAgendaAluno(){
		
		String strTitle = txtConstants.alunoAmbienteAgenda();
		
		String strImageAddress = "images/config_date.png";
		
		String strText = txtConstants.alunoAmbienteAgendaCompText1();
		strText+=txtConstants.alunoAmbienteAgendaCompText2();
		strText+=txtConstants.alunoAmbienteAgendaCompText3();
		strText+=txtConstants.alunoAmbienteAgendaCompText4();
		
		MainViewComponent mainViewComp = new MainViewComponent(strTitle,strImageAddress,strText);
		mainViewComp.flexTable.addClickHandler(new addClickHandler());		
		
		initWidget(mainViewComp);	
		
		
	}
	
	private class addClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			MainMenu.isFirstEventFire=true;
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_AGENDA);			
		}		
	}		


}
