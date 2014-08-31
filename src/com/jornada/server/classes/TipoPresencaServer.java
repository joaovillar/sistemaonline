package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.TipoPresenca;

public class TipoPresencaServer {
	
	public static String DB_SELECT = "SELECT * FROM tipo_presenca order by tipo_presenca asc;";
	public static String DB_SELECT_POR_ID_TIPO_PRESENCA = "SELECT * FROM tipo_presenca where id_tipo_presenca=?;";
	
	
	public static ArrayList<TipoPresenca> getTipoPresencas() {

		ArrayList<TipoPresenca> data = new ArrayList<TipoPresenca>();		

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
	
	
	public static TipoPresenca getTipoPresenca(int idTipoPresenca) {

		ArrayList<TipoPresenca> data = new ArrayList<TipoPresenca>();		
		TipoPresenca tipoPresenca = new TipoPresenca();

		Connection conn = ConnectionManager.getConnection();
		
		try 
		{

			PreparedStatement ps = conn.prepareStatement(DB_SELECT_POR_ID_TIPO_PRESENCA);	
			int count=0;
			ps.setInt(++count, idTipoPresenca);			
			data = getParameters(ps.executeQuery());
			tipoPresenca = data.get(0);
		} catch (SQLException sqlex) {
			data=null;
			tipoPresenca=null;
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return tipoPresenca;
	}
	
	
	private static ArrayList<TipoPresenca> getParameters(ResultSet rs){

		ArrayList<TipoPresenca> data = new ArrayList<TipoPresenca>();
		
		try{
		
		while (rs.next()) 		{
			TipoPresenca object = new TipoPresenca();			
			object.setIdTipoPresenca(rs.getInt("id_tipo_presenca"));
			object.setTipoPresenca(rs.getString("tipo_presenca"));

			data.add(object);
		}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}		
		return data;
	}	
	
	

}
