package com.jornada.client.classes.widgets.panel;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;

public class MpPanelLoading  extends HorizontalPanel{

	private String strLoading;
	private String strImgAddress;
	
	private Image imgLoading;
    private Label lblLoading;
    
    HorizontalPanel hPanelFather;
    
    private HorizontalPanel hPanelLoading;

    
    public MpPanelLoading(){
    	strLoading="";
    	strImgAddress="";    	
    	hPanelLoading = new HorizontalPanel();
    }  
    
    public MpPanelLoading(String imgAddress){
    	this.strLoading="";
    	this.strImgAddress=imgAddress;    	
    	hPanelLoading = new HorizontalPanel();
    }     
    
    
    public void setVisible(boolean is_visible){
    	lblLoading.setVisible(is_visible);
    	imgLoading.setVisible(is_visible);
    }
	
	public void show() {

		hPanelFather = new HorizontalPanel();
		hPanelFather.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		hPanelFather.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		if (strLoading.isEmpty()) {
			hPanelFather.setWidth("30px");
		}else{
			hPanelFather.setWidth("100px");
		}
		hPanelFather.setHeight("20px");
//		hPanelFather.setBorderWidth(2);
//		HorizontalPanel hPanel = new HorizontalPanel();
		lblLoading = new Label(strLoading);
		//lblLoading.setStyleName("label_loading");
		imgLoading = new Image(strImgAddress);
//		hPanel.add(new InlineHTML("&nbsp"));
		hPanelLoading.add(imgLoading);
		hPanelLoading.add(new InlineHTML("&nbsp"));
		hPanelLoading.add(lblLoading);
		//hPanelLoading.setVisible(false);
		// vPanelTrackingTable.add(hPanel);
//		hPanelFather.add(hPanel);
		hPanelFather.add(hPanelLoading);
		
		this.add(hPanelFather);
	}

	public String getTxtLoading() {
		return strLoading;
	}

	public void setTxtLoading(String txtLoading) {
		this.strLoading = txtLoading;
	}

	public String getImgAddress() {
		return strImgAddress;
	}

	public void setImgAddress(String imgAddress) {
		this.strImgAddress = imgAddress;
	}
	public void setWidth(String width){
		hPanelFather.setWidth(width);
	}

	public Label getLblLoading() {
		return lblLoading;
	}

	public void setLblLoading(Label lblLoading) {
		this.lblLoading = lblLoading;
	}
	
	
	
}
