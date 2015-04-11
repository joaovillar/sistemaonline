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
package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jornada.shared.classes.Avaliacao;
import com.jornada.shared.classes.CursoAvaliacao;
import com.jornada.shared.classes.TipoAvaliacao;
import com.jornada.shared.classes.boletim.AvaliacaoNota;

@RemoteServiceRelativePath("GWTServiceAvaliacao")
public interface GWTServiceAvaliacao extends RemoteService {


	public String AdicionarAvaliacao(int idCurso, Avaliacao object);	
	public String updateRow(boolean isCampoAssunto, Avaliacao object);
	public String updateRow(int idCurso,  boolean isCampoAssunto, Avaliacao object);
	public boolean deleteRow(int id_avaliacao); 	
	public ArrayList<Avaliacao> getAvaliacaoPelaDisciplina(int idDisciplina);	
	public ArrayList<CursoAvaliacao> getAvaliacaoPeloCurso(int idCurso);
	public ArrayList<TipoAvaliacao> getTipoAvaliacao();
	public ArrayList<AvaliacaoNota> getAvaliacaoNotaPeriodoDisciplinaSemRecuperacaoFinal(int idUsuario, int idCurso, String strNomePeriodo, String strNomeDisciplina);
	public ArrayList<AvaliacaoNota> getAvaliacaoNotaPeriodoDisciplina(int idUsuario, int idCurso, String strNomePeriodo, String strNomeDisciplina, int idAvaliacao);
	public ArrayList<String> getHeaderRelatorioBoletimDisciplina(int idCurso,int idPeriodo,  int idDisciplina);
	public ArrayList<AvaliacaoNota> getAvaliacaoNota(int idUsuario, int idCurso, String strNomeDisciplina);
	public ArrayList<AvaliacaoNota> getAvaliacaoBoletimNota(int idUsuario, int idCurso, String strNomePeriodo, String strNomeDisciplina, String strNomeAval);
	
	public static class Util {
		private static GWTServiceAvaliacaoAsync instance;
		public static GWTServiceAvaliacaoAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServiceAvaliacao.class);
			}
			return instance;
		}
	}
}
