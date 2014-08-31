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

public class ComponenteCursoAdmin extends Composite {
	
	VerticalPanel vPanelMain = new VerticalPanel(); 
	VerticalPanel vPanel1 = new VerticalPanel(); 
	VerticalPanel vPanel2 = new VerticalPanel(); 
	MainView mainView;
	
    private static ComponenteCursoAdmin uniqueInstance;
	
	public static ComponenteCursoAdmin getInstance(MainView mainView){
		if(uniqueInstance==null){
			uniqueInstance = new ComponenteCursoAdmin(mainView);
		}
		return uniqueInstance;
	}
	
	private ComponenteCursoAdmin(MainView mainView) {	
		
		TextConstants txtConstants = GWT.create(TextConstants.class);
		
		this.mainView=mainView;

		
		String strTitle = txtConstants.cursoAdmin();
		String strImageAddress = "images/address_book_new.128.128.png";
		String strText = txtConstants.cursoAdminCompText1();
		strText+=txtConstants.cursoAdminCompText2();
		strText+=txtConstants.cursoAdminCompText3();
		
		MainViewComponent mainViewComponent = new MainViewComponent(strTitle, strImageAddress, strText);	
		mainViewComponent.flexTable.addClickHandler(new addClickHandler());		
	
		initWidget(mainViewComponent);

	}
	

	
	private class addClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			//mainView.openCadastroCurso();
			MainMenu.isFirstEventFire=true;
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_CURSO_ADMIN);			
		}		
	}
	
	
	
	
	

	
	
	

}
