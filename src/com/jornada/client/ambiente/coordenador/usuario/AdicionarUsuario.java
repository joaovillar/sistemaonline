package com.jornada.client.ambiente.coordenador.usuario;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.jornada.client.ambiente.coordenador.curso.TelaInicialCurso;
import com.jornada.client.classes.listBoxes.MpSelectionTipoUsuario;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelTextBoxError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpacePanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Usuario;

public class AdicionarUsuario extends VerticalPanel {

	private AsyncCallback<Boolean> callbackAddUsuario;
//	private AsyncCallback<ArrayList<TipoUsuario>> callBackPopulateTipoUsuarioComboBox;	

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpLoadingSave = new MpPanelLoading("images/radar.gif");
//	MpPanelLoading mpLoadingListTipoUsuario = new MpPanelLoading("images/radar.gif");
	
	private TextBox txtPrimeiroNome;
	private TextBox txtSobreNome;
	private TextBox txtCPF;
	private TextBox txtEmail;
	private MpDateBoxWithImage dataNascimento;
	private TextBox txtTelefoneCelular;
	private TextBox txtTelefoneResidencial;
	private TextBox txtTelefoneComercial;
	
	//private ListBox selectTipoUsuario;
	private MpSelectionTipoUsuario selectTipoUsuario;
	
	private TextBox txtLogin;
	private PasswordTextBox txtSenha;
	
	private MpLabelTextBoxError lblErroPrimeiroNome;
	private MpLabelTextBoxError lblErroSobreNome;
	private MpLabelTextBoxError lblErroEmail;
	private MpLabelTextBoxError lblErroLogin;
	private MpLabelTextBoxError lblErroSenha;	

	
	@SuppressWarnings("unused")
	private TelaInicialUsuario telaInicialUsuario;
	
	TextConstants txtConstants;

	@SuppressWarnings("deprecation")
	public AdicionarUsuario(final TelaInicialUsuario telaInicialUsuario) {
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialUsuario=telaInicialUsuario;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpLoadingSave.setTxtLoading(txtConstants.geralCarregando());
		mpLoadingSave.show();
		mpLoadingSave.setVisible(false);
		
//		mpLoadingListTipoUsuario.setTxtLoading("");
//		mpLoadingListTipoUsuario.show();
//		mpLoadingListTipoUsuario.setVisible(false);

		Grid layout = new Grid(11,3);
		// layout.setStyleName("single_line_table");
		// layout.setWidth("800px");
		layout.setCellSpacing(3);
		layout.setCellPadding(3);
		layout.setBorderWidth(0);
		layout.setSize(Integer.toString(TelaInicialUsuario.intWidthTable),Integer.toString(TelaInicialUsuario.intHeightTable));
//		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		// Add a title to the form
		// layout.setHTML(0, 0, "");
//		cellFormatter.setColSpan(0, 0, 0);
//		cellFormatter.setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_CENTER);
		txtPrimeiroNome = new TextBox();		
		txtSobreNome = new TextBox(); 
		txtCPF = new TextBox();
		txtEmail = new TextBox();
		dataNascimento = new MpDateBoxWithImage();
		txtTelefoneCelular = new TextBox();
		txtTelefoneResidencial = new TextBox();
		txtTelefoneComercial  = new TextBox();
		selectTipoUsuario = new MpSelectionTipoUsuario();
		txtLogin = new TextBox();
		txtSenha = new PasswordTextBox();
		

		
//		txtSenha.getElement().setPropertyString("type", "password");
//		DatePicker date = new DatePicker();
//		date.setYearAndMonthDropdownVisible(true);
		
		dataNascimento.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
		dataNascimento.getDate().setWidth("170px");		
		

		txtPrimeiroNome.setStyleName("design_text_boxes");
		txtSobreNome.setStyleName("design_text_boxes"); 
		txtCPF.setStyleName("design_text_boxes");
		txtEmail.setStyleName("design_text_boxes");
//		dataNascimento.setStyleName("design_text_boxes");
		txtTelefoneCelular.setStyleName("design_text_boxes");
		txtTelefoneResidencial.setStyleName("design_text_boxes");
		txtTelefoneComercial.setStyleName("design_text_boxes");
		selectTipoUsuario.setStyleName("design_text_boxes");
		txtLogin.setStyleName("design_text_boxes");
		txtSenha.setStyleName("design_text_boxes");
		
		

		Label lblPrimeiroNome = new Label(txtConstants.usuarioPrimeiroNome());
		Label lblSobreNome = new Label(txtConstants.usuarioSobreNome());		
		Label lblCPF = new Label(txtConstants.usuarioCPF());		
		Label lblEmail = new Label(txtConstants.usuarioEmail());		
		Label lblDataNascimento = new Label(txtConstants.usuarioDataNascimento());
		Label lblTelefoneCelular = new Label(txtConstants.usuarioTelCelular());
		Label lblTelefoneResidencial = new Label(txtConstants.usuarioTelResidencial());
		Label lblTelefoneComercial = new Label(txtConstants.usuarioTelComercial());
		Label lblTipoUsuario = new Label(txtConstants.usuarioTipo());
		Label lblLogin = new Label(txtConstants.usuario());
		Label lblSenha = new Label(txtConstants.usuarioSenha());
		
		lblErroPrimeiroNome = new MpLabelTextBoxError();
		lblErroSobreNome = new MpLabelTextBoxError();
		lblErroEmail = new MpLabelTextBoxError();
		lblErroLogin = new MpLabelTextBoxError();
		lblErroSenha = new MpLabelTextBoxError();
		
		lblPrimeiroNome.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		lblSobreNome.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblCPF.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		lblEmail.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDataNascimento.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		lblTelefoneCelular.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblTelefoneResidencial.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblTelefoneComercial.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblTipoUsuario.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblLogin.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblSenha.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
		lblTipoUsuario.setStyleName("design_label");		
		lblPrimeiroNome.setStyleName("design_label");
		lblSobreNome.setStyleName("design_label");
		lblCPF.setStyleName("design_label");		
		lblEmail.setStyleName("design_label");
		lblDataNascimento.setStyleName("design_label");
		lblTelefoneCelular.setStyleName("design_label");
		lblTelefoneResidencial.setStyleName("design_label");
		lblTelefoneComercial.setStyleName("design_label");		
		lblLogin.setStyleName("design_label");
		lblSenha.setStyleName("design_label");		
		
		selectTipoUsuario.setWidth("350px");
		txtPrimeiroNome.setWidth("350px");
		txtSobreNome.setWidth("350px"); 
		txtCPF.setWidth("350px");
		txtEmail.setWidth("350px");
//		dataNascimento.setWidth("350px");
		txtTelefoneCelular.setWidth("350px");
		txtTelefoneResidencial.setWidth("350px");
		txtTelefoneComercial.setWidth("350px");
		selectTipoUsuario.setWidth("350px");
		txtLogin.setWidth("350px");
		txtSenha.setWidth("350px");
		
		dataNascimento.getDate().getDatePicker().setYearAndMonthDropdownVisible(true);
		dataNascimento.getDate().getDatePicker().setYearArrowsVisible(true);


		// Add some standard form options
		int row = 0;

		layout.setWidget(row, 0, lblTipoUsuario); layout.setWidget(row++, 1, selectTipoUsuario);
		layout.setWidget(row, 0, lblPrimeiroNome);layout.setWidget(row, 1, txtPrimeiroNome);layout.setWidget(row++, 2, lblErroPrimeiroNome);
//		layout.setWidget(row++, 2, new InlineHTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
		layout.setWidget(row, 0, lblSobreNome);layout.setWidget(row, 1, txtSobreNome);layout.setWidget(row++, 2, lblErroSobreNome);
		layout.setWidget(row, 0, lblEmail);layout.setWidget(row, 1, txtEmail);layout.setWidget(row++, 2, lblErroEmail);
		layout.setWidget(row, 0, lblLogin);layout.setWidget(row, 1, txtLogin);layout.setWidget(row++, 2, lblErroLogin);
		layout.setWidget(row, 0, lblSenha);layout.setWidget(row, 1, txtSenha);layout.setWidget(row++, 2, lblErroSenha);		
		layout.setWidget(row, 0, lblCPF);layout.setWidget(row++, 1, txtCPF);	
		layout.setWidget(row, 0, lblDataNascimento);layout.setWidget(row++, 1, dataNascimento);
//		DatePickerWithYearSelector dateYear = new DatePickerWithYearSelector();
//		layout.setWidget(row, 0, lblDataNascimento);layout.setWidget(row++, 1, dateYear);
//		layout.setWidget(row, 0, lblDataNascimento);layout.setWidget(row++, 1, date);
		
		layout.setWidget(row, 0, lblTelefoneCelular);layout.setWidget(row++, 1, txtTelefoneCelular);
		layout.setWidget(row, 0, lblTelefoneResidencial);layout.setWidget(row++, 1, txtTelefoneResidencial);
		layout.setWidget(row, 0, lblTelefoneComercial);layout.setWidget(row++, 1, txtTelefoneComercial);
		

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
			gridSave.setWidget(0, i++, mpLoadingSave);
		}
		
		MpSpacePanel mpSpacePanel = new MpSpacePanel();
		mpSpacePanel.setWidth(Integer.toString(TelaInicialCurso.intWidthTable-700)+"px");

		vFormPanel.add(layout);
		vFormPanel.add(gridSave);
		vFormPanel.add(mpSpacePanel);

		
		
		/***********************Begin Callbacks**********************/

		// Callback para adicionar Disciplina.
		callbackAddUsuario = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroSalvar());
				mpDialogBoxWarning.showDialog();
			}

			@Override
			public void onSuccess(Boolean result) {
				// lblLoading.setVisible(false);
				mpLoadingSave.setVisible(false);
				boolean isSuccess = result;
				if (isSuccess) {

					mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
					mpDialogBoxConfirm.setBodyText(txtConstants.usuarioSalvo());
					mpDialogBoxConfirm.showDialog();
					telaInicialUsuario.getEditarUsuario().updateClientData();
					telaInicialUsuario.getAssociarPaisAlunos().updateClientData();
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroSalvar());
					mpDialogBoxWarning.showDialog();
				}
			}
		};
	

		/***********************End Callbacks**********************/


		super.add(vFormPanel);

	}


	
	
	/****************Begin Event Handlers*****************/
	
	private class ClickHandlerSave implements ClickHandler {

		public void onClick(ClickEvent event) {

//			if (txtPrimeiroNome == null || txtPrimeiroNome.getText().isEmpty()) {
//
//				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//				mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.usuarioPrimeiroNome()));
//				mpDialogBoxWarning.showDialog();
//
//			} else {
			if(checkFieldsValidator()){	
				mpLoadingSave.setVisible(true);

				int intTipoUsuario = Integer.parseInt(selectTipoUsuario.getValue(selectTipoUsuario.getSelectedIndex()));				

				Usuario usuario = new Usuario();
				
				usuario.setPrimeiroNome(txtPrimeiroNome.getText());
				usuario.setSobreNome(txtSobreNome.getText());
				usuario.setCpf(txtCPF.getText());
				usuario.setDataNascimento(dataNascimento.getDate().getValue());
				usuario.setIdTipoUsuario(intTipoUsuario);
				usuario.setEmail(txtEmail.getText());
				usuario.setTelefoneCelular(txtTelefoneCelular.getText());
				usuario.setTelefoneResidencial(txtTelefoneResidencial.getText());
				usuario.setTelefoneComercial(txtTelefoneComercial.getText());
				usuario.setLogin(txtLogin.getText());
				usuario.setSenha(txtSenha.getText());

				GWTServiceUsuario.Util.getInstance().AdicionarUsuario(usuario, callbackAddUsuario);

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
		boolean isFirstNameOk=false;
		boolean isEmailOk=false;
		boolean isLoginOk=false;
		boolean isSenhaOk=false;
		boolean isSobreNomeOk=false;
		
		
		if(FieldVerifier.isValidName(txtPrimeiroNome.getText())){
			isFirstNameOk=true;	
			lblErroPrimeiroNome.hideErroMessage();
		}else{
			isFirstNameOk=false;
			lblErroPrimeiroNome.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.usuarioPrimeiroNome()));
		}
		
		if(FieldVerifier.isValidName(txtSobreNome.getText())){
			isSobreNomeOk=true;	
			lblErroSobreNome.hideErroMessage();
		}else{
			isSobreNomeOk=false;
			lblErroSobreNome.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.usuarioSobreNome()));
		}		
		
		if(FieldVerifier.isValidEmail(txtEmail.getText())){
			isEmailOk=true;
			lblErroEmail.hideErroMessage();
		}else{
			isEmailOk=false;
			lblErroEmail.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.usuarioEmail()));
		}
		
		if(FieldVerifier.isValidName(txtLogin.getText())){
			isLoginOk=true;
			lblErroLogin.hideErroMessage();
		}else{
			isLoginOk=false;
			lblErroLogin.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.usuario()));
		}		
		
		
		if(FieldVerifier.isValidName(txtSenha.getText())){
			isSenhaOk=true;
			lblErroSenha.hideErroMessage();
		}else{
			isSenhaOk=false;
			lblErroSenha.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.usuarioSenha()));
		}			
		
		isFieldsOk = isFirstNameOk && isSobreNomeOk && isEmailOk && isLoginOk && isSenhaOk;

		
		return isFieldsOk;
	}	
	
	private void cleanFields(){
		lblErroPrimeiroNome.hideErroMessage();
		lblErroSobreNome.hideErroMessage();
		lblErroEmail.hideErroMessage();
		lblErroLogin.hideErroMessage();
		lblErroSenha.hideErroMessage();
		
		txtPrimeiroNome.setValue("");
		txtSobreNome.setValue(""); 
		txtCPF.setValue("");
		txtEmail.setValue("");
		dataNascimento.getDate().setValue(null);
		txtTelefoneCelular.setValue("");
		txtTelefoneResidencial.setValue("");
		txtTelefoneComercial.setValue("");
		txtLogin.setValue("");
		txtSenha.setValue("");
	}
	
}
