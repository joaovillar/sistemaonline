package com.jornada.shared.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.view.client.ProvidesKey;

public class Curso implements Serializable, Comparable<Curso> {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8273266710711270812L;
	
	private int idCurso;
	private String nome;
	private String descricao;
	private String ementa;
	private Date dataInicial;
	private Date dataFinal;
	private String mediaNota;
	private String porcentagemPresenca;
	
	
	private ArrayList<Periodo> listPeriodos;
	
	private ArrayList<Usuario> listAlunos;



	public Curso() {
		listPeriodos = new ArrayList<Periodo>();
		listAlunos = new ArrayList<Usuario>();
	}
	
	
	public ArrayList<Periodo> getListPeriodos() {
		return listPeriodos;
	}



	public ArrayList<Usuario> getListAlunos() {
		return listAlunos;
	}

	public void setListAlunos(ArrayList<Usuario> listAlunos) {
		this.listAlunos = listAlunos;
	}

	public void setListPeriodos(ArrayList<Periodo> listPeriodos) {
		this.listPeriodos = listPeriodos;
	}



	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEmenta() {
		return ementa;
	}

	public void setEmenta(String ementa) {
		this.ementa = ementa;
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
	
	
	

	public String getMediaNota() {
		return mediaNota;
	}


	public void setMediaNota(String mediaNota) {
		this.mediaNota = mediaNota;
	}


	public String getPorcentagemPresenca() {
		return porcentagemPresenca;
	}


	public void setPorcentagemPresenca(String porcentagemPresenca) {
		this.porcentagemPresenca = porcentagemPresenca;
	}


	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String toString(){
		return "projeto:com.jornada.shared.classes.Curso";
	}



    @Override
    public int compareTo(Curso o) {
      return (o == null || o.nome == null) ? -1 : -o.nome.compareTo(nome);
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Curso) {
        return idCurso == ((Curso) o).idCurso;
      }
      return false;
    }
    
    public static final ProvidesKey<Curso> KEY_PROVIDER = new ProvidesKey<Curso>() {
        @Override
        public Object getKey(Curso item) {
          return item == null ? null : Integer.toString(item.getIdCurso());
        }
      };    
	
	
	

}
