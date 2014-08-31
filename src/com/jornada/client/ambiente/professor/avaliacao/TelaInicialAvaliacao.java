
package com.jornada.client.ambiente.professor.avaliacao;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;


public class TelaInicialAvaliacao extends Composite{
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=500;
	
	private AdicionarAvaliacao adicionarAvaliacao;
	private EditarAvaliacao editarAvaliacao;

	private MainView mainView;
	
	private static TelaInicialAvaliacao uniqueInstance;
	public static TelaInicialAvaliacao getInstance(MainView mainView){		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialAvaliacao(mainView);
		}else{
			uniqueInstance.adicionarAvaliacao.updateClientData();
			uniqueInstance.editarAvaliacao.updateClientData();
		}
		
		return uniqueInstance;
	}		

	private TelaInicialAvaliacao(MainView mainView) {
		
		this.mainView = mainView;
		
		 adicionarAvaliacao =  new AdicionarAvaliacao(this);
		 editarAvaliacao = new EditarAvaliacao(this);
		
		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);		
//		tabLayoutPanel.setPixelSize(intWidthTable+50, intHeightTable);
		tabLayoutPanel.setHeight(Integer.toString(intHeightTable)+"px");
		tabLayoutPanel.setAnimationDuration(500);
		tabLayoutPanel.setAnimationVertical(true);
		
		tabLayoutPanel.add(adicionarAvaliacao, new MpHeaderWidget(txtConstants.avaliacaoAdicionar(), "images/plus-circle.png"));
		tabLayoutPanel.add(editarAvaliacao, new MpHeaderWidget(txtConstants.avaliacaoEditar(), "images/comment_edit.png"));		
		
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
	
	protected void populateGrid(){
		editarAvaliacao.populateGridAvaliacao();
	}
	
	
	
}




