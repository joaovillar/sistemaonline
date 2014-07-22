package com.jornada.client.classes.widgets.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Widget;
import com.jornada.client.content.i18n.TextConstants;


@SuppressWarnings("deprecation")
public class MpDialogBoxRefreshPage extends DialogBox implements ClickListener {
	
	
	public static final String TYPE_CONFIRMATION="CONFIRMATION_DIALOG";
	public static final String TYPE_WARNING="WARNING_DIALOG";
	
//	private String title; 
	private String bodyText;
	private String TYPE_MESSAGE;	
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	public MpDialogBoxRefreshPage(){
//		title = "";
		bodyText = "";	
	}
	
	public void showDialog() {

//		title = txtConstants.geralAviso();
		setText(txtConstants.geralAviso());
		TYPE_MESSAGE = MpDialogBox.TYPE_WARNING;
		bodyText = txtConstants.geralRecarregarAmbiente();
		
		 

		    Button closeButton = new Button("Ok", this);
//		    closeButton.ad
		    closeButton.addKeyUpHandler(new EnterKeyUpHandler());
		    closeButton.setFocus(true);
		    closeButton.setStyleName("cw-Button");

		    DockPanel dock = new DockPanel();
		    dock.setSpacing(4);

		    dock.add(closeButton, DockPanel.SOUTH);

		    dock.setCellHorizontalAlignment(closeButton, DockPanel.ALIGN_RIGHT);
		    dock.setWidth("100%");
		    setWidget(dock);
		    
		    HorizontalPanel horizontalPanel = new HorizontalPanel();
		    dock.add(horizontalPanel, DockPanel.NORTH);
		    
		    String strImgAddress="";
		    if(TYPE_MESSAGE.equals(TYPE_CONFIRMATION)){
		    	strImgAddress = "images/image002.png";
		    }
		    else if(TYPE_MESSAGE.equals(TYPE_WARNING)){
		    	strImgAddress = "images/exclamation-icon-small.png";
		    }		    
		    
		    Image image = new Image(strImgAddress);
		    horizontalPanel.add(image);
		    HTML msg = new HTML("<center>"+bodyText+"</center>",true);
		    horizontalPanel.add(new InlineHTML("&nbsp;"));
		    horizontalPanel.add(msg);
			super.setGlassEnabled(true);
			super.setAnimationEnabled(true);
		    center();
		    show();
		    closeButton.setFocus(true);
	}
	



	public void onClick(Widget sender) {
		hide();
		Window.Location.reload();
	}
	
	private class EnterKeyUpHandler implements KeyUpHandler {
		 public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {				
				hide();
				Window.Location.reload();
			}
		}
	}
	

}



