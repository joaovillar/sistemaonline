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
import com.jornada.shared.classes.Aula;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.TipoPresenca;
import com.jornada.shared.classes.presenca.PresencaUsuarioAula;
import com.jornada.shared.classes.presenca.PresencaUsuarioDisciplina;
import com.jornada.shared.classes.presenca.PresencaUsuarioDisciplinaAluno;

@RemoteServiceRelativePath("GWTServicePresenca")
public interface GWTServicePresenca extends RemoteService {
	
	
	public String AdicionarNovaPresenca(Aula aula);
	public String AdicionarFaltaDisciplina(ArrayList<PresencaUsuarioDisciplina> listPresencaUsuarioDisciplina);
	public boolean updatePresencaRow(int idAula, int idUsuario, int idTipoPresenca);
	public ArrayList<PresencaUsuarioAula> getAlunos(int idCurso);
	public ArrayList<PresencaUsuarioDisciplina> getAlunosDisciplina(int idCurso, int idDisciplina);
	public ArrayList<PresencaUsuarioAula> getAlunos(int idCurso, String strAluno);
	public ArrayList<PresencaUsuarioAula> getListaPresencaAlunos(int idCurso, int idDisciplina);
	public ArrayList<ArrayList<String>> getListaPresencaAlunosArrayList(int idCurso, int idDisciplina);
	public ArrayList<Periodo> getPresencaAluno(int idUsuario, int idCurso);
	public ArrayList<PresencaUsuarioDisciplinaAluno> getPresencaUsuarioDisciplinaAluno(int idUsuario, int idCurso);
	
	public ArrayList<TipoPresenca> getTipoPresencas();
	
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static GWTServicePresencaAsync instance;
		public static GWTServicePresencaAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServicePresenca.class);
			}
			return instance;
		}
	}
}
