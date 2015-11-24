package com.jornada.client.ambiente.coordenador.relatorio.boletim.dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.ambiente.professor.avaliacao.TelaInicialAvaliacao;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelLeft;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.textbox.MpTextBox;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceCurso;
import com.jornada.client.service.GWTServiceNota;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimAluno;
import com.jornada.shared.classes.utility.MpUtilClient;

public class MpDialogBoxMultipleHistoricoAluno extends DecoratedPopupPanel {
	
	TextConstants txtConstants = GWT.create(TextConstants.class);
	
	protected VerticalPanel vBody;
	
	private MpSimplePager mpPager;

	private MpTextBox txtSearch;
	
	private Anchor anchor;
	
    private CellTable<TableMultipleBoletimAluno> cellTable;
    private Column<TableMultipleBoletimAluno, Boolean> columnParaImprimir;
    private Column<TableMultipleBoletimAluno, String> columnCurso;
    private Column<TableMultipleBoletimAluno, String> columnAluno;
//    private Column<TableMultipleBoletimAluno, String> columnDisciplina;
    private ListDataProvider<TableMultipleBoletimAluno> dataProvider = new ListDataProvider<TableMultipleBoletimAluno>(); 
    private ArrayList<TableMultipleBoletimAluno> arrayListBackup = new ArrayList<TableMultipleBoletimAluno>(); 
	
    private MpPanelLoading mpLoading = new MpPanelLoading("images/radar.gif");

    private MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
	
    private Grid gridLinkExcel;
    

	
	private static MpDialogBoxMultipleHistoricoAluno uniqueInstance;
	
	public static MpDialogBoxMultipleHistoricoAluno getInstance(){
		if(uniqueInstance==null){
			uniqueInstance = new MpDialogBoxMultipleHistoricoAluno();
			uniqueInstance.showDialog();
			uniqueInstance.populateGrid();
		}else{
		    uniqueInstance.vBody.clear();
			uniqueInstance.showDialog();
			uniqueInstance.populateGrid();
		}
		return uniqueInstance;
	}
	


	
	protected MpDialogBoxMultipleHistoricoAluno(){
		

		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpLoading.setTxtLoading(txtConstants.geralCarregando());
		mpLoading.show();
		mpLoading.setVisible(false);
		
		vBody = new VerticalPanel();
		vBody.setStyleName("dialogVPanelWhite");
		
		setWidget(vBody);
		super.setGlassEnabled(true);
		super.setAnimationEnabled(true);
		
	}
	
    protected void showDialog() {
        
        MpImageButton btnExcel = new MpImageButton(txtConstants.geralExcel(),"images/microsoft_office_2007_excel.16.png");
        btnExcel.addClickHandler(new ClickHandlerExcel());
        
        MpImageButton btnFechar = new MpImageButton(txtConstants.geralFecharJanela(), "images/ic_cancel_16px.png");
        btnFechar.addClickHandler(new ClickHandlerFechar());
        btnFechar.setFocus(true);      
        
        Label lblBaixarArquivo = new Label(txtConstants.usuarioBaixarPlanilhaExcel());
        anchor = new Anchor(txtConstants.usuarioCliqueAquiParaBaixar());
        
        MpLabelLeft lblCurso = new MpLabelLeft("Gerar vários boletins de Disciplinas");        
        lblCurso.setStyleName("label_comum_bold_12px");        
        MpLabelLeft lblNomeCurso = new MpLabelLeft("Por favor selecione os boletins que deseja gerar.");
        
        FlexTable flexTable = new FlexTable();
        flexTable.setCellPadding(2);
        flexTable.setCellSpacing(2);

        int row=0;
        flexTable.setWidget(row++, 0, lblCurso);
        flexTable.setWidget(row++, 0, lblNomeCurso);
        flexTable.setWidget(row++, 1, mpLoading);
        
        Grid gridBotoes = new Grid(1,2);
        gridBotoes.setBorderWidth(0);
        row=0;
        gridBotoes.setWidget(row, 0, btnExcel);
        gridBotoes.setWidget(row, 1, btnFechar);
        
        gridLinkExcel = new Grid(1,2);
        gridLinkExcel.setVisible(false);
        gridLinkExcel.setBorderWidth(0);
        row=0;
        gridLinkExcel.setWidget(row, 0, lblBaixarArquivo);
        gridLinkExcel.setWidget(row, 1, anchor);
        
        
        Grid gridAlign = new Grid(1,2);
        gridAlign.setWidth("100%");       
        gridAlign.setWidget(0, 0, gridLinkExcel);
        gridAlign.setWidget(0, 1, gridBotoes);
        gridAlign.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
        gridAlign.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
        
        vBody.add(flexTable);
        vBody.add(renderCellTable());
        vBody.add(gridAlign);  
        
        
        this.setWidth("800px");
        vBody.setWidth("100%");
        
        center();
        show();
        

    }	
    
    private VerticalPanel renderCellTable(){
        
        Label lblEmpty = new Label(txtConstants.avaliacaoNenhumaDisciplina());

        
        cellTable = new CellTable<TableMultipleBoletimAluno>(10,GWT.<CellTableStyle> create(CellTableStyle.class),TableMultipleBoletimAluno.KEY_PROVIDER);

        cellTable.setWidth("100%");
        cellTable.setAutoHeaderRefreshDisabled(true);
        cellTable.setAutoFooterRefreshDisabled(true);
        cellTable.setEmptyTableWidget(lblEmpty);


        // Add a selection model so we can select cells.
        final SelectionModel<TableMultipleBoletimAluno> selectionModel = new MultiSelectionModel<TableMultipleBoletimAluno>(TableMultipleBoletimAluno.KEY_PROVIDER);
        cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<TableMultipleBoletimAluno> createCheckboxManager());
        initTableColumns(selectionModel);
        
        dataProvider.addDataDisplay(cellTable);
        
        mpPager = new MpSimplePager();
        mpPager.setDisplay(cellTable);
        mpPager.setPageSize(100);
        
//        MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");

        txtSearch = new MpTextBox();
        txtSearch.setWidth("130px");
        txtSearch.setStyleName("design_text_boxes");
        
        txtSearch.addKeyUpHandler(new EnterKeyUpHandler());
//        btnFiltrar.addClickHandler(new ClickHandlerFiltrar());
        
        Grid gridPager = new Grid(1,6);
        gridPager.setCellPadding(1);
        gridPager.setCellSpacing(1);
        
        gridPager.setWidget(0, 0, txtSearch);
        gridPager.setWidget(0, 1, new InlineHTML("&nbsp;"));
        gridPager.setWidget(0, 2, mpPager);

//        gridPager.setWidget(0, 3, btnFiltrar);
//        gridPager.setWidget(0, 4, new InlineHTML("&nbsp;"));
        gridPager.setWidget(0, 5, mpLoading);
        
        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.setHeight(Integer.toString(TelaInicialAvaliacao.intHeightTable-180)+"px");
        scrollPanel.setAlwaysShowScrollBars(true);
        scrollPanel.setWidth("100%");
        scrollPanel.setAlwaysShowScrollBars(false);     
        scrollPanel.add(cellTable);
        
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setBorderWidth(1);
        vPanel.add(gridPager);
        vPanel.add(scrollPanel);
        vPanel.setWidth("100%");
        
        
        this.setWidth("100%");
        
        
        return vPanel;
        
    }
    
    
    protected void populateGrid() {
        mpLoading.setVisible(true);        
        GWTServiceCurso.Util.getInstance().getCursosBoletimAlunos(true, new CallbackBoletinAluno());
    }       

    private void addCellTableData(ListDataProvider<TableMultipleBoletimAluno> dataProvider) {

        ListHandler<TableMultipleBoletimAluno> sortHandler = new ListHandler<TableMultipleBoletimAluno>(dataProvider.getList());

        cellTable.addColumnSortHandler(sortHandler);

        initSortHandler(sortHandler);

    }
    
    
    private void initTableColumns(final SelectionModel<TableMultipleBoletimAluno> selectionModel) {
        
        columnParaImprimir = new Column<TableMultipleBoletimAluno, Boolean>(new CheckboxCell(true, false)) {
            @Override
            public Boolean getValue(TableMultipleBoletimAluno object) {
                return selectionModel.isSelected(object);
//                return object.isParaImprimir();
            }
        };  

        columnCurso = new Column<TableMultipleBoletimAluno, String>(new TextCell()) {
            @Override
            public String getValue(TableMultipleBoletimAluno object) {
                return object.getNomeCurso();
            }
        };   

        columnAluno = new Column<TableMultipleBoletimAluno, String>(new TextCell()) {
            @Override
            public String getValue(TableMultipleBoletimAluno object) {
                return object.getNomeAluno();
            }

        };
        
        
        cellTable.addColumn(columnParaImprimir, txtConstants.geralImprimir());
        cellTable.addColumn(columnCurso, txtConstants.curso());
        cellTable.addColumn(columnAluno, txtConstants.aluno());
        
    }
    
    public void initSortHandler(ListHandler<TableMultipleBoletimAluno> sortHandler) {
        
        columnParaImprimir.setSortable(true);
        sortHandler.setComparator(columnParaImprimir,new Comparator<TableMultipleBoletimAluno>() {
            @Override
            public int compare(TableMultipleBoletimAluno o1, TableMultipleBoletimAluno o2) {
                Boolean booO1 = o1.isParaImprimir();
                Boolean booO2 = o2.isParaImprimir();
                return booO1.compareTo(booO2);
            }
        }); 
        
        columnCurso.setSortable(true);
        sortHandler.setComparator(columnCurso, new Comparator<TableMultipleBoletimAluno>() {
            @Override
            public int compare(TableMultipleBoletimAluno o1, TableMultipleBoletimAluno o2) {
                return o1.getNomeCurso().compareTo(o2.getNomeCurso());
            }
        });

        columnAluno.setSortable(true);
        sortHandler.setComparator(columnAluno, new Comparator<TableMultipleBoletimAluno>() {
            @Override
            public int compare(TableMultipleBoletimAluno o1, TableMultipleBoletimAluno o2) {
                return o1.getNomeAluno().compareTo(o2.getNomeAluno());
            }
        });


    }

    
    private class EnterKeyUpHandler implements KeyUpHandler {
        public void onKeyUp(KeyUpEvent event) {
//           if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
               filtrarCellTable(txtSearch.getText());
//           }
       }
   }

   
//   private class ClickHandlerFiltrar implements ClickHandler {
//       public void onClick(ClickEvent event) {
//           filtrarCellTable(txtSearch.getText());
//       }
//   }  
   
   public void filtrarCellTable(String strFiltro) {
       
      
       
       removeCellTableFilter();

       strFiltro = strFiltro.toUpperCase();

       if (!strFiltro.isEmpty()) {

           int i = 0;
           while (i < dataProvider.getList().size()) {

               String strNomeCurso = dataProvider.getList().get(i).getNomeCurso();
               String strNomePeriodo = dataProvider.getList().get(i).getNomeAluno();

               String strJuntaTexto = strNomeCurso.toUpperCase() + " " + strNomePeriodo.toUpperCase() ;

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
   
   private class ClickHandlerExcel implements ClickHandler {
       @SuppressWarnings("unchecked")
    public void onClick(ClickEvent event) {
           mpLoading.setVisible(true);
           gridLinkExcel.setVisible(false);
            Set<TableMultipleBoletimAluno> selectedObjects = ((MultiSelectionModel<TableMultipleBoletimAluno>) (cellTable.getSelectionModel())).getSelectedSet();
            ArrayList<TableMultipleBoletimAluno> listTableMBD = new ArrayList<TableMultipleBoletimAluno>(selectedObjects);

            Collections.sort(listTableMBD, new Comparator<TableMultipleBoletimAluno>() {
                @Override
                public int compare(TableMultipleBoletimAluno o1, TableMultipleBoletimAluno o2) {
                    int compareResult = 0;

                    String str1 = o1.getNomeCurso();
                    String str2 = o2.getNomeCurso();
                    compareResult = str1.compareTo(str2);
                    if (compareResult != 0) return compareResult;

                    str1 = o1.getNomeAluno();
                    str2 = o2.getNomeAluno();
                    compareResult = str1.compareTo(str2);
                    if (compareResult != 0) return compareResult;


                    return compareResult;
                }
            });

//            GWTServiceNota.Util.getInstance().getExcelBoletimDisciplina(listTableMBD, new CallBackBoletim());
            
//            GWTServiceNota.Util.getInstance().getExcelBoletimAluno(listTableMBD, new CallBackBoletim());
            GWTServiceNota.Util.getInstance().getMultipleExcelHistoricoAluno(listTableMBD, new CallBackBoletim());


           
//           Window.alert(strTest);
       }
   }

    private class ClickHandlerFechar implements ClickHandler {
        public void onClick(ClickEvent event) {
            MpDialogBoxMultipleHistoricoAluno.this.hide();
        }
    }

    private class CallbackBoletinAluno implements AsyncCallback<ArrayList<TableMultipleBoletimAluno>>{
        @Override
        public void onFailure(Throwable caught) {
            mpLoading.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroCarregar());
        }

        @Override
        public void onSuccess(ArrayList<TableMultipleBoletimAluno> list) {
            MpUtilClient.isRefreshRequired(list);
            mpLoading.setVisible(false);
            dataProvider.getList().clear();
            arrayListBackup.clear();
//            cellTable.setRowCount(0);

            for(int i=0;i<list.size();i++){
                dataProvider.getList().add(list.get(i));
                arrayListBackup.add(list.get(i));
            }
            addCellTableData(dataProvider);
            cellTable.redraw(); 

        }
        
    }
    
    private class CallBackBoletim implements AsyncCallback<String> {

        @Override
        public void onFailure(Throwable caught) {
            mpLoading.setVisible(false);
            gridLinkExcel.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText(txtConstants.geralErroCarregar(txtConstants.geralErroCarregarDados()));
        }

        @Override
        public void onSuccess(String result) {
            
            result = result.replace("//", "/");
            anchor.setHref(GWT.getHostPageBaseURL()+result);
            mpLoading.setVisible(false);
            gridLinkExcel.setVisible(true);

        }
    }
    
    


}
