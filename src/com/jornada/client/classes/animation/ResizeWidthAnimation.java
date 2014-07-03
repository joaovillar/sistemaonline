package com.jornada.client.classes.animation;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;

public class ResizeWidthAnimation  extends Animation {
    // initial size of widget
    private int startWidth = 0;
    // desired size of widget. Widget will have this size after animation will stop to run
    private int desiredWidth = 0;
    
    Widget resizedWidget;
    
   public ResizeWidthAnimation(int desiredWidth, Widget resizedWidget) {
        this.startWidth = resizedWidget.getOffsetWidth();
        this.desiredWidth = desiredWidth;
        this.resizedWidget = resizedWidget;
    }
    @Override
    protected void onUpdate(double progress) {
        double width = extractProportionalLength(progress) ;
        resizedWidget.setWidth( width + Unit.PX.getType());
    }
    private double extractProportionalLength(double progress) {
        double outWidth = startWidth - (startWidth - desiredWidth) * progress;
        return outWidth;
    }
}	
