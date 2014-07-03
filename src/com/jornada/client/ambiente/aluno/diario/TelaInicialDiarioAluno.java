
package com.jornada.client.ambiente.aluno.diario;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;


public class TelaInicialDiarioAluno extends Composite{
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=550;
	


	private VisualizarDiarioAluno visualizarDiarioAluno;
	private MainView mainView;

	
	private static TelaInicialDiarioAluno uniqueInstance;
	public static TelaInicialDiarioAluno getInstance(MainView mainView){		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialDiarioAluno(mainView);
		}else{
			uniqueInstance.visualizarDiarioAluno.updateClientData();
		}
		return uniqueInstance;
	}		

	private TelaInicialDiarioAluno(MainView mainView) {
		
		this.mainView = mainView;
		
		visualizarDiarioAluno =  new VisualizarDiarioAluno(this);
		
		TabLayoutPanel stackPanel = new TabLayoutPanel(2.5, Unit.EM);		
		stackPanel.setPixelSize(intWidthTable+50, intHeightTable);
		stackPanel.setAnimationDuration(500);
		stackPanel.setAnimationVertical(true);
		
		stackPanel.add(visualizarDiarioAluno, new MpHeaderWidget(txtConstants.presencaVisualizarLista(), "images/programs_group_ii.16.png"));		
		
		
		VerticalPanel verticalPanelPage = new VerticalPanel();		
		verticalPanelPage.add(stackPanel);
		verticalPanelPage.add(new InlineHTML("&nbsp;"));
				
     	initWidget(verticalPanelPage);
		
	}
	
	public MainView getMainView() {
		return mainView;
	}

	
}




