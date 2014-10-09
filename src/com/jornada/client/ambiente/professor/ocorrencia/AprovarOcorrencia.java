package com.jornada.client.ambiente.professor.ocorrencia;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
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
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpConfirmDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceOcorrencia;
import com.jornada.shared.classes.RelUsuarioOcorrencia;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.ocorrencia.OcorrenciaParaAprovar;
import com.jornada.shared.classes.utility.MpUtilClient;

public class AprovarOcorrencia extends VerticalPanel {
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);

	boolean isFirstTime=true;
	
	RadioButton radioButtonYes;
	RadioButton radioButtonNo;
	
	private int intSelectedIndexToDelete;
	
//	private AsyncCallback<Boolean> callbackUpdateRow;
	
	private CellTable<OcorrenciaParaAprovar> cellTable;
	private Column<OcorrenciaParaAprovar, Boolean> liberarLeituraPaiColumn;
	private Column<OcorrenciaParaAprovar, Boolean> paiCienteColumn;
	private Column<OcorrenciaParaAprovar, String> nomeAlunoColumn;
	private Column<OcorrenciaParaAprovar, String> nomeCursoColumn;
	private Column<OcorrenciaParaAprovar, String> nomePeriodoColumn;
	private Column<OcorrenciaParaAprovar, String> nomeDisciplinaColumn;
//	private Column<OcorrenciaParaAprovar, String> nomeConteudoProgramaticoColumn;
	private Column<OcorrenciaParaAprovar, String> nomeOcorrenciaColumn;
	private Column<OcorrenciaParaAprovar, String> nomeDescricaoColumn;
	private Column<OcorrenciaParaAprovar, Date> dataColumn;
	private Column<OcorrenciaParaAprovar, String> horaColumn;	
	private ListDataProvider<OcorrenciaParaAprovar> dataProvider = new ListDataProvider<OcorrenciaParaAprovar>();
	
	private TextBox txtSearch;
	ArrayList<OcorrenciaParaAprovar> arrayListBackup = new ArrayList<OcorrenciaParaAprovar>();

//	private MpSelectionAlunoAmbientePais listBoxAluno;

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	
	TelaInicialProfessorOcorrencia telaInicialProfessorOcorrencia;
	
	Usuario usuarioLogado;
		
	
	private static AprovarOcorrencia uniqueInstance;
	
	
	public static AprovarOcorrencia getInstance(TelaInicialProfessorOcorrencia telaInicialProfessorOcorrencia){
		
		if(uniqueInstance==null){
			uniqueInstance = new AprovarOcorrencia(telaInicialProfessorOcorrencia);
		}
		
		return uniqueInstance;
	}

	private  AprovarOcorrencia(TelaInicialProfessorOcorrencia telaInicialProfessorOcorrencia) {
		
		
		this.telaInicialProfessorOcorrencia = telaInicialProfessorOcorrencia;
	    
		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);		

		
		Grid gridUseTemplate = new Grid(1,5);

		
		Label lblFiltroPor = new Label(txtConstants.ocorrenciaFiltrar());
		radioButtonYes = new RadioButton("useTemplate",txtConstants.ocorrenciaParaAprovar());
		radioButtonYes.addValueChangeHandler(new ValueChangeHandlerYes());
		radioButtonNo = new RadioButton("useTemplate", txtConstants.ocorrenciaJaAprovadas());
		radioButtonNo.addValueChangeHandler(new ValueChangeHandlerNo());
		radioButtonYes.setValue(true);
		
		gridUseTemplate.setWidget(0, 0, lblFiltroPor);
		gridUseTemplate.setWidget(0, 1, radioButtonYes);
		gridUseTemplate.setWidget(0, 2, radioButtonNo);
		gridUseTemplate.setWidget(0, 3, new InlineHTML("&nbsp;"));
		gridUseTemplate.setWidget(0, 4, mpPanelLoading);
		
		usuarioLogado = this.telaInicialProfessorOcorrencia.getMainView().getUsuarioLogado();



		Label lblEmpty1 = new Label(txtConstants.ocorrenciaNehumaOcorrencia());

		cellTable = new CellTable<OcorrenciaParaAprovar>(5,GWT.<CellTableStyle> create(CellTableStyle.class));
//		cellTable.setWidth(Integer.toString(TelaInicialAlunoOcorrencia.intWidthTable)+ "px");		
		cellTable.setWidth("100%");
		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);

		cellTable.setEmptyTableWidget(lblEmpty1);

		final SelectionModel<OcorrenciaParaAprovar> selectionModel = new MultiSelectionModel<OcorrenciaParaAprovar>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<OcorrenciaParaAprovar> createCheckboxManager());
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
		
		
		
		VerticalPanel vPanelEditGrid = new VerticalPanel();		
	
		
		vPanelEditGrid.add(gridUseTemplate);
		
		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(TelaInicialProfessorOcorrencia.intWidthTable+30)+"px",Integer.toString(TelaInicialProfessorOcorrencia.intHeightTable-110)+"px");
		scrollPanel.setHeight(Integer.toString(TelaInicialProfessorOcorrencia.intHeightTable-110)+"px");
		scrollPanel.setWidth("100%");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(cellTable);				
		
//		vPanelEditGrid.add(mpPager);
		vPanelEditGrid.add(flexTableFiltrar);
		vPanelEditGrid.add(scrollPanel);	
		vPanelEditGrid.setWidth("100%");
		
		populateGrid();

		this.setWidth("100%");
		super.add(vPanelEditGrid);

	}



	/**************** Begin Event Handlers *****************/


	/**************** End Event Handlers *****************/

	protected void populateGrid() {
		mpPanelLoading.setVisible(true);	
		isFirstTime=true;
		
		boolean buscarOcorrenciasParaAprovar = radioButtonYes.getValue();
		
		
		//Se for para buscar os pais que faltam para aprovar temos que buscar no banco os pais que faltam receber true;
		if(buscarOcorrenciasParaAprovar){
			buscarOcorrenciasParaAprovar=false;
		}else{
			buscarOcorrenciasParaAprovar = true;
		}
		
//		String locale = LocaleInfo.getCurrentLocale().getLocaleName();
		GWTServiceOcorrencia.Util.getInstance().getOcorrenciasParaAprovar(buscarOcorrenciasParaAprovar,
		
				new AsyncCallback<ArrayList<OcorrenciaParaAprovar>>() {

					@Override
					public void onFailure(Throwable caught) {
						mpPanelLoading.setVisible(false);	
						mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
						mpDialogBoxWarning.setBodyText(txtConstants.ocorrenciaErroCarregar());
						mpDialogBoxWarning.show();
						System.out.println(caught.getMessage());
					}

					@Override
					public void onSuccess(ArrayList<OcorrenciaParaAprovar> list) {
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
	
//	private class MpAlunoSelectionChangeHandler implements ChangeHandler {
//		public void onChange(ChangeEvent event) {
//				populateGrid();			
//		}	  
//	}
	

	public void updateClientData(){		

	    populateGrid();
//		if(usuarioLogado.getIdTipoUsuario()==TipoUsuario.ALUNO){
//			populateGrid();
//		}else{			
//			listBoxAluno.populateComboBox(usuarioLogado);
//		}
	}	
	
	
	private void addCellTableData(ListDataProvider<OcorrenciaParaAprovar> dataProvider) {

		ListHandler<OcorrenciaParaAprovar> sortHandler = new ListHandler<OcorrenciaParaAprovar>(dataProvider.getList());

		cellTable.addColumnSortHandler(sortHandler);

		initSortHandler(sortHandler);

	}

	private void initTableColumns(final SelectionModel<OcorrenciaParaAprovar> selectionModel) {
		
		
		liberarLeituraPaiColumn = new Column<OcorrenciaParaAprovar, Boolean>(new CheckboxCell(true, true)) {
			@Override
			public Boolean getValue(OcorrenciaParaAprovar object) {
				return object.isLiberarLeituraPai();
			}
		};		
		
		liberarLeituraPaiColumn.setFieldUpdater(new FieldUpdater<OcorrenciaParaAprovar, Boolean>() {
	        public void update(int index, OcorrenciaParaAprovar object, Boolean value) {
	        	object.setLiberarLeituraPai(value);
	        	
	        	RelUsuarioOcorrencia ruo = new RelUsuarioOcorrencia();
	        	ruo.setIdOcorrencia(object.getIdOcorrencia());
	        	ruo.setIdUsuario(object.getIdUsuario());
	        	ruo.setLiberarLeituraPai(object.isLiberarLeituraPai());
	        	
	        	
	        	GWTServiceOcorrencia.Util.getInstance().AtualizarLiberarPaiLeitura(ruo, new callbackUpdateRow());
	        }
	    });			
		
		paiCienteColumn = new Column<OcorrenciaParaAprovar, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(OcorrenciaParaAprovar object) {
				return object.isPaiCiente();
			}
		};
		
		nomeCursoColumn = new Column<OcorrenciaParaAprovar, String>(new TextCell()) {
			@Override
			public String getValue(OcorrenciaParaAprovar object) {
				return object.getNomeCurso();
			}
		};		

		nomePeriodoColumn = new Column<OcorrenciaParaAprovar, String>(new TextCell()) {
			@Override
			public String getValue(OcorrenciaParaAprovar object) {
				return object.getNomePeriodo();
			}
		};
		nomeDisciplinaColumn = new Column<OcorrenciaParaAprovar, String>(new TextCell()) {
			@Override
			public String getValue(OcorrenciaParaAprovar object) {
				return object.getNomeDisciplina();
			}
		};
//		nomeConteudoProgramaticoColumn = new Column<OcorrenciaParaAprovar, String>(new TextCell()) {
//			@Override
//			public String getValue(OcorrenciaParaAprovar object) {
//				return object.getNomeConteudoProgramatico();
//			}
//		};
		
		nomeAlunoColumn = new Column<OcorrenciaParaAprovar, String>(new TextCell()) {
			@Override
			public String getValue(OcorrenciaParaAprovar object) {
				return object.getUsuarioPrimeiroNome() + " " + object.getUsuarioSobreNome();
			}
		};
		
		

		nomeOcorrenciaColumn = new Column<OcorrenciaParaAprovar, String>(new TextCell()) {
			@Override
			public String getValue(OcorrenciaParaAprovar object) {
				return object.getAssunto();
			}
		};
		
		nomeDescricaoColumn = new Column<OcorrenciaParaAprovar, String>(new TextCell()) {
			@Override
			public String getValue(OcorrenciaParaAprovar object) {
				return object.getDescricao();
			}
		};			


		dataColumn = new Column<OcorrenciaParaAprovar, Date>(new DatePickerCell()) {
			@Override
			public Date getValue(OcorrenciaParaAprovar object) {			    
//				return MpUtilClient.convertStringToDate(object.getData());
				return object.getData();
			}
		};	

		horaColumn = new Column<OcorrenciaParaAprovar, String>(new TextCell()) {
			@Override
			public String getValue(OcorrenciaParaAprovar object) {
				return object.getHora();
			}
		};	
		
		Column<OcorrenciaParaAprovar, String> removeColumn = new Column<OcorrenciaParaAprovar, String>(new MyImageCellDelete()) {
			@Override
			public String getValue(OcorrenciaParaAprovar object) {
				return "images/delete.png";
			}
		};

		cellTable.addColumn(liberarLeituraPaiColumn, txtConstants.ocorrenciaLiberarLeituraPai());
		cellTable.addColumn(paiCienteColumn, txtConstants.ocorrenciaPaiCiente());
		cellTable.addColumn(removeColumn, txtConstants.geralRemover());
		cellTable.addColumn(nomeAlunoColumn, txtConstants.aluno());
		cellTable.addColumn(nomeCursoColumn, txtConstants.curso());
		cellTable.addColumn(nomePeriodoColumn, txtConstants.periodo());
		cellTable.addColumn(nomeDisciplinaColumn, txtConstants.disciplina());
//		cellTable.addColumn(nomeConteudoProgramaticoColumn, txtConstants.conteudoProgramatico());
		cellTable.addColumn(dataColumn, txtConstants.ocorrenciaData());		
		cellTable.addColumn(nomeOcorrenciaColumn, txtConstants.ocorrencia());
		cellTable.addColumn(nomeDescricaoColumn, txtConstants.ocorrenciaDescricao());
		cellTable.addColumn(horaColumn, txtConstants.ocorrenciaHora());
		
	

		cellTable.getColumn(cellTable.getColumnIndex(liberarLeituraPaiColumn)).setCellStyleNames("css-checkbox");
		cellTable.getColumn(cellTable.getColumnIndex(paiCienteColumn)).setCellStyleNames("css-checkbox");
		cellTable.getColumn(cellTable.getColumnIndex(nomeCursoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(nomePeriodoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(nomeDisciplinaColumn)).setCellStyleNames("hand-over-default");
//		cellTable.getColumn(cellTable.getColumnIndex(nomeConteudoProgramaticoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(nomeOcorrenciaColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(nomeDescricaoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(dataColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(horaColumn)).setCellStyleNames("hand-over-default");	
		cellTable.getColumn(cellTable.getColumnIndex(removeColumn)).setCellStyleNames("hand-over");
		
		
	}

	public void initSortHandler(ListHandler<OcorrenciaParaAprovar> sortHandler) {

		
		liberarLeituraPaiColumn.setSortable(true);
		sortHandler.setComparator(paiCienteColumn,new Comparator<OcorrenciaParaAprovar>() {
			@Override
			public int compare(OcorrenciaParaAprovar o1, OcorrenciaParaAprovar o2) {
				Boolean booO1 = o1.isLiberarLeituraPai();
				Boolean booO2 = o2.isLiberarLeituraPai();
				return booO1.compareTo(booO2);
			}
		});			
		
		paiCienteColumn.setSortable(true);
		sortHandler.setComparator(paiCienteColumn,new Comparator<OcorrenciaParaAprovar>() {
			@Override
			public int compare(OcorrenciaParaAprovar o1, OcorrenciaParaAprovar o2) {
				Boolean booO1 = o1.isPaiCiente();
				Boolean booO2 = o2.isPaiCiente();
				return booO1.compareTo(booO2);
			}
		});	
		
		nomeAlunoColumn.setSortable(true);
	    sortHandler.setComparator(nomeAlunoColumn, new Comparator<OcorrenciaParaAprovar>() {
	      @Override
	      public int compare(OcorrenciaParaAprovar o1, OcorrenciaParaAprovar o2) {
	    	  String nomeO1 = o1.getUsuarioPrimeiroNome() + " " +o1.getUsuarioSobreNome();
	    	  String nomeO2 = o2.getUsuarioPrimeiroNome() + " " +o2.getUsuarioSobreNome();
	    	  
	        return nomeO1.compareTo(nomeO2);
	      }
	    });			
		
		nomeOcorrenciaColumn.setSortable(true);
	    sortHandler.setComparator(nomeOcorrenciaColumn, new Comparator<OcorrenciaParaAprovar>() {
	      @Override
	      public int compare(OcorrenciaParaAprovar o1, OcorrenciaParaAprovar o2) {
	        return o1.getAssunto().compareTo(o2.getAssunto());
	      }
	    });	
	    
	    nomeDescricaoColumn.setSortable(true);
	    sortHandler.setComparator(nomeDescricaoColumn, new Comparator<OcorrenciaParaAprovar>() {
	      @Override
	      public int compare(OcorrenciaParaAprovar o1, OcorrenciaParaAprovar o2) {
	        return o1.getDescricao().compareTo(o2.getDescricao());
	      }
	    });			    
		
	    
	    dataColumn.setSortable(true);
	    sortHandler.setComparator(dataColumn, new Comparator<OcorrenciaParaAprovar>() {
	      @Override
	      public int compare(OcorrenciaParaAprovar o1, OcorrenciaParaAprovar o2) {
	        return o1.getData().compareTo(o2.getData());
	      }
	    });		
	    
	    horaColumn.setSortable(true);
	    sortHandler.setComparator(horaColumn, new Comparator<OcorrenciaParaAprovar>() {
	      @Override
	      public int compare(OcorrenciaParaAprovar o1, OcorrenciaParaAprovar o2) {
	        return o1.getHora().compareTo(o2.getHora());
	      }
	    });			    
	    
		nomeCursoColumn.setSortable(true);
	    sortHandler.setComparator(nomeCursoColumn, new Comparator<OcorrenciaParaAprovar>() {
	      @Override
	      public int compare(OcorrenciaParaAprovar o1, OcorrenciaParaAprovar o2) {
	        return o1.getNomeCurso().compareTo(o2.getNomeCurso());
	      }
	    });	
	    
	    nomePeriodoColumn.setSortable(true);
	    sortHandler.setComparator(nomePeriodoColumn, new Comparator<OcorrenciaParaAprovar>() {
	      @Override
	      public int compare(OcorrenciaParaAprovar o1, OcorrenciaParaAprovar o2) {
	        return o1.getNomePeriodo().compareTo(o2.getNomePeriodo());
	      }
	    });		
	    
	    nomeDisciplinaColumn.setSortable(true);
	    sortHandler.setComparator(nomeDisciplinaColumn, new Comparator<OcorrenciaParaAprovar>() {
	      @Override
	      public int compare(OcorrenciaParaAprovar o1, OcorrenciaParaAprovar o2) {
	        return o1.getNomeDisciplina().compareTo(o2.getNomeDisciplina());
	      }
	    });	
	    
//	    nomeConteudoProgramaticoColumn.setSortable(true);
//	    sortHandler.setComparator(nomeConteudoProgramaticoColumn, new Comparator<OcorrenciaParaAprovar>() {
//	      @Override
//	      public int compare(OcorrenciaParaAprovar o1, OcorrenciaParaAprovar o2) {
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
					String strNomeAluno = dataProvider.getList().get(i).getUsuarioPrimeiroNome();
					strNomeAluno += " " + dataProvider.getList().get(i).getUsuarioSobreNome();
					String strData = MpUtilClient.convertDateToString(dataProvider.getList().get(i).getData(), "EEEE, MMMM dd, yyyy");
//					String strData = dataProvider.getList().get(i).getData();
					String strHora = dataProvider.getList().get(i).getHora();
					String strCurso = dataProvider.getList().get(i).getNomeCurso();
					String strPeriodo = dataProvider.getList().get(i).getNomePeriodo();
					String strDisciplina = dataProvider.getList().get(i).getNomeDisciplina();
//					String strMateria = dataProvider.getList().get(i).getNomeConteudoProgramatico();

					String strJuntaTexto = strOcorrencia.toUpperCase() + " " + strDescricao.toUpperCase() + " " + strData.toUpperCase() + " " + strHora.toUpperCase();
					strJuntaTexto +=  " " + strCurso.toUpperCase() + " " + strPeriodo.toUpperCase() + " " + strDisciplina.toUpperCase() ;
					strJuntaTexto += " " + strNomeAluno.toUpperCase();

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

		private class ValueChangeHandlerYes implements ValueChangeHandler<Boolean>{
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
	            if(event.getValue() == true){
	            	populateGrid();
	            }			
			}
		}
		
		private class ValueChangeHandlerNo implements ValueChangeHandler<Boolean>{
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
	            if(event.getValue() == true){
	            	populateGrid();
	            	
	            }			
			}
		}		
		
		
		private class callbackUpdateRow implements AsyncCallback<Boolean> {

			public void onSuccess(Boolean success) {
//				paiCienteColumn.getValue(object);
				System.out.println("success");

			}

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.ocorrenciaErroAtualizar());
				mpDialogBoxWarning.showDialog();
			}
		};	
		
		private class callbackDelete implements AsyncCallback<Boolean> {

			public void onSuccess(Boolean success) {

				if (success == true) {
//					populateGrid();
					dataProvider.getList().remove(intSelectedIndexToDelete);
					dataProvider.refresh();	
					telaInicialProfessorOcorrencia.updateEditarOcorrenciaPopulateGrid();
					telaInicialProfessorOcorrencia.updateVisualizarOcorrenciaPopulateGrid();
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
		
		
		private class MyImageCellDelete extends ImageCell {

			@Override
			public Set<String> getConsumedEvents() {
				Set<String> consumedEvents = new HashSet<String>();
				consumedEvents.add("click");
				return consumedEvents;
			}

			@Override
			public void onBrowserEvent(Context context, Element parent,String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
				switch (DOM.eventGetType((Event) event)) {
				case Event.ONCLICK:
					
					final OcorrenciaParaAprovar object = (OcorrenciaParaAprovar) context.getKey();
					intSelectedIndexToDelete = context.getIndex();
					@SuppressWarnings("unused")
					CloseHandler<PopupPanel> closeHandler;

					MpConfirmDialogBox confirmationDialog = new MpConfirmDialogBox(
							txtConstants.geralRemover(), txtConstants.ocorrenciaDesejaRemover(object.getAssunto()), txtConstants.geralSim(), txtConstants.geralNao(),

							closeHandler = new CloseHandler<PopupPanel>() {

								public void onClose(CloseEvent<PopupPanel> event) {

									MpConfirmDialogBox x = (MpConfirmDialogBox) event.getSource();

									if (x.primaryActionFired()) {

										GWTServiceOcorrencia.Util.getInstance().deletarRelacionamentoUsuarioOcorrencia(object.getIdOcorrencia(), object.getIdUsuario(), new callbackDelete());
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
	
	

}
