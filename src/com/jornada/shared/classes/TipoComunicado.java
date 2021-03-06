package com.jornada.shared.classes;

import java.io.Serializable;

public class TipoComunicado implements Serializable {
	
	private static final long serialVersionUID = -2835085052301514592L;
	
	
	public final static String TIPO_COMUNICADO="comunicado";
	public final static String TIPO_EMAIL="email"; 
	
	public final static int MURAL=1;/**Mural (Aberto a todos)**/		
	public final static int INTERNO=2;/**Interno (Coordenadores, Professores)**/
	public final static int EXTERNO=5;/**Externo (Coordenadores, Professores, Pais, Alunos)**/
	
	public final static int EMAIL=8;/**Email (Coordenadores, Professores, Pais, Alunos)**/
	public final static int EMAIL_ALUNO_PAIS_PROFESSORES=13;/**Email (Coordenadores, Professores, Pais, Alunos)**/
	public final static int EMAIL_COORDENADORES=8;/**Email (Coordenadores, Administradores)**/
	public final static int EMAIL_INDIVIDUAL=11;/**Email (Coordenadores, Professores, Pais, Alunos)**/
	
	private  int idTipoComunicado;
	private  String tipoComunicadoNome;
	private  String descricao;
	
	public TipoComunicado(){
		
	}

	public int getIdTipoComunicado() {
		return idTipoComunicado;
	}

	public void setIdTipoComunicado(int idTipoComunicado) {
		this.idTipoComunicado = idTipoComunicado;
	}

	public String getTipoComunicadoNome() {
		return tipoComunicadoNome;
	}

	public void setTipoComunicadoNome(String tipoComunicadoNome) {
		this.tipoComunicadoNome = tipoComunicadoNome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
