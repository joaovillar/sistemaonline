package com.jornada.client.classes.widgets.header;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

@SuppressWarnings("deprecation")
public class MpHeaderWidget extends SimplePanel{
	
	public MpHeaderWidget(String text, String image){
		// Add the image and text to a horizontal panel
				HorizontalPanel hPanel = new HorizontalPanel();
				hPanel.setHeight("100%");
				hPanel.setSpacing(0);
				
				Image img = new Image(image);
				
				Label headerText = new Label(text);
				headerText.setStyleName("gwt-TabLayoutPanel");
				
				hPanel.add(img);	
				hPanel.add(new InlineHTML("&nbsp;"));		
				hPanel.add(headerText);
				
				Element imageContainer = DOM.getParent(img.getElement());
				imageContainer.setClassName("img-center-alignment");
				
				add(hPanel);
//				return new SimplePanel(hPanel);		
		
	}

}
