package com.jornada.client.ambiente.pais.ocorrencia;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.classes.listBoxes.ambiente.pais.MpSelectionAlunoAmbientePais;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpacePanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceOcorrencia;
import com.jornada.shared.classes.OcorrenciaAluno;
import com.jornada.shared.classes.utility.MpUtilClient;

public class VisualizarPaisOcorrencia extends VerticalPanel {
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);

	boolean isFirstTime=true;
	
	private AsyncCallback<Boolean> callbackUpdateRow;
	
	private CellTable<OcorrenciaAluno> cellTable;
	private Column<OcorrenciaAluno, Boolean> paiCienteColumn;
	private Column<OcorrenciaAluno, String> nomeCursoColumn;
	private Column<OcorrenciaAluno, String> nomePeriodoColumn;
	private Column<OcorrenciaAluno, String> nomeDisciplinaColumn;
	private Column<OcorrenciaAluno, String> nomeConteudoProgramaticoColumn;
	private Column<OcorrenciaAluno, String> nomeOcorrenciaColumn;
	private Column<OcorrenciaAluno, String> nomeDescricaoColumn;
	private Column<OcorrenciaAluno, Date> dataColumn;
	private Column<OcorrenciaAluno, String> horaColumn;	
	
	private ListDataProvider<OcorrenciaAluno> dataProvider = new ListDataProvider<OcorrenciaAluno>();
	
	private TextBox txtSearch;
	ArrayList<OcorrenciaAluno> arrayListBackup = new ArrayList<OcorrenciaAluno>();

	private MpSelectionAlunoAmbientePais listBoxAluno;

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
		
	
	private static VisualizarPaisOcorrencia uniqueInstance;
	
	private TelaInicialPaisOcorrencia telaInicialPaisOcorrencia;
	
	
	public static VisualizarPaisOcorrencia getInstance(TelaInicialPaisOcorrencia telaInicialPaisOcorrencia){
		
		if(uniqueInstance==null){
			uniqueInstance = new VisualizarPaisOcorrencia(telaInicialPaisOcorrencia);
		}
		return uniqueInstance;
	}

	private  VisualizarPaisOcorrencia(TelaInicialPaisOcorrencia telaInicialPaisOcorrencia) {
		
		this.telaInicialPaisOcorrencia = telaInicialPaisOcorrencia;
	    
		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);		

		Label lblAluno = new Label(txtConstants.aluno());

		listBoxAluno = new MpSelectionAlunoAmbientePais(this.telaInicialPaisOcorrencia.getMainView().getUsuarioLogado());
		listBoxAluno.setWidth("250px");
		listBoxAluno.addChangeHandler(new MpAlunoSelectionChangeHandler());

		Grid gridComboBox = new Grid(1, 6);
		gridComboBox.setCellSpacing(2);
		gridComboBox.setCellPadding(2);
		{

			gridComboBox.setWidget(0, 0, lblAluno);
			gridComboBox.setWidget(0, 1, listBoxAluno);
			gridComboBox.setWidget(0, 2, new InlineHTML("&nbsp;"));
			gridComboBox.setWidget(0, 3, mpPanelLoading);
			
		}

		Label lblEmpty1 = new Label(txtConstants.ocorrenciaNehumaOcorrencia());

		cellTable = new CellTable<OcorrenciaAluno>(5,GWT.<CellTableStyle> create(CellTableStyle.class));
		cellTable.setWidth(Integer.toString(TelaInicialPaisOcorrencia.intWidthTable)+ "px");		
		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);

		cellTable.setEmptyTableWidget(lblEmpty1);

		final SelectionModel<OcorrenciaAluno> selectionModel = new MultiSelectionModel<OcorrenciaAluno>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<OcorrenciaAluno> createCheckboxManager());
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
		
		
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize(Integer.toString(TelaInicialPaisOcorrencia.intWidthTable+30)+"px",Integer.toString(TelaInicialPaisOcorrencia.intHeightTable-110)+"px");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(cellTable);	
		
		VerticalPanel vPanelEditGrid = new VerticalPanel();		
		vPanelEditGrid.add(gridComboBox);
//		vPanelEditGrid.add(mpPager);
		vPanelEditGrid.add(flexTableFiltrar);
		vPanelEditGrid.add(scrollPanel);
		
		
		callbackUpdateRow = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {
//				paiCienteColumn.getValue(object);

			}

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.ocorrenciaErroAtualizar());
				mpDialogBoxWarning.showDialog();
			}
		};		




		super.add(vPanelEditGrid);

	}



	/**************** Begin Event Handlers *****************/


	/**************** End Event Handlers *****************/

	protected void populateGrid() {
		mpPanelLoading.setVisible(true);	
		isFirstTime=true;
//		String locale = LocaleInfo.getCurrentLocale().getLocaleName();
		int idAluno = Integer.parseInt(listBoxAluno.getValue(listBoxAluno.getSelectedIndex()));
		GWTServiceOcorrencia.Util.getInstance().getOcorrenciasPeloAluno(idAluno,
		
				new AsyncCallback<ArrayList<OcorrenciaAluno>>() {

					@Override
					public void onFailure(Throwable caught) {
						mpPanelLoading.setVisible(false);	
						mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
						mpDialogBoxWarning.setBodyText(txtConstants.ocorrenciaErroCarregar());
						mpDialogBoxWarning.show();
					}

					@Override
					public void onSuccess(ArrayList<OcorrenciaAluno> list) {
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
	
	private class MpAlunoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
				populateGrid();			
		}	  
	}
	
	public void updateClientData(){
		listBoxAluno.populateComboBox(this.telaInicialPaisOcorrencia.getMainView().getUsuarioLogado());
	}	

	
	private void addCellTableData(ListDataProvider<OcorrenciaAluno> dataProvider) {

		ListHandler<OcorrenciaAluno> sortHandler = new ListHandler<OcorrenciaAluno>(dataProvider.getList());

		cellTable.addColumnSortHandler(sortHandler);

		initSortHandler(sortHandler);

	}

	private void initTableColumns(final SelectionModel<OcorrenciaAluno> selectionModel) {
		
		
		paiCienteColumn = new Column<OcorrenciaAluno, Boolean>(new CheckboxCell(true, true)) {
			@Override
			public Boolean getValue(OcorrenciaAluno object) {
				return object.isPaiCiente();
			}
		};
		paiCienteColumn.setFieldUpdater(new FieldUpdater<OcorrenciaAluno, Boolean>() {
	        public void update(int index, OcorrenciaAluno object, Boolean value) {
	        	object.setPaiCiente(value);
	        	GWTServiceOcorrencia.Util.getInstance().AtualizarPaiCiente(object, callbackUpdateRow);
	        }
	    });		
		
		nomeCursoColumn = new Column<OcorrenciaAluno, String>(new TextCell()) {
			@Override
			public String getValue(OcorrenciaAluno object) {
				return object.getNomeCurso();
			}
		};		

		nomePeriodoColumn = new Column<OcorrenciaAluno, String>(new TextCell()) {
			@Override
			public String getValue(OcorrenciaAluno object) {
				return object.getNomePeriodo();
			}
		};
		nomeDisciplinaColumn = new Column<OcorrenciaAluno, String>(new TextCell()) {
			@Override
			public String getValue(OcorrenciaAluno object) {
				return object.getNomeDisciplina();
			}
		};
		nomeConteudoProgramaticoColumn = new Column<OcorrenciaAluno, String>(new TextCell()) {
			@Override
			public String getValue(OcorrenciaAluno object) {
				return object.getNomeConteudoProgramatico();
			}
		};		

		nomeOcorrenciaColumn = new Column<OcorrenciaAluno, String>(new TextCell()) {
			@Override
			public String getValue(OcorrenciaAluno object) {
				return object.getAssunto();
			}
		};
		
		nomeDescricaoColumn = new Column<OcorrenciaAluno, String>(new TextCell()) {
			@Override
			public String getValue(OcorrenciaAluno object) {
				return object.getDescricao();
			}
		};			


		dataColumn = new Column<OcorrenciaAluno, Date>(new DatePickerCell()) {
			@Override
			public Date getValue(OcorrenciaAluno object) {			    
				return MpUtilClient.convertStringToDate(object.getData());
			}
		};	

		horaColumn = new Column<OcorrenciaAluno, String>(new TextCell()) {
			@Override
			public String getValue(OcorrenciaAluno object) {
				return object.getHora();
			}
		};	


		cellTable.addColumn(paiCienteColumn, txtConstants.ocorrenciaPaiCiente());
		cellTable.addColumn(nomeOcorrenciaColumn, txtConstants.ocorrencia());
		cellTable.addColumn(nomeDescricaoColumn, txtConstants.ocorrenciaDescricao());
		cellTable.addColumn(dataColumn, txtConstants.ocorrenciaData());
		cellTable.addColumn(horaColumn, txtConstants.ocorrenciaHora());
		cellTable.addColumn(nomeCursoColumn, txtConstants.curso());
		cellTable.addColumn(nomePeriodoColumn, txtConstants.periodo());
		cellTable.addColumn(nomeDisciplinaColumn, txtConstants.disciplina());
		cellTable.addColumn(nomeConteudoProgramaticoColumn, txtConstants.conteudoProgramatico());		

		// Make the name column sortable.
//		nomeCursoColumn.setSortable(true);		

		cellTable.getColumn(cellTable.getColumnIndex(paiCienteColumn)).setCellStyleNames("css-checkbox");
		cellTable.getColumn(cellTable.getColumnIndex(nomeCursoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(nomePeriodoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(nomeDisciplinaColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(nomeConteudoProgramaticoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(nomeOcorrenciaColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(nomeDescricaoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(dataColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(horaColumn)).setCellStyleNames("hand-over-default");	
		
		
	}

	public void initSortHandler(ListHandler<OcorrenciaAluno> sortHandler) {
		
		paiCienteColumn.setSortable(true);
		sortHandler.setComparator(paiCienteColumn,new Comparator<OcorrenciaAluno>() {
			@Override
			public int compare(OcorrenciaAluno o1, OcorrenciaAluno o2) {
				Boolean booO1 = o1.isPaiCiente();
				Boolean booO2 = o2.isPaiCiente();
				return booO1.compareTo(booO2);
			}
		});	
		
		nomeOcorrenciaColumn.setSortable(true);
	    sortHandler.setComparator(nomeOcorrenciaColumn, new Comparator<OcorrenciaAluno>() {
	      @Override
	      public int compare(OcorrenciaAluno o1, OcorrenciaAluno o2) {
	        return o1.getAssunto().compareTo(o2.getAssunto());
	      }
	    });	
	    
	    nomeDescricaoColumn.setSortable(true);
	    sortHandler.setComparator(nomeDescricaoColumn, new Comparator<OcorrenciaAluno>() {
	      @Override
	      public int compare(OcorrenciaAluno o1, OcorrenciaAluno o2) {
	        return o1.getDescricao().compareTo(o2.getDescricao());
	      }
	    });			    
		
	    
	    dataColumn.setSortable(true);
	    sortHandler.setComparator(dataColumn, new Comparator<OcorrenciaAluno>() {
	      @Override
	      public int compare(OcorrenciaAluno o1, OcorrenciaAluno o2) {
	        return o1.getData().compareTo(o2.getData());
	      }
	    });		
	    
	    horaColumn.setSortable(true);
	    sortHandler.setComparator(horaColumn, new Comparator<OcorrenciaAluno>() {
	      @Override
	      public int compare(OcorrenciaAluno o1, OcorrenciaAluno o2) {
	        return o1.getHora().compareTo(o2.getHora());
	      }
	    });			    
	    
		nomeCursoColumn.setSortable(true);
	    sortHandler.setComparator(nomeCursoColumn, new Comparator<OcorrenciaAluno>() {
	      @Override
	      public int compare(OcorrenciaAluno o1, OcorrenciaAluno o2) {
	        return o1.getNomeCurso().compareTo(o2.getNomeCurso());
	      }
	    });	
	    
	    nomePeriodoColumn.setSortable(true);
	    sortHandler.setComparator(nomePeriodoColumn, new Comparator<OcorrenciaAluno>() {
	      @Override
	      public int compare(OcorrenciaAluno o1, OcorrenciaAluno o2) {
	        return o1.getNomePeriodo().compareTo(o2.getNomePeriodo());
	      }
	    });		
	    
	    nomeDisciplinaColumn.setSortable(true);
	    sortHandler.setComparator(nomeDisciplinaColumn, new Comparator<OcorrenciaAluno>() {
	      @Override
	      public int compare(OcorrenciaAluno o1, OcorrenciaAluno o2) {
	        return o1.getNomeDisciplina().compareTo(o2.getNomeDisciplina());
	      }
	    });	
	    
	    nomeConteudoProgramaticoColumn.setSortable(true);
	    sortHandler.setComparator(nomeConteudoProgramaticoColumn, new Comparator<OcorrenciaAluno>() {
	      @Override
	      public int compare(OcorrenciaAluno o1, OcorrenciaAluno o2) {
	        return o1.getNomeConteudoProgramatico().compareTo(o2.getNomeConteudoProgramatico());
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

					String strOcorrencia = dataProvider.getList().get(i).getAssunto();
					String strDescricao = dataProvider.getList().get(i).getDescricao();
//					String strData = MpUtilClient.convertDateToString(dataProvider.getList().get(i).getData(), "EEEE, MMMM dd, yyyy");
					String strData = dataProvider.getList().get(i).getData();
					String strHora = dataProvider.getList().get(i).getHora();
					String strCurso = dataProvider.getList().get(i).getNomeCurso();
					String strPeriodo = dataProvider.getList().get(i).getNomePeriodo();
					String strDisciplina = dataProvider.getList().get(i).getNomeDisciplina();
					String strMateria = dataProvider.getList().get(i).getNomeConteudoProgramatico();

					String strJuntaTexto = strOcorrencia.toUpperCase() + " " + strDescricao.toUpperCase() + " " + strData.toUpperCase() + " " + strHora.toUpperCase();
					strJuntaTexto +=  " " + strCurso.toUpperCase() + " " + strPeriodo.toUpperCase() + " " + strDisciplina.toUpperCase() + " " + strMateria.toUpperCase();

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
