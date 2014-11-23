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
import com.jornada.shared.classes.Nota;

@RemoteServiceRelativePath("GWTServiceNota")
public interface GWTServiceNota extends RemoteService {

	
	public boolean Adicionar(Nota object);
	public boolean updateRow(Nota object);
	public ArrayList<Nota> getNotaPelaAvaliacao(int idCurso, int idAvaliacao);
	public  String[][] getBoletimNotasPorAlunoPorCurso(int idCurso, int idTipoUsuario, int idUsuario);
	public ArrayList<ArrayList<String>> getBoletimPeriodo(int idCurso, int idPeriodo) ;
	public String getExcelBoletimPeriodo(int idCurso, int idPeriodo);
	
	public static class Util {
		private static GWTServiceNotaAsync instance;
		public static GWTServiceNotaAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServiceNota.class);
			}
			return instance;
		}
	}
}
