package com.jornada.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.jornada.client.content.i18n.TextConstants;

public class MainFooter extends Composite {
	
	TextConstants txtConstants;
	
	public MainFooter(){
		
		txtConstants = GWT.create(TextConstants.class);
		
		FlexTable flexTable = new FlexTable();
		flexTable.setStyleName("rodape"); 
		initWidget(flexTable);
		flexTable.setSize("100%", "100%"); 
		
		Label lblNewLabel = new Label(txtConstants.footerPolitica());
		lblNewLabel.setStyleName("label_rodape"); 
		flexTable.setWidget(0, 0, lblNewLabel);
		
		Label lblNewLabel_1 = new Label(txtConstants.footerUsoInterno()); 
		lblNewLabel_1.setStyleName("label_rodape"); 
		flexTable.setWidget(0, 1, lblNewLabel_1);
		
		Image image = new Image("images/chromelogo.png"); 
		flexTable.setWidget(0, 2, image);
		image.setHeight("28px"); 
		
		Label lblNewLabel_2 = new Label(txtConstants.footerEmail()); 
		lblNewLabel_2.setStyleName("label_rodape"); 
		flexTable.setWidget(1, 0, lblNewLabel_2);
		
		Label lblNewLabel_3 = new Label(txtConstants.footerLicensa()); 
		lblNewLabel_3.setStyleName("label_rodape"); 
		flexTable.setWidget(1, 1, lblNewLabel_3);
		lblNewLabel_3.setSize("100%", "100%"); 
		
		Label lblNewLabel_4 = new Label(txtConstants.footerBrowser()); 
		lblNewLabel_4.setStyleName("label_rodape"); 
		flexTable.setWidget(1, 2, lblNewLabel_4);
		
	}

}
