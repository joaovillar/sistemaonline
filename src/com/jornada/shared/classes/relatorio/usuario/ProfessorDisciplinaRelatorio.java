package com.jornada.shared.classes.relatorio.usuario;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;
import com.jornada.shared.classes.disciplina.ProfessorDisciplina;

public class ProfessorDisciplinaRelatorio extends ProfessorDisciplina implements Serializable{
	

	private static final long serialVersionUID = -9215381663621079271L;
	
	private int idCurso;
	private String nomeCurso;
	private String registroDocente;
	
    public int getIdCurso() {
        return idCurso;
    }
    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }
    
       
    public String getRegistroDocente() {
        return registroDocente;
    }
    public void setRegistroDocente(String registroDocente) {
        this.registroDocente = registroDocente;
    }
    public String getNomeCurso() {
        return nomeCurso;
    }
    
    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }    
	
    public static final ProvidesKey<ProfessorDisciplinaRelatorio> KEY_PROVIDER = new ProvidesKey<ProfessorDisciplinaRelatorio>() {
        @Override
        public Object getKey(ProfessorDisciplinaRelatorio item) {
          return item == null ? null : Integer.toString(item.getIdUsuario()+item.getIdCurso()+item.getIdPeriodo()+item.getIdDisciplina());
        }
      };  
	
}
