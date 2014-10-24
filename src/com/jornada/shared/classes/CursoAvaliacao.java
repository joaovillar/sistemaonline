package com.jornada.shared.classes;

import java.io.Serializable;
import java.util.Date;

public class CursoAvaliacao implements Serializable{
	
	private static final long serialVersionUID = 2875157536071782506L;
	
	private String nomeCurso;
	private String nomePeriodo;
	private String nomeDisciplina;
//	private String nomeConteudoProgramatico;
	private int idAvaliacao;
	private String assuntoAvaliacao;
	private String descricaoAvaliacao;
	private Date dataAvaliacao;
	private String horaAvaliacao;	
	private int idTipoAvaliacao;
	private String descricaoTipoAvaliacao;
	

	
	public CursoAvaliacao(){
		
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
//	public String getNomeConteudoProgramatico() {
//		return nomeConteudoProgramatico;
//	}
//	public void setNomeConteudoProgramatico(String nomeConteudoProgramatico) {
//		this.nomeConteudoProgramatico = nomeConteudoProgramatico;
//	}
	public String getAssuntoAvaliacao() {
		return assuntoAvaliacao;
	}
	public void setAssuntoAvaliacao(String nomeAvaliacao) {
		this.assuntoAvaliacao = nomeAvaliacao;
	}	
	public int getIdTipoAvaliacao() {
		return idTipoAvaliacao;
	}
	public void setIdTipoAvaliacao(int idTipoAvaliacao) {
		this.idTipoAvaliacao = idTipoAvaliacao;
	}


	public Date getDataAvaliacao() {
		return dataAvaliacao;
	}


	public void setDataAvaliacao(Date dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}


	public String getHoraAvaliacao() {
		return horaAvaliacao;
	}


	public void setHoraAvaliacao(String horaAvaliacao) {
		this.horaAvaliacao = horaAvaliacao;
	}


	public int getIdAvaliacao() {
		return idAvaliacao;
	}


	public void setIdAvaliacao(int idAvaliacao) {
		this.idAvaliacao = idAvaliacao;
	}


	public String getDescricaoAvaliacao() {
		return descricaoAvaliacao;
	}


	public void setDescricaoAvaliacao(String descricao) {
		this.descricaoAvaliacao = descricao;
	}


	public String getDescricaoTipoAvaliacao() {
		return descricaoTipoAvaliacao;
	}


	public void setDescricaoTipoAvaliacao(String descricaoTipoAvaliacao) {
		this.descricaoTipoAvaliacao = descricaoTipoAvaliacao;
	}


}
