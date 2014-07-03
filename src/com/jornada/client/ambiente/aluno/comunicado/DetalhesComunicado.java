package com.jornada.client.ambiente.aluno.comunicado;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.shared.classes.Comunicado;
import com.reveregroup.gwt.imagepreloader.FitImage;

public class DetalhesComunicado extends VerticalPanel{
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	public DetalhesComunicado(Comunicado object) {

		this.setWidth(Integer.toString(TelaInicialAlunoComunicado.intWidthTable)+ "px");		
		
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
		vPanelComunicado.setWidth(Integer.toString(TelaInicialAlunoComunicado.intWidthTable)+ "px");		
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
		vPanelAssunto.setWidth(Integer.toString(TelaInicialAlunoComunicado.intWidthTable-550)+ "px");		
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
		flexTable.setWidget(1, 0, img);
		flexTable.setWidget(1, 1, grid);
		
		flexTable.getFlexCellFormatter().setWidth(1,0,"15%"); 
		flexTable.getFlexCellFormatter().setVerticalAlignment(1, 1, ALIGN_TOP);
		
		this.add(flexTable);
	}
	


}
