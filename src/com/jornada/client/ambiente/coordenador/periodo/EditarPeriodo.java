package com.jornada.client.ambiente.coordenador.periodo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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
import com.jornada.client.classes.listBoxes.MpSelectionCurso;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpDatePickerCell;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.cells.MpTextAreaEditCell;
import com.jornada.client.classes.widgets.dialog.MpConfirmDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServicePeriodo;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.utility.MpUtilClient;

public class EditarPeriodo extends VerticalPanel {

	private AsyncCallback<Boolean> callbackUpdateRow;
	private AsyncCallback<Boolean> callbackDeletePeriodo;

	private CellTable<Periodo> cellTable;
	private ListDataProvider<Periodo> dataProvider = new ListDataProvider<Periodo>();
	private Column<Periodo, String> nomePeriodoColumn;
	private Column<Periodo, String> descricaoColumn;
	private Column<Periodo, String> objetivoColumn;
	private Column<Periodo, String> pesoColumn;
	private Column<Periodo, Date> dataInicialColumn;
	private Column<Periodo, Date> dataFinalColumn;

	private MpSelectionCurso listBoxCurso;
	
	private TextBox txtSearch;
	ArrayList<Periodo> arrayListBackup = new ArrayList<Periodo>();


	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	
	TextConstants txtConstants;
	
	@SuppressWarnings("unused")
	private TelaInicialPeriodo telaInicialPeriodo;
	
	private static EditarPeriodo uniqueInstance;
	
	public static EditarPeriodo getInstance(TelaInicialPeriodo telaInicialPeriodo) {

		if(uniqueInstance==null){
			uniqueInstance = new EditarPeriodo(telaInicialPeriodo);
		}		
		
		return uniqueInstance;
	}

	private EditarPeriodo(TelaInicialPeriodo telaInicialPeriodo) {
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialPeriodo=telaInicialPeriodo;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);		
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);		

		Label lblCursoEdit = new Label(txtConstants.curso());

		listBoxCurso = new MpSelectionCurso(true);
		listBoxCurso.setWidth("250px");
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());

//		MpImageButton btnSearch = new MpImageButton("Filtrar","images/magnifier.png");
//		btnSearch.addClickHandler(new ClickHandlerFilter());

		Grid gridComboBox = new Grid(1, 6);
		gridComboBox.setCellSpacing(2);
		gridComboBox.setCellPadding(2);
		{
//			gridComboBox.setWidget(0, 0, new InlineHTML("&nbsp;"));
			gridComboBox.setWidget(0, 0, lblCursoEdit);
			gridComboBox.setWidget(0, 1, listBoxCurso);
			gridComboBox.setWidget(0, 2, new InlineHTML("&nbsp;"));
//			gridComboBox.setWidget(0, 3, btnSearch);
//			gridComboBox.setWidget(0, 4, new InlineHTML("&nbsp;"));
			gridComboBox.setWidget(0, 3, mpPanelLoading);
			
		}

		Label lblEmpty = new Label(txtConstants.periodoNenhum());

		cellTable = new CellTable<Periodo>(5,GWT.<CellTableStyle> create(CellTableStyle.class));
//		cellTable.setWidth(Integer.toString(TelaInicialPeriodo.intWidthTable)+ "px");
		cellTable.setWidth("100%");
		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);
		cellTable.setEmptyTableWidget(lblEmpty);
		
		dataProvider.addDataDisplay(cellTable);	
		
		final SelectionModel<Periodo> selectionModel = new MultiSelectionModel<Periodo>(Periodo.KEY_PROVIDER);
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<Periodo> createCheckboxManager());

		initTableColumns(selectionModel);			
		
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
		flexTableFiltrar.setCellSpacing(3);
		flexTableFiltrar.setCellPadding(3);
		flexTableFiltrar.setBorderWidth(0);		
		flexTableFiltrar.setWidget(0, 0, mpPager);
		flexTableFiltrar.setWidget(0, 1, new MpSpaceVerticalPanel());
		flexTableFiltrar.setWidget(0, 2, txtSearch);
		flexTableFiltrar.setWidget(0, 3, btnFiltrar);		
		
		
		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(TelaInicialPeriodo.intWidthTable+20)+"px",Integer.toString(TelaInicialPeriodo.intHeightTable-110)+"px");
		scrollPanel.setHeight(Integer.toString(TelaInicialPeriodo.intHeightTable-110)+"px");
		scrollPanel.setWidth("100%");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(cellTable);
		

		VerticalPanel vPanelEditGrid = new VerticalPanel();	
		vPanelEditGrid.add(gridComboBox);
//		vPanelEditGrid.add(mpPager);
		vPanelEditGrid.add(flexTableFiltrar);
		vPanelEditGrid.add(scrollPanel);
		vPanelEditGrid.setWidth("100%");



		/************************* Begin Callback's *************************/


		
		
		callbackUpdateRow = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {
				mpPanelLoading.setVisible(false);	
				if(success==false){
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.geralErroAtualizar(txtConstants.periodo())+" "+txtConstants.geralRegarregarPagina());
					mpDialogBoxWarning.showDialog();					
				}
			}

			public void onFailure(Throwable caught) {
				mpPanelLoading.setVisible(false);	
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.geralErroAtualizar(txtConstants.periodo()));
				mpDialogBoxWarning.showDialog();
			}
		};

		callbackDeletePeriodo = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {
				mpPanelLoading.setVisible(false);	
				if (success == true) {
					populateGrid();
					// SC.say("Periodo removido com sucesso.");
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.geralErroRemover(txtConstants.periodo()));
					mpDialogBoxWarning.showDialog();
				}

			}

			public void onFailure(Throwable caught) {
				mpPanelLoading.setVisible(false);	
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.geralErroRemover(txtConstants.periodo()));
				mpDialogBoxWarning.showDialog();

			}
		};

		/*********************** End Callbacks **********************/

		/******** Begin Populate ********/
//		populateCursoComboBox();
		/******** End Populate ********/
		
		setWidth("100%");
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
				final Periodo per = (Periodo) context.getKey();
				@SuppressWarnings("unused")
				CloseHandler<PopupPanel> closeHandler;

				MpConfirmDialogBox confirmationDialog = new MpConfirmDialogBox(
						txtConstants.geralRemover(),txtConstants.periodoDesejaRemover(per.getNomePeriodo()), txtConstants.geralSim(), txtConstants.geralNao(),

						closeHandler = new CloseHandler<PopupPanel>() {

							public void onClose(CloseEvent<PopupPanel> event) {

								MpConfirmDialogBox x = (MpConfirmDialogBox) event.getSource();

								if (x.primaryActionFired()) {

									GWTServicePeriodo.Util.getInstance().deletePeriodoRow(per.getIdPeriodo(),callbackDeletePeriodo);

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


	protected void populateGrid() {
		mpPanelLoading.setVisible(true);	
		int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
		GWTServicePeriodo.Util.getInstance().getPeriodosPeloCurso(idCurso,
		
				new AsyncCallback<ArrayList<Periodo>>() {

					@Override
					public void onFailure(Throwable caught) {
						mpPanelLoading.setVisible(false);	
						mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
						mpDialogBoxWarning.setBodyText(txtConstants.geralErroCarregar(txtConstants.periodo()));
					}

					@Override
					public void onSuccess(ArrayList<Periodo> list) {
						MpUtilClient.isRefreshRequired(list);
						mpPanelLoading.setVisible(false);	
						dataProvider.getList().clear();
						arrayListBackup.clear();
						cellTable.setRowCount(0);
						for(int i=0;i<list.size();i++){
							dataProvider.getList().add(list.get(i));
							arrayListBackup.add(list.get(i));
						}
						addCellTableData(dataProvider);
						cellTable.redraw();								


					}
				});
	}
	
	private class MpCursoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
				populateGrid();			
		}	  
	}

	public void updateClientData() {
		listBoxCurso.populateComboBox();
	}

	
	private void addCellTableData(ListDataProvider<Periodo> dataProvider){
		
		 ListHandler<Periodo> sortHandler = new ListHandler<Periodo>(dataProvider.getList());
		 
		 cellTable.addColumnSortHandler(sortHandler);	

		 initSortHandler(sortHandler);
	
	}	
		
	private void initTableColumns(final SelectionModel<Periodo> selectionModel){
		
		nomePeriodoColumn = new Column<Periodo, String>(new EditTextCell()) {
			@Override
			public String getValue(Periodo periodo) {
				return periodo.getNomePeriodo();
			}

		};
		
		nomePeriodoColumn.setFieldUpdater(new FieldUpdater<Periodo, String>() {
			@Override
			public void update(int index, Periodo periodo, String value) {
				// Called when the user changes the value.
				if (FieldVerifier.isValidName(value)) {
					periodo.setNomePeriodo(value);
					GWTServicePeriodo.Util.getInstance().updatePeriodoRow(periodo, callbackUpdateRow);
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.periodoNome()));
					mpDialogBoxWarning.showDialog();

				}
			}
		});


		descricaoColumn = new Column<Periodo, String>(new MpTextAreaEditCell(5,150)) {
			@Override
			public String getValue(Periodo periodo) {
				return periodo.getDescricao();
			}
		};
		descricaoColumn.setFieldUpdater(new FieldUpdater<Periodo, String>() {
			@Override
			public void update(int index, Periodo periodo, String value) {
				// Called when the user changes the value.
				periodo.setDescricao(value);
				GWTServicePeriodo.Util.getInstance().updatePeriodoRow(periodo,callbackUpdateRow);
			}
		});


		objetivoColumn = new Column<Periodo, String>(new MpTextAreaEditCell(5,150)) {
			@Override
			public String getValue(Periodo periodo) {
				return periodo.getObjetivo();
			}
		};
		objetivoColumn.setFieldUpdater(new FieldUpdater<Periodo, String>() {
			@Override
			public void update(int index, Periodo periodo, String value) {
				// Called when the user changes the value.
				periodo.setObjetivo(value);
				GWTServicePeriodo.Util.getInstance().updatePeriodoRow(periodo,callbackUpdateRow);
			}
		});
		
		
        pesoColumn = new Column<Periodo, String>(new EditTextCell()) {
            @Override
            public String getValue(Periodo periodo) {
                return periodo.getPeso();
            }

        };
        pesoColumn.setFieldUpdater(new FieldUpdater<Periodo, String>() {
            @Override
            public void update(int index, Periodo periodo, String value) {
                // Called when the user changes the value.
                periodo.setPeso(value);
                GWTServicePeriodo.Util.getInstance().updatePeriodoRow(periodo,callbackUpdateRow);
            }
        });		

		dataInicialColumn = new Column<Periodo, Date>(new MpDatePickerCell()) {
			@Override
			public Date getValue(Periodo object) {
				return object.getDataInicial();
			}
		};
		dataInicialColumn.setFieldUpdater(new FieldUpdater<Periodo, Date>() {
			@Override
			public void update(int index, Periodo periodo, Date value) {
				// Called when the user changes the value.
				periodo.setDataInicial(value);
				GWTServicePeriodo.Util.getInstance().updatePeriodoRow(periodo,callbackUpdateRow);
			}
		});

		dataFinalColumn = new Column<Periodo, Date>(new MpDatePickerCell()) {
			@Override
			public Date getValue(Periodo object) {
				return object.getDataFinal();
			}
		};
		dataFinalColumn.setFieldUpdater(new FieldUpdater<Periodo, Date>() {
			@Override
			public void update(int index, Periodo periodo, Date value) {
				// Called when the user changes the value.
				periodo.setDataFinal(value);
				GWTServicePeriodo.Util.getInstance().updatePeriodoRow(periodo,callbackUpdateRow);
			}
		});

		Column<Periodo, String> removeColumn = new Column<Periodo, String>(new MyImageCell()) {
			@Override
			public String getValue(Periodo object) {
				return "images/delete.png";
			}
		};

		cellTable.addColumn(nomePeriodoColumn, txtConstants.periodoNome());
		cellTable.addColumn(descricaoColumn, txtConstants.periodoDescricao());
		cellTable.addColumn(objetivoColumn, txtConstants.periodoObjetivo());
		cellTable.addColumn(pesoColumn, "Peso");
		cellTable.addColumn(dataInicialColumn, txtConstants.periodoDataInicial());
		cellTable.addColumn(dataFinalColumn, txtConstants.periodoDataFinal());
		cellTable.addColumn(removeColumn, txtConstants.geralRemover());

		// Make the name column sortable.
//		nomePeriodoColumn.setSortable(true);		

		cellTable.getColumn(cellTable.getColumnIndex(nomePeriodoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(descricaoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(objetivoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(pesoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(removeColumn)).setCellStyleNames("hand-over");		
		
	}	
		
	public void initSortHandler(ListHandler<Periodo> sortHandler){
		nomePeriodoColumn.setSortable(true);
	    sortHandler.setComparator(nomePeriodoColumn, new Comparator<Periodo>() {
	      @Override
	      public int compare(Periodo o1, Periodo o2) {
	        return o1.getNomePeriodo().compareTo(o2.getNomePeriodo());
	      }
	    });		
	    
	    descricaoColumn.setSortable(true);
		sortHandler.setComparator(descricaoColumn, new Comparator<Periodo>() {
	      @Override
	      public int compare(Periodo o1, Periodo o2) {
	        return o1.getDescricao().compareTo(o2.getDescricao());
	      }
	    });	
		
		objetivoColumn.setSortable(true);
		sortHandler.setComparator(objetivoColumn, new Comparator<Periodo>() {
	      @Override
	      public int compare(Periodo o1, Periodo o2) {
	        return o1.getObjetivo().compareTo(o2.getObjetivo());
	      }
	    });		
		
        pesoColumn.setSortable(true);
        sortHandler.setComparator(pesoColumn, new Comparator<Periodo>() {
          @Override
          public int compare(Periodo o1, Periodo o2) {
            return o1.getPeso().compareTo(o2.getPeso());
          }
        }); 		
		
		dataInicialColumn.setSortable(true);
		sortHandler.setComparator(dataInicialColumn, new Comparator<Periodo>() {
	      @Override
	      public int compare(Periodo o1, Periodo o2) {
	        return o1.getDataInicial().compareTo(o2.getDataInicial());
	      }
	    });
		
		dataFinalColumn.setSortable(true);
		sortHandler.setComparator(dataFinalColumn, new Comparator<Periodo>() {
	      @Override
	      public int compare(Periodo o1, Periodo o2) {
	        return o1.getDataFinal().compareTo(o2.getDataFinal());
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

					String strNome = dataProvider.getList().get(i).getNomePeriodo();			
					String strDescricao = dataProvider.getList().get(i).getDescricao();
					String strObjetivo = dataProvider.getList().get(i).getObjetivo();
					String strPeso = dataProvider.getList().get(i).getPeso();
					String strDataInicial = MpUtilClient.convertDateToString(dataProvider.getList().get(i).getDataInicial(), "EEEE, MMMM dd, yyyy");
					String strDataFinal = MpUtilClient.convertDateToString(dataProvider.getList().get(i).getDataFinal(), "EEEE, MMMM dd, yyyy");

					String strJuntaTexto = strNome.toUpperCase() + " " + strDescricao.toUpperCase() + " " + strObjetivo.toUpperCase();
					strJuntaTexto +=  strPeso.toUpperCase() + " " + " " + strDataInicial.toUpperCase() + " " + strDataFinal.toUpperCase();

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
