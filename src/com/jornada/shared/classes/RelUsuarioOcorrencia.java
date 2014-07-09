package com.jornada.shared.classes;

import java.io.Serializable;

public class RelUsuarioOcorrencia implements Serializable {

	private static final long serialVersionUID = 6808097265191158633L;
	
	private int idRelUsuarioOcorrencia;
	private int idUsuario;
	private int idOcorrencia;
	private boolean paiCiente;	
	
	public RelUsuarioOcorrencia(){
		
	}

	public int getIdRelUsuarioOcorrencia() {
		return idRelUsuarioOcorrencia;
	}

	public void setIdRelUsuarioOcorrencia(int idRelUsuarioOcorrencia) {
		this.idRelUsuarioOcorrencia = idRelUsuarioOcorrencia;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdOcorrencia() {
		return idOcorrencia;
	}

	public void setIdOcorrencia(int idOcorrencia) {
		this.idOcorrencia = idOcorrencia;
	}

	public boolean isPaiCiente() {
		return paiCiente;
	}

	public void setPaiCiente(boolean paiCiente) {
		this.paiCiente = paiCiente;
	}
	
	


}
