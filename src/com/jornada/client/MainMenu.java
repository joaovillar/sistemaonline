package com.jornada.client;



import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.shared.classes.TipoUsuario;

@SuppressWarnings("rawtypes")
public class MainMenu extends Composite implements ValueChangeHandler {
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	
    public static final String MENU_TOKEN_PRINCIPAL = txtConstants.menuTokenPrincipal();
    private Hyperlink linkPrincipal;
    private Image imgPrincipal; 
    
    public static final String MENU_TOKEN_FERRAMENTA_COORDENADOR = txtConstants.menuTokenFerramentaCoordenador();
    private Hyperlink linkFerramentaCoordenador;
    private Image imgFerramentaCoordenador; 
    
    public static final String MENU_TOKEN_FERRAMENTA_COORDENADOR_COMUNICADO = txtConstants.menuTokenFerramentaCoordenadorComunicado();
    private Hyperlink linkFerramentaCoordenadorComunicado;
    private Image imgFerramentaCoordenadorComunicado;     
    
    public static final String MENU_TOKEN_FERRAMENTA_COORDENADOR_CURSO = txtConstants.menuTokenFerramentaCoordenadorCurso();
    private Hyperlink linkFerramentaCoordenadorCurso;
    private Image imgFerramentaCoordenadorCurso;
    
    public static final String MENU_TOKEN_FERRAMENTA_COORDENADOR_PERIODO = txtConstants.menuTokenFerramentaCoordenadorPeriodo();
    private Hyperlink linkFerramentaCoordenadorPeriodo;
    private Image imgFerramentaCoordenadorPeriodo;
    
    public static final String MENU_TOKEN_FERRAMENTA_COORDENADOR_DISCIPLINA = txtConstants.menuTokenFerramentaCoordenadorDisciplina();
    private Hyperlink linkFerramentaCoordenadorDisciplina;
    private Image imgFerramentaCoordenadorDisciplina;

    public static final String MENU_TOKEN_FERRAMENTA_COORDENADOR_CONTEUDO_PROGRAMATICO = txtConstants.menuTokenFerramentaCoordenadorConteudoProgramatico();
    private Hyperlink linkFerramentaCoordenadorConteudoProgramatico;
    private Image imgFerramentaCoordenadorConteudoProgramatico;
    
    public static final String MENU_TOKEN_FERRAMENTA_COORDENADOR_TOPICO = txtConstants.menuTokenFerramentaCoordenadorTopico();
    private Hyperlink linkFerramentaCoordenadorTopico;
    private Image imgFerramentaCoordenadorTopico;  
    
    public static final String MENU_TOKEN_FERRAMENTA_COORDENADOR_HIERARQUIA = txtConstants.menuTokenFerramentaCoordenadorHierarquia();
    private Hyperlink linkFerramentaCoordenadorHierarquia;
    private Image imgFerramentaCoordenadorHierarquia;      
    
    public static final String MENU_TOKEN_FERRAMENTA_COORDENADOR_USUARIO = txtConstants.menuTokenFerramentaCoordenadorUsuario();
    private Hyperlink linkFerramentaCoordenadorUsuario;
    private Image imgFerramentaCoordenadorUsuario;    
    
    public static final String MENU_TOKEN_FERRAMENTA_COORDENADOR_DIARIO = txtConstants.menuTokenFerramentaCoordenadorDiario();
    private Hyperlink linkFerramentaCoordenadorDiario;
    private Image imgFerramentaCoordenadorDiario;         
    
    public static final String MENU_TOKEN_FERRAMENTA_PROFESSOR = txtConstants.menuTokenFerramentaProfessor();
    private Hyperlink linkFerramentaProfessor;
    private Image imgFerramentaProfessor;     

    public static final String MENU_TOKEN_FERRAMENTA_PROFESSOR_AVALIACAO = txtConstants.menuTokenFerramentaProfessorAvaliacao();
    private Hyperlink linkFerramentaProfessorAvaliacao;
    private Image imgFerramentaProfessorAvaliacao;      
    
    public static final String MENU_TOKEN_FERRAMENTA_PROFESSOR_NOTA = txtConstants.menuTokenFerramentaProfessorNota();
    private Hyperlink linkFerramentaProfessorNota;
    private Image imgFerramentaProfessorNota;       
    
    public static final String MENU_TOKEN_FERRAMENTA_PROFESSOR_TOPICO = txtConstants.menuTokenFerramentaProfessorTopico();
    private Hyperlink linkProfessorTopico;
    private Image imgProfessorTopico;
    
    public static final String MENU_TOKEN_FERRAMENTA_PROFESSOR_COMUNICADO = txtConstants.menuTokenFerramentaProfessorComunicado();
    private Hyperlink linkProfessorComunicado;
    private Image imgProfessorComunicado;   
    
    public static final String MENU_TOKEN_FERRAMENTA_PROFESSOR_OCORRENCIA = txtConstants.menuTokenFerramentaProfessorOcorrencia();
    private Hyperlink linkProfessorOcorrencia;
    private Image imgProfessorOcorrencia;       
    
    public static final String MENU_TOKEN_FERRAMENTA_PROFESSOR_HIERARQUIA = txtConstants.menuTokenFerramentaProfessorHierarquia();
    private Hyperlink linkFerramentaProfessorHierarquia;
    private Image imgFerramentaProfessorHierarquia;        
    
    public static final String MENU_TOKEN_FERRAMENTA_PROFESSOR_DIARIO = txtConstants.menuTokenFerramentaProfessorDiario();
    private Hyperlink linkFerramentaProfessorDiario;
    private Image imgFerramentaProfessorDiario;     
    
    
    public static final String MENU_TOKEN_FERRAMENTA_ALUNO = txtConstants.menuTokenFerramentaAluno();
    private Hyperlink linkFerramentaAluno;
    private Image imgFerramentaAluno;       
    
    public static final String MENU_TOKEN_FERRAMENTA_ALUNO_NOTA = txtConstants.menuTokenFerramentaAlunoNota();
    private Hyperlink linkFerramentaAlunoNota;
    private Image imgFerramentaAlunoNota;   
    
    public static final String MENU_TOKEN_FERRAMENTA_ALUNO_AGENDA = txtConstants.menuTokenFerramentaAlunoAgenda();
    private Hyperlink linkFerramentaAlunoAvaliacao;
    private Image imgFerramentaAlunoAvaliacao;          
    
    public static final String MENU_TOKEN_FERRAMENTA_ALUNO_COMUNICADO =txtConstants.menuTokenFerramentaAlunoComunicado();
    private Hyperlink linkFerramentaAlunoComunicado;
    private Image imgFerramentaAlunoComunicado;    
    
    public static final String MENU_TOKEN_FERRAMENTA_ALUNO_OCORRENCIA = txtConstants.menuTokenFerramentaAlunoOcorrencia();
    private Hyperlink linkAlunoOcorrencia;
    private Image imgAlunoOcorrencia;     
    
    public static final String MENU_TOKEN_FERRAMENTA_ALUNO_HIERARQUIA = txtConstants.menuTokenFerramentaAlunoHierarquia();
    private Hyperlink linkFerramentaAlunoHierarquia;
    private Image imgFerramentaAlunoHierarquia;    
    
    
    public static final String MENU_TOKEN_FERRAMENTA_ALUNO_DIARIO = txtConstants.menuTokenFerramentaAlunoDiario();
    private Hyperlink linkFerramentaAlunoDiario;
    private Image imgFerramentaAlunoDiario;        
    
    public static final String MENU_TOKEN_FERRAMENTA_PAIS = txtConstants.menuTokenFerramentaPais();
    private Hyperlink linkFerramentaPais;
    private Image imgFerramentaPais;     
    
    public static final String MENU_TOKEN_FERRAMENTA_PAIS_AGENDA = txtConstants.menuTokenFerramentaPaisAgenda();
    private Hyperlink linkFerramentaPaisAvaliacao;
    private Image imgFerramentaPaisAvaliacao;    
    
    public static final String MENU_TOKEN_FERRAMENTA_PAIS_COMUNICADO = txtConstants.menuTokenFerramentaPaisComunicado();
    private Hyperlink linkFerramentaPaisComunicado;
    private Image imgFerramentaPaisComunicado;      
    
    public static final String MENU_TOKEN_FERRAMENTA_PAIS_OCORRENCIA = txtConstants.menuTokenFerramentaPaisOcorrencia();
    private Hyperlink linkPaisOcorrencia;
    private Image imgPaisOcorrencia;      
    
    public static final String MENU_TOKEN_FERRAMENTA_PAIS_NOTA = txtConstants.menuTokenFerramentaPaisNota();
    private Hyperlink linkFerramentaPaisNota;
    private Image imgFerramentaPaisNota;   
    
    public static final String MENU_TOKEN_FERRAMENTA_PAIS_HIERARQUIA = txtConstants.menuTokenFerramentaPaisHierarquia();
    private Hyperlink linkFerramentaPaisHierarquia;
    private Image imgFerramentaPaisHierarquia; 
    
    public static final String MENU_TOKEN_FERRAMENTA_PAIS_DIARIO = txtConstants.menuTokenFerramentaPaisDiario();
    private Hyperlink linkFerramentaPaisDiario;
    private Image imgFerramentaPaisDiario;  
    
    public static final String MENU_TOKEN_SAIR = "SAIR";
    
    //This check is to avoid the application update data twice
    //For somehow the History is raising the event twice
    //This variable control to allow just one reload.
    public static boolean isFirstEventFire=true;
    
    
    private HorizontalPanel hPanel;	
	
	private MainView mainView;
	
    
    
	
	@SuppressWarnings("unchecked")
	public MainMenu(MainView mainView){
		
		this.mainView = mainView;
		
	    History.addValueChangeHandler(this);
	    
        hPanel = new HorizontalPanel();
        hPanel.setStyleName("titulo_tabela");
        hPanel.setHeight("20px");      
        
        VerticalPanel vPanel = new VerticalPanel();
        HorizontalPanel vPanelBlankTop = new HorizontalPanel();
        vPanelBlankTop.setSize("15px", "10px");
//        vPanelBlankTop.setBorderWidth(1);
        HorizontalPanel vPanelBlankDown = new HorizontalPanel();
        vPanelBlankDown.setSize("15px", "10px");
//        vPanelBlankDown.setBorderWidth(1);
        
        vPanel.add(vPanelBlankTop);        
        vPanel.add(hPanel);
//        vPanel.add(new InlineHTML("&nbsp"));
        vPanel.add(vPanelBlankDown);     
//        vPanel.setBorderWidth(5);

        initWidget(vPanel);	    
		
	}
	
	@Override
	public void onValueChange(ValueChangeEvent event) {		
		if(isFirstEventFire==true){
			changePage(History.getToken());		
		} 
	}	
	
	public void changePage(String token) {
//		History.newItem(token);
		if(History.getToken().equals(MENU_TOKEN_PRINCIPAL)) {
			this.linkPaginaPrincipal(false);
			mainView.openMainView();
		} else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_COORDENADOR)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaCoordenador(false);
			mainView.openAdminEscola();		
		} else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_COORDENADOR_CURSO)) {
				
				this.linkPaginaPrincipal(true);
				this.linkFerramentaCoordenador(true);
				this.linkFerramentaCoordenadorCurso();
				 if(isFirstEventFire == true){
					 isFirstEventFire = false;
					 mainView.openCadastroCurso();
				 }
				
		} else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_COORDENADOR_PERIODO)) {
				
				this.linkPaginaPrincipal(true);
				this.linkFerramentaCoordenador(true);
				this.linkFerramentaCoordenadorPeriodo();
				if (isFirstEventFire == true) {
					isFirstEventFire = false;
					mainView.openCadastroPeriodo();
				}

		} else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_COORDENADOR_DISCIPLINA)) {
				this.linkPaginaPrincipal(true);
				this.linkFerramentaCoordenador(true);
				this.linkFerramentaCoordenadorDisciplina();
				if (isFirstEventFire == true) {
					isFirstEventFire = false;
					mainView.openCadastroDisciplina();
				}

		} else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_COORDENADOR_CONTEUDO_PROGRAMATICO)) {
				this.linkPaginaPrincipal(true);
				this.linkFerramentaCoordenador(true);
				this.linkFerramentaCoordenadorConteudoProgramatico();
				if (isFirstEventFire == true) {
					isFirstEventFire = false;
					mainView.openCadastroConteudoProgramatico();
				}				
			
		}	
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_COORDENADOR_TOPICO)) {

			this.linkPaginaPrincipal(true);
			this.linkFerramentaCoordenador(true);
			this.linkFerramentaCoordenadorTopico();
	
			if (isFirstEventFire == true) {
				isFirstEventFire = false;
				mainView.openCadastroAdminTopico();
			}				
			
		}	
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_COORDENADOR_HIERARQUIA)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaCoordenador(true);
			this.linkFerramentaCoordenadorHierarquia();
			mainView.openCadastroAdminHierarquia();
		}			
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_COORDENADOR_USUARIO)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaCoordenador(true);
			this.linkFerramentaCoordenadorUsuario();
			mainView.openCadastroUsuario();	
		}
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_COORDENADOR_COMUNICADO)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaCoordenador(true);
			this.linkFerramentaCoordenadorComunicados();
			mainView.openCadastroComunicado();	
		}
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_COORDENADOR_DIARIO)) {
		
			this.linkPaginaPrincipal(true);			
			this.linkFerramentaCoordenador(true);
			this.linkFerramentaCoordenadorDiario();
			if (isFirstEventFire == true) {
				isFirstEventFire = false;
				mainView.openFerramentaCoordenadorDiario();
			}						

		}		
		
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_PROFESSOR)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaProfessor(false);
			mainView.openFerramentaProfessor();
		}		
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_PROFESSOR_AVALIACAO)) {
			this.linkPaginaPrincipal(true);			
			this.linkFerramentaProfessor(true);
			this.linkFerramentaProfessorAvaliacao();			
			
			if (isFirstEventFire == true) {
				isFirstEventFire = false;
				mainView.openFerramentaProfessorAvaliacao();
			}		
		}		
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_PROFESSOR_NOTA)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaProfessor(true);
			this.linkFerramentaProfessorNota();		
			
			if (isFirstEventFire == true) {
				isFirstEventFire = false;
				mainView.openFerramentaProfessorNota();
			}				
			
		}
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_PROFESSOR_TOPICO)) {
			this.linkPaginaPrincipal(true);			
			this.linkFerramentaProfessor(true);
			this.linkProfessorTopico();
				
			if (isFirstEventFire == true) {
				isFirstEventFire = false;
				mainView.openCadastroProfessorTopico();
			}	
		}	
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_PROFESSOR_COMUNICADO)) {
			this.linkPaginaPrincipal(true);			
			this.linkFerramentaProfessor(true);
			this.linkProfessorComunicado();
			if (isFirstEventFire == true) {
				isFirstEventFire = false;
				mainView.openProfessorComunicado();
			}					
		}				
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_PROFESSOR_OCORRENCIA)) {
			this.linkPaginaPrincipal(true);			
			this.linkFerramentaProfessor(true);
			this.linkProfessorOcorrencia();
			if (isFirstEventFire == true) {
				isFirstEventFire = false;
				mainView.openCadastroProfessorOcorrencia();
			}	
				
		}		
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_PROFESSOR_HIERARQUIA)) {
			this.linkPaginaPrincipal(true);			
			this.linkFerramentaProfessor(true);
			this.linkFerramentaProfessorHierarquia();			
			mainView.openFerramentaProfessorHierarquia();
		}	
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_PROFESSOR_DIARIO)) {
			this.linkPaginaPrincipal(true);			
			this.linkFerramentaProfessor(true);
			this.linkFerramentaProfessorDiario();		
			
			if (isFirstEventFire == true) {
				isFirstEventFire = false;
				mainView.openFerramentaProfessorDiario();
			}
		}		
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_ALUNO)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaAluno(false);
			mainView.openFerramentaAluno();
		}
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_ALUNO_NOTA)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaAluno(true);
			this.linkFerramentaAlunoNota();			
			
			if (isFirstEventFire == true) {
				isFirstEventFire = false;
				mainView.openFerramentaAlunoNota();
			}
		}	
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_ALUNO_AGENDA)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaAluno(true);
			this.linkFerramentaAlunoAgenda();			
			
			if (isFirstEventFire == true) {
				isFirstEventFire = false;
				mainView.openFerramentaAlunoAgenda();
			}

		}		
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_ALUNO_COMUNICADO)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaAluno(true);
			this.linkFerramentaAlunoComunicado();			
			mainView.openFerramentaAlunoComunicado();
		}		
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_ALUNO_OCORRENCIA)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaAluno(true);
			this.linkAlunoOcorrencia();
			
			if (isFirstEventFire == true) {
				isFirstEventFire = false;
				mainView.openCadastroAlunoOcorrencia();
			}
		}			
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_ALUNO_HIERARQUIA)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaAluno(true);
			this.linkFerramentaAlunoHierarquia();			
			mainView.openFerramentaAlunoHierarquia();
		}
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_ALUNO_DIARIO)) {
			this.linkPaginaPrincipal(true);			
			this.linkFerramentaAluno(true);
			this.linkFerramentaAlunoDiario();			
			
			if (isFirstEventFire == true) {
				isFirstEventFire = false;
				mainView.openFerramentaAlunoDiario();
			}

		}
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_PAIS)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaPais(false);
			mainView.openFerramentaPais();
		}		
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_PAIS_AGENDA)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaPais(true);
			this.linkFerramentaPaisAgenda();			
			
			if (isFirstEventFire == true) {
				isFirstEventFire = false;
				mainView.openFerramentaPaisAgenda();
			}
		}			
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_PAIS_COMUNICADO)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaPais(true);
			this.linkFerramentaPaisComunicado();			
			mainView.openFerramentaPaisComunicado();
		}		
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_PAIS_NOTA)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaPais(true);
			this.linkFerramentaPaisNota();			
			
			if (isFirstEventFire == true) {
				isFirstEventFire = false;
				mainView.openFerramentaPaisNota();
			}

		}	
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_PAIS_OCORRENCIA)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaPais(true);
			this.linkPaisOcorrencia();
			
			if (isFirstEventFire == true) {
				isFirstEventFire = false;
				mainView.openCadastroPaisOcorrencia();
			}

		}		
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_PAIS_HIERARQUIA)) {
			this.linkPaginaPrincipal(true);
			this.linkFerramentaPais(true);
			this.linkFerramentaPaisHierarquia();			
			mainView.openFerramentaPaisHierarquia();
		}
		else if (History.getToken().equals(MENU_TOKEN_FERRAMENTA_PAIS_DIARIO)) {
			this.linkPaginaPrincipal(true);			
			this.linkFerramentaPais(true);
			this.linkFerramentaPaisDiario();			
			
			if (isFirstEventFire == true) {
				isFirstEventFire = false;
				mainView.openFerramentaPaisDiario();
			}

		}		
		else{

			this.linkPaginaPrincipal(false);
			
			if(mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.ALUNO){
				this.linkFerramentaAluno(false);
			}
			else if(mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.PAIS){
				this.linkFerramentaPais(false);
			}
			else if(mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.PROFESSOR){
				this.linkFerramentaProfessor(false);
			}
			else if(mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.COORDENADOR){
				this.linkFerramentaCoordenador(false);
			}			

		}
		
	}
	
	
	public void connectionLabel(){

		Label lblConnectionLabel = new Label(" >> ");
        hPanel.add(new InlineHTML("&nbsp"));        	
        hPanel.add(lblConnectionLabel);
        hPanel.add(new InlineHTML("&nbsp"));		
		
	}
	

	
	public void linkPaginaPrincipal(boolean showConnectionLabel) {

		hPanel.clear();
		
		if (mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.ADMINISTRADOR) {

			imgPrincipal = new Image("images/home-icon.png");
			linkPrincipal = new Hyperlink(txtConstants.principal(), MENU_TOKEN_PRINCIPAL);
			linkPrincipal.setStyleName("a");

			hPanel.add(imgPrincipal);
			hPanel.add(new InlineHTML("&nbsp"));
			hPanel.add(linkPrincipal);

			if (showConnectionLabel == true) {
				this.connectionLabel();
			}
		}

	}
	
	
	public void linkFerramentaCoordenador(boolean showConnectionLabel){
		
	    imgFerramentaCoordenador = new Image("images/manager_16.png");
	    linkFerramentaCoordenador = new Hyperlink(txtConstants.coordenadorAmbiente(), MENU_TOKEN_FERRAMENTA_COORDENADOR);
	    linkFerramentaCoordenador.setStyleName("a");		
	    
        hPanel.add(imgFerramentaCoordenador);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaCoordenador);	
        
        if(showConnectionLabel==true){
        	this.connectionLabel();        	
        }
		
	}
	
	
	public void linkFerramentaCoordenadorCurso(){		
		
	    imgFerramentaCoordenadorCurso = new Image("images/curso.png");
	    linkFerramentaCoordenadorCurso = new Hyperlink(txtConstants.curso(), MENU_TOKEN_FERRAMENTA_COORDENADOR_CURSO);	    
	    linkFerramentaCoordenadorCurso.setStyleName("a");
	    
        hPanel.add(imgFerramentaCoordenadorCurso);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaCoordenadorCurso);
	
	}		
	
	public void linkFerramentaCoordenadorPeriodo(){

		imgFerramentaCoordenadorPeriodo = new Image("images/my_projects_folder_16.png");
	    linkFerramentaCoordenadorPeriodo = new Hyperlink(txtConstants.coordenadorAmbientePeriodo(), MENU_TOKEN_FERRAMENTA_COORDENADOR_PERIODO);
	    linkFerramentaCoordenadorPeriodo.setStyleName("a");
	    
        hPanel.add(imgFerramentaCoordenadorPeriodo);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaCoordenadorPeriodo);

	}		


	public void linkFerramentaCoordenadorDisciplina(){

	    imgFerramentaCoordenadorDisciplina = new Image("images/disciplina.png");
	    linkFerramentaCoordenadorDisciplina = new Hyperlink(txtConstants.coordenadorAmbienteDisciplina(), MENU_TOKEN_FERRAMENTA_COORDENADOR_DISCIPLINA);
	    linkFerramentaCoordenadorDisciplina.setStyleName("a");
	    
        hPanel.add(imgFerramentaCoordenadorDisciplina);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaCoordenadorDisciplina);

	}	
	
	public void linkFerramentaCoordenadorConteudoProgramatico(){

	    imgFerramentaCoordenadorConteudoProgramatico = new Image("images/conteudoprogramatico.png");
	    linkFerramentaCoordenadorConteudoProgramatico = new Hyperlink(txtConstants.coordenadorAmbienteConteudoProgramatico(), MENU_TOKEN_FERRAMENTA_COORDENADOR_CONTEUDO_PROGRAMATICO);
	    linkFerramentaCoordenadorConteudoProgramatico.setStyleName("a");
	    
        hPanel.add(imgFerramentaCoordenadorConteudoProgramatico);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaCoordenadorConteudoProgramatico);

	}	
	
	public void linkFerramentaCoordenadorTopico(){

	    imgFerramentaCoordenadorTopico = new Image("images/type_list_16_16_v1.png");
	    linkFerramentaCoordenadorTopico = new Hyperlink(txtConstants.coordenadorAmbienteTopico(), MENU_TOKEN_FERRAMENTA_COORDENADOR_TOPICO);
	    linkFerramentaCoordenadorTopico.setStyleName("a");
	    
        hPanel.add(imgFerramentaCoordenadorTopico);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaCoordenadorTopico);

	}	
	
	public void linkFerramentaCoordenadorHierarquia(){

	    imgFerramentaCoordenadorHierarquia = new Image("images/catalog-icon-16.png");
	    linkFerramentaCoordenadorHierarquia = new Hyperlink(txtConstants.coordenadorAmbienteHierarquia(), MENU_TOKEN_FERRAMENTA_COORDENADOR_HIERARQUIA);
	    linkFerramentaCoordenadorHierarquia.setStyleName("a");
	    
        hPanel.add(imgFerramentaCoordenadorHierarquia);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaCoordenadorHierarquia);

	}		
	
	public void linkFerramentaCoordenadorUsuario(){

	    imgFerramentaCoordenadorUsuario = new Image("images/Actions-list-add-user-icon_16.png");
	    linkFerramentaCoordenadorUsuario = new Hyperlink(txtConstants.coordenadorAmbienteUsuario(), MENU_TOKEN_FERRAMENTA_COORDENADOR_USUARIO);
	    linkFerramentaCoordenadorUsuario.setStyleName("a");
	    
        hPanel.add(imgFerramentaCoordenadorUsuario);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaCoordenadorUsuario);

	}		
	
	public void linkFerramentaCoordenadorComunicados(){

	    imgFerramentaCoordenadorComunicado = new Image("images/notes_16.png");
	    linkFerramentaCoordenadorComunicado = new Hyperlink(txtConstants.coordenadorAmbienteComunicado(), MENU_TOKEN_FERRAMENTA_COORDENADOR_COMUNICADO);
	    linkFerramentaCoordenadorComunicado.setStyleName("a");
	    
        hPanel.add(imgFerramentaCoordenadorComunicado);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaCoordenadorComunicado);

	}	
	
	public void linkFerramentaCoordenadorDiario(){
	    imgFerramentaCoordenadorDiario = new Image("images/programs_group_ii.16.png");
	    linkFerramentaCoordenadorDiario = new Hyperlink(txtConstants.presencaAmbienteDiario(), MENU_TOKEN_FERRAMENTA_COORDENADOR_DIARIO);
	    linkFerramentaCoordenadorDiario.setStyleName("a");
	    
        hPanel.add(imgFerramentaCoordenadorDiario);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaCoordenadorDiario);
	}		
	
	public void linkFerramentaProfessor(boolean showConnectionLabel){

	    imgFerramentaProfessor = new Image("images/professor_16.png");
	    linkFerramentaProfessor = new Hyperlink(txtConstants.professorAmbiente(), MENU_TOKEN_FERRAMENTA_PROFESSOR);
	    linkFerramentaProfessor.setStyleName("a");
	    
        hPanel.add(imgFerramentaProfessor);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaProfessor);
        
        if(showConnectionLabel==true){
        	this.connectionLabel();
        }

	}	
	
	public void linkFerramentaProfessorAvaliacao(){

	    imgFerramentaProfessorAvaliacao = new Image("images/korganizer-icon_16.png");
	    linkFerramentaProfessorAvaliacao = new Hyperlink(txtConstants.professorAmbienteAvaliacao(), MENU_TOKEN_FERRAMENTA_PROFESSOR_AVALIACAO);
	    linkFerramentaProfessorAvaliacao.setStyleName("a");
	    
        hPanel.add(imgFerramentaProfessorAvaliacao);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaProfessorAvaliacao);

	}		
	
	public void linkFerramentaProfessorNota(){
	    imgFerramentaProfessorNota = new Image("images/Test-paper-16.png");
	    linkFerramentaProfessorNota = new Hyperlink(txtConstants.professorAmbienteNotas(), MENU_TOKEN_FERRAMENTA_PROFESSOR_NOTA);
	    linkFerramentaProfessorNota.setStyleName("a");
	    
        hPanel.add(imgFerramentaProfessorNota);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaProfessorNota);
	}
	
	public void linkProfessorTopico(){

	    imgProfessorTopico = new Image("images/type_list_16_16_v1.png");
	    linkProfessorTopico = new Hyperlink(txtConstants.professorAmbienteTopico(), MENU_TOKEN_FERRAMENTA_PROFESSOR_TOPICO);
	    linkProfessorTopico.setStyleName("a");
	    
        hPanel.add(imgProfessorTopico);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkProfessorTopico);

	}	
	
	public void linkProfessorComunicado(){

	    imgProfessorComunicado = new Image("images/notes_16.png");
	    linkProfessorComunicado = new Hyperlink(txtConstants.professorAmbienteComunicado(), MENU_TOKEN_FERRAMENTA_PROFESSOR_COMUNICADO);
	    linkProfessorComunicado.setStyleName("a");
	    
        hPanel.add(imgProfessorComunicado);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkProfessorComunicado);

	}		
	
	public void linkProfessorOcorrencia(){

	    imgProfessorOcorrencia = new Image("images/ocorrencia_16.png");
	    linkProfessorOcorrencia = new Hyperlink(txtConstants.professorAmbienteOcorrencia(), MENU_TOKEN_FERRAMENTA_PROFESSOR_OCORRENCIA);
	    linkProfessorOcorrencia.setStyleName("a");
	    
        hPanel.add(imgProfessorOcorrencia);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkProfessorOcorrencia);

	}	
	
	public void linkFerramentaProfessorHierarquia(){
	    imgFerramentaProfessorHierarquia = new Image("images/catalog-icon-16.png");
	    linkFerramentaProfessorHierarquia = new Hyperlink(txtConstants.professorAmbienteHierarquia(), MENU_TOKEN_FERRAMENTA_PROFESSOR_HIERARQUIA);
	    linkFerramentaProfessorHierarquia.setStyleName("a");
	    
        hPanel.add(imgFerramentaProfessorHierarquia);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaProfessorHierarquia);
	}	
	
	public void linkFerramentaProfessorDiario(){
	    imgFerramentaProfessorDiario = new Image("images/programs_group_ii.16.png");
	    linkFerramentaProfessorDiario = new Hyperlink(txtConstants.presencaAmbienteDiario(), MENU_TOKEN_FERRAMENTA_PROFESSOR_DIARIO);
	    linkFerramentaProfessorDiario.setStyleName("a");
	    
        hPanel.add(imgFerramentaProfessorDiario);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaProfessorDiario);
	}	
	
	public void linkFerramentaAluno(boolean showConnectionLabel){

	    imgFerramentaAluno = new Image("images/elementary_school_16.png");
	    linkFerramentaAluno = new Hyperlink(txtConstants.alunoAmbiente(), MENU_TOKEN_FERRAMENTA_ALUNO);
	    linkFerramentaAluno.setStyleName("a");
	    
        hPanel.add(imgFerramentaAluno);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaAluno);
        
        if(showConnectionLabel==true){
        	this.connectionLabel();
        }

	}		
	
	public void linkFerramentaAlunoNota(){
	    imgFerramentaAlunoNota = new Image("images/chart-icon_16.png");
	    linkFerramentaAlunoNota = new Hyperlink(txtConstants.alunoAmbienteNotas(), MENU_TOKEN_FERRAMENTA_ALUNO_NOTA);
	    linkFerramentaAlunoNota.setStyleName("a");
	    
        hPanel.add(imgFerramentaAlunoNota);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaAlunoNota);
	}	
	
	public void linkFerramentaAlunoAgenda(){
	    imgFerramentaAlunoAvaliacao = new Image("images/config_date_16.png");
	    linkFerramentaAlunoAvaliacao = new Hyperlink(txtConstants.alunoAmbienteAgenda(), MENU_TOKEN_FERRAMENTA_ALUNO_AGENDA);
	    linkFerramentaAlunoAvaliacao.setStyleName("a");
	    
        hPanel.add(imgFerramentaAlunoAvaliacao);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaAlunoAvaliacao);
	}	
	
	public void linkFerramentaAlunoComunicado(){
	    imgFerramentaAlunoComunicado = new Image("images/notes_16.png");
	    linkFerramentaAlunoComunicado = new Hyperlink(txtConstants.alunoAmbienteComunicado(), MENU_TOKEN_FERRAMENTA_ALUNO_COMUNICADO);
	    linkFerramentaAlunoComunicado.setStyleName("a");
	    
        hPanel.add(imgFerramentaAlunoComunicado);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaAlunoComunicado);
	}	
	
	public void linkAlunoOcorrencia(){

	    imgAlunoOcorrencia = new Image("images/ocorrencia_16.png");
	    linkAlunoOcorrencia = new Hyperlink(txtConstants.alunoAmbienteOcorrencia(), MENU_TOKEN_FERRAMENTA_ALUNO_OCORRENCIA);
	    linkAlunoOcorrencia.setStyleName("a");
	    
        hPanel.add(imgAlunoOcorrencia);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkAlunoOcorrencia);

	}		

	public void linkFerramentaAlunoHierarquia(){
	    imgFerramentaAlunoHierarquia = new Image("images/catalog-icon-16.png");
	    linkFerramentaAlunoHierarquia = new Hyperlink(txtConstants.alunoAmbienteHierarquia(), MENU_TOKEN_FERRAMENTA_ALUNO_HIERARQUIA);
	    linkFerramentaAlunoHierarquia.setStyleName("a");
	    
        hPanel.add(imgFerramentaAlunoHierarquia);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaAlunoHierarquia);
	}	
	
	public void linkFerramentaAlunoDiario(){
	    imgFerramentaAlunoDiario = new Image("images/programs_group_ii.16.png");
	    linkFerramentaAlunoDiario = new Hyperlink(txtConstants.presencaAmbienteDiario(), MENU_TOKEN_FERRAMENTA_ALUNO_DIARIO);
	    linkFerramentaAlunoDiario.setStyleName("a");
	    
        hPanel.add(imgFerramentaAlunoDiario);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaAlunoDiario);
	}		
	
	public void linkFerramentaPais(boolean showConnectionLabel){

	    imgFerramentaPais = new Image("images/parents-16.png");
	    linkFerramentaPais = new Hyperlink(txtConstants.paisAmbiente(), MENU_TOKEN_FERRAMENTA_PAIS);
	    linkFerramentaPais.setStyleName("a");
	    
        hPanel.add(imgFerramentaPais);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaPais);
        
        if(showConnectionLabel==true){
        	this.connectionLabel();
        }

	}		
	
	public void linkFerramentaPaisAgenda(){
	    imgFerramentaPaisAvaliacao = new Image("images/config_date_16.png");
	    linkFerramentaPaisAvaliacao = new Hyperlink(txtConstants.paisAmbienteAgenda(), MENU_TOKEN_FERRAMENTA_PAIS_AGENDA);
	    linkFerramentaPaisAvaliacao.setStyleName("a");
	    
        hPanel.add(imgFerramentaPaisAvaliacao);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaPaisAvaliacao);
	}	
	
	public void linkFerramentaPaisComunicado(){
	    imgFerramentaPaisComunicado = new Image("images/notes_16.png");
	    linkFerramentaPaisComunicado = new Hyperlink(txtConstants.paisAmbienteComunicado(), MENU_TOKEN_FERRAMENTA_PAIS_COMUNICADO);
	    linkFerramentaPaisComunicado.setStyleName("a");
	    
        hPanel.add(imgFerramentaPaisComunicado);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaPaisComunicado);
	}		
	
	public void linkFerramentaPaisNota(){
	    imgFerramentaPaisNota = new Image("images/chart-icon_16.png");
	    linkFerramentaPaisNota = new Hyperlink(txtConstants.paisAmbienteNota(), MENU_TOKEN_FERRAMENTA_PAIS_NOTA);
	    linkFerramentaPaisNota.setStyleName("a");
	    
        hPanel.add(imgFerramentaPaisNota);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaPaisNota);
	}	
	
	public void linkPaisOcorrencia(){

	    imgPaisOcorrencia = new Image("images/ocorrencia_16.png");
	    linkPaisOcorrencia = new Hyperlink(txtConstants.paisAmbienteOcorrencia(), MENU_TOKEN_FERRAMENTA_PAIS_OCORRENCIA);
	    linkPaisOcorrencia.setStyleName("a");
	    
        hPanel.add(imgPaisOcorrencia);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkPaisOcorrencia);

	}	

	public void linkFerramentaPaisHierarquia(){
	    imgFerramentaPaisHierarquia = new Image("images/catalog-icon-16.png");
	    linkFerramentaPaisHierarquia = new Hyperlink(txtConstants.paisAmbienteHierarquia(), MENU_TOKEN_FERRAMENTA_PAIS_HIERARQUIA);
	    linkFerramentaPaisHierarquia.setStyleName("a");
	    
        hPanel.add(imgFerramentaPaisHierarquia);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaPaisHierarquia);
	}		

	public void linkFerramentaPaisDiario(){
	    imgFerramentaPaisDiario = new Image("images/programs_group_ii.16.png");
	    linkFerramentaPaisDiario = new Hyperlink(txtConstants.presencaAmbienteDiario(), MENU_TOKEN_FERRAMENTA_PAIS_DIARIO);
	    linkFerramentaPaisDiario.setStyleName("a");
	    
        hPanel.add(imgFerramentaPaisDiario);
        hPanel.add(new InlineHTML("&nbsp"));
        hPanel.add(linkFerramentaPaisDiario);
	}		

	
}
