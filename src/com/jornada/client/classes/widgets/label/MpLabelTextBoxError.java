package com.jornada.client.classes.widgets.label;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.jornada.client.classes.widgets.panel.MpSpacePanel;

public class MpLabelTextBoxError extends Grid{
	
	private Image img;	
	private MpLabelError labelError;
	
	public MpLabelTextBoxError(){
		
		
	super(1,4);
		
		this.setCellSpacing(0);
		this.setCellPadding(0);
		
		
		img = new Image("images/error.png");
//		img.setSize("22px", "22px");
		img.setStyleName("imageCenter");
		img.setVisible(false);
		
		
		labelError = new MpLabelError();
		labelError.setVisible(false);
		
		int column=0;
		this.setWidget(0, column++, new MpSpacePanel());		
		this.setWidget(0, column++, img);		
		this.setWidget(0, column++, new MpSpacePanel());
		this.setWidget(0, column++, labelError);
		
	
		HTMLTable.CellFormatter formatter = this.getCellFormatter();		
		formatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
//		formatter.setHorizontalAlignment(0, 3, HasHorizontalAlignment.ALIGN_LEFT);
		
	}
	
	public void showErrorMessage(String strErrorMessage){
		img.setVisible(true);
		labelError.setVisible(true);		
		labelError.setText(strErrorMessage);
	}
	
	public void hideErroMessage(){
		img.setVisible(false);
		labelError.setVisible(false);		
		labelError.setText("");
	}	

}
