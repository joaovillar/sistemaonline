package com.jornada.client.ambiente.coordenador;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.jornada.client.MainMenu;
import com.jornada.client.MainView;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;

public class ComponenteComunicados extends Composite {


	private static ComponenteComunicados uniqueInstance;
	
	
	public static ComponenteComunicados getInstance(MainView mainView){		
		if(uniqueInstance==null){
			uniqueInstance = new ComponenteComunicados(mainView);
		}		
		return uniqueInstance;
	}
	

	private ComponenteComunicados(MainView mainView) {
		
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
