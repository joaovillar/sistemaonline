package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.UnidadeEscola;

public class UnidadeEscolaServer {
	
	public static String DB_SELECT = "SELECT * FROM unidade_escola;";
	public static String DB_SELECT_POR_ID_UNIDADE_ESCOLA = "SELECT * FROM unidade_escola where id_unidade_escola=?;";
	
	
	public static ArrayList<UnidadeEscola> getUnidadeEscolas() {

		ArrayList<UnidadeEscola> data = new ArrayList<UnidadeEscola>();		

		Connection conn = ConnectionManager.getConnection();
		
		try 
		{
			PreparedStatement ps = conn.prepareStatement(DB_SELECT);			
			data = getParameters(ps.executeQuery());
		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {			
			ConnectionManager.closeConnection(conn);
		}
		return data;
	}

	
	public static UnidadeEscola getUnidadeEscola(int idUnidadeEscola) {

		ArrayList<UnidadeEscola> data = new ArrayList<UnidadeEscola>();		
		UnidadeEscola unidadeEscola = new UnidadeEscola();

		Connection conn = ConnectionManager.getConnection();
		
		try 
		{
			PreparedStatement ps = conn.prepareStatement(DB_SELECT_POR_ID_UNIDADE_ESCOLA);	
			int count=0;
			ps.setInt(++count, idUnidadeEscola);			
			data = getParameters(ps.executeQuery());
			unidadeEscola = data.get(0);
		} catch (SQLException sqlex) {
			unidadeEscola=null;
			System.err.println(sqlex.getMessage());
		} finally {			
			ConnectionManager.closeConnection(conn);
		}
		return unidadeEscola;
	}	
	
	
	private static ArrayList<UnidadeEscola> getParameters(ResultSet rs){

		ArrayList<UnidadeEscola> data = new ArrayList<UnidadeEscola>();
		
		try{
		
		while (rs.next()) 		{
			UnidadeEscola object = new UnidadeEscola();			
			object.setIdUnidadeEscola(rs.getInt("id_unidade_escola"));
			object.setNomeUnidadeEscola(rs.getString("nome_unidade_escola"));

			data.add(object);
		}
		}catch(Exception ex){
			data=null;
			System.err.println(ex.getMessage());
		}		
		return data;
	}	
	
	

}
