package com.jornada.client.classes.widgets.textbox;


import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MpRichTextArea extends VerticalPanel{
	
	private RichTextArea textArea;
	
	public MpRichTextArea(){
		
		textArea = new RichTextArea();
        final MpRichTextToolbar  toolBar = new MpRichTextToolbar(textArea);
//		RichTextToolbar 
        VerticalPanel vp = new VerticalPanel();
        textArea.setWidth("100%");
        
        
        vp.add(toolBar);
        vp.add(textArea);
        
        super.add(vp);
	}

	public RichTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(RichTextArea textArea) {
		this.textArea = textArea;
	}
	
	

}
