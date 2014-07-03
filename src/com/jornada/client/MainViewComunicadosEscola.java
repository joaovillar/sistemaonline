package com.jornada.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.jornada.client.content.i18n.TextConstants;

public class MainViewComunicadosEscola extends Composite {


	private static MainViewComunicadosEscola uniqueInstance;
	
	
	public static MainViewComunicadosEscola getInstance(MainView mainView){		
		if(uniqueInstance==null){
			uniqueInstance = new MainViewComunicadosEscola(mainView);
		}		
		return uniqueInstance;
	}
	

	private MainViewComunicadosEscola(MainView mainView) {
		
		TextConstants txtConstants = GWT.create(TextConstants.class);

		String strTitle = txtConstants.coordenadorAmbienteComunicado();
		String strImageAddress = "images/notes.png";
		String strText = txtConstants.coordenadorAmbienteComunicadoCompText1();
		strText += txtConstants.coordenadorAmbienteComunicadoCompText2();

		MainViewComponent mainViewComp = new MainViewComponent(strTitle,strImageAddress, strText);
		mainViewComp.flexTable.addClickHandler(new addClickHandler());		

		initWidget(mainViewComp);

	}
	
	private class addClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {

			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_COMUNICADO);
			
		}
		
	}	

}
