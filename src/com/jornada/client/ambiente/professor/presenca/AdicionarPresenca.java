package com.jornada.client.ambiente.professor.presenca;

import java.util.ArrayList;
import java.util.Comparator;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxCursoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxDisciplinaAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxPeriodoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.suggestbox.MpListBoxPanelHelper;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBoxRefreshPage;
import com.jornada.client.classes.widgets.label.MpLabelTextBoxError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.classes.widgets.textbox.MpTextBox;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServicePresenca;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Presenca;
import com.jornada.shared.classes.presenca.PresencaUsuarioDisciplina;
import com.jornada.shared.classes.utility.MpUtilClient;

public class AdicionarPresenca extends VerticalPanel {

    MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
    MpDialogBox mpDialogBoxWarning = new MpDialogBox();
    MpPanelLoading mpPanelLoadingSaving = new MpPanelLoading("images/radar.gif");
    MpPanelLoading mpPanelLoadingAluno = new MpPanelLoading("images/radar.gif");

    private MpListBoxCursoAmbienteProfessor listBoxCurso;
    private MpListBoxPeriodoAmbienteProfessor listBoxPeriodo;
    private MpListBoxDisciplinaAmbienteProfessor listBoxDisciplina;
    // private MpSelectionConteudoProgramatico listBoxConteudoProgramatico;

    MpListBoxPanelHelper mpHelperCurso = new MpListBoxPanelHelper();

    private MpLabelTextBoxError lblErroDisciplina;
    private MpLabelTextBoxError lblErroTotalAulasBox;

    private ArrayList<Presenca> pendingChanges = new ArrayList<Presenca>();
    private CellTable<PresencaUsuarioDisciplina> cellTable;
    private ListDataProvider<PresencaUsuarioDisciplina> dataProvider = new ListDataProvider<PresencaUsuarioDisciplina>();
    ArrayList<PresencaUsuarioDisciplina> arrayListBackup = new ArrayList<PresencaUsuarioDisciplina>();
    // private Column<PresencaUsuarioDisciplina, String> columnTipoPresenca;
    private Column<PresencaUsuarioDisciplina, String> columnPrimeiroNome;
    private Column<PresencaUsuarioDisciplina, String> columnSobreNome;
    private Column<PresencaUsuarioDisciplina, String> columnNumeroFaltas;

    // private LinkedHashMap<String, String> listaTipoPresenca = new
    // LinkedHashMap<String, String>();

    VerticalPanel vFormPanel = new VerticalPanel();
    VerticalPanel vFormPanelGrid;

    // private MpDateBoxWithImage mpDateBoxInicial;
    private MpTextBox txtTotalAulas;

    TextConstants txtConstants;

    private MpTextBox txtSearch;

    private TelaInicialPresenca telaInicialDiarioProfessor;

    public AdicionarPresenca(final TelaInicialPresenca telaInicialDiarioProfessor) {

        txtConstants = GWT.create(TextConstants.class);

        this.telaInicialDiarioProfessor = telaInicialDiarioProfessor;

        mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
        mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
        mpPanelLoadingSaving.setTxtLoading(txtConstants.geralCarregando());
        mpPanelLoadingSaving.show();
        mpPanelLoadingSaving.setVisible(false);
        mpPanelLoadingAluno.setTxtLoading(txtConstants.geralCarregando());
        mpPanelLoadingAluno.show();
        mpPanelLoadingAluno.setVisible(false);

        FlexTable flexTableComboBoxes = new FlexTable();
        flexTableComboBoxes.setCellSpacing(2);
        flexTableComboBoxes.setCellPadding(2);
        flexTableComboBoxes.setBorderWidth(0);
        FlexCellFormatter cellFormatter = flexTableComboBoxes.getFlexCellFormatter();

        // Add a title to the form
        cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

        lblErroDisciplina = new MpLabelTextBoxError();
        lblErroTotalAulasBox = new MpLabelTextBoxError();

        Label lblCurso = new Label(txtConstants.curso());
        Label lblPeriodo = new Label(txtConstants.periodo());
        Label lblDisciplina = new Label(txtConstants.disciplina());
        Label lblNumeroAulas = new Label("Total Aulas");

        lblCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        lblPeriodo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        lblDisciplina.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        lblNumeroAulas.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

        lblCurso.setStyleName("design_label");
        lblPeriodo.setStyleName("design_label");
        lblDisciplina.setStyleName("design_label");
        lblNumeroAulas.setStyleName("design_label");

        // Add some standard form options
        int row = 1;
        flexTableComboBoxes.setWidget(row, 0, lblCurso);

        listBoxCurso = new MpListBoxCursoAmbienteProfessor(telaInicialDiarioProfessor.getMainView().getUsuarioLogado());
        listBoxPeriodo = new MpListBoxPeriodoAmbienteProfessor(telaInicialDiarioProfessor.getMainView().getUsuarioLogado());
        listBoxDisciplina = new MpListBoxDisciplinaAmbienteProfessor(telaInicialDiarioProfessor.getMainView().getUsuarioLogado());

        txtTotalAulas = new MpTextBox();
        txtTotalAulas.setWidth("50px");
        txtTotalAulas.setMaxLength(3);

        listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());
        listBoxPeriodo.addChangeHandler(new MpPeriodoSelectionChangeHandler());
        listBoxDisciplina.addChangeHandler(new MpDisciplinaSelectionChangeHandler());

        flexTableComboBoxes.setWidget(row, 0, lblCurso);
        flexTableComboBoxes.setWidget(row, 1, listBoxCurso);
        flexTableComboBoxes.setWidget(row++, 2, mpHelperCurso);
        flexTableComboBoxes.setWidget(row, 0, lblPeriodo);
        flexTableComboBoxes.setWidget(row++, 1, listBoxPeriodo);
        flexTableComboBoxes.setWidget(row, 0, lblDisciplina);
        flexTableComboBoxes.setWidget(row, 1, listBoxDisciplina);
        flexTableComboBoxes.setWidget(row++, 2, lblErroDisciplina);
        flexTableComboBoxes.setWidget(row, 0, lblNumeroAulas);
        flexTableComboBoxes.setWidget(row, 1, txtTotalAulas);
        flexTableComboBoxes.setWidget(row++, 2, lblErroTotalAulasBox);

        vFormPanel.setBorderWidth(0);
        vFormPanel.setWidth("100%");
        vFormPanel.add(flexTableComboBoxes);

        /*********************** Begin Callbacks **********************/

        /*********************** End Callbacks **********************/
        populateComboBoxTipoPresenca();

        this.setWidth("100%");
        super.add(vFormPanel);

    }

    public void initializeCellTable() {

        vFormPanelGrid = new VerticalPanel();

        cellTable = new CellTable<PresencaUsuarioDisciplina>(100, GWT.<CellTableStyle> create(CellTableStyle.class));
        cellTable.setWidth("100%");
        cellTable.setAutoHeaderRefreshDisabled(true);
        cellTable.setAutoFooterRefreshDisabled(true);

        // Add a selection model so we can select cells.
        final SelectionModel<PresencaUsuarioDisciplina> selectionModel = new MultiSelectionModel<PresencaUsuarioDisciplina>();
        cellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<PresencaUsuarioDisciplina> createCheckboxManager());
        initTableColumns(selectionModel);

        dataProvider.addDataDisplay(cellTable);

        MpSimplePager mpPager = new MpSimplePager();
        mpPager.setDisplay(cellTable);

        FlexTable flexTableFiltrarAluno = new FlexTable();
        flexTableFiltrarAluno.setCellSpacing(3);
        flexTableFiltrarAluno.setCellPadding(3);
        flexTableFiltrarAluno.setBorderWidth(0);

        txtSearch = new MpTextBox();
        txtSearch.setWidth("150px");
        txtSearch.setStyleName("design_text_boxes");

        txtSearch.addKeyUpHandler(new EnterKeyUpHandler());

        flexTableFiltrarAluno.setWidget(0, 0, txtSearch);
        flexTableFiltrarAluno.setWidget(0, 1, new MpSpaceVerticalPanel());
        flexTableFiltrarAluno.setWidget(0, 2, mpPager);
        flexTableFiltrarAluno.setWidget(0, 5, mpPanelLoadingAluno);

        MpImageButton btnSave = new MpImageButton("Salvar Numero de Faltas", "images/save.png");
        btnSave.addClickHandler(new ClickHandlerSave());

        Grid gridSave = new Grid(1, 2);
        gridSave.setCellSpacing(2);
        gridSave.setCellPadding(2);
        {
            int i = 0;
            gridSave.setWidget(0, i++, btnSave);
            gridSave.setWidget(0, i++, mpPanelLoadingSaving);
        }

        ScrollPanel scrollPanel = new ScrollPanel();
        // scrollPanel.setSize(Integer.toString(TelaInicialDiarioProfessor.intWidthTable+30)+"px",Integer.toString(TelaInicialDiarioProfessor.intHeightTable-250)+"px");
        // scrollPanel.setHeight(Integer.toString(TelaInicialPresenca.intHeightTable-250)+"px");
        // scrollPanel.setWidth("100%");
        // scrollPanel.setAlwaysShowScrollBars(false);
        scrollPanel.add(cellTable);

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setWidth("100%");
        // vPanel.setHeight("280px");
        vPanel.setCellVerticalAlignment(scrollPanel, ALIGN_TOP);
        vPanel.add(scrollPanel);

        vFormPanelGrid.add(flexTableFiltrarAluno);
        vFormPanelGrid.add(vPanel);
        vFormPanelGrid.add(gridSave);
        vFormPanelGrid.add(new MpSpaceVerticalPanel());

        vFormPanel.add(vFormPanelGrid);

    }

    /**************** Begin Event Handlers *****************/

    private class ClickHandlerSave implements ClickHandler {

        public void onClick(ClickEvent event) {

            if (checkFieldsValidator()) {

                removeCellTableFilter();

                boolean ehMenorQueTotalAula=true;
                
                int idDisciplina = Integer.parseInt(listBoxDisciplina.getValue(listBoxDisciplina.getSelectedIndex()));

                ArrayList<PresencaUsuarioDisciplina> listPresencaUsuarioDisciplina = new ArrayList<PresencaUsuarioDisciplina>();

                for (int i = 0; i < dataProvider.getList().size(); i++) {

                    // PresencaUsuarioDisciplina pud =
                    // cellTable.getVisibleItems().get(i);
                    PresencaUsuarioDisciplina pud = dataProvider.getList().get(i);
                    pud.setNumeroAulas(Integer.parseInt(txtTotalAulas.getValue()));
                    pud.setIdDisciplina(idDisciplina);
                    listPresencaUsuarioDisciplina.add(pud);
                   if(pud.getNumeroAulas()<pud.getNumeroFaltas() && ehMenorQueTotalAula==true){
                       ehMenorQueTotalAula=false;
                   }
                }

                

                if(ehMenorQueTotalAula==true){
                    mpPanelLoadingSaving.setVisible(true);
                    GWTServicePresenca.Util.getInstance().AdicionarFaltaDisciplina(listPresencaUsuarioDisciplina, new AddPresencaCallBack());    
                }else{
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText("Total Aula dever ser maior que numero de faltas. Favor verificar numero de faltas dos alunos");
                    mpDialogBoxWarning.showDialog();
                }
                

            }

        }
    }

    private class MpCursoSelectionChangeHandler implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            mpHelperCurso.populateSuggestBox(listBoxCurso);
            arrayListBackup.clear();
            pendingChanges.clear();
            int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
            listBoxPeriodo.populateComboBox(idCurso);

        }
    }

    private class MpPeriodoSelectionChangeHandler implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            int index = listBoxPeriodo.getSelectedIndex();
            if (index == -1) {
                listBoxDisciplina.clear();
                // listBoxConteudoProgramatico.clear();
            } else {
                int idPeriodo = Integer.parseInt(listBoxPeriodo.getValue(index));
                listBoxDisciplina.populateComboBox(idPeriodo);
            }
        }
    }

    private class MpDisciplinaSelectionChangeHandler implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            populateGridUsuarios();
            int index = listBoxDisciplina.getSelectedIndex();
            if (index == -1) {
                cleanFields();
                pendingChanges.clear();
                arrayListBackup.clear();
                dataProvider.getList().clear();
                cellTable.setRowCount(0);
            }
        }
    }

    /**************** End Event Handlers *****************/

    public void updateClientData() {
        listBoxCurso.populateComboBox(this.telaInicialDiarioProfessor.getMainView().getUsuarioLogado());
    }

    public boolean checkFieldsValidator() {

        boolean isFieldsOk = false;

        boolean isTotalAulasOk = false;
        if (FieldVerifier.isValidInteger(txtTotalAulas.getValue())) {
            isTotalAulasOk = true;
            lblErroTotalAulasBox.hideErroMessage();
        } else {
            isTotalAulasOk = false;
            lblErroTotalAulasBox.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.geralNumeroInteiro()));
        }
        
        int intTotalAulas = Integer.parseInt(txtTotalAulas.getValue());
        if (intTotalAulas>0) {
            isTotalAulasOk = true;
            lblErroTotalAulasBox.hideErroMessage();
        } else {
            isTotalAulasOk = false;
            lblErroTotalAulasBox.showErrorMessage("Total Aulas deve ser maior que Zero (0)");
        }

        boolean isConteudoOk = false;

        if (FieldVerifier.isValidListBoxSelectedValue(listBoxDisciplina.getSelectedIndex())) {
            isConteudoOk = true;
            lblErroDisciplina.hideErroMessage();
        } else {
            lblErroDisciplina.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.disciplina()));
        }

        isFieldsOk = isTotalAulasOk && isConteudoOk;

        return isFieldsOk;
    }

    private void populateGridUsuarios() {
        int idSelectedCurso = listBoxCurso.getSelectedIndex();
        int idSelectedDisciplina = listBoxDisciplina.getSelectedIndex();

        if (idSelectedCurso != -1) {
            int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
            if (idSelectedDisciplina != -1) {
                mpPanelLoadingAluno.setVisible(true);
                int idDisciplina = Integer.parseInt(listBoxDisciplina.getSelectedValue());
                GWTServicePresenca.Util.getInstance().getAlunosDisciplina(idCurso, idDisciplina, new PopularDiarioCallBack());
            }
        }
    }

    private void addCellTableData(ListDataProvider<PresencaUsuarioDisciplina> dataProvider) {

        ListHandler<PresencaUsuarioDisciplina> sortHandler = new ListHandler<PresencaUsuarioDisciplina>(dataProvider.getList());

        cellTable.addColumnSortHandler(sortHandler);

        initSortHandler(sortHandler);

    }

    private void initTableColumns(final SelectionModel<PresencaUsuarioDisciplina> selectionModel) {

        columnPrimeiroNome = new Column<PresencaUsuarioDisciplina, String>(new TextCell()) {
            @Override
            public String getValue(PresencaUsuarioDisciplina object) {
                return object.getUsuario().getPrimeiroNome();
            }

        };

        columnSobreNome = new Column<PresencaUsuarioDisciplina, String>(new TextCell()) {
            @Override
            public String getValue(PresencaUsuarioDisciplina object) {
                return object.getUsuario().getSobreNome();
            }

        };

        columnNumeroFaltas = new Column<PresencaUsuarioDisciplina, String>(new EditTextCell()) {
            @Override
            public String getValue(PresencaUsuarioDisciplina object) {
                return Integer.toString(object.getNumeroFaltas());
            }

        };
        columnNumeroFaltas.setFieldUpdater(new FieldUpdater<PresencaUsuarioDisciplina, String>() {
            @Override
            public void update(int index, PresencaUsuarioDisciplina object, String value) {
                // Called when the user changes the value.
                if (FieldVerifier.isValidInteger(value)) {
                    object.setNumeroFaltas(Integer.parseInt(value));
                } else {
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText(txtConstants.cursoCampoNomeObrigatorio());
                    mpDialogBoxWarning.showDialog();
                }
            }
        });

        // cellTable.addColumn(columnTipoPresenca, txtConstants.presenca());
        cellTable.addColumn(columnPrimeiroNome, txtConstants.usuarioPrimeiroNome());
        cellTable.addColumn(columnSobreNome, txtConstants.usuarioSobreNome());
        cellTable.addColumn(columnNumeroFaltas, "Numero Faltas");

        // cellTable.getColumn(cellTable.getColumnIndex(columnTipoPresenca)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(columnPrimeiroNome)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(columnSobreNome)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(columnNumeroFaltas)).setCellStyleNames("edit-cell");
    }

    public void initSortHandler(ListHandler<PresencaUsuarioDisciplina> sortHandler) {

        columnPrimeiroNome.setSortable(true);
        sortHandler.setComparator(columnPrimeiroNome, new Comparator<PresencaUsuarioDisciplina>() {
            @Override
            public int compare(PresencaUsuarioDisciplina o1, PresencaUsuarioDisciplina o2) {
                return o1.getUsuario().getPrimeiroNome().compareTo(o2.getUsuario().getPrimeiroNome());
            }
        });

        columnSobreNome.setSortable(true);
        sortHandler.setComparator(columnSobreNome, new Comparator<PresencaUsuarioDisciplina>() {
            @Override
            public int compare(PresencaUsuarioDisciplina o1, PresencaUsuarioDisciplina o2) {
                return o1.getUsuario().getSobreNome().compareTo(o2.getUsuario().getSobreNome());
            }
        });

        columnNumeroFaltas.setSortable(true);
        sortHandler.setComparator(columnSobreNome, new Comparator<PresencaUsuarioDisciplina>() {
            @Override
            public int compare(PresencaUsuarioDisciplina o1, PresencaUsuarioDisciplina o2) {
                String str1 = Integer.toString(o1.getNumeroFaltas());
                String str2 = Integer.toString(o2.getNumeroFaltas());
                return str1.compareTo(str2);
            }
        });

    }

    protected void cleanGrid() {
        cellTable.setRowCount(0);
        cellTable.redraw();
    }

    protected void populateComboBoxTipoPresenca() {
        initializeCellTable();
        populateGridUsuarios();
    }

    private class EnterKeyUpHandler implements KeyUpHandler {
        public void onKeyUp(KeyUpEvent event) {
            // if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            filtrarCellTable(txtSearch.getValue());
            // }
        }
    }


    public void filtrarCellTable(String strFiltro) {

        removeCellTableFilter();

        strFiltro = strFiltro.toUpperCase();

        if (!strFiltro.isEmpty()) {

            int i = 0;
            while (i < dataProvider.getList().size()) {

                String strPrimeiroNome = dataProvider.getList().get(i).getUsuario().getPrimeiroNome().toUpperCase();
                String strSobreNome = dataProvider.getList().get(i).getUsuario().getSobreNome().toUpperCase();

                String strJuntaTexto = strPrimeiroNome + " " + strSobreNome;
                strJuntaTexto = strJuntaTexto.toUpperCase();

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
        // cleanGrid();

        for (int i = 0; i < arrayListBackup.size(); i++) {
            dataProvider.getList().add(arrayListBackup.get(i));
        }
        cellTable.setPageStart(0);
    }

    private void cleanFields() {
        lblErroDisciplina.hideErroMessage();
        lblErroTotalAulasBox.hideErroMessage();
        txtTotalAulas.setValue("");
    }

    private class PopularDiarioCallBack implements AsyncCallback<ArrayList<PresencaUsuarioDisciplina>> {

        public void onFailure(Throwable caught) {
            mpPanelLoadingSaving.setVisible(false);
            mpPanelLoadingAluno.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText(txtConstants.presencaErroSalvar());
            mpDialogBoxWarning.showDialog();

        }

        @Override
        public void onSuccess(ArrayList<PresencaUsuarioDisciplina> list) {
            MpUtilClient.isRefreshRequired(list);
            mpPanelLoadingSaving.setVisible(false);
            mpPanelLoadingAluno.setVisible(false);

            if (list == null) {
                MpDialogBoxRefreshPage mpRefresh = new MpDialogBoxRefreshPage();
                mpRefresh.showDialog();

            } else {

                cleanFields();
                pendingChanges.clear();
                arrayListBackup.clear();
                dataProvider.getList().clear();
                cellTable.setRowCount(0);

                for (int i = 0; i < list.size(); i++) {
                    dataProvider.getList().add(list.get(i));
                    arrayListBackup.add(list.get(i));
                    txtTotalAulas.setValue(Integer.toString(list.get(i).getNumeroAulas()));
                }

                addCellTableData(dataProvider);

                cellTable.redraw();
            }

        }

    }

    private class AddPresencaCallBack implements AsyncCallback<String> {

        public void onFailure(Throwable caught) {
            mpPanelLoadingSaving.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText(txtConstants.presencaErroSalvar());
            mpDialogBoxWarning.showDialog();

        }

        @Override
        public void onSuccess(String strResult) {
            mpPanelLoadingSaving.setVisible(false);

            if (strResult.equalsIgnoreCase("ok")) {
                // vFormPanelGrid.clear();
                mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
                mpDialogBoxConfirm.setBodyText(txtConstants.presencaSalva());
                mpDialogBoxConfirm.showDialog();
                // telaInicialDiarioProfessor.updateEditarDiarioProfessor();

            } else {
                mpPanelLoadingSaving.setVisible(false);
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.presencaErroSalvar() + " " + txtConstants.geralRegarregarPagina());
                mpDialogBoxWarning.showDialog();

            }

        }

    }

}
