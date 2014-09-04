package com.jornada.shared.classes;

import java.io.Serializable;

public class RelUsuarioOcorrencia implements Serializable {

	private static final long serialVersionUID = 6808097265191158633L;

	private int idRelUsuarioOcorrencia;
	private int idUsuario;
	private int idOcorrencia;
	private String usuarioPrimeiroNome;
	private String usuarioSobreNome;
	private String assunto;
	private String descricao;
	private boolean paiCiente;
	private boolean liberarLeituraPai;

	public RelUsuarioOcorrencia() {

	}

	public String getUsuarioPrimeiroNome() {
		return usuarioPrimeiroNome;
	}

	public void setUsuarioPrimeiroNome(String usuarioPrimeiroNome) {
		this.usuarioPrimeiroNome = usuarioPrimeiroNome;
	}

	public String getUsuarioSobreNome() {
		return usuarioSobreNome;
	}

	public void setUsuarioSobreNome(String usuarioSobreNome) {
		this.usuarioSobreNome = usuarioSobreNome;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getIdRelUsuarioOcorrencia() {
		return idRelUsuarioOcorrencia;
	}

	public void setIdRelUsuarioOcorrencia(int idRelUsuarioOcorrencia) {
		this.idRelUsuarioOcorrencia = idRelUsuarioOcorrencia;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdOcorrencia() {
		return idOcorrencia;
	}

	public void setIdOcorrencia(int idOcorrencia) {
		this.idOcorrencia = idOcorrencia;
	}

	public boolean isPaiCiente() {
		return paiCiente;
	}

	public void setPaiCiente(boolean paiCiente) {
		this.paiCiente = paiCiente;
	}

	public boolean isLiberarLeituraPai() {
		return liberarLeituraPai;
	}

	public void setLiberarLeituraPai(boolean liberarLeituraPai) {
		this.liberarLeituraPai = liberarLeituraPai;
	}

}
