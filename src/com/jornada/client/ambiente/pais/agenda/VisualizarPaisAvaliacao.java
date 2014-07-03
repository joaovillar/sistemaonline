package com.jornada.client.ambiente.pais.agenda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.ambiente.aluno.agenda.TelaInicialAlunoAgenda;
import com.jornada.client.classes.listBoxes.ambiente.pais.MpSelectionCursoAmbientePais;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceAvaliacao;
import com.jornada.shared.classes.CursoAvaliacao;

public class VisualizarPaisAvaliacao extends VerticalPanel {
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);

	private CellTable<CursoAvaliacao> cellTable;
	private Column<CursoAvaliacao, String> nomePeriodoColumn;
	private Column<CursoAvaliacao, String> nomeDisciplinaColumn;
	private Column<CursoAvaliacao, String> nomeConteudoProgramaticoColumn;
	private Column<CursoAvaliacao, String> nomeAvaliacaoColumn;
	private Column<CursoAvaliacao, Date> dataColumn;
	private Column<CursoAvaliacao, String> horaColumn;	
	private ListDataProvider<CursoAvaliacao> dataProvider = new ListDataProvider<CursoAvaliacao>();

	private MpSelectionCursoAmbientePais listBoxCurso;


	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	
	private TelaInicialPaisAgenda telaInicialPaisAgenda;
	
	
	private static VisualizarPaisAvaliacao uniqueInstance;
	
	
	public static VisualizarPaisAvaliacao getInstance(TelaInicialPaisAgenda telaInicialPaisAgenda){
		
		if(uniqueInstance==null){
			uniqueInstance = new VisualizarPaisAvaliacao(telaInicialPaisAgenda);
		}
		return uniqueInstance;
	}

	private  VisualizarPaisAvaliacao(TelaInicialPaisAgenda telaInicialPaisAgenda) {
		
		this.telaInicialPaisAgenda=telaInicialPaisAgenda;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);		

		Label lblCursoEdit = new Label(txtConstants.curso());

		listBoxCurso = new MpSelectionCursoAmbientePais(telaInicialPaisAgenda.getMainView().getUsuarioLogado());
		listBoxCurso.setWidth("250px");
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());

		Grid gridComboBox = new Grid(1, 6);
		gridComboBox.setCellSpacing(2);
		gridComboBox.setCellPadding(2);
		{

			gridComboBox.setWidget(0, 0, lblCursoEdit);
			gridComboBox.setWidget(0, 1, listBoxCurso);
			gridComboBox.setWidget(0, 2, new InlineHTML("&nbsp;"));
			gridComboBox.setWidget(0, 3, mpPanelLoading);
			
		}

		Label lblEmpty = new Label(txtConstants.avaliacaoNenhumaCurso());
//		Label lblEmpty2 = new Label("Por favor, selecione o curso.");

		cellTable = new CellTable<CursoAvaliacao>(5,GWT.<CellTableStyle> create(CellTableStyle.class));
		cellTable.setWidth(Integer.toString(TelaInicialAlunoAgenda.intWidthTable)+ "px");	
		cellTable.setEmptyTableWidget(lblEmpty);

		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);

		final SelectionModel<CursoAvaliacao> selectionModel = new MultiSelectionModel<CursoAvaliacao>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<CursoAvaliacao> createCheckboxManager());
		initTableColumns(selectionModel);	
		
		dataProvider.addDataDisplay(cellTable);	
		
		MpSimplePager mpPager = new MpSimplePager();
		mpPager.setDisplay(cellTable);
		mpPager.setPageSize(15);			

		
		VerticalPanel vPanelEditGrid = new VerticalPanel();		
		vPanelEditGrid.add(gridComboBox);
		vPanelEditGrid.add(mpPager);
		vPanelEditGrid.add(cellTable);




		super.add(vPanelEditGrid);

	}



	/**************** Begin Event Handlers *****************/


	/**************** End Event Handlers *****************/

	protected void populateGrid() {
		mpPanelLoading.setVisible(true);	
		String locale = LocaleInfo.getCurrentLocale().getLocaleName();
		int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
		GWTServiceAvaliacao.Util.getInstance().getAvaliacaoPeloCurso(idCurso,locale,
		
				new AsyncCallback<ArrayList<CursoAvaliacao>>() {

					@Override
					public void onFailure(Throwable caught) {
						mpPanelLoading.setVisible(false);	
						mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
						mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroCarregar());
					}

					@Override
					public void onSuccess(ArrayList<CursoAvaliacao> list) {
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
	
	private class MpCursoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
				populateGrid();			
		}	  
	}
	
	
	public void updateClientData(){
		listBoxCurso.populateComboBox(this.telaInicialPaisAgenda.getMainView().getUsuarioLogado());
	}	
	
	
	
	
	private void addCellTableData(ListDataProvider<CursoAvaliacao> dataProvider) {

		ListHandler<CursoAvaliacao> sortHandler = new ListHandler<CursoAvaliacao>(dataProvider.getList());

		cellTable.addColumnSortHandler(sortHandler);

		initSortHandler(sortHandler);

	}

	private void initTableColumns(final SelectionModel<CursoAvaliacao> selectionModel) {
		
		nomePeriodoColumn = new Column<CursoAvaliacao, String>(new TextCell()) {
			@Override
			public String getValue(CursoAvaliacao object) {
				return object.getNomePeriodo();
			}
		};

		nomeDisciplinaColumn = new Column<CursoAvaliacao, String>(new TextCell()) {
			@Override
			public String getValue(CursoAvaliacao object) {
				return object.getNomeDisciplina();
			}
		};


		nomeConteudoProgramaticoColumn = new Column<CursoAvaliacao, String>(new TextCell()) {
			@Override
			public String getValue(CursoAvaliacao object) {
				return object.getNomeConteudoProgramatico();
			}
		};
		
		nomeAvaliacaoColumn = new Column<CursoAvaliacao, String>(new TextCell()) {
			@Override
			public String getValue(CursoAvaliacao object) {
				return object.getNomeAvaliacao();
			}
		};		


		dataColumn = new Column<CursoAvaliacao, Date>(new DatePickerCell()) {
			@Override
			public Date getValue(CursoAvaliacao object) {			    
				return object.getDataAvaliacao();
			}
		};	

		horaColumn = new Column<CursoAvaliacao, String>(new TextCell()) {
			@Override
			public String getValue(CursoAvaliacao object) {
				return object.getHoraAvaliacao();
			}
		};	

		//cellTable.addColumn(nomeCursoColumn, "Curso");

		cellTable.addColumn(nomeAvaliacaoColumn, txtConstants.avaliacao());
		cellTable.addColumn(dataColumn, txtConstants.avaliacaoData());
		cellTable.addColumn(horaColumn, txtConstants.avaliacaoHora());
		cellTable.addColumn(nomePeriodoColumn, txtConstants.periodo());
		cellTable.addColumn(nomeDisciplinaColumn,  txtConstants.disciplina());
		cellTable.addColumn(nomeConteudoProgramaticoColumn, txtConstants.conteudoProgramatico());	

		cellTable.getColumn(cellTable.getColumnIndex(nomeAvaliacaoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(dataColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(horaColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(nomePeriodoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(nomeDisciplinaColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(nomeConteudoProgramaticoColumn)).setCellStyleNames("hand-over-default");		
		
		
	}

	public void initSortHandler(ListHandler<CursoAvaliacao> sortHandler) {
		
		nomeAvaliacaoColumn.setSortable(true);
	    sortHandler.setComparator(nomeAvaliacaoColumn, new Comparator<CursoAvaliacao>() {
	      @Override
	      public int compare(CursoAvaliacao o1, CursoAvaliacao o2) {
	        return o1.getNomeAvaliacao().compareTo(o2.getNomeAvaliacao());
	      }
	    });	
	    
	    dataColumn.setSortable(true);
	    sortHandler.setComparator(dataColumn, new Comparator<CursoAvaliacao>() {
	      @Override
	      public int compare(CursoAvaliacao o1, CursoAvaliacao o2) {
	        return o1.getDataAvaliacao().compareTo(o2.getDataAvaliacao());
	      }
	    });		
	    
	    horaColumn.setSortable(true);
	    sortHandler.setComparator(horaColumn, new Comparator<CursoAvaliacao>() {
	      @Override
	      public int compare(CursoAvaliacao o1, CursoAvaliacao o2) {
	        return o1.getHoraAvaliacao().compareTo(o2.getHoraAvaliacao());
	      }
	    });		  
	    
	    nomePeriodoColumn.setSortable(true);
	    sortHandler.setComparator(nomePeriodoColumn, new Comparator<CursoAvaliacao>() {
	      @Override
	      public int compare(CursoAvaliacao o1, CursoAvaliacao o2) {
	        return o1.getNomePeriodo().compareTo(o2.getNomePeriodo());
	      }
	    });	
	    
	    nomeDisciplinaColumn.setSortable(true);
	    sortHandler.setComparator(nomeDisciplinaColumn, new Comparator<CursoAvaliacao>() {
	      @Override
	      public int compare(CursoAvaliacao o1, CursoAvaliacao o2) {
	        return o1.getNomeDisciplina().compareTo(o2.getNomeDisciplina());
	      }
	    });		
	    
	    nomeConteudoProgramaticoColumn.setSortable(true);
	    sortHandler.setComparator(nomeConteudoProgramaticoColumn, new Comparator<CursoAvaliacao>() {
	      @Override
	      public int compare(CursoAvaliacao o1, CursoAvaliacao o2) {
	        return o1.getNomeConteudoProgramatico().compareTo(o2.getNomeConteudoProgramatico());
	      }
	    });		  	    
	    
		
	}		
	
	

}
