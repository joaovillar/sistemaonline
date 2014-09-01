package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

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
	public static String DB_SELECT_TIPO_COMUNICADO_EXTERNO_ILIKE = "SELECT * FROM comunicado where "
			+ "( id_tipo_comunicado="
			+ Integer.toString(TipoComunicado.EXTERNO)
			+ " or "
			+ "  id_tipo_comunicado="
			+ Integer.toString(TipoComunicado.MURAL)
			+ " ) "
			+ "and (assunto ilike ? or descricao ilike ?) order by data, hora asc;";
	
	public static String DB_SELECT_TIPO_COMUNICADO_INTERNO_ILIKE = "SELECT * FROM comunicado where "
			+ "( id_tipo_comunicado="
			+ Integer.toString(TipoComunicado.EXTERNO)
			+ " or "
			+ "  id_tipo_comunicado="
			+ Integer.toString(TipoComunicado.INTERNO)
			+ " or "
			+ "  id_tipo_comunicado="
			+ Integer.toString(TipoComunicado.MURAL)
			+ " ) "
			+ "and (assunto ilike ? or descricao ilike ?) order by data, hora asc;";

	public static String DB_INSERT_REL_COMUNICADO_USUARIO = "INSERT INTO rel_comunicado_usuario ( id_comunicado, id_usuario ) VALUES (";
	public static String DB_GET_USER_ID_BY_REL_COMUNICADO_USUARIO = "SELECT id_usuario FROM rel_comunicado_usuario WHERE id_comunicado = ?";
	public static String DB_GET_COMUNICADO_PESSOAL = "SELECT c.id_comunicado, c.assunto, c.descricao, c.id_tipo_comunicado, c.id_escola, c.data, c.hora, c.nome_imagem "
			+ "FROM comunicado c, rel_comunicado_usuario rcu "
			+ "WHERE c.id_comunicado = rcu.id_comunicado and rcu.id_usuario = ?;";

	public static boolean AdicionarComunicado(Comunicado object,
			ArrayList<Integer> userIdList) {

		boolean isOperationDone = false;

		// JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {
			// dataBase.createConnection();

			// Date date = new Date();
			//
			// if (object.getData() == null) {
			// object.setData(null);
			// }
			

			int count = 0;
			PreparedStatement ps = conn.prepareStatement(
					ComunicadoServer.DB_INSERT_COMUNICADO,
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(++count, object.getAssunto());
			ps.setString(++count, object.getDescricao());

			// ps.setDate(++count, new
			// java.sql.Date(object.getData().getTime()));
			// ps.setTime(++count, object.getHora());
			ps.setDate(++count,
					MpUtilServer.convertStringToSqlDate(object.getData()));
			ps.setTime(++count,
					MpUtilServer.convertStringToSqlTime(object.getHora()));

			ps.setInt(++count, object.getIdTipoComunicado());
			ps.setString(++count, object.getNomeImagem());

			int numberUpdate = ps.executeUpdate();

			if (numberUpdate == 1) {
				isOperationDone = true;
			}

			if (object.getIdTipoComunicado() == TipoComunicado.EMAIL) {
				HashSet<Integer> hs = new HashSet<Integer>();
				hs.addAll(userIdList);
				userIdList.clear();
				userIdList.addAll(hs);
				try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						Boolean firstInsert = true;
						if (!userIdList.isEmpty()) {
							for (Integer userId : userIdList) {
								if (firstInsert) {
									DB_INSERT_REL_COMUNICADO_USUARIO = DB_INSERT_REL_COMUNICADO_USUARIO
											+ generatedKeys.getLong(1)
											+ ", "
											+ userId + ")";
									firstInsert = false;
								} else {
									DB_INSERT_REL_COMUNICADO_USUARIO = DB_INSERT_REL_COMUNICADO_USUARIO
											+ ", ("
											+ generatedKeys.getLong(1)
											+ ", " + userId + ")";
								}
							}
							PreparedStatement ps2 = conn
									.prepareStatement(ComunicadoServer.DB_INSERT_REL_COMUNICADO_USUARIO);
							ps2.executeUpdate();
						}
					}
				} catch (SQLException sqlex) {
					System.err.println(sqlex.getMessage());
				}
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

	public static boolean AtualizarComunicado(Comunicado object,
			ArrayList<Integer> userIdList) {

		boolean isOperationDone = false;

		// JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {
			// dataBase.createConnection();

			// Date date = new Date();
			//
			// if (object.getData() == null) {
			// object.setData(date);
			// }

//			String nomeImagem= object.getNomeImagem().replace(strRemoveImageAddress, "");
			
			// "UPDATE comunicado set assunto=?, descricao=?, data=?, hora=?, id_tipo_comunicado=? where id_comunicado=?;";
			int count = 0;
			PreparedStatement ps = conn
					.prepareStatement(ComunicadoServer.DB_UPDATE_COMUNICADO);
			ps.setString(++count, object.getAssunto());
			ps.setString(++count, object.getDescricao());

			// ps.setDate(++count, new
			// java.sql.Date(object.getData().getTime()));
			// ps.setTime(++count, object.getHora());
			ps.setDate(++count,
					MpUtilServer.convertStringToSqlDate(object.getData()));
			ps.setTime(++count,
					MpUtilServer.convertStringToSqlTime(object.getHora()));

			ps.setInt(++count, object.getIdTipoComunicado());
			ps.setString(++count, object.getNomeImagem());
//			ps.setString(++count, nomeImagem);
			ps.setInt(++count, object.getIdComunicado());

			int numberUpdate = ps.executeUpdate();

			if (numberUpdate == 1) {
				isOperationDone = true;
			}

			if (object.getIdTipoComunicado() == TipoComunicado.EMAIL) {

				HashSet<Integer> hs = new HashSet<Integer>();
				hs.addAll(userIdList);
				userIdList.clear();
				userIdList.addAll(hs);

				PreparedStatement ps2 = conn
						.prepareStatement(ComunicadoServer.DB_GET_USER_ID_BY_REL_COMUNICADO_USUARIO);
				ps2.setInt(1, object.getIdComunicado());
				ResultSet rs = ps2.executeQuery();

				while (rs.next()) {
					Integer userId = rs.getInt("id_usuario");
					for (int i = 0; i < userIdList.size(); i++) {
						if (userId.equals(userIdList.get(i))) {
							userIdList.remove(i);
							break;
						}
					}

				}

				Boolean firstInsert = true;
				if (!userIdList.isEmpty()) {
					for (Integer userId : userIdList) {
						if (firstInsert) {
							DB_INSERT_REL_COMUNICADO_USUARIO = DB_INSERT_REL_COMUNICADO_USUARIO
									+ object.getIdComunicado()
									+ ", "
									+ userId
									+ ")";
							firstInsert = false;
						} else {
							DB_INSERT_REL_COMUNICADO_USUARIO = DB_INSERT_REL_COMUNICADO_USUARIO
									+ ", ("
									+ object.getIdComunicado()
									+ ", "
									+ userId + ")";
						}
					}
					PreparedStatement ps3 = conn
							.prepareStatement(ComunicadoServer.DB_INSERT_REL_COMUNICADO_USUARIO);
					ps3.executeUpdate();
				}
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

	public static boolean deleteComunicadoRow(int id_comunicado) {

		boolean success = false;

		// JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();

		try {
			// dataBase.createConnection();

			int count = 0;
			PreparedStatement ps = conn
					.prepareStatement(ComunicadoServer.DB_DELETE_COMUNICADO);
			ps.setInt(++count, id_comunicado);

			int numberUpdate = ps.executeUpdate();

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

	public static ArrayList<Comunicado> getComunicados() {

		ArrayList<Comunicado> data = new ArrayList<Comunicado>();
		// JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {
			// dataBase.createConnection();

			PreparedStatement ps = conn
					.prepareStatement(ComunicadoServer.DB_SELECT_TIPO_COMUNICADO_ALL);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				Comunicado currentObject = new Comunicado();

				currentObject.setIdComunicado(rs.getInt("id_comunicado"));
				currentObject.setIdTipoComunicado(rs
						.getInt("id_tipo_comunicado"));
				currentObject.setIdEscola(rs.getInt("id_escola"));
				currentObject.setAssunto(rs.getString("assunto"));
				currentObject.setDescricao(rs.getString("descricao"));
				currentObject.setData(MpUtilServer.convertDateToString(rs
						.getDate("data")));
				currentObject.setHora(MpUtilServer.convertTimeToString(rs
						.getTime("hora")));
				currentObject.setNomeImagem(rs.getString("nome_imagem"));

				data.add(currentObject);
			}

		} catch (SQLException sqlex) {
			data = null;
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(conn);
		}

		return data;

	}

	public static ArrayList<Comunicado> getComunicados(String strFilter) {

		ArrayList<Comunicado> data = new ArrayList<Comunicado>();
		// JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {

			// dataBase.createConnection();

			PreparedStatement ps = conn
					.prepareStatement(ComunicadoServer.DB_SELECT_TIPO_COMUNICADO_ILIKE);
			int count = 0;
			ps.setString(++count, strFilter);
			ps.setString(++count, strFilter);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				Comunicado currentObject = new Comunicado();

				currentObject.setIdComunicado(rs.getInt("id_comunicado"));
				currentObject.setIdTipoComunicado(rs
						.getInt("id_tipo_comunicado"));
				currentObject.setIdEscola(rs.getInt("id_escola"));
				currentObject.setAssunto(rs.getString("assunto"));
				currentObject.setDescricao(rs.getString("descricao"));
				currentObject.setData(MpUtilServer.convertDateToString(rs
						.getDate("data")));
				currentObject.setHora(MpUtilServer.convertTimeToString(rs
						.getTime("hora")));
				currentObject.setNomeImagem(rs.getString("nome_imagem"));

				data.add(currentObject);
			}

		} catch (SQLException sqlex) {
			data = null;
			System.err.println("Error:<getComunicados>" + sqlex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(conn);
		}

		return data;

	}

	public static ArrayList<Comunicado> getComunicadosExterno(String strFilter,
			int idUsuario) {

		ArrayList<Comunicado> data = new ArrayList<Comunicado>();
		// JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {

			// dataBase.createConnection();

			PreparedStatement ps = conn
					.prepareStatement(ComunicadoServer.DB_SELECT_TIPO_COMUNICADO_EXTERNO_ILIKE);
			int count = 0;
			ps.setString(++count, strFilter);
			ps.setString(++count, strFilter);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				Comunicado currentObject = new Comunicado();

				currentObject.setIdComunicado(rs.getInt("id_comunicado"));
				currentObject.setIdTipoComunicado(rs
						.getInt("id_tipo_comunicado"));
				currentObject.setIdEscola(rs.getInt("id_escola"));
				currentObject.setAssunto(rs.getString("assunto"));
				currentObject.setDescricao(rs.getString("descricao"));
				currentObject.setData(MpUtilServer.convertDateToString(rs
						.getDate("data")));
				currentObject.setHora(MpUtilServer.convertTimeToString(rs
						.getTime("hora")));
				currentObject.setNomeImagem(rs.getString("nome_imagem"));

				data.add(currentObject);
			}

			PreparedStatement ps2 = conn
					.prepareStatement(DB_GET_COMUNICADO_PESSOAL);
			ps2.setInt(1, idUsuario);

			ResultSet rs2 = ps2.executeQuery();

			while (rs2.next()) {
				Comunicado currentObject = new Comunicado();

				currentObject.setIdComunicado(rs2.getInt("id_comunicado"));
				currentObject.setIdTipoComunicado(rs2
						.getInt("id_tipo_comunicado"));
				currentObject.setIdEscola(rs2.getInt("id_escola"));
				currentObject.setAssunto(rs2.getString("assunto"));
				currentObject.setDescricao(rs2.getString("descricao"));
				currentObject.setData(MpUtilServer.convertDateToString(rs2
						.getDate("data")));
				currentObject.setHora(MpUtilServer.convertTimeToString(rs2
						.getTime("hora")));
				currentObject.setNomeImagem(rs2.getString("nome_imagem"));

				data.add(currentObject);
			}

		} catch (SQLException sqlex) {
			data = null;
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(conn);
		}

		return data;

	}

	public static ArrayList<Comunicado> getComunicadosInterno(String strFilter,
			int idUsuario) {

		ArrayList<Comunicado> data = new ArrayList<Comunicado>();
		// JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {

			// dataBase.createConnection();

			PreparedStatement ps = conn
					.prepareStatement(ComunicadoServer.DB_SELECT_TIPO_COMUNICADO_INTERNO_ILIKE);
			int count = 0;
			ps.setString(++count, strFilter);
			ps.setString(++count, strFilter);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				Comunicado currentObject = new Comunicado();

				currentObject.setIdComunicado(rs.getInt("id_comunicado"));
				currentObject.setIdTipoComunicado(rs
						.getInt("id_tipo_comunicado"));
				currentObject.setIdEscola(rs.getInt("id_escola"));
				currentObject.setAssunto(rs.getString("assunto"));
				currentObject.setDescricao(rs.getString("descricao"));
				currentObject.setData(MpUtilServer.convertDateToString(rs
						.getDate("data")));
				currentObject.setHora(MpUtilServer.convertTimeToString(rs
						.getTime("hora")));
				currentObject.setNomeImagem(rs.getString("nome_imagem"));

				data.add(currentObject);
			}

			PreparedStatement ps2 = conn
					.prepareStatement(DB_GET_COMUNICADO_PESSOAL);
			ps2.setInt(1, idUsuario);

			ResultSet rs2 = ps2.executeQuery();

			while (rs2.next()) {
				Comunicado currentObject = new Comunicado();

				currentObject.setIdComunicado(rs2.getInt("id_comunicado"));
				currentObject.setIdTipoComunicado(rs2
						.getInt("id_tipo_comunicado"));
				currentObject.setIdEscola(rs2.getInt("id_escola"));
				currentObject.setAssunto(rs2.getString("assunto"));
				currentObject.setDescricao(rs2.getString("descricao"));
				currentObject.setData(MpUtilServer.convertDateToString(rs2
						.getDate("data")));
				currentObject.setHora(MpUtilServer.convertTimeToString(rs2
						.getTime("hora")));
				currentObject.setNomeImagem(rs2.getString("nome_imagem"));

				data.add(currentObject);
			}

		} catch (SQLException sqlex) {
			data = null;
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(conn);
		}

		return data;

	}

}
