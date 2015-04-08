package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.ConteudoProgramatico;

public class ConteudoProgramaticoServer {
	
	
	public static String DB_INSERT_CONTEUDO_PROGRAMATICO = "INSERT INTO conteudo_programatico (nome_conteudo_programatico, numeracao, descricao, objetivo, id_disciplina) VALUES (?,?,?,?,?) returning id_conteudo_programatico;";
	public static String DB_UPDATE_CONTEUDO_PROGRAMATICO = "UPDATE conteudo_programatico set nome_conteudo_programatico=?, numeracao=?, descricao=?, objetivo=?, id_disciplina=? where id_conteudo_programatico=?;";
	public static String DB_SELECT_CONTEUDO_PROGRAMATICO = "SELECT * FROM conteudo_programatico where (nome_conteudo_programatico ilike ?);";
	public static String DB_SELECT_ALL_CONTEUDO_PROGRAMATICO = "SELECT * FROM conteudo_programatico order by nome_conteudo_programatico asc;";
	public static String DB_SELECT_CONTEUDO_PROGRAMATICO_PELA_DISCIPLINA = "SELECT * FROM conteudo_programatico where id_disciplina=? order by numeracao asc;";
	public static String DB_DELETE_CONTEUDO_PROGRAMATICO = "delete from conteudo_programatico where id_conteudo_programatico=?;";		

	
	public ConteudoProgramaticoServer(){
		
	}

	
	public static int Adicionar(ConteudoProgramatico conteudoProgramatico) {
		
		int idConteudo=0;
		
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
//			Connection conn = dataBase.getConnection();
			
				int param = 0;
				PreparedStatement ps = conn.prepareStatement(DB_INSERT_CONTEUDO_PROGRAMATICO);
				ps.setString(++param, conteudoProgramatico.getNome().trim());
				ps.setString(++param, conteudoProgramatico.getNumeracao());				
				ps.setString(++param, conteudoProgramatico.getDescricao());
				ps.setString(++param, conteudoProgramatico.getObjetivo());
				ps.setInt(++param, conteudoProgramatico.getIdDisciplina());				
				
				ResultSet rs = ps.executeQuery();			
				rs.next();
				
				idConteudo = rs.getInt("id_conteudo_programatico");	
			
		}
		catch(Exception ex)
		{
			idConteudo=0;
			System.err.println(ex.getMessage());
		}
		finally
		{
			ConnectionManager.closeConnection(conn);
		}
		
		return idConteudo;
		
	}
	
	
//	public static ArrayList<ConteudoProgramatico> getConteudoProgramatico(int idDisciplina) {
//
//		ArrayList<ConteudoProgramatico> data = new ArrayList<ConteudoProgramatico>();
//		JornadaDataBase dataBase = new JornadaDataBase();
//
//		try 
//		{
//
//			dataBase.createConnection();
//			
//			Connection connection = dataBase.getConnection();
//
//			PreparedStatement ps = connection.prepareStatement(DB_SELECT_CONTEUDO_PROGRAMATICO);
//			
//			int count=0;
//			ps.setInt(++count, idDisciplina);			
//
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) 
//			{
//
//				ConteudoProgramatico current = new ConteudoProgramatico();
//				
//				current.setIdConteudoProgramatico(rs.getInt("id_conteudo_programatico"));
//				current.setIdDisciplina(rs.getInt("id_disciplina"));				
//				current.setNome(rs.getString("nome_conteudo_programatico"));				
//				current.setNumeracao(rs.getString("numeracao"));
//				current.setDescricao(rs.getString("descricao"));
//				current.setObjetivo(rs.getString("objetivo"));
//
//				data.add(current);
//			}
//			
//			for(ConteudoProgramatico conteudo : data){
//				Disciplina periodo = DisciplinaServer.getDisciplina(conteudo.getIdDisciplina());
//				conteudo.setDi(periodo);
//			}		
//
//		} catch (SQLException sqlex) {
//			System.err.println(sqlex.getMessage());
//		} finally {
////			dataBase.close();
//		}
//		
//		
//
//
//		return data;
//
//	}	
	
	
	public static ArrayList<ConteudoProgramatico> getConteudoProgramaticos(String strFilter) {

		ArrayList<ConteudoProgramatico> data = new ArrayList<ConteudoProgramatico>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();

		try 
		{

//			dataBase.createConnection();

			PreparedStatement ps = conn.prepareStatement(DB_SELECT_CONTEUDO_PROGRAMATICO);
			
			int count=0;
			ps.setString(++count, strFilter);			

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				ConteudoProgramatico current = new ConteudoProgramatico();
				
				current.setIdConteudoProgramatico(rs.getInt("id_conteudo_programatico"));
				current.setIdDisciplina(rs.getInt("id_disciplina"));
				current.setNome(rs.getString("nome_conteudo_programatico"));
				current.setNumeracao(rs.getString("numeracao"));				
				current.setDescricao(rs.getString("descricao"));
				current.setObjetivo(rs.getString("objetivo"));

				data.add(current);
			}
			
//			for(Disciplina disciplina : data){
//				Periodo periodo = PeriodoServer.getPeriodo(connection, disciplina.getIdPeriodo());
//				disciplina.setPeriodo(periodo);
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
		
	public static ArrayList<ConteudoProgramatico> getConteudoProgramaticos(int id_disciplina) {

		ArrayList<ConteudoProgramatico> data = new ArrayList<ConteudoProgramatico>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();
			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = conn.prepareStatement(DB_SELECT_CONTEUDO_PROGRAMATICO_PELA_DISCIPLINA);
			
			int count=0;
			ps.setInt(++count, id_disciplina);			

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				ConteudoProgramatico current = new ConteudoProgramatico();
				
				current.setIdConteudoProgramatico(rs.getInt("id_conteudo_programatico"));
				current.setIdDisciplina(rs.getInt("id_disciplina"));
				current.setNome(rs.getString("nome_conteudo_programatico"));				
				current.setNumeracao(rs.getString("numeracao"));
				current.setDescricao(rs.getString("descricao"));
				current.setObjetivo(rs.getString("objetivo"));


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
	
//	public static ArrayList<Periodo> getPeriodos(){
//		return PeriodoServer.getPeriodos();
//	}
	
	public static ArrayList<ConteudoProgramatico> getConteudoProgramaticos() {

		ArrayList<ConteudoProgramatico> data = new ArrayList<ConteudoProgramatico>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();
			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = conn.prepareStatement(DB_SELECT_ALL_CONTEUDO_PROGRAMATICO);
			
	    	ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				ConteudoProgramatico currentDisciplina = new ConteudoProgramatico();
				
				currentDisciplina.setIdConteudoProgramatico(rs.getInt("id_conteudo_programatico"));
				currentDisciplina.setIdDisciplina(rs.getInt("id_disciplina"));
				currentDisciplina.setNome(rs.getString("nome_conteudo_programatico"));
				currentDisciplina.setNumeracao(rs.getString("numeracao"));
				currentDisciplina.setDescricao(rs.getString("descricao"));
				currentDisciplina.setObjetivo(rs.getString("objetivo"));

				data.add(currentDisciplina);
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
	
	public static boolean deleteConteudoProgramaticoRow(int id_conteudo_programatico){
		
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			int count = 0;
			PreparedStatement ps = conn.prepareStatement(DB_DELETE_CONTEUDO_PROGRAMATICO);
			ps.setInt(++count, id_conteudo_programatico);

			int numberUpdate = ps.executeUpdate();


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
	
	public static boolean updateConteudoProgramaticoRow(ConteudoProgramatico conteudoProgramatico){
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			int count = 0;
			PreparedStatement ps = conn.prepareStatement(DB_UPDATE_CONTEUDO_PROGRAMATICO);
			ps.setString(++count, conteudoProgramatico.getNome().trim());
			ps.setString(++count, conteudoProgramatico.getNumeracao());
			ps.setString(++count, conteudoProgramatico.getDescricao());
			ps.setString(++count, conteudoProgramatico.getObjetivo());
			ps.setInt(++count, conteudoProgramatico.getIdDisciplina());
			ps.setInt(++count, conteudoProgramatico.getIdConteudoProgramatico());

			int numberUpdate = ps.executeUpdate();


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
		

}
