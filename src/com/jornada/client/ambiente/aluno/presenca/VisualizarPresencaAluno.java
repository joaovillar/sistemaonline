package com.jornada.client.ambiente.aluno.presenca;

import java.util.ArrayList;
import java.util.Comparator;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.ambiente.pais.presenca.TelaInicialPresencaAlunoPais;
import com.jornada.client.classes.listBoxes.MpSelectionAlunosPorCurso;
import com.jornada.client.classes.listBoxes.ambiente.aluno.MpListBoxCursoAmbienteAluno;
import com.jornada.client.classes.listBoxes.ambiente.pais.MpListBoxAlunosPorCursoAmbientePais;
import com.jornada.client.classes.listBoxes.ambiente.pais.MpListBoxCursoAmbientePais;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServicePresenca;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.presenca.PresencaUsuarioDisciplinaAluno;
import com.jornada.shared.classes.utility.MpUtilClient;

public class VisualizarPresencaAluno extends VerticalPanel {

    MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
    MpDialogBox mpDialogBoxWarning = new MpDialogBox();
    MpPanelLoading mpPanelLoadingAluno = new MpPanelLoading("images/radar.gif");

    private MpListBoxCursoAmbienteAluno listBoxCursoAluno;
    private MpSelectionAlunosPorCurso listBoxAlunosPorCurso;

    private MpListBoxCursoAmbientePais listBoxCursoAlunoPais;
    private MpListBoxAlunosPorCursoAmbientePais listBoxAlunosPorCursoPais;

    private TextBox txtSearch;

    private CellTable<PresencaUsuarioDisciplinaAluno> cellTable;
    private ListDataProvider<PresencaUsuarioDisciplinaAluno> dataProvider = new ListDataProvider<PresencaUsuarioDisciplinaAluno>();
    ArrayList<PresencaUsuarioDisciplinaAluno> arrayListBackup = new ArrayList<PresencaUsuarioDisciplinaAluno>();
    private Column<PresencaUsuarioDisciplinaAluno, String> columnNomePeriodo;
    private Column<PresencaUsuarioDisciplinaAluno, String> columnNomeDisciplina;
    private Column<PresencaUsuarioDisciplinaAluno, String> columnQuantAula;
    private Column<PresencaUsuarioDisciplinaAluno, String> columnNumeroPresenca;
    private Column<PresencaUsuarioDisciplinaAluno, String> columnQuantFalta;
    private Column<PresencaUsuarioDisciplinaAluno, String> columnPorcentagemPresenca;
    private Column<PresencaUsuarioDisciplinaAluno, String> columnSituacao;

     private Usuario usuarioLogado;

    VerticalPanel vFormPanel = new VerticalPanel();
    ScrollPanel scrollPanel = new ScrollPanel();

    TextConstants txtConstants;

//    private TelaInicialPresencaAluno telaInicialPresencaAluno;
    
    
    public VisualizarPresencaAluno(final TelaInicialPresencaAlunoPais telaInicialPresencaAlunoPais) {
//        this.telaInicialPresencaAluno = telaInicialPresencaAluno;
        usuarioLogado = telaInicialPresencaAlunoPais.getMainView().getUsuarioLogado();
        startUI(TelaInicialPresencaAluno.intHeightTable - 120);
        super.add(vFormPanel);
    }

    public VisualizarPresencaAluno(final TelaInicialPresencaAluno telaInicialPresencaAluno) {

//        this.telaInicialPresencaAluno = telaInicialPresencaAluno;
        usuarioLogado = telaInicialPresencaAluno.getMainView().getUsuarioLogado();
        startUI(TelaInicialPresencaAluno.intHeightTable - 120);
        super.add(vFormPanel);

    }
    
    
    
    private void startUI(int intHeightTable){
        
        scrollPanel.setAlwaysShowScrollBars(false);
//        scrollPanel.setHeight(Integer.toString(TelaInicialPresencaAluno.intHeightTable - 120) + "px");
        scrollPanel.setHeight(intHeightTable + "px");
        scrollPanel.setWidth("100%");

        txtConstants = GWT.create(TextConstants.class);

        mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
        mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
        mpPanelLoadingAluno.setTxtLoading(txtConstants.geralCarregando());
        mpPanelLoadingAluno.show();
        mpPanelLoadingAluno.setVisible(false);

        FlexTable flexTableWithListBoxes = new FlexTable();
        flexTableWithListBoxes.setCellSpacing(3);
        flexTableWithListBoxes.setCellPadding(3);
        flexTableWithListBoxes.setBorderWidth(0);
        FlexCellFormatter cellFormatter = flexTableWithListBoxes.getFlexCellFormatter();

        // Add a title to the form
        // cellFormatter.setColSpan(0, 0, 0);
        cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

        Label lblCurso = new Label(txtConstants.curso());

        lblCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        lblCurso.setStyleName("design_label");

        // Add some standard form options
        int row = 1;
        flexTableWithListBoxes.setWidget(row, 0, lblCurso);

        if (usuarioLogado.getIdTipoUsuario() == TipoUsuario.ALUNO) {
            listBoxCursoAluno = new MpListBoxCursoAmbienteAluno(usuarioLogado);
            listBoxCursoAluno.addChangeHandler(new MpCursoSelectionChangeHandler());
            flexTableWithListBoxes.setWidget(row, 0, lblCurso);
            flexTableWithListBoxes.setWidget(row, 1, listBoxCursoAluno);
            flexTableWithListBoxes.setWidget(row++, 2, mpPanelLoadingAluno);
        } else if (usuarioLogado.getIdTipoUsuario() == TipoUsuario.PAIS) {
            listBoxCursoAlunoPais = new MpListBoxCursoAmbientePais(usuarioLogado);
            listBoxCursoAlunoPais.addChangeHandler(new MpCursoSelectionChangeHandler());
            Label lblNomeAluno = new Label(txtConstants.alunoNome());
            lblNomeAluno.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
            lblNomeAluno.setStyleName("design_label");

            listBoxAlunosPorCursoPais = new MpListBoxAlunosPorCursoAmbientePais();
            listBoxAlunosPorCursoPais.addChangeHandler(new MpAlunosPorCursoSelectionChangeHandler());

            flexTableWithListBoxes.setWidget(row, 0, lblCurso);
            flexTableWithListBoxes.setWidget(row++, 1, listBoxCursoAlunoPais);
            flexTableWithListBoxes.setWidget(row, 0, lblNomeAluno);
            flexTableWithListBoxes.setWidget(row, 1, listBoxAlunosPorCursoPais);
            flexTableWithListBoxes.setWidget(row, 2, mpPanelLoadingAluno);

        } else {

            listBoxCursoAluno = new MpListBoxCursoAmbienteAluno(usuarioLogado);
            listBoxCursoAluno.addChangeHandler(new MpCursoSelectionChangeHandler());
            Label lblNomeAluno = new Label(txtConstants.alunoNome());
            lblNomeAluno.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
            lblNomeAluno.setStyleName("design_label");

            listBoxAlunosPorCurso = new MpSelectionAlunosPorCurso();
            listBoxAlunosPorCurso.addChangeHandler(new MpAlunosPorCursoSelectionChangeHandler());

            flexTableWithListBoxes.setWidget(row, 0, lblCurso);
            flexTableWithListBoxes.setWidget(row++, 1, listBoxCursoAluno);
            flexTableWithListBoxes.setWidget(row, 0, lblNomeAluno);
            flexTableWithListBoxes.setWidget(row, 1, listBoxAlunosPorCurso);
            flexTableWithListBoxes.setWidget(row, 2, mpPanelLoadingAluno);

        }

        vFormPanel.add(flexTableWithListBoxes);
        vFormPanel.setWidth("100%");


        initializeCellTable();

        this.setWidth("100%");
    }

    /*******************************************************************************************************/
    /*******************************************************************************************************/
    /*******************************************************************************************************/
    /*************************************** Begin Event Handlers ***************************************************/

    private class MpCursoSelectionChangeHandler implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            
            
            if (usuarioLogado.getIdTipoUsuario() == TipoUsuario.ALUNO) {

                if (listBoxCursoAluno.getSelectedIndex() == -1) {
                    cleanCellTable();
                } else {
                    int idCurso = Integer.parseInt(listBoxCursoAluno.getSelectedValue());
                    int idUsuario = usuarioLogado.getIdUsuario();
                    populateGrid(idCurso, idUsuario);
                }
                
            } else if (usuarioLogado.getIdTipoUsuario() == TipoUsuario.PAIS) {

                if (listBoxCursoAlunoPais.getSelectedIndex() == -1) {
                    cleanCellTable();
                } else {
                    int idCurso = Integer.parseInt(listBoxCursoAlunoPais.getSelectedValue());
//                    int idUsuario = usuarioLogado.getIdUsuario();
//                    populateGrid(idCurso, idUsuario);
                    listBoxAlunosPorCursoPais.populateComboBox(usuarioLogado, idCurso);
                }
                
            } else {
                
                if (listBoxCursoAluno.getSelectedIndex() == -1) {
                    cleanCellTable();
                } else {
                    int idCurso = Integer.parseInt(listBoxCursoAluno.getSelectedValue());
                    listBoxAlunosPorCurso.populateComboBox(idCurso);
                }
            }

            
 

        }
    }

    private class MpAlunosPorCursoSelectionChangeHandler implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            
            if (usuarioLogado.getIdTipoUsuario() == TipoUsuario.ALUNO) {

                if (listBoxAlunosPorCurso.getSelectedIndex() == -1) {
                    cleanCellTable();
                } else {
                    int idUsuario = usuarioLogado.getIdUsuario();
                    int idCurso = Integer.parseInt(listBoxCursoAluno.getSelectedValue());
                    populateGrid(idCurso, idUsuario);
                }
                
            } else if (usuarioLogado.getIdTipoUsuario() == TipoUsuario.PAIS) {

                if (listBoxAlunosPorCursoPais.getSelectedIndex() == -1) {
                    cleanCellTable();
                } else {
                    int idUsuario = Integer.parseInt(listBoxAlunosPorCursoPais.getSelectedValue());
                    int idCurso = Integer.parseInt(listBoxCursoAlunoPais.getSelectedValue());
                    populateGrid(idCurso, idUsuario);
                }

            }else{
                
                if (listBoxAlunosPorCurso.getSelectedIndex() == -1) {
                    cleanCellTable();
                } else {
                    int idUsuario = Integer.parseInt(listBoxAlunosPorCurso.getSelectedValue());
                    int idCurso = Integer.parseInt(listBoxCursoAluno.getSelectedValue());
                    populateGrid(idCurso, idUsuario);
                }
            }


        }
    }

    private class EnterKeyUpHandler implements KeyUpHandler {
        public void onKeyUp(KeyUpEvent event) {
//            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                filtrarCellTable(txtSearch.getText());
//            }
        }
    }

//    private class ClickHandlerFiltrar implements ClickHandler {
//        public void onClick(ClickEvent event) {
//            filtrarCellTable(txtSearch.getText());
//        }
//    }

    /*************************************** End Event Handlers ***************************************/
    /*******************************************************************************************************/
    /*******************************************************************************************************/
    /*******************************************************************************************************/
    /*******************************************************************************************************/
    /*************************************** Begin POPULATE DATA ***************************************/

    private void populateGrid(int idCurso, int idUsuario) {

            mpPanelLoadingAluno.setVisible(true);

            GWTServicePresenca.Util.getInstance().getPresencaUsuarioDisciplinaAluno(idUsuario, idCurso, new AsyncCallback<ArrayList<PresencaUsuarioDisciplinaAluno>>() {
                public void onFailure(Throwable caught) {
                    mpPanelLoadingAluno.setVisible(false);
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText(txtConstants.presencaErroSalvar());
                    mpDialogBoxWarning.showDialog();
                }

                @Override
                public void onSuccess(ArrayList<PresencaUsuarioDisciplinaAluno> list) {

                    MpUtilClient.isRefreshRequired(list);

                    mpPanelLoadingAluno.setVisible(false);
                    dataProvider.getList().clear();
                    arrayListBackup.clear();
                    cellTable.setRowCount(0);
                    for (int i = 0; i < list.size(); i++) {
                        dataProvider.getList().add(list.get(i));
                        arrayListBackup.add(list.get(i));
                    }
                    addCellTableData(dataProvider);
                    cellTable.redraw();
                }
            });
//        }
    }

    public void updateClientData() {
//        listBoxCursoAluno.populateComboBox(this.telaInicialPresencaAluno.getMainView().getUsuarioLogado());
        if(usuarioLogado.getIdTipoUsuario()==TipoUsuario.ALUNO){
            listBoxCursoAluno.populateComboBox(usuarioLogado);
        }else if(usuarioLogado.getIdTipoUsuario()==TipoUsuario.PAIS){
            listBoxCursoAlunoPais.populateComboBox(usuarioLogado);
        }
    }

    /*************************************** END POPULATE DATA ***********************************************/
    /*******************************************************************************************************/
    /*******************************************************************************************************/
    /*******************************************************************************************************/
    /*************************************** BEGIN CellTable Functions ***************************************/

    public void initializeCellTable() {
        cellTable = new CellTable<PresencaUsuarioDisciplinaAluno>(10, GWT.<CellTableStyle> create(CellTableStyle.class));

        cellTable.setWidth("100%");
        cellTable.setAutoHeaderRefreshDisabled(true);
        cellTable.setAutoFooterRefreshDisabled(true);

        dataProvider.addDataDisplay(cellTable);

        // Add a selection model so we can select cells.
        final SelectionModel<PresencaUsuarioDisciplinaAluno> selectionModel = new MultiSelectionModel<PresencaUsuarioDisciplinaAluno>();
        cellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<PresencaUsuarioDisciplinaAluno> createCheckboxManager());

        initTableColumns(selectionModel);

        final MpSimplePager mpPager = new MpSimplePager();
        mpPager.setDisplay(cellTable);

        String strSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        SafeHtmlBuilder builder = new SafeHtmlBuilder();
        builder.appendHtmlConstant(strSpace + strSpace + txtConstants.periodo() + strSpace + strSpace);

        FlexTable flexTableFiltrarAluno = new FlexTable();
        flexTableFiltrarAluno.setBorderWidth(2);
        flexTableFiltrarAluno.setCellSpacing(3);
        flexTableFiltrarAluno.setCellPadding(3);
        flexTableFiltrarAluno.setBorderWidth(0);

        // Label lblAluno = new Label(txtConstants.aluno());
//        MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
        if (txtSearch == null) {
            txtSearch = new TextBox();
            txtSearch.setStyleName("design_text_boxes");
        }

        txtSearch.addKeyUpHandler(new EnterKeyUpHandler());
//        btnFiltrar.addClickHandler(new ClickHandlerFiltrar());

        flexTableFiltrarAluno.setWidget(0, 0, txtSearch);
        flexTableFiltrarAluno.setWidget(0, 1, new MpSpaceVerticalPanel());        
        flexTableFiltrarAluno.setWidget(0, 2, mpPager);
//        flexTableFiltrarAluno.setWidget(0, 4, btnFiltrar);

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setWidth("100%");
        vPanel.setCellVerticalAlignment(cellTable, ALIGN_TOP);
        vPanel.add(cellTable);

        MpSpaceVerticalPanel mpSpaceVerticalPanel = new MpSpaceVerticalPanel();

        VerticalPanel vPanelInScroll = new VerticalPanel();
        vPanelInScroll.setWidth("100%");
        vPanelInScroll.setBorderWidth(0);
        vPanelInScroll.setCellVerticalAlignment(cellTable, ALIGN_TOP);
        vPanelInScroll.add(flexTableFiltrarAluno);
        vPanelInScroll.add(vPanel);
        vPanelInScroll.add(mpSpaceVerticalPanel);
        scrollPanel.clear();
        scrollPanel.add(vPanelInScroll);

        vFormPanel.add(scrollPanel);

        mpPanelLoadingAluno.setVisible(false);

        filtrarCellTable(txtSearch.getText());

    }

    protected void cleanCellTable() {
        if (cellTable != null) {
            dataProvider.getList().clear();
            cellTable.setRowCount(0);
            cellTable.redraw();
        }
    }

    private void addCellTableData(ListDataProvider<PresencaUsuarioDisciplinaAluno> dataProvider) {

        ListHandler<PresencaUsuarioDisciplinaAluno> sortHandler = new ListHandler<PresencaUsuarioDisciplinaAluno>(dataProvider.getList());

        cellTable.addColumnSortHandler(sortHandler);

        initSortHandler(sortHandler);

    }

    private void initTableColumns(final SelectionModel<PresencaUsuarioDisciplinaAluno> selectionModel) {

        columnNomePeriodo = new Column<PresencaUsuarioDisciplinaAluno, String>(new TextCell()) {
            @Override
            public String getValue(PresencaUsuarioDisciplinaAluno object) {
                return object.getNomePeriodo();
            }

        };

        columnNomeDisciplina = new Column<PresencaUsuarioDisciplinaAluno, String>(new TextCell()) {
            @Override
            public String getValue(PresencaUsuarioDisciplinaAluno object) {
                return object.getNomeDisciplina();
            }

        };

        columnQuantAula = new Column<PresencaUsuarioDisciplinaAluno, String>(new TextCell()) {
            @Override
            public String getValue(PresencaUsuarioDisciplinaAluno object) {
                return Integer.toString(object.getNumeroAulas());
            }
        };

        columnNumeroPresenca = new Column<PresencaUsuarioDisciplinaAluno, String>(new TextCell()) {
            @Override
            public String getValue(PresencaUsuarioDisciplinaAluno object) {
                return Integer.toString(object.getNumeroPresenca());
            }
        };

        columnQuantFalta = new Column<PresencaUsuarioDisciplinaAluno, String>(new TextCell()) {
            @Override
            public String getValue(PresencaUsuarioDisciplinaAluno object) {
                return Integer.toString(object.getNumeroFaltas());
            }
        };

        columnPorcentagemPresenca = new Column<PresencaUsuarioDisciplinaAluno, String>(new TextCell()) {
            @Override
            public String getValue(PresencaUsuarioDisciplinaAluno object) {

                return Integer.toString(object.getPorcentagemPresencaAula()) + "%";
            }
        };

        columnSituacao = new Column<PresencaUsuarioDisciplinaAluno, String>(new TextCell()) {
            @Override
            public String getValue(PresencaUsuarioDisciplinaAluno object) {

                return object.getSituacao();
            }
        };

        cellTable.addColumn(columnNomePeriodo, txtConstants.periodoNome());
        cellTable.addColumn(columnNomeDisciplina, txtConstants.disciplina());
        cellTable.addColumn(columnQuantAula, txtConstants.presencaQuantidadeAulas());
        cellTable.addColumn(columnNumeroPresenca, txtConstants.presencaQuantidadePresenca());
        cellTable.addColumn(columnQuantFalta, txtConstants.presencaQuantidadeFaltas());
        // cellTable.addColumn(quantJustificativaColumn,txtConstants.presencaQuantidadeJustificativas());
        cellTable.addColumn(columnPorcentagemPresenca, txtConstants.presencaSalaDeAula());
        cellTable.addColumn(columnSituacao, txtConstants.presencaSituacao());

        // Make the name column sortable.
        // nomePeriodoColumn.setSortable(true);

        // cellTable.getColumn(cellTable.getColumnIndex(nomePeriodoColumn)).setCellStyleNames("edit-cell");
        // cellTable.getColumn(cellTable.getColumnIndex(nomeDisciplinaColumn)).setCellStyleNames("edit-cell");
        // cellTable.getColumn(cellTable.getColumnIndex(quantAulaColumn)).setCellStyleNames("edit-cell");
        // cellTable.getColumn(cellTable.getColumnIndex(quantPresencaColumn)).setCellStyleNames("hand-over");

    }

    public void initSortHandler(ListHandler<PresencaUsuarioDisciplinaAluno> sortHandler) {
        columnNomePeriodo.setSortable(true);
        sortHandler.setComparator(columnNomePeriodo, new Comparator<PresencaUsuarioDisciplinaAluno>() {
            @Override
            public int compare(PresencaUsuarioDisciplinaAluno o1, PresencaUsuarioDisciplinaAluno o2) {
                return o1.getNomePeriodo().compareTo(o2.getNomePeriodo());
            }
        });

        columnNomeDisciplina.setSortable(true);
        sortHandler.setComparator(columnNomeDisciplina, new Comparator<PresencaUsuarioDisciplinaAluno>() {
            @Override
            public int compare(PresencaUsuarioDisciplinaAluno o1, PresencaUsuarioDisciplinaAluno o2) {
                return o1.getNomeDisciplina().compareTo(o2.getNomeDisciplina());
            }
        });

        columnQuantAula.setSortable(true);
        sortHandler.setComparator(columnQuantAula, new Comparator<PresencaUsuarioDisciplinaAluno>() {
            @Override
            public int compare(PresencaUsuarioDisciplinaAluno o1, PresencaUsuarioDisciplinaAluno o2) {
                String strO1 = Integer.toString(o1.getNumeroAulas());
                String strO2 = Integer.toString(o2.getNumeroAulas());
                return strO1.compareTo(strO2);
            }
        });

        columnNumeroPresenca.setSortable(true);
        sortHandler.setComparator(columnNumeroPresenca, new Comparator<PresencaUsuarioDisciplinaAluno>() {
            @Override
            public int compare(PresencaUsuarioDisciplinaAluno o1, PresencaUsuarioDisciplinaAluno o2) {
                String strO1 = Integer.toString(o1.getNumeroPresenca());
                String strO2 = Integer.toString(o2.getNumeroPresenca());
                return strO1.compareTo(strO2);
            }
        });

        columnQuantFalta.setSortable(true);
        sortHandler.setComparator(columnQuantFalta, new Comparator<PresencaUsuarioDisciplinaAluno>() {
            @Override
            public int compare(PresencaUsuarioDisciplinaAluno o1, PresencaUsuarioDisciplinaAluno o2) {
                String strO1 = Integer.toString(o1.getNumeroFaltas());
                String strO2 = Integer.toString(o2.getNumeroFaltas());
                return strO1.compareTo(strO2);
            }
        });

        columnSituacao.setSortable(true);
        sortHandler.setComparator(columnSituacao, new Comparator<PresencaUsuarioDisciplinaAluno>() {
            @Override
            public int compare(PresencaUsuarioDisciplinaAluno o1, PresencaUsuarioDisciplinaAluno o2) {
                return o1.getSituacao().compareTo(o2.getSituacao());
            }
        });

    }

    /*************************************** BEGIN CellTable Functions ***************************************/
    /*******************************************************************************************************/
    /*******************************************************************************************************/
    /*******************************************************************************************************/
    /*************************************** BEGIN Filterting CellTable ***************************************/

    public void filtrarCellTable(String strFiltro) {

        removeCellTableFilter();

        strFiltro = strFiltro.toUpperCase();
        strFiltro = strFiltro.trim();

        if (!strFiltro.isEmpty()) {

            int i = 0;
            while (i < dataProvider.getList().size()) {

                String strNomePeriodo = dataProvider.getList().get(i).getNomePeriodo();
                String strNomeDisciplina = dataProvider.getList().get(i).getNomeDisciplina();
                String strNumeroAula = Integer.toString(dataProvider.getList().get(i).getNumeroAulas());
                String strNumeroPresenca = Integer.toString(dataProvider.getList().get(i).getNumeroPresenca());
                String strNumeroFalta = Integer.toString(dataProvider.getList().get(i).getNumeroFaltas());
                String strPorcentagemPresenca = Integer.toString(dataProvider.getList().get(i).getPorcentagemPresencaAula());
                String strSituacao = dataProvider.getList().get(i).getSituacao();

                String strJuntaTexto = strNomePeriodo + " " + strNomeDisciplina + " " + strNumeroAula + 
                        " " + strNumeroPresenca + " " + strNumeroFalta + " " + strPorcentagemPresenca + " " + strSituacao;

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

        for (int i = 0; i < arrayListBackup.size(); i++) {
            dataProvider.getList().add(arrayListBackup.get(i));
        }
        cellTable.setPageStart(0);
    }
    /*************************************** END Filterting CellTable ***************************************/

}
