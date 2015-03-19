package com.jornada.shared.classes.boletim;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class TableMultipleBoletimDisciplina implements Serializable{
    
    private static final long serialVersionUID = -8936590499054963478L;
    
    private int idCurso;
    private int idPeriodo;
    private int idDisciplina;
    
    private String nomeCurso;    
    private String nomePeriodo;    
    private String nomeDisciplina;
    
    private boolean paraImprimir;

    
    public TableMultipleBoletimDisciplina(){
        
    }

    public int getIdCurso() {
        return idCurso;
    }


    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
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

    public boolean isParaImprimir() {
        return paraImprimir;
    }

    public void setParaImprimir(boolean paraImprimir) {
        this.paraImprimir = paraImprimir;
    }



    public static final ProvidesKey<TableMultipleBoletimDisciplina> KEY_PROVIDER = new ProvidesKey<TableMultipleBoletimDisciplina>() {
        @Override
        public Object getKey(TableMultipleBoletimDisciplina item) {
          return item == null ? null : Integer.toString(item.getIdCurso()+item.getIdPeriodo()+item.getIdDisciplina());
        }
      };



    
}
