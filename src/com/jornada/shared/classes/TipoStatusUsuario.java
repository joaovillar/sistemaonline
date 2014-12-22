package com.jornada.shared.classes;

import java.io.Serializable;

public class TipoStatusUsuario implements Serializable{

    private static final long serialVersionUID = -3160635726141013468L;
    
    public static final int ALUNO_ATIVO=1;
    public static final int ALUNO_TRANSFERIDO=2;
    public static final int ALUNO_CONCLUIDO=3;
    public static final int COORDENADOR_ATIVO=4;
    public static final int COORDENADOR_DESATIVADO=5;
    public static final int PROFESSOR_ATIVO=6;
    public static final int PROFESSOR_DESATIVADO=7;
    public static final int PAIS_ATIVO=8;
    public static final int PAIS_DESATIVADO=9;
    public static final int ADMINISTRADOR_ATIVO=10;
    public static final int ADMINISTRADOR_DESATIVADO=11;
    
    public static final String DB_ID_TIPO_STATUS_USUARIO = "id_tipo_status_usuario";
    
    private int idTipoStatusUsuario;
    private String nomeTipoStatusUsuario;
    private String DescricaoStatusUsuario;
    private int idTipoUsuario;
    
    
    public TipoStatusUsuario(){
        
    }


    public int getIdTipoStatusUsuario() {
        return idTipoStatusUsuario;
    }


    public void setIdTipoStatusUsuario(int idTipoStatusUsuario) {
        this.idTipoStatusUsuario = idTipoStatusUsuario;
    }


    public String getNomeTipoStatusUsuario() {
        return nomeTipoStatusUsuario;
    }


    public void setNomeTipoStatusUsuario(String nomeTipoStatusUsuario) {
        this.nomeTipoStatusUsuario = nomeTipoStatusUsuario;
    }


    public String getDescricaoStatusUsuario() {
        return DescricaoStatusUsuario;
    }


    public void setDescricaoStatusUsuario(String descricaoStatusUsuario) {
        DescricaoStatusUsuario = descricaoStatusUsuario;
    }


    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }


    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }


 

    
    
    
}
