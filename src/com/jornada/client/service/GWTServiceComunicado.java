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
import com.jornada.shared.classes.Comunicado;

@RemoteServiceRelativePath("GWTServiceComunicado")
public interface GWTServiceComunicado extends RemoteService {


	
	public boolean AdicionarComunicado(Comunicado object);
	public boolean AtualizarComunicado(Comunicado object);
	public boolean deleteComunicadoRow(int id_comunicado);
	public ArrayList<Comunicado> getComunicados();
	public ArrayList<Comunicado> getComunicados(String strFilter);
	
	
	public static class Util {
		private static GWTServiceComunicadoAsync instance;
		public static GWTServiceComunicadoAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServiceComunicado.class);
			}
			return instance;
		}
	}
}
