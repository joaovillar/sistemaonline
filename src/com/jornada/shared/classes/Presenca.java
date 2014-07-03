package com.jornada.shared.classes;

import java.io.Serializable;

public class Presenca implements Serializable{

	private static final long serialVersionUID = -4501316690819274492L;
	
	public static final int PRESENCA = 1;
	public static final int FALTA = 2;
	public static final int FALTA_JUSTIFICADA = 3;
	public static final int SEM_MATRICULA = 4;
	
	
	private int idPresenca;
	private int idAula;
	private int idUsuario;
	private int idTipoPresenca;
	
	private TipoPresenca tipoPresenca;
	
	public Presenca(){
		tipoPresenca = new TipoPresenca();
	}

	public int getIdPresenca() {
		return idPresenca;
	}

	public void setIdPresenca(int idPresenca) {
		this.idPresenca = idPresenca;
	}

	public int getIdAula() {
		return idAula;
	}

	public void setIdAula(int idAula) {
		this.idAula = idAula;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdTipoPresenca() {
		return idTipoPresenca;
	}

	public void setIdTipoPresenca(int idTipoPresenca) {
		this.idTipoPresenca = idTipoPresenca;
	}

	public TipoPresenca getTipoPresenca() {
		return tipoPresenca;
	}

	public void setTipoPresenca(TipoPresenca tipoPresenca) {
		this.tipoPresenca = tipoPresenca;
	}
	
	
	

}
