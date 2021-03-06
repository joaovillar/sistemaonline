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

@RemoteServiceRelativePath("GWTServiceHierarquiaCurso")
public interface GWTServiceHierarquiaCurso extends RemoteService {

	public ArrayList<Curso> getHierarquiaCursos();	
	public ArrayList<Curso> getHierarquiaCursos(String strFilter);
	public ArrayList<Curso> getHierarquiaCursosAmbientePais(Usuario usuarioPais);
	public ArrayList<Curso> getHierarquiaCursosAmbienteAluno(Usuario usuarioAluno);
	public ArrayList<Curso> getHierarquiaCursosAmbienteProfessor(Usuario usuarioProfessor, Boolean status);	
	
	
	public static class Util {
		private static GWTServiceHierarquiaCursoAsync instance;
		public static GWTServiceHierarquiaCursoAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServiceHierarquiaCurso.class);
			}
			return instance;
		}
	}
}
