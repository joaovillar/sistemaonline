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
import com.jornada.client.service.GWTServicePresenca;
import com.jornada.server.classes.AulaServer;
import com.jornada.server.classes.PresencaServer;
import com.jornada.server.classes.TipoPresencaServer;
import com.jornada.shared.classes.Aula;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.Presenca;
import com.jornada.shared.classes.TipoPresenca;
import com.jornada.shared.classes.presenca.PresencaUsuarioAula;

public class GWTServicePresencaImpl extends RemoteServiceServlet implements GWTServicePresenca {

	private static final long serialVersionUID = 5497060225313836065L;
	
	
	public String AdicionarNovaPresenca(Aula aula) {		
		
		String isAdicionarNovaPresencaOk= "false";
		
		int idAula = AulaServer.AdicionarAula(aula);
		
		if(idAula==0){
			isAdicionarNovaPresencaOk="erro:unique";
		}else{
			ArrayList<Presenca> arrayPresenca = aula.getArrayPresenca();
			
			for(int i=0;i<arrayPresenca.size();i++){
				Presenca presenca = arrayPresenca.get(i);
				presenca.setIdAula(idAula);
			}
			
			 if(PresencaServer.AdicionarPresenca(arrayPresenca)){
				 isAdicionarNovaPresencaOk="OK";
			 }
			 else{
				 isAdicionarNovaPresencaOk="erro:presenca";
			 }
			
		}
			
		
		return isAdicionarNovaPresencaOk;		
	}
	
	public boolean updatePresencaRow(int idAula, int idUsuario, int idTipoPresenca){		
		return PresencaServer.updatePresencaRow(idAula, idUsuario, idTipoPresenca);
	}	
	
	public ArrayList<PresencaUsuarioAula> getAlunos(int idCurso){				
		return PresencaServer.getAlunos(idCurso);		
	}
	
	public ArrayList<PresencaUsuarioAula> getAlunos(int idCurso, String strAluno){				
		return PresencaServer.getAlunos(idCurso, strAluno);		
	}	
		
	public ArrayList<TipoPresenca> getTipoPresencas(){
		return TipoPresencaServer.getTipoPresencas();
	}
	
	public ArrayList<PresencaUsuarioAula> getListaPresencaAlunos(int idCurso, int idDisciplina){
		return PresencaServer.getListaPresencaAlunos(idCurso, idDisciplina);
    }

	public ArrayList<ArrayList<String>> getListaPresencaAlunosArrayList(int idCurso, int idDisciplina){
		return PresencaServer.getListaPresencaAlunosArrayList(idCurso, idDisciplina);
    }

	public ArrayList<Periodo> getPresencaAluno(int idUsuario, int idCurso){
		return PresencaServer.getPresencaAluno(idUsuario, idCurso); 
	}
}
