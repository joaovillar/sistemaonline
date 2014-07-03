package com.jornada.shared.classes;

import java.io.Serializable;

public class Idioma implements Serializable{

	private static final long serialVersionUID = -3397266033239470984L;
	
	private int idIdioma;
	private String nomeIdioma;
	private String locale;
	
	
	public Idioma(){
		
	}


	public int getIdIdioma() {
		return idIdioma;
	}


	public void setIdIdioma(int idIdioma) {
		this.idIdioma = idIdioma;
	}


	public String getNomeIdioma() {
		return nomeIdioma;
	}


	public void setNomeIdioma(String nomeIdioma) {
		this.nomeIdioma = nomeIdioma;
	}


	public String getLocale() {
		return locale;
	}


	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	
	

}
