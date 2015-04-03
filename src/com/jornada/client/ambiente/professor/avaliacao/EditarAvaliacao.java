package com.jornada.client.ambiente.professor.avaliacao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
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
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
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
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxCursoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxDisciplinaAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxPeriodoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxPesoNota;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.cells.MpDatePickerCell;
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


	private AsyncCallback<String> callbackUpdateRow;
	private AsyncCallback<Boolean> callbackDelete;

	private VerticalPanel vPanelBody = new VerticalPanel();	
	
	 MpStyledSelectionCell pesoNotaCell;
	
	private CellTable<Avaliacao> cellTable;
	private Column<Avaliacao, String> assuntoColumn;
	private Column<Avaliacao, String> descricaoColumn;
	private Column<Avaliacao, String> columnTipoAvaliacao;
	private Column<Avaliacao, String> columnPesoNota;
	private Column<Avaliacao, Date> dataColumn;
	private Column<Avaliacao, String> horaColumn;
	private ListDataProvider<Avaliacao> dataProvider = new ListDataProvider<Avaliacao>();	
	
	private LinkedHashMap<String, String> listaTipoAvaliacao = new LinkedHashMap<String, String>();	
	private LinkedHashMap<String, String> listaHora = new LinkedHashMap<String, String>();
	private LinkedHashMap<String, String> listaPesoNota = new LinkedHashMap<String, String>();
	
	private MpListBoxCursoAmbienteProfessor listBoxCurso;
	private MpListBoxPeriodoAmbienteProfessor listBoxPeriodo;	
	private MpListBoxDisciplinaAmbienteProfessor listBoxDisciplina;
//	private MpSelectionConteudoProgramatico listBoxConteudoProgramatico;	

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
//		Label lblConteudoProgramatico = new Label(txtConstants.conteudoProgramatico());
		
		listBoxCurso = new MpListBoxCursoAmbienteProfessor(telaInicialAvaliacao.getMainView().getUsuarioLogado());
		listBoxPeriodo = new MpListBoxPeriodoAmbienteProfessor(telaInicialAvaliacao.getMainView().getUsuarioLogado());
		listBoxDisciplina = new MpListBoxDisciplinaAmbienteProfessor(telaInicialAvaliacao.getMainView().getUsuarioLogado());		
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
			gridComboBox.setWidget(row++, 2, new InlineHTML("&nbsp;"));
			gridComboBox.setWidget(row, 0, lblPeriodo);
			gridComboBox.setWidget(row, 1, listBoxPeriodo);
			gridComboBox.setWidget(row++, 2, new InlineHTML("&nbsp;"));
			gridComboBox.setWidget(row, 0, lblDisciplina);
			gridComboBox.setWidget(row, 1, listBoxDisciplina);
			gridComboBox.setWidget(row, 2, new InlineHTML("&nbsp;"));
			gridComboBox.setWidget(row++, 3, mpPanelLoading);

		}

			
		vPanelBody.add(gridComboBox);



		/************************* Begin Callback's *************************/

		
		
		callbackUpdateRow = new AsyncCallback<String>() {

			public void onSuccess(String success) {
			    
			    if(success.equals("true")){
			        
                } else if (success.equals(TipoAvaliacao.EXISTE_RECUPERACAO)) {
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroRecuperacao());
                    mpDialogBoxWarning.showDialog();
                }else if (success.equals(TipoAvaliacao.EXISTE_RECUPERACAO_FINAL)) {
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroRecuperacaoFinal());
                    mpDialogBoxWarning.showDialog();
                }else if (success.equals(TipoAvaliacao.EXISTE_ASSUNTO)) {
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroAssunto());
                    mpDialogBoxWarning.showDialog();
                }else{
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroAtualizar());
                    mpDialogBoxWarning.showDialog();
                }

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
		
		Label lblEmpty = new Label(txtConstants.avaliacaoNenhumaDisciplina());
//		Label lblEmpty2 = new Label("Por favor, selecione um Conteudo Programatico.");
		
		cellTable = new CellTable<Avaliacao>(5,GWT.<CellTableStyle> create(CellTableStyle.class));
//		cellTable.setWidth(Integer.toString(TelaInicialAvaliacao.intWidthTable)+ "px");
		cellTable.setWidth("100%");
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
//		scrollPanel.setSize(Integer.toString(TelaInicialAvaliacao.intWidthTable+30)+"px",Integer.toString(TelaInicialAvaliacao.intHeightTable-180)+"px");
		scrollPanel.setHeight(Integer.toString(TelaInicialAvaliacao.intHeightTable-180)+"px");
		scrollPanel.setWidth("100%");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(cellTable);
		
		vPanelBody.add(mpPager);
		vPanelBody.add(scrollPanel);
		vPanelBody.setWidth("100%");
		
		
		this.setWidth("100%");
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
		    populateGridAvaliacao();
		}  
	}
	

	/**************** End Event Handlers *****************/
	
	
	
	/**************** Begin Populate methods*****************/	
	


	protected void populateGridAvaliacao() {
		
		mpPanelLoading.setVisible(true);
		
		int indexDisciplina = listBoxDisciplina.getSelectedIndex();
		
		if (indexDisciplina == -1 ) {
			mpPanelLoading.setVisible(false);
			dataProvider.getList().clear();
		} 
		else{	
			int idDisciplina = Integer.parseInt(listBoxDisciplina.getValue(indexDisciplina));
			GWTServiceAvaliacao.Util.getInstance().getAvaliacaoPelaDisciplina(idDisciplina, 
					
					new AsyncCallback<ArrayList<Avaliacao>>() {

						@Override
						public void onFailure(Throwable caught) {
							mpPanelLoading.setVisible(false);
							mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
							mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroCarregar());
						}

						@Override
						public void onSuccess(ArrayList<Avaliacao> list) {
							MpUtilClient.isRefreshRequired(list);
							mpPanelLoading.setVisible(false);
							dataProvider.getList().clear();
							cellTable.setRowCount(0);
							for(int i=0;i<list.size();i++){
								dataProvider.getList().add(list.get(i));
							}
							addCellTableData(dataProvider);
							
							cellTable.redraw();

							
							disableListBoxCells();
							 


							
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
						MpUtilClient.isRefreshRequired(list);

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
		 
//		 disableListBoxCells();
		 
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
			    int idTipoAvaliacao = Integer.parseInt(value);
			    
			    int idCurso = Integer.parseInt(listBoxCurso.getSelectedValue());
			    
//			    Element el = DOM.getElementById("pesoNota");

			    String strClean = cellTable.getRowElement(index).getCells().getItem(3).getFirstChildElement().getInnerHTML();
                strClean = strClean.replace("<select disabled", "<select");
                strClean = strClean.replace("select=\"\"", "select");
                cellTable.getRowElement(index).getCells().getItem(3).getFirstChildElement().setInnerHTML(strClean);
                
                if (idTipoAvaliacao == TipoAvaliacao.INT_RECUPERACAO || 
                    idTipoAvaliacao == TipoAvaliacao.INT_RECUPERACAO_FINAL || 
                    idTipoAvaliacao == TipoAvaliacao.INT_ADICIONAL_NOTA) {

                    String strDisable = cellTable.getRowElement(index).getCells().getItem(3).getFirstChildElement().getInnerHTML();
                    strDisable = strDisable.replace("<select", "<select disabled");
                    cellTable.getRowElement(index).getCells().getItem(3).getFirstChildElement().setInnerHTML(strDisable);
                }

				object.setIdTipoAvaliacao(idTipoAvaliacao);
				GWTServiceAvaliacao.Util.getInstance().updateRow(idCurso, object, callbackUpdateRow);
			}
		});
	    
	    
        MpListBoxPesoNota mpListBoxPesoNota = new MpListBoxPesoNota();
        for (int i = 0; i < mpListBoxPesoNota.getItemCount(); i++) {
            listaPesoNota.put(mpListBoxPesoNota.getValue(i), mpListBoxPesoNota.getItemText(i));
        }

        pesoNotaCell = new MpStyledSelectionCell(listaPesoNota,"design_text_boxes");
        columnPesoNota = new Column<Avaliacao, String>(pesoNotaCell) {
          @Override
          public String getValue(Avaliacao object) {

//              Element el = DOM.getElementById("pesoNota");
//              if(el!=null){
//                  el.removeAttribute("disabled");              
//                  if (object.getIdTipoAvaliacao() == TipoAvaliacao.INT_RECUPERACAO || 
//                      object.getIdTipoAvaliacao() == TipoAvaliacao.INT_RECUPERACAO_FINAL || 
//                      object.getIdTipoAvaliacao() == TipoAvaliacao.INT_ADICIONAL_NOTA) {
//                      el.setAttribute("disabled", "");
//                  }
//              }
              
              
            return object.getPesoNota();
          }
        };

        columnPesoNota.setFieldUpdater(new FieldUpdater<Avaliacao, String>() {
            @Override
            public void update(int index, Avaliacao object, String value) {
                object.setPesoNota(value);
                GWTServiceAvaliacao.Util.getInstance().updateRow(object, callbackUpdateRow);
            }
        });	    
        	    
		dataColumn = new Column<Avaliacao, Date>(new MpDatePickerCell()) {
			@Override
			public Date getValue(Avaliacao object) {
				return object.getData();
			}
		};
		dataColumn.setFieldUpdater(new FieldUpdater<Avaliacao, Date>() {
			@Override
			public void update(int index, Avaliacao object, Date value) {
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
        cellTable.addColumn(columnPesoNota, "Peso Nota");		
		cellTable.addColumn(dataColumn, txtConstants.avaliacaoData());
		cellTable.addColumn(horaColumn, txtConstants.avaliacaoHora());
		cellTable.addColumn(removeColumn, txtConstants.geralRemover());

		cellTable.getColumn(cellTable.getColumnIndex(assuntoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(descricaoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnTipoAvaliacao)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnPesoNota)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(removeColumn)).setCellStyleNames("hand-over");		
		

		
	}
	
    public void disableListBoxCells() {
        

        for (int i = 0; i < dataProvider.getList().size(); i++) {

            int idTipoAvaliacao = dataProvider.getList().get(i).getIdTipoAvaliacao();
            String strClean="";
            
            
            //Código necessário devido a condição de corrida
            try{
                strClean = cellTable.getRowElement(i).getCells().getItem(3).getFirstChildElement().getInnerHTML();    
            }catch(Exception ex){
                Timer timer = new Timer() {
                    @Override
                    public void run() {
                        disableListBoxCells();
                    }
                };
                timer.schedule(100);                
            }
            
            strClean = strClean.replace("<select disabled", "<select");
            strClean = strClean.replace("select=\"\"", "select");
            cellTable.getRowElement(i).getCells().getItem(3).getFirstChildElement().setInnerHTML(strClean);

            if (idTipoAvaliacao == TipoAvaliacao.INT_RECUPERACAO || idTipoAvaliacao == TipoAvaliacao.INT_RECUPERACAO_FINAL || idTipoAvaliacao == TipoAvaliacao.INT_ADICIONAL_NOTA) {

                String strDisable = cellTable.getRowElement(i).getCells().getItem(3).getFirstChildElement().getInnerHTML();
                strDisable = strDisable.replace("<select", "<select disabled");
                cellTable.getRowElement(i).getCells().getItem(3).getFirstChildElement().setInnerHTML(strDisable);
            }
        }  

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
