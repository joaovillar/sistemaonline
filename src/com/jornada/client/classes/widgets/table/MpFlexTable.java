package com.jornada.client.classes.widgets.table;

import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.HasMouseUpHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlexTable;

public class MpFlexTable extends FlexTable implements MouseOverHandler, MouseOutHandler, MouseUpHandler, MouseDownHandler, HasMouseOverHandlers, HasMouseOutHandlers, HasMouseUpHandlers, HasMouseDownHandlers{

	
	public MpFlexTable(){
		this.addMouseOutHandler(this);
		this.addMouseOverHandler(this);
		this.addMouseUpHandler(this);
	}
	


	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addDomHandler(handler, MouseOutEvent.getType());
	}
	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return addDomHandler(handler, MouseOverEvent.getType());
	}
	@Override
	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {		
		return addDomHandler(handler, MouseUpEvent.getType());
	}
	@Override
	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		return addDomHandler(handler, MouseDownEvent.getType());
	}
	
	

	@Override
	public void onMouseOut(MouseOutEvent event) {		
	}
	@Override
	public void onMouseOver(MouseOverEvent event) {		
	}
	@Override
	public void onMouseUp(MouseUpEvent event) {
	}
	@Override
	public void onMouseDown(MouseDownEvent event) {
	}











}
