package com.jornada.shared.classes;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.view.client.ProvidesKey;

public class Usuario implements Serializable, Comparable<Usuario>  {

	private static final long serialVersionUID = 3304488184672078101L;
	private int idUsuario;
	private String login;
	private String senha;
	private String primeiroNome;
	private String sobreNome;
	private String cpf;
	private Date dataNascimento;
	private int idTipoUsuario;
	private String email;
	private String telefoneCelular;
	private String telefoneResidencial;
	private String telefoneComercial;	 
	private int idIdioma;
	
	private String sessionId;
	private boolean loggedIn;
	private boolean primeiroLogin;

	private TipoUsuario tipoUsuario;
	private Idioma idioma;

	public Usuario() {
		this.tipoUsuario = new TipoUsuario();
		this.idioma = new Idioma();

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

	public void setPrimeiroLogin(boolean primeiroLogin) {
		this.primeiroLogin = primeiroLogin;
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
