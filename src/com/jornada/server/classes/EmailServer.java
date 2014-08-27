package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.jornada.server.database.ConnectionManager;
import com.jornada.server.framework.EmailFrameWork;
import com.jornada.shared.classes.Comunicado;
import com.jornada.shared.classes.Usuario;

public class EmailServer {

	private static String DB_GET_USER_EMAIL = "SELECT email FROM usuario where usuario.id_usuario in (";
	private static String DB_GET_USERS_EMAIL = "SELECT primeiro_nome, sobre_nome, id_usuario FROM usuario";
	private static String DB_GET_USERS_EMAIL_BY_ID = "SELECT email FROM usuario where id_usuario in (";
	private static String DB_GET_USERS_BY_COMUNICATION = "SELECT primeiro_nome, sobre_nome "
			+ "FROM usuario u, rel_comunicado_usuario rcu "
			+ "WHERE rcu.id_usuario = u.id_usuario and rcu.id_comunicado = ?";

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

	public static HashMap<String, Integer> getUsersIdlList() {

		HashMap<String, Integer> emailList = new HashMap<String, Integer>();
		String name;

		Connection conn = ConnectionManager.getConnection();

		try {

			PreparedStatement ps = conn.prepareStatement(DB_GET_USERS_EMAIL);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				name = rs.getString("primeiro_nome");
				name = name + " " + rs.getString("sobre_nome");
				if (!rs.getString("id_usuario").isEmpty()) {
					emailList.put(name, rs.getInt("id_usuario"));
				}
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(conn);
		}

		return emailList;

	}

	public static Boolean sendMailByUserId(ArrayList<Integer> userList,
			String subject, String content) {
		EmailFrameWork emailFramework = new EmailFrameWork();

		emailFramework.sendMailByUserId(userList, subject, content);

		return true;
	}

	public static ArrayList<String> getEmailListByUserId(
			ArrayList<Integer> userIdList) {
		ArrayList<String> emailList = new ArrayList<String>();

		Boolean firstUser = true;

		for (Integer id : userIdList) {
			if (firstUser) {
				DB_GET_USERS_EMAIL_BY_ID = DB_GET_USERS_EMAIL_BY_ID + id;
				firstUser = false;
			} else {
				DB_GET_USERS_EMAIL_BY_ID = DB_GET_USERS_EMAIL_BY_ID + "," + id;
			}
		}

		firstUser = true;
		DB_GET_USERS_EMAIL_BY_ID = DB_GET_USERS_EMAIL_BY_ID + ");";

		Connection conn = ConnectionManager.getConnection();

		try {

			PreparedStatement ps = conn
					.prepareStatement(DB_GET_USERS_EMAIL_BY_ID);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				emailList.add(rs.getString("email"));
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(conn);
		}

		return emailList;
	}

	public static ArrayList<String> getComucidadoEmailList(Comunicado comunicado) {

		ArrayList<String> userList = new ArrayList<String>();
		Connection conn = ConnectionManager.getConnection();
		try {

			PreparedStatement ps = conn
					.prepareStatement(DB_GET_USERS_BY_COMUNICATION);
			ps.setInt(1, comunicado.getIdComunicado());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String userName = rs.getString("primeiro_nome") + " "
						+ rs.getString("sobre_nome");
				userList.add(userName);
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return userList;
	}

}
