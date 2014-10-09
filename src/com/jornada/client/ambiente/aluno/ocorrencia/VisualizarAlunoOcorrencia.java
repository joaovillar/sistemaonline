package com.jornada.client.ambiente.aluno.ocorrencia;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceOcorrencia;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.ocorrencia.OcorrenciaAluno;
import com.jornada.shared.classes.utility.MpUtilClient;

public class VisualizarAlunoOcorrencia extends VerticalPanel {
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);

	boolean isFirstTime=true;
	
//	private AsyncCallback<Boolean> callbackUpdateRow;
	
	private CellTable<OcorrenciaAluno> cellTable;
	private Column<OcorrenciaAluno, Boolean> paiCienteColumn;
	private Column<OcorrenciaAluno, String> nomeCursoColumn;
	private Column<OcorrenciaAluno, String> nomePeriodoColumn;
	private Column<OcorrenciaAluno, String> nomeDisciplinaColumn;
//	private Column<OcorrenciaAluno, String> nomeConteudoProgramaticoColumn;
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
	
	TelaInicialAlunoOcorrencia telaInicialAlunoOcorrencia;
	
	Usuario usuarioLogado;
		
	
	private static VisualizarAlunoOcorrencia uniqueInstance;
	
	
	public static VisualizarAlunoOcorrencia getInstance(TelaInicialAlunoOcorrencia telaInicialAlunoOcorrencia){
		
		if(uniqueInstance==null){
			uniqueInstance = new VisualizarAlunoOcorrencia(telaInicialAlunoOcorrencia);
		}
		
		return uniqueInstance;
	}

	private  VisualizarAlunoOcorrencia(TelaInicialAlunoOcorrencia telaInicialAlunoOcorrencia) {
		
		
		this.telaInicialAlunoOcorrencia = telaInicialAlunoOcorrencia;
	    
		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);		

		Label lblAluno = new Label(txtConstants.aluno());
		
		usuarioLogado = this.telaInicialAlunoOcorrencia.getMainView().getUsuarioLogado();



		Label lblEmpty1 = new Label(txtConstants.ocorrenciaNehumaOcorrencia());

		cellTable = new CellTable<OcorrenciaAluno>(15,GWT.<CellTableStyle> create(CellTableStyle.class));
//		cellTable.setWidth(Integer.toString(TelaInicialAlunoOcorrencia.intWidthTable)+ "px");		
//		cellTable.setWidth("1500px");
		cellTable.setWidth("100%");
		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);

		cellTable.setEmptyTableWidget(lblEmpty1);

		final SelectionModel<OcorrenciaAluno> selectionModel = new MultiSelectionModel<OcorrenciaAluno>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<OcorrenciaAluno> createCheckboxManager());
		initTableColumns(selectionModel);
		
		dataProvider.addDataDisplay(cellTable);	
		
		MpSimplePager mpPager = new MpSimplePager();
		mpPager.setDisplay(cellTable);
//		mpPager.setPageSize(15);	
		
		
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
		
		
		if(usuarioLogado.getIdTipoUsuario()==TipoUsuario.ADMINISTRADOR || usuarioLogado.getIdTipoUsuario()==TipoUsuario.COORDENADOR)
		{
			Grid gridComboBox = new Grid(1, 6);
			gridComboBox.setCellSpacing(2);
			gridComboBox.setCellPadding(2);
			
			
			listBoxAluno = new MpSelectionAlunoAmbientePais(usuarioLogado);
			listBoxAluno.setWidth("250px");
			listBoxAluno.addChangeHandler(new MpAlunoSelectionChangeHandler());
			
			gridComboBox.setWidget(0, 0, lblAluno);
			gridComboBox.setWidget(0, 1, listBoxAluno);
			gridComboBox.setWidget(0, 2, new InlineHTML("&nbsp;"));
			gridComboBox.setWidget(0, 3, mpPanelLoading);
			vPanelEditGrid.add(gridComboBox);
		}		
		
		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(TelaInicialAlunoOcorrencia.intWidthTable+30)+"px",Integer.toString(TelaInicialAlunoOcorrencia.intHeightTable-110)+"px");
		scrollPanel.setHeight(Integer.toString(TelaInicialAlunoOcorrencia.intHeightTable-110)+"px");
		scrollPanel.setWidth("100%");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(cellTable);				
		
//		vPanelEditGrid.add(mpPager);
		vPanelEditGrid.add(flexTableFiltrar);
		vPanelEditGrid.add(scrollPanel);
		vPanelEditGrid.setWidth("100%");
		
//		callbackUpdateRow = new AsyncCallback<Boolean>() {
//			public void onSuccess(Boolean success) {
////				paiCienteColumn.getValue(object);
//
//			}
//
//			public void onFailure(Throwable caught) {
//				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//				mpDialogBoxWarning.setBodyText(txtConstants.ocorrenciaErroAtualizar());
//				mpDialogBoxWarning.showDialog();
//			}
//		};		


		if(usuarioLogado.getIdTipoUsuario()==TipoUsuario.ALUNO){
			populateGrid();
		}
		
//		populateGrid();

		this.setWidth("100%");
		super.add(vPanelEditGrid);

	}



	/**************** Begin Event Handlers *****************/


	/**************** End Event Handlers *****************/

	protected void populateGrid() {
		mpPanelLoading.setVisible(true);	
		isFirstTime=true;
		int idAluno = 0;
		switch(usuarioLogado.getIdTipoUsuario()){
			case TipoUsuario.ADMINISTRADOR : idAluno = Integer.parseInt(listBoxAluno.getValue(listBoxAluno.getSelectedIndex()));break;
			case TipoUsuario.COORDENADOR : idAluno = Integer.parseInt(listBoxAluno.getValue(listBoxAluno.getSelectedIndex()));break;
			case TipoUsuario.ALUNO : idAluno = usuarioLogado.getIdUsuario();break;
			default : idAluno=0;break;
		}
		
//		String locale = LocaleInfo.getCurrentLocale().getLocaleName();
		GWTServiceOcorrencia.Util.getInstance().getOcorrenciasPeloAluno(idAluno,
		
				new AsyncCallback<ArrayList<OcorrenciaAluno>>() {

					@Override
					public void onFailure(Throwable caught) {
						mpPanelLoading.setVisible(false);	
						mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
						mpDialogBoxWarning.setBodyText(txtConstants.ocorrenciaErroCarregar());
						mpDialogBoxWarning.show();
						System.out.println(caught.getMessage());
					}

					@Override
					public void onSuccess(ArrayList<OcorrenciaAluno> list) {
						
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
	
	private class MpAlunoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
				populateGrid();			
		}	  
	}
	

	public void updateClientData(){		
		if(usuarioLogado.getIdTipoUsuario()==TipoUsuario.ALUNO){
			populateGrid();
		}else{			
			listBoxAluno.populateComboBox(usuarioLogado);
		}
	}	
	
	
	private void addCellTableData(ListDataProvider<OcorrenciaAluno> dataProvider) {

		ListHandler<OcorrenciaAluno> sortHandler = new ListHandler<OcorrenciaAluno>(dataProvider.getList());

		cellTable.addColumnSortHandler(sortHandler);

		initSortHandler(sortHandler);

	}

	private void initTableColumns(final SelectionModel<OcorrenciaAluno> selectionModel) {
		
		
		paiCienteColumn = new Column<OcorrenciaAluno, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(OcorrenciaAluno object) {
				return object.isPaiCiente();
			}
		};
		
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
//		nomeConteudoProgramaticoColumn = new Column<OcorrenciaAluno, String>(new TextCell()) {
//			@Override
//			public String getValue(OcorrenciaAluno object) {
//				return object.getNomeConteudoProgramatico();
//			}
//		};		

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
//				return MpUtilClient.convertStringToDate(object.getData());
				return object.getData();
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
//		cellTable.addColumn(nomeConteudoProgramaticoColumn, txtConstants.conteudoProgramatico());		

		cellTable.getColumn(cellTable.getColumnIndex(paiCienteColumn)).setCellStyleNames("css-checkbox");
		cellTable.getColumn(cellTable.getColumnIndex(nomeCursoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(nomePeriodoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(nomeDisciplinaColumn)).setCellStyleNames("hand-over-default");
//		cellTable.getColumn(cellTable.getColumnIndex(nomeConteudoProgramaticoColumn)).setCellStyleNames("hand-over-default");
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
	    
//	    nomeConteudoProgramaticoColumn.setSortable(true);
//	    sortHandler.setComparator(nomeConteudoProgramaticoColumn, new Comparator<OcorrenciaAluno>() {
//	      @Override
//	      public int compare(OcorrenciaAluno o1, OcorrenciaAluno o2) {
//	        return o1.getNomeConteudoProgramatico().compareTo(o2.getNomeConteudoProgramatico());
//	      }
//	    });		    
		
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

					String strOcorrencia = dataProvider.getList().get(i).getAssunto();
					String strDescricao = dataProvider.getList().get(i).getDescricao();
					String strData = MpUtilClient.convertDateToString(dataProvider.getList().get(i).getData(), "EEEE, MMMM dd, yyyy");
//					String strData = dataProvider.getList().get(i).getData();
					String strHora = dataProvider.getList().get(i).getHora();
					String strCurso = dataProvider.getList().get(i).getNomeCurso();
					String strPeriodo = dataProvider.getList().get(i).getNomePeriodo();
					String strDisciplina = dataProvider.getList().get(i).getNomeDisciplina();
//					String strMateria = dataProvider.getList().get(i).getNomeConteudoProgramatico();

					String strJuntaTexto = strOcorrencia.toUpperCase() + " " + strDescricao.toUpperCase() + " " + strData.toUpperCase() + " " + strHora.toUpperCase();
					strJuntaTexto +=  " " + strCurso.toUpperCase() + " " + strPeriodo.toUpperCase() + " " + strDisciplina.toUpperCase();

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
