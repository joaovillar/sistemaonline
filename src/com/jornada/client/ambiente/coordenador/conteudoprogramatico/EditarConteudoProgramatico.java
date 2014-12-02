package com.jornada.client.ambiente.coordenador.conteudoprogramatico;

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
import com.jornada.client.classes.listBoxes.MpSelectionDisciplina;
import com.jornada.client.classes.listBoxes.MpSelectionPeriodo;
import com.jornada.client.classes.listBoxes.suggestbox.MpListBoxPanelHelper;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.cells.MpTextAreaEditCell;
import com.jornada.client.classes.widgets.dialog.MpConfirmDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceConteudoProgramatico;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.ConteudoProgramatico;
import com.jornada.shared.classes.utility.MpUtilClient;

public class EditarConteudoProgramatico extends VerticalPanel {
	
	private AsyncCallback<Boolean> callbackUpdateRow;
	private AsyncCallback<Boolean> callbackDelete;

	private CellTable<ConteudoProgramatico> cellTable;
	private Column<ConteudoProgramatico, String> nomeColumn;
	private Column<ConteudoProgramatico, String> numeracaoColumn ;
	private Column<ConteudoProgramatico, String> descricaoColumn;
	private Column<ConteudoProgramatico, String> objetivoColumn;
	private ListDataProvider<ConteudoProgramatico> dataProvider = new ListDataProvider<ConteudoProgramatico>();	

	private MpSelectionCurso listBoxCurso;
	private MpSelectionPeriodo listBoxPeriodo;	
	private MpSelectionDisciplina listBoxDisciplina;
	
	MpListBoxPanelHelper mpHelperCurso = new  MpListBoxPanelHelper();
	
	private TextBox txtSearch;
	ArrayList<ConteudoProgramatico> arrayListBackup = new ArrayList<ConteudoProgramatico>();

	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	
	TextConstants txtConstants;
	
	@SuppressWarnings("unused")
	private TelaInicialConteudoProgramatico telaInicialConteudoProgramatico;

	public EditarConteudoProgramatico(TelaInicialConteudoProgramatico telaInicialConteudoProgramatico) {
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialConteudoProgramatico=telaInicialConteudoProgramatico;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);			

		Label lblCurso = new Label(txtConstants.curso());
		Label lblPeriodo = new Label(txtConstants.periodo());
		Label lblDisciplina = new Label(txtConstants.disciplina());

		listBoxCurso = new MpSelectionCurso(true);
		listBoxPeriodo = new MpSelectionPeriodo();
		listBoxDisciplina = new MpSelectionDisciplina();
		
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());		
		listBoxPeriodo.addChangeHandler(new MpPeriodoSelectionChangeHandler());		
		listBoxDisciplina.addChangeHandler(new MpDisciplinaSelectionChangeHandler());

		Grid gridComboBox = new Grid(3, 4);
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
			gridComboBox.setWidget(row++, 3, mpPanelLoading);
			
		}

		Label lblEmpty = new Label(txtConstants.conteudoProgramaticoNenhum());
//		Label lblEmpty2 = new Label("Por favor, selecione o Conteúdo Programático.");

		cellTable = new CellTable<ConteudoProgramatico>(5,GWT.<CellTableStyle> create(CellTableStyle.class));
//		cellTable.setWidth(Integer.toString(TelaInicialConteudoProgramatico.intWidthTable)+ "px");
		cellTable.setWidth("100%");
		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);
		cellTable.setEmptyTableWidget(lblEmpty);

		final SelectionModel<ConteudoProgramatico> selectionModel = new MultiSelectionModel<ConteudoProgramatico>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<ConteudoProgramatico> createCheckboxManager());
		initTableColumns(selectionModel);
		
		dataProvider.addDataDisplay(cellTable);	
		
		MpSimplePager mpPager = new MpSimplePager();
		mpPager.setDisplay(cellTable);
		mpPager.setPageSize(15);	
		
		
		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(TelaInicialConteudoProgramatico.intWidthTable+30)+"px",Integer.toString(TelaInicialConteudoProgramatico.intHeightTable-160)+"px");
		scrollPanel.setHeight(Integer.toString(TelaInicialConteudoProgramatico.intHeightTable-160)+"px");
		scrollPanel.setWidth("100%");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(cellTable);		
		
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
		
		
		VerticalPanel vPanelEditGrid = new VerticalPanel();		
		vPanelEditGrid.add(gridComboBox);
//		vPanelEditGrid.add(mpPager);
		vPanelEditGrid.add(flexTableFiltrar);
		vPanelEditGrid.add(scrollPanel);
		vPanelEditGrid.setWidth("100%");

		/************************* Begin Callback's *************************/
		
		callbackUpdateRow = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {
				if(success==false){
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.conteudoProgramaticoErroAtualizar()+" "+txtConstants.geralRegarregarPagina());
					mpDialogBoxWarning.showDialog();					
				}
			}

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.conteudoProgramaticoErroAtualizar());
				mpDialogBoxWarning.showDialog();
			}
		};

		callbackDelete = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {

				if (success == true) {
					populateGridConteudoProgramatico();
					// SC.say("Periodo removido com sucesso.");
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.conteudoProgramaticoErroRemover());
					mpDialogBoxWarning.showDialog();
				}

			}

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.conteudoProgramaticoErroRemover());
				mpDialogBoxWarning.showDialog();

			}
		};
		

		/*********************** End Callbacks **********************/
		
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
				final ConteudoProgramatico object = (ConteudoProgramatico) context.getKey();
				@SuppressWarnings("unused")
				CloseHandler<PopupPanel> closeHandler;

				MpConfirmDialogBox confirmationDialog = new MpConfirmDialogBox(
						txtConstants.geralRemover(),txtConstants.conteudoProgramaticoDesejaRemover(object.getNome()), txtConstants.geralSim(), txtConstants.geralNao(),

						closeHandler = new CloseHandler<PopupPanel>() {

							public void onClose(CloseEvent<PopupPanel> event) {

								MpConfirmDialogBox x = (MpConfirmDialogBox) event.getSource();

								if (x.primaryActionFired()) {

									GWTServiceConteudoProgramatico.Util.getInstance().deleteConteudoProgramaticoRow(object.getIdConteudoProgramatico(), callbackDelete);

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
			}
			else{
				int idPeriodo = Integer.parseInt(listBoxPeriodo.getValue(index));
				listBoxDisciplina.populateComboBox(idPeriodo);				
			}
		}  
	}	
	
	private class MpDisciplinaSelectionChangeHandler implements ChangeHandler{		
		public void onChange(ChangeEvent event) {
				populateGridConteudoProgramatico();			
		}
	}

	/**************** End Event Handlers *****************/
	
	
	
	/**************** Begin Populate methods*****************/	
	private void populateGridConteudoProgramatico() {

		mpPanelLoading.setVisible(true);
		
		int indexDisciplina = listBoxDisciplina.getSelectedIndex();
		
		if (indexDisciplina == -1 ) {
			mpPanelLoading.setVisible(false);
			dataProvider.getList().clear();
			arrayListBackup.clear();
		} 
		else{			
			int idDisciplina = Integer.parseInt(listBoxDisciplina.getValue(indexDisciplina));
			GWTServiceConteudoProgramatico.Util.getInstance().getConteudoProgramaticoPelaDisciplina(idDisciplina,

					new AsyncCallback<ArrayList<ConteudoProgramatico>>() {

						@Override
						public void onFailure(Throwable caught) {
							mpPanelLoading.setVisible(false);
							mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
							mpDialogBoxWarning.setBodyText(txtConstants.conteudoProgramaticoErroCarregar());
						}

						@Override
						public void onSuccess(ArrayList<ConteudoProgramatico> list) {

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
	
	public void updateClientData(){
		listBoxCurso.populateComboBox();
	}
	/**************** End Populate methods*****************/
	
	
	
	private void addCellTableData(ListDataProvider<ConteudoProgramatico> dataProvider){
		
		 ListHandler<ConteudoProgramatico> sortHandler = new ListHandler<ConteudoProgramatico>(dataProvider.getList());
		 
		 cellTable.addColumnSortHandler(sortHandler);	

		 initSortHandler(sortHandler);
	
	}


	private void initTableColumns(final SelectionModel<ConteudoProgramatico> selectionModel) {
		
		
		nomeColumn = new Column<ConteudoProgramatico, String>(new EditTextCell()) {
			@Override
			public String getValue(ConteudoProgramatico object) {
				return object.getNome();
			}

		};
		nomeColumn.setFieldUpdater(new FieldUpdater<ConteudoProgramatico, String>() {
			@Override
			public void update(int index, ConteudoProgramatico object, String value) {
				
				if (FieldVerifier.isValidName(value)) {
					object.setNome(value);
					GWTServiceConteudoProgramatico.Util.getInstance().updateConteudoProgramaticoRow(object, callbackUpdateRow);
				}else{
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.conteudoProgramaticoNome()));
					mpDialogBoxWarning.showDialog();
				}				
				
			}
		});
		
		numeracaoColumn = new Column<ConteudoProgramatico, String>(new EditTextCell()) {
			@Override
			public String getValue(ConteudoProgramatico object) {
				return object.getNumeracao();
			}

		};
		numeracaoColumn.setFieldUpdater(new FieldUpdater<ConteudoProgramatico, String>() {
			@Override
			public void update(int index, ConteudoProgramatico object, String value) {
				// Called when the user changes the value.
				object.setNumeracao(value);
				GWTServiceConteudoProgramatico.Util.getInstance().updateConteudoProgramaticoRow(object, callbackUpdateRow);
			}
		});

		descricaoColumn = new Column<ConteudoProgramatico, String>(new MpTextAreaEditCell(5,150)) {
			@Override
			public String getValue(ConteudoProgramatico object) {
				return object.getDescricao();
			}
		};
		descricaoColumn.setFieldUpdater(new FieldUpdater<ConteudoProgramatico, String>() {
			@Override
			public void update(int index, ConteudoProgramatico object, String value) {
				// Called when the user changes the value.
				object.setDescricao(value);
				GWTServiceConteudoProgramatico.Util.getInstance().updateConteudoProgramaticoRow(object, callbackUpdateRow);
			}
		});

		objetivoColumn = new Column<ConteudoProgramatico, String>(new MpTextAreaEditCell(5,150)) {
			@Override
			public String getValue(ConteudoProgramatico object) {
				return object.getObjetivo();
			}
		};
		objetivoColumn.setFieldUpdater(new FieldUpdater<ConteudoProgramatico, String>() {
			@Override
			public void update(int index, ConteudoProgramatico object, String value) {
				// Called when the user changes the value.
				object.setObjetivo(value);
				GWTServiceConteudoProgramatico.Util.getInstance().updateConteudoProgramaticoRow(object, callbackUpdateRow);
			}
		});

		Column<ConteudoProgramatico, String> removeColumn = new Column<ConteudoProgramatico, String>(new MyImageCell()) {
			@Override
			public String getValue(ConteudoProgramatico object) {
				return "images/delete.png";
			}
		};


		cellTable.addColumn(nomeColumn, txtConstants.conteudoProgramaticoNome());
		cellTable.addColumn(numeracaoColumn, txtConstants.conteudoProgramaticoNumeracao());
		cellTable.addColumn(descricaoColumn, txtConstants.conteudoProgramaticoDescricao());
		cellTable.addColumn(objetivoColumn, txtConstants.conteudoProgramaticoObjetivo());
		cellTable.addColumn(removeColumn, txtConstants.geralRemover());

		cellTable.getColumn(cellTable.getColumnIndex(nomeColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(numeracaoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(descricaoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(objetivoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(removeColumn)).setCellStyleNames("hand-over");
		
		
	}

	private void initSortHandler(ListHandler<ConteudoProgramatico> sortHandler) {
		
		nomeColumn.setSortable(true);
	    sortHandler.setComparator(nomeColumn, new Comparator<ConteudoProgramatico>() {
	      @Override
	      public int compare(ConteudoProgramatico o1, ConteudoProgramatico o2) {
	        return o1.getNome().compareTo(o2.getNome());
	      }
	    });	
	    
		numeracaoColumn.setSortable(true);
	    sortHandler.setComparator(numeracaoColumn, new Comparator<ConteudoProgramatico>() {
	      @Override
	      public int compare(ConteudoProgramatico o1, ConteudoProgramatico o2) {
	        return o1.getNumeracao().compareTo(o2.getNumeracao());
	      }
	    });	
	    
		descricaoColumn.setSortable(true);
	    sortHandler.setComparator(descricaoColumn, new Comparator<ConteudoProgramatico>() {
	      @Override
	      public int compare(ConteudoProgramatico o1, ConteudoProgramatico o2) {
	        return o1.getDescricao().compareTo(o2.getDescricao());
	      }
	    });		 
	    
		objetivoColumn.setSortable(true);
	    sortHandler.setComparator(objetivoColumn, new Comparator<ConteudoProgramatico>() {
	      @Override
	      public int compare(ConteudoProgramatico o1, ConteudoProgramatico o2) {
	        return o1.getObjetivo().compareTo(o2.getObjetivo());
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

					String strNome = dataProvider.getList().get(i).getNome();	
					String strDescricao = dataProvider.getList().get(i).getDescricao();
					String strObjetivo = dataProvider.getList().get(i).getObjetivo();
					String strNumeracao = dataProvider.getList().get(i).getNumeracao();
					
					String strJuntaTexto = strNome.toUpperCase() + " " + strDescricao.toUpperCase() + " " + strObjetivo.toUpperCase();
					strJuntaTexto +=  " " + strNumeracao.toUpperCase();

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
