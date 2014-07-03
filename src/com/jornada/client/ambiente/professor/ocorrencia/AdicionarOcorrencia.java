package com.jornada.client.ambiente.professor.ocorrencia;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
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
import com.jornada.client.ambiente.coordenador.usuario.TelaInicialUsuario;
import com.jornada.client.classes.listBoxes.MpSelectionConteudoProgramatico;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionCursoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionDisciplinaAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionPeriodoAmbienteProfessor;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelTextBoxError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpPanelPageMainView;
import com.jornada.client.classes.widgets.panel.MpSpacePanel;
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

	private AsyncCallback<Boolean> callbackAdd;
	
	private OcorrenciaAux ocorrenciaAux;
	
	private AsyncCallback<ArrayList<Usuario>> callbackGetAlunosFiltro;
//	private AsyncCallback<ArrayList<Usuario>> callbackGetPaisAssociados;	
	
	
	VerticalPanel vBodyPanel;
	
	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	
	private MpSelectionCursoAmbienteProfessor listBoxCurso;
	private MpSelectionPeriodoAmbienteProfessor listBoxPeriodo;
	private MpSelectionDisciplinaAmbienteProfessor listBoxDisciplina;	
	private MpSelectionConteudoProgramatico listBoxConteudo;	
	
	private ListBox multiBoxAlunosFiltrado;
	private ListBox multiBoxAlunosAssociado;	
	private TextBox txtFiltroAlunos;
	
	private MpLabelTextBoxError lblErroOcorrencia;
	private MpLabelTextBoxError lblErroConteudo;
	
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
		
		if (enumOcorrencia == EnumOcorrencia.ADICIONAR) {
			abrirTelaCadastro();
		} else if (enumOcorrencia == EnumOcorrencia.EDITAR) {

		}
		
		
		super.add(vBodyPanel);

	}
	
	public void abrirTelaCadastro(){
		vBodyPanel.add(mpPanelFormularioOcorrencia());
		vBodyPanel.add(new MpSpacePanel());
		vBodyPanel.add(mpPanelAssociarAlunos());
		vBodyPanel.add(new MpSpacePanel());
		vBodyPanel.add(gridBotaoSalvar());
	}
	
	public void criarTelaEditar(EditarOcorrencia editarOcorrencia){
		vBodyPanel.add(mpPanelFormularioOcorrencia());
		vBodyPanel.add(new MpSpacePanel());
		vBodyPanel.add(mpPanelAssociarAlunos());
		vBodyPanel.add(new MpSpacePanel());
		vBodyPanel.add(gridBotaoEditar());
		this.editarOcorrencia = editarOcorrencia;
	}
	
	public void popularCampos(OcorrenciaAux ocorrenciaAux){
		
		this.ocorrenciaAux = ocorrenciaAux;
		listBoxCurso.setSelectItem(this.ocorrenciaAux.getIdCurso());
		listBoxPeriodo.setSelectItem(this.ocorrenciaAux.getIdPeriodo());
		listBoxDisciplina.setSelectItem(this.ocorrenciaAux.getIdDisciplina());
		listBoxConteudo.setSelectItem(this.ocorrenciaAux.getIdConteudoProgramatico());
		
		listBoxCurso.setEnabled(false);
		listBoxPeriodo.setEnabled(false);
		listBoxDisciplina.setEnabled(false);
		listBoxConteudo.setEnabled(false);		
		
		txtAssunto.setText(this.ocorrenciaAux.getOcorrencia().getAssunto());
		txtDescricao.setText(this.ocorrenciaAux.getOcorrencia().getDescricao());
		mpDateBoxData.getDate().setValue(this.ocorrenciaAux.getOcorrencia().getData());
		mpTimePicker.setTime(this.ocorrenciaAux.getOcorrencia().getHora());	
		
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
		mpPanelForm.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable)+"px");
		
		FlexTable layout = new FlexTable();
		layout.setCellSpacing(2);
		layout.setCellPadding(2);
		layout.setBorderWidth(0);
		layout.setSize(Integer.toString(TelaInicialProfessorOcorrencia.intWidthTable),Integer.toString(TelaInicialProfessorOcorrencia.intHeightTable));
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		cellFormatter.setColSpan(0, 0, 0);
		cellFormatter.setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_CENTER);
		
		listBoxCurso = new MpSelectionCursoAmbienteProfessor(telaInicialProfessorOcorrencia.getMainView().getUsuarioLogado());		
		listBoxPeriodo = new MpSelectionPeriodoAmbienteProfessor(telaInicialProfessorOcorrencia.getMainView().getUsuarioLogado());		
		listBoxDisciplina = new MpSelectionDisciplinaAmbienteProfessor(telaInicialProfessorOcorrencia.getMainView().getUsuarioLogado());		
		listBoxConteudo = new MpSelectionConteudoProgramatico();
		
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());
		listBoxPeriodo.addChangeHandler(new MpPeriodoSelectionChangeHandler());		
		listBoxDisciplina.addChangeHandler(new MpDisciplinaSelectionChangeHandler());
		
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
		lblErroConteudo = new MpLabelTextBoxError();

		
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
		layout.setWidget(row, 0, lblCurso);layout.setWidget(row++, 1, listBoxCurso);
		layout.setWidget(row, 0, lblPeriodo);layout.setWidget(row++, 1, listBoxPeriodo);		
		layout.setWidget(row, 0, lblDisciplina);layout.setWidget(row++, 1, listBoxDisciplina);		
		layout.setWidget(row, 0, lblConteudo);layout.setWidget(row, 1, listBoxConteudo);layout.setWidget(row++, 2, lblErroConteudo);
		
		layout.setWidget(row, 0, lblAssunto);layout.setWidget(row, 1, txtAssunto);layout.setWidget(row++, 2, lblErroOcorrencia);
		layout.setWidget(row, 0, lblDescricao);layout.setWidget(row++, 1, txtDescricao);
		layout.setWidget(row, 0, lblData);layout.setWidget(row++, 1, mpDateBoxData);
		layout.setWidget(row, 0, lblHorario);layout.setWidget(row++, 1, mpTimePicker);

		mpPanelForm.add(layout);
		
		/***********************Begin Callbacks**********************/

		// Callback para adicionar Curso.
		callbackAdd = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
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
						cleanFields();
						mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
						mpDialogBoxConfirm.setBodyText(txtConstants.ocorrenciaSalva());
					}
					else if (enumOcorrencia == EnumOcorrencia.EDITAR) {
						editarOcorrencia.populateGridOcorrencia();
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
		};


		/***********************End Callbacks**********************/
		return mpPanelForm;
		
	}
	
	public MpPanelPageMainView mpPanelAssociarAlunos(){
		
		MpPanelPageMainView mpPanelAssociarAluno = new MpPanelPageMainView(txtConstants.ocorrenciaSelecionarAlunoEnviar(), "images/elementary_school_16.png");
		mpPanelAssociarAluno.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable)+"px");
		
		Grid gridFiltrar = new Grid(1,4);		
		gridFiltrar.setCellSpacing(3);
		gridFiltrar.setCellPadding(3);
		gridFiltrar.setBorderWidth(0);		
		
		Label lblFiltrarPais = new Label(txtConstants.ocorrenciaNomeDosAlunos());
		txtFiltroAlunos = new TextBox();		
		txtFiltroAlunos.addKeyPressHandler(new EnterKeyPressHandlerFiltrarAlunos());
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
		
		Grid gridSelecionar = new Grid(2,3);
		gridSelecionar.setCellSpacing(3);
		gridSelecionar.setCellPadding(3);
		gridSelecionar.setBorderWidth(0);		

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
	    
	    gridSelecionar.setWidget(0, 0, gridFiltrar);
	    gridSelecionar.setWidget(0, 1, new InlineHTML("&nbsp;"));
	    gridSelecionar.setWidget(0, 2, lblAluno);
	    gridSelecionar.setWidget(1, 0, multiBoxAlunosFiltrado);
	    gridSelecionar.setWidget(1, 1, gridBotoes);
	    gridSelecionar.setWidget(1, 2, multiBoxAlunosAssociado);	    
		
	    mpPanelAssociarAluno.add(gridSelecionar);
	    
		callbackGetAlunosFiltro = new AsyncCallback<ArrayList<Usuario>>() {

			public void onSuccess(ArrayList<Usuario> list) {
				
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
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.ocorrenciaErroBuscarAluno());
				mpDialogBoxWarning.showDialog();

			}
		};
		
		
		return mpPanelAssociarAluno;

	}	
		
	public VerticalPanel gridBotaoSalvar(){
		
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPanel.setWidth(Integer.toString(TelaInicialProfessorOcorrencia.intWidthTable)+"px");
		
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
		
		return vPanel;
	}
	
	public VerticalPanel gridBotaoEditar(){
		
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPanel.setWidth(Integer.toString(TelaInicialProfessorOcorrencia.intWidthTable)+"px");
		
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
		
		return vPanel;
	}
	
	/****************Begin Event Handlers*****************/
	
	private class ClickHandlerSave implements ClickHandler {

		public void onClick(ClickEvent event) {
			
//			int intIdConteudoProgramatico = Integer.parseInt(listBoxConteudoProgramatico.getValue(listBoxConteudoProgramatico.getSelectedIndex()));
			int intIdConteudo = listBoxConteudo.getSelectedIndex();

//			if (txtAssunto == null || txtAssunto.getText().isEmpty()) {
//
//				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//				mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.ocorrencia()));
//				mpDialogBoxWarning.showDialog();
//
//			} 
//			else if(intIdConteudo==-1){
//				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//				mpDialogBoxWarning.setBodyText(txtConstants.ocorrenciaErroSemUmConteudoProgramatico());
//				mpDialogBoxWarning.showDialog();
//			}else{
			
			if(checkFieldsValidator()){
				
				mpPanelLoading.setVisible(true);

				intIdConteudo = Integer.parseInt(listBoxConteudo.getValue(intIdConteudo));
				String strHora = mpTimePicker.getValue(mpTimePicker.getSelectedIndex());

				Ocorrencia ocorrencia = new Ocorrencia();
				ocorrencia.setAssunto(txtAssunto.getText());
				ocorrencia.setDescricao(txtDescricao.getText());
				ocorrencia.setData(mpDateBoxData.getDate().getValue());
				ocorrencia.setHora(MpUtilClient.convertStringToTime(strHora));
				ocorrencia.setIdConteudoProgramatico(intIdConteudo);
				
				ArrayList<Usuario> listUsuario = new ArrayList<Usuario>();
				for(int i=0;i<multiBoxAlunosAssociado.getItemCount();i++){
					int idUsuario = Integer.parseInt(multiBoxAlunosAssociado.getValue(i));
					Usuario  usuario = new Usuario();
					usuario.setIdUsuario(idUsuario);
					listUsuario.add(usuario);					
				}
				ocorrencia.setListUsuariosRelacionadosOcorrencia(listUsuario);
				
				if(AdicionarOcorrencia.this.enumOcorrencia == EnumOcorrencia.ADICIONAR){
					GWTServiceOcorrencia.Util.getInstance().AdicionarOcorrencia(ocorrencia, callbackAdd);	
				}
				else if(AdicionarOcorrencia.this.enumOcorrencia == EnumOcorrencia.EDITAR){
					ocorrencia.setIdOcorrencia(ocorrenciaAux.getOcorrencia().getIdOcorrencia());
					GWTServiceOcorrencia.Util.getInstance().AtualizarOcorrencia(ocorrencia, callbackAdd);
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
				listBoxConteudo.clear();
			}
			else{
				int idPeriodo = Integer.parseInt(listBoxPeriodo.getValue(index));
				listBoxDisciplina.populateComboBox(idPeriodo);				
			}
		}  
	}
	
	private class MpDisciplinaSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int index = listBoxDisciplina.getSelectedIndex();
			if(index==-1){
				listBoxConteudo.clear();
			}
			else{
				int idDisciplina= Integer.parseInt(listBoxDisciplina.getValue(index));
				listBoxConteudo.populateComboBox(idDisciplina);				
			}
		}  
	}	
	
	private class ClickHandlerFiltrarAlunos implements ClickHandler {
		public void onClick(ClickEvent event) {
			populateUsuarioPorCurso();
		}
	}	
	
	private class EnterKeyPressHandlerFiltrarAlunos implements KeyPressHandler{
		public void onKeyPress(KeyPressEvent event){
			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
				populateUsuarioPorCurso();
			}
		}
	}
	
	private void populateUsuarioPorCurso(){
		mpPanelLoading.setVisible(true);				
		int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
		GWTServiceUsuario.Util.getInstance().getUsuariosPorCurso(idCurso, "%" +  txtFiltroAlunos.getText() + "%", callbackGetAlunosFiltro);
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
		lblErroConteudo.hideErroMessage();
		lblErroOcorrencia.hideErroMessage();
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
		boolean isConteudoOk=false;
		
		if(FieldVerifier.isValidName(txtAssunto.getText())){
			isOcorrenciaOk=true;	
			lblErroOcorrencia.hideErroMessage();
		}else{
			isOcorrenciaOk=false;
			lblErroOcorrencia.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.ocorrencia()));
		}
		

		
		if(FieldVerifier.isValidListBoxSelectedValue(listBoxConteudo.getSelectedIndex())){
			isConteudoOk=true;
			lblErroConteudo.hideErroMessage();
		}else{
			lblErroConteudo.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.conteudoProgramatico()));
		}
		
		isFieldsOk = isOcorrenciaOk && isConteudoOk;

		
		return isFieldsOk;
	}	
	
	
	public void updateClientData(){
		listBoxCurso.populateComboBox(this.telaInicialProfessorOcorrencia.getMainView().getUsuarioLogado());
	}		
	

}
