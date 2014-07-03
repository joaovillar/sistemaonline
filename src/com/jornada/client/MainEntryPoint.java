package com.jornada.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.BrowserCloseDetector;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpPanelLogin;
import com.jornada.client.classes.widgets.textbox.MpTextBoxWithImage;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceLogin;
import com.jornada.shared.classes.Usuario;
//import com.jornada.client.classes.widgets.MpPanelLogin;
//import com.smartgwt.sample.showcase.client.data.CountryData;

public class MainEntryPoint implements EntryPoint {	
	
//	private AsyncCallback<Usuario> callbackAutenticarUsuario;

	private MpTextBoxWithImage textBoxUsername;
	private MpTextBoxWithImage textBoxPassword;
	
	private MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	private MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
	private MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");	
	
	private VerticalPanel vPanelPage;
	private MainView mainView;
	
	TextConstants txtConstants;
	

	public void onModuleLoad() {	
		
		
//		Window.addWindowClosingHandler(new ClosingHandler(){
//		    @Override 
//		    public void onWindowClosing(ClosingEvent event) {
//		    	System.out.println("If you leave, you may lose data. Continue?");
//		        event.setMessage("If you leave, you may lose data. Continue?");
//		    }
//		});
		

		
//		if (BrowserCloseDetector.get().wasClosed()) {
//			System.out.println("Browser was closed.");
//	    }
//	    else {
//	    	System.out.println("Refreshing or returning from another page.");
//	    }
		
		txtConstants = GWT.create(TextConstants.class);
	    
		vPanelPage = new VerticalPanel();
		vPanelPage.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPanelPage.setSize("100%", "100%");
		

		String sessionID;
		try {
			sessionID = Cookies.getCookie("sid");
		} catch (Exception ex) {
			sessionID = null;
		}
		
	    if (sessionID == null)
	    {
	    	displayLoginPage();
	    } else
	    {
//	    	verifyWindowsClose();
	        checkWithServerIfSessionIdIsStillLegal();
	    }
//		
//		vPanelPage.add(mainView);
		RootPanel.get().add(vPanelPage);		

	    
	  }
	
	
	public void verifyWindowsClose(){
//        Window.addWindowClosingHandler(new Window.ClosingHandler() {
//            @Override
//            public void onWindowClosing(ClosingEvent event) {
//
//                event.setMessage("You sure?");
//            }
//        });
//
//        Window.addCloseHandler(new CloseHandler<Window>() {
//            @Override
//            public void onClose(CloseEvent<Window> event) {
//            	System.out.println("Execute code when window closes!");
//            }
//        });
		
	if (BrowserCloseDetector.get().wasClosed()) {
		System.out.println("Browser was closed.");
    }
    else {
    	System.out.println("Refreshing or returning from another page.");
    }
	}
	
	
	
	private void createMainView(Usuario usuarioLogado){

		verifyWindowsClose();
		
		mainView = new MainView(usuarioLogado);		
		mainView.initHistorySupport();
    	vPanelPage.clear();
		vPanelPage.add(mainView);
	}
	
	
	
	public void displayLoginPage(){
		
		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);			
		
		MpPanelLogin mpPanelLogin = new MpPanelLogin(txtConstants.loginAcessarEscola(), "images/sign_in.png");

		mpPanelLogin.setWidth("25%");

		
//		Image image = new Image("images/sign_in.128.png");


		
		FlexTable flexTable = new FlexTable();
		mpPanelLogin.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
//		vLoginPanel.add(image);
		mpPanelLogin.add(flexTable);
		
		Label lblUsername = new Label(txtConstants.loginUser());
		Label lblPassword = new Label(txtConstants.loginSenha());
		
		textBoxUsername = new MpTextBoxWithImage(txtConstants.loginDigitarUser(),"textInputUser", false);
		textBoxPassword = new MpTextBoxWithImage(txtConstants.loginDigitarSenha(),"textInputPassword", true);
		
		
		lblUsername.setStyleName("design_label");		
		lblPassword.setStyleName("design_label");
		
		MpImageButton btnSignIn = new MpImageButton(txtConstants.loginLogar(),"images/image002.png");		
		textBoxUsername.addKeyDownHandler(new KeyDownHandlerLogin());		
		textBoxPassword.addKeyDownHandler(new KeyDownHandlerLogin());
		btnSignIn.addClickHandler(new ClickHandlerLogin());
		
		int row=0;
		
//		flexTable.getFlexCellFormatter().setRowSpan(0, 0, 10);
//		flexTable.setWidget(row++, 0, image);
		flexTable.setWidget(row++, 0, new InlineHTML("&nbsp;"));
		flexTable.setWidget(row++, 0, new InlineHTML("&nbsp;"));
//		flexTable.setWidget(row, 0, imgUser);
		flexTable.setWidget(row, 1, lblUsername);
		flexTable.setWidget(row++, 2, textBoxUsername);
//		flexTable.setWidget(row, 0, imgSenha);
		flexTable.setWidget(row, 1, lblPassword);
		flexTable.setWidget(row++, 2, textBoxPassword);
		flexTable.setWidget(row++, 2, btnSignIn);
//		flexTable.setWidget(row++, 3, mpPanelLoading);
		flexTable.setWidget(row, 0, new InlineHTML("&nbsp;"));
		flexTable.setWidget(row++, 2, mpPanelLoading);
		flexTable.setWidget(row++, 0, new InlineHTML("&nbsp;"));		

		
		
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setHeight("150px");
		HorizontalPanel hPanel2 = new HorizontalPanel();
		hPanel2.setHeight("150px");
		
//		vPanelPage.add(new MainTitle());

		vPanelPage.add(hPanel);
		vPanelPage.add(mpPanelLogin);
		vPanelPage.add(hPanel2);
		vPanelPage.add(new MainFooter());
		

	}
	
	
	 private void checkWithServerIfSessionIdIsStillLegal()
	 {
		 GWTServiceLogin.Util.getInstance().loginFromSessionServer(new AsyncCallback<Usuario>()
	     {
	         @Override
	         public void onFailure(Throwable caught)
	         {
	        	 displayLoginPage();
	         }
	  
	         @Override
	         public void onSuccess(Usuario user)
	         {
	             if (user == null)
	             {
	                 displayLoginPage();
//                 	mpPanelLoading.setVisible(false);
//                    Window.alert(txtConstants.loginAcessoNegado());
	             } else
	             {
	                 if (user.getLoggedIn())
	                 {

	                	 
	                	 if(isTheCorrectUserLanguage(user)){
	                		 createMainView(user);
	                	 }
	             	

	                 } else
	                 {
	                	 displayLoginPage();
	                 }
	             }
	         }
	  
	     });
	 }	
	
	
		private class KeyDownHandlerLogin implements KeyDownHandler {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					autenticarUsuario();					
				}
			}
		}	 
	 
	 
	 
	 private class ClickHandlerLogin implements ClickHandler{		 
		 public void onClick(ClickEvent event) {			 
			 autenticarUsuario();				
			}		 
	 }
	 
	 
	 
	 private void autenticarUsuario(){
		 
			mpPanelLoading.setVisible(true);
			if (textBoxUsername.getText().length() == 0	|| textBoxPassword.getText().length() == 0) {
				mpPanelLoading.setVisible(false);
					Window.alert(txtConstants.loginCampoVazio()); 
				}
			else{
				//authenticateUser(textBoxUsername.getText(), textBoxPassword.getText());
				GWTServiceLogin.Util.getInstance().loginServer(textBoxUsername.getValue(), textBoxPassword.getValue(), new AsyncCallback<Usuario>()
		                {
		                    @Override
		                    public void onSuccess(Usuario object)
		                    {
		                    	if(object==null){
		                        	mpPanelLoading.setVisible(false);
		                            Window.alert(txtConstants.loginAcessoNegado());
		                    	}
		                    	else if (object.getLoggedIn())
		                        {
		                        	mpPanelLoading.setVisible(false);
		                            //RootPanel.get().clear();
		                            // load the next app page
		                            //set session cookie for 1 day expiry.
		                            String sessionID = object.getSessionId();
		                            final long DURATION = 1000 * 60 * 60 * 24 * 1;
		                            Date expires = new Date(System.currentTimeMillis() + DURATION);
		                            Cookies.setCookie("sid", sessionID, expires, null, "/", false);			                            
		                       
		                            
			                        if(isTheCorrectUserLanguage(object)){
				                		 createMainView(object);
				                	 }
//		                            createMainView(object);
		                            
		                            
		                        } else{
		                        	mpPanelLoading.setVisible(false);
		                            Window.alert(txtConstants.loginAcessoNegado());
		                        }
		 
		                    }
		 
		                    @Override
		                    public void onFailure(Throwable caught)
		                    {
		                    	mpPanelLoading.setVisible(false);
		                        Window.alert(txtConstants.loginAcessoNegado());
		                    }
		                });					
			}		 
	 }
	 
	 
	 private boolean isTheCorrectUserLanguage(Usuario usuario){
		 boolean isCorrect=false;
		 
  		String browserLocale = Window.Location.getParameter("locale");
  		String userLocale = usuario.getIdioma().getLocale();
  		String urlParameter = Window.Location.getQueryString();
  		
			if(browserLocale==null || browserLocale.isEmpty()){
				String strURL = GWT.getHostPageBaseURL()+Window.Location.getQueryString();
				String strLocale="";
				if(strURL.contains("?")){
					strLocale="&locale=";
				}else{
					strLocale="?locale=";
				}
//				Window.Location.replace(GWT.getHostPageBaseURL()+Window.Location.getQueryString()+"&locale="+userLocale);
				Window.Location.replace(strURL+strLocale+userLocale);
			}else if(!browserLocale.equals(userLocale)){
				urlParameter = urlParameter.replace("locale="+browserLocale, "locale="+userLocale);
				Window.Location.replace(GWT.getHostPageBaseURL()+urlParameter);
			}
			else{
				isCorrect=true;
			}
		 
		 return isCorrect;
	 }
	
}


