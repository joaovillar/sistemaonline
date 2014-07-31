package com.jornada.client.ambiente.coordenador.usuario;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.jornada.client.ambiente.coordenador.curso.TelaInicialCurso;
import com.jornada.client.classes.listBoxes.MpListBoxEstados;
import com.jornada.client.classes.listBoxes.MpListBoxSexo;
import com.jornada.client.classes.listBoxes.MpListBoxTipoPais;
import com.jornada.client.classes.listBoxes.MpSelectionTipoUsuario;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceHorizontalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;

public class AdicionarUsuario extends VerticalPanel {

	private AsyncCallback<String> callbackAdicionarAtualizarUsuario;
	
	// private AsyncCallback<ArrayList<TipoUsuario>>
	// callBackPopulateTipoUsuarioComboBox;

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpLoadingSave = new MpPanelLoading("images/radar.gif");
	// MpPanelLoading mpLoadingListTipoUsuario = new
	// MpPanelLoading("images/radar.gif");

	FlexTable flexTable;

	private TextBox txtPrimeiroNome;
	private TextBox txtSobreNome;
	private TextBox txtCpf;
	private TextBox txtRg;
	private TextBox txtLogin;
	private PasswordTextBox txtSenha;
	private TextBox txtEmail;
	private MpDateBoxWithImage dataNascimento;
	private TextBox txtTelefoneCelular;
	private TextBox txtTelefoneResidencial;
	private TextBox txtTelefoneComercial;
	private TextBox txtEndereco;
	private TextBox txtNumRes;
	private TextBox txtBairro;
	private TextBox txtCidade;
	private TextBox txtCep;
	private MpSelectionTipoUsuario selectTipoUsuario;
	private MpListBoxEstados selectEstados;
	private MpListBoxSexo selectSexo;
	private MpListBoxTipoPais selectTipoPais;

	private int HIDE_ALUNO_MATRICULA;
	private int HIDE_ALUNO_SITUACAO_PAIS;
	private int HIDE_PAIS_RESPONSAVEIS;

	Label lblPrimeiroNome;
	Label lblSobreNome;
	Label lblCPF;
	Label lblRG;
	Label lblSexo;
	Label lblEmail;
	Label lblDataNascimento;
	Label lblTelefoneCelular;
	Label lblTelefoneResidencial;
	Label lblTelefoneComercial;
	Label lblTipoUsuario;
	Label lblLogin;
	Label lblSenha;
	Label lblEndereco;
	Label lblNumRes;
	Label lblBairro;
	Label lblCidade;
	Label lblEstado;
	Label lblCep;
	Label lblEmpresa;
	Label lblCargo;

	// Se for Pai
	private TextBox txtEmpresa;
	private TextBox txtCargo;
	private Label lblResponsavel;
	private Label lblTipoPais;
	private CheckBox checkBoxRespFinanceiro;
	private CheckBox checkBoxRespAcademico;
	private Grid gridResponsavel;

	// Se for aluno
	private Label lblMatricula;
	private Label lblDataMatr;
	private Label lblSituacaoDosResp;
	private TextBox txtMatricula;
	private TextBox txtSituacaoPaisOutros;
	private Grid gridSituacaoResp;
	private RadioButton radioButtonCasados;
	private RadioButton radioButtonSeparados;
	private RadioButton radioButtonOutros;
	private MpDateBoxWithImage dataMatr;

	boolean isAdicionarOperation = false;
	 private Usuario usuarioParaAtualizar;

	@SuppressWarnings("unused")
	private TelaInicialUsuario telaInicialUsuario;

	TextConstants txtConstants;

	private static AdicionarUsuario uniqueInstanceAdicionar;
	private static AdicionarUsuario uniqueInstanceAtualizar;

	public static AdicionarUsuario getInstanceAdicionar(TelaInicialUsuario telaInicialUsuario) {

		if (uniqueInstanceAdicionar == null) {
			uniqueInstanceAdicionar = new AdicionarUsuario(telaInicialUsuario,true);
		}

		return uniqueInstanceAdicionar;

	}

	public static AdicionarUsuario getInstanceAtualizar(TelaInicialUsuario telaInicialUsuario, Usuario usuario) {

		
		if (uniqueInstanceAtualizar == null) {
			uniqueInstanceAtualizar = new AdicionarUsuario(telaInicialUsuario,false);
			uniqueInstanceAtualizar.usuarioParaAtualizar = usuario;
			
		} else {
			uniqueInstanceAtualizar.usuarioParaAtualizar = usuario;
			uniqueInstanceAtualizar.popularUsuario(usuario);
		}

		return uniqueInstanceAtualizar;

	}

	@SuppressWarnings("deprecation")
	private AdicionarUsuario(final TelaInicialUsuario telaInicialUsuario, final boolean isAdicionarOperation) {

		txtConstants = GWT.create(TextConstants.class);
		
		this.isAdicionarOperation = isAdicionarOperation;

		this.telaInicialUsuario = telaInicialUsuario;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpLoadingSave.setTxtLoading(txtConstants.geralCarregando());
		mpLoadingSave.show();
		mpLoadingSave.setVisible(false);

		// Add a title to the form
		txtMatricula = new TextBox();
		dataMatr = new MpDateBoxWithImage();
		txtPrimeiroNome = new TextBox();
		txtSobreNome = new TextBox();
		txtCpf = new TextBox();
		txtRg = new TextBox();
		txtEmail = new TextBox();
		dataNascimento = new MpDateBoxWithImage();
		txtTelefoneCelular = new TextBox();
		txtTelefoneResidencial = new TextBox();
		txtTelefoneComercial = new TextBox();
		txtLogin = new TextBox();
		txtSenha = new PasswordTextBox();
		txtEndereco = new TextBox();
		txtNumRes = new TextBox();
		txtBairro = new TextBox();
		txtCidade = new TextBox();
		txtCep = new TextBox();
		txtEmpresa = new TextBox();
		txtCargo = new TextBox();
		txtSituacaoPaisOutros = new TextBox();

		checkBoxRespFinanceiro = new CheckBox(txtConstants.usuarioRespFinanceiro());
		checkBoxRespAcademico = new CheckBox(txtConstants.usuarioRespAcademico());

		// txtMatricula.setVisible(false);
		// txtEmpresa.setVisible(false);
		// txtCargo.setVisible(false);

		selectTipoUsuario = new MpSelectionTipoUsuario();
		selectEstados = new MpListBoxEstados();
		selectSexo = new MpListBoxSexo();
		selectTipoPais = new MpListBoxTipoPais();

		selectTipoPais.setVisible(false);

		selectTipoUsuario.addChangeHandler(new MpTipoUsuarioChangeHandler());

		dataNascimento.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
		dataNascimento.getDate().setWidth("170px");
		dataMatr.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
		dataMatr.getDate().setWidth("170px");

		txtMatricula.setStyleName("design_text_boxes");
		txtPrimeiroNome.setStyleName("design_text_boxes");
		txtSobreNome.setStyleName("design_text_boxes");
		txtCpf.setStyleName("design_text_boxes");
		txtRg.setStyleName("design_text_boxes");
		txtEmail.setStyleName("design_text_boxes");
		txtTelefoneCelular.setStyleName("design_text_boxes");
		txtTelefoneResidencial.setStyleName("design_text_boxes");
		txtTelefoneComercial.setStyleName("design_text_boxes");
		selectTipoUsuario.setStyleName("design_text_boxes");
		selectEstados.setStyleName("design_text_boxes");
		txtLogin.setStyleName("design_text_boxes");
		txtSenha.setStyleName("design_text_boxes");
		txtEndereco.setStyleName("design_text_boxes");
		txtNumRes.setStyleName("design_text_boxes");
		txtBairro.setStyleName("design_text_boxes");
		txtCidade.setStyleName("design_text_boxes");
		txtCep.setStyleName("design_text_boxes");
		txtEmpresa.setStyleName("design_text_boxes");
		txtCargo.setStyleName("design_text_boxes");
		txtSituacaoPaisOutros.setStyleName("design_text_boxes");

		lblDataMatr = new Label(txtConstants.usuarioDataMatricula());
		lblMatricula = new Label(txtConstants.usuarioMatricula());
		lblPrimeiroNome = new Label(txtConstants.usuarioPrimeiroNome());
		lblSobreNome = new Label(txtConstants.usuarioSobreNome());
		lblCPF = new Label(txtConstants.usuarioCPF());
		lblRG = new Label(txtConstants.usuarioRG());
		lblSexo = new Label(txtConstants.usuarioSexo());
		lblEmail = new Label(txtConstants.usuarioEmail());
		lblDataNascimento = new Label(txtConstants.usuarioDataNascimento());
		lblTelefoneCelular = new Label(txtConstants.usuarioTelCelular());
		lblTelefoneResidencial = new Label(txtConstants.usuarioTelResidencial());
		lblTelefoneComercial = new Label(txtConstants.usuarioTelComercial());
		lblTipoUsuario = new Label(txtConstants.usuarioTipo());
		lblLogin = new Label(txtConstants.usuario());
		lblSenha = new Label(txtConstants.usuarioSenha());
		lblEndereco = new Label(txtConstants.usuarioEndereco());
		lblNumRes = new Label(txtConstants.usuarioNumRes());
		lblBairro = new Label(txtConstants.usuarioBairro());
		lblCidade = new Label(txtConstants.usuarioCidade());
		lblEstado = new Label(txtConstants.usuarioEstado());
		lblCep = new Label(txtConstants.usuarioCep());
		lblEmpresa = new Label(txtConstants.usuarioEmpresa());
		lblCargo = new Label(txtConstants.usuarioCargo());
		lblResponsavel = new Label(txtConstants.usuarioResponsavel());
		lblTipoPais = new Label(txtConstants.usuarioTipoPai());
		lblSituacaoDosResp = new Label(txtConstants.usuarioSituacaoDosPais());

		radioButtonCasados = new RadioButton("useTemplate", txtConstants.usuarioCasados());
		radioButtonSeparados = new RadioButton("useTemplate", txtConstants.usuarioSeparados());
		radioButtonOutros = new RadioButton("useTemplate", txtConstants.usuarioOutros());
		radioButtonCasados.setValue(true);

		Image imgPrimeiroNome = new Image("images/bullet-red-icon.png");
		Image imgSobreNome = new Image("images/bullet-red-icon.png");
		Image imgLogin = new Image("images/bullet-red-icon.png");
		Image imgSenha = new Image("images/bullet-red-icon.png");
		Image imgEmail = new Image("images/bullet-red-icon.png");

		lblDataMatr.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblMatricula.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblPrimeiroNome.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblSobreNome.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblCPF.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblRG.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblSexo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblEmail.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblDataNascimento.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblTelefoneCelular.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblTelefoneResidencial.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblTelefoneComercial.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblTipoUsuario.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblLogin.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblSenha.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblEndereco.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblNumRes.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblBairro.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblCidade.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblEstado.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblCep.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblEmpresa.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblCargo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblTipoPais.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblSituacaoDosResp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		lblTipoUsuario.setStyleName("design_label");
		lblDataMatr.setStyleName("design_label");
		lblMatricula.setStyleName("design_label");
		lblPrimeiroNome.setStyleName("design_label");
		lblSobreNome.setStyleName("design_label");
		lblCPF.setStyleName("design_label");
		lblRG.setStyleName("design_label");
		lblSexo.setStyleName("design_label");
		lblEmail.setStyleName("design_label");
		lblDataNascimento.setStyleName("design_label");
		lblTelefoneCelular.setStyleName("design_label");
		lblTelefoneResidencial.setStyleName("design_label");
		lblTelefoneComercial.setStyleName("design_label");
		lblLogin.setStyleName("design_label");
		lblSenha.setStyleName("design_label");
		lblEndereco.setStyleName("design_label");
		lblNumRes.setStyleName("design_label");
		lblBairro.setStyleName("design_label");
		lblCidade.setStyleName("design_label");
		lblEstado.setStyleName("design_label");
		lblCep.setStyleName("design_label");
		lblEmpresa.setStyleName("design_label");
		lblCargo.setStyleName("design_label");
		lblTipoPais.setStyleName("design_label");
		lblSituacaoDosResp.setStyleName("design_label");

		String strSizeField = "250px";
		selectTipoUsuario.setWidth(strSizeField);
		txtMatricula.setWidth(strSizeField);
		txtPrimeiroNome.setWidth(strSizeField);
		txtSobreNome.setWidth(strSizeField);
		txtCpf.setWidth(strSizeField);
		txtRg.setWidth(strSizeField);
		txtEmail.setWidth(strSizeField);
		txtTelefoneCelular.setWidth(strSizeField);
		txtTelefoneResidencial.setWidth(strSizeField);
		txtTelefoneComercial.setWidth(strSizeField);
		selectTipoUsuario.setWidth(strSizeField);
		txtLogin.setWidth(strSizeField);
		txtSenha.setWidth(strSizeField);
		txtEndereco.setWidth(strSizeField);
		txtNumRes.setWidth("100px");
		txtBairro.setWidth(strSizeField);
		txtCidade.setWidth(strSizeField);
		selectEstados.setWidth("150px");
		selectSexo.setWidth("150px");
		selectTipoPais.setWidth("150px");
		txtCep.setWidth(strSizeField);
		txtEmpresa.setWidth(strSizeField);
		txtCargo.setWidth(strSizeField);
		txtSituacaoPaisOutros.setWidth("150px");

		dataNascimento.getDate().getDatePicker().setYearAndMonthDropdownVisible(true);
		dataNascimento.getDate().getDatePicker().setYearArrowsVisible(true);

		txtPrimeiroNome.setTitle(txtConstants.geralCampoObrigatorio(txtConstants.usuarioPrimeiroNome()));
		txtSobreNome.setTitle(txtConstants.geralCampoObrigatorio(txtConstants.usuarioSobreNome()));
		txtEmail.setTitle(txtConstants.geralCampoObrigatorio(txtConstants.usuarioEmail()));
		txtLogin.setTitle(txtConstants.geralCampoObrigatorio(txtConstants.usuario()));
		txtSenha.setTitle(txtConstants.usuarioSenha());

		imgPrimeiroNome.setTitle(txtConstants.geralCampoObrigatorio(txtConstants.usuarioPrimeiroNome()));
		imgSobreNome.setTitle(txtConstants.geralCampoObrigatorio(txtConstants.usuarioSobreNome()));
		imgEmail.setTitle(txtConstants.geralCampoObrigatorio(txtConstants.usuarioEmail()));
		imgLogin.setTitle(txtConstants.geralCampoObrigatorio(txtConstants.usuario()));
		imgSenha.setTitle(txtConstants.usuarioSenha());

		String strInLineSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		// String strLine =
		// "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
		// String strLine = ":";
		int row = 0;

		flexTable = new FlexTable();
		flexTable.setCellSpacing(3);
		flexTable.setCellPadding(3);
		flexTable.setBorderWidth(0);
		flexTable.setSize(Integer.toString(TelaInicialUsuario.intWidthTable),Integer.toString(TelaInicialUsuario.intHeightTable));
		flexTable.setWidget(row, 0, lblTipoUsuario);
		flexTable.setWidget(row++, 1, selectTipoUsuario);

		// flexTable.setWidget(row, 0, new InlineHTML("<b>Dados" + strLine+
		// "</b>"));
		// flexTable.getFlexCellFormatter().setColSpan(row++, 0, 10);

		// FlexTable flexTableDados = new FlexTable();
		// flexTableDados.setCellSpacing(2);
		// flexTableDados.setCellPadding(2);
		// flexTableDados.setBorderWidth(1);

		HIDE_ALUNO_MATRICULA = row;
		flexTable.setWidget(row, 0, lblMatricula);
		flexTable.setWidget(row, 1, txtMatricula);
		flexTable.setWidget(row, 2, new InlineHTML(strInLineSpace));
		flexTable.setWidget(row, 3, lblDataMatr);
		flexTable.setWidget(row++, 4, dataMatr);

		flexTable.setWidget(row, 0, lblPrimeiroNome);
		flexTable.setWidget(row, 1, txtPrimeiroNome);
		flexTable.setWidget(row, 2, imgPrimeiroNome);
		flexTable.setWidget(row, 3, lblSobreNome);
		flexTable.setWidget(row, 4, txtSobreNome);
		flexTable.setWidget(row++, 5, imgSobreNome);

		flexTable.setWidget(row, 0, lblEmail);
		flexTable.setWidget(row, 1, txtEmail);
		flexTable.setWidget(row, 2, imgEmail);
		flexTable.setWidget(row, 3, lblLogin);
		flexTable.setWidget(row, 4, txtLogin);
		flexTable.setWidget(row, 5, imgLogin);
		flexTable.setWidget(row, 6, lblSenha);
		flexTable.setWidget(row, 7, txtSenha);
		flexTable.setWidget(row++, 8, imgSenha);

		flexTable.setWidget(row, 0, lblDataNascimento);
		flexTable.setWidget(row, 1, dataNascimento);
		flexTable.setWidget(row, 2, new InlineHTML(strInLineSpace));
		flexTable.setWidget(row, 3, lblSexo);
		flexTable.setWidget(row, 4, selectSexo);
		flexTable.setWidget(row, 5, new InlineHTML(strInLineSpace));
		flexTable.setWidget(row, 6, lblTipoPais);
		flexTable.setWidget(row++, 7, selectTipoPais);

		flexTable.setWidget(row, 0, lblEndereco);
		flexTable.setWidget(row, 1, txtEndereco);
		flexTable.setWidget(row, 2, new InlineHTML(strInLineSpace));
		flexTable.setWidget(row, 3, lblNumRes);
		flexTable.setWidget(row, 4, txtNumRes);
		flexTable.setWidget(row, 5, new InlineHTML(strInLineSpace));
		flexTable.setWidget(row, 6, lblBairro);
		flexTable.setWidget(row++, 7, txtBairro);

		flexTable.setWidget(row, 0, lblCidade);
		flexTable.setWidget(row, 1, txtCidade);
		flexTable.setWidget(row, 2, new InlineHTML(strInLineSpace));
		flexTable.setWidget(row, 3, lblEstado);
		flexTable.setWidget(row, 4, selectEstados);
		flexTable.setWidget(row, 5, new InlineHTML(strInLineSpace));
		flexTable.setWidget(row, 6, lblCep);
		flexTable.setWidget(row++, 7, txtCep);

		flexTable.setWidget(row, 0, lblTelefoneCelular);
		flexTable.setWidget(row, 1, txtTelefoneCelular);
		flexTable.setWidget(row, 2, new InlineHTML(strInLineSpace));
		flexTable.setWidget(row, 3, lblTelefoneResidencial);
		flexTable.setWidget(row, 4, txtTelefoneResidencial);
		flexTable.setWidget(row, 5, new InlineHTML(strInLineSpace));
		flexTable.setWidget(row, 6, lblTelefoneComercial);
		flexTable.setWidget(row++, 7, txtTelefoneComercial);

		flexTable.setWidget(row, 0, lblCPF);
		flexTable.setWidget(row, 1, txtCpf);
		flexTable.setWidget(row, 2, new InlineHTML(strInLineSpace));
		flexTable.setWidget(row, 3, lblRG);
		flexTable.setWidget(row++, 4, txtRg);

		gridResponsavel = new Grid(1, 5);
		gridResponsavel.setWidget(0, 0, checkBoxRespAcademico);
		gridResponsavel.setWidget(0, 1, new InlineHTML(strInLineSpace));
		gridResponsavel.setWidget(0, 2, checkBoxRespFinanceiro);

		HIDE_PAIS_RESPONSAVEIS = row;
		flexTable.setWidget(row, 0, lblEmpresa);
		flexTable.setWidget(row, 1, txtEmpresa);
		flexTable.setWidget(row, 2, new InlineHTML(strInLineSpace));
		flexTable.setWidget(row, 3, lblCargo);
		flexTable.setWidget(row, 4, txtCargo);
		flexTable.setWidget(row, 5, new InlineHTML(strInLineSpace));
		flexTable.setWidget(row, 6, lblResponsavel);
		flexTable.setWidget(row++, 7, gridResponsavel);

		// radioButtonCasados.setValue(true);
		gridSituacaoResp = new Grid(1, 5);
		gridSituacaoResp.setWidget(0, 0, lblSituacaoDosResp);
		gridSituacaoResp.setWidget(0, 1, radioButtonCasados);
		gridSituacaoResp.setWidget(0, 2, radioButtonSeparados);
		gridSituacaoResp.setWidget(0, 3, radioButtonOutros);
		gridSituacaoResp.setWidget(0, 4, txtSituacaoPaisOutros);

		HIDE_ALUNO_SITUACAO_PAIS = row;
		flexTable.setWidget(row, 0, gridSituacaoResp);
		flexTable.getFlexCellFormatter().setColSpan(row++, 0, 10);

		flexTable.getRowFormatter().setVisible(HIDE_ALUNO_MATRICULA, false);
		flexTable.getRowFormatter().setVisible(HIDE_PAIS_RESPONSAVEIS, false);
		flexTable.getRowFormatter().setVisible(HIDE_ALUNO_SITUACAO_PAIS, false);

		String strTextoBotaoSubmeter = "";

		if (isAdicionarOperation == true) {
			strTextoBotaoSubmeter = txtConstants.geralSalvar();

		} else {
			strTextoBotaoSubmeter = txtConstants.geralAtualizar();
		}

		MpImageButton btnSave = new MpImageButton(strTextoBotaoSubmeter,"images/save.png");
		btnSave.addClickHandler(new ClickHandlerSave());
		MpImageButton btnClean = new MpImageButton(txtConstants.geralLimpar(),"images/erase.png");
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

		MpSpaceHorizontalPanel mpSpaceHorizontalPanel = new MpSpaceHorizontalPanel();
		mpSpaceHorizontalPanel.setWidth(Integer.toString(TelaInicialCurso.intWidthTable - 700) + "px");

		vFormPanel.add(flexTable);
		vFormPanel.add(gridSave);
		vFormPanel.add(mpSpaceHorizontalPanel);

		/*********************** Begin Callbacks **********************/

		// Callback para adicionar Disciplina.
		callbackAdicionarAtualizarUsuario = new AsyncCallback<String>() {

			public void onFailure(Throwable caught) {
				mpLoadingSave.setVisible(false);
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroSalvar());
				mpDialogBoxWarning.showDialog();
			}

			@Override
			public void onSuccess(String success) {
				// lblLoading.setVisible(false);
				mpLoadingSave.setVisible(false);
				
				if (success.equals("true")) {
					
					if(isAdicionarOperation==true){
						cleanFields();
						mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
						mpDialogBoxConfirm.setBodyText(txtConstants.usuarioSalvo());
						mpDialogBoxConfirm.showDialog();
						telaInicialUsuario.getEditarUsuario().updateClientData();
						telaInicialUsuario.getAssociarPaisAlunos().updateClientData();
					}else if(isAdicionarOperation==false){
						telaInicialUsuario.getEditarUsuario().updateClientDataRow(usuarioParaAtualizar.getIdUsuario());
						telaInicialUsuario.getAssociarPaisAlunos().updateClientData();						
					}

				}else if(success.contains("duplicate key")){
					String strUsuario = success.substring(success.indexOf("=(")+2);
					strUsuario = strUsuario.substring(0,strUsuario.indexOf(")"));
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					if(isAdicionarOperation==true){
						mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroSalvar() + " "+txtConstants.usuarioErroLoginDuplicado(strUsuario));	
					}else if(isAdicionarOperation==false){
						mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroAtualizar() + " "+txtConstants.usuarioErroLoginDuplicado(strUsuario));
					}					
					mpDialogBoxWarning.showDialog();	
				}
			}
		};
		
		
//		// Callback para adicionar Disciplina.
//		callbackAtualizarUsuario = new AsyncCallback<String>() {
//
//			public void onFailure(Throwable caught) {
//				mpLoadingSave.setVisible(false);
//				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//				mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroSalvar());
//				mpDialogBoxWarning.showDialog();
//			}
//
//			@Override
//			public void onSuccess(String success) {
//				// lblLoading.setVisible(false);
//				mpLoadingSave.setVisible(false);
//				
//				if (success.equals("true")) {				
//
//					telaInicialUsuario.getEditarUsuario().updateClientDataRow(usuarioParaAtualizar.getIdUsuario());
//					telaInicialUsuario.getAssociarPaisAlunos().updateClientData();
//
//				}else if(success.contains("duplicate key")){
//					String strUsuario = success.substring(success.indexOf("=(")+2);
//					strUsuario = strUsuario.substring(0,strUsuario.indexOf(")"));
//					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//					mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroAtualizar() + " "+txtConstants.usuarioErroLoginDuplicado(strUsuario));
//					mpDialogBoxWarning.showDialog();					
//				}
//			}
//		};		

		/*********************** End Callbacks **********************/
		
		
		if (isAdicionarOperation == true) {
			lblSenha.setVisible(true);
			txtSenha.setVisible(true);
			imgSenha.setVisible(true);			
			btnClean.setVisible(true);
		} else {
			lblSenha.setVisible(false);
			txtSenha.setVisible(false);
			imgSenha.setVisible(false);
			btnClean.setVisible(false);
		}


		super.add(vFormPanel);

	}

	/**************** Begin Event Handlers *****************/

	private class ClickHandlerSave implements ClickHandler {

		public void onClick(ClickEvent event) {

			if (checkFieldsValidator()) {
				mpLoadingSave.setVisible(true);

				int intTipoUsuario = Integer.parseInt(selectTipoUsuario.getValue(selectTipoUsuario.getSelectedIndex()));
				String unidadeFederativa = selectEstados.getValue(selectEstados.getSelectedIndex());
				String sexo = selectSexo.getValue(selectSexo.getSelectedIndex());
				String tipoPais = selectTipoPais.getValue(selectTipoPais.getSelectedIndex());
				boolean respAcademico = checkBoxRespAcademico.getValue();
				boolean respFinanceiro = checkBoxRespFinanceiro.getValue();
				boolean situacaoResponsaveisCasados = radioButtonCasados.getValue();
				boolean situacaoResponsaveisSeparados = radioButtonSeparados.getValue();
				boolean situacaoResponsaveisOutros = radioButtonOutros.getValue();
				String strSitResp = "";
				String strSitRespOutros = "";
				if (situacaoResponsaveisCasados == true){
					strSitResp = "casados";
				}else if (situacaoResponsaveisSeparados == true){
					strSitResp = "divorciados";
				}else if (situacaoResponsaveisOutros == true) {
					strSitResp = "outros";
					strSitRespOutros = txtSituacaoPaisOutros.getText();
				}

				Usuario usuario = new Usuario();

				usuario.setPrimeiroNome(txtPrimeiroNome.getText());
				usuario.setSobreNome(txtSobreNome.getText());
				usuario.setCpf(txtCpf.getText());
				usuario.setDataNascimento(dataNascimento.getDate().getValue());
				usuario.setIdTipoUsuario(intTipoUsuario);
				usuario.setEmail(txtEmail.getText());
				usuario.setTelefoneCelular(txtTelefoneCelular.getText());
				usuario.setTelefoneResidencial(txtTelefoneResidencial.getText());
				usuario.setTelefoneComercial(txtTelefoneComercial.getText());
				usuario.setLogin(txtLogin.getText());
				usuario.setSenha(txtSenha.getText());
				usuario.setEndereco(txtEndereco.getText());
				usuario.setNumeroResidencia(txtNumRes.getText());
				usuario.setBairro(txtBairro.getText());
				usuario.setCidade(txtCidade.getText());
				usuario.setUnidadeFederativa(unidadeFederativa);
				usuario.setCep(txtCep.getText());
				usuario.setRg(txtRg.getText());
				usuario.setSexo(sexo);

				if (intTipoUsuario == TipoUsuario.ALUNO) {
					usuario.setRegistroMatricula(txtMatricula.getText());
					usuario.setDataMatricula(dataMatr.getDate().getValue());
					usuario.setSituacaoResponsaveis(strSitResp);
					usuario.setSituacaoResponsaveisOutros(strSitRespOutros);
				} else if (intTipoUsuario == TipoUsuario.PAIS) {
					usuario.setEmpresaOndeTrabalha(txtEmpresa.getText());
					usuario.setCargo(txtCargo.getText());
					usuario.setRespAcademico(respAcademico);
					usuario.setRespFinanceiro(respFinanceiro);
					usuario.setTipoPais(tipoPais);
				}

				if(isAdicionarOperation==true){
					GWTServiceUsuario.Util.getInstance().AdicionarUsuario(usuario,callbackAdicionarAtualizarUsuario);
				}else{
					usuario.setIdUsuario(usuarioParaAtualizar.getIdUsuario());
					GWTServiceUsuario.Util.getInstance().updateUsuarioRow(usuario, callbackAdicionarAtualizarUsuario);
				}
				

			}

		}
	}

	private class ClickHandlerClean implements ClickHandler {
		public void onClick(ClickEvent event) {
			cleanFields();

		}
	}

	/**************** End Event Handlers *****************/

	public boolean checkFieldsValidator() {

		boolean isFieldsOk = false;
		boolean isFirstNameOk = false;
		boolean isEmailOk = false;
		boolean isLoginOk = false;
		boolean isSenhaOk = false;
		boolean isSobreNomeOk = false;

		if (FieldVerifier.isValidName(txtPrimeiroNome.getText())) {
			isFirstNameOk = true;
			txtPrimeiroNome.setStyleName("design_text_boxes");
		} else {
			isFirstNameOk = false;
			txtPrimeiroNome.setStyleName("design_text_boxes_erro");
		}

		if (FieldVerifier.isValidName(txtSobreNome.getText())) {
			isSobreNomeOk = true;
			txtSobreNome.setStyleName("design_text_boxes");
		} else {
			isSobreNomeOk = false;
			txtSobreNome.setStyleName("design_text_boxes_erro");
		}

		if (FieldVerifier.isValidEmail(txtEmail.getText())) {
			isEmailOk = true;
			txtEmail.setStyleName("design_text_boxes");
		} else {
			isEmailOk = false;
			txtEmail.setStyleName("design_text_boxes_erro");
		}

		if (FieldVerifier.isValidName(txtLogin.getText())) {
			isLoginOk = true;
			txtLogin.setStyleName("design_text_boxes");
		} else {
			isLoginOk = false;
			txtLogin.setStyleName("design_text_boxes_erro");
		}

		if (FieldVerifier.isValidName(txtSenha.getText())) {
			isSenhaOk = true;
			txtSenha.setStyleName("design_text_boxes");
		} else {
			isSenhaOk = false;
			txtSenha.setStyleName("design_text_boxes_erro");
		}
		
		if(isAdicionarOperation==false){
			isSenhaOk=true;
		}

		isFieldsOk = isFirstNameOk && isSobreNomeOk && isEmailOk && isLoginOk && isSenhaOk;

		return isFieldsOk;
	}

	private void cleanFields() {

		txtPrimeiroNome.setStyleName("design_text_boxes");
		txtSobreNome.setStyleName("design_text_boxes");
		txtEmail.setStyleName("design_text_boxes");
		txtLogin.setStyleName("design_text_boxes");
		txtSenha.setStyleName("design_text_boxes");

		txtMatricula.setValue("");
		txtPrimeiroNome.setValue("");
		txtSobreNome.setValue("");
		txtCpf.setValue("");
		txtEmail.setValue("");
		dataNascimento.getDate().setValue(null);
		dataMatr.getDate().setValue(null);
		txtTelefoneCelular.setValue("");
		txtTelefoneResidencial.setValue("");
		txtTelefoneComercial.setValue("");
		txtLogin.setValue("");
		txtSenha.setValue("");
		txtEndereco.setValue("");
		txtNumRes.setValue("");
		txtBairro.setValue("");
		txtCidade.setValue("");
		txtCep.setValue("");
		txtRg.setValue("");
		txtEmpresa.setValue("");
		txtCargo.setValue("");
		checkBoxRespAcademico.setValue(false);
		checkBoxRespFinanceiro.setValue(false);
		txtSituacaoPaisOutros.setValue("");
		radioButtonCasados.setValue(true);

	}

	private class MpTipoUsuarioChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			
			int intTipoUsuario = Integer.parseInt(selectTipoUsuario.getValue(selectTipoUsuario.getSelectedIndex()));

			if (isAdicionarOperation == true) {
				showCamposDeAcordoTipoUsuario(intTipoUsuario);
			} else {
				usuarioParaAtualizar.setIdTipoUsuario(intTipoUsuario);
				uniqueInstanceAtualizar.popularUsuario(usuarioParaAtualizar);
			}

		}
	}

	private void popularUsuario(Usuario usuario) {

		cleanFields();

		txtMatricula.setValue(usuario.getRegistroMatricula());
		txtPrimeiroNome.setValue(usuario.getPrimeiroNome());
		txtSobreNome.setValue(usuario.getSobreNome());
		txtEmail.setValue(usuario.getEmail());
		txtLogin.setValue(usuario.getLogin());
		txtSenha.setValue("");
		txtEndereco.setValue(usuario.getEndereco());
		txtNumRes.setValue(usuario.getNumeroResidencia());
		txtBairro.setValue(usuario.getBairro());
		txtCidade.setValue(usuario.getCidade());
		txtCep.setValue(usuario.getCep());
		txtTelefoneCelular.setValue(usuario.getTelefoneCelular());
		txtTelefoneResidencial.setValue(usuario.getTelefoneResidencial());
		txtTelefoneComercial.setValue(usuario.getTelefoneComercial());
		txtCpf.setValue(usuario.getCpf());
		txtRg.setValue(usuario.getRg());
		txtEmpresa.setValue(usuario.getEmpresaOndeTrabalha());
		txtCargo.setValue(usuario.getCargo());

		selectTipoUsuario.setSelectItem(usuario.getIdTipoUsuario());
		selectSexo.setSelectItem(usuario.getSexo());
		selectEstados.setSelectItem(usuario.getUnidadeFederativa());
		selectTipoPais.setSelectItem(usuario.getTipoPais());

		dataNascimento.getDate().setValue(usuario.getDataNascimento());
		dataMatr.getDate().setValue(usuario.getDataMatricula());

		checkBoxRespAcademico.setValue(usuario.isRespAcademico());
		checkBoxRespAcademico.setValue(usuario.isRespFinanceiro());

		if (usuario.getSituacaoResponsaveis() != null) {
			if (usuario.getSituacaoResponsaveis().equals("casados")) {
				radioButtonCasados.setValue(true);
			} else if (usuario.getSituacaoResponsaveis().equals("separados")) {
				radioButtonSeparados.setValue(true);
			} else {
				radioButtonOutros.setValue(true);
				txtSituacaoPaisOutros.setValue(usuario .getSituacaoResponsaveisOutros());
			}
		}

		showCamposDeAcordoTipoUsuario(usuario.getIdTipoUsuario());

	}

	private void showCamposDeAcordoTipoUsuario(int idTipoUsuario) {

		lblTipoPais.setVisible(false);
		selectTipoPais.setVisible(false);

		flexTable.getRowFormatter().setVisible(HIDE_ALUNO_MATRICULA, false);
		flexTable.getRowFormatter().setVisible(HIDE_ALUNO_SITUACAO_PAIS, false);
		flexTable.getRowFormatter().setVisible(HIDE_PAIS_RESPONSAVEIS, false);

		if (idTipoUsuario == TipoUsuario.PAIS) {
			flexTable.getRowFormatter().setVisible(HIDE_PAIS_RESPONSAVEIS, true);
			lblTipoPais.setVisible(true);
			selectTipoPais.setVisible(true);

		} else if (idTipoUsuario == TipoUsuario.ALUNO) {
			flexTable.getRowFormatter().setVisible(HIDE_ALUNO_MATRICULA, true);
			flexTable.getRowFormatter().setVisible(HIDE_ALUNO_SITUACAO_PAIS,true);
		}

	}

}
