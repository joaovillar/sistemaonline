package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.Nota;
import com.jornada.shared.classes.boletim.TabelaBoletim;

public class NotaServer {	
	
	public static final String DB_INSERT = "INSERT INTO nota (id_avaliacao, id_usuario, nota) VALUES (?,?,?);";
	public static final String DB_UPDATE = "UPDATE nota set nota=? where id_avaliacao=? and id_usuario=?;";
	public static final String DB_SELECT_NOTA_PELA_AVALIACAO = "SELECT * FROM nota where id_avaliacao=?;";	
	public static final String DB_SELECT_BOLETIM_NOTA_ALUNO_POR_CURSO =
	"		select d.*, cp.*, a.*, n,* from "+ 
	"		( "+
	"			select "+ 
	"				c.id_curso, c.nome_curso, "+ 
	"				p.id_periodo, p.nome_periodo, "+ 
	"				d.id_disciplina, d.nome_disciplina "+
	"			from usuario u "+
	"			inner join rel_curso_usuario rcu on u.id_usuario=rcu.id_usuario "+
	"			inner join curso c on c.id_curso = rcu.id_curso "+
	"			inner join periodo p on c.id_curso = p.id_curso "+
	"			inner join disciplina d on p.id_periodo = d.id_periodo "+
	"			where  "+
	"			u.id_tipo_usuario=? and  rcu.id_usuario=? and c.id_curso=? "+ 
	"		) d "+
	"		left join ( select * from conteudo_programatico ) cp on d.id_disciplina=cp.id_disciplina "+
	"		left join ( select * from avaliacao ) a on cp.id_conteudo_programatico=a.id_conteudo_programatico "+
	"		left join ( select * from nota where id_usuario=? ) n on a.id_avaliacao=n.id_avaliacao ";
	
	public static Boolean Adicionar(Nota object) {
		
		Boolean isOperationDone = false; 
		
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
//			Connection conn = dataBase.getConnection();
			int count = 0;
			PreparedStatement ps = conn.prepareStatement(DB_INSERT);
			ps.setInt(++count, object.getIdAvaliacao());
			ps.setInt(++count, object.getIdUsuario());
			ps.setString(++count, object.getNota());

			int numberUpdate = ps.executeUpdate();

			if (numberUpdate == 1) {
				isOperationDone = true;
			}

		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(conn);
		}
		
		return isOperationDone;
	}
	
	public static boolean updateRow(Nota object){
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			int count = 0;
			PreparedStatement psUpdate = conn.prepareStatement(DB_UPDATE);
//			psUpdate.setInt(++count, object.getIdAvaliacao());				
//			psUpdate.setInt(++count, object.getIdUsuario());
			psUpdate.setString(++count, object.getNota());
			psUpdate.setInt(++count, object.getIdAvaliacao());
			psUpdate.setInt(++count, object.getIdUsuario());

			int numberUpdate = psUpdate.executeUpdate();

			if (numberUpdate == 1) {
				success = true;
			}
			else{
				count=0;
				PreparedStatement psInsert = conn.prepareStatement(DB_INSERT);
				psInsert.setInt(++count, object.getIdAvaliacao());				
				psInsert.setInt(++count, object.getIdUsuario());
				psInsert.setString(++count, object.getNota());
				numberUpdate = psInsert.executeUpdate();
				if (numberUpdate == 1){
					success = true;
				}
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
	
	public static ArrayList<Nota> getNotas(int idAvaliacao) {

		ArrayList<Nota> data = new ArrayList<Nota>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();

		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = conn.prepareStatement(DB_SELECT_NOTA_PELA_AVALIACAO);
			
			int count=0;
			ps.setInt(++count, idAvaliacao);			

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				Nota current = new Nota();
				
				current.setIdNota(rs.getInt("id_nota"));
				current.setIdAvaliacao(rs.getInt("id_avaliacao"));
				current.setIdUsuario(rs.getInt("id_usuario"));
				current.setNota(rs.getString("nota"));				

				data.add(current);
			}

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}

		return data;

	}		
	
	public static ArrayList<TabelaBoletim> getBoletimNotasPorAlunoPorCurso(int idCurso, int idTipoUsuario, int idUsuario){
		
		ArrayList<TabelaBoletim> data = new ArrayList<TabelaBoletim>();
		
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection conn = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = conn.prepareStatement(DB_SELECT_BOLETIM_NOTA_ALUNO_POR_CURSO);
			
			int count=0;
			ps.setInt(++count, idTipoUsuario);	
			ps.setInt(++count, idUsuario);
			ps.setInt(++count, idCurso);	
			ps.setInt(++count, idUsuario);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{

				TabelaBoletim current = new TabelaBoletim();
				
				current.setNomeCurso(rs.getString("nome_curso"));
				current.setNomePeriodo(rs.getString("nome_periodo"));
				current.setNomeDisciplina(rs.getString("nome_disciplina"));
				current.setNomeConteudoProgramatico(rs.getString("descricao"));
				current.setNomeAvaliacao(rs.getString("assunto"));
				current.setNota(rs.getString("nota"));				

				data.add(current);
			}

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(conn);
		}
		
		return data;
	}
	
	


}
