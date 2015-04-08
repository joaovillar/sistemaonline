package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.Usuario;




public class PeriodoServer {
	
	
	public static String DB_INSERT_PERIODO = "INSERT INTO periodo (nome_periodo, descricao, numeracao, objetivo, id_curso, data_inicial, data_final, peso) VALUES (?,?,?,?,?,?,?,?) returning id_periodo";
	public static String DB_UPDATE_PERIODO = "UPDATE periodo set nome_periodo=?, descricao=?, objetivo=?, id_curso=?, data_inicial=?, data_final=?, peso=? where id_periodo=?";
	public static String DB_SELECT_PERIODO = "SELECT * FROM periodo where (nome_periodo ilike ?);";
	public static String DB_SELECT_PERIODO_ID = "SELECT * FROM periodo where id_periodo=?;";
	public static String DB_SELECT_PERIODO_ALL = "SELECT * FROM periodo order by nome_periodo asc;";
	public static String DB_SELECT_PERIODO_PELO_CURSO = "SELECT * FROM periodo where id_curso=? order by nome_periodo asc;";
	public static String DB_DELETE_PERIODO = "delete from periodo where id_periodo=?";	
	
	public static String DB_SELECT_PERIODO_PELO_CURSO_AMBIENTE_PROFESSOR=
			"select * from periodo where id_periodo in( "+
			"		select id_periodo from disciplina where id_usuario=? "+
			") and id_curso=? ";

	
	public PeriodoServer(){
		
	}

    public static String AdicionarPeriodoString(Periodo periodo) {

        String success = "false";

        // JornadaDataBase dataBase = new JornadaDataBase();
        Connection connection = ConnectionManager.getConnection();

        try {
            // dataBase.createConnection();
            // Connection conn = dataBase.getConnection();

            Date date = new Date();

            if (periodo.getDataInicial() == null) {
                periodo.setDataInicial(date);
            }
            if (periodo.getDataFinal() == null) {
                periodo.setDataFinal(date);
            }

            int param = 0;
            PreparedStatement pstmtInsertPeriodo = connection.prepareStatement(DB_INSERT_PERIODO);
            pstmtInsertPeriodo.setString(++param, periodo.getNomePeriodo().trim());
            pstmtInsertPeriodo.setString(++param, periodo.getDescricao());
            pstmtInsertPeriodo.setString(++param, periodo.getNumeracao());
            pstmtInsertPeriodo.setString(++param, periodo.getObjetivo());
            pstmtInsertPeriodo.setInt(++param, periodo.getIdCurso());
            pstmtInsertPeriodo.setDate(++param, new java.sql.Date(periodo.getDataInicial().getTime()));
            pstmtInsertPeriodo.setDate(++param, new java.sql.Date(periodo.getDataFinal().getTime()));
            pstmtInsertPeriodo.setString(++param, periodo.getPeso());

            ResultSet rs = pstmtInsertPeriodo.executeQuery();
            rs.next();

            success = "true";

        } catch (Exception ex) {
            success = ex.getMessage();
            System.err.println(ex.getMessage());
        } finally {
            // dataBase.close();
            ConnectionManager.closeConnection(connection);

        }

        return success;
    }
	
	public static int AdicionarPeriodo(Periodo periodo) {
		
		int idPeriodo=0;
		
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection conn = dataBase.getConnection();
			
			Date date = new Date();
			
			if (periodo.getDataInicial() == null) {
				periodo.setDataInicial(date);
			}
			if (periodo.getDataFinal() == null) {
				periodo.setDataFinal(date);
			}			
			
			
				int param = 0;
				PreparedStatement pstmtInsertPeriodo = connection.prepareStatement(DB_INSERT_PERIODO);
				pstmtInsertPeriodo.setString(++param, periodo.getNomePeriodo().trim());
				pstmtInsertPeriodo.setString(++param, periodo.getDescricao());
				pstmtInsertPeriodo.setString(++param, periodo.getNumeracao());
				pstmtInsertPeriodo.setString(++param, periodo.getObjetivo());
				pstmtInsertPeriodo.setInt(++param, periodo.getIdCurso());				
				pstmtInsertPeriodo.setDate(++param, new java.sql.Date(periodo.getDataInicial().getTime()));
				pstmtInsertPeriodo.setDate(++param, new java.sql.Date(periodo.getDataFinal().getTime()));
				pstmtInsertPeriodo.setString(++param, periodo.getPeso());   
				
				ResultSet rs = pstmtInsertPeriodo.executeQuery();			
				rs.next();
				
				idPeriodo = rs.getInt("id_periodo");
		

		}
		catch(Exception ex)
		{
			idPeriodo=0;
			System.err.println(ex.getMessage());
		}
		finally
		{
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
			
		}
		
		return idPeriodo;
	}
	
	
	public static ArrayList<Periodo> getPeriodos(int idCurso) {

		ArrayList<Periodo> data = new ArrayList<Periodo>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement psPeriodo = connection.prepareStatement(DB_SELECT_PERIODO_PELO_CURSO);
			
			int count=0;
			psPeriodo.setInt(++count, idCurso);			
			
			data = getPeriodoParameters(psPeriodo.executeQuery());
		

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}	
	
	
	public static ArrayList<Periodo> getPeriodos(String strFilter) {

		ArrayList<Periodo> data = new ArrayList<Periodo>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement psPeriodo = connection.prepareStatement(DB_SELECT_PERIODO);
			
			int count=0;
			psPeriodo.setString(++count, strFilter);			

			data = getPeriodoParameters(psPeriodo.executeQuery());
			
		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}
		
		return data;

	}
		
	public static ArrayList<Periodo> getPeriodosPeloCurso(int id_curso) {

		ArrayList<Periodo> data = new ArrayList<Periodo>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement psPeriodo = connection.prepareStatement(DB_SELECT_PERIODO_PELO_CURSO);
			
			int count=0;
			psPeriodo.setInt(++count, id_curso);			

			data = getPeriodoParameters(psPeriodo.executeQuery());

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}
	
	public static ArrayList<Periodo> getPeriodosPeloCursoAmbienteProfessor(Usuario usuario, int id_curso) {

		ArrayList<Periodo> data = new ArrayList<Periodo>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement psPeriodo = connection.prepareStatement(DB_SELECT_PERIODO_PELO_CURSO_AMBIENTE_PROFESSOR);
			
			int count=0;
			psPeriodo.setInt(++count, usuario.getIdUsuario());	
			psPeriodo.setInt(++count, id_curso);			

			data = getPeriodoParameters(psPeriodo.executeQuery());

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}
		return data;
	}
	
	public static ArrayList<Curso> getCursos(){
		return CursoServer.getCursos();
	}
	
	public static ArrayList<Periodo> getPeriodos() {

		ArrayList<Periodo> data = new ArrayList<Periodo>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try 
		{
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement psPeriodo = connection.prepareStatement(DB_SELECT_PERIODO_ALL);
			
			data = getPeriodoParameters(psPeriodo.executeQuery());

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}	
	
	public static boolean deletePeriodoRow(int id_periodo){
		
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			int count = 0;
			PreparedStatement deletePeriodo = connection.prepareStatement(DB_DELETE_PERIODO);
			deletePeriodo.setInt(++count, id_periodo);

			int numberUpdate = deletePeriodo.executeUpdate();


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
	
	public static boolean updatePeriodoRow(Periodo periodo){
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			int count = 0;
			PreparedStatement updatePeriodo = connection.prepareStatement(DB_UPDATE_PERIODO);
			updatePeriodo.setString(++count, periodo.getNomePeriodo().trim());
			updatePeriodo.setString(++count, periodo.getDescricao());
			updatePeriodo.setString(++count, periodo.getObjetivo());
			updatePeriodo.setInt(++count, periodo.getIdCurso());
			updatePeriodo.setDate(++count, new java.sql.Date(periodo.getDataInicial().getTime()));
			updatePeriodo.setDate(++count, new java.sql.Date(periodo.getDataFinal().getTime()));
			updatePeriodo.setString(++count, periodo.getPeso());
			updatePeriodo.setInt(++count, periodo.getIdPeriodo());

			int numberUpdate = updatePeriodo.executeUpdate();


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
	
	
	private static ArrayList<Periodo> getPeriodoParameters(ResultSet rs){

		ArrayList<Periodo> data = new ArrayList<Periodo>();
		
		try{
		
		while (rs.next()) 		{
			Periodo object = new Periodo();			
			object.setIdPeriodo(rs.getInt("id_periodo"));
			object.setIdCurso(rs.getInt("id_curso"));
			object.setNomePeriodo(rs.getString("nome_periodo"));
			object.setPeso(rs.getString("peso"));
			object.setDescricao(rs.getString("descricao"));
			object.setObjetivo(rs.getString("objetivo"));
			object.setDataInicial(rs.getDate("data_inicial"));
			object.setDataFinal(rs.getDate("data_final"));
			data.add(object);
		}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}		
		return data;
	}


    public static Periodo getPeriodo(int idPeriodo) {
        ArrayList<Periodo> data = new ArrayList<Periodo>();     
        Connection conn = ConnectionManager.getConnection();
        try 
        {
            PreparedStatement ps = conn.prepareStatement(PeriodoServer.DB_SELECT_PERIODO_ID);
            int count=0;
            ps.setInt(++count, idPeriodo);
            
            data = getPeriodoParameters(ps.executeQuery());

        } catch (SQLException sqlex) {
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(conn);
        }
        
        if(data==null || data.size()==0){
            return null;
        }
        else{
            return data.get(0);
        }
    }	
    
    
    
		

}
