package com.jornada.shared.classes.boletim;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class TableMultipleBoletimAluno implements Serializable{
    
    private static final long serialVersionUID = -8936590499054963478L;
    
    private int idCurso;
    private int idAluno;
    
    private String nomeCurso;    
    private String nomeAluno;    

    
    private boolean paraImprimir;

    
    public TableMultipleBoletimAluno(){
        
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
    
    


    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public boolean isParaImprimir() {
        return paraImprimir;
    }

    public void setParaImprimir(boolean paraImprimir) {
        this.paraImprimir = paraImprimir;
    }



    public static final ProvidesKey<TableMultipleBoletimAluno> KEY_PROVIDER = new ProvidesKey<TableMultipleBoletimAluno>() {
        @Override
        public Object getKey(TableMultipleBoletimAluno item) {
          return item == null ? null : Integer.toString(item.getIdCurso())+":"+Integer.toString(item.getIdAluno());
        }
      };



    
}
