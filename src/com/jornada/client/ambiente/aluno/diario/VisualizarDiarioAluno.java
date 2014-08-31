package com.jornada.client.ambiente.aluno.diario;

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
import com.jornada.client.classes.listBoxes.MpSelectionAlunosPorCurso;
import com.jornada.client.classes.listBoxes.ambiente.aluno.MpSelectionCursoAmbienteAluno;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServicePresenca;
import com.jornada.shared.classes.Aula;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.Presenca;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.presenca.TabelaPresencaAluno;
import com.jornada.shared.classes.utility.MpUtilClient;

public class VisualizarDiarioAluno extends VerticalPanel {

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpPanelLoadingAluno = new MpPanelLoading("images/radar.gif");

	private MpSelectionCursoAmbienteAluno listBoxCurso;
	private MpSelectionAlunosPorCurso listBoxAlunosPorCurso;
	
	private TextBox txtSearch;
	
	private CellTable<TabelaPresencaAluno> cellTable;
	private ListDataProvider<TabelaPresencaAluno> dataProvider  = new ListDataProvider<TabelaPresencaAluno>();
	ArrayList<TabelaPresencaAluno> arrayListBackup = new ArrayList<TabelaPresencaAluno>(); 
	private Column<TabelaPresencaAluno, String> nomePeriodoColumn;
	private Column<TabelaPresencaAluno, String> nomeDisciplinaColumn;
	private Column<TabelaPresencaAluno, String> quantAulaColumn;
	private Column<TabelaPresencaAluno, String> quantPresencaColumn;
	private Column<TabelaPresencaAluno, String> quantFaltaColumn;
	private Column<TabelaPresencaAluno, String> quantJustificativaColumn;
	private Column<TabelaPresencaAluno, String> porcentagemPresencaColumn;
	private Column<TabelaPresencaAluno, String> situacaoColumn;
	
	private int idUsuario;
		
	VerticalPanel vFormPanel = new VerticalPanel();
	ScrollPanel scrollPanel = new ScrollPanel();
	
	TextConstants txtConstants;

	
	private TelaInicialDiarioAluno telaInicialDiarioAluno;

	public VisualizarDiarioAluno(final TelaInicialDiarioAluno telaInicialDiarioAluno) {
		
		scrollPanel.setAlwaysShowScrollBars(false);

//		scrollPanel.setSize(Integer.toString(TelaInicialDiarioAluno.intWidthTable+30)+"px",Integer.toString(TelaInicialDiarioAluno.intHeightTable-120)+"px");
		scrollPanel.setHeight(Integer.toString(TelaInicialDiarioAluno.intHeightTable-120)+"px");
		scrollPanel.setWidth("100%");
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialDiarioAluno=telaInicialDiarioAluno;

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
		cellFormatter.setColSpan(0, 0, 0);
		cellFormatter.setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_CENTER);

		
		Label lblCurso = new Label(txtConstants.curso());
		
		lblCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		lblCurso.setStyleName("design_label");


		// Add some standard form options
		int row = 1;
		flexTableWithListBoxes.setWidget(row, 0, lblCurso);

		listBoxCurso = new MpSelectionCursoAmbienteAluno(telaInicialDiarioAluno.getMainView().getUsuarioLogado());	
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());
		

		if(telaInicialDiarioAluno.getMainView().getUsuarioLogado().getIdTipoUsuario()==TipoUsuario.ALUNO){
			flexTableWithListBoxes.setWidget(row, 0, lblCurso);	
			flexTableWithListBoxes.setWidget(row, 1, listBoxCurso);
			flexTableWithListBoxes.setWidget(row++, 2, mpPanelLoadingAluno);
			idUsuario = telaInicialDiarioAluno.getMainView().getUsuarioLogado().getIdUsuario();
			
		}else{
			Label lblNomeAluno = new Label(txtConstants.alunoNome());
			lblNomeAluno.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);	
			lblNomeAluno.setStyleName("design_label");
			
			listBoxAlunosPorCurso = new MpSelectionAlunosPorCurso();
			listBoxAlunosPorCurso.addChangeHandler(new MpAlunosPorCursoSelectionChangeHandler());

			flexTableWithListBoxes.setWidget(row, 0, lblCurso);	
			flexTableWithListBoxes.setWidget(row++, 1, listBoxCurso);
			flexTableWithListBoxes.setWidget(row, 0, lblNomeAluno);
			flexTableWithListBoxes.setWidget(row, 1, listBoxAlunosPorCurso);
			flexTableWithListBoxes.setWidget(row, 2, mpPanelLoadingAluno);
			
		}
		
		
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

				if (telaInicialDiarioAluno.getMainView().getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.ALUNO) {
					populateGrid();
				} else {
					listBoxAlunosPorCurso.populateComboBox(idCurso);
				}
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
					mpDialogBoxWarning.setBodyText(txtConstants.presencaErroSalvar());
					mpDialogBoxWarning.showDialog();
				}

				@Override
				public void onSuccess(ArrayList<Periodo> list) {
					
					MpUtilClient.isRefreshRequired(list);
					
					ArrayList<TabelaPresencaAluno> listTabelaPresencaAluno = convertePeriodoParaTabela(list);
					
					mpPanelLoadingAluno.setVisible(false);
					dataProvider.getList().clear();
					arrayListBackup.clear();
					cellTable.setRowCount(0);
					for(int i=0;i<listTabelaPresencaAluno.size();i++){
						dataProvider.getList().add(listTabelaPresencaAluno.get(i));
						arrayListBackup.add(listTabelaPresencaAluno.get(i));
					}
					addCellTableData(dataProvider);
					cellTable.redraw();			
				}
			});
		}
	}	
	
	private ArrayList<TabelaPresencaAluno> convertePeriodoParaTabela(ArrayList<Periodo> arrayPeriodo){
		
		int intPorcentagemPresencaCurso = Integer.parseInt(listBoxCurso.getListCurso().get(listBoxCurso.getSelectedIndex()).getPorcentagemPresenca());
		
		ArrayList<TabelaPresencaAluno> listTpa = new ArrayList<TabelaPresencaAluno>();
		
		for(int cvPer=0;cvPer<arrayPeriodo.size();cvPer++){
					
			Periodo periodo = arrayPeriodo.get(cvPer);
			
			for(int cvDis=0;cvDis<periodo.getListDisciplinas().size();cvDis++){
				TabelaPresencaAluno tpa = new TabelaPresencaAluno();
				Disciplina disciplina = periodo.getListDisciplinas().get(cvDis);				
				int quantidateAula = disciplina.getListAula().size();
				int quantidatePresencas = getNumeroTipoPresenca(disciplina.getListAula(), Presenca.PRESENCA);
				int quantidateFaltas = getNumeroTipoPresenca(disciplina.getListAula(), Presenca.FALTA);
				int quantidateJustificadas = getNumeroTipoPresenca(disciplina.getListAula(), Presenca.FALTA_JUSTIFICADA);
				int quantidadePresencaSalaDeAula;
				
				if(quantidateAula==0 || (quantidatePresencas==0&&quantidateJustificadas==0)){
					quantidadePresencaSalaDeAula=0;
				}else{
					Double doublePresenca = (((double)quantidatePresencas+(double)quantidateJustificadas)/(double)quantidateAula)*100;
					quantidadePresencaSalaDeAula = 	doublePresenca.intValue();
				}		
				
				if(quantidadePresencaSalaDeAula>=intPorcentagemPresencaCurso){
					tpa.setSituacao(TabelaPresencaAluno.APROVADO);
				}else{
					tpa.setSituacao(TabelaPresencaAluno.REPROVADO);
				}
				
				
				tpa.setNomePeriodo(periodo.getNomePeriodo());
				tpa.setNomeDisciplina(disciplina.getNome());
				tpa.setQuantAulas(quantidateAula);
				tpa.setQuantPresenca(quantidatePresencas);
				tpa.setQuantFaltas(quantidateFaltas);
				tpa.setQuantJustificadas(quantidateJustificadas);
				tpa.setPresencaSalaAula(quantidadePresencaSalaDeAula);
				
				listTpa.add(tpa);
				
			}
			
			
			
		}
		
		return listTpa;
	}
	
	private int getNumeroTipoPresenca(ArrayList<Aula> listAula, int tipoPresenca){
		int quantidadeTipoPresenca=0;
		
		for(int cvAula=0;cvAula<listAula.size();cvAula++){
			Aula aula = listAula.get(cvAula);
			
			for(int cvPre=0;cvPre<aula.getArrayPresenca().size();cvPre++){
				Presenca presenca = aula.getArrayPresenca().get(cvPre);
				if(presenca.getIdTipoPresenca()==tipoPresenca){
					quantidadeTipoPresenca++;
				}
			}			
		}
		return quantidadeTipoPresenca;		
	}

	public void updateClientData(){
		listBoxCurso.populateComboBox(this.telaInicialDiarioAluno.getMainView().getUsuarioLogado());
	}	
	
/***************************************END POPULATE DATA***********************************************/		
/*******************************************************************************************************/	
/*******************************************************************************************************/
/*******************************************************************************************************/	
/***************************************BEGIN CellTable Functions***************************************/
	
	
	public void initializeCellTable(){
		cellTable = new CellTable<TabelaPresencaAluno>(10,GWT.<CellTableStyle> create(CellTableStyle.class));

//		cellTable.setWidth(Integer.toString(TelaInicialDiarioAluno.intWidthTable)+ "px");		
		cellTable.setWidth("100%");
		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);
		
		dataProvider.addDataDisplay(cellTable);	
		
		// Add a selection model so we can select cells.
		final SelectionModel<TabelaPresencaAluno> selectionModel = new MultiSelectionModel<TabelaPresencaAluno>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<TabelaPresencaAluno> createCheckboxManager());

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
		
		
//		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(TelaInicialAlunoOcorrencia.intWidthTable+30)+"px",Integer.toString(TelaInicialAlunoOcorrencia.intHeightTable-110)+"px");
//		scrollPanel.setAlwaysShowScrollBars(true);		
//		scrollPanel.add(cellTable);	
		
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setWidth("100%");
		vPanel.setCellVerticalAlignment(cellTable, ALIGN_TOP);
		vPanel.add(cellTable);
		
		MpSpaceVerticalPanel mpSpaceVerticalPanel = new MpSpaceVerticalPanel();
		
		VerticalPanel vPanelInScroll = new VerticalPanel();
		vPanelInScroll.setWidth("100%");
		vPanelInScroll.setBorderWidth(0);
		vPanelInScroll.setCellVerticalAlignment(cellTable, ALIGN_TOP);
		vPanelInScroll.add(flexTableFiltrarAluno);
		vPanelInScroll.add(vPanel);
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
	
	private void addCellTableData(ListDataProvider<TabelaPresencaAluno> dataProvider){
		
		 ListHandler<TabelaPresencaAluno> sortHandler = new ListHandler<TabelaPresencaAluno>(dataProvider.getList());
		 
		 cellTable.addColumnSortHandler(sortHandler);	

		 initSortHandler(sortHandler);
	
	}
		
	private void initTableColumns(
			final SelectionModel<TabelaPresencaAluno> selectionModel) {

		nomePeriodoColumn = new Column<TabelaPresencaAluno, String>(new TextCell()) {
			@Override
			public String getValue(TabelaPresencaAluno object) {
				return object.getNomePeriodo();
			}

		};

		nomeDisciplinaColumn = new Column<TabelaPresencaAluno, String>(new TextCell()) {
			@Override
			public String getValue(TabelaPresencaAluno object) {
				return object.getNomeDisciplina();
			}

		};

		quantAulaColumn = new Column<TabelaPresencaAluno, String>(new TextCell()) {
			@Override
			public String getValue(TabelaPresencaAluno object) {
				return Integer.toString(object.getQuantAulas());
			}
		};

		quantPresencaColumn = new Column<TabelaPresencaAluno, String>(new TextCell()) {
			@Override
			public String getValue(TabelaPresencaAluno object) {
				return Integer.toString(object.getQuantPresenca());
			}
		};

		quantFaltaColumn = new Column<TabelaPresencaAluno, String>(new TextCell()) {
			@Override
			public String getValue(TabelaPresencaAluno object) {
				return Integer.toString(object.getQuantFaltas());
			}
		};

		quantJustificativaColumn = new Column<TabelaPresencaAluno, String>(new TextCell()) {
			@Override
			public String getValue(TabelaPresencaAluno object) {
				return Integer.toString(object.getQuantJustificadas());
			}
		};
		
		porcentagemPresencaColumn = new Column<TabelaPresencaAluno, String>(new TextCell()) {
			@Override
			public String getValue(TabelaPresencaAluno object) {
			
				return Integer.toString(object.getPresencaSalaAula())+"%";
			}
		};		
		
		situacaoColumn = new Column<TabelaPresencaAluno, String>(new TextCell()) {
			@Override
			public String getValue(TabelaPresencaAluno object) {
			
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

	public void initSortHandler(ListHandler<TabelaPresencaAluno> sortHandler){
		nomePeriodoColumn.setSortable(true);
	    sortHandler.setComparator(nomePeriodoColumn, new Comparator<TabelaPresencaAluno>() {
	      @Override
	      public int compare(TabelaPresencaAluno o1, TabelaPresencaAluno o2) {
	        return o1.getNomePeriodo().compareTo(o2.getNomePeriodo());
	      }
	    });		
	    
		nomeDisciplinaColumn.setSortable(true);
	    sortHandler.setComparator(nomeDisciplinaColumn, new Comparator<TabelaPresencaAluno>() {
	      @Override
	      public int compare(TabelaPresencaAluno o1, TabelaPresencaAluno o2) {
	        return o1.getNomeDisciplina().compareTo(o2.getNomeDisciplina());
	      }
	    });	
		
	    quantAulaColumn.setSortable(true);
		sortHandler.setComparator(quantAulaColumn, new Comparator<TabelaPresencaAluno>() {
	      @Override
	      public int compare(TabelaPresencaAluno o1, TabelaPresencaAluno o2) {
	    	String strO1 = Integer.toString(o1.getQuantAulas());
	    	String strO2 = Integer.toString(o2.getQuantAulas());
	        return strO1.compareTo(strO2);
	      }
	    });		
		
	    quantPresencaColumn.setSortable(true);
		sortHandler.setComparator(quantPresencaColumn, new Comparator<TabelaPresencaAluno>() {
	      @Override
	      public int compare(TabelaPresencaAluno o1, TabelaPresencaAluno o2) {
	    	String strO1 = Integer.toString(o1.getQuantPresenca());
	    	String strO2 = Integer.toString(o2.getQuantPresenca());
	        return strO1.compareTo(strO2);
	      }
	    });		

	    quantFaltaColumn.setSortable(true);
		sortHandler.setComparator(quantFaltaColumn, new Comparator<TabelaPresencaAluno>() {
	      @Override
	      public int compare(TabelaPresencaAluno o1, TabelaPresencaAluno o2) {
	    	String strO1 = Integer.toString(o1.getQuantFaltas());
	    	String strO2 = Integer.toString(o2.getQuantFaltas());
	        return strO1.compareTo(strO2);
	      }
	    });
		
	    quantJustificativaColumn.setSortable(true);
		sortHandler.setComparator(quantJustificativaColumn, new Comparator<TabelaPresencaAluno>() {
	      @Override
	      public int compare(TabelaPresencaAluno o1, TabelaPresencaAluno o2) {
	    	String strO1 = Integer.toString(o1.getQuantJustificadas());
	    	String strO2 = Integer.toString(o2.getQuantJustificadas());
	        return strO1.compareTo(strO2);
	      }
	    });	
		
		situacaoColumn.setSortable(true);
	    sortHandler.setComparator(situacaoColumn, new Comparator<TabelaPresencaAluno>() {
	      @Override
	      public int compare(TabelaPresencaAluno o1, TabelaPresencaAluno o2) {
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


