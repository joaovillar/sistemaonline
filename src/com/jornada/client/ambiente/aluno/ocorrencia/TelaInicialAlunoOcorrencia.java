package com.jornada.client.ambiente.aluno.ocorrencia;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;

public class TelaInicialAlunoOcorrencia extends Composite{	
	
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
//	private VerticalPanel verticalPanelPage;
//	VerticalPanel vPanelBody;
	private TabLayoutPanel tabLayoutPanel;

	public  static final int intWidthTable=1500;
	public static final int intHeightTable=500;
	
	private VisualizarAlunoOcorrencia visualizarAlunoOcorrencia;
	private MainView mainView;


	
	private static TelaInicialAlunoOcorrencia uniqueInstance;
	public static TelaInicialAlunoOcorrencia getInstance(MainView mainView){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialAlunoOcorrencia(mainView);
		}else{
			uniqueInstance.visualizarAlunoOcorrencia.updateClientData();
		}
		
		return uniqueInstance;
	}		

	private TelaInicialAlunoOcorrencia(MainView mainView) {
		
		this.mainView = mainView;
		
		this.visualizarAlunoOcorrencia = VisualizarAlunoOcorrencia.getInstance(this);
		
		tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);	
		tabLayoutPanel.setHeight(Integer.toString(intHeightTable)+"px");
		tabLayoutPanel.setAnimationDuration(500);
		tabLayoutPanel.setAnimationVertical(true);
		
		tabLayoutPanel.add(visualizarAlunoOcorrencia, new MpHeaderWidget(txtConstants.ocorrenciaPorAluno(), "images/elementary_school_16.png"));

				
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
//		
//		hPanel.add(new Image(image));
//		hPanel.add(new InlineHTML("&nbsp;"));
//		Label headerText = new Label(text);
//		headerText.setStyleName("gwt-TabLayoutPanel");
//		hPanel.add(headerText);
//		return new SimplePanel(hPanel);
//	}	


}
