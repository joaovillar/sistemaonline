package com.jornada.shared.classes;

import java.io.Serializable;

public class TipoAvaliacao implements Serializable{

	private static final long serialVersionUID = 3807262093299630070L;
	
	public static final String EXISTE_RECUPERACAO = "false:existe.recuperacao";
	public static final String STR_RECUPERACAO = "Recuperação";
	
	public static final int INT_TRABALHO_INDIVIDUAL=1;
	public static final int INT_TRABALHO_GRUPO=2;
	public static final int INT_PROVA_INDIVIDUAL=3;
	public static final int INT_PROVA_GRUPO=4;
	public static final int INT_EXERCICIO_FIXACAO=5;
    public static final int INT_RECUPERACAO=6;	
	
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
