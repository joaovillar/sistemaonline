package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.Usuario;

public class EmailServer {

	public static String DB_GET_USER_EMAIL = "SELECT email FROM usuario where usuario.id_usuario in (";

	public static String getUserEmail(ArrayList<Usuario> users) {

		String emailList = "";
		String usersId;

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

}
