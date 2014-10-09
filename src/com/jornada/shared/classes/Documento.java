package com.jornada.shared.classes;

import java.io.Serializable;

public class Documento implements Serializable {

	private static final long serialVersionUID = 7663750057940951949L;
	
	public static final int DOCUMENTO_CONTRATO_ALUNO=3;
	
	private int idDocumento;
    private String nomeDocumento;
    private String descricao;
    private String nomeFisicoDocumento;
    private boolean isEmailSent;
    private String emailSubject;
    private String emailContent;
    
    public Documento(){
        
    }
	
	
	public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNomeDocumento() {
        return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {
        this.nomeDocumento = nomeDocumento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeFisicoDocumento() {
        return nomeFisicoDocumento;
    }

    public void setNomeFisicoDocumento(String nomeFisicoDocumento) {
        this.nomeFisicoDocumento = nomeFisicoDocumento;
    }


    public boolean isEmailSent() {
        return isEmailSent;
    }


    public void setEmailSent(boolean isEmailSent) {
        this.isEmailSent = isEmailSent;
    }


    public String getEmailSubject() {
        return emailSubject;
    }


    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }


    public String getEmailContent() {
        return emailContent;
    }


    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }




	
	

}
