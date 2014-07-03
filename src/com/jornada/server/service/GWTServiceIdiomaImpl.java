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
import com.jornada.client.service.GWTServiceIdioma;
import com.jornada.server.classes.IdiomaServer;
import com.jornada.server.classes.UsuarioServer;
import com.jornada.shared.classes.Idioma;

public class GWTServiceIdiomaImpl extends RemoteServiceServlet implements GWTServiceIdioma {

	private static final long serialVersionUID = -5196665601892846873L;
	
	@Override
	public ArrayList<Idioma> getIdiomas() {
		return IdiomaServer.getIdiomas();	
	}	
	
	public boolean atualizarIdiomaUsuario(int idUsuario, String strLocale){
		boolean booAtualizar=false;
		
		int idIdioma = IdiomaServer.getIdIdioma(strLocale);
		
		booAtualizar = UsuarioServer.atualizarIdioma(idUsuario, idIdioma);
		
		return booAtualizar;
	}
}
