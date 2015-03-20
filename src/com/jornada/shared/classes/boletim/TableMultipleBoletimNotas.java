package com.jornada.shared.classes.boletim;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class TableMultipleBoletimNotas implements Serializable{
    
    private static final long serialVersionUID = -8936590499054963478L;
    
    private int idCurso;
    
    private String nomeCurso;    
    
    private boolean paraImprimir;

    
    public TableMultipleBoletimNotas(){
        
    }

    public int getIdCurso() {
        return idCurso;
    }


    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }


    public String getNomeCurso() {
        return nomeCurso;
    }


    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }


    public boolean isParaImprimir() {
        return paraImprimir;
    }

    public void setParaImprimir(boolean paraImprimir) {
        this.paraImprimir = paraImprimir;
    }



    public static final ProvidesKey<TableMultipleBoletimNotas> KEY_PROVIDER = new ProvidesKey<TableMultipleBoletimNotas>() {
        @Override
        public Object getKey(TableMultipleBoletimNotas item) {
          return item == null ? null : Integer.toString(item.getIdCurso());
        }
      };



    
}
