package com.jornada.shared.classes.presenca;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;
import com.jornada.shared.classes.Aula;
import com.jornada.shared.classes.Presenca;
import com.jornada.shared.classes.Usuario;

public class PresencaUsuarioAula implements Serializable{

	private static final long serialVersionUID = -2874758914766643316L;
	
	private Usuario usuario;
	private Presenca presenca;
	private Aula aula;
//	private int idTipoPresenca;
	
	public PresencaUsuarioAula(){
		usuario = new Usuario();
		presenca = new Presenca();
		
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Presenca getPresenca() {
		return presenca;
	}

	public void setPresenca(Presenca presenca) {
		this.presenca = presenca;
	}

	public Aula getAula() {
		return aula;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
	}

	@Override
	public String toString() {
		return "PresencaUsuarioAula [usuario=" + usuario + ", presenca="
				+ presenca + ", aula=" + aula + "]";
	}
	
    public static final ProvidesKey<PresencaUsuarioAula> KEY_PROVIDER = new ProvidesKey<PresencaUsuarioAula>() {
        @Override
        public Object getKey(PresencaUsuarioAula item) {
            return item == null ? null : 
                Integer.toString(item.getAula().getIdAula())+
                Integer.toString(item.getPresenca().getIdPresenca())+
                Integer.toString(item.getUsuario().getIdUsuario());
        }
      };  

}
