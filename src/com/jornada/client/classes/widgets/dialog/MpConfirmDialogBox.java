package com.jornada.client.classes.widgets.dialog;

/**
 *PUBLIC SOFTWARE
 *
 *This source code has been placedin the public domain. You can use, modify, and distribute
 *the source code and executable programs based on the source code.
 *
 *However, note the following:
 *
 *DISCLAIMER OF WARRANTY
 *
 * This source code is provided "as is" and without warranties as to performance
 * or merchantability. The author and/or distributors of this source code may
 * have made statements about this source code. Any such statements do not constitute
 * warranties and shall not be relied on by the user in deciding whether to use
 * this source code.This source code is provided without any express or implied
 * warranties whatsoever. Because of the diversity of conditions and hardware
 * under which this source code may be used, no warranty of fitness for a
 * particular purpose is offered. The user is advised to test the source code
 * thoroughly before relying on it. The user must assume the entire risk of
 * using the source code.
 * 
 */
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.*;
import com.jornada.client.classes.widgets.button.MpImageButton;

/**
 * @author amal
 * @version 1.0
 */
public final class MpConfirmDialogBox extends DialogBox {

	private VerticalPanel mainPanel;
	private HorizontalPanel btnPanel;
	private HTML messageLbl;
	private boolean primaryActionFired = false;
	private boolean secondaryActionFired = false;

	public boolean primaryActionFired() {
		return primaryActionFired;
	}

	public boolean secondaryActionFired() {
		return secondaryActionFired;
	}

	/**
	 * 
	 * @param title
	 * @param message
	 * @param primaryActionText
	 * @param secondaryActionText
	 * @param closeHandler
	 * 
	 *            sample usage - retrieving the confirmation status
	 * 
	 *            <pre>
	 * CloseHandler<PopupPanel> closeHandler=new CloseHandler<PopupPanel>()
	 *          {
	 * 
	 *               public void onClose(CloseEvent<PopupPanel> event)
	 *                 {
	 *                  MyGwtConfirmDialog x=(MyGwtConfirmDialog)event.getSource();
	 *                  Window.alert("primary "+x.primaryActionFired()+" ; secondary "+x.secondaryActionFired());
	 *                 }
	 *           };
	 *         MyGwtConfirmDialog dia=new MyGwtConfirmDialog(<title>,<message>,<okText>,<cancelText>,closeHandler);
	 * </pre>
	 */
	public MpConfirmDialogBox(String title, String message,
			String primaryActionText, String secondaryActionText,
			CloseHandler<PopupPanel> closeHandler) {
		super(false, true);
		super.addCloseHandler(closeHandler);
		super.setText(title);
		mainPanel = new VerticalPanel();
		messageLbl = new HTML(message);
		btnPanel = new HorizontalPanel();
		mainPanel.add(messageLbl);
		mainPanel.add(btnPanel);
		
		MpImageButton primaryBtn = new MpImageButton(primaryActionText, "images/image002.png");
		primaryBtn.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				primaryActionFired = true;
				MpConfirmDialogBox.this.hide();
			}
		});
		
		MpImageButton secondaryBtn = new MpImageButton(secondaryActionText, "images/cross-circle-frame.png");
		secondaryBtn.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				secondaryActionFired = true;
				MpConfirmDialogBox.this.hide();
			}
		});		
		
//		primaryBtn = new Button(primaryActionText, new ClickHandler() {
//
//			public void onClick(ClickEvent event) {
//				primaryActionFired = true;
//				MpConfirmDialogBox.this.hide();
//			}
//		});
		
		
//		secondaryBtn = new Button(secondaryActionText, new ClickHandler() {
//
//			public void onClick(ClickEvent event) {
//				secondaryActionFired = true;
//				MpConfirmDialogBox.this.hide();
//			}
//		});
		btnPanel.add(primaryBtn);
		btnPanel.add(secondaryBtn);
		btnPanel.setCellHorizontalAlignment(primaryBtn, HorizontalPanel.ALIGN_RIGHT);
		btnPanel.setCellHorizontalAlignment(secondaryBtn,HorizontalPanel.ALIGN_LEFT);
		super.setWidget(mainPanel);
		mainPanel.setHeight("100px");
		mainPanel.setWidth("300px");
		btnPanel.setHeight("30px");
		btnPanel.setVerticalAlignment(HorizontalPanel.ALIGN_BOTTOM);
		btnPanel.addStyleName("dia-btnPanel");
		primaryBtn.addStyleName("dia-primaryBtn");
		secondaryBtn.addStyleName("dia-secondaryBtn");
		messageLbl.addStyleName("dia-message");
		super.setGlassEnabled(true);
		super.setAnimationEnabled(true);
	}

	/**
	 * 
	 * @param message
	 * @param closeHandler
	 * @see MpConfirmDialogBox#MyGwtConfirmDialog(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      com.google.gwt.event.logical.shared.CloseHandler)
	 */
	public MpConfirmDialogBox(String message, CloseHandler<PopupPanel> closeHandler) {
		this("Confirmation", message, "Ok", "Cancel", closeHandler);
	}

	/**
	 * should be used for displaying the dialog .
	 */
	public void paint() {
		primaryActionFired = false;
		secondaryActionFired = false;
		super.center();
//		primaryBtn.setFocus(true);
	}

	/**
	 * 
	 * @param width
	 */
	public void setMainPanelWidth(String width) {
		mainPanel.setWidth(width);
	}
}