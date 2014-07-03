package com.jornada.shared.classes;

import java.io.Serializable;

public class TipoAvaliacao implements Serializable{

	private static final long serialVersionUID = 3807262093299630070L;
	
	private int idTipoAvaliacao;
	private String nomeTipoAvaliacao;
	private String descricao;
	
	public TipoAvaliacao(){
		
	}

	public int getIdTipoAvaliacao() {
		return idTipoAvaliacao;
	}

	public void setIdTipoAvaliacao(int idTipoAvaliacao) {
		this.idTipoAvaliacao = idTipoAvaliacao;
	}

	public String getNomeTipoAvaliacao() {
		return nomeTipoAvaliacao;
	}

	public void setNomeTipoAvaliacao(String nomeTipoAvaliacao) {
		this.nomeTipoAvaliacao = nomeTipoAvaliacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
