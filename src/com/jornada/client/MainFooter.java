package com.jornada.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.jornada.client.content.i18n.TextConstants;

public class MainFooter extends Composite {
	
	TextConstants txtConstants;
	
	public MainFooter(){
		
		txtConstants = GWT.create(TextConstants.class);
		
		
		final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
	    simplePopup.setWidth("150px");
	    simplePopup.setWidget(new HTML(txtConstants.footerEmailSuporteMsg()+" <b>"+txtConstants.footerEmailEndereco()+"</b>"));
	    simplePopup.setAnimationEnabled(true);
//	    simplePopup.setGlassEnabled(true);
		
		FlexTable flexTable = new FlexTable();
		flexTable.setStyleName("rodape"); 
		initWidget(flexTable);
		flexTable.setSize("100%", "100%"); 
		
		Label lblPolitica = new Label(txtConstants.footerPolitica());
		lblPolitica.setStyleName("label_rodape"); 
		flexTable.setWidget(0, 0, lblPolitica);
		
		Label lblUsoInterno = new Label(txtConstants.footerUsoInterno()); 
		lblUsoInterno.setStyleName("label_rodape"); 
		flexTable.setWidget(0, 1, lblUsoInterno);
		
		Image image = new Image("images/chromelogo.png"); 
		flexTable.setWidget(0, 2, image);
		image.setHeight("28px"); 
		
		Label lblEmailSuporte = new Label(txtConstants.footerEmailSuporte()); 
		lblEmailSuporte.setStyleName("label_rodape_link");
		lblEmailSuporte.addClickHandler(new ClickHandler() {
	          public void onClick(ClickEvent event) {
	              // Reposition the popup relative to the button
	              Widget source = (Widget) event.getSource();
	              int left = source.getAbsoluteLeft() + 60;
	              int top = source.getAbsoluteTop() - 50;
	              simplePopup.setPopupPosition(left, top);

	              // Show the popup
	              simplePopup.show();
	            }
	          });
		flexTable.setWidget(1, 0, lblEmailSuporte);

		
		Label lblLicensa = new Label(txtConstants.footerLicensa()); 
		lblLicensa.setStyleName("label_rodape"); 
		flexTable.setWidget(1, 1, lblLicensa);
		lblLicensa.setSize("100%", "100%"); 
		
		Label lblMelhorBrowser = new Label(txtConstants.footerBrowser()); 
		lblMelhorBrowser.setStyleName("label_rodape"); 
		flexTable.setWidget(1, 2, lblMelhorBrowser);
		
	}

}
