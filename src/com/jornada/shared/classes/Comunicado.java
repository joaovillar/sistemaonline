package com.jornada.shared.classes;

import java.io.Serializable;
import java.util.Date;

public class Comunicado implements Serializable{

	private static final long serialVersionUID = 7796284603094153903L;
	
	private int idComunicado;
	private int idTipoComunicado;
	private int idEscola;		
	private String assunto;
	private String descricao;
	private Date data;
	private String hora;	  
	private String nomeImagem;
	
	public Comunicado(){
		
	}

	public int getIdComunicado() {
		return idComunicado;
	}

	public void setIdComunicado(int idComunicado) {
		this.idComunicado = idComunicado;
	}

	public int getIdTipoComunicado() {
		return idTipoComunicado;
	}

	public void setIdTipoComunicado(int idTipoComunicado) {
		this.idTipoComunicado = idTipoComunicado;
	}

	public int getIdEscola() {
		return idEscola;
	}

	public void setIdEscola(int idEscola) {
		this.idEscola = idEscola;
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

	public String getNomeImagem() {
		return nomeImagem;
	}

	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}



	
	

}
