package com.jornada.client.ambiente;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.jornada.client.MainMenu;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;

public class MainViewAmbientePais extends Composite {

	
	public MainViewAmbientePais(){
		
		TextConstants txtConstants = GWT.create(TextConstants.class);
		
		String strTitle = txtConstants.paisAmbiente();
		
		String strImageAddress = "images/parents-128.png";
		
		String strText = txtConstants.paisAmbienteCompText1();
		strText+=txtConstants.paisAmbienteCompText2();
		strText+=txtConstants.paisAmbienteCompText3();		
//		strText+=txtConstants.paisAmbienteCompText4();
		strText+=txtConstants.paisAmbienteOcorrenciaCompText1();

		
		MainViewComponent mainViewComp = new MainViewComponent(strTitle,strImageAddress,strText);
		mainViewComp.flexTable.addClickHandler(new addClickHandler());		
		
		initWidget(mainViewComp);	
		
		
	}
	
	private class addClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS);			
		}		
	}		


}
