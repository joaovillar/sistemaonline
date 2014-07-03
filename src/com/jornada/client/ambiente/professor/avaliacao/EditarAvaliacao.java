package com.jornada.client.ambiente.professor.avaliacao;

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
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.classes.listBoxes.MpSelectionConteudoProgramatico;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionCursoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionDisciplinaAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionPeriodoAmbienteProfessor;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.cells.MpStyledSelectionCell;
import com.jornada.client.classes.widgets.dialog.MpConfirmDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.timepicker.MpTimePicker;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceAvaliacao;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Avaliacao;
import com.jornada.shared.classes.TipoAvaliacao;
import com.jornada.shared.classes.utility.MpUtilClient;

public class EditarAvaliacao extends VerticalPanel {
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);


	private AsyncCallback<Boolean> callbackUpdateRow;
	private AsyncCallback<Boolean> callbackDelete;

	private VerticalPanel vPanelBody = new VerticalPanel();	
	
	private CellTable<Avaliacao> cellTable;
	private Column<Avaliacao, String> assuntoColumn;
	private Column<Avaliacao, String> descricaoColumn;
	private Column<Avaliacao, String> columnTipoAvaliacao;
	private Column<Avaliacao, Date> dataColumn;
	private Column<Avaliacao, String> horaColumn;
	private ListDataProvider<Avaliacao> dataProvider = new ListDataProvider<Avaliacao>();	
	
	private LinkedHashMap<String, String> listaTipoAvaliacao = new LinkedHashMap<String, String>();	
	private LinkedHashMap<String, String> listaHora = new LinkedHashMap<String, String>();
	
	private MpSelectionCursoAmbienteProfessor listBoxCurso;
	private MpSelectionPeriodoAmbienteProfessor listBoxPeriodo;	
	private MpSelectionDisciplinaAmbienteProfessor listBoxDisciplina;
	private MpSelectionConteudoProgramatico listBoxConteudoProgramatico;	

	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	
	
	private TelaInicialAvaliacao telaInicialAvaliacao;

	public EditarAvaliacao(TelaInicialAvaliacao telaInicialAvaliacao) {
		
		this.telaInicialAvaliacao=telaInicialAvaliacao;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);			

		Label lblCurso = new Label(txtConstants.curso());
		Label lblPeriodo = new Label(txtConstants.periodo());
		Label lblDisciplina = new Label(txtConstants.disciplina());
		Label lblConteudoProgramatico = new Label(txtConstants.conteudoProgramatico());
		
		listBoxCurso = new MpSelectionCursoAmbienteProfessor(telaInicialAvaliacao.getMainView().getUsuarioLogado());
		listBoxPeriodo = new MpSelectionPeriodoAmbienteProfessor(telaInicialAvaliacao.getMainView().getUsuarioLogado());
		listBoxDisciplina = new MpSelectionDisciplinaAmbienteProfessor(telaInicialAvaliacao.getMainView().getUsuarioLogado());		
		listBoxConteudoProgramatico = new MpSelectionConteudoProgramatico();		
		
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());
		listBoxPeriodo.addChangeHandler(new MpPeriodoSelectionChangeHandler());		
		listBoxDisciplina.addChangeHandler(new MpDisciplinaSelectionChangeHandler());		
		listBoxConteudoProgramatico.addChangeHandler(new MpConteudoProgramaticoSelectionChangeHandler());
		

		Grid gridComboBox = new Grid(4, 4);
		gridComboBox.setCellSpacing(2);
		gridComboBox.setCellPadding(2);
		{
			int row=0;
			gridComboBox.setWidget(row, 0, lblCurso);
			gridComboBox.setWidget(row, 1, listBoxCurso);
			gridComboBox.setWidget(row++, 2, new InlineHTML("&nbsp;"));
			gridComboBox.setWidget(row, 0, lblPeriodo);
			gridComboBox.setWidget(row, 1, listBoxPeriodo);
			gridComboBox.setWidget(row++, 2, new InlineHTML("&nbsp;"));
			gridComboBox.setWidget(row, 0, lblDisciplina);
			gridComboBox.setWidget(row, 1, listBoxDisciplina);
			gridComboBox.setWidget(row++, 2, new InlineHTML("&nbsp;"));
			gridComboBox.setWidget(row, 0, lblConteudoProgramatico);
			gridComboBox.setWidget(row, 1, listBoxConteudoProgramatico);
			gridComboBox.setWidget(row, 2, new InlineHTML("&nbsp;"));
			gridComboBox.setWidget(row++, 3, mpPanelLoading);			
		}

			
		vPanelBody.add(gridComboBox);



		/************************* Begin Callback's *************************/

		
		
		callbackUpdateRow = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {

			}

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroAtualizar());
				mpDialogBoxWarning.showDialog();
			}
		};

		callbackDelete = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {

				if (success == true) {
					populateGridAvaliacao();
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroRemover());
					mpDialogBoxWarning.showDialog();
				}

			}

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroRemover());
				mpDialogBoxWarning.showDialog();

			}
		};
		

		/*********************** End Callbacks **********************/
		
		
		

		/******** Begin Populate ********/
//		populateCursoComboBox();
		populateComboBoxTipoAvaliacao();
		/******** End Populate ********/
		
		
		super.add(vPanelBody);
		
	}

	private void renderCellTable(){
		
		Label lblEmpty = new Label(txtConstants.avaliacaoNenhumaAvaliacaoNoConteudo());
//		Label lblEmpty2 = new Label("Por favor, selecione um Conteudo Programatico.");
		
		cellTable = new CellTable<Avaliacao>(5,GWT.<CellTableStyle> create(CellTableStyle.class));
		cellTable.setWidth(Integer.toString(TelaInicialAvaliacao.intWidthTable)+ "px");
		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);
		cellTable.setEmptyTableWidget(lblEmpty);

		// Add a selection model so we can select cells.
		final SelectionModel<Avaliacao> selectionModel = new MultiSelectionModel<Avaliacao>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<Avaliacao> createCheckboxManager());
		initTableColumns(selectionModel);
		
		dataProvider.addDataDisplay(cellTable);
		
		MpSimplePager mpPager = new MpSimplePager();
		mpPager.setDisplay(cellTable);
		mpPager.setPageSize(15);
		
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize(Integer.toString(TelaInicialAvaliacao.intWidthTable+30)+"px",Integer.toString(TelaInicialAvaliacao.intHeightTable-180)+"px");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(cellTable);
		
		vPanelBody.add(mpPager);
		vPanelBody.add(scrollPanel);
		
		populateGridAvaliacao();
		
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
				final Avaliacao object = (Avaliacao) context.getKey();
				@SuppressWarnings("unused")
				CloseHandler<PopupPanel> closeHandler;

				MpConfirmDialogBox confirmationDialog = new MpConfirmDialogBox(
						txtConstants.geralRemover(),txtConstants.avaliacaoDesejaRemover(object.getAssunto()), txtConstants.geralSim(),txtConstants.geralNao(),

						closeHandler = new CloseHandler<PopupPanel>() {

							public void onClose(CloseEvent<PopupPanel> event) {

								MpConfirmDialogBox x = (MpConfirmDialogBox) event.getSource();

								if (x.primaryActionFired()) {

									GWTServiceAvaliacao.Util.getInstance().deleteRow(object.getIdAvaliacao(), callbackDelete);

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
			int index = listBoxPeriodo.getSelectedIndex();
			if(index==-1){
				listBoxDisciplina.clear();
				listBoxConteudoProgramatico.clear();
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
			int index = listBoxDisciplina.getSelectedIndex();
			if(index==-1){
				listBoxConteudoProgramatico.clear();
				dataProvider.getList().clear();
			}
			else{
				int idDisciplina= Integer.parseInt(listBoxDisciplina.getValue(index));
				listBoxConteudoProgramatico.populateComboBox(idDisciplina);				
			}
		}  
	}
	
	private class MpConteudoProgramaticoSelectionChangeHandler implements ChangeHandler{
		
		public void onChange(ChangeEvent event){
			populateGridAvaliacao();
//			populateComboBoxTipoAvaliacao();
		}
	}	
	

	/**************** End Event Handlers *****************/
	
	
	
	/**************** Begin Populate methods*****************/	
	


	protected void populateGridAvaliacao() {
		
		mpPanelLoading.setVisible(true);
		
		int indexConteudoProgramatico = listBoxConteudoProgramatico.getSelectedIndex();
		
		if (indexConteudoProgramatico == -1 ) {
			mpPanelLoading.setVisible(false);
			dataProvider.getList().clear();
		} 
		else{	
			int idConteudoProgramatico = Integer.parseInt(listBoxConteudoProgramatico.getValue(indexConteudoProgramatico));
			GWTServiceAvaliacao.Util.getInstance().getAvaliacaoPeloConteudoProgramatico(idConteudoProgramatico, 
					
					new AsyncCallback<ArrayList<Avaliacao>>() {

						@Override
						public void onFailure(Throwable caught) {
							mpPanelLoading.setVisible(false);
							mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
							mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroCarregar());
						}

						@Override
						public void onSuccess(ArrayList<Avaliacao> list) {
							mpPanelLoading.setVisible(false);
							dataProvider.getList().clear();
							cellTable.setRowCount(0);
							for(int i=0;i<list.size();i++){
								dataProvider.getList().add(list.get(i));
							}
							addCellTableData(dataProvider);
							cellTable.redraw();	
						}
					});
			
		}
		
	}
	
	protected void populateComboBoxTipoAvaliacao() {
		
		GWTServiceAvaliacao.Util.getInstance().getTipoAvaliacao(
		
				new AsyncCallback<ArrayList<TipoAvaliacao>>() {

					@Override
					public void onFailure(Throwable caught) {
						mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
						mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroCarregar());
					}

					@Override
					public void onSuccess(ArrayList<TipoAvaliacao> list) {

						for(TipoAvaliacao currentTipoUsuario : list){
							String strIdTipoAvaliacao = Integer.toString(currentTipoUsuario.getIdTipoAvaliacao());
							String strNomeTipoAvaliacao = currentTipoUsuario.getNomeTipoAvaliacao();
							listaTipoAvaliacao.put(strIdTipoAvaliacao, strNomeTipoAvaliacao);						
						}
						renderCellTable();
						
					}
				});
	}	
	
	
	public void updateClientData(){
		listBoxCurso.populateComboBox(this.telaInicialAvaliacao.getMainView().getUsuarioLogado());
	}		
	
	/**************** End Populate methods*****************/

	
	private void addCellTableData(ListDataProvider<Avaliacao> dataProvider){
		
		 ListHandler<Avaliacao> sortHandler = new ListHandler<Avaliacao>(dataProvider.getList());
		 
		 cellTable.addColumnSortHandler(sortHandler);	

		 initSortHandler(sortHandler);
	
	}


	private void initTableColumns(final SelectionModel<Avaliacao> selectionModel) {
		
		assuntoColumn = new Column<Avaliacao, String>(new EditTextCell()) {
			@Override
			public String getValue(Avaliacao object) {
				return object.getAssunto();
			}

		};
		assuntoColumn.setFieldUpdater(new FieldUpdater<Avaliacao, String>() {
			@Override
			public void update(int index, Avaliacao object, String value) {
				// Called when the user changes the value.
				
				if (FieldVerifier.isValidName(value)) {					
					object.setAssunto(value);
					GWTServiceAvaliacao.Util.getInstance().updateRow(object, callbackUpdateRow);
				}else{
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.avaliacaoAssunto()));
					mpDialogBoxWarning.showDialog();
				}				

			}
		});
		

		descricaoColumn = new Column<Avaliacao, String>(new EditTextCell()) {
			@Override
			public String getValue(Avaliacao object) {
				return object.getDescricao();
			}
		};
		descricaoColumn.setFieldUpdater(new FieldUpdater<Avaliacao, String>() {
			@Override
			public void update(int index, Avaliacao object, String value) {
				// Called when the user changes the value.
				object.setDescricao(value);
				//getServicePeriodoAsync().updatePeriodoRow(object,callbackUpdateRow);
				GWTServiceAvaliacao.Util.getInstance().updateRow(object, callbackUpdateRow);
			}
		});


	    MpStyledSelectionCell tipoAvaliacaoCell = new MpStyledSelectionCell(listaTipoAvaliacao,"design_text_boxes");
	    columnTipoAvaliacao = new Column<Avaliacao, String>(tipoAvaliacaoCell) {
	      @Override
	      public String getValue(Avaliacao object) {
	        return Integer.toString(object.getIdTipoAvaliacao());
	      }
	    };
	    columnTipoAvaliacao.setFieldUpdater(new FieldUpdater<Avaliacao, String>() {
			@Override
			public void update(int index, Avaliacao object, String value) {
				// Called when the user changes the value.
				object.setIdTipoAvaliacao(Integer.parseInt(value));
				GWTServiceAvaliacao.Util.getInstance().updateRow(object, callbackUpdateRow);
			}
		});
	    
		dataColumn = new Column<Avaliacao, Date>(new DatePickerCell()) {
			@Override
			public Date getValue(Avaliacao object) {
//				Date date = MpUtilClient.convertStringToDate(object.getData());
//				return date;
				return object.getData();
			}
		};
		dataColumn.setFieldUpdater(new FieldUpdater<Avaliacao, Date>() {
			@Override
			public void update(int index, Avaliacao object, Date value) {
				// Called when the user changes the value.
//				object.setData(MpUtilClient.convertDateToString(value));
				object.setData(value);
				GWTServiceAvaliacao.Util.getInstance().updateRow(object,callbackUpdateRow);
			}
		});	  
		
		MpTimePicker mpTimePicker = new MpTimePicker(7, 24);
		for(int i=0;i<mpTimePicker.getItemCount();i++){
			listaHora.put(mpTimePicker.getValue(i), mpTimePicker.getItemText(i));
		}		
		
	    MpStyledSelectionCell horaCell = new MpStyledSelectionCell(listaHora,"design_text_boxes");
	    
	    horaColumn = new Column<Avaliacao, String>(horaCell) {
	      @Override
			public String getValue(Avaliacao object) {
				String strHora = MpUtilClient.add_AM_PM(object.getHora());
//				strHora = strHora.substring(0, strHora.lastIndexOf(":"));
//				String aux = MpUtilClient.getHourFromString(strHora);
//				int intaux = Integer.parseInt(aux);
//				if (intaux>=12){
//					strHora = strHora + " PM";
//				}
//				else{
//					strHora = strHora + " AM";
//				}
				return strHora;
			}
	    };
	    horaColumn.setFieldUpdater(new FieldUpdater<Avaliacao, String>() {
			@Override
			public void update(int index, Avaliacao object, String value) {
				object.setHora(value);
				GWTServiceAvaliacao.Util.getInstance().updateRow(object, callbackUpdateRow);
			}
		});



		Column<Avaliacao, String> removeColumn = new Column<Avaliacao, String>(new MyImageCell()) {
			@Override
			public String getValue(Avaliacao object) {
				return "images/delete.png";
			}
		};


		cellTable.addColumn(assuntoColumn, txtConstants.avaliacaoAssunto());
		cellTable.addColumn(descricaoColumn, txtConstants.avaliacaoDescricao());
		cellTable.addColumn(columnTipoAvaliacao, txtConstants.avaliacaoTipo());
		cellTable.addColumn(dataColumn, txtConstants.avaliacaoData());
		cellTable.addColumn(horaColumn, txtConstants.avaliacaoHora());
		cellTable.addColumn(removeColumn, txtConstants.geralRemover());

		cellTable.getColumn(cellTable.getColumnIndex(assuntoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(descricaoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnTipoAvaliacao)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(removeColumn)).setCellStyleNames("hand-over");		
		
	}

	public void initSortHandler(ListHandler<Avaliacao> sortHandler) {
		
		assuntoColumn.setSortable(true);
	    sortHandler.setComparator(assuntoColumn, new Comparator<Avaliacao>() {
	      @Override
	      public int compare(Avaliacao o1, Avaliacao o2) {
	        return o1.getAssunto().compareTo(o2.getAssunto());
	      }
	    });	
	    
	    descricaoColumn.setSortable(true);
	    sortHandler.setComparator(descricaoColumn, new Comparator<Avaliacao>() {
	      @Override
	      public int compare(Avaliacao o1, Avaliacao o2) {
	        return o1.getDescricao().compareTo(o2.getDescricao());
	      }
	    });	
	    
	    columnTipoAvaliacao.setSortable(true);
	    sortHandler.setComparator(columnTipoAvaliacao, new Comparator<Avaliacao>() {
	      @Override
	      public int compare(Avaliacao o1, Avaliacao o2) {
	    	  int primitive1 = o1.getIdTipoAvaliacao(), primitive2 = o2.getIdTipoAvaliacao();
	    	  Integer a = new Integer(primitive1);
	    	  Integer b = new Integer(primitive2);
	    	  return a.compareTo(b);
	      }
	    });		    
	    
	    dataColumn.setSortable(true);
	    sortHandler.setComparator(dataColumn, new Comparator<Avaliacao>() {
	      @Override
	      public int compare(Avaliacao o1, Avaliacao o2) {
	        return o1.getData().compareTo(o2.getData());
	      }
	    });	
	    
	    horaColumn.setSortable(true);
	    sortHandler.setComparator(horaColumn, new Comparator<Avaliacao>() {
	      @Override
	      public int compare(Avaliacao o1, Avaliacao o2) {
	        return o1.getHora().compareTo(o2.getHora());
	      }
	    });			    
	    
	}
	
	
}
