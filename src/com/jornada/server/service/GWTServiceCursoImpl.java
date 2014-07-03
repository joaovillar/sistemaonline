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
import com.jornada.client.service.GWTServiceCurso;
import com.jornada.server.classes.CursoServer;
import com.jornada.server.classes.HierarquiaCursoServer;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;

@SuppressWarnings("serial")
public class GWTServiceCursoImpl extends RemoteServiceServlet implements GWTServiceCurso {

	@Override

	public int AdicionarCurso(Curso curso) {		
		return CursoServer.AdicionarCurso(curso);		
	}
	
	
	public boolean AdicionarCursoTemplate(int idCursoTemplate, Curso curso) {
		Curso template = HierarquiaCursoServer.getHierarquiaCurso(idCursoTemplate);
		template.setNome(curso.getNome());
		template.setDataInicial(curso.getDataInicial());
		template.setDataFinal(curso.getDataFinal());		
		return CursoServer.AdicionarCursoTemplate(template);		
	}	
	
	
	public boolean updateCursoRow(Curso curso){		
		return CursoServer.updateCursoRow(curso);
	}	
	public boolean deleteCursoRow(int id_curso){		
		return CursoServer.deleteCursoRow(id_curso);		
	}		
	public ArrayList<Curso> getCursos() {		
		return CursoServer.getCursos();
	}	
	
	public ArrayList<Curso> getCursosPorPaiAmbientePais(Usuario usuario) {			
		switch(usuario.getIdTipoUsuario()){
			case TipoUsuario.ADMINISTRADOR : return CursoServer.getCursos();
			case TipoUsuario.COORDENADOR : return CursoServer.getCursos();
			case TipoUsuario.PAIS : return CursoServer.getCursosPorPaiAmbientePais(usuario);
			default: return null;
		}
	}
	
	public ArrayList<Curso> getCursosPorAlunoAmbienteAluno(Usuario usuario) {			
		switch(usuario.getIdTipoUsuario()){
			case TipoUsuario.ADMINISTRADOR : return CursoServer.getCursos();
			case TipoUsuario.COORDENADOR : return CursoServer.getCursos();
			case TipoUsuario.ALUNO : return CursoServer.getCursosPorAlunoAmbienteAluno(usuario);
			default: return null;
		}
	}		
	
	
	public ArrayList<Curso> getCursosAmbienteProfessor(Usuario usuario) {			
		switch(usuario.getIdTipoUsuario()){
			case TipoUsuario.ADMINISTRADOR : return CursoServer.getCursos();
			case TipoUsuario.COORDENADOR : return CursoServer.getCursos();
			case TipoUsuario.PROFESSOR : return CursoServer.getCursosAmbienteProfessor(usuario);
			default: return null;
		}
	}	
	
	public ArrayList<Curso> getCursos(String strFilter) {				
		return CursoServer.getCursos(strFilter);
	}		
	public ArrayList<Usuario> getTodosOsAlunosDoCurso(int id_curso){		
		return CursoServer.getTodosOsAlunosDoCurso(id_curso);	
	}	
	public boolean associarAlunosAoCurso(int id_curso,ArrayList<String> list_id_aluno){		
		return CursoServer.associarAlunosAoCurso(id_curso, list_id_aluno);
	}	


}
