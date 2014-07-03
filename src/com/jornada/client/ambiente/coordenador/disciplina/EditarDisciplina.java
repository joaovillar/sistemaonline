package com.jornada.client.ambiente.coordenador.disciplina;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

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
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
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
import com.jornada.client.classes.listBoxes.MpSelectionCurso;
import com.jornada.client.classes.listBoxes.MpSelectionPeriodo;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.cells.MpTextAreaEditCell;
import com.jornada.client.classes.widgets.dialog.MpConfirmDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpacePanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceDisciplina;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Disciplina;

public class EditarDisciplina extends VerticalPanel {

	private AsyncCallback<Boolean> callbackUpdateRow;
	private AsyncCallback<Boolean> callbackDelete;

	private CellTable<Disciplina> cellTable;
	private Column<Disciplina, String> nomeDisciplinaColumn;
	private Column<Disciplina, String> cargaHorariaDisciplinaColumn;
	private Column<Disciplina, String> descricaoColumn;
	private Column<Disciplina, String> objetivoColumn;
	private ListDataProvider<Disciplina> dataProvider = new ListDataProvider<Disciplina>();	
	
	private TextBox txtSearch;
	ArrayList<Disciplina> arrayListBackup = new ArrayList<Disciplina>();

	private MpSelectionCurso listBoxCurso;
	private MpSelectionPeriodo listBoxPeriodo;

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	
	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	
	TextConstants txtConstants;
	

	private TelaInicialDisciplina telaInicialDisciplina;

	public EditarDisciplina(TelaInicialDisciplina telaInicialDisciplina) {
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialDisciplina=telaInicialDisciplina;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);			

		Label lblCursoEdit = new Label(txtConstants.curso());
		Label lblPeriodoEdit = new Label(txtConstants.periodo());

		listBoxCurso = new MpSelectionCurso();
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());
		
		listBoxPeriodo = new MpSelectionPeriodo();	
		listBoxPeriodo.addChangeHandler(new MpPeriodoSelectionChangeHandler());


		Grid gridComboBox = new Grid(2, 4);
		gridComboBox.setCellSpacing(2);
		gridComboBox.setCellPadding(2);
		gridComboBox.setWidget(0, 0, lblCursoEdit);
		gridComboBox.setWidget(0, 1, listBoxCurso);
		gridComboBox.setWidget(0, 2, new InlineHTML("&nbsp;"));
		gridComboBox.setWidget(1, 0, lblPeriodoEdit);
		gridComboBox.setWidget(1, 1, listBoxPeriodo);
		gridComboBox.setWidget(1, 2, new InlineHTML("&nbsp;"));
		gridComboBox.setWidget(1, 3, mpPanelLoading);


		Label lblEmpty = new Label(txtConstants.disciplinaNenhum());
//		Label lblEmpty2 = new Label("Por favor, selecione a disciplina.");

		cellTable = new CellTable<Disciplina>(5,GWT.<CellTableStyle> create(CellTableStyle.class));
		cellTable.setWidth(Integer.toString(TelaInicialDisciplina.intWidthTable)+ "px");
		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);
		cellTable.setEmptyTableWidget(lblEmpty);

		// Add a selection model so we can select cells.
		final SelectionModel<Disciplina> selectionModel = new MultiSelectionModel<Disciplina>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<Disciplina> createCheckboxManager());

		dataProvider.addDataDisplay(cellTable);	
		
		MpSimplePager mpPager = new MpSimplePager();
		mpPager.setDisplay(cellTable);
		mpPager.setPageSize(15);			

		initTableColumns(selectionModel);
		
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize(Integer.toString(TelaInicialDisciplina.intWidthTable+30)+"px",Integer.toString(TelaInicialDisciplina.intHeightTable-130)+"px");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(cellTable);
		
		
		
		MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
		
		if (txtSearch == null) {
			txtSearch = new TextBox();
			txtSearch.setStyleName("design_text_boxes");
		}
		
		txtSearch.addKeyDownHandler(new EnterKeyPressHandler());
		btnFiltrar.addClickHandler(new ClickHandlerFiltrar());
		
		FlexTable flexTableFiltrar = new FlexTable();	
		flexTableFiltrar.setBorderWidth(2);
		flexTableFiltrar.setCellSpacing(3);
		flexTableFiltrar.setCellPadding(3);
		flexTableFiltrar.setBorderWidth(0);		
		flexTableFiltrar.setWidget(0, 0, mpPager);
		flexTableFiltrar.setWidget(0, 1, new MpSpacePanel());
		flexTableFiltrar.setWidget(0, 2, txtSearch);
		flexTableFiltrar.setWidget(0, 3, btnFiltrar);
		
		
		VerticalPanel vPanelEditGrid = new VerticalPanel();		
		vPanelEditGrid.add(gridComboBox);
//		vPanelEditGrid.add(mpPager);
		vPanelEditGrid.add(flexTableFiltrar);
		vPanelEditGrid.add(scrollPanel);


		/************************* Begin Callback's *************************/


		callbackUpdateRow = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {

				mpPanelLoading.setVisible(false);
				getTelaInicialDisciplina().getAssociarProfessorDisciplina().updateClientData();
				
			}

			public void onFailure(Throwable caught) {
				mpPanelLoading.setVisible(false);
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.disciplinaErroAtualizar());
				mpDialogBoxWarning.showDialog();
			}
		};

		callbackDelete = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {

				mpPanelLoading.setVisible(false);
				
				if (success == true) {
					populateGridDisciplina();
					// SC.say("Periodo removido com sucesso.");
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.disciplinaErroRemover());
					mpDialogBoxWarning.showDialog();
				}

			}

			public void onFailure(Throwable caught) {
				mpPanelLoading.setVisible(false);
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.disciplinaErroRemover());
				mpDialogBoxWarning.showDialog();

			}
		};
		

		/*********************** End Callbacks **********************/
		
		super.add(vPanelEditGrid);
		
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
				final Disciplina object = (Disciplina) context.getKey();
				@SuppressWarnings("unused")
				CloseHandler<PopupPanel> closeHandler;

				MpConfirmDialogBox confirmationDialog = new MpConfirmDialogBox(
						txtConstants.geralRemover(),txtConstants.disciplinaDesejaRemover(object.getNome()), txtConstants.geralSim(), txtConstants.geralNao(),

						closeHandler = new CloseHandler<PopupPanel>() {

							public void onClose(CloseEvent<PopupPanel> event) {

								MpConfirmDialogBox x = (MpConfirmDialogBox) event.getSource();

								if (x.primaryActionFired()) {

									//getServicePeriodoAsync().deletePeriodoRow(per.getIdPeriodo(),callbackDelete);
									GWTServiceDisciplina.Util.getInstance().deleteDisciplinaRow(object.getIdDisciplina(), callbackDelete);

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

	/**************** Begin Event Handlers *****************/
	
	private class MpCursoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
			listBoxPeriodo.populateComboBox(idCurso);
		}  
	}	
	
	private class MpPeriodoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			populateGridDisciplina();
		}
	}	

	/**************** End Event Handlers *****************/	
	
	
	/**************** Begin Populate methods*****************/		

	protected void populateGridDisciplina() {
		
		mpPanelLoading.setVisible(true);
		if (listBoxPeriodo.getSelectedIndex() == -1) {
			mpPanelLoading.setVisible(false);
			dataProvider.getList().clear();
		} else {

			int idPeriodo = Integer.parseInt(listBoxPeriodo.getValue(listBoxPeriodo.getSelectedIndex()));
			GWTServiceDisciplina.Util.getInstance().getDisciplinasPeloPeriodo(idPeriodo,
					new AsyncCallback<ArrayList<Disciplina>>() {

						@Override
						public void onFailure(Throwable caught) {
							mpPanelLoading.setVisible(false);
							mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
							mpDialogBoxWarning.setBodyText(txtConstants.disciplinaErroCarregar());
						}

						@Override
						public void onSuccess(ArrayList<Disciplina> list) {

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
	
	public void updateClientData(){
		listBoxCurso.populateComboBox();
	}


	public TelaInicialDisciplina getTelaInicialDisciplina() {
		return telaInicialDisciplina;
	}
	
	/**************** End Populate methods*****************/
	
	
	
	private void addCellTableData(ListDataProvider<Disciplina> dataProvider){
		
		 ListHandler<Disciplina> sortHandler = new ListHandler<Disciplina>(dataProvider.getList());
		 
		 cellTable.addColumnSortHandler(sortHandler);	

		 initSortHandler(sortHandler);
	
	}
	
	private void initTableColumns(final SelectionModel<Disciplina> selectionModel){
		
		nomeDisciplinaColumn = new Column<Disciplina, String>(new EditTextCell()) {
			@Override
			public String getValue(Disciplina object) {
				return object.getNome();
			}

		};
		nomeDisciplinaColumn.setFieldUpdater(new FieldUpdater<Disciplina, String>() {
			@Override
			public void update(int index, Disciplina object, String value) {
				if (FieldVerifier.isValidName(value)) {
				object.setNome(value);
				GWTServiceDisciplina.Util.getInstance().updateDisciplinaRow(object, callbackUpdateRow);
				}
				else{
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.disciplinaNome()));
					mpDialogBoxWarning.showDialog();
				}
			}
		});
		
		cargaHorariaDisciplinaColumn = new Column<Disciplina, String>(new EditTextCell()) {
			@Override
			public String getValue(Disciplina object) {
				return Integer.toString(object.getCargaHoraria());				
			}

		};
		cargaHorariaDisciplinaColumn.setFieldUpdater(new FieldUpdater<Disciplina, String>() {
			@Override
			public void update(int index, Disciplina object, String value) {
				int intCargaHoraria=0;				
				if (FieldVerifier.isValidInteger(value)) {
					intCargaHoraria = Integer.parseInt(value);
					object.setCargaHoraria(intCargaHoraria);
					GWTServiceDisciplina.Util.getInstance().updateDisciplinaRow(object, callbackUpdateRow);
				}
				else{
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.geralErroTipo(txtConstants.geralNumeroInteiro()));
					mpDialogBoxWarning.showDialog();
				}

			}
		});

		



		descricaoColumn = new Column<Disciplina, String>(new MpTextAreaEditCell(5,150)) {
//		descricaoColumn = new Column<Disciplina, String>(new ClickableTextCell()) {
			@Override
			public String getValue(Disciplina object) {
//				Window.alert("Test");
				return object.getDescricao();
			}
		};
		descricaoColumn.setFieldUpdater(new FieldUpdater<Disciplina, String>() {
			@Override
			public void update(int index, Disciplina object, String value) {
//				Window.alert("Test");
				object.setDescricao(value);
				GWTServiceDisciplina.Util.getInstance().updateDisciplinaRow(object, callbackUpdateRow);
			}
		});


		objetivoColumn = new Column<Disciplina, String>(new MpTextAreaEditCell(5,150)) {
			@Override
			public String getValue(Disciplina object) {
				return object.getObjetivo();
			}
		};
		objetivoColumn.setFieldUpdater(new FieldUpdater<Disciplina, String>() {
			@Override
			public void update(int index, Disciplina object, String value) {
				object.setObjetivo(value);
				GWTServiceDisciplina.Util.getInstance().updateDisciplinaRow(object, callbackUpdateRow);
			}
		});



		Column<Disciplina, String> removeColumn = new Column<Disciplina, String>(new MyImageCell()) {
			@Override
			public String getValue(Disciplina object) {
				return "images/delete.png";
			}
		};

		cellTable.addColumn(nomeDisciplinaColumn, txtConstants.disciplinaNome());
		cellTable.addColumn(cargaHorariaDisciplinaColumn, txtConstants.disciplinaCarga());
		cellTable.addColumn(descricaoColumn, txtConstants.disciplinaDescricao());
		cellTable.addColumn(objetivoColumn, txtConstants.disciplinaObjetivo());
		cellTable.addColumn(removeColumn, txtConstants.geralRemover());


		cellTable.getColumn(cellTable.getColumnIndex(nomeDisciplinaColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(cargaHorariaDisciplinaColumn)).setCellStyleNames("edit-cell");
//		cellTable.getColumn(cellTable.getColumnIndex(descricaoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(objetivoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(removeColumn)).setCellStyleNames("hand-over");				
		
	}
	
	
	public void initSortHandler(ListHandler<Disciplina> sortHandler){
		nomeDisciplinaColumn.setSortable(true);
	    sortHandler.setComparator(nomeDisciplinaColumn, new Comparator<Disciplina>() {
	      @Override
	      public int compare(Disciplina o1, Disciplina o2) {
	        return o1.getNome().compareTo(o2.getNome());
	      }
	    });		
	    
		cargaHorariaDisciplinaColumn.setSortable(true);
	    sortHandler.setComparator(cargaHorariaDisciplinaColumn, new Comparator<Disciplina>() {
	      @Override
	      public int compare(Disciplina o1, Disciplina o2) {	    	  
	    	  int primitive1 = o1.getCargaHoraria(), primitive2 = o2.getCargaHoraria();
	    	  Integer a = new Integer(primitive1);
	    	  Integer b = new Integer(primitive2);
	    	  return a.compareTo(b);
	      }
	    });	

	    descricaoColumn.setSortable(true);
	    sortHandler.setComparator(descricaoColumn, new Comparator<Disciplina>() {
	      @Override
	      public int compare(Disciplina o1, Disciplina o2) {	    	  
	    	  return o1.getDescricao().compareTo(o2.getDescricao());
	      }
	    });		    
	    
	    objetivoColumn.setSortable(true);
	    sortHandler.setComparator(objetivoColumn, new Comparator<Disciplina>() {
	      @Override
	      public int compare(Disciplina o1, Disciplina o2) {	    	  
	    	  return o1.getObjetivo().compareTo(o2.getObjetivo());
	      }
	    });		    
	    
	}
	

	
	
	/*******************************************************************************************************/	
	/*******************************************************************************************************/
	/***************************************BEGIN Filterting CellTable***************************************/	
		
	
	private class EnterKeyPressHandler implements KeyDownHandler {
		 public void onKeyDown(KeyDownEvent event) {
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

					String strNome = dataProvider.getList().get(i).getNome();
					String strCarga = Integer.toString(dataProvider.getList().get(i).getCargaHoraria());
					String strDescricao = dataProvider.getList().get(i).getDescricao();
					String strObjetivo = dataProvider.getList().get(i).getObjetivo();

					String strJuntaTexto = strNome.toUpperCase() + " " + strCarga.toUpperCase() + " " + strDescricao.toUpperCase() + " " + strObjetivo.toUpperCase();

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
