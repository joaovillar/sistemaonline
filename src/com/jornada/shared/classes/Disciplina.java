package com.jornada.shared.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Disciplina implements Serializable {


	private static final long serialVersionUID = -2606695220488374696L;
	
	public static final String DB_UNIQUE_KEY = "unike_idperiodo_nomedisciplina";
	 
	private int idPeriodo;
	private int idDisciplina;
	private int idUsuario;
	private String nome;
	private int cargaHoraria;	
	private String descricao;
	private String objetivo;
	
	private Usuario professor;
	
	
	private ArrayList<ConteudoProgramatico> listConteudoProgramatico;
	private ArrayList<Aula> listAula;
	private ArrayList<Avaliacao> listAvaliacao;
	
	
	public Disciplina(){
		
//		periodo = new Periodo();
		professor = new Usuario();
		listConteudoProgramatico = new ArrayList<ConteudoProgramatico>();
		listAula = new ArrayList<Aula>();
		listAvaliacao = new ArrayList<Avaliacao>();
		
	}
	
	

	public Usuario getProfessor() {
		return professor;
	}



	public void setProfessor(Usuario professor) {
		this.professor = professor;
	}



	public int getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public int getIdDisciplina() {
		return idDisciplina;
	}

	public void setIdDisciplina(int idDisciplina) {
		this.idDisciplina = idDisciplina;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}	

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}


    public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public ArrayList<ConteudoProgramatico> getListConteudoProgramatico() {
		return listConteudoProgramatico;
	}

	public void setListConteudoProgramatico(
			ArrayList<ConteudoProgramatico> listConteudoProgramatico) {
		this.listConteudoProgramatico = listConteudoProgramatico;
	}

	public ArrayList<Aula> getListAula() {
		return listAula;
	}
	
	public void setListAula(ArrayList<Aula> listAula) {
		this.listAula = listAula;
	}
	
	



	public ArrayList<Avaliacao> getListAvaliacao() {
        return listAvaliacao;
    }



    public void setListAvaliacao(ArrayList<Avaliacao> listAvaliacao) {
        this.listAvaliacao = listAvaliacao;
    }
    
    public String getMediaAlunoDisciplina(int idUsuario){
        String media = "";
        int countNota=0;
        double somaMedia=0;
        for (Avaliacao avaliacao : getListAvaliacao()) {
            
            if (avaliacao.getIdDisciplina() == this.getIdDisciplina()) {
                
                for (Nota nota : avaliacao.getListNota()) {
                    
                    if (nota.getIdUsuario() == idUsuario) {
                        countNota++;
                        somaMedia = (somaMedia + Double.parseDouble(nota.getNota()));
                    }
                    
                }
                
            }
            
        }
        
        if (countNota != 0) {
            somaMedia = somaMedia / countNota;
            media = Double.toString(somaMedia);
        }
        
        return media;
        
    }



    public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	public String toString(){
		return "projeto:com.jornada.shared.classes.Disciplina";
	}
	


}
