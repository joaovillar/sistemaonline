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
import com.jornada.client.service.GWTServiceOcorrencia;
import com.jornada.server.classes.OcorrenciaServer;
import com.jornada.shared.classes.Ocorrencia;
import com.jornada.shared.classes.RelUsuarioOcorrencia;
import com.jornada.shared.classes.ocorrencia.OcorrenciaAluno;
import com.jornada.shared.classes.ocorrencia.OcorrenciaParaAprovar;

public class GWTServiceOcorrenciaImpl extends RemoteServiceServlet implements GWTServiceOcorrencia {

	private static final long serialVersionUID = 2791919856614673527L;
	
	public boolean AdicionarOcorrencia(Ocorrencia object) {		
		return OcorrenciaServer.AdicionarOcorrencia(object);		
	}
	
	public boolean AtualizarOcorrencia(Ocorrencia object) {		
		return OcorrenciaServer.AtualizarOcorrencia(object);
	}
	
	public boolean AtualizarPaiCiente(OcorrenciaAluno object) {		
		return OcorrenciaServer.AtualizarPaiCiente(object);
	}	
	
	public boolean AtualizarLiberarPaiLeitura(RelUsuarioOcorrencia object) {		
		return OcorrenciaServer.AtualizarLiberarPaiLeitura(object);
	}		
	
	public boolean deleteOcorrenciaRow(int id_ocorrencia){		
		return OcorrenciaServer.deleteOcorrenciaRow(id_ocorrencia);		
	}	
	
	public boolean deletarRelacionamentoUsuarioOcorrencia(int idOcorrencia, int idUsuario){		
		return OcorrenciaServer.deletarRelacionamentoUsuarioOcorrencia(idOcorrencia, idUsuario);		
	}	
	
	
	@Override
	public ArrayList<Ocorrencia> getOcorrencias() {		
		return OcorrenciaServer.getOcorrencias();
	}
	
	@Override
	public ArrayList<Ocorrencia> getOcorrencias(String strFilter) {		
		return OcorrenciaServer.getOcorrencias(strFilter);
	}	
	
	@Override
	public ArrayList<Ocorrencia> getOcorrenciasPeloConteudoProgramatico(int idConteudoProgramatico) {		
		return OcorrenciaServer.getOcorrenciasPeloConteudoProgramatico(idConteudoProgramatico);
	}	
	
	public ArrayList<OcorrenciaAluno> getOcorrenciasPeloAluno(int idAluno){
		return OcorrenciaServer.getOcorrenciasPeloAluno(idAluno);
	}
	
	public ArrayList<OcorrenciaParaAprovar> getOcorrenciasParaAprovar(Boolean ehParaAprovar) {
		return OcorrenciaServer.getOcorrenciasParaAprovar(ehParaAprovar);
	}
	
	
	public ArrayList<OcorrenciaAluno> getTodasAsOcorrenciasDosAlunos(int idConteudoProgramatico){
		return OcorrenciaServer.getTodasAsOcorrenciasDosAlunos(idConteudoProgramatico);
	}	
	
	
}
