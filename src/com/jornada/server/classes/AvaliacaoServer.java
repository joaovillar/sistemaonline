package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.jornada.server.classes.utility.MpUtilServer;
import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.Avaliacao;
import com.jornada.shared.classes.CursoAvaliacao;
import com.jornada.shared.classes.TipoAvaliacao;

public class AvaliacaoServer {
	
	
	public static String DB_INSERT_AVALIACAO = "INSERT INTO avaliacao (id_conteudo_programatico, id_tipo_avaliacao, assunto, descricao, data, hora) VALUES (?,?,?,?,?,?);";
	public static String DB_UPDATE = "UPDATE avaliacao set id_tipo_avaliacao=?, assunto=?, descricao=?, data=?, hora=? where id_avaliacao=?;";
	public static String DB_DELETE = "delete from avaliacao where id_avaliacao=?;";	
	public static String DB_SELECT_AVALIACAO_PELO_CONTEUDO_PROGRAMATICO = "SELECT * FROM avaliacao where id_conteudo_programatico=? order by assunto asc;";	
	public static String DB_SELECT_TIPO_AVALIACAO_ALL = "SELECT * FROM tipo_avaliacao order by nome_tipo_avaliacao asc;";
	public static String DB_SELECT_TIPO_AVALIACAO = "SELECT * FROM tipo_avaliacao where id_tipo_avaliacao=? order by nome_tipo_avaliacao asc;";
	public static String DB_SELECT_AVALIACAO_PELO_CURSO=
			"select  c.nome_curso, p.nome_periodo, d.nome_disciplina, cp.nome_conteudo_programatico, a.assunto, a.data, a.hora, a.id_tipo_avaliacao  "+
//			"from usuario u "+
//			"inner join rel_curso_usuario rcu on u.id_usuario=rcu.id_usuario "+
//			"inner join curso c on c.id_curso = rcu.id_curso "+
			"from curso c "+
			"inner join periodo p on c.id_curso = p.id_curso "+
			"inner join disciplina d on p.id_periodo = d.id_periodo "+
			"inner join conteudo_programatico cp on d.id_disciplina = cp.id_disciplina "+
			"inner join avaliacao a on cp.id_conteudo_programatico = a.id_conteudo_programatico "+
			"where  "+
			"c.id_curso=? "+ 
			"group by c.nome_curso, p.nome_periodo, d.nome_disciplina, cp.nome_conteudo_programatico, a.assunto, a.data, a.hora, a.id_tipo_avaliacao  "+
			"order by a.data, a.hora asc ";		
		
	
	
	
	public static Boolean Adicionar(Avaliacao object) {
		
		Boolean isOperationDone = false; 
		
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
			
			//(id_conteudo_programatico, id_tipo_avaliacao, assunto, descricao, data, hora)
			
			Date date = new Date();

			if (object.getData() == null) {
				object.setData(date);
			}
				int count = 0;
				PreparedStatement ps = conn.prepareStatement(DB_INSERT_AVALIACAO);
				ps.setInt(++count, object.getIdConteudoProgramatico());
				ps.setInt(++count, object.getIdTipoAvaliacao());				
				ps.setString(++count, object.getAssunto());
				ps.setString(++count, object.getDescricao());
				ps.setDate(++count, new java.sql.Date(object.getData().getTime()));				
//				ps.setTime(++count, object.getHora());

//				ps.setDate(++count, MpUtilServer.convertStringToSqlDate(object.getData()));				
				ps.setTime(++count, MpUtilServer.convertStringToSqlTime(object.getHora()));

				
				int numberUpdate = ps.executeUpdate();
			
			
			if(numberUpdate == 1)
			{
				isOperationDone = true;
			}
			
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
		}
		finally
		{
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
			
		}
		
		return isOperationDone;
	}
		
	public static boolean updateRow(Avaliacao object){
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
			

			int count = 0;
			PreparedStatement ps = conn.prepareStatement(DB_UPDATE);
			ps.setInt(++count, object.getIdTipoAvaliacao());				
			ps.setString(++count, object.getAssunto());
			ps.setString(++count, object.getDescricao());
			ps.setDate(++count, new java.sql.Date(object.getData().getTime()));				
//			ps.setTime(++count, object.getHora());
//			ps.setDate(++count, MpUtilServer.convertStringToSqlDate(object.getData()));				
			ps.setTime(++count, MpUtilServer.convertStringToSqlTime(object.getHora()));
			
			
			ps.setInt(++count, object.getIdAvaliacao());

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
	
	public static boolean deleteRow(int id_topico){
		
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
			

			int count = 0;
			PreparedStatement ps = conn.prepareStatement(DB_DELETE);
			ps.setInt(++count, id_topico);

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
	
	public static ArrayList<Avaliacao> getAvaliacao(int id_conteudo_programatico) {

		ArrayList<Avaliacao> data = new ArrayList<Avaliacao>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();
			
			

			PreparedStatement ps = conn.prepareStatement(DB_SELECT_AVALIACAO_PELO_CONTEUDO_PROGRAMATICO);
			
			int count=0;
			ps.setInt(++count, id_conteudo_programatico);			

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				Avaliacao current = new Avaliacao();
				
				current.setIdAvaliacao(rs.getInt("id_avaliacao"));
				current.setIdConteudoProgramatico(rs.getInt("id_conteudo_programatico"));
				current.setIdTipoAvaliacao(rs.getInt("id_tipo_avaliacao"));				
				current.setAssunto(rs.getString("assunto"));				
				current.setDescricao(rs.getString("descricao"));
				current.setData(rs.getDate("data"));
				current.setHora(rs.getString("hora"));

				data.add(current);
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}

		return data;

	}		
		
	public static ArrayList<CursoAvaliacao> getAvaliacaoPeloCurso(int id_curso, String locale) {

		ArrayList<CursoAvaliacao> data = new ArrayList<CursoAvaliacao>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{
//			dataBase.createConnection();

			PreparedStatement ps = conn.prepareStatement(DB_SELECT_AVALIACAO_PELO_CURSO);
			
			int count=0;
			ps.setInt(++count, id_curso);			

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				CursoAvaliacao current = new CursoAvaliacao();
				
				current.setNomeCurso(rs.getString("nome_curso"));
				current.setNomePeriodo(rs.getString("nome_periodo"));
				current.setNomeDisciplina(rs.getString("nome_disciplina"));
				current.setNomeConteudoProgramatico(rs.getString("nome_conteudo_programatico"));
				current.setNomeAvaliacao(rs.getString("assunto"));
//				current.setDataAvaliacao(MpUtilServer.convertDateToString(rs.getDate("data"), locale));
				current.setDataAvaliacao(rs.getDate("data"));
				current.setHoraAvaliacao(MpUtilServer.convertTimeToString(rs.getTime("hora")));
				current.setIdTipoAvaliacao(rs.getInt("id_tipo_avaliacao"));

				data.add(current);
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}

		return data;

	}			
	
	
	public static ArrayList<TipoAvaliacao> getTipoAvaliacao() {

		ArrayList<TipoAvaliacao> data = new ArrayList<TipoAvaliacao>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{
//			dataBase.createConnection();
			
			PreparedStatement ps = conn.prepareStatement(AvaliacaoServer.DB_SELECT_TIPO_AVALIACAO_ALL);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				TipoAvaliacao currentObject = new TipoAvaliacao();
				
				currentObject.setIdTipoAvaliacao(rs.getInt("id_tipo_avaliacao"));
				currentObject.setNomeTipoAvaliacao(rs.getString("nome_tipo_avaliacao"));
				currentObject.setDescricao(rs.getString("descricao"));

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
	
	public static TipoAvaliacao getTipoAvaliacao(int idTipoAvaliacao) {

		TipoAvaliacao currentObject = new TipoAvaliacao();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();
			PreparedStatement ps = conn.prepareStatement(AvaliacaoServer.DB_SELECT_TIPO_AVALIACAO);
			
			int count=0;
			ps.setInt(++count, idTipoAvaliacao);

			ResultSet rs = ps.executeQuery();
//			while (rs.next()) 
//			{
			rs.next();				
				
				currentObject.setIdTipoAvaliacao(rs.getInt("id_tipo_avaliacao"));
				currentObject.setNomeTipoAvaliacao(rs.getString("nome_tipo_avaliacao"));
				currentObject.setDescricao(rs.getString("descricao"));

//				data.add(currentObject);
//			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}

		return currentObject;

	}				
	

}
