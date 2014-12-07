package com.jornada.client.ambiente.coordenador.usuario;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.MpListBoxCursoItemTodos;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.MpListBoxDocumentos;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBoxRefreshPage;
import com.jornada.client.classes.widgets.label.MpLabel;
import com.jornada.client.classes.widgets.label.MpLabelRight;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpPanelPageMainView;
import com.jornada.client.classes.widgets.textbox.MpTextBox;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceDocumento;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.classes.RelDocumentoUsuario;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.utility.MpUtilClient;

public class ContratoUsuario extends VerticalPanel {

    private VerticalPanel vFormPanel;

    private MpPanelPageMainView mpPanelFiltrarPaisPor;
    private MpPanelPageMainView mpPanelFiltrarPaisPorCurso;
//    private MpPanelPageMainView mpPanelFiltrarFilhoPor;
    private MpPanelPageMainView mpPanelFiltrarFilhoPorCurso;

    private MpListBoxDocumentos mpSelectionDocumentos;

    private RadioButton radioButtonFiltrarPaisPorNome;
    private RadioButton radioButtonFiltrarPaisPorCurso;
    private RadioButton radioButtonRespFinanceiro;
    private RadioButton radioButtonRespAcademico;
    private RadioButton radioButtonRespTodos;

    MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
    MpDialogBox mpDialogBoxWarning = new MpDialogBox();
    MpPanelLoading mpPanelLoadingSalvarPais = new MpPanelLoading("images/radar.gif");
    MpPanelLoading mpPanelLoadingFiltrarPais = new MpPanelLoading("images/radar.gif");
    MpPanelLoading mpPanelLoadingFiltrarCursoPais = new MpPanelLoading("images/radar.gif");
    MpPanelLoading mpPanelLoadingSalvarFilhos = new MpPanelLoading("images/radar.gif");
    MpPanelLoading mpPanelLoadingFiltrarFilhos = new MpPanelLoading("images/radar.gif");
    MpPanelLoading mpPanelLoadingFiltrarCursoFilhos = new MpPanelLoading("images/radar.gif");


    MpTextBox txtFiltroCursosPais;    
    MpTextBox txtFiltroCursosFilhos;

    private MpTextBox txtFiltroNomeDosPais;
    private ListBox multiBoxPaisFiltrados;
    private ListBox multiBoxPaisAssociados;
    
    private MpTextBox txtFiltroFilhos;    
    private ListBox multiBoxFilhosFiltrados;
    private ListBox multiBoxFilhosAssociados;
    

    // private ListBox listBoxCurso;
    private MpListBoxCursoItemTodos mpSelectionCursoPorPais;
    private MpListBoxCursoItemTodos mpSelectionCursoPorFilhos;
    
    private ArrayList<Usuario> listAuxUsuarioPais;
    private ArrayList<Usuario> listAuxUsuarioFilhos;

    @SuppressWarnings("unused")
    private TelaInicialUsuario telaInicialPeriodo;

    private TextConstants txtConstants;

    private static ContratoUsuario uniqueInstance;

    public static ContratoUsuario getInstance(final TelaInicialUsuario telaInicialUsuario) {

        if (uniqueInstance == null) {
            uniqueInstance = new ContratoUsuario(telaInicialUsuario);
        }

        return uniqueInstance;

    }

    private ContratoUsuario(final TelaInicialUsuario telaInicialUsuario) {

        txtConstants = GWT.create(TextConstants.class);
        
        listAuxUsuarioPais = new ArrayList<Usuario>();
        listAuxUsuarioFilhos = new ArrayList<Usuario>();

        this.telaInicialPeriodo = telaInicialUsuario;

        mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
        mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
        mpPanelLoadingSalvarPais.setTxtLoading("");
        mpPanelLoadingSalvarPais.show();
        mpPanelLoadingSalvarPais.setVisible(false);
        mpPanelLoadingFiltrarPais.setTxtLoading("");
        mpPanelLoadingFiltrarPais.show();
        mpPanelLoadingFiltrarPais.setVisible(false);
        mpPanelLoadingFiltrarCursoPais.setTxtLoading("");
        mpPanelLoadingFiltrarCursoPais.show();
        mpPanelLoadingFiltrarCursoPais.setVisible(false);
        mpPanelLoadingSalvarFilhos.setTxtLoading("");
        mpPanelLoadingSalvarFilhos.show();
        mpPanelLoadingSalvarFilhos.setVisible(false);
        mpPanelLoadingFiltrarFilhos.setTxtLoading("");
        mpPanelLoadingFiltrarFilhos.show();
        mpPanelLoadingFiltrarFilhos.setVisible(false);
        mpPanelLoadingFiltrarCursoFilhos.setTxtLoading("");
        mpPanelLoadingFiltrarCursoFilhos.show();
        mpPanelLoadingFiltrarCursoFilhos.setVisible(false);
        

        mpSelectionDocumentos = new MpListBoxDocumentos();
        mpSelectionDocumentos.addChangeHandler(new ChangeHandlerPopularDocumentos());
        mpSelectionCursoPorPais = new MpListBoxCursoItemTodos(false, "Todos os Cursos");
        mpSelectionCursoPorPais.addChangeHandler(new ChangeHandlerPopularPaisAssociados());
        mpSelectionCursoPorFilhos = new MpListBoxCursoItemTodos(false, "Todos os Cursos");
        mpSelectionCursoPorFilhos.addChangeHandler(new ChangeHandlerPopularFilhosAssociados());
        

        mpPanelFiltrarPaisPor = drawPassoFiltrarPaisPor();
        mpPanelFiltrarPaisPorCurso = drawPassoFiltrarPaisPorCurso();
        mpPanelFiltrarFilhoPorCurso = drawPassoFiltrarFilhosPorCurso();
        mpPanelFiltrarFilhoPorCurso.setVisible(false);

        vFormPanel = new VerticalPanel();
        vFormPanel.setWidth("100%");
        vFormPanel.add(mpPanelFiltrarPaisPor);
        vFormPanel.add(new InlineHTML("&nbsp;"));
        vFormPanel.add(mpPanelFiltrarPaisPorCurso);
        vFormPanel.add(mpPanelFiltrarFilhoPorCurso);

        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.setHeight(Integer.toString(TelaInicialUsuario.intHeightTable - 40) + "px");
        scrollPanel.setAlwaysShowScrollBars(false);
        scrollPanel.add(vFormPanel);

        this.setWidth("100%");
        super.add(scrollPanel);

    }

    public MpPanelPageMainView drawPassoFiltrarPaisPor() {

        MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.documentoSelecionarContrato(), "images/check.png");
        mpPanel.setWidth("100%");

        MpLabelRight lblTipoDoc = new MpLabelRight(txtConstants.documentoContrato());
        MpLabelRight lblFiltrarPaisPor = new MpLabelRight("Filtrar");

        radioButtonFiltrarPaisPorCurso = new RadioButton("useFiltroPais", "Por Pais");
        radioButtonFiltrarPaisPorCurso.addValueChangeHandler(new ValueChangeHandlerFiltrarPaisPorCurso());
        radioButtonFiltrarPaisPorCurso.setValue(true);
        radioButtonFiltrarPaisPorNome = new RadioButton("useFiltroPais", "Por Filhos");
        radioButtonFiltrarPaisPorNome.addValueChangeHandler(new ValueChangeHandlerFiltrarPaisPorNome());

        Grid gridFiltrarPaisPor = new Grid(1, 3);
        gridFiltrarPaisPor.setCellPadding(1);
        gridFiltrarPaisPor.setCellSpacing(1);
        gridFiltrarPaisPor.setWidget(0, 0, lblFiltrarPaisPor);
        gridFiltrarPaisPor.setWidget(0, 1, radioButtonFiltrarPaisPorCurso);
        gridFiltrarPaisPor.setWidget(0, 2, radioButtonFiltrarPaisPorNome);

        FlexTable flexTableFiltroPais = new FlexTable();
        flexTableFiltroPais.setCellSpacing(3);
        flexTableFiltroPais.setCellPadding(3);
        flexTableFiltroPais.setBorderWidth(0);
        int row = 0;
        flexTableFiltroPais.setWidget(row, 0, lblTipoDoc);
        flexTableFiltroPais.setWidget(row, 1, mpSelectionDocumentos);
         flexTableFiltroPais.setWidget(row, 2, new Label("|"));
         flexTableFiltroPais.setWidget(row++, 3, gridFiltrarPaisPor);

        mpPanel.addPage(flexTableFiltroPais);

        return mpPanel;
    }

    @SuppressWarnings("deprecation")
    public MpPanelPageMainView drawPassoFiltrarPaisPorCurso() {

        MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.documentoCursoParaFilterPais(), "images/parents-16.png");
        mpPanel.setWidth("100%");

        MpLabelRight lblCurso = new MpLabelRight("Filtro " + txtConstants.curso());
        MpLabelRight lblFiltrarResponsavel = new MpLabelRight(txtConstants.documentoReponsavel());

        txtFiltroCursosPais = new MpTextBox();
        txtFiltroCursosPais.setWidth("150px");
        txtFiltroCursosPais.addKeyUpHandler(new PaisEnterKeyUpHandlerFiltrarListBox());
        MpImageButton btnFiltroCursoPais = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
        btnFiltroCursoPais.addClickHandler(new PaisClickHandlerFiltrarListBoxCurso());

        radioButtonRespFinanceiro = new RadioButton("useFiltrarResponsaveis", txtConstants.documentoFinanceiro());
        radioButtonRespFinanceiro.setValue(true);
        radioButtonRespAcademico = new RadioButton("useFiltrarResponsaveis", txtConstants.documentoAcademico());
        radioButtonRespTodos = new RadioButton("useFiltrarResponsaveis", txtConstants.documentoTodos());

        Grid gridFiltrarResponsaveis = new Grid(1, 3);
        gridFiltrarResponsaveis.setCellPadding(1);
        gridFiltrarResponsaveis.setCellSpacing(1);
        // gridFiltrarResponsaveis.setWidget(0, 0, lblFiltrarResponsavel);
        gridFiltrarResponsaveis.setWidget(0, 0, radioButtonRespFinanceiro);
        gridFiltrarResponsaveis.setWidget(0, 1, radioButtonRespAcademico);
        gridFiltrarResponsaveis.setWidget(0, 2, radioButtonRespTodos);

        FlexTable flexTableFiltroPaisCurso = new FlexTable();
        flexTableFiltroPaisCurso.setCellSpacing(3);
        flexTableFiltroPaisCurso.setCellPadding(3);
        flexTableFiltroPaisCurso.setBorderWidth(0);
        int row = 0;
        flexTableFiltroPaisCurso.setWidget(row, 0, lblCurso);
        flexTableFiltroPaisCurso.setWidget(row, 1, mpSelectionCursoPorPais);
        flexTableFiltroPaisCurso.setWidget(row, 2, new InlineHTML("&nbsp;"));
        flexTableFiltroPaisCurso.setWidget(row, 3, txtFiltroCursosPais);
        flexTableFiltroPaisCurso.setWidget(row, 4, new InlineHTML("&nbsp;"));
        flexTableFiltroPaisCurso.setWidget(row, 5, btnFiltroCursoPais);
        flexTableFiltroPaisCurso.setWidget(row, 6, new InlineHTML("&nbsp;"));
        flexTableFiltroPaisCurso.setWidget(row++, 7, mpPanelLoadingFiltrarCursoPais);
        // flexTableFiltroPaisCurso.setWidget(row, 2, new InlineHTML("|"));
        flexTableFiltroPaisCurso.setWidget(row, 0, lblFiltrarResponsavel);
        flexTableFiltroPaisCurso.setWidget(row++, 1, gridFiltrarResponsaveis);

        MpLabelRight lblFiltrarPais = new MpLabelRight(txtConstants.usuarioNomePais());
        txtFiltroNomeDosPais = new MpTextBox();
        txtFiltroNomeDosPais.setWidth("150px");
        txtFiltroNomeDosPais.addKeyUpHandler(new PaisEnterKeyUpHandlerFiltrar());
        MpImageButton btnFiltroNomeDosPais = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
        btnFiltroNomeDosPais.addClickHandler(new PaisClickHandlerFiltrar());
        FlexTable flexTableFiltrar = new FlexTable();
        flexTableFiltrar.setCellSpacing(2);
        flexTableFiltrar.setCellPadding(2);
        flexTableFiltrar.setBorderWidth(0);
        flexTableFiltrar.setWidget(0, 0, lblFiltrarPais);
        flexTableFiltrar.setWidget(0, 1, txtFiltroNomeDosPais);
        flexTableFiltrar.setWidget(0, 2, btnFiltroNomeDosPais);
        flexTableFiltrar.setWidget(0, 3, mpPanelLoadingFiltrarPais);

        MpImageButton mpButtonParaEsquerda = new MpImageButton("", "images/resultset_previous.png");
        MpImageButton mpButtonParaDireita = new MpImageButton("", "images/resultset_next.png");
        mpButtonParaDireita.addClickHandler(new PaisClickHandlerPaisParaDireita());
        mpButtonParaEsquerda.addClickHandler(new PaisClickHandlerPaisParaEsquerda());
        FlexTable flexTableBotoes = new FlexTable();
        flexTableBotoes.setCellSpacing(2);
        flexTableBotoes.setCellPadding(2);
        flexTableBotoes.setBorderWidth(0);
        flexTableBotoes.setWidget(0, 0, mpButtonParaDireita);
        flexTableBotoes.setWidget(1, 0, mpButtonParaEsquerda);

        MpLabel lblPaisAssociados = new MpLabel(txtConstants.usuarioPaisAssociados());

        multiBoxPaisFiltrados = new ListBox(true);
        multiBoxPaisFiltrados.setWidth("450px");
        multiBoxPaisFiltrados.setHeight("130px");
        multiBoxPaisFiltrados.setVisibleItemCount(10);
        multiBoxPaisFiltrados.setStyleName("design_text_boxes");

        multiBoxPaisAssociados = new ListBox(true);
        multiBoxPaisAssociados.setWidth("450px");
        multiBoxPaisAssociados.setHeight("130px");
        multiBoxPaisAssociados.setVisibleItemCount(10);
        multiBoxPaisAssociados.setStyleName("design_text_boxes");

        FlexTable flexTableListBoxes = new FlexTable();
        flexTableListBoxes.setCellSpacing(2);
        flexTableListBoxes.setCellPadding(2);
        flexTableListBoxes.setBorderWidth(0);

        flexTableListBoxes.setWidget(0, 0, flexTableFiltrar);
        flexTableListBoxes.setWidget(0, 1, new InlineHTML("&nbsp;"));
        flexTableListBoxes.setWidget(0, 2, lblPaisAssociados);
        flexTableListBoxes.setWidget(1, 0, multiBoxPaisFiltrados);
        flexTableListBoxes.setWidget(1, 1, flexTableBotoes);
        flexTableListBoxes.setWidget(1, 2, multiBoxPaisAssociados);
        flexTableListBoxes.setWidget(2, 0, new InlineHTML("&nbsp;"));
        flexTableListBoxes.setWidget(3, 0, drawPassoSubmeterFormPais());
        flexTableListBoxes.getFlexCellFormatter().setColSpan(3, 0, 3);
        flexTableListBoxes.getFlexCellFormatter().setAlignment(3, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(flexTableFiltroPaisCurso);
        vPanel.add(flexTableListBoxes);
        vPanel.add(new InlineHTML("&nbsp;"));

        mpPanel.addPage(vPanel);

        return mpPanel;
    }
    
    
    @SuppressWarnings("deprecation")
    public MpPanelPageMainView drawPassoFiltrarFilhosPorCurso() {

        MpPanelPageMainView mpPanel = new MpPanelPageMainView("Por favor, selecione o curso para filtrar os alunos.", "images/elementary_school_16.png");
        mpPanel.setWidth("100%");

        MpLabelRight lblCurso = new MpLabelRight("Filtro " + txtConstants.curso());
//        MpLabelRight lblFiltrarResponsavel = new MpLabelRight(txtConstants.documentoReponsavel());

        txtFiltroCursosFilhos = new MpTextBox();
        txtFiltroCursosFilhos.setWidth("150px");
        txtFiltroCursosFilhos.addKeyUpHandler(new FilhosEnterKeyUpHandlerFiltrarListBox());
        MpImageButton btnFiltrarFilhos = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
        btnFiltrarFilhos.addClickHandler(new FilhosClickHandlerFiltrarListBoxCurso());


        FlexTable flexTableFiltroFilhosCurso = new FlexTable();
        flexTableFiltroFilhosCurso.setCellSpacing(3);
        flexTableFiltroFilhosCurso.setCellPadding(3);
        flexTableFiltroFilhosCurso.setBorderWidth(0);
        int row = 0;
        flexTableFiltroFilhosCurso.setWidget(row, 0, lblCurso);
        flexTableFiltroFilhosCurso.setWidget(row, 1, mpSelectionCursoPorFilhos);
        flexTableFiltroFilhosCurso.setWidget(row, 2, new InlineHTML("&nbsp;"));
        flexTableFiltroFilhosCurso.setWidget(row, 3, txtFiltroCursosFilhos);
        flexTableFiltroFilhosCurso.setWidget(row, 4, new InlineHTML("&nbsp;"));
        flexTableFiltroFilhosCurso.setWidget(row, 5, btnFiltrarFilhos);
        flexTableFiltroFilhosCurso.setWidget(row, 6, new InlineHTML("&nbsp;"));
        flexTableFiltroFilhosCurso.setWidget(row++, 7, mpPanelLoadingFiltrarCursoFilhos);


        MpLabelRight lblFiltrarFilhos = new MpLabelRight(txtConstants.usuarioNomeAluno());
        txtFiltroFilhos = new MpTextBox();
        txtFiltroFilhos.setWidth("150px");
        txtFiltroFilhos.addKeyUpHandler(new FilhosEnterKeyUpHandlerFiltrar());
        MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
        btnFiltrar.addClickHandler(new FilhosClickHandlerFiltrar());
        FlexTable flexTableFiltrar = new FlexTable();
        flexTableFiltrar.setCellSpacing(2);
        flexTableFiltrar.setCellPadding(2);
        flexTableFiltrar.setBorderWidth(0);
        flexTableFiltrar.setWidget(0, 0, lblFiltrarFilhos);
        flexTableFiltrar.setWidget(0, 1, txtFiltroFilhos);
        flexTableFiltrar.setWidget(0, 2, btnFiltrar);
        flexTableFiltrar.setWidget(0, 3, mpPanelLoadingFiltrarPais);

        MpImageButton mpButtonParaEsquerda = new MpImageButton("", "images/resultset_previous.png");
        MpImageButton mpButtonParaDireita = new MpImageButton("", "images/resultset_next.png");
        mpButtonParaDireita.addClickHandler(new FilhosClickHandlerPaisParaDireita());
        mpButtonParaEsquerda.addClickHandler(new FilhosClickHandlerPaisParaEsquerda());
        FlexTable flexTableBotoes = new FlexTable();
        flexTableBotoes.setCellSpacing(2);
        flexTableBotoes.setCellPadding(2);
        flexTableBotoes.setBorderWidth(0);
        flexTableBotoes.setWidget(0, 0, mpButtonParaDireita);
        flexTableBotoes.setWidget(1, 0, mpButtonParaEsquerda);

        MpLabel lblFilhosAssociados = new MpLabel("Alunos Associados");

        multiBoxFilhosFiltrados = new ListBox(true);
        multiBoxFilhosFiltrados.setWidth("450px");
        multiBoxFilhosFiltrados.setHeight("130px");
        multiBoxFilhosFiltrados.setVisibleItemCount(10);
        multiBoxFilhosFiltrados.setStyleName("design_text_boxes");

        multiBoxFilhosAssociados = new ListBox(true);
        multiBoxFilhosAssociados.setWidth("450px");
        multiBoxFilhosAssociados.setHeight("130px");
        multiBoxFilhosAssociados.setVisibleItemCount(10);
        multiBoxFilhosAssociados.setStyleName("design_text_boxes");

        FlexTable flexTableListBoxes = new FlexTable();
        flexTableListBoxes.setCellSpacing(2);
        flexTableListBoxes.setCellPadding(2);
        flexTableListBoxes.setBorderWidth(0);

        flexTableListBoxes.setWidget(0, 0, flexTableFiltrar);
        flexTableListBoxes.setWidget(0, 1, new InlineHTML("&nbsp;"));
        flexTableListBoxes.setWidget(0, 2, lblFilhosAssociados);
        flexTableListBoxes.setWidget(1, 0, multiBoxFilhosFiltrados);
        flexTableListBoxes.setWidget(1, 1, flexTableBotoes);
        flexTableListBoxes.setWidget(1, 2, multiBoxFilhosAssociados);
        flexTableListBoxes.setWidget(2, 0, new InlineHTML("&nbsp;"));
        flexTableListBoxes.setWidget(3, 0, drawPassoSubmeterFormFilhos());
        flexTableListBoxes.getFlexCellFormatter().setColSpan(3, 0, 3);
        flexTableListBoxes.getFlexCellFormatter().setAlignment(3, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(flexTableFiltroFilhosCurso);
        vPanel.add(flexTableListBoxes);
        vPanel.add(new InlineHTML("&nbsp;"));

        mpPanel.addPage(vPanel);

        return mpPanel;
    }    

    public VerticalPanel drawPassoSubmeterFormPais() {

        MpImageButton btnEnviarEmail = new MpImageButton(txtConstants.documentoEnviarContratoPorEmail(), "images/letter.png");
        btnEnviarEmail.addClickHandler(new PaisClickHandlerBotaoEnviarEmail());
//        MpImageButton btnImprimir = new MpImageButton(txtConstants.documentoImprimirContrato(), "images/Print.16");
//        btnImprimir.addClickHandler(new PaisClickHandlerBotaoImprimirContrato());

        Grid gridSave = new Grid(1, 2);
        gridSave.setCellSpacing(2);
        gridSave.setCellPadding(2);
        {
            int i = 0;
            gridSave.setWidget(0, i++, btnEnviarEmail);
//            gridSave.setWidget(0, i++, btnImprimir);
            gridSave.setWidget(0, i++, mpPanelLoadingSalvarPais);
        }

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(gridSave);

        return vPanel;
    }
    
    public VerticalPanel drawPassoSubmeterFormFilhos() {

        MpImageButton btnEnviarEmail = new MpImageButton(txtConstants.documentoEnviarContratoPorEmail() + " para os Pais", "images/letter.png");
        btnEnviarEmail.addClickHandler(new FilhosClickHandlerBotaoEnviarEmail());
//        MpImageButton btnImprimir = new MpImageButton(txtConstants.documentoImprimirContrato(), "images/Print.16");
//        btnImprimir.addClickHandler(new PaisClickHandlerBotaoImprimirContrato());

        Grid gridSave = new Grid(1, 2);
        gridSave.setCellSpacing(2);
        gridSave.setCellPadding(2);
        {
            int i = 0;
            gridSave.setWidget(0, i++, btnEnviarEmail);
//            gridSave.setWidget(0, i++, btnImprimir);
            gridSave.setWidget(0, i++, mpPanelLoadingSalvarFilhos);
        }

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(gridSave);

        return vPanel;
    }    

    /**************** Begin Event Handlers *****************/

    private class PaisClickHandlerBotaoEnviarEmail implements ClickHandler {

        public void onClick(ClickEvent event) {

            if (checkFieldsValidator()) {

                mpPanelLoadingSalvarPais.setVisible(true);

                int idCurso = Integer.parseInt(mpSelectionCursoPorPais.getValue(mpSelectionCursoPorPais.getSelectedIndex()));
                int idDocumento = Integer.parseInt(mpSelectionDocumentos.getValue(mpSelectionDocumentos.getSelectedIndex()));

                ArrayList<String> listIdPais = new ArrayList<String>();
                for (int i = 0; i < multiBoxPaisAssociados.getItemCount(); i++) {
                    listIdPais.add(multiBoxPaisAssociados.getValue(i));
                }
                String strAddress = GWT.getHostPageBaseURL();
                
                if(idCurso!=0){
                    GWTServiceDocumento.Util.getInstance().associarDocumentoUsuarios(idCurso, idDocumento,  TipoUsuario.PAIS, listIdPais, strAddress, new PaisClassCallbackEnviarEmail());
                }else{
                    GWTServiceDocumento.Util.getInstance().associarDocumentoTodosUsuarios(idDocumento, TipoUsuario.PAIS, listIdPais, new PaisClassCallbackEnviarEmail());
                }

            }

        }
    }
    
    private class FilhosClickHandlerBotaoEnviarEmail implements ClickHandler {

        public void onClick(ClickEvent event) {

            if (checkFieldsValidator()) {

                mpPanelLoadingSalvarFilhos.setVisible(true);

                int idCurso = Integer.parseInt(mpSelectionCursoPorFilhos.getValue(mpSelectionCursoPorFilhos.getSelectedIndex()));
                int idDocumento = Integer.parseInt(mpSelectionDocumentos.getValue(mpSelectionDocumentos.getSelectedIndex()));

                ArrayList<String> listIdFilhos = new ArrayList<String>();
                for (int i = 0; i < multiBoxFilhosAssociados.getItemCount(); i++) {
                    listIdFilhos.add(multiBoxFilhosAssociados.getValue(i));
                }
                String strAddress = GWT.getHostPageBaseURL();
                if(idCurso!=0){
                    GWTServiceDocumento.Util.getInstance().associarDocumentoUsuarios(idCurso, idDocumento, TipoUsuario.ALUNO, listIdFilhos,strAddress, new FilhosClassCallbackEnviarEmail());
                }else{
                    GWTServiceDocumento.Util.getInstance().associarDocumentoTodosUsuarios(idDocumento, TipoUsuario.ALUNO, listIdFilhos, new FilhosClassCallbackEnviarEmail());
                }

            }

        }
    }    
    
//    private class PaisClickHandlerBotaoImprimirContrato implements ClickHandler {
//
//        public void onClick(ClickEvent event) {
////            MpDialogBoxEnviarEmail.getInstance(); 
//
//
//        }
//    }    
    


    /**************** End Event Handlers *****************/

    public boolean checkFieldsValidator() {
        boolean isFieldsOk = false;
        isFieldsOk = true;

        // if(FieldVerifier.isValidName(txtNomePeriodo.getText())){
        // isFieldsOk=true;
        // lblErroNomePeriodo.hideErroMessage();
        // }
        // else{
        // isFieldsOk=false;
        // lblErroNomePeriodo.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.periodoNome()));
        // }

        return isFieldsOk;
    }


    public void updateClientData() {
        mpSelectionCursoPorPais.populateComboBox();
        mpSelectionCursoPorFilhos.populateComboBox();
    }

    private class ValueChangeHandlerFiltrarPaisPorCurso implements ValueChangeHandler<Boolean> {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
            if (event.getValue() == true) {
                mpPanelFiltrarPaisPorCurso.setVisible(true);
                mpPanelFiltrarFilhoPorCurso.setVisible(false);
            }
        }
    }

    private class ValueChangeHandlerFiltrarPaisPorNome implements ValueChangeHandler<Boolean> {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
            if (event.getValue() == true) {
                mpPanelFiltrarPaisPorCurso.setVisible(false);
                mpPanelFiltrarFilhoPorCurso.setVisible(true);
            }
        }
    }

    private void PaisFiltrarHandler() {
        mpPanelLoadingFiltrarCursoPais.setVisible(true);

        String strFilterResp = "";
        if (radioButtonRespAcademico.getValue()) {
            strFilterResp = " and resp_academico=true ";
        } else if (radioButtonRespFinanceiro.getValue()) {
            strFilterResp = " and resp_financeiro=true ";
        } else if (radioButtonRespTodos.getValue()) {
            strFilterResp = " ";
        }

        int idCurso = Integer.parseInt(mpSelectionCursoPorPais.getValue(mpSelectionCursoPorPais.getSelectedIndex()));
        String strFilterName = txtFiltroNomeDosPais.getText();

        if(idCurso==0){
            GWTServiceUsuario.Util.getInstance().getTodosPais(strFilterResp, strFilterName, new ClassCallbackFiltroPais());
        }else{
            GWTServiceUsuario.Util.getInstance().getPaisPorCurso(idCurso, strFilterResp, strFilterName, new ClassCallbackFiltroPais());    
        }
        
    }
    
    
    private void FilhosFiltrarHandler() {
        mpPanelLoadingFiltrarPais.setVisible(true);

        int idCurso = Integer.parseInt(mpSelectionCursoPorFilhos.getValue(mpSelectionCursoPorFilhos.getSelectedIndex()));
        String strFilterName = txtFiltroFilhos.getText();

        if(0==idCurso){
            GWTServiceUsuario.Util.getInstance().getUsuariosPorTipoUsuario(TipoUsuario.ALUNO, "%"+strFilterName+"%", new ClassCallbackFiltroFilhos());
        }else{
            GWTServiceUsuario.Util.getInstance().getAlunosPorCurso(idCurso, "%"+strFilterName+"%", new ClassCallbackFiltroFilhos());    
        }
        
    }    
    
    
    

    private class PaisClickHandlerFiltrar implements ClickHandler {
        public void onClick(ClickEvent event) {
            PaisFiltrarHandler();
        }
    }

    private class PaisEnterKeyUpHandlerFiltrar implements KeyUpHandler {
        public void onKeyUp(KeyUpEvent event) {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                PaisFiltrarHandler();
            }
        }
    }
    
    private class FilhosClickHandlerFiltrar implements ClickHandler {
        public void onClick(ClickEvent event) {
            FilhosFiltrarHandler();
        }
    }
    
    private class FilhosEnterKeyUpHandlerFiltrar implements KeyUpHandler {
        public void onKeyUp(KeyUpEvent event) {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                FilhosFiltrarHandler();
            }
        }
    }    

    private class PaisClickHandlerPaisParaDireita implements ClickHandler {
        public void onClick(ClickEvent event) {

            int i = 0;
            while (i < multiBoxPaisFiltrados.getItemCount()) {

                if (multiBoxPaisFiltrados.isItemSelected(i)) {
                    String value = multiBoxPaisFiltrados.getValue(multiBoxPaisFiltrados.getSelectedIndex());
                    String item = multiBoxPaisFiltrados.getItemText(multiBoxPaisFiltrados.getSelectedIndex());
                    if (!containsItem(multiBoxPaisAssociados, item)) {
                        multiBoxPaisAssociados.addItem(item, value);
                    }
                    multiBoxPaisFiltrados.removeItem(multiBoxPaisFiltrados.getSelectedIndex());
                    i = 0;
                    continue;
                }
                i++;
            }
        }
    }

    private class PaisClickHandlerPaisParaEsquerda implements ClickHandler {
        public void onClick(ClickEvent event) {

            int i = 0;
            while (i < multiBoxPaisAssociados.getItemCount()) {
                if (multiBoxPaisAssociados.isItemSelected(i)) {
                    String value = multiBoxPaisAssociados.getValue(multiBoxPaisAssociados.getSelectedIndex());
                    String item = multiBoxPaisAssociados.getItemText(multiBoxPaisAssociados.getSelectedIndex());
                    if (!containsItem(multiBoxPaisFiltrados, item)) {
                        multiBoxPaisFiltrados.addItem(item, value);
                    }
                    multiBoxPaisAssociados.removeItem(multiBoxPaisAssociados.getSelectedIndex());
                    i = 0;
                    continue;
                }
                i++;
            }
        }
    }
    
    private class FilhosClickHandlerPaisParaDireita implements ClickHandler {
        public void onClick(ClickEvent event) {

            int i = 0;
            while (i < multiBoxFilhosFiltrados.getItemCount()) {

                if (multiBoxFilhosFiltrados.isItemSelected(i)) {
                    String value = multiBoxFilhosFiltrados.getValue(multiBoxFilhosFiltrados.getSelectedIndex());
                    String item = multiBoxFilhosFiltrados.getItemText(multiBoxFilhosFiltrados.getSelectedIndex());
                    if (!containsItem(multiBoxFilhosAssociados, item)) {
                        multiBoxFilhosAssociados.addItem(item, value);
                    }
                    multiBoxFilhosFiltrados.removeItem(multiBoxFilhosFiltrados.getSelectedIndex());
                    i = 0;
                    continue;
                }
                i++;
            }
        }
    }

    private class FilhosClickHandlerPaisParaEsquerda implements ClickHandler {
        public void onClick(ClickEvent event) {

            int i = 0;
            while (i < multiBoxFilhosAssociados.getItemCount()) {
                if (multiBoxFilhosAssociados.isItemSelected(i)) {
                    String value = multiBoxFilhosAssociados.getValue(multiBoxFilhosAssociados.getSelectedIndex());
                    String item = multiBoxFilhosAssociados.getItemText(multiBoxFilhosAssociados.getSelectedIndex());
                    if (!containsItem(multiBoxFilhosFiltrados, item)) {
                        multiBoxFilhosFiltrados.addItem(item, value);
                    }
                    multiBoxFilhosAssociados.removeItem(multiBoxFilhosAssociados.getSelectedIndex());
                    i = 0;
                    continue;
                }
                i++;
            }
        }
    }    

    private boolean containsItem(ListBox listBox, String item) {
        boolean contain = false;

        for (int i = 0; i < listBox.getItemCount(); i++) {
            String strItem = listBox.getItemText(i);
            if (strItem.equals(item)) {
                contain = true;
                break;
            }
        }
        return contain;
    }

    private class ClassCallbackFiltroPais implements AsyncCallback<ArrayList<Usuario>> {

        @Override
        public void onFailure(Throwable caught) {
            disablePanelsLoading();
            MpDialogBoxRefreshPage mpDialogBoxRefreshPage = new MpDialogBoxRefreshPage();
            mpDialogBoxRefreshPage.showDialog();

        }

        @Override
        public void onSuccess(ArrayList<Usuario> result) {
            MpUtilClient.isRefreshRequired(result);
            // mpPanelLoadingFiltrarPais.setVisible(false);
            disablePanelsLoading();

            // Begin Cleaning fields
            multiBoxPaisFiltrados.clear();

            for (int i = 0; i < result.size(); i++) {
                Usuario usuario = result.get(i);
                multiBoxPaisFiltrados.addItem(usuario.getPrimeiroNome() + " " + usuario.getSobreNome(), Integer.toString(usuario.getIdUsuario()));
            }

        }

    }
    
    
    private class ClassCallbackFiltroFilhos implements AsyncCallback<ArrayList<Usuario>> {

        @Override
        public void onFailure(Throwable caught) {
            disablePanelsLoading();
            MpDialogBoxRefreshPage mpDialogBoxRefreshPage = new MpDialogBoxRefreshPage();
            mpDialogBoxRefreshPage.showDialog();

        }

        @Override
        public void onSuccess(ArrayList<Usuario> result) {
            MpUtilClient.isRefreshRequired(result);
            // mpPanelLoadingFiltrarPais.setVisible(false);
            disablePanelsLoading();

            // Begin Cleaning fields
            multiBoxFilhosFiltrados.clear();

            for (int i = 0; i < result.size(); i++) {
                Usuario usuario = result.get(i);
                multiBoxFilhosFiltrados.addItem(usuario.getPrimeiroNome() + " " + usuario.getSobreNome(), Integer.toString(usuario.getIdUsuario()));
            }

        }

    }    
    

    private void paisEventoFiltrarCurso() {
        multiBoxPaisFiltrados.clear();
        txtFiltroNomeDosPais.setText("");
        mpSelectionCursoPorPais.filterComboBox(txtFiltroCursosPais.getText());
        popularPaisAssociados();
    }
    
    private void filhosEventoFiltrarCurso() {
        multiBoxFilhosFiltrados.clear();
        txtFiltroFilhos.setText("");
        mpSelectionCursoPorFilhos.filterComboBox(txtFiltroCursosFilhos.getText());
        popularFilhosAssociados();
    }
    

    private class PaisClickHandlerFiltrarListBoxCurso implements ClickHandler {
        public void onClick(ClickEvent event) {
            paisEventoFiltrarCurso();
        }
    }

    private class PaisEnterKeyUpHandlerFiltrarListBox implements KeyUpHandler {
        public void onKeyUp(KeyUpEvent event) {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                paisEventoFiltrarCurso();
            }
        }
    }
    
    
    private class FilhosClickHandlerFiltrarListBoxCurso implements ClickHandler {
        public void onClick(ClickEvent event) {
            filhosEventoFiltrarCurso();
        }
    }

    private class FilhosEnterKeyUpHandlerFiltrarListBox implements KeyUpHandler {
        public void onKeyUp(KeyUpEvent event) {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                filhosEventoFiltrarCurso();
            }
        }
    }

    private void disablePanelsLoading() {
        mpPanelLoadingSalvarPais.setVisible(false);
        mpPanelLoadingFiltrarCursoPais.setVisible(false);
        mpPanelLoadingFiltrarPais.setVisible(false);
        mpPanelLoadingSalvarFilhos.setVisible(false);
        mpPanelLoadingFiltrarCursoFilhos.setVisible(false);
        mpPanelLoadingFiltrarFilhos.setVisible(false);
        
    }

    private void popularPaisAssociados() {
        if (mpSelectionCursoPorPais.getSelectedIndex() == -1) {
            disablePanelsLoading();
        } else {
            mpPanelLoadingFiltrarCursoPais.setVisible(true);
            int idCurso = Integer.parseInt(mpSelectionCursoPorPais.getValue(mpSelectionCursoPorPais.getSelectedIndex()));
            int idDocumento = Integer.parseInt(mpSelectionDocumentos.getValue(mpSelectionDocumentos.getSelectedIndex()));

            if(0==idCurso){
                GWTServiceDocumento.Util.getInstance().getRelDocumentoUsuariosPorTipoSemCurso(idDocumento, TipoUsuario.PAIS,new ClassCallbackGetPaisAssociados());
            }else{
                GWTServiceDocumento.Util.getInstance().getRelDocumentoUsuariosPorTipo(idCurso, idDocumento, TipoUsuario.PAIS,new ClassCallbackGetPaisAssociados());
            }
            
        }
    }
    
    
    private void popularFilhosAssociados() {
        if (mpSelectionCursoPorFilhos.getSelectedIndex() == -1) {
            disablePanelsLoading();
        } else {
            mpPanelLoadingFiltrarCursoPais.setVisible(true);
            int idCurso = Integer.parseInt(mpSelectionCursoPorFilhos.getValue(mpSelectionCursoPorFilhos.getSelectedIndex()));
            int idDocumento = Integer.parseInt(mpSelectionDocumentos.getValue(mpSelectionDocumentos.getSelectedIndex()));

            if(0==idCurso){
                GWTServiceDocumento.Util.getInstance().getRelDocumentoUsuariosPorTipoSemCurso(idDocumento, TipoUsuario.ALUNO,new ClassCallbackGetFilhosAssociados());
            }else{
                GWTServiceDocumento.Util.getInstance().getRelDocumentoUsuariosPorTipo(idCurso, idDocumento, TipoUsuario.ALUNO, new ClassCallbackGetFilhosAssociados());
            }
           
            
        }
    }
    

    private class ChangeHandlerPopularDocumentos implements ChangeHandler {
        public void onChange(ChangeEvent event) {

            mpSelectionCursoPorPais.populateComboBox();
            mpSelectionCursoPorFilhos.populateComboBox();
        }
    }

    private class ChangeHandlerPopularPaisAssociados implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            multiBoxPaisFiltrados.clear();
            txtFiltroNomeDosPais.setText("");
            popularPaisAssociados();
        }
    }
    
    private class ChangeHandlerPopularFilhosAssociados implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            multiBoxFilhosFiltrados.clear();
            txtFiltroFilhos.setText("");
            popularFilhosAssociados();
        }
    }    

    private class PaisClassCallbackEnviarEmail implements AsyncCallback<Boolean> {

        @Override
        public void onFailure(Throwable caught) {
            disablePanelsLoading();
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText(txtConstants.geralErro());
            mpDialogBoxWarning.showDialog();
        }

        @Override
        public void onSuccess(Boolean result) {
            disablePanelsLoading();
            if (result.booleanValue()) {
                mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
                mpDialogBoxConfirm.setBodyText(txtConstants.documentoEmailNovosPais());
                mpDialogBoxConfirm.showDialog();
                
                enviarEmailPais();
                
            } else {
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroAssociarPaisAluno());
                mpDialogBoxWarning.showDialog();

            }
        }
    }
    
    private class FilhosClassCallbackEnviarEmail implements AsyncCallback<Boolean> {

        @Override
        public void onFailure(Throwable caught) {
            disablePanelsLoading();
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText(txtConstants.geralErro());
            mpDialogBoxWarning.showDialog();
        }

        @Override
        public void onSuccess(Boolean result) {
            disablePanelsLoading();
            if (result.booleanValue()) {
                mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
                mpDialogBoxConfirm.setBodyText(txtConstants.documentoEmailNovosPais());
                mpDialogBoxConfirm.showDialog();
                
                enviarEmailFilhos();
                
            } else {
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroAssociarPaisAluno());
                mpDialogBoxWarning.showDialog();

            }
        }
    }    
    
    private void enviarEmailPais(){
        int idCurso = Integer.parseInt(mpSelectionCursoPorPais.getValue(mpSelectionCursoPorPais.getSelectedIndex()));
        int idDocumento = Integer.parseInt(mpSelectionDocumentos.getValue(mpSelectionDocumentos.getSelectedIndex()));

        ArrayList<String> listIdPais = new ArrayList<String>();
        for (int i = 0; i < multiBoxPaisAssociados.getItemCount(); i++) {
            listIdPais.add(multiBoxPaisAssociados.getValue(i));
        }
        String strAddress = GWT.getHostPageBaseURL();
        
        if(0==idCurso){
            GWTServiceDocumento.Util.getInstance().enviarEmailDocumentoSemCurso(idDocumento, TipoUsuario.PAIS, listIdPais, strAddress, new classCallbackEmails());
        }else{
            GWTServiceDocumento.Util.getInstance().enviarEmailDocumento(idCurso, idDocumento, TipoUsuario.PAIS, listIdPais, strAddress, new classCallbackEmails());    
        }
        
    }
    
    private void enviarEmailFilhos(){
        int idCurso = Integer.parseInt(mpSelectionCursoPorFilhos.getValue(mpSelectionCursoPorFilhos.getSelectedIndex()));
        int idDocumento = Integer.parseInt(mpSelectionDocumentos.getValue(mpSelectionDocumentos.getSelectedIndex()));

        ArrayList<String> listIdFilhos = new ArrayList<String>();
        for (int i = 0; i < multiBoxFilhosAssociados.getItemCount(); i++) {
            listIdFilhos.add(multiBoxFilhosAssociados.getValue(i));
        }
        String strAddress = GWT.getHostPageBaseURL();
        if(0==idCurso){
            GWTServiceDocumento.Util.getInstance().enviarEmailDocumentoSemCurso(idDocumento, TipoUsuario.ALUNO, listIdFilhos, strAddress, new classCallbackEmails());
        }else{
            GWTServiceDocumento.Util.getInstance().enviarEmailDocumento(idCurso, idDocumento, TipoUsuario.ALUNO, listIdFilhos, strAddress, new classCallbackEmails());    
        }
        
    }    

    private class ClassCallbackGetPaisAssociados implements AsyncCallback<ArrayList<RelDocumentoUsuario>> {

        @Override
        public void onFailure(Throwable caught) {
            disablePanelsLoading();
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText(txtConstants.geralErroCarregarDados());
            mpDialogBoxWarning.showDialog();
        }

        @Override
        public void onSuccess(ArrayList<RelDocumentoUsuario> result) {
            MpUtilClient.isRefreshRequired(result);
            disablePanelsLoading();
            // Begin Cleaning fields
            multiBoxPaisAssociados.clear();
            listAuxUsuarioPais.clear();

            // End Cleaning fields

            for (int i = 0; i < result.size(); i++) {
                Usuario usuario = result.get(i).getUsuario();
                multiBoxPaisAssociados.addItem(usuario.getPrimeiroNome() + " " + usuario.getSobreNome(), Integer.toString(usuario.getIdUsuario()));
                listAuxUsuarioPais.add(usuario);
            }

        }

    }
    
    private class ClassCallbackGetFilhosAssociados implements AsyncCallback<ArrayList<RelDocumentoUsuario>> {

        @Override
        public void onFailure(Throwable caught) {
            disablePanelsLoading();
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText(txtConstants.geralErroCarregarDados());
            mpDialogBoxWarning.showDialog();
        }

        @Override
        public void onSuccess(ArrayList<RelDocumentoUsuario> result) {
            MpUtilClient.isRefreshRequired(result);
            disablePanelsLoading();
            // Begin Cleaning fields
            multiBoxFilhosAssociados.clear();
            listAuxUsuarioFilhos.clear();

            // End Cleaning fields

            for (int i = 0; i < result.size(); i++) {
                Usuario usuario = result.get(i).getUsuario();
                multiBoxFilhosAssociados.addItem(usuario.getPrimeiroNome() + " " + usuario.getSobreNome(), Integer.toString(usuario.getIdUsuario()));
                listAuxUsuarioFilhos.add(usuario);
            }

        }

    }    
    
    private class classCallbackEmails implements AsyncCallback<String> {

        @Override
        public void onFailure(Throwable caught) {
            disablePanelsLoading();
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText(txtConstants.documentoErroEmail());
            mpDialogBoxWarning.showDialog();
        }

        @Override
        public void onSuccess(String result) {
            MpUtilClient.isRefreshRequired(result);
            disablePanelsLoading();
            // Begin Cleaning fields
            
            if(result.equals("success")){
                
            }else{
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.documentoErroEmail()+" "+result);
                mpDialogBoxWarning.showDialog();                
            }


        }

    }    
}
