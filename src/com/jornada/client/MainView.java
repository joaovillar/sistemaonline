package com.jornada.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
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
import com.jornada.client.ambiente.aluno.presenca.TelaInicialPresencaAluno;
import com.jornada.client.ambiente.coordenador.TelaInicialAdminEscola;
import com.jornada.client.ambiente.coordenador.TelaInicialAdminEscolaCurso;
import com.jornada.client.ambiente.coordenador.comunicado.TelaInicialComunicado;
import com.jornada.client.ambiente.coordenador.conteudoprogramatico.TelaInicialConteudoProgramatico;
import com.jornada.client.ambiente.coordenador.curso.TelaInicialCurso;
import com.jornada.client.ambiente.coordenador.disciplina.TelaInicialDisciplina;
import com.jornada.client.ambiente.coordenador.hierarquia.HierarquiaCursoCoordenador;
import com.jornada.client.ambiente.coordenador.periodo.TelaInicialPeriodo;
import com.jornada.client.ambiente.coordenador.relatorio.TelaInicialRelatorioNew;
import com.jornada.client.ambiente.coordenador.relatorio.boletim.TelaInicialBoletim;
import com.jornada.client.ambiente.coordenador.relatorio.usuario.TelaInicialRelatorioUsuario;
import com.jornada.client.ambiente.coordenador.topico.TelaInicialTopico;
import com.jornada.client.ambiente.coordenador.usuario.TelaInicialUsuario;
import com.jornada.client.ambiente.pais.TelaInicialPais;
import com.jornada.client.ambiente.pais.agenda.TelaInicialPaisAgenda;
import com.jornada.client.ambiente.pais.diario.TelaInicialDiarioPais;
import com.jornada.client.ambiente.pais.hierarquia.HierarquiaCursoPais;
import com.jornada.client.ambiente.pais.notas.TelaInicialPaisVisualizarNotas;
import com.jornada.client.ambiente.pais.ocorrencia.TelaInicialPaisOcorrencia;
import com.jornada.client.ambiente.pais.presenca.TelaInicialPresencaAlunoPais;
import com.jornada.client.ambiente.professor.TelaInicialProfessor;
import com.jornada.client.ambiente.professor.avaliacao.TelaInicialAvaliacao;
import com.jornada.client.ambiente.professor.comunicado.TelaInicialComunicadoProfessor;
import com.jornada.client.ambiente.professor.diario.TelaInicialDiarioProfessor;
import com.jornada.client.ambiente.professor.hierarquia.HierarquiaCursoProfessor;
import com.jornada.client.ambiente.professor.nota.TelaInicialNota;
import com.jornada.client.ambiente.professor.ocorrencia.TelaInicialProfessorOcorrencia;
import com.jornada.client.ambiente.professor.presenca.TelaInicialPresenca;
import com.jornada.client.ambiente.professor.topico.TelaInicialTopicoProfessor;
import com.jornada.client.classes.widgets.popup.MpPopupLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;
//import com.jornada.client.ambiente.professor.nota.CadastroNota;


@SuppressWarnings("deprecation")
public class MainView extends Composite implements HistoryListener{
	
	private MainTitle mainTitle;
	private MainMenu mainMenu;
	private MainBody mainBody;
	private MainFooter mainFooter = new MainFooter();	
	
//	MpPanelLoading mpLoading = new MpPanelLoading("images/radar.gif");
	
	
	
	VerticalPanel vPanelTitle = new VerticalPanel();
	VerticalPanel vPanelMenu = new VerticalPanel();
	VerticalPanel vPanelBody = new VerticalPanel();
	VerticalPanel vPanelFooter = new VerticalPanel();
	
	VerticalPanel vPanelPrincipal = new VerticalPanel();
	
	private Usuario usuarioLogado;
	
	TextConstants txtConstants = GWT.create(TextConstants.class);
	
	MpPopupLoading mpPopupLoading = new MpPopupLoading(txtConstants.geralCarregando(), "");

	public MainView(Usuario usuarioLogado) {	
		
		this.usuarioLogado = usuarioLogado;
		
//		mpLoading.setTxtLoading(txtConstants.geralCarregando());
//		mpLoading.show();
//		mpLoading.setVisible(false);
				
		mainMenu = new MainMenu(this);
		mainTitle = MainTitle.getInstance(this);
		mainBody = MainBody.getInstance(this);
		
        HorizontalPanel vPanelBlankTop = new HorizontalPanel();
//        vPanelBlankTop.setBorderWidth(2);
        vPanelBlankTop.setSize("15px", "40px");
	
//        HorizontalPanel hP = new HorizontalPanel();
//
//        
//        final MultiSelectionModel<String> selectionModel = new MultiSelectionModel<String>(ContactDatabase.ContactInfo.KEY_PROVIDER);
//        CellTree.Resources res = GWT.create(CellTree.BasicResources.class);
//        CellTree cellTree = new CellTree(new TreeModelSideMenu(selectionModel), null, res);
//        cellTree.setAnimationEnabled(true);
       
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
		

		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		mainBody = MainBody.getInstance(this);

		this.vPanelBody.add(mainBody);
		this.vPanelMenu.setVisible(true);
	}	
	
	public void openAdminEscola(String strToken){

//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR);
	    History.newItem(strToken);

		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();				
				TelaInicialAdminEscola telaInicialAdminEscola = TelaInicialAdminEscola.getInstance(MainView.this);
				vPanelBody.add(telaInicialAdminEscola);
				vPanelMenu.setVisible(true);
			}
		});
		
	}
	
	public void openAdminEscolaCurso(String strToken){

//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_CURSO_ADMIN);	
	    History.newItem(strToken);   
	
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				
				TelaInicialAdminEscolaCurso telaInicialAdminEscolaCurso = TelaInicialAdminEscolaCurso.getInstance(MainView.this);
				
				vPanelBody.add(telaInicialAdminEscolaCurso);
				vPanelMenu.setVisible(true);
			}
		});

		
	}
	
	public void openCadastroCurso(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_CURSO);
	    History.newItem(strToken);
	
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();

				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				
				TelaInicialCurso telaInicialCurso = TelaInicialCurso.getInstance(MainView.this);
				
				vPanelBody.add(telaInicialCurso);
				vPanelMenu.setVisible(true);		

			}
		});		
		
	}	
		
	public void openCadastroPeriodo(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_PERIODO);	
	    History.newItem(strToken);   
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialPeriodo telaInicialPeriodo = TelaInicialPeriodo.getInstance();
				vPanelBody.add(telaInicialPeriodo);
				vPanelMenu.setVisible(true);	
			}
		});
		
	}
	
	public void openCadastroDisciplina(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_DISCIPLINA);
	    History.newItem(strToken);
	    
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);	
				TelaInicialDisciplina telaInicialDisciplina = TelaInicialDisciplina.getInstance();
				vPanelBody.add(telaInicialDisciplina);
				vPanelMenu.setVisible(true);
			}
		});
	
		
	}
	
	public void openCadastroConteudoProgramatico(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_CONTEUDO_PROGRAMATICO);
	    History.newItem(strToken);
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialConteudoProgramatico telaInicialConteudoProgramatico = TelaInicialConteudoProgramatico.getInstance(MainView.this);
				vPanelBody.add(telaInicialConteudoProgramatico);
				vPanelMenu.setVisible(true);
			}
		});
	
		
	}	
	
	public void openCadastroAdminTopico(String strToken) {

//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_TOPICO);
	    History.newItem(strToken);
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialTopico telaInicialTopico = TelaInicialTopico.getInstance();
				vPanelBody.add(telaInicialTopico);
				vPanelMenu.setVisible(true);
			}
		});

	}
	
	public void openCadastroAdminHierarquia(String strToken) {

//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_HIERARQUIA);
	    History.newItem(strToken);

		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				HierarquiaCursoCoordenador hierarquiaCursoCoordenador = new HierarquiaCursoCoordenador(MainView.this);
				vPanelBody.add(hierarquiaCursoCoordenador);
				vPanelMenu.setVisible(true);
			}
		});

	}	
	
	public void openCadastroUsuario(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_USUARIO);	
	    History.newItem(strToken);   
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialUsuario telaInicialUsuario = TelaInicialUsuario.getInstance();
				vPanelBody.add(telaInicialUsuario);
				vPanelMenu.setVisible(true);
			}
		});
	}	
	
	public void openCadastroComunicado(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_COMUNICADO);	
	    History.newItem(strToken);    
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialComunicado telaInicialComunicado = TelaInicialComunicado.getInstance();
				vPanelBody.add(telaInicialComunicado);
				vPanelMenu.setVisible(true);
			}
		});
	}	
	
	public void openCadastroCoordenadorOcorrencia(String strToken) {
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_OCORRENCIA);
	    History.newItem(strToken);

		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);		
				TelaInicialProfessorOcorrencia telaInicialOcorrencia = TelaInicialProfessorOcorrencia.getInstance(MainView.this);
				vPanelBody.add(telaInicialOcorrencia);
				vPanelMenu.setVisible(true);
			}
		});		
	}	
	
	public void openFerramentaCoordenadorDiario(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_DIARIO);		
	    History.newItem(strToken);        
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialDiarioProfessor telaInicialDiarioProfessor = TelaInicialDiarioProfessor.getInstance(MainView.this);		
				vPanelBody.add(telaInicialDiarioProfessor);
				vPanelMenu.setVisible(true);
			}
		});
	}	
	
    public void openFerramentaCoordenadorPresenca(String strToken){
        
//        History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_PRESENCA);     
        History.newItem(strToken);     

        mpPopupLoading.show();
        GWT.runAsync(new RunAsyncCallback() {
            public void onFailure(Throwable caught) {
                mpPopupLoading.hide();
                Window.alert("Code download failed");
            }

            public void onSuccess() {
                mpPopupLoading.hide();
                vPanelBody.clear();
                vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
                TelaInicialPresenca telaInicialPresenca = TelaInicialPresenca.getInstance(MainView.this);      
                vPanelBody.add(telaInicialPresenca);
                vPanelMenu.setVisible(true);    
            }
        });
    }  
	
	
	
    public void openFerramentaCoordenadorRelatorio(String strToken){
        
//        History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_RELATORIO);  
        History.newItem(strToken);  
        
        mpPopupLoading.show();
        GWT.runAsync(new RunAsyncCallback() {
            public void onFailure(Throwable caught) {
                mpPopupLoading.hide();
                Window.alert("Code download failed");
            }

            public void onSuccess() {
                mpPopupLoading.hide();
                vPanelBody.clear();
                vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
                TelaInicialRelatorioNew telaInicialRelatorio = TelaInicialRelatorioNew.getInstance(MainView.this);
                vPanelBody.add(telaInicialRelatorio);
                vPanelMenu.setVisible(true);
            }
        });
    }   
    
    public void openFerramentaCoordenadorBoletim(String strToken){
        
//        History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_BOLETIM); 
        History.newItem(strToken); 
        
        mpPopupLoading.show();
        GWT.runAsync(new RunAsyncCallback() {
            public void onFailure(Throwable caught) {
                mpPopupLoading.hide();
                Window.alert("Code download failed");
            }

            public void onSuccess() {
                mpPopupLoading.hide();
                vPanelBody.clear();
                vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
                TelaInicialBoletim telaInicialBoletim = TelaInicialBoletim.getInstance(MainView.this);
                vPanelBody.add(telaInicialBoletim);
                vPanelMenu.setVisible(true);
            }
        });
    } 
    
    public void openFerramentaCoordenadorRelatorioUsuario(String strToken){
        
//        History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_RELATORIO_USUARIO);
        History.newItem(strToken);
        
        mpPopupLoading.show();
        GWT.runAsync(new RunAsyncCallback() {
            public void onFailure(Throwable caught) {
                mpPopupLoading.hide();
                Window.alert("Code download failed");
            }

            public void onSuccess() {
                mpPopupLoading.hide();
                vPanelBody.clear();
                vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
                TelaInicialRelatorioUsuario telaInicialRelatorioUsuario = TelaInicialRelatorioUsuario.getInstance(MainView.this);
                vPanelBody.add(telaInicialRelatorioUsuario);
                vPanelMenu.setVisible(true);
            }
        });
    }     
	
	
	
	
	public void openFerramentaProfessor(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR);	
	    History.newItem(strToken); 
		
		mpPopupLoading.show();
		 GWT.runAsync(new RunAsyncCallback() {
	          public void onFailure(Throwable caught) {
	        	mpPopupLoading.hide();
	            Window.alert("Code download failed");
	          }

	          public void onSuccess() {
	        	  mpPopupLoading.hide();
	              vPanelBody.clear();
	              vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

	              TelaInicialProfessor telaInicialProfessor = TelaInicialProfessor.getInstance(MainView.this);
	              vPanelBody.add(telaInicialProfessor);
	              vPanelMenu.setVisible(true);
	          }
	        });
		
	}	
	
	public void openFerramentaProfessorAvaliacao(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_AVALIACAO);
	    History.newItem(strToken);
	
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialAvaliacao telaInicialAvaliacao = TelaInicialAvaliacao.getInstance(MainView.this);
				vPanelBody.add(telaInicialAvaliacao);
				vPanelMenu.setVisible(true);
			}
		});
	}	
	
	public void openFerramentaProfessorNota(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_NOTA);	
	    History.newItem(strToken);    
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialNota telaInicialNota = TelaInicialNota.getInstance(MainView.this);
				vPanelBody.add(telaInicialNota);
				vPanelMenu.setVisible(true);	
			}
		});		
		
	}	
	
	
    public void openCadastroProfessorConteudoProgramatico(String strToken){
        
//        History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_CONTEUDO_PROGRAMATICO);
        History.newItem(strToken);
        
        mpPopupLoading.show();
        GWT.runAsync(new RunAsyncCallback() {
            public void onFailure(Throwable caught) {
                mpPopupLoading.hide();
                Window.alert("Code download failed");
            }

            public void onSuccess() {
                mpPopupLoading.hide();
                vPanelBody.clear();
                vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
                TelaInicialConteudoProgramatico telaInicialConteudoProgramatico = TelaInicialConteudoProgramatico.getInstance(MainView.this);
                vPanelBody.add(telaInicialConteudoProgramatico);
                vPanelMenu.setVisible(true);
            }
        });
    
        
    }   
	
	
	
	public void openCadastroProfessorTopico(String strToken) {

//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_TOPICO);
	    History.newItem(strToken);

		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialTopicoProfessor telaInicialTopicoProfessor = TelaInicialTopicoProfessor.getInstance(MainView.this);
				vPanelBody.add(telaInicialTopicoProfessor);
				vPanelMenu.setVisible(true);
			}
		});		

	}	
	
	public void openProfessorComunicado(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_COMUNICADO);
	    History.newItem(strToken);
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialComunicadoProfessor telaInicialComunicadoProfessor = TelaInicialComunicadoProfessor.getInstance(MainView.this);		
				vPanelBody.add(telaInicialComunicadoProfessor);
				vPanelMenu.setVisible(true);	
			}
		});
	}
	
	public void openCadastroProfessorOcorrencia(String strToken) {
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_OCORRENCIA);
	    History.newItem(strToken);
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);		
				TelaInicialProfessorOcorrencia telaInicialProfessorOcorrencia = TelaInicialProfessorOcorrencia.getInstance(MainView.this);
				vPanelBody.add(telaInicialProfessorOcorrencia);
				vPanelMenu.setVisible(true);
			}
		});		
		
	}	
	
	public void openFerramentaProfessorHierarquia(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_HIERARQUIA);
	    History.newItem(strToken);
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				HierarquiaCursoProfessor telaInicialHierarquiaCurso = new HierarquiaCursoProfessor(MainView.this);		
				vPanelBody.add(telaInicialHierarquiaCurso);
				vPanelMenu.setVisible(true);	
			}
		});
	}		
	
	public void openFerramentaProfessorRelatorio(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_RELATORIO);	
	    History.newItem(strToken);   
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialBoletim telaInicialRelatorio = TelaInicialBoletim.getInstance(MainView.this);		
				vPanelBody.add(telaInicialRelatorio);
				vPanelMenu.setVisible(true);	
			}
		});
	}		
	
	
	   public void openFerramentaProfessorDiario(String strToken){
	        
//	        History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_DIARIO);    
	       History.newItem(strToken);    

	        mpPopupLoading.show();
	        GWT.runAsync(new RunAsyncCallback() {
	            public void onFailure(Throwable caught) {
	                mpPopupLoading.hide();
	                Window.alert("Code download failed");
	            }

	            public void onSuccess() {
	                mpPopupLoading.hide();
	                vPanelBody.clear();
	                vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
	                TelaInicialDiarioProfessor telaInicialDiarioProfessor = TelaInicialDiarioProfessor.getInstance(MainView.this);      
	                vPanelBody.add(telaInicialDiarioProfessor);
	                vPanelMenu.setVisible(true);    
	            }
	        });
	    }   
	
       public void openFerramentaProfessorPresenca(String strToken){
           
//           History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PROFESSOR_PRESENCA); 
           History.newItem(strToken); 

           mpPopupLoading.show();
           GWT.runAsync(new RunAsyncCallback() {
               public void onFailure(Throwable caught) {
                   mpPopupLoading.hide();
                   Window.alert("Code download failed");
               }

               public void onSuccess() {
                   mpPopupLoading.hide();
                   vPanelBody.clear();
                   vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
                   TelaInicialPresenca telaInicialPresenca = TelaInicialPresenca.getInstance(MainView.this);      
                   vPanelBody.add(telaInicialPresenca);
                   vPanelMenu.setVisible(true);    
               }
           });
       }   	
	
	
	
	public void openFerramentaAluno(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO);	
	    History.newItem(strToken); 

		mpPopupLoading.show();
		 GWT.runAsync(new RunAsyncCallback() {
	          public void onFailure(Throwable caught) {
	        	mpPopupLoading.hide();
	            Window.alert("Code download failed");
	          }

	          public void onSuccess() {
	        	  mpPopupLoading.hide();
	              vPanelBody.clear();
	              vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

	              TelaInicialAluno telaInicialAluno = TelaInicialAluno.getInstance(MainView.this);
	              vPanelBody.add(telaInicialAluno);
	              vPanelMenu.setVisible(true);       

	          }
	        });
	}		
	
	public void openFerramentaAlunoNota(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_NOTA);	
	    History.newItem(strToken);    

		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialAlunoVisualizarNotas telaInicialAlunoVisualizarNotas = TelaInicialAlunoVisualizarNotas.getInstance(MainView.this);
				vPanelBody.add(telaInicialAlunoVisualizarNotas);
				vPanelMenu.setVisible(true);	
			}
		});		
		
	}
	
	public void openFerramentaAlunoAgenda(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_AGENDA);	
	    History.newItem(strToken);  
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialAlunoAgenda telaInicialAlunoAgenda = TelaInicialAlunoAgenda.getInstance(MainView.this);
				vPanelBody.add(telaInicialAlunoAgenda);
				vPanelMenu.setVisible(true);	
			}
		});		
		
	}	
	
	public void openFerramentaAlunoComunicado(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_COMUNICADO);	
	    History.newItem(strToken);  	
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialAlunoComunicado telaInicialAlunoComunicado = TelaInicialAlunoComunicado.getInstance(MainView.this);		
				vPanelBody.add(telaInicialAlunoComunicado);
				vPanelMenu.setVisible(true);		
			}
		});
		
	}	
	
	public void openCadastroAlunoOcorrencia(String strToken) {
	
//	    History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_OCORRENCIA);
	    History.newItem(strToken);
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);		
				TelaInicialAlunoOcorrencia telaInicialAlunoOcorrencia = TelaInicialAlunoOcorrencia.getInstance(MainView.this);
				vPanelBody.add(telaInicialAlunoOcorrencia);
				vPanelMenu.setVisible(true);
			}
		});
		
	}		
	
	public void openFerramentaAlunoHierarquia(String strToken){		
		
//	    History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_HIERARQUIA);	
	    History.newItem(strToken);  		

		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				HierarquiaCursoAluno hierarquiaCursoAluno = new HierarquiaCursoAluno(MainView.this);
				vPanelBody.add(hierarquiaCursoAluno);
				vPanelMenu.setVisible(true);
			}
		});
		
	}	
	
	
	public void openFerramentaAlunoDiario(String strToken){		
		
//	    History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_DIARIO);	
	    History.newItem(strToken);  

		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialDiarioAluno telaInicialDiarioAluno = TelaInicialDiarioAluno.getInstance(MainView.this);
				vPanelBody.add(telaInicialDiarioAluno);
				vPanelMenu.setVisible(true);	
			}
		});
		
	}	
	
	
    public void openFerramentaAlunoPresenca(String strToken){
        
//        History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_PRESENCA);     
        History.newItem(strToken);     

        mpPopupLoading.show();
        GWT.runAsync(new RunAsyncCallback() {
            public void onFailure(Throwable caught) {
                mpPopupLoading.hide();
                Window.alert("Code download failed");
            }

            public void onSuccess() {
                mpPopupLoading.hide();
                vPanelBody.clear();
                vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
                TelaInicialPresencaAluno telaInicialPresencaAluno = TelaInicialPresencaAluno.getInstance(MainView.this);      
                vPanelBody.add(telaInicialPresencaAluno);
                vPanelMenu.setVisible(true);    
            }
        });
    } 
	
	public void openFerramentaPais(String strToken){	
	    
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS);
	    History.newItem(strToken);
		
		mpPopupLoading.show();
        GWT.runAsync(new RunAsyncCallback() {
            public void onFailure(Throwable caught) {
              mpPopupLoading.hide();
              Window.alert("Code download failed");
            }

            public void onSuccess() {
            	mpPopupLoading.hide();
                vPanelBody.clear();
                vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

                TelaInicialPais telaInicialPais = TelaInicialPais.getInstance(MainView.this);
                vPanelBody.add(telaInicialPais);
                vPanelMenu.setVisible(true);       

            }
          });
		
		
	}		
	
	public void openFerramentaPaisAgenda(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_AGENDA);	
	    History.newItem(strToken);   
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialPaisAgenda telaInicialPaisAgenda = TelaInicialPaisAgenda.getInstance(MainView.this);
				vPanelBody.add(telaInicialPaisAgenda);
				vPanelMenu.setVisible(true);		
			}
		});
		
		
	}	
	
	public void openFerramentaPaisComunicado(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_COMUNICADO);	
	    History.newItem(strToken);   
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialAlunoComunicado telaInicialAlunoComunicado = TelaInicialAlunoComunicado.getInstance(MainView.this);		
				vPanelBody.add(telaInicialAlunoComunicado);
				vPanelMenu.setVisible(true);	
			}
		});
		
	}
		
	public void openFerramentaPaisNota(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_NOTA);	
	    History.newItem(strToken); 
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialPaisVisualizarNotas telaInicialAlunoVisualizarNotas = TelaInicialPaisVisualizarNotas.getInstance(MainView.this);
				vPanelBody.add(telaInicialAlunoVisualizarNotas);
				vPanelMenu.setVisible(true);	
			}
		});
		
	}
	
	public void openCadastroPaisOcorrencia(String strToken) {
	
//	    History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_OCORRENCIA);
	    History.newItem(strToken);
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);		
				TelaInicialPaisOcorrencia telaInicialPaisOcorrencia = TelaInicialPaisOcorrencia.getInstance(MainView.this);
				vPanelBody.add(telaInicialPaisOcorrencia);
				vPanelMenu.setVisible(true);
			}
		});
		
	}		
	
	public void openFerramentaPaisHierarquia(String strToken){
		
//		History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS_HIERARQUIA);
	    History.newItem(strToken);
		
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				HierarquiaCursoPais hierarquiaCursoPais = new HierarquiaCursoPais(MainView.this);		
				vPanelBody.add(hierarquiaCursoPais);
				vPanelMenu.setVisible(true);		
			}
		});
		
	}		

	public void openFerramentaPaisDiario(String strToken){		
		History.newItem(strToken);	
		
		mpPopupLoading.show();
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
				mpPopupLoading.hide();
				Window.alert("Code download failed");
			}

			public void onSuccess() {
				mpPopupLoading.hide();
				vPanelBody.clear();
				vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				TelaInicialDiarioPais telaInicialDiarioPais = TelaInicialDiarioPais.getInstance(MainView.this);
				vPanelBody.add(telaInicialDiarioPais);
				vPanelMenu.setVisible(true);	
			}
		});
		
	}	
	
    public void openFerramentaAlunoPaisPresenca(String strToken){
        
//      History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO_PRESENCA);     
      History.newItem(strToken);     

      mpPopupLoading.show();
      GWT.runAsync(new RunAsyncCallback() {
          public void onFailure(Throwable caught) {
              mpPopupLoading.hide();
              Window.alert("Code download failed");
          }

          public void onSuccess() {
              mpPopupLoading.hide();
              vPanelBody.clear();
              vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
              TelaInicialPresencaAlunoPais telaInicialPresencaPaisAluno = TelaInicialPresencaAlunoPais.getInstance(MainView.this);      
              vPanelBody.add(telaInicialPresencaPaisAluno);
              vPanelMenu.setVisible(true);    
          }
      });
  }
	
	public MainMenu getMainMenu() {
		return mainMenu;
	}



	
	
	
	
}

