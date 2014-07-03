package com.jornada.shared.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Aula implements Serializable{

	private static final long serialVersionUID = -4785913202831073638L;
	
	private int idAula;
	private int idDisciplina;
	private Date data;
	

	private ArrayList<Presenca> arrayPresenca;
	
	
	public Aula(){
		
	}


	public int getIdAula() {
		return idAula;
	}


	public void setIdAula(int idAula) {
		this.idAula = idAula;
	}


	public int getIdDisciplina() {
		return idDisciplina;
	}


	public void setIdDisciplina(int idDisciplina) {
		this.idDisciplina = idDisciplina;
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
	}


	public ArrayList<Presenca> getArrayPresenca() {
		return arrayPresenca;
	}


	public void setArrayPresenca(ArrayList<Presenca> arrayPresenca) {
		this.arrayPresenca = arrayPresenca;
	}
	
	
	
	
	

}
