package com.jornada.client.classes.widgets.multibox;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;

public class Span extends HTML implements HasText {

    public Span() {
        super(DOM.createElement("span"));
    }

    public Span(String text) {
        this();
        setText(text);
    }
    
}