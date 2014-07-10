package com.jornada.client.ambiente.pais;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.jornada.client.MainMenu;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;



public class ComponenteDiarioPais extends Composite{

	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
    private static ComponenteDiarioPais uniqueInstance;
	
	public static ComponenteDiarioPais getInstance(){
		if(uniqueInstance==null){
			uniqueInstance = new ComponenteDiarioPais();
		}
		return uniqueInstance;
	}	
	
	private ComponenteDiarioPais(){
		
		String strTitle = txtConstants.presencaAmbienteDiario();
		
		String strImageAddress = "images/programs_group_ii.128.png";
		
		String strText = txtConstants.presencaAmbienteDiarioAcompanhamento();
		strText+=txtConstants.presencaAmbienteDiarioPresenca();
		strText+=txtConstants.presencaAmbienteDiarioConteudo();
		
		MainViewComponent mainViewComp = new MainViewComponent(strTitle,strImageAddress,strText);
		mainViewComp.flexTable.addClickHandler(new addClickHandler());		
		
		initWidget(mainViewComp);	
		
		
	}
	
	private class addClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			MainMenu.isFirstEventFire=true;
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_DIARIO);			
		}		
	}	


}
