package com.jornada.shared.classes.disciplina;

import java.io.Serializable;

public class ProfessorDisciplina implements Serializable{


    private static final long serialVersionUID = 5674567101808495151L;
    private int idPeriodo;
    private int idDisciplina;
    private int idUsuario;
    private String nomePeriodo;    
    private String nomeDisciplina;    
    private String primeiroNome;
    private String sobreNome;

	
	public ProfessorDisciplina(){
		
	}

	
	
	
    public int getIdUsuario() {
        return idUsuario;
    }




    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }




    public String getNomePeriodo() {
        return nomePeriodo;
    }

    public void setNomePeriodo(String nomePeriodo) {
        this.nomePeriodo = nomePeriodo;
    }


    
    
    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }


    public String getPrimeiroNome() {
        if(null==primeiroNome)primeiroNome="";
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getSobreNome() {
        if(null==sobreNome)sobreNome="";
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }


	
	
	
	
	

}
