package com.jornada.client.ambiente.coordenador.relatorio;

import java.util.ArrayList;
import java.util.Comparator;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.jornada.client.ambiente.general.nota.DialogBoxNota;
import com.jornada.client.classes.listBoxes.MpSelectionCurso;
import com.jornada.client.classes.listBoxes.MpSelectionPeriodo;
import com.jornada.client.classes.listBoxes.suggestbox.MpListBoxPanelHelper;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelRight;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceDisciplina;
import com.jornada.client.service.GWTServiceNota;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.utility.MpUtilClient;

public class BoletimPeriodo extends VerticalPanel {

    private final static int INT_POSITION_NAME=1;

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpLoading = new MpPanelLoading("images/radar.gif");
	
	MpListBoxPanelHelper mpHelperCurso;
	
	VerticalPanel panel = new VerticalPanel();
	
	private CellTable<ArrayList<String>> cellTable;
    private ListDataProvider<ArrayList<String>> dataProvider;
    
    ArrayList<String> arrayDisciplinaColumns = new ArrayList<String>();

	private MpSelectionCurso listBoxCurso;	
	private MpSelectionPeriodo listBoxPeriodo; 

	@SuppressWarnings("unused")
	private TelaInicialRelatorio telaInicialPeriodo;
	
	TextConstants txtConstants;
	
	ScrollPanel scrollPanel = new ScrollPanel();
	VerticalPanel vFormPanel;
	FlexTable flexTableNota;
	
	private static BoletimPeriodo uniqueInstance;
	
	public static BoletimPeriodo getInstance(final TelaInicialRelatorio telaInicialRelatorio){

		if(uniqueInstance==null){
			uniqueInstance = new BoletimPeriodo(telaInicialRelatorio);
		}
		
		return uniqueInstance;
		
	}

	private BoletimPeriodo(final TelaInicialRelatorio telaInicialRelatorio) {
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialPeriodo=telaInicialRelatorio;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpLoading.setTxtLoading(txtConstants.geralCarregando());
		mpLoading.show();
		mpLoading.setVisible(false);

		FlexTable flexTable = new FlexTable();
		flexTable.setCellSpacing(3);
		flexTable.setCellPadding(3);


		MpLabelRight lblCurso = new MpLabelRight(txtConstants.curso());
		MpLabelRight lblPeriodo = new MpLabelRight(txtConstants.periodo());
		
		mpHelperCurso = new  MpListBoxPanelHelper();
		
		listBoxCurso = new MpSelectionCurso(true);
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());
		
		listBoxPeriodo = new MpSelectionPeriodo();
		listBoxPeriodo.addChangeHandler(new MpPeriodoSelectionChangeHandler());
		
		// Add some standard form options
		int row = 1;

		flexTable.setWidget(row, 0, lblCurso);flexTable.setWidget(row, 1, listBoxCurso); flexTable.setWidget(row++, 2, mpHelperCurso);//flexTable.setWidget(row++, 2, txtFiltroNomeCurso);
		flexTable.setWidget(row, 0, lblPeriodo);flexTable.setWidget(row, 1, listBoxPeriodo); flexTable.setWidget(row++, 2, mpLoading); 



		vFormPanel = new VerticalPanel();
		flexTableNota = new FlexTable();

		
		vFormPanel.add(flexTable);
		vFormPanel.add(flexTableNota);

        scrollPanel = new ScrollPanel();
        scrollPanel.setHeight(Integer.toString(TelaInicialRelatorio.intHeightTable - 40) + "px");
        scrollPanel.setWidth("100%");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(vFormPanel);
		
		super.setWidth("100%");

		super.add(scrollPanel);

	}


	public void updateClientData() {
		listBoxCurso.populateComboBox();
	}
	
    private class MpCursoSelectionChangeHandler implements ChangeHandler {
        public void onChange(ChangeEvent event) {  
            mpHelperCurso.populateSuggestBox(listBoxCurso);
            int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
            listBoxPeriodo.populateComboBox(idCurso);
            flexTableNota.clear();
        }  
    }  
    
    private class MpPeriodoSelectionChangeHandler implements ChangeHandler {
        public void onChange(ChangeEvent event) {  
            
            int indexIdPeriodo = listBoxPeriodo.getSelectedIndex();
            if (indexIdPeriodo != -1) {
                int idPeriodo = Integer.parseInt(listBoxPeriodo.getValue(indexIdPeriodo));
                mpLoading.setVisible(true);
                populateColunasDisciplinas(idPeriodo);
                
            }
        }  
    }      
    

    private class CallBackCarregarNotas implements AsyncCallback<ArrayList<ArrayList<String>>>{

        @Override
        public void onFailure(Throwable caught) {
            mpLoading.setVisible(false);
            MpUtilClient.isRefreshRequired();
        }

        @Override
        public void onSuccess(ArrayList<ArrayList<String>> list) {
            mpLoading.setVisible(false);
            MpUtilClient.isRefreshRequired(list);
            
            dataProvider = new ListDataProvider<ArrayList<String>>();

            dataProvider.getList().clear();

            for (int i = 0; i < list.size(); i++) {
                dataProvider.getList().add(list.get(i));               
            }
            
            initializeCellTable();
        
        }
        
    }
    
    public void initializeCellTable(){
        cellTable = new CellTable<ArrayList<String>>(15,GWT.<CellTableStyle> create(CellTableStyle.class));
        cellTable.setAutoHeaderRefreshDisabled(true);
        cellTable.setAutoFooterRefreshDisabled(true);
        
        dataProvider.addDataDisplay(cellTable); 
        
        final MpSimplePager mpPager = new MpSimplePager();
        mpPager.setDisplay(cellTable);
        
        /////////////////////////ColumnName//////////////////////////////////
        IndexedColumn indexColumnName = new IndexedColumn(INT_POSITION_NAME);
        
        ListHandler<ArrayList<String>> sortHandler = new ListHandler<ArrayList<String>>(dataProvider.getList());
        
        indexColumnName.setSortable(true);
        sortHandler.setComparator(indexColumnName, new Comparator<ArrayList<String>>() {
          @Override
          public int compare(ArrayList<String> o1, ArrayList<String> o2) {
              System.out.println(o1.get(INT_POSITION_NAME));
            return o1.get(INT_POSITION_NAME).compareTo(o2.get(INT_POSITION_NAME));
          }
        }); 
        cellTable.addColumnSortHandler(sortHandler);
        /////////////////////////ColumnName//////////////////////////////////
        
        String strSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        SafeHtmlBuilder builder = new SafeHtmlBuilder();
        builder.appendHtmlConstant(strSpace + strSpace + txtConstants.aluno() + strSpace + strSpace);
        SafeHtml safeHtml = builder.toSafeHtml();
        cellTable.addColumn(indexColumnName, safeHtml);
        
        for (int column = 0; column < arrayDisciplinaColumns.size(); column++) { 
            final String strDisciplina = Curso.getAbreviarNomeCurso(arrayDisciplinaColumns.get(column)); 
//            final String strDisciplina = arrayDisciplinaColumns.get(column); 
            final IndexedColumn indexedColumn = new IndexedColumn(column+INT_POSITION_NAME+1);
            
            final Header<String> header = new Header<String>(new ClickableTextCell()) {
                @Override
                public String getValue() {
                    return  strDisciplina;
                }    
                
            };
            
           
            final int intColumn = column;
            
            indexedColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
            indexedColumn.setSortable(true);
            
            indexedColumn.setFieldUpdater(new FieldUpdater<ArrayList<String>, String>() {
                @Override
                public void update(int index, ArrayList<String> object, final String value) {
                    

                    String strIdUsuario = dataProvider.getList().get(index).get(0);
                    int idUsuario = Integer.parseInt(strIdUsuario);
                    int idCurso = Integer.parseInt(listBoxCurso.getSelectedValue());
                    String strNomeCurso = listBoxCurso.getSelectedItemText();
                    String strNomePeriodo = listBoxPeriodo.getSelectedItemText();
                    String strNomeDisciplina = arrayDisciplinaColumns.get(indexedColumn.getIndex()-INT_POSITION_NAME-1);
                    Double mediaNotaCurso = Double.parseDouble(listBoxCurso.getListCurso().get(listBoxCurso.getSelectedIndex()).getMediaNota());
                    
//                    Window.alert(Integer.toString(column)+":"+value+":"+nomeUsuario+":"+idUsuario+":"+header.getValue());
                    DialogBoxNota.getInstance(idUsuario, idCurso, strNomeCurso, strNomeDisciplina, strNomePeriodo, mediaNotaCurso);
                }
            });
            
            sortHandler.setComparator(indexedColumn, new Comparator<ArrayList<String>>() {
                @Override
                public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                    System.out.println(o1.get(intColumn+INT_POSITION_NAME+1));
                  return o1.get(intColumn+INT_POSITION_NAME+1).compareTo(o2.get(intColumn+INT_POSITION_NAME+1));
                }
              }); 
            
            cellTable.addColumn(indexedColumn,header);
        }
        
        
        
        Image imgExcel = new Image("images/excel.24.png");
        imgExcel.addClickHandler(new ClickHandlerExcel());
        imgExcel.setStyleName("hand-over");
        imgExcel.setTitle(txtConstants.geralExcel());
        
        int columnImg = 0;
        FlexTable flexTableImg = new FlexTable();
        flexTableImg.setCellPadding(2);
        flexTableImg.setCellSpacing(2);
        flexTableImg.setWidget(0, columnImg++, imgExcel);
        flexTableImg.setBorderWidth(0);
        
        FlexTable flexTableMenu = new FlexTable();
        flexTableMenu.setCellPadding(0);
        flexTableMenu.setCellSpacing(0);
        flexTableMenu.setBorderWidth(0);
        flexTableMenu.setWidth("100%");     
        flexTableMenu.setWidget(0, 0, mpPager);
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
    
    class IndexedColumn extends Column<ArrayList<String>, String> {
        private int index;
        public IndexedColumn(int index) {
            super(new ClickableTextCell());
            this.index = index;
        }
        
      
        public int getIndex(){
            return this.index;
        }

        @Override
        public String getValue(ArrayList<String> object) {
            String strTest="";
            try{
                strTest = object.get(this.index);
                System.out.println("Teste:"+strTest);
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
            
            return strTest;
        }
        
        @Override
        public String getCellStyleNames(Context context, ArrayList<String>  object) {
            Double doubleMediaNotaCurso = Double.parseDouble(listBoxCurso.getListCurso().get(listBoxCurso.getSelectedIndex()).getMediaNota());
            try {
                double doubleNumber = Double.parseDouble(object.get(this.index));
                if (doubleNumber >= doubleMediaNotaCurso) {
                    return "table-boletim-cell-green";
                } else if (doubleNumber < doubleMediaNotaCurso) {
                    return "table-boletim-cell-red";
                }else{
                    return "";
                }
            } catch (Exception ex) {
                return "";
            }
              
        }  
                
    }    
    
    private void populateColunasDisciplinas(int idPeriodo) {
        GWTServiceDisciplina.Util.getInstance().getDisciplinasPeloPeriodo(idPeriodo, new CallBackCarregarDisciplina());
    }
    
    private void populateNotas(){
        mpLoading.setVisible(true);
        int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
        int idPeriodo = Integer.parseInt(listBoxPeriodo.getValue(listBoxPeriodo.getSelectedIndex()));
       GWTServiceNota.Util.getInstance().getBoletimPeriodo(idCurso, idPeriodo, new CallBackCarregarNotas());
    }
    
    
    private class CallBackCarregarDisciplina implements AsyncCallback<ArrayList<Disciplina>>{
        
        @Override
        public void onFailure(Throwable caught) {
            mpLoading.setVisible(false);
            MpUtilClient.isRefreshRequired();
        }

        @Override
        public void onSuccess(ArrayList<Disciplina> list) {
            mpLoading.setVisible(false);
            MpUtilClient.isRefreshRequired(list);
            arrayDisciplinaColumns.clear();
            for (int i = 0; i < list.size(); i++) {
                arrayDisciplinaColumns.add(list.get(i).getNome());
            }        

            populateNotas();
//            initializeCellTable();        
        }
    }
	
    private class ClickHandlerExcel implements ClickHandler{
        @Override
        public void onClick(ClickEvent event) {
            int idCurso = Integer.parseInt(listBoxCurso.getSelectedValue());
            int idPeriodo = Integer.parseInt(listBoxPeriodo.getSelectedValue());
            MpDialogBoxExcelRelatorioBoletim.getInstance(idCurso, idPeriodo);                 
        }
    }
    
	
}
