package com.jornada.client.ambiente.coordenador.comunicado;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.PreloadedImage;
import gwtupload.client.PreloadedImage.OnLoadPreloadedImageHandler;
import gwtupload.client.SingleUploader;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.jornada.client.classes.listBoxes.MpSelectionTipoComunicado;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.MpListBoxDocumentos;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.multibox.MultiBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpPanelPageMainView;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.classes.widgets.textbox.MpRichTextArea;
import com.jornada.client.classes.widgets.timepicker.MpTimePicker;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceComunicado;
import com.jornada.client.service.GWTServiceEmail;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Comunicado;
import com.jornada.shared.classes.TipoComunicado;
import com.jornada.shared.classes.utility.MpUtilClient;

public class FormularioComunicado extends VerticalPanel {

    static TextConstants txtConstants = GWT.create(TextConstants.class);

    private static final String ATUALIZAR = txtConstants.comunicadoAtualizar();
    private static final String ADICIONAR = txtConstants.comunicadoAdicionar();

    private VerticalPanel vBodyPanel;
    private AsyncCallback<Boolean> callbackAddComunicado;

    private MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
    private MpDialogBox mpDialogBoxWarning = new MpDialogBox();
    private MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");

    private String strNomeImagem = "";

    private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
    private HashMap<String, Integer> emailList = new HashMap<String, Integer>();

    private MultiBox multiBox;
    private TextBox txtAssunto;
    private MpRichTextArea mpRichTextDescricao;
    private MpDateBoxWithImage mpDateBoxData;
    private MpTimePicker mpTimePicker;
    private MpSelectionTipoComunicado listBoxTipoComunicados;

    private Label lblFromEmail;

    private MpImageButton btnAdicionarComunicado;
    private MpImageButton btnAtualizarComunicado;

    // private MpLabelTextBoxError lblErroComunicado;

    private Comunicado comunicado;

    private TelaInicialComunicado telaInicialComunicado;

    private static FormularioComunicado uniqueInstance;

    private FlowPanel panelImages = new FlowPanel();
    
    private FlexTable flexTableCampoEmail;
    
    private MpPanelPageMainView drawPassoDoisSelecionarImagem;
    private MpPanelPageMainView drawPassoDoisEscolherDocumento;
    
    private MpListBoxDocumentos mpListBoxDocumentos;
    private RadioButton radioButtonYes;
    private RadioButton radioButtonNo;
//    private Grid gridAnexarDocumento;
    
    // private PreloadedImage preloadedImage;

    public static FormularioComunicado getInstance(TelaInicialComunicado telaInicialComunicado) {
        if (uniqueInstance == null) {
            uniqueInstance = new FormularioComunicado(telaInicialComunicado);
        }
        return uniqueInstance;
    }

    private FormularioComunicado(TelaInicialComunicado telaInicialComunicado) {

        this.telaInicialComunicado = telaInicialComunicado;

        mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
        mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
        mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
        mpPanelLoading.show();
        mpPanelLoading.setVisible(false);
        
        mpListBoxDocumentos = new MpListBoxDocumentos();

        vBodyPanel = new VerticalPanel();

        MpImageButton btnRetornarTelaAnterior = new MpImageButton(txtConstants.comunicadoRetornarTela(), "images/previousFolder.png");
        btnRetornarTelaAnterior.addClickHandler(new ClickHandlerCancelar());
        
        drawPassoDoisSelecionarImagem = drawPassoDoisSelecionarImagem();
        drawPassoDoisEscolherDocumento = drawPassoDoisEscolherDocumento();
        
        vBodyPanel.add(btnRetornarTelaAnterior);
        vBodyPanel.add(new MpSpaceVerticalPanel());
        vBodyPanel.add(drawPassoUmCamposTitulo());
        vBodyPanel.add(new MpSpaceVerticalPanel());
        vBodyPanel.add(drawPassoDoisSelecionarImagem);
        vBodyPanel.add(drawPassoDoisEscolherDocumento);
        vBodyPanel.add(new MpSpaceVerticalPanel());
        vBodyPanel.add(drawPassoTresDescricao());
        vBodyPanel.add(new MpSpaceVerticalPanel());
        vBodyPanel.add(drawPassoQuatroSubmeterComunicado());
        vBodyPanel.setWidth("100%");
        vBodyPanel.setBorderWidth(0);

        this.setWidth("100%");
        super.add(vBodyPanel);
    }

    @SuppressWarnings("deprecation")
    public MpPanelPageMainView drawPassoUmCamposTitulo() {

        MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.comunicadoInsiraComunicado(), "/images/insert_header.png");
        mpPanel.setWidth("100%");

        // Add a title to the form
        txtAssunto = new TextBox();

        multiBox = new MultiBox(oracle);

        mpDateBoxData = new MpDateBoxWithImage();
        mpDateBoxData.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
        txtAssunto.setStyleName("design_text_boxes");
        txtAssunto.setTitle(txtConstants.geralCampoObrigatorio(txtConstants.comunicadoAssunto()));

        Label lblAssunto = new Label(txtConstants.comunicadoAssunto());
        Label lblTipoComunicado = new Label(txtConstants.comunicadoTipo());
        lblFromEmail = new Label("Para");
//        lblFromEmail.setVisible(false);
        Label lblDataInicial = new Label(txtConstants.comunicadoData());
        Label lblHora = new Label(txtConstants.comunicadoHora());
        
        
        
        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.setHeight("100px");
        scrollPanel.setWidth("100%");
        scrollPanel.setAlwaysShowScrollBars(false);
        scrollPanel.add(multiBox);
        
        flexTableCampoEmail = new FlexTable();
        flexTableCampoEmail.setVisible(false);
        flexTableCampoEmail.setBorderWidth(0);
        flexTableCampoEmail.setWidget(0, 0, lblFromEmail);
        flexTableCampoEmail.setWidget(0, 1, scrollPanel);
        flexTableCampoEmail.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);

        listBoxTipoComunicados = new MpSelectionTipoComunicado();
        listBoxTipoComunicados.setStyleName("design_text_boxes");
        listBoxTipoComunicados.addChangeHandler(new CliclHandlerTipoComunicado());

        lblAssunto.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        lblTipoComunicado.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        lblFromEmail.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        lblDataInicial.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        lblHora.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

        lblAssunto.setStyleName("design_label");
        lblTipoComunicado.setStyleName("design_label");
        lblDataInicial.setStyleName("design_label");
        lblHora.setStyleName("design_label");

        txtAssunto.setWidth("350px");
        mpDateBoxData.getDate().setWidth("200px");

        mpTimePicker = new MpTimePicker(7, 22);


        FlexTable gridCommunicateType = new FlexTable();
        gridCommunicateType.setCellSpacing(3);
        gridCommunicateType.setCellPadding(3);
        gridCommunicateType.setBorderWidth(0);

        int row = 0;

        gridCommunicateType.setWidget(row, 0, lblTipoComunicado);
        gridCommunicateType.setWidget(row, 1, listBoxTipoComunicados);
        gridCommunicateType.setWidget(row++, 2, new InlineHTML("&nbsp"));
        // gridCommunicateType.setWidget(row, 3, lblFromEmail);
        // gridCommunicateType.setWidget(row++, 4, multiBox);
        gridCommunicateType.setWidget(row, 0, lblAssunto);
        gridCommunicateType.setWidget(row++, 1, txtAssunto);
        // gridCommunicateType.setWidget(row++, 2, lblErroComunicado);
        gridCommunicateType.setWidget(row, 0, lblDataInicial);
        gridCommunicateType.setWidget(row++, 1, mpDateBoxData);
        gridCommunicateType.setWidget(row, 0, lblHora);
        gridCommunicateType.setWidget(row++, 1, mpTimePicker);
        // gridCommunicateType.setWidget(row, 0,
        // lblFromEmail);gridCommunicateType.setWidget(row++, 1, scrollPanel);

        row = 0;
        gridCommunicateType.setWidget(row, 3, flexTableCampoEmail);
        gridCommunicateType.getFlexCellFormatter().setRowSpan(row, 3, 4);
        gridCommunicateType.getFlexCellFormatter().setVerticalAlignment(row, 3, HasVerticalAlignment.ALIGN_TOP);

        // vPanelCommunicateType.add(gridCommunicateType);

        // VerticalPanel emailPanel = new VerticalPanel();
        //
        // Grid emailGrid = new Grid(4, 3);
        // emailGrid.setCellSpacing(3);
        // emailGrid.setCellPadding(3);
        // emailGrid.setBorderWidth(1);
        // emailGrid.setSize(
        // Integer.toString((TelaInicialComunicado.intWidthTable) / 2),
        // Integer.toString(TelaInicialComunicado.intHeightTable));
        //
        // row = 0;
        //
        // emailGrid.setWidget(row, 0, lblFromEmail);
        // emailGrid.setWidget(row, 1, multiBox);
        // emailPanel.add(emailGrid);

        // Grid mainGrid = new Grid(1, 2);
        // mainGrid.setCellSpacing(3);
        // mainGrid.setCellPadding(3);
        // mainGrid.setBorderWidth(1);
        // mainGrid.setSize(Integer.toString(TelaInicialComunicado.intWidthTable),
        // Integer.toString(TelaInicialComunicado.intHeightTable));
        //
        // row = 0;
        //
        // mainGrid.setWidget(row, 0, vPanelCommunicateType);
        // mainGrid.setWidget(row, 1, emailPanel);

        // ScrollPanel scrollPanel = new ScrollPanel();
        // scrollPanel.setHeight( "200px");
        // scrollPanel.setWidth("100%");
        // scrollPanel.setAlwaysShowScrollBars(false);
        // scrollPanel.add(gridCommunicateType);

        // mpPanel.add(scrollPanel);
        mpPanel.add(gridCommunicateType);

        return mpPanel;

    }

    public MpPanelPageMainView drawPassoDoisSelecionarImagem() {

        MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.comunicadoSelecionaImagem(), "images/picture_16.png");
        // mpPanel.setWidth(Integer.toString(TelaInicialDisciplina.intWidthTable)+"px");
        mpPanel.setWidth("100%");
        // mpPanel.setWidth(Integer.toString(TelaInicialDisciplina.intWidthTable)
        // + "px");
        mpPanel.setHeight("70px");

        Grid grid = new Grid(1, 3);
        grid.setCellSpacing(3);
        grid.setCellPadding(3);
        grid.setBorderWidth(0);
        // grid.setSize(Integer.toString(TelaInicialComunicado.intWidthTable),Integer.toString(TelaInicialComunicado.intHeightTable));
        // grid.setHeight(Integer.toString(TelaInicialComunicado.intHeightTable)+"px");
        grid.setWidth("100%");

        Grid vPanelImagem = new Grid(1, 2);
        vPanelImagem.setSize("15px", "73px");

        MpImageButton mpButton = new MpImageButton(txtConstants.comunicadoEscolherImagem(), "images/image_16.png");

        SingleUploader singleUploader = new SingleUploader(FileInputType.CUSTOM.with(mpButton));

        singleUploader.setAutoSubmit(true);
        singleUploader.setValidExtensions("jpg", "gif", "png");
        singleUploader.addOnFinishUploadHandler(onFinishUploaderHandler);

        vPanelImagem.setWidget(0, 0, singleUploader);
        vPanelImagem.setWidget(0, 1, panelImages);

        mpPanel.add(vPanelImagem);

        return mpPanel;

    }

    public MpPanelPageMainView drawPassoDoisEscolherDocumento() {

        
        MpPanelPageMainView mpPanel = new MpPanelPageMainView("Por favor, selecione o documento caso precisar", "images/doc_google_docs.16.png");
        // mpPanel.setWidth(Integer.toString(TelaInicialDisciplina.intWidthTable)+"px");
        mpPanel.setWidth("100%");
        mpPanel.setHeight("100px");

        Grid vPanelImagem = new Grid(1, 1);

        radioButtonYes = new RadioButton("useTemplate",txtConstants.geralSim());
        radioButtonYes.addValueChangeHandler(new ValueChangeHandlerYes());
        radioButtonNo = new RadioButton("useTemplate", txtConstants.geralNao());
        radioButtonNo.addValueChangeHandler(new ValueChangeHandlerNo());
        radioButtonNo.setValue(true);
        mpListBoxDocumentos.setVisible(false);
        Label lblAnexarDocumento = new Label("Enviar Documento?");
        lblAnexarDocumento.setStyleName("design_label");
        
         
        
        
        Grid gridDesejaAnexar = new Grid(1,5);   
        gridDesejaAnexar.setCellPadding(2);
        gridDesejaAnexar.setCellSpacing(2);        
        
        int row=0;
        gridDesejaAnexar.setWidget(row, 0, lblAnexarDocumento);
        gridDesejaAnexar.setWidget(row, 1, radioButtonYes);
        gridDesejaAnexar.setWidget(row, 2, radioButtonNo);
        gridDesejaAnexar.setWidget(row, 3, new InlineHTML("&nbsp;"));
        gridDesejaAnexar.setWidget(row, 4, mpListBoxDocumentos);
        
        
        
        

        
        



        vPanelImagem.setWidget(0, 0, gridDesejaAnexar);
//        vPanelImagem.setWidget(1, 0, gridAnexarDocumento);
//        vPanelImagem.setWidget(0, 1, panelImages);

        mpPanel.add(vPanelImagem);

        return mpPanel;

    }    
    
    public MpPanelPageMainView drawPassoTresDescricao() {

        MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.comunicadoInsiraDescricao(), "images/note_2add.png");
        // mpPanel.setWidth(Integer.toString(TelaInicialDisciplina.intWidthTable)+"px");
        mpPanel.setWidth("100%");
        // mpPanel.setWidth(Integer.toString(TelaInicialDisciplina.intWidthTable)
        // + "px");

        Grid grid = new Grid(1, 2);
        grid.setCellSpacing(3);
        grid.setCellPadding(3);
        grid.setBorderWidth(0);
        // grid.setSize(Integer.toString(TelaInicialComunicado.intWidthTable),Integer.toString(TelaInicialComunicado.intHeightTable));
        // grid.setHeight(Integer.toString(TelaInicialComunicado.intHeightTable)+"px");
        // grid.setWidth("100%");

        Label lblDescricaoCurso = new Label(txtConstants.cursoDescricao());
        lblDescricaoCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        lblDescricaoCurso.setStyleName("design_label");

        mpRichTextDescricao = new MpRichTextArea();

        // Add some standard form options
        int row = 0;
        grid.setWidget(row, 0, lblDescricaoCurso);
        grid.setWidget(row++, 1, mpRichTextDescricao);

        mpPanel.add(grid);

        return mpPanel;

    }

    public VerticalPanel drawPassoQuatroSubmeterComunicado() {

        FlexTable flexTable = new FlexTable();
        flexTable.setCellSpacing(2);
        flexTable.setCellPadding(2);
        flexTable.setBorderWidth(0);
        // flexTable.setWidth("100%");

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setWidth("100%");
        vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        // vPanel.setWidth(Integer.toString(TelaInicialDisciplina.intWidthTable)+"px");
        // + "px");

        btnAdicionarComunicado = new MpImageButton(ADICIONAR, "images/save.png");
        btnAdicionarComunicado.addClickHandler(new ClickHandlerSave());
        btnAdicionarComunicado.setVisible(true);

        btnAtualizarComunicado = new MpImageButton(ATUALIZAR, "images/save.png");
        btnAtualizarComunicado.addClickHandler(new ClickHandlerSave());
        btnAtualizarComunicado.setVisible(false);

        MpImageButton btnClean = new MpImageButton(txtConstants.geralLimpar(), "images/erase.png");
        btnClean.addClickHandler(new ClickHandlerClean());

        MpImageButton btnCancelarComunicado = new MpImageButton(txtConstants.geralCancelar(), "images/cross-circle-frame.png");
        btnCancelarComunicado.addClickHandler(new ClickHandlerCancelar());

        flexTable.setWidget(0, 0, btnAdicionarComunicado);
        flexTable.setWidget(0, 1, btnAtualizarComunicado);
        flexTable.setWidget(0, 2, btnClean);
        flexTable.setWidget(0, 3, btnCancelarComunicado);
        flexTable.setWidget(0, 4, mpPanelLoading);
        flexTable.setWidget(0, 5, new InlineHTML("&nbsp;"));

        vPanel.add(flexTable);
        vPanel.add(new InlineHTML("&nbsp;"));

        /*********************** Begin Callbacks **********************/

        callbackAddComunicado = new AsyncCallback<Boolean>() {

            public void onFailure(Throwable caught) {
                mpPanelLoading.setVisible(false);
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.comunicadoErroSalvar());
                mpDialogBoxWarning.showDialog();
            }

            @Override
            public void onSuccess(Boolean result) {
                mpPanelLoading.setVisible(false);
                boolean isSuccess = result;
                if (isSuccess) {

                    if (btnAdicionarComunicado.isVisible()) {
                        cleanFields();
                    }
                    mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
                    mpDialogBoxConfirm.setBodyText(txtConstants.comunicadoSalvo());
                    mpDialogBoxConfirm.showDialog();
                    telaInicialComunicado.populateGrid();

                } else {
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText(txtConstants.comunicadoErroSalvar());
                    mpDialogBoxWarning.showDialog();
                }
            }
        };

        GWTServiceEmail.Util.getInstance().getUsersIdlList(new AsyncCallback<HashMap<String, Integer>>() {

            public void onSuccess(HashMap<String, Integer> UserEmailList) {
                emailList = UserEmailList;
                for (String key : UserEmailList.keySet()) {
                    oracle.add(key);
                }

            }

            public void onFailure(Throwable cautch) {

            }
        });

        /*********************** End Callbacks **********************/

        return vPanel;
    }

    /**************** Begin Event Handlers *****************/

    private class ClickHandlerSave implements ClickHandler {

        public void onClick(ClickEvent event) {


            if (checkFieldsValidator()) {

                mpPanelLoading.setVisible(true);

                String strHora = mpTimePicker.getValue(mpTimePicker.getSelectedIndex());

                int intIdTipoComunicado = Integer.parseInt(listBoxTipoComunicados.getValue(listBoxTipoComunicados.getSelectedIndex()));

                Comunicado object = new Comunicado();
                
                object.setAssunto(txtAssunto.getText());
                object.setData(mpDateBoxData.getDate().getValue());
                object.setHora(strHora);
                object.setIdTipoComunicado(intIdTipoComunicado);
                object.setNomeImagem(strNomeImagem);                                
                if (object.getIdTipoComunicado() == TipoComunicado.EMAIL && radioButtonYes.getValue()) {
                    String strDoc =  GWT.getHostPageBaseURL()+mpListBoxDocumentos.getValue(mpListBoxDocumentos.getSelectedIndex());
//                    String strEmail = 
                    SafeHtml s_safe = SafeHtmlUtils.fromSafeConstant(mpRichTextDescricao.getTextArea().getHTML() + "<br><a href='"+strDoc+"?user=parameter' target='_blank'>Click aqui para baixar o documento.</a>");
                    RichTextArea rich = new RichTextArea();
                    rich.setHTML(s_safe);
                    mpRichTextDescricao.setTextArea(rich);
                }
                object.setDescricao(mpRichTextDescricao.getTextArea().getHTML());


                ArrayList<Integer> userEmailList = new ArrayList<Integer>();

                if (object.getIdTipoComunicado() == TipoComunicado.EMAIL) {
                    String[] userList = multiBox.getList().getElement().getString().split(" x ");

                    for (int i = 0; i < userList.length - 1; i++) {
                        String key = userList[i].replace("<ul class=\"multiValueSuggestBox-list\">", "").replace("<li class=\"multiValueSuggestBox-token\">", "").replace("<p>", "").replace("</p>", "").replace("<span>", "").replace("</span>", "").replace("</li>", "");

                        userEmailList.add(emailList.get(key));
                    }

                    sendMailByUserId(userEmailList, txtAssunto.getText(), mpRichTextDescricao.getTextArea().getHTML().toString());
                }

                if (btnAdicionarComunicado.isVisible()) {
                    GWTServiceComunicado.Util.getInstance().AdicionarComunicado(object, userEmailList, callbackAddComunicado);
                } else if (btnAtualizarComunicado.isVisible()) {
                    object.setIdComunicado(comunicado.getIdComunicado());
                    GWTServiceComunicado.Util.getInstance().AtualizarComunicado(object, userEmailList, callbackAddComunicado);
                }
            }

        }
    }

    private class ClickHandlerCancelar implements ClickHandler {
        public void onClick(ClickEvent event) {
            cleanFields();
            telaInicialComunicado.openTabelaComunicado();
        }
    }

    private class ClickHandlerClean implements ClickHandler {
        public void onClick(ClickEvent event) {
            cleanFields();
        }
    }

    /**************** End Event Handlers *****************/

    public Comunicado getComunicado() {
        return comunicado;
    }

    public void setComunicado(Comunicado comunicado) {
        this.comunicado = comunicado;
    }

    public void populateGridComunicados() {

    }

    public void openFormularioParaAtualizar(Comunicado comunicado) {

        setComunicado(comunicado);

        multiBox.clearList();

        GWTServiceEmail.Util.getInstance().getComucidadoEmailList(comunicado, new AsyncCallback<ArrayList<String>>() {

            public void onSuccess(ArrayList<String> userNameList) {
                for (String userName : userNameList) {
                    multiBox.addItem(userName);
                }
            }

            public void onFailure(Throwable cautch) {

            }
        });

        btnAtualizarComunicado.setVisible(true);
        btnAdicionarComunicado.setVisible(false);

        txtAssunto.setText(this.comunicado.getAssunto());
        mpRichTextDescricao.getTextArea().setHTML(this.comunicado.getDescricao());

        mpDateBoxData.getDate().setValue(this.comunicado.getData());

        strNomeImagem = this.comunicado.getNomeImagem();
        new PreloadedImage("images/download/" + strNomeImagem, showImage);

        // Date horaComunicadoDB = new
        // java.util.Date(this.comunicado.getHora());

        String strHoraDB = MpUtilClient.getHourFromString(this.comunicado.getHora());
        String strMinutosDB = MpUtilClient.getMinutesFromString(this.comunicado.getHora());
        System.out.println("Hora:" + strHoraDB);
        System.out.println("Minutos:" + strMinutosDB);

        // Selecting Item listBoxTime
        for (int i = 0; i < mpTimePicker.getItemCount(); i++) {
            String strHora = mpTimePicker.getValue(i);
            String strHoraListBox = strHora.substring(0, strHora.indexOf(":"));
            String strMinutosListBox = strHora.substring(strHora.lastIndexOf(":") + 1, strHora.lastIndexOf(":") + 3);

            int intHoraDB = Integer.parseInt(strHoraDB);
            int intMinutoDB = Integer.parseInt(strMinutosDB);
            int intHoraListBox = Integer.parseInt(strHoraListBox);
            int intMinutoListBox = Integer.parseInt(strMinutosListBox);

            if ((intHoraDB == intHoraListBox) && (intMinutoDB == intMinutoListBox)) {

                mpTimePicker.setSelectedIndex(i);
            }

        }

        // Selecting Item listBoxComunicados
        for (int i = 0; i < listBoxTipoComunicados.getItemCount(); i++) {
            if (this.comunicado.getIdTipoComunicado() == Integer.parseInt(listBoxTipoComunicados.getValue(i))) {
                listBoxTipoComunicados.setSelectedIndex(i);
            }
        }
        
        if(comunicado.getIdTipoComunicado()==TipoComunicado.EMAIL){
            flexTableCampoEmail.setVisible(true);
            drawPassoDoisEscolherDocumento.setVisible(true);
            drawPassoDoisSelecionarImagem.setVisible(false);
        }else{
            flexTableCampoEmail.setVisible(false);
            drawPassoDoisEscolherDocumento.setVisible(false);
            drawPassoDoisSelecionarImagem.setVisible(true);            
        }

        setVisible(true);
    }

    public void openFormularioParaAdicionar() {
        cleanFields();
        btnAdicionarComunicado.setVisible(true);
        btnAtualizarComunicado.setVisible(false);
        setVisible(true);
    }

    // Load the image in the document and in the case of success attach it to
    // the viewer
    private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
        @SuppressWarnings("deprecation")
        public void onFinish(IUploader uploader) {
            if (uploader.getStatus() == Status.SUCCESS) {

                new PreloadedImage(uploader.fileUrl(), showImage);

                // The server sends useful information to the client by default
                UploadedInfo info = uploader.getServerInfo();

                strNomeImagem = uploader.getServerMessage().getMessage();

                System.out.println("uploader.getFileInput().getName() " + uploader.getFileInput().getName());
                System.out.println("File name " + info.name);
                System.out.println("File content-type " + info.ctype);
                System.out.println("File size " + info.size);

                // You can send any customized message and parse it
                System.out.println("Server message: " + uploader.getServerMessage().getMessage());
                // System.out.println("Server message " + info.message);
            }
        }
    };

    // Attach an image to the pictures viewer
    private OnLoadPreloadedImageHandler showImage = new OnLoadPreloadedImageHandler() {
        public void onLoad(PreloadedImage image) {
            image.setWidth("64px");
            image.setHeight("64px");
            panelImages.clear();
            panelImages.add(image);
        }
    };

    public boolean checkFieldsValidator() {

        boolean isFieldsOk = false;
        boolean isAssuntoComunicadoOk = false;

        if (FieldVerifier.isValidName(txtAssunto.getText())) {
            isAssuntoComunicadoOk = true;
            // lblErroComunicado.hideErroMessage();
            txtAssunto.setStyleName("design_text_boxes");
        } else {
            isAssuntoComunicadoOk = false;
            // lblErroComunicado.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.comunicadoAssunto()));
            txtAssunto.setStyleName("design_text_boxes_erro");
        }

        isFieldsOk = isAssuntoComunicadoOk;

        return isFieldsOk;
    }

    public void cleanFields() {
        // btnAdicionarComunicado.setText(ADICIONAR);
        // lblErroComunicado.hideErroMessage();
        txtAssunto.setStyleName("design_text_boxes");
        this.comunicado = null;
        txtAssunto.setText("");
        mpRichTextDescricao.getTextArea().setHTML("");
        mpDateBoxData.getDate().setValue(null);
        mpTimePicker.setSelectedIndex(0);
        listBoxTipoComunicados.setSelectedIndex(0);
        strNomeImagem = "";
        panelImages.clear();
    }

    private void sendMailByUserId(ArrayList<Integer> userList, String subject, String content) {
        GWTServiceEmail.Util.getInstance().sendMailByUserId(userList, subject, content, new AsyncCallback<Boolean>() {
            public void onSuccess(Boolean state) {
            }

            public void onFailure(Throwable cautch) {
                System.out.println("Error:FormularioComunicado.sendMailByUserId()");

            }
        });
    }
    
    
    private class ValueChangeHandlerYes implements ValueChangeHandler<Boolean>{
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
            if(event.getValue() == true){
                  // gridAnexarDocumento.getRowFormatter().setVisible(0, true);
                mpListBoxDocumentos.setVisible(true);
            }           
        }
    }
    
    private class ValueChangeHandlerNo implements ValueChangeHandler<Boolean>{
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
            if(event.getValue() == true){
 
                mpListBoxDocumentos.setVisible(false);
            }           
        }
    }    
    
    
    private class CliclHandlerTipoComunicado implements ChangeHandler{
        @Override
        public void onChange(ChangeEvent event) {
            int intIdTipoComunicado = Integer.parseInt(listBoxTipoComunicados.getValue(listBoxTipoComunicados.getSelectedIndex()));
//            if (listBoxTipoComunicados.getItemText((listBoxTipoComunicados.getSelectedIndex())).equals("Comunicado Usuarios")) {
            if(intIdTipoComunicado==TipoComunicado.EMAIL){
                flexTableCampoEmail.setVisible(true);
                drawPassoDoisEscolherDocumento.setVisible(true);
                drawPassoDoisSelecionarImagem.setVisible(false);
//                lblFromEmail.setVisible(true);
//                multiBox.setVisible(true);
            } else {
                flexTableCampoEmail.setVisible(false);
                drawPassoDoisEscolherDocumento.setVisible(false);
                drawPassoDoisSelecionarImagem.setVisible(true);

//                lblFromEmail.setVisible(false);
//                multiBox.setVisible(false);
            }

        }

    }
}

