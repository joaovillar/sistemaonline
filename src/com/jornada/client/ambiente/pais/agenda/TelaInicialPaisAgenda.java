package com.jornada.client.ambiente.pais.agenda;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;

public class TelaInicialPaisAgenda extends Composite{	
	
//	private VerticalPanel verticalPanelPage;
//	VerticalPanel vPanelBody;
	private TabLayoutPanel tabLayoutPanel;

	public  static final int intWidthTable=1500;
	public static final int intHeightTable=500;
	
	private VisualizarPaisAvaliacao visualizarPaisAvaliacao;
	private VisualizarPaisAgenda visualizarPaisAgenda;
	
	private MainView mainView;

	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	private static TelaInicialPaisAgenda uniqueInstance;
	public static TelaInicialPaisAgenda getInstance(MainView mainView){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialPaisAgenda(mainView);
		}else{
			uniqueInstance.visualizarPaisAgenda.updateClientData();
			uniqueInstance.visualizarPaisAvaliacao.updateClientData();
		}
		
		return uniqueInstance;
	}		

	private TelaInicialPaisAgenda(MainView mainView) {
		
		this.mainView = mainView;
		
		this.visualizarPaisAvaliacao = VisualizarPaisAvaliacao.getInstance(this);
		this.visualizarPaisAgenda = VisualizarPaisAgenda.getInstance(this);
		
		tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);	

//		tabLayoutPanel.setPixelSize(intWidthTable+50, intHeightTable+160);
		tabLayoutPanel.setHeight(Integer.toString(intHeightTable+160)+"px");
		tabLayoutPanel.setAnimationDuration(500);
		tabLayoutPanel.setAnimationVertical(true);
		
//		vPanelBody = new VerticalPanel();
		
		tabLayoutPanel.add(visualizarPaisAgenda, new MpHeaderWidget(txtConstants.agendaCalendario(), "images/calendar.png"));
		tabLayoutPanel.add(visualizarPaisAvaliacao, new MpHeaderWidget(txtConstants.agendaAtividades(), "images/application_view_detail.png"));
		
//		verticalPanelPage = new VerticalPanel();		
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
//		
//		hPanel.add(new Image(image));
//		hPanel.add(new InlineHTML("&nbsp;"));
//		Label headerText = new Label(text);
//		headerText.setStyleName("gwt-TabLayoutPanel");
//		hPanel.add(headerText);
//		return new SimplePanel(hPanel);
//	}

	public MainView getMainView() {
		return mainView;
	}	
	
	


}
