package com.jornada.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.ambiente.general.ajuda.DialogBoxSobre;
import com.jornada.client.ambiente.general.configuracao.DialogBoxIdioma;
import com.jornada.client.ambiente.general.configuracao.DialogBoxSenha;
import com.jornada.client.classes.resources.MyResources;
import com.jornada.client.classes.resources.MyTemplate;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceLogin;
import com.jornada.shared.classes.utility.MpUtilClient;
//import com.jornada.client.classes.resources.MyResources;

public class MainTitle extends Composite {
	
	
	private static final String SAIR = "SAIR"; 
	private static final String CONFIGURACAO_IDIOMA = "CONFIGURARAO_IDIOMA";
//	private static final String CONFIGURACAO_DESIGN = "CONFIGURARAO_DESIGN";
	private static final String CONFIGURACAO_SENHA = "CONFIGURARAO_SENHA";
	private static final String AJUDA_SOBRE= "AJUDA_SOBRE";
	private static final String AJUDA_CONTEUDO= "AJUDA_CONTEUDO";
	
	private AsyncCallback<Boolean> callBackSair;
	
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
//	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	
	
	private MainView mainView;
	
	private static MainTitle uniqueInstance;
	
	TextConstants txtConstants;
	
	public static MainTitle getInstance(MainView mainView){
		if(uniqueInstance==null){
			uniqueInstance = new MainTitle(mainView);
		}
		return uniqueInstance;
	}	
	
//	@SuppressWarnings("deprecation")
	private MainTitle(MainView mainView) {
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.mainView = mainView;
		
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		
		VerticalPanel verticalPanel = new VerticalPanel();			
		verticalPanel.setStyleName("titulo_tabela");
		
		HorizontalPanel horizontalPanelTop = new HorizontalPanel();
		horizontalPanelTop.setSpacing(3);
		horizontalPanelTop.setBorderWidth(0);
		horizontalPanelTop.setStyleName("tabela_mainview_titulo");
		horizontalPanelTop.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		initWidget(verticalPanel);
		verticalPanel.setSize("100%", "100px");
		horizontalPanelTop.setSize("100%", "40px");
		verticalPanel.add(horizontalPanelTop);
		
		Image image = new Image("images/Courses-128.png");
//		Image image = new Image("images/gwt.png");
		horizontalPanelTop.add(image);
		horizontalPanelTop.setCellHorizontalAlignment(image, HasHorizontalAlignment.ALIGN_CENTER);
		image.setSize("62px", "62px");
		
//		Label lblNewLabel = new Label("Ambiente Web de Apoio Escolar");
		Label lblNewLabel = new Label(txtConstants.titleNome());
		lblNewLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblNewLabel.setStyleName("MainViewTitleLabel");
		horizontalPanelTop.add(lblNewLabel);
		lblNewLabel.setWidth("631px");
		
		HorizontalPanel horizontalPanelDown = new HorizontalPanel();
//		horizontalPanelDown.setBorderWidth(1);
		verticalPanel.add(horizontalPanelDown);
		horizontalPanelDown.setSize("100%", "100%");
		
		Date today = new Date();
		
		Label lblWelcome = new Label(txtConstants.titleBemVindo(this.mainView.getUsuarioLogado().getPrimeiroNome(),this.mainView.getUsuarioLogado().getSobreNome()));
		Label lblSeparador = new Label("|");
		Label lblDate = new Label(MpUtilClient.convertDateToString(today));
		
		
		lblWelcome.setStyleName("MainViewSubTitleLabel");
		lblDate.setStyleName("MainViewSubTitleLabel");

		
		Grid gridEsquerda = new Grid(1,12);
		gridEsquerda.setCellSpacing(2);
		gridEsquerda.setCellPadding(2);
		{
			int column=0;
			gridEsquerda.setWidget(0, column++, lblWelcome);
			gridEsquerda.setWidget(0, column++, new InlineHTML("&nbsp;"));
			gridEsquerda.setWidget(0, column++, lblSeparador);
			gridEsquerda.setWidget(0, column++, new InlineHTML("&nbsp;"));
			gridEsquerda.setWidget(0, column++, lblDate);
		}
		
		horizontalPanelDown.add(gridEsquerda);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanelDown.add(horizontalPanel);
		horizontalPanelDown.setCellHorizontalAlignment(horizontalPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
//	    Command menuCommand = new Command() {
//	        private int curPhrase = 0;
//	        private final String[] phrases = {"Test1","Test2","Test3","Test4"};
//
//	        public void execute() {
//	          Window.alert(phrases[curPhrase]);
//	          curPhrase = (curPhrase + 1) % phrases.length;
//	        }
//	      };	    
	    
	      
		MyTemplate  template = GWT.create(MyTemplate .class);
		MyResources  resource = GWT.create(MyResources .class);
		
		SafeHtml safeHtmlConfiguracao = template.createItem(resource.iconGear().getSafeUri(),SafeHtmlUtils.fromString(txtConstants.titleConfiguracao()));
		SafeHtml safeHtmlAjuda = template.createItem(resource.iconHelp().getSafeUri(),SafeHtmlUtils.fromString(txtConstants.geralAjuda()));
		SafeHtml safeHtmlSair = template.createItem(resource.iconExit().getSafeUri(),SafeHtmlUtils.fromString(txtConstants.geralSair()));
		
		// Create a menu bar
	    MenuBar menu = new MenuBar();
	    menu.setStyleName("titulo_tabela_menu");
	    menu.setAutoOpen(false);
	    menu.setAnimationEnabled(true);
	    menu.setHeight("18px");		
//	    menu.setWidth("200px");
    	
	    // Create the file menu
	    MenuBar menuConfiguracao = new MenuBar(true); 
	    menuConfiguracao.setAnimationEnabled(true);
	    

	    
	    MenuBar menuAjuda = new MenuBar(true); 
	    menuAjuda.setAnimationEnabled(true);	 
	    
	    MenuBar menuSair = new MenuBar(true); 
	    menuSair.setAnimationEnabled(true);		
	    
	    
	    
	    menu.addItem(new MenuItem(safeHtmlConfiguracao, menuConfiguracao));
	    menu.addSeparator();menu.addSeparator();menu.addSeparator();
	    menu.addItem(new MenuItem(safeHtmlAjuda, menuAjuda));
	    menu.addSeparator();menu.addSeparator();menu.addSeparator();
	    menu.addItem(safeHtmlSair,new MenuBarCommand(SAIR));
	    
        menuConfiguracao.addItem(txtConstants.idioma(), new MenuBarCommand(CONFIGURACAO_IDIOMA));
        menuConfiguracao.addItem(txtConstants.senhaAlterar(), new MenuBarCommand(CONFIGURACAO_SENHA));
//        menuConfiguracao.addItem("Design", new MenuBarCommand(CONFIGURACAO_DESIGN));
        
        menuAjuda.addItem(txtConstants.ajudaConteudo(), new MenuBarCommand(AJUDA_CONTEUDO));
        menuAjuda.addSeparator();
        menuAjuda.addItem(txtConstants.ajudaSobre(), new MenuBarCommand(AJUDA_SOBRE));
	
	    
//	    String[] ajudaOptions = {txtConstants.ajudaConteudo(),txtConstants.ajudaSobre()};
//	    for (int i = 0; i < ajudaOptions.length; i++) {
//	    	if(i==ajudaOptions.length-1){
//	    		menuAjuda.addSeparator();
//	    	}
//	        menuAjuda.addItem(ajudaOptions[i], menuCommand);
//	    }		  
	    
		
		
		Grid gridDireita = new Grid(1,1);
		gridDireita.setCellSpacing(2);
		gridDireita.setCellPadding(2);
		{
			int column=0;
			gridDireita.setWidget(0, column++, menu);
		}
		horizontalPanelDown.add(gridDireita);
		horizontalPanelDown.setCellHorizontalAlignment(gridDireita, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		callBackSair = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {
				
				String strUrl = GWT.getHostPageBaseURL()+Window.Location.getQueryString();
				try {
					strUrl = strUrl.substring(0, strUrl.indexOf("?"));
					
				} catch (Exception ex) {
					strUrl = GWT.getHostPageBaseURL()+ Window.Location.getQueryString();
				}
				Window.Location.replace(strUrl);
//				Window.Location.reload();
			}

			public void onFailure(Throwable caught) {

				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.loginErroSairSistema());
				mpDialogBoxWarning.showDialog();
			}
		};		
		
		
	}
	
	
	public MainView getMainView() {
		return mainView;
	}

	
	
	private class MenuBarCommand implements Command
	   {
	      private String id;

	      public MenuBarCommand(String id)
	      {
	         this.id = id;
	      }

	      @Override
	      public void execute()
	      {
//	         Window.alert("Test:"+_id);
	    	  if(this.id.equals(SAIR)){
//	    		  Window.alert("Test:"+_id);
	    		  GWTServiceLogin.Util.getInstance().logout(callBackSair);			
	    	  }
	    	  else if(this.id.equals(CONFIGURACAO_IDIOMA)){
	    		  DialogBoxIdioma.getInstance(uniqueInstance.getMainView().getUsuarioLogado());
	    	  }
	    	  else if(this.id.equals(CONFIGURACAO_SENHA)){
	    		  DialogBoxSenha.getInstance(uniqueInstance.getMainView().getUsuarioLogado());
	    	  }	    	  
	    	  else if(this.id.equals(AJUDA_SOBRE)){
	    		  DialogBoxSobre.getInstance();
	    	  }	    	  

	      }
	   }
	
	


}
