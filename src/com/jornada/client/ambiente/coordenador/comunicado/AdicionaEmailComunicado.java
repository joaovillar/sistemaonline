package com.jornada.client.ambiente.coordenador.comunicado;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.SingleUploader;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.client.classes.listBoxes.MpSelectionTipoEmail;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.MpSelectionCursoItemTodos;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.MpSelectionUnidadeEscola;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelLeft;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.textbox.MpRichTextArea;
import com.jornada.client.classes.widgets.textbox.MpTextBox;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceEmail;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.TipoComunicado;
import com.jornada.shared.classes.usuario.UsuarioNomeID;
import com.jornada.shared.classes.utility.MpUtilClient;

public class AdicionaEmailComunicado extends VerticalPanel {
    
    
    private final static int ROW_FILTRO_CURSO = 3; 
    private final static int ROW_TIPO_USUARIO = 4;
    private final static int ROW_TIPO_PARA = 5;

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpPanelParaLoading = new MpPanelLoading("images/radar.gif");
	MpPanelLoading mpPanelSendLoading = new MpPanelLoading("images/radar.gif");
	
    private CheckBox checkBoxAluno;
    private CheckBox checkBoxPais;
    private CheckBox checkBoxProfessor;
	
	private FlexTable flexTable;

	private MpSelectionTipoEmail listBoxTipoEmail;
	
	private MpTextBox txtAssunto;
	private MpRichTextArea mpRichTextAreaEmail;
	private MpSelectionCursoItemTodos listBoxCursoItemTodos;
	
	private MpSelectionUnidadeEscola listBoxUnidadeEscola;
	
	private MpSelection multiBoxUsuariosFiltrados;
	
	private MpLabelLeft lblNomeArquivoUploaded;
	private String strNomeFisicoUploaded="";
	
//	private MpTextBox txtFiltroNome;    
	
	
//	private MultiBox multiBox;
//	private HashMap<String, Integer> emailList = new HashMap<String, Integer>();
//	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	
	@SuppressWarnings("unused")
	private TelaInicialComunicado telaInicialComunicado;
	
	TextConstants txtConstants;
	
	private static AdicionaEmailComunicado uniqueInstance;
	
	public static AdicionaEmailComunicado getInstance(final TelaInicialComunicado telaInicialComunicado){

		if(uniqueInstance==null){
			uniqueInstance = new AdicionaEmailComunicado(telaInicialComunicado);
		}
		
		return uniqueInstance;
		
	}

	private AdicionaEmailComunicado(final TelaInicialComunicado telaInicialComunicado) {
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialComunicado=telaInicialComunicado;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpPanelParaLoading.show();
	    mpPanelSendLoading.show();
	    disablePanelsLoading();    
		
		txtAssunto = new MpTextBox();
		mpRichTextAreaEmail = new MpRichTextArea();
		txtAssunto.setStyleName("design_text_boxes");
		
		txtAssunto.setTitle(txtConstants.geralCampoObrigatorio(txtConstants.emailAssunto()));


		MpLabelLeft lblEmail = new MpLabelLeft(txtConstants.emailEmail());
		MpLabelLeft lblUnidade = new MpLabelLeft(txtConstants.usuarioUnidadeEscola());
		MpLabelLeft lblAssunto = new MpLabelLeft(txtConstants.emailAssunto());		
		MpLabelLeft lblAnexo = new MpLabelLeft(txtConstants.emailAnexo());
		MpLabelLeft lblFiltarCurso = new MpLabelLeft(txtConstants.emailFiltroCurso());
		MpLabelLeft lblPara = new MpLabelLeft(txtConstants.emailPara());
//		MpLabelLeft lblParaIndividual = new MpLabelLeft("Para");
		MpLabelLeft lblTipoUsuario = new MpLabelLeft(txtConstants.emailTipoUsuario());
		
        multiBoxUsuariosFiltrados = new MpSelection();
        multiBoxUsuariosFiltrados.setMultipleSelect(true);
        multiBoxUsuariosFiltrados.setVisibleItemCount(5);
        multiBoxUsuariosFiltrados.setSize("350px", "70px");
        multiBoxUsuariosFiltrados.setStyleName("design_text_boxes");
        multiBoxUsuariosFiltrados.setTitle(txtConstants.geralCampoObrigatorio(txtConstants.emailPara())+". "+txtConstants.emailPeloMenosUsuarioPrecisaSerSelecionado());

        
//        txtFiltroNome = new MpTextBox();     
//        txtFiltroNome.addKeyUpHandler(new EnterKeyUpHandlerFiltrarNome());

        Image img = new Image("images/group.png");
        img.setTitle(txtConstants.emailAbrirTelaParaVisualizarEmails());
        img.setStyleName("imageCenterPointer");
        img.addClickHandler(new clickHandlerAddress());

        
        HorizontalPanel hPanelMultiBoxAlunoPaiProfessor = new HorizontalPanel();
        hPanelMultiBoxAlunoPaiProfessor.setBorderWidth(0);
        hPanelMultiBoxAlunoPaiProfessor.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        hPanelMultiBoxAlunoPaiProfessor.add(multiBoxUsuariosFiltrados);
        hPanelMultiBoxAlunoPaiProfessor.add(new InlineHTML("&nbsp;"));
        hPanelMultiBoxAlunoPaiProfessor.add(img);
        hPanelMultiBoxAlunoPaiProfessor.add(new InlineHTML("&nbsp;"));
        hPanelMultiBoxAlunoPaiProfessor.add(mpPanelParaLoading);
        
		txtAssunto.setWidth("350px");
		mpRichTextAreaEmail.setSize("350px", "50px");
		
		listBoxTipoEmail = new MpSelectionTipoEmail();
		listBoxTipoEmail.addChangeHandler(new ChangeHandlerListBoxTipoEmail());
		
		
		listBoxCursoItemTodos = new MpSelectionCursoItemTodos(true, txtConstants.emailTodosOsCursos());
//		listBoxCursoItemTodos.setMultipleSelect(true);
//		listBoxCursoItemTodos.setVisibleItemCount(5);
//		listBoxCursoItemTodos.setSize("350px", "70px");
        listBoxCursoItemTodos.addChangeHandler(new ChangeHandlerListBoxCurso());	
        
        listBoxUnidadeEscola = new MpSelectionUnidadeEscola();
        listBoxUnidadeEscola.addChangeHandler(new ChangeHandlerListBoxUnidade());
        
        
        checkBoxAluno = new CheckBox(txtConstants.aluno());
        checkBoxPais = new CheckBox(txtConstants.pais());
        checkBoxProfessor = new CheckBox(txtConstants.professor());
        
        checkBoxAluno.addClickHandler(new clickHandlerCheckBox());
        checkBoxPais.addClickHandler(new clickHandlerCheckBox());
        checkBoxProfessor.addClickHandler(new clickHandlerCheckBox());
        
        
        checkBoxAluno.setValue(true);
        checkBoxPais.setValue(true);
        checkBoxProfessor.setValue(true);
        Grid gridTipoUsuario = new Grid(1, 3);
        gridTipoUsuario.setBorderWidth(0);
        gridTipoUsuario.setWidget(0, 0, checkBoxAluno);
        gridTipoUsuario.setWidget(0, 1, checkBoxPais);
        gridTipoUsuario.setWidget(0, 2, checkBoxProfessor);
        
        lblNomeArquivoUploaded = new MpLabelLeft("");
        MpImageButton mpButton = new MpImageButton(txtConstants.emailEscolherArquivo(), "images/folder_open_add2.png");
        SingleUploader singleUploader = new SingleUploader(FileInputType.CUSTOM.with(mpButton));
        singleUploader.setAutoSubmit(true);
        singleUploader.addOnFinishUploadHandler(onFinishUploaderHandler);   
        
        Grid gridSingleUpload = new Grid(1,2);
        gridSingleUpload.setCellPadding(1);
        gridSingleUpload.setCellSpacing(1);
        
        gridSingleUpload.setWidget(0, 0, singleUploader);
        gridSingleUpload.setWidget(0, 1, lblNomeArquivoUploaded);
		
        
        flexTable = new FlexTable();
        flexTable.setCellSpacing(3);
        flexTable.setCellPadding(3);
        flexTable.setBorderWidth(0);
        flexTable.setWidth("100%");       
        
		// Add some standard form options
		int row = 1;

		flexTable.setWidget(row, 0, lblEmail);flexTable.setWidget(row++, 1, listBoxTipoEmail);
		flexTable.setWidget(row, 0, lblUnidade);flexTable.setWidget(row++, 1, listBoxUnidadeEscola);
		
		
		flexTable.setWidget(row, 0, lblFiltarCurso);flexTable.setWidget(row++, 1, listBoxCursoItemTodos);//flexTable.setWidget(row++, 2, new MpImageHelper(listBoxNomeCurso));//flexTable.setWidget(row++, 2, txtFiltroNomeCurso);
		flexTable.setWidget(row, 0, lblTipoUsuario);flexTable.setWidget(row++, 1, gridTipoUsuario);
		flexTable.setWidget(row, 0, lblPara);flexTable.setWidget(row++, 1, hPanelMultiBoxAlunoPaiProfessor);//flexTable.setWidget(row++, 2, mpPanelParaLoading);//flexTable.setWidget(row++, 2, txtFiltroNomeCurso);
//		flexTable.setWidget(row, 0, lblParaIndividual);flexTable.setWidget(row++, 1, multiBox);
		
		flexTable.setWidget(row, 0, lblAnexo);flexTable.setWidget(row++, 1, gridSingleUpload);//flexTable.setWidget(row++, 2, lblErroNomePeriodo);
		flexTable.setWidget(row, 0, lblAssunto);flexTable.setWidget(row++, 1, txtAssunto);//flexTable.setWidget(row++, 2, lblErroNomePeriodo);
		flexTable.setWidget(row, 0, mpRichTextAreaEmail);
		flexTable.getFlexCellFormatter().setColSpan(row, 0, 3);
		
		
		

		MpImageButton btnEnviarEmail = new MpImageButton(txtConstants.emailEnviarEmail(), "images/image002.png");
		btnEnviarEmail.addClickHandler(new ClickHandlerEnviarEmail());
		MpImageButton btnClean = new MpImageButton(txtConstants.geralLimpar(), "images/erase.png");
		btnClean.addClickHandler(new ClickHandlerClean());

		VerticalPanel vFormPanel = new VerticalPanel();
		vFormPanel.setWidth("100%");

		Grid gridSave = new Grid(1, 3);
		gridSave.setCellSpacing(2);
		gridSave.setCellPadding(2);
		{
			int i = 0;
			gridSave.setWidget(0, i++, btnEnviarEmail);
			gridSave.setWidget(0, i++, btnClean);
			gridSave.setWidget(0, i++, mpPanelSendLoading);
		}
		

		vFormPanel.add(flexTable);
		vFormPanel.add(gridSave);
		
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setWidth("100%");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(vFormPanel);
		
		super.add(scrollPanel);

	}

	
	
	/****************Begin Event Handlers*****************/
	
	private class ClickHandlerEnviarEmail implements ClickHandler {

		public void onClick(ClickEvent event) {

			if(checkFieldsValidator()){			

				mpPanelSendLoading.setVisible(true);

				int idTipoEmail = Integer.parseInt(listBoxTipoEmail.getValue(listBoxTipoEmail.getSelectedIndex()));
				String subject = txtAssunto.getText();
				String descricao = mpRichTextAreaEmail.getTextArea().getHTML();
				String strFileName = lblNomeArquivoUploaded.getText();
				
	            ArrayList<Integer> listUser = new ArrayList<Integer>();
                for (int i = 0; i < multiBoxUsuariosFiltrados.getItemCount(); i++) {
                    if (multiBoxUsuariosFiltrados.isItemSelected(i)) {
                        int intIdPeriodo = Integer.parseInt(multiBoxUsuariosFiltrados.getValue(i));
                        listUser.add(intIdPeriodo);
                    }
                }
//                for(int i=0;i<multiBoxUsuariosFiltrados.getItemCount();i++){
//                    int idAluno = Integer.parseInt(multiBoxUsuariosFiltrados.getValue(i));
//                    listUser.add(idAluno);
//                }
	            
				GWTServiceEmail.Util.getInstance().sendEmailParaAlunosPaisProfessores(idTipoEmail, listUser, subject, descricao, strNomeFisicoUploaded, strFileName ,new CallBackEnviarEmail());

			}

		}
	}
	
	private class ClickHandlerClean implements ClickHandler {
		public void onClick(ClickEvent event) {
			cleanFields();
			
		}
	}	
	
	/****************End Event Handlers*****************/

	
	public boolean checkFieldsValidator(){
	    boolean isFieldsOk = false;
		boolean isAssuntoOk = false;
		boolean isUsuarioSelectedOk = false;
		
		
		for (int i = 0; i < multiBoxUsuariosFiltrados.getItemCount(); i++) {
            if (multiBoxUsuariosFiltrados.isItemSelected(i)) {
                isUsuarioSelectedOk=true;
                break;
            }
        }
		
		if(isUsuarioSelectedOk){
		    multiBoxUsuariosFiltrados.setStyleName("design_text_boxes");
		}else{
		    multiBoxUsuariosFiltrados.setStyleName("design_text_boxes_erro");
		}
		
		if(FieldVerifier.isValidName(txtAssunto.getText())){
			isAssuntoOk=true;	
//			lblErroNomePeriodo.hideErroMessage();
			txtAssunto.setStyleName("design_text_boxes");
		}
		else{
			isFieldsOk=false;
			txtAssunto.setStyleName("design_text_boxes_erro");
		}
		
		isFieldsOk = isAssuntoOk && isUsuarioSelectedOk;
		
		return isFieldsOk;
	}
	
	
	private void cleanFields(){
//		lblErroNomePeriodo.hideErroMessage();
	    txtAssunto.setStyleName("design_text_boxes");
	    multiBoxUsuariosFiltrados.setStyleName("design_text_boxes");
		txtAssunto.setValue("");
		mpRichTextAreaEmail.getTextArea().setHTML("");
//		txtObjetivoPeriodo.setValue("");
//		mpDateBoxInicial.getDate().setValue(null);
//		mpDateBoxFinal.getDate().setValue(null);
	}
	
	
	private void populateUsuario(){
	    
	    mpPanelParaLoading.setVisible(true);
	    String strValue = listBoxTipoEmail.getValue(listBoxTipoEmail.getSelectedIndex());
	    int idUnidadeEscola = Integer.parseInt(listBoxUnidadeEscola.getValue(listBoxUnidadeEscola.getSelectedIndex()));
	    multiBoxUsuariosFiltrados.clear();
	    if(strValue.equals(Integer.toString(TipoComunicado.EMAIL_ALUNO_PAIS_PROFESSORES))){
	        int idCurso = Integer.parseInt(listBoxCursoItemTodos.getValue(listBoxCursoItemTodos.getSelectedIndex()));
	        boolean showAluno = checkBoxAluno.getValue();
	        boolean showPais = checkBoxPais.getValue();
	        boolean showProfessor = checkBoxProfessor.getValue();
	        
            GWTServiceUsuario.Util.getInstance().getAlunosTodosOuPorCurso(idCurso, idUnidadeEscola, showAluno, showPais, showProfessor, new CallbackPopulateCampoPara());    
	    }else if(strValue.equals(Integer.toString(TipoComunicado.EMAIL_COORDENADORES))){
	        GWTServiceUsuario.Util.getInstance().getCoordenadoresAdministradoresNomeId(idUnidadeEscola, new CallbackPopulateCampoPara());
	    }
	    
	}


	public void updateClientData() {
//		listBoxNomeCurso.populateComboBox();
	}
	
	
    private class ChangeHandlerListBoxTipoEmail implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            
            String strValue = listBoxTipoEmail.getValue(listBoxTipoEmail.getSelectedIndex());
            if (strValue.equals(Integer.toString(TipoComunicado.EMAIL_ALUNO_PAIS_PROFESSORES))) {
                populateUsuario();
                flexTable.getRowFormatter().setVisible(ROW_FILTRO_CURSO, true);
                flexTable.getRowFormatter().setVisible(ROW_TIPO_USUARIO, true);
                flexTable.getRowFormatter().setVisible(ROW_TIPO_PARA, true);
            } else if (strValue.equals(Integer.toString(TipoComunicado.EMAIL_COORDENADORES))) {
                populateUsuario();
                flexTable.getRowFormatter().setVisible(ROW_FILTRO_CURSO, false);
                flexTable.getRowFormatter().setVisible(ROW_TIPO_USUARIO, false);
                flexTable.getRowFormatter().setVisible(ROW_TIPO_PARA, true);
            } else if(strValue.equals(Integer.toString(TipoComunicado.EMAIL_INDIVIDUAL))){
                flexTable.getRowFormatter().setVisible(ROW_FILTRO_CURSO, false);
                flexTable.getRowFormatter().setVisible(ROW_TIPO_USUARIO, false);
                flexTable.getRowFormatter().setVisible(ROW_TIPO_PARA, false);
            }else{
                flexTable.getRowFormatter().setVisible(ROW_FILTRO_CURSO, false);
                flexTable.getRowFormatter().setVisible(ROW_TIPO_USUARIO, false);
                flexTable.getRowFormatter().setVisible(ROW_TIPO_PARA, false);

            }

        }
    }
    
    private class ChangeHandlerListBoxUnidade implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            

            String strValue = listBoxTipoEmail.getValue(listBoxTipoEmail.getSelectedIndex());
            if(strValue.equals(Integer.toString(TipoComunicado.EMAIL_ALUNO_PAIS_PROFESSORES))){
                populateUsuario();
            }else{
                multiBoxUsuariosFiltrados.clear();
                populateUsuario();
            }
                

        }
    }
    private class ChangeHandlerListBoxCurso implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            

            String strValue = listBoxTipoEmail.getValue(listBoxTipoEmail.getSelectedIndex());
            if(strValue.equals(Integer.toString(TipoComunicado.EMAIL_ALUNO_PAIS_PROFESSORES))){
                populateUsuario();
            }else{
                multiBoxUsuariosFiltrados.clear();
            }
                

        }
    }
    private class CallbackPopulateCampoPara implements AsyncCallback<ArrayList<UsuarioNomeID>> {

        @Override
        public void onFailure(Throwable caught) {
            disablePanelsLoading();
            MpUtilClient.isRefreshRequired();
//            Window.alert(caught.getMessage());
        }

        @Override
        public void onSuccess(ArrayList<UsuarioNomeID> result) {
            disablePanelsLoading();
            MpUtilClient.isRefreshRequired(result);
            multiBoxUsuariosFiltrados.clear();
//            for (UsuarioNomeID user : result) {
            for(int i=0;i<result.size();i++){
                UsuarioNomeID user = result.get(i);
                multiBoxUsuariosFiltrados.addItem(user.getNomeUsuario(), Integer.toString(user.getIdUsuario()));
                multiBoxUsuariosFiltrados.setItemSelected(i, true);
            }            
            
        }
        
        
    }
    private class clickHandlerCheckBox implements ClickHandler{

        @Override
        public void onClick(ClickEvent event) {
            String strValue = listBoxTipoEmail.getValue(listBoxTipoEmail.getSelectedIndex());
            if(strValue.equals(Integer.toString(TipoComunicado.EMAIL_ALUNO_PAIS_PROFESSORES))){
                populateUsuario();
            }else{
                multiBoxUsuariosFiltrados.clear();
            }            
        }

    }
    private class CallBackEnviarEmail implements AsyncCallback<String>{

        @Override
        public void onFailure(Throwable caught) {
            disablePanelsLoading();
            MpUtilClient.isRefreshRequired();
        }

        @Override
        public void onSuccess(String result) {
            disablePanelsLoading();
            MpUtilClient.isRefreshRequired(result);
            mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
            mpDialogBoxConfirm.setBodyText(txtConstants.emailEnviado());
            mpDialogBoxConfirm.showDialog();            
        }


        
    }
    
//    private class CallbackPopulateOracle implements AsyncCallback<HashMap<String, Integer>>{
//
//        @Override
//        public void onFailure(Throwable caught) {
//            
//            disablePanelsLoading();
//            Window.alert(caught.getMessage());            
//        }
//        
//        @Override
//        public void onSuccess(HashMap<String, Integer> result) {
//            disablePanelsLoading();
//            MpUtilClient.isRefreshRequired(result);
//            emailList = result;
//            for (String key : result.keySet()) {
//                oracle.add(key);
//            }
//            
//        }
//        
//    }
    
    
    private void disablePanelsLoading() {
        mpPanelParaLoading.setVisible(false);
        mpPanelSendLoading.setVisible(false);
    }
    
//   private void populateOracle(){
//       GWTServiceEmail.Util.getInstance().getUsersIdlList(new CallbackPopulateOracle());
//               
//   }
    
//    private class EnterKeyUpHandlerFiltrarNome implements KeyUpHandler{
//        public void onKeyUp(KeyUpEvent event){
//            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//                //listBoxNomeCurso.filterComboBox(txtFiltroNomeCurso.getText());
//                multiBoxUsuariosFiltrados.filterComboBox(txtFiltroNome.getText());
//
//            }
//        }
//    }    
    // Load the image in the document and in the case of success attach it to the viewer
    private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {

      public void onFinish(IUploader uploader) {
        if (uploader.getStatus() == Status.SUCCESS) {

          
          // The server sends useful information to the client by default
          UploadedInfo info = uploader.getServerInfo();


          System.out.println("uploader.getFileInput().getName() " + uploader.getFileInput().getName());
          System.out.println("File name " + info.name);
          System.out.println("File content-type " + info.ctype);
          System.out.println("File size " + info.size);

          // You can send any customized message and parse it 
          System.out.println("Server message :" + uploader.getServerMessage().getMessage());
          
//        strNomeFisicoUploaded = info.message;
          strNomeFisicoUploaded =  uploader.getServerMessage().getMessage();

          lblNomeArquivoUploaded.setText(info.name);
        }
      }
    };
    

    private class clickHandlerAddress implements ClickHandler{

        @Override
        public void onClick(ClickEvent event) {
            
            ArrayList<String> listIdUsuarios = new ArrayList<String>();

            for (int i = 0; i < multiBoxUsuariosFiltrados.getItemCount(); i++) {
                if (multiBoxUsuariosFiltrados.isItemSelected(i)) {
                    String intId = multiBoxUsuariosFiltrados.getValue(i);
                    listIdUsuarios.add(intId);
                }
            }
            
            MpDialogBoxAddress.getInstance(listIdUsuarios);            
        }
        
    }
    
    
	
}
