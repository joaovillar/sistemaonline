package com.jornada.client.classes.hierarquia;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.shared.classes.Topico;

public class MpScrollPanelTopico extends ScrollPanel{
	
	protected static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	public MpScrollPanelTopico(Topico object){
		
		Label lblNome = new Label(txtConstants.topicoNome());	
		Label lblNomeDB = new Label(object.getNome());
		
		Label lblNumeracao = new Label(txtConstants.topicoNumeracao());	
		Label lblNumeracaoDB = new Label(object.getNumeracao());				
		
		Label lblDescricao = new Label(txtConstants.topicoDescricao());	
		Label lblDescricaoDB = new Label(object.getDescricao());	
		
		Label lblObjetivo = new Label(txtConstants.topicoObjetivo());	
		Label lblObjetivoDB = new Label(object.getObjetivo());
		
		lblNome.setStyleName("label_comum_bold_12px");lblNomeDB.setStyleName("label_comum");
		lblNumeracao.setStyleName("label_comum_bold_12px");lblNumeracaoDB.setStyleName("label_comum");	
		lblDescricao.setStyleName("label_comum_bold_12px");lblDescricaoDB.setStyleName("label_comum");		
		lblObjetivo.setStyleName("label_comum_bold_12px");lblObjetivoDB.setStyleName("label_comum");	
		
		Grid gridNumeracao = new Grid(1,10);		
		gridNumeracao.setCellPadding(0);
		gridNumeracao.setCellSpacing(0);
		gridNumeracao.setBorderWidth(0);		
		int column=0;
		gridNumeracao.setWidget(0, column++, lblNumeracao);
		gridNumeracao.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridNumeracao.setWidget(0, column++, lblNumeracaoDB);			

		
		FlexTable flexTableConteudo = new FlexTable();
		flexTableConteudo.setCellPadding(2);
		flexTableConteudo.setCellSpacing(2);	
//		flexTableConteudo.getFlexCellFormatter().setColSpan(0, 0, 2);
//		flexTableConteudo.getColumnFormatter().setWidth(0, "150px");		
		
		int row=0;
//		flexTableConteudo.setWidget(row++, 0, vPanelTitle);
//		flexTable.setWidget(row, 0, lblNome);flexTable.setWidget(row++, 1, lblNomeDB);
//		flexTableConteudo.setWidget(row++, 0, lblNumeracao);
//		flexTableConteudo.setWidget(row++, 0, lblNumeracaoDB);	
		flexTableConteudo.setWidget(row++, 0, gridNumeracao);
		flexTableConteudo.setWidget(row++, 0, new MpSpaceVerticalPanel());
		flexTableConteudo.setWidget(row++, 0, lblDescricao);
		flexTableConteudo.setWidget(row++, 0, lblDescricaoDB);
		flexTableConteudo.setWidget(row++, 0, new MpSpaceVerticalPanel());
		flexTableConteudo.setWidget(row++, 0, lblObjetivo);
		flexTableConteudo.setWidget(row++, 0, lblObjetivoDB);
		flexTableConteudo.setWidget(row++, 0, new MpSpaceVerticalPanel());

		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(MpHierarquiaCurso.intWidthTable-200)+"px",Integer.toString(MpHierarquiaCurso.intHeightTable-30)+"px");
		scrollPanel.setHeight(Integer.toString(MpHierarquiaCurso.intHeightTable-30)+"px");
		scrollPanel.setWidth("100%");
		scrollPanel.setAlwaysShowScrollBars(false);				
		scrollPanel.add(flexTableConteudo);	
		
		this.setWidth("100%");
		this.add(scrollPanel);

		
	}



}
