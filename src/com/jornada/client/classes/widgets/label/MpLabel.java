package com.jornada.client.classes.widgets.label;

import com.google.gwt.user.client.ui.Label;

public class MpLabel extends Label{


	public MpLabel(){
		setStyleName("design_label");	
	}
	
	public MpLabel(String text){
		setText(text);
		setStyleName("design_label");		
	}

}
