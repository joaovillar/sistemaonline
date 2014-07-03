package com.jornada.shared.classes;

import java.io.Serializable;
import java.util.Date;

public class CursoAvaliacao implements Serializable{
	
	private static final long serialVersionUID = 2875157536071782506L;
	
	private String nomeCurso;
	private String nomePeriodo;
	private String nomeDisciplina;
	private String nomeConteudoProgramatico;
	private String nomeAvaliacao;
//	private Date dataAvaliacao;
//	private Time horaAvaliacao;
	private Date dataAvaliacao;
	private String horaAvaliacao;	
	private int idTipoAvaliacao;
	
	public static final int INT_TRABALHO_INDIVIDUAL=1;
	public static final int INT_TRABALHO_GRUPO=2;
	public static final int INT_PROVA_INDIVIDUAL=3;
	public static final int INT_PROVA_GRUPO=4;
	public static final int INT_EXERCICIO_FIXACAO=5;
	
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
	public String getNomeConteudoProgramatico() {
		return nomeConteudoProgramatico;
	}
	public void setNomeConteudoProgramatico(String nomeConteudoProgramatico) {
		this.nomeConteudoProgramatico = nomeConteudoProgramatico;
	}
	public String getNomeAvaliacao() {
		return nomeAvaliacao;
	}
	public void setNomeAvaliacao(String nomeAvaliacao) {
		this.nomeAvaliacao = nomeAvaliacao;
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
	
	
	

}
