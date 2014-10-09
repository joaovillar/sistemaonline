package com.jornada.shared.classes;

import java.io.Serializable;

public class RelDocumentoUsuario implements Serializable {

    private static final long serialVersionUID = 6808097265191158633L;
    
    private int idRelDocumentoUsuario;
    private int idUsuario;
    private int idTipoUsuario;
    private int idCurso;
    private int idDocumento;
    private boolean isEmailSent;
    
    private Usuario usuario;

    
    public RelDocumentoUsuario(){
        usuario = new Usuario();
    }


    public int getIdRelDocumentoUsuario() {
        return idRelDocumentoUsuario;
    }


    public void setIdRelDocumentoUsuario(int idRelDocumentoUsuario) {
        this.idRelDocumentoUsuario = idRelDocumentoUsuario;
    }





    public int getIdUsuario() {
        return idUsuario;
    }


    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }


    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }


    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }


    public int getIdCurso() {
        return idCurso;
    }


    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }


    public int getIdDocumento() {
        return idDocumento;
    }


    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }


    public boolean isEmailSent() {
        return isEmailSent;
    }


    public void setEmailSent(boolean isEmailSent) {
        this.isEmailSent = isEmailSent;
    }


    public Usuario getUsuario() {
        return usuario;
    }


    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }









}
