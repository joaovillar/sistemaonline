package com.jornada.shared.classes;

import java.io.Serializable;

public class UnidadeEscola implements Serializable {

	private static final long serialVersionUID = 7663750057940951949L;
	
	public static final String DB_NOME_UNIDADE_ESCOLA = "nome_unidade_escola";
	public static final int INT_AMBAS = 3;


	private int idUnidadeEscola;
	private String nomeUnidadeEscola;
	
	public UnidadeEscola(){
		
	}

	public int getIdUnidadeEscola() {
		return idUnidadeEscola;
	}

	public void setIdUnidadeEscola(int idUnidadeEscola) {
		this.idUnidadeEscola = idUnidadeEscola;
	}

	public String getNomeUnidadeEscola() {
		return nomeUnidadeEscola;
	}

	public void setNomeUnidadeEscola(String nomeUnidadeEscola) {
		this.nomeUnidadeEscola = nomeUnidadeEscola;
	}
	
	

}
