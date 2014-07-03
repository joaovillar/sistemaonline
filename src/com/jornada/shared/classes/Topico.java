package com.jornada.shared.classes;

import java.io.Serializable;

public class Topico implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2862188719945331129L;
	private int idTopico;
	private int idConteudoProgramatico;	
	private String nome;
	private String numeracao;	
	private String descricao;
	private String objetivo;
	

	
	public Topico(){		
	}
	
	
	
	public int getIdTopico() {
		return idTopico;
	}



	public void setIdTopico(int idTopico) {
		this.idTopico = idTopico;
	}



	public int getIdConteudoProgramatico() {
		return idConteudoProgramatico;
	}

	public void setIdConteudoProgramatico(int idConteudoProgramatico) {
		this.idConteudoProgramatico = idConteudoProgramatico;
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

	public String getNumeracao() {
		return numeracao;
	}

	public void setNumeracao(String numeracao) {
		this.numeracao = numeracao;
	}
	
	public String toString(){
		return "projeto:com.jornada.shared.classes.Topico";
	}


	
}
