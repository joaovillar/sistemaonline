package com.jornada.client.classes.widgets.button;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;

/**
 *
 * @author waf039
 */
public class MpImageButton extends Button
{
    
    
    public MpImageButton(){
        
    }
    
    public MpImageButton(String strText, String strImgSrc){
    	this.setStyleName("cw-Button");
    	setText(strText);    	
    	setImgSrc(strImgSrc);
    }
    
    public void setImgSrc(String imgSrc) {
        Image img = new Image(imgSrc);
        String definedStyles = img.getElement().getAttribute("style");
        img.getElement().setAttribute("style",definedStyles);
        img.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
        DOM.insertBefore(getElement(), img.getElement(), DOM.getFirstChild(getElement()));
    }

    @Override
    public void setText(String text) {
		if (!text.isEmpty()) {
	    	@SuppressWarnings("deprecation")
			com.google.gwt.user.client.Element span = DOM.createElement("span");
	    	span.setInnerText("");
			span.setInnerText(text);
			span.getStyle().setPaddingLeft(5, Unit.PX);
			span.getStyle().setPaddingRight(3, Unit.PX);
			span.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
			span.getStyle().setColor("black");

			span.setAttribute("class", "arial12R6D6D6D");
			DOM.insertChild(getElement(), span, 0);
		}
    }   
    
}
