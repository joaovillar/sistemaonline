package com.jornada.client.ambiente.coordenador;

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

public class ComponenteTopico extends Composite {
	
	VerticalPanel vPanelMain = new VerticalPanel(); 
	VerticalPanel vPanel1 = new VerticalPanel(); 
	VerticalPanel vPanel2 = new VerticalPanel(); 
	private MainView mainView;

	
	
	private static ComponenteTopico uniqueInstance;

	public static ComponenteTopico getInstance(MainView mainView) {
		if (uniqueInstance == null) {
			uniqueInstance = new ComponenteTopico(mainView);
		}
		return uniqueInstance;
	}		
	
	private ComponenteTopico(MainView mainView) {		
		
		TextConstants txtConstants = GWT.create(TextConstants.class);
		
		this.mainView=mainView;
		
		String strTitle = txtConstants.coordenadorAmbienteTopico();
		String strImageAddress = "images/type_list_128.png";
		String strText = txtConstants.topicoCompText1();
		strText+= txtConstants.topicoCompText2();
		strText+= txtConstants.topicoCompText3();
		
		MainViewComponent mainViewComponent = new MainViewComponent(strTitle, strImageAddress, strText);	
		mainViewComponent.flexTable.addClickHandler(new addClickHandler());		
	
		initWidget(mainViewComponent);

	}
	

	
	private class addClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			MainMenu.isFirstEventFire=true;
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_TOPICO);
		}		
	}




	public MainView getMainView() {
		return mainView;
	}

	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}
	
	
	
	
	

	
	
	

}
