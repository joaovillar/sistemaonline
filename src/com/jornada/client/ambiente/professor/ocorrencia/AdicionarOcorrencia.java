package com.jornada.client.ambiente.professor.ocorrencia;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxCursoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxDisciplinaAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxPeriodoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.suggestbox.MpListBoxPanelHelper;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelTextBoxError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpPanelPageMainView;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.classes.widgets.timepicker.MpTimePicker;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceOcorrencia;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Ocorrencia;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.utility.MpUtilClient;


public class AdicionarOcorrencia extends VerticalPanel {
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);

//	private AsyncCallback<Boolean> callbackAdd;
	
	private OcorrenciaAux ocorrenciaAux;
	
	private AsyncCallback<ArrayList<Usuario>> callbackGetAlunosFiltro;
//	private AsyncCallback<ArrayList<Usuario>> callbackGetPaisAssociados;	
	
	
	VerticalPanel vBodyPanel;
	
	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	
	private MpListBoxCursoAmbienteProfessor listBoxCurso;
	private MpListBoxPeriodoAmbienteProfessor listBoxPeriodo;
	private MpListBoxDisciplinaAmbienteProfessor listBoxDisciplina;	
//	private MpSelectionConteudoProgramatico listBoxConteudo;	
	
	MpListBoxPanelHelper mpHelperCurso = new  MpListBoxPanelHelper();
	
	private MpSelection listBoxCursoUpdate;
	private MpSelection listBoxPeriodoUpdate;
	private MpSelection listBoxDisciplinaUpdate;	
//	private MpSelection listBoxConteudoUpdate;		
	
	private ListBox multiBoxAlunosFiltrado;
	private ListBox multiBoxAlunosAssociado;	
	private TextBox txtFiltroAlunos;
	
	private MpLabelTextBoxError lblErroOcorrencia;
	private MpLabelTextBoxError lblErroDisciplina;
	private MpLabelTextBoxError lblErroData;
	
	EditarOcorrencia editarOcorrencia;


	private TextBox txtAssunto;
	private TextArea txtDescricao;
	private MpDateBoxWithImage mpDateBoxData;
	MpTimePicker mpTimePicker;
	
	private TelaInicialProfessorOcorrencia telaInicialProfessorOcorrencia;
	private EnumOcorrencia enumOcorrencia;
	
	
	

	
	public AdicionarOcorrencia(TelaInicialProfessorOcorrencia telaInicialProfessorOcorrencia, EnumOcorrencia enumOcorrencia) {
		
		this.telaInicialProfessorOcorrencia = telaInicialProfessorOcorrencia;
		
		this.enumOcorrencia = enumOcorrencia;
		
		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);
		
		vBodyPanel = new VerticalPanel();
		vBodyPanel.setWidth("100%");
		
		if (enumOcorrencia == EnumOcorrencia.ADICIONAR) {
			abrirTelaCadastro();
//			editarOcorrencia = new EditarOcorrencia(telaInicialProfessorOcorrencia);
		} else if (enumOcorrencia == EnumOcorrencia.EDITAR) {

		}
		
		this.setWidth("100%");
		super.add(vBodyPanel);

	}
	
	public void abrirTelaCadastro(){
		vBodyPanel.add(mpPanelFormularioOcorrencia());
		vBodyPanel.add(new MpSpaceVerticalPanel());
		vBodyPanel.add(mpPanelAssociarAlunos());
		vBodyPanel.add(new MpSpaceVerticalPanel());
//		vBodyPanel.add(gridBotaoSalvar());
	}
	
	public void criarTelaEditar(EditarOcorrencia editarOcorrencia){
		vBodyPanel.add(mpPanelFormularioOcorrenciaUpdate());
		vBodyPanel.add(new MpSpaceVerticalPanel());
		vBodyPanel.add(mpPanelAssociarAlunos());
		vBodyPanel.add(new MpSpaceVerticalPanel());
//		vBodyPanel.add(gridBotaoEditar());
		this.editarOcorrencia = editarOcorrencia;
	}
	
	public void popularCampos(OcorrenciaAux ocorrenciaAux){
		
		this.ocorrenciaAux = ocorrenciaAux;
		
//		listBoxCurso.setSelectItem(this.ocorrenciaAux.getIdCurso());
//		listBoxPeriodo.setSelectItem(this.ocorrenciaAux.getIdPeriodo());
//		listBoxDisciplina.setSelectItem(this.ocorrenciaAux.getIdDisciplina());
//		listBoxConteudo.setSelectItem(this.ocorrenciaAux.getIdConteudoProgramatico());
		
		listBoxCursoUpdate.addItem(this.ocorrenciaAux.getNomeCurso(), Integer.toString(this.ocorrenciaAux.getIdCurso()));
		listBoxPeriodoUpdate.addItem(this.ocorrenciaAux.getNomePeriodo(), Integer.toString(this.ocorrenciaAux.getIdPeriodo()));
		listBoxDisciplinaUpdate.addItem(this.ocorrenciaAux.getNomeDisciplina(), Integer.toString(this.ocorrenciaAux.getIdDisciplina()));
//		listBoxConteudoUpdate.addItem(this.ocorrenciaAux.getNomeConteudoProgramatico(), Integer.toString(this.ocorrenciaAux.getIdConteudoProgramatico()));
		
		listBoxCursoUpdate.setEnabled(false);
		listBoxPeriodoUpdate.setEnabled(false);
		listBoxDisciplinaUpdate.setEnabled(false);
//		listBoxConteudoUpdate.setEnabled(false);		
		
		txtAssunto.setText(this.ocorrenciaAux.getOcorrencia().getAssunto());
		txtDescricao.setText(this.ocorrenciaAux.getOcorrencia().getDescricao());
//		mpDateBoxData.getDate().setValue(MpUtilClient.convertStringToDate(this.ocorrenciaAux.getOcorrencia().getData()));
		//TODO investigate
		mpDateBoxData.getDate().setValue(this.ocorrenciaAux.getOcorrencia().getData());
		mpTimePicker.setTime(MpUtilClient.convertStringToTime(this.ocorrenciaAux.getOcorrencia().getHora()));	
		
		multiBoxAlunosAssociado.clear();
		for(int i=0;i<this.ocorrenciaAux.getOcorrencia().getListUsuariosRelacionadosOcorrencia().size();i++){			
			Usuario usuario = this.ocorrenciaAux.getOcorrencia().getListUsuariosRelacionadosOcorrencia().get(i); 
			int value = usuario.getIdUsuario();
			String item = usuario.getPrimeiroNome() + " " + usuario.getSobreNome();			
			multiBoxAlunosAssociado.addItem(item, Integer.toString(value));			
		}
		

	}

	@SuppressWarnings("deprecation")
	public MpPanelPageMainView mpPanelFormularioOcorrencia(){
		
		MpPanelPageMainView mpPanelForm = new MpPanelPageMainView(txtConstants.ocorrenciaPreencherCampo(), "images/note2_delete.png");
//		mpPanelForm.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable)+"px");
		mpPanelForm.setWidth("100%");
		
		FlexTable layout = new FlexTable();
		layout.setCellSpacing(2);
		layout.setCellPadding(2);
		layout.setBorderWidth(0);
//		layout.setSize(Integer.toString(TelaInicialProfessorOcorrencia.intWidthTable),Integer.toString(TelaInicialProfessorOcorrencia.intHeightTable));
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

//		cellFormatter.setColSpan(0, 0, 0);
		cellFormatter.setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_CENTER);
		
		listBoxCurso = new MpListBoxCursoAmbienteProfessor(telaInicialProfessorOcorrencia.getMainView().getUsuarioLogado());		
		listBoxPeriodo = new MpListBoxPeriodoAmbienteProfessor(telaInicialProfessorOcorrencia.getMainView().getUsuarioLogado());		
		listBoxDisciplina = new MpListBoxDisciplinaAmbienteProfessor(telaInicialProfessorOcorrencia.getMainView().getUsuarioLogado());		
//		listBoxConteudo = new MpSelectionConteudoProgramatico();
		
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());
		listBoxPeriodo.addChangeHandler(new MpPeriodoSelectionChangeHandler());		
//		listBoxDisciplina.addChangeHandler(new MpDisciplinaSelectionChangeHandler());
		
		txtAssunto = new TextBox();
		txtDescricao = new TextArea();
		mpDateBoxData = new MpDateBoxWithImage();		
		mpTimePicker = new MpTimePicker(7,22);		
		

		txtAssunto.setStyleName("design_text_boxes");
		txtDescricao.setStyleName("design_text_boxes");
//		mpDateBoxData.setStyleName("design_text_boxes");
		mpDateBoxData.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));

		
		Label lblCurso = new Label(txtConstants.curso());
		Label lblPeriodo = new Label(txtConstants.periodo());
		Label lblDisciplina = new Label(txtConstants.disciplina());
		Label lblConteudo = new Label(txtConstants.conteudoProgramatico());
		Label lblAssunto = new Label(txtConstants.ocorrencia());		
		Label lblDescricao = new Label(txtConstants.ocorrenciaDescricao());
		Label lblData = new Label(txtConstants.ocorrenciaData());
		Label lblHorario = new Label(txtConstants.ocorrenciaHora());
		
		lblErroOcorrencia = new MpLabelTextBoxError();
		lblErroDisciplina = new MpLabelTextBoxError();
		lblErroData = new MpLabelTextBoxError();

		
		lblCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblPeriodo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDisciplina.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblConteudo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblAssunto.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDescricao.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblData.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		lblHorario.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
		lblCurso.setStyleName("design_label");
		lblPeriodo.setStyleName("design_label");
		lblDisciplina.setStyleName("design_label");
		lblConteudo.setStyleName("design_label");
		lblAssunto.setStyleName("design_label");
		lblDescricao.setStyleName("design_label");
		lblData.setStyleName("design_label");
		lblHorario.setStyleName("design_label");
		txtAssunto.setWidth("350px");
		txtDescricao.setSize("350px", "50px");
		mpDateBoxData.getDate().setWidth("170px");


		// Add some standard form options
		int row = 1;
		layout.setWidget(row, 0, lblCurso);layout.setWidget(row, 1, listBoxCurso);layout.setWidget(row++, 2, mpHelperCurso);
		layout.setWidget(row, 0, lblPeriodo);layout.setWidget(row++, 1, listBoxPeriodo);		
		layout.setWidget(row, 0, lblDisciplina);layout.setWidget(row, 1, listBoxDisciplina);layout.setWidget(row++, 2, lblErroDisciplina);
//		layout.setWidget(row, 0, lblConteudo);layout.setWidget(row, 1, listBoxConteudo);layout.setWidget(row++, 2, lblErroConteudo);
		
		layout.setWidget(row, 0, lblAssunto);layout.setWidget(row, 1, txtAssunto);layout.setWidget(row++, 2, lblErroOcorrencia);
		layout.setWidget(row, 0, lblDescricao);layout.setWidget(row++, 1, txtDescricao);
		layout.setWidget(row, 0, lblData);layout.setWidget(row, 1, mpDateBoxData);layout.setWidget(row++, 2, lblErroData);
		layout.setWidget(row, 0, lblHorario);layout.setWidget(row++, 1, mpTimePicker);

		mpPanelForm.add(layout);
		
		/***********************Begin Callbacks**********************/
		/***********************End Callbacks**********************/
		return mpPanelForm;
		
	}
	
	@SuppressWarnings("deprecation")
	public MpPanelPageMainView mpPanelFormularioOcorrenciaUpdate(){
		
		MpPanelPageMainView mpPanelForm = new MpPanelPageMainView(txtConstants.ocorrenciaPreencherCampo(), "images/note2_delete.png");
//		mpPanelForm.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable)+"px");
		mpPanelForm.setWidth("100%");
		
		FlexTable layout = new FlexTable();
		layout.setCellSpacing(2);
		layout.setCellPadding(2);
		layout.setBorderWidth(0);
//		layout.setSize(Integer.toString(TelaInicialProfessorOcorrencia.intWidthTable),Integer.toString(TelaInicialProfessorOcorrencia.intHeightTable));
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

//		cellFormatter.setColSpan(0, 0, 0);
		cellFormatter.setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_CENTER);
		
		listBoxCursoUpdate = new MpSelection();		
		listBoxPeriodoUpdate = new MpSelection();		
		listBoxDisciplinaUpdate = new MpSelection();		
//		listBoxConteudoUpdate = new MpSelection();
		
		
		txtAssunto = new TextBox();
		txtDescricao = new TextArea();
		mpDateBoxData = new MpDateBoxWithImage();		
		mpTimePicker = new MpTimePicker(7,22);		
		

		txtAssunto.setStyleName("design_text_boxes");
		txtDescricao.setStyleName("design_text_boxes");
//		mpDateBoxData.setStyleName("design_text_boxes");
		mpDateBoxData.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));

		
		Label lblCurso = new Label(txtConstants.curso());
		Label lblPeriodo = new Label(txtConstants.periodo());
		Label lblDisciplina = new Label(txtConstants.disciplina());
		Label lblConteudo = new Label(txtConstants.conteudoProgramatico());
		Label lblAssunto = new Label(txtConstants.ocorrencia());		
		Label lblDescricao = new Label(txtConstants.ocorrenciaDescricao());
		Label lblData = new Label(txtConstants.ocorrenciaData());
		Label lblHorario = new Label(txtConstants.ocorrenciaHora());
		
		lblErroOcorrencia = new MpLabelTextBoxError();
		lblErroDisciplina = new MpLabelTextBoxError();
		lblErroData = new MpLabelTextBoxError();

		
		lblCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblPeriodo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDisciplina.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblConteudo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblAssunto.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDescricao.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblData.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		lblHorario.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
		lblCurso.setStyleName("design_label");
		lblPeriodo.setStyleName("design_label");
		lblDisciplina.setStyleName("design_label");
		lblConteudo.setStyleName("design_label");
		lblAssunto.setStyleName("design_label");
		lblDescricao.setStyleName("design_label");
		lblData.setStyleName("design_label");
		lblHorario.setStyleName("design_label");
		txtAssunto.setWidth("350px");
		txtDescricao.setSize("350px", "50px");
		mpDateBoxData.getDate().setWidth("170px");


		// Add some standard form options
		int row = 1;
		layout.setWidget(row, 0, lblCurso);layout.setWidget(row++, 1, listBoxCursoUpdate);
		layout.setWidget(row, 0, lblPeriodo);layout.setWidget(row++, 1, listBoxPeriodoUpdate);		
		layout.setWidget(row, 0, lblDisciplina);layout.setWidget(row, 1, listBoxDisciplinaUpdate);layout.setWidget(row++, 2, lblErroDisciplina);		
//		layout.setWidget(row, 0, lblConteudo);layout.setWidget(row, 1, listBoxConteudoUpdate);layout.setWidget(row++, 2, lblErroConteudo);
		
		layout.setWidget(row, 0, lblAssunto);layout.setWidget(row, 1, txtAssunto);layout.setWidget(row++, 2, lblErroOcorrencia);
		layout.setWidget(row, 0, lblDescricao);layout.setWidget(row++, 1, txtDescricao);
		layout.setWidget(row, 0, lblData);layout.setWidget(row, 1, mpDateBoxData);layout.setWidget(row++, 2, lblErroData);
		layout.setWidget(row, 0, lblHorario);layout.setWidget(row++, 1, mpTimePicker);

		mpPanelForm.add(layout);
		
		/***********************Begin Callbacks**********************/
		/***********************End Callbacks**********************/
		return mpPanelForm;
		
	}	
	
	@SuppressWarnings("deprecation")
    public MpPanelPageMainView mpPanelAssociarAlunos(){
		
		MpPanelPageMainView mpPanelAssociarAluno = new MpPanelPageMainView(txtConstants.ocorrenciaSelecionarAlunoEnviar(), "images/elementary_school_16.png");
//		mpPanelAssociarAluno.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable)+"px");
		mpPanelAssociarAluno.setWidth("100%");
		
		Grid gridFiltrar = new Grid(1,4);		
		gridFiltrar.setCellSpacing(3);
		gridFiltrar.setCellPadding(3);
		gridFiltrar.setBorderWidth(0);		
		
		Label lblFiltrarPais = new Label(txtConstants.ocorrenciaNomeDosAlunos());
		txtFiltroAlunos = new TextBox();		
		txtFiltroAlunos.addKeyUpHandler(new EnterKeyUpHandlerFiltrarAlunos());
		MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
		btnFiltrar.addClickHandler(new ClickHandlerFiltrarAlunos());		
		
		lblFiltrarPais.setStyleName("design_label");	
		lblFiltrarPais.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		txtFiltroAlunos.setStyleName("design_text_boxes");		
		
		gridFiltrar.setWidget(0, 0, lblFiltrarPais);
		gridFiltrar.setWidget(0, 1, txtFiltroAlunos);
		gridFiltrar.setWidget(0, 2, btnFiltrar);
		gridFiltrar.setWidget(0, 3, mpPanelLoading);
		
		
		Grid gridBotoes = new Grid(2,1);
		gridBotoes.setCellSpacing(3);
		gridBotoes.setCellPadding(3);
		gridBotoes.setBorderWidth(0);
		
		MpImageButton mpButtonParaEsquerda = new MpImageButton("", "images/resultset_previous.png");
		MpImageButton mpButtonParaDireita = new MpImageButton("", "images/resultset_next.png");
		
		mpButtonParaDireita.addClickHandler(new ClickHandlerParaDireita());
		mpButtonParaEsquerda.addClickHandler(new ClickHandlerParaEsquerda());		
		
		gridBotoes.setWidget(0, 0, mpButtonParaDireita);
		gridBotoes.setWidget(1, 0, mpButtonParaEsquerda);		
		
		FlexTable flexTable = new FlexTable();
		flexTable.setCellSpacing(3);
		flexTable.setCellPadding(3);
		flexTable.setBorderWidth(0);		

		Label lblAluno = new Label(txtConstants.ocorrenciaAlunosAssociados());
		lblAluno.setStyleName("design_label");
		
		multiBoxAlunosFiltrado = new ListBox(true);
	    multiBoxAlunosFiltrado.setWidth("450px");
	    multiBoxAlunosFiltrado.setHeight("100px");
	    multiBoxAlunosFiltrado.setVisibleItemCount(10);	
	    multiBoxAlunosFiltrado.setStyleName("design_text_boxes");
	    
		multiBoxAlunosAssociado = new ListBox(true);
	    multiBoxAlunosAssociado.setWidth("450px");
	    multiBoxAlunosAssociado.setHeight("100px");
	    multiBoxAlunosAssociado.setVisibleItemCount(10);	
	    multiBoxAlunosAssociado.setStyleName("design_text_boxes");
	    
	    flexTable.setWidget(0, 0, gridFiltrar);
	    flexTable.setWidget(0, 1, new InlineHTML("&nbsp;"));
	    flexTable.setWidget(0, 2, lblAluno);
	    flexTable.setWidget(1, 0, multiBoxAlunosFiltrado);
	    flexTable.setWidget(1, 1, gridBotoes);
	    flexTable.setWidget(1, 2, multiBoxAlunosAssociado);	    
	    
	    flexTable.setWidget(2,0,new InlineHTML("&nbsp;"));	    
	    
		if (enumOcorrencia == EnumOcorrencia.ADICIONAR) {
		    flexTable.setWidget(3,0,gridBotaoSalvar());
		} else if (enumOcorrencia == EnumOcorrencia.EDITAR) {
			flexTable.setWidget(3,0,gridBotaoEditar());		    
		}
		flexTable.getFlexCellFormatter().setColSpan(3, 0, 3);
	    
	    mpPanelAssociarAluno.add(flexTable);
	    
		callbackGetAlunosFiltro = new AsyncCallback<ArrayList<Usuario>>() {

			public void onSuccess(ArrayList<Usuario> list) {
				MpUtilClient.isRefreshRequired(list);
				mpPanelLoading.setVisible(false);				
				
				//Begin Cleaning fields
				multiBoxAlunosFiltrado.clear();

				//End Cleaning fields
				
				for(int i=0;i<list.size();i++){
					Usuario usuario = list.get(i);
					multiBoxAlunosFiltrado.addItem(usuario.getPrimeiroNome() +" "+usuario.getSobreNome(), Integer.toString(usuario.getIdUsuario()));
				}


			}
			
			public void onFailure(Throwable caught) {
				mpPanelLoading.setVisible(false);
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.ocorrenciaErroBuscarAluno());
				mpDialogBoxWarning.showDialog();

			}
		};
		
		
		return mpPanelAssociarAluno;

	}	
		
	public VerticalPanel gridBotaoSalvar(){
		
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setWidth("100%");
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
//		vPanel.setWidth(Integer.toString(TelaInicialProfessorOcorrencia.intWidthTable)+"px");
		
		MpImageButton btnSave = new MpImageButton(txtConstants.ocorrenciaSalvarOcorrencia(), "images/save.png");
		btnSave.addClickHandler(new ClickHandlerSave());
		MpImageButton btnClean = new MpImageButton(txtConstants.geralLimpar(), "images/erase.png");
		btnClean.addClickHandler(new ClickHandlerClean());	
		
		Grid gridSave = new Grid(1, 3);
		gridSave.setCellSpacing(2);
		gridSave.setCellPadding(2);
		{
			int i = 0;
			gridSave.setWidget(0, i++, btnSave);
			gridSave.setWidget(0, i++, btnClean);
			gridSave.setWidget(0, i++, mpPanelLoading);
		}
		
		vPanel.add(gridSave);		
		vPanel.add(new InlineHTML("&nbsp;"));
		
		return vPanel;
	}
	
	public VerticalPanel gridBotaoEditar(){
		
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
//		vPanel.setWidth(Integer.toString(TelaInicialProfessorOcorrencia.intWidthTable)+"px");
		vPanel.setWidth("100%");
		
		MpImageButton btnSave = new MpImageButton(txtConstants.geralAtualizar(), "images/save.png");
		btnSave.addClickHandler(new ClickHandlerSave());
		
		MpImageButton btnClean = new MpImageButton(txtConstants.geralLimpar(), "images/erase.png");
		btnClean.addClickHandler(new ClickHandlerClean());			
		
		MpImageButton btnCancelar = new MpImageButton(txtConstants.geralCancelar(), "images/cross-circle-frame.png");
		btnCancelar.addClickHandler(new ClickHandlerCancelar());
		
		
		Grid gridSave = new Grid(1, 4);
		gridSave.setCellSpacing(2);
		gridSave.setCellPadding(2);
		{
			int i = 0;
			gridSave.setWidget(0, i++, btnSave);
			gridSave.setWidget(0, i++, btnClean);
			gridSave.setWidget(0, i++, btnCancelar);
			gridSave.setWidget(0, i++, mpPanelLoading);
		}
		
		vPanel.add(gridSave);		
		vPanel.add(new InlineHTML("&nbsp;"));
		
		return vPanel;
	}
	
	/****************Begin Event Handlers*****************/
	
	private class ClickHandlerSave implements ClickHandler {

		public void onClick(ClickEvent event) {
			
			

		
			if(checkFieldsValidator()){
				
				mpPanelLoading.setVisible(true);

				int intIdDisciplina = 0;
				
				if(AdicionarOcorrencia.this.enumOcorrencia == EnumOcorrencia.ADICIONAR){
					intIdDisciplina = Integer.parseInt(listBoxDisciplina.getValue(listBoxDisciplina.getSelectedIndex()));
				}else if(AdicionarOcorrencia.this.enumOcorrencia == EnumOcorrencia.EDITAR){
					intIdDisciplina = Integer.parseInt(listBoxDisciplinaUpdate.getValue(listBoxDisciplinaUpdate.getSelectedIndex()));
				}
				String strHora = mpTimePicker.getValue(mpTimePicker.getSelectedIndex());

				Ocorrencia ocorrencia = new Ocorrencia();
				ocorrencia.setAssunto(txtAssunto.getText());
				ocorrencia.setDescricao(txtDescricao.getText());
				ocorrencia.setData(mpDateBoxData.getDate().getValue());
//				ocorrencia.setHora(MpUtilClient.convertStringToTime(strHora));
//				ocorrencia.setData(MpUtilClient.convertDateToString(mpDateBoxData.getDate().getValue()));
				ocorrencia.setHora(strHora);
				ocorrencia.setIdDisciplina(intIdDisciplina);
				
				ArrayList<Usuario> listUsuario = new ArrayList<Usuario>();
				for(int i=0;i<multiBoxAlunosAssociado.getItemCount();i++){
					int idUsuario = Integer.parseInt(multiBoxAlunosAssociado.getValue(i));
					Usuario  usuario = new Usuario();
					usuario.setIdUsuario(idUsuario);
					listUsuario.add(usuario);					
				}
				ocorrencia.setListUsuariosRelacionadosOcorrencia(listUsuario);
				
				if(AdicionarOcorrencia.this.enumOcorrencia == EnumOcorrencia.ADICIONAR){
					GWTServiceOcorrencia.Util.getInstance().AdicionarOcorrencia(ocorrencia, new callbackAdd());	
				}
				else if(AdicionarOcorrencia.this.enumOcorrencia == EnumOcorrencia.EDITAR){
					ocorrencia.setIdOcorrencia(ocorrenciaAux.getOcorrencia().getIdOcorrencia());
					GWTServiceOcorrencia.Util.getInstance().AtualizarOcorrencia(ocorrencia, new callbackAdd());
				}
				
					
			}

		}
	}
	
	private class ClickHandlerCancelar implements ClickHandler {

		public void onClick(ClickEvent event) {
			//vBodyPanel.clear();
			editarOcorrencia.vPanelEditarDetalhes.setVisible(false);
			editarOcorrencia.vPanelEditarTabela.setVisible(true);
			//AdicionarOcorrencia.this.clear();

		}
	}
	
	private class MpCursoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {	
		    mpHelperCurso.populateSuggestBox(listBoxCurso);
			multiBoxAlunosFiltrado.clear();
			int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
			listBoxPeriodo.populateComboBox(idCurso);
		}  
	}	
	
	private class MpPeriodoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int index = listBoxPeriodo.getSelectedIndex();
			if(index==-1){
				listBoxDisciplina.clear();
//				listBoxConteudo.clear();
			}
			else{
				int idPeriodo = Integer.parseInt(listBoxPeriodo.getValue(index));
				if (enumOcorrencia == EnumOcorrencia.ADICIONAR){
					listBoxDisciplina.populateComboBox(idPeriodo);					
				}
								
			}
		}  
	}
	
//	private class MpDisciplinaSelectionChangeHandler implements ChangeHandler {
//		public void onChange(ChangeEvent event) {
//			int index = listBoxDisciplina.getSelectedIndex();
//			if(index==-1){
//				listBoxConteudo.clear();
//			}
//			else{
//				int idDisciplina= Integer.parseInt(listBoxDisciplina.getValue(index));
//				if (enumOcorrencia == EnumOcorrencia.ADICIONAR){
//					listBoxConteudo.populateComboBox(idDisciplina);	
//				}
//			}
//		}  
//	}	
	
	private class ClickHandlerFiltrarAlunos implements ClickHandler {
		public void onClick(ClickEvent event) {
			populateUsuarioPorCurso();
		}
	}	
	
	private class EnterKeyUpHandlerFiltrarAlunos implements KeyUpHandler{
		public void onKeyUp(KeyUpEvent event){
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				populateUsuarioPorCurso();
			}
		}
	}
	
	private void populateUsuarioPorCurso(){
		mpPanelLoading.setVisible(true);
		int idCurso = 0;
		
		if (enumOcorrencia == EnumOcorrencia.ADICIONAR) {
		    idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));;
		}else{
		    idCurso = Integer.parseInt(listBoxCursoUpdate.getValue(listBoxCursoUpdate.getSelectedIndex()));;
		}
		
		GWTServiceUsuario.Util.getInstance().getAlunosPorCurso(idCurso, "%" +  txtFiltroAlunos.getText() + "%", callbackGetAlunosFiltro);
	}
	
	private class ClickHandlerParaDireita implements ClickHandler {
		public void onClick(ClickEvent event) {

			int i=0;
			while(i<multiBoxAlunosFiltrado.getItemCount()){			
			
				if(multiBoxAlunosFiltrado.isItemSelected(i)){
					String value = multiBoxAlunosFiltrado.getValue(multiBoxAlunosFiltrado.getSelectedIndex());
					String item = multiBoxAlunosFiltrado.getItemText(multiBoxAlunosFiltrado.getSelectedIndex());
					if(!containsItem(multiBoxAlunosAssociado, item)){
						multiBoxAlunosAssociado.addItem(item, value);
					}
					multiBoxAlunosFiltrado.removeItem(multiBoxAlunosFiltrado.getSelectedIndex());
					i=0;
					continue;
				}
				i++;
			}
		}
	}
		
	private class ClickHandlerParaEsquerda implements ClickHandler {
		public void onClick(ClickEvent event) {			

			int i=0;
			while(i<multiBoxAlunosAssociado.getItemCount()){			
				if (multiBoxAlunosAssociado.isItemSelected(i)) {
					String value = multiBoxAlunosAssociado.getValue(multiBoxAlunosAssociado.getSelectedIndex());
					String item = multiBoxAlunosAssociado.getItemText(multiBoxAlunosAssociado.getSelectedIndex());
					if (!containsItem(multiBoxAlunosFiltrado, item)) {
						multiBoxAlunosFiltrado.addItem(item, value);
					}
					multiBoxAlunosAssociado.removeItem(multiBoxAlunosAssociado.getSelectedIndex());
					i=0;
					continue;
				}
				i++;
			}
		}
	}
	
	private class ClickHandlerClean implements ClickHandler {
		public void onClick(ClickEvent event) {
			cleanFields();			
		}
	}		
	/****************End Event Handlers*****************/
	
	
	private boolean containsItem(ListBox listBox, String item){
		boolean contain = false;
		
		for(int i=0;i<listBox.getItemCount();i++){
			String strItem = listBox.getItemText(i);
			if(strItem.equals(item)){
				contain=true;
				break;
			}
		}			
		return contain;
	}
	
	private void cleanFields(){
		lblErroDisciplina.hideErroMessage();
		lblErroOcorrencia.hideErroMessage();
		lblErroData.hideErroMessage();
		txtAssunto.setValue("");
		txtDescricao.setValue("");
		mpDateBoxData.getDate().setValue(null);
		mpTimePicker.setSelectedIndex(0);
		multiBoxAlunosAssociado.clear();
		multiBoxAlunosFiltrado.clear();
	}

	public OcorrenciaAux getOcorrenciaAux() {
		return ocorrenciaAux;
	}

	public void setOcorrenciaAux(OcorrenciaAux ocorrenciaAux) {
		this.ocorrenciaAux = ocorrenciaAux;
	}
	

	
	
	public boolean checkFieldsValidator(){
		
		boolean isFieldsOk = false;
		boolean isOcorrenciaOk=false;
		boolean isDisciplinaOk=false;
		boolean isDataOk=false;
		
		if(FieldVerifier.isValidName(txtAssunto.getText())){
			isOcorrenciaOk=true;	
			lblErroOcorrencia.hideErroMessage();
		}else{
			isOcorrenciaOk=false;
			lblErroOcorrencia.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.ocorrencia()));
		}		

		if (enumOcorrencia == EnumOcorrencia.ADICIONAR) {
			if (FieldVerifier.isValidListBoxSelectedValue(listBoxDisciplina.getSelectedIndex())) {
			    isDisciplinaOk = true;
				lblErroDisciplina.hideErroMessage();
			} else {
				lblErroDisciplina.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.conteudoProgramatico()));
			}
		} else {
			isDisciplinaOk=true;
		}
		
		if(FieldVerifier.isValidDate(mpDateBoxData.getDate().getTextBox().getValue())){
			isDataOk=true;
			lblErroData.hideErroMessage();
		}else{
			lblErroData.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.ocorrenciaData()));
		}
		
		
		isFieldsOk = isOcorrenciaOk && isDisciplinaOk && isDataOk;

		
		return isFieldsOk;
	}	
	
	
	public void updateClientData(){
		listBoxCurso.populateComboBox(this.telaInicialProfessorOcorrencia.getMainView().getUsuarioLogado());
	}
	
	
	
	private class callbackAdd implements AsyncCallback<Boolean> {

		public void onFailure(Throwable caught) {
			mpPanelLoading.setVisible(false);
			mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
			mpDialogBoxWarning.setBodyText(txtConstants.ocorrenciaErroSalvar());
			mpDialogBoxWarning.showDialog();
		}

		@Override
		public void onSuccess(Boolean result) {
			// lblLoading.setVisible(false);
			mpPanelLoading.setVisible(false);
			boolean isSuccess = result;
			if (isSuccess) {

				if (enumOcorrencia == EnumOcorrencia.ADICIONAR) {
//					editarOcorrencia.populateGridOcorrencia();
//					editarOcorrencia.updateClientData();
					telaInicialProfessorOcorrencia.updateEditarOcorrenciaPopulateGrid();
					telaInicialProfessorOcorrencia.updateVisualizarOcorrenciaPopulateGrid();
					telaInicialProfessorOcorrencia.updateAprovarOcorrenciaPopulateGrid();
					cleanFields();
					mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
					mpDialogBoxConfirm.setBodyText(txtConstants.ocorrenciaSalva());						
				}
				else if (enumOcorrencia == EnumOcorrencia.EDITAR) {
//					editarOcorrencia.populateGridOcorrencia();
					telaInicialProfessorOcorrencia.updateEditarOcorrenciaPopulateGrid();
					telaInicialProfessorOcorrencia.updateVisualizarOcorrenciaPopulateGrid();
					telaInicialProfessorOcorrencia.updateAprovarOcorrenciaPopulateGrid();
					editarOcorrencia.vPanelEditarDetalhes.setVisible(false);
					editarOcorrencia.vPanelEditarTabela.setVisible(true);
					mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
					mpDialogBoxConfirm.setBodyText(txtConstants.ocorrenciaAtualizada());
				}
				
				mpDialogBoxConfirm.showDialog();
	
				
			} else {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.ocorrenciaErroSalvar());
				mpDialogBoxWarning.showDialog();
			}
		}
	}
	

}
