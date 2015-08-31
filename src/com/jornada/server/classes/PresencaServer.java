package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.Aula;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.Presenca;
import com.jornada.shared.classes.TipoPresenca;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.presenca.PresencaUsuarioAula;
import com.jornada.shared.classes.presenca.PresencaUsuarioDisciplina;
import com.jornada.shared.classes.presenca.PresencaUsuarioDisciplinaAluno;

public class PresencaServer {
	
	public static final String DB_INSERT_PRESENCA = "insert into presenca (id_aula, id_usuario, id_tipo_presenca) values (?,?,?) returning id_presenca;";
	public static final String DB_INSERT_FALTA = "insert into presenca_disciplina (id_disciplina, id_usuario, numero_falta, numero_total_aula) values (?,?,?,?) returning id_presenca_disciplina;";
	public static final String DB_SELECT_PRESENCA = "SELECT * FROM presenca order by id_aula asc;";
	public static final String DB_SELECT_PRESENCA_DISCIPLINA = "SELECT * FROM presenca_disciplina where id_disciplina=? and id_usuario=? order by id_usuario asc;";
	public static final String DB_SELECT_PRESENCA_POR_ID_PRESENCA = "SELECT * FROM presenca where id_presenca=?;";
	public static final String DB_SELECT_PRESENCA_POR_ID_AULA = "SELECT * FROM presenca where id_aula=? order by id_aula asc;";
	public static final String DB_SELECT_PRESENCA_POR_ID_AULA_ID_USUARIO = "SELECT * FROM presenca where id_aula=? and id_usuario=? order by id_aula asc;";
	public static String DB_UPDATE_PRESENCA = "UPDATE presenca set id_tipo_presenca=? where id_aula=? and id_usuario=?;";
	public static String DB_UPDATE_FALTA = "UPDATE presenca_disciplina set numero_falta=?, numero_total_aula=? where id_disciplina=? and id_usuario=?;";

	
	
	
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
	
	   public static boolean AdicionarFalta(ArrayList<PresencaUsuarioDisciplina> listPud){
	        
	        boolean isOK=false;

	        Connection conn = ConnectionManager.getConnection();
	        try {

	            for(int i=0;i<listPud.size();i++){
	                
	                PresencaUsuarioDisciplina row = listPud.get(i);
	                
	                int count = 0;
	                PreparedStatement insert = conn.prepareStatement(DB_INSERT_FALTA);
	                insert.setInt(++count, row.getIdDisciplina());
	                insert.setInt(++count, row.getUsuario().getIdUsuario());
	                insert.setInt(++count, row.getNumeroFaltas());
	                insert.setInt(++count, row.getNumeroAulas());
	                
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
	   
	   
    public static boolean updateFalta(ArrayList<PresencaUsuarioDisciplina> listPud) {
        boolean success = false;

        Connection conn = ConnectionManager.getConnection();
        try {
            
            for (PresencaUsuarioDisciplina pud : listPud) {
                int count = 0;
                PreparedStatement ps = conn.prepareStatement(DB_UPDATE_FALTA);
                ps.setInt(++count, pud.getNumeroFaltas());
                ps.setInt(++count, pud.getNumeroAulas());
                ps.setInt(++count, pud.getIdDisciplina());
                ps.setInt(++count, pud.getUsuario().getIdUsuario());

                int numberUpdate = ps.executeUpdate();

                if (numberUpdate == 1) {
                    success = true;
                }
            }

        } catch (SQLException sqlex) {
            success = false;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(conn);
        }

        return success;
    }
	
	public static boolean updateDiarioRow(int idAula, int idUsuario, int idTipoPresenca){
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
	            data=null;
	            System.err.println(sqlex.getMessage());
	        } finally {
	            ConnectionManager.closeConnection(conn);
	        }
	        return data;
	    }
	
	
	
	public static PresencaUsuarioDisciplina getPresencaDisciplinaAluno(int idDisciplina, int idAluno) {
	    PresencaUsuarioDisciplina object = new PresencaUsuarioDisciplina();       
	    Disciplina disciplina = DisciplinaServer.getDisciplina(idDisciplina);
		Connection conn = ConnectionManager.getConnection();
		try {
            PreparedStatement ps = conn.prepareStatement(DB_SELECT_PRESENCA_DISCIPLINA);
            int count = 0;
            ps.setInt(++count, idDisciplina);
            ps.setInt(++count, idAluno);
			ResultSet rs = ps.executeQuery();
			object.setUsuario(UsuarioServer.getUsuarioPeloId(idAluno));
            object.setIdDisciplina(disciplina.getIdDisciplina());        
            object.setNomeDisciplina(disciplina.getNome());  
			while (rs.next()){			  
	            object.setNumeroAulas(rs.getInt("numero_total_aula"));
	            object.setNumeroFaltas(rs.getInt("numero_falta"));	           
	        }
		} catch (SQLException sqlex) {
		    object=null;
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return object;
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
			data=null;
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
			data=null;
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
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return data;
	}	
	
	public static ArrayList<PresencaUsuarioAula> getAlunos(int idCurso){
		
		ArrayList<PresencaUsuarioAula> arrayPresencaUsuario = new ArrayList<PresencaUsuarioAula>();
		
		ArrayList<Usuario> arrayUsuario = UsuarioServer.getAlunosPorCurso(idCurso);
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
	
    public static ArrayList<PresencaUsuarioDisciplina> getAlunosDisciplina(int idCurso, int idDisciplina) {

        ArrayList<PresencaUsuarioDisciplina> arrayPresencaUsuario = new ArrayList<PresencaUsuarioDisciplina>();

        ArrayList<Usuario> arrayUsuario = UsuarioServer.getAlunosPorCurso(idCurso);

        for (Usuario user : arrayUsuario) {
            PresencaUsuarioDisciplina presencaUsuario = PresencaServer.getPresencaDisciplinaAluno(idDisciplina, user.getIdUsuario());
//            presencaUsuario.setUsuario(user);
            arrayPresencaUsuario.add(presencaUsuario);
        }

        return arrayPresencaUsuario;
    }
	
	public static ArrayList<PresencaUsuarioAula> getAlunos(int idCurso, String strAluno){
		
		ArrayList<PresencaUsuarioAula> arrayPresencaUsuario = new ArrayList<PresencaUsuarioAula>();
		
		ArrayList<Usuario> arrayUsuario = UsuarioServer.getAlunosPorCurso(idCurso, strAluno);
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

        ArrayList<Usuario> arrayUsuario = UsuarioServer.getAlunosPorCurso(idCurso);
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

        ArrayList<Usuario> arrayUsuario = UsuarioServer.getAlunosPorCurso(idCurso);
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
    
	
    public static ArrayList<PresencaUsuarioDisciplinaAluno> getPresencaUsuarioDisciplinaAluno(int idUsuario, int idCurso) {
        
        Curso curso = CursoServer.getCurso(idCurso);
        
        int intPorcentagemPresencaCurso = Integer.parseInt(curso.getPorcentagemPresenca());

        ArrayList<PresencaUsuarioDisciplinaAluno> listPuda = new ArrayList<PresencaUsuarioDisciplinaAluno>();
        
        ArrayList<Periodo> listPeriodos = PeriodoServer.getPeriodos(idCurso);

        for (Periodo periodo : listPeriodos) {

            
            ArrayList<Disciplina> listDisciplinas = DisciplinaServer.getDisciplinasPeloPeriodo(periodo.getIdPeriodo());
            periodo.setListDisciplinas(listDisciplinas);

            for (Disciplina disciplina : listDisciplinas) {
                
                PresencaUsuarioDisciplina pud = getPresencaDisciplinaAluno(disciplina.getIdDisciplina(), idUsuario);
                PresencaUsuarioDisciplinaAluno puda = new PresencaUsuarioDisciplinaAluno();
                
                
                puda.setIdPeriodo(periodo.getIdPeriodo());
                puda.setNomePeriodo(periodo.getNomePeriodo());
                puda.setIdDisciplina(pud.getIdDisciplina());
                puda.setNomeDisciplina(pud.getNomeDisciplina());
                puda.setNumeroAulas(pud.getNumeroAulas());
                puda.setNumeroFaltas(pud.getNumeroFaltas());
                puda.setUsuario(pud.getUsuario());
                puda.setNumeroPresenca(puda.getNumeroAulas()-puda.getNumeroFaltas());                

                Double doublePresenca = (((double) puda.getNumeroPresenca() / (double) puda.getNumeroAulas())) * 100;
                int quantidadePresencaSalaDeAula =  doublePresenca.intValue();
                
                puda.setPorcentagemPresencaAula(quantidadePresencaSalaDeAula);
                 
                
                if(pud.getNumeroAulas()==0){
                    puda.setSituacao(Presenca.SEM_RESULTADO);
                }else if(quantidadePresencaSalaDeAula>=intPorcentagemPresencaCurso){
                    puda.setSituacao(Presenca.APROVADO);
                }else{
                    puda.setSituacao(Presenca.REPROVADO);
                }
                
                listPuda.add(puda);
                
            }

        }

        return listPuda;
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
