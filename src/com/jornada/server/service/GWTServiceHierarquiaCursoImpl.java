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

import com.jornada.client.service.GWTServiceHierarquiaCurso;
import com.jornada.server.classes.HierarquiaCursoServer;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GWTServiceHierarquiaCursoImpl extends RemoteServiceServlet implements GWTServiceHierarquiaCurso {

	private static final long serialVersionUID = 4409046912033721940L;
	
	
	public ArrayList<Curso> getHierarquiaCursos() {			
		return HierarquiaCursoServer.getHierarquiaCursos();		
	}
	
	public ArrayList<Curso> getHierarquiaCursosAmbientePais(Usuario usuarioPais) {
		
		switch(usuarioPais.getIdTipoUsuario()){
			case TipoUsuario.ADMINISTRADOR: return HierarquiaCursoServer.getHierarquiaCursos();		
			case TipoUsuario.COORDENADOR: return HierarquiaCursoServer.getHierarquiaCursos();
			case TipoUsuario.PAIS: return HierarquiaCursoServer.getHierarquiaCursosAmbientePais(usuarioPais);		
			default : return null;
		}
		
	}
	
	public ArrayList<Curso> getHierarquiaCursosAmbienteAluno(Usuario usuarioAluno) {
		
		switch (usuarioAluno.getIdTipoUsuario()) {
			case TipoUsuario.ADMINISTRADOR: return HierarquiaCursoServer.getHierarquiaCursos();
			case TipoUsuario.COORDENADOR: return HierarquiaCursoServer.getHierarquiaCursos();
			case TipoUsuario.ALUNO: return HierarquiaCursoServer.getHierarquiaCursosAmbienteAluno(usuarioAluno);
			default: return null;
		}
	}
	
	public ArrayList<Curso> getHierarquiaCursosAmbienteProfessor(Usuario usuarioProfessor) {
		
		switch (usuarioProfessor.getIdTipoUsuario()) {
			case TipoUsuario.ADMINISTRADOR: return HierarquiaCursoServer.getHierarquiaCursos();
			case TipoUsuario.COORDENADOR: return HierarquiaCursoServer.getHierarquiaCursos();
			case TipoUsuario.PROFESSOR: return HierarquiaCursoServer.getHierarquiaCursosAmbienteProfessor(usuarioProfessor);
			default: return null;
		}
	}
		
}
