package com.jornada.client.ambiente.coordenador.relatorio;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;
//import com.google.gwt.dom.client.Element;

public class TelaInicialRelatorio extends Composite {
	
//	private TabLayoutPanel tabLayoutPanel;

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=500;
	
	private BoletimSala boletimSala;

	
	TextConstants txtConstants;

	@SuppressWarnings("unused")
	private MainView mainView;
	
	private static TelaInicialRelatorio uniqueInstance;
	public static TelaInicialRelatorio getInstance(MainView mainView){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialRelatorio(mainView);
		}else{
		    uniqueInstance.boletimSala.updateClientData();
		}
		
		return uniqueInstance;
	}		

	private TelaInicialRelatorio(MainView mainView) {
		
		this.mainView = mainView;
		
		txtConstants = GWT.create(TextConstants.class);
		
		boletimSala = BoletimSala.getInstance(this);		

		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);	
		tabLayoutPanel.getElement().getStyle().setMarginBottom(10.0, Unit.PX);
		tabLayoutPanel.setHeight(Integer.toString(TelaInicialRelatorio.intHeightTable)+"px");
		tabLayoutPanel.setAnimationDuration(500);
		tabLayoutPanel.setAnimationVertical(true);

		
	
		tabLayoutPanel.add(boletimSala, new MpHeaderWidget(txtConstants.relatorioBoletimPorPeriodo(), ""));

		initWidget(tabLayoutPanel);	
		
	}
	

	


}
