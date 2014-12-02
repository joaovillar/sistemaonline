package com.jornada.client.ambiente.coordenador.disciplina;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;

import com.google.gwt.cell.client.FieldUpdater;
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
import com.jornada.client.classes.listBoxes.MpSelectionCurso;
import com.jornada.client.classes.listBoxes.suggestbox.MpListBoxPanelHelper;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.cells.MpStyledSelectionCell;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceDisciplina;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.disciplina.ProfessorDisciplina;
import com.jornada.shared.classes.utility.MpUtilClient;

public class AdicionarProfessorDisciplina extends VerticalPanel {


	private CellTable<ProfessorDisciplina> cellTable;
	private ListDataProvider<ProfessorDisciplina> dataProvider = new ListDataProvider<ProfessorDisciplina>();
	private Column<ProfessorDisciplina, String> nomePeriodoColumn;
	private Column<ProfessorDisciplina, String> nomeDisciplinaColumn;
	private Column<ProfessorDisciplina, String> nomeProfessorColumn;
	
	private LinkedHashMap<String, String> listaNomeProfessor = new LinkedHashMap<String, String>();    

	MpListBoxPanelHelper mpHelperCurso = new  MpListBoxPanelHelper();
	private MpSelectionCurso listBoxCurso;
//	private MpSelectionProfessor listBoxProfessor;
	
	private TextBox txtSearch;
	ArrayList<ProfessorDisciplina> arrayListBackup = new ArrayList<ProfessorDisciplina>();


	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	
	final SelectionModel<ProfessorDisciplina> selectionModel;
	
	TextConstants txtConstants;
	
	@SuppressWarnings("unused")
	private TelaInicialDisciplina telaInicial;
	
	private static AdicionarProfessorDisciplina uniqueInstance;
	
	public static AdicionarProfessorDisciplina getInstance(TelaInicialDisciplina telaInicialPeriodo) {

		if(uniqueInstance==null){
			uniqueInstance = new AdicionarProfessorDisciplina(telaInicialPeriodo);
		}		
		
		return uniqueInstance;
	}

	private AdicionarProfessorDisciplina(TelaInicialDisciplina telaInicial) {
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicial=telaInicial;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);		
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);		

		Label lblCursoEdit = new Label(txtConstants.curso());
		
//		listBoxProfessor = new MpSelectionProfessor();
//		listBoxProfessor.setWidth("250px");
//		listBoxProfessor.addChangeHandler(new MpProfessorSelectionChangeHandler());

		listBoxCurso = new MpSelectionCurso(true);
		listBoxCurso.setWidth("250px");
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());

		Grid gridComboBox = new Grid(1, 6);
		gridComboBox.setCellSpacing(2);
		gridComboBox.setCellPadding(2);
		{
			gridComboBox.setWidget(0, 0, lblCursoEdit);
			gridComboBox.setWidget(0, 1, listBoxCurso);
			gridComboBox.setWidget(0, 2, mpHelperCurso);
			gridComboBox.setWidget(0, 3, new InlineHTML("&nbsp;"));
			gridComboBox.setWidget(0, 4, mpPanelLoading);
		}

		Label lblEmpty = new Label(txtConstants.periodoNenhum());

		cellTable = new CellTable<ProfessorDisciplina>(5,GWT.<CellTableStyle> create(CellTableStyle.class));
		cellTable.setWidth("100%");
		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);
		cellTable.setEmptyTableWidget(lblEmpty);
		
		dataProvider.addDataDisplay(cellTable);	
		
		selectionModel = new MultiSelectionModel<ProfessorDisciplina>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<ProfessorDisciplina> createCheckboxManager());

		//initTableColumns(selectionModel);			
		
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
		scrollPanel.setHeight(Integer.toString(TelaInicialDisciplina.intHeightTable-110)+"px");
		scrollPanel.setWidth("100%");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(cellTable);
		

		VerticalPanel vPanelEditGrid = new VerticalPanel();	
		vPanelEditGrid.add(gridComboBox);
//		vPanelEditGrid.add(mpPager);
		vPanelEditGrid.add(flexTableFiltrar);
		vPanelEditGrid.add(scrollPanel);
		vPanelEditGrid.setWidth("100%");


		/******** Begin Populate ********/
//		populateCursoComboBox();
		populateComboBoxProfessor();
		/******** End Populate ********/
		
		setWidth("100%");
		super.add(vPanelEditGrid);

	}
	
	
	
    protected void populateComboBoxProfessor() {
        
        //GWTServiceAvaliacao.Util.getInstance().getTipoAvaliacao(
        GWTServiceUsuario.Util.getInstance().getUsuariosPorTipoUsuario(TipoUsuario.PROFESSOR,
        
                new AsyncCallback<ArrayList<Usuario>>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                        mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroCarregar());
                    }

                    @Override
                    public void onSuccess(ArrayList<Usuario> list) {
                        MpUtilClient.isRefreshRequired(list);
                        
                        dataProvider.getList().clear();
                        cellTable.setRowCount(0);
                        
                        listaNomeProfessor.put("0", "Sem professor");     
                        for(Usuario usuario : list){
                            String strIdProfessor = Integer.toString(usuario.getIdUsuario());
                            String strNomeProfessor = usuario.getPrimeiroNome() + " " + usuario.getSobreNome();
                            listaNomeProfessor.put(strIdProfessor, strNomeProfessor);                       
                        }
//                        renderCellTable();

                        initTableColumns(selectionModel);   
                        
                    }
                });
    }

    

    
	protected void populateGrid() {
		mpPanelLoading.setVisible(true);	
		int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
        dataProvider.getList().clear();
        cellTable.setRowCount(0);
		GWTServiceDisciplina.Util.getInstance().getPeriodoDisciplinaProfessor(idCurso, new callbackPopulate());
		

	}
	
	
	
	private class MpCursoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
		    mpHelperCurso.populateSuggestBox(listBoxCurso);
				populateGrid();
//		    listBoxProfessor.populateComboBox();
		}	  
	}

	public void updateClientData() {
//		listBoxCurso.populateComboBox();
//	    populateComboBoxProfessor();
	    populateGrid();
	}

	
	private void addCellTableData(ListDataProvider<ProfessorDisciplina> dataProvider){
		
		 ListHandler<ProfessorDisciplina> sortHandler = new ListHandler<ProfessorDisciplina>(dataProvider.getList());
		 
		 cellTable.addColumnSortHandler(sortHandler);	

		 initSortHandler(sortHandler);
	
	}	
		
	private void initTableColumns(final SelectionModel<ProfessorDisciplina> selectionModel){
		
		nomePeriodoColumn = new Column<ProfessorDisciplina, String>(new TextCell()) {
			@Override
			public String getValue(ProfessorDisciplina object) {
				return object.getNomePeriodo();
			}

		};
		
        nomeDisciplinaColumn = new Column<ProfessorDisciplina, String>(new TextCell()) {
            @Override
            public String getValue(ProfessorDisciplina object) {
                return object.getNomeDisciplina();
            }

        };
        
        
        MpStyledSelectionCell listaProfessorCell = new MpStyledSelectionCell(listaNomeProfessor,"design_text_boxes");
        
       nomeProfessorColumn = new Column<ProfessorDisciplina, String>(listaProfessorCell) {
            @Override
            public String getValue(ProfessorDisciplina object) {
                return Integer.toString(object.getIdUsuario());
            }

        };
        nomeProfessorColumn.setFieldUpdater(new FieldUpdater<ProfessorDisciplina, String>() {
            @Override
            public void update(int index, ProfessorDisciplina object, String value) {
                // Called when the user changes the value.
                object.setIdUsuario(Integer.parseInt(value));
                int idProfessor = object.getIdUsuario();
                int idDisciplina = object.getIdDisciplina();
//                object.setIdTipoAvaliacao(Integer.parseInt(value));
//                GWTServiceAvaliacao.Util.getInstance().updateRow(object, callbackUpdateRow);
                GWTServiceDisciplina.Util.getInstance().updateDisciplinaComIdProfessor(idProfessor, idDisciplina, new callbackUpdateProfessorDisciplina());
            }
        });        

		cellTable.addColumn(nomePeriodoColumn, txtConstants.periodoNome());
		cellTable.addColumn(nomeDisciplinaColumn, txtConstants.disciplina());
		cellTable.addColumn(nomeProfessorColumn, txtConstants.professor());
		
		cellTable.getColumn(cellTable.getColumnIndex(nomePeriodoColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(nomeDisciplinaColumn)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(nomeProfessorColumn)).setCellStyleNames("edit-cell");
		
		populateGrid();
		
	}	
		
	public void initSortHandler(ListHandler<ProfessorDisciplina> sortHandler){
		nomePeriodoColumn.setSortable(true);
	    sortHandler.setComparator(nomePeriodoColumn, new Comparator<ProfessorDisciplina>() {
	      @Override
	      public int compare(ProfessorDisciplina o1, ProfessorDisciplina o2) {
	        return o1.getNomePeriodo().compareTo(o2.getNomePeriodo());
	      }
	    });		
	    
        nomeDisciplinaColumn.setSortable(true);
        sortHandler.setComparator(nomeDisciplinaColumn, new Comparator<ProfessorDisciplina>() {
          @Override
          public int compare(ProfessorDisciplina o1, ProfessorDisciplina o2) {
            return o1.getNomeDisciplina().compareTo(o2.getNomeDisciplina());
          }
        }); 	    
        nomeProfessorColumn.setSortable(true);
        sortHandler.setComparator(nomeProfessorColumn, new Comparator<ProfessorDisciplina>() {
          @Override
          public int compare(ProfessorDisciplina o1, ProfessorDisciplina o2) {
              int primitive1 = o1.getIdUsuario(), primitive2 = o2.getIdUsuario();
              Integer a = new Integer(primitive1);
              Integer b = new Integer(primitive2);
              return a.compareTo(b);
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

					String strNomePeriodo = dataProvider.getList().get(i).getNomePeriodo();			
					String strNomeDisciplina = dataProvider.getList().get(i).getNomeDisciplina();
					String strNome = dataProvider.getList().get(i).getPrimeiroNome() + " "+dataProvider.getList().get(i).getSobreNome();
					int idUsuario=dataProvider.getList().get(i).getIdUsuario();
					
					if(idUsuario==0){strNome="Sem professor";}


					String strJuntaTexto = strNomePeriodo.toUpperCase() + " " + strNomeDisciplina.toUpperCase() + " ";
					strJuntaTexto +=  " " + strNome.toUpperCase() ;

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
	
        
    private class callbackUpdateProfessorDisciplina  implements AsyncCallback<Boolean>{

            public void onSuccess(Boolean success) {
                mpPanelLoading.setVisible(false);   
                if (success == true) {
//                    populateGrid();
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
        }
        
    private class callbackPopulate implements AsyncCallback<ArrayList<ProfessorDisciplina>> {

                  @Override
                  public void onFailure(Throwable caught) {
                      mpPanelLoading.setVisible(false);   
                      mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                      mpDialogBoxWarning.setBodyText(txtConstants.geralErroCarregar(txtConstants.periodo()));
                  }

                  @Override
                  public void onSuccess(ArrayList<ProfessorDisciplina> list) {
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
              }

	
}
