package com.jornada.client.ambiente.professor.ocorrencia;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionCursoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionDisciplinaAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionPeriodoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.suggestbox.MpListBoxPanelHelper;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.cells.MpStyledSelectionCell;
import com.jornada.client.classes.widgets.cells.MpTextAreaEditCell;
import com.jornada.client.classes.widgets.dialog.MpConfirmDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.classes.widgets.timepicker.MpTimePicker;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceOcorrencia;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Ocorrencia;
import com.jornada.shared.classes.utility.MpUtilClient;

public class EditarOcorrencia extends VerticalPanel {
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);

	private AsyncCallback<Boolean> callbackUpdateRow;
	private AsyncCallback<Boolean> callbackDelete;

	private CellTable<Ocorrencia> cellTable;
	private Column<Ocorrencia, String> assuntoColumn;
	private Column<Ocorrencia, String> descricaoColumn;
	private Column<Ocorrencia, Date> dataColumn;
	private Column<Ocorrencia, String> horaColumn;
	private ListDataProvider<Ocorrencia> dataProvider = new ListDataProvider<Ocorrencia>();	
	
	private TextBox txtSearch;
	private ArrayList<Ocorrencia> arrayListBackup = new ArrayList<Ocorrencia>();

	private MpSelectionCursoAmbienteProfessor listBoxCurso;
	private MpSelectionPeriodoAmbienteProfessor listBoxPeriodo;	
	private MpSelectionDisciplinaAmbienteProfessor listBoxDisciplina;
//	private MpSelectionConteudoProgramatico listBoxConteudoProgramatico;
	
	MpListBoxPanelHelper mpHelperCurso = new  MpListBoxPanelHelper();
	
	private int intSelectedIndexToDelete;
	
	private MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	private MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	private MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	
	private LinkedHashMap<String, String> listaHora = new LinkedHashMap<String, String>();
	
	VerticalPanel vPanelEditarOcorrencia;
	VerticalPanel vPanelEditarTabela;
	VerticalPanel vPanelEditarDetalhes;
	AdicionarOcorrencia adicionarOcorrencia;
	
	
	private TelaInicialProfessorOcorrencia telaInicialProfessorOcorrencia;

	public EditarOcorrencia(TelaInicialProfessorOcorrencia telaInicialProfessorOcorrencia) {
		
		this.telaInicialProfessorOcorrencia=telaInicialProfessorOcorrencia;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);	
		
		vPanelEditarOcorrencia = new VerticalPanel();
		
		vPanelEditarTabela = new VerticalPanel();
		vPanelEditarTabela.setWidth("100%");
		vPanelEditarTabela.add(abrirTelaEditar());

		
		adicionarOcorrencia = new AdicionarOcorrencia(this.telaInicialProfessorOcorrencia, EnumOcorrencia.EDITAR);
		adicionarOcorrencia.criarTelaEditar(EditarOcorrencia.this);
		vPanelEditarDetalhes = new VerticalPanel();
		vPanelEditarDetalhes.setWidth("100%");
		vPanelEditarDetalhes.add(adicionarOcorrencia);
		vPanelEditarDetalhes.setVisible(false);
		
		
		vPanelEditarOcorrencia.add(vPanelEditarTabela);
		vPanelEditarOcorrencia.add(vPanelEditarDetalhes);
		vPanelEditarOcorrencia.setWidth("100%");
		vPanelEditarOcorrencia.setBorderWidth(0);

		this.setWidth("100%");
		super.add(vPanelEditarOcorrencia);
		
	}
	
	public VerticalPanel abrirTelaEditar(){
		Label lblCurso = new Label(txtConstants.curso());
		Label lblPeriodo = new Label(txtConstants.periodo());
		Label lblDisciplina = new Label(txtConstants.disciplina());
//		Label lblConteudoProgramatico = new Label(txtConstants.conteudoProgramatico());

		listBoxCurso = new MpSelectionCursoAmbienteProfessor(telaInicialProfessorOcorrencia.getMainView().getUsuarioLogado());
		listBoxPeriodo = new MpSelectionPeriodoAmbienteProfessor(telaInicialProfessorOcorrencia.getMainView().getUsuarioLogado());
		listBoxDisciplina = new MpSelectionDisciplinaAmbienteProfessor(telaInicialProfessorOcorrencia.getMainView().getUsuarioLogado());		
//		listBoxConteudoProgramatico = new MpSelectionConteudoProgramatico();		
		
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());
		listBoxPeriodo.addChangeHandler(new MpPeriodoSelectionChangeHandler());		
		listBoxDisciplina.addChangeHandler(new MpDisciplinaSelectionChangeHandler());		
//		listBoxConteudoProgramatico.addChangeHandler(new MpConteudoProgramaticoSelectionChangeHandler());

		Grid gridComboBox = new Grid(4, 4);
		gridComboBox.setCellSpacing(2);
		gridComboBox.setCellPadding(2);
		{
			int row=0;
			gridComboBox.setWidget(row, 0, lblCurso);
			gridComboBox.setWidget(row, 1, listBoxCurso);
			gridComboBox.setWidget(row++, 2, mpHelperCurso);
			gridComboBox.setWidget(row, 0, lblPeriodo);
			gridComboBox.setWidget(row, 1, listBoxPeriodo);
			gridComboBox.setWidget(row++, 2, new InlineHTML("&nbsp;"));
			gridComboBox.setWidget(row, 0, lblDisciplina);
			gridComboBox.setWidget(row, 1, listBoxDisciplina);
			gridComboBox.setWidget(row, 2, new InlineHTML("&nbsp;"));
//			gridComboBox.setWidget(row, 0, lblConteudoProgramatico);
//			gridComboBox.setWidget(row, 1, listBoxConteudoProgramatico);
//			gridComboBox.setWidget(row, 2, new InlineHTML("&nbsp;"));
			gridComboBox.setWidget(row++, 3, mpPanelLoading);			
		}

//		Label lblEmpty1 = new Label("Nenhum Tópico associado a este Conteúdo Programático.");
		Label lblEmpty = new Label(txtConstants.ocorrenciaSelecionarConteudo());

		cellTable = new CellTable<Ocorrencia>(5,GWT.<CellTableStyle> create(CellTableStyle.class));
//		cellTable.setWidth(Integer.toString(TelaInicialProfessorOcorrencia.intWidthTable)+ "px");
		cellTable.setWidth("100%");
		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);
		cellTable.setLoadingIndicator(lblEmpty);

		// Add a selection model so we can select cells.
		final SelectionModel<Ocorrencia> selectionModel = new MultiSelectionModel<Ocorrencia>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<Ocorrencia> createCheckboxManager());
		initTableColumns(selectionModel);

		dataProvider.addDataDisplay(cellTable);	
		
		MpSimplePager mpPager = new MpSimplePager();
		mpPager.setDisplay(cellTable);
		mpPager.setPageSize(15);
		
		MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
		
		if (txtSearch == null) {
			txtSearch = new TextBox();
			txtSearch.setStyleName("design_text_boxes");
		}
		
		txtSearch.addKeyUpHandler(new EnterKeyUpHandler());
		btnFiltrar.addClickHandler(new ClickHandlerFiltrar());
		
		FlexTable flexTableFiltrar = new FlexTable();	
		flexTableFiltrar.setBorderWidth(2);
		flexTableFiltrar.setCellSpacing(3);
		flexTableFiltrar.setCellPadding(3);
		flexTableFiltrar.setBorderWidth(0);		
		flexTableFiltrar.setWidget(0, 0, mpPager);
		flexTableFiltrar.setWidget(0, 1, new MpSpaceVerticalPanel());
		flexTableFiltrar.setWidget(0, 2, txtSearch);
		flexTableFiltrar.setWidget(0, 3, btnFiltrar);		
		
		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(TelaInicialProfessorOcorrencia.intWidthTable+30)+"px",Integer.toString(TelaInicialProfessorOcorrencia.intHeightTable-180)+"px");
		scrollPanel.setWidth("100%");
		scrollPanel.setHeight(Integer.toString(TelaInicialProfessorOcorrencia.intHeightTable-180)+"px");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(cellTable);			
		
		
		VerticalPanel vPanelEdit = new VerticalPanel();		
		vPanelEdit.add(gridComboBox);
//		vPanelEdit.add(mpPager);
		vPanelEdit.add(flexTableFiltrar);
		vPanelEdit.add(scrollPanel);
		vPanelEdit.setWidth("100%");
		vPanelEdit.setBorderWidth(0);


		/************************* Begin Callback's *************************/

		callbackUpdateRow = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {

			}

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.ocorrenciaErroAtualizar());
				mpDialogBoxWarning.showDialog();
			}
		};

		callbackDelete = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {

				if (success == true) {
//					populateGridOcorrencia();
					dataProvider.getList().remove(intSelectedIndexToDelete);
					dataProvider.refresh();	
					telaInicialProfessorOcorrencia.updateVisualizarOcorrenciaPopulateGrid();
					telaInicialProfessorOcorrencia.updateAprovarOcorrenciaPopulateGrid();
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.ocorrenciaErroAtualizar());
					mpDialogBoxWarning.showDialog();
				}

			}

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.ocorrenciaErroAtualizar());
				mpDialogBoxWarning.showDialog();

			}
		};
		/*********************** End Callbacks **********************/

		//vPanelEditGrid.add(vPanelTelaEditar);
		return vPanelEdit;
	}

	/**************** Begin Event Handlers *****************/

	private class MpCursoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {	
		    mpHelperCurso.populateSuggestBox(listBoxCurso);
			int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
			listBoxPeriodo.populateComboBox(idCurso);
		}  
	}	
	
	private class MpPeriodoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int index = listBoxPeriodo.getSelectedIndex();
			if(index==-1){
				listBoxDisciplina.clear();
//				listBoxConteudoProgramatico.clear();
				dataProvider.getList().clear();
			}
			else{
				int idPeriodo = Integer.parseInt(listBoxPeriodo.getValue(index));
				listBoxDisciplina.populateComboBox(idPeriodo);				
			}
		}  
	}
	
	private class MpDisciplinaSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
		    populateGridOcorrencia();
//			int index = listBoxDisciplina.getSelectedIndex();
//			if(index==-1){
////				listBoxConteudoProgramatico.clear();
//				dataProvider.getList().clear();
//			}
//			else{
//				int idDisciplina= Integer.parseInt(listBoxDisciplina.getValue(index));
//				listBoxConteudoProgramatico.populateComboBox(idDisciplina);				
//			}
		}  
	}
	
//	private class MpConteudoProgramaticoSelectionChangeHandler implements ChangeHandler{
//		
//		public void onChange(ChangeEvent event){
//			populateGridOcorrencia();
//		}
//	}
	
	/**************** End Event Handlers *****************/
	
	/**************** Begin Populate methods*****************/	
	
	protected void populateGridOcorrencia() {
		
		mpPanelLoading.setVisible(true);
		
		int indexDisciplina = listBoxDisciplina.getSelectedIndex();
		
		if (indexDisciplina == -1 ) {
			mpPanelLoading.setVisible(false);
			dataProvider.getList().clear();
		} 
		else{			
			int idDisciplina = Integer.parseInt(listBoxDisciplina.getValue(indexDisciplina));
//			GWTServiceTopico.Util.getInstance().getTopicoPeloConteudoProgramatico(idConteudoProgramatico,
			GWTServiceOcorrencia.Util.getInstance().getOcorrenciasPeloConteudoProgramatico(idDisciplina,
					
					new AsyncCallback<ArrayList<Ocorrencia>>() {

						@Override
						public void onFailure(Throwable caught) {
							mpPanelLoading.setVisible(false);
							mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
							mpDialogBoxWarning.setBodyText(txtConstants.ocorrenciaErroCarregar());
						}

						@Override
						public void onSuccess(ArrayList<Ocorrencia> list) {
							MpUtilClient.isRefreshRequired(list);
							mpPanelLoading.setVisible(false);
							dataProvider.getList().clear();
							arrayListBackup.clear();
							cellTable.setRowCount(0);
							for (int i = 0; i < list.size(); i++) {
								dataProvider.getList().add(list.get(i));
								arrayListBackup.add(list.get(i));
							}
							addCellTableData(dataProvider);
							cellTable.redraw();
						}
					});					
		}
	}
	
	/**************** End Populate methods*****************/
		
	private class MyImageCellEdit extends ImageCell {

		@Override
		public Set<String> getConsumedEvents() {
			Set<String> consumedEvents = new HashSet<String>();
			consumedEvents.add("click");
			return consumedEvents;
		}

		@Override
		public void onBrowserEvent(Context context, Element parent, String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
			
			switch (DOM.eventGetType((Event) event)) {
			case Event.ONCLICK:
				final Ocorrencia object = (Ocorrencia) context.getKey();
								
//				telaInicialComunicado.openFormularioComunicadoParaAtualizar(object);
				vPanelEditarTabela.setVisible(false);
				
				//AdicionarOcorrencia add = new AdicionarOcorrencia(null); 				
				OcorrenciaAux ocorrenciaAux = new OcorrenciaAux();
				ocorrenciaAux.setIdCurso(Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex())));
				ocorrenciaAux.setIdPeriodo(Integer.parseInt(listBoxPeriodo.getValue(listBoxPeriodo.getSelectedIndex())));
				ocorrenciaAux.setIdDisciplina(Integer.parseInt(listBoxDisciplina.getValue(listBoxDisciplina.getSelectedIndex())));
//				ocorrenciaAux.setIdConteudoProgramatico(Integer.parseInt(listBoxConteudoProgramatico.getValue(listBoxConteudoProgramatico.getSelectedIndex())));
				
				ocorrenciaAux.setNomeCurso(listBoxCurso.getItemText(listBoxCurso.getSelectedIndex()));
				ocorrenciaAux.setNomePeriodo(listBoxPeriodo.getItemText(listBoxPeriodo.getSelectedIndex()));
				ocorrenciaAux.setNomeDisciplina(listBoxDisciplina.getItemText(listBoxDisciplina.getSelectedIndex()));
//				ocorrenciaAux.setNomeConteudoProgramatico(listBoxConteudoProgramatico.getItemText(listBoxConteudoProgramatico.getSelectedIndex()));
				
				ocorrenciaAux.setOcorrencia(object);

				adicionarOcorrencia.popularCampos(ocorrenciaAux);
				
				vPanelEditarDetalhes.setVisible(true);
				break;

			default:
				Window.alert("Test default");
				break;
			}
		}

	}	
	
	private class MyImageCell extends ImageCell {

		@Override
		public Set<String> getConsumedEvents() {
			Set<String> consumedEvents = new HashSet<String>();
			consumedEvents.add("click");
			return consumedEvents;
		}

		@Override
		public void onBrowserEvent(Context context, Element parent,
				String value, NativeEvent event,
				ValueUpdater<String> valueUpdater) {
			switch (DOM.eventGetType((Event) event)) {
			case Event.ONCLICK:
				final Ocorrencia object = (Ocorrencia) context.getKey();
				intSelectedIndexToDelete = context.getIndex();

				@SuppressWarnings("unused")
				CloseHandler<PopupPanel> closeHandler;

				MpConfirmDialogBox confirmationDialog = new MpConfirmDialogBox(
						txtConstants.geralRemover(), txtConstants.ocorrenciaDesejaRemover(object.getAssunto()), txtConstants.geralSim(), txtConstants.geralNao(),

						closeHandler = new CloseHandler<PopupPanel>() {

							public void onClose(CloseEvent<PopupPanel> event) {

								MpConfirmDialogBox x = (MpConfirmDialogBox) event.getSource();

								if (x.primaryActionFired()) {

//									GWTServiceTopico.Util.getInstance().deleteTopicoRow(object.getIdTopico(), callbackDelete);
									GWTServiceOcorrencia.Util.getInstance().deleteOcorrenciaRow(object.getIdOcorrencia(), callbackDelete);


								}
							}
						}

				);
				confirmationDialog.paint();
				break;

			default:
				Window.alert("Test default");
				break;
			}
		}

	}	
	
	
	public void updateClientData(){
		listBoxCurso.populateComboBox(this.telaInicialProfessorOcorrencia.getMainView().getUsuarioLogado());
	}		
	
	
	
	private void addCellTableData(ListDataProvider<Ocorrencia> dataProvider){
		
		 ListHandler<Ocorrencia> sortHandler = new ListHandler<Ocorrencia>(dataProvider.getList());
		 
		 cellTable.addColumnSortHandler(sortHandler);	

		 initSortHandler(sortHandler);
	
	}


	private void initTableColumns(final SelectionModel<Ocorrencia> selectionModel) {
		
		
		assuntoColumn = new Column<Ocorrencia, String>(new EditTextCell()) {
			@Override
			public String getValue(Ocorrencia object) {
				return object.getAssunto();
			}

		};
		assuntoColumn.setFieldUpdater(new FieldUpdater<Ocorrencia, String>() {
			@Override
			public void update(int index, Ocorrencia object, String value) {
				// Called when the user changes the value.

				if (FieldVerifier.isValidName(value)) {					
					object.setAssunto(value);
					GWTServiceOcorrencia.Util.getInstance().AtualizarOcorrencia(object, callbackUpdateRow);
				}else{
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.ocorrencia()));
					mpDialogBoxWarning.showDialog();
				}					
			}
		});
		

		descricaoColumn = new Column<Ocorrencia, String>(new MpTextAreaEditCell(5,150)) {
			@Override
			public String getValue(Ocorrencia object) {
				return object.getDescricao();
			}
		};
		descricaoColumn.setFieldUpdater(new FieldUpdater<Ocorrencia, String>() {
			@Override
			public void update(int index, Ocorrencia object, String value) {
				// Called when the user changes the value.
				object.setDescricao(value);
				GWTServiceOcorrencia.Util.getInstance().AtualizarOcorrencia(object, callbackUpdateRow);
			}
		});
		
		dataColumn = new Column<Ocorrencia, Date>(new DatePickerCell()) {
			@Override
			public Date getValue(Ocorrencia object) {
//				return MpUtilClient.convertStringToDate(object.getData());
				return object.getData();
			}
		};
		dataColumn.setFieldUpdater(new FieldUpdater<Ocorrencia, Date>() {
			@Override
			public void update(int index, Ocorrencia object, Date value) {
				// Called when the user changes the value.
//				object.setData(MpUtilClient.convertDateToString(value));
//				GWTServiceAvaliacao.Util.getInstance().updateRow(object,callbackUpdateRow);
				object.setData(value);
				GWTServiceOcorrencia.Util.getInstance().AtualizarOcorrencia(object, callbackUpdateRow);
			}
		});	
		
		MpTimePicker mpTimePicker = new MpTimePicker(7, 24);
		for(int i=0;i<mpTimePicker.getItemCount();i++){
			listaHora.put(mpTimePicker.getValue(i), mpTimePicker.getItemText(i));
		}	
		
	    MpStyledSelectionCell horaCell = new MpStyledSelectionCell(listaHora,"design_text_boxes");
	    
	    horaColumn = new Column<Ocorrencia, String>(horaCell) {
	      @Override
			public String getValue(Ocorrencia object) {
//				String strHora = MpUtilClient.convertTimeToString(object.getHora());
//				return strHora;
	    	  return object.getHora();
			}
	    };
	    horaColumn.setFieldUpdater(new FieldUpdater<Ocorrencia, String>() {
			@Override
			public void update(int index, Ocorrencia object, String value) {
//				object.setHora(MpUtilClient.convertStringToTime(value));
				object.setHora(value);
				GWTServiceOcorrencia.Util.getInstance().AtualizarOcorrencia(object, callbackUpdateRow);
			}
		});		

		Column<Ocorrencia, String> editColumn = new Column<Ocorrencia, String>(new MyImageCellEdit()) {
			@Override
			public String getValue(Ocorrencia object) {
				return "images/comment_edit.png";
			}
		};		    

		Column<Ocorrencia, String> removeColumn = new Column<Ocorrencia, String>(new MyImageCell()) {
			@Override
			public String getValue(Ocorrencia object) {
				return "images/delete.png";
			}
		};


		cellTable.addColumn(assuntoColumn, txtConstants.ocorrencia());
		cellTable.addColumn(descricaoColumn, txtConstants.ocorrenciaDescricao());
		cellTable.addColumn(dataColumn, txtConstants.ocorrenciaData());
		cellTable.addColumn(horaColumn, txtConstants.ocorrenciaHora());
		cellTable.addColumn(editColumn, txtConstants.ocorrenciaEditarDetalhes());
		cellTable.addColumn(removeColumn, txtConstants.geralRemover());
		cellTable.setColumnWidth(editColumn, "100px");
		cellTable.setColumnWidth(removeColumn, "70px");

		cellTable.getColumn(cellTable.getColumnIndex(assuntoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(descricaoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(editColumn)).setCellStyleNames("hand-over");		
		cellTable.getColumn(cellTable.getColumnIndex(removeColumn)).setCellStyleNames("hand-over");		
		
		
	}

	public void initSortHandler(ListHandler<Ocorrencia> sortHandler) {
		
		
		assuntoColumn.setSortable(true);
	    sortHandler.setComparator(assuntoColumn, new Comparator<Ocorrencia>() {
	      @Override
	      public int compare(Ocorrencia o1, Ocorrencia o2) {
	        return o1.getAssunto().compareTo(o2.getAssunto());
	      }
	    });	
	    
	    descricaoColumn.setSortable(true);
	    sortHandler.setComparator(descricaoColumn, new Comparator<Ocorrencia>() {
	      @Override
	      public int compare(Ocorrencia o1, Ocorrencia o2) {
	        return o1.getDescricao().compareTo(o2.getDescricao());
	      }
	    });		  
	    
	    dataColumn.setSortable(true);
	    sortHandler.setComparator(dataColumn, new Comparator<Ocorrencia>() {
	      @Override
	      public int compare(Ocorrencia o1, Ocorrencia o2) {
	        return o1.getData().compareTo(o2.getData());
	      }
	    });	
	    
	    horaColumn.setSortable(true);
	    sortHandler.setComparator(horaColumn, new Comparator<Ocorrencia>() {
	      @Override
	      public int compare(Ocorrencia o1, Ocorrencia o2) {
	        return o1.getHora().compareTo(o2.getHora());
	      }
	    });	
	    
	    
	    
	}
	
	
	/*******************************************************************************************************/	
	/*******************************************************************************************************/
	/***************************************BEGIN Filterting CellTable***************************************/
	
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

					String strAssunto = dataProvider.getList().get(i).getAssunto();			
					String strDescricao = dataProvider.getList().get(i).getDescricao();
					
//					Date date = MpUtilClient.convertStringToDate(dataProvider.getList().get(i).getData());					
//					String strData = MpUtilClient.convertDateToString(date, "EEEE, MMMM dd, yyyy");
					String strData = MpUtilClient.convertDateToString(dataProvider.getList().get(i).getData(), "EEEE, MMMM dd, yyyy");

					String strHora = dataProvider.getList().get(i).getHora();
					String strJuntaTexto = strAssunto.toUpperCase() + " " + strDescricao.toUpperCase() ;
					strJuntaTexto +=  " " + strData.toUpperCase() + " " + strHora.toUpperCase();

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
	/***************************************BEGIN Filterting CellTable***************************************/	
	

}


