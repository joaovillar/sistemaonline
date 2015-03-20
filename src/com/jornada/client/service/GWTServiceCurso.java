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
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.boletim.TableMultipleBoletimAnual;
import com.jornada.shared.classes.boletim.TableMultipleBoletimDisciplina;
import com.jornada.shared.classes.boletim.TableMultipleBoletimNotas;
import com.jornada.shared.classes.boletim.TableMultipleBoletimPeriodo;

@RemoteServiceRelativePath("GWTServiceCurso")
public interface GWTServiceCurso extends RemoteService {
	
	
	public int AdicionarCurso(Curso curso);	
	public boolean AdicionarCursoTemplate(int idCursoTemplate, Integer[] idCursosImportarAluno, Curso curso);	
	public String updateCursoRow(Curso curso);	
	public boolean deleteCursoRow(int id_curso);	
	public ArrayList<Curso> getCursos();
	public ArrayList<Curso> getCursos(Boolean status);
	public ArrayList<Curso> getCursosPorPaiAmbientePais(Usuario usuario);	
	public ArrayList<Curso> getCursosPorAlunoAmbienteAluno(Usuario usuario);
	public ArrayList<Curso> getCursosAmbienteProfessor(Usuario usuario, Boolean status);
	public ArrayList<Curso> getCursos(String strFilter, Boolean status);	
	public ArrayList<Usuario> getTodosOsAlunosDoCurso(int id_curso);	
	public boolean associarAlunosAoCurso(int id_curso, ArrayList<Integer> list_id_aluno);
	public String AdicionarCursoString(Curso curso);
	public ArrayList<TableMultipleBoletimDisciplina> getCursosPeriodoDisciplina(Boolean status);
	public ArrayList<TableMultipleBoletimPeriodo> getCursosPeriodo(Boolean status);
	public ArrayList<TableMultipleBoletimAnual> getCursosBoletimAnual(Boolean status);
	public ArrayList<TableMultipleBoletimNotas> getCursosBoletimNotas(Boolean status);
	

	public static class Util {
		private static GWTServiceCursoAsync instance;
		public static GWTServiceCursoAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServiceCurso.class);
			}
			return instance;
		}
	}



}
