package com.jornada.shared.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.view.client.ProvidesKey;

public class Periodo implements Serializable, Comparable<Periodo>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4710332602168218200L;
	
	public static final String DB_UNIQUE_KEY = "unike_idcurso_nomeperiodo";
	
	private int idCurso;
	private int idPeriodo;
	private String nomePeriodo;
	private String descricao;
	private String numeracao;
	private String objetivo;
	private String peso;
	private Date dataInicial;
	private Date dataFinal;
	
	private ArrayList<Disciplina> listDisciplinas;
	
//	private Curso curso;

	
	public Periodo(){
//		curso = new Curso();	
		listDisciplinas = new ArrayList<Disciplina>();
	}
	
	
		
//	public Curso getCurso() {
//		return curso;
//	}
//
//
//
//	public void setCurso(Curso curso) {
//		this.curso = curso;
//	}






	public int getIdPeriodo() {
		return idPeriodo;
	}
	
	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	
	public Date getDataInicial() {
		return dataInicial;
	}
	
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}


	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public int getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public String getNomePeriodo() {
		return nomePeriodo;
	}

	public void setNomePeriodo(String nomePeriodo) {
		this.nomePeriodo = nomePeriodo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNumeracao() {
		return numeracao;
	}

	public void setNumeracao(String numeracao) {
		this.numeracao = numeracao;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	
	
	public ArrayList<Disciplina> getListDisciplinas() {
		return listDisciplinas;
	}


	public void setListDisciplinas(ArrayList<Disciplina> listDisciplinas) {
		this.listDisciplinas = listDisciplinas;
	}
	
	public String getPeso() {
	    if(peso==null)peso="1";
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }



    public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String toString(){
		return "projeto:com.jornada.shared.classes.Periodo";
	}
	
    @Override
    public int compareTo(Periodo o) {
      return (o == null || o.nomePeriodo == null) ? -1 : -o.nomePeriodo.compareTo(nomePeriodo);
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Periodo) {
        return idPeriodo == ((Periodo) o).idPeriodo;
      }
      return false;
    }
    
    public static final ProvidesKey<Periodo> KEY_PROVIDER = new ProvidesKey<Periodo>() {
        @Override
        public Object getKey(Periodo item) {
          return item == null ? null : Integer.toString(item.getIdPeriodo());
        }
      };  

}
