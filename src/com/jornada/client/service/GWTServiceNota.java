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
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimAluno;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimAnual;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimDisciplina;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimNotas;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimPeriodo;

@RemoteServiceRelativePath("GWTServiceNota")
public interface GWTServiceNota extends RemoteService {

	
	public boolean Adicionar(Nota object);
	public boolean updateRow(Nota object);
	public ArrayList<Nota> getNotaPelaAvaliacao(int idCurso, int idAvaliacao);
	public  String[][] getBoletimNotasPorAlunoPorCurso(int idCurso, int idTipoUsuario, int idUsuario);
	public ArrayList<ArrayList<String>> getBoletimPeriodo(int idCurso, int idPeriodo) ;
	public String getExcelBoletimPeriodo(int idCurso, int idPeriodo);
	public String getExcelBoletimPeriodo(ArrayList<TableMultipleBoletimPeriodo> listTableMBD);
	public ArrayList<ArrayList<String>> getRelatorioBoletimDisciplina(int idCurso, int idPeriodo, int idDisciplina);
	public String getExcelBoletimDisciplina(int idCurso, int idPeriodo, int idDisciplina);
	public String getExcelBoletimDisciplina(ArrayList<TableMultipleBoletimDisciplina> listTableMBD);
	public ArrayList<ArrayList<String>> getBoletimAnual(int idCurso);
	public ArrayList<ArrayList<String>> getBoletimNotas(int idCurso);
	public String getExcelBoletimNotas(int idCurso);
	public String getExcelBoletimAnual(int idCurso);
	public String getExcelBoletimAnual(ArrayList<TableMultipleBoletimAnual> listTableMBD);
	public ArrayList<ArrayList<String>> getNotasAluno(int idCurso, int idTipoUsuario, int idUsuario);
	public ArrayList<ArrayList<String>> getBoletimAluno(int idCurso, int idAluno);
	public String getExcelBoletimAluno(int idCurso, int idAluno);
	public String getExcelBoletimAluno(ArrayList<TableMultipleBoletimAluno> listTableMBD);
	public String getExcelBoletimNotas(ArrayList<TableMultipleBoletimNotas> listTableMBD);
	public ArrayList<ArrayList<String>> getHistoricoAluno(int idCurso, int idAluno);
	public String getExcelHistoricoAluno(int idCurso, int idAluno);
	public String getMultipleExcelHistoricoAluno(ArrayList<TableMultipleBoletimAluno> listTableMBD);

	
	
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
