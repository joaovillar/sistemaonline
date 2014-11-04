package com.jornada.client.classes.widgets.popup;


import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;

public class MpPopupLoading extends PopupPanel{
	
	MpPanelLoading mpLoading;
	
	public MpPopupLoading(String strMessage, String strImg) {

//		setAnimationEnabled(true);

		mpLoading = new MpPanelLoading(strImg);
		mpLoading.setTxtLoading(strMessage);
		mpLoading.show();
		mpLoading.setVisible(true);
		
		
		setStyleName("tabela_mainview_titulo");
		
		mpLoading.getLblLoading().setStyleName("labelWhite");
		

		setPopupPositionAndShow(new PopupPanel.PositionCallback() {
			public void setPosition(int offsetWidth, int offsetHeight) {
				int left = Window.getClientWidth()/2;
				int top = Window.getScrollTop()+20;
				setPopupPosition(left, top);
			}
		});
		
		this.setWidget(mpLoading);
		this.hide();
	}

}
