package com.jornada.client.ambiente.coordenador.curso;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.jornada.client.classes.listBoxes.MpSelectionCurso;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelTextBoxError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceCurso;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Curso;

public class AdicionarCursoDeUmTemplate extends VerticalPanel{
	
	private AsyncCallback<Boolean> callbackAddCursoTemplate;
	
	TextConstants txtConstants;
	
	@SuppressWarnings("unused")
	private TelaInicialCurso telaInicialCurso;
	
	private static AdicionarCursoDeUmTemplate uniqueInstance;	
	
	private TextBox txtFiltroCursoEstrutura;	
	private TextBox txtNomeCursoEstrutura;
	private TextBox txtFiltroCursoImportar;	
	
	private MpImageButton btnFiltrarEstrutura;
	private MpImageButton btnFiltrarImportarAluno;
	
	private MpDateBoxWithImage mpDateBoxInicial;
	private MpDateBoxWithImage mpDateBoxFinal;
	
	private MpLabelTextBoxError lblErroNomeCurso;
	
	private MpSelectionCurso listBoxCursoEstrutura;
	private MpSelectionCurso listBoxCursoImportarAluno;
	
	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading hPanelLoading = new MpPanelLoading("images/radar.gif");
	
	
	public static AdicionarCursoDeUmTemplate getInstance(final TelaInicialCurso telaInicialCurso){		
		if(uniqueInstance==null){
			uniqueInstance = new AdicionarCursoDeUmTemplate(telaInicialCurso);		
		}		
		return uniqueInstance;
	}
	

	@SuppressWarnings("deprecation")
	private AdicionarCursoDeUmTemplate(final TelaInicialCurso telaInicialCurso){
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialCurso = telaInicialCurso;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		hPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		hPanelLoading.show();
		hPanelLoading.setVisible(false);
		
		
		Label lblCursoEstrutura = new Label(txtConstants.cursoTemplate());
		Label lblCursoNome = new Label(txtConstants.cursoNovoCurso());
		Label lblDataInicial = new Label(txtConstants.cursoDataInicial());
		Label lblDataFinal = new Label(txtConstants.cursoDataFinal());
		Label lblCursoImportarAlunos = new Label(txtConstants.cursoImportarAlunosDoCurso());
		lblErroNomeCurso = new MpLabelTextBoxError();
		
		listBoxCursoEstrutura = new MpSelectionCurso();				
		txtFiltroCursoEstrutura = new TextBox();
		btnFiltrarEstrutura = new MpImageButton(txtConstants.cursoFiltrar(), "images/magnifier.png");
		
		
		txtNomeCursoEstrutura = new TextBox();
		mpDateBoxInicial = new MpDateBoxWithImage();		
		mpDateBoxFinal = new MpDateBoxWithImage();		

		listBoxCursoImportarAluno = new MpSelectionCurso();
		txtFiltroCursoImportar = new TextBox();
		btnFiltrarImportarAluno = new MpImageButton(txtConstants.cursoFiltrar(), "images/magnifier.png");
		
		listBoxCursoImportarAluno.setMultipleSelect(true);
		listBoxCursoImportarAluno.setVisibleItemCount(5);
		listBoxCursoImportarAluno.setSize("350px", "70px");


				
		
		mpDateBoxInicial.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));	
		mpDateBoxFinal.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
		
		lblCursoEstrutura.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblCursoNome.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDataInicial.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDataFinal.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblCursoImportarAlunos.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		
		lblCursoEstrutura.setStyleName("design_label");
		lblCursoNome.setStyleName("design_label");		
		lblCursoImportarAlunos.setStyleName("design_label");
		txtNomeCursoEstrutura.setStyleName("design_text_boxes");
		txtFiltroCursoEstrutura.setStyleName("design_text_boxes");	
		txtFiltroCursoImportar.setStyleName("design_text_boxes");
				
		txtNomeCursoEstrutura.setWidth("350px");
		mpDateBoxInicial.getDate().setWidth("170px");
		mpDateBoxFinal.getDate().setWidth("170px");
		
				
		txtFiltroCursoEstrutura.addKeyUpHandler(new EnterKeyUpHandlerFiltrarCursoEstrutura());		
		btnFiltrarEstrutura.addClickHandler(new ClickHandlerFiltrarCursoEstrutura());	
		
		txtFiltroCursoImportar.addKeyUpHandler(new EnterKeyUpHandlerFiltrarCursoImportarAluno());		
		btnFiltrarImportarAluno.addClickHandler(new ClickHandlerFiltrarCursoImportarAluno());
		
		
		FlexTable flexTable = new FlexTable();
		flexTable.setCellSpacing(3);
		flexTable.setCellPadding(3);
		flexTable.setBorderWidth(0);
		int row=0;
		
		
		flexTable.setWidget(row, 0, lblCursoNome);flexTable.setWidget(row, 1, txtNomeCursoEstrutura);flexTable.setWidget(row, 2, lblErroNomeCurso);flexTable.getFlexCellFormatter().setColSpan(row++, 2, 3);
		flexTable.setWidget(row, 0, lblDataInicial);flexTable.setWidget(row++, 1, mpDateBoxInicial);
		flexTable.setWidget(row, 0, lblDataFinal);flexTable.setWidget(row++, 1, mpDateBoxFinal);
		flexTable.setWidget(row, 0, lblCursoEstrutura);flexTable.setWidget(row, 1, listBoxCursoEstrutura);flexTable.setWidget(row, 2, txtFiltroCursoEstrutura);flexTable.setWidget(row++, 3, btnFiltrarEstrutura);
		flexTable.setWidget(row, 0, lblCursoImportarAlunos);flexTable.setWidget(row, 1, listBoxCursoImportarAluno);flexTable.setWidget(row, 2, txtFiltroCursoImportar);flexTable.setWidget(row++, 3, btnFiltrarImportarAluno);
		flexTable.setWidget(row, 0, new InlineHTML("&nbsp;"));
		
		
		
		
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

		vFormPanel.add(flexTable);
		vFormPanel.add(gridSave);
		
		
		// Callback para adicionar Curso.
		callbackAddCursoTemplate = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				hPanelLoading.setVisible(false);				
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.cursoErroSalvar());
				mpDialogBoxWarning.showDialog();
			}

			@Override
			public void onSuccess(Boolean result) {
				// lblLoading.setVisible(false);
				hPanelLoading.setVisible(false);
				boolean isSuccess = result;
				if (isSuccess) {
					updateListBoxCurso();
					cleanFields();
					mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
					mpDialogBoxConfirm.setBodyText(txtConstants.cursoSalvoSucesso());
					mpDialogBoxConfirm.showDialog();
//					EditarCurso.dataGrid().redraw();
					telaInicialCurso.updatePopulateGrid();
					telaInicialCurso.updateAssociarCurso();
					
				} else {
					hPanelLoading.setVisible(false);
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.cursoErroSalvar());
					mpDialogBoxWarning.showDialog();
				}
			}
		};


		/***********************End Callbacks**********************/		
		
		
		
		super.add(vFormPanel);
		
		
	}
	
	
	private class EnterKeyUpHandlerFiltrarCursoEstrutura implements KeyUpHandler{
		public void onKeyUp(KeyUpEvent event){
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				listBoxCursoEstrutura.filterComboBox(txtFiltroCursoEstrutura.getText());
			}
		}
	}	
	
	private class ClickHandlerFiltrarCursoEstrutura implements ClickHandler {
		public void onClick(ClickEvent event) {
			listBoxCursoEstrutura.filterComboBox(txtFiltroCursoEstrutura.getText());
		}
	}
	
	
	private class EnterKeyUpHandlerFiltrarCursoImportarAluno implements KeyUpHandler{
		public void onKeyUp(KeyUpEvent event){
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				listBoxCursoImportarAluno.filterComboBox(txtFiltroCursoImportar.getText());
			}
		}
	}	
	
	private class ClickHandlerFiltrarCursoImportarAluno implements ClickHandler {
		public void onClick(ClickEvent event) {
			listBoxCursoImportarAluno.filterComboBox(txtFiltroCursoImportar.getText());
		}
	}
	
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
				curso.setNome(txtNomeCursoEstrutura.getText());
				curso.setDataInicial(mpDateBoxInicial.getDate().getValue());
				curso.setDataFinal(mpDateBoxFinal.getDate().getValue());
				
				int idCursoEstrutura = Integer.parseInt(listBoxCursoEstrutura.getValue(listBoxCursoEstrutura.getSelectedIndex()));
//				int idCursoImportarAlunos = Integer.parseInt(listBoxCursoImportarAluno.getValue(listBoxCursoImportarAluno.getSelectedIndex()));
				
				ArrayList<Integer> arraySelectedImportarAluno = new ArrayList<Integer>();
				
				for(int i=0;i<listBoxCursoImportarAluno.getItemCount();i++){
					if(listBoxCursoImportarAluno.isItemSelected(i)){
						int intIdCursoSelected = Integer.parseInt(listBoxCursoImportarAluno.getValue(i));
						arraySelectedImportarAluno.add(intIdCursoSelected);
					}
				}
				
				Integer[] intIdCursosImportarAlunosSelected = arraySelectedImportarAluno.toArray(new Integer[]{});

//				getServiceCursoAsync().AdicionarCurso(curso,callbackAddCurso);
				GWTServiceCurso.Util.getInstance().AdicionarCursoTemplate(idCursoEstrutura, intIdCursosImportarAlunosSelected, curso, callbackAddCursoTemplate);

			}

		}
	}	
	
	
	
	private class ClickHandlerClean implements ClickHandler {

		public void onClick(ClickEvent event) {
			cleanFields();
			
		}


	}	
	
	public boolean checkFieldsValidator(){
		boolean isFieldsOk = false;
		
		if(FieldVerifier.isValidName(txtNomeCursoEstrutura.getText())){
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
		txtNomeCursoEstrutura.setValue("");
		mpDateBoxInicial.getDate().setValue(null);
		mpDateBoxFinal.getDate().setValue(null);
		listBoxCursoImportarAluno.setSelectItem("");
	}
	
	protected void updateListBoxCurso(){
		listBoxCursoEstrutura.populateComboBox();
	}
		

}
