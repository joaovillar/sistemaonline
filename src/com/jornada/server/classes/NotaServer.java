package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
import com.jornada.shared.classes.boletim.TabelaBoletim;

public class NotaServer {	
	
	public static final String DB_INSERT = "INSERT INTO nota (id_avaliacao, id_usuario, nota) VALUES (?,?,?);";
	public static final String DB_UPDATE = "UPDATE nota set nota=? where id_avaliacao=? and id_usuario=?;";
	public static final String DB_SELECT_NOTA_PELA_AVALIACAO = "SELECT * FROM nota where id_avaliacao=?;";	
	public static final String DB_SELECT_BOLETIM_NOTA_ALUNO_POR_CURSO =
	"		select d.*, a.*, n,* from "+ 
	"		( "+
	"			select "+ 
	"				c.id_curso, c.nome_curso, "+ 
	"				p.id_periodo, p.nome_periodo, "+ 
	"				d.id_disciplina, d.nome_disciplina "+
	"			from usuario u "+
	"			inner join rel_curso_usuario rcu on u.id_usuario=rcu.id_usuario "+
	"			inner join curso c on c.id_curso = rcu.id_curso "+
	"			inner join periodo p on c.id_curso = p.id_curso "+
	"			inner join disciplina d on p.id_periodo = d.id_periodo "+
	"			where  "+
	"			u.id_tipo_usuario=? and  rcu.id_usuario=? and c.id_curso=? "+ 
	"		) d "+
//	"		left join ( select * from conteudo_programatico ) cp on d.id_disciplina=cp.id_disciplina "+
	"		left join ( select * from avaliacao ) a on d.id_disciplina=a.id_disciplina  "+
	"		left join ( select * from nota where id_usuario=? ) n on a.id_avaliacao=n.id_avaliacao ";
	
	public static Boolean Adicionar(Nota object) {
		
		Boolean isOperationDone = false; 
		
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
//			Connection conn = dataBase.getConnection();
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
	
	public static boolean updateRow(Nota object){
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			int count = 0;
			PreparedStatement psUpdate = conn.prepareStatement(DB_UPDATE);
//			psUpdate.setInt(++count, object.getIdAvaliacao());				
//			psUpdate.setInt(++count, object.getIdUsuario());
			psUpdate.setString(++count, object.getNota());
			psUpdate.setInt(++count, object.getIdAvaliacao());
			psUpdate.setInt(++count, object.getIdUsuario());

			int numberUpdate = psUpdate.executeUpdate();

			if (numberUpdate == 1) {
				success = true;
			}
			else{
				count=0;
				PreparedStatement psInsert = conn.prepareStatement(DB_INSERT);
				psInsert.setInt(++count, object.getIdAvaliacao());				
				psInsert.setInt(++count, object.getIdUsuario());
				psInsert.setString(++count, object.getNota());
				numberUpdate = psInsert.executeUpdate();
				if (numberUpdate == 1){
					success = true;
				}
			}

		} catch (SQLException sqlex) {
			success=false;
			System.err.println(sqlex.getMessage());			
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}		


		return success;
	}
	
	public static ArrayList<Nota> getNotas(int idAvaliacao) {

		ArrayList<Nota> data = new ArrayList<Nota>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();

		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = conn.prepareStatement(DB_SELECT_NOTA_PELA_AVALIACAO);
			
			int count=0;
			ps.setInt(++count, idAvaliacao);			

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				Nota current = new Nota();
				
				current.setIdNota(rs.getInt("id_nota"));
				current.setIdAvaliacao(rs.getInt("id_avaliacao"));
				current.setIdUsuario(rs.getInt("id_usuario"));
				current.setNota(rs.getString("nota"));				

				data.add(current);
			}

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}

		return data;

	}		
	
	public static ArrayList<TabelaBoletim> getBoletimNotasPorAlunoPorCurso(int idCurso, int idTipoUsuario, int idUsuario){
		
		ArrayList<TabelaBoletim> data = new ArrayList<TabelaBoletim>();
		
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = conn.prepareStatement(DB_SELECT_BOLETIM_NOTA_ALUNO_POR_CURSO);
			
			int count=0;
			ps.setInt(++count, idTipoUsuario);	
			ps.setInt(++count, idUsuario);
			ps.setInt(++count, idCurso);	
			ps.setInt(++count, idUsuario);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				TabelaBoletim current = new TabelaBoletim();
				
				current.setNomeCurso(rs.getString("nome_curso"));
				current.setNomePeriodo(rs.getString("nome_periodo"));
				current.setIdDisciplina(rs.getInt("id_disciplina"));
				current.setNomeDisciplina(rs.getString("nome_disciplina"));
//				current.setNomeConteudoProgramatico(rs.getString("descricao"));
				current.setNomeAvaliacao(rs.getString("assunto"));
				current.setNota(rs.getString("nota"));				

				data.add(current);
			}

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
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
        
        //Adicionando o Header 
        ArrayList<String> itemPeriodo = new ArrayList<String>();
        HashSet<String> hashSetDisciplinas = new HashSet<String>();
        
        itemPeriodo.add("Disciplinas");
        //Pegando nome dos Periodos e de todas as disciplinas do ano
        for (Periodo periodo : listPeriodo) {            
            itemPeriodo.add(periodo.getNomePeriodo());
            ArrayList<Disciplina> listDisciplina = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());
            for (Disciplina disciplina : listDisciplina) {
                hashSetDisciplinas.add(disciplina.getNome());
            }
        }
        itemPeriodo.add("Média Final");
        listNotas.add(itemPeriodo);
        
        ArrayList<String> listNomeDisciplinas = new ArrayList<String>(hashSetDisciplinas);
        Collections.sort(listNomeDisciplinas);  
        
        
        for (int i=0;i<listNomeDisciplinas.size();i++) {
            ArrayList<String> array = new ArrayList<String>();
            String nomeDisciplina = listNomeDisciplinas.get(i);

            array.add(nomeDisciplina);

            int numberPesoPeriodos=0;
            double somaMedia=0;
            for (Periodo periodo : listPeriodo) {
                String notaDisciplinaPeriodo = "-";
                ArrayList<Disciplina> listDisciplina = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());
                for (Disciplina disciplina : listDisciplina) {

                    if (disciplina.getNome().equals(nomeDisciplina)) {
                        disciplina.setListAvaliacao(AvaliacaoServer.getAvaliacaoComNotas(disciplina.getIdDisciplina()));
                        notaDisciplinaPeriodo = disciplina.getMediaAlunoDisciplina(curso, idUsuario);
                    }
                }
                if (notaDisciplinaPeriodo.isEmpty() || notaDisciplinaPeriodo.equals("-")) {
                    notaDisciplinaPeriodo = "-";
                } else {
                    notaDisciplinaPeriodo = MpUtilServer.getDecimalFormatedOneDecimal(Double.parseDouble(notaDisciplinaPeriodo));
                }               
                
                if (!notaDisciplinaPeriodo.equals("-")) {
                    int peso = Integer.parseInt(periodo.getPeso());
                    for (int countPesos = 0; countPesos < peso; countPesos++) {
                        numberPesoPeriodos++;
                        somaMedia = somaMedia + Double.parseDouble(notaDisciplinaPeriodo);
                    }
                }
                
                array.add(notaDisciplinaPeriodo);
                
            }
            
            if(numberPesoPeriodos>0){
                double doubleMediaFinal = somaMedia / numberPesoPeriodos;
//                String strMediaFinalDisciplina = MpUtilServer.getDecimalFormatedOneDecimal(doubleMediaFinal);
//                if(strMediaFinalDisciplina.isEmpty()){
//                    strMediaFinalDisciplina="-";
//                }
                array.add(MpUtilServer.getDecimalFormatedOneDecimal(doubleMediaFinal));
            }else{
                array.add("-");
            }
            
            listNotas.add(array);

        }
        
        return listNotas;
    }
    
    
    public static ArrayList<ArrayList<String>> getBoletimAnual(int idCurso) {
        Curso curso = CursoServer.getCurso(idCurso);
        ArrayList<ArrayList<String>> listNotas = new ArrayList<ArrayList<String>>();
        ArrayList<Periodo> listPeriodo = PeriodoServer.getPeriodos(idCurso);
        ArrayList<Usuario> listUsuario = UsuarioServer.getAlunosPorCurso(idCurso);
        
        HashSet<String> hashSetDisciplinas = new HashSet<String>();
        //Pegando nome das disciplinas de todo o ano letivo
        for (Periodo periodo : listPeriodo) {
            ArrayList<Disciplina> listDisciplina = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());
            for (Disciplina disciplina : listDisciplina) {
                hashSetDisciplinas.add(disciplina.getNome());
            }
        }
        
        ArrayList<String> listDisciplinasAno = new ArrayList<String>(hashSetDisciplinas);
        Collections.sort(listDisciplinasAno);      
        
        ArrayList<String> listDisciplinasAnoAluno = new ArrayList<String>();
        listDisciplinasAnoAluno.add("IdUsuario");
        listDisciplinasAnoAluno.add("Alunos");
        for (String string : listDisciplinasAno) {
            listDisciplinasAnoAluno.add(string);
        }
        listDisciplinasAnoAluno.add("RESULTADO FINAL");
        
        listNotas.add(listDisciplinasAnoAluno);
        for(Usuario usuario : listUsuario){
            ArrayList<String> array = new ArrayList<String>();
            array.add(Integer.toString(usuario.getIdUsuario()));
            
            if (usuario.getIdTipoStatusUsuario() == TipoStatusUsuario.ALUNO_TRANSFERIDO) {
                array.add(usuario.getPrimeiroNome() + " " + usuario.getSobreNome() + " - "+ usuario.getTipoStatusUsuario().getNomeTipoStatusUsuario());
            } else {
                array.add(usuario.getPrimeiroNome() + " " + usuario.getSobreNome());
            }
            
            for(int i=2;i<listDisciplinasAnoAluno.size()-1;i++){
                String nomeDisciplina = listDisciplinasAnoAluno.get(i);
                String text = getMediaAnoAlunoDisciplina(usuario.getIdUsuario(), idCurso,listPeriodo,nomeDisciplina);
                if(text.isEmpty())text="-";
                if(usuario.getIdTipoStatusUsuario()==TipoStatusUsuario.ALUNO_TRANSFERIDO)text="-";
                array.add(text);
            }
            
            listNotas.add(array);      
        }
        
        

        
        for(int line=0; line< listNotas.size();line++){            
            if (line > 0) {         
                boolean aprovado=true;
                ArrayList<String> row = listNotas.get(line);

                int idAluno = Integer.parseInt(row.get(0));
                Usuario aluno = UsuarioServer.getUsuarioPeloId(idAluno);
                
                for (int column = 0; column < row.size(); column++) {
                    if(column>1){
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
        
        
        System.out.println(listNotas.toString());
        return listNotas;
    }
        
    
    public static ArrayList<ArrayList<String>> getBoletimNotas(int idCurso){
        
        ArrayList<ArrayList<String>> listNotas = new ArrayList<ArrayList<String>>();
        
        Curso curso = CursoServer.getCurso(idCurso);
        ArrayList<Periodo> listPeriodo = PeriodoServer.getPeriodos(curso.getIdCurso());
        ArrayList<Usuario> listUsuario = UsuarioServer.getAlunosPorCurso(idCurso);
        
        HashSet<String> hashSetDisciplinas = new HashSet<String>();
        //Pegando nome das disciplinas de todo o ano letivo
        for (Periodo periodo : listPeriodo) {
            ArrayList<Disciplina> listDisciplina = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());
            for (Disciplina disciplina : listDisciplina) {
                hashSetDisciplinas.add(disciplina.getNome());
            }
        }
           
        
        ArrayList<String> listDisciplinasAno = new ArrayList<String>(hashSetDisciplinas);        
        Collections.sort(listDisciplinasAno);      
        
        
        //Nome das disciplinas do curso
        ArrayList<String> listDisciplinasAnoAluno = new ArrayList<String>();
        listDisciplinasAnoAluno.add("IdUsuario");
        listDisciplinasAnoAluno.add("Alunos");
        listDisciplinasAnoAluno.add("Etapas");
        for (String string : listDisciplinasAno) {
            listDisciplinasAnoAluno.add(string);
        }
        
        
        
        //Pegando nome de avaliações
        HashSet<String> hashSetAvaliacao = new HashSet<String>();
        for(Periodo periodo : listPeriodo){            
            ArrayList<Disciplina> listDisciplinas = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());            
            for (Disciplina disciplina : listDisciplinas) {
                ArrayList<Avaliacao> listAvaliacoes = AvaliacaoServer.getAvaliacao(disciplina.getIdDisciplina());
                for (Avaliacao avaliacao : listAvaliacoes) {
                    if (avaliacao.getIdTipoAvaliacao() != TipoAvaliacao.INT_RECUPERACAO) {
                        avaliacao.setTipoAvaliacao(AvaliacaoServer.getTipoAvaliacao(avaliacao.getIdTipoAvaliacao()));
                        hashSetAvaliacao.add(avaliacao.getTipoAvaliacao().getNomeTipoAvaliacao());
                    }
                }
            }
        }
        
        ArrayList<String> listNomeAvaliacao = new ArrayList<String>(hashSetAvaliacao);
        listNomeAvaliacao.add(TipoAvaliacao.STR_RECUPERACAO);
        listNomeAvaliacao.add("Média Final");
        
        listNotas.add(listDisciplinasAnoAluno);
        
        
        //Montando linha a linha os resultadas de cada aluno por disciplina e avaliação
        for (Usuario usuario : listUsuario) {
            
            for (Periodo periodo : listPeriodo) {
                ArrayList<Disciplina> listDisciplinas = DisciplinaServer.getDisciplinasComAvaliacoes(periodo.getIdPeriodo());
                
                for (String strNomeAvaliacao : listNomeAvaliacao) {

                        ArrayList<String> listRow = new ArrayList<String>();
                        int idUser = usuario.getIdUsuario();

                        listRow.add(Integer.toString(idUser));
                        listRow.add(usuario.getPrimeiroNome() + " " + usuario.getSobreNome());
                        listRow.add("[" + periodo.getNomePeriodo() + "] " + strNomeAvaliacao);

                        for (String strNomeDisc : listDisciplinasAno) {

                            Disciplina disciplina = DisciplinaServer.getDisciplinaFromArray(strNomeDisc, listDisciplinas);

                            if (strNomeAvaliacao.equals("Média Final")) {
                                String strMedia = "-";
                                if (disciplina == null) {
                                    strMedia = "-";
                                } else {
                                    strMedia = disciplina.getMediaAlunoDisciplina(curso, idUser);
                                    if (strMedia == null || strMedia.isEmpty()) {
                                        strMedia = "-";
                                    } else {
                                        double doubleMediaAluno = Double.parseDouble(strMedia);
                                        strMedia = MpUtilServer.getDecimalFormatedOneDecimal(doubleMediaAluno);
                                    }

                                }
                                listRow.add(strMedia);

                            } else {

                                String strNota = "-";

                                if (disciplina == null) {
                                    strNota = "-";
                                } else {
                                    int idDisciplina = disciplina.getIdDisciplina();
                                    strNota = getNota(listDisciplinas, idUser, idDisciplina, strNomeAvaliacao);
                                    if (strNota == null || strNota.isEmpty()) {
                                        strNota = "-";
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
        
        
//        for (Usuario usuario : listUsuario) {
//            
//            for (Periodo periodo : listPeriodo) {
//                
//                ArrayList<String> listRow = new ArrayList<String>();
//                int idUser = usuario.getIdUsuario();
//                listRow.add(Integer.toString(idUser));
//                listRow.add(usuario.getPrimeiroNome() + " " + usuario.getSobreNome());
//                
//                ArrayList<Disciplina> listDisciplinas = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo()); 
//          
//
//                
//                listNotas.add(listRow);
//            }
//            
//        }
        
                
        
        return listNotas;
    }
    
    
//    private static Disciplina getDisciplinaFromArray(String strNomeDisciplina, ArrayList<Disciplina> listDisciplina){
//        
//        Disciplina disciplina = null;
//        
//        for (Disciplina disciplinaRow : listDisciplina) {
//            
//            if(disciplinaRow.getNome().equals(strNomeDisciplina)){
//                disciplina = disciplinaRow;
//            }
//        }
//        
//        return disciplina;
//    }
//    
    private static String getNota(ArrayList<Disciplina> listDisciplina, int idUsuario, int idDisciplina, String strNomeTipoAvaliacao){
        String strNota = "";
        
        for(Disciplina disciplina : listDisciplina){
            if(disciplina.getIdDisciplina()==idDisciplina){
                ArrayList<Avaliacao> listAvaliacao = disciplina.getListAvaliacao();
                for(Avaliacao avaliacao : listAvaliacao){
                    if(avaliacao.getTipoAvaliacao().getNomeTipoAvaliacao().equals(strNomeTipoAvaliacao)){
                        ArrayList<Nota> listNota = avaliacao.getListNota();
                        for(Nota nota : listNota){
                            if(nota.getIdUsuario()==idUsuario ){
                                strNota = nota.getNota();
                            }
                        }  
                    }   
                }                
            }

        }
        
        
        return strNota;
    }
    
    public static String getMediaAnoAlunoDisciplina(int idUsuario, int idCurso, ArrayList<Periodo> listPeriodo, String strDisciplina){
        String strMedia="";
        double doubleMediaAluno=0;
        int countMedia=0;
        Curso curso = CursoServer.getCurso(idCurso);

        for (Periodo periodo : listPeriodo) {
            
            ArrayList<Disciplina> listDisciplina = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());
            
            for (Disciplina disciplina : listDisciplina) {              
                
                if (disciplina.getNome().equals(strDisciplina)) {
                    
                    disciplina.setListAvaliacao(AvaliacaoServer.getAvaliacaoComNotas(disciplina.getIdDisciplina()));
                    strMedia = disciplina.getMediaAlunoDisciplina(curso, idUsuario);
                    
                    int intPesoNotaPeriodo = Integer.parseInt(periodo.getPeso());
                    for (int i = 0; i < intPesoNotaPeriodo; i++) {
                        if (strMedia == null || strMedia.isEmpty()) {
                        } else {
                            doubleMediaAluno = doubleMediaAluno + Double.parseDouble(strMedia);
                            countMedia++;
//                            break;
                        }
                    }
                    
                }
            }     
        }
       
        if (countMedia != 0) {
            doubleMediaAluno = doubleMediaAluno / countMedia;
            strMedia = MpUtilServer.getDecimalFormatedOneDecimal(doubleMediaAluno);           
        }
        
        return strMedia;
    }
        
    public static ArrayList<ArrayList<String>> getRelatorioBoletimDisciplina(int idCurso, int idPeriodo, int idDisciplina) {
        
        Curso curso = CursoServer.getCurso(idCurso);
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();   
        ArrayList<Usuario> listUsuario = UsuarioServer.getAlunosPorCurso(idCurso);       
        
        
        Disciplina disciplina = DisciplinaServer.getDisciplina(idDisciplina);
        disciplina.setListAvaliacao(AvaliacaoServer.getAvaliacaoComNotas(idDisciplina));
                
        for (Usuario usuario : listUsuario) {
            
            ArrayList<String> item = new ArrayList<String>();
            item.add(Integer.toString(usuario.getIdUsuario()));
            if (usuario.getIdTipoStatusUsuario() == TipoStatusUsuario.ALUNO_TRANSFERIDO) {
                item.add(usuario.getPrimeiroNome() + " " + usuario.getSobreNome()+" - "+usuario.getTipoStatusUsuario().getNomeTipoStatusUsuario());
            } else {
                item.add(usuario.getPrimeiroNome() + " " + usuario.getSobreNome());
            }
            
            for (Avaliacao avaliacao : disciplina.getListAvaliacao()) {
                
                String strNota="";
                if (usuario.getIdTipoStatusUsuario() == TipoStatusUsuario.ALUNO_TRANSFERIDO) {
                    strNota="-";
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
            list.add(item);
        }        

        
        for (int i=0;i<list.size();i++) {
            int idUsuario = Integer.parseInt(list.get(i).get(0));
            Usuario aluno = UsuarioServer.getUsuarioPeloId(idUsuario);
            if (aluno.getIdTipoStatusUsuario() == 2) {
                list.get(i).add("-");
                list.get(i).add("-");

            } else {
                String strMedia = disciplina.getMediaAlunoDisciplina(curso, idUsuario);
                if (strMedia == null || strMedia.isEmpty()) {
                    strMedia = "-";
                    list.get(i).add(strMedia);
                    list.get(i).add(strMedia);
                } else {
                    double doubleMediaAluno = Double.parseDouble(strMedia);
                    list.get(i).add(MpUtilServer.getDecimalFormatedTwoDecimal(doubleMediaAluno));
                    list.get(i).add(MpUtilServer.getDecimalFormatedOneDecimal(doubleMediaAluno));
                }
            }
            
        }

        return list;
    }
  
    public static ArrayList<ArrayList<String>> getMediaNotaAlunosNasDisciplinas(int idCurso, ArrayList<Usuario> listUsuario, ArrayList<Disciplina> listDisciplinas){
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        
        Curso curso = CursoServer.getCurso(idCurso);
        
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
                    String strMedia = disciplina.getMediaAlunoDisciplina(curso, usuario.getIdUsuario());
                    if (strMedia == null || strMedia.isEmpty()) {
                        strMedia = "-";
                        item.add(strMedia);
                    } else {
                        double doubleMediaAluno = Double.parseDouble(strMedia);
                        item.add(MpUtilServer.getDecimalFormatedOneDecimal(doubleMediaAluno));
                    }
                }
            }
            
            list.add(item);
        }
       
        return list;
    }
    
    
    
    public static String getExcelBoletimNotas(int idCurso) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Boletim Notas");
//        sheet.setColumnBreak(15);
        sheet.getPrintSetup().setLandscape(true);
        sheet.setAutobreaks(true);
        sheet.setFitToPage(true);
        sheet.getPrintSetup().setFitWidth((short)1);
        sheet.getPrintSetup().setFitHeight((short)0);
        
        
        ArrayList<ArrayList<String>> listAvaliacaoNotasAlunos = getBoletimNotas(idCurso);
        Curso curso = CursoServer.getCurso(idCurso);
        
        int intColumn=0;
        int intLine=0;
        Row row = sheet.createRow((short) intLine);  
        row.createCell((short) intColumn).setCellValue("PLANILHA DE NOTAS - "+curso.getNome());
        row.getCell((short) intColumn).setCellStyle(ExcelFramework.getStyleTitleBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine,intLine,0,8));
        intLine++;
        
//        row = sheet.createRow((short) intLine);  
//        row.createCell((short) intColumn).setCellValue(curso.getNome());
//        row.getCell((short) intColumn).setCellStyle(ExcelFramework.getStyleTitleBoletim(wb));
//        sheet.addMergedRegion(new CellRangeAddress(intLine,intLine,0,8));
//        intLine++;        
        
//        row = sheet.createRow((short) intLine++);  
//        row.createCell((short) intColumn).setCellValue("Aluno");
//        row.getCell((short) intColumn++).setCellStyle(ExcelFramework.getStyleHeaderBoletim(wb));
       
            ArrayList<String> listHeaderAlunoNotas = listAvaliacaoNotasAlunos.get(0);
            row = sheet.createRow((short) intLine++);
            intColumn = listHeaderAlunoNotas.size();
            for (int i = 0; i < listHeaderAlunoNotas.size()-1; i++) {
                row.createCell((short) i);
                if(i==0 || i==1){
                    String strText = listHeaderAlunoNotas.get(i+1); 
                    row.getCell((short) i).setCellValue(strText);                     
                    row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING); 
                    row.getCell((short) i).setCellStyle(ExcelFramework.getStyleHeaderBoletim(wb)); 
                }else{
                    String strText = listHeaderAlunoNotas.get(i+1); 
                    strText = Curso.getAbreviarNomeCurso(strText);
                    row.getCell((short) i).setCellValue(strText);            
                    row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING); 
                    row.getCell((short) i).setCellStyle(ExcelFramework.getStyleHeaderBoletim(wb)); 
                }
            }
     
        
        for (int line=1; line<listAvaliacaoNotasAlunos.size();line++) {
            ArrayList<String> listAlunoNotas = listAvaliacaoNotasAlunos.get(line);
            row = sheet.createRow((short) intLine++);
            for (int i = 0; i < listAlunoNotas.size()-1; i++) { 
                String strText = listAlunoNotas.get(i+1);   
                row.createCell((short) i);
                
                if(i==0 || i==1){
                    row.getCell((short) i).setCellValue(strText);                     
                    row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING); 
                    row.getCell((short) i).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb)); 
                    
                }else{
                    if(FieldVerifier.isNumeric(strText)){
                        double nota = Double.parseDouble(strText);
                        double mediaCurso = Double.parseDouble(curso.getMediaNota());
                        String strSinal="";
                        if(nota<mediaCurso){
                            strSinal="*";
                            row.getCell((short) i).setCellValue(strSinal+strText);                     
                            row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING); 
                        }else{
                            row.getCell((short) i).setCellValue(nota);                     
                            row.getCell((short) i).setCellType(Cell.CELL_TYPE_NUMERIC); 
                        }

                    }else{
                        row.getCell((short) i).setCellValue(strText);                     
                        row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING); 
                    }

                    row.getCell((short) i).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));               
                     
                }        
            }
        }
        
        for (int i = 0; i < intColumn; i++) {   
                sheet.autoSizeColumn(i,true);
        }
        
        return ExcelFramework.getExcelAddress(wb,"GerarExcelBoletimNotas_");
    }    
    
    
       
    public static String getExcelBoletimAnual(int idCurso) {
        XSSFWorkbook wb = new XSSFWorkbook();

        // /Creating Tabs
        XSSFSheet sheet = wb.createSheet("Boletim Anual");
        sheet.setFitToPage(true);
        sheet.getPrintSetup().setLandscape(true);
        sheet.setMargin((short)1, 1.5);

        ArrayList<ArrayList<String>> listNotas = getBoletimAnual(idCurso);
        ArrayList<String> listHeader = listNotas.get(0);
        
        Curso curso = CursoServer.getCurso(idCurso);
        
        String texto1  ="COLÉGIO INTEGRADO";
        String texto2  ="Ato de Criação da Escola : Portaria do Dirigente Regional de Ensino de 26/01 - Publicado no D.O.E. de 27/01/99";
        String texto3 = "Na data de "+ MpUtilServer.convertDateToString(curso.getDataFinal(),"dd MMMM yyyy")+", encerrou-se o ";
        String texto4 = "processo de avaliação de aprendizagem dos alunos do : ";
        String texto5 = curso.getNome()+", ";
        String texto6 = "deste estabelecimento com o resultado abaixo discriminado. ";
        String texto7 = "\n\nDias Letivos:200";
        int intColumn=0;
        int intLine=0;
        
        Row row = sheet.createRow(intLine);  
        row.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(intLine,intLine+1,0,listHeader.size()-2));
        row.getCell(0).setCellValue(texto1);
        row.getCell(0).setCellStyle(ExcelFramework.getStyleTitleBoletimAno(wb));   
        

        intLine=intLine+2;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue(texto2);     
        row.getCell(0).setCellStyle(ExcelFramework.getStyleCellFontBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine,intLine,0,listHeader.size()-2));
        
        int startMergedComments=3;
        int sizeMergedComments=5;
        intLine=intLine+1;
        row = sheet.createRow(intLine);
        row.createCell(0).setCellValue(new XSSFRichTextString(texto3+texto4+texto5+texto6+texto7));     
//        row.getCell(0).setCellStyle(ExcelFramework.getStyleCellFontBoletimDataFinalizacao(wb));
        CellRangeAddress regionComments = new CellRangeAddress(intLine,intLine+sizeMergedComments,0,0);
        ExcelFramework.cleanBeforeMergeOnValidCells(sheet, regionComments, ExcelFramework.getStyleCellFontBoletimDataFinalizacao(wb));
        sheet.addMergedRegion(regionComments);
        
        row.createCell(1).setCellValue("DISCIPLINAS - GRADE CURRICULAR"); 
        CellRangeAddress regionGrade = new CellRangeAddress(intLine,intLine,1,listHeader.size()-3);
        ExcelFramework.cleanBeforeMergeOnValidCells(sheet, regionGrade, ExcelFramework.getStyleHeaderBoletim(wb));
        sheet.addMergedRegion(regionGrade);
        row.createCell(listHeader.size()-2).setCellValue("RESULTADO FINAL"); 
        row.getCell(listHeader.size()-2).setCellStyle(ExcelFramework.getStyleHeaderBoletimRotation90(wb));
        

        intLine=intLine+sizeMergedComments+1;
        

        
        row = sheet.createRow(intLine++);  
        
        

        for(int i=1;i<listHeader.size();i++){
            String header = "";
            row.createCell(intColumn);
            row.getCell(intColumn).setCellType(Cell.CELL_TYPE_STRING);   
            
            if (i == 1) {

                header = listHeader.get(i);
                row.getCell(intColumn).setCellValue(header);
                row.getCell(intColumn).setCellStyle(ExcelFramework.getStyleHeaderBoletim(wb));  
                
            } else {
                
                int intSizeDisciplina=1;
                if (i == listHeader.size() - 1) {
                    header = listHeader.get(i);
                    intSizeDisciplina=2;
                } else {
                    intSizeDisciplina=1;
                    header = Curso.getAbreviarNomeCursoAno(listHeader.get(i));
                }

                row = sheet.getRow(startMergedComments + 1);
                row.createCell(intColumn);
                row.getCell(intColumn).setCellValue(header);
                row.getCell(intColumn).setCellStyle(ExcelFramework.getStyleHeaderBoletimRotation90(wb));

                CellRangeAddress region = new CellRangeAddress(intLine - sizeMergedComments - intSizeDisciplina, intLine - 1, intColumn, intColumn);
                ExcelFramework.cleanBeforeMergeOnValidCells(sheet, region, ExcelFramework.getStyleHeaderBoletimRotation90(wb));
                sheet.addMergedRegion(region);

            }           
            
            intColumn++;
        }

        
        for(int r=1;r<listNotas.size();r++){
            ArrayList<String> listRow = listNotas.get(r);
            row = sheet.createRow((short) intLine++);
            for (int c=1; c< listRow.size(); c++) {
                String strText = listRow.get(c);
                row.createCell((short) c-1);
                
                if(c==1){
                    row.getCell((short) c-1).setCellValue(strText);                     
                    row.getCell((short) c-1).setCellType(Cell.CELL_TYPE_STRING); 
                    row.getCell((short) c-1).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb)); 
                }else{
                    if(FieldVerifier.isNumeric(strText)){
                        row.getCell((short) c-1).setCellValue(Double.parseDouble(strText));                     
                        row.getCell((short) c-1).setCellType(Cell.CELL_TYPE_NUMERIC); 
                    }else{
                        row.getCell((short) c-1).setCellValue(strText);                     
                        row.getCell((short) c-1).setCellType(Cell.CELL_TYPE_STRING); 
                    }
                    row.getCell((short) c-1).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));     
                }        
            }
        }
        
        for (int i = 0; i < intColumn; i++) {   
            if(i==0){
                sheet.setColumnWidth(i, 20000); 
            }else if(i==intColumn-1){
                sheet.autoSizeColumn(i,true);
            }else{
                sheet.setColumnWidth(i, 2000);
            }
        }
        
        ExcelFramework.createImage(wb, sheet);
        
        CellRangeAddress regionAll = new CellRangeAddress(0, intLine-1, 0, listHeader.size()-2);
        RegionUtil.setBorderBottom(BorderStyle.THICK.ordinal(), regionAll, sheet, wb);
        RegionUtil.setBorderTop(BorderStyle.THICK.ordinal(), regionAll, sheet, wb);
        RegionUtil.setBorderLeft(BorderStyle.THICK.ordinal(), regionAll, sheet, wb);
        RegionUtil.setBorderRight(BorderStyle.THICK.ordinal(), regionAll, sheet, wb);
        
        return ExcelFramework.getExcelAddress(wb,"GerarExcelBoletimAnual_");
    }
    
    public static String gerarExcelBoletimPeriodo(int idCurso, int idPeriodo) {

        XSSFWorkbook wb = new XSSFWorkbook();
        // /Creating Tabs
        XSSFSheet sheet = wb.createSheet("Boletim Periodo");
        sheet.setFitToPage(true);
        sheet.getPrintSetup().setLandscape(true);
        
        ArrayList<Disciplina> listDisciplinas = DisciplinaServer.getDisciplinasComAvaliacoes(idPeriodo);
        ArrayList<Usuario> listUsuario = UsuarioServer.getAlunosPorCurso(idCurso);
        Periodo periodo = PeriodoServer.getPeriodo(idPeriodo);
        Curso curso = CursoServer.getCurso(idCurso);
        
        ArrayList<ArrayList<String>> listMediaNotaAlunos = getMediaNotaAlunosNasDisciplinas(idCurso, listUsuario, listDisciplinas);
        
        int intColumn=0;
        int intLine=0;
        Row row = sheet.createRow((short) intLine);  
        row.createCell((short) intColumn).setCellValue("PLANILHA DE NOTAS     -     "+curso.getNome()+"     -     "+periodo.getNomePeriodo());
        row.getCell((short) intColumn).setCellStyle(ExcelFramework.getStyleTitleBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine,intLine,0,8));
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
            for (int i = 0; i < listAlunoNotas.size()-1; i++) { 
                String strText = listAlunoNotas.get(i+1);   
                row.createCell((short) i);
                
                if(i==0){
                    row.getCell((short) i).setCellValue(strText);                     
                    row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING); 
                    row.getCell((short) i).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb)); 
                }else{
                    if(FieldVerifier.isNumeric(strText)){
                        row.getCell((short) i).setCellValue(Double.parseDouble(strText));                     
                        row.getCell((short) i).setCellType(Cell.CELL_TYPE_NUMERIC); 
                    }else{
                        row.getCell((short) i).setCellValue(strText);                     
                        row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING); 
                    }
                    row.getCell((short) i).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));     
                }        
            }
        }    
        
        for (int i = 0; i < intColumn; i++) {   
            if(i==0){
                sheet.autoSizeColumn(i,true);
            }else{
                sheet.setColumnWidth(i, 2000);
            }
        }

        return ExcelFramework.getExcelAddress(wb,"GerarExcelBoletimPeriodo_");

    }
    
    public static String gerarExcelBoletimDisciplina(int idCurso, int idPeriodo, int idDisciplina) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Boletim Disciplina");
        sheet.setFitToPage(true);
        sheet.getPrintSetup().setLandscape(true);
        
        ArrayList<String> listHeader = AvaliacaoServer.getHeaderRelatorioBoletimDisciplina(idCurso, idPeriodo, idDisciplina);
        ArrayList<ArrayList<String>> listMediaNotaAlunos = getRelatorioBoletimDisciplina(idCurso, idPeriodo, idDisciplina);
        Curso curso = CursoServer.getCurso(idCurso);
        Periodo periodo = PeriodoServer.getPeriodo(idPeriodo);
        Disciplina disciplina = DisciplinaServer.getDisciplina(idDisciplina);        
        
        int intColumn=0;
        int intLine=0;
        Row row = sheet.createRow((short) intLine);  
        row.createCell((short) intColumn).setCellValue("PLANILHA DE NOTAS");
        row.getCell((short) intColumn).setCellStyle(ExcelFramework.getStyleTitleBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine,intLine,0,8));
        intLine++;
        
        row = sheet.createRow((short) intLine);  
        row.createCell((short) intColumn).setCellValue(curso.getNome()+"     -     "+periodo.getNomePeriodo()+"     -     "+disciplina.getNome());
        row.getCell((short) intColumn).setCellStyle(ExcelFramework.getStyleTitleBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine,intLine,0,8));
        intLine++;        
        
        row = sheet.createRow((short) intLine++);  
        row.createCell((short) intColumn).setCellValue("Aluno");
        row.getCell((short) intColumn++).setCellStyle(ExcelFramework.getStyleHeaderBoletim(wb));
        
        for (String strHeader : listHeader) {            
            
            int indexIdAvaliacao = strHeader.indexOf("|");
            if(indexIdAvaliacao!=-1){
                strHeader = strHeader.substring(indexIdAvaliacao+1);
            }
            row.createCell((short) intColumn).setCellValue(strHeader);
            row.getCell((short) intColumn).setCellType(Cell.CELL_TYPE_STRING);  
            row.getCell((short) intColumn++).setCellStyle(ExcelFramework.getStyleHeaderBoletim(wb));              
        }
        
        for (ArrayList<String> listAlunoNotas : listMediaNotaAlunos) {
            row = sheet.createRow((short) intLine++);
            for (int i = 0; i < listAlunoNotas.size()-1; i++) { 
                String strText = listAlunoNotas.get(i+1);   
                row.createCell((short) i);
                
                if(i==0){
                    row.getCell((short) i).setCellValue(strText);                     
                    row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING); 
                    row.getCell((short) i).setCellStyle(ExcelFramework.getStyleCellLeftBoletim(wb)); 
                }else{
                    if(FieldVerifier.isNumeric(strText)){
                        row.getCell((short) i).setCellValue(Double.parseDouble(strText));                     
                        row.getCell((short) i).setCellType(Cell.CELL_TYPE_NUMERIC); 
                    }else{
                        row.getCell((short) i).setCellValue(strText);                     
                        row.getCell((short) i).setCellType(Cell.CELL_TYPE_STRING); 
                    }
                    row.getCell((short) i).setCellStyle(ExcelFramework.getStyleCellCenterBoletim(wb));     
                }        
            }
        }
        
        for (int i = 0; i < intColumn; i++) {   
//            if(i==0){
                sheet.autoSizeColumn(i,true);
//            }else{
//                sheet.setColumnWidth(i, 6000);
//            }
        }
        
//        return "";
        return ExcelFramework.getExcelAddress(wb,"GerarExcelBoletimDisciplina_");
    }
    

    

}
