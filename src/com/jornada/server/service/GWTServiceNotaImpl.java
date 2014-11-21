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
import java.util.Iterator;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServiceNota;
import com.jornada.server.classes.AvaliacaoServer;
import com.jornada.server.classes.CursoServer;
import com.jornada.server.classes.DisciplinaServer;
import com.jornada.server.classes.NotaServer;
import com.jornada.server.classes.UsuarioServer;
import com.jornada.server.classes.boletim.BoletimDisciplinaPeriodo;
import com.jornada.server.classes.utility.MpUtilServer;
import com.jornada.shared.classes.Avaliacao;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.Nota;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.boletim.TabelaBoletim;

public class GWTServiceNotaImpl extends RemoteServiceServlet implements GWTServiceNota {

    private static final long serialVersionUID = -8992002345363001986L;

    public boolean Adicionar(Nota object) {
        return NotaServer.Adicionar(object);
    }

    public boolean updateRow(Nota object) {
        return NotaServer.updateRow(object);
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

    public ArrayList<ArrayList<String>> getBoletimTrimestre(int idCurso, int idPeriodo) {

        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        ArrayList<Disciplina> listDisciplinas = DisciplinaServer.getDisciplinas(idPeriodo);
        for (Disciplina disciplina : listDisciplinas) {
            disciplina.setListAvaliacao(AvaliacaoServer.getAvaliacao(disciplina.getIdDisciplina()));
            for (Avaliacao avaliacao : disciplina.getListAvaliacao()) {
                avaliacao.setListNota(NotaServer.getNotas(avaliacao.getIdAvaliacao()));
            }
        }

        ArrayList<Usuario> listUsuario = UsuarioServer.getAlunosPorCurso(idCurso);

       
        for (Usuario usuario : listUsuario) {
            ArrayList<String> item = new ArrayList<String>();
            
            item.add(usuario.getPrimeiroNome() + " "+usuario.getSobreNome());
            for (Disciplina disciplina : listDisciplinas) {
                System.out.print("Disciplina:" + disciplina.getNome() + " || Usuario:" + usuario.getPrimeiroNome() + " || ");
                System.out.println("Nota:" + disciplina.getMediaAlunoDisciplina(usuario.getIdUsuario()));
                String strMedia = disciplina.getMediaAlunoDisciplina(usuario.getIdUsuario());
                if(strMedia==null || strMedia.isEmpty()){
                    strMedia = "-";
                    item.add(strMedia);
                }else{
                    double doubleMediaAluno = Double.parseDouble(strMedia);
                    item.add(MpUtilServer.getDecimalFormated(doubleMediaAluno));
                }
               
            }
            list.add(item);
        }
       
        return list;

    }

}
