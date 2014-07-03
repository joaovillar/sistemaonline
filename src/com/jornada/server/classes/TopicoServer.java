package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jornada.server.database.ConnectionManager;
import com.jornada.server.database.JornadaDataBase;
import com.jornada.shared.classes.Topico;

public class TopicoServer {
	
	
	public static String DB_INSERT_TOPICO = "INSERT INTO topico (nome_topico, numeracao, descricao, objetivo, id_conteudo_programatico) VALUES (?,?,?,?,?) returning id_topico;";
	public static String DB_UPDATE_TOPICO = "UPDATE topico set nome_topico=?, numeracao=?, descricao=?, objetivo=?, id_conteudo_programatico=? where id_topico=?;";
	public static String DB_SELECT_TOPICO = "SELECT * FROM topico where (nome_topico ilike ?);";
	public static String DB_SELECT_ALL_TOPICO = "SELECT * FROM topico order by nome_topico asc;";
	public static String DB_SELECT_TOPICO_PELO_CONTEUDO_PROGRAMATICO = "SELECT * FROM topico where id_conteudo_programatico=? order by numeracao asc;";
	public static String DB_DELETE_TOPICO = "delete from topico where id_topico=?;";		

	
	public TopicoServer(){
		
	}

	
	public static int Adicionar(Topico topico) {
		
		int idTopico=0;
		
		JornadaDataBase dataBase = new JornadaDataBase();

		try {
			dataBase.createConnection();
			Connection conn = dataBase.getConnection();
			
				int param = 0;
				PreparedStatement ps = conn.prepareStatement(DB_INSERT_TOPICO);
				ps.setString(++param, topico.getNome());
				ps.setString(++param, topico.getNumeracao());				
				ps.setString(++param, topico.getDescricao());
				ps.setString(++param, topico.getObjetivo());
				ps.setInt(++param, topico.getIdConteudoProgramatico());				
				
				ResultSet rs = ps.executeQuery();			
				rs.next();
				
				idTopico = rs.getInt("id_topico");
			
//			
//			if(numberUpdate == 1)
//			{
//				isOperationDone = true;
//			}
			
		}
		catch(Exception ex)
		{
			idTopico=0;
			System.err.println(ex.getMessage());
		}
		finally
		{
		}
		
		return idTopico;
	}
	
	

	
	public static ArrayList<Topico> getTopicos(String strFilter) {

		ArrayList<Topico> data = new ArrayList<Topico>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = connection.prepareStatement(DB_SELECT_TOPICO);
			
			int count=0;
			ps.setString(++count, strFilter);			

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				Topico current = new Topico();
				
				current.setIdConteudoProgramatico(rs.getInt("id_conteudo_programatico"));
				current.setIdTopico(rs.getInt("id_topico"));
				current.setNome(rs.getString("nome_topico"));
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
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}
		
		


		return data;

	}
		
	public static ArrayList<Topico> getTopicos(int id_conteudo_programatico) {

		ArrayList<Topico> data = new ArrayList<Topico>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = connection.prepareStatement(DB_SELECT_TOPICO_PELO_CONTEUDO_PROGRAMATICO);
			
			int count=0;
			ps.setInt(++count, id_conteudo_programatico);			

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				Topico current = new Topico();
				
				current.setIdConteudoProgramatico(rs.getInt("id_conteudo_programatico"));
				current.setIdTopico(rs.getInt("id_topico"));
				current.setNome(rs.getString("nome_topico"));				
				current.setNumeracao(rs.getString("numeracao"));
				current.setDescricao(rs.getString("descricao"));
				current.setObjetivo(rs.getString("objetivo"));


				data.add(current);
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}	
	

	public static ArrayList<Topico> getTopicos() {

		ArrayList<Topico> data = new ArrayList<Topico>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = connection.prepareStatement(DB_SELECT_ALL_TOPICO);
			
	    	ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				Topico current = new Topico();
				
				current.setIdConteudoProgramatico(rs.getInt("id_conteudo_programatico"));
				current.setIdTopico(rs.getInt("id_topico"));
				current.setNome(rs.getString("nome_topico"));
				current.setNumeracao(rs.getString("numeracao"));
				current.setDescricao(rs.getString("descricao"));
				current.setObjetivo(rs.getString("objetivo"));

				data.add(current);
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}	
	
	public static boolean deleteTopicoRow(int id_topico){
		
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			int count = 0;
			PreparedStatement ps = connection.prepareStatement(DB_DELETE_TOPICO);
			ps.setInt(++count, id_topico);

			int numberUpdate = ps.executeUpdate();


			if (numberUpdate == 1) {
				success = true;
			}


		} catch (SQLException sqlex) {
			success=false;
			System.err.println(sqlex.getMessage());			
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}
		
		return success;
	}		
	
	public static boolean updateTopicoRow(Topico topico){
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			int count = 0;
			PreparedStatement ps = connection.prepareStatement(DB_UPDATE_TOPICO);
			ps.setString(++count, topico.getNome());
			ps.setString(++count, topico.getNumeracao());
			ps.setString(++count, topico.getDescricao());
			ps.setString(++count, topico.getObjetivo());
			ps.setInt(++count, topico.getIdConteudoProgramatico());
			ps.setInt(++count, topico.getIdTopico());

			int numberUpdate = ps.executeUpdate();


			if (numberUpdate == 1) {
				success = true;
			}


		} catch (SQLException sqlex) {
			success=false;
			System.err.println(sqlex.getMessage());			
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}		


		return success;
	}	
		

}
