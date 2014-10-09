package com.jornada.client.classes.hierarquia;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import com.jornada.client.MainView;
import com.jornada.client.classes.resources.CellTreeStyle;
import com.jornada.client.classes.resources.CustomTreeModel;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpPanelPageMainView;
import com.jornada.client.classes.widgets.textbox.MpTextBox;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceHierarquiaCurso;
import com.jornada.shared.classes.ConteudoProgramatico;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Topico;
import com.jornada.shared.classes.utility.MpUtilClient;

public abstract class MpHierarquiaCurso extends Composite {

    protected AsyncCallback<ArrayList<Curso>> callBackListaCursos;

    protected static final int intWidthTable = 1400;
    protected static final int intHeightTable = 500;

    protected static final int intWidthNavigationPanel = 350;

    protected VerticalPanel panelTree;
    protected VerticalPanel panelDetalhes;
    protected ScrollPanel scrollPanelTree;

    protected Grid gridSearch;
    private MpTextBox textBoxSearch;

    protected static final int LOADING_POSITION = 1;

    protected Label labelMessage;
    
    protected MainView mainView;

    protected MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
    protected MpDialogBox mpDialogBoxWarning = new MpDialogBox();
    protected MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");

    protected static TextConstants txtConstants = GWT.create(TextConstants.class);

    public MpHierarquiaCurso(MainView mainView) {

        this.mainView = mainView;
        
        mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
        mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
        mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
        mpPanelLoading.show();

        /************************* Begin Callback's *************************/

        callBackListaCursos = new AsyncCallback<ArrayList<Curso>>() {

            public void onSuccess(ArrayList<Curso> list) {

                MpUtilClient.isRefreshRequired(list);

                labelMessage = new Label("");

                final SingleSelectionModel<Object> selectionModel = new SingleSelectionModel<Object>(CustomTreeModel.KEY_PROVIDER_OBJECT);

                TreeViewModel treeViewModel = new CustomTreeModel(list, selectionModel);

                CellTree.Resources resource = GWT.create(CellTreeStyle.class);

                CellTree tree = new CellTree(treeViewModel, null, resource);
                tree.setAnimationEnabled(false);

                selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

                    public void onSelectionChange(SelectionChangeEvent event) {

                        // SingleSelectionModel<Object> selectionModelAux =
                        // selectionModel;

                        Object object = selectionModel.getSelectedObject();

                        // selectionModel.setSelected(object, true);

                        if (object instanceof Curso) {
                            showCurso((Curso) object);
                        } else if (object instanceof Periodo) {
                            showPeriodo((Periodo) object);
                        } else if (object instanceof Disciplina) {
                            showDisciplina((Disciplina) object);
                        } else if (object instanceof ConteudoProgramatico) {
                            showConteudoProgramatico((ConteudoProgramatico) object);
                        } else if (object instanceof Topico) {
                            showTopico((Topico) object);
                        } else {
                            labelMessage.setText("No Object");
                        }

                    }

                });

                // Open the first Curso by default.
                // TreeNode rootNode = tree.getRootTreeNode();
                // TreeNode firstPlaylist = rootNode.setChildOpen(0, true);
                // firstPlaylist.setChildOpen(0, true);

                gridSearch.getRowFormatter().setVisible(LOADING_POSITION, false);
                panelTree.clear();
                panelTree.add(tree);

            }

            public void onFailure(Throwable cautch) {
//                mpPanelLoading.setVisible(false);
                gridSearch.getRowFormatter().setVisible(LOADING_POSITION, false);
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.cursoErroCarregarLista());
                mpDialogBoxWarning.showDialog();

            }
        };

        /*********************** End Callbacks **********************/

        // panelTree.setBorderWidth(1);

        panelDetalhes = new VerticalPanel();
        panelDetalhes.setBorderWidth(0);
        panelDetalhes.setHeight(Integer.toString(intHeightTable) + "px");
        panelDetalhes.setWidth("100%");
        panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

        panelTree = new VerticalPanel();
        // panelTree.setSize(Integer.toString(intHeightNavigationPanel)+"px",Integer.toString(intHeightTable)+"px");
        // panelTree.add(mpPanelLoading);

        ScrollPanel scrollPanelTree = new ScrollPanel();
        scrollPanelTree.setStyleName("scrollbar");
        scrollPanelTree.add(panelTree);
        scrollPanelTree.setAlwaysShowScrollBars(false);
        scrollPanelTree.setSize(Integer.toString(intWidthNavigationPanel) + "px", Integer.toString(intHeightTable) + "px");

        MpPanelPageMainView mpPanelTree = new MpPanelPageMainView(txtConstants.cursoNavegacao(), "images/view_tree-16.png");
        // mpPanelTree.setSize(Integer.toString(intHeightNavigationPanel)+"px",Integer.toString(intHeightTable)+"px");

        textBoxSearch = new MpTextBox();
        textBoxSearch.setWidth("100%");
        textBoxSearch.addKeyUpHandler(new clickHandlerPopulateTree());
        panelTree.add(textBoxSearch);

        gridSearch = new Grid(2, 1);
        gridSearch.setBorderWidth(0);
        gridSearch.setWidth("99%");
        gridSearch.setWidget(0, 0, textBoxSearch);
        gridSearch.setWidget(1, 0, mpPanelLoading);
     
        int intTipoUser = mainView.getUsuarioLogado().getIdTipoUsuario();
        
        gridSearch.getRowFormatter().setVisible(0, false);
        if(intTipoUser==TipoUsuario.ADMINISTRADOR || intTipoUser==TipoUsuario.COORDENADOR ){
            gridSearch.getRowFormatter().setVisible(0, true);            
        }

        VerticalPanel vPanel = new VerticalPanel();

        vPanel.add(gridSearch);
        // vPanel.add(mpPanelLoading);
        vPanel.add(scrollPanelTree);

        // mpPanelTree.addPage(scrollPanelTree);
        mpPanelTree.addPage(vPanel);

        Grid grid = new Grid(1, 2);
        grid.setWidth("100%");
        grid.setCellPadding(0);
        grid.setCellSpacing(0);
        grid.setBorderWidth(0);

        grid.setWidget(0, 0, mpPanelTree);
        grid.setWidget(0, 1, panelDetalhes);
        grid.getCellFormatter().setWidth(0, 0, Integer.toString(intWidthNavigationPanel) + "px");
        grid.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);

        initWidget(grid);

        this.setWidth("100%");
    }

    
    public abstract void populateTree(MainView mainView);

    public void showCurso(Curso object) {

        panelDetalhes.clear();

        VerticalPanel vPanelTitle = vPanelTitulo(txtConstants.curso(), object.getNome(), "images/folder_library-24.png");
        vPanelTitle.setWidth("100%");

        panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

        FlexTable flexTable = new FlexTable();
        flexTable.setWidth("100%");
        flexTable.setCellPadding(2);
        flexTable.setCellSpacing(2);
        flexTable.setWidget(0, 0, vPanelTitle);

        flexTable.setWidget(1, 0, new MpScrollPanelCurso(object));

        panelDetalhes.add(flexTable);

    }

    public void showPeriodo(Periodo object) {
        panelDetalhes.clear();

        VerticalPanel vPanelTitle = vPanelTitulo(txtConstants.periodo(), object.getNomePeriodo(), "images/my_projects_folder-24.png");
        vPanelTitle.setWidth("100%");

        panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

        FlexTable flexTable = new FlexTable();
        flexTable.setWidth("100%");
        flexTable.setCellPadding(2);
        flexTable.setCellSpacing(2);
        flexTable.setWidget(0, 0, vPanelTitle);
        flexTable.setWidget(1, 0, new MpScrollPanelPeriodo(object));

        panelDetalhes.add(flexTable);
    }

    public void showDisciplina(Disciplina object) {

        panelDetalhes.clear();

        VerticalPanel vPanelTitle = vPanelTitulo(txtConstants.disciplina(), object.getNome(), "images/books-24.png");
        vPanelTitle.setWidth("100%");

        panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

        FlexTable flexTable = new FlexTable();
        flexTable.setWidth("100%");
        flexTable.setCellPadding(2);
        flexTable.setCellSpacing(2);
        flexTable.setWidget(0, 0, vPanelTitle);
        flexTable.setWidget(1, 0, new MpScrollPanelDisciplina(object));

        panelDetalhes.add(flexTable);
    }

    public void showConteudoProgramatico(ConteudoProgramatico object) {

        panelDetalhes.clear();

        VerticalPanel vPanelTitle = vPanelTitulo(txtConstants.conteudoProgramatico(), object.getNome(), "images/textdocument-24.png");
        vPanelTitle.setWidth("100%");

        panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

        FlexTable flexTable = new FlexTable();
        flexTable.setWidth("100%");
        flexTable.setCellPadding(2);
        flexTable.setCellSpacing(2);
        flexTable.setWidget(0, 0, vPanelTitle);
        flexTable.setWidget(1, 0, new MpScrollPanelConteudoProgramatico(object));

        panelDetalhes.add(flexTable);

    }

    public void showTopico(Topico object) {

        panelDetalhes.clear();

        VerticalPanel vPanelTitle = vPanelTitulo(txtConstants.topico(), object.getNome(), "images/type_list-24.png");
        vPanelTitle.setWidth("100%");

        panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

        FlexTable flexTable = new FlexTable();
        flexTable.setWidth("100%");
        flexTable.setCellPadding(2);
        flexTable.setCellSpacing(2);
        flexTable.setWidget(0, 0, vPanelTitle);
        flexTable.setWidget(1, 0, new MpScrollPanelTopico(object));

        panelDetalhes.add(flexTable);
    }

    public VerticalPanel vPanelTitulo(String strTitle, String strNome, String strImgAddress) {
        Label lblTitulo = new Label(strTitle);
        lblTitulo.setStyleName("label_comum_bold_12px");
        Label lblNome = new Label(strNome);
        lblNome.setStyleName("label_comum");
        lblTitulo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        VerticalPanel vPanelTitle = new VerticalPanel();
        vPanelTitle.setStyleName("designTree");
        vPanelTitle.setWidth(Integer.toString(intWidthTable - 200) + "px");

        vPanelTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        vPanelTitle.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        Image img = new Image(strImgAddress);

        Grid grid = new Grid(1, 3);
        grid.setCellPadding(1);
        grid.setCellSpacing(1);
        grid.setWidget(0, 0, img);
        grid.setWidget(0, 1, lblTitulo);
        grid.setWidget(0, 2, lblNome);

        vPanelTitle.add(grid);

        return vPanelTitle;
    }

    private class clickHandlerPopulateTree implements KeyUpHandler {
        @Override
        public void onKeyUp(KeyUpEvent event) {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                // mpPanelLoading.setVisible(true);
                panelTree.clear();
                gridSearch.getRowFormatter().setVisible(LOADING_POSITION, true);
                GWTServiceHierarquiaCurso.Util.getInstance().getHierarquiaCursos("%" + textBoxSearch.getText() + "%", callBackListaCursos);
            }
        }

    }

}
