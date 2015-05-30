
package com.jornada.client.ambiente.pais.presenca;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.jornada.client.MainView;
import com.jornada.client.ambiente.aluno.presenca.VisualizarPresencaAluno;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;


public class TelaInicialPresencaAlunoPais extends Composite{
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=550;
	
//	private VisualizarPresencaAlunoPais visualizarPresencaAlunoPais;
	private VisualizarPresencaAluno visualizarPresencaAluno;
	private MainView mainView;

	
	private static TelaInicialPresencaAlunoPais uniqueInstance;
	public static TelaInicialPresencaAlunoPais getInstance(MainView mainView){		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialPresencaAlunoPais(mainView);
		}else{
			uniqueInstance.visualizarPresencaAluno.updateClientData();
		}
		return uniqueInstance;
	}		

	private TelaInicialPresencaAlunoPais(MainView mainView) {
		
		this.mainView = mainView;
		
		visualizarPresencaAluno =  new VisualizarPresencaAluno(this);
		
		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);		
//		tabLayoutPanel.setPixelSize(intWidthTable+50, intHeightTable);
		tabLayoutPanel.setHeight(Integer.toString(intHeightTable)+"px");
		tabLayoutPanel.setAnimationDuration(500);
		tabLayoutPanel.setAnimationVertical(true);
		
		tabLayoutPanel.add(visualizarPresencaAluno, new MpHeaderWidget(txtConstants.presencaVisualizarLista(), "images/programs_group_ii.16.png"));		
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




