package com.jornada.shared.classes.boletim;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class TableMultipleBoletimPeriodo implements Serializable{
    
    private static final long serialVersionUID = -8936590499054963478L;
    
    private int idCurso;
    private int idPeriodo;
    
    private String nomeCurso;    
    private String nomePeriodo;    
    
    private boolean paraImprimir;

    
    public TableMultipleBoletimPeriodo(){
        
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

    public boolean isParaImprimir() {
        return paraImprimir;
    }

    public void setParaImprimir(boolean paraImprimir) {
        this.paraImprimir = paraImprimir;
    }



    public static final ProvidesKey<TableMultipleBoletimPeriodo> KEY_PROVIDER = new ProvidesKey<TableMultipleBoletimPeriodo>() {
        @Override
        public Object getKey(TableMultipleBoletimPeriodo item) {
          return item == null ? null : Integer.toString(item.getIdCurso()+item.getIdPeriodo());
        }
      };



    
}
