package com.jornada.client.ambiente.general.configuracao;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Usuario;

public class DialogBoxPrimeiroLogin extends DialogBox {
	
	private AsyncCallback<Boolean> callbackAtualizarSenha;
	
	TextConstants txtConstants = GWT.create(TextConstants.class);
	
//	MpSelection listBoxIdiomas;
	
	private TextBox txtNovaSenha;
	private TextBox txtConfirmarNovaSenha;
	
	private Usuario usuario;
	
	private MainView mainView;
	private VerticalPanel vPanelPage;
	
//	private String urlLocale;

	MpPanelLoading mpLoading = new MpPanelLoading("images/radar.gif");
	
	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
	
	
	private static DialogBoxPrimeiroLogin uniqueInstance;
	
	public static DialogBoxPrimeiroLogin getInstance(Usuario usuario){
		if(uniqueInstance==null){
			uniqueInstance = new DialogBoxPrimeiroLogin(usuario);
		}else{
			uniqueInstance.setUsuario(usuario);
			uniqueInstance.show();
		}
		return uniqueInstance;
	}

	
	private DialogBoxPrimeiroLogin(Usuario usuario){
		
		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpLoading.setTxtLoading("");
		mpLoading.show();
		mpLoading.setVisible(false);
		
		this.usuario = usuario;
		
//		setTitle("Idioma");
		VerticalPanel vBody = new VerticalPanel();
		
		Label lblNovaSenha = new Label(txtConstants.loginNovaSenha());
		Label lblConfirmarNovaSenha = new Label(txtConstants.loginConfirmarSenha());	
		Label primeiroLogin = new Label(txtConstants.loginPrimeiroLogin());
		
		lblNovaSenha.setStyleName("design_label");
		lblConfirmarNovaSenha.setStyleName("design_label");
		
		txtNovaSenha = new TextBox();
		txtConfirmarNovaSenha = new TextBox();
		
		txtNovaSenha.getElement().setPropertyString("type", "password");		
		txtConfirmarNovaSenha.getElement().setPropertyString("type", "password");
		
		txtNovaSenha.setStyleName("design_text_boxes");
		txtConfirmarNovaSenha.setStyleName("design_text_boxes");
		
//		listBoxIdiomas = new MpSelection();
//		listBoxIdiomas.setWidth("200px");		
//		listBoxIdiomas.addItem(txtConstants.idiomaIngles(), "en");
//		listBoxIdiomas.addItem(txtConstants.idiomaPortugues(), "pt_BR");
//		listBoxIdiomas.setSelectItem(usuario.getIdioma().getLocale());
		
		MpImageButton btnAtualizar = new MpImageButton(txtConstants.geralAtualizar(), "images/image002.png");
		btnAtualizar.addClickHandler(new ClickHandlerAtualizar());
		
		
		MpImageButton btnCancelar = new MpImageButton(txtConstants.geralCancelar(), "images/cross-circle-frame.png");
		btnCancelar.addClickHandler(new ClickHandlerCancelar());
		
		
		
		Grid gridListBox = new Grid(2,3);
		gridListBox.setCellPadding(2);
		gridListBox.setCellSpacing(2);

		int row=0;
		gridListBox.setWidget(row, 0, lblNovaSenha);
		gridListBox.setWidget(row++, 1, txtNovaSenha);
		gridListBox.setWidget(row, 0, lblConfirmarNovaSenha);
		gridListBox.setWidget(row, 1, txtConfirmarNovaSenha);		
		gridListBox.setWidget(row, 2, mpLoading);

		
		
		Grid gridBotoes = new Grid(1,2);
		row=0;
		gridBotoes.getCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		gridBotoes.getCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_LEFT);
		gridBotoes.setWidth("100%");
//		gridBotoes.setBorderWidth(1);
		
		
		row=0;
		gridBotoes.setWidget(row, 0, btnAtualizar);
		gridBotoes.setWidget(row, 1, btnCancelar);
			
		vBody.add(primeiroLogin);
		vBody.add(gridListBox);
		vBody.add(gridBotoes);

	    /***********************Begin Callbacks**********************/

		// Callback para adicionar Curso.
	    callbackAtualizarSenha = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				mpLoading.setVisible(false);
				DialogBoxPrimeiroLogin.this.hide();
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.idiomaErroSalvar());
				mpDialogBoxWarning.showDialog();
			}

			@Override
			public void onSuccess(Boolean result) {
				mpLoading.setVisible(false);
				DialogBoxPrimeiroLogin.this.hide();
				boolean isSuccess = result;
				if (isSuccess) {
					txtNovaSenha.setValue("");
					txtConfirmarNovaSenha.setValue("");
					DialogBoxPrimeiroLogin.this.hide();			                            
                	createMainView();
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.senhaErroSalvar());
					mpDialogBoxWarning.showDialog();
				}
			}
		};


		/***********************End Callbacks**********************/
		
		
		setWidget(vBody);
		super.setGlassEnabled(true);
		super.setAnimationEnabled(true);
	    center();
	    show();
		
	}
	
	
	private class ClickHandlerAtualizar implements ClickHandler{
		
		public void onClick(ClickEvent event){
			
			if (FieldVerifier.isValidPassword(txtNovaSenha.getValue())) {
				if (txtNovaSenha.getValue().equals(txtConfirmarNovaSenha.getValue())) {
					mpLoading.setVisible(true);
					GWTServiceUsuario.Util.getInstance().atualizarSenha(usuario.getIdUsuario(), txtNovaSenha.getValue(), callbackAtualizarSenha);
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.senhaNaoConfere());
					mpDialogBoxWarning.showDialog();
				}
			}else{
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.senhaErroSenha());
				mpDialogBoxWarning.showDialog();				
			}

//			GWTServiceIdioma.Util.getInstance().atualizarIdiomaUsuario(usuario.getIdUsuario(), newLocaleParameter, callbackAtualizarIdioma);
			
						
		}		
	}
	
		
	private class ClickHandlerCancelar implements ClickHandler{		
		public void onClick(ClickEvent event){
			DialogBoxPrimeiroLogin.this.hide();			
		}		
	}
	
private void createMainView(){
	
		String sessionID = usuario.getSessionId();
		//milliseconds * seconds * minutes * hours * days
		final long DURATION = 1000 * 60 * 60 * 24 * 1;
		Date expires = new Date(System.currentTimeMillis() + DURATION);
		Cookies.setCookie("sid", sessionID, expires, null, "/", false);
		
		mainView = new MainView(usuario);		
		mainView.initHistorySupport();
		vPanelPage = new VerticalPanel();
		vPanelPage.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPanelPage.setSize("100%", "100%");
		RootPanel.get().clear();
		RootPanel.get().add(vPanelPage);
    	vPanelPage.clear();
		vPanelPage.add(mainView);
	}

}
