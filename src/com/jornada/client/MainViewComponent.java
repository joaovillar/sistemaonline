package com.jornada.client;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.widgets.table.FlexTableHelper;
import com.jornada.client.classes.widgets.table.MpFlexTable;

public class MainViewComponent extends Composite {
	

	public MpFlexTable flexTable;
	

	public MainViewComponent(String strTitle, String strImageAddress, String strText){	
		

		flexTable = new MpFlexTable();
		flexTable.setStyleName("fundo_tabela_componente");
//		flexTable.setStyleName("fundo_tabela_componente_over");   
		initWidget(flexTable);
		
		flexTable.setSize("350px", "150px");		
		
		flexTable.addMouseOverHandler(new mouseOverImageHandler());
		flexTable.addMouseOutHandler(new mouseOutImageHandler());
		flexTable.addMouseDownHandler(new mouseDownHandler());
		flexTable.addMouseUpHandler(new mouseUpHandler());
		
		
		Label lblTitle = new Label(strTitle);
		lblTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblTitle.setStyleName("titulo_tabela");
		flexTable.setWidget(0, 0, lblTitle);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		Image image = new Image(strImageAddress);
		image.setSize("128px", "128px");
		flexTable.setWidget(1, 0, image);
		flexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
//		flexTable.getFlexCellFormatter().setColSpan(0, 0, 0);
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 1);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		flexTable.getFlexCellFormatter().setRowSpan(1, 0, 1);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		flexTable.setWidget(1, 1, verticalPanel);
		verticalPanel.setWidth("247px");
		
		Label lbl1 = new Label(strText);
		lbl1.getElement().getStyle().setProperty("whiteSpace", "pre");
		lbl1.setStyleName("label_comum");
		verticalPanel.add(lbl1);
		

		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		FlexTableHelper.fixRowSpan(flexTable);
	}
	
	
	private class mouseOverImageHandler implements MouseOverHandler {
		@Override
		public void onMouseOver(MouseOverEvent event) {			
			flexTable.setStyleName("fundo_tabela_componente_over");
		}
	}	
	private class mouseOutImageHandler implements MouseOutHandler{
		@Override
		public void onMouseOut(MouseOutEvent event) {
			flexTable.setStyleName("fundo_tabela_componente");
//		    flexTable.setStyleName("fundo_tabela_componente_over");   
		}		
	}	
	private class mouseDownHandler implements MouseDownHandler {
		@Override
		public void onMouseDown(MouseDownEvent event) {
			flexTable.setStyleName("fundo_tabela_componente_down");
		}
	}	
	private class mouseUpHandler implements MouseUpHandler{
		@Override
		public void onMouseUp(MouseUpEvent event) {
			flexTable.setStyleName("fundo_tabela_componente_over");			
		}		
	}	

}
