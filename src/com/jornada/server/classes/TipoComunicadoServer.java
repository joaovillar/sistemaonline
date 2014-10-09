package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.TipoComunicado;

public class TipoComunicadoServer {
	
	public static String DB_SELECT_TIPO_COMUNICADO_ALL = "SELECT * FROM tipo_comunicado " +
			"where tipo_comunicado_tipo='"+TipoComunicado.TIPO_COMUNICADO+"' " +
			"order by tipo_comunicado_nome asc;";
	
    public static String DB_SELECT_TIPO_EMAIL_ALL = "SELECT * FROM tipo_comunicado " +
            "where tipo_comunicado_tipo='"+TipoComunicado.TIPO_EMAIL+"' " +
            "order by tipo_comunicado_nome asc;";
	
	
	public static ArrayList<TipoComunicado> getTipoComunicados() {

		ArrayList<TipoComunicado> data = new ArrayList<TipoComunicado>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = connection.prepareStatement(TipoComunicadoServer.DB_SELECT_TIPO_COMUNICADO_ALL);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				TipoComunicado currentObject = new TipoComunicado();
				
				currentObject.setIdTipoComunicado(rs.getInt("id_tipo_comunicado"));
				currentObject.setTipoComunicadoNome(rs.getString("tipo_comunicado_nome"));
				currentObject.setDescricao(rs.getString("descricao"));


				data.add(currentObject);
			}

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}		
	
	
    public static ArrayList<TipoComunicado> getTipoComunicadoEmails() {

        ArrayList<TipoComunicado> data = new ArrayList<TipoComunicado>();
//      JornadaDataBase dataBase = new JornadaDataBase();
        Connection connection = ConnectionManager.getConnection();
        try 
        {

//          dataBase.createConnection();            
//          Connection connection = dataBase.getConnection();

            PreparedStatement ps = connection.prepareStatement(TipoComunicadoServer.DB_SELECT_TIPO_EMAIL_ALL);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) 
            {

                TipoComunicado currentObject = new TipoComunicado();
                
                currentObject.setIdTipoComunicado(rs.getInt("id_tipo_comunicado"));
                currentObject.setTipoComunicadoNome(rs.getString("tipo_comunicado_nome"));
                currentObject.setDescricao(rs.getString("descricao"));


                data.add(currentObject);
            }

        } catch (SQLException sqlex) {
            data=null;
            System.err.println(sqlex.getMessage());
        } finally {
//          dataBase.close();
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }       	

}
