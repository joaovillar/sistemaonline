package com.jornada.shared.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Ocorrencia implements Serializable {

	private static final long serialVersionUID = 6519840683304628830L;
	
	private int idOcorrencia;
	private String assunto;
	private String descricao;
	private int idDisciplina;
	private Date data;
	private String hora;
	
	private ArrayList<Usuario> listUsuariosRelacionadosOcorrencia;
	
	
	public Ocorrencia(){		
		listUsuariosRelacionadosOcorrencia = new  ArrayList<Usuario>();		
	}

	
	public int getIdOcorrencia() {
		return idOcorrencia;
	}
	public void setIdOcorrencia(int idOcorrencia) {
		this.idOcorrencia = idOcorrencia;
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
	public int getIdDisciplina() {
		return idDisciplina;
	}
	public void setIdDisciplina(int idDisciplina) {
		this.idDisciplina = idDisciplina;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}	

	public ArrayList<Usuario> getListUsuariosRelacionadosOcorrencia() {
		return listUsuariosRelacionadosOcorrencia;
	}

	public void setListUsuariosRelacionadosOcorrencia(ArrayList<Usuario> listUsuariosRelacionadosOcorrencia) {
		this.listUsuariosRelacionadosOcorrencia = listUsuariosRelacionadosOcorrencia;
	}
	

}
