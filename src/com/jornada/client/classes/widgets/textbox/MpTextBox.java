package com.jornada.client.classes.widgets.textbox;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.jornada.client.classes.widgets.label.MpLabelError;
import com.jornada.client.classes.widgets.panel.MpSpacePanel;

public class MpTextBox extends Grid{
	
	private TextBox textBox;
	private Image img;	
	private MpLabelError labelError;
	
	public MpTextBox(){
		
		super(1,5);
		
		this.setCellSpacing(0);
		this.setCellPadding(0);
		
		
		textBox = new TextBox();
		textBox.setStyleName("design_text_boxes");
		
		img = new Image("images/bullet_red.png");
		img.setSize("22px", "22px");
		img.setStyleName("imageCenter");
		img.setVisible(false);
		
		
		labelError = new MpLabelError();
		labelError.setVisible(false);
		
		int column=0;
		this.setWidget(0, column++, textBox);
		this.setWidget(0, column++, new MpSpacePanel());		
		this.setWidget(0, column++, img);		
		this.setWidget(0, column++, new MpSpacePanel());
		this.setWidget(0, column++, labelError);
		
	
		HTMLTable.CellFormatter formatter = this.getCellFormatter();		
		formatter.setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_LEFT);
//		formatter.setHorizontalAlignment(0, 3, HasHorizontalAlignment.ALIGN_LEFT);
		
	}

	public TextBox getTextBox() {
		return textBox;
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
