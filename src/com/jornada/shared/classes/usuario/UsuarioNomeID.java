package com.jornada.shared.classes.usuario;

import java.io.Serializable;

public class UsuarioNomeID implements Serializable {

    private static final long serialVersionUID = 6706337014362796734L;
    
    private String NomeUsuario;
    private int idUsuario;

    public UsuarioNomeID() {

    }

    public String getNomeUsuario() {
        return NomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        NomeUsuario = nomeUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }



}
