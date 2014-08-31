package com.jornada.client.ambiente.professor.diario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionCursoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionDisciplinaAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionPeriodoAmbienteProfessor;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.cells.MpStyledSelectionCell;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBoxRefreshPage;
import com.jornada.client.classes.widgets.label.MpLabelTextBoxError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServicePresenca;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Aula;
import com.jornada.shared.classes.Presenca;
import com.jornada.shared.classes.TipoPresenca;
import com.jornada.shared.classes.presenca.PresencaUsuarioAula;
import com.jornada.shared.classes.utility.MpUtilClient;

public class AdicionarDiarioProfessor extends VerticalPanel {

	private AsyncCallback<ArrayList<PresencaUsuarioAula>> callBackDiario;
	private AsyncCallback<String> callBackAddPresenca;

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpPanelLoadingSaving = new MpPanelLoading("images/radar.gif");
	MpPanelLoading mpPanelLoadingAluno = new MpPanelLoading("images/radar.gif");

	private MpSelectionCursoAmbienteProfessor listBoxCurso;
	private MpSelectionPeriodoAmbienteProfessor listBoxPeriodo;
	private MpSelectionDisciplinaAmbienteProfessor listBoxDisciplina;	
//	private MpSelectionConteudoProgramatico listBoxConteudoProgramatico;
	
	private MpLabelTextBoxError lblErroDisciplina;
	private MpLabelTextBoxError lblErroDateBox;	
	
	
	private ArrayList<Presenca> pendingChanges = new ArrayList<Presenca>();
	private CellTable<PresencaUsuarioAula> cellTable;
	private ListDataProvider<PresencaUsuarioAula> dataProvider = new ListDataProvider<PresencaUsuarioAula>();
	ArrayList<PresencaUsuarioAula> arrayListBackup = new ArrayList<PresencaUsuarioAula>(); 
	private Column<PresencaUsuarioAula, String> columnTipoPresenca;
	private Column<PresencaUsuarioAula, String> columnPrimeiroNome;
	private Column<PresencaUsuarioAula, String> columnSobreNome;
	
	private LinkedHashMap<String, String> listaTipoPresenca = new LinkedHashMap<String, String>();
	
	VerticalPanel vFormPanel = new VerticalPanel();
	
	private MpDateBoxWithImage mpDateBoxInicial;

	TextConstants txtConstants;
	
	private TextBox txtSearch;
	
	private TelaInicialDiarioProfessor telaInicialDiarioProfessor;

	@SuppressWarnings("deprecation")
	public AdicionarDiarioProfessor(final TelaInicialDiarioProfessor telaInicialDiarioProfessor) {
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialDiarioProfessor=telaInicialDiarioProfessor;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpPanelLoadingSaving.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoadingSaving.show();
		mpPanelLoadingSaving.setVisible(false);
		mpPanelLoadingAluno.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoadingAluno.show();
		mpPanelLoadingAluno.setVisible(false);

		FlexTable flexTable = new FlexTable();
		flexTable.setCellSpacing(3);
		flexTable.setCellPadding(3);
		flexTable.setBorderWidth(0);
//		flexTable.setSize(Integer.toString(TelaInicialDiarioProfessor.intWidthTable),Integer.toString(TelaInicialDiarioProfessor.intHeightTable));
//		flexTable.setHeight(Integer.toString(TelaInicialDiarioProfessor.intHeightTable)+"px");
//		flexTable.setWidth("100%");
		FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();

		// Add a title to the form
		cellFormatter.setColSpan(0, 0, 0);
		cellFormatter.setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_CENTER);
		
		lblErroDisciplina = new MpLabelTextBoxError();
		lblErroDateBox = new MpLabelTextBoxError();			
		
		Label lblCurso = new Label(txtConstants.curso());
		Label lblPeriodo = new Label(txtConstants.periodo());
		Label lblDisciplina = new Label(txtConstants.disciplina());
		Label lblDataInicial = new Label(txtConstants.presencaData());
		
		lblCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		lblPeriodo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		lblDisciplina.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDataInicial.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		lblCurso.setStyleName("design_label");
		lblPeriodo.setStyleName("design_label");
		lblDisciplina.setStyleName("design_label");
		lblDataInicial.setStyleName("design_label");

		// Add some standard form options
		int row = 1;
		flexTable.setWidget(row, 0, lblCurso);

		listBoxCurso = new MpSelectionCursoAmbienteProfessor(telaInicialDiarioProfessor.getMainView().getUsuarioLogado());		
		listBoxPeriodo = new MpSelectionPeriodoAmbienteProfessor(telaInicialDiarioProfessor.getMainView().getUsuarioLogado());		
		listBoxDisciplina = new MpSelectionDisciplinaAmbienteProfessor(telaInicialDiarioProfessor.getMainView().getUsuarioLogado());		
		mpDateBoxInicial = new MpDateBoxWithImage();
		mpDateBoxInicial.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
		mpDateBoxInicial.getDate().setWidth("170px");		
		
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());
		listBoxPeriodo.addChangeHandler(new MpPeriodoSelectionChangeHandler());		
		listBoxDisciplina.addChangeHandler(new MpDisciplinaSelectionChangeHandler());

		
		flexTable.setWidget(row, 0, lblCurso);	flexTable.setWidget(row++, 1, listBoxCurso);
		flexTable.setWidget(row, 0, lblPeriodo);flexTable.setWidget(row++, 1, listBoxPeriodo);
		flexTable.setWidget(row, 0, lblDisciplina);flexTable.setWidget(row, 1, listBoxDisciplina);flexTable.setWidget(row++, 2, lblErroDisciplina);
		flexTable.setWidget(row, 0, lblDataInicial);flexTable.setWidget(row, 1, mpDateBoxInicial);flexTable.setWidget(row++, 2, lblErroDateBox);
		
		vFormPanel.setBorderWidth(0);
		vFormPanel.setWidth("100%");
		vFormPanel.add(flexTable);

		/***********************Begin Callbacks**********************/

		callBackDiario = new AsyncCallback<ArrayList<PresencaUsuarioAula>>() {

			public void onFailure(Throwable caught) {
				mpPanelLoadingSaving.setVisible(false);
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.presencaErroSalvar());
				mpDialogBoxWarning.showDialog();			
				
			}

			@Override
			public void onSuccess(ArrayList<PresencaUsuarioAula> list) {
				MpUtilClient.isRefreshRequired(list);
				mpPanelLoadingSaving.setVisible(false);
				
				if (list == null) {
					MpDialogBoxRefreshPage mpRefresh = new MpDialogBoxRefreshPage();
					mpRefresh.showDialog();

				} else {

					pendingChanges.clear();
					arrayListBackup.clear();
					dataProvider.getList().clear();

					for (int i = 0; i < list.size(); i++) {
						dataProvider.getList().add(list.get(i));
						arrayListBackup.add(list.get(i));
					}

					cleanGrid();

					addCellTableData(dataProvider);				
				}

			}
		};
		
		callBackAddPresenca = new AsyncCallback<String>() {

			public void onFailure(Throwable caught) {
				mpPanelLoadingSaving.setVisible(false);
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.presencaErroSalvar());
				mpDialogBoxWarning.showDialog();		

			}

			@Override
			public void onSuccess(String strResult) {
				mpPanelLoadingSaving.setVisible(false);
				
				if(strResult.equalsIgnoreCase("ok")){
//					pendingChanges.clear();
					mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
					mpDialogBoxConfirm.setBodyText(txtConstants.presencaSalva());
					mpDialogBoxConfirm.showDialog();	
					telaInicialDiarioProfessor.updateEditarDiarioProfessor();
					populateGridUsuarios();
					
				}else{
					mpPanelLoadingSaving.setVisible(false);
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.presencaErroSalvar()+" "+txtConstants.geralRegarregarPagina());
					mpDialogBoxWarning.showDialog();							
					
				}

			}
		};
		

		

		/***********************End Callbacks**********************/
		populateComboBoxTipoPresenca();
		
		this.setWidth("100%");
		super.add(vFormPanel);

	}
	
	
	public void initializeCellTable(){
		cellTable = new CellTable<PresencaUsuarioAula>(10,GWT.<CellTableStyle> create(CellTableStyle.class),PresencaUsuarioAula.KEY_PROVIDER);
//		cellTable.setWidth(Integer.toString(TelaInicialDiarioProfessor.intWidthTable)+ "px");		
		cellTable.setWidth("100%");
		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);

		// Add a selection model so we can select cells.
		final SelectionModel<PresencaUsuarioAula> selectionModel = new MultiSelectionModel<PresencaUsuarioAula>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<PresencaUsuarioAula> createCheckboxManager());
		initTableColumns(selectionModel);

		dataProvider.addDataDisplay(cellTable);	
		
		MpSimplePager mpPager = new MpSimplePager();
		mpPager.setDisplay(cellTable);
//		mpPager.setPageSize(10);
		
		FlexTable flexTableFiltrarAluno = new FlexTable();		
		flexTableFiltrarAluno.setCellSpacing(3);
		flexTableFiltrarAluno.setCellPadding(3);
		flexTableFiltrarAluno.setBorderWidth(0);
		
		Label lblAluno = new Label(txtConstants.aluno());
		MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
		txtSearch = new TextBox();
		txtSearch.setStyleName("design_text_boxes");	
		
		txtSearch.addKeyUpHandler(new EnterKeyUpHandler());
		btnFiltrar.addClickHandler(new ClickHandlerFiltrar());
		
		flexTableFiltrarAluno.setWidget(0, 0, mpPager);
		flexTableFiltrarAluno.setWidget(0, 1, new MpSpaceVerticalPanel());
		flexTableFiltrarAluno.setWidget(0, 2, lblAluno);
		flexTableFiltrarAluno.setWidget(0, 3, txtSearch);
		flexTableFiltrarAluno.setWidget(0, 4, btnFiltrar);
		flexTableFiltrarAluno.setWidget(0, 5, mpPanelLoadingAluno);	
		
		
		MpImageButton btnSave = new MpImageButton(txtConstants.presencaSalvarListaPresenca(), "images/save.png");
		btnSave.addClickHandler(new ClickHandlerSave());
		
		Grid gridSave = new Grid(1, 2);
		gridSave.setCellSpacing(2);
		gridSave.setCellPadding(2);
		{
			int i = 0;
			gridSave.setWidget(0, i++, btnSave);
			gridSave.setWidget(0, i++, mpPanelLoadingSaving);
		}		
		
		
		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(TelaInicialDiarioProfessor.intWidthTable+30)+"px",Integer.toString(TelaInicialDiarioProfessor.intHeightTable-250)+"px");
		scrollPanel.setHeight(Integer.toString(TelaInicialDiarioProfessor.intHeightTable-250)+"px");
		scrollPanel.setWidth("100%");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(cellTable);				
		
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setWidth("100%");
		vPanel.setHeight("280px");
		vPanel.setCellVerticalAlignment(scrollPanel, ALIGN_TOP);
		vPanel.add(scrollPanel);
		
		vFormPanel.add(flexTableFiltrarAluno);
		vFormPanel.add(vPanel);
		vFormPanel.add(gridSave);
		vFormPanel.add(new MpSpaceVerticalPanel());
//		vFormPanel.setWidth("100%");
		
		
		vFormPanel.setCellHorizontalAlignment(gridSave, ALIGN_CENTER);
		
	}


	
	/****************Begin Event Handlers*****************/
	
	private class ClickHandlerSave implements ClickHandler {

		public void onClick(ClickEvent event) {

			if (checkFieldsValidator()) {
				int idDisciplina = Integer.parseInt(listBoxDisciplina.getValue(listBoxDisciplina.getSelectedIndex()));

				Aula aula = new Aula();
				aula.setIdDisciplina(idDisciplina);
				aula.setData(mpDateBoxInicial.getDate().getValue());
				System.out.println(mpDateBoxInicial.getDate().getValue());

				ArrayList<Presenca> arrayPresenca = new ArrayList<Presenca>();

				for (int i = 0; i < arrayListBackup.size(); i++) {

					PresencaUsuarioAula pu = arrayListBackup.get(i);
					Presenca presenca = new Presenca();
					
					presenca.setIdUsuario(pu.getUsuario().getIdUsuario());
					presenca.setIdTipoPresenca(pu.getPresenca().getIdTipoPresenca());

					for(int pend=0;pend<pendingChanges.size();pend++){

						if(presenca.getIdUsuario()==pendingChanges.get(pend).getIdUsuario()){
							presenca.setIdUsuario(pendingChanges.get(pend).getIdUsuario());
							presenca.setIdTipoPresenca(pendingChanges.get(pend).getIdTipoPresenca());
						}						
					}
					

					arrayPresenca.add(presenca);

				}
				
				aula.setArrayPresenca(arrayPresenca);
				
				cleanFields();
				removeCellTableFilter();
				mpPanelLoadingSaving.setVisible(true);				

				GWTServicePresenca.Util.getInstance().AdicionarNovaPresenca(aula, callBackAddPresenca);

			}

		}
	}
	
	
	private class MpCursoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {		
			arrayListBackup.clear();
			pendingChanges.clear();
			int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
			listBoxPeriodo.populateComboBox(idCurso);
			populateGridUsuarios();
		}  
	}	
	
	private class MpPeriodoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int index = listBoxPeriodo.getSelectedIndex();
			if(index==-1){
				listBoxDisciplina.clear();
//				listBoxConteudoProgramatico.clear();
			}
			else{
				int idPeriodo = Integer.parseInt(listBoxPeriodo.getValue(index));
				listBoxDisciplina.populateComboBox(idPeriodo);				
			}
		}  
	}
	
	private class MpDisciplinaSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
//			int index = listBoxDisciplina.getSelectedIndex();
//			if(index==-1){
////				listBoxConteudoProgramatico.clear();
//			}
//			else{
//				int idDisciplina= Integer.parseInt(listBoxDisciplina.getValue(index));
////				listBoxConteudoProgramatico.populateComboBox(idDisciplina);				
//			}
		}  
	}	
	
	
	/****************End Event Handlers*****************/
	
	public void updateClientData(){
		listBoxCurso.populateComboBox(this.telaInicialDiarioProfessor.getMainView().getUsuarioLogado());
	}
	
	
	public boolean checkFieldsValidator(){
		
		boolean isFieldsOk = false;				
		
		
		boolean isDateOk=false;
		if(FieldVerifier.isValidDate(mpDateBoxInicial.getDate().getTextBox().getValue())){
			isDateOk=true;	
			lblErroDateBox.hideErroMessage();
		}else{
			isDateOk=false;
			lblErroDateBox.showErrorMessage(txtConstants.geralCampoObrigatorio("Data"));
		}
		
		boolean isConteudoOk=false;
		
		if(FieldVerifier.isValidListBoxSelectedValue(listBoxDisciplina.getSelectedIndex())){
			isConteudoOk=true;
			lblErroDisciplina.hideErroMessage();
		}else{
			lblErroDisciplina.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.disciplina()));
		}
		
		isFieldsOk = isDateOk && isConteudoOk;

		
		return isFieldsOk;
	}	
	
	private void cleanFields(){
		lblErroDisciplina.hideErroMessage();
		lblErroDateBox.hideErroMessage();
		txtSearch.setText("");
		mpDateBoxInicial.getDate().setValue(null);
//		txtNome.setValue("");
//		txtNumeracao.setValue("");
//		txtDescricao.setValue("");
//		txtObjetivo.setValue("");
	}	
	
	
	private void populateGridUsuarios() {
		int idSelectedCurso = listBoxCurso.getSelectedIndex();
		if (idSelectedCurso != -1) {
			int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
			GWTServicePresenca.Util.getInstance().getAlunos(idCurso, callBackDiario);
		}
	}	
	
	
	private void addCellTableData(ListDataProvider<PresencaUsuarioAula> dataProvider) {

		ListHandler<PresencaUsuarioAula> sortHandler = new ListHandler<PresencaUsuarioAula>(dataProvider.getList());

		cellTable.addColumnSortHandler(sortHandler);

		initSortHandler(sortHandler);

	}

	private void initTableColumns(final SelectionModel<PresencaUsuarioAula> selectionModel) {
		
		
	    MpStyledSelectionCell tipoAvaliacaoCell = new MpStyledSelectionCell(listaTipoPresenca,"design_text_boxes");
	    columnTipoPresenca = new Column<PresencaUsuarioAula, String>(tipoAvaliacaoCell) {
	      @Override
	      public String getValue(PresencaUsuarioAula object) {
	        return Integer.toString(object.getPresenca().getIdTipoPresenca());
	      }
	    };
	    columnTipoPresenca.setFieldUpdater(new FieldUpdater<PresencaUsuarioAula, String>() {
			@Override
			public void update(int index, PresencaUsuarioAula object, String value) {
				// Called when the user changes the value.
				
//				Presenca presenca = arrayListBackup.get(index).getPresenca().setIdTipoPresenca(Integer.parseInt(value));
//				presenca.setIdTipoPresenca(Integer.parseInt(value));	
				Presenca presenca = new Presenca();
				presenca.setIdUsuario(arrayListBackup.get(index).getUsuario().getIdUsuario());
				presenca.setIdTipoPresenca(Integer.parseInt(value));
								
				pendingChanges.add(presenca);
				
//				for(int i=0;i<arrayListBackup.size();i++){
//					System.out.println("getIdUsuario:"+presenca.getIdUsuario());
//					System.out.println("getIdTipoPresenca:"+presenca.getIdTipoPresenca());
					
//				}
//				System.out.println("========================");
				
//				object.getPresenca().setIdTipoPresenca(Integer.parseInt(value));
			}
		});
		
		columnPrimeiroNome = new Column<PresencaUsuarioAula, String>(new TextCell()) {
			@Override
			public String getValue(PresencaUsuarioAula object) {
//				return object.getPrimeiroNome();
				return object.getUsuario().getPrimeiroNome();
			}

		};
		
		columnSobreNome = new Column<PresencaUsuarioAula, String>(new TextCell()) {
			@Override
			public String getValue(PresencaUsuarioAula object) {
				return object.getUsuario().getSobreNome();
			}

		};

		cellTable.addColumn(columnTipoPresenca, txtConstants.presenca());
		cellTable.addColumn(columnPrimeiroNome, txtConstants.usuarioPrimeiroNome());
		cellTable.addColumn(columnSobreNome, txtConstants.usuarioSobreNome());

		cellTable.getColumn(cellTable.getColumnIndex(columnTipoPresenca)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnPrimeiroNome)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnSobreNome)).setCellStyleNames("edit-cell");
		
		cellTable.setColumnWidth(cellTable.getColumnIndex(columnTipoPresenca), "75px");
		cellTable.setColumnWidth(cellTable.getColumnIndex(columnPrimeiroNome), "250px");
			
	}

	public void initSortHandler(ListHandler<PresencaUsuarioAula> sortHandler) {
		
		
		
	    columnTipoPresenca.setSortable(true);
	    sortHandler.setComparator(columnTipoPresenca, new Comparator<PresencaUsuarioAula>() {
	      @Override
	      public int compare(PresencaUsuarioAula o1, PresencaUsuarioAula o2) {
	    	  int primitive1 = o1.getPresenca().getIdTipoPresenca(), primitive2 = o2.getPresenca().getIdTipoPresenca();
	    	  Integer a = new Integer(primitive1);
	    	  Integer b = new Integer(primitive2);
	    	  return a.compareTo(b);
	      }
	    });			
		
		columnPrimeiroNome.setSortable(true);
	    sortHandler.setComparator(columnPrimeiroNome, new Comparator<PresencaUsuarioAula>() {
	      @Override
	      public int compare(PresencaUsuarioAula o1, PresencaUsuarioAula o2) {
	        return o1.getUsuario().getPrimeiroNome().compareTo(o2.getUsuario().getPrimeiroNome());
	      }
	    });			
	    
		columnSobreNome.setSortable(true);
	    sortHandler.setComparator(columnSobreNome, new Comparator<PresencaUsuarioAula>() {
	      @Override
	      public int compare(PresencaUsuarioAula o1, PresencaUsuarioAula o2) {
	        return o1.getUsuario().getSobreNome().compareTo(o2.getUsuario().getSobreNome());
	      }
	    });			
	
	    
	}
	
	protected void cleanGrid() {		
		cellTable.setRowCount(0);
    	cellTable.redraw();		
	}	
	
	protected void populateComboBoxTipoPresenca() {
		
//		GWTServiceAvaliacao.Util.getInstance().getTipoAvaliacao(
	GWTServicePresenca.Util.getInstance().getTipoPresencas(
		
				new AsyncCallback<ArrayList<TipoPresenca>>() {

					@Override
					public void onFailure(Throwable caught) {
						mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
						mpDialogBoxWarning.setBodyText(txtConstants.presencaErroCarregarDados());
					}

					@Override
					public void onSuccess(ArrayList<TipoPresenca> list) {
						MpUtilClient.isRefreshRequired(list);
						for(TipoPresenca currentTipoPresenca : list){
							String strIdTipoAvaliacao = Integer.toString(currentTipoPresenca.getIdTipoPresenca());
							String strNomeTipoAvaliacao = currentTipoPresenca.getTipoPresenca();
							listaTipoPresenca.put(strIdTipoAvaliacao, strNomeTipoAvaliacao);						
						}
						initializeCellTable();
						populateGridUsuarios();
						
					}
				});
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

	
	public void filtrarCellTable(String strFiltro) {
		
		removeCellTableFilter();

		strFiltro = strFiltro.toUpperCase();

		if (!strFiltro.isEmpty()) {

			int i = 0;
			while (i < dataProvider.getList().size()) {

				String strPrimeiroNome = dataProvider.getList().get(i).getUsuario().getPrimeiroNome().toUpperCase();
				String strSobreNome = dataProvider.getList().get(i).getUsuario().getSobreNome().toUpperCase();

				String strJuntaTexto = strPrimeiroNome+ " " + strSobreNome;
				strJuntaTexto = strJuntaTexto.toUpperCase();

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

}
