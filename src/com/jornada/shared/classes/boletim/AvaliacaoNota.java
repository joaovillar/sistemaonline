package com.jornada.shared.classes.boletim;

import java.io.Serializable;

import com.jornada.shared.classes.Avaliacao;

public class AvaliacaoNota extends Avaliacao implements Serializable{

    private static final long serialVersionUID = -8936590499054963478L;
    
    private String nomeCurso;
    private String nomePeriodo;
    private String nomeDisciplina;
    private double nota;
    
    public AvaliacaoNota(){
        
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public String getNomePeriodo() {
        return nomePeriodo;
    }

    public void setNomePeriodo(String nomePeriodo) {
        this.nomePeriodo = nomePeriodo;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
    
    


}
