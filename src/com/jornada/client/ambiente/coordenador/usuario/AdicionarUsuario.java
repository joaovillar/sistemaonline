package com.jornada.client.ambiente.coordenador.usuario;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.jornada.client.ambiente.coordenador.curso.TelaInicialCurso;
import com.jornada.client.classes.listBoxes.MpSelectionTipoUsuario;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.MpListBoxEstados;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.MpListBoxSexo;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.MpListBoxTipoPais;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.MpSelectionUnidadeEscola;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelLeft;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceHorizontalPanel;
import com.jornada.client.classes.widgets.textbox.MpPasswordTextBox;
import com.jornada.client.classes.widgets.textbox.MpTextBox;
import com.jornada.client.content.config.ConfigClient;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.framework.Print;
import com.jornada.client.service.GWTServiceDocumento;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;

public class AdicionarUsuario extends VerticalPanel {

	private AsyncCallback<String> callbackAdicionarAtualizarUsuario;
	
	// private AsyncCallback<ArrayList<TipoUsuario>>
	// callBackPopulateTipoUsuarioComboBox;
	private VerticalPanel vFormPanel;

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpLoadingSave = new MpPanelLoading("images/radar.gif");
	// MpPanelLoading mpLoadingListTipoUsuario = new
	// MpPanelLoading("images/radar.gif");

	FlexTable flexTable;

	private MpTextBox txtPrimeiroNome;
	private MpTextBox txtSobreNome;
	private MpTextBox txtCpf;
	private MpTextBox txtRg;
	private MpTextBox txtLogin;
	private MpPasswordTextBox txtSenha;
	private MpTextBox txtEmail;
	private MpDateBoxWithImage dataNascimento;
	private MpTextBox txtTelefoneCelular;
	private MpTextBox txtTelefoneResidencial;
	private MpTextBox txtTelefoneComercial;
	private MpTextBox txtEndereco;
	private MpTextBox txtNumRes;
	private MpTextBox txtBairro;
	private MpTextBox txtCidade;
	private MpTextBox txtCep;
	private MpSelectionTipoUsuario selectTipoUsuario;
	private MpListBoxEstados selectEstados;
	private MpListBoxSexo selectSexo;
	private MpListBoxTipoPais selectTipoPais;
	private MpSelectionUnidadeEscola selectUnidadeEscola;

	private int HIDE_ALUNO_MATRICULA;
	private int HIDE_ALUNO_SITUACAO_PAIS;
	private int HIDE_PAIS_RESPONSAVEIS;

	MpLabelLeft lblPrimeiroNome;
	MpLabelLeft lblSobreNome;
	MpLabelLeft lblCPF;
	MpLabelLeft lblRG;
	MpLabelLeft lblSexo;
	MpLabelLeft lblEmail;
	MpLabelLeft lblDataNascimento;
	MpLabelLeft lblTelefoneCelular;
	MpLabelLeft lblTelefoneResidencial;
	MpLabelLeft lblTelefoneComercial;
	MpLabelLeft lblTipoUsuario;
	MpLabelLeft lblLogin;
	MpLabelLeft lblSenha;
	MpLabelLeft lblEndereco;
	MpLabelLeft lblNumRes;
	MpLabelLeft lblBairro;
	MpLabelLeft lblCidade;
	MpLabelLeft lblEstado;
	MpLabelLeft lblCep;
	MpLabelLeft lblEmpresa;
	MpLabelLeft lblCargo;
	MpLabelLeft lblUnidadeEscola;

	// Se for Pai
	private MpTextBox txtEmpresa;
	private MpTextBox txtCargo;
	private MpLabelLeft lblResponsavel;
	private MpLabelLeft lblTipoPais;
	private CheckBox checkBoxRespFinanceiro;
	private CheckBox checkBoxRespAcademico;
	private Grid gridResponsavel;

	// Se for aluno
	private MpLabelLeft lblMatricula;
	private MpLabelLeft lblDataMatr;
	private MpLabelLeft lblSituacaoDosResp;
	private MpTextBox txtMatricula;
	private MpTextBox txtSituacaoPaisOutros;
	private Grid gridSituacaoResp;
	private RadioButton radioButtonCasados;
	private RadioButton radioButtonSeparados;
	private RadioButton radioButtonOutros;
	private MpDateBoxWithImage dataMatr;
	private MpLabelLeft lblRegistroAluno;
	private MpTextBox txtRegistroAluno;
	
	
	MpImageButton btnContrato;
	MpImageButton btnPrinter;
	MpImageButton btnClean;
	MpImageButton btnSave;
	

	boolean isAdicionarOperation = false;
	boolean isFirstLoadUpdate=false;
	private Usuario usuarioParaAtualizar;
	 
	 Grid gridBotoes;

	@SuppressWarnings("unused")
	private TelaInicialUsuario telaInicialUsuario;

	TextConstants txtConstants = GWT.create(TextConstants.class);
	ConfigClient clientConfig = GWT.create(ConfigClient.class);

	private static AdicionarUsuario uniqueInstanceAdicionar;
	private static AdicionarUsuario uniqueInstanceAtualizar;

	public static AdicionarUsuario getInstanceAdicionar(TelaInicialUsuario telaInicialUsuario) {

		if (uniqueInstanceAdicionar == null) {
			uniqueInstanceAdicionar = new AdicionarUsuario(telaInicialUsuario,true);
			uniqueInstanceAtualizar = new AdicionarUsuario(telaInicialUsuario,false);
		}

		return uniqueInstanceAdicionar;

	}

	public static AdicionarUsuario getInstanceAtualizar(TelaInicialUsuario telaInicialUsuario, Usuario usuario) {

		
		if (uniqueInstanceAtualizar == null) {			

			uniqueInstanceAtualizar = new AdicionarUsuario(telaInicialUsuario,false);
			uniqueInstanceAtualizar.usuarioParaAtualizar = usuario;
			uniqueInstanceAtualizar.isFirstLoadUpdate=true;
			
//			uniqueInstanceAtualizar.popularUsuario(usuario);
			
		} else {
			uniqueInstanceAtualizar.usuarioParaAtualizar = usuario;
			uniqueInstanceAtualizar.popularUsuario(usuario);
		}

		return uniqueInstanceAtualizar;

	}

	@SuppressWarnings("deprecation")
	private AdicionarUsuario(final TelaInicialUsuario telaInicialUsuario, final boolean isAdicionarOperation) {

		
		
		this.isAdicionarOperation = isAdicionarOperation;

		this.telaInicialUsuario = telaInicialUsuario;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpLoadingSave.setTxtLoading(txtConstants.geralCarregando());
		mpLoadingSave.show();
		mpLoadingSave.setVisible(false);

		// Add a title to the form
		txtMatricula = new MpTextBox();
		txtRegistroAluno = new MpTextBox();
		dataMatr = new MpDateBoxWithImage();
		txtPrimeiroNome = new MpTextBox();
		txtSobreNome = new MpTextBox();
		txtCpf = new MpTextBox();
		txtRg = new MpTextBox();
		txtEmail = new MpTextBox();
		dataNascimento = new MpDateBoxWithImage();
		txtTelefoneCelular = new MpTextBox();
		txtTelefoneResidencial = new MpTextBox();
		txtTelefoneComercial = new MpTextBox();
		txtLogin = new MpTextBox();
		txtSenha = new MpPasswordTextBox();
		txtEndereco = new MpTextBox();
		txtNumRes = new MpTextBox();
		txtBairro = new MpTextBox();
		txtCidade = new MpTextBox();
		txtCep = new MpTextBox();
		txtEmpresa = new MpTextBox();
		txtCargo = new MpTextBox();
		txtSituacaoPaisOutros = new MpTextBox();

		checkBoxRespFinanceiro = new CheckBox(txtConstants.usuarioRespFinanceiro());
		checkBoxRespAcademico = new CheckBox(txtConstants.usuarioRespAcademico());


		selectTipoUsuario = new MpSelectionTipoUsuario();
//		if(isAdicionarOperation){
		selectTipoUsuario.populateComboBox();
//		}

		selectUnidadeEscola = new MpSelectionUnidadeEscola();
		selectEstados = new MpListBoxEstados();
		selectSexo = new MpListBoxSexo();
		selectTipoPais = new MpListBoxTipoPais();


		selectTipoPais.setVisible(false);

		selectTipoUsuario.addChangeHandler(new MpTipoUsuarioChangeHandler());
		selectUnidadeEscola.addChangeHandler(new MpUnidadeEscolaChangeHandler());

		dataNascimento.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
		dataNascimento.getDate().setWidth("180px");
		dataMatr.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
		dataMatr.getDate().setWidth("180px");


		lblUnidadeEscola = new MpLabelLeft(txtConstants.usuarioUnidadeEscola());
		lblDataMatr = new MpLabelLeft(txtConstants.usuarioDataMatricula());
		lblMatricula = new MpLabelLeft(txtConstants.usuarioMatricula());
		lblRegistroAluno = new MpLabelLeft(txtConstants.usuarioRegistroAluno());
		lblPrimeiroNome = new MpLabelLeft(txtConstants.usuarioPrimeiroNome());
		lblSobreNome = new MpLabelLeft(txtConstants.usuarioSobreNome());
		lblCPF = new MpLabelLeft(txtConstants.usuarioCPF());
		lblRG = new MpLabelLeft(txtConstants.usuarioRG());
		lblSexo = new MpLabelLeft(txtConstants.usuarioSexo());
		lblEmail = new MpLabelLeft(txtConstants.usuarioEmail());
		lblDataNascimento = new MpLabelLeft(txtConstants.usuarioDataNascimento());
		lblTelefoneCelular = new MpLabelLeft(txtConstants.usuarioTelCelular());
		lblTelefoneResidencial = new MpLabelLeft(txtConstants.usuarioTelResidencial());
		lblTelefoneComercial = new MpLabelLeft(txtConstants.usuarioTelComercial());
		lblTipoUsuario = new MpLabelLeft(txtConstants.usuarioTipo());
		lblLogin = new MpLabelLeft(txtConstants.usuario());
		lblSenha = new MpLabelLeft(txtConstants.usuarioSenha());
		lblEndereco = new MpLabelLeft(txtConstants.usuarioEndereco());
		lblNumRes = new MpLabelLeft(txtConstants.usuarioNumRes());
		lblBairro = new MpLabelLeft(txtConstants.usuarioBairro());
		lblCidade = new MpLabelLeft(txtConstants.usuarioCidade());
		lblEstado = new MpLabelLeft(txtConstants.usuarioEstado());
		lblCep = new MpLabelLeft(txtConstants.usuarioCep());
		lblEmpresa = new MpLabelLeft(txtConstants.usuarioEmpresa());
		lblCargo = new MpLabelLeft(txtConstants.usuarioCargo());
		lblResponsavel = new MpLabelLeft(txtConstants.usuarioResponsavel());
		lblTipoPais = new MpLabelLeft(txtConstants.usuarioTipoPai());
		lblSituacaoDosResp = new MpLabelLeft(txtConstants.usuarioSituacaoDosPais());
		

		radioButtonCasados = new RadioButton("useTemplate", txtConstants.usuarioCasados());
		radioButtonSeparados = new RadioButton("useTemplate", txtConstants.usuarioSeparados());
		radioButtonOutros = new RadioButton("useTemplate", txtConstants.usuarioOutros());
		radioButtonCasados.setValue(true);

		Image imgPrimeiroNome = new Image("images/bullet_red.png");
		Image imgSobreNome = new Image("images/bullet_red.png");
		Image imgLogin = new Image("images/bullet_red.png");
		Image imgSenha = new Image("images/bullet_red.png");
		Image imgEmail = new Image("images/bullet_red.png");

		String strSizeField = "250px";
		selectTipoUsuario.setWidth(strSizeField);
		selectUnidadeEscola.setWidth(strSizeField);
		txtMatricula.setWidth(strSizeField);
		txtRegistroAluno.setWidth(strSizeField);
		txtPrimeiroNome.setWidth(strSizeField);
		txtSobreNome.setWidth(strSizeField);
		txtCpf.setWidth(strSizeField);
		txtRg.setWidth(strSizeField);
		txtEmail.setWidth(strSizeField);
		txtTelefoneCelular.setWidth(strSizeField);
		txtTelefoneResidencial.setWidth(strSizeField);
		txtTelefoneComercial.setWidth(strSizeField);
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
		flexTable.getElement().setId("flexTable");
		flexTable.setCellSpacing(3);
		flexTable.setCellPadding(3);
		flexTable.setBorderWidth(0);
		flexTable.setSize(Integer.toString(TelaInicialUsuario.intWidthTable),Integer.toString(TelaInicialUsuario.intHeightTable));
		
		boolean showUnidadeEscola = Boolean.valueOf(clientConfig.usuarioShowUnidadeEscola());	
		if (showUnidadeEscola) {
			flexTable.setWidget(row, 0, lblTipoUsuario);
			flexTable.setWidget(row, 1, selectTipoUsuario);
			flexTable.setWidget(row, 2, new InlineHTML(strInLineSpace));
			flexTable.setWidget(row, 3, lblUnidadeEscola);
			flexTable.setWidget(row++, 4, selectUnidadeEscola);
		} else {
			flexTable.setWidget(row, 0, lblTipoUsuario);
			flexTable.setWidget(row++, 1, selectTipoUsuario);

		}

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
		flexTable.setWidget(row, 4, dataMatr);
		flexTable.setWidget(row, 5, new InlineHTML(strInLineSpace));
		flexTable.setWidget(row, 6, lblRegistroAluno);
		flexTable.setWidget(row++, 7, txtRegistroAluno);

		flexTable.setWidget(row, 0, lblPrimeiroNome);
		flexTable.setWidget(row, 1, txtPrimeiroNome);
		flexTable.setWidget(row, 2, imgPrimeiroNome);
		flexTable.setWidget(row, 3, lblSobreNome);
		flexTable.setWidget(row, 4, txtSobreNome);
		flexTable.setWidget(row++, 5, imgSobreNome);

		flexTable.setWidget(row, 0, lblLogin);
		flexTable.setWidget(row, 1, txtLogin);
		flexTable.setWidget(row, 2, imgLogin);
		flexTable.setWidget(row, 3, lblSenha);
		flexTable.setWidget(row, 4, txtSenha);
		flexTable.setWidget(row, 5, imgSenha);
		flexTable.setWidget(row, 6, lblEmail);
		flexTable.setWidget(row++, 7, txtEmail);


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

		btnSave = new MpImageButton(strTextoBotaoSubmeter,"images/save.png");
		btnSave.addClickHandler(new ClickHandlerSave());
		btnClean = new MpImageButton(txtConstants.geralLimpar(),"images/erase.png");
		btnClean.addClickHandler(new ClickHandlerClean());
		btnPrinter = new MpImageButton(txtConstants.geralImprimir(),"images/Print.16.png");
		btnPrinter.addClickHandler(new ClickHandlerPrint());
        btnContrato = new MpImageButton(txtConstants.documentoContrato(),"images/check.png");
        btnContrato.addClickHandler(new ClickHandlerContrato());		

//		Image imgExcel = new Image("images/excel.24.png");


		vFormPanel = new VerticalPanel();
		vFormPanel.getElement().setId("vFormPanel");

		gridBotoes = new Grid(1, 5);
		gridBotoes.setCellSpacing(2);
		gridBotoes.setCellPadding(2);
		{
			int i = 0;
			gridBotoes.setWidget(0, i++, btnSave);
			gridBotoes.setWidget(0, i++, btnClean);
			gridBotoes.setWidget(0, i++, btnPrinter);
			gridBotoes.setWidget(0, i++, btnContrato);
			gridBotoes.setWidget(0, i++, mpLoadingSave);
		}

		MpSpaceHorizontalPanel mpSpaceHorizontalPanel = new MpSpaceHorizontalPanel();
		mpSpaceHorizontalPanel.setWidth(Integer.toString(TelaInicialCurso.intWidthTable - 700) + "px");

		vFormPanel.add(flexTable);
		vFormPanel.add(gridBotoes);
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
				}else if(success.contains(Usuario.DB_UNIQUE_LOGIN)){
					String strUsuario = success.substring(success.indexOf("=(")+2);
					strUsuario = strUsuario.substring(0,strUsuario.indexOf(")"));
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					if(isAdicionarOperation==true){
						mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroSalvar() + " "+txtConstants.usuarioErroLoginDuplicado(strUsuario));	
					}else if(isAdicionarOperation==false){
						mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroAtualizar() + " "+txtConstants.usuarioErroLoginDuplicado(strUsuario));
					}					
					mpDialogBoxWarning.showDialog();	
				}else{
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroSalvar()+" "+txtConstants.geralRecarregarAmbiente());
					mpDialogBoxWarning.showDialog();					
				}
			}
		};
		
		


		/*********************** End Callbacks **********************/
		
		
		if (isAdicionarOperation == true) {
			lblSenha.setVisible(true);
			txtSenha.setVisible(true);
			
			imgSenha.setVisible(true);			
			btnClean.setVisible(true);
			btnPrinter.setVisible(false);
			btnContrato.setVisible(false);
		} else {
			lblSenha.setVisible(false);
			txtSenha.setVisible(false);
			imgSenha.setVisible(false);
			btnClean.setVisible(false);
			btnPrinter.setVisible(true);
			btnContrato.setVisible(true);
		}


		super.add(vFormPanel);

	}
	
	
	
	private Usuario getUsuarioDoForm(){
	    
        int intIdTipoUsuario = Integer.parseInt(selectTipoUsuario.getValue(selectTipoUsuario.getSelectedIndex()));
        String unidadeFederativa = selectEstados.getValue(selectEstados.getSelectedIndex());
        String sexo = selectSexo.getValue(selectSexo.getSelectedIndex());
        String tipoPais = selectTipoPais.getValue(selectTipoPais.getSelectedIndex());
        int intIdUnidadeEscola = Integer.parseInt(selectUnidadeEscola.getValue(selectUnidadeEscola.getSelectedIndex()));
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
        
        if(isAdicionarOperation==true){        
        }else{
            usuario.setIdUsuario(usuarioParaAtualizar.getIdUsuario());    
        }
        usuario.setIdUnidadeEscola(intIdUnidadeEscola);
        usuario.setPrimeiroNome(txtPrimeiroNome.getText());
        usuario.setSobreNome(txtSobreNome.getText());
        usuario.setCpf(txtCpf.getText());
        usuario.setDataNascimento(dataNascimento.getDate().getValue());
        usuario.setIdTipoUsuario(intIdTipoUsuario);
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

        if (intIdTipoUsuario == TipoUsuario.ALUNO) {
            usuario.setRegistroMatricula(txtMatricula.getText());
            usuario.setRegistroAluno(txtRegistroAluno.getText());
            usuario.setDataMatricula(dataMatr.getDate().getValue());
            usuario.setSituacaoResponsaveis(strSitResp);
            usuario.setSituacaoResponsaveisOutros(strSitRespOutros);
        } else if (intIdTipoUsuario == TipoUsuario.PAIS) {
            usuario.setEmpresaOndeTrabalha(txtEmpresa.getText());
            usuario.setCargo(txtCargo.getText());
            usuario.setRespAcademico(respAcademico);
            usuario.setRespFinanceiro(respFinanceiro);
            usuario.setTipoPais(tipoPais);
        }
        
        return usuario;
	}

	/**************** Begin Event Handlers *****************/

	private class ClickHandlerSave implements ClickHandler {

		public void onClick(ClickEvent event) {

			if (checkFieldsValidator()) {
				mpLoadingSave.setVisible(true);
				
				Usuario usuario = getUsuarioDoForm();

				if(isAdicionarOperation==true){
					GWTServiceUsuario.Util.getInstance().AdicionarUsuario(usuario,callbackAdicionarAtualizarUsuario);
				}else{
					
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

	
	private class ClickHandlerPrint implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {

			FlexTable flexTableTitle = new FlexTable();
			flexTableTitle.setBorderWidth(0);
			flexTableTitle.setWidth("100%");
			Label lblCadastro = new Label(txtConstants.usuarioRegistro());
			flexTableTitle.setWidget(0, 0, lblCadastro);
			flexTableTitle.getCellFormatter().setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_CENTER);
			flexTableTitle.setStyleName("designTree");		
			
			Print.it("<link type='text/css' rel='stylesheet' href='Jornada.css'>", flexTableTitle.getElement(), flexTable.getElement());  

		}
	}
	
    private class ClickHandlerContrato implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            
            String strAddress = GWT.getHostPageBaseURL();
            
            Usuario usuario = getUsuarioDoForm();
            GWTServiceDocumento.Util.getInstance().criarDocumentoParaAlunoTelaUsuario(usuario, strAddress, new callBackContratoAluno());



        }
    }	
	/**************** End Event Handlers *****************/

	public boolean checkFieldsValidator() {

		boolean isFieldsOk = false;
		boolean isFirstNameOk = false;
		//boolean isEmailOk = false;
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

//		if (FieldVerifier.isValidEmail(txtEmail.getText())) {
//			isEmailOk = true;
//			txtEmail.setStyleName("design_text_boxes");
//		} else {
//			isEmailOk = false;
//			txtEmail.setStyleName("design_text_boxes_erro");
//		}

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

		isFieldsOk = isFirstNameOk && isSobreNomeOk && isLoginOk && isSenhaOk;

		return isFieldsOk;
	}

	private void cleanFields() {

		txtPrimeiroNome.setStyleName("design_text_boxes");
		txtSobreNome.setStyleName("design_text_boxes");
//		txtEmail.setStyleName("design_text_boxes");
		txtLogin.setStyleName("design_text_boxes");
		txtSenha.setStyleName("design_text_boxes");

		txtMatricula.setValue("");
		txtRegistroAluno.setValue("");
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

			}else if (isAdicionarOperation == false)  {

				if(isFirstLoadUpdate){
					isFirstLoadUpdate=false;
				
//					showCamposDeAcordoTipoUsuario(usuarioParaAtualizar.getIdTipoUsuario());
					uniqueInstanceAtualizar.popularUsuario(usuarioParaAtualizar);
					selectTipoUsuario.setSelectItem(usuarioParaAtualizar.getIdTipoUsuario());
				}else{
				    
					showCamposDeAcordoTipoUsuario(intTipoUsuario);
				}

			}

		}
	}
	
	private class MpUnidadeEscolaChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {

//			if (isAdicionarOperation == false) {
//				if(isFirstLoadUpdate){
//					isFirstLoadUpdate=false;
//					uniqueInstanceAtualizar.popularUsuario(usuarioParaAtualizar);					
//				}
//			} 
		}
	}
	
	

	private void popularUsuario(Usuario usuario) {

		cleanFields();

		txtMatricula.setValue(usuario.getRegistroMatricula());
		txtRegistroAluno.setValue(usuario.getRegistroAluno());
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
		selectUnidadeEscola.setSelectItem(usuario.getIdUnidadeEscola());
		
		dataNascimento.getDate().setValue(usuario.getDataNascimento());
		dataMatr.getDate().setValue(usuario.getDataMatricula());
		
		checkBoxRespAcademico.setValue(usuario.isRespAcademico());
		checkBoxRespFinanceiro.setValue(usuario.isRespFinanceiro());


		if (usuario.getSituacaoResponsaveis() != null) {
			if (usuario.getSituacaoResponsaveis().equals("casados")) {
				radioButtonCasados.setValue(true);
			} else if (usuario.getSituacaoResponsaveis().equals("separados")) {
				radioButtonSeparados.setValue(true);
			} else {
				radioButtonOutros.setEnabled(true);
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
		    btnContrato.setVisible(true);
			flexTable.getRowFormatter().setVisible(HIDE_ALUNO_MATRICULA, true);
			flexTable.getRowFormatter().setVisible(HIDE_ALUNO_SITUACAO_PAIS,true);
		}

	}
	
	
	private class callBackContratoAluno implements AsyncCallback<String>{

        @Override
        public void onFailure(Throwable caught) {
            // TODO Auto-generated method stub
            Window.alert(caught.getMessage());
            
        }

        @Override
        public void onSuccess(String result) {
           System.out.println(result);
           Window.open(result, "blank", "target='_blank'");            
        }
	    
	}

}
