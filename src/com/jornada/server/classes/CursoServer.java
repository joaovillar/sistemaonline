 package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.Avaliacao;
import com.jornada.shared.classes.ConteudoProgramatico;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.Topico;
import com.jornada.shared.classes.Usuario;

public class CursoServer{

//	public static String DB_INSERT_CURSO = "INSERT INTO curso (nome_curso, descricao, ementa, data_inicial, data_final) VALUES (?,?,?,?,?)";
//	public static String DB_UPDATE_CURSO = "UPDATE curso set nome_curso=?, descricao=?, ementa=?, data_inicial=?, data_final=? where id_curso=?";
	public static String DB_INSERT_CURSO = "INSERT INTO curso (nome_curso, descricao, ementa, media_nota, porcentagem_presenca, data_inicial, data_final, status, ensino, ano) VALUES (?,?,?,?,?,?,?,?,?,?) returning id_curso";
	public static String DB_UPDATE_CURSO = "UPDATE curso set nome_curso=?, descricao=?, ementa=?, media_nota=?, porcentagem_presenca=?, data_inicial=?, data_final=?, status=?, ensino=?, ano=? where id_curso=?";	
	public static String DB_SELECT_CURSO_ILIKE = "SELECT * FROM curso where (nome_curso ilike ?) and status=? order by nome_curso asc";
	public static String DB_SELECT_CURSO_ALL = "SELECT * FROM curso order by nome_curso asc;";
	public static String DB_SELECT_CURSO_ALL_STATUS = "SELECT * FROM curso where status = ? order by nome_curso asc;";
	public static String DB_SELECT_CURSO_ID = "SELECT * FROM curso where id_curso=?;";
	public static String DB_DELETE_CURSO = "delete from curso where id_curso=?";
	public static String DB_DELETE_REL_CURSO_ALUNO = "delete from rel_curso_usuario where id_curso=?";
	public static String DB_INSERT_REL_CURSO_ALUNO = "INSERT INTO rel_curso_usuario (id_usuario, id_curso) VALUES (?,?)";
	
	public static String DB_SELECT_ALL_ALUNOS_POR_CURSO = 
			"select * from usuario where id_usuario in "+
			"( select id_usuario from rel_curso_usuario where id_curso=? ) "+
			"order by primeiro_nome, sobre_nome";
	
	public static String DB_SELECT_CURSO_ID_PAIS =  
			"select * from curso where id_curso in "+
			"( "+
			"	select id_curso from rel_curso_usuario where id_usuario in " +
			"	(  select id_usuario_aluno from rel_pai_aluno where id_usuario_pais=? ) " +
			"   group by id_curso "+
			")  and status=? ; ";
	
	public static String DB_SELECT_CURSO_ID_ALUNO =  
			"select * from curso where id_curso in " +
			"( select id_curso from rel_curso_usuario where id_usuario = ? group by id_curso ) and status=?;";
	
	public static String DB_SELECT_CURSO_ID_PROFESSOR = 
		"select * from curso where id_curso in( "+
		"	select id_curso from periodo where id_periodo in( "+
		"		select id_periodo from disciplina where id_usuario=? "+
		"	) "+
		") and status=? ; ";

	
	private static final long serialVersionUID = 8881085880718739029L;

	public CursoServer(){
		
	}
	
	public static int AdicionarCurso(Curso curso) {

		int idCurso=0;

		Connection conn = ConnectionManager.getConnection();
		try {

			Date date = new Date();

			if (curso.getDataInicial() == null) {
				curso.setDataInicial(date);
			}
			if (curso.getDataFinal() == null) {
				curso.setDataFinal(date);
			}

			int count = 0;
			PreparedStatement insertCurso = conn.prepareStatement(CursoServer.DB_INSERT_CURSO);
			insertCurso.setString(++count, curso.getNome());
			insertCurso.setString(++count, curso.getDescricao());
			insertCurso.setString(++count, curso.getEmenta());			
			insertCurso.setString(++count, curso.getMediaNota());
			insertCurso.setString(++count, curso.getPorcentagemPresenca());
			insertCurso.setDate(++count, new java.sql.Date(curso.getDataInicial().getTime()));
			insertCurso.setDate(++count, new java.sql.Date(curso.getDataFinal().getTime()));
			insertCurso.setBoolean(++count, curso.isStatus());
			insertCurso.setString(++count, curso.getEnsino());
			insertCurso.setString(++count, curso.getAno());
			
			ResultSet rs = insertCurso.executeQuery();			
			rs.next();
			
			idCurso = rs.getInt("id_curso");			



		} catch (SQLException sqlex) {
			idCurso=0;
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(conn);
		}
		
		return idCurso;

//		return isOperationDone;
	}
	
	public static String AdicionarCursoString(Curso curso) {

	    String success = "false";

        Connection conn = ConnectionManager.getConnection();
        try {

            Date date = new Date();

            if (curso.getDataInicial() == null) {
                curso.setDataInicial(date);
            }
            if (curso.getDataFinal() == null) {
                curso.setDataFinal(date);
            }

            int count = 0;
            PreparedStatement insertCurso = conn.prepareStatement(CursoServer.DB_INSERT_CURSO);
            insertCurso.setString(++count, curso.getNome());
            insertCurso.setString(++count, curso.getDescricao());
            insertCurso.setString(++count, curso.getEmenta());          
            insertCurso.setString(++count, curso.getMediaNota());
            insertCurso.setString(++count, curso.getPorcentagemPresenca());
            insertCurso.setDate(++count, new java.sql.Date(curso.getDataInicial().getTime()));
            insertCurso.setDate(++count, new java.sql.Date(curso.getDataFinal().getTime()));
            insertCurso.setBoolean(++count, curso.isStatus());
            insertCurso.setString(++count, curso.getEnsino());
            insertCurso.setString(++count, curso.getAno());
            
            ResultSet rs = insertCurso.executeQuery();          
            rs.next();
            
            success = "true";      



        } catch (SQLException sqlex) {
            success = sqlex.getMessage();
            System.err.println(sqlex.getMessage());
        } finally {
            // dataBase.close();
            ConnectionManager.closeConnection(conn);
        }
        
        return success;

//      return isOperationDone;
    }
	
	public static boolean AdicionarCursoTemplate(Curso curso, Integer[] idCursosImportarAluno) {

		boolean isOperationDone = false;
		
		int intNewIdCurso = CursoServer.AdicionarCurso(curso);
		curso.setIdCurso(intNewIdCurso);
		isOperationDone=true;
		
		for(int intPeriodos=0;intPeriodos<curso.getListPeriodos().size();intPeriodos++){
			
			Periodo periodo = curso.getListPeriodos().get(intPeriodos);
			periodo.setIdCurso(intNewIdCurso);
			periodo.setDataInicial(null);
			periodo.setDataFinal(null);	
			
			int intNewIdPeriodo = PeriodoServer.AdicionarPeriodo(periodo);
			isOperationDone=true;
			
			for(int intDisciplinas=0;intDisciplinas<periodo.getListDisciplinas().size();intDisciplinas++){
				
				Disciplina disciplina = periodo.getListDisciplinas().get(intDisciplinas);
				disciplina.setIdPeriodo(intNewIdPeriodo);	
				
				int intNewIdDisciplina = DisciplinaServer.AdicionarDisciplina(disciplina);
				
				Usuario professor = disciplina.getProfessor();
                if(professor!=null){
                    DisciplinaServer.updateDisciplinaComIdProfessor(professor.getIdUsuario(), intNewIdDisciplina);
                }				
                
                
                for(int intAvaliacao=0; intAvaliacao<disciplina.getListAvaliacao().size();intAvaliacao++){
                    Avaliacao avaliacao = disciplina.getListAvaliacao().get(intAvaliacao);
                    avaliacao.setIdDisciplina(intNewIdDisciplina);
                    AvaliacaoServer.Adicionar(avaliacao);
                    isOperationDone=true;
                }

				
				isOperationDone =true;
				
				for(int intConteudo=0;intConteudo<disciplina.getListConteudoProgramatico().size();intConteudo++){
					ConteudoProgramatico conteudo = disciplina.getListConteudoProgramatico().get(intConteudo);
					conteudo.setIdDisciplina(intNewIdDisciplina);
					 int intIdNewConteudo = ConteudoProgramaticoServer.Adicionar(conteudo);
					 conteudo.setIdConteudoProgramatico(intIdNewConteudo);
					 isOperationDone = true;
					 
					 
//					 for(int intAvaliacao=0; intAvaliacao<conteudo.getListAvaliacao().size();intAvaliacao++){
//						 Avaliacao avaliacao = conteudo.getListAvaliacao().get(intAvaliacao);
//						 avaliacao.setIdDisciplina(intIdNewConteudo);
//						 AvaliacaoServer.Adicionar(avaliacao);
//						 isOperationDone=true;
//					 }
					 
					for(int intTopico=0; intTopico<conteudo.getListTopico().size();intTopico++){
						Topico topico = conteudo.getListTopico().get(intTopico);
						topico.setIdConteudoProgramatico(intIdNewConteudo);
						int intIdNewTopico = TopicoServer.Adicionar(topico);
						topico.setIdTopico(intIdNewTopico);
						isOperationDone = true;
					}
					
				}
			}
			
		}
		
		HashSet<Integer> hashSetIdAlunosImportar = new HashSet<Integer>();
		
		for(int i=0; i<idCursosImportarAluno.length;i++){
			ArrayList<Usuario> listUsuario = new ArrayList<Usuario>();
			listUsuario.addAll(UsuarioServer.getAlunosPorCurso(idCursosImportarAluno[i]));
			
			for(int cv=0;cv<listUsuario.size();cv++){
				hashSetIdAlunosImportar.add(listUsuario.get(cv).getIdUsuario());
			}
		}
		
		ArrayList<Integer> listIdAlunosImportar = new ArrayList<Integer>(hashSetIdAlunosImportar);
		
		isOperationDone = CursoServer.associarAlunosAoCurso(intNewIdCurso, listIdAlunosImportar);
		
//		for(int i=0;i<listIdAlunosImportar.size();i++){
//			Usuario aluno = hashSetIdAlunosImportar.get(i);
////			insertedOk = UsuarioServer.AdicionarUsuario(aluno);			
//			isOperationDone = CursoServer.associarAlunosAoCurso(intNewIdCurso, listIdAlunosImportar);
//		}

		return isOperationDone;
	}	

	
	public static String updateCursoRow(Curso curso){
	    String success="false";

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();
			
			int count = 0;
			PreparedStatement updateCurso = conn.prepareStatement(CursoServer.DB_UPDATE_CURSO);
			updateCurso.setString(++count, curso.getNome());
			updateCurso.setString(++count, curso.getDescricao());
			updateCurso.setString(++count, curso.getEmenta());
			updateCurso.setString(++count, curso.getMediaNota());			
			updateCurso.setString(++count, curso.getPorcentagemPresenca());
			updateCurso.setDate(++count, new java.sql.Date(curso.getDataInicial().getTime()));
			updateCurso.setDate(++count, new java.sql.Date(curso.getDataFinal().getTime()));
            updateCurso.setBoolean(++count, curso.isStatus());
            updateCurso.setString(++count, curso.getEnsino());
            updateCurso.setString(++count, curso.getAno());
			updateCurso.setInt(++count, curso.getIdCurso());

			int numberUpdate = updateCurso.executeUpdate();


			if (numberUpdate == 1) {
			    success = "true";
			}


		} catch (SQLException sqlex) {
		    success=sqlex.getMessage();
			System.err.println(sqlex.getMessage());			
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}
		
		return success;
	}	
	
	
	public static boolean deleteCursoRow(int id_curso){
		
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			int count = 0;
			PreparedStatement deleteCurso = conn.prepareStatement(CursoServer.DB_DELETE_CURSO);
			deleteCurso.setInt(++count, id_curso);

			int numberUpdate = deleteCurso.executeUpdate();


			if (numberUpdate == 1) {
				success = true;
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
	
	
	public static ArrayList<Curso> getCursos(){
		ArrayList<Curso> data = new ArrayList<Curso>();		
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement psCurso = conn.prepareStatement(CursoServer.DB_SELECT_CURSO_ALL);
			
			data = getCursoParameters(psCurso.executeQuery());

		} catch (SQLException sqlex) {			
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}
		return data;
	}	
	
	   public static ArrayList<Curso> getCursos(boolean status){
	        ArrayList<Curso> data = new ArrayList<Curso>();     
//	      JornadaDataBase dataBase = new JornadaDataBase();
	        Connection conn = ConnectionManager.getConnection();
	        try 
	        {
//	          dataBase.createConnection();            
//	          Connection connection = dataBase.getConnection();

	            PreparedStatement psCurso = conn.prepareStatement(CursoServer.DB_SELECT_CURSO_ALL_STATUS);
	            int count=0;
	            psCurso.setBoolean(++count, status);
	            data = getCursoParameters(psCurso.executeQuery());

	        } catch (SQLException sqlex) {          
	            data=null;
	            System.err.println(sqlex.getMessage());
	        } finally {
//	          dataBase.close();
	            ConnectionManager.closeConnection(conn);
	        }
	        return data;
	    }   
	
	
	public static Curso getCurso(int idCurso) {
		ArrayList<Curso> data = new ArrayList<Curso>();		
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();


			PreparedStatement ps = conn.prepareStatement(CursoServer.DB_SELECT_CURSO_ID);
			int count=0;
			ps.setInt(++count, idCurso);
			
			data = getCursoParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}
		
		if(data==null || data.size()==0){
			return null;
		}
		else{
			return data.get(0);
		}

	}		
	
	public static ArrayList<Curso> getCursosPorPaiAmbientePais(Usuario usuario, Boolean status) {
		ArrayList<Curso> data = new ArrayList<Curso>();		
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = conn.prepareStatement(CursoServer.DB_SELECT_CURSO_ID_PAIS);
			
			int count=0;
			ps.setInt(++count, usuario.getIdUsuario());
			ps.setBoolean(++count, status);
			
			data = getCursoParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}
		return data;
	}		
	

	public static ArrayList<Curso> getCursosPorAlunoAmbienteAluno(Usuario usuario, Boolean status) {
		ArrayList<Curso> data = new ArrayList<Curso>();		
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = conn.prepareStatement(CursoServer.DB_SELECT_CURSO_ID_ALUNO);
			
			int count=0;
			ps.setInt(++count, usuario.getIdUsuario());
			ps.setBoolean(++count, status);
			
			data = getCursoParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
			
		}
		return data;
	}		
	
	
	public static ArrayList<Curso> getCursosAmbienteProfessor(Usuario usuario, Boolean status) {
		ArrayList<Curso> data = new ArrayList<Curso>();		
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = conn.prepareStatement(CursoServer.DB_SELECT_CURSO_ID_PROFESSOR);
			
			int count=0;
			ps.setInt(++count, usuario.getIdUsuario());
			ps.setBoolean(++count, status);
			
			data = getCursoParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}
		return data;
	}		
	
	public static ArrayList<Curso> getCursos(String strFilter, boolean status) {

		ArrayList<Curso> data = new ArrayList<Curso>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement psCurso = conn.prepareStatement(CursoServer.DB_SELECT_CURSO_ILIKE);
			
			int count=0;
			psCurso.setString(++count, strFilter);
			psCurso.setBoolean(++count, status);
			
			
			data = getCursoParameters(psCurso.executeQuery());

//			ResultSet rs = psCurso.executeQuery();
//			while (rs.next()) 
//			{
//
//				Curso currentCurso = new Curso();
//				
//				currentCurso.setIdCurso(rs.getInt("id_curso"));
//				currentCurso.setNome(rs.getString("nome_curso"));
//				currentCurso.setDescricao(rs.getString("descricao"));
//				currentCurso.setEmenta(rs.getString("ementa"));
//				currentCurso.setDataInicial(rs.getDate("data_inicial"));
//				currentCurso.setDataFinal(rs.getDate("data_final"));
////				currentCurso.put("id_departamento", rs.getDate("id_departamento"));
//
//				data.add(currentCurso);
//			}

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}

		return data;

	}	
	
	public static ArrayList<Usuario> getTodosOsAlunosDoCurso(int id_curso) {

		ArrayList<Usuario> data = new ArrayList<Usuario>();
		Connection conn = ConnectionManager.getConnection();
		try 
		{
			PreparedStatement ps = conn.prepareStatement(CursoServer.DB_SELECT_ALL_ALUNOS_POR_CURSO);
			
			int count=0;
			ps.setInt(++count, id_curso);
			
			data = UsuarioServer.getUserParameters(ps.executeQuery());


		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(conn);
		}

		return data;

	}	
		
	public static boolean associarAlunosAoCurso(int id_curso, ArrayList<Integer> list_id_aluno){

		boolean success = false;

//		JornadaDataBase dataBase = new JornadaDataBase();
//		Connection conn = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();
			
			deleteAssociacaoCursoAluno(id_curso);
			insertAssociacaoCursoAluno(id_curso, list_id_aluno);
			
//			deleteAssociacaoCursoAluno(conn, id_curso);
//			insertAssociacaoCursoAluno(conn, id_curso, list_id_aluno);
			success=true;

		} catch (Exception ex) {
			success = false;
			System.err.println(ex.getMessage());
		} finally {
			// dataBase.close();
//			ConnectionManager.closeConnection(conn);
		}

		return success;
	}	
	
	private static int deleteAssociacaoCursoAluno(int id_curso){
		
		int count = 0;
		int deleted = 0;

		Connection conn = ConnectionManager.getConnection();
		
		try {
			
//			Connection connection = conn;			

			PreparedStatement psDelete = conn.prepareStatement(CursoServer.DB_DELETE_REL_CURSO_ALUNO);
			psDelete.setInt(++count, id_curso);
			deleted = psDelete.executeUpdate();

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
//			ConnectionManager.closeConnection(conn);
			ConnectionManager.closeConnection(conn);
		}
		
		return deleted;
		
	}
	
	private static int insertAssociacaoCursoAluno(int id_curso, ArrayList<Integer> list_id_aluno){
		
		int count = 0;
		int intAddedItems = 0;
		
		Connection conn = ConnectionManager.getConnection();
		
		try {
			
//			Connection connection = conn;

			for(int i=0;i<list_id_aluno.size();i++){
				count=0;
				int id_usuario = list_id_aluno.get(i);
				PreparedStatement psInsert = conn.prepareStatement(CursoServer.DB_INSERT_REL_CURSO_ALUNO);
				psInsert.setInt(++count, id_usuario);				
				psInsert.setInt(++count, id_curso);	
				intAddedItems=psInsert.executeUpdate();					

			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(conn);
		}
		
		return intAddedItems;
		
	}	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}		
		
	private static ArrayList<Curso> getCursoParameters(ResultSet rs){

		ArrayList<Curso> data = new ArrayList<Curso>();
		
		try{
		
		while (rs.next()) 		{
			Curso object = new Curso();			
			object.setIdCurso(rs.getInt("id_curso"));
			object.setNome(rs.getString("nome_curso"));
			object.setDescricao(rs.getString("descricao"));
			object.setEmenta(rs.getString("ementa"));
			object.setStatus(rs.getBoolean("status"));
			object.setDataInicial(rs.getDate("data_inicial"));
			object.setDataFinal(rs.getDate("data_final"));
			object.setMediaNota(rs.getString("media_nota"));
			object.setPorcentagemPresenca(rs.getString("porcentagem_presenca"));
			object.setEnsino(rs.getString("ensino"));
			object.setAno(rs.getString("ano"));
//			currentCurso.put("id_departamento", rs.getDate("id_departamento"));
			data.add(object);
		}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}		
		return data;
	}	

}

