package com.jornada.client.ambiente.general.ajuda;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.content.i18n.TextConstants;

public class DialogBoxSobre extends DialogBox { 
	
	
	TextConstants txtConstants = GWT.create(TextConstants.class);
	
	private static DialogBoxSobre uniqueInstance;
	
	public static DialogBoxSobre getInstance(){
		if(uniqueInstance==null){
			uniqueInstance = new DialogBoxSobre();
		}else{
			uniqueInstance.show();
		}
		return uniqueInstance;
	}

	
	private DialogBoxSobre(){
		
		 
		
		VerticalPanel vBody = new VerticalPanel();
		
		Label lblTitle = new Label(txtConstants.ajudaSobre() + " "+txtConstants.titleNome());
		lblTitle.setStyleName("design_label");
		

		
		MpImageButton btnAtualizar = new MpImageButton(txtConstants.geralOk(), "images/image002.png");
		btnAtualizar.addClickHandler(new ClickHandlerOK());
		
		
//		MpImageButton btnCancelar = new MpImageButton(txtConstants.geralCancelar(), "images/cross-circle-frame.png");
//		btnCancelar.addClickHandler(new ClickHandlerCancelar());
		
		
		Image img = new Image("images/Courses-128.png");
		
		
		FlexTable flexTable = new FlexTable();
		flexTable.setCellPadding(2);
		flexTable.setCellSpacing(2);
//		flexTable.setBorderWidth(1);
		
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 3);
		flexTable.getFlexCellFormatter().setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_LEFT);
		flexTable.getFlexCellFormatter().setStyleName(0, 0, "designAjuda");
		
		String strText = txtConstants.ajudaText1();
		       strText +=txtConstants.ajudaText2();
		       strText +=txtConstants.ajudaText3();
		
		Label lbl1 = new Label(strText);
		lbl1.getElement().getStyle().setProperty("whiteSpace", "pre");
		lbl1.setStyleName("label_comum");
		
		VerticalPanel verticalPanel = new VerticalPanel();

//		verticalPanel.setWidth("700px");
		verticalPanel.add(lbl1);

		int row=0;
		flexTable.setWidget(row++, 0, lblTitle);
		flexTable.getFlexCellFormatter().setRowSpan(row, 0, 4);flexTable.setWidget(row, 0, img);
		flexTable.setWidget(row++, 1, verticalPanel);
//		flexTable.setWidget(row++, 0, lblText2);
//		flexTable.setWidget(row++, 0, lblText3);
//		gridListBox.setWidget(row, 1, listBoxIdiomas);
//		gridListBox.setWidget(row, 2, mpLoading);

		
		
		Grid gridBotoes = new Grid(1,1);
		row=0;
		gridBotoes.getCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
//		gridBotoes.getCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_LEFT);
		gridBotoes.setWidth("100%");
//		gridBotoes.setBorderWidth(1);
		
		
//		row=0;
		gridBotoes.setWidget(row, 0, btnAtualizar);
//		gridBotoes.setWidget(row, 1, btnCancelar);
			
		vBody.add(flexTable);
		vBody.add(gridBotoes);



		/***********************End Callbacks**********************/
		
//		setWidth("650px");
		setWidget(vBody);
//		super.setWidth("700px");
		super.setGlassEnabled(true);
		super.setAnimationEnabled(true);
	    center();
	    show();
		
	}
	
	private class ClickHandlerOK implements ClickHandler{		
		public void onClick(ClickEvent event){
					DialogBoxSobre.getInstance().hide();	
		}		
	}
	
		

}
