package com.jornada.client.ambiente.professor.nota;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpPanelPageMainView;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.classes.Avaliacao;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.utility.MpUtilClient;


public class EditarNotaPorAluno extends VerticalPanel{
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	//Curso=Professor
	//Aluno=Disciplina
//	private AsyncCallback<ArrayList<Curso>> callBackPopulateCursoComboBox;	
	private AsyncCallback<ArrayList<Usuario>> callbackGetAlunosFiltro;
//	private AsyncCallback<ArrayList<Disciplina>> callbackGetDisciplinasFiltro;
//	private AsyncCallback<ArrayList<Disciplina>> callbackGetDisciplinasAssociadas;	
//	private AsyncCallback<Boolean> callbackAssociarDisciplinaAoProfessor;
//	private AsyncCallback<ArrayList<Periodo>> callBackPopulatePeriodoComboBox;
	
	
	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpPanelAlunosLoading = new MpPanelLoading("images/radar.gif");
//	MpPanelLoading mpPanelDisciplinaLoading = new MpPanelLoading("images/radar.gif");
//	MpPanelLoading mpPanelAssociandoLoading = new MpPanelLoading("images/radar.gif");
	
	private CellTable<Avaliacao> cellTable;
//	private ListDataProvider<Avaliacao> dataProvider = new ListDataProvider<Avaliacao>();	
	
	
	private TextBox txtFiltroAluno;	
//	private TextBox txtFiltroDisciplina;
	
//	private ListBox listBoxCurso;
//	private ListBox listBoxPeriodo;		
	private ListBox listBoxAluno;
//	private ListBox multiBoxDisciplinaFiltrado;
//	private ListBox multiBoxDisciplinaAssociada;
	
//	private CadastroNota cadastroNota;

	
	public EditarNotaPorAluno(TelaInicialNota telaInicialNota){
		
//		this.cadastroNota=cadastroNota;
		
		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		
		mpPanelAlunosLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelAlunosLoading.show();
		mpPanelAlunosLoading.setVisible(false);
		
//		mpPanelDisciplinaLoading.setTxtLoading("loading...");
//		mpPanelDisciplinaLoading.show();
//		mpPanelDisciplinaLoading.setVisible(false);
//
//		mpPanelAssociandoLoading.setTxtLoading("loading...");
//		mpPanelAssociandoLoading.show();
//		mpPanelAssociandoLoading.setVisible(false);
		
		
		VerticalPanel vBodyPanel = new VerticalPanel();
		
		vBodyPanel.add(drawPassoUmSelecioneProfessor());
		vBodyPanel.add(new InlineHTML("&nbsp;"));
		vBodyPanel.add(drawPassoDoisEditarNotasAluno());
//		vBodyPanel.add(new InlineHTML("&nbsp;"));
//		vBodyPanel.add(drawPassoTresSubmeterAssociacao());		
		
		super.add(vBodyPanel);		
		
		
	}
	
	
	
	public MpPanelPageMainView drawPassoUmSelecioneProfessor(){

		
		MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.notaSelecionarAlunoDesejaAdicionarAlterarNota(), "images/user_male_black_red_16.png");
		mpPanel.setWidth(Integer.toString(TelaInicialNota.intWidthTable)+"px");
		
		FlexTable flexTableFiltrar = new FlexTable();		
		flexTableFiltrar.setCellSpacing(3);
		flexTableFiltrar.setCellPadding(3);
		flexTableFiltrar.setBorderWidth(0);		
		
		Label lblFiltrarAluno = new Label(txtConstants.alunoNome());
		txtFiltroAluno = new TextBox();		
		txtFiltroAluno.addKeyUpHandler(new EnterKeyUpHandlerFiltrarAluno());
		MpImageButton btnFiltrar = new MpImageButton(txtConstants.usuarioFiltrarListaAlunos(), "images/magnifier.png");
		btnFiltrar.addClickHandler(new ClickHandlerFiltrarAluno());		
		
		lblFiltrarAluno.setStyleName("design_label");	
		lblFiltrarAluno.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		txtFiltroAluno.setStyleName("design_text_boxes");		
		
		listBoxAluno = new ListBox();
		listBoxAluno.setStyleName("design_text_boxes");
		listBoxAluno.setWidth("350px");
//		listBoxAluno.addChangeHandler(new ChangeHandlerPopularDisciplinasAssociadas());			

		
		flexTableFiltrar.setWidget(0, 0, lblFiltrarAluno);
		flexTableFiltrar.setWidget(0, 1, listBoxAluno);
		flexTableFiltrar.setWidget(0, 2, txtFiltroAluno);
		flexTableFiltrar.setWidget(0, 3, btnFiltrar);
		flexTableFiltrar.setWidget(0, 4, mpPanelAlunosLoading);	
		
		mpPanel.add(flexTableFiltrar);

		
		callbackGetAlunosFiltro = new AsyncCallback<ArrayList<Usuario>>() {

			public void onSuccess(ArrayList<Usuario> list) {
				MpUtilClient.isRefreshRequired(list);
				mpPanelAlunosLoading.setVisible(false);
				
					listBoxAluno.clear();
					for (int i = 0; i < list.size(); i++) {
						Usuario usuario = list.get(i);
						listBoxAluno.addItem(usuario.getPrimeiroNome() + " "+ usuario.getSobreNome(),Integer.toString(usuario.getIdUsuario()));
					}
				
//				popularDisciplinasAssociadas();
				
			}
			
			public void onFailure(Throwable caught) {
				mpPanelAlunosLoading.setVisible(false);
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.notaErroBuscarAluno());
				mpDialogBoxWarning.showDialog();

			}
		};		
		
		popularOnLoadListBoxAluno();		
		
		return mpPanel;
		
	}
	
	
	private MpPanelPageMainView drawPassoDoisEditarNotasAluno(){
		MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.notaInserirAlterarNotasAluno(), "images/Test-paper-16.png");
		mpPanel.setWidth(Integer.toString(TelaInicialNota.intWidthTable)+"px");
		
		cellTable = new CellTable<Avaliacao>(5,GWT.<CellTableStyle> create(CellTableStyle.class));
		cellTable.setWidth(Integer.toString(TelaInicialNota.intWidthTable)+ "px");
		
		// Add a selection model so we can select cells.
		final SelectionModel<Avaliacao> selectionModel = new MultiSelectionModel<Avaliacao>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<Avaliacao> createCheckboxManager());
		
		return mpPanel;
		
	}	
	

	private void popularOnLoadListBoxAluno(){
		mpPanelAlunosLoading.setVisible(true);				
		GWTServiceUsuario.Util.getInstance().getUsuariosPorTipoUsuario(TipoUsuario.ALUNO, "%" + txtFiltroAluno.getText() + "%", callbackGetAlunosFiltro);		
	}
	
	private class ClickHandlerFiltrarAluno implements ClickHandler {
		public void onClick(ClickEvent event) {
				mpPanelAlunosLoading.setVisible(true);				
				GWTServiceUsuario.Util.getInstance().getUsuariosPorTipoUsuario(TipoUsuario.ALUNO, "%" + txtFiltroAluno.getText() + "%", callbackGetAlunosFiltro);
		}
	}
	
	private class EnterKeyUpHandlerFiltrarAluno implements KeyUpHandler{
		public void onKeyUp(KeyUpEvent event){
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				mpPanelAlunosLoading.setVisible(true);				
				GWTServiceUsuario.Util.getInstance().getUsuariosPorTipoUsuario(TipoUsuario.ALUNO, "%" + txtFiltroAluno.getText() + "%", callbackGetAlunosFiltro);
			}
		}
	}		
	
}
