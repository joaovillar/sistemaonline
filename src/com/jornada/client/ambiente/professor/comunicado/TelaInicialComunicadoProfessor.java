package com.jornada.client.ambiente.professor.comunicado;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;

public class TelaInicialComunicadoProfessor extends Composite{	
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
//	private VerticalPanel verticalPanelPage;
//	VerticalPanel vPanelBody;
//	private TabLayoutPanel tabLayoutPanel;

	public  static final int intWidthTable=1500;
	public static final int intHeightTable=500;
	
	private TabelaComunicadoProfessor tabelaComunicadoProfessor;
//	private TabelaComunicadoProfessor visualizarAgenda;
	
	private MainView mainView;


	
	private static TelaInicialComunicadoProfessor uniqueInstance;
	public static TelaInicialComunicadoProfessor getInstance(MainView mainView){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialComunicadoProfessor(mainView);
		}
		
		return uniqueInstance;
	}		

	private TelaInicialComunicadoProfessor(MainView mainView) {
		
		this.mainView = mainView;
		
		this.tabelaComunicadoProfessor = TabelaComunicadoProfessor.getInstance(this);
//		this.visualizarAgenda = TabelaComunicadoProfessor.getInstance(this);
	
		
//		tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);	
		TabPanel tabLayoutPanel = new TabPanel(); 
		tabLayoutPanel.setWidth("99%");

//		tabLayoutPanel.setHeight(Integer.toString(intHeightTable+160)+"px");
//		tabLayoutPanel.setAnimationDuration(500);
//		tabLayoutPanel.setAnimationVertical(true);
		
		
		tabLayoutPanel.add(tabelaComunicadoProfessor, new MpHeaderWidget(txtConstants.alunoAmbienteComunicado(), "images/notes_16.png"));
//		tabLayoutPanel.add(visualizarAgenda, new MpHeaderWidget(txtConstants.alunoAmbienteComunicado(), "images/notes_16.png"));
	    tabLayoutPanel.selectTab(0);
				
     	initWidget(tabLayoutPanel);
		
	}
	
	public MainView getMainView(){
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
