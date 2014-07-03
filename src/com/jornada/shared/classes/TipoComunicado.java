package com.jornada.shared.classes;

import java.io.Serializable;

public class TipoComunicado implements Serializable {
	
	private static final long serialVersionUID = -2835085052301514592L;
	
	private  int idTipoComunicado;
	private  String tipoComunicadoNome;
	private  String descricao;
	
	public TipoComunicado(){
		
	}

	public int getIdTipoComunicado() {
		return idTipoComunicado;
	}

	public void setIdTipoComunicado(int idTipoComunicado) {
		this.idTipoComunicado = idTipoComunicado;
	}

	public String getTipoComunicadoNome() {
		return tipoComunicadoNome;
	}

	public void setTipoComunicadoNome(String tipoComunicadoNome) {
		this.tipoComunicadoNome = tipoComunicadoNome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
