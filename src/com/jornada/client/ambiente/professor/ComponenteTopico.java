package com.jornada.client.ambiente.professor;

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

	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	private static ComponenteTopico uniqueInstance;

	public static ComponenteTopico getInstance(MainView mainView) {
		if (uniqueInstance == null) {
			uniqueInstance = new ComponenteTopico(mainView);
		}
		return uniqueInstance;
	}		
	
	private ComponenteTopico(MainView mainView) {		
		
		this.mainView=mainView;
		
		String strImageAddress = "images/type_list_128.png";
		
		String strTitle = txtConstants.professorAmbienteTopico();		
		String strText = txtConstants.professorAmbienteTopicoCompText1();
		strText+=txtConstants.professorAmbienteTopicoCompText2();
		strText+=txtConstants.professorAmbienteTopicoCompText3();
		
		MainViewComponent mainViewComponent = new MainViewComponent(strTitle, strImageAddress, strText);	
		mainViewComponent.flexTable.addClickHandler(new addClickHandler());		
	
		initWidget(mainViewComponent);

	}
	
	private class addClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_TOPICO);
		}		
	}

	public MainView getMainView() {
		return mainView;
	}

	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}

}
