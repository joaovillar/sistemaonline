package com.jornada.client.ambiente.coordenador.relatorio.usuario;

import java.util.ArrayList;
import java.util.Comparator;

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
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.ambiente.coordenador.relatorio.usuario.dialog.MpDialogBoxExcelProfessorDisciplina;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.classes.relatorio.usuario.ProfessorDisciplinaRelatorio;
import com.jornada.shared.classes.utility.MpUtilClient;

public class RelatorioProfessorDisciplina extends VerticalPanel {


	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpLoading = new MpPanelLoading("images/radar.gif");
	
	VerticalPanel panel = new VerticalPanel();
	
	private CellTable<ProfessorDisciplinaRelatorio> cellTable;
    private ListDataProvider<ProfessorDisciplinaRelatorio> dataProvider;   
    private Column<ProfessorDisciplinaRelatorio, String> primeiroNomeColumn;
    private Column<ProfessorDisciplinaRelatorio, String> sobreNomeColumn;
    private Column<ProfessorDisciplinaRelatorio, String> nomeCursoColumn;
    private Column<ProfessorDisciplinaRelatorio, String> nomePeriodoColumn;
    private Column<ProfessorDisciplinaRelatorio, String> nomeDisciplinaColumn;
    
    
    ArrayList<ProfessorDisciplinaRelatorio> arrayListBackup = new ArrayList<ProfessorDisciplinaRelatorio>();    
    private TextBox txtSearch;


	@SuppressWarnings("unused")
	private TelaInicialRelatorioUsuario telaInicialRelatorioUsuario;
	
	TextConstants txtConstants;
	
	ScrollPanel scrollPanel = new ScrollPanel();
	VerticalPanel vFormPanel;
	FlexTable flexTableNota;
	
	private static RelatorioProfessorDisciplina uniqueInstance;
	
	public static RelatorioProfessorDisciplina getInstance(final TelaInicialRelatorioUsuario telaInicialRelatorioUsuario){

		if(uniqueInstance==null){
			uniqueInstance = new RelatorioProfessorDisciplina(telaInicialRelatorioUsuario);
		}
		uniqueInstance.populateProfessorDisciplina();
		return uniqueInstance;
		
	}

	private RelatorioProfessorDisciplina(final TelaInicialRelatorioUsuario telaInicialRelatorioUsuario) {
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialRelatorioUsuario=telaInicialRelatorioUsuario;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpLoading.setTxtLoading(txtConstants.geralCarregando());
		mpLoading.show();
		mpLoading.setVisible(false);

		FlexTable flexTable = new FlexTable();
		flexTable.setCellSpacing(3);
		flexTable.setCellPadding(3);

		vFormPanel = new VerticalPanel();
		flexTableNota = new FlexTable();
		
		vFormPanel.add(flexTable);
		vFormPanel.add(flexTableNota);

        scrollPanel = new ScrollPanel();
//        scrollPanel.setHeight(Integer.toString(TelaInicialRelatorioUsuario.intHeightTable - 40) + "px");
//        scrollPanel.setWidth("100%");
//		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(vFormPanel);
		
		super.setWidth("100%");

		super.add(scrollPanel);

	}


	public void updateClientData() {
	    populateProfessorDisciplina();
	}
	
    private class CallBackCarregarProfessorDisciplina implements AsyncCallback<ArrayList<ProfessorDisciplinaRelatorio>>{

        @Override
        public void onFailure(Throwable caught) {
            mpLoading.setVisible(false);
            MpUtilClient.isRefreshRequired();
        }

        @Override
        public void onSuccess(ArrayList<ProfessorDisciplinaRelatorio> list) {
            mpLoading.setVisible(false);
            MpUtilClient.isRefreshRequired(list);
            
            dataProvider = new ListDataProvider<ProfessorDisciplinaRelatorio>();

            dataProvider.getList().clear();
            arrayListBackup.clear();
            


            for (int i = 1; i < list.size(); i++) {
                dataProvider.getList().add(list.get(i));  
                arrayListBackup.add(list.get(i));
            }
            
            initializeCellTable();
            addCellTableData(dataProvider);
        
        }
        
    }
    
    public void initializeCellTable(){
        
        cellTable = new CellTable<ProfessorDisciplinaRelatorio>(100,GWT.<CellTableStyle> create(CellTableStyle.class));
        cellTable.setAutoHeaderRefreshDisabled(true);
        cellTable.setAutoFooterRefreshDisabled(true);
        
        
        
        final SelectionModel<ProfessorDisciplinaRelatorio> selectionModel = new MultiSelectionModel<ProfessorDisciplinaRelatorio>();
        cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<ProfessorDisciplinaRelatorio> createCheckboxManager());
//        initTableColumns();
        
        dataProvider.addDataDisplay(cellTable); 
        
        
        initTableColumns();   
        
        final MpSimplePager mpPager = new MpSimplePager();
        mpPager.setDisplay(cellTable);
        
        
//        Image imgMultiple = new Image("images/table-multiple-icon-24.png");
//        imgMultiple.addClickHandler(new ClickHandlerMultipleExcel());
//        imgMultiple.setStyleName("hand-over");
//        imgMultiple.setTitle(txtConstants.geralMultipleExcel());       
        
        
        Image imgExcel = new Image("images/excel.24.png");
        imgExcel.addClickHandler(new ClickHandlerExcel());
        imgExcel.setStyleName("hand-over");
        imgExcel.setTitle(txtConstants.geralExcel());
        
        int columnImg = 0;
        FlexTable flexTableImg = new FlexTable();
        flexTableImg.setCellPadding(2);
        flexTableImg.setCellSpacing(2);
        flexTableImg.setWidget(0, columnImg++, imgExcel);
//        flexTableImg.setWidget(0, columnImg++, imgMultiple);
        flexTableImg.setBorderWidth(0);
        
        
        if (txtSearch == null) {
            txtSearch = new TextBox();
            txtSearch.setStyleName("design_text_boxes");
        }
        
        txtSearch.addKeyUpHandler(new EnterKeyUpHandler());

        
        FlexTable flexTableSearch = new FlexTable();
        flexTableSearch.setWidth("550px");
        flexTableSearch.setWidget(0, 0, txtSearch);
        

        flexTableSearch.setWidget(0, 2, mpPager);
        flexTableSearch.setWidget(0, 3, mpLoading);
//        flexTableSearch.setWidget(0, 4, new Label("1111"));
        
        FlexTable flexTableMenu = new FlexTable();
        flexTableMenu.setCellPadding(0);
        flexTableMenu.setCellSpacing(0);
        flexTableMenu.setBorderWidth(0);
        flexTableMenu.setWidth("100%");     
        flexTableMenu.setWidget(0, 0, flexTableSearch);
        flexTableMenu.setWidget(0, 1, flexTableImg);
        flexTableMenu.getCellFormatter().setWidth(0, 0, "70%");
        flexTableMenu.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
        flexTableMenu.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_BOTTOM);
        flexTableMenu.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_BOTTOM);
        flexTableNota.setBorderWidth(0);
        flexTableNota.clear();
        flexTableNota.setWidget(0,0,flexTableMenu);
        flexTableNota.setWidget(1,0,cellTable);
        
    }
    
 
    private void populateProfessorDisciplina(){
        mpLoading.setVisible(true);
       
       GWTServiceUsuario.Util.getInstance().getProfessoresDisciplinas(new CallBackCarregarProfessorDisciplina());
       
    }
    
    
	
    private class ClickHandlerExcel implements ClickHandler{
        @Override
        public void onClick(ClickEvent event) {
            MpDialogBoxExcelProfessorDisciplina.getInstance();                 
        }
    }
    
    /*******************************************************************************************************/   
    /*******************************************************************************************************/
    /***************************************BEGIN Filterting CellTable***************************************/
    public void filtrarCellTable(String strFiltro) {
        
        removeCellTableFilter();

        strFiltro = strFiltro.toUpperCase();

        if (!strFiltro.isEmpty()) {

            int i = 0;
            while (i < dataProvider.getList().size()) {

                String strPrimeiroNome = dataProvider.getList().get(i).getPrimeiroNome();    
                String strSobreNome = dataProvider.getList().get(i).getSobreNome();
                String strNomeCurso = dataProvider.getList().get(i).getNomeCurso();
                String strNomePeriodo = dataProvider.getList().get(i).getNomePeriodo();
                String strNomeDisciplina = dataProvider.getList().get(i).getNomeDisciplina();
                
                if(strPrimeiroNome==null)strPrimeiroNome="";
                if(strSobreNome==null)strSobreNome="";
                if(strNomeCurso==null)strNomeCurso="";
                if(strNomePeriodo==null)strNomePeriodo="";
                if(strNomeDisciplina==null)strNomeDisciplina="";
                


                String strJuntaTexto = strPrimeiroNome.toUpperCase() + " " + strSobreNome.toUpperCase() + " " + strNomeCurso.toUpperCase()+ " ";
                strJuntaTexto +=  strNomePeriodo.toUpperCase() + " " + " " + strNomeDisciplina.toUpperCase();

                if (!strJuntaTexto.contains(strFiltro)) {
                    dataProvider.getList().remove(i);
                    i = 0;
                    continue;
                }

                System.out.println(i++);
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
    
    
    private class EnterKeyUpHandler implements KeyUpHandler {
        public void onKeyUp(KeyUpEvent event) {
//           if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
               filtrarCellTable(txtSearch.getText());
//           }
       }
   }
    
/***************************************END Filterting CellTable***************************************/
    


//    private class ClickHandlerMultipleExcel implements ClickHandler {
//        @Override
//        public void onClick(ClickEvent event) {
//            MpDialogBoxMultipleBoletimAnual.getInstance();
//        }
//    }
    
    private void addCellTableData(ListDataProvider<ProfessorDisciplinaRelatorio> dataProvider){

        
        ListHandler<ProfessorDisciplinaRelatorio> sortHandler = new ListHandler<ProfessorDisciplinaRelatorio>(dataProvider.getList());
        
        cellTable.addColumnSortHandler(sortHandler);   
        
        initSortHandler(sortHandler);

       
   
   }
    
    public void initSortHandler(ListHandler<ProfessorDisciplinaRelatorio> sortHandler){
        
        primeiroNomeColumn.setSortable(true);
        sortHandler.setComparator(primeiroNomeColumn, new Comparator<ProfessorDisciplinaRelatorio>() {
          @Override
          public int compare(ProfessorDisciplinaRelatorio o1, ProfessorDisciplinaRelatorio o2) {
            return o1.getPrimeiroNome().compareTo(o2.getPrimeiroNome());
          }
        }); 
        
        sobreNomeColumn.setSortable(true);
        sortHandler.setComparator(sobreNomeColumn, new Comparator<ProfessorDisciplinaRelatorio>() {
          @Override
          public int compare(ProfessorDisciplinaRelatorio o1, ProfessorDisciplinaRelatorio o2) {
            return o1.getSobreNome().compareTo(o2.getSobreNome());
          }
        }); 
        
        nomeCursoColumn.setSortable(true);
        sortHandler.setComparator(nomeCursoColumn, new Comparator<ProfessorDisciplinaRelatorio>() {
          @Override
          public int compare(ProfessorDisciplinaRelatorio o1, ProfessorDisciplinaRelatorio o2) {
            return o1.getNomeCurso().compareTo(o2.getNomeCurso());
          }
        }); 
        
        nomePeriodoColumn.setSortable(true);
        sortHandler.setComparator(nomePeriodoColumn, new Comparator<ProfessorDisciplinaRelatorio>() {
          @Override
          public int compare(ProfessorDisciplinaRelatorio o1, ProfessorDisciplinaRelatorio o2) {
            return o1.getNomePeriodo().compareTo(o2.getNomePeriodo());
          }
        }); 
        
        nomeDisciplinaColumn.setSortable(true);
        sortHandler.setComparator(nomeDisciplinaColumn, new Comparator<ProfessorDisciplinaRelatorio>() {
          @Override
          public int compare(ProfessorDisciplinaRelatorio o1, ProfessorDisciplinaRelatorio o2) {
            return o1.getNomeDisciplina().compareTo(o2.getNomeDisciplina());
          }
        }); 
        

            
    }
   

private void initTableColumns(){
    
   
    
        primeiroNomeColumn = new Column<ProfessorDisciplinaRelatorio, String>(new TextCell()) {
            @Override
            public String getValue(ProfessorDisciplinaRelatorio object) {
                return object.getPrimeiroNome();
            }
        };
        
        sobreNomeColumn = new Column<ProfessorDisciplinaRelatorio, String>(new TextCell()) {
            @Override
            public String getValue(ProfessorDisciplinaRelatorio object) {
                return object.getSobreNome();
            }
        };

        nomeCursoColumn = new Column<ProfessorDisciplinaRelatorio, String>(new TextCell()) {
            @Override
            public String getValue(ProfessorDisciplinaRelatorio object) {
                return object.getNomeCurso();
            }
        };
        
        nomePeriodoColumn = new Column<ProfessorDisciplinaRelatorio, String>(new TextCell()) {
            @Override
            public String getValue(ProfessorDisciplinaRelatorio object) {
                return object.getNomePeriodo();
            }
        };
        nomeDisciplinaColumn = new Column<ProfessorDisciplinaRelatorio, String>(new TextCell()) {
            @Override
            public String getValue(ProfessorDisciplinaRelatorio object) {
                return object.getNomeDisciplina();
            }
        };


        cellTable.addColumn(primeiroNomeColumn, txtConstants.usuarioPrimeiroNome());
        cellTable.addColumn(sobreNomeColumn, txtConstants.usuarioSobreNome());
        cellTable.addColumn(nomeCursoColumn, txtConstants.cursoNome());
        cellTable.addColumn(nomePeriodoColumn, txtConstants.periodoNome());
        cellTable.addColumn(nomeDisciplinaColumn, txtConstants.disciplinaNome());
        

        cellTable.getColumn(cellTable.getColumnIndex(primeiroNomeColumn)).setCellStyleNames("hand-over-default");
        cellTable.getColumn(cellTable.getColumnIndex(sobreNomeColumn)).setCellStyleNames("hand-over-default");
        cellTable.getColumn(cellTable.getColumnIndex(nomeCursoColumn)).setCellStyleNames("hand-over-default");
        cellTable.getColumn(cellTable.getColumnIndex(nomePeriodoColumn)).setCellStyleNames("hand-over-default");
        cellTable.getColumn(cellTable.getColumnIndex(nomeDisciplinaColumn)).setCellStyleNames("hand-over-default");


    }
	
}
