package com.jornada.client.ambiente.pais;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.jornada.client.MainMenu;
import com.jornada.client.MainView;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;

public class ComponenteAgendaPais extends Composite {

	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
    private static ComponenteAgendaPais uniqueInstance;
	
	public static ComponenteAgendaPais getInstance(MainView mainView){
		if(uniqueInstance==null){
			uniqueInstance = new ComponenteAgendaPais(mainView);
		}
		return uniqueInstance;
	}	
	
	private  ComponenteAgendaPais(MainView mainView){
		
		String strTitle = txtConstants.paisAmbienteAgenda();
		
		String strImageAddress = "images/config_date.png";
		
		String strText = txtConstants.paisAmbienteAgendaCompText1();
		strText+=txtConstants.paisAmbienteAgendaCompText2();
		strText+=txtConstants.paisAmbienteAgendaCompText3();
		strText+=txtConstants.paisAmbienteAgendaCompText4();
		
		MainViewComponent mainViewComp = new MainViewComponent(strTitle,strImageAddress,strText);
		mainViewComp.flexTable.addClickHandler(new addClickHandler());		
		
		initWidget(mainViewComp);	
		
		
	}
	
	private class addClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			MainMenu.isFirstEventFire=true;
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_AGENDA);			
		}		
	}		


}
