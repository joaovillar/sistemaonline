package com.jornada.shared.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Avaliacao implements Serializable{	
	
	private static final long serialVersionUID = 7710560979869058468L;
	
	private int idAvaliacao;
//	private int idConteudoProgramatico;
	private int idDisciplina;
	private int idTipoAvaliacao;
	private String assunto;
	private String descricao;
	private boolean valeNota;
	private String pesoNota;
	private Date data;
	private String hora;
	
	
	private ArrayList<Nota> listNota;
	private TipoAvaliacao tipoAvaliacao;
	
	public Avaliacao(){
		listNota = new ArrayList<Nota>();
		tipoAvaliacao = new TipoAvaliacao();
	}

	public int getIdAvaliacao() {
		return idAvaliacao;
	}

	public void setIdAvaliacao(int idAvaliacao) {
		this.idAvaliacao = idAvaliacao;
	}

	public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public int getIdTipoAvaliacao() {
		return idTipoAvaliacao;
	}

	public void setIdTipoAvaliacao(int idTipoAvaliacao) {
		this.idTipoAvaliacao = idTipoAvaliacao;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
	
	

	public boolean isValeNota() {
        return valeNota;
    }

    public void setValeNota(boolean valeNota) {
        this.valeNota = valeNota;
    }
    
    

    public String getPesoNota() {
        return pesoNota;
    }

    public void setPesoNota(String pesoNota) {
        this.pesoNota = pesoNota;
    }

    public ArrayList<Nota> getListNota() {
		return listNota;
	}

	public void setListNota(ArrayList<Nota> listNota) {
		this.listNota = listNota;
	}

	public TipoAvaliacao getTipoAvaliacao() {
		return tipoAvaliacao;
	}

	public void setTipoAvaliacao(TipoAvaliacao tipoAvaliacao) {
		this.tipoAvaliacao = tipoAvaliacao;
	}

}
