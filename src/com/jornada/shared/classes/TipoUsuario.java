package com.jornada.shared.classes;

import java.io.Serializable;

public class TipoUsuario implements Serializable {

	private static final long serialVersionUID = -2027240557520365855L;
	
	
	
	
	private int idTipoUsuario;
	private String nomeTipoUsuario;

	public static final String DB_NOME_TIPO_USUARIO = "nome_tipo_usuario";
	public final static int COORDENADOR=1;	
	public final static int PROFESSOR=2;	
	public final static int PAIS=3;
	public final static int ALUNO=4;
	public final static int VISITANTE=5;
	public final static int ADMINISTRADOR=6;


	public TipoUsuario() {
	}

	public int getIdTipoUsuario() {
		return idTipoUsuario;
	}

	public void setIdTipoUsuario(int idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}

	public String getNomeTipoUsuario() {
		return nomeTipoUsuario;
	}

	public void setNomeTipoUsuario(String nomeTipoUsuario) {
		this.nomeTipoUsuario = nomeTipoUsuario;
	}

}
