package com.jornada.client.ambiente.coordenador.curso;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.jornada.client.classes.listBoxes.MpListBoxMediaNota;
import com.jornada.client.classes.listBoxes.MpListBoxPorcentagemPresenca;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelTextBoxError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpacePanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceCurso;
import com.jornada.client.service.GWTServiceCursoAsync;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Curso;


public class AdicionarCurso extends VerticalPanel {

	private AsyncCallback<Integer> callbackAddCurso;
	
	
	private AdicionarCursoDeUmTemplate adicionarCursoDeUmTemplate;


	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading hPanelLoading = new MpPanelLoading("images/radar.gif");


	private TextBox txtNomeCurso;
	private TextArea txtDescricaoCurso;
	private TextArea txtEmentaCurso;
	private MpListBoxMediaNota mpListBoxMediaNota;
	private MpListBoxPorcentagemPresenca mpListBoxPorcentagemPresenca;	
	private MpDateBoxWithImage mpDateBoxInicial;
	private MpDateBoxWithImage mpDateBoxFinal;
	
	MpLabelTextBoxError lblErroNomeCurso;
	
	@SuppressWarnings("unused")
	private TelaInicialCurso telaInicialCurso;
	
	TextConstants txtConstants;
	
	
	VerticalPanel vPanelAddCursoDeTemplate;
	VerticalPanel vPanelAddNovoCurso;
//	FlexTable flexTableAddCourseFromTemplate;

	@SuppressWarnings("deprecation")
	public AdicionarCurso(final TelaInicialCurso telaInicialCurso) {
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialCurso = telaInicialCurso;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		hPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		hPanelLoading.show();
		hPanelLoading.setVisible(false);
		

		
		Grid gridUseTemplate = new Grid(1,3);
		RadioButton radioButtonYes = new RadioButton("useTemplate",txtConstants.geralSim());
		radioButtonYes.addValueChangeHandler(new ValueChangeHandlerYes());
		RadioButton radioButtonNo = new RadioButton("useTemplate", txtConstants.geralNao());
		radioButtonNo.addValueChangeHandler(new ValueChangeHandlerNo());
		radioButtonNo.setValue(true);
		Label lblUseTemplate = new Label(txtConstants.cursoUsarCursoJaCriado());
		lblUseTemplate.setStyleName("design_label");
		
		gridUseTemplate.setWidget(0, 0, lblUseTemplate);gridUseTemplate.setWidget(0, 1, radioButtonYes);gridUseTemplate.setWidget(0, 2, radioButtonNo);

		adicionarCursoDeUmTemplate = AdicionarCursoDeUmTemplate.getInstance(telaInicialCurso);
		
		vPanelAddCursoDeTemplate = new VerticalPanel();
		vPanelAddNovoCurso = new VerticalPanel();



		FlexTable flexTableAddNewCourse = new FlexTable();
		flexTableAddNewCourse.setCellSpacing(3);
		flexTableAddNewCourse.setCellPadding(3);
		flexTableAddNewCourse.setBorderWidth(0);
		FlexCellFormatter cellFormatter = flexTableAddNewCourse.getFlexCellFormatter();

		// Add a title to the form
		// layout.setHTML(0, 0, "");
		cellFormatter.setColSpan(0, 0, 0);
		cellFormatter.setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_CENTER);
		txtNomeCurso = new TextBox();
		txtDescricaoCurso = new TextArea();
		txtEmentaCurso = new TextArea();
		mpListBoxMediaNota = new MpListBoxMediaNota();
		mpListBoxPorcentagemPresenca = new MpListBoxPorcentagemPresenca();
		mpDateBoxInicial = new MpDateBoxWithImage();
		mpDateBoxInicial.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
		mpDateBoxFinal = new MpDateBoxWithImage();
		mpDateBoxFinal.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
		
		txtNomeCurso.setStyleName("design_text_boxes");
		txtDescricaoCurso.setStyleName("design_text_boxes");
		txtEmentaCurso.setStyleName("design_text_boxes");
//		txtMediaNota.setStyleName("design_text_boxes");
//		mpListBoxPorcentagemPresenca.setStyleName("design_text_boxes");


		Label lblNomeCurso = new Label(txtConstants.cursoNome());		
		Label lblDescricaoCurso = new Label(txtConstants.cursoDescricao());
		Label lblEmentaCurso = new Label(txtConstants.cursoEmenta());
		Label lblMediaNotaCurso = new Label(txtConstants.cursoMediaNota());
		Label lblPorcentagemPresencaCurso = new Label(txtConstants.cursoPorcentagemPresenca());
		Label lblDataInicial = new Label(txtConstants.cursoDataInicial());
		Label lblDataFinal = new Label(txtConstants.cursoDataFinal());
		
		lblErroNomeCurso = new MpLabelTextBoxError();

		
		lblNomeCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDescricaoCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		lblEmentaCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblMediaNotaCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		lblPorcentagemPresencaCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDataInicial.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		lblDataFinal.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		
		
		lblNomeCurso.setStyleName("design_label");
		lblDescricaoCurso.setStyleName("design_label");
		lblEmentaCurso.setStyleName("design_label");		
		lblMediaNotaCurso.setStyleName("design_label");
		lblPorcentagemPresencaCurso.setStyleName("design_label");		
		lblDataInicial.setStyleName("design_label");
		lblDataFinal.setStyleName("design_label");
		txtNomeCurso.setWidth("350px");
		txtDescricaoCurso.setSize("350px", "50px");
		txtEmentaCurso.setSize("350px", "50px");
//		txtMediaNota.setWidth("150px");
		mpListBoxMediaNota.setWidth("80px");
		mpListBoxPorcentagemPresenca.setWidth("80px");
		mpDateBoxInicial.getDate().setWidth("170px");
		mpDateBoxFinal.getDate().setWidth("170px");
		
		

		// Add some standard form options
		int row = 1;
		flexTableAddNewCourse.setWidget(row, 0, lblNomeCurso)     ;flexTableAddNewCourse.setWidget(row, 1, txtNomeCurso); flexTableAddNewCourse.setWidget(row++, 2, lblErroNomeCurso);
		flexTableAddNewCourse.setWidget(row, 0, lblDescricaoCurso);flexTableAddNewCourse.setWidget(row++, 1, txtDescricaoCurso);
		flexTableAddNewCourse.setWidget(row, 0, lblEmentaCurso)   ;flexTableAddNewCourse.setWidget(row++, 1, txtEmentaCurso);
		flexTableAddNewCourse.setWidget(row, 0, lblMediaNotaCurso);flexTableAddNewCourse.setWidget(row++, 1, mpListBoxMediaNota);
		flexTableAddNewCourse.setWidget(row, 0, lblPorcentagemPresencaCurso)   ;flexTableAddNewCourse.setWidget(row++, 1, mpListBoxPorcentagemPresenca);
		flexTableAddNewCourse.setWidget(row, 0, lblDataInicial)   ;flexTableAddNewCourse.setWidget(row++, 1, mpDateBoxInicial);
		flexTableAddNewCourse.setWidget(row, 0, lblDataFinal)     ;flexTableAddNewCourse.setWidget(row++, 1, mpDateBoxFinal);

		MpImageButton btnSave = new MpImageButton(txtConstants.cursoSalvar(), "images/save.png");
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
		
		
		vPanelAddCursoDeTemplate.add(adicionarCursoDeUmTemplate);
		vPanelAddCursoDeTemplate.setVisible(false);
		
		vPanelAddNovoCurso.add(flexTableAddNewCourse);
		vPanelAddNovoCurso.add(gridSave);
		
		
		vFormPanel.add(gridUseTemplate);
		vFormPanel.add(vPanelAddNovoCurso);	
		vFormPanel.add(vPanelAddCursoDeTemplate);
//		vFormPanel.add(gridSave);		
		vFormPanel.add(mpSpacePanel);

		
		
		/***********************Begin Callbacks**********************/

		// Callback para adicionar Curso.
		callbackAddCurso = new AsyncCallback<Integer>() {

			public void onFailure(Throwable caught) {
				hPanelLoading.setVisible(false);
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.cursoErroSalvar());
				mpDialogBoxWarning.showDialog();
			}

			@Override
			public void onSuccess(Integer result) {
				// lblLoading.setVisible(false);
				hPanelLoading.setVisible(false);
				int isSuccess = result;
				if (isSuccess!=0) {
					cleanFields();
					mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
					mpDialogBoxConfirm.setBodyText(txtConstants.cursoSalvoSucesso());
					mpDialogBoxConfirm.showDialog();
//					EditarCurso.dataGrid().redraw();
					telaInicialCurso.updatePopulateGrid();
					telaInicialCurso.updateAssociarCurso();
					getAdicionarCursoDeUmTemplate().updateListBoxCurso();
					
				} else {
					hPanelLoading.setVisible(false);
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.cursoErroSalvar());
					mpDialogBoxWarning.showDialog();
				}
			}
		};


		/***********************End Callbacks**********************/

		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize(Integer.toString(TelaInicialCurso.intWidthTable+30)+"px",Integer.toString(TelaInicialCurso.intHeightTable-40)+"px");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(vFormPanel);
		
		super.add(scrollPanel);
		
//		super.add(vFormPanel);

	}

	public static GWTServiceCursoAsync getServiceCursoAsync() {
		return GWT.create(GWTServiceCurso.class);
	}


	
	
	/****************Begin Event Handlers*****************/
	
	private class ClickHandlerSave implements ClickHandler {

		public void onClick(ClickEvent event) {

//			if (txtNomeCurso.getTextBox() == null || txtNomeCurso.getTextBox().getText().isEmpty()) {
//			if(checkFieldsValidator()==false){
//
//				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//				mpDialogBoxWarning.setBodyText(txtConstants.cursoCampoNomeObrigatorio());
//				mpDialogBoxWarning.showDialog();
//
//			} else {
			if(checkFieldsValidator()){
				hPanelLoading.setVisible(true);

				Curso curso = new Curso();
				curso.setNome(txtNomeCurso.getText());
				curso.setDescricao(txtDescricaoCurso.getText());
				curso.setEmenta(txtEmentaCurso.getText());
				curso.setDataInicial(mpDateBoxInicial.getDate().getValue());
				curso.setDataFinal(mpDateBoxFinal.getDate().getValue());
				curso.setMediaNota(mpListBoxMediaNota.getValue(mpListBoxMediaNota.getSelectedIndex()));
				curso.setPorcentagemPresenca(mpListBoxPorcentagemPresenca.getValue(mpListBoxPorcentagemPresenca.getSelectedIndex()));

//				getServiceCursoAsync().AdicionarCurso(curso,callbackAddCurso);
				GWTServiceCurso.Util.getInstance().AdicionarCurso(curso, callbackAddCurso);

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
		
		if(FieldVerifier.isValidName(txtNomeCurso.getText())){
			isFieldsOk=true;	
			lblErroNomeCurso.hideErroMessage();
		}
		else{
			isFieldsOk=false;
			lblErroNomeCurso.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.cursoNome()));
		}
		
		return isFieldsOk;
	}
	
	
	private void cleanFields(){
		lblErroNomeCurso.hideErroMessage();
		txtNomeCurso.setValue("");
		txtDescricaoCurso.setValue("");
		txtEmentaCurso.setValue("");
		mpDateBoxInicial.getDate().setValue(null);
		mpDateBoxFinal.getDate().setValue(null);
	}
	
	
	private class ValueChangeHandlerYes implements ValueChangeHandler<Boolean>{
		@Override
		public void onValueChange(ValueChangeEvent<Boolean> event) {
            if(event.getValue() == true){
            	vPanelAddNovoCurso.setVisible(false);
            	vPanelAddCursoDeTemplate.setVisible(true);
            }			
		}
	}
	
	private class ValueChangeHandlerNo implements ValueChangeHandler<Boolean>{
		@Override
		public void onValueChange(ValueChangeEvent<Boolean> event) {
            if(event.getValue() == true){
            	vPanelAddCursoDeTemplate.setVisible(false);
            	vPanelAddNovoCurso.setVisible(true);
            	
            }			
		}
	}

	public final AdicionarCursoDeUmTemplate getAdicionarCursoDeUmTemplate() {
		return adicionarCursoDeUmTemplate;
	}

	
	

}
