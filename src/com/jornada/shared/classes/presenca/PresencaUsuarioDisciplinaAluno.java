package com.jornada.shared.classes.presenca;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class PresencaUsuarioDisciplinaAluno extends PresencaUsuarioDisciplina implements Serializable{

    private static final long serialVersionUID = -1818251646973495514L;
    
    private int idPeriodo;
    private String nomePeriodo;
    private int numeroPresenca;
    private int porcentagemPresencaAula;
    private String situacao;

    public PresencaUsuarioDisciplinaAluno(){
		
	}
    
    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }
    
    public String getNomePeriodo() {
        return nomePeriodo;
    }

    public void setNomePeriodo(String nomePeriodo) {
        this.nomePeriodo = nomePeriodo;
    }
    
    
    public int getNumeroPresenca() {
        return numeroPresenca;
    }

    public void setNumeroPresenca(int numeroPresenca) {
        this.numeroPresenca = numeroPresenca;
    }

    public String getSituacao() {
        return situacao;
    }
    
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    
    

    public int getPorcentagemPresencaAula() {
        return porcentagemPresencaAula;
    }

    public void setPorcentagemPresencaAula(int porcentagemPresencaAula) {
        this.porcentagemPresencaAula = porcentagemPresencaAula;
    }

    @Override
	public String toString() {
		return "PresencaUsuarioAula [idUsuario=" + getUsuario().getIdUsuario() + ", idDisciplina="
				+ getIdDisciplina() + ", idPeriodo="+getIdPeriodo()+"]";
	}
	
    public static final ProvidesKey<PresencaUsuarioDisciplinaAluno> KEY_PROVIDER = new ProvidesKey<PresencaUsuarioDisciplinaAluno>() {
        @Override
        public Object getKey(PresencaUsuarioDisciplinaAluno item) {
            return item == null ? null : 
//                Integer.toString(item.getAula().getIdAula())+
//                Integer.toString(item.getPresenca().getIdPresenca())+
                Integer.toString(item.getUsuario().getIdUsuario());
        }
      };  

}
