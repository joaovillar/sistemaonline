package com.jornada.client.ambiente.coordenador.comunicado;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.PreloadedImage;
import gwtupload.client.PreloadedImage.OnLoadPreloadedImageHandler;
import gwtupload.client.SingleUploader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.jornada.client.ambiente.coordenador.disciplina.TelaInicialDisciplina;
import com.jornada.client.classes.listBoxes.MpSelectionTipoComunicado;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelTextBoxError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpPanelPageMainView;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.classes.widgets.textbox.MpRichTextArea;
import com.jornada.client.classes.widgets.timepicker.MpTimePicker;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceComunicado;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Comunicado;
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
	
	private String strNomeImagem="";

	private TextBox txtAssunto;
	private MpRichTextArea mpRichTextDescricao;
	private MpDateBoxWithImage mpDateBoxData;
	private MpTimePicker mpTimePicker;
	private MpSelectionTipoComunicado listBoxTipoComunicados;
	
	private MpImageButton btnAdicionarComunicado;
	private MpImageButton btnAtualizarComunicado;
	
	private MpLabelTextBoxError lblErroComunicado;
		
	private Comunicado comunicado;
	
	private TelaInicialComunicado telaInicialComunicado;
	
    private static FormularioComunicado uniqueInstance;
    
    private FlowPanel panelImages = new FlowPanel();
//    private PreloadedImage preloadedImage;
	
	public static FormularioComunicado getInstance(TelaInicialComunicado telaInicialComunicado){
		if(uniqueInstance==null){
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

		vBodyPanel = new VerticalPanel();
		
		MpImageButton btnRetornarTelaAnterior = new MpImageButton(txtConstants.comunicadoRetornarTela(), "images/previousFolder.png");
		btnRetornarTelaAnterior.addClickHandler(new ClickHandlerCancelar());		
		
		vBodyPanel.add(btnRetornarTelaAnterior);
		vBodyPanel.add(new MpSpaceVerticalPanel());
		vBodyPanel.add(drawPassoUmCamposTitulo());
		vBodyPanel.add(new MpSpaceVerticalPanel());
		vBodyPanel.add(drawPassoDoisSelecionarImagem());
		vBodyPanel.add(new MpSpaceVerticalPanel());
		vBodyPanel.add(drawPassoTresDescricao());
		vBodyPanel.add(new MpSpaceVerticalPanel());
		vBodyPanel.add(drawPassoQuatroSubmeterComunicado());
		
		super.add(vBodyPanel);
	}
	
	
	
	
	@SuppressWarnings("deprecation")
	public MpPanelPageMainView drawPassoUmCamposTitulo(){
		
		MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.comunicadoInsiraComunicado(), "images/insert_header.png");
		mpPanel.setWidth(Integer.toString(TelaInicialDisciplina.intWidthTable)+"px");
		


		// Add a title to the form
		txtAssunto = new TextBox();
		mpDateBoxData = new MpDateBoxWithImage();
		mpDateBoxData.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
		txtAssunto.setStyleName("design_text_boxes");
//		dateBoxData.setStyleName("design_text_boxes");

		Label lblAssunto = new Label(txtConstants.comunicadoAssunto());
		Label lblTipoComunicado = new Label(txtConstants.comunicadoTipo());
		Label lblDataInicial = new Label(txtConstants.comunicadoData());
		Label lblHora = new Label(txtConstants.comunicadoHora());
		
		lblErroComunicado = new MpLabelTextBoxError();

		
		listBoxTipoComunicados = new MpSelectionTipoComunicado();
		listBoxTipoComunicados.setStyleName("design_text_boxes");
//		listBoxTipoComunicados.setWidth("300px");				
		
		lblAssunto.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblTipoComunicado.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDataInicial.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblHora.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		lblAssunto.setStyleName("design_label");
		lblTipoComunicado.setStyleName("design_label");
		lblDataInicial.setStyleName("design_label");
		lblHora.setStyleName("design_label");

				
		txtAssunto.setWidth("350px");
		mpDateBoxData.getDate().setWidth("170px");

		mpTimePicker = new MpTimePicker(7,22);
		
		Grid grid = new Grid(4,3);
		grid.setCellSpacing(3);
		grid.setCellPadding(3);
		grid.setBorderWidth(0);
		grid.setSize(Integer.toString(TelaInicialComunicado.intWidthTable),Integer.toString(TelaInicialComunicado.intHeightTable));		

		int row = 0;
		
		grid.setWidget(row, 0, lblTipoComunicado);grid.setWidget(row++, 1, listBoxTipoComunicados);		
		grid.setWidget(row, 0, lblAssunto);grid.setWidget(row, 1, txtAssunto);grid.setWidget(row++, 2, lblErroComunicado); 	
		grid.setWidget(row, 0, lblDataInicial); grid.setWidget(row++, 1, mpDateBoxData);		
		grid.setWidget(row, 0, lblHora);grid.setWidget(row++, 1, mpTimePicker);
		

		mpPanel.add(grid);
		
		return mpPanel;
		
	}
	
	public MpPanelPageMainView drawPassoDoisSelecionarImagem(){
		
		MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.comunicadoSelecionaImagem(), "images/picture_16.png");
		mpPanel.setWidth(Integer.toString(TelaInicialDisciplina.intWidthTable)+"px");
		mpPanel.setHeight("70px");
		
		Grid grid = new Grid(1,3);
		grid.setCellSpacing(3);
		grid.setCellPadding(3);
		grid.setBorderWidth(0);
		grid.setSize(Integer.toString(TelaInicialComunicado.intWidthTable),Integer.toString(TelaInicialComunicado.intHeightTable));
		
		Grid vPanelImagem = new Grid(1,2);
		vPanelImagem.setSize("15px", "73px");
		
		MpImageButton mpButton = new MpImageButton(txtConstants.comunicadoEscolherImagem(),"images/image_16.png");		

		SingleUploader singleUploader = new SingleUploader(FileInputType.CUSTOM.with(mpButton));

		singleUploader.setAutoSubmit(true);
		singleUploader.setValidExtensions("jpg", "gif", "png");
		singleUploader.addOnFinishUploadHandler(onFinishUploaderHandler);
		
		vPanelImagem.setWidget(0, 0, singleUploader);
		vPanelImagem.setWidget(0, 1, panelImages);		

		mpPanel.add(vPanelImagem);
		
		return mpPanel;
		
	}	
	
	public MpPanelPageMainView drawPassoTresDescricao(){
		
		MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.comunicadoInsiraDescricao(), "images/note_2add.png");
		mpPanel.setWidth(Integer.toString(TelaInicialDisciplina.intWidthTable)+"px");
		
		Grid grid = new Grid(1,2);
		grid.setCellSpacing(3);
		grid.setCellPadding(3);
		grid.setBorderWidth(0);
		grid.setSize(Integer.toString(TelaInicialComunicado.intWidthTable),Integer.toString(TelaInicialComunicado.intHeightTable));


		Label lblDescricaoCurso = new Label(txtConstants.cursoDescricao());
		lblDescricaoCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDescricaoCurso.setStyleName("design_label");		
		
		mpRichTextDescricao  = new MpRichTextArea();

		// Add some standard form options
		int row = 0;
		grid.setWidget(row, 0, lblDescricaoCurso);
		grid.setWidget(row++, 1, mpRichTextDescricao);

		mpPanel.add(grid);
		
		return mpPanel;
		
	}	
	
	public VerticalPanel drawPassoQuatroSubmeterComunicado(){

		FlexTable flexTable = new FlexTable();	
		flexTable.setCellSpacing(2);
		flexTable.setCellPadding(2);
		flexTable.setBorderWidth(0);		
		
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPanel.setWidth(Integer.toString(TelaInicialDisciplina.intWidthTable)+"px");

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
		
		
		/***********************Begin Callbacks**********************/

		callbackAddComunicado = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.comunicadoErroSalvar());
				mpDialogBoxWarning.showDialog();
			}

			@Override
			public void onSuccess(Boolean result) {
				mpPanelLoading.setVisible(false);
				boolean isSuccess = result;
				if (isSuccess) {
					
					if(btnAdicionarComunicado.isVisible()){
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

		

		/***********************End Callbacks**********************/
	
		return vPanel;
	}	
	
	
	/****************Begin Event Handlers*****************/
	
	private class ClickHandlerSave implements ClickHandler {

		public void onClick(ClickEvent event) {

//			if (txtAssunto == null || txtAssunto.getText().isEmpty()) {
//
//				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//				mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.comunicadoAssunto()));
//				mpDialogBoxWarning.showDialog();
//
//			} else {
			if(checkFieldsValidator()){
				
				mpPanelLoading.setVisible(true);
				
	
				String strHora = mpTimePicker.getValue(mpTimePicker.getSelectedIndex());
//				Date hora = DateTimeFormat.getFormat("hh:mm a").parse(strHora);
				
				int intIdTipoComunicado = Integer.parseInt(listBoxTipoComunicados.getValue(listBoxTipoComunicados.getSelectedIndex()));
				
				Comunicado object = new Comunicado();
				
				
				object.setAssunto(txtAssunto.getText());
				object.setDescricao(mpRichTextDescricao.getTextArea().getHTML());
				object.setData(MpUtilClient.convertDateToString(mpDateBoxData.getDate().getValue()));				
				object.setHora(strHora);				
				object.setIdTipoComunicado(intIdTipoComunicado);
				object.setNomeImagem(strNomeImagem);
				
				if(btnAdicionarComunicado.isVisible()){
					GWTServiceComunicado.Util.getInstance().AdicionarComunicado(object, callbackAddComunicado);					
				}				
				else if(btnAtualizarComunicado.isVisible()){
					object.setIdComunicado(comunicado.getIdComunicado());
					GWTServiceComunicado.Util.getInstance().AtualizarComunicado(object, callbackAddComunicado);
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
	
	/****************End Event Handlers*****************/


	public Comunicado getComunicado() {
		return comunicado;
	}

	public void setComunicado(Comunicado comunicado) {
		this.comunicado = comunicado;	
	}
	
	
	public void populateGridComunicados(){
		
	}
	
	public void openFormularioParaAtualizar(Comunicado comunicado){
		
		setComunicado(comunicado);
		
		btnAtualizarComunicado.setVisible(true);
		btnAdicionarComunicado.setVisible(false);
		
		txtAssunto.setText(this.comunicado.getAssunto());
		mpRichTextDescricao.getTextArea().setHTML(this.comunicado.getDescricao());

		mpDateBoxData.getDate().setValue(MpUtilClient.convertStringToDate(this.comunicado.getData()));

		strNomeImagem = this.comunicado.getNomeImagem();
		new PreloadedImage("images/download/"+strNomeImagem, showImage);
		


		
//		Date horaComunicadoDB = new java.util.Date(this.comunicado.getHora());

		
		String strHoraDB = MpUtilClient.getHourFromString(this.comunicado.getHora());
		String strMinutosDB = MpUtilClient.getMinutesFromString(this.comunicado.getHora());
		System.out.println("Hora:"+strHoraDB);
		System.out.println("Minutos:"+strMinutosDB);
		
		//Selecting Item listBoxTime 
		for(int i=0;i<mpTimePicker.getItemCount();i++){
			String strHora = mpTimePicker.getValue(i);	
			String strHoraListBox = strHora.substring(0,strHora.indexOf(":"));
			String strMinutosListBox = strHora.substring(strHora.lastIndexOf(":")+1,strHora.lastIndexOf(":")+3);
			
			int intHoraDB = Integer.parseInt(strHoraDB);
			int intMinutoDB = Integer.parseInt(strMinutosDB);
			int intHoraListBox = Integer.parseInt(strHoraListBox);
			int intMinutoListBox = Integer.parseInt(strMinutosListBox);
			
			if( (intHoraDB == intHoraListBox) && (intMinutoDB == intMinutoListBox)){
				
				mpTimePicker.setSelectedIndex(i);
			}


		}
		
		//Selecting Item listBoxComunicados 
		for(int i=0;i<listBoxTipoComunicados.getItemCount();i++){
			if(this.comunicado.getIdTipoComunicado()==Integer.parseInt(listBoxTipoComunicados.getValue(i))){
				listBoxTipoComunicados.setSelectedIndex(i);
			}
		}		
		
		setVisible(true);
		
	}
	

	
	public void openFormularioParaAdicionar(){
		cleanFields();		
		btnAdicionarComunicado.setVisible(true);
		btnAtualizarComunicado.setVisible(false);
		setVisible(true);
	}
	
	
	  // Load the image in the document and in the case of success attach it to the viewer
	  private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
	    @SuppressWarnings("deprecation")
		public void onFinish(IUploader uploader) {
	      if (uploader.getStatus() == Status.SUCCESS) {

	    	 new PreloadedImage(uploader.fileUrl(), showImage);
	        
	        // The server sends useful information to the client by default
	        UploadedInfo info = uploader.getServerInfo();

	        
	        strNomeImagem = info.message;

	        System.out.println("uploader.getFileInput().getName() " + uploader.getFileInput().getName());
	        System.out.println("File name " + info.name);
	        System.out.println("File content-type " + info.ctype);
	        System.out.println("File size " + info.size);

	        // You can send any customized message and parse it 
	        System.out.println("Server message " + info.message);
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
	  
	  
	  
		public boolean checkFieldsValidator(){
			
			boolean isFieldsOk = false;					
			boolean isNomeDisciplinaOk=false;		

			if(FieldVerifier.isValidName(txtAssunto.getText())){
				isNomeDisciplinaOk=true;	
				lblErroComunicado.hideErroMessage();
			}else{
				isNomeDisciplinaOk=false;
				lblErroComunicado.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.comunicadoAssunto()));
			}
			
			isFieldsOk = isNomeDisciplinaOk;
			
			return isFieldsOk;
		}	  
	

		public void cleanFields(){
//			btnAdicionarComunicado.setText(ADICIONAR);
			lblErroComunicado.hideErroMessage();
			this.comunicado=null;
			txtAssunto.setText("");		
			mpRichTextDescricao.getTextArea().setHTML("");
			mpDateBoxData.getDate().setValue(null);
			mpTimePicker.setSelectedIndex(0);
			listBoxTipoComunicados.setSelectedIndex(0);
			strNomeImagem="";
			panelImages.clear();
		}
		
		

	
}

