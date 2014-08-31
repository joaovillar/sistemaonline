package com.jornada.client.classes.widgets.multibox;


import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class Paragraph extends Widget implements HasText {

    public Paragraph() {
        setElement(DOM.createElement("p"));
    }

    public Paragraph(String text) {
        this();
        setText(text);
    }

    public String getText() {
        return getElement().getInnerText();
    }

    public void setText(String text) {
        getElement().setInnerText(text);
    }
}
