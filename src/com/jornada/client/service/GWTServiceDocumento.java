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
import com.jornada.shared.classes.Documento;
import com.jornada.shared.classes.RelDocumentoUsuario;
import com.jornada.shared.classes.Usuario;

@RemoteServiceRelativePath("GWTServiceDocumento")
public interface GWTServiceDocumento extends RemoteService {
    
    
    public ArrayList<Documento> getDocumentos();
    public Boolean associarDocumentoUsuarios(int idCurso, int idDocumento, int idTipoUsuario, ArrayList<String> listIdPais, String url);
    public ArrayList<RelDocumentoUsuario> getRelDocumentoUsuariosPorTipo(int idCurso, int idDocumento, int idTipoUsuario);
    public ArrayList<RelDocumentoUsuario> getRelDocumentoUsuariosSemCurso(int idDocumento, int idTipoUsuario);
    public ArrayList<RelDocumentoUsuario> getRelDocumentoUsuariosPorTipoSemCurso(int idDocumento, int idTipoUsuario);
    public String enviarEmailDocumento(int idCurso, int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios, String url);
    public String criarDocumentoParaAlunoTelaUsuario(Usuario usuario, String hostPageBaseURL);
    public boolean associarDocumentoTodosUsuarios(int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios);
    public String enviarEmailDocumentoSemCurso(int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios, String url);
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static GWTServiceDocumentoAsync instance;
		public static GWTServiceDocumentoAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServiceDocumento.class);
			}
			return instance;
		}
	}
}
