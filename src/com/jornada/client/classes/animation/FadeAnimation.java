package com.jornada.client.classes.animation;


import java.math.BigDecimal;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.Element;

@SuppressWarnings("deprecation")
public class FadeAnimation extends Animation {

	private Element element;
	private double opacityIncrement;
	private double targetOpacity;
	private double baseOpacity;

	public FadeAnimation(Element element) {
		this.element = element;
	}

	@Override
	protected void onUpdate(double progress) {
		element.getStyle()
				.setOpacity(baseOpacity + progress * opacityIncrement);
	}

	@Override
	protected void onComplete() {
		super.onComplete();
		element.getStyle().setOpacity(targetOpacity);
	}

	public void fade(int duration, double targetOpacity) {
		if (targetOpacity > 1.0) {
			targetOpacity = 1.0;
		}
		if (targetOpacity < 0.0) {
			targetOpacity = 0.0;
		}
		this.targetOpacity = targetOpacity;
		String opacityStr = element.getStyle().getOpacity();
		try {
			baseOpacity = new BigDecimal(opacityStr).doubleValue();
			opacityIncrement = targetOpacity - baseOpacity;
			run(duration);
		} catch (NumberFormatException e) {
			// set opacity directly
			onComplete();
		}
	}
	
	public void setOpacity(double targetOpacity) {
		element.getStyle().setOpacity(targetOpacity);
	}
	
	// For reference - an example to see how to deal with dom
	/*
	showDetails.addDomHandler(new MouseOverHandler() {

		@Override
		public void onMouseOver(MouseOverEvent event) {
			fadeAnimation.fade(1500, 1.0);
		}
	}, MouseOverEvent.getType());

	showDetails.addDomHandler(new MouseOutHandler() {

		@Override
		public void onMouseOut(MouseOutEvent event) {
			fadeAnimation.fade(1500, 0.3);
		}
	}, MouseOutEvent.getType());
	*/
}
