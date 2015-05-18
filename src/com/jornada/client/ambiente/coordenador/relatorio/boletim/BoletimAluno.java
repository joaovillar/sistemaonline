package com.jornada.client.ambiente.coordenador.relatorio.boletim;

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
import com.jornada.client.ambiente.coordenador.relatorio.boletim.customcell.CustomHeaderBuilderBoletimAluno;
import com.jornada.client.ambiente.coordenador.relatorio.boletim.dialog.MpDialogBoxExcelRelatorioBoletim;
import com.jornada.client.ambiente.coordenador.relatorio.boletim.dialog.MpDialogBoxMultipleBoletimAluno;
import com.jornada.client.ambiente.general.nota.DialogBoxNotaBoletimAluno;
import com.jornada.client.ambiente.general.nota.DialogBoxNotasAno;
import com.jornada.client.classes.listBoxes.MpSelectionAlunosPorCurso;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxCursoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.suggestbox.MpListBoxPanelHelper;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelRight;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceNota;
import com.jornada.shared.classes.Nota;
import com.jornada.shared.classes.TipoAvaliacao;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.utility.MpUtilClient;

public class BoletimAluno extends VerticalPanel {

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

    private MpListBoxCursoAmbienteProfessor listBoxCurso;
    private MpSelectionAlunosPorCurso listBoxAlunosPorCurso;
//    private MpSelectionDisciplinaAmbienteProfessor listBoxDisciplina;

    private TelaInicialBoletim telaInicialRelatorio;

    TextConstants txtConstants;

    ScrollPanel scrollPanel = new ScrollPanel();
    VerticalPanel vFormPanel;
    FlexTable flexTableNota;

    private Usuario usuarioLogado;

    private static BoletimAluno uniqueInstance;

    public static BoletimAluno getInstance(final TelaInicialBoletim telaInicialRelatorio) {

        if (uniqueInstance == null) {
            uniqueInstance = new BoletimAluno(telaInicialRelatorio);
        }

        return uniqueInstance;

    }

    private BoletimAluno(final TelaInicialBoletim telaInicialRelatorio) {

        txtConstants = GWT.create(TextConstants.class);

        this.telaInicialRelatorio = telaInicialRelatorio;

        usuarioLogado = this.telaInicialRelatorio.getMainView().getUsuarioLogado();

        mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
        mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
        mpLoading.setTxtLoading(txtConstants.geralCarregando());
        mpLoading.show();
        mpLoading.setVisible(false);

        FlexTable flexTable = new FlexTable();
        flexTable.setCellSpacing(3);
        flexTable.setCellPadding(3);

        MpLabelRight lblCurso = new MpLabelRight(txtConstants.curso());
        MpLabelRight lblAluno = new MpLabelRight(txtConstants.aluno());


        mpHelperCurso = new MpListBoxPanelHelper();
        mpHelperAluno = new MpListBoxPanelHelper();

        listBoxCurso = new MpListBoxCursoAmbienteProfessor(usuarioLogado);
        listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());

        listBoxAlunosPorCurso = new MpSelectionAlunosPorCurso();
        listBoxAlunosPorCurso.addChangeHandler(new MpAlunoSelectionChangeHandler());


        // Add some standard form options
        int row = 1;

        flexTable.setWidget(row, 0, lblCurso);
        flexTable.setWidget(row, 1, listBoxCurso);
        flexTable.setWidget(row++, 2, mpHelperCurso);// flexTable.setWidget(row++,
                                                     // 2, txtFiltroNomeCurso);
        flexTable.setWidget(row, 0, lblAluno);
        flexTable.setWidget(row, 1, listBoxAlunosPorCurso);
        flexTable.setWidget(row, 2, mpHelperAluno);
        flexTable.setWidget(row++, 3, mpLoading);

        vFormPanel = new VerticalPanel();
        flexTableNota = new FlexTable();

        vFormPanel.add(flexTable);
        vFormPanel.add(flexTableNota);

        scrollPanel = new ScrollPanel();
        scrollPanel.setHeight(Integer.toString(TelaInicialBoletim.intHeightTable - 40) + "px");
        scrollPanel.setWidth("100%");
        scrollPanel.setAlwaysShowScrollBars(false);
        scrollPanel.add(vFormPanel);

        super.setWidth("100%");

        super.add(scrollPanel);

    }

    public void updateClientData() {
        listBoxCurso.populateComboBox(usuarioLogado);
    }

    private class MpCursoSelectionChangeHandler implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            mpHelperCurso.populateSuggestBox(listBoxCurso);
            int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
            listBoxAlunosPorCurso.populateComboBox(idCurso);
            flexTableNota.clear();
        }
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



    private class CallBackCarregarNotas implements AsyncCallback<ArrayList<ArrayList<String>>> {

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
                arrayHeadersText.add(listColumns.get(i));
                arrayHeadersTextBackup.add(listColumns.get(i));
            }   

            for (int i = 1; i < list.size(); i++) {
                dataProvider.getList().add(list.get(i));
                arrayListBackup.add(list.get(i));
            }

            initializeCellTable();

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
        IndexedColumn indexColumnName = new IndexedColumn(INT_POSITION_NAME, false);

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
        builder.appendHtmlConstant(strSpace + strSpace + "Disciplina" + strSpace + strSpace);
        SafeHtml safeHtml = builder.toSafeHtml();
        cellTable.addColumn(indexColumnName, safeHtml);
        


        for (int column = 0; column < arrayHeadersText.size(); column++) {

            String strHeaderText = arrayHeadersText.get(column);
 
            String strPeriodo = strHeaderText.substring(strHeaderText.indexOf("[") + 1, strHeaderText.indexOf("]") - 1);
            String strNomeAval = strHeaderText.substring(strHeaderText.indexOf("]") + 1, strHeaderText.length());

            if (column == (arrayHeadersText.size() - 1) || column == (arrayHeadersText.size() - 2) || column == (arrayHeadersText.size() - 3)) {
                strPeriodo = strPeriodo.substring(0, 7).toUpperCase() + strNomeAval;
            } else {
                strPeriodo = "[" + strPeriodo.substring(0, 7).toUpperCase() + "]<br>" + strNomeAval;
            }

 
            final String strHeader = strPeriodo;

            boolean booAdicionarNota = false;
            if (strHeader.equals(TipoAvaliacao.STR_ADICIONAL_NOTA)) {
                booAdicionarNota = true;
            }

            final IndexedColumn indexedColumn = new IndexedColumn(column + INT_POSITION_NAME + 1, booAdicionarNota);

            final Header<String> header = new Header<String>(new ClickableTextCell()) {
                @Override
                public String getValue() {
                    return strHeader;
           
                }       
                @Override
                public void render(Context context,SafeHtmlBuilder sb){
                    
                    String strTitle=getValue();
                    if(strTitle.contains(Nota.STR_BOLETIM_ALUNO_EX)){
                        strTitle = "Exame Nota";
                    }else if(strTitle.contains(Nota.STR_BOLETIM_ALUNO_MFP)){
                        strTitle = "Média Final Provisória";
                    }else if(strTitle.contains(Nota.STR_BOLETIM_ALUNO_MFA)){
                        strTitle = Nota.STR_MEDIA_FINAL_ANUAL;
                    }else if(strTitle.contains(Nota.STR_BOLETIM_ALUNO_REC)){
                        strTitle = txtConstants.avaliacaoRecuperacao();
                    }else if(strTitle.contains(Nota.STR_BOLETIM_ALUNO_MP)){
                        strTitle = "Média Trimestral Provisória";
                    }else if(strTitle.contains(Nota.STR_BOLETIM_ALUNO_MF)){
                        strTitle = "Média Final do Trimestre";
                    }   
                    sb.appendHtmlConstant("<div align=\"center\"><span title=\""+strTitle+"\">"+getValue()+"</span></div>"); 
                }


            };
            

            final int intColumn = column;

            indexedColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
            indexedColumn.setSortable(true);
            indexedColumn.setFieldUpdater(new FieldUpdater<ArrayList<String>, String>() {
                @Override
                public void update(int index, ArrayList<String> object, final String value) {

                    int idUsuario = Integer.parseInt(listBoxAlunosPorCurso.getSelectedValue());
                    int idCurso = Integer.parseInt(listBoxCurso.getSelectedValue());
                    String strNomeCurso = listBoxCurso.getSelectedItemText();
                    Double mediaNotaCurso = Double.parseDouble(listBoxCurso.getListCurso().get(listBoxCurso.getSelectedIndex()).getMediaNota());
                    String strHeader = arrayHeadersTextBackup.get(indexedColumn.getIndex() - 1);
                    String strNomePeriodo = strHeader.substring(1, strHeader.indexOf("]"));
                    String strNomeProva = strHeader.substring(strHeader.indexOf("]") + 1);

                    String strNomeDisciplina = object.get(0);
                    if (intColumn == (arrayHeadersText.size() - 1) || intColumn == (arrayHeadersText.size() - 2) || intColumn == (arrayHeadersText.size() - 3)) {
//                        DialogBoxNotaBoletimAluno.getInstance(idUsuario, idCurso, strNomeCurso, strNomeDisciplina, strNomePeriodo, mediaNotaCurso, strNomeProva);
                        DialogBoxNotasAno.getInstance(idUsuario, idCurso, strNomeCurso, strNomeDisciplina, mediaNotaCurso);
                    } else {
                        DialogBoxNotaBoletimAluno.getInstance(idUsuario, idCurso, strNomeCurso, strNomeDisciplina, strNomePeriodo, mediaNotaCurso, strNomeProva);
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
        
        cellTable.setHeaderBuilder(new CustomHeaderBuilderBoletimAluno(cellTable, arrayHeadersTextBackup));
        
        Image imgMultiple = new Image("images/table-multiple-icon-24.png");
        imgMultiple.addClickHandler(new ClickHandlerMultipleExcel());
        imgMultiple.setStyleName("hand-over");
        imgMultiple.setTitle(txtConstants.geralMultipleExcel());   
        
        Image imgExcel = new Image("images/excel.24.png");
        imgExcel.addClickHandler(new ClickHandlerExcel());
        imgExcel.setStyleName("hand-over");
        imgExcel.setTitle(txtConstants.geralExcel());

        int columnImg = 0;
        FlexTable flexTableImg = new FlexTable();
        flexTableImg.setCellPadding(2);
        flexTableImg.setCellSpacing(2);
        flexTableImg.setWidget(0, columnImg++, imgExcel);
        flexTableImg.setWidget(0, columnImg++, imgMultiple);
        flexTableImg.setBorderWidth(0);
        
//        MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
        
        if (txtSearch == null) {
            txtSearch = new TextBox();
            txtSearch.setStyleName("design_text_boxes");
        }
        
        txtSearch.addKeyUpHandler(new EnterKeyUpHandler());
//        btnFiltrar.addClickHandler(new ClickHandlerFiltrar());

        
        FlexTable flexTableSearch = new FlexTable();
        flexTableSearch.setWidth("350px");
        flexTableSearch.setWidget(0, 0, txtSearch);
        flexTableSearch.setWidget(0, 2, mpPager);
//        flexTableSearch.setWidget(0, 1, new MpSpaceVerticalPanel());
        
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
        private boolean booAdicionaNota;

        public IndexedColumn(int index, boolean booAdicionaNota) {
            super(new ClickableTextCell());
            this.index = index;
            this.booAdicionaNota = booAdicionaNota;
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

                if (booAdicionaNota == true) {
                    strStyle = "table-boletim-cell-green";
                } else if (doubleNumber >= doubleMediaNotaCurso) {
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

//    private void populateColunasAvaliacoes() {
//        int idCurso = Integer.parseInt(listBoxCurso.getSelectedValue());
//        int idAluno = Integer.parseInt(listBoxAlunosPorCurso.getSelectedValue());
////        GWTServiceAvaliacao.Util.getInstance().getHeaderRelatorioBoletimDisciplina(idCurso, idAluno, idDisciplina, new CallBackCarregarAvaliacao());
//    }

    private void populateNotas() {
        mpLoading.setVisible(true);
        int idCurso = Integer.parseInt(listBoxCurso.getSelectedValue());
        int idAluno = Integer.parseInt(listBoxAlunosPorCurso.getSelectedValue());
        GWTServiceNota.Util.getInstance().getBoletimAluno(idCurso, idAluno, new CallBackCarregarNotas());
    }

   

    private class ClickHandlerExcel implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            int idCurso = Integer.parseInt(listBoxCurso.getSelectedValue());
            int idAluno = Integer.parseInt(listBoxAlunosPorCurso.getSelectedValue());

            MpDialogBoxExcelRelatorioBoletim.getInstanceBoletimAluno(idCurso, idAluno);
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
   
   private class ClickHandlerMultipleExcel implements ClickHandler {
       @Override
       public void onClick(ClickEvent event) {
           MpDialogBoxMultipleBoletimAluno.getInstance();
       }
   }


}
