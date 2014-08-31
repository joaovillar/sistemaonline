package com.jornada.client.ambiente.professor.ocorrencia;

import com.jornada.shared.classes.Ocorrencia;

class OcorrenciaAux {
	
	private int idCurso;
	private int idPeriodo;
	private int idDisciplina;
	private int idConteudoProgramatico;
	
	private String nomeCurso;
	private String nomePeriodo;
	private String nomeDisciplina;
	private String nomeConteudoProgramatico;
	
	
	private Ocorrencia ocorrencia;
	
	public OcorrenciaAux(){
		
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public int getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public int getIdDisciplina() {
		return idDisciplina;
	}

	public void setIdDisciplina(int idDisciplina) {
		this.idDisciplina = idDisciplina;
	}

	public int getIdConteudoProgramatico() {
		return idConteudoProgramatico;
	}

	public void setIdConteudoProgramatico(int idConteudoProgramatico) {
		this.idConteudoProgramatico = idConteudoProgramatico;
	}

	public Ocorrencia getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(Ocorrencia ocorrencia) {
		this.ocorrencia = ocorrencia;
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



	
	
}
