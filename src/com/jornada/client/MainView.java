package com.jornada.client;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.ambiente.aluno.TelaInicialAluno;
import com.jornada.client.ambiente.aluno.agenda.TelaInicialAlunoAgenda;
import com.jornada.client.ambiente.aluno.comunicado.TelaInicialAlunoComunicado;
import com.jornada.client.ambiente.aluno.diario.TelaInicialDiarioAluno;
import com.jornada.client.ambiente.aluno.hierarquia.HierarquiaCursoAluno;
import com.jornada.client.ambiente.aluno.notas.TelaInicialAlunoVisualizarNotas;
import com.jornada.client.ambiente.aluno.ocorrencia.TelaInicialAlunoOcorrencia;
import com.jornada.client.ambiente.coordenador.TelaInicialAdminEscola;
import com.jornada.client.ambiente.coordenador.TelaInicialAdminEscolaCurso;
import com.jornada.client.ambiente.coordenador.comunicado.TelaInicialComunicado;
import com.jornada.client.ambiente.coordenador.conteudoprogramatico.TelaInicialConteudoProgramatico;
import com.jornada.client.ambiente.coordenador.curso.TelaInicialCurso;
import com.jornada.client.ambiente.coordenador.disciplina.TelaInicialDisciplina;
import com.jornada.client.ambiente.coordenador.hierarquia.HierarquiaCursoCoordenador;
import com.jornada.client.ambiente.coordenador.periodo.TelaInicialPeriodo;
import com.jornada.client.ambiente.coordenador.topico.TelaInicialTopico;
import com.jornada.client.ambiente.coordenador.usuario.TelaInicialUsuario;
import com.jornada.client.ambiente.pais.TelaInicialPais;
import com.jornada.client.ambiente.pais.agenda.TelaInicialPaisAgenda;
import com.jornada.client.ambiente.pais.diario.TelaInicialDiarioPais;
import com.jornada.client.ambiente.pais.hierarquia.HierarquiaCursoPais;
import com.jornada.client.ambiente.pais.notas.TelaInicialPaisVisualizarNotas;
import com.jornada.client.ambiente.pais.ocorrencia.TelaInicialPaisOcorrencia;
import com.jornada.client.ambiente.professor.TelaInicialProfessor;
import com.jornada.client.ambiente.professor.avaliacao.TelaInicialAvaliacao;
import com.jornada.client.ambiente.professor.comunicado.TelaInicialComunicadoProfessor;
import com.jornada.client.ambiente.professor.diario.TelaInicialDiarioProfessor;
import com.jornada.client.ambiente.professor.hierarquia.HierarquiaCursoProfessor;
import com.jornada.client.ambiente.professor.nota.TelaInicialNota;
import com.jornada.client.ambiente.professor.ocorrencia.TelaInicialProfessorOcorrencia;
import com.jornada.client.ambiente.professor.topico.TelaInicialTopicoProfessor;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;
//import com.jornada.client.ambiente.professor.nota.CadastroNota;

@SuppressWarnings("deprecation")
public class MainView extends Composite implements HistoryListener{
	
	private MainTitle mainTitle;
	private MainMenu mainMenu;
	private MainBody mainBody;
	private MainFooter mainFooter = new MainFooter();	
	
	VerticalPanel vPanelTitle = new VerticalPanel();
	VerticalPanel vPanelMenu = new VerticalPanel();
	VerticalPanel vPanelBody = new VerticalPanel();
	VerticalPanel vPanelFooter = new VerticalPanel();
	
	VerticalPanel vPanelPrincipal = new VerticalPanel();
	
	private Usuario usuarioLogado;

	public MainView(Usuario usuarioLogado) {	
		
		this.usuarioLogado = usuarioLogado;
				
		mainMenu = new MainMenu(this);
		mainTitle = MainTitle.getInstance(this);
		mainBody = MainBody.getInstance(this);
		
        HorizontalPanel vPanelBlankTop = new HorizontalPanel();
//        vPanelBlankTop.setBorderWidth(2);
        vPanelBlankTop.setSize("15px", "40px");
	
		vPanelTitle.add(mainTitle);		
		vPanelMenu.add(mainMenu);		
		vPanelBody.add(mainBody);
		vPanelFooter.add(mainFooter);
		
		vPanelPrincipal.add(vPanelTitle);
		vPanelPrincipal.add(vPanelMenu);
		vPanelPrincipal.add(vPanelBody);
		vPanelPrincipal.add(vPanelBlankTop);
		vPanelPrincipal.add(vPanelFooter);
		
		vPanelTitle.setSize("100%", "100%");
		vPanelBody.setSize("100%", "100%");
		vPanelFooter.setSize("100%", "100%");
		vPanelPrincipal.setSize("99%", "100%");
		

		openMainView();
		
		//new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1500);
		
		vPanelTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);		
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPanelFooter.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPanelPrincipal.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);	
		
//		Label labelTest = new Label("Testing");
//		vPanelPrincipal.add(labelTest);
		
		vPanelPrincipal.setSpacing(2);	
		
		initWidget(vPanelPrincipal);		
		
	}
	
//	public void displayLoginPage(){		
//		this.vPanelPrincipal.clear();
//		mainEntryPoint.displayLoginPage();
//	}
	
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}


	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}



	public void initHistorySupport() {
        // add the MainPanel as a history listener
        History.addHistoryListener(this);
        
        String INIT_STATE = Window.Location.getParameter("page");

        // check to see if there are any tokens passed at startup via the browser's URI
        String token = History.getToken();
        if (token.length() == 0) {
            if ((INIT_STATE != null) && (!INIT_STATE.isEmpty())) {
                onHistoryChanged(INIT_STATE);
            } else {
            	
                onHistoryChanged("initstate");
            }
        } else {
            onHistoryChanged(token);
        }
    }   
	
	
	public void checkUserPermission(){
		
		String strUrl = History.getToken();
		if(strUrl.equals(MainMenu.MENU_TOKEN_SAIR)){
			History.newItem(MainMenu.MENU_TOKEN_PRINCIPAL);	
		}else
		if(usuarioLogado.getIdTipoUsuario()==TipoUsuario.ALUNO){
			if(strUrl.startsWith(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO)){
				History.newItem(strUrl);
			}else{ 
				History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO);	
			}					
		}
		else if(usuarioLogado.getIdTipoUsuario()==TipoUsuario.PROFESSOR){
			if(strUrl.startsWith(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR)){
				History.newItem(strUrl);
			}else{ 
				History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR);	
			}	
		}
		else if(usuarioLogado.getIdTipoUsuario()==TipoUsuario.PAIS){
			if(strUrl.startsWith(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS)){
				History.newItem(strUrl);
			}else{ 
				History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS);	
			}	
		}		
		else if(usuarioLogado.getIdTipoUsuario()==TipoUsuario.COORDENADOR){
		}

		else if(usuarioLogado.getIdTipoUsuario()==TipoUsuario.ADMINISTRADOR){			
		}
		else{
			//History.newItem(MainMenu.MENU_TOKEN_PRINCIPAL);
		}		
	}

    @Override
    public void onHistoryChanged(String historyToken) {
        mainMenu.changePage(historyToken);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }	
    


	public void openMainView(){

	
		this.vPanelBody.clear();
		
		checkUserPermission();
		
		//new ElementFader().fade(vPanelBody.getElement(), 0, 1, 500);
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		mainBody = MainBody.getInstance(this);

		this.vPanelBody.add(mainBody);
		this.vPanelMenu.setVisible(true);
	}	
	
	public void openAdminEscola(){

		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR);
		
		this.vPanelBody.clear();
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 500);
		
		TelaInicialAdminEscola telaInicialAdminEscola = TelaInicialAdminEscola.getInstance(this);
		
		this.vPanelBody.add(telaInicialAdminEscola);
		this.vPanelMenu.setVisible(true);

		
	}
	
	public void openAdminEscolaCurso(){

		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_CURSO_ADMIN);
		
		this.vPanelBody.clear();
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 500);
		
		TelaInicialAdminEscolaCurso telaInicialAdminEscolaCurso = TelaInicialAdminEscolaCurso.getInstance(this);
		
		this.vPanelBody.add(telaInicialAdminEscolaCurso);
		this.vPanelMenu.setVisible(true);

		
	}
	
	public void openCadastroCurso(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_CURSO);
		
		//new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);
		
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
//		CadastroCurso cadastroCurso = new CadastroCurso();
		TelaInicialCurso telaInicialCurso = TelaInicialCurso.getInstance(this);
		
		this.vPanelBody.add(telaInicialCurso);
		this.vPanelMenu.setVisible(true);	
		
	}	
		
	public void openCadastroPeriodo(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_PERIODO);
		
		//new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);
		//new ElementFader().fade(vPanelMenu.getElement(), 0, 1, 1500);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
//		CadastroPeriodo cadastroPeriodo = new CadastroPeriodo();

		TelaInicialPeriodo telaInicialPeriodo = TelaInicialPeriodo.getInstance();


		this.vPanelBody.add(telaInicialPeriodo);
		this.vPanelMenu.setVisible(true);	
		
	}
	
	public void openCadastroDisciplina(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_DISCIPLINA);
		
		//new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);
	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
//		CadastroDisciplina cadastroDisciplina = new CadastroDisciplina();
		TelaInicialDisciplina telaInicialDisciplina = TelaInicialDisciplina.getInstance();
		this.vPanelBody.add(telaInicialDisciplina);
		this.vPanelMenu.setVisible(true);
	
		
	}
	
	
	public void openCadastroConteudoProgramatico(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_CONTEUDO_PROGRAMATICO);
		
		//new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);
	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
//		CadastroConteudoProgramatico cadastroConteudoProgramatico = new CadastroConteudoProgramatico();
		TelaInicialConteudoProgramatico telaInicialConteudoProgramatico = TelaInicialConteudoProgramatico.getInstance();
		this.vPanelBody.add(telaInicialConteudoProgramatico);
		this.vPanelMenu.setVisible(true);
	
		
	}	
	
	public void openCadastroAdminTopico() {

		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_TOPICO);

		// new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);

		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialTopico telaInicialTopico = TelaInicialTopico.getInstance();
		this.vPanelBody.add(telaInicialTopico);
		this.vPanelMenu.setVisible(true);

	}
	
	public void openCadastroAdminHierarquia() {

		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_HIERARQUIA);

		// new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);

		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		HierarquiaCursoCoordenador hierarquiaCursoCoordenador = new HierarquiaCursoCoordenador(this);
		this.vPanelBody.add(hierarquiaCursoCoordenador);
		this.vPanelMenu.setVisible(true);

	}	
	
	public void openCadastroUsuario(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_USUARIO);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialUsuario telaInicialUsuario = TelaInicialUsuario.getInstance();
		this.vPanelBody.add(telaInicialUsuario);
		this.vPanelMenu.setVisible(true);
	}	
	
	public void openCadastroComunicado(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_COMUNICADO);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialComunicado telaInicialComunicado = TelaInicialComunicado.getInstance();
		this.vPanelBody.add(telaInicialComunicado);
		this.vPanelMenu.setVisible(true);
	}	
	
	public void openCadastroCoordenadorOcorrencia() {
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_OCORRENCIA);
		// new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
		TelaInicialProfessorOcorrencia telaInicialOcorrencia = TelaInicialProfessorOcorrencia.getInstance(this);
		this.vPanelBody.add(telaInicialOcorrencia);
		this.vPanelMenu.setVisible(true);
	}	
	
	public void openFerramentaCoordenadorDiario(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_DIARIO);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialDiarioProfessor telaInicialDiarioProfessor = TelaInicialDiarioProfessor.getInstance(this);
		
		this.vPanelBody.add(telaInicialDiarioProfessor);
		this.vPanelMenu.setVisible(true);		
	}	
	
	
	public void openFerramentaProfessor(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialProfessor telaInicialProfessor = TelaInicialProfessor.getInstance(this);
		this.vPanelBody.add(telaInicialProfessor);
		this.vPanelMenu.setVisible(true);		
	}	
	
	
	public void openFerramentaProfessorAvaliacao(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_AVALIACAO);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialAvaliacao telaInicialAvaliacao = TelaInicialAvaliacao.getInstance(this);
		this.vPanelBody.add(telaInicialAvaliacao);
		this.vPanelMenu.setVisible(true);		
	}	
	
	
	public void openFerramentaProfessorNota(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_NOTA);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialNota telaInicialNota = TelaInicialNota.getInstance(this);
		this.vPanelBody.add(telaInicialNota);
		this.vPanelMenu.setVisible(true);		
	}	
	
	
	public void openCadastroProfessorTopico() {

		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_TOPICO);

		// new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);

		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialTopicoProfessor telaInicialTopicoProfessor = TelaInicialTopicoProfessor.getInstance(this);
		this.vPanelBody.add(telaInicialTopicoProfessor);
		this.vPanelMenu.setVisible(true);

	}	
	
	
	public void openProfessorComunicado(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_COMUNICADO);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialComunicadoProfessor telaInicialComunicadoProfessor = TelaInicialComunicadoProfessor.getInstance();
		
		this.vPanelBody.add(telaInicialComunicadoProfessor);
		this.vPanelMenu.setVisible(true);		
	}
	
	public void openCadastroProfessorOcorrencia() {
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_OCORRENCIA);
		// new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
		TelaInicialProfessorOcorrencia telaInicialProfessorOcorrencia = TelaInicialProfessorOcorrencia.getInstance(this);
		this.vPanelBody.add(telaInicialProfessorOcorrencia);
		this.vPanelMenu.setVisible(true);
	}	
	
	public void openFerramentaProfessorHierarquia(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_HIERARQUIA);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		HierarquiaCursoProfessor telaInicialHierarquiaCurso = new HierarquiaCursoProfessor(this);
		
		this.vPanelBody.add(telaInicialHierarquiaCurso);
		this.vPanelMenu.setVisible(true);		
	}		
	
	
	public void openFerramentaProfessorDiario(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_DIARIO);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialDiarioProfessor telaInicialDiarioProfessor = TelaInicialDiarioProfessor.getInstance(this);
		
		this.vPanelBody.add(telaInicialDiarioProfessor);
		this.vPanelMenu.setVisible(true);		
	}		
	
	public void openFerramentaAluno(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialAluno telaInicialAluno = TelaInicialAluno.getInstance(this);
		this.vPanelBody.add(telaInicialAluno);
		this.vPanelMenu.setVisible(true);		
	}		
	
	public void openFerramentaAlunoNota(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_NOTA);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialAlunoVisualizarNotas telaInicialAlunoVisualizarNotas = TelaInicialAlunoVisualizarNotas.getInstance(this);
		this.vPanelBody.add(telaInicialAlunoVisualizarNotas);
		this.vPanelMenu.setVisible(true);		
	}
	
	public void openFerramentaAlunoAgenda(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_AGENDA);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialAlunoAgenda telaInicialAlunoAgenda = TelaInicialAlunoAgenda.getInstance(this);
		this.vPanelBody.add(telaInicialAlunoAgenda);
		this.vPanelMenu.setVisible(true);		
	}	
	
	public void openFerramentaAlunoComunicado(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_COMUNICADO);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialAlunoComunicado telaInicialAlunoComunicado = TelaInicialAlunoComunicado.getInstance();
		
		this.vPanelBody.add(telaInicialAlunoComunicado);
		this.vPanelMenu.setVisible(true);		
	}	
	
	public void openCadastroAlunoOcorrencia() {
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_OCORRENCIA);
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
		TelaInicialAlunoOcorrencia telaInicialAlunoOcorrencia = TelaInicialAlunoOcorrencia.getInstance(this);
		this.vPanelBody.add(telaInicialAlunoOcorrencia);
		this.vPanelMenu.setVisible(true);
	}		
	
	public void openFerramentaAlunoHierarquia(){		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_HIERARQUIA);		
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

//		TelaInicialHierarquiaCurso telaInicialHierarquiaCurso = new TelaInicialHierarquiaCurso();
		HierarquiaCursoAluno hierarquiaCursoAluno = new HierarquiaCursoAluno(this);
		this.vPanelBody.add(hierarquiaCursoAluno);
		this.vPanelMenu.setVisible(true);		
	}	
	
	
	public void openFerramentaAlunoDiario(){		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_DIARIO);		
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialDiarioAluno telaInicialDiarioAluno = TelaInicialDiarioAluno.getInstance(this);
//		HierarquiaCursoAluno hierarquiaCursoAluno = new HierarquiaCursoAluno(this);
		this.vPanelBody.add(telaInicialDiarioAluno);
		this.vPanelMenu.setVisible(true);		
	}		
	
	public void openFerramentaPais(){		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS);		
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialPais telaInicialPais = TelaInicialPais.getInstance(this);
		this.vPanelBody.add(telaInicialPais);
		this.vPanelMenu.setVisible(true);		
	}		
	
	public void openFerramentaPaisAgenda(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_AGENDA);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialPaisAgenda telaInicialPaisAgenda = TelaInicialPaisAgenda.getInstance(this);
		this.vPanelBody.add(telaInicialPaisAgenda);
		this.vPanelMenu.setVisible(true);		
	}	
		
	

	public void openFerramentaPaisComunicado(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_COMUNICADO);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialAlunoComunicado telaInicialAlunoComunicado = TelaInicialAlunoComunicado.getInstance();
		
		this.vPanelBody.add(telaInicialAlunoComunicado);
		this.vPanelMenu.setVisible(true);		
	}
	
		
	public void openFerramentaPaisNota(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_NOTA);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		TelaInicialPaisVisualizarNotas telaInicialAlunoVisualizarNotas = TelaInicialPaisVisualizarNotas.getInstance(this);
		this.vPanelBody.add(telaInicialAlunoVisualizarNotas);
		this.vPanelMenu.setVisible(true);		
	}
	
	public void openCadastroPaisOcorrencia() {
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_OCORRENCIA);
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
		TelaInicialPaisOcorrencia telaInicialPaisOcorrencia = TelaInicialPaisOcorrencia.getInstance(this);
		this.vPanelBody.add(telaInicialPaisOcorrencia);
		this.vPanelMenu.setVisible(true);
	}		
	
	public void openFerramentaPaisHierarquia(){
		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_HIERARQUIA);		
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

//		TelaInicialPaisHierarquiaCurso telaInicialPaisHierarquiaCurso = new TelaInicialPaisHierarquiaCurso(this);
		HierarquiaCursoPais hierarquiaCursoPais = new HierarquiaCursoPais(this);
		
		this.vPanelBody.add(hierarquiaCursoPais);
		this.vPanelMenu.setVisible(true);		
	}		

	public void openFerramentaPaisDiario(){		
		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_DIARIO);		
		this.vPanelBody.clear();
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
		

		TelaInicialDiarioPais telaInicialDiarioPais = TelaInicialDiarioPais.getInstance(this);
//		HierarquiaCursoAluno hierarquiaCursoAluno = new HierarquiaCursoAluno(this);
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1300);	
		
		this.vPanelBody.add(telaInicialDiarioPais);
		this.vPanelMenu.setVisible(true);	
//		new RockAnimation(vPanelBody.getElement());
		
	}	
	
	public MainMenu getMainMenu() {
		return mainMenu;
	}



	


	
	
	
	
	
	
}

