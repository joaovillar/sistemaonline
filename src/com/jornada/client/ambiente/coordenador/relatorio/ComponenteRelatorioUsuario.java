package com.jornada.client.ambiente.coordenador.relatorio;

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

public class ComponenteRelatorioUsuario extends Composite {
	
	VerticalPanel vPanelMain = new VerticalPanel(); 
	VerticalPanel vPanel1 = new VerticalPanel(); 
	VerticalPanel vPanel2 = new VerticalPanel(); 
	MainView mainView;
	
	private static ComponenteRelatorioUsuario uniqueInstance;

	public static ComponenteRelatorioUsuario getInstance(MainView mainView) {
		if (uniqueInstance == null) {
			uniqueInstance = new ComponenteRelatorioUsuario(mainView);
		}
		return uniqueInstance;
	}		
	
	private ComponenteRelatorioUsuario(MainView mainView) {		
		
		TextConstants txtConstants = GWT.create(TextConstants.class);
		
		this.mainView=mainView;
		
		String strTitle = txtConstants.usuario();
		String strImageAddress = "images/Apps-preferences-desktop-user-icon-128.png";
		String strText = txtConstants.relatorioVisualisarImprimirRelatorios();
        strText+=txtConstants.usuarioCompText2();
        strText+=txtConstants.usuarioCompText3();
        strText+=txtConstants.usuarioCompText4();
		
		MainViewComponent mainViewComponent = new MainViewComponent(strTitle, strImageAddress, strText);	
		mainViewComponent.flexTable.addClickHandler(new addClickHandler());		
	
		initWidget(mainViewComponent);

	}
	

	
	private class addClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
		    MainMenu.isFirstEventFire = true;
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_RELATORIO_USUARIO);
			
		}
		
	}
	
	
	
	
	

	
	
	

}
