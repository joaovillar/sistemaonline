package com.jornada.client.ambiente.pais.notas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;

public class TelaInicialPaisVisualizarNotas extends Composite{
	

	public  static final int INT_WIDTH_TABLE=1500;
	public static final int INT_HEIGHT_TABLE=550;
	
	private VisualizarPaisNotasAluno visualizarPaisNotasAluno;

	private MainView mainView;
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	private static TelaInicialPaisVisualizarNotas uniqueInstance;
	
	public static TelaInicialPaisVisualizarNotas getInstance(MainView mainView){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialPaisVisualizarNotas(mainView);
		}else{
			uniqueInstance.visualizarPaisNotasAluno.updateClientData();
		}
		
		return uniqueInstance;
	}		

	private TelaInicialPaisVisualizarNotas(MainView mainView) {
		
		
		this.mainView = mainView;
		
		visualizarPaisNotasAluno = VisualizarPaisNotasAluno.getInstance(this);
//		editarNota = new EditarNota(this);
	
		
		// StackLayoutPanel stackPanel = new StackLayoutPanel(Unit.EM);
		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);		
//		tabLayoutPanel.setPixelSize(intWidthTable+50, intHeightTable);
//		tabLayoutPanel.setPixelSize(intWidthTable, intHeightTable);
		tabLayoutPanel.setHeight(Integer.toString(INT_HEIGHT_TABLE)+"px");
		tabLayoutPanel.setAnimationDuration(500);
		tabLayoutPanel.setAnimationVertical(true);

		
		tabLayoutPanel.add(visualizarPaisNotasAluno, new MpHeaderWidget(txtConstants.alunoAmbienteNotas(), "images/chart-icon_16.png"));
//		stackPanel.add(editarVisualizarNotas, createHeaderWidget("Notas por Conteúdo Programático", "images/comment_edit.png"));				

		
		
//		VerticalPanel verticalPanelPage = new VerticalPanel();		
//		verticalPanelPage.add(tabLayoutPanel);
//		verticalPanelPage.add(new InlineHTML("&nbsp;"));
				
     	initWidget(tabLayoutPanel);
		
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
