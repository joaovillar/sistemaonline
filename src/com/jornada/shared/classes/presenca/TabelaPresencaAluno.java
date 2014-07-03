package com.jornada.shared.classes.presenca;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class TabelaPresencaAluno implements Serializable{

	public static final String APROVADO="Aprovado";
	public static final String REPROVADO="Reprovado";
	
	private static final long serialVersionUID = -4471323168968291223L;
	
	private String nomePeriodo;
	private String nomeDisciplina;
	private int quantAulas;
	private int quantPresenca;
	private int quantFaltas;
	private int quantJustificadas;
	private int presencaSalaAula;
	private String situacao;
	
	
	public TabelaPresencaAluno(){
		
	}


	public String getNomePeriodo() {
		return nomePeriodo;
	}


	public void setNomePeriodo(String nomePeriodo) {
		this.nomePeriodo = nomePeriodo;
	}


	public String getNomeDisciplina() {
		return nomeDisciplina;
	}


	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}


	public int getQuantAulas() {
		return quantAulas;
	}


	public void setQuantAulas(int quantAulas) {
		this.quantAulas = quantAulas;
	}


	public int getQuantPresenca() {
		return quantPresenca;
	}


	public void setQuantPresenca(int quantPresenca) {
		this.quantPresenca = quantPresenca;
	}


	public int getQuantFaltas() {
		return quantFaltas;
	}


	public void setQuantFaltas(int quantFaltas) {
		this.quantFaltas = quantFaltas;
	}


	public int getQuantJustificadas() {
		return quantJustificadas;
	}


	public void setQuantJustificadas(int quantJustificadas) {
		this.quantJustificadas = quantJustificadas;
	}


	public int getPresencaSalaAula() {
		return presencaSalaAula;
	}


	public void setPresencaSalaAula(int presencaSalaAula) {
		this.presencaSalaAula = presencaSalaAula;
	}


	public String getSituacao() {
		return situacao;
	}


	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}


	@Override
	public String toString() {
		return "TabelaPresencaAluno [nomePeriodo=" + nomePeriodo
				+ ", nomeDisciplina=" + nomeDisciplina + ", quantAulas="
				+ quantAulas + ", quantPresenca=" + quantPresenca
				+ ", quantFaltas=" + quantFaltas + ", quantJustificadas="
				+ quantJustificadas + ", presencaSalaAula=" + presencaSalaAula
				+ ", situacao=" + situacao + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nomeDisciplina == null) ? 0 : nomeDisciplina.hashCode());
		result = prime * result
				+ ((nomePeriodo == null) ? 0 : nomePeriodo.hashCode());
		result = prime * result + presencaSalaAula;
		result = prime * result + quantAulas;
		result = prime * result + quantFaltas;
		result = prime * result + quantJustificadas;
		result = prime * result + quantPresenca;
		result = prime * result
				+ ((situacao == null) ? 0 : situacao.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TabelaPresencaAluno other = (TabelaPresencaAluno) obj;
		if (nomeDisciplina == null) {
			if (other.nomeDisciplina != null)
				return false;
		} else if (!nomeDisciplina.equals(other.nomeDisciplina))
			return false;
		if (nomePeriodo == null) {
			if (other.nomePeriodo != null)
				return false;
		} else if (!nomePeriodo.equals(other.nomePeriodo))
			return false;
		if (presencaSalaAula != other.presencaSalaAula)
			return false;
		if (quantAulas != other.quantAulas)
			return false;
		if (quantFaltas != other.quantFaltas)
			return false;
		if (quantJustificadas != other.quantJustificadas)
			return false;
		if (quantPresenca != other.quantPresenca)
			return false;
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		return true;
	}
	
	
    public static final ProvidesKey<TabelaPresencaAluno> KEY_PROVIDER = new ProvidesKey<TabelaPresencaAluno>() {
        @Override
        public Object getKey(TabelaPresencaAluno item) {
          return item == null ? null : item.toString();
        }
      };  
	
	
	
}
