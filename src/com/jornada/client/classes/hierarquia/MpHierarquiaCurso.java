package com.jornada.client.classes.hierarquia;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import com.jornada.client.MainView;
import com.jornada.client.classes.resources.CellTreeStyle;
import com.jornada.client.classes.resources.CustomTreeModel;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpPanelPageMainView;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.shared.classes.ConteudoProgramatico;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.Topico;

public abstract class MpHierarquiaCurso extends Composite{
	
	
//	private TextBox txtSearchCurso;
//	ListDataProvider<Usuario> dataProviderCurso = new ListDataProvider<Usuario>();
//	ArrayList<Usuario> arrayListBackupCurso = new ArrayList<Usuario>();

	
	
	protected AsyncCallback<ArrayList<Curso>> callBackListaCursos;
	
	
	protected  static final int intWidthTable=1400;
	protected static final int intHeightTable=500;
	
	protected static final int intWidthNavigationPanel=300;
	
	protected VerticalPanel panelTree;
	protected VerticalPanel panelDetalhes;
	
	protected Label labelMessage;
	
	protected MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	protected MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
	protected MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	
	protected static TextConstants txtConstants = GWT.create(TextConstants.class);
	
//	private MainView mainView;
	
	public MpHierarquiaCurso(){
		
		
		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);		
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);
		mpPanelLoading.setWidth(Integer.toString(intWidthNavigationPanel)+"px");
		
		
		/************************* Begin Callback's *************************/
		
		callBackListaCursos = new AsyncCallback<ArrayList<Curso>>() {
		
		public void onSuccess(ArrayList<Curso> listaCurso) {
			
			labelMessage = new Label("");
			
			
			final SingleSelectionModel<Object> selectionModel = new SingleSelectionModel<Object>(CustomTreeModel.KEY_PROVIDER_OBJECT);
			

			TreeViewModel treeViewModel = new CustomTreeModel(listaCurso, selectionModel);
			
			CellTree.Resources resource = GWT.create(CellTreeStyle.class);
			
			CellTree tree = new CellTree(treeViewModel, null,  resource);
			tree.setAnimationEnabled(false);
			tree.setWidth(Integer.toString(intWidthNavigationPanel-5)+"px");
			

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
//		    TreeNode rootNode = tree.getRootTreeNode();
//		    TreeNode firstPlaylist = rootNode.setChildOpen(0, true);
//		    firstPlaylist.setChildOpen(0, true);

			
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
		
		ScrollPanel scrollPanelTree = new ScrollPanel();
		scrollPanelTree.add(panelTree);
		scrollPanelTree.setAlwaysShowScrollBars(false);
		scrollPanelTree.setSize(Integer.toString(intWidthNavigationPanel)+"px",Integer.toString(intHeightTable)+"px");
		
//		scrollPanelDetalhes.setWidth(Integer.toString(intWidthTable-150)+ "px");
//		scrollPanelDetalhes.setHeight(Integer.toString(intHeightTable)+ "px");
		
		MpPanelPageMainView mpPanelTree = new MpPanelPageMainView(txtConstants.cursoNavegacao(), "images/view_tree-16.png");
		//mpPanelTree.setSize(Integer.toString(intHeightNavigationPanel)+"px",Integer.toString(intHeightTable)+"px");
		mpPanelTree.addPage(scrollPanelTree);
		
		
		
		Grid grid = new Grid(1,2);
		grid.setCellPadding(0);
		grid.setCellSpacing(0);
		grid.setBorderWidth(0);
		
		grid.setWidget(0, 0, mpPanelTree);
		grid.setWidget(0, 1, panelDetalhes);
		grid.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
		
		initWidget(grid);
//		populateTree();

	}	
	
	
	public abstract void populateTree(MainView mainView);
//	{
//		mpPanelLoading.setVisible(true);
//		GWTServiceHierarquiaCurso.Util.getInstance().getHierarquiaCursosAmbienteAluno(this.mainView.getUsuarioLogado(), callBackListaCursos);
//	}
	
	
	public void showCurso(Curso object){
		
		
//		Label lblNomeCurso = new Label(txtConstants.cursoNome());				
//		Label lblDescricaoCurso = new Label(txtConstants.cursoDescricao());				
//		Label lblEmentaCurso = new Label(txtConstants.cursoEmenta());			
//		Label lblMediaNotaCurso = new Label(txtConstants.cursoMediaNota());
//		Label lblPorcentagemPresencaCurso = new Label(txtConstants.cursoPorcentagemPresenca());		
//		Label lblDataInicialCurso = new Label(txtConstants.cursoDataInicial());			
//		Label lblDataFinalCurso = new Label(txtConstants.cursoDataFinal());			
//		Label lblAlunosCurso = new Label(txtConstants.cursoAlunosDoCurso());		
//		
//		Label lblNomeCursoDB = new Label(object.getNome());
//		Label lblDescricaoCursoDB = new Label(object.getDescricao());	
//		Label lblEmentaCursoDB = new Label(object.getEmenta());		
//		Label lblMediaNotaCursoDB = new Label(object.getMediaNota());
//		Label lblPorcentagemPresencaCursoDB = new Label(object.getPorcentagemPresenca()+"%");		
//		
//		Label lblDataInicialCursoDB = new Label(MpUtilClient.convertDateToString(object.getDataInicial()));	
//		Label lblDataFinalCursoDB = new Label(MpUtilClient.convertDateToString(object.getDataFinal()));		
//		
//		lblNomeCurso.setStyleName("label_comum_bold_12px");
//		lblNomeCursoDB.setStyleName("design_label");
//		lblDescricaoCurso.setStyleName("label_comum_bold_12px");
//		lblDescricaoCursoDB.setStyleName("label_comum");		
//		lblEmentaCurso.setStyleName("label_comum_bold_12px");
//		lblEmentaCursoDB.setStyleName("label_comum");	
//		lblMediaNotaCurso.setStyleName("label_comum_bold_12px");
//		lblMediaNotaCursoDB.setStyleName("label_comum");
//		lblPorcentagemPresencaCurso.setStyleName("label_comum_bold_12px");
//		lblPorcentagemPresencaCursoDB.setStyleName("label_comum");		
//		lblDataInicialCurso.setStyleName("label_comum_bold_12px");
//		lblDataInicialCursoDB.setStyleName("label_comum");	
//		lblDataFinalCurso.setStyleName("label_comum_bold_12px");
//		lblDataFinalCursoDB.setStyleName("design_label");		
//		lblAlunosCurso.setStyleName("label_comum_bold_12px");
//		
//		
//		Grid gridData = new Grid(1,10);		
//		gridData.setCellPadding(0);
//		gridData.setCellSpacing(0);
//		gridData.setBorderWidth(0);		
//		int column=0;
//		gridData.setWidget(0, column++, lblDataInicialCurso);
//		gridData.setWidget(0, column++, new MpSpacePanel());
//		gridData.setWidget(0, column++, lblDataInicialCursoDB);
//		gridData.setWidget(0, column++, new MpSpacePanel());
//		gridData.setWidget(0, column++, new MpSpacePanel());
//		gridData.setWidget(0, column++, new MpSpacePanel());
//		gridData.setWidget(0, column++, lblDataFinalCurso);
//		gridData.setWidget(0, column++, new MpSpacePanel());
//		gridData.setWidget(0, column++, lblDataFinalCursoDB);
//		
//		
//		Grid gridMediaPresenca = new Grid(1,10);		
//		gridMediaPresenca.setCellPadding(0);
//		gridMediaPresenca.setCellSpacing(0);
//		gridMediaPresenca.setBorderWidth(0);		
//		column=0;
//		gridMediaPresenca.setWidget(0, column++, lblMediaNotaCurso);
//		gridMediaPresenca.setWidget(0, column++, new MpSpacePanel());
//		gridMediaPresenca.setWidget(0, column++, lblMediaNotaCursoDB);
//		gridMediaPresenca.setWidget(0, column++, new MpSpacePanel());
//		gridMediaPresenca.setWidget(0, column++, new MpSpacePanel());
//		gridMediaPresenca.setWidget(0, column++, new MpSpacePanel());
//		gridMediaPresenca.setWidget(0, column++, lblPorcentagemPresencaCurso);
//		gridMediaPresenca.setWidget(0, column++, new MpSpacePanel());
//		gridMediaPresenca.setWidget(0, column++, lblPorcentagemPresencaCursoDB);
//		
//		
//
//		
//    	FlexTable flexTableConteudo = new FlexTable();
//		flexTableConteudo.setCellPadding(2);
//		flexTableConteudo.setCellSpacing(2);	
//		flexTableConteudo.setBorderWidth(0);
//		int row=0;
//		flexTableConteudo.setWidget(row++, 0, lblDescricaoCurso);
//		flexTableConteudo.setWidget(row++, 0, lblDescricaoCursoDB);
//		flexTableConteudo.setWidget(row++, 0, new MpSpacePanel());
//		flexTableConteudo.setWidget(row++, 0, lblEmentaCurso);
//		flexTableConteudo.setWidget(row++, 0, lblEmentaCursoDB);
//		flexTableConteudo.setWidget(row++, 0, new MpSpacePanel());
//		flexTableConteudo.setWidget(row++, 0, gridMediaPresenca);
//		flexTableConteudo.setWidget(row++, 0, new MpSpacePanel());		
//		
//		flexTableConteudo.setWidget(row++, 0, gridData);
//		
//		flexTableConteudo.setWidget(row++, 0, new InlineHTML("&nbsp;"));
//		flexTableConteudo.setWidget(row++, 0, lblAlunosCurso);
//		
//		
//		CellTable<Usuario> cellTable = new CellTable<Usuario>(10,GWT.<CellTableStyle> create(CellTableStyle.class));
//
//		cellTable.setWidth(Integer.toString(intWidthTable-250)+ "px");
//        cellTable.setPageSize(10);
//	    
//	    MpSimplePager mpPager = new MpSimplePager();
//		mpPager.setDisplay(cellTable);
//		mpPager.setPageSize(10);
//		
//		
//		MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
//		
//		if (txtSearchCurso == null) {
//			txtSearchCurso = new TextBox();
//			txtSearchCurso.setStyleName("design_text_boxes");
//		}
//		
//		txtSearchCurso.addKeyDownHandler(new EnterKeyPressHandler());
//		btnFiltrar.addClickHandler(new ClickHandlerFiltrar());
//		
//		FlexTable flexTableFiltrar = new FlexTable();	
//		flexTableFiltrar.setBorderWidth(2);
//		flexTableFiltrar.setCellSpacing(3);
//		flexTableFiltrar.setCellPadding(3);
//		flexTableFiltrar.setBorderWidth(0);		
//		flexTableFiltrar.setWidget(0, 0, mpPager);
//		flexTableFiltrar.setWidget(0, 1, new MpSpacePanel());
//		flexTableFiltrar.setWidget(0, 2, txtSearchCurso);
//		flexTableFiltrar.setWidget(0, 3, btnFiltrar);	
//		
//		Column<Usuario, String> columnPrimeiroNome = new Column<Usuario, String>(new TextCell()) {
//			@Override
//			public String getValue(Usuario object) {
//				return object.getPrimeiroNome();
//			}
//		};		
//		Column<Usuario, String> columnSobreNome = new Column<Usuario, String>(new TextCell()) {
//			@Override
//			public String getValue(Usuario object) {
//				return object.getSobreNome();
//			}
//		};	
//		Column<Usuario, String> columnCPF = new Column<Usuario, String>(new TextCell()) {
//			@Override
//			public String getValue(Usuario object) {
//				return object.getCpf();
//			}
//		};		
//		Column<Usuario, String> columnEmail = new Column<Usuario, String>(new TextCell()) {
//			@Override
//			public String getValue(Usuario object) {
//				return object.getEmail();
//			}
//		};		
//		Column<Usuario, String> columnDataNascimento = new Column<Usuario, String>(new TextCell()) {
//			@Override
//			public String getValue(Usuario object) {
//				return MpUtilClient.convertDateToString(object.getDataNascimento());
//			}
//		};	
//		Column<Usuario, String> columnTelefoneCelular = new Column<Usuario, String>(new TextCell()) {
//			@Override
//			public String getValue(Usuario object) {
//				return object.getTelefoneCelular();
//			}
//		};		
//		Column<Usuario, String> columnTelefoneResidencial = new Column<Usuario, String>(
//				new EditTextCell()) {
//			@Override
//			public String getValue(Usuario object) {
//				return object.getTelefoneResidencial();
//			}
//		};	
//		
//		cellTable.addColumn(columnPrimeiroNome, txtConstants.usuarioPrimeiroNome());
//		cellTable.addColumn(columnSobreNome, txtConstants.usuarioSobreNome());
//		cellTable.addColumn(columnCPF, txtConstants.usuarioCPF());
//		cellTable.addColumn(columnEmail, txtConstants.usuarioEmail());
//		cellTable.addColumn(columnDataNascimento, txtConstants.usuarioDataNascimento());
//		cellTable.addColumn(columnTelefoneCelular, txtConstants.usuarioTelCelular());
//		cellTable.addColumn(columnTelefoneResidencial, txtConstants.usuarioTelResidencial());
//		
//		
//		cellTable.getColumn(cellTable.getColumnIndex(columnPrimeiroNome)).setCellStyleNames("hand-over-default");
//		cellTable.getColumn(cellTable.getColumnIndex(columnSobreNome)).setCellStyleNames("hand-over-default");
//		cellTable.getColumn(cellTable.getColumnIndex(columnCPF)).setCellStyleNames("hand-over-default");
//		cellTable.getColumn(cellTable.getColumnIndex(columnEmail)).setCellStyleNames("hand-over-default");
//		cellTable.getColumn(cellTable.getColumnIndex(columnDataNascimento)).setCellStyleNames("hand-over-default");
//		cellTable.getColumn(cellTable.getColumnIndex(columnTelefoneCelular)).setCellStyleNames("hand-over-default");
//		cellTable.getColumn(cellTable.getColumnIndex(columnTelefoneResidencial)).setCellStyleNames("hand-over-default");		
//		
//		
//		
//		dataProviderCurso.addDataDisplay(cellTable);
//		
//		dataProviderCurso.getList().clear();
//		
//		for(int i=0;i<object.getListAlunos().size();i++){
//			dataProviderCurso.getList().add(object.getListAlunos().get(i));
//			arrayListBackupCurso.add(object.getListAlunos().get(i));
//			
//		}
//		
//		Grid gridAlunos = new Grid(2,1);
//		gridAlunos.setCellPadding(0);
//		gridAlunos.setCellSpacing(0);
//		
//		gridAlunos.setWidget(0, 0, mpPager);
//		gridAlunos.setWidget(1, 0, cellTable);
//		
//		
//		flexTableConteudo.setWidget(row, 0, gridAlunos);
//		flexTableConteudo.getFlexCellFormatter().setColSpan(row++, 0, 4);
//		
			
//		
//		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(intWidthTable-200)+"px",Integer.toString(intHeightTable-30)+"px");
//		scrollPanel.setAlwaysShowScrollBars(false);				
//		scrollPanel.add(flexTableConteudo);
		
		panelDetalhes.clear();
		
		VerticalPanel vPanelTitle = vPanelTitulo(txtConstants.curso(), object.getNome(), "images/folder_library-24.png");
		
		panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		
    	FlexTable flexTable = new FlexTable();
    	flexTable.setCellPadding(2);
    	flexTable.setCellSpacing(2);
    	flexTable.setWidget(0, 0, vPanelTitle);		
//		flexTable.setWidget(1, 0, scrollPanel);
    	flexTable.setWidget(1, 0, new MpScrollPanelCurso(object));
		
		panelDetalhes.add(flexTable);		

		
	}
	
	public void showPeriodo(Periodo object){
		panelDetalhes.clear();

		VerticalPanel vPanelTitle = vPanelTitulo(txtConstants.periodo(), object.getNomePeriodo(), "images/my_projects_folder-24.png");
		
//		Label lblNomePeriodo = new Label(txtConstants.periodoNome());	
//		Label lblNomePeriodoDB = new Label(object.getNomePeriodo());
//		
//		Label lblDescricaoPeriodo = new Label(txtConstants.periodoDescricao());	
//		Label lblDescricaoPeriodoDB = new Label(object.getDescricao());	
//		
//		Label lblObjetivoPeriodo = new Label(txtConstants.periodoObjetivo());	
//		Label lblObjetivoPeriodoDB = new Label(object.getObjetivo());			
//		
//		Label lblDataInicialPeriodo = new Label(txtConstants.periodoDataInicial());	
//		Label lblDataInicialPeriodoDB = new Label(MpUtilClient.convertDateToString(object.getDataInicial()));				
//		
//		Label lblDataFinalPeriodo = new Label(txtConstants.periodoDataFinal());	
//		Label lblDataFinalPeriodoDB = new Label(MpUtilClient.convertDateToString(object.getDataFinal()));			
//		
//		
//		lblNomePeriodo.setStyleName("label_comum_bold_12px");
//		lblNomePeriodoDB.setStyleName("label_comum");
//		lblDescricaoPeriodo.setStyleName("label_comum_bold_12px");
//		lblDescricaoPeriodoDB.setStyleName("label_comum");		
//		lblObjetivoPeriodo.setStyleName("label_comum_bold_12px");
//		lblObjetivoPeriodoDB.setStyleName("label_comum");	
//		lblDataInicialPeriodo.setStyleName("label_comum_bold_12px");
//		lblDataInicialPeriodoDB.setStyleName("label_comum");	
//		lblDataFinalPeriodo.setStyleName("label_comum_bold_12px");
//		lblDataFinalPeriodoDB.setStyleName("label_comum");		
//		
//		
//		Grid gridData = new Grid(1,10);
//		
//		gridData.setCellPadding(0);
//		gridData.setCellSpacing(0);
//		gridData.setBorderWidth(0);
//		
//		int column=0;
//		gridData.setWidget(0, column++, lblDataInicialPeriodo);
//		gridData.setWidget(0, column++, new MpSpacePanel());
//		gridData.setWidget(0, column++, lblDataInicialPeriodoDB);
//		gridData.setWidget(0, column++, new MpSpacePanel());
//		gridData.setWidget(0, column++, new MpSpacePanel());
//		gridData.setWidget(0, column++, new MpSpacePanel());
//		gridData.setWidget(0, column++, lblDataFinalPeriodo);
//		gridData.setWidget(0, column++, new MpSpacePanel());
//		gridData.setWidget(0, column++, lblDataFinalPeriodoDB);		
//		
//		FlexTable flexTableConteudo = new FlexTable();
//		flexTableConteudo.setCellPadding(2);
//		flexTableConteudo.setCellSpacing(2);	
////		flexTableConteudo.getFlexCellFormatter().setColSpan(0, 0, 2);
//		flexTableConteudo.setBorderWidth(0);
////		flexTableConteudo.getColumnFormatter().setWidth(0, "150px");		
//		
//		int row=0;
////		flexTableConteudo.setWidget(row++, 0, vPanelTitle);
////		flexTable.setWidget(row, 0, lblNomePeriodo);flexTable.setWidget(row++, 1, lblNomePeriodoDB);
//		flexTableConteudo.setWidget(row++, 0, lblDescricaoPeriodo);
//		flexTableConteudo.setWidget(row++, 0, lblDescricaoPeriodoDB);
//		flexTableConteudo.setWidget(row++, 0, new MpSpacePanel());
//		flexTableConteudo.setWidget(row++, 0, lblObjetivoPeriodo);
//		flexTableConteudo.setWidget(row++, 0, lblObjetivoPeriodoDB);
//		flexTableConteudo.setWidget(row++, 0, new MpSpacePanel());
////		flexTableConteudo.setWidget(row++, 0, lblDataInicialPeriodo);flexTableConteudo.setWidget(row++, 0, lblDataInicialPeriodoDB);
////		flexTableConteudo.setWidget(row++, 0, lblDataFinalPeriodo);flexTableConteudo.setWidget(row++, 0, lblDataFinalPeriodoDB);
//		flexTableConteudo.setWidget(row++, 0, gridData);
//		
//		
//		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(intWidthTable-200)+"px",Integer.toString(intHeightTable-30)+"px");
//		scrollPanel.setAlwaysShowScrollBars(false);				
//		scrollPanel.add(flexTableConteudo);	
		
		panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		
    	FlexTable flexTable = new FlexTable();
    	flexTable.setCellPadding(2);
    	flexTable.setCellSpacing(2);
    	flexTable.setWidget(0, 0, vPanelTitle);		
//		flexTable.setWidget(1, 0, scrollPanel);		
    	flexTable.setWidget(1, 0, new MpScrollPanelPeriodo(object));
		
//		panelDetalhes.add(flexTableConteudo);
		panelDetalhes.add(flexTable);
	}
	
	public void showDisciplina(Disciplina object){

		panelDetalhes.clear();

		VerticalPanel vPanelTitle = vPanelTitulo(txtConstants.disciplina(), object.getNome(), "images/books-24.png");
		
//		Label lblNome = new Label(txtConstants.disciplinaNome());	
//		Label lblNomeDB = new Label(object.getNome());
//		
//		Label lblCargaHorario = new Label(txtConstants.disciplinaCarga());	
//		Label lblCargaHorarioDB = new Label(Integer.toString(object.getCargaHoraria()));				
//		
//		Label lblDescricao = new Label(txtConstants.disciplinaDescricao());	
//		Label lblDescricaoDB = new Label(object.getDescricao());	
//		
//		Label lblObjetivo = new Label(txtConstants.disciplinaObjetivo());	
//		Label lblObjetivoDB = new Label(object.getObjetivo());			
//		
//		Label lblProfessor = new Label(txtConstants.professor());
//		String strProfessorNome = object.getProfessor()==null? "" : object.getProfessor().getPrimeiroNome();
//		String strProfessorSobreNome = object.getProfessor()==null? "" : object.getProfessor().getSobreNome();
//		Label lblProfessorDB = new Label(strProfessorNome + " " + strProfessorSobreNome);				
//		
//		lblNome.setStyleName("label_comum_bold_12px");
//		lblNomeDB.setStyleName("label_comum");
//		lblCargaHorario.setStyleName("label_comum_bold_12px");
//		lblCargaHorarioDB.setStyleName("label_comum");	
//		lblDescricao.setStyleName("label_comum_bold_12px");
//		lblDescricaoDB.setStyleName("label_comum");		
//		lblObjetivo.setStyleName("label_comum_bold_12px");
//		lblObjetivoDB.setStyleName("label_comum");	
//		lblProfessor.setStyleName("label_comum_bold_12px");
//		lblProfessorDB.setStyleName("label_comum");			
//		
//		
//		Grid gridCargaHorario = new Grid(1,10);		
//		gridCargaHorario.setCellPadding(0);
//		gridCargaHorario.setCellSpacing(0);
//		gridCargaHorario.setBorderWidth(0);		
//		int column=0;
//		gridCargaHorario.setWidget(0, column++, lblCargaHorario);
//		gridCargaHorario.setWidget(0, column++, new MpSpacePanel());
//		gridCargaHorario.setWidget(0, column++, lblCargaHorarioDB);
//		
//	
//		FlexTable flexTableConteudo = new FlexTable();
//		flexTableConteudo.setCellPadding(2);
//		flexTableConteudo.setCellSpacing(2);	
////		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
////		flexTableConteudo.getColumnFormatter().setWidth(0, "150px");		
//		
//		int row=0;
////		flexTableConteudo.setWidget(row++, 0, vPanelTitle);
////		flexTable.setWidget(row, 0, lblNome);flexTable.setWidget(row++, 1, lblNomeDB);
////		flexTableConteudo.setWidget(row++, 0, lblCargaHorario);
////		flexTableConteudo.setWidget(row++, 0, lblCargaHorarioDB);	
//		flexTableConteudo.setWidget(row++, 0, gridCargaHorario);	
//		flexTableConteudo.setWidget(row++, 0, new MpSpacePanel());
//		flexTableConteudo.setWidget(row++, 0, lblDescricao);
//		flexTableConteudo.setWidget(row++, 0, lblDescricaoDB);
//		flexTableConteudo.setWidget(row++, 0, new MpSpacePanel());
//		flexTableConteudo.setWidget(row++, 0, lblObjetivo);
//		flexTableConteudo.setWidget(row++, 0, lblObjetivoDB);
//		flexTableConteudo.setWidget(row++, 0, new MpSpacePanel());
//		flexTableConteudo.setWidget(row++, 0, lblProfessor);
//		flexTableConteudo.setWidget(row++, 0, lblProfessorDB);
//
//
//		
//		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(intWidthTable-200)+"px",Integer.toString(intHeightTable-30)+"px");
//		scrollPanel.setAlwaysShowScrollBars(false);				
//		scrollPanel.add(flexTableConteudo);	
		
		panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		
		FlexTable flexTable = new FlexTable();
    	flexTable.setCellPadding(2);
    	flexTable.setCellSpacing(2);
    	flexTable.setWidget(0, 0, vPanelTitle);		
//		flexTable.setWidget(1, 0, scrollPanel);	
    	flexTable.setWidget(1, 0, new MpScrollPanelDisciplina(object));
		
//		panelDetalhes.add(flexTableConteudo);
		panelDetalhes.add(flexTable);
	}	
	
	public void showConteudoProgramatico(ConteudoProgramatico object) {
		
		panelDetalhes.clear();
		
		VerticalPanel vPanelTitle = vPanelTitulo(txtConstants.conteudoProgramatico(), object.getNome(), "images/textdocument-24.png");

//		Label lblNome = new Label(txtConstants.conteudoProgramaticoNome());
//		Label lblNomeDB = new Label(object.getNome());
//		Label lblNumeracao = new Label(txtConstants.conteudoProgramaticoNumeracao());
//		Label lblNumeracaoDB = new Label(object.getNumeracao());
//		Label lblDescricao = new Label(txtConstants.conteudoProgramaticoDescricao());
//		Label lblDescricaoDB = new Label(object.getDescricao());
//		Label lblObjetivo = new Label(txtConstants.conteudoProgramaticoObjetivo());
//		Label lblObjetivoDB = new Label(object.getObjetivo());
//		Label lblAvaliacoes = new Label(txtConstants.avaliacao());
//
//		lblNome.setStyleName("label_comum_bold_12px");
//		lblNomeDB.setStyleName("label_comum");
//		lblNumeracao.setStyleName("label_comum_bold_12px");
//		lblNumeracaoDB.setStyleName("label_comum");
//		lblDescricao.setStyleName("label_comum_bold_12px");
//		lblDescricaoDB.setStyleName("label_comum");
//		lblObjetivo.setStyleName("label_comum_bold_12px");
//		lblObjetivoDB.setStyleName("label_comum");
//		lblAvaliacoes.setStyleName("label_comum_bold_12px");
//		
//		
//		Grid gridNumeracao = new Grid(1,10);		
//		gridNumeracao.setCellPadding(0);
//		gridNumeracao.setCellSpacing(0);
//		gridNumeracao.setBorderWidth(0);		
//		int column=0;
//		gridNumeracao.setWidget(0, column++, lblNumeracao);
//		gridNumeracao.setWidget(0, column++, new MpSpacePanel());
//		gridNumeracao.setWidget(0, column++, lblNumeracaoDB);		
//		
//		FlexTable flexTableConteudo = new FlexTable();
//		flexTableConteudo.setCellPadding(2);
//		flexTableConteudo.setCellSpacing(2);
////		flexTableConteudo.getFlexCellFormatter().setColSpan(0, 0, 2);
////		flexTableConteudo.getColumnFormatter().setWidth(0, "150px");
//		
//
//		int row = 0;
////		flexTableConteudo.setWidget(row++, 0, vPanelTitle);
//		// flexTable.setWidget(row, 0, lblNome);flexTable.setWidget(row++, 1,lblNomeDB);
////		flexTableConteudo.setWidget(row++, 0, lblNumeracao);
////		flexTableConteudo.setWidget(row++, 0, lblNumeracaoDB);
//		flexTableConteudo.setWidget(row++, 0, gridNumeracao);
//		flexTableConteudo.setWidget(row++, 0, new MpSpacePanel());
//		flexTableConteudo.setWidget(row++, 0, lblDescricao);
//		flexTableConteudo.setWidget(row++, 0, lblDescricaoDB);
//		flexTableConteudo.setWidget(row++, 0, new MpSpacePanel());
//		flexTableConteudo.setWidget(row++, 0, lblObjetivo);
//		flexTableConteudo.setWidget(row++, 0, lblObjetivoDB);
//		flexTableConteudo.setWidget(row++, 0, new MpSpacePanel());
//		flexTableConteudo.setWidget(row++, 0, new InlineHTML("&nbsp"));
//		flexTableConteudo.setWidget(row++, 0, lblAvaliacoes);
//
//		Label lblEmpty = new Label(txtConstants.avaliacaoNenhumaCurso());
//
//		CellTable<Avaliacao> cellTable = new CellTable<Avaliacao>(5,GWT.<CellTableStyle> create(CellTableStyle.class));
//		cellTable.setEmptyTableWidget(lblEmpty);
//		cellTable.setWidth(Integer.toString(intWidthTable - 250) + "px");
//		cellTable.setPageSize(10);
//
//		MpSimplePager mpPager = new MpSimplePager();
//		mpPager.setDisplay(cellTable);
//		mpPager.setPageSize(10);
//
//		ListDataProvider<Avaliacao> dataProvider = new ListDataProvider<Avaliacao>();
//
//		Column<Avaliacao, String> assuntoColumn = new Column<Avaliacao, String>(
//				new TextCell()) {
//			@Override
//			public String getValue(Avaliacao object) {
//				return object.getAssunto();
//			}
//		};
//		Column<Avaliacao, String> descricaoColumn = new Column<Avaliacao, String>(
//				new TextCell()) {
//			@Override
//			public String getValue(Avaliacao object) {
//				return object.getDescricao();
//			}
//		};
//		Column<Avaliacao, String> columnTipoAvaliacao = new Column<Avaliacao, String>(
//				new TextCell()) {
//			@Override
//			public String getValue(Avaliacao object) {
//				return object.getTipoAvaliacao().getNomeTipoAvaliacao();
//			}
//		};
//		Column<Avaliacao, String> dataColumn = new Column<Avaliacao, String>(new TextCell()) {
//			@Override
//			public String getValue(Avaliacao object) {
//				//return object.getData();
//				return MpUtilClient.convertDateToString(object.getData());
//			}
//		};
//		Column<Avaliacao, String> horaColumn = new Column<Avaliacao, String>(
//				new TextCell()) {
//			@Override
//			public String getValue(Avaliacao object) {
//				return object.getHora();
//			}
//		};
//
//		cellTable.addColumn(assuntoColumn, txtConstants.avaliacaoAssunto());
//		cellTable.addColumn(descricaoColumn, txtConstants.avaliacaoDescricao());
//		cellTable.addColumn(columnTipoAvaliacao, txtConstants.avaliacaoTipo());
//		cellTable.addColumn(dataColumn, txtConstants.avaliacaoData());
//		cellTable.addColumn(horaColumn, txtConstants.avaliacaoHora());
//
//		cellTable.getColumn(cellTable.getColumnIndex(assuntoColumn)).setCellStyleNames("hand-over-default");
//		cellTable.getColumn(cellTable.getColumnIndex(descricaoColumn)).setCellStyleNames("hand-over-default");
//		cellTable.getColumn(cellTable.getColumnIndex(columnTipoAvaliacao)).setCellStyleNames("hand-over-default");
//		cellTable.getColumn(cellTable.getColumnIndex(dataColumn)).setCellStyleNames("hand-over-default");
//		cellTable.getColumn(cellTable.getColumnIndex(horaColumn)).setCellStyleNames("hand-over-default");
//
//		dataProvider.addDataDisplay(cellTable);
//		
//
//		dataProvider.getList().clear();
//
//		for (int i = 0; i < object.getListAvaliacao().size(); i++) {
//			dataProvider.getList().add(object.getListAvaliacao().get(i));
//		}
//
//		Grid gridAvaliacoes = new Grid(2, 1);
//		gridAvaliacoes.setCellPadding(0);
//		gridAvaliacoes.setCellSpacing(0);
//		gridAvaliacoes.setWidget(0, 0, cellTable);
//		gridAvaliacoes.setWidget(1, 0, mpPager);
//		
//
//		flexTableConteudo.setWidget(row, 0, gridAvaliacoes);
//		flexTableConteudo.getFlexCellFormatter().setColSpan(row++, 0, 2);
//
//		panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
//		
//		
//		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(intWidthTable-200)+"px",Integer.toString(intHeightTable-30)+"px");
//		scrollPanel.setAlwaysShowScrollBars(false);				
//		scrollPanel.add(flexTableConteudo);	
		
		panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		
    	FlexTable flexTable = new FlexTable();
    	flexTable.setCellPadding(2);
    	flexTable.setCellSpacing(2);
    	flexTable.setWidget(0, 0, vPanelTitle);		
		flexTable.setWidget(1, 0, new MpScrollPanelConteudoProgramatico(object));		

//		panelDetalhes.add(flexTableConteudo);
		panelDetalhes.add(flexTable);
		
	}
	
	public void showTopico(Topico object){
		
		panelDetalhes.clear();
		
		VerticalPanel vPanelTitle = vPanelTitulo(txtConstants.topico(),object.getNome(), "images/type_list-24.png");
		
//		Label lblNome = new Label(txtConstants.topicoNome());	
//		Label lblNomeDB = new Label(object.getNome());
//		
//		Label lblNumeracao = new Label(txtConstants.topicoNumeracao());	
//		Label lblNumeracaoDB = new Label(object.getNumeracao());				
//		
//		Label lblDescricao = new Label(txtConstants.topicoDescricao());	
//		Label lblDescricaoDB = new Label(object.getDescricao());	
//		
//		Label lblObjetivo = new Label(txtConstants.topicoObjetivo());	
//		Label lblObjetivoDB = new Label(object.getObjetivo());
//		
//		lblNome.setStyleName("label_comum_bold_12px");lblNomeDB.setStyleName("label_comum");
//		lblNumeracao.setStyleName("label_comum_bold_12px");lblNumeracaoDB.setStyleName("label_comum");	
//		lblDescricao.setStyleName("label_comum_bold_12px");lblDescricaoDB.setStyleName("label_comum");		
//		lblObjetivo.setStyleName("label_comum_bold_12px");lblObjetivoDB.setStyleName("label_comum");	
//		
//		Grid gridNumeracao = new Grid(1,10);		
//		gridNumeracao.setCellPadding(0);
//		gridNumeracao.setCellSpacing(0);
//		gridNumeracao.setBorderWidth(0);		
//		int column=0;
//		gridNumeracao.setWidget(0, column++, lblNumeracao);
//		gridNumeracao.setWidget(0, column++, new MpSpacePanel());
//		gridNumeracao.setWidget(0, column++, lblNumeracaoDB);			
//
//		
//		FlexTable flexTableConteudo = new FlexTable();
//		flexTableConteudo.setCellPadding(2);
//		flexTableConteudo.setCellSpacing(2);	
////		flexTableConteudo.getFlexCellFormatter().setColSpan(0, 0, 2);
////		flexTableConteudo.getColumnFormatter().setWidth(0, "150px");		
//		
//		int row=0;
////		flexTableConteudo.setWidget(row++, 0, vPanelTitle);
////		flexTable.setWidget(row, 0, lblNome);flexTable.setWidget(row++, 1, lblNomeDB);
////		flexTableConteudo.setWidget(row++, 0, lblNumeracao);
////		flexTableConteudo.setWidget(row++, 0, lblNumeracaoDB);	
//		flexTableConteudo.setWidget(row++, 0, gridNumeracao);
//		flexTableConteudo.setWidget(row++, 0, new MpSpacePanel());
//		flexTableConteudo.setWidget(row++, 0, lblDescricao);
//		flexTableConteudo.setWidget(row++, 0, lblDescricaoDB);
//		flexTableConteudo.setWidget(row++, 0, new MpSpacePanel());
//		flexTableConteudo.setWidget(row++, 0, lblObjetivo);
//		flexTableConteudo.setWidget(row++, 0, lblObjetivoDB);
//		flexTableConteudo.setWidget(row++, 0, new MpSpacePanel());
//
//		panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
//		
//		
//		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(intWidthTable-200)+"px",Integer.toString(intHeightTable-30)+"px");
//		scrollPanel.setAlwaysShowScrollBars(false);				
//		scrollPanel.add(flexTableConteudo);	
		
		panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		
    	FlexTable flexTable = new FlexTable();
    	flexTable.setCellPadding(2);
    	flexTable.setCellSpacing(2);
    	flexTable.setWidget(0, 0, vPanelTitle);		
		flexTable.setWidget(1, 0, new MpScrollPanelTopico(object));			

		
//		panelDetalhes.add(flexTableConteudo);
		panelDetalhes.add(flexTable);
	}	
	
	
	public VerticalPanel vPanelTitulo(String strTitle, String strNome, String strImgAddress){
		Label lblTitulo = new Label(strTitle);
		lblTitulo.setStyleName("label_comum_bold_12px");
		Label lblNome = new Label(strNome);
		lblNome.setStyleName("label_comum");
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


//	/*******************************************************************************************************/	
//	/*******************************************************************************************************/
//	/***************************************BEGIN Filterting CellTable***************************************/
//	
//	private class EnterKeyPressHandler implements KeyDownHandler {
//		 public void onKeyDown(KeyDownEvent event) {
//			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//				filtrarCellTable(txtSearchCurso.getText());
//			}
//		}
//	}
//
//	
//	private class ClickHandlerFiltrar implements ClickHandler {
//		public void onClick(ClickEvent event) {
//			filtrarCellTable(txtSearchCurso.getText());
//		}
//	}		
//		
//		public void filtrarCellTable(String strFiltro) {
//			
//			removeCellTableFilter();
//
//			strFiltro = strFiltro.toUpperCase();
//
//			if (!strFiltro.isEmpty()) {
//
//				int i = 0;
//				while (i < dataProviderCurso.getList().size()) {
//
//					String strPrimeiroNome = dataProviderCurso.getList().get(i).getPrimeiroNome();			
//					String strSobreNome = dataProviderCurso.getList().get(i).getSobreNome();
//					String strEmail = dataProviderCurso.getList().get(i).getEmail();
//					String strDataNascimento = MpUtilClient.convertDateToString(dataProviderCurso.getList().get(i).getDataNascimento(), "EEEE, MMMM dd, yyyy");
//					String strTelefoneCelular = dataProviderCurso.getList().get(i).getTelefoneCelular();
//					String strTelefoneResidencial = dataProviderCurso.getList().get(i).getTelefoneResidencial();
//
//					String strJuntaTexto = strPrimeiroNome.toUpperCase() + " " + strSobreNome.toUpperCase() + " " + strEmail.toUpperCase();
//					strJuntaTexto +=  " " + strDataNascimento.toUpperCase() + " " + strTelefoneCelular.toUpperCase() + " " + strTelefoneResidencial.toUpperCase();
//
//					if (!strJuntaTexto.contains(strFiltro)) {
//						dataProviderCurso.getList().remove(i);
//						i = 0;
//						continue;
//					}
//
//					i++;
//				}
//
//			}
//
//		}
//		
//		public void removeCellTableFilter(){
//			
//			dataProviderCurso.getList().clear();
//
//			for (int i = 0; i < arrayListBackupCurso.size(); i++) {
//				dataProviderCurso.getList().add(arrayListBackupCurso.get(i));
//			}
//			cellTable.setPageStart(0);
//		}
//	/***************************************BEGIN Filterting CellTable***************************************/	
	
	

}


