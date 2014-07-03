package com.jornada.client.ambiente.coordenador.periodo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.jornada.client.ambiente.coordenador.curso.TelaInicialCurso;
import com.jornada.client.classes.listBoxes.MpSelectionCurso;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelTextBoxError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpacePanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServicePeriodo;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Periodo;

public class AdicionarPeriodo extends VerticalPanel {

	private AsyncCallback<Integer> callbackAddPeriodo;


	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading hPanelLoading = new MpPanelLoading("images/radar.gif");

	private ListBox listBoxCurso;
	private MpSelectionCurso mpSelectionCurso;
	
	private TextBox txtNomePeriodo;
	private TextArea txtDescricaoPeriodo;
	private TextArea txtObjetivoPeriodo;
	private MpDateBoxWithImage mpDateBoxInicial;
	private MpDateBoxWithImage mpDateBoxFinal;
	
	private MpLabelTextBoxError lblErroNomePeriodo;
	
	@SuppressWarnings("unused")
	private TelaInicialPeriodo telaInicialPeriodo;
	
	TextConstants txtConstants;

	@SuppressWarnings("deprecation")
	public AdicionarPeriodo(final TelaInicialPeriodo telaInicialPeriodo) {
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialPeriodo=telaInicialPeriodo;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		hPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		hPanelLoading.show();
		hPanelLoading.setVisible(false);

		FlexTable flexTable = new FlexTable();
		flexTable.setCellSpacing(3);
		flexTable.setCellPadding(3);
		flexTable.setBorderWidth(0);
		flexTable.setSize(Integer.toString(TelaInicialPeriodo.intWidthTable),Integer.toString(TelaInicialPeriodo.intHeightTable));
		FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();

		// Add a title to the form
		cellFormatter.setColSpan(0, 0, 0);
		cellFormatter.setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_CENTER);
		txtNomePeriodo = new TextBox();
		txtDescricaoPeriodo = new TextArea();
		txtObjetivoPeriodo = new TextArea();
		mpDateBoxInicial = new MpDateBoxWithImage();
		mpDateBoxInicial.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
		mpDateBoxFinal = new MpDateBoxWithImage();
		mpDateBoxFinal.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
		txtNomePeriodo.setStyleName("design_text_boxes");
		txtDescricaoPeriodo.setStyleName("design_text_boxes");
		txtObjetivoPeriodo.setStyleName("design_text_boxes");
//		formDateInicial.getDate().setStyleName("design_text_boxes");
//		formDateFinal.setStyleName("design_text_boxes");

		Label lblCurso = new Label(txtConstants.curso());
		Label lblNomePeriodo = new Label(txtConstants.periodoNome());		
		Label lblDescricaoPeriodo = new Label(txtConstants.periodoDescricao());		
		Label lblObjetivoPeriodo = new Label(txtConstants.periodoObjetivo());		
		Label lblDateInicial = new Label(txtConstants.periodoDataInicial());		
		Label lblDateFinal = new Label(txtConstants.periodoDataFinal());
		
		lblErroNomePeriodo = new MpLabelTextBoxError();
		
		lblCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);	
		lblNomePeriodo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDescricaoPeriodo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblObjetivoPeriodo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDateInicial.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDateFinal.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		lblCurso.setStyleName("design_label");
		lblNomePeriodo.setStyleName("design_label");
		lblDescricaoPeriodo.setStyleName("design_label");
		lblObjetivoPeriodo.setStyleName("design_label");
		lblDateInicial.setStyleName("design_label");
		lblDateFinal.setStyleName("design_label");
		txtNomePeriodo.setWidth("350px");
		txtDescricaoPeriodo.setSize("350px", "50px");
		txtObjetivoPeriodo.setSize("350px", "50px");
		mpDateBoxInicial.getDate().setWidth("170px");
		mpDateBoxFinal.getDate().setWidth("170px");
		
		
		mpSelectionCurso = new MpSelectionCurso();
		listBoxCurso = mpSelectionCurso;

		// Add some standard form options
		int row = 1;

		flexTable.setWidget(row, 0, lblCurso);
		flexTable.setWidget(row++, 1, mpSelectionCurso);
		
		flexTable.setWidget(row, 0, lblNomePeriodo);flexTable.setWidget(row, 1, txtNomePeriodo);flexTable.setWidget(row++, 2, lblErroNomePeriodo);
		flexTable.setWidget(row, 0, lblDescricaoPeriodo);flexTable.setWidget(row++, 1, txtDescricaoPeriodo);
		flexTable.setWidget(row, 0, lblObjetivoPeriodo);flexTable.setWidget(row++, 1, txtObjetivoPeriodo);
		flexTable.setWidget(row, 0, lblDateInicial);flexTable.setWidget(row++, 1, mpDateBoxInicial);
		flexTable.setWidget(row, 0, lblDateFinal);flexTable.setWidget(row++, 1, mpDateBoxFinal);

		MpImageButton btnSave = new MpImageButton(txtConstants.geralSalvar(), "images/save.png");
		btnSave.addClickHandler(new ClickHandlerSave());
		MpImageButton btnClean = new MpImageButton(txtConstants.geralLimpar(), "images/erase.png");
		btnClean.addClickHandler(new ClickHandlerClean());

		VerticalPanel vFormPanel = new VerticalPanel();

		Grid gridSave = new Grid(1, 3);
		gridSave.setCellSpacing(2);
		gridSave.setCellPadding(2);
		{
			int i = 0;
			gridSave.setWidget(0, i++, btnSave);
			gridSave.setWidget(0, i++, btnClean);
			gridSave.setWidget(0, i++, hPanelLoading);
		}
		
		MpSpacePanel mpSpacePanel = new MpSpacePanel();
		mpSpacePanel.setWidth(Integer.toString(TelaInicialCurso.intWidthTable-700)+"px");

		vFormPanel.add(flexTable);
		vFormPanel.add(gridSave);
		vFormPanel.add(mpSpacePanel);
		
		/***********************Begin Callbacks**********************/

		// Callback para adicionar Periodo.
		callbackAddPeriodo = new AsyncCallback<Integer>() {

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.periodoErroSalvar());
				mpDialogBoxWarning.showDialog();
			}

			@Override
			public void onSuccess(Integer result) {
				hPanelLoading.setVisible(false);
				int isSuccess = result;
				if (isSuccess>0) {
//					txtNomePeriodo.getTextBox().setValue("");
//					txtDescricaoPeriodo.setValue("");
//					txtObjetivoPeriodo.setValue("");
//					mpDateBoxInicial.getDate().setValue(null);
//					mpDateBoxFinal.getDate().setValue(null);
					cleanFields();
					mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
					mpDialogBoxConfirm.setBodyText(txtConstants.periodoSalvoSucesso());
					mpDialogBoxConfirm.showDialog();
					telaInicialPeriodo.populateGrid();
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.periodoErroSalvar());
					mpDialogBoxWarning.showDialog();
				}
			}
		};

		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize(Integer.toString(TelaInicialPeriodo.intWidthTable+30)+"px",Integer.toString(TelaInicialPeriodo.intHeightTable-40)+"px");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(vFormPanel);

		super.add(scrollPanel);
//		super.add(vFormPanel);

	}

	
	
	/****************Begin Event Handlers*****************/
	
	private class ClickHandlerSave implements ClickHandler {

		public void onClick(ClickEvent event) {

//			if (txtNomePeriodo == null || txtNomePeriodo.getTextBox().getText().isEmpty()) {
//
//				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//				mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.periodoNome()));
//				mpDialogBoxWarning.showDialog();
//
//			} else {
			if(checkFieldsValidator()){			

				hPanelLoading.setVisible(true);

				int intIdCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));

				Periodo periodo = new Periodo();
				periodo.setIdCurso(intIdCurso);
				periodo.setNomePeriodo(txtNomePeriodo.getText());
				periodo.setDescricao(txtDescricaoPeriodo.getText());
				periodo.setObjetivo(txtObjetivoPeriodo.getText());
				periodo.setDataInicial(mpDateBoxInicial.getDate().getValue());
				periodo.setDataFinal(mpDateBoxFinal.getDate().getValue());

				GWTServicePeriodo.Util.getInstance().AdicionarPeriodo(periodo, callbackAddPeriodo);

			}

		}
	}
	
	private class ClickHandlerClean implements ClickHandler {
		public void onClick(ClickEvent event) {
			cleanFields();
			
		}
	}	
	
	/****************End Event Handlers*****************/

	
	public boolean checkFieldsValidator(){
		boolean isFieldsOk = false;
		
		if(FieldVerifier.isValidName(txtNomePeriodo.getText())){
			isFieldsOk=true;	
			lblErroNomePeriodo.hideErroMessage();
		}
		else{
			isFieldsOk=false;
			lblErroNomePeriodo.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.periodoNome()));
		}
		
		return isFieldsOk;
	}
	
	
	private void cleanFields(){
		lblErroNomePeriodo.hideErroMessage();
		txtNomePeriodo.setValue("");
		txtDescricaoPeriodo.setValue("");
		txtObjetivoPeriodo.setValue("");
		mpDateBoxInicial.getDate().setValue(null);
		mpDateBoxFinal.getDate().setValue(null);
	}


	public void updateClientData() {
		mpSelectionCurso.populateComboBox();
	}
	
	
	
	
}
