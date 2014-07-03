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
import com.jornada.shared.classes.Ocorrencia;
import com.jornada.shared.classes.OcorrenciaAluno;

@RemoteServiceRelativePath("GWTServiceOcorrencia")
public interface GWTServiceOcorrencia extends RemoteService {

	public boolean AdicionarOcorrencia(Ocorrencia object);
	public boolean AtualizarOcorrencia(Ocorrencia object);
	public boolean AtualizarPaiCiente(OcorrenciaAluno object);
	public boolean deleteOcorrenciaRow(int id_ocorrencia);
	public ArrayList<Ocorrencia> getOcorrencias();
	public ArrayList<Ocorrencia> getOcorrencias(String strFilter);	
	public ArrayList<Ocorrencia> getOcorrenciasPeloConteudoProgramatico(int idConteudoProgramatico);
	public ArrayList<OcorrenciaAluno> getOcorrenciasPeloAluno(int idAluno, String locale);
	
	
	public static class Util {
		private static GWTServiceOcorrenciaAsync instance;
		public static GWTServiceOcorrenciaAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServiceOcorrencia.class);
			}
			return instance;
		}
	}



}
