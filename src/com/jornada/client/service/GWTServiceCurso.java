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

@RemoteServiceRelativePath("GWTServiceCurso")
public interface GWTServiceCurso extends RemoteService {
	
	
	public int AdicionarCurso(Curso curso);	
	public boolean AdicionarCursoTemplate(int idCursoTemplate, Integer[] idCursosImportarAluno, Curso curso);	
	public boolean updateCursoRow(Curso curso);	
	public boolean deleteCursoRow(int id_curso);	
	public ArrayList<Curso> getCursos();
	public ArrayList<Curso> getCursosPorPaiAmbientePais(Usuario usuario);	
	public ArrayList<Curso> getCursosPorAlunoAmbienteAluno(Usuario usuario);
	public ArrayList<Curso> getCursosAmbienteProfessor(Usuario usuario);
	public ArrayList<Curso> getCursos(String strFilter);	
	public ArrayList<Usuario> getTodosOsAlunosDoCurso(int id_curso);	
	public boolean associarAlunosAoCurso(int id_curso, ArrayList<Integer> list_id_aluno);
	

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
