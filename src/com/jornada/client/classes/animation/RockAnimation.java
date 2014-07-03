package com.jornada.client.classes.animation;

	import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;

	@SuppressWarnings("deprecation")
	public class RockAnimation extends Animation {

	    private Element element;
	    private int currentOp;
	    private final static int FADE_OUT = 0;
	    private final static int FADE_IN = 1;
	    private final static int SLIDE_IN = 2;
	    private final static int SLIDE_OUT = 3;


	    public RockAnimation(Element element) {
	        this.element = element;
	    }

	    public void fadeOut(int durationMilli) {
	        cancel();
	        currentOp = RockAnimation.FADE_OUT;
	        run(durationMilli);
	    }

	    public void fadeIn(int durationMilli) {
	        cancel();
	        currentOp = RockAnimation.FADE_IN;
	        run(durationMilli);
	    }

	    public void slideIn(int durationMilli) {
	        cancel();
	        currentOp = RockAnimation.SLIDE_IN;
	        run(durationMilli);
	    }

	    public void slideOut(int durationMilli) {
	        cancel();
	        currentOp = RockAnimation.SLIDE_OUT;
	        run(durationMilli);
	    }

	    @Override
	    protected void onUpdate(double progress) {
	        switch (currentOp) {
	        case RockAnimation.FADE_IN:
	            doFadeIn(progress);
	            break;
	        case RockAnimation.FADE_OUT:
	            doFadeOut(progress);
	            break;
	        case RockAnimation.SLIDE_IN:
	            doSlideIn(progress);
	            break;
	        case RockAnimation.SLIDE_OUT:
	            doSlideOut(progress);
	            break;
	        }
	    }

	    private void doFadeOut(double progress) {
	        element.getStyle().setOpacity(1.0d - progress);
	    }

	    private void doFadeIn(double progress) {
	        element.getStyle().setOpacity(progress);
	    }

	    private void doSlideIn(double progress) {
	        double height = element.getScrollHeight();
	        element.getStyle().setHeight(height * (1.0d - progress), Unit.PX);
	    }

	    private void doSlideOut(double progress) {
	            // Hard coded value. How can I find out what
	            // the element's max natural height is if it's 
	            // currently set to height: 0 ?
	        element.getStyle().setHeight(200 * progress, Unit.PX);
	    }
	}