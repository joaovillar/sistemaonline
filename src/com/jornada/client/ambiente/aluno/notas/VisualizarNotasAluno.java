package com.jornada.client.ambiente.aluno.notas;

import java.util.ArrayList;
import java.util.Comparator;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;
import com.googlecode.gwt.charts.client.options.Animation;
import com.googlecode.gwt.charts.client.options.AnimationEasing;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.Legend;
import com.googlecode.gwt.charts.client.options.LegendPosition;
import com.googlecode.gwt.charts.client.options.VAxis;
import com.jornada.client.ambiente.general.nota.DialogBoxNota;
import com.jornada.client.ambiente.general.nota.DialogBoxNotasAno;
import com.jornada.client.classes.listBoxes.MpSelectionAlunosPorCurso;
import com.jornada.client.classes.listBoxes.ambiente.aluno.MpSelectionCursoAmbienteAluno;
import com.jornada.client.classes.listBoxes.suggestbox.MpListBoxPanelHelper;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpPanelPageMainView;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceNota;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.utility.MpUtilClient;

public class VisualizarNotasAluno extends VerticalPanel {

    private ArrayList<ArrayList<String>> listForChart;

    private ColumnChart chartNotas;

    private Grid gridBoletimChart;

    static TextConstants txtConstants = GWT.create(TextConstants.class);

    MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
    MpDialogBox mpDialogBoxWarning = new MpDialogBox();
    MpPanelLoading mpLoading = new MpPanelLoading("images/radar.gif");

    private CellTable<ArrayList<String>> cellTable;
    private ListDataProvider<ArrayList<String>> dataProvider;
    ArrayList<String> arrayPeriodoColumns = new ArrayList<String>();

    private final static int INT_POSITION_NAME = 0;
    
//    private MpSelection listBoxCursoAluno;
//    private MpSelection listBoxAlunosPorCursoAluno;

    private MpSelectionCursoAmbienteAluno listBoxCursoAluno;
    private MpSelectionAlunosPorCurso listBoxAlunosPorCursoAluno;
    
    private Usuario usuarioLogado;

    MpListBoxPanelHelper mpHelperCurso = new MpListBoxPanelHelper();
    MpListBoxPanelHelper mpHelperAluno = new MpListBoxPanelHelper();

    private VerticalPanel vPanelBoletim;

    private TelaInicialAlunoVisualizarNotas telaInicialAlunoVisualizarNotas;
 
    private static VisualizarNotasAluno uniqueInstance;

    public static VisualizarNotasAluno getInstance(TelaInicialAlunoVisualizarNotas telaInicialAlunoVisualizarNotas) {
        if (uniqueInstance == null) {
            uniqueInstance = new VisualizarNotasAluno(telaInicialAlunoVisualizarNotas);
        }
        return uniqueInstance;
    }
  

    private VisualizarNotasAluno(TelaInicialAlunoVisualizarNotas telaInicialAlunoVisualizarNotas) {

        this.telaInicialAlunoVisualizarNotas = telaInicialAlunoVisualizarNotas;
        
        usuarioLogado = telaInicialAlunoVisualizarNotas.getMainView().getUsuarioLogado();

        mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
        mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);

        mpLoading.setTxtLoading(txtConstants.geralCarregando());
        mpLoading.show();
        mpLoading.setVisible(false);

        vPanelBoletim = new VerticalPanel();

        VerticalPanel vBodyPanel = new VerticalPanel();
        vBodyPanel.setBorderWidth(0);

        vBodyPanel.add(drawPassoUmSelecioneAluno());
        vBodyPanel.add(new InlineHTML("&nbsp;"));
        vBodyPanel.setWidth("100%");

        this.setWidth("100%");
        super.add(vBodyPanel);

    }
    
//    private VisualizarNotasAluno(TelaInicialPaisVisualizarNotas telaInicialPaisVisualizarNotas) {
//
//        this.telaInicialPaisVisualizarNotas = telaInicialPaisVisualizarNotas;
//        
//        usuarioLogado = telaInicialPaisVisualizarNotas.getMainView().getUsuarioLogado();
//
//        mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
//        mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
//
//        mpLoading.setTxtLoading(txtConstants.geralCarregando());
//        mpLoading.show();
//        mpLoading.setVisible(false);
//
//        vPanelBoletim = new VerticalPanel();
//
//        VerticalPanel vBodyPanel = new VerticalPanel();
//        vBodyPanel.setBorderWidth(0);
//
//        vBodyPanel.add(drawPassoUmSelecioneAluno());
//        vBodyPanel.add(new InlineHTML("&nbsp;"));
//        vBodyPanel.setWidth("100%");
//
//        this.setWidth("100%");
//        super.add(vBodyPanel);
//
//    }    

    public MpPanelPageMainView drawPassoUmSelecioneAluno() {

        MpPanelPageMainView mpPanelPasso1 = new MpPanelPageMainView(txtConstants.notaSelecionarAluno(), "images/user_male_black_red_16.png");
        mpPanelPasso1.setWidth("100%");
        mpPanelPasso1.setHeight(Integer.toString(TelaInicialAlunoVisualizarNotas.INI_HEIGHT_TABLE - 50) + "px");

        

        Label lblNomeCurso = new Label(txtConstants.curso());
        Label lblNomeAluno = new Label(txtConstants.alunoNome());

        listBoxCursoAluno = new MpSelectionCursoAmbienteAluno(usuarioLogado);
        listBoxCursoAluno.addChangeHandler(new MpCursoSelectionChangeHandler());

        listBoxAlunosPorCursoAluno = new MpSelectionAlunosPorCurso();
        listBoxAlunosPorCursoAluno.addChangeHandler(new MpAlunosPorCursoSelectionChangeHandler());
        
        
//        listBoxCursoPais = new MpSelectionCursoAmbientePais(usuarioLogado);
//        listBoxCursoPais.addChangeHandler(new MpCursoSelectionChangeHandler());
//
//        listBoxAlunosPorCursoPais = new MpSelectionAlunosPorCursoAmbientePais();
//        listBoxAlunosPorCursoPais.addChangeHandler(new MpAlunosPorCursoSelectionChangeHandler());        

        lblNomeCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        lblNomeAluno.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

        lblNomeCurso.setStyleName("design_label");
        lblNomeAluno.setStyleName("design_label");

        FlexTable flexTableFiltrar = new FlexTable();
        flexTableFiltrar.setCellSpacing(2);
        flexTableFiltrar.setCellPadding(2);
        flexTableFiltrar.setBorderWidth(0);

        int row = 1;
        int column = 0;
        flexTableFiltrar.setWidget(row, column++, lblNomeCurso);
        flexTableFiltrar.setWidget(row, column++, listBoxCursoAluno);

        if (usuarioLogado.getIdTipoUsuario() == TipoUsuario.ALUNO) {
            listBoxAlunosPorCursoAluno.clear();
            listBoxAlunosPorCursoAluno.addItem(usuarioLogado.getPrimeiroNome() + " " + usuarioLogado.getSobreNome(), Integer.toString(usuarioLogado.getIdUsuario()));
        }else {
            flexTableFiltrar.setWidget(row, column++, mpHelperCurso);
            flexTableFiltrar.setWidget(row, column++, new MpSpaceVerticalPanel());
            flexTableFiltrar.setWidget(row, column++, lblNomeAluno);
            flexTableFiltrar.setWidget(row, column++, listBoxAlunosPorCursoAluno);
            flexTableFiltrar.setWidget(row, column++, mpHelperAluno);
        }
        flexTableFiltrar.setWidget(row, column++, new MpSpaceVerticalPanel());
        flexTableFiltrar.setWidget(row, column++, mpLoading);

        gridBoletimChart = new Grid(1, 2);
        gridBoletimChart.setBorderWidth(0);
        gridBoletimChart.setCellPadding(2);
        gridBoletimChart.setCellSpacing(2);
        gridBoletimChart.setHeight(Integer.toString(TelaInicialAlunoVisualizarNotas.INI_HEIGHT_TABLE - 180) + "px");

        row = 0;
        Grid gridBoletim = new Grid(2, 1);
        gridBoletim.setBorderWidth(0);
        gridBoletim.setWidget(row++, 0, new MpSpaceVerticalPanel());
        gridBoletim.setWidget(row, 0, vPanelBoletim);

        gridBoletimChart.setWidth("100%");
        gridBoletimChart.setWidget(0, 0, gridBoletim);

        gridBoletimChart.getCellFormatter().setWidth(0, 0, "500px");
        gridBoletimChart.getCellFormatter().setWidth(0, 1, "600px");
        gridBoletimChart.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
        gridBoletimChart.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        gridBoletimChart.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
        gridBoletimChart.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);

        mpPanelPasso1.add(flexTableFiltrar);
        mpPanelPasso1.add(gridBoletimChart);
        mpPanelPasso1.setBorderWidth(0);
        mpPanelPasso1.add(new InlineHTML("&nbsp;"));

        return mpPanelPasso1;

    }

    public Grid getGridBoletimChart() {
        return gridBoletimChart;
    }

    private class MpCursoSelectionChangeHandler implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            mpHelperCurso.populateSuggestBox(listBoxCursoAluno);
            int idCurso = Integer.parseInt(listBoxCursoAluno.getValue(listBoxCursoAluno.getSelectedIndex()));

            if (telaInicialAlunoVisualizarNotas.getMainView().getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.ALUNO) {
                populateBoletimAluno();
            } else {
                listBoxAlunosPorCursoAluno.populateComboBox(idCurso);
            }

        }
    }

    private class MpAlunosPorCursoSelectionChangeHandler implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            mpHelperAluno.populateSuggestBox(listBoxAlunosPorCursoAluno);
            vPanelBoletim.clear();
            populateBoletimAluno();
        }
    }

    protected void populateBoletimAluno() {
        mpLoading.setVisible(true);
        int idCurso = Integer.parseInt(listBoxCursoAluno.getValue(listBoxCursoAluno.getSelectedIndex()));
        int idTipoUsuario = TipoUsuario.ALUNO;
        int intIdCurso = listBoxAlunosPorCursoAluno.getSelectedIndex();

        if (intIdCurso == -1) {
            mpLoading.setVisible(false);
            vPanelBoletim.clear();
            if (chartNotas != null) {
                chartNotas.setVisible(false);
            }
        } else {
            int idUsuario = Integer.parseInt(listBoxAlunosPorCursoAluno.getValue(intIdCurso));
            GWTServiceNota.Util.getInstance().getNotasAluno(idCurso, idTipoUsuario, idUsuario, new CallbackBoletim());
        }

    }

    public void updateClientData() {
        listBoxCursoAluno.populateComboBox(this.telaInicialAlunoVisualizarNotas.getMainView().getUsuarioLogado());
    }

    private class CallbackBoletim implements AsyncCallback<ArrayList<ArrayList<String>>> {

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
            arrayPeriodoColumns.clear();

            ArrayList<String> listColumns = list.get(0);
            for (int i = 1; i < listColumns.size(); i++) {
                arrayPeriodoColumns.add(listColumns.get(i));
            }

            for (int i = 1; i < list.size(); i++) {
                dataProvider.getList().add(list.get(i));
            }

            initializeCellTable();
            setListForChart(list);

            // Create the API Loader
            ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
            chartLoader.loadApi(new Runnable() {
                @Override
                public void run() {
                    // Create and attach the chart
                    // gridBoletimChart.clear();
                    // chart.clearChart();
                    if (chartNotas == null) {
                        chartNotas = new ColumnChart();
                        getGridBoletimChart().setWidget(0, 1, chartNotas);
                    }

                    drawChart(getListForChart());

                }
            });

        }

    }

    public ArrayList<ArrayList<String>> getListForChart() {
        return listForChart;
    }

    public void setListForChart(ArrayList<ArrayList<String>> list) {
        this.listForChart = list;
    }

    private void drawChart(ArrayList<ArrayList<String>> list) {

        chartNotas.setVisible(true);

        // Prepare the data
        DataTable dataTable = DataTable.create();

        ArrayList<String> listDisciplinas = new ArrayList<String>();

        // Pegando Disciplinas
        dataTable.addColumn(ColumnType.STRING, txtConstants.disciplina());
        for (int row = 1; row < list.size(); row++) {
            ArrayList<String> cellRow = list.get(row);
            dataTable.addColumn(ColumnType.NUMBER, cellRow.get(0));
            listDisciplinas.add(cellRow.get(0));
        }

        // Pegando Periodo
        ArrayList<String> listPeriodoCell = list.get(0);
        ArrayList<String> listPeriodo = new ArrayList<String>();
        dataTable.addRows(listPeriodoCell.size() - 2);
        for (int col = 1; col < listPeriodoCell.size() - 1; col++) {
            String strPeriodo = listPeriodoCell.get(col);
            dataTable.setValue(col - 1, 0, String.valueOf(strPeriodo));
            listPeriodo.add(strPeriodo);
        }

        for (int col = 0; col < listDisciplinas.size(); col++) {

            ArrayList<String> cellRow = list.get(col + 1);

            for (int row = 0; row < listPeriodo.size(); row++) {

                String nota = cellRow.get(row + 1);

                double doubleNota = 0;
                if (nota.equals("-")) {
                    doubleNota = 0;
                } else {
                    doubleNota = Double.parseDouble(nota);
                }

                dataTable.setValue(row, col + 1, doubleNota);
            }
        }

        // Set options
        ColumnChartOptions options = ColumnChartOptions.create();
        options.setFontName("Tahoma");
        options.setTitle(txtConstants.alunoAmbienteNotas());

        options.setHAxis(HAxis.create(txtConstants.disciplina()));

        VAxis vaxis = VAxis.create(txtConstants.nota());
        vaxis.setMaxValue(10.0);
        vaxis.setMinValue(0.0);
        options.setVAxis(vaxis);

        VAxis.create().setMaxValue(10.0);
        VAxis.create().setMaxValue(10.0);

        Animation animation = Animation.create();
        animation.setDuration(500);
        animation.setEasing(AnimationEasing.OUT);
        Legend legend = Legend.create(LegendPosition.BOTTOM);

        options.setAnimation(animation);
        options.setLegend(legend);

        // Draw the chart
        chartNotas.draw(dataTable, options);
        
        chartNotas.setWidth("600px");
        chartNotas.setHeight("350px");
        chartNotas.setSize("600px", "350px");
        chartNotas.setPixelSize(600, 350);
        chartNotas.onResize();
        chartNotas.redraw();

//        chartNotas.setWidth("900px");
//        chartNotas.setHeight("550px");

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
                return o1.get(INT_POSITION_NAME).compareTo(o2.get(INT_POSITION_NAME));
            }
        });
        cellTable.addColumnSortHandler(sortHandler);
        // ///////////////////////ColumnName//////////////////////////////////

        // String strSpace =
        // "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        SafeHtmlBuilder builder = new SafeHtmlBuilder();
        builder.appendHtmlConstant(txtConstants.disciplina());
        SafeHtml safeHtml = builder.toSafeHtml();
        cellTable.addColumn(indexColumnName, safeHtml);

        for (int column = 0; column < arrayPeriodoColumns.size(); column++) {

            final String strPeriodo = arrayPeriodoColumns.get(column);

            final IndexedColumn indexedColumn = new IndexedColumn(column + INT_POSITION_NAME + 1);

            final Header<String> header = new Header<String>(new ClickableTextCell()) {
                @Override
                public String getValue() {
                    return strPeriodo;
                }

            };

            final int intColumn = column;

            indexedColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
            indexedColumn.setSortable(true);

            indexedColumn.setFieldUpdater(new FieldUpdater<ArrayList<String>, String>() {
                @Override
                public void update(int index, ArrayList<String> object, final String value) {

                    if (!value.equals("-")) {
                        int idUsuario = Integer.parseInt(listBoxAlunosPorCursoAluno.getSelectedValue());
                        int idCurso = Integer.parseInt(listBoxCursoAluno.getSelectedValue());
                        String strNomeCurso = listBoxCursoAluno.getSelectedItemText();
                        String strNomeDisciplina = dataProvider.getList().get(index).get(0);
                        String strNomePeriodo = arrayPeriodoColumns.get(indexedColumn.getIndex() - INT_POSITION_NAME - 1);
                        Double mediaNotaCurso = Double.parseDouble(listBoxCursoAluno.getListCurso().get(listBoxCursoAluno.getSelectedIndex()).getMediaNota());
                        int indexCol = indexedColumn.getIndex();

                        if (indexCol != arrayPeriodoColumns.size() + INT_POSITION_NAME) {
                            DialogBoxNota.getInstance(idUsuario, idCurso, strNomeCurso, strNomeDisciplina, strNomePeriodo, mediaNotaCurso);
                        } else if (indexCol == arrayPeriodoColumns.size() + INT_POSITION_NAME) {
                            DialogBoxNotasAno.getInstance(idUsuario, idCurso, strNomeCurso, strNomeDisciplina, mediaNotaCurso);
                        }
                    }
                }
            });

            sortHandler.setComparator(indexedColumn, new Comparator<ArrayList<String>>() {
                @Override
                public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                    return o1.get(intColumn + INT_POSITION_NAME+1).compareTo(o2.get(intColumn + INT_POSITION_NAME+1));
                }
            });

            cellTable.addColumn(indexedColumn, header);
        }

        vPanelBoletim.add(cellTable);

    }

    private class IndexedColumn extends Column<ArrayList<String>, String> {
        private int index;

        public IndexedColumn(int index) {
            super(new ClickableTextCell());
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        @Override
        public String getValue(ArrayList<String> object) {
            String strTest = "";
            try {
                strTest = object.get(this.index);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            return strTest;
        }

        @Override
        public String getCellStyleNames(Context context, ArrayList<String> object) {
            Double doubleMediaNotaCurso = Double.parseDouble(listBoxCursoAluno.getListCurso().get(listBoxCursoAluno.getSelectedIndex()).getMediaNota());
            try {
                double doubleNumber = Double.parseDouble(object.get(this.index));
                if (doubleNumber >= doubleMediaNotaCurso) {
                    return "table-boletim-cell-green";
                } else if (doubleNumber < doubleMediaNotaCurso) {
                    return "table-boletim-cell-red";
                } else {
                    return "";
                }
            } catch (Exception ex) {
                return "";
            }

        }

    }

}
