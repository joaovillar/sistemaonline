package com.jornada.client.ambiente.pais.presenca;

import java.util.ArrayList;
import java.util.Comparator;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.classes.listBoxes.ambiente.pais.MpListBoxAlunosPorCursoAmbientePais;
import com.jornada.client.classes.listBoxes.ambiente.pais.MpListBoxCursoAmbientePais;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServicePresenca;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.presenca.PresencaUsuarioDisciplinaAluno;
import com.jornada.shared.classes.utility.MpUtilClient;

public class VisualizarPresencaAlunoPais extends VerticalPanel {

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpPanelLoadingAluno = new MpPanelLoading("images/radar.gif");

	private MpListBoxCursoAmbientePais listBoxCurso;
	private MpListBoxAlunosPorCursoAmbientePais listBoxAlunosPorCurso;
	
	private TextBox txtSearch;
	
	private CellTable<PresencaUsuarioDisciplinaAluno> cellTable;
	private ListDataProvider<PresencaUsuarioDisciplinaAluno> dataProvider  = new ListDataProvider<PresencaUsuarioDisciplinaAluno>();
	ArrayList<PresencaUsuarioDisciplinaAluno> arrayListBackup = new ArrayList<PresencaUsuarioDisciplinaAluno>(); 
	private Column<PresencaUsuarioDisciplinaAluno, String> nomePeriodoColumn;
	private Column<PresencaUsuarioDisciplinaAluno, String> nomeDisciplinaColumn;
	private Column<PresencaUsuarioDisciplinaAluno, String> quantAulaColumn;
	private Column<PresencaUsuarioDisciplinaAluno, String> quantPresencaColumn;
	private Column<PresencaUsuarioDisciplinaAluno, String> quantFaltaColumn;
	private Column<PresencaUsuarioDisciplinaAluno, String> quantJustificativaColumn;
	private Column<PresencaUsuarioDisciplinaAluno, String> porcentagemPresencaColumn;
	private Column<PresencaUsuarioDisciplinaAluno, String> situacaoColumn;
	
	private int idUsuario;
		
	VerticalPanel vFormPanel = new VerticalPanel();
	ScrollPanel scrollPanel = new ScrollPanel();
	
	TextConstants txtConstants;
	
	private TelaInicialPresencaAlunoPais telaInicialPresencaAlunoPais;

	public VisualizarPresencaAlunoPais(final TelaInicialPresencaAlunoPais telaInicialPresencaAlunoPais) {
		
		scrollPanel.setAlwaysShowScrollBars(false);

//		scrollPanel.setSize(Integer.toString(TelaInicialDiarioPais.intWidthTable+30)+"px",Integer.toString(TelaInicialDiarioPais.intHeightTable-120)+"px");
		scrollPanel.setHeight(Integer.toString(TelaInicialPresencaAlunoPais.intHeightTable-120)+"px");
		scrollPanel.setWidth("100%");
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialPresencaAlunoPais=telaInicialPresencaAlunoPais;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpPanelLoadingAluno.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoadingAluno.show();
		mpPanelLoadingAluno.setVisible(false);

		FlexTable flexTableWithListBoxes = new FlexTable();
		flexTableWithListBoxes.setCellSpacing(3);
		flexTableWithListBoxes.setCellPadding(3);
		flexTableWithListBoxes.setBorderWidth(0);		
		FlexCellFormatter cellFormatter = flexTableWithListBoxes.getFlexCellFormatter();

		// Add a title to the form
//		cellFormatter.setColSpan(0, 0, 0);
		cellFormatter.setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_CENTER);
		
		
		Label lblCurso = new Label(txtConstants.curso());
		lblCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		


		lblCurso.setStyleName("design_label");

		// Add some standard form options
		int row = 1;
		flexTableWithListBoxes.setWidget(row, 0, lblCurso);

		listBoxCurso = new MpListBoxCursoAmbientePais(telaInicialPresencaAlunoPais.getMainView().getUsuarioLogado());	
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());
		
//		if(telaInicialDiarioPais.getMainView().getUsuarioLogado().getIdTipoUsuario()==TipoUsuario.PAIS){
//			flexTableWithListBoxes.setWidget(row, 0, lblCurso);	
//			flexTableWithListBoxes.setWidget(row, 1, listBoxCurso);
//			flexTableWithListBoxes.setWidget(row++, 2, mpPanelLoadingAluno);
//			idUsuario = telaInicialDiarioPais.getMainView().getUsuarioLogado().getIdUsuario();
//			
//		}else{
			Label lblNomeAluno = new Label(txtConstants.alunoNome());
			lblNomeAluno.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);	
			lblNomeAluno.setStyleName("design_label");
			
			listBoxAlunosPorCurso = new MpListBoxAlunosPorCursoAmbientePais();
			listBoxAlunosPorCurso.addChangeHandler(new MpAlunosPorCursoSelectionChangeHandler());

			flexTableWithListBoxes.setWidget(row, 0, lblCurso);	
			flexTableWithListBoxes.setWidget(row++, 1, listBoxCurso);
			flexTableWithListBoxes.setWidget(row, 0, lblNomeAluno);
			flexTableWithListBoxes.setWidget(row, 1, listBoxAlunosPorCurso);
			flexTableWithListBoxes.setWidget(row, 2, mpPanelLoadingAluno);
			
//		}

		
		vFormPanel.add(flexTableWithListBoxes);
		vFormPanel.setWidth("100%");

/***************************************Begin Callbacks***************************************/

		

/***************************************End Callbacks***************************************/
		
		initializeCellTable();
		
		this.setWidth("100%");
		super.add(vFormPanel);

	}
	
/*******************************************************************************************************/
/*******************************************************************************************************/	
/*******************************************************************************************************/	
/***************************************Begin Event Handlers***************************************************/
	
	
	private class MpCursoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			
			int indexCurso = listBoxCurso.getSelectedIndex();
			
			if (indexCurso == -1) {				
				cleanCellTable();
			}else{				
				int idCurso = Integer.parseInt(listBoxCurso.getValue(indexCurso));

//				if (telaInicialDiarioPais.getMainView().getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.PAIS) {
//					populateGrid();
//				} else {
					listBoxAlunosPorCurso.populateComboBox(telaInicialPresencaAlunoPais.getMainView().getUsuarioLogado(), idCurso);
//				}
			}		
		}  
	}	
	
	
	private class MpAlunosPorCursoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int indexUsuario = listBoxAlunosPorCurso.getSelectedIndex();
			if (indexUsuario == -1) {
				cleanCellTable();
			}else{
				idUsuario = Integer.parseInt(listBoxAlunosPorCurso.getValue(indexUsuario));
				populateGrid();				
			}

		}  
	}	

	
	private class EnterKeyUpHandler implements KeyUpHandler {
		 public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				filtrarCellTable(txtSearch.getText());
			}
		}
	}

	
	private class ClickHandlerFiltrar implements ClickHandler {
		public void onClick(ClickEvent event) {
			filtrarCellTable(txtSearch.getText());
		}
	}
	
/***************************************End Event Handlers***************************************/
/*******************************************************************************************************/			
/*******************************************************************************************************/
/*******************************************************************************************************/	
/*******************************************************************************************************/	
/***************************************Begin POPULATE DATA***************************************/	
	

	
	private void populateGrid() {

		int indexCurso = listBoxCurso.getSelectedIndex();
//		int idUsuario = this.telaInicialDiarioAluno.getMainView().getUsuarioLogado().getIdUsuario();
		if (indexCurso != -1) {

			mpPanelLoadingAluno.setVisible(true);
			int idCurso = Integer.parseInt(listBoxCurso.getValue(indexCurso));
			
			GWTServicePresenca.Util.getInstance().getPresencaAluno(idUsuario, idCurso, 
			new AsyncCallback<ArrayList<Periodo>>() 
			{
				public void onFailure(Throwable caught) {
					mpPanelLoadingAluno.setVisible(false);
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.topicoErroSalvar());
					mpDialogBoxWarning.showDialog();
				}

				@Override
				public void onSuccess(ArrayList<Periodo> list) {
					MpUtilClient.isRefreshRequired(list);
//					ArrayList<PresencaUsuarioDisciplinaAluno> listPresencaUsuarioDisciplinaAluno = convertePeriodoParaTabela(list);
					
					mpPanelLoadingAluno.setVisible(false);
					dataProvider.getList().clear();
					arrayListBackup.clear();
					cellTable.setRowCount(0);
//					for(int i=0;i<listPresencaUsuarioDisciplinaAluno.size();i++){
//						dataProvider.getList().add(listPresencaUsuarioDisciplinaAluno.get(i));
//						arrayListBackup.add(listPresencaUsuarioDisciplinaAluno.get(i));
//					}
					addCellTableData(dataProvider);
					cellTable.redraw();			
				}
			});
		}
	}	
	
//	private ArrayList<PresencaUsuarioDisciplinaAluno> convertePeriodoParaTabela(ArrayList<Periodo> arrayPeriodo){
//		
//		int intPorcentagemPresencaCurso = Integer.parseInt(listBoxCurso.getListCurso().get(listBoxCurso.getSelectedIndex()).getPorcentagemPresenca());
//		
//		ArrayList<PresencaUsuarioDisciplinaAluno> listTpa = new ArrayList<PresencaUsuarioDisciplinaAluno>();
//		
//		for(int cvPer=0;cvPer<arrayPeriodo.size();cvPer++){
//					
//			Periodo periodo = arrayPeriodo.get(cvPer);
//			
//			for(int cvDis=0;cvDis<periodo.getListDisciplinas().size();cvDis++){
//				PresencaUsuarioDisciplinaAluno tpa = new PresencaUsuarioDisciplinaAluno();
//				Disciplina disciplina = periodo.getListDisciplinas().get(cvDis);				
//				int quantidateAula = disciplina.getListAula().size();
//				int quantidatePresencas = disciplina.getQuantidadePresenca();
//				int quantidateFaltas = disciplina.getQuantidadeFalta();
//				int quantidateJustificadas = disciplina.getQuantidadeFaltaJustificada();
//				int quantidadePresencaSalaDeAula;
//				
//				if(quantidateAula==0 || (quantidatePresencas==0&&quantidateJustificadas==0)){
//					quantidadePresencaSalaDeAula=0;
//				}else{
//					Double doublePresenca = (((double)quantidatePresencas+(double)quantidateJustificadas)/(double)quantidateAula)*100;
//					quantidadePresencaSalaDeAula = 	doublePresenca.intValue();
//				}		
//				
//				if(quantidadePresencaSalaDeAula>=intPorcentagemPresencaCurso){
//					tpa.setSituacao(Presenca.APROVADO);
//				}else{
//					tpa.setSituacao(Presenca.REPROVADO);
//				}
//				
//				
//				tpa.setNomePeriodo(periodo.getNomePeriodo());
//				tpa.setNomeDisciplina(disciplina.getNome());
//				tpa.setQuantAulas(quantidateAula);
//				tpa.setQuantPresenca(quantidatePresencas);
//				tpa.setQuantFaltas(quantidateFaltas);
//				tpa.setQuantJustificadas(quantidateJustificadas);
//				tpa.setPresencaSalaAula(quantidadePresencaSalaDeAula);
//				
//				listTpa.add(tpa);
//				
//			}
//			
//			
//			
//		}
//		
//		return listTpa;
//	}
	
//	private int getNumeroTipoPresenca(ArrayList<Aula> listAula, int tipoPresenca){
//		int quantidadeTipoPresenca=0;
//		
//		for(int cvAula=0;cvAula<listAula.size();cvAula++){
//			Aula aula = listAula.get(cvAula);
//			
//			for(int cvPre=0;cvPre<aula.getArrayPresenca().size();cvPre++){
//				Presenca presenca = aula.getArrayPresenca().get(cvPre);
//				if(presenca.getIdTipoPresenca()==tipoPresenca){
//					quantidadeTipoPresenca++;
//				}
//			}			
//		}
//		return quantidadeTipoPresenca;		
//	}

	public void updateClientData(){
		listBoxCurso.populateComboBox(this.telaInicialPresencaAlunoPais.getMainView().getUsuarioLogado());
	}	
	
/***************************************END POPULATE DATA***********************************************/		
/*******************************************************************************************************/	
/*******************************************************************************************************/
/*******************************************************************************************************/	
/***************************************BEGIN CellTable Functions***************************************/
	
	
	public void initializeCellTable(){
		cellTable = new CellTable<PresencaUsuarioDisciplinaAluno>(10,GWT.<CellTableStyle> create(CellTableStyle.class));

//		cellTable.setWidth(Integer.toString(TelaInicialDiarioPais.intWidthTable)+ "px");		
		cellTable.setWidth("100%");
		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);
		
		dataProvider.addDataDisplay(cellTable);	
		
		// Add a selection model so we can select cells.
		final SelectionModel<PresencaUsuarioDisciplinaAluno> selectionModel = new MultiSelectionModel<PresencaUsuarioDisciplinaAluno>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<PresencaUsuarioDisciplinaAluno> createCheckboxManager());

		initTableColumns(selectionModel);		
		
		final MpSimplePager mpPager = new MpSimplePager();
		mpPager.setDisplay(cellTable);
//		mpPager.setPageSize(10);
		
		
		String strSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		SafeHtmlBuilder builder = new SafeHtmlBuilder();
		builder.appendHtmlConstant(strSpace + strSpace + txtConstants.periodo() + strSpace + strSpace);
//		SafeHtml safeHtml = builder.toSafeHtml();
	
		FlexTable flexTableFiltrarAluno = new FlexTable();	
		flexTableFiltrarAluno.setBorderWidth(2);
		flexTableFiltrarAluno.setCellSpacing(3);
		flexTableFiltrarAluno.setCellPadding(3);
		flexTableFiltrarAluno.setBorderWidth(0);
		
//		Label lblAluno = new Label(txtConstants.aluno());
		MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
		if (txtSearch == null) {
			txtSearch = new TextBox();
			txtSearch.setStyleName("design_text_boxes");
		}
			
		
		txtSearch.addKeyUpHandler(new EnterKeyUpHandler());
		btnFiltrar.addClickHandler(new ClickHandlerFiltrar());
		
		flexTableFiltrarAluno.setWidget(0, 0, mpPager);
		flexTableFiltrarAluno.setWidget(0, 1, new MpSpaceVerticalPanel());
//		flexTableFiltrarAluno.setWidget(0, 2, lblAluno);
		flexTableFiltrarAluno.setWidget(0, 3, txtSearch);
		flexTableFiltrarAluno.setWidget(0, 4, btnFiltrar);
//		flexTableFiltrarAluno.setWidget(0, 5, mpPanelLoadingAluno);
		
		
		MpSpaceVerticalPanel mpSpaceVerticalPanel = new MpSpaceVerticalPanel();
		
		VerticalPanel vPanelInScroll = new VerticalPanel();
		vPanelInScroll.setBorderWidth(0);
		vPanelInScroll.setWidth("100%");
		vPanelInScroll.setCellVerticalAlignment(cellTable, ALIGN_TOP);
		vPanelInScroll.add(flexTableFiltrarAluno);
		vPanelInScroll.add(cellTable);
		vPanelInScroll.add(mpSpaceVerticalPanel);
		scrollPanel.clear();
		scrollPanel.add(vPanelInScroll);
		
		vFormPanel.add(scrollPanel);

		
		mpPanelLoadingAluno.setVisible(false);
		
		filtrarCellTable(txtSearch.getText());
		
	}	
	

	
	protected void cleanCellTable() {
		if (cellTable != null) {
//			arrayColumns.clear();
//			arrayAula.clear();
//			arrayListBackup.clear();
//			dataProvider.getList().clear();
//			dataProvider=null;
			dataProvider.getList().clear();			
//			vFormGrid.clear();
			cellTable.setRowCount(0);
			cellTable.redraw();
		}
	}	
	
	private void addCellTableData(ListDataProvider<PresencaUsuarioDisciplinaAluno> dataProvider){
		
		 ListHandler<PresencaUsuarioDisciplinaAluno> sortHandler = new ListHandler<PresencaUsuarioDisciplinaAluno>(dataProvider.getList());
		 
		 cellTable.addColumnSortHandler(sortHandler);	

		 initSortHandler(sortHandler);
	
	}
		
	private void initTableColumns(
			final SelectionModel<PresencaUsuarioDisciplinaAluno> selectionModel) {

		nomePeriodoColumn = new Column<PresencaUsuarioDisciplinaAluno, String>(new TextCell()) {
			@Override
			public String getValue(PresencaUsuarioDisciplinaAluno object) {
				return object.getNomePeriodo();
			}

		};

		nomeDisciplinaColumn = new Column<PresencaUsuarioDisciplinaAluno, String>(new TextCell()) {
			@Override
			public String getValue(PresencaUsuarioDisciplinaAluno object) {
				return object.getNomeDisciplina();
			}

		};

//		quantAulaColumn = new Column<PresencaUsuarioDisciplinaAluno, String>(new TextCell()) {
//			@Override
//			public String getValue(PresencaUsuarioDisciplinaAluno object) {
//				return Integer.toString(object.getQuantAulas());
//			}
//		};

//		quantPresencaColumn = new Column<PresencaUsuarioDisciplinaAluno, String>(new TextCell()) {
//			@Override
//			public String getValue(PresencaUsuarioDisciplinaAluno object) {
//				return Integer.toString(object.getQuantPresenca());
//			}
//		};

//		quantFaltaColumn = new Column<PresencaUsuarioDisciplinaAluno, String>(new TextCell()) {
//			@Override
//			public String getValue(PresencaUsuarioDisciplinaAluno object) {
//				return Integer.toString(object.getQuantFaltas());
//			}
//		};

//		quantJustificativaColumn = new Column<PresencaUsuarioDisciplinaAluno, String>(new TextCell()) {
//			@Override
//			public String getValue(PresencaUsuarioDisciplinaAluno object) {
//				return Integer.toString(object.getQuantJustificadas());
//			}
//		};
//		
//		porcentagemPresencaColumn = new Column<PresencaUsuarioDisciplinaAluno, String>(new TextCell()) {
//			@Override
//			public String getValue(PresencaUsuarioDisciplinaAluno object) {
//			
//				return Integer.toString(object.getPresencaSalaAula())+"%";
//			}
//		};		
		
		situacaoColumn = new Column<PresencaUsuarioDisciplinaAluno, String>(new TextCell()) {
			@Override
			public String getValue(PresencaUsuarioDisciplinaAluno object) {
			
				return object.getSituacao();
			}
		};		

		cellTable.addColumn(nomePeriodoColumn, txtConstants.periodoNome());
		cellTable.addColumn(nomeDisciplinaColumn, txtConstants.disciplina());
		cellTable.addColumn(quantAulaColumn, txtConstants.presencaQuantidadeAulas());
		cellTable.addColumn(quantPresencaColumn, txtConstants.presencaQuantidadePresenca());
		cellTable.addColumn(quantFaltaColumn, txtConstants.presencaQuantidadeFaltas());
		cellTable.addColumn(quantJustificativaColumn,txtConstants.presencaQuantidadeJustificativas());
		cellTable.addColumn(porcentagemPresencaColumn,txtConstants.presencaSalaDeAula());
		cellTable.addColumn(situacaoColumn,txtConstants.presencaSituacao());

		// Make the name column sortable.
		// nomePeriodoColumn.setSortable(true);

		// cellTable.getColumn(cellTable.getColumnIndex(nomePeriodoColumn)).setCellStyleNames("edit-cell");
		// cellTable.getColumn(cellTable.getColumnIndex(nomeDisciplinaColumn)).setCellStyleNames("edit-cell");
		// cellTable.getColumn(cellTable.getColumnIndex(quantAulaColumn)).setCellStyleNames("edit-cell");
		// cellTable.getColumn(cellTable.getColumnIndex(quantPresencaColumn)).setCellStyleNames("hand-over");

	}

	public void initSortHandler(ListHandler<PresencaUsuarioDisciplinaAluno> sortHandler){
		nomePeriodoColumn.setSortable(true);
	    sortHandler.setComparator(nomePeriodoColumn, new Comparator<PresencaUsuarioDisciplinaAluno>() {
	      @Override
	      public int compare(PresencaUsuarioDisciplinaAluno o1, PresencaUsuarioDisciplinaAluno o2) {
	        return o1.getNomePeriodo().compareTo(o2.getNomePeriodo());
	      }
	    });		
	    
		nomeDisciplinaColumn.setSortable(true);
	    sortHandler.setComparator(nomeDisciplinaColumn, new Comparator<PresencaUsuarioDisciplinaAluno>() {
	      @Override
	      public int compare(PresencaUsuarioDisciplinaAluno o1, PresencaUsuarioDisciplinaAluno o2) {
	        return o1.getNomeDisciplina().compareTo(o2.getNomeDisciplina());
	      }
	    });	
		
//	    quantAulaColumn.setSortable(true);
//		sortHandler.setComparator(quantAulaColumn, new Comparator<PresencaUsuarioDisciplinaAluno>() {
//	      @Override
//	      public int compare(PresencaUsuarioDisciplinaAluno o1, PresencaUsuarioDisciplinaAluno o2) {
//	    	String strO1 = Integer.toString(o1.getQuantAulas());
//	    	String strO2 = Integer.toString(o2.getQuantAulas());
//	        return strO1.compareTo(strO2);
//	      }
//	    });		
		
//	    quantPresencaColumn.setSortable(true);
//		sortHandler.setComparator(quantPresencaColumn, new Comparator<PresencaUsuarioDisciplinaAluno>() {
//	      @Override
//	      public int compare(PresencaUsuarioDisciplinaAluno o1, PresencaUsuarioDisciplinaAluno o2) {
//	    	String strO1 = Integer.toString(o1.getQuantPresenca());
//	    	String strO2 = Integer.toString(o2.getQuantPresenca());
//	        return strO1.compareTo(strO2);
//	      }
//	    });		

//	    quantFaltaColumn.setSortable(true);
//		sortHandler.setComparator(quantFaltaColumn, new Comparator<PresencaUsuarioDisciplinaAluno>() {
//	      @Override
//	      public int compare(PresencaUsuarioDisciplinaAluno o1, PresencaUsuarioDisciplinaAluno o2) {
//	    	String strO1 = Integer.toString(o1.getQuantFaltas());
//	    	String strO2 = Integer.toString(o2.getQuantFaltas());
//	        return strO1.compareTo(strO2);
//	      }
//	    });
		
//	    quantJustificativaColumn.setSortable(true);
//		sortHandler.setComparator(quantJustificativaColumn, new Comparator<PresencaUsuarioDisciplinaAluno>() {
//	      @Override
//	      public int compare(PresencaUsuarioDisciplinaAluno o1, PresencaUsuarioDisciplinaAluno o2) {
//	    	String strO1 = Integer.toString(o1.getQuantJustificadas());
//	    	String strO2 = Integer.toString(o2.getQuantJustificadas());
//	        return strO1.compareTo(strO2);
//	      }
//	    });	
		
		situacaoColumn.setSortable(true);
	    sortHandler.setComparator(situacaoColumn, new Comparator<PresencaUsuarioDisciplinaAluno>() {
	      @Override
	      public int compare(PresencaUsuarioDisciplinaAluno o1, PresencaUsuarioDisciplinaAluno o2) {
	        return o1.getSituacao().compareTo(o2.getSituacao());
	      }
	    });			
		
	}	
	
/***************************************BEGIN CellTable Functions***************************************/
/*******************************************************************************************************/
/*******************************************************************************************************/	
/*******************************************************************************************************/
/***************************************BEGIN Filterting CellTable***************************************/	
	
	public void filtrarCellTable(String strFiltro) {
		
		removeCellTableFilter();

		strFiltro = strFiltro.toUpperCase();

		if (!strFiltro.isEmpty()) {

			int i = 0;
			while (i < dataProvider.getList().size()) {

				String strNomePeriodo =dataProvider.getList().get(i).getNomePeriodo();
				String strNomeDisciplina =dataProvider.getList().get(i).getNomeDisciplina();	
				

				String strJuntaTexto = strNomePeriodo.toUpperCase() + strNomeDisciplina.toUpperCase();

				if (!strJuntaTexto.contains(strFiltro)) {
					dataProvider.getList().remove(i);
					i = 0;
					continue;
				}

				i++;
			}

		}

	}
	
	public void removeCellTableFilter(){
		
		dataProvider.getList().clear();

		for (int i = 0; i < arrayListBackup.size(); i++) {
			dataProvider.getList().add(arrayListBackup.get(i));
		}
		cellTable.setPageStart(0);
	}
/***************************************END Filterting CellTable***************************************/
		
	
	
}


