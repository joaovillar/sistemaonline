package com.jornada.client.ambiente.professor.comunicado;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;

public class TelaInicialComunicadoProfessor extends Composite{	
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	private VerticalPanel verticalPanelPage;
	VerticalPanel vPanelBody;
	private TabLayoutPanel stackPanel;

	public  static final int intWidthTable=1500;
	public static final int intHeightTable=500;
	
	private TabelaComunicadoProfessor tabelaComunicadoProfessor;
//	private VisualizarAgenda visualizarAgenda;


	
	private static TelaInicialComunicadoProfessor uniqueInstance;
	public static TelaInicialComunicadoProfessor getInstance(){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialComunicadoProfessor();
		}
		
		return uniqueInstance;
	}		

	private TelaInicialComunicadoProfessor() {
		
		
		this.tabelaComunicadoProfessor = TabelaComunicadoProfessor.getInstance(this);
	
		
		stackPanel = new TabLayoutPanel(2.5, Unit.EM);	

		stackPanel.setPixelSize(intWidthTable+50, intHeightTable+160);
		stackPanel.setAnimationDuration(500);
		stackPanel.setAnimationVertical(true);
		
		vPanelBody = new VerticalPanel();
		
		stackPanel.add(tabelaComunicadoProfessor, new MpHeaderWidget(txtConstants.alunoAmbienteComunicado(), "images/notes_16.png"));
		
		verticalPanelPage = new VerticalPanel();		
		verticalPanelPage.add(stackPanel);
		verticalPanelPage.add(new InlineHTML("&nbsp;"));
				
     	initWidget(verticalPanelPage);
		
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
