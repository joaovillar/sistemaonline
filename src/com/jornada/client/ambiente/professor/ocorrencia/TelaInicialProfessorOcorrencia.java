package com.jornada.client.ambiente.professor.ocorrencia;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;

public class TelaInicialProfessorOcorrencia extends Composite{
	
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	public  static final int intWidthTable=1400;
	public static final int intHeightTable=600;
	
	private AdicionarOcorrencia adicionarOcorrencia;
	private EditarOcorrencia editarOcorrencia;
	
	private MainView mainView;

	
	private static TelaInicialProfessorOcorrencia uniqueInstance;
	public static TelaInicialProfessorOcorrencia getInstance(MainView mainView){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialProfessorOcorrencia(mainView);
		}else{
			uniqueInstance.adicionarOcorrencia.updateClientData();
			uniqueInstance.editarOcorrencia.updateClientData();
		}
		
		return uniqueInstance;
	}		

	private TelaInicialProfessorOcorrencia(MainView mainView) {
		
		this.mainView = mainView;
		
		
		adicionarOcorrencia = new AdicionarOcorrencia(this, EnumOcorrencia.ADICIONAR);
		editarOcorrencia = new EditarOcorrencia(this);
		
		// StackLayoutPanel stackPanel = new StackLayoutPanel(Unit.EM);
		TabLayoutPanel stackPanel = new TabLayoutPanel(2.5, Unit.EM);		
		stackPanel.setPixelSize(intWidthTable+50, intHeightTable);
		stackPanel.setAnimationDuration(500);
		stackPanel.setAnimationVertical(true);

		
		stackPanel.add(adicionarOcorrencia, new MpHeaderWidget(txtConstants.ocorrenciaAdicionarNovaOcorrência(), "images/plus-circle.png"));
		stackPanel.add(editarOcorrencia, new MpHeaderWidget(txtConstants.ocorrenciaEditar(), "images/comment_edit.png"));				
//		stackPanel.add(associarCursoAluno, createHeaderWidget("Adicionar Aluno ao Curso", "images/user1_add2_16.png"));
		
		
		VerticalPanel verticalPanelPage = new VerticalPanel();		
		verticalPanelPage.add(stackPanel);
		verticalPanelPage.add(new InlineHTML("&nbsp;"));
				
     	initWidget(verticalPanelPage);
		
	}
	
	
	
	
	public MainView getMainView() {
		return mainView;
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

}
