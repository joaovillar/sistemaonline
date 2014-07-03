package com.jornada.client.ambiente.aluno;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.jornada.client.MainMenu;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;



public class ComponenteNotasAluno extends Composite{

	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
    private static ComponenteNotasAluno uniqueInstance;
	
	public static ComponenteNotasAluno getInstance(){
		if(uniqueInstance==null){
			uniqueInstance = new ComponenteNotasAluno();
		}
		return uniqueInstance;
	}	
	
	private ComponenteNotasAluno(){
		
		String strTitle = txtConstants.alunoAmbienteNotas();
		
		String strImageAddress = "images/chart-icon.png";
		
		String strText = txtConstants.alunoAmbienteNotasCompText1();
		strText+=txtConstants.alunoAmbienteNotasCompText2();
		strText+=txtConstants.alunoAmbienteNotasCompText3();
		
		MainViewComponent mainViewComp = new MainViewComponent(strTitle,strImageAddress,strText);
		mainViewComp.flexTable.addClickHandler(new addClickHandler());		
		
		initWidget(mainViewComp);	
		
		
	}
	
	private class addClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {

			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_NOTA);
			
		}
		
	}	


}
