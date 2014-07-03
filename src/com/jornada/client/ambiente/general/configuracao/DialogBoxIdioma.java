package com.jornada.client.ambiente.general.configuracao;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceIdioma;
import com.jornada.shared.classes.Usuario;

public class DialogBoxIdioma extends DialogBox {
	
	private AsyncCallback<Boolean> callbackAtualizarIdioma;
	
	TextConstants txtConstants = GWT.create(TextConstants.class);
	
	MpSelection listBoxIdiomas;
	
	private Usuario usuario;
	
	private String urlLocale;
	
	MpPanelLoading mpLoading = new MpPanelLoading("images/radar.gif");
	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
	
	
	private static DialogBoxIdioma uniqueInstance;
	
	public static DialogBoxIdioma getInstance(Usuario usuario){
		if(uniqueInstance==null){
			uniqueInstance = new DialogBoxIdioma(usuario);
		}else{
			uniqueInstance.show();
		}
		return uniqueInstance;
	}

	
	private DialogBoxIdioma(Usuario usuario){
		
		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpLoading.setTxtLoading("");
		mpLoading.show();
		mpLoading.setVisible(false);
		
		this.usuario = usuario;
		
//		setTitle("Idioma");
		VerticalPanel vBody = new VerticalPanel();
		
		Label lblIdioma = new Label(txtConstants.idioma());
		lblIdioma.setStyleName("design_label");
		
		listBoxIdiomas = new MpSelection();
		listBoxIdiomas.setWidth("200px");		
		listBoxIdiomas.addItem(txtConstants.idiomaIngles(), "en");
		listBoxIdiomas.addItem(txtConstants.idiomaPortugues(), "pt_BR");
		listBoxIdiomas.setSelectItem(usuario.getIdioma().getLocale());
		
		MpImageButton btnAtualizar = new MpImageButton(txtConstants.geralAtualizar(), "images/image002.png");
		btnAtualizar.addClickHandler(new ClickHandlerAtualizar());
		
		
		MpImageButton btnCancelar = new MpImageButton(txtConstants.geralCancelar(), "images/cross-circle-frame.png");
		btnCancelar.addClickHandler(new ClickHandlerCancelar());
		
		
		
		Grid gridListBox = new Grid(1,3);
		gridListBox.setCellPadding(2);
		gridListBox.setCellSpacing(2);

		int row=0;
		gridListBox.setWidget(row, 0, lblIdioma);
		gridListBox.setWidget(row, 1, listBoxIdiomas);
		gridListBox.setWidget(row, 2, mpLoading);

		
		
		Grid gridBotoes = new Grid(1,2);
		gridBotoes.getCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		gridBotoes.getCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_LEFT);
		gridBotoes.setWidth("100%");
//		gridBotoes.setBorderWidth(1);
		
		
		row=0;
		gridBotoes.setWidget(row, 0, btnAtualizar);
		gridBotoes.setWidget(row, 1, btnCancelar);
			
		vBody.add(gridListBox);
		vBody.add(gridBotoes);

	    /***********************Begin Callbacks**********************/

		// Callback para adicionar Curso.
	    callbackAtualizarIdioma = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				mpLoading.setVisible(false);
				DialogBoxIdioma.this.hide();
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.idiomaErroSalvar());
				mpDialogBoxWarning.showDialog();
			}

			@Override
			public void onSuccess(Boolean result) {
				mpLoading.setVisible(false);
				DialogBoxIdioma.this.hide();
				boolean isSuccess = result;
				if (isSuccess) {
//					Window.Location.replace(urlLocale);
//					mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
//					mpDialogBoxConfirm.setBodyText(txtConstants.cursoSalvoSucesso());
//					mpDialogBoxConfirm.showDialog();
					//setLocaleCookie(listBoxIdiomas.getValue(listBoxIdiomas.getSelectedIndex()));
					Window.Location.assign(urlLocale);
//					Window.Location.reload();
					
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.idiomaErroSalvar());
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
	
//	private void setLocaleCookie( String locale )
//	{
//	    final String cookieName = LocaleInfo.getLocaleCookieName();
//	    if ( cookieName != null )
//	    {
//	        Date expires = new Date();
//	        expires.setYear( expires.getYear() + 1 );
//	        Cookies.setCookie( cookieName, locale, expires );
//	    }
//
//	}
	
	private class ClickHandlerAtualizar implements ClickHandler{
		
		public void onClick(ClickEvent event){
			System.out.println(GWT.getHostPageBaseURL());
			System.out.println(Window.Location.getQueryString());
			String urlParameter = Window.Location.getQueryString();
			
			String oldLocaleParameter = Window.Location.getParameter("locale");
			String newLocaleParameter = listBoxIdiomas.getValue(listBoxIdiomas.getSelectedIndex());
			
			
			
			if(oldLocaleParameter==null || oldLocaleParameter.isEmpty()){
				urlLocale = GWT.getHostPageBaseURL()+Window.Location.getQueryString()+"&locale="+newLocaleParameter;
			}else{
				urlParameter = urlParameter.replace("locale="+oldLocaleParameter, "locale="+newLocaleParameter);
				urlLocale = GWT.getHostPageBaseURL()+urlParameter;
			}
			mpLoading.setVisible(true);
//			setLocaleCookie(listBoxIdiomas.getValue(listBoxIdiomas.getSelectedIndex()));
	
//			usuario.getIdioma().setLocale(newLocaleParameter);
			GWTServiceIdioma.Util.getInstance().atualizarIdiomaUsuario(usuario.getIdUsuario(), newLocaleParameter, callbackAtualizarIdioma);
			
						
		}		
	}
	
		
	private class ClickHandlerCancelar implements ClickHandler{		
		public void onClick(ClickEvent event){
			DialogBoxIdioma.this.hide();			
		}		
	}	

}
