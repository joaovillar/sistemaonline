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



public class ComponenteNotasPais extends Composite{

	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
    private static ComponenteNotasPais uniqueInstance;
	
	public static ComponenteNotasPais getInstance(MainView mainView){
		if(uniqueInstance==null){
			uniqueInstance = new ComponenteNotasPais(mainView);
		}
		return uniqueInstance;
	}	
	
	private ComponenteNotasPais(MainView mainView){
		
		String strTitle = txtConstants.paisAmbienteNota();
		
		String strImageAddress = "images/chart-icon.png";
		
		String strText = txtConstants.paisAmbienteNotaCompText1();
		strText+=txtConstants.paisAmbienteNotaCompText2();
		strText+=txtConstants.paisAmbienteNotaCompText3();
		
		MainViewComponent mainViewComp = new MainViewComponent(strTitle,strImageAddress,strText);
		mainViewComp.flexTable.addClickHandler(new addClickHandler());		
		
		initWidget(mainViewComp);	
		
		
	}
	
	private class addClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_NOTA);			
		}		
	}	


}
