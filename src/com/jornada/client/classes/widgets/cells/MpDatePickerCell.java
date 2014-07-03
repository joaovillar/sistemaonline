package com.jornada.client.classes.widgets.cells;

import com.google.gwt.cell.client.DatePickerCell;

public class MpDatePickerCell extends DatePickerCell{
	
	public MpDatePickerCell(){
	    this.getDatePicker().setYearAndMonthDropdownVisible(true);
	    this.getDatePicker().setYearArrowsVisible(true);
	}

}
