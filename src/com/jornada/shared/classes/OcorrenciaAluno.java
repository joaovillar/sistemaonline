package com.jornada.shared.classes;

import java.io.Serializable;

public class OcorrenciaAluno implements Serializable{

	private static final long serialVersionUID = 5726512755511867012L;
	
	private String nomeCurso;
	private String nomePeriodo;
	private String nomeDisciplina;
	private String nomeConteudoProgramatico;
	private int idOcorrencia;
	private int idUsuario;
	private String assunto;
	private String descricao;
	private String data;
	private String hora;
	private boolean paiCiente;
	private Usuario usuario;
		
	public OcorrenciaAluno(){
		
		usuario = new Usuario();
		
	}

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public String getNomePeriodo() {
		return nomePeriodo;
	}

	public void setNomePeriodo(String nomePeriodo) {
		this.nomePeriodo = nomePeriodo;
	}

	public String getNomeDisciplina() {
		return nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	public String getNomeConteudoProgramatico() {
		return nomeConteudoProgramatico;
	}

	public void setNomeConteudoProgramatico(String nomeConteudoProgramatico) {
		this.nomeConteudoProgramatico = nomeConteudoProgramatico;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public boolean isPaiCiente() {
		return paiCiente;
	}

	public void setPaiCiente(boolean paiCiente) {
		this.paiCiente = paiCiente;
	}

	public int getIdOcorrencia() {
		return idOcorrencia;
	}

	public void setIdOcorrencia(int idOcorrencia) {
		this.idOcorrencia = idOcorrencia;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	

}
