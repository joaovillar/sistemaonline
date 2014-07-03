package com.jornada.shared.classes.boletim;

import java.io.Serializable;

public class TabelaBoletim implements Serializable{
	

	private static final long serialVersionUID = -9215381663621079271L;
	
	private String NomeCurso;
	private String NomePeriodo;
	private String NomeDisciplina;
	private String NomeConteudoProgramatico;
	private String NomeAvaliacao;
	private String Nota;

	public TabelaBoletim() {

	}

	public String getNomeCurso() {
		return NomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		NomeCurso = nomeCurso;
	}

	public String getNomePeriodo() {
		return NomePeriodo;
	}

	public void setNomePeriodo(String nomePeriodo) {
		NomePeriodo = nomePeriodo;
	}

	public String getNomeDisciplina() {
		return NomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		NomeDisciplina = nomeDisciplina;
	}

	public String getNomeConteudoProgramatico() {
		return NomeConteudoProgramatico;
	}

	public void setNomeConteudoProgramatico(String nomeConteudoProgramatico) {
		NomeConteudoProgramatico = nomeConteudoProgramatico;
	}

	public String getNomeAvaliacao() {
		return NomeAvaliacao;
	}

	public void setNomeAvaliacao(String nomeAvaliacao) {
		NomeAvaliacao = nomeAvaliacao;
	}

	public String getNota() {
		return Nota;
	}

	public void setNota(String nota) {
		Nota = nota;
	}
	
	

}
