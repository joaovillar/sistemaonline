package com.jornada.client.ambiente.pais;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.jornada.client.MainMenu;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;


public class ComponenteVisualizarCursoPais extends Composite{

	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	public ComponenteVisualizarCursoPais(){
		
		String strTitle = txtConstants.paisAmbienteHierarquia();
		
		String strImageAddress = "images/catalog-icon-128.png";
		
		String strText = txtConstants.paisAmbienteHierarquiaCompText1();
		strText+=txtConstants.paisAmbienteHierarquiaCompText2();

		
		MainViewComponent mainViewComp = new MainViewComponent(strTitle,strImageAddress,strText);
		mainViewComp.flexTable.addClickHandler(new addClickHandler());		
		
		initWidget(mainViewComp);	
		
		
	}
	
	private class addClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {

			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_HIERARQUIA);
			
		}
		
	}	


}
