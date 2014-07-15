package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.Aula;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.Presenca;
import com.jornada.shared.classes.TipoPresenca;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.presenca.PresencaUsuarioAula;

public class PresencaServer {
	
	public static final String DB_INSERT_PRESENCA = "insert into presenca (id_aula, id_usuario, id_tipo_presenca) values (?,?,?) returning id_presenca;";
	public static final String DB_SELECT_PRESENCA = "SELECT * FROM presenca order by id_aula asc;";
	public static final String DB_SELECT_PRESENCA_POR_ID_PRESENCA = "SELECT * FROM presenca where id_presenca=?;";
	public static final String DB_SELECT_PRESENCA_POR_ID_AULA = "SELECT * FROM presenca where id_aula=? order by id_aula asc;";
	public static final String DB_SELECT_PRESENCA_POR_ID_AULA_ID_USUARIO = "SELECT * FROM presenca where id_aula=? and id_usuario=? order by id_aula asc;";
	public static String DB_UPDATE_PRESENCA = "UPDATE presenca set id_tipo_presenca=? where id_aula=? and id_usuario=?;";

	
	
	
	public static boolean AdicionarPresenca(ArrayList<Presenca> arrayPresenca){
		
		boolean isOK=false;

		Connection conn = ConnectionManager.getConnection();
		try {

			for(int i=0;i<arrayPresenca.size();i++){
				
				Presenca presenca = arrayPresenca.get(i);
				
				int count = 0;
				PreparedStatement insert = conn.prepareStatement(DB_INSERT_PRESENCA);
				insert.setInt(++count, presenca.getIdAula());
				insert.setInt(++count, presenca.getIdUsuario());
				insert.setInt(++count, presenca.getIdTipoPresenca());
				
				ResultSet rs = insert.executeQuery();			
				rs.next();
				
			}

			
			isOK = true;

		} catch (SQLException sqlex) {
			isOK=false;
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(conn);
		}
		
		return isOK;

	}	
	
	public static boolean updatePresencaRow(int idAula, int idUsuario, int idTipoPresenca){
		boolean success=false;

		Connection conn = ConnectionManager.getConnection();
		try {
			int count = 0;
			PreparedStatement ps = conn.prepareStatement(DB_UPDATE_PRESENCA);
			ps.setInt(++count, idTipoPresenca);
			ps.setInt(++count, idAula);
			ps.setInt(++count, idUsuario);

			int numberUpdate = ps.executeUpdate();


			if (numberUpdate == 1) {
				success = true;
			}


		} catch (SQLException sqlex) {
			success=false;
			System.err.println(sqlex.getMessage());			
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		
		return success;
	}	
	
	
	
	
	public static ArrayList<Presenca> getPresencas() {
		ArrayList<Presenca> data = new ArrayList<Presenca>();		
		Connection conn = ConnectionManager.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(DB_SELECT_PRESENCA);			
			data = getParameters(ps.executeQuery());
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return data;
	}
	
	public static Presenca getPresenca(int idPresenca) {
		ArrayList<Presenca> data = new ArrayList<Presenca>();
		Presenca presenca = null;
		Connection conn = ConnectionManager.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(DB_SELECT_PRESENCA_POR_ID_PRESENCA);		
			int count=0;
			ps.setInt(++count, idPresenca);
			data = getParameters(ps.executeQuery());
			presenca = data.get(0);		
			
			presenca.setTipoPresenca(TipoPresencaServer.getTipoPresenca(presenca.getIdTipoPresenca()));
			
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return presenca;
	}	
	
	public static ArrayList<Presenca> getPresencas(int idAula) {
		ArrayList<Presenca> data = new ArrayList<Presenca>();		
		Connection conn = ConnectionManager.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(DB_SELECT_PRESENCA_POR_ID_AULA);
			int count=0;
			ps.setInt(++count, idAula);
			data = getParameters(ps.executeQuery());
			
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return data;
	}	
	
	public static ArrayList<Presenca> getPresencas(int idAula, int idUsuario) {
		ArrayList<Presenca> data = new ArrayList<Presenca>();		
		Connection conn = ConnectionManager.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(DB_SELECT_PRESENCA_POR_ID_AULA_ID_USUARIO);
			int count=0;
			ps.setInt(++count, idAula);
			ps.setInt(++count, idUsuario);
			data = getParameters(ps.executeQuery());
			
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return data;
	}	
	
	public static ArrayList<PresencaUsuarioAula> getAlunos(int idCurso){
		
		ArrayList<PresencaUsuarioAula> arrayPresencaUsuario = new ArrayList<PresencaUsuarioAula>();
		
		ArrayList<Usuario> arrayUsuario = UsuarioServer.getUsuariosPorCurso(idCurso);
//		Presenca presenca = PresencaServer.getPresenca(1);	
		Presenca presenca = new Presenca();
		TipoPresenca tipoPresenca = TipoPresencaServer.getTipoPresenca(1);
		presenca.setIdTipoPresenca(tipoPresenca.getIdTipoPresenca());
		presenca.setTipoPresenca(tipoPresenca);		
		
		for(int i=0;i<arrayUsuario.size();i++){
			PresencaUsuarioAula presencaUsuarioAula = new PresencaUsuarioAula();			
			presencaUsuarioAula.setUsuario(arrayUsuario.get(i));
			presencaUsuarioAula.setPresenca(presenca);			
//			presencaUsuario.setIdTipoPresenca(1);		
			arrayPresencaUsuario.add(presencaUsuarioAula);
		}
		
		return arrayPresencaUsuario;
	}
	
	public static ArrayList<PresencaUsuarioAula> getAlunos(int idCurso, String strAluno){
		
		ArrayList<PresencaUsuarioAula> arrayPresencaUsuario = new ArrayList<PresencaUsuarioAula>();
		
		ArrayList<Usuario> arrayUsuario = UsuarioServer.getUsuariosPorCurso(idCurso, strAluno);
		Presenca presenca = PresencaServer.getPresenca(1);	
		
		for(int i=0;i<arrayUsuario.size();i++){
			PresencaUsuarioAula presencaUsuarioAula = new PresencaUsuarioAula();
			presencaUsuarioAula.setUsuario(arrayUsuario.get(i));
			presencaUsuarioAula.setPresenca(presenca);
//			presencaUsuario.setIdTipoPresenca(1);		
			arrayPresencaUsuario.add(presencaUsuarioAula);
		}
		
		return arrayPresencaUsuario;
	}
	
    public static ArrayList<PresencaUsuarioAula> getListaPresencaAlunos(int idCurso, int idDisciplina)
    {

        ArrayList<Usuario> arrayUsuario = UsuarioServer.getUsuariosPorCurso(idCurso);
        ArrayList<Aula> arrayAula = AulaServer.getAulas(idDisciplina);


        ArrayList<PresencaUsuarioAula> resultRows = new ArrayList<PresencaUsuarioAula>();

		for (int i = 0; i < arrayUsuario.size(); i++) {
			Usuario usuario = arrayUsuario.get(i);


			PresencaUsuarioAula pu = new PresencaUsuarioAula();
			pu.setUsuario(usuario);

			for (int ca = 0; ca < arrayAula.size(); ca++) {
				Aula aula = arrayAula.get(ca);
				ArrayList<Presenca> arrayPresenca = PresencaServer.getPresencas(aula.getIdAula());
				boolean isAvailable = false;
				for (int cp = 0; cp < arrayPresenca.size(); cp++) {
					Presenca presenca = arrayPresenca.get(cp);

					if (presenca.getIdUsuario() == usuario.getIdUsuario()) {
						isAvailable = true;
						pu.setPresenca(presenca);

					}
				}
				if (isAvailable == false) {
					pu.setPresenca(null);
				}
			}
			resultRows.add(pu);
		}
        

//        System.out.println(resultRows);
        return resultRows;
    }		
    
    public static ArrayList<ArrayList<String>> getListaPresencaAlunosArrayList(int idCurso, int idDisciplina)
    {

        ArrayList<Usuario> arrayUsuario = UsuarioServer.getUsuariosPorCurso(idCurso);
        ArrayList<Aula> arrayAula = AulaServer.getAulas(idDisciplina);

        ArrayList<ArrayList<String>> resultRows = new ArrayList<ArrayList<String>>();

        for (int i = 0; i < arrayUsuario.size(); i++)
        {
            Usuario usuario = arrayUsuario.get(i);

           
            ArrayList<String> rowInfomacaoUsuario = new ArrayList<String>();
            rowInfomacaoUsuario.add(Integer.toString(usuario.getIdUsuario()));
            rowInfomacaoUsuario.add(usuario.getPrimeiroNome() + " " + usuario.getSobreNome());
            
            
            ArrayList<String> rowTipoPresenca = new ArrayList<String>();
            int countFaltas=0;
            for (int ca = 0; ca < arrayAula.size(); ca++)
            {
                Aula aula = arrayAula.get(ca);
                ArrayList<Presenca> arrayPresenca = PresencaServer.getPresencas(aula.getIdAula());
                boolean isAvailable=false;
                for (int cp = 0; cp < arrayPresenca.size(); cp++)
                {
                    Presenca presenca = arrayPresenca.get(cp);

                    if (presenca.getIdUsuario() == usuario.getIdUsuario())
                    {                     
                        isAvailable=true;
                        rowTipoPresenca.add(Integer.toString(presenca.getIdTipoPresenca()));
                        if(presenca.getIdTipoPresenca()==Presenca.FALTA){
                        	countFaltas++;
                        }
                    }
                }
                if(isAvailable==false){
                    rowTipoPresenca.add(Integer.toString(Presenca.SEM_MATRICULA));
                }
            }
            
            rowInfomacaoUsuario.add(Integer.toString(countFaltas));
            
            rowInfomacaoUsuario.addAll(rowTipoPresenca);
            
            resultRows.add(rowInfomacaoUsuario);


        }
        
//        System.out.println(firstRow);
        System.out.println(resultRows);
        return resultRows;
    }	    
	
	public static ArrayList<Periodo> getPresencaAluno(int idUsuario, int idCurso){
		
		ArrayList<Periodo> periodos = PeriodoServer.getPeriodos(idCurso);
		
		for(int cvPer=0;cvPer<periodos.size();cvPer++){
			
			int idPeriodo = periodos.get(cvPer).getIdPeriodo();			
			ArrayList<Disciplina> disciplinas = DisciplinaServer.getDisciplinasPeloPeriodo(idPeriodo);			
			periodos.get(cvPer).setListDisciplinas(disciplinas);
			
			for(int cvDis=0;cvDis<disciplinas.size();cvDis++){
				
				int idDisciplina = disciplinas.get(cvDis).getIdDisciplina();
				ArrayList<Aula> aulas = AulaServer.getAulas(idDisciplina);				
				disciplinas.get(cvDis).setListAula(aulas);
				
				for(int cvAula=0;cvAula<aulas.size();cvAula++){
					
					int idAula = aulas.get(cvAula).getIdAula();
					ArrayList<Presenca> presencas = PresencaServer.getPresencas(idAula, idUsuario);					
					aulas.get(cvAula).setArrayPresenca(presencas);
					
					for(int cvPre=0;cvPre<presencas.size();cvPre++){
						int idTipoPresenca = presencas.get(cvPre).getIdTipoPresenca();
						TipoPresenca tipoPresenca = TipoPresencaServer.getTipoPresenca(idTipoPresenca);
						presencas.get(cvPre).setTipoPresenca(tipoPresenca);
					}
					
				}
				
			}
			
		}

		return periodos;
	}    
    
    
    
	private static ArrayList<Presenca> getParameters(ResultSet rs){

		ArrayList<Presenca> data = new ArrayList<Presenca>();
		
		try{
		
		while (rs.next()) 		{
			Presenca object = new Presenca();		
			object.setIdPresenca(rs.getInt("id_presenca"));
			object.setIdAula(rs.getInt("id_aula"));
			object.setIdUsuario(rs.getInt("id_usuario"));
			object.setIdTipoPresenca(rs.getInt("id_tipo_presenca"));
			data.add(object);
		}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}		
		return data;
	}

}
