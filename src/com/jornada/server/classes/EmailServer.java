package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.jornada.server.database.ConnectionManager;
import com.jornada.server.framework.EmailFrameWork;
import com.jornada.shared.classes.Usuario;

public class EmailServer {

	private static String DB_GET_USER_EMAIL = "SELECT email FROM usuario where usuario.id_usuario in (";
	private static String DB_GET_TEACHERS_EMAIL = "SELECT primeiro_nome, sobre_nome, email FROM usuario where usuario.id_tipo_usuario in (1,2,6)";

	public static String getUserEmail(ArrayList<Usuario> users) {

		String emailList = "";

		Boolean firstUser = true;
		for (Usuario user : users) {
			if (firstUser) {
				DB_GET_USER_EMAIL = DB_GET_USER_EMAIL + user.getIdUsuario();
				firstUser = false;
			} else {
				DB_GET_USER_EMAIL = DB_GET_USER_EMAIL + ","
						+ user.getIdUsuario();
			}
		}
		firstUser = true;
		DB_GET_USER_EMAIL = DB_GET_USER_EMAIL + ");";

		Connection conn = ConnectionManager.getConnection();

		try {

			PreparedStatement ps = conn.prepareStatement(DB_GET_USER_EMAIL);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				if (firstUser) {
					emailList = emailList + rs.getString("email");
					firstUser = false;
				} else {
					emailList = emailList + ", " + rs.getString("email");
				}
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(conn);
		}

		return emailList;

	}
	
	public static HashMap<String,String> getTeachersEmail() {

		HashMap<String,String> emailList = new HashMap<String,String>();
		String name;

		Connection conn = ConnectionManager.getConnection();

		try {

			PreparedStatement ps = conn.prepareStatement(DB_GET_TEACHERS_EMAIL);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				name = rs.getString("primeiro_nome");
				name = name + " " + rs.getString("sobre_nome");
				if(!rs.getString("email").isEmpty()){
					emailList.put(name, rs.getString("email"));
				}
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(conn);
		}

		return emailList;

	}
	
	public static Boolean sendEmail(ArrayList<String> emailList, String subject, String content) {
		EmailFrameWork emailFramework = new EmailFrameWork();
		
		emailFramework.sendMail(emailList, subject, content);
		
		return true;
	}

}
