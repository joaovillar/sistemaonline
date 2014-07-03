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
import com.jornada.shared.classes.Avaliacao;
import com.jornada.shared.classes.CursoAvaliacao;
import com.jornada.shared.classes.TipoAvaliacao;

public class GWTServiceAvaliacaoImpl extends RemoteServiceServlet implements GWTServiceAvaliacao {
	

	private static final long serialVersionUID = 2704141295428049564L;
	
	
	public boolean AdicionarAvaliacao(Avaliacao object) {		
		return AvaliacaoServer.Adicionar(object);	
	}	
	
	public boolean updateRow(Avaliacao object) {
		return AvaliacaoServer.updateRow(object);
	}	
	
	public boolean deleteRow(int id_avaliacao) {
		return AvaliacaoServer.deleteRow(id_avaliacao);
	}	
	
	public ArrayList<Avaliacao> getAvaliacaoPeloConteudoProgramatico(int idConteudoProgramatico) {		
		return AvaliacaoServer.getAvaliacao(idConteudoProgramatico);
	}	
	
	public ArrayList<CursoAvaliacao> getAvaliacaoPeloCurso(int id_curso, String locale){
		return AvaliacaoServer.getAvaliacaoPeloCurso(id_curso, locale);
	}
	
	public ArrayList<TipoAvaliacao> getTipoAvaliacao(){		
		return AvaliacaoServer.getTipoAvaliacao();		
	}
	
	
}
