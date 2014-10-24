package com.jornada.client.content.i18n;

import com.google.gwt.i18n.client.Messages;

public interface TextConstants extends Messages {

	/******Login Begin******/
	String loginAcessarEscola();
	String loginUser();
	String loginSenha();
	String loginDigitarUser();
    String loginDigitarSenha();
    String loginLogar();
    String loginCampoVazio();
    String loginAcessoNegado();
    String loginErroSairSistema();
    String loginNovaSenha();
    String loginConfirmarSenha();
    String loginPrimeiroLogin();
	/******Logn End******/
    
    /******MainTitle Begin******/
    String titleNome();
    String titleBemVindo(String primeiroNome, String segundoNome);
    String titleConfiguracao();
    /******MainTitle End******/
    
    /******MainMenu Begin******/
    String menuTokenPrincipal();
    String menuTokenFerramentaCoordenador();
    String menuTokenFerramentaCoordenadorComunicado();
    String menuTokenFerramentaCoordenadorOcorrencia();
    String menuTokenFerramentaCoordenadorCursoAdmin();
    String menuTokenFerramentaCoordenadorCurso();
    String menuTokenFerramentaCoordenadorPeriodo();
    String menuTokenFerramentaCoordenadorDisciplina();
    String menuTokenFerramentaCoordenadorConteudoProgramatico();
    String menuTokenFerramentaCoordenadorTopico();
    String menuTokenFerramentaCoordenadorHierarquia();
    String menuTokenFerramentaCoordenadorUsuario();
    String menuTokenFerramentaCoordenadorDiario();
	String menuTokenFerramentaProfessor();
	String menuTokenFerramentaProfessorAvaliacao();
	String menuTokenFerramentaProfessorNota();
	String menuTokenFerramentaProfessorTopico();
	String menuTokenFerramentaProfessorComunicado();	
	String menuTokenFerramentaProfessorOcorrencia();
	String menuTokenFerramentaProfessorHierarquia();
	String menuTokenFerramentaProfessorDiario();
	String menuTokenFerramentaAluno();
	String menuTokenFerramentaAlunoNota();
	String menuTokenFerramentaAlunoAgenda();
	String menuTokenFerramentaAlunoComunicado();
	String menuTokenFerramentaAlunoOcorrencia();
	String menuTokenFerramentaAlunoHierarquia();
	String menuTokenFerramentaAlunoDiario();	
	String menuTokenFerramentaPais();
	String menuTokenFerramentaPaisAgenda();
	String menuTokenFerramentaPaisComunicado();
	String menuTokenFerramentaPaisOcorrencia();
	String menuTokenFerramentaPaisNota();
	String menuTokenFerramentaPaisHierarquia();
	String menuTokenFerramentaPaisDiario();
    /******MainTitle End******/
	
    /******MainBody Begin******/
    String principal();
    /******MainBody End******/
    
    /******MainFooter Begin******/
    String footerPolitica();
    String footerUsoInterno();
    String footerEmailSuporte();
    String footerEmailSuporteMsg();
    String footerEmailEndereco();
    String footerLicensa();
    String footerBrowser();    
    /******MainFooter End******/
    
    /******AmbienteDoCoordenador Begin******/
    String coordenadorAmbiente();
    String coordenadorAmbientePeriodo();
    String coordenadorAmbienteDisciplina();
    String coordenadorAmbienteConteudoProgramatico();
    String coordenadorAmbienteTopico();
    String coordenadorAmbienteHierarquia();
    String coordenadorAmbienteUsuario();
    String coordenadorAmbienteCompText1();
    String coordenadorAmbienteCompText2();
    String coordenadorAmbienteCompText3();
    
    String coordenadorAmbienteComunicado();
    String coordenadorAmbienteComunicadoCompText1();
    String coordenadorAmbienteComunicadoCompText2();
    
    String coordenadorAmbienteOcorrencia();
    String coordenadorAmbienteOcorrenciaCompText1();
    String coordenadorAmbienteOcorrenciaCompText2();
    
   /******AmbienteDoCoordenador End******/
    
    /******AmbienteDoProfessor Begin******/
    String professorAmbiente();
    String professorAmbienteCompText1();
    String professorAmbienteCompText2();
    String professorAmbienteCompText3();
    String professorAmbienteCompText4();
    String professorAmbienteCompText5();
    String professorAmbienteCompText6();    
    
    String professorAmbienteAvaliacao();
    String professorAmbienteAvaliacaoCompText1();
    String professorAmbienteAvaliacaoCompText2();
    
    String professorAmbienteNotas();
    String professorAmbienteNotasCompText1();
    String professorAmbienteNotasCompText2();
    
    String professorAmbienteOcorrencia();
    String professorAmbienteOcorrenciaCompText1();
    String professorAmbienteOcorrenciaCompText2();
    
    String professorAmbienteComunicado();
    String professorAmbienteComunicadoCompText1();
    String professorAmbienteComunicadoCompText2();    
    
    String professorAmbienteTopico();
    String professorAmbienteTopicoCompText1();
    String professorAmbienteTopicoCompText2();
    String professorAmbienteTopicoCompText3();
    
    String professorAmbienteHierarquia();    
    String professorAmbienteHierarquiaCompText1();
    String professorAmbienteHierarquiaCompText2();
    		
    /******AmbienteDoProfessor End******/
    
    
    /******AmbienteDoAluno Begin******/
    String alunoAmbiente();
    
	String alunoAmbienteNotas();
	String alunoAmbienteNotasCompText1();
	String alunoAmbienteNotasCompText2();
	String alunoAmbienteNotasCompText3();

    String alunoAmbienteComunicado();
    String alunoAmbienteComunicadoCompText1();
    String alunoAmbienteComunicadoCompText2();    
    
    String alunoAmbienteHierarquia();
    String alunoAmbienteHierarquiaCompText1();
    String alunoAmbienteHierarquiaCompText2();    

    String alunoAmbienteAgenda();
    String alunoAmbienteAgendaCompText1();
    String alunoAmbienteAgendaCompText2();
    String alunoAmbienteAgendaCompText3();
    String alunoAmbienteAgendaCompText4();    

    String alunoAmbienteOcorrencia();    
    String alunoAmbienteOcorrenciaCompText1();
    String alunoAmbienteOcorrenciaCompText2();
    String alunoAmbienteOcorrenciaCompText3();
    /******AmbienteDoAluno End******/
    
    
    /******AmbienteDosPais Begin******/
    String paisAmbiente();
    String paisAmbienteCompText1();
    String paisAmbienteCompText2();
    String paisAmbienteCompText3();
    
    String paisAmbienteAgenda();
    String paisAmbienteAgendaCompText1();
    String paisAmbienteAgendaCompText2();
    String paisAmbienteAgendaCompText3();
    String paisAmbienteAgendaCompText4();      

    
    String paisAmbienteComunicado();
    String paisAmbienteComunicadoCompText1();
    String paisAmbienteComunicadoCompText2();
    
    String paisAmbienteNota(); 
    String paisAmbienteNotaCompText1(); 
    String paisAmbienteNotaCompText2(); 
    String paisAmbienteNotaCompText3(); 
    
    String paisAmbienteOcorrencia();    
    String paisAmbienteOcorrenciaAluno();
    String paisAmbienteOcorrenciaCompText1();   
    String paisAmbienteOcorrenciaCompText2();
    String paisAmbienteOcorrenciaCompText3();
    String paisAmbienteOcorrenciaCompText4();
    
    String paisAmbienteHierarquia();    
    String paisAmbienteHierarquiaCompText1();  
    String paisAmbienteHierarquiaCompText2();  
    /******AmbienteDosPais End******/
	
	
	
	/******Curso Begin******/
	String curso();
	String cursoNome();
	String cursoNovoCurso();
	String cursoDescricao();
	String cursoEmenta();
	String cursoMediaNota();
	String cursoPorcentagemPresenca();
	String cursoDataInicial();
	String cursoDataFinal();
	String cursoSalvar();
	String cursoAdicionar();
	String cursoEditar();
	String cursoAdicionarAlunoAoCurso();
	String cursoSelecionarCurso();
	String cursoSelecionarAluno();
	String cursoFiltrar();
	String cursoErroSalvar();
	String cursoErroCarregar();
	String cursoErroCarregarLista();
	String cursoSalvoSucesso();
	String cursoCampoNomeObrigatorio();
	String cursoErroAtualizar();
	String cursoErroRemover();
	String cursoRemoverTitle();
	String cursoRemoverMsg(String nomeCurso);
	String cursoAlunosAssociados();
	String cursoAssociarAlunos();
	String cursoAlunosDoCurso();
	String cursoErroAssociarAluno();
	String cursoAlunosInseridosComSucesso();
	String cursoTemplate();
	String cursoUsarCursoJaCriado();
	String cursoNavegacao();	
	String cursoCompText1();
	String cursoCompText2();
	String cursoCompText3();	
	String cursoAdmin();
	String cursoAdminCompText1();
	String cursoAdminCompText2();
	String cursoAdminCompText3();
	String cursoImportarAlunosDoCurso();
	String cursoTodosAlunos();
	
	/*******Curso End*******/
	
	
	
	/*******Periodo Begin*******/
	String periodo();	
	String periodoNome();
	String periodoDescricao();
	String periodoObjetivo();
	String periodoDataInicial();
	String periodoDataFinal();	
	String periodoAdicionar();	
	String periodoEditar();
	String periodoDesejaRemover(String peridoNome);
	String periodoNenhum();
	String periodoErroSalvar();
	String periodoErroAtualizar();
	String periodoSalvoSucesso();
	String periodoCompText1();
	String periodoCompText2();
	String periodoCompText3();
	/*******Periodo End*******/
	
	
	/*******Disciplina Begin*******/
	String disciplina();	
	String disciplinaNome();
	String disciplinaCarga();
	String disciplinaDescricao();
	String disciplinaObjetivo();
	String disciplinaErroSalvar();
	String disciplinaSalva();
	String disciplinaNenhum();
	String disciplinaDesejaRemover(String nomeDisciplina);
	String disciplinaSelecionarProfessor();
	String disciplinaFiltrarProfessor();
	String disciplinaSelecionar();
	String disciplinaAssociadas();
	String disciplinaAssociarProfessores();
	String disciplinaErroAssociarProfessor();
	String disciplinaErroAtualizar();
	String disciplinaErroRemover();
	String disciplinaErroCarregar();
	String disciplinaErroBuscarProfessores();
	String disciplinaAssociadosComSucesso();
	String disciplinaAdicionar();
	String disciplinaEditar();
	String disciplinaAdicionarProfessor();	
	String disciplinaCompText1();
	String disciplinaCompText2();
	String disciplinaCompText3();
	/*******Disciplina End*******/	
	
	/*******ConteudoProgramatico Begin*******/
	String conteudoProgramatico();	
	String conteudoProgramaticoAdicionar();
	String conteudoProgramaticoEditar();
	String conteudoProgramaticoNome();
	String conteudoProgramaticoNumeracao();
	String conteudoProgramaticoDescricao();
	String conteudoProgramaticoObjetivo();
	String conteudoProgramaticoSalvo();
	String conteudoProgramaticoNenhum();
	String conteudoProgramaticoErroSalvar();
	String conteudoProgramaticoErroAtualizar();
	String conteudoProgramaticoErroRemover();
	String conteudoProgramaticoErroCarregar();
	String conteudoProgramaticoDesejaRemover(String nomeConteudoProgramatico);	
	String conteudoProgramaticoCompText1();
	String conteudoProgramaticoCompText2();
	String conteudoProgramaticoCompText3();
	/*******ConteudoProgramatico End*******/	
	

	/*******Tópicos Begin*******/
	String topico();	
	String topicoAdicionar();
	String topicoEditar();
	String topicoNome();
	String topicoNumeracao();
	String topicoDescricao();
	String topicoObjetivo();
	String topicoErroSalvar();
	String topicoErroAtualizar();
	String topicoErroCarregar();
	String topicoSalvo();
	String topicoDesejaRemover(String nomeTopico);
	String topicoNenhum();	
	String topicoCompText1();
	String topicoCompText2();
	String topicoCompText3();
	/*******Topicos End*******/
	
	
	/*******Usuario Begin*******/
	String usuario();
	String usuarioAdicionar();
	String usuarioEditar();
	String usuarioAssociarPaisAlunos();
	String usuarioPrimeiroNome();
	String usuarioSobreNome();
	String usuarioCPF();
	String usuarioEmail();
	String usuarioDataNascimento();
	String usuarioTelCelular();
	String usuarioTelResidencial();
	String usuarioTelComercial();
	String usuarioTipo();
	String usuarioSenha();
	String usuarioErroSalvar();
	String usuarioSalvo();
	String usuarioSelecioneAluno();
	String usuarioNomeAluno();
	String usuarioFiltrarListaAlunos();
	String usuarioSelecionaPais();
	String usuarioNomePais();
	String usuarioErroBuscarPais();
	String usuarioAssociarPaisAluno();
	String usuarioErroAssociarPaisAluno();
	String usuarioPaisAlunoAssociadosComSucesso();
	String usuarioErroAtualizar();
	String usuarioErroRemover();
	String usuarioErroCarregar();
	String usuarioDesejaRemover(String primeiroNome, String sobreNome);
	String usuarioPaisAssociados();
	String usuarioCompText1();
	String usuarioCompText2();
	String usuarioCompText3();
	String usuarioCompText4();
	String usuarioFiltrarUsuario();
	String usuarioImportar();
	String usuarioSelecionarArquivo();
	String usuarioProcurar();
	String usuarioSelecionarTemplate();
	String usuarioArquivoExcelTemplate();
	String usuarioSelecionarArquivoExcel();
	String usuarioImportarArquivoExcel();
	String usuarioListaUsuariosInvalidos();
	String usuarioPlanilhaImportadoComSucesso();
	String usuarioErroImportarUsuarios();
	String usuarioErroImportarUsuariosNull();
	String usuarioRespAcademico();
	String usuarioRespFinanceiro();
	String usuarioDataMatricula();
	String usuarioMatricula();
	String usuarioRG();
	String usuarioSexo();
	String usuarioEndereco(); 
	String usuarioNumRes();
	String usuarioBairro();
	String usuarioCidade();
	String usuarioEstado();
	String usuarioCep();
	String usuarioEmpresa(); 
	String usuarioCargo();
	String usuarioResponsavel();
	String usuarioTipoPai();
	String usuarioSituacaoDosPais(); 
	String usuarioCasados();
	String usuarioSeparados();
	String usuarioOutros();	
	String usuarioErroLoginDuplicado(String login);
	String usuarioRegistroAluno();
	String usuarioUnidadeEscola();
	String usuarioBaixarPlanilhaExcel();
	String usuarioCliqueAquiParaBaixar();
	String usuarioRegistro();
	/*******Usuario End*******/
	
	
	/*******Comunicado Begin*******/
	String comunicado();
	
	
	String comunicadoAssunto();
	String comunicadoTipo();
	String comunicadoFiltrarComunicado();
	String comunicadoAdicionarNovo();
	String comunicadoImagem();
	String comunicadoDetalhes();
	String comunicadoData();
	String comunicadoHora();
	String comunicadoDesejaRemover(String assuntoComunicado);
	String comunicadoAtualizar();
	String comunicadoAdicionar();
	String comunicadoRetornarTela();
	String comunicadoInsiraComunicado();
	String comunicadoSelecionaImagem();
	String comunicadoEscolherImagem();
	String comunicadoInsiraDescricao();
	String comunicadoErroSalvar();
	String comunicadoErroCarregar();
	String comunicadoSalvo();
	String comunicadoVerDetalhes();
	/*******Comunicado End*******/	
	
	
	/*******Aluno Begin*******/
	String aluno();
	String alunoNome();
	/*******Aluno End*******/
	
	/*******NOTA End*******/
	String nota();
	String notaSelecionarAluno();
	String notaErroCarregar();
	String notaMediaFinal();
	String notaSelecionarAlunoDesejaAdicionarAlterarNota();
	String notaErroBuscarAluno();
	String notaInserirAlterarNotasAluno();
	String notaEditarNotas();
	String notaNehumaNota();
	String notaErroAtualizar();
	/*******NOTA End*******/	
	
	/*******PROFESSOR Begin*******/
	String professor();
	String professorNome();
	/*******PROFESSOR End*******/
	
	
	/*******Pais Begin*******/
	String pais();
	String paisNome();
   /*******Pais End*******/
	
	/*******AVALIAÇÔES Begin*******/
	String avaliacao();
	String avaliacaoAdicionar();
	String avaliacaoEditar();
	String avaliacaoAssunto();
	String avaliacaoDescricao();
	String avaliacaoTipo();
	String avaliacaoData();
	String avaliacaoHora();	
	String avaliacaoNenhumaAvaliacaoNoConteudo();
	String avaliacaoErroCarregar();
	String avaliacaoErroSalvar();
	String avaliacaoErroAtualizar();
	String avaliacaoErroRemover();
	String avaliacaoNenhumaCurso();
	String avaliacaoNenhumaDisciplina();
	String avaliacaoSelecioneCurso();	
	String avaliacaoSalva();
	String avaliacaoDesejaRemover(String assuntoAvaliacao);
	/*******AVALIAÇÔES End*******/
	
	
	/*******AGENDA BEGIN*******/
	String agenda();
	String agendaCalendario();
	String agendaAtividades();
	String agendaDia();
	String agendaSemana();
	String agendaMes();
	String agendaTrabalho();
	String agendaProva();
	String agendaExercicio();
	String agendaSetDayOfMonthFormat();
	String agendaSetTimeFormat();
	String agendaSetDateFormat();
	String agendaSetDayOfWeekFormat();
	String agendaSetAm();
	String agendaSetPm();
	String agendaSetNoon();
	String agendaDetalhes();

	/*******AGENDA END*******/
	
	
	/*******Ocorrencia BEGIN*******/
	String ocorrencia();
	String ocorrenciaPorAluno();
	String ocorrenciaNehumaOcorrencia();
	String ocorrenciaPaiCiente();
	String ocorrenciaDescricao();
	String ocorrenciaData();
	String ocorrenciaHora();
	String ocorrenciaErroAtualizar();
	String ocorrenciaErroCarregar();
	String ocorrenciaErroBuscarAluno();	
	String ocorrenciaErroSalvar();
	String ocorrenciaSalva();
	String ocorrenciaAtualizada();
	String ocorrenciaAdicionarNovaOcorrencia();
	String ocorrenciaEditar();
	String ocorrenciaSelecionarConteudo();
	String ocorrenciaEditarDetalhes();
	String ocorrenciaDesejaRemover(String nomeOcorrencia);
	String ocorrenciaPreencherCampo();
	String ocorrenciaSelecionarAlunoEnviar();
	String ocorrenciaAlunosAssociados();
	String ocorrenciaNomeDosAlunos();
	String ocorrenciaSalvarOcorrencia();
	String ocorrenciaErroSemUmConteudoProgramatico();
	String ocorrenciaLiberarLeituraPai();
	String ocorrenciaFiltrar();
	String ocorrenciaParaAprovar();
	String ocorrenciaJaAprovadas();
	String ocorrenciaVisualizar();
	String ocorrenciaAprovar();
	/*******Ocorrencia END*******/
	
	
	
	/******* PRESENCA BEGIN *******/
	String presenca();
	String presencaData();
	String presencaSalva();
	String presencaSalvarListaPresenca();
	String presencaErroCarregarDados();
	String presencaNumeroFaltas();
	String presencaErroAtualizar();
	String presencaErroSalvar();
	String presencaCriarLista();
	String presencaEditarLista();
	String presencaVisualizarLista();
	String presencaQuantidadeAulas();
	String presencaQuantidadePresenca();
	String presencaQuantidadeFaltas();
	String presencaQuantidadeJustificativas();
	String presencaSalaDeAula();
	String presencaSituacao();
	String presencaAmbienteDiario();
	String presencaAmbienteDiarioAcompanhamento();
	String presencaAmbienteDiarioPresenca();
	String presencaAmbienteDiarioConteudo();
	String presencaRemoverMsg(String presencaText);
	/******* PRESENCA END *******/
	
	

	/*******USO GERAL BEGIN*******/
	String geralCarregando();
	String geralAviso();
	String geralConfirmacao();
	String geralEditar();
	String geralRemover();
	String geralAtualizar();
	String geralSim();
	String geralNao();
	String geralFiltrar();
	String geralAjuda();
	String geralSalvar();
	String geralCancelar();
	String geralCampoObrigatorio(String campo);
	String geralEmailInvalido(String campo);
	String geralErroAtualizar(String campo);
	String geralErroCarregar(String campo);
	String geralErroCarregarDados();
	String geralErroRemover(String campo);
	String geralErroTipo(String campo);
	String erroPopularCombobox();
	String geralLimpar();
	String geralSair();
	String geralOk();
	String geralNumeroInteiro();
	String geralNumeroDouble();
	String geralRecarregarAmbiente();
	String geralErro();
	String geralFecharJanela();
	String geralRegarregarPagina();
	String geralBreakLine();
	String geralImprimir();
	String geralExcel();
	/*******USO GERAL END*******/
	
	/*******CONFIGURACOES BEGIN*******/
	String idioma();
	String idiomaIngles();
	String idiomaPortugues();
	String idiomaErroSalvar();
	String senhaAlterar();
	String senhaErroSalvar();
	String senhaNaoConfere();
	String senhaErroSenha();
	/*******CONFIGURACOES END*******/
	
	/*******AJUDA BEGIN*******/
	String ajuda();
	String ajudaConteudo();
	String ajudaSobre();
	String ajudaText1();
	String ajudaText2();
	String ajudaText3();	
	/*******AJUDA END*******/
	
	
	/*******ESTADOS BEGIN*******/
	String ufAcre();
	String ufAlagoas();
	String ufAmapa();
	String ufAmazonas();   
	String ufBahia(); 
	String ufCeara();
	String ufDistritoFederal();
	String ufEspiritoSanto();
	String ufGoias();
	String ufMaranhao();  
	String ufMatoGrosso();
	String ufMatoGrossoDoSul();
	String ufMinasGerais();
	String ufPara();
	String ufParaiba();  
	String ufParana();
	String ufPernambuco();
	String ufPiaui();
	String ufRioDeJaneiro();
	String ufRioGrandeDoSul();
	String ufRioGrandeDoNorte();
	String ufRondonia();
	String ufRoraima();
	String ufSantaCatarina();  
	String ufSaoPaulo();
	String ufSergipe();  
	String ufTocantins();  
	/*******ESTADOS END*******/
	
	/*******Tipos Pais BEGIN*******/
	String tipoPaiMae();
	String tipoPaiPai();
	String tipoPaiMadastra(); 
	String tipoPaiPadastro();
	String tipoPaiOutros();
	/*******Tipos Pais END*******/	
	
	/*******Sexo BEGIN*******/
	String sexoFeminino();
	String sexoMasculino(); 
	/*******Sexo END*******/
	
	/*******Documento BEGIN*******/
	String documentoSelecionarContrato();
	String documentoContrato();
	String documentoFiltrarPais();
	String documentoPorCurso();
	String documentoPorNome();
	String documentoCursoParaFilterPais();
	String documentoFiltro(); 
	String documentoReponsavel();
	String documentoEnviarContratoPorEmail();
	String documentoEmailNovosPais();
	String documentoErroEmail();
	String documentoFinanceiro();
	String documentoAcademico();
	String documentoTodos();
	String documentoImprimirContrato();
	/*******Documento END*******/
	
	
	/*******EMAIL BEGIN*******/
	String emailEnviado();
    String emailAssunto();
    String emailEmail();
    String emailAnexo();
    String emailFiltroCurso();
    String emailPara();
    String emailTipoUsuario(); 
    String emailPeloMenosUsuarioPrecisaSerSelecionado();  
    String emailAbrirTelaParaVisualizarEmails();
    String emailTodosOsCursos();
    String emailEscolherArquivo();
    String emailEnviarEmail();
    /*******EMAIL END*******/

}
