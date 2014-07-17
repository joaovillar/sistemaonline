package com.jornada.client.classes.widgets.textbox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBox;
 
public class PromptedTextBox extends TextBox implements KeyUpHandler, FocusHandler, ClickHandler
{
    private String promptText;
    private String promptStyle;
 
    public PromptedTextBox(String promptText, String promptStyleName)
    {
        this.promptText = promptText;
        this.promptStyle = promptStyleName;
        this.addKeyUpHandler(this);
        this.addFocusHandler(this);
        this.addClickHandler(this);
        showPrompt();
    }
 
    public void showPrompt()
    {
        this.addStyleName(promptStyle);
        this.setText(this.promptText);
    }
 
    public void hidePrompt()
    {
        this.setText(null);
        this.removeStyleName(promptStyle);
    }
 
    @Override
    public void onKeyUp(KeyUpEvent event)
    {
        if (promptText.equals(this.getText())
        	 && !(event.getNativeKeyCode() == KeyCodes.KEY_TAB))
        {
            hidePrompt();
        }
    }
 
    @Override
    public void onFocus(FocusEvent event)
    {
        this.setCursorPos(0);
    }
 
    @Override
    public void onClick(ClickEvent event)
    {
        if (promptText.equals(this.getText()))
            hidePrompt();
    }
}
