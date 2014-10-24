package com.jornada.shared.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class ConteudoProgramatico implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7532017045185296833L;

	private int idConteudoProgramatico;
	private int idDisciplina;
	private String nome;
	private String numeracao;	
	private String descricao;
	private String objetivo;
	
//	private Periodo periodo;
	
	private ArrayList<Topico> listTopico;

	
	
	public ConteudoProgramatico(){
		
//		periodo = new Periodo();
		listTopico = new ArrayList<Topico>();
	}
	
	public int getIdConteudoProgramatico() {
		return idConteudoProgramatico;
	}

	public void setIdConteudoProgramatico(int idConteudoProgramatico) {
		this.idConteudoProgramatico = idConteudoProgramatico;
	}

	public int getIdDisciplina() {
		return idDisciplina;
	}

	public void setIdDisciplina(int idDisciplina) {
		this.idDisciplina = idDisciplina;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

//	public Periodo getPeriodo() {
//		return periodo;
//	}
//
//	public void setPeriodo(Periodo periodo) {
//		this.periodo = periodo;
//	}

	public String getNumeracao() {
		return numeracao;
	}

	public void setNumeracao(String numeracao) {
		this.numeracao = numeracao;
	}

	public ArrayList<Topico> getListTopico() {
		return listTopico;
	}

	public void setListTopico(ArrayList<Topico> listTopico) {
		this.listTopico = listTopico;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String toString(){
		return "projeto:com.jornada.shared.classes.ConteudoProgramatico";
	}

	
}
