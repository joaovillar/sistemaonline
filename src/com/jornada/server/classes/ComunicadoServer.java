package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jornada.server.classes.utility.MpUtilServer;
import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.Comunicado;
import com.jornada.shared.classes.TipoComunicado;

public class ComunicadoServer {
	
	
	public static String DB_INSERT_COMUNICADO = "INSERT INTO comunicado (assunto, descricao, data, hora, id_tipo_comunicado,nome_imagem) VALUES (?,?,?,?,?,?)";
	public static String DB_UPDATE_COMUNICADO = "UPDATE comunicado set assunto=?, descricao=?, data=?, hora=?, id_tipo_comunicado=?, nome_imagem=? where id_comunicado=?;";
	public static String DB_DELETE_COMUNICADO = "delete from comunicado where id_comunicado=?";	
	public static String DB_SELECT_TIPO_COMUNICADO_ALL = "SELECT * FROM comunicado order by data, hora asc;";
	public static String DB_SELECT_TIPO_COMUNICADO_ILIKE = "SELECT * FROM comunicado where (assunto ilike ? or descricao ilike ?) order by data, hora asc;";
	public static String DB_SELECT_TIPO_COMUNICADO_EXTERNO_ILIKE = 
			"SELECT * FROM comunicado where " +
			"( id_tipo_comunicado="+Integer.toString(TipoComunicado.EXTERNO)+" or " +
			"  id_tipo_comunicado="+Integer.toString(TipoComunicado.MURAL)+" ) " +
			"and (assunto ilike ? or descricao ilike ?) order by data, hora asc;";
	
	public static String DB_SELECT_TIPO_COMUNICADO_INTERNO_ILIKE = 
			"SELECT * FROM comunicado where " +
			"( id_tipo_comunicado="+Integer.toString(TipoComunicado.EXTERNO)+" or " +
			"  id_tipo_comunicado="+Integer.toString(TipoComunicado.INTERNO)+" or " +
			"  id_tipo_comunicado="+Integer.toString(TipoComunicado.MURAL)+" ) " +
			"and (assunto ilike ? or descricao ilike ?) order by data, hora asc;";
	

	
	
	public static boolean AdicionarComunicado(Comunicado object) {

		boolean isOperationDone = false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
			

//			Date date = new Date();
//
//			if (object.getData() == null) {
//				object.setData(null);
//			}

			int count = 0;
			PreparedStatement ps = conn.prepareStatement(ComunicadoServer.DB_INSERT_COMUNICADO);
			ps.setString(++count, object.getAssunto());
			ps.setString(++count, object.getDescricao());

//			ps.setDate(++count, new java.sql.Date(object.getData().getTime()));
//			ps.setTime(++count, object.getHora());
			ps.setDate(++count, MpUtilServer.convertStringToSqlDate(object.getData()));
			ps.setTime(++count, MpUtilServer.convertStringToSqlTime(object.getHora()));
			
			ps.setInt(++count, object.getIdTipoComunicado());
			ps.setString(++count, object.getNomeImagem());

			int numberUpdate = ps.executeUpdate();

			if (numberUpdate == 1) {
				isOperationDone = true;
			}

		} catch (SQLException sqlex) {
			isOperationDone = false;
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(conn);
		}

		return isOperationDone;
	}	
	
	public static boolean AtualizarComunicado(Comunicado object) {

		boolean isOperationDone = false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
			

//			Date date = new Date();
//
//			if (object.getData() == null) {
//				object.setData(date);
//			}
			
		
			//"UPDATE comunicado set assunto=?, descricao=?, data=?, hora=?, id_tipo_comunicado=? where id_comunicado=?;";
			int count = 0;
			PreparedStatement ps = conn.prepareStatement(ComunicadoServer.DB_UPDATE_COMUNICADO);
			ps.setString(++count, object.getAssunto());
			ps.setString(++count, object.getDescricao());
			
//			ps.setDate(++count, new java.sql.Date(object.getData().getTime()));
//			ps.setTime(++count, object.getHora());
			ps.setDate(++count, MpUtilServer.convertStringToSqlDate(object.getData()));
			ps.setTime(++count, MpUtilServer.convertStringToSqlTime(object.getHora()));
			
			ps.setInt(++count, object.getIdTipoComunicado());
			ps.setString(++count, object.getNomeImagem());
			ps.setInt(++count, object.getIdComunicado());

			int numberUpdate = ps.executeUpdate();

			if (numberUpdate == 1) {
				isOperationDone = true;
			}

		} catch (SQLException sqlex) {
			isOperationDone = false;
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(conn);
		}

		return isOperationDone;
	}		
	
	public static boolean deleteComunicadoRow(int id_comunicado){
		
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
			

			int count = 0;
			PreparedStatement ps = conn.prepareStatement(ComunicadoServer.DB_DELETE_COMUNICADO);
			ps.setInt(++count, id_comunicado);

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
	
	public static ArrayList<Comunicado> getComunicados() {

		ArrayList<Comunicado> data = new ArrayList<Comunicado>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{
//			dataBase.createConnection();

			PreparedStatement ps = conn.prepareStatement(ComunicadoServer.DB_SELECT_TIPO_COMUNICADO_ALL);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				Comunicado currentObject = new Comunicado();
				
				currentObject.setIdComunicado(rs.getInt("id_comunicado"));				
				currentObject.setIdTipoComunicado(rs.getInt("id_tipo_comunicado"));
				currentObject.setIdEscola(rs.getInt("id_escola"));
				currentObject.setAssunto(rs.getString("assunto"));
				currentObject.setDescricao(rs.getString("descricao"));
				currentObject.setData(MpUtilServer.convertDateToString(rs.getDate("data")));
				currentObject.setHora(MpUtilServer.convertTimeToString(rs.getTime("hora")));
				currentObject.setNomeImagem(rs.getString("nome_imagem"));

				data.add(currentObject);
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}

		return data;

	}	
	
	public static ArrayList<Comunicado> getComunicados(String strFilter) {

		ArrayList<Comunicado> data = new ArrayList<Comunicado>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();
			
			

			PreparedStatement ps = conn.prepareStatement(ComunicadoServer.DB_SELECT_TIPO_COMUNICADO_ILIKE);
			int count=0;
			ps.setString(++count, strFilter);
			ps.setString(++count, strFilter);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				Comunicado currentObject = new Comunicado();
				
				currentObject.setIdComunicado(rs.getInt("id_comunicado"));				
				currentObject.setIdTipoComunicado(rs.getInt("id_tipo_comunicado"));
				currentObject.setIdEscola(rs.getInt("id_escola"));
				currentObject.setAssunto(rs.getString("assunto"));
				currentObject.setDescricao(rs.getString("descricao"));
				currentObject.setData(MpUtilServer.convertDateToString(rs.getDate("data")));
				currentObject.setHora(MpUtilServer.convertTimeToString(rs.getTime("hora")));
				currentObject.setNomeImagem(rs.getString("nome_imagem"));

				data.add(currentObject);
			}

		} catch (SQLException sqlex) {
			data=null;
			System.err.println("Error:<getComunicados>"+sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}

		return data;

	}
	
	public static ArrayList<Comunicado> getComunicadosExterno(String strFilter) {

		ArrayList<Comunicado> data = new ArrayList<Comunicado>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();

			PreparedStatement ps = conn.prepareStatement(ComunicadoServer.DB_SELECT_TIPO_COMUNICADO_EXTERNO_ILIKE);
			int count=0;
			ps.setString(++count, strFilter);
			ps.setString(++count, strFilter);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				Comunicado currentObject = new Comunicado();
				
				currentObject.setIdComunicado(rs.getInt("id_comunicado"));				
				currentObject.setIdTipoComunicado(rs.getInt("id_tipo_comunicado"));
				currentObject.setIdEscola(rs.getInt("id_escola"));
				currentObject.setAssunto(rs.getString("assunto"));
				currentObject.setDescricao(rs.getString("descricao"));
				currentObject.setData(MpUtilServer.convertDateToString(rs.getDate("data")));
				currentObject.setHora(MpUtilServer.convertTimeToString(rs.getTime("hora")));
				currentObject.setNomeImagem(rs.getString("nome_imagem"));

				data.add(currentObject);
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}

		return data;

	}		
	
	public static ArrayList<Comunicado> getComunicadosInterno(String strFilter) {

		ArrayList<Comunicado> data = new ArrayList<Comunicado>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();

			PreparedStatement ps = conn.prepareStatement(ComunicadoServer.DB_SELECT_TIPO_COMUNICADO_INTERNO_ILIKE);
			int count=0;
			ps.setString(++count, strFilter);
			ps.setString(++count, strFilter);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				Comunicado currentObject = new Comunicado();
				
				currentObject.setIdComunicado(rs.getInt("id_comunicado"));				
				currentObject.setIdTipoComunicado(rs.getInt("id_tipo_comunicado"));
				currentObject.setIdEscola(rs.getInt("id_escola"));
				currentObject.setAssunto(rs.getString("assunto"));
				currentObject.setDescricao(rs.getString("descricao"));
				currentObject.setData(MpUtilServer.convertDateToString(rs.getDate("data")));
				currentObject.setHora(MpUtilServer.convertTimeToString(rs.getTime("hora")));
				currentObject.setNomeImagem(rs.getString("nome_imagem"));

				data.add(currentObject);
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}

		return data;

	}		

	
	

}
