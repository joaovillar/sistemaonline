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

public class ComponenteConteudoProgramatico extends Composite {
	
	VerticalPanel vPanelMain = new VerticalPanel(); 
	VerticalPanel vPanel1 = new VerticalPanel(); 
	VerticalPanel vPanel2 = new VerticalPanel(); 
	MainView mainView;
	
	private static ComponenteConteudoProgramatico uniqueInstance;

	public static ComponenteConteudoProgramatico getInstance(MainView mainView) {
		if (uniqueInstance == null) {
			uniqueInstance = new ComponenteConteudoProgramatico(mainView);
		}
		return uniqueInstance;
	}		
		
	
	private ComponenteConteudoProgramatico(MainView mainView) {
		
		TextConstants txtConstants = GWT.create(TextConstants.class);
		
		this.mainView=mainView;
		
		String strTitle = txtConstants.coordenadorAmbienteConteudoProgramatico();
		String strImageAddress = "images/Text-Document-icon_128.png";
		String strText = txtConstants.conteudoProgramaticoCompText1();
		strText+=txtConstants.conteudoProgramaticoCompText2();
		strText+=txtConstants.conteudoProgramaticoCompText3();
		
		MainViewComponent mainViewComponent = new MainViewComponent(strTitle, strImageAddress, strText);	
		mainViewComponent.flexTable.addClickHandler(new addClickHandler());		
	
		initWidget(mainViewComponent);

	}
	
	private class addClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			MainMenu.isFirstEventFire=true;
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_CONTEUDO_PROGRAMATICO);
//			mainView.openCadastroConteudoProgramatico();			
		}		
	}
	
	
	
	
	

	
	
	

}
