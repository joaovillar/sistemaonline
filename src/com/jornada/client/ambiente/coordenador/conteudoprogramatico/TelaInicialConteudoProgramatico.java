package com.jornada.client.ambiente.coordenador.conteudoprogramatico;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;


public class TelaInicialConteudoProgramatico extends Composite{
	

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=500;
	
	private AdicionarConteudoProgramatico adicionarConteudoProgramatico;
	private EditarConteudoProgramatico editarConteudoProgramatico;
	
	TextConstants txtConstants;

	
	private static TelaInicialConteudoProgramatico uniqueInstance;
	public static TelaInicialConteudoProgramatico getInstance(){		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialConteudoProgramatico();
		}else{
			uniqueInstance.adicionarConteudoProgramatico.updateClientData();
			uniqueInstance.editarConteudoProgramatico.updateClientData();
		}
		
		return uniqueInstance;
	}		

	private TelaInicialConteudoProgramatico() {
		
		txtConstants = GWT.create(TextConstants.class);
		
		 adicionarConteudoProgramatico =  new AdicionarConteudoProgramatico(this);
		 editarConteudoProgramatico = new EditarConteudoProgramatico(this);
		
		TabLayoutPanel stackPanel = new TabLayoutPanel(2.5, Unit.EM);		
		stackPanel.setPixelSize(intWidthTable+50, intHeightTable);
		stackPanel.setAnimationDuration(500);
		stackPanel.setAnimationVertical(true);
		
		stackPanel.add(adicionarConteudoProgramatico, new MpHeaderWidget(txtConstants.conteudoProgramaticoAdicionar(), "images/plus-circle.png"));
		stackPanel.add(editarConteudoProgramatico, new MpHeaderWidget(txtConstants.conteudoProgramaticoEditar(), "images/comment_edit.png"));		
		
		
		VerticalPanel verticalPanelPage = new VerticalPanel();		
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
//		hPanel.add(new Image(image));
//		Label headerText = new Label(text);
//		headerText.setStyleName("gwt-TabLayoutPanel");
//		hPanel.add(headerText);
//		return new SimplePanel(hPanel);
//	}

	public AdicionarConteudoProgramatico getAdicionarConteudoProgramatico() {
		return adicionarConteudoProgramatico;
	}

	public EditarConteudoProgramatico getEditarConteudoProgramatico() {
		return editarConteudoProgramatico;
	}
	
//	protected void populateGrid(){
//		editarConteudoProgramatico.populateGridConteudoProgramatico();
//	}
//	
	
	
	
	
}




