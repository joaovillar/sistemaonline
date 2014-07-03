package com.jornada.client.ambiente.pais;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.jornada.client.MainMenu;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;

public class ComponenteComunicadoPais extends Composite {

	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	private static ComponenteComunicadoPais uniqueInstance;
	
	
	public static ComponenteComunicadoPais getInstance(){		
		if(uniqueInstance==null){
			uniqueInstance = new ComponenteComunicadoPais();
		}		
		return uniqueInstance;
	}
	

	private ComponenteComunicadoPais() {

		String strTitle = txtConstants.paisAmbienteComunicado();
		String strImageAddress = "images/notes.png";
		String strText = txtConstants.paisAmbienteComunicadoCompText1();
		strText += txtConstants.paisAmbienteComunicadoCompText2();

		MainViewComponent mainViewComp = new MainViewComponent(strTitle,strImageAddress, strText);
		mainViewComp.flexTable.addClickHandler(new addClickHandler());		

		initWidget(mainViewComp);

	}
	
	private class addClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {

			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_COMUNICADO);
			
		}
		
	}	

}
