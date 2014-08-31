
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
	private AssociarProfessorDisciplina associarProfessorDisciplina;

	private static TelaInicialDisciplina uniqueInstance;
	public static TelaInicialDisciplina getInstance(){		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialDisciplina();
		}
		else{
			uniqueInstance.adicionarDisciplina.updateClientData();
			uniqueInstance.editarDisciplina.updateClientData();
			uniqueInstance.associarProfessorDisciplina.updateClientData();
		}
		return uniqueInstance;
	}	

	private TelaInicialDisciplina() {
		
		TextConstants txtConstants = GWT.create(TextConstants.class);
		
		 adicionarDisciplina =  new AdicionarDisciplina(this);
		 editarDisciplina = new EditarDisciplina(this);
		 associarProfessorDisciplina = new AssociarProfessorDisciplina();
		
		// StackLayoutPanel stackPanel = new StackLayoutPanel(Unit.EM);
		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);		
//		stackPanel.setPixelSize(intWidthTable+50, intHeightTable);
		tabLayoutPanel.setHeight(Integer.toString(intHeightTable)+"px");
		
		tabLayoutPanel.setAnimationDuration(500);
		tabLayoutPanel.setAnimationVertical(true);
		
		tabLayoutPanel.add(adicionarDisciplina, new MpHeaderWidget(txtConstants.disciplinaAdicionar(), "images/plus-circle.png"));
		tabLayoutPanel.add(editarDisciplina, new MpHeaderWidget(txtConstants.disciplinaEditar(), "images/comment_edit.png"));
		tabLayoutPanel.add(associarProfessorDisciplina, new MpHeaderWidget(txtConstants.disciplinaAdicionarProfessor(), "images/user1_add2_16.png"));
		
		
//		VerticalPanel verticalPanelPage = new VerticalPanel();		
//		verticalPanelPage.add(tabLayoutPanel);
//		verticalPanelPage.add(new InlineHTML("&nbsp;"));
				
		
     	initWidget(tabLayoutPanel);
		
	}

	
//	private Widget createHeaderWidget(String text, String image) {
//		// Add the image and text to a horizontal panel
//		HorizontalPanel hPanel = new HorizontalPanel();
//		hPanel.setHeight("100%");
//		hPanel.setSpacing(0);
//		hPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
//		hPanel.add(new Image(image));
//		Label headerText = new Label(text);
//		headerText.setStyleName("gwt-TabLayoutPanel");
//		hPanel.add(headerText);
//		return new SimplePanel(hPanel);
//	}

	public AssociarProfessorDisciplina getAssociarProfessorDisciplina() {
		return associarProfessorDisciplina;
	}

	public EditarDisciplina getEditarDisciplina() {
		return editarDisciplina;
	}
	
	
	
//	protected void populateGrid(){
//		editarDisciplina.populateGridDisciplina();
//	}
	
	
	
	
	
	
	
}




