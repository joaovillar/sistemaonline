
package com.jornada.client.ambiente.coordenador.periodo;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;


public class TelaInicialPeriodo extends Composite{
	

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=500;
	
	private AdicionarPeriodo adicionarPeriodo;
	private EditarPeriodo editarPeriodo;
	
	TextConstants txtConstants;

	private static TelaInicialPeriodo uniqueInstance;
	public static TelaInicialPeriodo getInstance(){		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialPeriodo();			
		}		
		else{
			uniqueInstance.adicionarPeriodo.updateClientData();
			uniqueInstance.editarPeriodo.updateClientData();
		}
		return uniqueInstance;
	}		

	private TelaInicialPeriodo() {
		
		txtConstants = GWT.create(TextConstants.class);
		
		 adicionarPeriodo =  AdicionarPeriodo.getInstance(this);
		 editarPeriodo = EditarPeriodo.getInstance(this);
		
		// StackLayoutPanel stackPanel = new StackLayoutPanel(Unit.EM);
		TabLayoutPanel stackPanel = new TabLayoutPanel(2.5, Unit.EM);		
		stackPanel.setPixelSize(intWidthTable+50, intHeightTable);
		stackPanel.setAnimationDuration(500);
		stackPanel.setAnimationVertical(true);
		
		stackPanel.add(adicionarPeriodo, new MpHeaderWidget(txtConstants.periodoAdicionar(), "images/plus-circle.png"));
		stackPanel.add(editarPeriodo, new MpHeaderWidget(txtConstants.periodoEditar(), "images/comment_edit.png"));		
		
		
		VerticalPanel verticalPanelPage = new VerticalPanel();		
		verticalPanelPage.add(stackPanel);
		verticalPanelPage.add(new InlineHTML("&nbsp;"));
				
     	initWidget(verticalPanelPage);
		
	}


	protected void populateGrid(){
		editarPeriodo.populateGrid();
	}

	
	
}




