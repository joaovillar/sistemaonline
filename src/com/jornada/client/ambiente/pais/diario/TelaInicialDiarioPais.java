
package com.jornada.client.ambiente.pais.diario;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;


public class TelaInicialDiarioPais extends Composite{
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=550;
	
	private VisualizarDiarioPais visualizarDiarioPais;
	private MainView mainView;

	
	private static TelaInicialDiarioPais uniqueInstance;
	public static TelaInicialDiarioPais getInstance(MainView mainView){		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialDiarioPais(mainView);
		}else{
			uniqueInstance.visualizarDiarioPais.updateClientData();
		}
		return uniqueInstance;
	}		

	private TelaInicialDiarioPais(MainView mainView) {
		
		this.mainView = mainView;
		
		visualizarDiarioPais =  new VisualizarDiarioPais(this);
		
		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);		
//		tabLayoutPanel.setPixelSize(intWidthTable+50, intHeightTable);
		tabLayoutPanel.setHeight(Integer.toString(intHeightTable)+"px");
		tabLayoutPanel.setAnimationDuration(500);
		tabLayoutPanel.setAnimationVertical(true);
		
		tabLayoutPanel.add(visualizarDiarioPais, new MpHeaderWidget(txtConstants.presencaVisualizarLista(), "images/programs_group_ii.16.png"));		
//		
//		
//		VerticalPanel verticalPanelPage = new VerticalPanel();		
//		verticalPanelPage.add(tabLayoutPanel);
//		verticalPanelPage.add(new InlineHTML("&nbsp;"));
				
     	initWidget(tabLayoutPanel);
		
	}
	
	public MainView getMainView() {
		return mainView;
	}

	
}




