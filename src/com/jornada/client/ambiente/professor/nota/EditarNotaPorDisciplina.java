package com.jornada.client.ambiente.professor.nota;

import java.util.ArrayList;
import java.util.Comparator;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.classes.listBoxes.MpSelectionAvaliacao;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxCursoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxDisciplinaAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxPeriodoAmbienteProfessor;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceNota;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Nota;
import com.jornada.shared.classes.utility.MpUtilClient;

public class EditarNotaPorDisciplina extends VerticalPanel {
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);

//	private AsyncCallback<ArrayList<Usuario>> callbackGetUsuariosFiltro;
	private AsyncCallback<Boolean> callbackUpdateRow;
//	private AsyncCallback<Boolean> callbackDelete;

	private CellTable<Nota> cellTable;
	private Column<Nota, String> nomeUsuarioColumn;
	private Column<Nota, String> notaColumn;
	private ListDataProvider<Nota> dataProvider = new ListDataProvider<Nota>();	
	private ListDataProvider<Nota> finalDataProvider = new ListDataProvider<Nota>();	

	private MpListBoxCursoAmbienteProfessor listBoxCurso;
	private MpListBoxPeriodoAmbienteProfessor listBoxPeriodo;	
	private MpListBoxDisciplinaAmbienteProfessor listBoxDisciplina;
//	private MpSelectionConteudoProgramatico listBoxConteudoProgramatico;
	private MpSelectionAvaliacao listBoxAvaliacao;
	
	
	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	
	private TextBox txtSearch;
	
	private TelaInicialNota telaInicialNota;
	


    public EditarNotaPorDisciplina(TelaInicialNota telaInicialNota) {
        
        
        
        this.telaInicialNota=telaInicialNota;
        setWidth(Integer.toString(TelaInicialNota.intWidthTable)+ "px");

        mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
        mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
        mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
        mpPanelLoading.show();
        mpPanelLoading.setVisible(false);       
        

        Label lblCurso = new Label(txtConstants.curso());
        Label lblPeriodo = new Label(txtConstants.periodo());
        Label lblDisciplina = new Label(txtConstants.disciplina());
//      Label lblConteudoProgramatico = new Label(txtConstants.conteudoProgramatico());
        Label lblAvaliacao = new Label(txtConstants.avaliacao());

        listBoxCurso = new MpListBoxCursoAmbienteProfessor(telaInicialNota.getMainView().getUsuarioLogado());
        listBoxPeriodo = new MpListBoxPeriodoAmbienteProfessor(telaInicialNota.getMainView().getUsuarioLogado());
        listBoxDisciplina = new MpListBoxDisciplinaAmbienteProfessor(telaInicialNota.getMainView().getUsuarioLogado());     
//      listBoxConteudoProgramatico = new MpSelectionConteudoProgramatico();        
        listBoxAvaliacao = new MpSelectionAvaliacao();
        
        listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());
        listBoxPeriodo.addChangeHandler(new MpPeriodoSelectionChangeHandler());     
        listBoxDisciplina.addChangeHandler(new MpDisciplinaSelectionChangeHandler());       
//      listBoxConteudoProgramatico.addChangeHandler(new MpConteudoProgramaticoSelectionChangeHandler());
        listBoxAvaliacao.addChangeHandler(new MpAvaliacaoSelectionChangeHandler());

        Grid gridComboBox = new Grid(5, 4);
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
//          gridComboBox.setWidget(row, 0, lblConteudoProgramatico);
//          gridComboBox.setWidget(row, 1, listBoxConteudoProgramatico);
//          gridComboBox.setWidget(row++, 2, new InlineHTML("&nbsp;"));
            gridComboBox.setWidget(row, 0, lblAvaliacao);
            gridComboBox.setWidget(row, 1, listBoxAvaliacao);
            gridComboBox.setWidget(row, 2, new InlineHTML("&nbsp;"));           
            gridComboBox.setWidget(row++, 3, mpPanelLoading);           
        }

        Label lblEmpty = new Label(txtConstants.notaNehumaNota());
//      Label lblEmpty2 = new Label("Por favor, selecione um Conteúdo Programático.");

        cellTable = new CellTable<Nota>(100,GWT.<CellTableStyle> create(CellTableStyle.class));
//      cellTable.setWidth(Integer.toString(TelaInicialNota.intWidthTable-900)+ "px");
        cellTable.setWidth("100%");
        cellTable.setAutoHeaderRefreshDisabled(true);
        cellTable.setAutoFooterRefreshDisabled(true);
        cellTable.setEmptyTableWidget(lblEmpty);


        // Add a selection model so we can select cells.
        final SelectionModel<Nota> selectionModel = new MultiSelectionModel<Nota>();
        cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<Nota> createCheckboxManager());
        initTableColumns(selectionModel);

        dataProvider.addDataDisplay(cellTable); 
        
        MpSimplePager mpPager = new MpSimplePager();
        mpPager.setDisplay(cellTable);
        mpPager.setPageSize(100);
        
        
        Label lblFiltroUsuario = new Label(txtConstants.usuarioFiltrarUsuario());
        txtSearch = new TextBox();
        MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");

        txtSearch.addKeyUpHandler(new EnterKeyUpHandler());     
        btnFiltrar.addClickHandler(new ClickHandlerFiltrar());

        lblFiltroUsuario.setStyleName("design_label");
        lblFiltroUsuario.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);        
        txtSearch.setStyleName("design_text_boxes");    
        
        FlexTable flexTableFiltrar = new FlexTable();
        flexTableFiltrar.setWidget(0, 0, mpPager);
        flexTableFiltrar.setWidget(0, 1, new InlineHTML("&nbsp"));
        flexTableFiltrar.setWidget(0, 2, lblFiltroUsuario);
        flexTableFiltrar.setWidget(0, 3, txtSearch);
        flexTableFiltrar.setWidget(0, 4, btnFiltrar);
        flexTableFiltrar.setWidget(0, 5, mpPanelLoading);
        
        
        ScrollPanel scrollPanel = new ScrollPanel();
//      scrollPanel.setSize(Integer.toString(TelaInicialNota.intWidthTable+30)+"px",Integer.toString(TelaInicialNota.intHeightTable-240)+"px");
//        scrollPanel.setHeight(Integer.toString(TelaInicialNota.intHeightTable-240)+"px");
//        scrollPanel.setWidth("100%");
        scrollPanel.setAlwaysShowScrollBars(false);     
        scrollPanel.add(cellTable);     

        
        
        VerticalPanel vPanelEditGrid = new VerticalPanel();     
        vPanelEditGrid.add(gridComboBox);
//        vPanelEditGrid.add(new InlineHTML("&nbsp;"));
        vPanelEditGrid.add(flexTableFiltrar);
        vPanelEditGrid.add(scrollPanel);
        
//      vPanelEditGrid.setWidth("100%");


        /************************* Begin Callback's *************************/

        callbackUpdateRow = new AsyncCallback<Boolean>() {

            public void onSuccess(Boolean success) {
                if(success==false){
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText(txtConstants.notaErroAtualizar()+" "+txtConstants.geralRegarregarPagina());
                    mpDialogBoxWarning.showDialog();
                    
                }

            }

            public void onFailure(Throwable caught) {
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.notaErroAtualizar());
                mpDialogBoxWarning.showDialog();
            }
        };

        /*********************** End Callbacks **********************/

        this.setWidth("100%");
        super.add(vPanelEditGrid);
        
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
//				listBoxConteudoProgramatico.clear();
				listBoxAvaliacao.clear();
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
//				listBoxConteudoProgramatico.clear();
				listBoxAvaliacao.clear();
				dataProvider.getList().clear();
			}
			else{
				int idDisciplina= Integer.parseInt(listBoxDisciplina.getValue(index));
//				listBoxConteudoProgramatico.populateComboBox(idDisciplina);	
				listBoxAvaliacao.populateComboBox(idDisciplina);  
			}
		}  
	}
	
//	private class MpConteudoProgramaticoSelectionChangeHandler implements ChangeHandler{
//		
//		public void onChange(ChangeEvent event){
////			populateGridTopico();
//			int index = listBoxConteudoProgramatico.getSelectedIndex();
//			if(index==-1){
////				listBoxConteudoProgramatico.clear();
//				listBoxAvaliacao.clear();
//				dataProvider.getList().clear();
//			}
//			else{
//				int idConteudoProgramatico= Integer.parseInt(listBoxConteudoProgramatico.getValue(index));
//				listBoxAvaliacao.populateComboBox(idConteudoProgramatico);				
//			}			
//		}
//	}
	
	private class MpAvaliacaoSelectionChangeHandler implements ChangeHandler{
		
		public void onChange(ChangeEvent event){
			populateGridNota();
			
		}
	}	
	
	private class EnterKeyUpHandler implements KeyUpHandler{
		
		public void onKeyUp(KeyUpEvent event){
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				filteringDataProvider();
			}
		}
	}	
	
	
	private class ClickHandlerFiltrar implements ClickHandler{
		
		public void onClick(ClickEvent event){
			filteringDataProvider();
		}
	}
	
	
	/**************** End Event Handlers *****************/
	
	
	
	/**************** Begin Populate methods*****************/	
	
	protected void populateGridNota() {
		
		mpPanelLoading.setVisible(true);
		
		int indexCurso = listBoxCurso.getSelectedIndex();
		int indexAvaliacao = listBoxAvaliacao.getSelectedIndex();
		
		if (indexAvaliacao == -1 ) {
			mpPanelLoading.setVisible(false);
			dataProvider.getList().clear();
		} 
		else{			
			int idCurso = Integer.parseInt(listBoxCurso.getValue(indexCurso));
			int idAvaliacao = Integer.parseInt(listBoxAvaliacao.getValue(indexAvaliacao));
			GWTServiceNota.Util.getInstance().getNotaPelaAvaliacao(idCurso, idAvaliacao,					
					new AsyncCallback<ArrayList<Nota>>() {
						@Override
						public void onFailure(Throwable caught) {
							mpPanelLoading.setVisible(false);
							mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
							mpDialogBoxWarning.setBodyText(txtConstants.notaErroCarregar());
						}
						@Override
						public void onSuccess(ArrayList<Nota> list) {
							MpUtilClient.isRefreshRequired(list);
							mpPanelLoading.setVisible(false);
							dataProvider.getList().clear();
							finalDataProvider.getList().clear();
							cellTable.setRowCount(0);
							for (int i = 0; i < list.size(); i++) {
								
								dataProvider.getList().add(list.get(i));
								finalDataProvider.getList().add(list.get(i));
							}
							addCellTableData(dataProvider);
							cellTable.redraw();
						}
					});					
		}
	}
	
	public void updateClientData(){
		listBoxCurso.populateComboBox(this.telaInicialNota.getMainView().getUsuarioLogado());
	}		
	
	/**************** End Populate methods*****************/

	
	
	public void filteringDataProvider() {
		
		dataProvider.getList().clear();

		for (int i = 0; i < finalDataProvider.getList().size(); i++) {
			dataProvider.getList().add(finalDataProvider.getList().get(i));
		}

		String strSearch = txtSearch.getText();
		strSearch = strSearch.toUpperCase();

		if (!strSearch.isEmpty()) {
			
//			for (int i = 0; i < dataProvider.getList().size(); i++) {
			int i=0;
			while(i<dataProvider.getList().size()){

				Nota nota = dataProvider.getList().get(i);
				String strNome = nota.getNomeUsuario().toUpperCase();

				if (!strNome.contains(strSearch)) {
					dataProvider.getList().remove(i);
					i = 0;
					continue;
				}
				i++;
			}
			
		}

		dataProvider.refresh();
	}
	
	
	
	private void addCellTableData(ListDataProvider<Nota> dataProvider){
		
		 ListHandler<Nota> sortHandler = new ListHandler<Nota>(dataProvider.getList());
		 
		 cellTable.addColumnSortHandler(sortHandler);	

		 initSortHandler(sortHandler);
	
	}


	private void initTableColumns(final SelectionModel<Nota> selectionModel) {
		
		nomeUsuarioColumn = new Column<Nota, String>(new TextCell()) {
			@Override
			public String getValue(Nota object) {
				return object.getNomeUsuario();
			}

		};
		
		notaColumn = new Column<Nota, String>(new EditTextCell()) {
			@Override
			public String getValue(Nota object) {
				return object.getNota();
			}

		};
		notaColumn.setFieldUpdater(new FieldUpdater<Nota, String>() {
			@Override
			public void update(int index, Nota object, String value) {
				// Called when the user changes the value.
				
				if (FieldVerifier.isValidDouble(value)) {
				    if(FieldVerifier.isValidGrade(value)){
	                    object.setNota(value);
	                    GWTServiceNota.Util.getInstance().updateRow(object,callbackUpdateRow);				        
				    }else{
	                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
	                    mpDialogBoxWarning.setBodyText(txtConstants.geralErroTipo(txtConstants.geralValorEntre0E10()));
	                    mpDialogBoxWarning.showDialog();				        
				    }
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.geralErroTipo(txtConstants.geralNumeroDouble()));
					mpDialogBoxWarning.showDialog();
				}			
				
				

			}
		});


		cellTable.addColumn(nomeUsuarioColumn, txtConstants.alunoNome());
		cellTable.addColumn(notaColumn, txtConstants.nota());
		cellTable.setColumnWidth(nomeUsuarioColumn, 70.0, Unit.PCT);

		cellTable.getColumn(cellTable.getColumnIndex(nomeUsuarioColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(notaColumn)).setCellStyleNames("edit-cell-nota");		
	}

	public void initSortHandler(ListHandler<Nota> sortHandler) {
		nomeUsuarioColumn.setSortable(true);
	    sortHandler.setComparator(nomeUsuarioColumn, new Comparator<Nota>() {
	      @Override
	      public int compare(Nota o1, Nota o2) {
	        return o1.getNomeUsuario().compareTo(o2.getNomeUsuario());
	      }
	    });	
	    
	    notaColumn.setSortable(true);
	    sortHandler.setComparator(notaColumn, new Comparator<Nota>() {
	      @Override
	      public int compare(Nota o1, Nota o2) {
	        return o1.getNota().compareTo(o2.getNota());
	      }
	    });		    
	    
	}
	
}
