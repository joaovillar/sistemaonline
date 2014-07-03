
package com.jornada.client.ambiente.professor.nota;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;


public class TelaInicialNota extends Composite{
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=500;
	

	private EditarNotaPorConteudoProgramatico editarNotaPorConteudoProgramatico;
	private MainView mainView;

	
	private static TelaInicialNota uniqueInstance;
	public static TelaInicialNota getInstance(MainView mainView){		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialNota(mainView);
		}else{
			uniqueInstance.editarNotaPorConteudoProgramatico.updateClientData();
		}
		return uniqueInstance;
	}		

	private TelaInicialNota(MainView mainView) {
		
		this.mainView = mainView;
		
		editarNotaPorConteudoProgramatico =  new EditarNotaPorConteudoProgramatico(this);
//		editarNotaPorAluno = new EditarNotaPorAluno(this);
		
		TabLayoutPanel stackPanel = new TabLayoutPanel(2.5, Unit.EM);		
		stackPanel.setPixelSize(intWidthTable+50, intHeightTable);
		stackPanel.setAnimationDuration(500);
		stackPanel.setAnimationVertical(true);
		
		stackPanel.add(editarNotaPorConteudoProgramatico, new MpHeaderWidget(txtConstants.notaEditarNotasConteudoProgramatico(), "images/plus-circle.png"));
//		stackPanel.add(editarNotaPorAluno, createHeaderWidget("Editar Notas por Aluno", "images/comment_edit.png"));		
		
		
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
	
//	protected void populateGrid(){
//		editarNota.populateGridConteudoProgramatico();
//	}
	
	
	
}




