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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServiceAula;
import com.jornada.server.classes.AulaServer;
import com.jornada.server.classes.PresencaServer;
import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.Aula;

public class GWTServiceAulaImpl extends RemoteServiceServlet implements GWTServiceAula {

	private static final long serialVersionUID = -985949196450131576L;
	
	public ArrayList<Aula> getAulas(){
		return AulaServer.getAulas();
	}	
	
	public ArrayList<Aula> getAulas(int idDisciplina){
		return AulaServer.getAulas(idDisciplina);
	}	
	
	public boolean deleteAula(int idAula){		
		return AulaServer.deleteAula(idAula);
	}
	
	
//	public ArrayList<Periodo> getPresencaAluno(int idCurso){
		
//		ArrayList<Periodo> periodos = PeriodoServer.getPeriodos(idCurso);
//		
//		for(int cvPer=0;cvPer<periodos.size();cvPer++){
//			
//			int idPeriodo = periodos.get(cvPer).getIdPeriodo();			
//			ArrayList<Disciplina> disciplinas = DisciplinaServer.getDisciplinasPeloPeriodo(idPeriodo);			
//			periodos.get(cvPer).setListDisciplinas(disciplinas);
//			
//			for(int cvDis=0;cvDis<disciplinas.size();cvDis++){
//				
//				int idDisciplina = disciplinas.get(cvDis).getIdDisciplina();
//				ArrayList<Aula> aulas = AulaServer.getAulas(idDisciplina);				
//				disciplinas.get(cvDis).setListAula(aulas);
//				
//				for(int cvAula=0;cvAula<aulas.size();cvAula++){
//					
//					int idAula = aulas.get(cvAula).getIdAula();
//					ArrayList<Presenca> presencas = PresencaServer.getPresencas(idAula);					
//					aulas.get(cvAula).setArrayPresenca(presencas);
//					
//					for(int cvPre=0;cvPre<presencas.size();cvPre++){
//						int idTipoPresenca = presencas.get(cvPre).getIdTipoPresenca();
//						TipoPresenca tipoPresenca = TipoPresencaServer.getTipoPresenca(idTipoPresenca);
//						presencas.get(cvPre).setTipoPresenca(tipoPresenca);
//					}
//					
//				}
//				
//			}
//			
//		}

//		return periodos;
//	}
	
}
