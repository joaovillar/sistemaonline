package com.jornada.client.ambiente;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainMenu;
import com.jornada.client.MainView;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;

public class MainViewAmbienteCoordenador extends Composite {
	
	private static MainViewAmbienteCoordenador uniqueInstance;
	
	VerticalPanel vPanelMain = new VerticalPanel(); 
	VerticalPanel vPanel1 = new VerticalPanel(); 
	VerticalPanel vPanel2 = new VerticalPanel(); 
	MainView mainView;
	
	public static MainViewAmbienteCoordenador getInstance(MainView mainView){		
		if(uniqueInstance==null){
			uniqueInstance = new MainViewAmbienteCoordenador(mainView);
		}		
		return uniqueInstance;
	}
	
	private MainViewAmbienteCoordenador(MainView mainView) {		
		
		TextConstants txtConstants = GWT.create(TextConstants.class);
		
		this.mainView=mainView;

		
		String strTitle = txtConstants.coordenadorAmbiente();
		String strImageAddress = "images/manager_128.png";
		String strText = txtConstants.coordenadorAmbienteCompText1();
		strText+=txtConstants.coordenadorAmbienteCompText2();
		strText+=txtConstants.coordenadorAmbienteCompText3();
		
		MainViewComponent mainViewComponent = new MainViewComponent(strTitle, strImageAddress, strText);	
		mainViewComponent.flexTable.addClickHandler(new addClickHandler());		
	
		initWidget(mainViewComponent);

	}
	

	
	private class addClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {

			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR);
			
		}
		
	}
	
	
	
	
	

	
	
	

}
