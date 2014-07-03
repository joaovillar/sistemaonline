package com.jornada.client.ambiente.aluno;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.jornada.client.MainMenu;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;

public class ComponenteComunicadoAluno extends Composite {


	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	private static ComponenteComunicadoAluno uniqueInstance;
	
	
	public static ComponenteComunicadoAluno getInstance(){		
		if(uniqueInstance==null){
			uniqueInstance = new ComponenteComunicadoAluno();
		}		
		return uniqueInstance;
	}
	

	private ComponenteComunicadoAluno() {

		String strTitle = txtConstants.alunoAmbienteComunicado();
		String strImageAddress = "images/notes.png";
		String strText = txtConstants.alunoAmbienteComunicadoCompText1();
		strText += txtConstants.alunoAmbienteComunicadoCompText2();

		MainViewComponent mainViewComp = new MainViewComponent(strTitle,strImageAddress, strText);
		mainViewComp.flexTable.addClickHandler(new addClickHandler());		

		initWidget(mainViewComp);

	}
	
	private class addClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {

			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_COMUNICADO);
			
		}
		
	}	

}
