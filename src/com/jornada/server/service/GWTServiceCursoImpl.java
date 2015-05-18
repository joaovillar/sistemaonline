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
import com.jornada.client.service.GWTServiceCurso;
import com.jornada.server.classes.CursoServer;
import com.jornada.server.classes.DisciplinaServer;
import com.jornada.server.classes.HierarquiaCursoServer;
import com.jornada.server.classes.PeriodoServer;
import com.jornada.server.classes.UsuarioServer;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimAluno;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimAnual;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimDisciplina;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimNotas;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimPeriodo;

@SuppressWarnings("serial")
public class GWTServiceCursoImpl extends RemoteServiceServlet implements GWTServiceCurso {

	@Override

	public int AdicionarCurso(Curso curso) {		
		return CursoServer.AdicionarCurso(curso);		
	}
	
	
    public String AdicionarCursoString(Curso curso) {
        return CursoServer.AdicionarCursoString(curso);
    }
	
	public boolean AdicionarCursoTemplate(int idCursoEstrutura, Integer[] idCursosImportarAluno, Curso curso) {
		boolean insertedOk = false;
		Curso template = HierarquiaCursoServer.getHierarquiaCurso(idCursoEstrutura);
		template.setNome(curso.getNome());
		template.setDataInicial(curso.getDataInicial());
		template.setDataFinal(curso.getDataFinal());
		insertedOk = CursoServer.AdicionarCursoTemplate(template, idCursosImportarAluno);
		
//		ArrayList<Usuario> listAlunosImportar = new ArrayList<Usuario>();
//		
//		for(int i=0; i<idCursosImportarAluno.length;i++){
//			listAlunosImportar.addAll(UsuarioServer.getUsuariosPorCurso(idCursosImportarAluno[i]));
//		}
//		
//		for(int i=0;i<listAlunosImportar.size();i++){
//			Usuario aluno = listAlunosImportar.get(i);
////			insertedOk = UsuarioServer.AdicionarUsuario(aluno);			
//			insertedOk = CursoServer.associarAlunosAoCurso(id_curso, list_id_aluno)
//		}
		
		
		return insertedOk;
//		return CursoServer.AdicionarCursoTemplate(template);		
	}	
	
	
	public String updateCursoRow(Curso curso){		
		return CursoServer.updateCursoRow(curso);
	}	
	public boolean deleteCursoRow(int id_curso){		
		return CursoServer.deleteCursoRow(id_curso);		
	}		
	public ArrayList<Curso> getCursos() {		
		return CursoServer.getCursos();
	}	
	
	public ArrayList<Curso> getCursosPorPaiAmbientePais(Usuario usuario){			
		switch(usuario.getIdTipoUsuario()){
			case TipoUsuario.ADMINISTRADOR : return CursoServer.getCursos(true);
			case TipoUsuario.COORDENADOR : return CursoServer.getCursos(true);
			case TipoUsuario.PAIS : return CursoServer.getCursosPorPaiAmbientePais(usuario, true);
			default: return null;
		}
	}
	
	public ArrayList<Curso> getCursosPorAlunoAmbienteAluno(Usuario usuario){			
		switch(usuario.getIdTipoUsuario()){
			case TipoUsuario.ADMINISTRADOR : return CursoServer.getCursos(true);
			case TipoUsuario.COORDENADOR : return CursoServer.getCursos(true);
			case TipoUsuario.ALUNO : return CursoServer.getCursosPorAlunoAmbienteAluno(usuario, true);
			default: return null;
		}
	}		
	
	
	public ArrayList<Curso> getCursosAmbienteProfessor(Usuario usuario, Boolean status){			
		switch(usuario.getIdTipoUsuario()){
			case TipoUsuario.ADMINISTRADOR : return CursoServer.getCursos(status);
			case TipoUsuario.COORDENADOR : return CursoServer.getCursos(status);
			case TipoUsuario.PROFESSOR : return CursoServer.getCursosAmbienteProfessor(usuario, status);
			default: return null;
		}
	}	
	
	public ArrayList<Curso> getCursos(String strFilter, Boolean status) {	
	        return CursoServer.getCursos(strFilter, status);
		
	}		

    public ArrayList<Curso> getCursos(Boolean status) {
        return CursoServer.getCursos(status);

    }
    
    public ArrayList<TableMultipleBoletimDisciplina> getCursosPeriodoDisciplina(Boolean status) {
        
        ArrayList<TableMultipleBoletimDisciplina> listTableMulti = new ArrayList<TableMultipleBoletimDisciplina>();
        ArrayList<Curso> listCursos = CursoServer.getCursos(status);
        
        for (Curso curso : listCursos) {
            
            ArrayList<Periodo> listPeriodo = PeriodoServer.getPeriodos(curso.getIdCurso());
            for (Periodo periodo : listPeriodo) {

                ArrayList<Disciplina> listDisciplina = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());
                for (Disciplina disciplina : listDisciplina) {
                    
                    TableMultipleBoletimDisciplina multiTable = new TableMultipleBoletimDisciplina();
                   
                    multiTable.setIdCurso(curso.getIdCurso());
                    multiTable.setNomeCurso(curso.getNome());
                    multiTable.setIdPeriodo(periodo.getIdPeriodo());
                    multiTable.setNomePeriodo(periodo.getNomePeriodo());
                    multiTable.setIdDisciplina(disciplina.getIdDisciplina());
                    multiTable.setNomeDisciplina(disciplina.getNome());
                    listTableMulti.add(multiTable);
                    
                }
            }
            
            
        }
        
        return listTableMulti;

    }
    
    
    public ArrayList<TableMultipleBoletimPeriodo> getCursosPeriodo(Boolean status) {
        
        ArrayList<TableMultipleBoletimPeriodo> listTableMulti = new ArrayList<TableMultipleBoletimPeriodo>();
        ArrayList<Curso> listCursos = CursoServer.getCursos(status);
        
        for (Curso curso : listCursos) {
            
            ArrayList<Periodo> listPeriodo = PeriodoServer.getPeriodos(curso.getIdCurso());
            for (Periodo periodo : listPeriodo) {

                TableMultipleBoletimPeriodo multiTable = new TableMultipleBoletimPeriodo();                
                multiTable.setIdCurso(curso.getIdCurso());
                multiTable.setNomeCurso(curso.getNome());
                multiTable.setIdPeriodo(periodo.getIdPeriodo());
                multiTable.setNomePeriodo(periodo.getNomePeriodo());
                listTableMulti.add(multiTable);
            }
        }        
        return listTableMulti;
    }
    
    public ArrayList<TableMultipleBoletimAnual> getCursosBoletimAnual(Boolean status) {
        
        ArrayList<TableMultipleBoletimAnual> listTableMulti = new ArrayList<TableMultipleBoletimAnual>();
        ArrayList<Curso> listCursos = CursoServer.getCursos(status);
        
        for (Curso curso : listCursos) {
            
            TableMultipleBoletimAnual multiTable = new TableMultipleBoletimAnual();                
            multiTable.setIdCurso(curso.getIdCurso());
            multiTable.setNomeCurso(curso.getNome());
            listTableMulti.add(multiTable);
            
        }        
        return listTableMulti;
    }
    
    public ArrayList<TableMultipleBoletimNotas> getCursosBoletimNotas(Boolean status) {
        
        ArrayList<TableMultipleBoletimNotas> listTableMulti = new ArrayList<TableMultipleBoletimNotas>();
        ArrayList<Curso> listCursos = CursoServer.getCursos(status);
        
        for (Curso curso : listCursos) {
            
            TableMultipleBoletimNotas multiTable = new TableMultipleBoletimNotas();                
            multiTable.setIdCurso(curso.getIdCurso());
            multiTable.setNomeCurso(curso.getNome());
            listTableMulti.add(multiTable);
            
        }        
        return listTableMulti;
    }
    
    public ArrayList<TableMultipleBoletimAluno> getCursosBoletimAlunos(Boolean status) {
        
        ArrayList<TableMultipleBoletimAluno> listTableMulti = new ArrayList<TableMultipleBoletimAluno>();
        ArrayList<Curso> listCursos = CursoServer.getCursos(status);
        
        for (Curso curso : listCursos) {
            
            ArrayList<Usuario> listAluno = UsuarioServer.getAlunosPorCurso(curso.getIdCurso());
            
            for (Usuario aluno : listAluno) {
                
                TableMultipleBoletimAluno multiTable = new TableMultipleBoletimAluno();                
                multiTable.setIdCurso(curso.getIdCurso());
                multiTable.setNomeCurso(curso.getNome());
                multiTable.setIdAluno(aluno.getIdUsuario());
                multiTable.setNomeAluno(aluno.getPrimeiroNome() + " " + aluno.getSobreNome());
                listTableMulti.add(multiTable);                
            }           

            
        }        
        return listTableMulti;
    }
     	
	public ArrayList<Usuario> getTodosOsAlunosDoCurso(int id_curso){		
		return CursoServer.getTodosOsAlunosDoCurso(id_curso);	
	}	
	public boolean associarAlunosAoCurso(int id_curso,ArrayList<Integer> list_id_aluno){		
		return CursoServer.associarAlunosAoCurso(id_curso, list_id_aluno);
	}





}
