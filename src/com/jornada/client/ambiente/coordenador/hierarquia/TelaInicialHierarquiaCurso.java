package com.jornada.client.ambiente.coordenador.hierarquia;

import java.util.ArrayList;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import com.jornada.client.classes.resources.CellTreeStyle;
import com.jornada.client.classes.resources.CustomTreeModel;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpPanelPageMainView;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceHierarquiaCurso;
import com.jornada.shared.classes.Avaliacao;
import com.jornada.shared.classes.ConteudoProgramatico;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.Topico;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.utility.MpUtilClient;

public class TelaInicialHierarquiaCurso extends Composite{
	
	
	private AsyncCallback<ArrayList<Curso>> callBackListaCursos;
	
	
	public  static final int intWidthTable=1400;
	public static final int intHeightTable=500;
	
	public static final int intHeightNavigationPanel=300;
	
	VerticalPanel panelTree;
	VerticalPanel panelDetalhes;
	
	Label labelMessage;
	
	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	
	TextConstants txtConstants;
	
	public TelaInicialHierarquiaCurso(){
		
		txtConstants = GWT.create(TextConstants.class);
		
		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);		
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);
		mpPanelLoading.setWidth(Integer.toString(intHeightNavigationPanel)+"px");
		
		
		/************************* Begin Callback's *************************/
		
		callBackListaCursos = new AsyncCallback<ArrayList<Curso>>() {
		
		public void onSuccess(ArrayList<Curso> listaCurso) {
			
			labelMessage = new Label("");
			
			
			final SingleSelectionModel<Object> selectionModel = new SingleSelectionModel<Object>(CustomTreeModel.KEY_PROVIDER_OBJECT);
			

			TreeViewModel treeViewModel = new CustomTreeModel(listaCurso, selectionModel);
			
			CellTree.Resources resource = GWT.create(CellTreeStyle.class);
			
			CellTree tree = new CellTree(treeViewModel, null,  resource);
			tree.setAnimationEnabled(true);
			tree.setWidth(Integer.toString(intHeightNavigationPanel-5)+"px");
			

		    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		          
		    	public void onSelectionChange(SelectionChangeEvent event) {
		    		
//		    		SingleSelectionModel<Object> selectionModelAux = selectionModel;
		    		

		            Object object = selectionModel.getSelectedObject();

//		            selectionModel.setSelected(object, true);
		            
		            if(object instanceof Curso){
		            	showCurso((Curso)object);
		            }
		            else if (object instanceof Periodo){
		            	showPeriodo((Periodo)object);
		            }
		            else if (object instanceof Disciplina){		            	
		            	showDisciplina((Disciplina)object);
		            }	
		            else if (object instanceof ConteudoProgramatico){
		            	showConteudoProgramatico((ConteudoProgramatico)object);
		            }
		            else if (object instanceof Topico){
		            	showTopico((Topico)object);
		            }		            
		            else{
		            	labelMessage.setText("No Object");
		            }
		            
		          }
		    	
		        });			
			
	

			 // Open the first Curso by default.
		    TreeNode rootNode = tree.getRootTreeNode();
		    TreeNode firstPlaylist = rootNode.setChildOpen(0, true);
		    firstPlaylist.setChildOpen(0, true);

			
//			panelTree.add(labelMessage);
			mpPanelLoading.setVisible(false);
			panelTree.clear();
			panelTree.add(tree);
	

		}

		public void onFailure(Throwable cautch) {
			mpPanelLoading.setVisible(false);	
			mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
			mpDialogBoxWarning.setBodyText(txtConstants.cursoErroCarregarLista());
			mpDialogBoxWarning.showDialog();
			

		}
	};		
		
	/*********************** End Callbacks **********************/				
		
//		panelTree.setBorderWidth(1);
	
		panelDetalhes = new VerticalPanel();
		panelDetalhes.setBorderWidth(0);
		panelDetalhes.setSize(Integer.toString(intWidthTable-200)+"px",Integer.toString(intHeightTable)+"px");
		panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
	
		panelTree = new VerticalPanel();
		//panelTree.setSize(Integer.toString(intHeightNavigationPanel)+"px",Integer.toString(intHeightTable)+"px");
		panelTree.add(mpPanelLoading);	
		
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.add(panelTree);
		scrollPanel.setAlwaysShowScrollBars(false);
		scrollPanel.setSize(Integer.toString(intHeightNavigationPanel)+"px",Integer.toString(intHeightTable)+"px");
		
		MpPanelPageMainView mpPanelTree = new MpPanelPageMainView(txtConstants.cursoNavegacao(), "images/view_tree-16.png");
		//mpPanelTree.setSize(Integer.toString(intHeightNavigationPanel)+"px",Integer.toString(intHeightTable)+"px");
		mpPanelTree.addPage(scrollPanel);
		
		
		
		Grid grid = new Grid(1,2);
		grid.setCellPadding(0);
		grid.setCellSpacing(0);
		grid.setBorderWidth(0);
		
		grid.setWidget(0, 0, mpPanelTree);
		grid.setWidget(0, 1, panelDetalhes);
		grid.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
		
		initWidget(grid);
		populateTree();

	}	
	
	
	public void populateTree(){
		mpPanelLoading.setVisible(true);
		GWTServiceHierarquiaCurso.Util.getInstance().getHierarquiaCursos(callBackListaCursos);
	}
	
	
	public void showCurso(Curso object){
		
		panelDetalhes.clear();

		FlexTable flexTable = new FlexTable();
		flexTable.setCellPadding(2);
		flexTable.setCellSpacing(2);	
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		flexTable.setBorderWidth(0);
		flexTable.getColumnFormatter().setWidth(0, "150px");
		
		VerticalPanel vPanelTitle = vPanelTitulo(txtConstants.curso(), object.getNome(), "images/folder_library-24.png");
		
		Label lblNomeCurso = new Label(txtConstants.cursoNome());				
		Label lblDescricaoCurso = new Label(txtConstants.cursoDescricao());				
		Label lblEmentaCurso = new Label(txtConstants.cursoEmenta());			
		Label lblDataInicialCurso = new Label(txtConstants.cursoDataInicial());			
		Label lblDataFinalCurso = new Label(txtConstants.cursoDataFinal());			
		Label lblAlunosCurso = new Label(txtConstants.cursoAlunosDoCurso());		
		
		Label lblNomeCursoDB = new Label(object.getNome());
//		Label lblDescricaoCursoDB = new Label(object.getDescricao());	
		TextArea lblDescricaoCursoDB = new TextArea();
		lblDescricaoCursoDB.setValue(object.getDescricao());
		lblDescricaoCursoDB.setSize("350px", "50px");
		Label lblEmentaCursoDB = new Label(object.getEmenta());			
		Label lblDataInicialCursoDB = new Label(MpUtilClient.convertDateToString(object.getDataInicial()));	
		Label lblDataFinalCursoDB = new Label(MpUtilClient.convertDateToString(object.getDataFinal()));		
		
		lblNomeCurso.setStyleName("label_comum_bold_12px");
		lblNomeCursoDB.setStyleName("design_label");
		lblDescricaoCurso.setStyleName("label_comum_bold_12px");
//		lblDescricaoCursoDB.setStyleName("design_label");		
		lblDescricaoCursoDB.setStyleName("design_text_boxes");
		lblEmentaCurso.setStyleName("label_comum_bold_12px");
		lblEmentaCursoDB.setStyleName("design_label");	
		lblDataInicialCurso.setStyleName("label_comum_bold_12px");
		lblDataInicialCursoDB.setStyleName("design_label");	
		lblDataFinalCurso.setStyleName("label_comum_bold_12px");
		lblDataFinalCursoDB.setStyleName("design_label");		
		lblAlunosCurso.setStyleName("label_comum_bold_12px");
		
		int row=0;
		flexTable.setWidget(row++, 0, vPanelTitle);
//		flexTable.setWidget(row, 0, lblNomeCurso);flexTable.setWidget(row++, 1, lblNomeCursoDB);
		flexTable.setWidget(row, 0, lblDescricaoCurso);flexTable.setWidget(row++, 1, lblDescricaoCursoDB);
		flexTable.setWidget(row, 0, lblEmentaCurso);flexTable.setWidget(row++, 1, lblEmentaCursoDB);
		flexTable.setWidget(row, 0, lblDataInicialCurso);flexTable.setWidget(row++, 1, lblDataInicialCursoDB);
		flexTable.setWidget(row, 0, lblDataFinalCurso);flexTable.setWidget(row++, 1, lblDataFinalCursoDB);
		flexTable.setWidget(row++, 0, new InlineHTML("&nbsp;"));
		flexTable.setWidget(row++, 0, lblAlunosCurso);

		
		CellTable<Usuario> cellTable = new CellTable<Usuario>(10,GWT.<CellTableStyle> create(CellTableStyle.class));

		cellTable.setWidth(Integer.toString(intWidthTable-250)+ "px");
        cellTable.setPageSize(10);
	    
	    MpSimplePager mpPager = new MpSimplePager();
		mpPager.setDisplay(cellTable);
		mpPager.setPageSize(10);
		
		Column<Usuario, String> columnPrimeiroNome = new Column<Usuario, String>(new TextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getPrimeiroNome();
			}
		};		
		Column<Usuario, String> columnSobreNome = new Column<Usuario, String>(new TextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getSobreNome();
			}
		};	
		Column<Usuario, String> columnCPF = new Column<Usuario, String>(new TextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getCpf();
			}
		};		
		Column<Usuario, String> columnEmail = new Column<Usuario, String>(new TextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getEmail();
			}
		};		
		Column<Usuario, String> columnDataNascimento = new Column<Usuario, String>(new TextCell()) {
			@Override
			public String getValue(Usuario object) {
				return MpUtilClient.convertDateToString(object.getDataNascimento());
			}
		};	
		Column<Usuario, String> columnTelefoneCelular = new Column<Usuario, String>(new TextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getTelefoneCelular();
			}
		};		
		Column<Usuario, String> columnTelefoneResidencial = new Column<Usuario, String>(
				new EditTextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getTelefoneResidencial();
			}
		};	
		
		cellTable.addColumn(columnPrimeiroNome, txtConstants.usuarioPrimeiroNome());
		cellTable.addColumn(columnSobreNome, txtConstants.usuarioSobreNome());
		cellTable.addColumn(columnCPF, txtConstants.usuarioCPF());
		cellTable.addColumn(columnEmail, txtConstants.usuarioEmail());
		cellTable.addColumn(columnDataNascimento, txtConstants.usuarioDataNascimento());
		cellTable.addColumn(columnTelefoneCelular, txtConstants.usuarioTelCelular());
		cellTable.addColumn(columnTelefoneResidencial, txtConstants.usuarioTelResidencial());
		
		
		cellTable.getColumn(cellTable.getColumnIndex(columnPrimeiroNome)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(columnSobreNome)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(columnCPF)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(columnEmail)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(columnDataNascimento)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(columnTelefoneCelular)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(columnTelefoneResidencial)).setCellStyleNames("hand-over-default");		
		
		ListDataProvider<Usuario> dataProvider = new ListDataProvider<Usuario>();
		dataProvider.addDataDisplay(cellTable);
		
		dataProvider.getList().clear();
		
		for(int i=0;i<object.getListAlunos().size();i++){
			dataProvider.getList().add(object.getListAlunos().get(i));
		}


		
		Grid gridAlunos = new Grid(2,1);
		gridAlunos.setCellPadding(0);
		gridAlunos.setCellSpacing(0);
		
		gridAlunos.setWidget(0, 0, cellTable);
		gridAlunos.setWidget(1, 0, mpPager);
		
		flexTable.setWidget(row, 0, gridAlunos);
		flexTable.getFlexCellFormatter().setColSpan(row++, 0, 2);
		
		panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		panelDetalhes.add(flexTable);		

		
	}
	
	public void showPeriodo(Periodo object){
		panelDetalhes.clear();

		FlexTable flexTable = new FlexTable();
		flexTable.setCellPadding(2);
		flexTable.setCellSpacing(2);	
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		flexTable.setBorderWidth(0);
		flexTable.getColumnFormatter().setWidth(0, "150px");
		
		VerticalPanel vPanelTitle = vPanelTitulo(txtConstants.periodo(), object.getNomePeriodo(), "images/my_projects_folder-24.png");
		
		Label lblNomePeriodo = new Label(txtConstants.periodoNome());	
		Label lblNomePeriodoDB = new Label(object.getNomePeriodo());
		
		Label lblDescricaoPeriodo = new Label(txtConstants.periodoDescricao());	
		Label lblDescricaoPeriodoDB = new Label(object.getDescricao());	
		
		Label lblObjetivoPeriodo = new Label(txtConstants.periodoObjetivo());	
		Label lblObjetivoPeriodoDB = new Label(object.getObjetivo());			
		
		Label lblDataInicialPeriodo = new Label(txtConstants.periodoDataInicial());	
		Label lblDataInicialPeriodoDB = new Label(MpUtilClient.convertDateToString(object.getDataInicial()));				
		
		Label lblDataFinalPeriodo = new Label(txtConstants.periodoDataFinal());	
		Label lblDataFinalPeriodoDB = new Label(MpUtilClient.convertDateToString(object.getDataFinal()));			
		
		
		lblNomePeriodo.setStyleName("label_comum_bold_12px");
		lblNomePeriodoDB.setStyleName("design_label");
		lblDescricaoPeriodo.setStyleName("label_comum_bold_12px");
		lblDescricaoPeriodoDB.setStyleName("design_label");		
		lblObjetivoPeriodo.setStyleName("label_comum_bold_12px");
		lblObjetivoPeriodoDB.setStyleName("design_label");	
		lblDataInicialPeriodo.setStyleName("label_comum_bold_12px");
		lblDataInicialPeriodoDB.setStyleName("design_label");	
		lblDataFinalPeriodo.setStyleName("label_comum_bold_12px");
		lblDataFinalPeriodoDB.setStyleName("design_label");		
		
		int row=0;
		flexTable.setWidget(row++, 0, vPanelTitle);
//		flexTable.setWidget(row, 0, lblNomePeriodo);flexTable.setWidget(row++, 1, lblNomePeriodoDB);
		flexTable.setWidget(row, 0, lblDescricaoPeriodo);flexTable.setWidget(row++, 1, lblDescricaoPeriodoDB);
		flexTable.setWidget(row, 0, lblObjetivoPeriodo);flexTable.setWidget(row++, 1, lblObjetivoPeriodoDB);
		flexTable.setWidget(row, 0, lblDataInicialPeriodo);flexTable.setWidget(row++, 1, lblDataInicialPeriodoDB);
		flexTable.setWidget(row, 0, lblDataFinalPeriodo);flexTable.setWidget(row++, 1, lblDataFinalPeriodoDB);
		panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		
		panelDetalhes.add(flexTable);
	}
	
	public void showDisciplina(Disciplina object){
		panelDetalhes.clear();

		FlexTable flexTable = new FlexTable();
		flexTable.setCellPadding(2);
		flexTable.setCellSpacing(2);	
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		flexTable.getColumnFormatter().setWidth(0, "150px");

		VerticalPanel vPanelTitle = vPanelTitulo(txtConstants.disciplina(), object.getNome(), "images/books-24.png");
		
		Label lblNome = new Label(txtConstants.disciplinaNome());	
		Label lblNomeDB = new Label(object.getNome());
		
		Label lblCargaHorario = new Label(txtConstants.disciplinaCarga());	
		Label lblCargaHorarioDB = new Label(Integer.toString(object.getCargaHoraria()));				
		
		Label lblDescricao = new Label(txtConstants.disciplinaDescricao());	
		Label lblDescricaoDB = new Label(object.getDescricao());	
		
		Label lblObjetivo = new Label(txtConstants.disciplinaObjetivo());	
		Label lblObjetivoDB = new Label(object.getObjetivo());			
		
		Label lblProfessor = new Label(txtConstants.professor());
		String strProfessorNome = object.getProfessor()==null? "" : object.getProfessor().getPrimeiroNome();
		String strProfessorSobreNome = object.getProfessor()==null? "" : object.getProfessor().getSobreNome();
		Label lblProfessorDB = new Label(strProfessorNome + " " + strProfessorSobreNome);				
		
		lblNome.setStyleName("label_comum_bold_12px");
		lblNomeDB.setStyleName("design_label");
		lblCargaHorario.setStyleName("label_comum_bold_12px");
		lblCargaHorarioDB.setStyleName("design_label");	
		lblDescricao.setStyleName("label_comum_bold_12px");
		lblDescricaoDB.setStyleName("design_label");		
		lblObjetivo.setStyleName("label_comum_bold_12px");
		lblObjetivoDB.setStyleName("design_label");	
		lblProfessor.setStyleName("label_comum_bold_12px");
		lblProfessorDB.setStyleName("design_label");			
	
		
		int row=0;
		flexTable.setWidget(row++, 0, vPanelTitle);
//		flexTable.setWidget(row, 0, lblNome);flexTable.setWidget(row++, 1, lblNomeDB);
		flexTable.setWidget(row, 0, lblCargaHorario);flexTable.setWidget(row++, 1, lblCargaHorarioDB);		
		flexTable.setWidget(row, 0, lblDescricao);flexTable.setWidget(row++, 1, lblDescricaoDB);
		flexTable.setWidget(row, 0, lblObjetivo);flexTable.setWidget(row++, 1, lblObjetivoDB);
		flexTable.setWidget(row, 0, lblProfessor);flexTable.setWidget(row++, 1, lblProfessorDB);

		panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		
		panelDetalhes.add(flexTable);
	}	
	
	public void showConteudoProgramatico(ConteudoProgramatico object) {
		panelDetalhes.clear();

		FlexTable flexTable = new FlexTable();
		flexTable.setCellPadding(2);
		flexTable.setCellSpacing(2);
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		flexTable.getColumnFormatter().setWidth(0, "150px");

		VerticalPanel vPanelTitle = vPanelTitulo(txtConstants.conteudoProgramatico(), object.getNome(), "images/textdocument-24.png");

		Label lblNome = new Label(txtConstants.conteudoProgramaticoNome());
		Label lblNomeDB = new Label(object.getNome());
		Label lblNumeracao = new Label(txtConstants.conteudoProgramaticoNumeracao());
		Label lblNumeracaoDB = new Label(object.getNumeracao());
		Label lblDescricao = new Label(txtConstants.conteudoProgramaticoDescricao());
		Label lblDescricaoDB = new Label(object.getDescricao());
		Label lblObjetivo = new Label(txtConstants.conteudoProgramaticoObjetivo());
		Label lblObjetivoDB = new Label(object.getObjetivo());
		Label lblAvaliacoes = new Label(txtConstants.avaliacao());

		lblNome.setStyleName("label_comum_bold_12px");
		lblNomeDB.setStyleName("design_label");
		lblNumeracao.setStyleName("label_comum_bold_12px");
		lblNumeracaoDB.setStyleName("design_label");
		lblDescricao.setStyleName("label_comum_bold_12px");
		lblDescricaoDB.setStyleName("design_label");
		lblObjetivo.setStyleName("label_comum_bold_12px");
		lblObjetivoDB.setStyleName("design_label");
		lblAvaliacoes.setStyleName("label_comum_bold_12px");

		int row = 0;
		flexTable.setWidget(row++, 0, vPanelTitle);
		// flexTable.setWidget(row, 0, lblNome);flexTable.setWidget(row++, 1,lblNomeDB);
		flexTable.setWidget(row, 0, lblNumeracao);flexTable.setWidget(row++, 1, lblNumeracaoDB);
		flexTable.setWidget(row, 0, lblDescricao);flexTable.setWidget(row++, 1, lblDescricaoDB);
		flexTable.setWidget(row, 0, lblObjetivo);flexTable.setWidget(row++, 1, lblObjetivoDB);
		flexTable.setWidget(row++, 0, new InlineHTML("&nbsp"));
		flexTable.setWidget(row++, 0, lblAvaliacoes);

		Label lblEmpty = new Label(txtConstants.avaliacaoNenhumaCurso());

		CellTable<Avaliacao> cellTable = new CellTable<Avaliacao>(5,GWT.<CellTableStyle> create(CellTableStyle.class));
		cellTable.setEmptyTableWidget(lblEmpty);
		cellTable.setWidth(Integer.toString(intWidthTable - 250) + "px");
		cellTable.setPageSize(10);

		MpSimplePager mpPager = new MpSimplePager();
		mpPager.setDisplay(cellTable);
		mpPager.setPageSize(10);

		ListDataProvider<Avaliacao> dataProvider = new ListDataProvider<Avaliacao>();

		Column<Avaliacao, String> assuntoColumn = new Column<Avaliacao, String>(
				new TextCell()) {
			@Override
			public String getValue(Avaliacao object) {
				return object.getAssunto();
			}
		};
		Column<Avaliacao, String> descricaoColumn = new Column<Avaliacao, String>(
				new TextCell()) {
			@Override
			public String getValue(Avaliacao object) {
				return object.getDescricao();
			}
		};
		Column<Avaliacao, String> columnTipoAvaliacao = new Column<Avaliacao, String>(
				new TextCell()) {
			@Override
			public String getValue(Avaliacao object) {
				return object.getTipoAvaliacao().getNomeTipoAvaliacao();
			}
		};
		Column<Avaliacao, String> dataColumn = new Column<Avaliacao, String>(
				new TextCell()) {
			@Override
			public String getValue(Avaliacao object) {
//				return object.getData();
				return MpUtilClient.convertDateToString(object.getData());
			}
		};
		Column<Avaliacao, String> horaColumn = new Column<Avaliacao, String>(
				new TextCell()) {
			@Override
			public String getValue(Avaliacao object) {
				return object.getHora();
			}
		};

		cellTable.addColumn(assuntoColumn, txtConstants.avaliacaoAssunto());
		cellTable.addColumn(descricaoColumn, txtConstants.avaliacaoDescricao());
		cellTable.addColumn(columnTipoAvaliacao, txtConstants.avaliacaoTipo());
		cellTable.addColumn(dataColumn, txtConstants.avaliacaoData());
		cellTable.addColumn(horaColumn, txtConstants.avaliacaoHora());

		cellTable.getColumn(cellTable.getColumnIndex(assuntoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(descricaoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(columnTipoAvaliacao)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(dataColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(horaColumn)).setCellStyleNames("hand-over-default");

		dataProvider.addDataDisplay(cellTable);
		

		dataProvider.getList().clear();

		for (int i = 0; i < object.getListAvaliacao().size(); i++) {
			dataProvider.getList().add(object.getListAvaliacao().get(i));
		}

		Grid gridAvaliacoes = new Grid(2, 1);
		gridAvaliacoes.setCellPadding(0);
		gridAvaliacoes.setCellSpacing(0);
		gridAvaliacoes.setWidget(0, 0, cellTable);
		gridAvaliacoes.setWidget(1, 0, mpPager);
		

		flexTable.setWidget(row, 0, gridAvaliacoes);
		flexTable.getFlexCellFormatter().setColSpan(row++, 0, 2);

		panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

		panelDetalhes.add(flexTable);
	}
	
	public void showTopico(Topico object){
		
		panelDetalhes.clear();
		
		VerticalPanel vPanelTitle = vPanelTitulo(txtConstants.topico(),object.getNome(), "images/type_list-24.png");
		
		FlexTable flexTable = new FlexTable();
		flexTable.setCellPadding(2);
		flexTable.setCellSpacing(2);	
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		flexTable.getColumnFormatter().setWidth(0, "150px");
		
		Label lblNome = new Label(txtConstants.topicoNome());	
		Label lblNomeDB = new Label(object.getNome());
		
		Label lblNumeracao = new Label(txtConstants.topicoNumeracao());	
		Label lblNumeracaoDB = new Label(object.getNumeracao());				
		
		Label lblDescricao = new Label(txtConstants.topicoDescricao());	
		Label lblDescricaoDB = new Label(object.getDescricao());	
		
		Label lblObjetivo = new Label(txtConstants.topicoObjetivo());	
		Label lblObjetivoDB = new Label(object.getObjetivo());
		
		lblNome.setStyleName("label_comum_bold_12px");lblNomeDB.setStyleName("design_label");
		lblNumeracao.setStyleName("label_comum_bold_12px");lblNumeracaoDB.setStyleName("design_label");	
		lblDescricao.setStyleName("label_comum_bold_12px");lblDescricaoDB.setStyleName("design_label");		
		lblObjetivo.setStyleName("label_comum_bold_12px");lblObjetivoDB.setStyleName("design_label");	

		
		int row=0;
		flexTable.setWidget(row++, 0, vPanelTitle);
//		flexTable.setWidget(row, 0, lblNome);flexTable.setWidget(row++, 1, lblNomeDB);
		flexTable.setWidget(row, 0, lblNumeracao);flexTable.setWidget(row++, 1, lblNumeracaoDB);		
		flexTable.setWidget(row, 0, lblDescricao);flexTable.setWidget(row++, 1, lblDescricaoDB);
		flexTable.setWidget(row, 0, lblObjetivo);flexTable.setWidget(row++, 1, lblObjetivoDB);

		panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		

		
		panelDetalhes.add(flexTable);
	}	
	
	
	public VerticalPanel vPanelTitulo(String strTitle, String strNome, String strImgAddress){
		Label lblTitulo = new Label(strTitle);
		lblTitulo.setStyleName("label_comum_bold_12px");
		Label lblNome = new Label(strNome);
		lblNome.setStyleName("design_label");
		lblTitulo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);	
		
		VerticalPanel vPanelTitle = new VerticalPanel();
		vPanelTitle.setStyleName("designTree");
		vPanelTitle.setWidth(Integer.toString(intWidthTable-200)+ "px");		
		
		vPanelTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		vPanelTitle.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		Image img = new Image(strImgAddress);
		
		Grid grid = new Grid(1,3);
		grid.setCellPadding(1);
		grid.setCellSpacing(1);
		grid.setWidget(0, 0, img);
		grid.setWidget(0, 1, lblTitulo);	
		grid.setWidget(0, 2, lblNome);	
		
		vPanelTitle.add(grid);	
		
		return vPanelTitle;
	}

}


