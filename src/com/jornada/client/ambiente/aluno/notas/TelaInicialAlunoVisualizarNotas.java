package com.jornada.client.ambiente.aluno.notas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;

public class TelaInicialAlunoVisualizarNotas extends Composite{
	

	public  static final int intWidthTable=1500;
	public static final int intHeightTable=550;
	
	private VisualizarAlunoNotasAluno visualizarAlunoNotasAluno;
//	private EditarNota editarNota;
	
	private MainView mainView;
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	private static TelaInicialAlunoVisualizarNotas uniqueInstance;
	public static TelaInicialAlunoVisualizarNotas getInstance(MainView mainView){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialAlunoVisualizarNotas(mainView);
		}
		else{
			uniqueInstance.visualizarAlunoNotasAluno.updateClientData();
		}
		
		return uniqueInstance;
	}		

	private TelaInicialAlunoVisualizarNotas(MainView mainView) {
		
		this.mainView = mainView;
		
//		visualizarAlunoNotasAluno = new VisualizarAlunoNotasAluno(this);
		visualizarAlunoNotasAluno = VisualizarAlunoNotasAluno.getInstance(this);
		

		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);	
		tabLayoutPanel.getElement().getStyle().setMarginBottom(10.0, Unit.PX);
		tabLayoutPanel.getElement().getStyle().setBorderWidth(10.0, Unit.PX);
		
		tabLayoutPanel.setHeight(Integer.toString(intHeightTable)+"px");
		tabLayoutPanel.setAnimationDuration(500);
		tabLayoutPanel.setAnimationVertical(true);
		
		tabLayoutPanel.add(visualizarAlunoNotasAluno, new MpHeaderWidget(txtConstants.alunoAmbienteNotas(), "images/chart-icon_16.png"));

	
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
