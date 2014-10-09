
package com.jornada.client.ambiente.coordenador.disciplina;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;


public class TelaInicialDisciplina extends Composite{
	

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=500;
	
	private AdicionarDisciplina adicionarDisciplina;
	private EditarDisciplina editarDisciplina;
//	private AssociarProfessorDisciplina associarProfessorDisciplina;
	private AdicionarProfessorDisciplina adicionarProfessorDisciplina;

	private static TelaInicialDisciplina uniqueInstance;
	public static TelaInicialDisciplina getInstance(){		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialDisciplina();
		}
		else{
			uniqueInstance.adicionarDisciplina.updateClientData();
			uniqueInstance.editarDisciplina.updateClientData();
//			uniqueInstance.associarProfessorDisciplina.updateClientData();
			uniqueInstance.adicionarProfessorDisciplina.updateClientData();
			
		}
		return uniqueInstance;
	}	

	private TelaInicialDisciplina() {
		
		TextConstants txtConstants = GWT.create(TextConstants.class);
		
		 adicionarDisciplina =  new AdicionarDisciplina(this);
		 editarDisciplina = new EditarDisciplina(this);
//		 associarProfessorDisciplina = new AssociarProfessorDisciplina();
		 adicionarProfessorDisciplina = AdicionarProfessorDisciplina.getInstance(this);
		
		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);		
		tabLayoutPanel.setHeight(Integer.toString(intHeightTable)+"px");
		
		tabLayoutPanel.setAnimationDuration(500);
		tabLayoutPanel.setAnimationVertical(true);
		
		tabLayoutPanel.add(adicionarDisciplina, new MpHeaderWidget(txtConstants.disciplinaAdicionar(), "images/plus-circle.png"));
		tabLayoutPanel.add(editarDisciplina, new MpHeaderWidget(txtConstants.disciplinaEditar(), "images/comment_edit.png"));
//		tabLayoutPanel.add(associarProfessorDisciplina, new MpHeaderWidget(txtConstants.disciplinaAdicionarProfessor(), "images/user1_add2_16.png"));
		tabLayoutPanel.add(adicionarProfessorDisciplina, new MpHeaderWidget(txtConstants.disciplinaAdicionarProfessor(), "images/user1_add2_16.png"));
		
     	initWidget(tabLayoutPanel);
		
	}

//	public AssociarProfessorDisciplina getAssociarProfessorDisciplina() {
//		return associarProfessorDisciplina;
//	}
	
    public AdicionarProfessorDisciplina getAdicionarProfessorDisciplina() {
        return adicionarProfessorDisciplina;
    }

	public EditarDisciplina getEditarDisciplina() {
		return editarDisciplina;
	}
	
	
}




