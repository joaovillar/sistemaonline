package com.jornada.client.classes.hierarquia;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.utility.MpUtilClient;

public class MpScrollPanelPeriodo extends ScrollPanel{

	protected static TextConstants txtConstants = GWT.create(TextConstants.class);

	public MpScrollPanelPeriodo(Periodo object){
		
		Label lblNomePeriodo = new Label(txtConstants.periodoNome());	
		Label lblNomePeriodoDB = new Label(object.getNomePeriodo());
		
		Label lblDescricaoPeriodo = new Label(txtConstants.periodoDescricao());	
		Label lblDescricaoPeriodoDB = new Label(object.getDescricao());	
		
		Label lblObjetivoPeriodo = new Label(txtConstants.periodoObjetivo());	
		Label lblObjetivoPeriodoDB = new Label(object.getObjetivo());			
		
		Label lblDataInicialPeriodo = new Label(txtConstants.periodoDataInicial());	
		Label lblDataInicialPeriodoDB = new Label(MpUtilClient.convertDateToString(object.getDataInicial()));				
		
		Label lblDataFinalPeriodo = new Label(txtConstants.periodoDataFinal());	
		Label lblDataFinalPeriodoDB = new Label(MpUtilClient.convertDateToString(object.getDataFinal()));			
		
		
		lblNomePeriodo.setStyleName("label_comum_bold_12px");
		lblNomePeriodoDB.setStyleName("label_comum");
		lblDescricaoPeriodo.setStyleName("label_comum_bold_12px");
		lblDescricaoPeriodoDB.setStyleName("label_comum");		
		lblObjetivoPeriodo.setStyleName("label_comum_bold_12px");
		lblObjetivoPeriodoDB.setStyleName("label_comum");	
		lblDataInicialPeriodo.setStyleName("label_comum_bold_12px");
		lblDataInicialPeriodoDB.setStyleName("label_comum");	
		lblDataFinalPeriodo.setStyleName("label_comum_bold_12px");
		lblDataFinalPeriodoDB.setStyleName("label_comum");		
		
		
		Grid gridData = new Grid(1,10);
		
		gridData.setCellPadding(0);
		gridData.setCellSpacing(0);
		gridData.setBorderWidth(0);
		
		int column=0;
		gridData.setWidget(0, column++, lblDataInicialPeriodo);
		gridData.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridData.setWidget(0, column++, lblDataInicialPeriodoDB);
		gridData.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridData.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridData.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridData.setWidget(0, column++, lblDataFinalPeriodo);
		gridData.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridData.setWidget(0, column++, lblDataFinalPeriodoDB);		
		
		FlexTable flexTableConteudo = new FlexTable();
		flexTableConteudo.setCellPadding(2);
		flexTableConteudo.setCellSpacing(2);	
//		flexTableConteudo.getFlexCellFormatter().setColSpan(0, 0, 2);
		flexTableConteudo.setBorderWidth(0);
//		flexTableConteudo.getColumnFormatter().setWidth(0, "150px");		
		
		int row=0;
//		flexTableConteudo.setWidget(row++, 0, vPanelTitle);
//		flexTable.setWidget(row, 0, lblNomePeriodo);flexTable.setWidget(row++, 1, lblNomePeriodoDB);
		flexTableConteudo.setWidget(row++, 0, lblDescricaoPeriodo);
		flexTableConteudo.setWidget(row++, 0, lblDescricaoPeriodoDB);
		flexTableConteudo.setWidget(row++, 0, new MpSpaceVerticalPanel());
		flexTableConteudo.setWidget(row++, 0, lblObjetivoPeriodo);
		flexTableConteudo.setWidget(row++, 0, lblObjetivoPeriodoDB);
		flexTableConteudo.setWidget(row++, 0, new MpSpaceVerticalPanel());
//		flexTableConteudo.setWidget(row++, 0, lblDataInicialPeriodo);flexTableConteudo.setWidget(row++, 0, lblDataInicialPeriodoDB);
//		flexTableConteudo.setWidget(row++, 0, lblDataFinalPeriodo);flexTableConteudo.setWidget(row++, 0, lblDataFinalPeriodoDB);
		flexTableConteudo.setWidget(row++, 0, gridData);
//		panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		
		
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize(Integer.toString(MpHierarquiaCurso.intWidthTable-200)+"px",Integer.toString(MpHierarquiaCurso.intHeightTable-30)+"px");
		scrollPanel.setAlwaysShowScrollBars(false);				
		scrollPanel.add(flexTableConteudo);	
		
		this.add(scrollPanel);
		
	}
}
