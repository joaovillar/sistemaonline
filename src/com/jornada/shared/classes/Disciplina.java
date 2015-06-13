package com.jornada.shared.classes;

import java.io.Serializable;
import java.util.ArrayList;

import com.jornada.shared.utility.MpUtilShared;

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
	private boolean isObrigatoria;
	
	
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
	

	public boolean isObrigatoria() {
        return isObrigatoria;
    }



    public void setObrigatoria(boolean isObrigatoria) {
        this.isObrigatoria = isObrigatoria;
    }



    public ArrayList<Avaliacao> getListAvaliacao() {
        return listAvaliacao;
    }



    public void setListAvaliacao(ArrayList<Avaliacao> listAvaliacao) {
        this.listAvaliacao = listAvaliacao;
    }
    
    public String getMediaAlunoDisciplina(Curso curso, ArrayList<Periodo> listPeriodo, int idUsuario, boolean calcularRecuperacao){
        String media = "";
        double countPesoNota=0;
        double somaMediaPonderada=0;
        double notaRecuperacao=0;
        double notaAdicional=0;
        

        for (Avaliacao avaliacao : getListAvaliacao()) {
           
            if (avaliacao.getIdTipoAvaliacao() != TipoAvaliacao.INT_RECUPERACAO_FINAL) {
                if (avaliacao.getIdDisciplina() == this.getIdDisciplina()) {

                    for (Nota nota : avaliacao.getListNota()) {

                        if (nota.getIdUsuario() == idUsuario) {

                            if (avaliacao.getIdTipoAvaliacao() == TipoAvaliacao.INT_RECUPERACAO) {
                                notaRecuperacao = Double.parseDouble(nota.getNota());
                            } else if(avaliacao.getIdTipoAvaliacao() == TipoAvaliacao.INT_ADICIONAL_NOTA){
                                notaAdicional = notaAdicional + Double.parseDouble(nota.getNota());
                            }
                            else {
                                countPesoNota = countPesoNota + Double.parseDouble(avaliacao.getPesoNota());
                                somaMediaPonderada = somaMediaPonderada + (Double.parseDouble(nota.getNota()) * Double.parseDouble(avaliacao.getPesoNota()));
                            }
                        }

                    }

                }
            }

        }
        
        if (countPesoNota != 0) {
            
            //Calcula Média Ponderada
            somaMediaPonderada = somaMediaPonderada / countPesoNota;
            
            somaMediaPonderada = Double.parseDouble(MpUtilShared.getDecimalFormatedOneDecimalMultipleFive(somaMediaPonderada));
            notaRecuperacao = Double.parseDouble(MpUtilShared.getDecimalFormatedOneDecimalMultipleFive(notaRecuperacao));
            
          //Adicionar Nota Adicional
            somaMediaPonderada = somaMediaPonderada + notaAdicional;
            
            if (calcularRecuperacao==true) {
                // Media com Recuperação
                if (notaRecuperacao > 0){
                    if (somaMediaPonderada < Double.parseDouble(curso.getMediaNota())) {
                                         
                        //Caso o aluno faça a recuperação e tire uma nota pior que ele já tinha a media dele deve ser mantida
                        if (notaRecuperacao > somaMediaPonderada) {
                            somaMediaPonderada = (somaMediaPonderada + notaRecuperacao) / 2;
                        }
                        

                        //<BEGIN> Gambiarra truncar nota em 6 se a Media + recuperação for maior q 6 e não for p ultimo trimestre
                        // Se for ultimo trimestre mantem o 6
                        //Requisito Integrado
                        boolean isUltimoPeriodo=false;
                        int intRecTrimestre=1;
                        for(int i=0;i< listPeriodo.size();i++){
                            if(this.getIdPeriodo()==listPeriodo.get(i).getIdPeriodo()){
                                if(intRecTrimestre==3){
                                    isUltimoPeriodo=true;
                                }
                            }
                            intRecTrimestre++;
                        }
                        if(isUltimoPeriodo==false){
                            if(somaMediaPonderada>6){
                                somaMediaPonderada=6;
                            }
                        }
                      //<END>                         
                        
                        
                    }
                }
                

            }
            
            
            
            //Se o aluno tira 10 e é adicionado uma nota adicional, a gente fecha com 10.
            if(somaMediaPonderada>10){
                somaMediaPonderada=10;
            }
            
            media = Double.toString(somaMediaPonderada);
        }
        
        return media;
        
    }
    
    
    
    public String getNotaRecuperacaoFinal(int idUsuario){
        String strNotaRecuperacaoFinal="";
        //Pegar Nota Recuperação
        for(int i=0;i<this.getListAvaliacao().size();i++){
            Avaliacao avaliacao = this.getListAvaliacao().get(i);
            if(avaliacao.getIdTipoAvaliacao()==TipoAvaliacao.INT_RECUPERACAO_FINAL){
                for(Nota nota : avaliacao.getListNota()){
                    if(nota.getIdUsuario()==idUsuario){
                        if(nota.getNota()!=null && !nota.getNota().isEmpty()){
                            strNotaRecuperacaoFinal=nota.getNota();
                        }
                    }
                }
            }
        }
        return strNotaRecuperacaoFinal;
    }

    
    public String getNotaRecuperacao(int idUsuario){
        String strNotaRecuperacaoFinal="";
        //Pegar Nota Recuperação
        for(int i=0;i<this.getListAvaliacao().size();i++){
            Avaliacao avaliacao = this.getListAvaliacao().get(i);
            if(avaliacao.getIdTipoAvaliacao()==TipoAvaliacao.INT_RECUPERACAO){
                for(Nota nota : avaliacao.getListNota()){
                    if(nota.getIdUsuario()==idUsuario){
                        if(nota.getNota()!=null && !nota.getNota().isEmpty()){
                            strNotaRecuperacaoFinal=nota.getNota();
                        }
                    }
                }
            }
        }
        return strNotaRecuperacaoFinal;
    }

    
    
    private int getQuantidadeTipoPresenca(int tipoPresenca){
        int quantidadeTipoPresenca=0;
        
        for(int cvAula=0;cvAula<getListAula().size();cvAula++){
            Aula aula = getListAula().get(cvAula);
            
            for(int cvPre=0;cvPre<aula.getArrayPresenca().size();cvPre++){
                Presenca presenca = aula.getArrayPresenca().get(cvPre);
                if(presenca.getIdTipoPresenca()==tipoPresenca){
                    quantidadeTipoPresenca++;
                }
            }           
        }
        return quantidadeTipoPresenca;      
    }
    
    public int getQuantidadePresenca(){  
        return getQuantidadeTipoPresenca(Presenca.PRESENCA);
    }
    
    public int getQuantidadeFalta(){  
        return getQuantidadeTipoPresenca(Presenca.FALTA);
    }
    
    public int getQuantidadeFaltaJustificada(){  
        return getQuantidadeTipoPresenca(Presenca.FALTA_JUSTIFICADA);
    }
    
    public Avaliacao getRecuperacaoFinal(){
        Avaliacao aval = null;
        
        
        for(int i=0;i<getListAvaliacao().size();i++){
            Avaliacao avaliacaoCurrent = getListAvaliacao().get(i);
            if(avaliacaoCurrent.getIdTipoAvaliacao()==TipoAvaliacao.INT_RECUPERACAO_FINAL){
                aval = avaliacaoCurrent;
            }
        }
        
        return aval;
    }

    public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	public String toString(){
		return "projeto:com.jornada.shared.classes.Disciplina";
	}
	


}
