package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
    
    
    public static ArrayList<ArrayList<String>> getBoletimAnual(int idCurso) {
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
        
        listNotas.add(listDisciplinasAnoAluno);
        for(Usuario usuario : listUsuario){
            ArrayList<String> array = new ArrayList<String>();
            array.add(Integer.toString(usuario.getIdUsuario()));
            array.add(usuario.getPrimeiroNome() + " " + usuario.getSobreNome());
            
            for(int i=2;i<listDisciplinasAnoAluno.size();i++){
                String nomeDisciplina = listDisciplinasAnoAluno.get(i);
//                String text = getMediaAnoAlunoDisciplina(usuario.getIdUsuario(), idCurso,listPeriodo,nomeDisciplina) + "||"+nomeDisciplina;\
                String text = getMediaAnoAlunoDisciplina(usuario.getIdUsuario(), idCurso,listPeriodo,nomeDisciplina);
                array.add(text);
            }
            
            listNotas.add(array);      
        }
        System.out.println(listNotas.toString());
        

        return listNotas;
    }
    
    
    
    public static String getMediaAnoAlunoDisciplina(int idUsuario, int idCurso, ArrayList<Periodo> listPeriodo, String strDisciplina){
        String strMedia="";
        double doubleMediaAluno=0;
        int countMedia=0;
       
        for (Periodo periodo : listPeriodo) {
            
            ArrayList<Disciplina> listDisciplina = DisciplinaServer.getDisciplinas(periodo.getIdPeriodo());
            
            for (Disciplina disciplina : listDisciplina) {              
                
                if (disciplina.getNome().equals(strDisciplina)) {
                    
                    disciplina.setListAvaliacao(AvaliacaoServer.getAvaliacaoComNotas(disciplina.getIdDisciplina()));
                    strMedia = disciplina.getMediaAlunoDisciplina(idUsuario);
                    if(strMedia==null || strMedia.isEmpty()){}else{
                        doubleMediaAluno = doubleMediaAluno + Double.parseDouble(strMedia);
                        countMedia++;
                        break;
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
        
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();   
        ArrayList<Usuario> listUsuario = UsuarioServer.getAlunosPorCurso(idCurso);       
        
        
        Disciplina disciplina = DisciplinaServer.getDisciplina(idDisciplina);
        disciplina.setListAvaliacao(AvaliacaoServer.getAvaliacaoComNotas(idDisciplina));
                
        for (Usuario usuario : listUsuario) {
            ArrayList<String> item = new ArrayList<String>();
            item.add(Integer.toString(usuario.getIdUsuario()));
            item.add(usuario.getPrimeiroNome()+" "+usuario.getSobreNome());
            
            for (Avaliacao avaliacao : disciplina.getListAvaliacao()) {
                
                String strNota="";
                for(Nota nota : avaliacao.getListNota()){
                    if(nota.getIdUsuario()==usuario.getIdUsuario()){
                        if(avaliacao.getIdAvaliacao()==nota.getIdAvaliacao()){
                            strNota = nota.getNota();
                            break;
                        }
                    }
                }
                if(strNota==null || strNota.isEmpty()){
                    strNota = "-";
                }
                item.add(strNota);
            }
            list.add(item);
        }        

        
        for (int i=0;i<list.size();i++) {
            int idUsuario = Integer.parseInt(list.get(i).get(0));
            String strMedia = disciplina.getMediaAlunoDisciplina(idUsuario);
            if(strMedia==null || strMedia.isEmpty()){
                strMedia = "-";
                list.get(i).add(strMedia);
                list.get(i).add(strMedia);
            }else{
                double doubleMediaAluno = Double.parseDouble(strMedia);
                list.get(i).add(MpUtilServer.getDecimalFormatedTwoDecimal(doubleMediaAluno));
                list.get(i).add(MpUtilServer.getDecimalFormatedOneDecimal(doubleMediaAluno));
            }
            
        }

        return list;
    }
    
    
    public static ArrayList<ArrayList<String>> getMediaNotaAlunosNasDisciplinas(ArrayList<Usuario> listUsuario, ArrayList<Disciplina> listDisciplinas){
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        
        for (Usuario usuario : listUsuario) {
            ArrayList<String> item = new ArrayList<String>();
            
            item.add(Integer.toString(usuario.getIdUsuario()));
            item.add(usuario.getPrimeiroNome() + " "+usuario.getSobreNome());
            for (Disciplina disciplina : listDisciplinas) {
                String strMedia = disciplina.getMediaAlunoDisciplina(usuario.getIdUsuario());
                if(strMedia==null || strMedia.isEmpty()){
                    strMedia = "-";
                    item.add(strMedia);
                }else{
                    double doubleMediaAluno = Double.parseDouble(strMedia);
                    item.add(MpUtilServer.getDecimalFormatedOneDecimal(doubleMediaAluno));
                }
            }
            
            list.add(item);
        }
       
        return list;
    }
    
    
    public static String getExcelBoletimAnual(int idCurso) {
        XSSFWorkbook wb = new XSSFWorkbook();

        // /Creating Tabs
        XSSFSheet sheet = wb.createSheet("Boletim Anual");
        
        ArrayList<ArrayList<String>> listNotas = getBoletimAnual(idCurso);
        
        int intColumn=0;
        int intLine=0;
        Row row = sheet.createRow((short) intLine);  
        row.createCell((short) intColumn).setCellValue("PLANILHA DE NOTAS ");
        row.getCell((short) intColumn).setCellStyle(ExcelFramework.getStyleTitleBoletim(wb));
        sheet.addMergedRegion(new CellRangeAddress(intLine,intLine,0,8));
        intLine++;
        row = sheet.createRow((short) intLine++);  
        
        ArrayList<String> listHeader = listNotas.get(0);
        
        for(int i=1;i<listHeader.size();i++){
            String header = "";
            if(i==1){
                header = listHeader.get(i);
            }else{
                header = Curso.getAbreviarNomeCurso(listHeader.get(i));
            }
            
            row.createCell((short) intColumn).setCellValue(header);
            row.getCell((short) intColumn).setCellType(Cell.CELL_TYPE_STRING);  
            row.getCell((short) intColumn++).setCellStyle(ExcelFramework.getStyleHeaderBoletim(wb));  
        }
//        intLine++;
        
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
                sheet.autoSizeColumn(i,true);
            }else{
                sheet.setColumnWidth(i, 2000);
            }
        }
        
        return ExcelFramework.getExcelAddress(wb,"GerarExcelBoletimAnual_");
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
