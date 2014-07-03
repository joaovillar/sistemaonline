package com.jornada.shared.classes;

import java.io.Serializable;

public class TipoPresenca implements Serializable{

	private static final long serialVersionUID = 2267928968229946710L;
	
	
	private int idTipoPresenca;
	private String tipoPresenca;
	
	public TipoPresenca(){
		
	}

	public int getIdTipoPresenca() {
		return idTipoPresenca;
	}

	public void setIdTipoPresenca(int idTipoPresenca) {
		this.idTipoPresenca = idTipoPresenca;
	}

	public String getTipoPresenca() {
		return tipoPresenca;
	}

	public void setTipoPresenca(String tipoPresenca) {
		this.tipoPresenca = tipoPresenca;
	}
	
	
	

}
