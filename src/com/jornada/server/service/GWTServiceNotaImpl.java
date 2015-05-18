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

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServiceNota;
import com.jornada.server.classes.CursoServer;
import com.jornada.server.classes.NotaServer;
import com.jornada.server.classes.boletim.BoletimDisciplinaPeriodo;
import com.jornada.server.framework.excel.ExcelFramework;
import com.jornada.shared.classes.Nota;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.relatorio.boletim.TabelaBoletim;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimAluno;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimAnual;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimDisciplina;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimNotas;
import com.jornada.shared.classes.relatorio.boletim.TableMultipleBoletimPeriodo;

public class GWTServiceNotaImpl extends RemoteServiceServlet implements GWTServiceNota {

    private static final long serialVersionUID = -8992002345363001986L;

    public boolean Adicionar(Nota object) {
        return NotaServer.Adicionar(object);
    }

    public boolean updateRow(Nota object) {
        
        if(object.getNota()==null || object.getNota().isEmpty()){
            return NotaServer.deleteNotaVaziaAluno(object);            
        }else {
            return NotaServer.updateRow(object);    
        }
        
    }

    public ArrayList<Nota> getNotaPelaAvaliacao(int idCurso, int idAvaliacao) {

        ArrayList<Nota> alunosENotas = new ArrayList<Nota>();
        ArrayList<Usuario> alunosListadosNoCurso = CursoServer.getTodosOsAlunosDoCurso(idCurso);
        ArrayList<Nota> notasCadastradas = NotaServer.getNotas(idAvaliacao);

        for (int intUsuarios = 0; intUsuarios < alunosListadosNoCurso.size(); intUsuarios++) {
            Usuario usuario = alunosListadosNoCurso.get(intUsuarios);
            Nota notaAluno = new Nota();
            boolean achouUser = false;

            for (int intNotas = 0; intNotas < notasCadastradas.size(); intNotas++) {
                Nota nota = notasCadastradas.get(intNotas);
                if (usuario.getIdUsuario() == nota.getIdUsuario()) {
                    nota.setNomeUsuario(usuario.getPrimeiroNome() + " " + usuario.getSobreNome());
                    notaAluno = nota;
                    achouUser = true;
                    break;
                }
            }

            if (achouUser == false) {
                notaAluno = new Nota();
                notaAluno.setIdAvaliacao(idAvaliacao);
                notaAluno.setIdUsuario(usuario.getIdUsuario());
                notaAluno.setNomeUsuario(usuario.getPrimeiroNome() + " " + usuario.getSobreNome());
                notaAluno.setNota("");
            }

            alunosENotas.add(notaAluno);
        }

        return alunosENotas;
    }

    public String[][] getBoletimNotasPorAlunoPorCurso(int idCurso, int idTipoUsuario, int idUsuario) {

        ArrayList<TabelaBoletim> listTabelaBoletim = NotaServer.getBoletimNotasPorAlunoPorCurso(idCurso, idTipoUsuario, idUsuario);

        String[][] boletim = BoletimDisciplinaPeriodo.getBoletim(listTabelaBoletim);

        return boletim;
    }
    

    public ArrayList<ArrayList<String>> getNotasAluno(int idCurso, int idTipoUsuario, int idUsuario) {

        return NotaServer.getNotasAluno(idCurso, idUsuario);
       
    }

    public ArrayList<ArrayList<String>> getBoletimPeriodo(int idCurso, int idPeriodo) {
       return NotaServer.getBoletimPeriodo(idCurso, idPeriodo);
    }

    public ArrayList<ArrayList<String>> getBoletimAnual(int idCurso){
       return NotaServer.getBoletimAnual(idCurso);
    }
    
    public ArrayList<ArrayList<String>> getBoletimNotas(int idCurso){
        return NotaServer.getBoletimNotas(idCurso);
     }
    
    public ArrayList<ArrayList<String>> getBoletimAluno(int idCurso, int idAluno){
        return NotaServer.getBoletimAluno(idCurso, idAluno);
     }
    
    public ArrayList<ArrayList<String>> getHistoricoAluno(int idCurso, int idAluno){
        return NotaServer.getHistoricoAluno(idCurso, idAluno);
     }
    
    public ArrayList<ArrayList<String>> getRelatorioBoletimDisciplina(int idCurso, int idPeriodo, int idDisciplina) {
        return NotaServer.getRelatorioBoletimDisciplina(idCurso, idPeriodo, idDisciplina);
     }
    
    public String getExcelBoletimNotas(int idCurso){
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Boletim Notas");
        NotaServer.getExcelBoletimNotas(wb, sheet, idCurso);
        return ExcelFramework.getExcelAddress(wb, "GerarExcelBoletimNotas_");
     }
    
    public String getExcelBoletimNotas(ArrayList<TableMultipleBoletimNotas> listTableMBD){

        
        XSSFWorkbook wb = new XSSFWorkbook();
        for (int i = 0; i < listTableMBD.size(); i++) {
            TableMultipleBoletimNotas tableMBD = listTableMBD.get(i);
            String strTab = Integer.toString(i+1)+") ";
            strTab += tableMBD.getNomeCurso().substring(0, 3);
            XSSFSheet sheet = wb.createSheet(strTab);
            NotaServer.getExcelBoletimNotas(wb, sheet, tableMBD.getIdCurso());
        }
        return ExcelFramework.getExcelAddress(wb, "GerarExcelBoletimNotas_");
        
     }
    
    public String getExcelBoletimPeriodo(int idCurso, int idPeriodo) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Boletim Periodo");
        NotaServer.gerarExcelBoletimPeriodo(wb, sheet, idCurso, idPeriodo);
        return ExcelFramework.getExcelAddress(wb, "GerarExcelBoletimPeriodo_");
    }
    
    public String getExcelBoletimPeriodo(ArrayList<TableMultipleBoletimPeriodo> listTableMBD) {

        XSSFWorkbook wb = new XSSFWorkbook();
        for (int i = 0; i < listTableMBD.size(); i++) {
            TableMultipleBoletimPeriodo tableMBD = listTableMBD.get(i);
            
            String strTab = Integer.toString(i+1)+") ";
            strTab += tableMBD.getNomeCurso().substring(0, 3) + "-";
            strTab += tableMBD.getNomePeriodo().substring(0, 3);
            XSSFSheet sheet = wb.createSheet(strTab);
            NotaServer.gerarExcelBoletimPeriodo(wb, sheet, tableMBD.getIdCurso(), tableMBD.getIdPeriodo());
        }
        return ExcelFramework.getExcelAddress(wb, "GerarExcelBoletimPeriodo_");
    }
    
    public String getExcelBoletimAluno(int idCurso, int idAluno) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Boletim Anual Aluno");
        NotaServer.getExcelBoletimAluno(wb, sheet, idCurso, idAluno);
        return ExcelFramework.getExcelAddress(wb, "GerarExcelBoletimAnualAluno_");
    }
    
    public String getExcelBoletimAluno(ArrayList<TableMultipleBoletimAluno> listTableMBD) {        
        XSSFWorkbook wb = new XSSFWorkbook();
        for (int i = 0; i < listTableMBD.size(); i++) {
            TableMultipleBoletimAluno tableMBD = listTableMBD.get(i);
            String strTab = Integer.toString(i+1)+") ";
            strTab += tableMBD.getNomeCurso().substring(0,3)+"-";
            strTab += tableMBD.getNomeAluno().substring(0,3);
            XSSFSheet sheet = wb.createSheet(strTab);
            NotaServer.getExcelBoletimAluno(wb, sheet, tableMBD.getIdCurso(), tableMBD.getIdAluno());
        }
        return ExcelFramework.getExcelAddress(wb, "GerarExcelBoletimAnualAluno_");        
    }
    
    public String getExcelBoletimAnual(int idCurso){
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Boletim Anual");
        NotaServer.getExcelBoletimAnual(wb, sheet, idCurso);
        return ExcelFramework.getExcelAddress(wb, "GerarExcelBoletimAnual_");
    }
    
    public String getExcelHistoricoAluno(int idCurso, int idAluno){
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Historico");
        NotaServer.getExcelHistoricoAluno(wb, sheet, idCurso, idAluno);
        return ExcelFramework.getExcelAddress(wb, "GerarExcelHistoricoAluno_");
    }
    
    public String getExcelBoletimAnual(ArrayList<TableMultipleBoletimAnual> listTableMBD){
        
        XSSFWorkbook wb = new XSSFWorkbook();
        for (int i = 0; i < listTableMBD.size(); i++) {
            TableMultipleBoletimAnual tableMBD = listTableMBD.get(i);
            String strTab = Integer.toString(i+1)+") ";
            strTab += tableMBD.getNomeCurso().substring(0, 3);
            XSSFSheet sheet = wb.createSheet(strTab);
            NotaServer.getExcelBoletimAnual(wb, sheet, tableMBD.getIdCurso());
        }
        return ExcelFramework.getExcelAddress(wb, "GerarExcelBoletimAnual_");
    }

    public String getExcelBoletimDisciplina(int idCurso, int idPeriodo, int idDisciplina) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Boletim Disciplina");
        NotaServer.gerarExcelBoletimDisciplina(wb, sheet, idCurso, idPeriodo, idDisciplina);
        return ExcelFramework.getExcelAddress(wb, "GerarExcelBoletimDisciplina_");
    }
    
    public String getExcelBoletimDisciplina(ArrayList<TableMultipleBoletimDisciplina> listTableMBD) {
        XSSFWorkbook wb = new XSSFWorkbook();
        for (int i=0;i<listTableMBD.size();i++) {
            TableMultipleBoletimDisciplina tableMBD = listTableMBD.get(i);
            String strTab = Integer.toString(i+1)+") ";
            strTab += tableMBD.getNomeCurso().substring(0,3)+"-";
            strTab += tableMBD.getNomePeriodo().substring(0,3)+"-";
            strTab += tableMBD.getNomeDisciplina().substring(0,3);
            XSSFSheet sheet = wb.createSheet(strTab);
            NotaServer.gerarExcelBoletimDisciplina(wb, sheet, tableMBD.getIdCurso(), tableMBD.getIdPeriodo(), tableMBD.getIdDisciplina());
        }

        return ExcelFramework.getExcelAddress(wb, "GerarExcelBoletimDisciplina_");
    }
}
