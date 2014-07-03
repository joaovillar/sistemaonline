package com.jornada.client.classes.animation;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;

@SuppressWarnings("deprecation")
public class ElementFader {

    private int stepCount;

    public ElementFader() {
        this.stepCount = 0;
    }

    private void incrementStep() {
        stepCount++;
    }

    private int getStepCount() {
        return stepCount;
    }

    public void fade(final Element element, final float startOpacity, final float endOpacity, int totalTimeMillis) {
        final int numberOfSteps = 30;
        int stepLengthMillis = totalTimeMillis / numberOfSteps;

        stepCount = 0;

        final float deltaOpacity = (float) (endOpacity - startOpacity) / numberOfSteps;

        Timer timer = new Timer() {

            @Override
            public void run() {
                float opacity = startOpacity + (getStepCount() * deltaOpacity);
                DOM.setStyleAttribute(element, "opacity", Float.toString(opacity));

                incrementStep();
                if (getStepCount() == numberOfSteps) {
                    DOM.setStyleAttribute(element, "opacity", Float.toString(endOpacity));
                    this.cancel();
                }
            }
        };

        timer.scheduleRepeating(stepLengthMillis);
    }
}