package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.Aula;

public class AulaServer {
	
	
	public static final String DB_INSERT_AULA = "insert into aula (id_disciplina, data) values (?,?) returning id_aula;";
	public static final String DB_DELETE_AULA = "delete from aula where id_aula=?";
	public static final String DB_SELECT_AULA = "SELECT * FROM aula order by data asc;";
	public static final String DB_SELECT_AULA_POR_ID_DISCIPLINA = "SELECT * FROM aula where id_disciplina=? order by data asc;";
	
	
	public static int AdicionarAula(Aula aula){
		
		int idAula=0;

		Connection conn = ConnectionManager.getConnection();
		try {

			Date date = new Date();

			if (aula.getData() == null) {
				aula.setData(date);
			}

			int count = 0;
			PreparedStatement insert = conn.prepareStatement(DB_INSERT_AULA);
			insert.setInt(++count, aula.getIdDisciplina());
			insert.setDate(++count, new java.sql.Date(aula.getData().getTime()));
			
			ResultSet rs = insert.executeQuery();			
			rs.next();
			
			idAula = rs.getInt("id_aula");	

		} catch (SQLException sqlex) {
			idAula=0;
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(conn);
		}
		
		return idAula;

	}

	public static boolean deleteAula(int idAula) {

		boolean success = false;

		// JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {
			// dataBase.createConnection();
			// Connection connection = dataBase.getConnection();

			int count = 0;
			PreparedStatement delete = conn.prepareStatement(DB_DELETE_AULA);
			delete.setInt(++count, idAula);

			int numberUpdate = delete.executeUpdate();

			if (numberUpdate == 1) {
				success = true;
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
	
	public static ArrayList<Aula> getAulas() {
		ArrayList<Aula> data = new ArrayList<Aula>();		
		Connection conn = ConnectionManager.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(DB_SELECT_AULA);			
			data = getParameters(ps.executeQuery());
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return data;
	}	
	
	public static ArrayList<Aula> getAulas(int idDisciplina) {
		ArrayList<Aula> data = new ArrayList<Aula>();		
		Connection conn = ConnectionManager.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(DB_SELECT_AULA_POR_ID_DISCIPLINA);
			int count=0;
			ps.setInt(++count, idDisciplina);
			data = getParameters(ps.executeQuery());
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return data;
	}	
		
	
	
	
	
	private static ArrayList<Aula> getParameters(ResultSet rs){

		ArrayList<Aula> data = new ArrayList<Aula>();
		
		try{
		
		while (rs.next()) 		{
			Aula object = new Aula();			
			object.setIdAula(rs.getInt("id_aula"));
			object.setIdDisciplina(rs.getInt("id_disciplina"));
			object.setData(rs.getDate("data"));
			data.add(object);
		}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}		
		return data;
	}
	
}
