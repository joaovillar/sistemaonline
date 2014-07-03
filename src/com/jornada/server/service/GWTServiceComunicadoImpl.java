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
import com.jornada.client.service.GWTServiceComunicado;
import com.jornada.server.classes.ComunicadoServer;
import com.jornada.shared.classes.Comunicado;

public class GWTServiceComunicadoImpl extends RemoteServiceServlet implements GWTServiceComunicado {

	private static final long serialVersionUID = 6710411400591010766L;

	
	public boolean AdicionarComunicado(Comunicado object) {		
		return ComunicadoServer.AdicionarComunicado(object);		
	}
	
	public boolean AtualizarComunicado(Comunicado object) {		
		return ComunicadoServer.AtualizarComunicado(object);		
	}
	
	public boolean deleteComunicadoRow(int id_comunicado){		
		return ComunicadoServer.deleteComunicadoRow(id_comunicado);		
	}	
	
	@Override
	public ArrayList<Comunicado> getComunicados() {
		
		return ComunicadoServer.getComunicados();
	}
	
	@Override
	public ArrayList<Comunicado> getComunicados(String strFilter) {
		
		return ComunicadoServer.getComunicados(strFilter);
	}	
}
