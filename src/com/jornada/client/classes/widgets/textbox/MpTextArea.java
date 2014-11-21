package com.jornada.client.classes.widgets.textbox;

import com.google.gwt.user.client.ui.TextArea;

public class MpTextArea extends TextArea{
	


	
	public MpTextArea(){
		
		setStyleName("design_text_boxes");
		getElement().setAttribute("maxlength", "5000");
		setSize("350px", "50px");
	}


}
