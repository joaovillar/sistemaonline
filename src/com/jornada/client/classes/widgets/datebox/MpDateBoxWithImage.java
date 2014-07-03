package com.jornada.client.classes.widgets.datebox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.datepicker.client.DateBox;
import com.jornada.client.classes.widgets.panel.MpSpacePanel;

public class MpDateBoxWithImage extends Grid{
	
	
	private DateBox date;
	private Image img;
	
	public MpDateBoxWithImage(){
		
		super(1,3);
		
		this.setCellSpacing(0);
		this.setCellPadding(0);

		
		date = new DateBox();
		date.setStyleName("design_text_boxes_date_box");
		date.getDatePicker().setYearAndMonthDropdownVisible(true);
		date.getDatePicker().setYearArrowsVisible(true);
		
		img = new Image("images/calendar-32.png");
		img.setSize("20px", "20px");
		img.setStyleName("imageCenterDateBox");
		img.addClickHandler(new ClickHandlerDate());
		
		int column=0;
		this.setWidget(0, column++, date);
		this.setWidget(0, column++, new MpSpacePanel());		
		this.setWidget(0, column++, img);	
		
		HTMLTable.CellFormatter formatter = this.getCellFormatter();		
		formatter.setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_LEFT);
		
	}
	
	
	public DateBox getDate(){
		return date;
	}
	
	private class ClickHandlerDate implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			date.showDatePicker();
		}
		
	}

}
