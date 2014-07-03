package com.jornada.client.classes.widgets.textbox;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBox;

public class MpTextBoxWithImage extends TextBox implements BlurHandler, FocusHandler
{
    String watermark;
    String masked;
    HandlerRegistration blurHandler;
    HandlerRegistration focusHandler;
    boolean isPassword;
     
    public MpTextBoxWithImage( )
    {
        super();        
    }
     

    
   
     
    public MpTextBoxWithImage(String watermarkText, String stylePrimaryName, boolean isPassword)
    {
    	this.isPassword = isPassword;
    	this.setStylePrimaryName(stylePrimaryName);
        setWatermark(watermarkText);
        this.getElement().setPropertyString("type", "text");
    }
     
    /**
     * Adds a watermark if the parameter is not NULL or EMPTY
     * 
     * @param watermark
     */
    public void setWatermark(final String watermark)
    {
        this.watermark = watermark;
         
        if ((watermark != null) && (watermark != ""))
        {
            blurHandler = addBlurHandler(this);
            focusHandler = addFocusHandler(this);
            EnableWatermark();
        }
        else
        {
            // Remove handlers
            blurHandler.removeHandler();
            focusHandler.removeHandler();
        }
    }
 
    @Override
    public void onBlur(BlurEvent event)
    {
        EnableWatermark();
    }
     
    void EnableWatermark()
    {
        String text = getText(); 
        if ((text.length() == 0) || (text.equalsIgnoreCase(watermark)))
        {
        	this.getElement().setPropertyString("type", "text");
            // Show watermark
            setText(watermark);
            addStyleDependentName("watermark");
        }
    }
 
    @Override
    public void onFocus(FocusEvent event)
    {
        removeStyleDependentName("watermark");
         
        if (getText().equalsIgnoreCase(watermark))
        {
            // Hide watermark
        	if(isPassword){
        		 this.getElement().setPropertyString("type", "password");
        	}
            setText("");
        }
    }
    
  
    
    


}