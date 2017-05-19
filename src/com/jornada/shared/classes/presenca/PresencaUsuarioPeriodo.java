package com.jornada.shared.classes.presenca;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;
import com.jornada.shared.classes.Usuario;

public class PresencaUsuarioPeriodo implements Serializable{

	private static final long serialVersionUID = -2874758914766643316L;
	
	private Usuario usuario;
	private int idPeriodo;
	private String nomePeriodo;
	private int numeroAulas;
	private int numeroFaltas;
	
	public PresencaUsuarioPeriodo(){
		usuario = new Usuario();
//		presenca = new Presenca();
		
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	



	public String getNomePeriodo() {
        return nomePeriodo;
    }

    public void setNomePeriodo(String nomePeriodo) {
        this.nomePeriodo = nomePeriodo;
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
    
    

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    @Override
	public String toString() {
		return "PresencaUsuarioAula [idUsuario=" + usuario.getIdUsuario() + ", idDisciplina="
				+ idPeriodo + "]";
	}
	
    public static final ProvidesKey<PresencaUsuarioPeriodo> KEY_PROVIDER = new ProvidesKey<PresencaUsuarioPeriodo>() {
        @Override
        public Object getKey(PresencaUsuarioPeriodo item) {
            return item == null ? null : 
//                Integer.toString(item.getAula().getIdAula())+
//                Integer.toString(item.getPresenca().getIdPresenca())+
                Integer.toString(item.getUsuario().getIdUsuario());
        }
      };  

}
