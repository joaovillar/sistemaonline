/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jornada.server.service;

import java.util.ArrayList;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServiceAvaliacao;
import com.jornada.server.classes.AvaliacaoServer;
import com.jornada.server.classes.CursoServer;
import com.jornada.server.classes.DisciplinaServer;
import com.jornada.server.classes.PeriodoServer;
import com.jornada.shared.classes.Avaliacao;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.CursoAvaliacao;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.TipoAvaliacao;
import com.jornada.shared.classes.boletim.AvaliacaoNota;

public class GWTServiceAvaliacaoImpl extends RemoteServiceServlet implements GWTServiceAvaliacao {
	

	private static final long serialVersionUID = 2704141295428049564L;
	
	
	public String AdicionarAvaliacao(int idCurso, Avaliacao object) {	
	    
	    String strIsSuccess="";	   
        
        if (jaExisteRecuperacao(object)) {
            strIsSuccess = TipoAvaliacao.EXISTE_RECUPERACAO;
        } else if(jaExisteRecuperacaoFinal(idCurso, object)){
            strIsSuccess = TipoAvaliacao.EXISTE_RECUPERACAO_FINAL;
        }else {
            if(AvaliacaoServer.Adicionar(object)){
                strIsSuccess="true";
            }else{
                strIsSuccess="false";
            }
        }
        
        return strIsSuccess;	    

	}	
	
    public String updateRow(int idCurso, Avaliacao object) {
        String strIsSuccess = "";

        if (jaExisteRecuperacao(object)) {
            strIsSuccess = TipoAvaliacao.EXISTE_RECUPERACAO;
        } else if (jaExisteRecuperacaoFinal(idCurso, object)) {
            strIsSuccess = TipoAvaliacao.EXISTE_RECUPERACAO_FINAL;
        } else {
            if (AvaliacaoServer.updateRow(object)) {
                strIsSuccess = "true";
            } else {
                strIsSuccess = "false";
            }
        }
        return strIsSuccess;
    }
    
    public String updateRow(Avaliacao object) {
        String strIsSuccess = "";

        if (jaExisteRecuperacao(object)) {
            strIsSuccess = TipoAvaliacao.EXISTE_RECUPERACAO;
        } else {
            if (AvaliacaoServer.updateRow(object)) {
                strIsSuccess = "true";
            } else {
                strIsSuccess = "false";
            }
        }
        return strIsSuccess;
    }
	
	
	public boolean jaExisteRecuperacao(Avaliacao object){
	    boolean existeRecuperacao=false;
        if (object.getIdTipoAvaliacao() == TipoAvaliacao.INT_RECUPERACAO) {
            ArrayList<Avaliacao> listAvaliacao = AvaliacaoServer.getAvaliacao(object.getIdDisciplina(), true);
            for (Avaliacao avaliacao : listAvaliacao) {
                if (avaliacao.getIdTipoAvaliacao() == TipoAvaliacao.INT_RECUPERACAO) {
                    existeRecuperacao = true;
                }
            }
        } 
        return existeRecuperacao;
	}
	
	
    public boolean jaExisteRecuperacaoFinal(int idCurso, Avaliacao object) {
        boolean existeRecuperacaoFinal = false;
        
        if (object.getIdTipoAvaliacao() == TipoAvaliacao.INT_RECUPERACAO_FINAL) {
            Curso curso = CursoServer.getCurso(idCurso);

            ArrayList<Periodo> listPeriodo = PeriodoServer.getPeriodos(curso.getIdCurso());

            for (int i = 0; i < listPeriodo.size(); i++) {
                Disciplina disciplina = DisciplinaServer.getDisciplina(object.getIdDisciplina());
                disciplina.setListAvaliacao(AvaliacaoServer.getAvaliacao(object.getIdDisciplina()));
                for (Avaliacao avaliacao : disciplina.getListAvaliacao()) {
                    if (avaliacao.getIdTipoAvaliacao() == TipoAvaliacao.INT_RECUPERACAO_FINAL) {
                        existeRecuperacaoFinal = true;
                    }
                }
            }
        }
        return existeRecuperacaoFinal;
    }
	
	public boolean deleteRow(int id_avaliacao) {
		return AvaliacaoServer.deleteRow(id_avaliacao);
	}	
	
	public ArrayList<Avaliacao> getAvaliacaoPelaDisciplina(int idDisciplina) {		
		return AvaliacaoServer.getAvaliacao(idDisciplina);
	}	
	
	public ArrayList<CursoAvaliacao> getAvaliacaoPeloCurso(int idCurso){
		return AvaliacaoServer.getAvaliacaoPeloCurso(idCurso);
	}
	
	
	public ArrayList<AvaliacaoNota> getAvaliacaoNota(int idUsuario, int idCurso, String strNomeDisciplina){
	    return  AvaliacaoServer.getAvaliacaoNota(idUsuario, idCurso, strNomeDisciplina);
	}
	
    public ArrayList<AvaliacaoNota> getAvaliacaoNotaPeriodoDisciplinaSemRecuperacaoFinal(int idUsuario, int idCurso, String strNomePeriodo, String strNomeDisciplina) {
        return AvaliacaoServer.getAvaliacaoNotaPeriodoDisciplinaSemRecuperacaoFinal(idUsuario, idCurso, strNomePeriodo, strNomeDisciplina);         
    }
    
    public ArrayList<AvaliacaoNota> getAvaliacaoNotaPeriodoDisciplina(int idUsuario, int idCurso, String strNomePeriodo, String strNomeDisciplina, int idAvaliacao) {
        return AvaliacaoServer.getAvaliacaoNota(idUsuario, idCurso, strNomePeriodo, strNomeDisciplina, idAvaliacao); 
        
    }
	
	public ArrayList<TipoAvaliacao> getTipoAvaliacao(){		
		return AvaliacaoServer.getTipoAvaliacao();		
	}
	
	
	public ArrayList<String> getHeaderRelatorioBoletimDisciplina(int idCurso,int idPeriodo,  int idDisciplina){
	    
	   return AvaliacaoServer.getHeaderRelatorioBoletimDisciplina(idCurso,idPeriodo, idDisciplina);
	    
	}
	
}
