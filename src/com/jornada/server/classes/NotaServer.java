package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jornada.ConfigJornada;
import com.jornada.server.classes.utility.MpUtilServer;
import com.jornada.server.database.ConnectionManager;
import com.jornada.server.framework.excel.ExcelFramework;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Avaliacao;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.Nota;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.TipoAvaliacao;
import com.jornada.shared.classes.TipoStatusUsuario;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.curso.Ano;
import com.jornada.shared.classes.curso.AnoItem;
import com.jornada.shared.classes.curso.Ensino;
import com.jornada.shared.classes.presenca.PresencaUsuarioDisciplina;
import com.jornada.shared.classes.relatorio.boletim.TabelaBoletim;
//import com.jornada.shared.utility.MpUtilShared;

public class NotaServer {

    public static final String DB_INSERT = "INSERT INTO nota (id_avaliacao, id_usuario, nota) VALUES (?,?,?);";
    public static final String DB_DELETE_CAMPO_VAZIO = "DELETE FROM nota where id_avaliacao = ? and id_usuario = ?;";
    public static final String DB_UPDATE = "UPDATE nota set nota=? where id_avaliacao=? and id_usuario=?;";
    public static final String DB_SELECT_NOTA_PELA_AVALIACAO = "SELECT * FROM nota where id_avaliacao=?;";
    public static final String DB_SELECT_BOLETIM_NOTA_ALUNO_POR_CURSO = "		select d.*, a.*, n,* from " + "		( " + "			select " + "				c.id_curso, c.nome_curso, " + "				p.id_periodo, p.nome_periodo, " + "				d.id_disciplina, d.nome_disciplina " + "			from usuario u " + "			inner join rel_curso_usuario rcu on u.id_usuario=rcu.id_usuario " + "			inner join curso c on c.id_curso = rcu.id_curso " + "			inner join periodo p on c.id_curso = p.id_curso " + "			inner join disciplina d on p.id_periodo = d.id_periodo " + "			where  " + "			u.id_tipo_usuario=? and  rcu.id_usuario=? and c.id_curso=? " + "		) d " +
    // "		left join ( select * from conteudo_programatico ) cp on d.id_disciplina=cp.id_disciplina "+
    "		left join ( select * from avaliacao ) a on d.id_disciplina=a.id_disciplina  " + "		left join ( select * from nota where id_usuario=? ) n on a.id_avaliacao=n.id_avaliacao ";

    public static Boolean Adicionar(Nota object) {

        Boolean isOperationDone = false;

        // JornadaDataBase dataBase = new JornadaDataBase();
        Connection conn = ConnectionManager.getConnection();
        try {
            // dataBase.createConnection();
            // Connection conn = dataBase.getConnection();
            int count = 0;
            PreparedStatement ps = conn.prepareStatement(DB_INSERT);
            ps.setInt(++count, object.getIdAvaliacao());
            ps.setInt(++count, object.getIdUsuario());
            ps.setString(++count, object.getNota());

            int numberUpdate = ps.executeUpdate();

            if (numberUpdate == 1) {
                isOperationDone = true;
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            // dataBase.close();
            ConnectionManager.closeConnection(conn);
        }

        return isOperationDone;
    }
    
    public static boolean deleteNotaVaziaAluno(Nota object){

        Boolean isOperationDone = false;

        // JornadaDataBase dataBase = new JornadaDataBase();
        Connection conn = ConnectionManager.getConnection();
        try {
            // dataBase.createConnection();
            // Connection conn = dataBase.getConnection();
            int count = 0;
            PreparedStatement ps = conn.prepareStatement(DB_DELETE_CAMPO_VAZIO);
            ps.setInt(++count, object.getIdAvaliacao());
            ps.setInt(++count, object.getIdUsuario());

            ps.executeUpdate();

            isOperationDone = true;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            // dataBase.close();
            ConnectionManager.closeConnection(conn);
        }

        return isOperationDone;
        
        
    }

    public static boolean updateRow(Nota object) {
        boolean success = false;

        // JornadaDataBase dataBase = new JornadaDataBase();
        Connection conn = ConnectionManager.getConnection();
        try {
            // dataBase.createConnection();
            // Connection connection = dataBase.getConnection();

            int count = 0;
            PreparedStatement psUpdate = conn.prepareStatement(DB_UPDATE);
            // psUpdate.setInt(++count, object.getIdAvaliacao());
            // psUpdate.setInt(++count, object.getIdUsuario());
            psUpdate.setString(++count, object.getNota());
            psUpdate.setInt(++count, object.getIdAvaliacao());
            psUpdate.setInt(++count, object.getIdUsuario());

            int numberUpdate = psUpdate.executeUpdate();

            if (numberUpdate == 1) {
                success = true;
            } else {
                count = 0;
                PreparedStatement psInsert = conn.prepareStatement(DB_INSERT);
                psInsert.setInt(++count, object.getIdAvaliacao());
                psInsert.setInt(++count, object.getIdUsuario());
                psInsert.setString(++count, object.getNota());
                numberUpdate = psInsert.executeUpdate();
                if (numberUpdate == 1) {
                    success = true;
                }
            }

        } catch (SQLException sqlex) {
            success = false;
            System.err.println(sqlex.getMessage());
        } finally {
            // dataBase.close();
            ConnectionManager.closeConnection(conn);
        }

        return success;
    }

    public static ArrayList<Nota> getNotas(int idAvaliacao) {

        ArrayList<Nota> data = new ArrayList<Nota>();
        // JornadaDataBase dataBase = new JornadaDataBase();
        Connection conn = ConnectionManager.getConnection();

        try {

            // dataBase.createConnection();
            // Connection connection = dataBase.getConnection();

            PreparedStatement ps = conn.prepareStatement(DB_SELECT_NOTA_PELA_AVALIACAO);

            int count = 0;
            ps.setInt(++count, idAvaliacao);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Nota current = new Nota();

                current.setIdNota(rs.getInt("id_nota"));
                current.setIdAvaliacao(rs.getInt("id_avaliacao"));
                current.setIdUsuario(rs.getInt("id_usuario"));
                current.setNota(rs.getString("nota"));

                data.add(current);
            }

        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            // dataBase.close();
            ConnectionManager.closeConnection(conn);
        }

        return data;

    }

    public static ArrayList<TabelaBoletim> getBoletimNotasPorAlunoPorCurso(int idCurso, int idTipoUsuario, int idUsuario) {

        ArrayList<TabelaBoletim> data = new ArrayList<TabelaBoletim>();

        // JornadaDataBase dataBase = new JornadaDataBase();
        Connection conn = ConnectionManager.getConnection();
        try {

            // dataBase.createConnection();
            // Connection connection = dataBase.getConnection();

            PreparedStatement ps = conn.prepareStatement(DB_SELECT_BOLETIM_NOTA_ALUNO_POR_CURSO);

            int count = 0;
            ps.setInt(++count, idTipoUsuario);
            ps.setInt(++count, idUsuario);
            ps.setInt(++count, idCurso);
            ps.setInt(++count, idUsuario);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                TabelaBoletim current = new TabelaBoletim();

                current.setNomeCurso(rs.getString("nome_curso"));
                current.setNomePeriodo(rs.getString("nome_periodo"));
                current.setIdDisciplina(rs.getInt("id_disciplina"));
                current.setNomeDisciplina(rs.getString("nome_disciplina"));
                // current.setNomeConteudoProgramatico(rs.getString("descricao"));
                current.setNomeAvaliacao(rs.getString("assunto"));
                current.setNota(rs.getString("nota"));

                data.add(current);
            }

        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            // dataBase.close();
            ConnectionManager.closeConnection(conn);
        }

        return data;
    }

    public static ArrayList<ArrayList<String>> getBoletimPeriodo(int idCurso, int idPeriodo) {

        ArrayList<Disciplina> listDisciplinas = DisciplinaServer.getDisciplinasComAvaliacoes(idPeriodo);

        ArrayList<Usuario> listUsuario = UsuarioServer.getAlunosPorCurso(idCurso);

        return getMediaNotaAlunosNasDisciplinas(idCurso, listUsuario, listDisciplinas);
    }

    public static ArrayList<ArrayList<String>> getNotasAluno(int idCurso, int idUsuario) {

        Curso curso = CursoServer.getCurso(idCurso);
        ArrayList<ArrayList<String>> listNotas = new ArrayList<ArrayList<String>>();
        ArrayList<Periodo> listPeriodo = PeriodoServer.getPeriodos(idCurso);
//        String strNotaRecuperacaoFinal = "";

        // Adicionando o Header
        ArrayList<String> itemPeriodo = new ArrayList<String>();
        HashSet<String> hashSetDisciplinas = new HashSet<String>();

        itemPeriodo.add("Disciplinas");
        // Pegando nome dos Periodos e de todas as disciplinas do ano
        for (Periodo periodo : listPeriodo) {
            itemPeriodo.add(periodo.getNomePeriodo());
            ArrayList<Disciplina> listDisciplina = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());
            for (Disciplina disciplina : listDisciplina) {
                hashSetDisciplinas.add(disciplina.getNome());
            }
        }
        itemPeriodo.add(Nota.STR_MEDIA_FINAL);
        listNotas.add(itemPeriodo);

        ArrayList<String> listNomeDisciplinas = new ArrayList<String>(hashSetDisciplinas);
        Collections.sort(listNomeDisciplinas);

        for (int i = 0; i < listNomeDisciplinas.size(); i++) {
            ArrayList<String> array = new ArrayList<String>();
            String nomeDisciplina = listNomeDisciplinas.get(i);

            array.add(nomeDisciplina);

//            int numberPesoPeriodos = 0;
//            double doubleSomaMedia = 0;
//            int intRecPeriodo=1;
            for (Periodo periodo : listPeriodo) {
                String notaDisciplinaPeriodo = "-";
                ArrayList<Disciplina> listDisciplina = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());
                for (Disciplina disciplina : listDisciplina) {

                    if (disciplina.getNome().equals(nomeDisciplina)) {
                        disciplina.setListAvaliacao(AvaliacaoServer.getAvaliacaoComNotas(disciplina.getIdDisciplina()));
                        
                        
                        notaDisciplinaPeriodo = disciplina.getMediaAlunoDisciplina(curso, listPeriodo, idUsuario, true);

                    }

                    // Pegar Nota Recuperação
//                    for (Avaliacao avaliacao : disciplina.getListAvaliacao()) {
//                        if (avaliacao.getIdTipoAvaliacao() == TipoAvaliacao.INT_RECUPERACAO_FINAL) {
//                            for (Nota nota : avaliacao.getListNota()) {
//                                if (nota.getIdUsuario() == idUsuario) {
//                                    if (nota.getNota() != null && !nota.getNota().isEmpty()) {
//                                        strNotaRecuperacaoFinal = nota.getNota();
//                                    }
//                                }
//                            }
//                        }
//                    }

                }
                if (notaDisciplinaPeriodo.isEmpty() || notaDisciplinaPeriodo.equals("-")) {
                    notaDisciplinaPeriodo = "-";
                } else {
                    notaDisciplinaPeriodo = MpUtilServer.getDecimalFormatedOneDecimalMultipleFive(Double.parseDouble(notaDisciplinaPeriodo));
                }

//                if (!notaDisciplinaPeriodo.equals("-")) {
//                    int peso = Integer.parseInt(periodo.getPeso());
//                    for (int countPesos = 0; countPesos < peso; countPesos++) {
//                        numberPesoPeriodos++;
////                        doubleSomaMedia = doubleSomaMedia + Double.parseDouble(notaDisciplinaPeriodo);
//                    }
//                }

                array.add(notaDisciplinaPeriodo);
//                intRecPeriodo++;
            }
            
            String strMediaAnualDisciplina = getMediaAnualDisciplina(idUsuario, idCurso, listPeriodo, nomeDisciplina);
            if (strMediaAnualDisciplina == null || strMediaAnualDisciplina.isEmpty()) {
                array.add("-");
            } else{
                double doubleMediaAluno = Double.parseDouble(strMediaAnualDisciplina);
//                array.add(MpUtilServer.getDecimalFormatedOneDecimal(doubleMediaAluno));
//                if(recuperacaoFinal!=null){
//                    row.add(recuperacaoFinal.getNotaRecuperacaoFinal());
//                }else{
//                    row.add("-");
//                }
               
                array.add(MpUtilServer.getDecimalFormatedOneDecimalMultipleFive(doubleMediaAluno));
            }
//            else if (numberPesoPeriodos > 0) {
//                doubleSomaMedia = Double.parseDouble(strMediaAnualDisciplina);
//                double doubleMediaFinal = doubleSomaMedia / numberPesoPeriodos;
//
//                if (strNotaRecuperacaoFinal.isEmpty()) {
//                    strNotaRecuperacaoFinal = Double.toString(doubleMediaFinal);
//                }
//                Double doubleNotaRecuperacaoFinal = Double.parseDouble(strNotaRecuperacaoFinal);
//                doubleMediaFinal = (doubleMediaFinal + doubleNotaRecuperacaoFinal) / 2;
//
//                array.add(MpUtilServer.getDecimalFormatedOneDecimal(doubleMediaFinal));
////                 array.add("::::");
//            } else {
//                array.add("=");
//            }

            listNotas.add(array);

        }

        return listNotas;
    }

    
    //Andre
    public static ArrayList<ArrayList<String>> getBoletimAnual(int idCurso) {
        Curso curso = CursoServer.getCurso(idCurso);
        ArrayList<ArrayList<String>> listNotas = new ArrayList<ArrayList<String>>();
        ArrayList<Periodo> listPeriodo = PeriodoServer.getPeriodos(idCurso);
        ArrayList<Usuario> listUsuario = UsuarioServer.getAlunosPorCurso(idCurso);

        HashSet<String> hashSetDisciplinas = new HashSet<String>();
        // Pegando nome das disciplinas de todo o ano letivo
        for (Periodo periodo : listPeriodo) {
            ArrayList<Disciplina> listDisciplina = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());
            for (Disciplina disciplina : listDisciplina) {
                hashSetDisciplinas.add(disciplina.getNome());
            }
        }

        ArrayList<String> listDisciplinasAno = new ArrayList<String>(hashSetDisciplinas);
        Collections.sort(listDisciplinasAno);

        // Adicionando Disciplinas
        ArrayList<String> listDisciplinasAnoAluno = new ArrayList<String>();
        listDisciplinasAnoAluno.add("IdUsuario");
        listDisciplinasAnoAluno.add("Alunos");
        for (String string : listDisciplinasAno) {
            listDisciplinasAnoAluno.add(string);
        }
        listDisciplinasAnoAluno.add("RESULTADO FINAL");

        // Adicionando Nome Aluno
        listNotas.add(listDisciplinasAnoAluno);
        for (Usuario usuario : listUsuario) {
            ArrayList<String> array = new ArrayList<String>();
            array.add(Integer.toString(usuario.getIdUsuario()));

            if (usuario.getIdTipoStatusUsuario() == TipoStatusUsuario.ALUNO_TRANSFERIDO) {
                array.add(usuario.getPrimeiroNome() + " " + usuario.getSobreNome() + " - " + usuario.getTipoStatusUsuario().getNomeTipoStatusUsuario());
            } else {
                array.add(usuario.getPrimeiroNome() + " " + usuario.getSobreNome());
            }

            for (int i = 2; i < listDisciplinasAnoAluno.size() - 1; i++) {
                String nomeDisciplina = listDisciplinasAnoAluno.get(i);
                String strMediaAnualDisciplina = getMediaAnualDisciplina(usuario.getIdUsuario(), idCurso, listPeriodo, nomeDisciplina);
                if (strMediaAnualDisciplina.isEmpty())
                    strMediaAnualDisciplina = "-";
                if (usuario.getIdTipoStatusUsuario() == TipoStatusUsuario.ALUNO_TRANSFERIDO)
                    strMediaAnualDisciplina = "-";
                array.add(strMediaAnualDisciplina);
            }

            listNotas.add(array);
        }

        for (int line = 0; line < listNotas.size(); line++) {
            if (line > 0) {
                boolean aprovado = true;
                ArrayList<String> row = listNotas.get(line);

                int idAluno = Integer.parseInt(row.get(0));
                Usuario aluno = UsuarioServer.getUsuarioPeloId(idAluno);

                for (int column = 0; column < row.size(); column++) {
                    if (column > 1) {
                        try {
                            double nota = Double.parseDouble(row.get(column));
                            double mediaCurso = Double.parseDouble(curso.getMediaNota());
                            if (nota < mediaCurso) {
                                aprovado = false;
                                break;
                            }
                        } catch (Exception ex) {
                        }
                    }
                }

                if (aluno.getIdTipoStatusUsuario() == TipoStatusUsuario.ALUNO_TRANSFERIDO) {
                    row.add(aluno.getTipoStatusUsuario().getNomeTipoStatusUsuario());
                } else {
                    if (aprovado == true) {
                        row.add("Aprovado");
                    } else {
                        row.add("Reprovado");
                    }
                }
            }
        }

//        System.out.println(listNotas.toString());
        return listNotas;
    }

    public static ArrayList<ArrayList<String>> getBoletimNotas(int idCurso) {

        ArrayList<ArrayList<String>> listNotas = new ArrayList<ArrayList<String>>();

        Curso curso = CursoServer.getCurso(idCurso);
        ArrayList<Periodo> listPeriodo = PeriodoServer.getPeriodos(curso.getIdCurso());
        ArrayList<Usuario> listUsuario = UsuarioServer.getAlunosPorCurso(idCurso);

        HashSet<String> hashSetDisciplinas = new HashSet<String>();
        // Pegando nome das disciplinas de todo o ano letivo
        for (Periodo periodo : listPeriodo) {
            ArrayList<Disciplina> listDisciplina = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());
            for (Disciplina disciplina : listDisciplina) {
                hashSetDisciplinas.add(disciplina.getNome());
            }
        }

        ArrayList<String> listDisciplinasAno = new ArrayList<String>(hashSetDisciplinas);
        Collections.sort(listDisciplinasAno);

        // Nome das disciplinas do curso e adicionando campos extras
        ArrayList<String> listDisciplinasAnoAluno = new ArrayList<String>();
        listDisciplinasAnoAluno.add("IdUsuario");
        listDisciplinasAnoAluno.add("Alunos");
        listDisciplinasAnoAluno.add("Etapas");
        for (String string : listDisciplinasAno) {
            listDisciplinasAnoAluno.add(string);
        }

        listNotas.add(listDisciplinasAnoAluno);

        for (Usuario usuario : listUsuario) {
            for (Periodo periodo : listPeriodo) {

                // Pegando as avaliações por periodo
                ArrayList<Disciplina> listDisciplinas = DisciplinaServer.getDisciplinasComAvaliacoes(periodo.getIdPeriodo());

                HashSet<String> hashSetAval = new HashSet<String>();
                // ArrayList<String> listNomeAvaliacao = new
                // ArrayList<String>();
                for (Disciplina disciplina : listDisciplinas) {
                    for (Avaliacao aval : disciplina.getListAvaliacao()) {
                        // listNomeAvaliacao.add(aval.getIdAvaliacao()+":"+aval.getTipoAvaliacao().getNomeTipoAvaliacao());
                        // listNomeAvaliacao.add(aval.getIdAvaliacao()+":"+aval.getAssunto());
                        if (aval.getIdTipoAvaliacao() != TipoAvaliacao.INT_RECUPERACAO_FINAL && aval.getIdTipoAvaliacao() != TipoAvaliacao.INT_RECUPERACAO) {
                            hashSetAval.add(aval.getAssunto());
                        }
                    }
                }
                ArrayList<String> listNomeAvaliacao = new ArrayList<String>(hashSetAval);
                if (listNomeAvaliacao.size() != 0) {
                    listNomeAvaliacao.add(TipoAvaliacao.STR_RECUPERACAO);
                    listNomeAvaliacao.add(TipoAvaliacao.STR_RECUPERACAO_FINAL);
                    listNomeAvaliacao.add(Nota.STR_MEDIA_FINAL);
                }

                for (String strNomeAvaliacao : listNomeAvaliacao) {

                    ArrayList<String> listRow = new ArrayList<String>();
                    int idUser = usuario.getIdUsuario();

                    listRow.add(Integer.toString(idUser));
                    listRow.add(usuario.getPrimeiroNome() + " " + usuario.getSobreNome());
                    // listRow.add("[" + periodo.getNomePeriodo() + "] " +
                    // strNomeAvaliacao.substring(strNomeAvaliacao.indexOf(":")+1));
                    listRow.add("[" + periodo.getNomePeriodo() + "] " + strNomeAvaliacao);

                    for (String strNomeDisc : listDisciplinasAno) {

                        Disciplina disciplina = DisciplinaServer.getDisciplinaFromArray(strNomeDisc, listDisciplinas);

                        if (strNomeAvaliacao.equals(Nota.STR_MEDIA_FINAL)) {
                            String strMedia = "-";
                            if (disciplina == null) {
                                strMedia = "-";
                            } else {

                                strMedia = disciplina.getMediaAlunoDisciplina(curso, listPeriodo, idUser, true);
                                if (strMedia == null || strMedia.isEmpty()) {
                                    strMedia = "-";
                                } else {
                                    double doubleMediaAluno = Double.parseDouble(strMedia);
                                    strMedia = MpUtilServer.getDecimalFormatedOneDecimalMultipleFive(doubleMediaAluno);
                                }
                            }
                            listRow.add(strMedia);
                        } else {
                            String strNota = "-";
                            if (disciplina == null) {
                                strNota = "-";
                            } else {
                                int idDisciplina = disciplina.getIdDisciplina();

                                if (strNomeAvaliacao.equals(TipoAvaliacao.STR_RECUPERACAO_FINAL)) {
                                    strNota = getNotaRecuperacao(listDisciplinas, idUser, idDisciplina, strNomeAvaliacao, TipoAvaliacao.INT_RECUPERACAO_FINAL);
                                } else if (strNomeAvaliacao.equals(TipoAvaliacao.STR_RECUPERACAO)) {
                                    strNota = getNotaRecuperacao(listDisciplinas, idUser, idDisciplina, strNomeAvaliacao, TipoAvaliacao.INT_RECUPERACAO);
                                } else {
                                    strNota = getNota(listDisciplinas, idUser, idDisciplina, strNomeAvaliacao);
                                }

                                if (strNota == null || strNota.isEmpty()) {
                                    strNota = "-";
                                    if (strNomeAvaliacao.equals(TipoAvaliacao.STR_RECUPERACAO_FINAL)) {
                                        strNota = "remover.linha";
                                    }
                                }
                            }
                            listRow.add(strNota);
                        }
                    }
                    // Adicionando Provas
                    listNotas.add(listRow);
                }

            }
        }

        for (int i = 0; i < listNotas.size(); i++) {
            ArrayList<String> listRow = listNotas.get(i);
            int count = 3;
            for (int j = 2; j < listRow.size(); j++) {
                String strText = listRow.get(j);
                if (strText.equals("remover.linha")) {
                    listRow.set(j, "-");
                    count++;
                }
            }

            if (count == listRow.size()) {
                listNotas.remove(i);
            }

        }

        return listNotas;
    }
    
    public static String getNomeDisciplinaCleaned(String strNomeDisciplina){
        
        String text = strNomeDisciplina.substring(0,strNomeDisciplina.indexOf(FieldVerifier.INI_SEPARATOR));
        text = text.substring(0,text.lastIndexOf(")"));
        text = text.substring(0,text.lastIndexOf("("));        
        return text;
    }
    
    public static ArrayList<ArrayList<String>> getHistoricoAluno(int idCurso, int idAluno) {
        
        ArrayList<ArrayList<String>> listHistorico = new ArrayList<ArrayList<String>>();
        Usuario aluno = UsuarioServer.getUsuarioPeloId(idAluno);
        ArrayList<Curso> listCursos = new ArrayList<Curso>();
        Curso cursoAtual = CursoServer.getCurso(idCurso);
        ArrayList<Curso> listCursosAtivados = new ArrayList<Curso>();
        ArrayList<Curso> listCursosDesativados = CursoServer.getCursosPorAlunoAmbienteAluno(aluno, false);
        
        listCursosAtivados.add(cursoAtual);
        
        listCursos.addAll(listCursosAtivados);
        listCursos.addAll(listCursosDesativados);
        
        Ano ano = new Ano();
        

        if (cursoAtual != null) {
            
            if (cursoAtual.getEnsino().equals(Ensino.FUNDAMENTAL)) {
          
                ArrayList<String> listHeader = new ArrayList<String>();
                listHeader.add(" ");

                
                
                for(int i=0;i<ano.getListFundamental().size();i++){
                    AnoItem anoItem = ano.getListFundamental().get(i);
                    listHeader.add(anoItem.getValue()+FieldVerifier.INI_SEPARATOR+anoItem.getName());
                }
                listHistorico.add(listHeader);
                
                HashSet<String> hashDisciplinas = new HashSet<String>();
                hashDisciplinas.add("Disciplinas");
                for(Curso cursoRow : listCursos){
                    ArrayList<Periodo> listPeriodos = PeriodoServer.getPeriodos(cursoRow.getIdCurso());
                    for(Periodo periodoRow : listPeriodos){
                        ArrayList<Disciplina> listDisciplinas = DisciplinaServer.getDisciplinas(periodoRow.getIdPeriodo());
                        for(Disciplina disciplinaRow : listDisciplinas){
                            String strText ="";
                            if(disciplinaRow.isObrigatoria()){
                                strText= "BNC";
                            }else{
                               strText = "PD"; 
                            }
                            hashDisciplinas.add(disciplinaRow.getNome()+"("+strText+")"+FieldVerifier.INI_SEPARATOR+disciplinaRow.isObrigatoria());
                        }
                    }
                }
                
                
                
                ArrayList<String> listDisciplinas = new ArrayList<String>(hashDisciplinas);
                
                
                Collections.sort(listDisciplinas, new Comparator<String>() {
                    public int compare(String str1, String str2) {
                        if(str1.equals("Disciplinas")){
                            str1="0Disciplinas";
                        }
                        if(str2.equals("Disciplinas")){
                            str2="0Disciplinas";
                        }                        
                        return str1.compareTo(str2);
                    }
                });
                
                
                for(int row = 0; row<listDisciplinas.size();row++){
                    ArrayList<String> listRow = new ArrayList<String>();
                    if(row>0){                        
                        for(int column=0;column<listHeader.size();column++){
                            if(column==0){
                                listRow.add(listDisciplinas.get(row));
                            }else{
                                listRow.add("-");
                            }
                        }
                        listHistorico.add(listRow);
                    }                    
                }
                
//                for(Curso cursoRow : listCursos){
                for(int column=1; column<listHeader.size();column++){
                    String strHeader = listHeader.get(column).substring(0,listHeader.get(column).indexOf(FieldVerifier.INI_SEPARATOR));
                    
                    int idCursoColumn=0;
                    for(Curso cursoRow : listCursos){
                        if(cursoRow.getAno().equals(strHeader)){
                            idCursoColumn = cursoRow.getIdCurso();
                        }
                    }
                    if(idCursoColumn>0){
                        ArrayList<Periodo> listPeriodo = PeriodoServer.getPeriodos(idCursoColumn);
                        
                        
//                        for(String strDisciplina : listDisciplinas){
                        for (int row = 1;row<listDisciplinas.size();row++){
                            
                            
                            String strDisciplina = listDisciplinas.get(row);
                            

                            if (fd(listPeriodo, strDisciplina)) {
                                strDisciplina = getNomeDisciplinaCleaned(strDisciplina);
                                String strMediaAnualDisciplina = getMediaAnualDisciplina(idAluno, idCursoColumn, listPeriodo, strDisciplina);
                                if (strMediaAnualDisciplina == null || strMediaAnualDisciplina.isEmpty()) {
                                    strMediaAnualDisciplina = "-";
                                }
                                listHistorico.get(row).set(column, strMediaAnualDisciplina);
                            } else {
                                listHistorico.get(row).set(column, "-");
                            }
                        
                        }
                    }

                }
                
                
                
                
            } else if (cursoAtual.getEnsino().equals(Ensino.MEDIO)) {
                
                
                ArrayList<String> listHeader = new ArrayList<String>();
                listHeader.add(" ");
            
                //Pegando Séries
                for(int i=0;i<ano.getListMedio().size();i++){
                    AnoItem anoItem = ano.getListMedio().get(i);
                    listHeader.add(anoItem.getValue()+FieldVerifier.INI_SEPARATOR+anoItem.getName());
                }
                listHistorico.add(listHeader);
                

                //Pegando Disciplinas
                HashSet<String> hashDisciplinas = new HashSet<String>();
                hashDisciplinas.add("Disciplinas");
                for(Curso cursoRow : listCursos){
                    ArrayList<Periodo> listPeriodos = PeriodoServer.getPeriodos(cursoRow.getIdCurso());
                    for(Periodo periodoRow : listPeriodos){
                        ArrayList<Disciplina> listDisciplinas = DisciplinaServer.getDisciplinas(periodoRow.getIdPeriodo());
                        for(Disciplina disciplinaRow : listDisciplinas){
                            
                            String strText ="";
                            if(disciplinaRow.isObrigatoria()){
                                strText= "BNC";
                            }else{
                               strText = "PD"; 
                            }
                            hashDisciplinas.add(disciplinaRow.getNome()+"("+strText+")"+FieldVerifier.INI_SEPARATOR+disciplinaRow.isObrigatoria());
                            
//                            hashDisciplinas.add(disciplinaRow.getNome().trim()+FieldVerifier.INI_SEPARATOR+disciplinaRow.isObrigatoria());
//                            System.out.println("Disciplina:"+disciplinaRow.getNome());
                        }
                    }
                }
                
                
                //Ordenando Disciplinas
                ArrayList<String> listDisciplinas = new ArrayList<String>(hashDisciplinas);
                
                Collections.sort(listDisciplinas, new Comparator<String>() {
                    public int compare(String str1, String str2) {
                        if(str1.equals("Disciplinas")){
                            str1=" 0Disciplinas";
                        }
                        if(str2.equals("Disciplinas")){
                            str2=" 0Disciplinas";
                        }                        
                        return str1.compareTo(str2);
                    }
                });           
                
                
                
                for(int row = 0; row<listDisciplinas.size();row++){
                    ArrayList<String> listRow = new ArrayList<String>();
                    if(row>0){                        
                        for(int column=0;column<listHeader.size();column++){
                            if(column==0){
                                listRow.add(listDisciplinas.get(row));
                            }else{
                                listRow.add("-");
                            }
                        }
                        listHistorico.add(listRow);
                    }                    
                }
                
                
                for (int column = 1; column < listHeader.size(); column++) {
                    int indexOfHeader = listHeader.get(column).indexOf(FieldVerifier.INI_SEPARATOR);
                    
//                    if (column > 1) {
                        String strHeader = "";
                        strHeader = listHeader.get(column).substring(0, indexOfHeader);

                        int idCursoColumn = 0;
                        for (Curso cursoRow : listCursos) {
                            if (cursoRow.getAno().equals(strHeader)) {
                                idCursoColumn = cursoRow.getIdCurso();
                            }
                        }
                        if (idCursoColumn > 0) {
                            ArrayList<Periodo> listPeriodo = PeriodoServer.getPeriodos(idCursoColumn);

                            // for(String strDisciplina : listDisciplinas){
                            for (int row = 1; row < listDisciplinas.size(); row++) {
//                                String strDisciplina = listDisciplinas.get(row);fg
//                                strDisciplina = strDisciplina.substring(0, strDisciplina.indexOf(FieldVerifier.INI_SEPARATOR));
//                                String strMediaAnualDisciplina = getMediaAnualDisciplina(idAluno, idCursoColumn, listPeriodo, strDisciplina);
//                                if (strMediaAnualDisciplina == null || strMediaAnualDisciplina.isEmpty()) {
//                                    strMediaAnualDisciplina = "-";
//                                }
//                                listHistorico.get(row).set(column, strMediaAnualDisciplina);
                                
                                String strDisciplina = listDisciplinas.get(row);
                                

                                if (fd(listPeriodo, strDisciplina)) {
                                    strDisciplina = getNomeDisciplinaCleaned(strDisciplina);
                                    String strMediaAnualDisciplina = getMediaAnualDisciplina(idAluno, idCursoColumn, listPeriodo, strDisciplina);
                                    if (strMediaAnualDisciplina == null || strMediaAnualDisciplina.isEmpty()) {
                                        strMediaAnualDisciplina = "-";
                                    }
                                    listHistorico.get(row).set(column, strMediaAnualDisciplina);
                                } else {
                                    listHistorico.get(row).set(column, "-");
                                }
                            }
                        }
//                    }

                }

            }
            
        }
        
        
        return listHistorico;
    }
    
    
    public static boolean fd(ArrayList<Periodo> listPeriodo, String strDisc){
        
        boolean result=false;
        for (int p = 0; p < listPeriodo.size(); p++) {
            
            ArrayList<Disciplina> listDisciplina = DisciplinaServer.getDisciplinas(listPeriodo.get(p).getIdPeriodo());
            
            for(int d = 0; d < listDisciplina.size(); d++){
                
                String strNomeDisciplina = listDisciplina.get(d).getNome();
                boolean isObrigatorio = false;
                
                String strDisciplina = getNomeDisciplinaCleaned(strDisc);
                String strIsObrigatorio = strDisc.substring(0, strDisc.lastIndexOf(")"));
                strIsObrigatorio = strIsObrigatorio.substring(strIsObrigatorio.lastIndexOf("(")+1);
                if(strIsObrigatorio.equals("BNC")){
                    isObrigatorio = true;
                }
                
                if (strNomeDisciplina.equals(strDisciplina)) {
                    if (isObrigatorio == listDisciplina.get(d).isObrigatoria()) {
                        result = true;
                    }
                }
            }            
            
        }
        
        return result;
    }

    public static ArrayList<ArrayList<String>> getBoletimAluno(int idCurso, int idAluno) {
        
        Curso curso = CursoServer.getCurso(idCurso);
        
        ArrayList<ArrayList<String>> listBoletimAluno = new ArrayList<ArrayList<String>>();
        
        ArrayList<Periodo> listPeriodo = PeriodoServer.getPeriodos(idCurso);
        
        ArrayList<String> siglasPeriodo = new ArrayList<String>();
        siglasPeriodo.add(Nota.STR_BOLETIM_ALUNO_MP);
        siglasPeriodo.add(Nota.STR_BOLETIM_ALUNO_REC);
        siglasPeriodo.add(Nota.STR_BOLETIM_ALUNO_MF);
        

        ArrayList<String> rowPeriodos = new ArrayList<String>();
       
        HashSet<String> hashSetDisciplinas = new HashSet<String>();
        rowPeriodos.add("Períodos");
        
//        int cvPeriodos=0;
        for(Periodo periodo : listPeriodo){            
            
            rowPeriodos.add(periodo.getIdPeriodo()+":"+periodo.getNomePeriodo());
            
//            siglasPeriodo.set(cvPeriodos, siglasPeriodo.get(cvPeriodos));
            
            ArrayList<Disciplina> listDisciplinas = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());
            
            for(Disciplina disciplina : listDisciplinas){  
                hashSetDisciplinas.add(disciplina.getNome());
            }                
//            cvPeriodos++;
        }
        
        ArrayList<String> rowDisciplinas = new ArrayList<String>(hashSetDisciplinas);
        Collections.sort(rowDisciplinas);
        ArrayList<String> rowDisciplinasOrdered = new ArrayList<String>();
        rowDisciplinasOrdered.add("Disciplina");
        for (String string : rowDisciplinas) {
            rowDisciplinasOrdered.add(string);
        }
        
//        ArrayList<ArrayList<String>> listBoletimAluno = new  ArrayList<ArrayList<String>> ();
        
        
        
        int cvFirstLine=0;
        for(String nomeDisciplina : rowDisciplinasOrdered){
            ArrayList<String> row = new ArrayList<String>();
            row.add(nomeDisciplina);
            int intSiglas=0;
            Avaliacao recuperacaoFinal = null;
            for(Periodo periodo : listPeriodo){ 
                
                Disciplina disciplina=null;
                if (cvFirstLine > 0) {
                    disciplina = DisciplinaServer.getDisciplinaPeloPeriodo(periodo.getIdPeriodo(), nomeDisciplina);
                    if (disciplina != null) {
                        disciplina.setListAvaliacao(AvaliacaoServer.getAvaliacaoComNotas(disciplina.getIdDisciplina()));
                        if (recuperacaoFinal == null) {
                            recuperacaoFinal = disciplina.getRecuperacaoFinal();
                        }
                    }
                }
               
                for(String strSiglas : siglasPeriodo){
                    if (cvFirstLine == 0) {
                        row.add("["+periodo.getNomePeriodo() + "] " + strSiglas+(intSiglas+1));
                    } else {
                        
                        if(disciplina!=null){
                            if (strSiglas.equals(Nota.STR_BOLETIM_ALUNO_MP)) {
                                String strMedia = disciplina.getMediaAlunoDisciplina(curso, listPeriodo, idAluno, false);
                                if (strMedia == null || strMedia.isEmpty()) {
                                    strMedia = "-";
                                } else {
                                    double doubleMediaAluno = Double.parseDouble(strMedia);
                                    strMedia = MpUtilServer.getDecimalFormatedOneDecimalMultipleFive(doubleMediaAluno);
                                }
                                row.add(strMedia);
                            } else if (strSiglas.equals(Nota.STR_BOLETIM_ALUNO_REC)) {
                                String strMedia = disciplina.getNotaRecuperacao(idAluno);
                                if (strMedia == null || strMedia.isEmpty()) {
                                    strMedia = "-";
                                } else {
                                    double doubleMediaAluno = Double.parseDouble(strMedia);
                                    strMedia = MpUtilServer.getDecimalFormatedOneDecimalMultipleFive(doubleMediaAluno);
                                }
                                row.add(strMedia);
                            } else if (strSiglas.equals(Nota.STR_BOLETIM_ALUNO_MF)) {
                                String strMedia = disciplina.getMediaAlunoDisciplina(curso, listPeriodo, idAluno, true);
                                if (strMedia == null || strMedia.isEmpty()) {
                                    strMedia = "-";
                                } else {
//                                    System.out.println("Disciplina:"+disciplina.getNome());
                                    double doubleMediaAluno = Double.parseDouble(strMedia);
                                    strMedia = MpUtilServer.getDecimalFormatedOneDecimalMultipleFive(doubleMediaAluno);
                                }
                                row.add(strMedia);
                            }
                        }
                    }
                }
                intSiglas++;
            }
            
            if (cvFirstLine == 0) {
                row.add("MFP");
                row.add("EX");
                row.add("MFA");
            }else{
                
                String strMediaAnualDisciplina = getMediaAnualDisciplina(idAluno, idCurso, listPeriodo, nomeDisciplina);
                if (strMediaAnualDisciplina == null || strMediaAnualDisciplina.isEmpty()) {
                    strMediaAnualDisciplina = "-";
                    row.add(strMediaAnualDisciplina);
                    row.add(strMediaAnualDisciplina);
                    row.add(strMediaAnualDisciplina);
                } else {
                    double doubleMediaAluno = Double.parseDouble(strMediaAnualDisciplina);
                    row.add(MpUtilServer.getDecimalFormatedOneDecimal(doubleMediaAluno));
                    if(recuperacaoFinal!=null){
                        row.add(recuperacaoFinal.getNotaRecuperacaoFinal());
                    }else{
                        row.add("-");
                    }
                   
                    row.add(MpUtilServer.getDecimalFormatedOneDecimalMultipleFive(doubleMediaAluno));
                }
                
            }
            cvFirstLine++;
            listBoletimAluno.add(row);
        }
        
        
        
        
        return listBoletimAluno;
                
    }

    private static String getNota(ArrayList<Disciplina> listDisciplina, int idUsuario, int idDisciplina, String strAssuntoAvaliacao) {
        String strNota = "";

        for (Disciplina disciplina : listDisciplina) {
            if (disciplina.getIdDisciplina() == idDisciplina) {
                ArrayList<Avaliacao> listAvaliacao = disciplina.getListAvaliacao();
                for (Avaliacao avaliacao : listAvaliacao) {
                    // if(avaliacao.getIdAvaliacao()== idAvaliacao){
                    if (avaliacao.getAssunto().equals(strAssuntoAvaliacao)) {
                        ArrayList<Nota> listNota = avaliacao.getListNota();
                        for (Nota nota : listNota) {
                            if (nota.getIdUsuario() == idUsuario) {
                                strNota = nota.getNota();
                            }
                        }
                    }
                }
            }

        }

        return strNota;
    }

    private static String getNotaRecuperacao(ArrayList<Disciplina> listDisciplina, int idUsuario, int idDisciplina, String strAssuntoAvaliacao, int intTipoAvaliacao) {
        String strNota = "";

        for (Disciplina disciplina : listDisciplina) {
            if (disciplina.getIdDisciplina() == idDisciplina) {
                ArrayList<Avaliacao> listAvaliacao = disciplina.getListAvaliacao();
                for (Avaliacao avaliacao : listAvaliacao) {
                    if (avaliacao.getIdTipoAvaliacao() == intTipoAvaliacao) {
                        // if
                        // (avaliacao.getAssunto().equals(strAssuntoAvaliacao))
                        // {
                        ArrayList<Nota> listNota = avaliacao.getListNota();
                        for (Nota nota : listNota) {
                            if (nota.getIdUsuario() == idUsuario) {
                                strNota = nota.getNota();
                            }
                        }
                    }
                }
            }

        }

        return strNota;
    }

    public static String getMediaAnualDisciplina(int idUsuario, int idCurso, ArrayList<Periodo> listPeriodo, String strDisciplina) {
        String strMedia = "";
        double doubleMediaAluno = 0;
        int countMedia = 0;
        Curso curso = CursoServer.getCurso(idCurso);

        String strNotaRecuperacaoFinal = "";

        for (Periodo periodo : listPeriodo) {

            ArrayList<Disciplina> listDisciplina = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());

            for (Disciplina disciplina : listDisciplina) {

                if (disciplina.getNome().equals(strDisciplina)) {

                    disciplina.setListAvaliacao(AvaliacaoServer.getAvaliacaoComNotas(disciplina.getIdDisciplina()));
                    strMedia = disciplina.getMediaAlunoDisciplina(curso, listPeriodo, idUsuario, true);

                    // Pegar Nota Recuperação
                    for (int i = 0; i < disciplina.getListAvaliacao().size(); i++) {
                        Avaliacao avaliacao = disciplina.getListAvaliacao().get(i);
                        if (avaliacao.getIdTipoAvaliacao() == TipoAvaliacao.INT_RECUPERACAO_FINAL) {
                            for (Nota nota : avaliacao.getListNota()) {
                                if (nota.getIdUsuario() == idUsuario) {
                                    if (nota.getNota() != null && !nota.getNota().isEmpty()) {
                                        strNotaRecuperacaoFinal = nota.getNota();
                                    }
                                }
                            }
                        }
                    }

                    int intPesoNotaPeriodo = Integer.parseInt(periodo.getPeso());
                    for (int i = 0; i < intPesoNotaPeriodo; i++) {
                        if (strMedia == null || strMedia.isEmpty()) {
                        } else {
                            doubleMediaAluno = doubleMediaAluno + Double.parseDouble(strMedia);
                            countMedia++;
                            // break;
                        }
                    }

                }
            }
        }

        if (countMedia != 0) {

            doubleMediaAluno = doubleMediaAluno / countMedia;

            if (strNotaRecuperacaoFinal.isEmpty()) {
                strNotaRecuperacaoFinal = Double.toString(doubleMediaAluno);
            }
            Double doubleNotaRecuperacaoFinal = Double.parseDouble(strNotaRecuperacaoFinal);
            doubleMediaAluno = (doubleMediaAluno + doubleNotaRecuperacaoFinal) / 2;

            strMedia = MpUtilServer.getDecimalFormatedOneDecimalMultipleFive(doubleMediaAluno);
        }

        return strMedia;
    }

    public static ArrayList<ArrayList<String>> getRelatorioBoletimDisciplina(int idCurso, int idPeriodo, int idDisciplina) {

        Curso curso = CursoServer.getCurso(idCurso);
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        ArrayList<Periodo> listPeriodo = PeriodoServer.getPeriodos(curso.getIdCurso());
        ArrayList<Usuario> listUsuario = UsuarioServer.getAlunosPorCurso(idCurso);

        Disciplina disciplina = DisciplinaServer.getDisciplina(idDisciplina);
        disciplina.setListAvaliacao(AvaliacaoServer.getAvaliacaoComNotas(idDisciplina));

        for (Usuario usuario : listUsuario) {

            ArrayList<String> item = new ArrayList<String>();
            item.add(Integer.toString(usuario.getIdUsuario()));
            if (usuario.getIdTipoStatusUsuario() == TipoStatusUsuario.ALUNO_TRANSFERIDO) {
                item.add(usuario.getPrimeiroNome() + " " + usuario.getSobreNome() + " - " + usuario.getTipoStatusUsuario().getNomeTipoStatusUsuario());
            } else {
                item.add(usuario.getPrimeiroNome() + " " + usuario.getSobreNome());
            }

            for (Avaliacao avaliacao : disciplina.getListAvaliacao()) {
                if (avaliacao.getIdTipoAvaliacao() != TipoAvaliacao.INT_RECUPERACAO_FINAL) {
                    String strNota = "";
                    if (usuario.getIdTipoStatusUsuario() == TipoStatusUsuario.ALUNO_TRANSFERIDO) {
                        strNota = "-";
                    } else {
                        for (Nota nota : avaliacao.getListNota()) {
                            if (nota.getIdUsuario() == usuario.getIdUsuario()) {
                                if (avaliacao.getIdAvaliacao() == nota.getIdAvaliacao()) {
                                    strNota = nota.getNota();
                                    break;
                                }
                            }
                        }
                        if (strNota == null || strNota.isEmpty()) {
                            strNota = "-";
                        }
                    }
                    item.add(strNota);
                }
            }
            list.add(item);
        }

        // Pegando a Média
        for (int i = 0; i < list.size(); i++) {
            int idUsuario = Integer.parseInt(list.get(i).get(0));
            Usuario aluno = UsuarioServer.getUsuarioPeloId(idUsuario);
            if (aluno.getIdTipoStatusUsuario() == 2) {
                list.get(i).add("-");
                list.get(i).add("-");

            } else {
                String strMedia = disciplina.getMediaAlunoDisciplina(curso, listPeriodo, idUsuario, true);
                if (strMedia == null || strMedia.isEmpty()) {
                    strMedia = "-";
                    list.get(i).add(strMedia);
                    list.get(i).add(strMedia);
                } else {
                    double doubleMediaAluno = Double.parseDouble(strMedia);
                    // list.get(i).add(MpUtilServer.getDecimalFormatedTwoDecimal(doubleMediaAluno));
                    // list.get(i).add(MpUtilServer.getDecimalFormatedOneDecimal(doubleMediaAluno));
                    list.get(i).add(MpUtilServer.getDecimalFormatedTwoDecimal(doubleMediaAluno));
                    list.get(i).add(MpUtilServer.getDecimalFormatedOneDecimalMultipleFive(doubleMediaAluno));
                }
            }

        }

        return list;
    }

    public static ArrayList<ArrayList<String>> getMediaNotaAlunosNasDisciplinas(int idCurso, ArrayList<Usuario> listUsuario, ArrayList<Disciplina> listDisciplinas) {
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

        Curso curso = CursoServer.getCurso(idCurso);
        ArrayList<Periodo> listPeriodo = PeriodoServer.getPeriodos(curso.getIdCurso());

        for (Usuario usuario : listUsuario) {
            ArrayList<String> item = new ArrayList<String>();

            item.add(Integer.toString(usuario.getIdUsuario()));
            if (usuario.getIdTipoStatusUsuario() == TipoStatusUsuario.ALUNO_TRANSFERIDO) {
                item.add(usuario.getPrimeiroNome() + " " + usuario.getSobreNome() + " - " + usuario.getTipoStatusUsuario().getNomeTipoStatusUsuario());
            } else {
                item.add(usuario.getPrimeiroNome() + " " + usuario.getSobreNome());
            }
            for (Disciplina disciplina : listDisciplinas) {
                if (usuario.getIdTipoStatusUsuario() == TipoStatusUsuario.ALUNO_TRANSFERIDO) {
                    item.add("-");
                } else {
                    String strMedia = disciplina.getMediaAlunoDisciplina(curso, listPeriodo, usuario.getIdUsuario(), true);
                    if (strMedia == null || strMedia.isEmpty()) {
                        strMedia = "-";
                        item.add(strMedia);
                    } else {
                        double doubleMediaAluno = Double.parseDouble(strMedia);
                        item.add(MpUtilServer.getDecimalFormatedOneDecimalMultipleFive(doubleMediaAluno));
                    }
                }
            }

            list.add(item);
        }

        return list;
    }

    public static String getExcelBoletimNotas(XSSFWorkbook wb, XSSFSheet sheet, int idCurso) {
//        XSSFWorkbook wb = new XSSFWorkbook();
//        XSSFSheet sheet = wb.createSheet("Boletim Notas");
        // sheet.setColumnBreak(15);
        sheet.getPrintSetup().setLandscape(true);
        sheet.setAutobreaks(true);
        sheet.setFitToPage(true);
        sheet.getPrintSetup().setFitWidth((short) 1);
        sheet.getPrintSetup().setFitHeight((short) 0);

        ArrayList<ArrayList<String>> listAvaliacaoNotasAlunos = getBoletimNotas(idCurso);
        Curso curso = CursoServer.getCurso(idCurso);

        int intColumn = 0;
        int intLine = 0;
        Row row = sheet.createRow((short) intLine);
        row.createCell((short) intColumn).setCellValue("PLANILHA DE NOTAS - " + curso.getNome());
        row.getCell((short) intColumn).setCellStyle(ExcelFramework.getStyleTitleBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, 0, 8));
        intLine++;

        // row = sheet.createRow((short) intLine);
        // row.createCell((short) intColumn).setCellValue(curso.getNome());
        // row.getCell((short)
        // intColumn).setCellStyle(ExcelFramework.getStyleTitleBoletim(wb));
        // sheet.addMergedRegion(new CellRangeAddress(intLine,intLine,0,8));
        // intLine++;

        // row = sheet.createRow((short) intLine++);
        // row.createCell((short) intColumn).setCellValue("Aluno");
        // row.getCell((short)
        // intColumn++).setCellStyle(ExcelFramework.getStyleHeaderBoletim(wb));
        if (listAvaliacaoNotasAlunos.size() > 0) {
            ArrayList<String> listHeaderAlunoNotas = listAvaliacaoNotasAlunos.get(0);
            row = sheet.createRow((short) intLine++);
            intColumn = listHeaderAlunoNotas.size();
            for (int i = 0; i < listHeaderAlunoNotas.size() - 1; i++) {
                row.createCell((short) i);
                if (i == 0 || i == 1) {
                    String strText = listHeaderAlunoNotas.get(i + 1);
                    row.getCell((short) i).setCellValue(strText);
                    row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell((short) i).setCellStyle(ExcelFramework.getStyleHeaderBoletim(wb));
                } else {
                    String strText = listHeaderAlunoNotas.get(i + 1);
                    strText = Curso.getAbreviarNomeCurso(strText);
                    row.getCell((short) i).setCellValue(strText);
                    row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell((short) i).setCellStyle(ExcelFramework.getStyleHeaderBoletim(wb));
                }
            }

            for (int line = 1; line < listAvaliacaoNotasAlunos.size(); line++) {
                ArrayList<String> listAlunoNotas = listAvaliacaoNotasAlunos.get(line);
                row = sheet.createRow((short) intLine++);
                for (int i = 0; i < listAlunoNotas.size() - 1; i++) {
                    String strText = listAlunoNotas.get(i + 1);
                    row.createCell((short) i);

                    if (i == 0 || i == 1) {
                        row.getCell((short) i).setCellValue(strText);
                        row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING);
                        row.getCell((short) i).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));

                    } else {
                        if (FieldVerifier.isNumeric(strText)) {
                            double nota = Double.parseDouble(strText);
                            double mediaCurso = Double.parseDouble(curso.getMediaNota());
                            String strSinal = "";
                            if (nota < mediaCurso) {
                                strSinal = "*";
                                row.getCell((short) i).setCellValue(strSinal + strText);
                                row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING);
                            } else {
                                row.getCell((short) i).setCellValue(nota);
                                row.getCell((short) i).setCellType(Cell.CELL_TYPE_NUMERIC);
                            }

                        } else {
                            row.getCell((short) i).setCellValue(strText);
                            row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING);
                        }

                        row.getCell((short) i).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));

                    }
                }
            }
        }

        for (int i = 0; i < intColumn; i++) {
            sheet.autoSizeColumn(i, true);
        }

        return "";
//        return ExcelFramework.getExcelAddress(wb, "GerarExcelBoletimNotas_");
    }

    public static void getExcelBoletimAnual(XSSFWorkbook wb, XSSFSheet sheet, int idCurso) {
//        XSSFWorkbook wb = new XSSFWorkbook();
//        XSSFSheet sheet = wb.createSheet("Boletim Anual");
        sheet.setFitToPage(true);
        sheet.getPrintSetup().setLandscape(true);
        sheet.setMargin((short) 1, 1.5);

        ArrayList<ArrayList<String>> listNotas = getBoletimAnual(idCurso);
        ArrayList<String> listHeader = listNotas.get(0);

        Curso curso = CursoServer.getCurso(idCurso);

        String texto1 = "COLÉGIO INTEGRADO";
        String texto2 = "Ato de Criação da Escola : Portaria do Dirigente Regional de Ensino de 26/01 - Publicado no D.O.E. de 27/01/99";
        String texto3 = "Na data de " + MpUtilServer.convertDateToString(curso.getDataFinal(), "dd MMMM yyyy") + ", encerrou-se o ";
        String texto4 = "processo de avaliação de aprendizagem dos alunos do : ";
        String texto5 = curso.getNome() + ", ";
        String texto6 = "deste estabelecimento com o resultado abaixo discriminado. ";
        String texto7 = "\n\nDias Letivos:200";
        int intColumn = 0;
        int intLine = 0;

        Row row = sheet.createRow(intLine);
        row.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine + 1, 0, listHeader.size() - 2));
        row.getCell(0).setCellValue(texto1);
        row.getCell(0).setCellStyle(ExcelFramework.getStyleTitleBoletimAno(wb));

        intLine = intLine + 2;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue(texto2);
        row.getCell(0).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, 0, listHeader.size() - 2));

        int startMergedComments = 3;
        int sizeMergedComments = 5;
        intLine = intLine + 1;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue(new XSSFRichTextString(texto3 + texto4 + texto5 + texto6 + texto7));
        CellRangeAddress regionComments = new CellRangeAddress(intLine, intLine + sizeMergedComments, 0, 0);
        ExcelFramework.cleanBeforeMergeOnValidCells(sheet, regionComments, ExcelFramework.getStyleCellFontBoletimDataFinalizacao(wb));
        sheet.addMergedRegion(regionComments);

        row.createCell(1).setCellValue("DISCIPLINAS - GRADE CURRICULAR");
        CellRangeAddress regionGrade = new CellRangeAddress(intLine, intLine, 1, listHeader.size() - 3);
        ExcelFramework.cleanBeforeMergeOnValidCells(sheet, regionGrade, ExcelFramework.getStyleHeaderBoletim(wb));
        sheet.addMergedRegion(regionGrade);
        row.createCell(listHeader.size() - 2).setCellValue("RESULTADO FINAL");
        row.getCell(listHeader.size() - 2).setCellStyle(ExcelFramework.getStyleHeaderBoletimRotation90Right(wb));

        intLine = intLine + sizeMergedComments + 1;

        row = sheet.createRow(intLine++);

        for (int i = 1; i < listHeader.size(); i++) {
            String header = "";
            row.createCell(intColumn);
            row.getCell(intColumn).setCellType(Cell.CELL_TYPE_STRING);

            if (i == 1) {

                header = listHeader.get(i);
                row.getCell(intColumn).setCellValue(header);
                row.getCell(intColumn).setCellStyle(ExcelFramework.getStyleHeaderBoletim(wb));

            } else {

                int intSizeDisciplina = 1;
                if (i == listHeader.size() - 1) {
                    header = listHeader.get(i);
                    intSizeDisciplina = 2;
                } else {
                    intSizeDisciplina = 1;
                    header = Curso.getAbreviarNomeCursoAno(listHeader.get(i));
                }

                row = sheet.getRow(startMergedComments + 1);
                row.createCell(intColumn);
                row.getCell(intColumn).setCellValue(header);
                row.getCell(intColumn).setCellStyle(ExcelFramework.getStyleHeaderBoletimRotation90Right(wb));

                CellRangeAddress region = new CellRangeAddress(intLine - sizeMergedComments - intSizeDisciplina, intLine - 1, intColumn, intColumn);
                ExcelFramework.cleanBeforeMergeOnValidCells(sheet, region, ExcelFramework.getStyleHeaderBoletimRotation90Right(wb));
                sheet.addMergedRegion(region);

            }

            intColumn++;
        }

        for (int r = 1; r < listNotas.size(); r++) {
            ArrayList<String> listRow = listNotas.get(r);
            row = sheet.createRow((short) intLine++);
            for (int c = 1; c < listRow.size(); c++) {
                String strText = listRow.get(c);
                row.createCell((short) c - 1);

                if (c == 1) {
                    row.getCell((short) c - 1).setCellValue(strText);
                    row.getCell((short) c - 1).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell((short) c - 1).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
                } else {
                    if (FieldVerifier.isNumeric(strText)) {
                        row.getCell((short) c - 1).setCellValue(Double.parseDouble(strText));
                        row.getCell((short) c - 1).setCellType(Cell.CELL_TYPE_NUMERIC);
                    } else {
                        row.getCell((short) c - 1).setCellValue(strText);
                        row.getCell((short) c - 1).setCellType(Cell.CELL_TYPE_STRING);
                    }
                    row.getCell((short) c - 1).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
                }
            }
        }

        for (int i = 0; i < intColumn; i++) {
            if (i == 0) {
                sheet.setColumnWidth(i, 20000);
            } else if (i == intColumn - 1) {
                sheet.autoSizeColumn(i, true);
            } else {
                sheet.setColumnWidth(i, 2000);
            }
        }

        ExcelFramework.createImage(wb, sheet);

        CellRangeAddress regionAll = new CellRangeAddress(0, intLine - 1, 0, listHeader.size() - 2);
        RegionUtil.setBorderBottom(BorderStyle.THICK.ordinal(), regionAll, sheet, wb);
        RegionUtil.setBorderTop(BorderStyle.THICK.ordinal(), regionAll, sheet, wb);
        RegionUtil.setBorderLeft(BorderStyle.THICK.ordinal(), regionAll, sheet, wb);
        RegionUtil.setBorderRight(BorderStyle.THICK.ordinal(), regionAll, sheet, wb);

//        return ExcelFramework.getExcelAddress(wb, "GerarExcelBoletimAnual_");

    }
    
    public static void getExcelHistoricoAluno(XSSFWorkbook wb, XSSFSheet sheet, int idCurso, int idAluno) {
        
        Curso curso = CursoServer.getCurso(idCurso);
        if(curso.getEnsino().equals(Ensino.FUNDAMENTAL)){
            getExcelHistoricoAlunoFundamental(wb, sheet, idCurso, idAluno);
        }else if (curso.getEnsino().equals(Ensino.MEDIO)){
            getExcelHistoricoAlunoMedio(wb, sheet, idCurso, idAluno);
        }
    }
    
    
    public static void getExcelHistoricoAlunoFundamentalNew(XSSFWorkbook wb, XSSFSheet sheet, int idCurso, int idAluno) {
        
    }
    
    public static void getExcelHistoricoAlunoFundamental(XSSFWorkbook wb, XSSFSheet sheet, int idCurso, int idAluno) {
        
        
        sheet.setFitToPage(true);
        sheet.getPrintSetup().setLandscape(false);
        sheet.setMargin((short) 1, 1.5);
        ArrayList<ArrayList<String>> listHistorico = getHistoricoAluno(idCurso, idAluno);
        ArrayList<String> listHeader = listHistorico.get(0);
        Usuario aluno = UsuarioServer.getUsuarioPeloId(idAluno);        
        
        String strCidadeNascimento = (aluno.getCidadeNascimento()==null)?"":aluno.getCidadeNascimento();
        String strUFNascimento = (aluno.getUfNascimento()==null)?"":aluno.getUfNascimento();
        String strPaisNascimento = (aluno.getPaisNascimento()==null)?"":aluno.getPaisNascimento();
        
        
        String texto1 = "COLÉGIO INTEGRADO";
        String texto2 = "Ato de Criação da Escola : Portaria do Dirigente Regional de Ensino de 26/01 - Publicado no D.O.E. de 27/01/99";
        String texto3 = "DIRETORIA DE ENSINO DA REGIÃO CAMPINAS LESTE";
        String texto4 = "HISTÓRICO ESCOLAR - ENSINO FUNDAMENTAL";
        String texto5 = "Nome do Aluno: "+aluno.getPrimeiroNome().toUpperCase() + " " + aluno.getSobreNome().toUpperCase() ;
        String texto6 = "R.G.:"+aluno.getRg();        
        String texto7 = "Nascimento";
        String texto8 = "Município: "+strCidadeNascimento + "                    "+"Estado: "+strUFNascimento+"                    "+"País: "+strPaisNascimento;
        String texto9 = "Filiação: Omitida de acordo com a DEL. CEE-04/95 DOE de 13/06/95                   Data: "+aluno.getDataNascimento();
        String texto10 = "Fundamento Legal: Lei Federal 9394/96 Artigo 32,33 e 34; Resolução CNE/CEB nº 02/98; Res SEE/SP nº 11/2005, alterada pela Resolução SE nº 2/2006.  Lei Federal  11.114/2005 e Lei Federal 11.274/2006";
        String texto11 = "EM 2009-Matrícula Inicia no 2º Ano do Ensino Fundamental de 09 anos-Fundamentação Legal: Del.CEE 73/2008";
        String texto12 = "BASE NACIONAL COMUM";
//        String texto13 = "PARTE DIVERSIFICADA";
        
        int MAX_COLUMNS = 3;
        int intColumn = 2;
        int intColumnBackup = intColumn;
        int intLine = 0;

        Row row = sheet.createRow(intLine);
        row.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine + 1, 0, listHeader.size()+MAX_COLUMNS));
        row.getCell(0).setCellValue(texto1);
        row.getCell(0).setCellStyle(ExcelFramework.getStyleTitleBoletimAno(wb));
        
        intLine = intLine + 2;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue(texto2);
        row.getCell(0).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, 0, listHeader.size()+MAX_COLUMNS));

        intLine = intLine + 1;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue(texto3);
        row.getCell(0).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, 0, listHeader.size()+MAX_COLUMNS));
        
        intLine = intLine + 1;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue(texto4);
        row.getCell(0).setCellStyle(ExcelFramework.getStyleTitleBoletimAno(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, 0, listHeader.size()+MAX_COLUMNS));
        
        intLine = intLine + 1;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue(texto5);
        row.getCell(0).setCellStyle(ExcelFramework.getStyleCellFontBoldLeft(wb));
//        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, 0, listHeader.size()+MAX_COLUMNS-2));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet,new CellRangeAddress(intLine, intLine, 0, listHeader.size()+MAX_COLUMNS-2));
        row.createCell(listHeader.size()+MAX_COLUMNS-1).setCellValue(texto6);
        row.getCell(listHeader.size()+MAX_COLUMNS-1).setCellStyle(ExcelFramework.getStyleCellFontBoldLeft(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, listHeader.size()+MAX_COLUMNS-1, listHeader.size()+MAX_COLUMNS));

        
        intLine = intLine + 1;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue(texto7);
        row.getCell(0).setCellStyle(ExcelFramework.getStyleCellFontBoldLeft(wb));
//        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine+1, 0, 1));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine+1, 0, 1));
        row.createCell(2).setCellValue(texto8);
        row.getCell(2).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
//        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, 2, listHeader.size()+MAX_COLUMNS));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, 2, listHeader.size()+MAX_COLUMNS));
        intLine = intLine + 1;
        row = sheet.createRow(intLine);
        row.createCell(2).setCellValue(texto9);
        row.getCell(2).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
//        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, 2, listHeader.size()+MAX_COLUMNS));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, 2, listHeader.size()+MAX_COLUMNS));
        
        
        intLine = intLine + 1;
        
        for(int i=intLine;i<300;i++){
            sheet.createRow(i).createCell(0);
        }

       int rowInicioBaseNacionalComum = 0;
        
        
      //Criando Linhas de disciplinas e notas
        boolean isCreateAno = false;
        for (int i = 0; i < listHeader.size(); i++) {
            String header = "";            

            //Criando Colunas dos Anos
            if (i == 0) {
                row = sheet.getRow(intLine);
                row.createCell(1);
                header = "ÁREAS DE CONHECIMENTO";
                row.getCell(1).setCellValue(header);
                row.getCell(1).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
//                sheet.addMergedRegion(new CellRangeAddress(intLine, intLine+3, 1, 4));
                ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine+3, 1, 4));
                rowInicioBaseNacionalComum = intLine+4;
                
                row.createCell(intColumn+MAX_COLUMNS);
                row.getCell(intColumn+MAX_COLUMNS).setCellValue("ANO / PERÍODO LETIVO");
                row.getCell(intColumn+MAX_COLUMNS).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
//                sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS, listHeader.size()+MAX_COLUMNS));
                ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS, listHeader.size()+MAX_COLUMNS));
                intLine++;
                
                row = sheet.createRow(intLine);
                row.createCell(intColumn+MAX_COLUMNS);
                row.getCell(intColumn+MAX_COLUMNS).setCellValue("CICLO I");
                row.getCell(intColumn+MAX_COLUMNS).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
//                sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS, intColumn+MAX_COLUMNS+4));
                ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS, intColumn+MAX_COLUMNS+4));
                
                row.createCell(intColumn+MAX_COLUMNS+5);
                row.getCell(intColumn+MAX_COLUMNS+5).setCellValue("CICLO II");
                row.getCell(intColumn+MAX_COLUMNS+5).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
//                sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS+5, listHeader.size()+MAX_COLUMNS));
                ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS+5, listHeader.size()+MAX_COLUMNS));
                
                intLine++;                
                row = sheet.createRow(intLine);
                
                for(int iSerie=0;iSerie<9;iSerie++){                    
                    String strSerie="";
                    if(iSerie>0){
                        strSerie = Integer.toString(iSerie)+"ª Série";                     
                    }
                    row.createCell(intColumn+MAX_COLUMNS+iSerie);
                    row.getCell(intColumn+MAX_COLUMNS+iSerie).setCellValue(strSerie);
                    row.getCell(intColumn+MAX_COLUMNS+iSerie).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));                       
                }
                
                intLine = intLine+1;

            } 
            else {
                
                if(isCreateAno==false){
                    row = sheet.createRow(intLine);
                    isCreateAno=true;
                }
                
                header = listHeader.get(i);
                header = header.substring(header.indexOf(FieldVerifier.INI_SEPARATOR) + FieldVerifier.INI_SEPARATOR.length());
                
                row.createCell(intColumn + 2);
                row.getCell(intColumn + 2).setCellType(Cell.CELL_TYPE_STRING);                
                row.getCell(intColumn + 2).setCellValue(header);
                row.getCell(intColumn + 2).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
            }
            intColumn++;
        }
        
        
        intColumn = intColumnBackup;
        
        int intBNC = 0;
//        int intPD = 0;
        
        //Criando Linhas de disciplinas e notas
        
        intLine = intLine + 1;
        int intRowMergeAno1=intLine;
        
        int rowFinalBaseNacionalComum=0;
        
        //Adicionando Base Nacional Comum
        for (int r = 1; r < listHistorico.size(); r++) {
            ArrayList<String> listRow = listHistorico.get(r);
            row = sheet.createRow((short) intLine);
            String auxString = "";
            for (int c = 0; c < listRow.size(); c++) {
                String strText = listRow.get(c);
                String strDisc ="";
//                String isFalse ="";
                
                if (c == 0) {
                    strDisc = strText.substring(0,strText.indexOf(FieldVerifier.INI_SEPARATOR));
                    strText = strText.substring(strText.indexOf(FieldVerifier.INI_SEPARATOR)+FieldVerifier.INI_SEPARATOR.length());
                    auxString=strText;
//                    isFalse=auxString;
                }

                if (auxString.equals("true")) {
                    intBNC++;
                    if (c == 0) {
                        row.createCell((short) c + intColumn);
                        
                        strDisc = strDisc.substring(0, strDisc.lastIndexOf(")"));
                        strDisc = strDisc.substring(0, strDisc.lastIndexOf("("));

                        row.getCell((short) c + intColumn).setCellValue(strDisc);
                        row.getCell((short) c + intColumn).setCellType(Cell.CELL_TYPE_STRING);
                        row.getCell((short) c + intColumn).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
                        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn, intColumn + 2));
                    } else {
                        row.createCell((short) c + intColumn + 2);
                        if (FieldVerifier.isNumeric(strText)&& !strText.equals("false")) {
                            row.getCell((short) c + intColumn + 2).setCellValue(Double.parseDouble(strText));
                            row.getCell((short) c + intColumn + 2).setCellType(Cell.CELL_TYPE_NUMERIC);
                        } else {
                            if (strText == null || strText.isEmpty() ) {
                                strText = "-";
                            }
                            row.getCell((short) c + intColumn + 2).setCellValue(strText);
                            row.getCell((short) c + intColumn + 2).setCellType(Cell.CELL_TYPE_STRING);
                        }
                        row.getCell((short) c + intColumn + 2).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
                    }
                } else {
                    break;
                }
                
            }
            if (auxString.equals("true")) {
                intLine++;
            }
        }
        rowFinalBaseNacionalComum=intLine;
        
      //Adicionando PARTE DIVERSIFICADA
        for (int r = 1; r < listHistorico.size(); r++) {
            ArrayList<String> listRow = listHistorico.get(r);
            row = sheet.createRow((short) intLine);
            String auxString = "";
            for (int c = 0; c < listRow.size(); c++) {
                String strText = listRow.get(c);
                String strDisc ="";
                
                if (c == 0) {
                    strDisc = strText.substring(0,strText.indexOf(FieldVerifier.INI_SEPARATOR));
                    strText = strText.substring(strText.indexOf(FieldVerifier.INI_SEPARATOR)+FieldVerifier.INI_SEPARATOR.length());
                    auxString=strText;
                }

                if (auxString.equals("false")) {
                    intBNC++;
                    if (c == 0) {
                        row.createCell((short) c + intColumn);
                        
                        strDisc = strDisc.substring(0, strDisc.lastIndexOf(")"));
                        strDisc = strDisc.substring(0, strDisc.lastIndexOf("("));

                        row.getCell((short) c + intColumn).setCellValue(strDisc);
                        row.getCell((short) c + intColumn).setCellType(Cell.CELL_TYPE_STRING);
                        row.getCell((short) c + intColumn).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
                        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn, intColumn + 2));
                    } else {
                        row.createCell((short) c + intColumn + 2);
                        if (FieldVerifier.isNumeric(strText)) {
                            row.getCell((short) c + intColumn + 2).setCellValue(Double.parseDouble(strText));
                            row.getCell((short) c + intColumn + 2).setCellType(Cell.CELL_TYPE_NUMERIC);
                        } else {
                            if (strText == null || strText.isEmpty()) {
                                strText = "-";
                            }
                            row.getCell((short) c + intColumn + 2).setCellValue(strText);
                            row.getCell((short) c + intColumn + 2).setCellType(Cell.CELL_TYPE_STRING);
                        }
                        row.getCell((short) c + intColumn + 2).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
                    }
                } else {
                    break;
                }
                
            }
            if (auxString.equals("false")) {
                intLine++;
            }
        }
        
        
        row = sheet.getRow(rowInicioBaseNacionalComum);
        row.createCell(intColumn-1);
        row.getCell(intColumn-1).setCellValue("BASE NACIONAL COMUM");
        row.getCell(intColumn-1).setCellStyle(ExcelFramework.getStyleHeaderBoletimRotation90Center(wb, true));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(rowInicioBaseNacionalComum, rowFinalBaseNacionalComum-1, intColumn-1, intColumn-1));
        
        
        row = sheet.getRow(rowFinalBaseNacionalComum);
        row.createCell(intColumn-1);
        row.getCell(intColumn-1).setCellValue("PARTE DIVERSIFICADA");
        row.getCell(intColumn-1).setCellStyle(ExcelFramework.getStyleHeaderBoletimRotation90Center(wb, false));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(rowFinalBaseNacionalComum, intLine-1, intColumn-1, intColumn-1));
        
        
        row = sheet.createRow((short) intLine);
        row.createCell((short) intColumn-1);
        row.getCell((short) intColumn-1).setCellValue("Base Nacional Comum");
        row.getCell((short) intColumn-1).setCellStyle(ExcelFramework.getStyleCellFontBoldLeft(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn-1, intColumn+2));
        
        intLine = intLine+1;
        row = sheet.createRow(intLine);
        row.createCell(intColumn-1);
        row.getCell(intColumn-1).setCellValue("Parte Diversificada");
        row.getCell(intColumn-1).setCellStyle(ExcelFramework.getStyleCellFontBoldLeft(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn-1, intColumn+2));
        
        intLine = intLine+1;
        row = sheet.createRow((short) intLine);
        row.createCell((short) intColumn-1);
        row.getCell((short) intColumn-1).setCellValue("TOTAL DA CARGA HORÁRIA");
        row.getCell((short) intColumn-1).setCellStyle(ExcelFramework.getStyleCellFontBoldLeft(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn-1, intColumn+2));
        
        

        
        ArrayList<String> listCargaHorariaDisciplinaObrigatoria = getCargaHorariaAno(aluno, true);
        ArrayList<String> listCargaHorariaDisciplinaNaoObrigatoria = getCargaHorariaAno(aluno, false);
        
        
        for(int i = 1;i<listHeader.size();i++){
            String strHeader = listHeader.get(i);
            System.out.println("Ano:"+strHeader);
            //Andre
//            sheet.getRow(intLine-2).getCell(intColumn+2+i).setCellValue("-");
            String strBNC = getCargaHorariaDoArray(listCargaHorariaDisciplinaObrigatoria, strHeader);
            String strPD = getCargaHorariaDoArray(listCargaHorariaDisciplinaNaoObrigatoria, strHeader);
            String iBNC = "0";
            String iPD = "0";
            
            sheet.getRow(intLine-2).createCell(intColumn+2+i).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
            
            if (FieldVerifier.isNumeric(strBNC)) {
                sheet.getRow(intLine-2).getCell(intColumn+2+i).setCellValue(Double.parseDouble(strBNC));
                sheet.getRow(intLine-2).getCell(intColumn+2+i).setCellType(Cell.CELL_TYPE_NUMERIC);
                iBNC = strBNC;
            }else{
                sheet.getRow(intLine-2).getCell(intColumn+2+i).setCellType(Cell.CELL_TYPE_STRING);
                sheet.getRow(intLine-2).getCell(intColumn+2+i).setCellValue(strBNC);  
            }
            
            
            sheet.getRow(intLine-1).createCell(intColumn+2+i).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
            if (FieldVerifier.isNumeric(strPD)) {
                sheet.getRow(intLine-1).getCell(intColumn+2+i).setCellValue(Double.parseDouble(strPD));
                sheet.getRow(intLine-1).getCell(intColumn+2+i).setCellType(Cell.CELL_TYPE_NUMERIC);
                iPD = strPD;
            }else{
                sheet.getRow(intLine-1).getCell(intColumn+2+i).setCellType(Cell.CELL_TYPE_STRING);
                sheet.getRow(intLine-1).getCell(intColumn+2+i).setCellValue(strPD);        
            }
                
            
            int soma = Integer.parseInt(iBNC) + Integer.parseInt(iPD);
            if (soma > 0) {
                String strSoma = Integer.toString(soma);
                sheet.getRow(intLine).createCell(intColumn + 2 + i).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
                sheet.getRow(intLine).getCell(intColumn + 2 + i).setCellType(Cell.CELL_TYPE_NUMERIC);
                sheet.getRow(intLine).getCell(intColumn + 2 + i).setCellValue(Double.parseDouble(strSoma));
            } else {
                sheet.getRow(intLine).createCell(intColumn + 2 + i).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
                sheet.getRow(intLine).getCell(intColumn + 2 + i).setCellType(Cell.CELL_TYPE_STRING);
                sheet.getRow(intLine).getCell(intColumn + 2 + i).setCellValue("-");
            }


            
        }
        
        
        sheet.getRow(intRowMergeAno1).getCell(intColumn+3).setCellValue(texto11);
        CellRangeAddress regionAno1 = new CellRangeAddress(intRowMergeAno1, intLine, intColumn+3, intColumn+3);
        ExcelFramework.cleanBeforeMergeOnValidCells(sheet, regionAno1, ExcelFramework.getStyleHeaderBoletimRotation90Center(wb, false));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, regionAno1);
        
        if(intBNC>0){
            System.out.println("intRowMergeAno1:"+intRowMergeAno1);
            sheet.getRow(intRowMergeAno1).createCell(1).setCellValue("-");    
            sheet.getRow(intRowMergeAno1).getCell(1).setCellValue(texto12);
            sheet.getRow(intRowMergeAno1).getCell(1).setCellStyle(ExcelFramework.getStyleHeaderBoletimRotation90Center(wb, false));
        }
        
        intLine = intLine+1;
        
        sheet.getRow(intLine).setHeight( (short) 150 );
        sheet.getRow(intLine).createCell(1);
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, 1, listHeader.size()+MAX_COLUMNS));
        
        intLine = intLine+1;
        sheet.getRow(intLine).createCell(1).setCellValue("Estudos Realizados");
        sheet.getRow(intLine).getCell(1).setCellStyle(ExcelFramework.getStyleHeaderBoletimRotation90Center(wb, false));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine+listHeader.size()-2, 1, 1));
        
        sheet.getRow(intLine).createCell(2).setCellValue("Ano");
        sheet.getRow(intLine).getCell(2).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
        sheet.getRow(intLine).createCell(3).setCellValue("Período Letivo"); 
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, 3, 4));
        sheet.getRow(intLine).getCell(3).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
        sheet.getRow(intLine).createCell(5).setCellValue("Estabelecimento de Ensino");
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet,new CellRangeAddress(intLine, intLine, 5, 9));
        sheet.getRow(intLine).getCell(5).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
        sheet.getRow(intLine).createCell(10).setCellValue("Município");
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet,new CellRangeAddress(intLine, intLine, 10, 12));
        sheet.getRow(intLine).getCell(10).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
        sheet.getRow(intLine).createCell(13).setCellValue("UF");
        sheet.getRow(intLine).getCell(13).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
        
        
        // Aidcionando Periodo Letivo em Estudos realizados
        int intLineBackup = intLine;
        intLine++;
        for(int i=0;i<listHeader.size()-2;i++){
            if(i==0){
                sheet.getRow(intLine).createCell(3).setCellValue("1º Ano"); 
                ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine++, 3, 4));  
            }
            else{
                sheet.getRow(intLine).createCell(3).setCellValue((i+1)+"ª Série/"+(i+2)+"º Ano"); 
                ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine++, 3, 4));   
            }
            sheet.getRow(intLine-1).getCell(3).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
        }
        
        // Adicionando Estabelecimento de Ensino em Estudos realizados
        intLine = intLineBackup;
        intLine++;
        for (int i = 0; i < listHeader.size() - 2; i++) {
            ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine++, 5, 9));
        }

        //Adicionando Municipio de Ensino em Estudos realizados
        intLine = intLineBackup;
        intLine++;     
        for (int i = 0; i < listHeader.size() - 2; i++) {
            ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine++, 10, 12));
        }
        
        
        // Adicionando Texto do Ano de Ensino em Estudos realizados
        Curso curso = CursoServer.getCurso(idCurso);
        ArrayList<Curso> listCursosDesativados = CursoServer.getCursosPorAlunoAmbienteAluno(aluno, false);
        ArrayList<Curso> listCursos = new  ArrayList<Curso>();
        listCursos.add(curso);
        listCursos.addAll(listCursosDesativados);
        intLine = intLineBackup;
        intLine++;     
        for (int i = 1; i < listHeader.size() - 1; i++) {

            String strAno = listHeader.get(i);
            sheet.getRow(intLine).createCell(2).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
            sheet.getRow(intLine).createCell(13).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
            if (!strAno.isEmpty()) {
                strAno = strAno.substring(0, strAno.indexOf(FieldVerifier.INI_SEPARATOR));

                for (Curso rowCurso : listCursos) {
                    if (rowCurso.getAno().equals(strAno)) {
                        String strYear = MpUtilServer.convertDateToString(rowCurso.getDataInicial(), "yyyy");
                        int intYear = Integer.parseInt(strYear);         
                        //Ano
                        sheet.getRow(intLine-1).createCell(2).setCellType(Cell.CELL_TYPE_NUMERIC);                        
                        sheet.getRow(intLine-1).getCell(2).setCellValue(intYear);
                        sheet.getRow(intLine-1).getCell(2).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
                        //Estabelecimento
                        sheet.getRow(intLine-1).createCell(5).setCellValue(ConfigJornada.getProperty("config.nome.escola"));
                        sheet.getRow(intLine-1).getCell(5).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
                        //Municipio
                        String strMunicipio = aluno.getUnidadeEscola().getNomeUnidadeEscola();
                        strMunicipio = strMunicipio.substring(0,strMunicipio.indexOf("-"));
                        sheet.getRow(intLine-1).createCell(10).setCellValue(strMunicipio);
                        sheet.getRow(intLine-1).getCell(10).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
                        //UF
                        String strUF = aluno.getUnidadeEscola().getNomeUnidadeEscola();
                        strUF = strUF.substring(strUF.indexOf("-")+1,strUF.length());
                        sheet.getRow(intLine-1).createCell(13).setCellValue(strUF);
                        sheet.getRow(intLine-1).getCell(13).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
                    }
                }
            }            
            intLine++;
        }        
        
        //Adicionando merge com texto do Fundamento Legal
        sheet.getRow(8).getCell(0).setCellValue(texto10);
        CellRangeAddress region = new CellRangeAddress(8, intLine-1, 0, 0);
        ExcelFramework.cleanBeforeMergeOnValidCells(sheet, region, ExcelFramework.getStyleHeaderBoletimRotation90Center(wb, false));
        sheet.addMergedRegion(region);

        sheet.getRow(intLine).getCell(0).setCellValue("Observações:");
        sheet.getRow(intLine).getCell(0).setCellStyle(ExcelFramework.getStyleCellFontBoldLeftNoBorders(wb));
        sheet.addMergedRegion( new CellRangeAddress(intLine, intLine, 0, listHeader.size()+MAX_COLUMNS));

        intLine++;
        sheet.getRow(intLine).getCell(0).setCellValue("Nº de concluinte GDAE:");
        sheet.addMergedRegion( new CellRangeAddress(intLine, intLine, 0, listHeader.size()+MAX_COLUMNS));
        ExcelFramework.setRegionBorder(wb, sheet, new CellRangeAddress(intLine-1, intLine, 0, listHeader.size()+MAX_COLUMNS));
        
        
        intLine++;
        sheet.getRow(intLine).getCell(0).setCellValue("CERTIFICADO");
        sheet.getRow(intLine).getCell(0).setCellStyle(ExcelFramework.getStyleCellFontBoldCenterNoBorders(wb));
        sheet.addMergedRegion( new CellRangeAddress(intLine, intLine, 0, listHeader.size()+MAX_COLUMNS));
        intLine++;
        
        String strYear = MpUtilServer.convertDateToString(curso.getDataInicial(), "yyyy");
        String strCertificado1 = "O Diretor do "+ ConfigJornada.getProperty("config.nome.escola")+ " nos termos do Inciso VII, Artigo 24 da Lei Federal 9394/96, que ";
        String strCertificado2 = aluno.getPrimeiroNome() + " " + aluno.getSobreNome()+", RG:"+aluno.getRg()+", concluiu o "+curso.getNome() + ", no ano letivo de "+strYear+", ";
        String strCertificado3 = "estando apta a prosseguir seus estudos no ____________________________.";
        
        sheet.getRow(intLine).getCell(0).setCellValue(strCertificado1);
        sheet.getRow(intLine).getCell(0).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        sheet.addMergedRegion( new CellRangeAddress(intLine, intLine, 0, listHeader.size()+MAX_COLUMNS));
        
        intLine++;
        sheet.getRow(intLine).getCell(0).setCellValue(strCertificado2);
        sheet.getRow(intLine).getCell(0).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        sheet.addMergedRegion( new CellRangeAddress(intLine, intLine, 0, listHeader.size()+MAX_COLUMNS));
        
        intLine++;
        sheet.getRow(intLine).getCell(0).setCellValue(strCertificado3);
        sheet.getRow(intLine).getCell(0).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        sheet.addMergedRegion( new CellRangeAddress(intLine, intLine, 0, listHeader.size()+MAX_COLUMNS));
        ExcelFramework.setRegionBorder(wb, sheet, new CellRangeAddress(intLine-3, intLine, 0, listHeader.size()+MAX_COLUMNS));
        
        
        intLine++;
        intLine++;
        sheet.getRow(intLine).createCell(3).setCellValue("_____________________");sheet.getRow(intLine).getCell(3).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        sheet.getRow(intLine).createCell(6).setCellValue("_____________________");sheet.getRow(intLine).getCell(6).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        sheet.getRow(intLine).createCell(9).setCellValue("_____________________");sheet.getRow(intLine).getCell(9).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        intLine++;
        sheet.getRow(intLine).createCell(3).setCellValue("Data");sheet.getRow(intLine).getCell(3).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        sheet.getRow(intLine).createCell(6).setCellValue("Secretária");sheet.getRow(intLine).getCell(6).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        sheet.getRow(intLine).createCell(9).setCellValue("Diretor");sheet.getRow(intLine).getCell(9).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        

        
    }
    
    
    private static ArrayList<String> getCargaHorariaAno(Usuario usuario, boolean is_disciplina_obrigatoria) {
        ArrayList<String> listCargaHoraria = new ArrayList<String>();
        ArrayList<Curso> listCurso = CursoServer.getTodosCursosDoAluno(usuario);
        for (int iCurso = 0; iCurso < listCurso.size(); iCurso++) {
            Curso curso = listCurso.get(iCurso);
            ArrayList<Periodo> listPeriodo = PeriodoServer.getPeriodos(curso.getIdCurso());
            int cargaHoraria = 0;
            for (Periodo periodo : listPeriodo) {
                ArrayList<Disciplina> listDisciplina = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());
                for (Disciplina disciplina : listDisciplina) {
                    if(disciplina.isObrigatoria()==is_disciplina_obrigatoria){
                        cargaHoraria = cargaHoraria + disciplina.getCargaHoraria();
                    }                    
                }
            }
            listCargaHoraria.add(curso.getAno() + FieldVerifier.INI_SEPARATOR + Integer.toString(cargaHoraria));
            System.out.println(listCargaHoraria.get(iCurso));
            
        }
        return listCargaHoraria;
    }
    
    
    private static String getCargaHorariaDoArray(ArrayList<String> listCargaHoraria, String strAno){
        
        String strCargaHoraria="-";
        String strText1 = strAno.substring(0, strAno.indexOf(FieldVerifier.INI_SEPARATOR));
        for(int i = 0; i<listCargaHoraria.size();i++){
            String strText2 = listCargaHoraria.get(i);
            strText2 = strText2.substring(0, strText2.indexOf(FieldVerifier.INI_SEPARATOR));
            if(strText1.equals(strText2)){
                strCargaHoraria = listCargaHoraria.get(i).substring(strAno.lastIndexOf(">")+1);
            }
        }
        
        return strCargaHoraria;
    }
    
    public static void getExcelHistoricoAlunoMedio(XSSFWorkbook wb, XSSFSheet sheet, int idCurso, int idAluno) {
        
        
        sheet.setFitToPage(true);
        sheet.getPrintSetup().setLandscape(false);
        sheet.setMargin((short) 1, 1.5);
        ArrayList<ArrayList<String>> listHistorico = getHistoricoAluno(idCurso, idAluno);
        ArrayList<String> listHeader = listHistorico.get(0);
        Usuario aluno = UsuarioServer.getUsuarioPeloId(idAluno);        
        Curso curso = CursoServer.getCurso(idCurso);
        
        String strCidadeNascimento = (aluno.getCidadeNascimento()==null)?"":aluno.getCidadeNascimento();
        String strUFNascimento = (aluno.getUfNascimento()==null)?"":aluno.getUfNascimento();
        String strPaisNascimento = (aluno.getPaisNascimento()==null)?"":aluno.getPaisNascimento();        
        
                   
//        String texto10 = "Fundamento Legal: Lei Federal 9394/96 Artigo 32,33 e 34; Resolução CNE/CEB nº 02/98; Res SEE/SP nº 11/2005, alterada pela Resolução SE nº 2/2006.  Lei Federal  11.114/2005 e Lei Federal 11.274/2006";
//        String texto13 = "PARTE DIVERSIFICADA";
        
        int MAX_COLUMNS = 2;
        int intColumn = 2;
        int intColumnBackup = intColumn;
        int intLine = 0;
        
        int intCompleteSizeColumns = (listHeader.size()*2)+MAX_COLUMNS;

        Row row = sheet.createRow(intLine);
        row.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine + 1, 0, intCompleteSizeColumns));
        row.getCell(0).setCellValue("COLÉGIO INTEGRADO");
        row.getCell(0).setCellStyle(ExcelFramework.getStyleTitleBoletimAno(wb));
        
        intLine = intLine + 2;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue("Ato de Criação da Escola : Portaria do Dirigente Regional de Ensino de 26/01 - Publicado no D.O.E. de 27/01/99");
        row.getCell(0).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, 0, intCompleteSizeColumns));
        
        intLine = intLine + 1;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue("HISTÓRICO ESCOLAR - ENSINO MÉDIO");
        row.getCell(0).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, 0, intCompleteSizeColumns));        

        
        intLine = intLine + 1;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue("Aluno: "+aluno.getPrimeiroNome().toUpperCase() + " " + aluno.getSobreNome().toUpperCase());
        row.getCell(0).setCellStyle(ExcelFramework.getStyleCellFontBoldLeft(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet,new CellRangeAddress(intLine, intLine, 0, intCompleteSizeColumns));
        

        
        intLine = intLine + 1;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue("Data Nasc.: "+aluno.getDataNascimento());
        row.getCell(0).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, 0, 2));
        row.createCell(3).setCellValue("Município: "+strCidadeNascimento + "                    "+"Estado: "+strUFNascimento);
        row.getCell(3).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, 3, intCompleteSizeColumns));
        
        
        intLine = intLine + 1;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue("País: "+strPaisNascimento);
        row.getCell(0).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, 0, 2));
        row.createCell(3).setCellValue("R.G.:"+aluno.getRg());
        row.getCell(3).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, 3, intCompleteSizeColumns));
         
        
        intLine = intLine + 1;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue("Filiação: Omitida de acordo com a DEL. CEE-04/95 DOE de 13/06/95");
        row.getCell(0).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, 0, intCompleteSizeColumns));
        
        
        intLine = intLine + 1;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue("HISTÓRICO ESCOLAR - ENSINO MÉDIO");
        row.getCell(0).setCellStyle(ExcelFramework.getStyleTitleBoletimAno(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, 0, intCompleteSizeColumns));

        
        
        intLine = intLine + 1;
       
        
      //Criando Linhas de disciplinas e notas
//        boolean isCreateAno = false;
        
        String header = "";    
        row = sheet.createRow(intLine);        
        row.createCell(0);
        header = "Componente Curricular";
        row.getCell(0).setCellValue(header);
        row.getCell(0).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
        
        
        int intSerie=1;
        
        row = sheet.getRow(intLine);
        row.createCell(intColumn+MAX_COLUMNS+1);
        row.getCell(intColumn+MAX_COLUMNS+intSerie).setCellValue("1º Série");
        row.getCell(intColumn+MAX_COLUMNS+intSerie).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS+intSerie, intColumn+MAX_COLUMNS+intSerie+1));
        
        intSerie = intSerie+2;
        
        row = sheet.getRow(intLine);
        row.createCell(intColumn+MAX_COLUMNS+3);
        row.getCell(intColumn+MAX_COLUMNS+intSerie).setCellValue("2º Série");
        row.getCell(intColumn+MAX_COLUMNS+intSerie).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS+intSerie, intColumn+MAX_COLUMNS+intSerie+1));

        intSerie = intSerie+2;
        
        row = sheet.getRow(intLine);
        row.createCell(intColumn+MAX_COLUMNS+intSerie);
        row.getCell(intColumn+MAX_COLUMNS+intSerie).setCellValue("3º Série");
        row.getCell(intColumn+MAX_COLUMNS+intSerie).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS+intSerie, intColumn+MAX_COLUMNS+intSerie+1));

        
        intSerie = 1;
        intLine++;
        row = sheet.createRow(intLine);  
        row.createCell(intColumn+MAX_COLUMNS+intSerie);
        row.getCell(intColumn+MAX_COLUMNS+intSerie).setCellValue("Nota");
        row.getCell(intColumn+MAX_COLUMNS+intSerie).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS+intSerie, intColumn+MAX_COLUMNS+intSerie));
        row.createCell(intColumn+MAX_COLUMNS+intSerie+1);
        row.getCell(intColumn+MAX_COLUMNS+intSerie+1).setCellValue("C.H.");
        row.getCell(intColumn+MAX_COLUMNS+intSerie+1).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS+intSerie+1, intColumn+MAX_COLUMNS+intSerie+1));
        
        //Merge and add border on cell "Componente Curricular"
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine-1, intLine, 0, intColumn+MAX_COLUMNS));
        intSerie = intSerie+2;
        
        row.createCell(intColumn+MAX_COLUMNS+intSerie);
        row.getCell(intColumn+MAX_COLUMNS+intSerie).setCellValue("Nota");
        row.getCell(intColumn+MAX_COLUMNS+intSerie).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS+intSerie, intColumn+MAX_COLUMNS+intSerie));
        row.createCell(intColumn+MAX_COLUMNS+intSerie+1);
        row.getCell(intColumn+MAX_COLUMNS+intSerie+1).setCellValue("C.H.");
        row.getCell(intColumn+MAX_COLUMNS+intSerie+1).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS+intSerie+1, intColumn+MAX_COLUMNS+intSerie+1));        
        
        intSerie = intSerie+2;
        
        row.createCell(intColumn+MAX_COLUMNS+intSerie);
        row.getCell(intColumn+MAX_COLUMNS+intSerie).setCellValue("Nota");
        row.getCell(intColumn+MAX_COLUMNS+intSerie).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS+intSerie, intColumn+MAX_COLUMNS+intSerie));
        row.createCell(intColumn+MAX_COLUMNS+intSerie+1);
        row.getCell(intColumn+MAX_COLUMNS+intSerie+1).setCellValue("C.H.");
        row.getCell(intColumn+MAX_COLUMNS+intSerie+1).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS+intSerie+1, intColumn+MAX_COLUMNS+intSerie+1));        
                
     
        intColumn = intColumnBackup;
        
        int intBNC = 0;

        
        ArrayList<Curso> listCurso = getCursoHistorical(idCurso, idAluno);
        
       
        //Criando Linhas de disciplinas e notas
        ///////////////////INICIO BASE NACIONAL COMUM///////////////////        
        intLine = intLine + 1;
        int intRowMergeAno1=intLine;
        for (int r = 1; r < listHistorico.size(); r++) {
            ArrayList<String> listRow = listHistorico.get(r);
            row = sheet.createRow((short) intLine);
            
            String auxString = "";
            
            int intJumpColumn=2;
//            String strDisc="";
            String strDisc ="";
            for (int c = 0; c < listRow.size(); c++) {
                
                String strText = listRow.get(c);
                
//                String isFalse ="";
                
                if (c == 0) {
                    strDisc = strText.substring(0,strText.indexOf(FieldVerifier.INI_SEPARATOR));
                    strText = strText.substring(strText.indexOf(FieldVerifier.INI_SEPARATOR)+FieldVerifier.INI_SEPARATOR.length());
                    auxString=strText;
//                    isFalse=auxString;
                }
                
                if (auxString.equals("true")) {
                    intBNC++;
                    if (c == 0) {

                        row.createCell((short) c + intColumn - 1);
                        strDisc = strDisc.substring(0, strDisc.lastIndexOf(")"));
                        strDisc = strDisc.substring(0, strDisc.lastIndexOf("("));

                        row.getCell((short) c + intColumn - 1).setCellValue(strDisc);
                        row.getCell((short) c + intColumn - 1).setCellType(Cell.CELL_TYPE_STRING);
                        row.getCell((short) c + intColumn - 1).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
                        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn - 1, intColumn + 2));
                    } else {
                        

                        row.createCell((short) c + intColumn + intJumpColumn);
                        row.createCell((short) c + intColumn + intJumpColumn + 1);
                        Double doubleCargaHoraria = 0.0;

                        if (FieldVerifier.isNumeric(strText)) {
                            // Nota
                            row.getCell((short) c + intColumn + intJumpColumn).setCellValue(Double.parseDouble(strText));
                            row.getCell((short) c + intColumn + intJumpColumn).setCellType(Cell.CELL_TYPE_NUMERIC);
                            doubleCargaHoraria = Double.parseDouble(getCargaHorarioEnsinoMedio(listCurso, idCurso, strDisc));

                        } else {
                            if (strText == null || strText.isEmpty()) {
                                strText = "-";
                                strDisc="";
                            }
                            
                            // Nota
                            row.getCell((short) c + intColumn + intJumpColumn).setCellValue(strText);
                            row.getCell((short) c + intColumn + intJumpColumn).setCellType(Cell.CELL_TYPE_STRING);

                        }                        

                        
                        // Nota
                        // C.H.
                        row.getCell((short) c + intColumn + intJumpColumn + 1).setCellValue(doubleCargaHoraria);
                        row.getCell((short) c + intColumn + intJumpColumn + 1).setCellType(Cell.CELL_TYPE_NUMERIC);
                        row.getCell((short) c + intColumn + intJumpColumn).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
                        // C.H.
                        row.getCell((short) c + intColumn + intJumpColumn + 1).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
                        intJumpColumn = intJumpColumn + 1;
                    }
                } else {
                    break;
                }
                
            }
            if (auxString.equals("true")) {
                intLine++;
            }
        }
        
        
        row = sheet.createRow((short) intLine);
        row.createCell((short) intColumn-1);
        row.getCell((short) intColumn-1).setCellValue("Base Nacional Comum");
        row.getCell((short) intColumn-1).setCellStyle(ExcelFramework.getStyleCellFontBoldLeft(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn-1, intColumn+2));
        
        
        intLine = intLine+1;
        row = sheet.createRow((short) intLine);
        row.createCell((short) intColumn-1);
        row.getCell((short) intColumn-1).setCellValue("Total da Carga Horária");
        row.getCell((short) intColumn-1).setCellStyle(ExcelFramework.getStyleCellFontBoldLeft(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn-1, intColumn+2));
        
        
        ArrayList<String> listCargaHorariaDisciplinaObrigatoria = getCargaHorariaAno(aluno, true);
        ArrayList<String> listCargaHorariaDisciplinaNaoObrigatoria = getCargaHorariaAno(aluno, false);

        for(int i = 1;i<listHeader.size()+intColumn+1;i=i+2){
            
            sheet.getRow(intLine-1).createCell(intColumn+2+i).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
            sheet.getRow(intLine-1).getCell(intColumn+2+i).setCellValue("-");
            ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine-1, intLine-1, intColumn+2+i, intColumn+3+i));     
      

        }
        
        
        //Ensino Médio
        
        int moveColumn=3;
        for (int i = 1; i < listHeader.size(); i++) {
            String strHeader = listHeader.get(i);

            String strBNC = getCargaHorariaDoArray(listCargaHorariaDisciplinaObrigatoria, strHeader);
            
            if (FieldVerifier.isNumeric(strBNC)) {
                sheet.getRow(intLine).createCell(intColumn + moveColumn).setCellValue(Double.parseDouble(strBNC));
                sheet.getRow(intLine).getCell(intColumn + moveColumn).setCellType(Cell.CELL_TYPE_NUMERIC);
                ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn + moveColumn , intColumn + moveColumn+1));
                sheet.getRow(intLine).getCell(intColumn + moveColumn).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
            } else {            
                sheet.getRow(intLine).createCell(intColumn + moveColumn).setCellValue(strBNC);
                sheet.getRow(intLine).getCell(intColumn + moveColumn).setCellType(Cell.CELL_TYPE_STRING);
                sheet.getRow(intLine).getCell(intColumn + moveColumn).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
                ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn + moveColumn , intColumn + moveColumn+1));
                
            }
            
            moveColumn = moveColumn+2;

        }
        
        
        if(intBNC>0){
            sheet.getRow(intRowMergeAno1).createCell(0).setCellValue("-");    
            sheet.getRow(intRowMergeAno1).getCell(0).setCellValue("BASE NACIONAL COMUM");
            ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intRowMergeAno1, intLine, 0, 0));
            sheet.getRow(intRowMergeAno1).getCell(0).setCellStyle(ExcelFramework.getStyleHeaderBoletimRotation90Center(wb, false));
        }

        
        ///////////////////INICIO PARTE DIVERSIFICADA//////////////////        
        intLine = intLine + 1;
        intRowMergeAno1=intLine;
        for (int r = 1; r < listHistorico.size(); r++) {
            ArrayList<String> listRow = listHistorico.get(r);
            row = sheet.createRow((short) intLine);
            
            String auxString = "";
            
            int intJumpColumn=2;
//            String strDisc="";
            
            for (int c = 0; c < listRow.size(); c++) {
                
                String strText = listRow.get(c);
                String strDisc ="";
//                String isFalse ="";
                
                if (c == 0) {
                    strDisc = strText.substring(0,strText.indexOf(FieldVerifier.INI_SEPARATOR));
                    strText = strText.substring(strText.indexOf(FieldVerifier.INI_SEPARATOR)+FieldVerifier.INI_SEPARATOR.length());
                    auxString=strText;
//                    isFalse=auxString;    
                }
                
                if (auxString.equals("false")) {
                    intBNC++;
                    if (c == 0) {

                        row.createCell((short) c + intColumn - 1);
                        strDisc = strDisc.substring(0, strDisc.lastIndexOf(")"));
                        strDisc = strDisc.substring(0, strDisc.lastIndexOf("("));
                        
//                        strDisc = strText.substring(strText.indexOf(FieldVerifier.INI_SEPARATOR) + FieldVerifier.INI_SEPARATOR.length());
//                        if (strDisc.equals("true")) {
//                            intBNC++;
//                        }

//                        strText = strText.substring(0, strText.indexOf(FieldVerifier.INI_SEPARATOR));
//                        strDisc = strText;
                        row.getCell((short) c + intColumn - 1).setCellValue(strDisc);
                        row.getCell((short) c + intColumn - 1).setCellType(Cell.CELL_TYPE_STRING);
                        row.getCell((short) c + intColumn - 1).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
                        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn - 1, intColumn + 2));
                    } else {
                        row.createCell((short) c + intColumn + intJumpColumn);
                        row.createCell((short) c + intColumn + intJumpColumn + 1);
                        Double doubleCargaHoraria = Double.parseDouble(getCargaHorarioEnsinoMedio(listCurso, idCurso, strDisc));

                        if (FieldVerifier.isNumeric(strText)) {
                            // Nota
                            row.getCell((short) c + intColumn + intJumpColumn).setCellValue(Double.parseDouble(strText));
                            row.getCell((short) c + intColumn + intJumpColumn).setCellType(Cell.CELL_TYPE_NUMERIC);

                        } else {
                            if (strText == null || strText.isEmpty()) {
                                strText = "-";
                            }
                            // Nota
                            row.getCell((short) c + intColumn + intJumpColumn).setCellValue(strText);
                            row.getCell((short) c + intColumn + intJumpColumn).setCellType(Cell.CELL_TYPE_STRING);

                        }
                        // Nota
                        // C.H.
                        row.getCell((short) c + intColumn + intJumpColumn + 1).setCellValue(doubleCargaHoraria);
                        row.getCell((short) c + intColumn + intJumpColumn + 1).setCellType(Cell.CELL_TYPE_NUMERIC);
                        row.getCell((short) c + intColumn + intJumpColumn).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
                        // C.H.
                        row.getCell((short) c + intColumn + intJumpColumn + 1).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
                        intJumpColumn = intJumpColumn + 1;
                    }
                } else {
                    break;
                }
                
            }
            if (auxString.equals("false")) {
                intLine++;
            }
        }
        
        
        row = sheet.createRow((short) intLine);
        row.createCell((short) intColumn-1);
        row.getCell((short) intColumn-1).setCellValue("Parte Diversificada");
        row.getCell((short) intColumn-1).setCellStyle(ExcelFramework.getStyleCellFontBoldLeft(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn-1, intColumn+2));
        
        
        intLine = intLine+1;
        row = sheet.createRow((short) intLine);
        row.createCell((short) intColumn-1);
        row.getCell((short) intColumn-1).setCellValue("Total da Carga Horária");
        row.getCell((short) intColumn-1).setCellStyle(ExcelFramework.getStyleCellFontBoldLeft(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn-1, intColumn+2));
        

        for(int i = 1;i<listHeader.size()+intColumn+1;i=i+2){
            
            sheet.getRow(intLine-1).createCell(intColumn+2+i).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
            sheet.getRow(intLine-1).getCell(intColumn+2+i).setCellValue("-");
            ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine-1, intLine-1, intColumn+2+i, intColumn+3+i));
        }
        
        if(intBNC>0){
            sheet.getRow(intRowMergeAno1).createCell(0).setCellValue("-");    
            sheet.getRow(intRowMergeAno1).getCell(0).setCellValue("PARTE DIVERSIFICADA");
            ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intRowMergeAno1, intLine, 0, 0));
            sheet.getRow(intRowMergeAno1).getCell(0).setCellStyle(ExcelFramework.getStyleHeaderBoletimRotation90Center(wb, false));
        }        
        
        
        //Ensino Médio
        
        moveColumn=3;
        for (int i = 1; i < listHeader.size(); i++) {
            String strHeader = listHeader.get(i);
            System.out.println("Ano:" + strHeader);

            String strPD = getCargaHorariaDoArray(listCargaHorariaDisciplinaNaoObrigatoria, strHeader);
            
            if (FieldVerifier.isNumeric(strPD)) {
                sheet.getRow(intLine).createCell(intColumn + moveColumn).setCellValue(Double.parseDouble(strPD));
                sheet.getRow(intLine).getCell(intColumn + moveColumn).setCellType(Cell.CELL_TYPE_NUMERIC);
                ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn + moveColumn , intColumn + moveColumn+1));
                sheet.getRow(intLine).getCell(intColumn + moveColumn).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
            } else {            
                sheet.getRow(intLine).createCell(intColumn + moveColumn).setCellValue(strPD);
                sheet.getRow(intLine).getCell(intColumn + moveColumn).setCellType(Cell.CELL_TYPE_STRING);
                sheet.getRow(intLine).getCell(intColumn + moveColumn).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
                ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn + moveColumn , intColumn + moveColumn+1));
                
            }
            
            moveColumn = moveColumn+2;

        }            
        
        
        intLine++;
        String strCargaHorariaTotal = "Carga Horária Total";
        sheet.createRow(intLine).createCell(0).setCellValue(strCargaHorariaTotal);
        sheet.getRow(intLine).getCell(0).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, 0, intColumn+MAX_COLUMNS));
        
        intLine++;
        //Linha
        sheet.createRow(intLine).setHeight( (short) 150 );
        sheet.getRow(intLine).createCell(1);
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, 0,  intColumn+MAX_COLUMNS+intSerie+1));
        
        for (int i = 1; i < listHeader.size() + intColumn + 1; i = i + 2) {

            sheet.getRow(intLine - 1).createCell(intColumn + 2 + i).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
            sheet.getRow(intLine - 1).getCell(intColumn + 2 + i).setCellValue("-");
            ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine - 1, intLine - 1, intColumn + 2 + i, intColumn + 3 + i));
        }
        
        
        intLine++;
        intRowMergeAno1 = intLine;
        

        sheet.createRow(intLine).createCell(intColumn+MAX_COLUMNS+1).setCellValue("Série / Termo");
        sheet.getRow(intLine).getCell(intColumn+MAX_COLUMNS+1).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS+1, intColumn+MAX_COLUMNS+1));
        
        sheet.getRow(intLine).createCell(intColumn+MAX_COLUMNS+2).setCellValue("Ano");
        sheet.getRow(intLine).getCell(intColumn+MAX_COLUMNS+2).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS+2, intColumn+MAX_COLUMNS+2));
        
        sheet.getRow(intLine).createCell(intColumn+MAX_COLUMNS+3).setCellValue("Estabelecimento de Ensino");
        sheet.getRow(intLine).getCell(intColumn+MAX_COLUMNS+3).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS+3, intColumn+MAX_COLUMNS+4));
        
        sheet.getRow(intLine).createCell(intColumn+MAX_COLUMNS+5).setCellValue("Município / UF");
        sheet.getRow(intLine).getCell(intColumn+MAX_COLUMNS+5).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+MAX_COLUMNS+5, intColumn+MAX_COLUMNS+6));
        
        intLine++;
        
        // Adicionando Periodo Letivo em ENSINO Fundamental
        int intLineBackupEnsinoMedio = intLine;
        for(int i=1;i<10;i++){

                sheet.createRow(intLine).createCell(intColumn+3).setCellValue((i+"ª"));  
                sheet.getRow(intLine).getCell(intColumn+3).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
                ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+3, intColumn+3   ));  
                intLine++;
        }   
        
        
        sheet.getRow(intLineBackupEnsinoMedio-1).createCell(1).setCellValue("Ensino Fundamental");
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLineBackupEnsinoMedio-1, intLine-1, intColumn-1, intColumn+2));
        sheet.getRow(intLineBackupEnsinoMedio-1).getCell(1).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));  
        
        
        
        ArrayList<Curso> listCursosDesativados = CursoServer.getCursosPorAlunoAmbienteAluno(aluno, false);
        ArrayList<Curso> listCursos = new  ArrayList<Curso>();
        listCursos.add(curso);
        listCursos.addAll(listCursosDesativados);
        
        intLine = intLineBackupEnsinoMedio;
        
        for (int i = 1; i < 10; i++) {
            String strAno = Integer.toString(i)+"ano";
          //Ano
            sheet.getRow(intLine).createCell(intColumn+4).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
            ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+4, intColumn+4));
            //Estabelecimento
            sheet.getRow(intLine).createCell(intColumn+5).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
            ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+5, intColumn+6));
            //Municipio
            sheet.getRow(intLine).createCell(intColumn+7).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
            ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+7, intColumn+8));
            
            for (Curso rowCurso : listCursos) {
                if (rowCurso.getAno().equals(strAno) && rowCurso.getEnsino().equals("Ensino Fundamental")) {
                    String strYear = MpUtilServer.convertDateToString(rowCurso.getDataInicial(), "yyyy");
                    int intYear = Integer.parseInt(strYear);         
                    //Ano
                    sheet.getRow(intLine).getCell(intColumn+4).setCellType(Cell.CELL_TYPE_NUMERIC);                        
                    sheet.getRow(intLine).getCell(intColumn+4).setCellValue(intYear);
                    //Estabelecimento
                    sheet.getRow(intLine).getCell(intColumn+5).setCellValue(ConfigJornada.getProperty("config.nome.escola"));
                    //Municipio
                    String strMunicipio = aluno.getUnidadeEscola().getNomeUnidadeEscola();
                    sheet.getRow(intLine).getCell(intColumn+7).setCellValue(strMunicipio);

                }
            }
            
            intLine++;
        }        
        

        sheet.createRow(intLine).setHeight( (short) 150 );
        sheet.getRow(intLine).createCell(1);
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, 1,  intColumn+MAX_COLUMNS+intSerie+1));
        
        
        // Adicionando Periodo Letivo em ENSINO MEDIO
        intLine++;
        intLineBackupEnsinoMedio = intLine;
        for(int i=1;i<listHeader.size();i++){

                sheet.createRow(intLine).createCell(intColumn+3).setCellValue((i+"ª"));  
                sheet.getRow(intLine).getCell(intColumn+3).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
                ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+3, intColumn+3   ));  
                intLine++;

        }
        intLine = intLineBackupEnsinoMedio;
        
        

        
        for (int i = 1; i < listHeader.size(); i++) {

            String strAno = listHeader.get(i);

            //Ano
            sheet.getRow(intLine).createCell(intColumn+4).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
            ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+4, intColumn+4));
            //Estabelecimento
            sheet.getRow(intLine).createCell(intColumn+5).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
            ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+5, intColumn+6));
            //Municipio
            sheet.getRow(intLine).createCell(intColumn+7).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
            ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, intColumn+7, intColumn+8));
            
            if (!strAno.isEmpty()) {
                strAno = strAno.substring(0, strAno.indexOf(FieldVerifier.INI_SEPARATOR));

                for (Curso rowCurso : listCursos) {
                    if (rowCurso.getAno().equals(strAno) && rowCurso.getEnsino().equals("Ensino Médio")) {
                        String strYear = MpUtilServer.convertDateToString(rowCurso.getDataInicial(), "yyyy");
                        int intYear = Integer.parseInt(strYear);         
                        //Ano
                        sheet.getRow(intLine).getCell(intColumn+4).setCellType(Cell.CELL_TYPE_NUMERIC);                        
                        sheet.getRow(intLine).getCell(intColumn+4).setCellValue(intYear);
                        //Estabelecimento
                        sheet.getRow(intLine).getCell(intColumn+5).setCellValue(ConfigJornada.getProperty("config.nome.escola"));
                        //Municipio
                        String strMunicipio = aluno.getUnidadeEscola().getNomeUnidadeEscola();
                        sheet.getRow(intLine).getCell(intColumn+7).setCellValue(strMunicipio);

                    }
                }
                
            }    
            
            intLine++;
        }                
        
        intLine = intLineBackupEnsinoMedio;
        sheet.getRow(intLine).createCell(1).setCellValue("Ensino Médio");
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine+listHeader.size()-2, intColumn-1, intColumn+2));
        sheet.getRow(intLine).getCell(1).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb)); 
        
        intLine++;
        sheet.getRow(intRowMergeAno1).createCell(0).setCellValue("Estudos Realizados");
        sheet.getRow(intRowMergeAno1).getCell(0).setCellStyle(ExcelFramework.getStyleHeaderBoletimRotation90Center(wb, false));
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intRowMergeAno1, intLine+1, 0, 0));  
        
        intLine++;
        intLine++;
        //Linha
        sheet.createRow(intLine).setHeight( (short) 150 );
        sheet.getRow(intLine).createCell(1);
        ExcelFramework.setMergeRegionAndSetBorders(wb, sheet, new CellRangeAddress(intLine, intLine, 0,  intColumn+MAX_COLUMNS+intSerie+1));

        intLine++;
        sheet.createRow(intLine).createCell(0).setCellValue("CERTIFICADO");
        sheet.getRow(intLine).getCell(0).setCellStyle(ExcelFramework.getStyleCellFontBoldCenterNoBorders(wb));
        sheet.addMergedRegion( new CellRangeAddress(intLine, intLine, 0, intColumn+MAX_COLUMNS+intSerie+1));
        intLine++;
        
        String strYear = MpUtilServer.convertDateToString(curso.getDataInicial(), "yyyy");
        String strCertificado1 = "O Diretor do "+ ConfigJornada.getProperty("config.nome.escola")+ " nos termos do Inciso VII, Artigo 24 da Lei Federal 9394/96, que ";
        String strCertificado2 = aluno.getPrimeiroNome() + " " + aluno.getSobreNome()+", RG:"+aluno.getRg()+", concluiu o "+curso.getNome() + ", no ano letivo de "+strYear+", ";
        String strCertificado3 = "estando apta a prosseguir seus estudos no ____________________________.";
        
        sheet.createRow(intLine).createCell(0).setCellValue(strCertificado1);
        sheet.getRow(intLine).getCell(0).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        sheet.addMergedRegion( new CellRangeAddress(intLine, intLine, 0, intColumn+MAX_COLUMNS+intSerie+1));
        
        intLine++;
        sheet.createRow(intLine).createCell(0).setCellValue(strCertificado2);
        sheet.getRow(intLine).getCell(0).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        sheet.addMergedRegion( new CellRangeAddress(intLine, intLine, 0, intColumn+MAX_COLUMNS+intSerie+1));
        
        intLine++;
        sheet.createRow(intLine).createCell(0).setCellValue(strCertificado3);
        sheet.getRow(intLine).getCell(0).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        sheet.addMergedRegion( new CellRangeAddress(intLine, intLine, 0, intColumn+MAX_COLUMNS+intSerie+1));
//        ExcelFramework.setRegionBorder(wb, sheet, new CellRangeAddress(intLine-3, intLine, 0, intColumn+MAX_COLUMNS+intSerie+1));
        
        
        intLine++;
        intLine++;
        sheet.createRow(intLine).createCell(3).setCellValue("_____________________");sheet.getRow(intLine).getCell(3).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        sheet.getRow(intLine).createCell(6).setCellValue("_____________________");sheet.getRow(intLine).getCell(6).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        sheet.getRow(intLine).createCell(9).setCellValue("_____________________");sheet.getRow(intLine).getCell(9).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        intLine++;
        sheet.createRow(intLine).createCell(3).setCellValue("Data");sheet.getRow(intLine).getCell(3).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        sheet.getRow(intLine).createCell(6).setCellValue("Secretária");sheet.getRow(intLine).getCell(6).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        sheet.getRow(intLine).createCell(9).setCellValue("Diretor");sheet.getRow(intLine).getCell(9).setCellStyle(ExcelFramework.getStyleCellFontCenterNoBorders(wb));
        


        
    }
    
    
    
    

    public static ArrayList<Curso> getCursoHistorical(int idCurso, int idAluno){
        
        Usuario aluno = UsuarioServer.getUsuarioPeloId(idAluno);
        ArrayList<Curso> listCursos = new ArrayList<Curso>();
        Curso cursoAtual = CursoServer.getCurso(idCurso);
        ArrayList<Curso> listCursosAtivados = new ArrayList<Curso>();
        ArrayList<Curso> listCursosDesativados = CursoServer.getCursosPorAlunoAmbienteAluno(aluno, false);
        
        listCursosAtivados.add(cursoAtual);
        
        listCursos.addAll(listCursosAtivados);
        listCursos.addAll(listCursosDesativados);
        
        
        for(Curso curso : listCursos){
            ArrayList<Periodo> listPeriodos = PeriodoServer.getPeriodos(curso.getIdCurso());
            curso.setListPeriodos(listPeriodos);
            
            for (Periodo periodo : listPeriodos) {
                ArrayList<Disciplina> listDisciplinas = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());
                periodo.setListDisciplinas(listDisciplinas);
                
            }
        }
        
        return listCursos;
    }
    
    public static String getCargaHorarioEnsinoMedio(ArrayList<Curso> listCurso, int idCurso, String strNomeDisciplina){
                
        int intCargaHoraria=0;
        
        for(Curso curso : listCurso){
            
            if(curso.getIdCurso() == idCurso){
                ArrayList<Periodo> listPeriodos = curso.getListPeriodos();
                for(Periodo periodo : listPeriodos){
                    ArrayList<Disciplina> listDisciplinas = periodo.getListDisciplinas();
                    for(Disciplina disciplina : listDisciplinas){
                        if(disciplina.getNome().equals(strNomeDisciplina)){
                            intCargaHoraria = intCargaHoraria + disciplina.getCargaHoraria();
                        }
                    }
                }
            }
        }       
        
        return Integer.toString(intCargaHoraria);
    }
    
   
    public static String getExcelBoletimAluno(XSSFWorkbook wb, XSSFSheet sheet, int idCurso, int idAluno) {
//        XSSFWorkbook wb = new XSSFWorkbook();
//        XSSFSheet sheet = wb.createSheet("Boletim Anual");
        sheet.setFitToPage(true);
        sheet.getPrintSetup().setLandscape(false);
        sheet.setMargin((short) 1, 1.5);
        

        ArrayList<ArrayList<String>> listNotas = getBoletimAluno(idCurso, idAluno);
        ArrayList<String> listHeader = listNotas.get(0);

        Curso curso = CursoServer.getCurso(idCurso);
        Usuario aluno = UsuarioServer.getUsuarioPeloId(idAluno);

        String texto1 = "BOLETIM DO ALUNO";
        String texto2 = "Curso : "+ curso.getNome().toUpperCase();
        String texto3 = "Nome do Aluno: "+aluno.getPrimeiroNome().toUpperCase() + " " + aluno.getSobreNome().toUpperCase() ;
        int intColumn = 0;
        int intLine = 0;

        Row row = sheet.createRow(intLine);
        row.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine + 2, 0, listHeader.size()-1));
        row.getCell(0).setCellValue(texto1);
        row.getCell(0).setCellStyle(ExcelFramework.getStyleTitleBoletimAno(wb));

        intLine = intLine + 3;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue(texto2);
        row.getCell(0).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, 0, listHeader.size()-1));
        
        
        intLine++;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue(texto3);
        row.getCell(0).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, 0, listHeader.size()-1));

        intLine++;
        row = sheet.createRow(intLine);

        HashSet<String> hashPeriodos = new HashSet<String>();
        for (int i = 1; i < listHeader.size() - 3; i++) {
            String strHeaderText = listHeader.get(i);

            String strPeriodo = strHeaderText.substring(strHeaderText.indexOf("[") + 1, strHeaderText.indexOf("]"));

            if (strPeriodo != null && !strPeriodo.isEmpty()) {
                hashPeriodos.add(strPeriodo);
            }

        }
        hashPeriodos.add("Total");

        ArrayList<String> listPeriodosNome = new ArrayList<String>(hashPeriodos);
        Collections.sort(listPeriodosNome);

        int column = 1;
        for (int i = 0; i < listPeriodosNome.size(); i++) {

            row.createCell(column).setCellValue(listPeriodosNome.get(i));
            row.getCell(column).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
            sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, column, column + 2));
            column = column + 3;

        }

        intLine++;
        row = sheet.createRow(intLine++);
        for (int i = 0; i < listHeader.size(); i++) {
            String header = "";
            row.createCell(intColumn);
            row.getCell(intColumn).setCellType(Cell.CELL_TYPE_STRING);

            if (i == 0) {

                header = listHeader.get(i);
                row.getCell(intColumn).setCellValue(header.toUpperCase());
                row.getCell(intColumn).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));

            } else {
                header = listHeader.get(i);
                header = header.substring(header.indexOf("]") + 1, header.length());
                row.getCell(intColumn).setCellValue(header);
                row.getCell(intColumn).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));

            }
            //
            intColumn++;
        }

        for (int r = 1; r < listNotas.size(); r++) {
            ArrayList<String> listRow = listNotas.get(r);
            row = sheet.createRow((short) intLine++);
            for (int c = 0; c < listRow.size(); c++) {
                String strText = listRow.get(c);
                row.createCell((short) c);

                if (c == 0) {
                    row.getCell((short) c).setCellValue(strText);
                    row.getCell((short) c).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell((short) c).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
                } else {
                    if (FieldVerifier.isNumeric(strText)) {
//                        row.getCell((short) c).setCellValue(Double.parseDouble(strText));
//                        row.getCell((short) c).setCellType(Cell.CELL_TYPE_NUMERIC);
                        double nota = Double.parseDouble(strText);
                        double mediaCurso = Double.parseDouble(curso.getMediaNota());
                        String strSinal = "";
                        if (nota < mediaCurso) {
                            strSinal = "*";
                            row.getCell((short) c).setCellValue(strSinal + strText);
                            row.getCell((short) c).setCellType(Cell.CELL_TYPE_STRING);
                        } else {
                            row.getCell((short) c).setCellValue(nota);
                            row.getCell((short) c).setCellType(Cell.CELL_TYPE_NUMERIC);
                        }
                    } else {
                        row.getCell((short) c).setCellValue(strText);
                        row.getCell((short) c).setCellType(Cell.CELL_TYPE_STRING);
                    }
                    row.getCell((short) c).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
                }
            }
        }
        
        
        sheet.setAutobreaks(false);
        sheet.setRowBreak(intLine);
        sheet.setColumnBreak(15);
  
        
        int intEndNotas = intLine;
        
        intLine = intLine +2;
        row = sheet.createRow((short) intLine++);
        row.createCell(0).setCellValue("Resultado Final");
        row.getCell((short) 0).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
        row.createCell(3).setCellValue("Faltas Globalizadas");
        row.getCell((short) 3).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine-1, intLine-1, 3, 3+listPeriodosNome.size()-1));
        
        int intEndFaltas = intLine;
        
        row = sheet.createRow((short) intLine++);
        row.createCell(0).setCellValue((curso.isStatus()==true)?"EM CURSO":"CONCLUÍDO");
        row.getCell((short) 0).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));

        for(int i = 0; i<listPeriodosNome.size();i++){
            String strAbrev = "";
            
            if(i==listPeriodosNome.size()-1){
                strAbrev = listPeriodosNome.get(i).toUpperCase();
            }else{
                strAbrev = listPeriodosNome.get(i).substring(0, 7).toUpperCase();
            }
                
            row.createCell(3+i).setCellValue(strAbrev);
            row.getCell((short) 3+i).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb)); 
        }
        
        
        ArrayList<Periodo> periodoList = PeriodoServer.getPeriodos(curso.getIdCurso());
        
        
        
//        System.out.println("Faltas Total:"+intNumeroTotalFaltas);
        

       
//        ArrayList<Periodo> listPeriodoFaltas = PresencaServer.getPresencaAluno(idAluno, idCurso);
//        row = sheet.createRow((short) intLine++);
//        
//        
        int intCountTotal = 0;
        for (int i = 0; i < listPeriodosNome.size(); i++) {
//            for (int cvPeriodo = 0; cvPeriodo < listPeriodoFaltas.size(); cvPeriodo++) {
//                Periodo periodo = listPeriodoFaltas.get(cvPeriodo);
//                System.out.println(periodo.getNomePeriodo());
//                if (periodo.getNomePeriodo().equals(listPeriodosNome.get(i))) {
//                    intCountFaltas = intCountFaltas + periodo.getQuantidadeFalta();
//                }
//    
//            }
            
            int intCountFaltas = 0;
            for (Periodo periodo : periodoList) {

                if (periodo.getNomePeriodo().equals(listPeriodosNome.get(i))) {

                    if (periodo.getNomePeriodo().equals(listPeriodosNome.get(i))) {
                        ArrayList<Disciplina> disciplinaList = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());
                        for (Disciplina disciplina : disciplinaList) {
                            PresencaUsuarioDisciplina preUsuDisc = PresencaServer.getPresencaDisciplinaAluno(disciplina.getIdDisciplina(), idAluno);
                            intCountFaltas = intCountFaltas + preUsuDisc.getNumeroFaltas();
                        }
                    }
                    // System.out.println("Faltas:"+intCountTotal);
                }

            }
            
            intCountTotal = intCountTotal + intCountFaltas;
            if(i==listPeriodosNome.size()-1){
                String strFaltas = Integer.toString(intCountTotal);
                row.createCell(3 + i).setCellValue(Double.parseDouble(strFaltas));
                row.getCell((short) 3 + i).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
                row.getCell((short) 3 + i).setCellType(Cell.CELL_TYPE_NUMERIC);
            }else{
                String strFaltas = Integer.toString(intCountFaltas);
                row.createCell(3 + i).setCellValue(Double.parseDouble(strFaltas));
                row.getCell((short) 3 + i).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
                row.getCell((short) 3 + i).setCellType(Cell.CELL_TYPE_NUMERIC);
            }

        }


        
              
        
        
        intLine = intLine +2;
        row = sheet.createRow((short) intLine++);
        row.createCell(0).setCellValue("Legenda");
        row.getCell((short) 0).setCellStyle(ExcelFramework.getStyleCellFontBoldCenter(wb));
        row.createCell(3).setCellValue("Atenção:");
        row.getCell(3).setCellStyle(ExcelFramework.getStyleCellFontAtencao(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine-1, intLine-1, 3, 12));
        row = sheet.createRow((short) intLine++);
        row.createCell(0).setCellValue("MP : Média Trimestral Provisória");
        row.getCell((short) 0).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
        row.createCell(3).setCellValue("1- Média mínima para aprovação............... "+curso.getMediaNota()+"%");
        row.getCell(3).setCellStyle(ExcelFramework.getStyleCellFontAtencao(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine-1, intLine-1, 3, 12));
        row = sheet.createRow((short) intLine++);
        row.createCell(0).setCellValue("REC : Nota de Recuperação");
        row.getCell((short) 0).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
        row.createCell(3).setCellValue("2- Frequência mínima para aprovação.......... "+curso.getPorcentagemPresenca()+"%");
        row.getCell(3).setCellStyle(ExcelFramework.getStyleCellFontAtencao(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine-1, intLine-1, 3, 12));
        row = sheet.createRow((short) intLine++);
        row.createCell(0).setCellValue("MF : Média Final do Trimestre");
        row.getCell((short) 0).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
        row.createCell(3).setCellValue("3- As faltas são globalizadas, ou seja, 1 falta = 1 aula");
        row.getCell(3).setCellStyle(ExcelFramework.getStyleCellFontAtencao(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine-1, intLine-1, 3, 12));
        row = sheet.createRow((short) intLine++);
        row.createCell(0).setCellValue("MFP : Média Anual Provisória");
        row.getCell((short) 0).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
        row.createCell(3).setCellValue("4- As notas precedidas de asteriscos(*) estão abaixo da média");
        row.getCell(3).setCellStyle(ExcelFramework.getStyleCellFontAtencao(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine-1, intLine-1, 3, 12));
        row = sheet.createRow((short) intLine++);
        row.createCell(0).setCellValue("EX : Nota do Exame");
        row.getCell((short) 0).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
        row.createCell(3).setCellValue("5- Número de dias letivos: 200");
        row.getCell(3).setCellStyle(ExcelFramework.getStyleCellFontAtencao(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine-1, intLine-1, 3, 12));
        row = sheet.createRow((short) intLine++);
        row.createCell(0).setCellValue("MFA : Média Final Anual");
        row.getCell((short) 0).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
        row.createCell(3).setCellValue("6- Recuperação: Prevalecerá a maior nota não ultrapassando a média");
        row.getCell(3).setCellStyle(ExcelFramework.getStyleCellFontAtencao(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine-1, intLine-1, 3, 12));

        //
        for (int i = 0; i < intColumn; i++) {
            sheet.autoSizeColumn(i, true);
        }
//
        ExcelFramework.createImage(wb, sheet);
//
        
        CellRangeAddress regionFirst = new CellRangeAddress(0, 4, 0, listHeader.size() - 1);
        RegionUtil.setBorderBottom(BorderStyle.THIN.ordinal(), regionFirst, sheet, wb);
        RegionUtil.setBorderTop(BorderStyle.THIN.ordinal(), regionFirst, sheet, wb);
        RegionUtil.setBorderLeft(BorderStyle.THIN.ordinal(), regionFirst, sheet, wb);
        RegionUtil.setBorderRight(BorderStyle.THIN.ordinal(), regionFirst, sheet, wb);
        
        CellRangeAddress regionSecond = new CellRangeAddress(3, 3, 0, listHeader.size() - 1);
        RegionUtil.setBorderBottom(BorderStyle.THIN.ordinal(), regionSecond, sheet, wb);
        RegionUtil.setBorderTop(BorderStyle.THIN.ordinal(), regionSecond, sheet, wb);
        RegionUtil.setBorderLeft(BorderStyle.THIN.ordinal(), regionSecond, sheet, wb);
        RegionUtil.setBorderRight(BorderStyle.THIN.ordinal(), regionSecond, sheet, wb);
        
        CellRangeAddress regionAll = new CellRangeAddress(0, intEndNotas - 1, 0, listHeader.size() - 1);
        RegionUtil.setBorderBottom(BorderStyle.THIN.ordinal(), regionAll, sheet, wb);
        RegionUtil.setBorderTop(BorderStyle.THIN.ordinal(), regionAll, sheet, wb);
        RegionUtil.setBorderLeft(BorderStyle.THIN.ordinal(), regionAll, sheet, wb);
        RegionUtil.setBorderRight(BorderStyle.THIN.ordinal(), regionAll, sheet, wb);
        
        CellRangeAddress regionFaltas = new CellRangeAddress(intEndFaltas-1, intEndFaltas-1, 3, 3 + listPeriodosNome.size() - 1);
        RegionUtil.setBorderBottom(BorderStyle.THIN.ordinal(), regionFaltas, sheet, wb);
        RegionUtil.setBorderTop(BorderStyle.THIN.ordinal(), regionFaltas, sheet, wb);
        RegionUtil.setBorderLeft(BorderStyle.THIN.ordinal(), regionFaltas, sheet, wb);
        RegionUtil.setBorderRight(BorderStyle.THIN.ordinal(), regionFaltas, sheet, wb);

        return "";
//        return ExcelFramework.getExcelAddress(wb, "GerarExcelBoletimAnual_");
    }

    public static String gerarExcelBoletimPeriodo(XSSFWorkbook wb, XSSFSheet sheet, int idCurso, int idPeriodo) {

//        XSSFWorkbook wb = new XSSFWorkbook();
//        XSSFSheet sheet = wb.createSheet("Boletim Periodo");
        sheet.setFitToPage(true);
        sheet.getPrintSetup().setLandscape(true);

        ArrayList<Disciplina> listDisciplinas = DisciplinaServer.getDisciplinasComAvaliacoes(idPeriodo);
        ArrayList<Usuario> listUsuario = UsuarioServer.getAlunosPorCurso(idCurso);
        Periodo periodo = PeriodoServer.getPeriodo(idPeriodo);
        Curso curso = CursoServer.getCurso(idCurso);

        ArrayList<ArrayList<String>> listMediaNotaAlunos = getMediaNotaAlunosNasDisciplinas(idCurso, listUsuario, listDisciplinas);

        int intColumn = 0;
        int intLine = 0;
        Row row = sheet.createRow((short) intLine);
        row.createCell((short) intColumn).setCellValue("PLANILHA DE NOTAS     -     " + curso.getNome() + "     -     " + periodo.getNomePeriodo());
        row.getCell((short) intColumn).setCellStyle(ExcelFramework.getStyleTitleBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, 0, 8));
        intLine++;

        row = sheet.createRow((short) intLine++);
        row.createCell((short) intColumn).setCellValue("Aluno");
        row.getCell((short) intColumn++).setCellStyle(ExcelFramework.getStyleHeaderBoletim(wb));

        for (Disciplina disciplina : listDisciplinas) {
            String nomeDisciplina = Curso.getAbreviarNomeCurso(disciplina.getNome());
            row.createCell((short) intColumn).setCellValue(nomeDisciplina);
            row.getCell((short) intColumn).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell((short) intColumn++).setCellStyle(ExcelFramework.getStyleHeaderBoletim(wb));
        }

        for (ArrayList<String> listAlunoNotas : listMediaNotaAlunos) {
            row = sheet.createRow((short) intLine++);
            for (int i = 0; i < listAlunoNotas.size() - 1; i++) {
                String strText = listAlunoNotas.get(i + 1);
                row.createCell((short) i);

                if (i == 0) {
                    row.getCell((short) i).setCellValue(strText);
                    row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell((short) i).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
                } else {
                    if (FieldVerifier.isNumeric(strText)) {
                        row.getCell((short) i).setCellValue(Double.parseDouble(strText));
                        row.getCell((short) i).setCellType(Cell.CELL_TYPE_NUMERIC);
                    } else {
                        row.getCell((short) i).setCellValue(strText);
                        row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING);
                    }
                    row.getCell((short) i).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
                }
            }
        }

        for (int i = 0; i < intColumn; i++) {
            if (i == 0) {
                sheet.autoSizeColumn(i, true);
            } else {
                sheet.setColumnWidth(i, 2000);
            }
        }

        return "";//ExcelFramework.getExcelAddress(wb, "GerarExcelBoletimPeriodo_");

    }

    public static String gerarExcelBoletimDisciplina(XSSFWorkbook wb, XSSFSheet sheet, int idCurso, int idPeriodo, int idDisciplina) {
//        XSSFWorkbook wb = new XSSFWorkbook();
//        XSSFSheet sheet = wb.createSheet("Boletim Disciplina");
        sheet.setFitToPage(true);
        sheet.getPrintSetup().setLandscape(true);

        ArrayList<String> listHeader = AvaliacaoServer.getHeaderRelatorioBoletimDisciplina(idCurso, idPeriodo, idDisciplina);
        ArrayList<ArrayList<String>> listMediaNotaAlunos = getRelatorioBoletimDisciplina(idCurso, idPeriodo, idDisciplina);
        Curso curso = CursoServer.getCurso(idCurso);
        Periodo periodo = PeriodoServer.getPeriodo(idPeriodo);
        Disciplina disciplina = DisciplinaServer.getDisciplina(idDisciplina);

        int intColumn = 0;
        int intLine = 0;
        Row row = sheet.createRow((short) intLine);
        row.createCell((short) intColumn).setCellValue("PLANILHA DE NOTAS");
        row.getCell((short) intColumn).setCellStyle(ExcelFramework.getStyleTitleBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, 0, 8));
        intLine++;

        row = sheet.createRow((short) intLine);
        row.createCell((short) intColumn).setCellValue(curso.getNome() + "     -     " + periodo.getNomePeriodo() + "     -     " + disciplina.getNome());
        row.getCell((short) intColumn).setCellStyle(ExcelFramework.getStyleTitleBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine, intLine, 0, 8));
        intLine++;

        row = sheet.createRow((short) intLine++);
        row.createCell((short) intColumn).setCellValue("Aluno");
        row.getCell((short) intColumn++).setCellStyle(ExcelFramework.getStyleHeaderBoletim(wb));

        for (String strHeader : listHeader) {

            int indexIdAvaliacao = strHeader.indexOf("|");
            if (indexIdAvaliacao != -1) {
                strHeader = strHeader.substring(indexIdAvaliacao + 1);
            }
            row.createCell((short) intColumn).setCellValue(strHeader);
            row.getCell((short) intColumn).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell((short) intColumn++).setCellStyle(ExcelFramework.getStyleHeaderBoletim(wb));
        }

        for (ArrayList<String> listAlunoNotas : listMediaNotaAlunos) {
            row = sheet.createRow((short) intLine++);
            for (int i = 0; i < listAlunoNotas.size() - 1; i++) {
                String strText = listAlunoNotas.get(i + 1);
                row.createCell((short) i);

                if (i == 0) {
                    row.getCell((short) i).setCellValue(strText);
                    row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell((short) i).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb));
                } else {
                    if (FieldVerifier.isNumeric(strText)) {
                        row.getCell((short) i).setCellValue(Double.parseDouble(strText));
                        row.getCell((short) i).setCellType(Cell.CELL_TYPE_NUMERIC);
                    } else {
                        row.getCell((short) i).setCellValue(strText);
                        row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING);
                    }
                    row.getCell((short) i).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));
                }
            }
        }

        for (int i = 0; i < intColumn; i++) {
            sheet.autoSizeColumn(i, true);
        }

        return "";
//        return ExcelFramework.getExcelAddress(wb, "GerarExcelBoletimDisciplina_");
    }
    


}
