package com.jornada.client.ambiente.professor.topico;

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
import com.jornada.client.classes.widgets.dialog.MpConfirmDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceTopico;
import com.jornada.shared.classes.Topico;
import com.jornada.shared.classes.utility.MpUtilClient;

public class EditarTopicoProfessor extends VerticalPanel {

	private AsyncCallback<Boolean> callbackUpdateRow;
	private AsyncCallback<Boolean> callbackDelete;

	private CellTable<Topico> cellTable;
	private Column<Topico, String> nomeColumn;
	private Column<Topico, String> numeracaoColumn;
	private Column<Topico, String> descricaoColumn;
	private Column<Topico, String> objetivoColumn;
	private ListDataProvider<Topico> dataProvider = new ListDataProvider<Topico>();	

	private MpSelectionCursoAmbienteProfessor listBoxCurso;
	private MpSelectionPeriodoAmbienteProfessor listBoxPeriodo;	
	private MpSelectionDisciplinaAmbienteProfessor listBoxDisciplina;
	private MpSelectionConteudoProgramatico listBoxConteudoProgramatico;
	
	
	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	
	TextConstants txtConstants;

	private TelaInicialTopicoProfessor telaInicialTopicoProfessor;

	
	public EditarTopicoProfessor(TelaInicialTopicoProfessor telaInicialTopicoProfessor) {
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialTopicoProfessor=telaInicialTopicoProfessor;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpPanelLoading.setTxtLoading("carregando...");
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);		
		

		Label lblCurso = new Label(txtConstants.curso());
		Label lblPeriodo = new Label(txtConstants.periodo());
		Label lblDisciplina = new Label(txtConstants.disciplina());
		Label lblConteudoProgramatico = new Label(txtConstants.conteudoProgramatico());

		listBoxCurso = new MpSelectionCursoAmbienteProfessor(telaInicialTopicoProfessor.getMainView().getUsuarioLogado());
		listBoxPeriodo = new MpSelectionPeriodoAmbienteProfessor(telaInicialTopicoProfessor.getMainView().getUsuarioLogado());
		listBoxDisciplina = new MpSelectionDisciplinaAmbienteProfessor(telaInicialTopicoProfessor.getMainView().getUsuarioLogado());		
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

		Label lblEmpty = new Label(txtConstants.topicoNenhum());
//		Label lblEmpty2 = new Label("Por favor, selecione um Conteúdo Programático.");

		cellTable = new CellTable<Topico>(5,GWT.<CellTableStyle> create(CellTableStyle.class));
//		cellTable.setWidth(Integer.toString(TelaInicialTopicoProfessor.intWidthTable)+ "px");		
		cellTable.setWidth("100%");
		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);
		cellTable.setEmptyTableWidget(lblEmpty);

		// Add a selection model so we can select cells.
		final SelectionModel<Topico> selectionModel = new MultiSelectionModel<Topico>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<Topico> createCheckboxManager());
		initTableColumns(selectionModel);

		dataProvider.addDataDisplay(cellTable);	
		
		MpSimplePager mpPager = new MpSimplePager();
		mpPager.setDisplay(cellTable);
		mpPager.setPageSize(15);
		
		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(TelaInicialTopicoProfessor.intWidthTable+30)+"px",Integer.toString(TelaInicialTopicoProfessor.intHeightTable-180)+"px");
		scrollPanel.setHeight(Integer.toString(TelaInicialTopicoProfessor.intHeightTable-180)+"px");
		scrollPanel.setWidth("100%");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(cellTable);	
		
		
		VerticalPanel vPanelEditGrid = new VerticalPanel();
		vPanelEditGrid.setWidth("100%");
		vPanelEditGrid.add(gridComboBox);
		vPanelEditGrid.add(mpPager);
		vPanelEditGrid.add(scrollPanel);


		/************************* Begin Callback's *************************/

		callbackUpdateRow = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {

			}

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.topicoErroAtualizar());
				mpDialogBoxWarning.showDialog();
			}
		};

		callbackDelete = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {

				if (success == true) {
					populateGridTopico();
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.topicoErroAtualizar());
					mpDialogBoxWarning.showDialog();
				}

			}

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.topicoErroAtualizar());
				mpDialogBoxWarning.showDialog();

			}
		};
		/*********************** End Callbacks **********************/

		this.setWidth("100%");
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
				final Topico object = (Topico) context.getKey();
				@SuppressWarnings("unused")
				CloseHandler<PopupPanel> closeHandler;

				MpConfirmDialogBox confirmationDialog = new MpConfirmDialogBox(
						txtConstants.geralRemover(),txtConstants.topicoDesejaRemover(object.getNome()), txtConstants.geralSim(), txtConstants.geralNao(),

						closeHandler = new CloseHandler<PopupPanel>() {

							public void onClose(CloseEvent<PopupPanel> event) {

								MpConfirmDialogBox x = (MpConfirmDialogBox) event.getSource();

								if (x.primaryActionFired()) {

									GWTServiceTopico.Util.getInstance().deleteTopicoRow(object.getIdTopico(), callbackDelete);

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
			populateGridTopico();
		}
	}
	
	
	/**************** End Event Handlers *****************/
	
	
	
	/**************** Begin Populate methods*****************/	
	
	protected void populateGridTopico() {
		
		mpPanelLoading.setVisible(true);
		
		int indexConteudoProgramatico = listBoxConteudoProgramatico.getSelectedIndex();
		
		if (indexConteudoProgramatico == -1 ) {
			mpPanelLoading.setVisible(false);
			dataProvider.getList().clear();
		} 
		else{			
			int idConteudoProgramatico = Integer.parseInt(listBoxConteudoProgramatico.getValue(indexConteudoProgramatico));
			GWTServiceTopico.Util.getInstance().getTopicoPeloConteudoProgramatico(idConteudoProgramatico, 
					
					new AsyncCallback<ArrayList<Topico>>() {

						@Override
						public void onFailure(Throwable caught) {
							mpPanelLoading.setVisible(false);
							mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
							mpDialogBoxWarning.setBodyText(txtConstants.topicoErroCarregar());
						}

						@Override
						public void onSuccess(ArrayList<Topico> list) {
							MpUtilClient.isRefreshRequired(list);
							mpPanelLoading.setVisible(false);
							dataProvider.getList().clear();
							cellTable.setRowCount(0);
							for (int i = 0; i < list.size(); i++) {
								dataProvider.getList().add(list.get(i));
							}
							addCellTableData(dataProvider);
							cellTable.redraw();
						}
					});					
		}
	}
	
	/**************** End Populate methods*****************/

	public void updateClientData(){
		listBoxCurso.populateComboBox(this.telaInicialTopicoProfessor.getMainView().getUsuarioLogado());
	}
	
	
	private void addCellTableData(ListDataProvider<Topico> dataProvider) {

		ListHandler<Topico> sortHandler = new ListHandler<Topico>(dataProvider.getList());

		cellTable.addColumnSortHandler(sortHandler);

		initSortHandler(sortHandler);

	}

	private void initTableColumns(final SelectionModel<Topico> selectionModel) {
		
		nomeColumn = new Column<Topico, String>(new EditTextCell()) {
			@Override
			public String getValue(Topico object) {
				return object.getNome();
			}

		};
		nomeColumn.setFieldUpdater(new FieldUpdater<Topico, String>() {
			@Override
			public void update(int index, Topico object, String value) {
				// Called when the user changes the value.
				object.setNome(value);
				GWTServiceTopico.Util.getInstance().updateTopicoRow(object, callbackUpdateRow);
			}
		});
		
		numeracaoColumn = new Column<Topico, String>(new EditTextCell()) {
			@Override
			public String getValue(Topico object) {
				return object.getNumeracao();
			}

		};
		numeracaoColumn.setFieldUpdater(new FieldUpdater<Topico, String>() {
			@Override
			public void update(int index, Topico object, String value) {
				// Called when the user changes the value.
				object.setNumeracao(value);
				GWTServiceTopico.Util.getInstance().updateTopicoRow(object, callbackUpdateRow);
			}
		});

		descricaoColumn = new Column<Topico, String>(new EditTextCell()) {
			@Override
			public String getValue(Topico object) {
				return object.getDescricao();
			}
		};
		descricaoColumn.setFieldUpdater(new FieldUpdater<Topico, String>() {
			@Override
			public void update(int index, Topico object, String value) {
				// Called when the user changes the value.
				object.setDescricao(value);
				GWTServiceTopico.Util.getInstance().updateTopicoRow(object, callbackUpdateRow);
			}
		});


		objetivoColumn = new Column<Topico, String>(new EditTextCell()) {
			@Override
			public String getValue(Topico object) {
				return object.getObjetivo();
			}
		};
		objetivoColumn.setFieldUpdater(new FieldUpdater<Topico, String>() {
			@Override
			public void update(int index, Topico object, String value) {
				// Called when the user changes the value.
				object.setObjetivo(value);
				GWTServiceTopico.Util.getInstance().updateTopicoRow(object, callbackUpdateRow);
			}
		});

		Column<Topico, String> removeColumn = new Column<Topico, String>(new MyImageCell()) {
			@Override
			public String getValue(Topico object) {
				return "images/delete.png";
			}
		};


		cellTable.addColumn(nomeColumn, txtConstants.topicoNome());
		cellTable.addColumn(numeracaoColumn, txtConstants.topicoNumeracao());
		cellTable.addColumn(descricaoColumn, txtConstants.topicoDescricao());
		cellTable.addColumn(objetivoColumn, txtConstants.topicoObjetivo());
		cellTable.addColumn(removeColumn, txtConstants.geralRemover());

		// Make the name column sortable.
		nomeColumn.setSortable(true);		

		cellTable.getColumn(cellTable.getColumnIndex(nomeColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(numeracaoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(descricaoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(objetivoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(removeColumn)).setCellStyleNames("hand-over");		
		
	}

	public void initSortHandler(ListHandler<Topico> sortHandler) {
		nomeColumn.setSortable(true);
	    sortHandler.setComparator(nomeColumn, new Comparator<Topico>() {
	      @Override
	      public int compare(Topico o1, Topico o2) {
	        return o1.getNome().compareTo(o2.getNome());
	      }
	    });		
	    
		numeracaoColumn.setSortable(true);
	    sortHandler.setComparator(numeracaoColumn, new Comparator<Topico>() {
	      @Override
	      public int compare(Topico o1, Topico o2) {	    	  
	    	  return o1.getNumeracao().compareTo(o2.getNumeracao());
	      }
	    });	
	    
		descricaoColumn.setSortable(true);
	    sortHandler.setComparator(descricaoColumn, new Comparator<Topico>() {
	      @Override
	      public int compare(Topico o1, Topico o2) {	    	  
	    	  return o1.getDescricao().compareTo(o2.getDescricao());
	      }
	    });		
	    
		objetivoColumn.setSortable(true);
	    sortHandler.setComparator(objetivoColumn, new Comparator<Topico>() {
	      @Override
	      public int compare(Topico o1, Topico o2) {	    	  
	    	  return o1.getObjetivo().compareTo(o2.getObjetivo());
	      }
	    });		    
	    
	}
	
	
}
