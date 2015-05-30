package com.jornada.shared.classes.presenca;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;
import com.jornada.shared.classes.Usuario;

public class PresencaUsuarioDisciplina implements Serializable{

	private static final long serialVersionUID = -2874758914766643316L;
	
	private Usuario usuario;
	private int idDisciplina;
	private String nomeDisciplina;
	private int numeroAulas;
	private int numeroFaltas;
	
	public PresencaUsuarioDisciplina(){
		usuario = new Usuario();
//		presenca = new Presenca();
		
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	



	public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public int getNumeroAulas() {
        return numeroAulas;
    }

    public void setNumeroAulas(int numeroAulas) {
        this.numeroAulas = numeroAulas;
    }

    public int getNumeroFaltas() {
        return numeroFaltas;
    }

    public void setNumeroFaltas(int numeroFaltas) {
        this.numeroFaltas = numeroFaltas;
    }
    
    

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    @Override
	public String toString() {
		return "PresencaUsuarioAula [idUsuario=" + usuario.getIdUsuario() + ", idDisciplina="
				+ idDisciplina + "]";
	}
	
    public static final ProvidesKey<PresencaUsuarioDisciplina> KEY_PROVIDER = new ProvidesKey<PresencaUsuarioDisciplina>() {
        @Override
        public Object getKey(PresencaUsuarioDisciplina item) {
            return item == null ? null : 
//                Integer.toString(item.getAula().getIdAula())+
//                Integer.toString(item.getPresenca().getIdPresenca())+
                Integer.toString(item.getUsuario().getIdUsuario());
        }
      };  

}
