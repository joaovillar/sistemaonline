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
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.jornada.client.ambiente.coordenador.relatorio.customcell.CustomHeaderBuilderHistoricoAluno;
import com.jornada.client.ambiente.coordenador.relatorio.dialog.DialogBoxHistoricoAluno;
import com.jornada.client.ambiente.coordenador.relatorio.dialog.MpDialogBoxExcelHistoricoAluno;
import com.jornada.client.ambiente.coordenador.relatorio.dialog.MpDialogBoxExcelRelatorioBoletim;
import com.jornada.client.classes.listBoxes.MpSelectionAlunosPorCurso;
import com.jornada.client.classes.listBoxes.MpSelectionCurso;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.curso.MpListBoxStatusCurso;
import com.jornada.client.classes.listBoxes.suggestbox.MpListBoxPanelHelper;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelRight;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceNota;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.utility.MpUtilClient;

public class HistoricoAluno extends VerticalPanel {

    private final static int INT_POSITION_NAME = 0;

    MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
    MpDialogBox mpDialogBoxWarning = new MpDialogBox();
    MpPanelLoading mpLoading = new MpPanelLoading("images/radar.gif");


    MpListBoxPanelHelper mpHelperCurso;
    MpListBoxPanelHelper mpHelperAluno;

    VerticalPanel panel = new VerticalPanel();

    private CellTable<ArrayList<String>> cellTable;
    private ListDataProvider<ArrayList<String>> dataProvider;
    
    ArrayList<ArrayList<String>> arrayListBackup = new ArrayList<ArrayList<String>>();    
    private TextBox txtSearch;

    ArrayList<String> arrayHeadersText = new ArrayList<String>();
    ArrayList<String> arrayHeadersTextBackup = new ArrayList<String>();

    private MpListBoxStatusCurso listBoxStatusCurso;
    private MpSelectionCurso listBoxCurso;
    private MpSelectionAlunosPorCurso listBoxAlunosPorCurso;

    @SuppressWarnings("unused")
    private TelaInicialRelatorio telaInicialRelatorio;

    TextConstants txtConstants;

    ScrollPanel scrollPanel = new ScrollPanel();
    VerticalPanel vFormPanel;
    FlexTable flexTableNota;

//    private Usuario usuarioLogado;

    private static HistoricoAluno uniqueInstance;

    public static HistoricoAluno getInstance(final TelaInicialRelatorio telaInicialRelatorio) {

        if (uniqueInstance == null) {
            uniqueInstance = new HistoricoAluno(telaInicialRelatorio);
        }

        return uniqueInstance;

    }

    private HistoricoAluno(final TelaInicialRelatorio telaInicialRelatorio) {

        txtConstants = GWT.create(TextConstants.class);

        this.telaInicialRelatorio = telaInicialRelatorio;

//        usuarioLogado = this.telaInicialRelatorio.getMainView().getUsuarioLogado();

        mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
        mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
        mpLoading.setTxtLoading(txtConstants.geralCarregando());
        mpLoading.show();
        mpLoading.setVisible(false);

        FlexTable flexTable = new FlexTable();
        flexTable.setCellSpacing(3);
        flexTable.setCellPadding(3);

        MpLabelRight lblStatusCurso = new MpLabelRight(txtConstants.cursoStatus());
        MpLabelRight lblCurso = new MpLabelRight(txtConstants.curso());
        MpLabelRight lblAluno = new MpLabelRight(txtConstants.aluno());


        mpHelperCurso = new MpListBoxPanelHelper();
        mpHelperAluno = new MpListBoxPanelHelper();
        
        listBoxStatusCurso = new MpListBoxStatusCurso();
        listBoxStatusCurso.addChangeHandler(new MpStatusCursoChangeHandler());

        listBoxCurso = new MpSelectionCurso(true);
        listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());

        listBoxAlunosPorCurso = new MpSelectionAlunosPorCurso();
        listBoxAlunosPorCurso.addChangeHandler(new MpAlunoSelectionChangeHandler());

//        listBoxAlunos = new MpSelectionAlunoAmbientePais(usuarioLogado);
//        listBoxAlunos.addChangeHandler(new MpAlunoSelectionChangeHandler());


        // Add some standard form options
        int row = 1;
        
        
        flexTable.setWidget(row, 0, lblStatusCurso);
        flexTable.setWidget(row++, 1, listBoxStatusCurso);
//        flexTable.setWidget(row++, 2, mpHelperCurso);

        flexTable.setWidget(row, 0, lblCurso);
        flexTable.setWidget(row, 1, listBoxCurso);
        flexTable.setWidget(row++, 2, mpHelperCurso);
                                                     
        flexTable.setWidget(row, 0, lblAluno);
        flexTable.setWidget(row, 1, listBoxAlunosPorCurso);
        flexTable.setWidget(row, 2, mpHelperAluno);
        flexTable.setWidget(row++, 3, mpLoading);

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

    private class MpAlunoSelectionChangeHandler implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            mpHelperAluno.populateSuggestBox(listBoxAlunosPorCurso);
            int indexIdAluno = listBoxAlunosPorCurso.getSelectedIndex();
            if (indexIdAluno != -1) {
                mpLoading.setVisible(true);
                populateNotas();
            }
        }
    }



  

    public void initializeCellTable() {
        cellTable = new CellTable<ArrayList<String>>(15, GWT.<CellTableStyle> create(CellTableStyle.class));
        cellTable.setAutoHeaderRefreshDisabled(true);
        cellTable.setAutoFooterRefreshDisabled(true);

        dataProvider.addDataDisplay(cellTable);

        final MpSimplePager mpPager = new MpSimplePager();
        mpPager.setDisplay(cellTable);

        // ///////////////////////ColumnName//////////////////////////////////
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
        // ///////////////////////ColumnName//////////////////////////////////
        

        String strSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        SafeHtmlBuilder builder = new SafeHtmlBuilder();
        builder.appendHtmlConstant(strSpace + strSpace + "Áreas de Conhecimento" + strSpace + strSpace);
        SafeHtml safeHtml = builder.toSafeHtml();
        cellTable.addColumn(indexColumnName, safeHtml);
        


        for (int column = 0; column < arrayHeadersText.size(); column++) {

            String strHeaderText = arrayHeadersText.get(column);
            strHeaderText = strHeaderText.substring(strHeaderText.indexOf(FieldVerifier.INI_SEPARATOR)+FieldVerifier.INI_SEPARATOR.length());
 
//            final String strHeader = strHeaderText.substring(strHeaderText.indexOf(FieldVerifier.INI_SEPARATOR)+2, strHeaderText.length());
            final String strHeader = strHeaderText;
            final IndexedColumn indexedColumn = new IndexedColumn(column + INT_POSITION_NAME + 1);

            final Header<String> header = new Header<String>(new ClickableTextCell()) {
                @Override
                public String getValue() {
                    return strHeader+":::";           
                }       
            };
            

            final int intColumn = column;

            indexedColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
            indexedColumn.setSortable(true);
            indexedColumn.setFieldUpdater(new FieldUpdater<ArrayList<String>, String>() {
                @Override
                public void update(int index, ArrayList<String> object, final String value) {
                    
                    if (!value.equals("-")) {

                        
                        int idCurso = Integer.parseInt(listBoxCurso.getSelectedValue());
                        String strNomeCurso = listBoxCurso.getSelectedItemText();
                        int idUsuario = Integer.parseInt(listBoxAlunosPorCurso.getSelectedValue());
                        String strNomeDisciplina = object.get(0);
//                        strNomeDisciplina = strNomeDisciplina.substring(strNomeDisciplina.indexOf(FieldVerifier.INI_SEPARATOR)+FieldVerifier.INI_SEPARATOR.length());
                        Double mediaNotaCurso = Double.parseDouble(listBoxCurso.getListCurso().get(listBoxCurso.getSelectedIndex()).getMediaNota());

                        if (indexedColumn.getIndex() != arrayListBackup.size() + INT_POSITION_NAME) {
                            DialogBoxHistoricoAluno.getInstance(idCurso, idUsuario, strNomeCurso, strNomeDisciplina, mediaNotaCurso);
                        }
                    }

                }
            });

            sortHandler.setComparator(indexedColumn, new Comparator<ArrayList<String>>() {
                @Override
                public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                    return o1.get(intColumn + INT_POSITION_NAME + 1).compareTo(o2.get(intColumn + INT_POSITION_NAME + 1));
                }
            });

            cellTable.addColumn(indexedColumn, header);           
            
        }
        
        cellTable.setHeaderBuilder(new CustomHeaderBuilderHistoricoAluno(cellTable, arrayHeadersText));
        
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
        
//        MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
        
        if (txtSearch == null) {
            txtSearch = new TextBox();
            txtSearch.setStyleName("design_text_boxes");
        }
        
        txtSearch.addKeyUpHandler(new EnterKeyUpHandler());
//        btnFiltrar.addClickHandler(new ClickHandlerFiltrar());

        
        FlexTable flexTableSearch = new FlexTable();
        flexTableSearch.setWidth("370px");
        flexTableSearch.setWidget(0, 0, txtSearch);        
//        flexTableSearch.setWidget(0, 1, new MpSpaceVerticalPanel());
        flexTableSearch.setWidget(0, 1, mpPager);

//        flexTableSearch.setWidget(0, 3, btnFiltrar);   

        FlexTable flexTableMenu = new FlexTable();
        flexTableMenu.setCellPadding(0);
        flexTableMenu.setCellSpacing(0);
        flexTableMenu.setBorderWidth(0);
        flexTableMenu.setWidth("100%");

//        flexTableMenu.setWidget(0, 0, mpPager);
        flexTableMenu.setWidget(0, 0, flexTableSearch);
        flexTableMenu.setWidget(0, 1, flexTableImg);
        flexTableMenu.getCellFormatter().setWidth(0, 0, "70%");
        flexTableMenu.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
        flexTableMenu.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_BOTTOM);
        flexTableMenu.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_BOTTOM);
        flexTableNota.setBorderWidth(0);
        flexTableNota.clear();
        flexTableNota.setWidget(0, 0, flexTableMenu);
        flexTableNota.setWidget(1, 0, cellTable);

    }



    class IndexedColumn extends Column<ArrayList<String>, String> {
        private int index;
//        private boolean booAdicionaNota;

        public IndexedColumn(int index) {
            super(new ClickableTextCell());
            this.index = index;
//            this.booAdicionaNota = booAdicionaNota;
        }

        public int getIndex() {
            return this.index;
        }

        @Override
        public String getValue(ArrayList<String> object) {
            String strTest = "";
            try {
                strTest = object.get(this.index);
                System.out.println("Teste:" + strTest);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            return strTest;
        }

        @Override
        public String getCellStyleNames(Context context, ArrayList<String> object) {
            String strStyle = "";
            Double doubleMediaNotaCurso = Double.parseDouble(listBoxCurso.getListCurso().get(listBoxCurso.getSelectedIndex()).getMediaNota());
            try {
                double doubleNumber = Double.parseDouble(object.get(this.index));

               if (doubleNumber >= doubleMediaNotaCurso) {
                    strStyle = "table-boletim-cell-green";
                } else if (doubleNumber < doubleMediaNotaCurso) {
                    strStyle = "table-boletim-cell-red";
                } else {
                    strStyle = "";
                }
            } catch (Exception ex) {
                strStyle = "";
            }

            return strStyle;
        }

    }

    private void populateNotas() {
        mpLoading.setVisible(true);
        int idCurso = Integer.parseInt(listBoxCurso.getSelectedValue());
        int idAluno = Integer.parseInt(listBoxAlunosPorCurso.getSelectedValue());
        GWTServiceNota.Util.getInstance().getHistoricoAluno(idCurso, idAluno, new CallBackCarregarHistorico());
    }

   

    private class ClickHandlerExcel implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            int idCurso = Integer.parseInt(listBoxCurso.getSelectedValue());
            int idAluno = Integer.parseInt(listBoxAlunosPorCurso.getSelectedValue());

            MpDialogBoxExcelHistoricoAluno.getInstance(idCurso, idAluno);
        }
    }

    public void filtrarCellTable(String strFiltro) {

        removeCellTableFilter();

        strFiltro = strFiltro.toUpperCase();

        if (!strFiltro.isEmpty()) {

            int i = 0;
            while (i < dataProvider.getList().size()) {

                ArrayList<String> row = dataProvider.getList().get(i);

                String strJuntaTexto = "";
                for (int j=0;j<row.size();j++) {
                    String strText  = row.get(j);
                    strJuntaTexto += strText.toUpperCase();
                }

                if (!strJuntaTexto.contains(strFiltro)) {
                    dataProvider.getList().remove(i);
                    i = 0;
                    continue;
                }

                i++;
            }

        }

    }

    public void removeCellTableFilter() {

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

   
//   private class ClickHandlerFiltrar implements ClickHandler {
//       public void onClick(ClickEvent event) {
//           filtrarCellTable(txtSearch.getText());
//       }
//   }   
   
   private class CallBackCarregarHistorico implements AsyncCallback<ArrayList<ArrayList<String>>> {

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
           arrayListBackup.clear();
           arrayHeadersText.clear();
           arrayHeadersTextBackup.clear();
           
           ArrayList<String> listColumns = list.get(0);
           for (int i = 1; i < listColumns.size(); i++) {
               String header = listColumns.get(i);
               arrayHeadersText.add(header);
               arrayHeadersTextBackup.add(listColumns.get(i));
           }   

           for (int i = 1; i < list.size(); i++) {
               ArrayList<String> listRow = list.get(i);
               String strText = listRow.get(0);
               strText = strText.substring(strText.indexOf(FieldVerifier.INI_SEPARATOR)+FieldVerifier.INI_SEPARATOR.length());
               listRow.set(0, strText);
               dataProvider.getList().add(list.get(i));
               arrayListBackup.add(list.get(i));
           }

           initializeCellTable();

       }

   }
   
   
   private class MpStatusCursoChangeHandler implements ChangeHandler {
       public void onChange(ChangeEvent event) {
           mpHelperCurso.populateSuggestBox(listBoxCurso);
           boolean booStatusCurso = Boolean.parseBoolean(listBoxStatusCurso.getSelectedValue());
           listBoxCurso.populateComboBox(booStatusCurso);

       }
   }
   
   private class MpCursoSelectionChangeHandler implements ChangeHandler {
       public void onChange(ChangeEvent event) {
           mpHelperCurso.populateSuggestBox(listBoxCurso);
           int idCurso = Integer.parseInt(listBoxCurso.getSelectedValue());
           listBoxAlunosPorCurso.populateComboBox(idCurso);
           flexTableNota.clear();
       }
   }


}
