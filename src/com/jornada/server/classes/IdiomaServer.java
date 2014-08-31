package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.Idioma;

public class IdiomaServer {
	
	
	public static String DB_SELECT_IDIOMA_ALL = "SELECT * FROM idioma order by locale asc;";
	public static String DB_SELECT_ID_IDIOMA_BY_LOCALE = "SELECT id_idioma FROM idioma where locale=?;";
	public static String DB_SELECT_IDIOMA_BY_ID = "SELECT * FROM idioma where id_idioma=?;";
	
	
	
	public static int getIdIdioma(String strLocale){
		int idIdioma=0;
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement psCurso = conn.prepareStatement(DB_SELECT_ID_IDIOMA_BY_LOCALE);
			
			psCurso.setString(1, strLocale);
			
			ResultSet rs = psCurso.executeQuery();
			
			rs.next();
			
			idIdioma = rs.getInt("id_idioma");
			
			

		} catch (SQLException sqlex) {
			idIdioma=0;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}
		return idIdioma;		
	}
	
	
	public static ArrayList<Idioma> getIdiomas() {
		ArrayList<Idioma> data = new ArrayList<Idioma>();		
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement psCurso = conn.prepareStatement(DB_SELECT_IDIOMA_ALL);
			
			data = getParameters(psCurso.executeQuery());

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}
		return data;
	}	
	
	public static Idioma getIdioma(int idIdioma) {
		ArrayList<Idioma> data = new ArrayList<Idioma>();		
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = conn.prepareStatement(DB_SELECT_IDIOMA_BY_ID);
			ps.setInt(1, idIdioma);
			
			data = getParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}
		return data.get(0);
	}		
	
	private static ArrayList<Idioma> getParameters(ResultSet rs){

		ArrayList<Idioma> data = new ArrayList<Idioma>();
		
		try{
		
		while (rs.next()) 		{
			Idioma object = new Idioma();			
			object.setIdIdioma(rs.getInt("id_idioma"));
			object.setNomeIdioma(rs.getString("nome_idioma"));
			object.setLocale(rs.getString("locale"));
			data.add(object);
		}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}		
		return data;
	}
	
	

}
