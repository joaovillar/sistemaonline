package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.jornada.server.classes.utility.MpUtilServer;
import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.Ocorrencia;
import com.jornada.shared.classes.OcorrenciaAluno;
import com.jornada.shared.classes.RelUsuarioOcorrencia;
import com.jornada.shared.classes.Usuario;

public class OcorrenciaServer {
	
	public static String DB_INSERT_OCORRENCIA = "INSERT INTO ocorrencia (assunto, descricao, data, hora, id_conteudo_programatico) VALUES (?,?,?,?,?) returning id_ocorrencia";
	public static String DB_INSERT_REL_USUARIO_OCORRENCIA = "INSERT INTO rel_usuario_ocorrencia (id_usuario, id_ocorrencia) VALUES (?,?)";
	public static String DB_UPDATE_OCORRENCIA = "UPDATE ocorrencia set assunto=?, descricao=?, data=?, hora=?, id_conteudo_programatico=? where id_ocorrencia=?;";
	public static String DB_UPDATE_OCORRENCIA_PAI_CIENTE = "UPDATE rel_usuario_ocorrencia set pai_ciente=? where id_ocorrencia=? and id_usuario=?;";
	public static String DB_DELETE_OCORRENCIA = "delete from ocorrencia where id_ocorrencia=?";	
	public static String DB_DELETE_RELACIONAMENTO_USUARIO_OCORRENCIA = "delete from rel_usuario_ocorrencia where id_ocorrencia=?";
	public static String DB_SELECT_OCORRENCIA_ALL = "SELECT * FROM ocorrencia order by data, hora asc;";
	public static String DB_SELECT_OCORRENCIA_ILIKE = "SELECT * FROM ocorrencia where (assunto ilike ? or descricao ilike ?) order by data, hora asc;";
	public static String DB_SELECT_OCORRENCIA_POR_CONTEUDO_PROGRAMATICO = "SELECT * FROM ocorrencia where (id_conteudo_programatico = ?) order by data, hora asc;";
	
	
	public static String DB_SELECT_REL_USUARIO_OCORRENCIA = "SELECT * FROM rel_usuario_ocorrencia where id_ocorrencia = ? and id_usuario = ?;";
	
	public static String DB_SELECT_OCORRENCIA_PELO_ALUNO=
	"select "+
	"c.nome_curso as nomecurso, "+
	"p.nome_periodo as nomeperiodo, "+ 
	"d.nome_disciplina as nomedisciplina, "+
	"cp.nome_conteudo_programatico as nomeconteudo, "+
	"o.*," +
	"ruo.id_usuario, "+
	"ruo.pai_ciente "+
	"from ocorrencia o "+
	"inner join rel_usuario_ocorrencia ruo on o.id_ocorrencia=ruo.id_ocorrencia "+
	"inner join conteudo_programatico cp on o.id_conteudo_programatico = cp.id_conteudo_programatico "+
	"inner join disciplina d on cp.id_disciplina = d.id_disciplina "+
	"inner join periodo p on d.id_periodo = p.id_periodo "+
	"inner join curso c on p.id_curso = c.id_curso "+
	"inner join rel_curso_usuario rcu on c.id_curso  = rcu.id_curso "+ 
	"where ruo.id_usuario=?	and rcu.id_usuario=ruo.id_usuario ";
	

	public static boolean AdicionarOcorrencia(Ocorrencia object) {

		boolean isOperationDone = false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			Date date = new Date();

			if (object.getData() == null) {
				object.setData(MpUtilServer.convertDateToString(date));
			}

			int count = 0;
			PreparedStatement ps = connection.prepareStatement(DB_INSERT_OCORRENCIA);
			ps.setString(++count, object.getAssunto());
			ps.setString(++count, object.getDescricao());
//			ps.setDate(++count, new java.sql.Date(object.getData().getTime()));
//			ps.setTime(++count, object.getHora());
			ps.setDate(++count, MpUtilServer.convertStringToSqlDate(object.getData()));
			ps.setTime(++count, MpUtilServer.convertStringToSqlTime(object.getHora()));
			ps.setInt(++count, object.getIdConteudoProgramatico());

			ResultSet rs = ps.executeQuery();			
			rs.next();
			
			object.setIdOcorrencia(rs.getInt("id_ocorrencia"));


			isOperationDone = AdicionarRelacionamentoUsuarioOcorrencia(object);


		} catch (SQLException sqlex) {
			isOperationDone = false;
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return isOperationDone;
	}	
	
	public static boolean AdicionarRelacionamentoUsuarioOcorrencia(Ocorrencia object) {

		boolean isOperationDone = false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			
			
			for(int i=0;i<object.getListUsuariosRelacionadosOcorrencia().size();i++){
				int count=0;
				int idUsuario = object.getListUsuariosRelacionadosOcorrencia().get(i).getIdUsuario();
				int idOcorrrencia = object.getIdOcorrencia();
				PreparedStatement psInsert = connection.prepareStatement(DB_INSERT_REL_USUARIO_OCORRENCIA);
				psInsert.setInt(++count, idUsuario);				
				psInsert.setInt(++count, idOcorrrencia);	
				psInsert.executeUpdate();			

			}
			
			isOperationDone = true;


		} catch (SQLException sqlex) {
			isOperationDone = false;
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return isOperationDone;
	}		

	public static boolean AtualizarOcorrencia(Ocorrencia object) {

		boolean isOperationDone = false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			Date date = new Date();

			if (object.getData() == null) {
				object.setData(MpUtilServer.convertDateToString(date));
			}
			//"UPDATE ocorrencia set assunto=?, descricao=?, data=?, hora=?, id_conteudo_programatico=? where id_ocorrencia=?;";
			int count = 0;
			PreparedStatement ps = connection.prepareStatement(OcorrenciaServer.DB_UPDATE_OCORRENCIA);
			ps.setString(++count, object.getAssunto());
			ps.setString(++count, object.getDescricao());
//			ps.setDate(++count, new java.sql.Date(object.getData().getTime()));
//			ps.setTime(++count, object.getHora());
			ps.setDate(++count, MpUtilServer.convertStringToSqlDate(object.getData()));
			ps.setTime(++count, MpUtilServer.convertStringToSqlTime(object.getHora()));
			ps.setInt(++count, object.getIdConteudoProgramatico());
			ps.setInt(++count, object.getIdOcorrencia());

			int numberUpdate = ps.executeUpdate();

			if (numberUpdate == 1) {
				if(deletarRelacionamentoUsuarioOcorrencia(object.getIdOcorrencia())){
					if(AdicionarRelacionamentoUsuarioOcorrencia(object)){
						isOperationDone = true;						
					}
				}
			}

		} catch (SQLException sqlex) {
			isOperationDone = false;
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return isOperationDone;
	}	
	
	public static boolean AtualizarPaiCiente(OcorrenciaAluno object) {

		boolean isOperationDone = false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();


			
			int count = 0;
			PreparedStatement ps = connection.prepareStatement(OcorrenciaServer.DB_UPDATE_OCORRENCIA_PAI_CIENTE);
			ps.setBoolean(++count, object.isPaiCiente());
			ps.setInt(++count, object.getIdOcorrencia());
			ps.setInt(++count, object.getIdUsuario());

			int numberUpdate = ps.executeUpdate();

			if (numberUpdate == 1) {
				isOperationDone = true;
			}

		} catch (SQLException sqlex) {
			isOperationDone = false;
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return isOperationDone;
	}		
	
	public static boolean deleteOcorrenciaRow(int id_ocorrencia){
		
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			int count = 0;
			PreparedStatement ps = connection.prepareStatement(DB_DELETE_OCORRENCIA);
			ps.setInt(++count, id_ocorrencia);

			int numberUpdate = ps.executeUpdate();


			if (numberUpdate == 1) {
				success = true;
			}


		} catch (SQLException sqlex) {
			success=false;
			System.err.println(sqlex.getMessage());			
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}
		
		return success;
	}	
	
	public static boolean deletarRelacionamentoUsuarioOcorrencia(int id_ocorrencia){
		
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			int count = 0;
			PreparedStatement ps = connection.prepareStatement(DB_DELETE_RELACIONAMENTO_USUARIO_OCORRENCIA);
			ps.setInt(++count, id_ocorrencia);

			ps.executeUpdate();
			success = true;



		} catch (SQLException sqlex) {
			success=false;
			System.err.println(sqlex.getMessage());			
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}
		
		return success;
	}				
	
	public static ArrayList<Ocorrencia> getOcorrencias() {

		ArrayList<Ocorrencia> data = new ArrayList<Ocorrencia>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = connection.prepareStatement(OcorrenciaServer.DB_SELECT_OCORRENCIA_ALL);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				Ocorrencia currentObject = new Ocorrencia();
				
				currentObject.setIdOcorrencia(rs.getInt("id_ocorrencia"));				
				currentObject.setIdConteudoProgramatico(rs.getInt("id_conteudo_programatico"));
				currentObject.setAssunto(rs.getString("assunto"));
				currentObject.setDescricao(rs.getString("descricao"));
//				currentObject.setData(rs.getDate("data"));
//				currentObject.setHora(rs.getTime("hora"));
				currentObject.setData(MpUtilServer.convertDateToString(rs.getDate("data")));
				currentObject.setHora(MpUtilServer.convertTimeToString(rs.getTime("hora")));
				
				currentObject.setListUsuariosRelacionadosOcorrencia(UsuarioServer.getUsuariosPorOcorrencia(currentObject.getIdOcorrencia()));

				data.add(currentObject);
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}	

	public static ArrayList<Ocorrencia> getOcorrencias(String strFilter) {

		ArrayList<Ocorrencia> data = new ArrayList<Ocorrencia>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = connection.prepareStatement(OcorrenciaServer.DB_SELECT_OCORRENCIA_ILIKE);
			int count=0;
			ps.setString(++count, strFilter);
			ps.setString(++count, strFilter);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				Ocorrencia currentObject = new Ocorrencia();
				
				currentObject.setIdOcorrencia(rs.getInt("id_ocorrencia"));				
				currentObject.setIdConteudoProgramatico(rs.getInt("id_conteudo_programatico"));
				currentObject.setAssunto(rs.getString("assunto"));
				currentObject.setDescricao(rs.getString("descricao"));
//				currentObject.setData(rs.getDate("data"));
//				currentObject.setHora(rs.getTime("hora"));
				currentObject.setData(MpUtilServer.convertDateToString(rs.getDate("data")));
				currentObject.setHora(MpUtilServer.convertTimeToString(rs.getTime("hora")));

				data.add(currentObject);
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}			
	
	public static ArrayList<Ocorrencia> getOcorrenciasPeloConteudoProgramatico(int idConteudoProgramatico) {

		ArrayList<Ocorrencia> data = new ArrayList<Ocorrencia>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = connection.prepareStatement(OcorrenciaServer.DB_SELECT_OCORRENCIA_POR_CONTEUDO_PROGRAMATICO);
			int count=0;
			ps.setInt(++count, idConteudoProgramatico);
//			ps.setString(++count, strFilter);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				Ocorrencia currentObject = new Ocorrencia();
				
				currentObject.setIdOcorrencia(rs.getInt("id_ocorrencia"));				
				currentObject.setIdConteudoProgramatico(rs.getInt("id_conteudo_programatico"));
				currentObject.setAssunto(rs.getString("assunto"));
				currentObject.setDescricao(rs.getString("descricao"));
//				currentObject.setData(rs.getDate("data"));
//				currentObject.setHora(rs.getTime("hora"));
				currentObject.setData(MpUtilServer.convertDateToString(rs.getDate("data")));
				currentObject.setHora(MpUtilServer.convertTimeToString(rs.getTime("hora")));
				
				currentObject.setListUsuariosRelacionadosOcorrencia(UsuarioServer.getUsuariosPorOcorrencia(currentObject.getIdOcorrencia()));

				data.add(currentObject);
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}			
	
	public static ArrayList<OcorrenciaAluno> getOcorrenciasPeloAluno(int idAluno) {

		ArrayList<OcorrenciaAluno> data = new ArrayList<OcorrenciaAluno>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = connection.prepareStatement(OcorrenciaServer.DB_SELECT_OCORRENCIA_PELO_ALUNO);
			int count=0;
			ps.setInt(++count, idAluno);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				OcorrenciaAluno currentObject = new OcorrenciaAluno();
				currentObject.setNomeCurso(rs.getString("nomecurso"));
				currentObject.setNomePeriodo(rs.getString("nomeperiodo"));
				currentObject.setNomeDisciplina(rs.getString("nomedisciplina"));
				currentObject.setNomeConteudoProgramatico(rs.getString("nomeconteudo"));		
				currentObject.setIdOcorrencia(rs.getInt("id_ocorrencia"));
				currentObject.setIdUsuario(rs.getInt("id_usuario"));
				currentObject.setAssunto(rs.getString("assunto"));
				currentObject.setDescricao(rs.getString("descricao"));
//				currentObject.setData(rs.getDate("data"));
				currentObject.setData(MpUtilServer.convertDateToString(rs.getDate("data")));
				currentObject.setHora(MpUtilServer.convertTimeToString(rs.getTime("hora")));
				currentObject.setPaiCiente(rs.getBoolean("pai_ciente"));

				data.add(currentObject);
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}
	
	
	public static RelUsuarioOcorrencia getRelUsuarioOcorrencia(int idOcorrencia, int idUsuario) {

		RelUsuarioOcorrencia currentObject = new RelUsuarioOcorrencia();
		// JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try {

			// dataBase.createConnection();
			// Connection connection = dataBase.getConnection();

			PreparedStatement ps = connection.prepareStatement(OcorrenciaServer.DB_SELECT_REL_USUARIO_OCORRENCIA);
			int count = 0;
			ps.setInt(++count, idOcorrencia);
			ps.setInt(++count, idUsuario);

			ResultSet rs = ps.executeQuery();
			rs.next();

			currentObject.setIdRelUsuarioOcorrencia(rs.getInt("id_rel_usuario_ocorrencia"));
			currentObject.setIdOcorrencia(rs.getInt("id_ocorrencia"));
			currentObject.setIdUsuario(rs.getInt("id_usuario"));
			currentObject.setPaiCiente(rs.getBoolean("pai_ciente"));

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return currentObject;

	}
	
	public static ArrayList<OcorrenciaAluno> getTodasAsOcorrenciasDosAlunos(int idConteudoProgramatico){	
		
		ArrayList<OcorrenciaAluno> listaOcorrenciaTodosAlunos = new ArrayList<OcorrenciaAluno>();		
		
		ArrayList<Ocorrencia> listaOcorrencias = getOcorrenciasPeloConteudoProgramatico(idConteudoProgramatico);
		
		for(int cvOcorrencia=0;cvOcorrencia<listaOcorrencias.size();cvOcorrencia++){
			
			Ocorrencia ocorrencia = listaOcorrencias.get(cvOcorrencia);
			
			for(int cvUsuario=0;cvUsuario<ocorrencia.getListUsuariosRelacionadosOcorrencia().size();cvUsuario++){
				
				Usuario usuario = ocorrencia.getListUsuariosRelacionadosOcorrencia().get(cvUsuario);
				
				int idOcorrencia = ocorrencia.getIdOcorrencia();
				int idUsuario = usuario.getIdUsuario();
				
				RelUsuarioOcorrencia relUsuarioOcorrencia = getRelUsuarioOcorrencia(idOcorrencia, idUsuario);
				
				OcorrenciaAluno ocorrenciaAluno = new OcorrenciaAluno();
				
				ocorrenciaAluno.setAssunto(ocorrencia.getAssunto());
				ocorrenciaAluno.setDescricao(ocorrencia.getDescricao());
				ocorrenciaAluno.setData(ocorrencia.getData());
				ocorrenciaAluno.setHora(ocorrencia.getHora());
				ocorrenciaAluno.setPaiCiente(relUsuarioOcorrencia.isPaiCiente());
				ocorrenciaAluno.setIdUsuario(usuario.getIdUsuario());
				ocorrenciaAluno.setUsuario(usuario);
				
				listaOcorrenciaTodosAlunos.add(ocorrenciaAluno);
				
			}
			
		}
		
		return listaOcorrenciaTodosAlunos;
		
	}
	
	

	
}
