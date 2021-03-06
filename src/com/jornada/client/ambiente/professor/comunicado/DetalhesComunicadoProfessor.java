package com.jornada.client.ambiente.professor.comunicado;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.shared.classes.Comunicado;
import com.reveregroup.gwt.imagepreloader.FitImage;

public class DetalhesComunicadoProfessor extends VerticalPanel{
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	public DetalhesComunicadoProfessor(Comunicado object) {

		this.setWidth(Integer.toString(TelaInicialComunicadoProfessor.intWidthTable)+ "px");		
		
		FlexTable flexTable = new FlexTable();		
		flexTable.setCellPadding(2);
		flexTable.setCellSpacing(2);
//		flexTable.setBorderWidth(2);
		flexTable.setWidth("100%");
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		
		FitImage img = new FitImage("images/download/"+object.getNomeImagem());
		img.setFixedWidth(512);

		Label lblComunicado = new Label(txtConstants.comunicado().toUpperCase());
		lblComunicado.setHorizontalAlignment(ALIGN_CENTER);
		
		
		VerticalPanel vPanelComunicado = new VerticalPanel();
		vPanelComunicado.setStyleName("designComunicado");
//		vPanelComunicado.setWidth(Integer.toString(TelaInicialComunicadoProfessor.intWidthTable)+ "px");		
		vPanelComunicado.setWidth("100%");
		vPanelComunicado.add(lblComunicado);
		
		
		
		FlexTable grid = new FlexTable();
		grid.setCellPadding(2);
		grid.setCellSpacing(2);
//		grid.setBorderWidth(2);
		

		
		Label lblAssunto  = new Label(object.getAssunto());
		Label lblData  = new Label(txtConstants.comunicadoData()+": "+object.getData());
		Label lblHora  = new Label(txtConstants.comunicadoHora()+": "+object.getHora());
		
		lblAssunto.setHorizontalAlignment(ALIGN_CENTER);
		lblData.setHorizontalAlignment(ALIGN_LEFT);
		lblHora.setHorizontalAlignment(ALIGN_LEFT);
		
		lblAssunto.setStyleName("label_comum_bold");
		
		VerticalPanel vPanelAssunto = new VerticalPanel();
//		vPanelAssunto.setStyleName("designLogin");
//		vPanelAssunto.setWidth(Integer.toString(TelaInicialComunicadoProfessor.intWidthTable-550)+ "px");		
		vPanelAssunto.setWidth("100%");
		vPanelAssunto.add(lblAssunto);
		
		int countGrid = 0;
		grid.setWidget(countGrid++, 0, new InlineHTML("&nbsp;"));
		grid.setWidget(countGrid++, 0, vPanelAssunto);
		grid.setWidget(countGrid++, 0, new InlineHTML("&nbsp;"));
		grid.setWidget(countGrid++, 0, lblData);
//		grid.setWidget(countGrid++, 0, new InlineHTML("&nbsp;"));
		grid.setWidget(countGrid++, 0, lblHora);
		grid.setWidget(countGrid++, 0, new InlineHTML("&nbsp;"));
		grid.setWidget(countGrid++, 0, new InlineHTML(object.getDescricao()));
		
//		grid.getFlexCellFormatter().setColSpan(0, 0, 2);
//		grid.getFlexCellFormatter().setColSpan(8, 0, 2);
		
		
		flexTable.setWidget(0, 0, vPanelComunicado);
		flexTable.setWidth("100%");
//		if(object.getNomeImagem()!=null)flexTable.setWidget(1, 0, img);
		int column=0;
        if (object.getNomeImagem() != null) {
            flexTable.setWidget(1, column, img);
            flexTable.getFlexCellFormatter().setWidth(1,column++,"15%");
        }	
		flexTable.setWidget(1, column, grid);
		
		flexTable.getFlexCellFormatter().setVerticalAlignment(1, column, ALIGN_TOP);
		
        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.setHeight(Integer.toString(TelaInicialComunicadoProfessor.intHeightTable + 90) + "px");
        scrollPanel.setAlwaysShowScrollBars(false);
        scrollPanel.add(flexTable);
		
		this.setWidth("100%");
		this.add(scrollPanel);
//		this.add(flexTable);
	}
	


}
