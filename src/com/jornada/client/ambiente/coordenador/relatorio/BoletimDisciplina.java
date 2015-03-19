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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.jornada.client.ambiente.coordenador.relatorio.dialog.MpDialogBoxMultipleBoletimDisciplina;
import com.jornada.client.ambiente.general.nota.DialogBoxNota;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionCursoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionDisciplinaAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionPeriodoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.suggestbox.MpListBoxPanelHelper;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelRight;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.textbox.MpTextBox;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceAvaliacao;
import com.jornada.client.service.GWTServiceNota;
import com.jornada.shared.classes.TipoAvaliacao;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.utility.MpUtilClient;

public class BoletimDisciplina extends VerticalPanel {

    private final static int INT_POSITION_NAME = 1;

    MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
    MpDialogBox mpDialogBoxWarning = new MpDialogBox();
    MpPanelLoading mpLoading = new MpPanelLoading("images/radar.gif");

    MpListBoxPanelHelper mpHelperCurso;

    VerticalPanel panel = new VerticalPanel();

    private CellTable<ArrayList<String>> cellTable;
    private ListDataProvider<ArrayList<String>> dataProvider;
    
    ArrayList<ArrayList<String>> arrayListBackup = new ArrayList<ArrayList<String>>();    
    private MpTextBox txtSearch;

    ArrayList<String> arrayAvaliacaoColumns = new ArrayList<String>();

    private MpSelectionCursoAmbienteProfessor listBoxCurso;
    private MpSelectionPeriodoAmbienteProfessor listBoxPeriodo;
    private MpSelectionDisciplinaAmbienteProfessor listBoxDisciplina;

    private TelaInicialRelatorio telaInicialRelatorio;

    TextConstants txtConstants;

    ScrollPanel scrollPanel = new ScrollPanel();
    VerticalPanel vFormPanel;
    FlexTable flexTableNota;

    private Usuario usuarioLogado;

    private static BoletimDisciplina uniqueInstance;

    public static BoletimDisciplina getInstance(final TelaInicialRelatorio telaInicialRelatorio) {

        if (uniqueInstance == null) {
            uniqueInstance = new BoletimDisciplina(telaInicialRelatorio);
        }

        return uniqueInstance;

    }

    private BoletimDisciplina(final TelaInicialRelatorio telaInicialRelatorio) {

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
        MpLabelRight lblPeriodo = new MpLabelRight(txtConstants.periodo());
        MpLabelRight lblDisciplina = new MpLabelRight(txtConstants.disciplina());

        mpHelperCurso = new MpListBoxPanelHelper();

        listBoxCurso = new MpSelectionCursoAmbienteProfessor(usuarioLogado);
        listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());

        listBoxPeriodo = new MpSelectionPeriodoAmbienteProfessor(usuarioLogado);
        listBoxPeriodo.addChangeHandler(new MpPeriodoSelectionChangeHandler());

        listBoxDisciplina = new MpSelectionDisciplinaAmbienteProfessor(usuarioLogado);
        listBoxDisciplina.addChangeHandler(new MpDisciplinaSelectionChangeHandler());

        // Add some standard form options
        int row = 1;

        flexTable.setWidget(row, 0, lblCurso);
        flexTable.setWidget(row, 1, listBoxCurso);
        flexTable.setWidget(row++, 2, mpHelperCurso);// flexTable.setWidget(row++,
                                                     // 2, txtFiltroNomeCurso);
        flexTable.setWidget(row, 0, lblPeriodo);
        flexTable.setWidget(row++, 1, listBoxPeriodo);
        flexTable.setWidget(row, 0, lblDisciplina);
        flexTable.setWidget(row, 1, listBoxDisciplina);
        flexTable.setWidget(row++, 2, mpLoading);

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
        listBoxCurso.populateComboBox(usuarioLogado);
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
                listBoxDisciplina.populateComboBox(idPeriodo);

            } else {
                listBoxDisciplina.clear();
            }
        }
    }

    private class MpDisciplinaSelectionChangeHandler implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            int indexIdDisciplina = listBoxDisciplina.getSelectedIndex();
            if (indexIdDisciplina != -1) {
                mpLoading.setVisible(true);
                populateColunasAvaliacoes();
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

            for (int i = 0; i < list.size(); i++) {
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
        builder.appendHtmlConstant(strSpace + strSpace + txtConstants.aluno() + strSpace + strSpace);
        SafeHtml safeHtml = builder.toSafeHtml();
        cellTable.addColumn(indexColumnName, safeHtml);

        for (int column = 0; column < arrayAvaliacaoColumns.size(); column++) {

            String strAvaliacaoText = arrayAvaliacaoColumns.get(column);
            int indexIdAvaliacao = strAvaliacaoText.indexOf("|");
            if (indexIdAvaliacao != -1) {
                strAvaliacaoText = strAvaliacaoText.substring(indexIdAvaliacao + 1);
            }
            final String strAvaliacao = strAvaliacaoText;

            boolean booAdicionarNota = false;
            if (strAvaliacao.equals(TipoAvaliacao.STR_ADICIONAL_NOTA)) {
                booAdicionarNota = true;
            }

            final IndexedColumn indexedColumn = new IndexedColumn(column + INT_POSITION_NAME + 1, booAdicionarNota);

            final Header<String> header = new Header<String>(new ClickableTextCell()) {
                @Override
                public String getValue() {
                    return strAvaliacao;
                }

            };

            final int intColumn = column;

            indexedColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
            indexedColumn.setSortable(true);

            indexedColumn.setFieldUpdater(new FieldUpdater<ArrayList<String>, String>() {
                @Override
                public void update(int index, ArrayList<String> object, final String value) {

                    if (!value.equals("-")) {
                        String strIdUsuario = dataProvider.getList().get(index).get(0);
                        int idUsuario = Integer.parseInt(strIdUsuario);
                        int idCurso = Integer.parseInt(listBoxCurso.getSelectedValue());
                        String strNomeCurso = listBoxCurso.getSelectedItemText();
                        String strNomePeriodo = listBoxPeriodo.getSelectedItemText();
                        String strNomeDisciplina = listBoxDisciplina.getSelectedItemText();
                        Double mediaNotaCurso = Double.parseDouble(listBoxCurso.getListCurso().get(listBoxCurso.getSelectedIndex()).getMediaNota());

                        String strAvaliacaoColumn = arrayAvaliacaoColumns.get(indexedColumn.getIndex() - INT_POSITION_NAME - 1);
                        int indexIdAvaliacao = strAvaliacaoColumn.indexOf("|");
                        if (indexIdAvaliacao == -1) {
                            DialogBoxNota.getInstance(idUsuario, idCurso, strNomeCurso, strNomeDisciplina, strNomePeriodo, mediaNotaCurso);
                        } else {
                            String strIdAvaliacao = strAvaliacaoColumn.substring(0, indexIdAvaliacao);
                            int idAvaliacao = Integer.parseInt(strIdAvaliacao);
                            DialogBoxNota.getInstance(idUsuario, idCurso, strNomeCurso, strNomeDisciplina, strNomePeriodo, idAvaliacao, mediaNotaCurso);
                        }

                    }
                }
            });

            sortHandler.setComparator(indexedColumn, new Comparator<ArrayList<String>>() {
                @Override
                public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                    System.out.println(o1.get(intColumn + INT_POSITION_NAME + 1));
                    return o1.get(intColumn + INT_POSITION_NAME + 1).compareTo(o2.get(intColumn + INT_POSITION_NAME + 1));
                }
            });

            cellTable.addColumn(indexedColumn, header);
        }
        
        
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
        
        MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
        
        if (txtSearch == null) {
            txtSearch = new MpTextBox();
            txtSearch.setWidth("130px");
            txtSearch.setStyleName("design_text_boxes");
        }
        
        txtSearch.addKeyUpHandler(new EnterKeyUpHandler());
        btnFiltrar.addClickHandler(new ClickHandlerFiltrar());

        
        FlexTable flexTableSearch = new FlexTable();
        flexTableSearch.setCellPadding(0);
        flexTableSearch.setCellSpacing(0);
        flexTableSearch.setWidth("350px");
        flexTableSearch.setWidget(0, 0, txtSearch);
//        flexTableSearch.setWidget(0, 1, new MpSpaceVerticalPanel());
        flexTableSearch.setWidget(0, 2, mpPager);        
//        flexTableSearch.setWidget(0, 3, new MpSpaceVerticalPanel());   

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

    private void populateColunasAvaliacoes() {
        int idCurso = Integer.parseInt(listBoxCurso.getSelectedValue());
        int idPeriodo = Integer.parseInt(listBoxPeriodo.getSelectedValue());
        int idDisciplina = Integer.parseInt(listBoxDisciplina.getSelectedValue());
        GWTServiceAvaliacao.Util.getInstance().getHeaderRelatorioBoletimDisciplina(idCurso, idPeriodo, idDisciplina, new CallBackCarregarAvaliacao());
    }

    private void populateNotas() {
        mpLoading.setVisible(true);
        int idCurso = Integer.parseInt(listBoxCurso.getSelectedValue());
        int idPeriodo = Integer.parseInt(listBoxPeriodo.getSelectedValue());
        int idDisciplina = Integer.parseInt(listBoxDisciplina.getSelectedValue());
        GWTServiceNota.Util.getInstance().getRelatorioBoletimDisciplina(idCurso, idPeriodo, idDisciplina, new CallBackCarregarNotas());
    }

    private class CallBackCarregarAvaliacao implements AsyncCallback<ArrayList<String>> {

        @Override
        public void onFailure(Throwable caught) {
            mpLoading.setVisible(false);
            MpUtilClient.isRefreshRequired();
        }

        @Override
        public void onSuccess(ArrayList<String> list) {
            mpLoading.setVisible(false);
            MpUtilClient.isRefreshRequired(list);
            arrayAvaliacaoColumns.clear();
            for (int i = 0; i < list.size(); i++) {
                arrayAvaliacaoColumns.add(list.get(i));
            }

            populateNotas();

        }
    }

    private class ClickHandlerExcel implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            int idCurso = Integer.parseInt(listBoxCurso.getSelectedValue());
            int idPeriodo = Integer.parseInt(listBoxPeriodo.getSelectedValue());
            int idDisciplina = Integer.parseInt(listBoxDisciplina.getSelectedValue());
            MpDialogBoxExcelRelatorioBoletim.getInstance(idCurso, idPeriodo, idDisciplina);
        }
    }
    
    private class ClickHandlerMultipleExcel implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            MpDialogBoxMultipleBoletimDisciplina.getInstance();
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
                for (int j=1;j<row.size();j++) {
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
//           if (event.getNativeKeyCode() == KeyCodes.) {
               filtrarCellTable(txtSearch.getText());
//           }
       }
   }

   
   private class ClickHandlerFiltrar implements ClickHandler {
       public void onClick(ClickEvent event) {
           filtrarCellTable(txtSearch.getText());
       }
   }       


}
