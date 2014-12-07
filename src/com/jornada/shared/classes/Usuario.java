package com.jornada.shared.classes;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.view.client.ProvidesKey;

public class Usuario implements Serializable, Comparable<Usuario>  {

	private static final long serialVersionUID = 3304488184672078101L;
	
	
	public static final String DB_UNIQUE_LOGIN = "unique_login";
	public static final String DB_PRIMEIRO_NOME = "primeiro_nome";
	public static final String DB_SOBRE_NOME = "sobre_nome";
	public static final String DB_EMAIL = "email";
	public static final String DB_CPF = "cpf";
	
	
	private int idUsuario;
	private String login;
	private String senha;
	private String primeiroNome;
	private String sobreNome;
	private String cpf;	
	private String email;
	private String telefoneCelular;
	private String telefoneResidencial;
	private String telefoneComercial;
	private String endereco;
	private String numeroResidencia;
	private String bairro;
	private String cidade;
	private String unidadeFederativa;
	private String cep;
	private Date dataMatricula;
	private String rg;
	private String sexo;
	private String empresaOndeTrabalha;
	private String cargo;
	private boolean respAcademico;
	private boolean respFinanceiro;
	private String registroMatricula;
	private String tipoPais;
	private String situacaoResponsaveis;
	private String situacaoResponsaveisOutros;
	private String registroAluno;
	private int idUnidadeEscola;
	private String observacao;

	
	private int idTipoUsuario;
	private int idIdioma;
	private int idTipoStatusUsuario;
	
	private Date dataNascimento;
	
	private String sessionId;
	private boolean loggedIn;
	private boolean primeiroLogin;

	private TipoUsuario tipoUsuario;
	private Idioma idioma;
	private UnidadeEscola unidadeEscola;
	private TipoStatusUsuario tipoStatusUsuario;

	public Usuario() {
		this.tipoUsuario = new TipoUsuario();
		this.idioma = new Idioma();
		this.unidadeEscola = new UnidadeEscola();
		this.tipoStatusUsuario= new TipoStatusUsuario(); 

	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public int getIdTipoUsuario() {
		return idTipoUsuario;
	}

	public void setIdTipoUsuario(int idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public String getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public void setTelefoneResidencial(String telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}

	public String getTelefoneComercial() {
		return telefoneComercial;
	}

	public void setTelefoneComercial(String telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
    public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	
	public int getIdIdioma() {
		return idIdioma;
	}

	public void setIdIdioma(int idIdioma) {
		this.idIdioma = idIdioma;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}
	
	public boolean isPrimeiroLogin() {
		return primeiroLogin;
	}
	public String getEndereco() {
		return endereco;
	}

	public void setPrimeiroLogin(boolean primeiroLogin) {
		this.primeiroLogin = primeiroLogin;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumeroResidencia() {
		return numeroResidencia;
	}

	public void setNumeroResidencia(String numeroResidencia) {
		this.numeroResidencia = numeroResidencia;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUnidadeFederativa() {
		return unidadeFederativa;
	}

	public void setUnidadeFederativa(String unidadeFederativa) {
		this.unidadeFederativa = unidadeFederativa;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Date getDataMatricula() {
		return dataMatricula;
	}

	public void setDataMatricula(Date dataMatricula) {
		this.dataMatricula = dataMatricula;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEmpresaOndeTrabalha() {
		return empresaOndeTrabalha;
	}

	public void setEmpresaOndeTrabalha(String empresaOndeTrabalha) {
		this.empresaOndeTrabalha = empresaOndeTrabalha;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public boolean isRespAcademico() {
		return respAcademico;
	}

	public void setRespAcademico(boolean respAcademico) {
		this.respAcademico = respAcademico;
	}

	public boolean isRespFinanceiro() {
		return respFinanceiro;
	}

	public void setRespFinanceiro(boolean respFinanceiro) {
		this.respFinanceiro = respFinanceiro;
	}

	public String getRegistroMatricula() {
		return registroMatricula;
	}

	public void setRegistroMatricula(String registroMatricula) {
		this.registroMatricula = registroMatricula;
	}

	public String getTipoPais() {
		return tipoPais;
	}

	public void setTipoPais(String tipoPais) {
		this.tipoPais = tipoPais;
	}

	public String getSituacaoResponsaveis() {
		return situacaoResponsaveis;
	}

	public void setSituacaoResponsaveis(String situacaoResponsaveis) {
		this.situacaoResponsaveis = situacaoResponsaveis;
	}

	public String getSituacaoResponsaveisOutros() {
		return situacaoResponsaveisOutros;
	}

	public void setSituacaoResponsaveisOutros(String situacaoResponsaveisOutros) {
		this.situacaoResponsaveisOutros = situacaoResponsaveisOutros;
	}
	
	public String getRegistroAluno() {
		return registroAluno;
	}

	public void setRegistroAluno(String registroAluno) {
		this.registroAluno = registroAluno;
	}
	

	public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public UnidadeEscola getUnidadeEscola() {
		return unidadeEscola;
	}

	public void setUnidadeEscola(UnidadeEscola unidadeEscola) {
		this.unidadeEscola = unidadeEscola;
	}

	public int getIdUnidadeEscola() {
		return idUnidadeEscola;
	}

	public void setIdUnidadeEscola(int idUnidadeEscola) {
		this.idUnidadeEscola = idUnidadeEscola;
	}
	
	

	public int getIdTipoStatusUsuario() {
        return idTipoStatusUsuario;
    }

    public void setIdTipoStatusUsuario(int idTipoStatusUsuario) {
        this.idTipoStatusUsuario = idTipoStatusUsuario;
    }

    public TipoStatusUsuario getTipoStatusUsuario() {
        return tipoStatusUsuario;
    }

    public void setTipoStatusUsuario(TipoStatusUsuario tipoStatusUsuario) {
        this.tipoStatusUsuario = tipoStatusUsuario;
    }

    @Override
    public int compareTo(Usuario o) {
      return (o == null || (o.primeiroNome+o.sobreNome) == null) ? -1 : -(o.primeiroNome+o.sobreNome).compareTo((o.primeiroNome+o.sobreNome));
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Periodo) {
        return idUsuario == ((Usuario) o).idUsuario;
      }
      return false;
    }
    
    public static final ProvidesKey<Usuario> KEY_PROVIDER = new ProvidesKey<Usuario>() {
        @Override
        public Object getKey(Usuario item) {
          return item == null ? null : Integer.toString(item.getIdUsuario());
        }
      }; 	

}
