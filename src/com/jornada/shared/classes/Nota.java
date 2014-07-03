package com.jornada.shared.classes;

import java.io.Serializable;

public class Nota implements Serializable {

	private static final long serialVersionUID = 9221572507882503398L;
	
	private int idNota;	
	private int idUsuario;
	private String nomeUsuario;
	private int idAvaliacao;
	private String nota;
	
	
//	public static final String STR_NOTA_ESCOLA = ConfigJornada.getProperty("config.media.nota");
	

	
	public Nota(){
		
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public int getIdAvaliacao() {
		return idAvaliacao;
	}

	public void setIdAvaliacao(int idAvaliacao) {
		this.idAvaliacao = idAvaliacao;
	}

	public int getIdNota() {
		return idNota;
	}

	public void setIdNota(int idNota) {
		this.idNota = idNota;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}


	
	
	

}
