package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jornada.server.classes.utility.MpUtilServer;
import com.jornada.server.database.ConnectionManager;
import com.jornada.server.framework.excel.ExcelFramework;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.Nota;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.boletim.TabelaBoletim;

@SuppressWarnings("deprecation")
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

        return getMediaNotaAlunosNasDisciplinas(listUsuario, listDisciplinas);
    }
    
    
    public static ArrayList<ArrayList<String>> getMediaNotaAlunosNasDisciplinas(ArrayList<Usuario> listUsuario, ArrayList<Disciplina> listDisciplinas){
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        
        for (Usuario usuario : listUsuario) {
            ArrayList<String> item = new ArrayList<String>();
            
            item.add(usuario.getPrimeiroNome() + " "+usuario.getSobreNome());
            for (Disciplina disciplina : listDisciplinas) {
                String strMedia = disciplina.getMediaAlunoDisciplina(usuario.getIdUsuario());
                if(strMedia==null || strMedia.isEmpty()){
                    strMedia = "-";
                    item.add(strMedia);
                }else{
                    double doubleMediaAluno = Double.parseDouble(strMedia);
                    item.add(MpUtilServer.getDecimalFormated(doubleMediaAluno));
                }
            }
            item.add(Integer.toString(usuario.getIdUsuario()));
            list.add(item);
        }
       
        return list;
    }
    
    
    public static String gerarExcelBoletimPeriodo(int idCurso, int idPeriodo) {
       

        XSSFWorkbook wb = new XSSFWorkbook();

        
        // /Creating Tabs
        XSSFSheet sheet = wb.createSheet("Boletim Periodo");
        
        ArrayList<Disciplina> listDisciplinas = DisciplinaServer.getDisciplinasComAvaliacoes(idPeriodo);
        ArrayList<Usuario> listUsuario = UsuarioServer.getAlunosPorCurso(idCurso);
        Periodo periodo = PeriodoServer.getPeriodo(idPeriodo);
        Curso curso = CursoServer.getCurso(idCurso);
        
        ArrayList<ArrayList<String>> listMediaNotaAlunos = getMediaNotaAlunosNasDisciplinas(listUsuario, listDisciplinas);
        
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
                String strText = listAlunoNotas.get(i);   
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
    
    

    

}
