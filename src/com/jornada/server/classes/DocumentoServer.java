package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jornada.server.database.ConnectionManager;
import com.jornada.server.framework.EmailFrameWork;
import com.jornada.server.framework.WordFramework;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Documento;
import com.jornada.shared.classes.RelDocumentoUsuario;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;

public class DocumentoServer {
	
	public static String DB_SELECT_DOCUMENTO = "SELECT * FROM documento where is_visible=true order by nome_documento asc;";
	public static String DB_SELECT_DOCUMENTO_IS_VISIBLE = "SELECT * FROM documento where is_visible=? and id_unidade_escola=?;";
	public static String DB_SELECT_DOCUMENTO_POR_ID = "SELECT * FROM documento where id_documento=? and is_visible=true;";
	public static String DB_SELECT_REL_DOCUMENTO_USUARIO_IDCURSO_IDDOCUMENTO = 
	        "SELECT * FROM rel_documento_usuario where id_curso=? and id_documento=? and id_tipo_usuario=?;";
    public static String DB_SELECT_REL_DOCUMENTO_USUARIO_IDCURSO_IDDOCUMENTO_SEMCURSO = 
            "SELECT * FROM rel_documento_usuario where (id_curso is null) and id_documento=? and id_tipo_usuario=?;";	
    public static String DB_SELECT_REL_DOCUMENTO_USUARIO_IDCURSO_IDDOCUMENTO_ID_USUARIO = 
            "SELECT * FROM rel_documento_usuario where id_curso=? and id_documento=? and id_usuario=?;";
    public static String DB_SELECT_REL_DOCUMENTO_USUARIO_SEMCURSO_IDDOCUMENTO_IDUSUARIO = 
            "SELECT * FROM rel_documento_usuario where (id_curso is null) and id_documento=? and id_usuario=?;";    
    public static final String DB_INSERT_REL_DOC_USUARIO = "INSERT INTO rel_documento_usuario (id_curso, id_documento, id_usuario, id_tipo_usuario, is_email_sent) VALUES (?,?,?,?,?)";	
    public static final String DB_INSERT_REL_DOC_USUARIO_SEM_CURSO = "INSERT INTO rel_documento_usuario (id_documento, id_usuario, id_tipo_usuario, is_email_sent) VALUES (?,?,?,?)";    
    public static final String DB_DELETE_REL_DOC_USUARIO = "delete from rel_documento_usuario where id_curso=? and id_documento=? and id_tipo_usuario=? ";
    public static final String DB_DELETE_REL_DOC_USUARIO_SEMCURSO = "delete from rel_documento_usuario where (id_curso is null) and id_documento=? and id_tipo_usuario=? ";
    public static final String DB_DELETE_REL_DOC_TODOS_TIPO_USUARIOS = "delete from rel_documento_usuario where id_curso=? and id_documento=? and id_tipo_usuario=?";
    public static final String DB_DELETE_REL_DOC_TODOS_TIPO_USUARIOS_SEMCURSO = "delete from rel_documento_usuario where (id_curso is null) and id_documento=? and id_tipo_usuario=?";
    
	
	public static ArrayList<Documento> getDocumentos() {

		ArrayList<Documento> data = new ArrayList<Documento>();		

		Connection conn = ConnectionManager.getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(DB_SELECT_DOCUMENTO);			
			data = getParametersDocumento(ps.executeQuery());
		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {			
			ConnectionManager.closeConnection(conn);
		}
		return data;
	}
	
    public static Documento getDocumento(int idDocumento) {

        ArrayList<Documento> data = new ArrayList<Documento>();     

        Connection conn = ConnectionManager.getConnection();
        
        try {
            PreparedStatement ps = conn.prepareStatement(DB_SELECT_DOCUMENTO_POR_ID);
            int count=0;
            ps.setInt(++count, idDocumento);
            data = getParametersDocumento(ps.executeQuery());
            
        } catch (SQLException sqlex) {
            data=null;
            System.err.println(sqlex.getMessage());
        } finally {         
            ConnectionManager.closeConnection(conn);
        }
        return data.get(0);
    }	
    
    public static Documento getDocumento(int idUnidadeEscola, boolean isVisible) {

        ArrayList<Documento> data = new ArrayList<Documento>();     

        Connection conn = ConnectionManager.getConnection();
        
        try {
            PreparedStatement ps = conn.prepareStatement(DB_SELECT_DOCUMENTO_IS_VISIBLE);
            int count=0;
            ps.setBoolean(++count, isVisible);
            ps.setInt(++count, idUnidadeEscola);
            data = getParametersDocumento(ps.executeQuery());
            
        } catch (SQLException sqlex) {
            data=null;
            System.err.println(sqlex.getMessage());
        } finally {         
            ConnectionManager.closeConnection(conn);
        }
        return data.get(0);
    }    
	
	
    public static ArrayList<RelDocumentoUsuario> getRelDocumentoUsuariosPorTipo(int idCurso, int idDocumento, int idTipoUsuario) {

        ArrayList<RelDocumentoUsuario> data = new ArrayList<RelDocumentoUsuario>();

        Connection conn = ConnectionManager.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(DB_SELECT_REL_DOCUMENTO_USUARIO_IDCURSO_IDDOCUMENTO);
            int count=0;
            ps.setInt(++count, idCurso);
            ps.setInt(++count, idDocumento);
            ps.setInt(++count, idTipoUsuario);
            
            data = getParametersRelDocumentoUsuario(ps.executeQuery());
            
        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(conn);
        }
        return data;
    }
    
    public static ArrayList<RelDocumentoUsuario> getRelDocumentoUsuariosPorTipoSemCurso(int idDocumento, int idTipoUsuario) {

        ArrayList<RelDocumentoUsuario> data = new ArrayList<RelDocumentoUsuario>();

        Connection conn = ConnectionManager.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(DB_SELECT_REL_DOCUMENTO_USUARIO_IDCURSO_IDDOCUMENTO_SEMCURSO);
            int count=0;

            ps.setInt(++count, idDocumento);
            ps.setInt(++count, idTipoUsuario);
            
            data = getParametersRelDocumentoUsuario(ps.executeQuery());
            
        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(conn);
        }
        return data;
    }    
    
    public static ArrayList<RelDocumentoUsuario> getRelDocumentoUsuarios(int idCurso, int idDocumento, int idUsuario) {

        ArrayList<RelDocumentoUsuario> data = new ArrayList<RelDocumentoUsuario>();

        Connection conn = ConnectionManager.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(DB_SELECT_REL_DOCUMENTO_USUARIO_IDCURSO_IDDOCUMENTO_ID_USUARIO);
            int count=0;
            ps.setInt(++count, idCurso);
            ps.setInt(++count, idDocumento);            
            ps.setInt(++count, idUsuario);
            
            data = getParametersRelDocumentoUsuario(ps.executeQuery());
            
        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(conn);
        }
        return data;
    }  
    
    public static ArrayList<RelDocumentoUsuario> getRelDocumentoUsuariosSemCurso(int idDocumento, int idUsuario) {

        ArrayList<RelDocumentoUsuario> data = new ArrayList<RelDocumentoUsuario>();

        Connection conn = ConnectionManager.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(DB_SELECT_REL_DOCUMENTO_USUARIO_SEMCURSO_IDDOCUMENTO_IDUSUARIO);
            int count=0;
            ps.setInt(++count, idDocumento);            
            ps.setInt(++count, idUsuario);
            
            data = getParametersRelDocumentoUsuario(ps.executeQuery());
            
        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(conn);
        }
        return data;
    }    
        
    
   
	
	private static ArrayList<Documento> getParametersDocumento(ResultSet rs){

		ArrayList<Documento> data = new ArrayList<Documento>();
		
		try{
		
		while (rs.next()) 		{
		    Documento object = new Documento();			
			object.setIdDocumento(rs.getInt("id_documento"));
			object.setNomeDocumento(rs.getString("nome_documento"));
			object.setDescricao(rs.getString("descricao"));
			object.setNomeFisicoDocumento(rs.getString("nome_fisico_documento"));
			object.setEmailSubject(rs.getString("email_subject"));
			object.setEmailContent(rs.getString("email_content"));
			data.add(object);
		}
		}catch(Exception ex){
			data=null;
			System.err.println(ex.getMessage());
		}		
		return data;
	}
	
	private static ArrayList<RelDocumentoUsuario> getParametersRelDocumentoUsuario(ResultSet rs){

        ArrayList<RelDocumentoUsuario> data = new ArrayList<RelDocumentoUsuario>();
        
        try{
        
        while (rs.next())       {
            RelDocumentoUsuario object = new RelDocumentoUsuario();         
            object.setIdRelDocumentoUsuario(rs.getInt("id_rel_documento_usuario"));
            object.setIdDocumento(rs.getInt("id_documento"));
            object.setIdCurso(rs.getInt("id_curso"));
            object.setIdUsuario(rs.getInt("id_usuario"));
            object.setIdTipoUsuario(rs.getInt("id_tipo_usuario"));
//            object.setIdUsuarioAluno(rs.getInt("id_usuario_aluno"));
            object.setEmailSent(rs.getBoolean("is_email_sent"));
            object.setUsuario(UsuarioServer.getUsuarioPeloId(object.getIdUsuario()));

            data.add(object);
        }
        }catch(Exception ex){
            data=null;
            System.err.println(ex.getMessage());
        }       
        return data;
    }	
	
    public static boolean associarDocumentoUsuarios(int idCurso, int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios){

        boolean success = false;

//      JornadaDataBase dataBase = new JornadaDataBase();
        Connection connection = ConnectionManager.getConnection();
        try {
//          dataBase.createConnection();
//          Connection connection = dataBase.getConnection();
            
            
            if (listIdUsuarios != null && listIdUsuarios.size() > 0) {
                deleteAssociacaoDocumentoUsuarios(connection, idCurso, idDocumento, idTipoUsuario, listIdUsuarios);
                insertAssociacaoUsuarioDocumento(connection, idCurso, idDocumento, idTipoUsuario, listIdUsuarios);
            } else if (listIdUsuarios.size() == 0) {
                deleteAssociacaoDocumentoTodosUsuarios(connection, idCurso, idDocumento, idTipoUsuario);
            }            
            
            success=true;

        } catch (Exception ex) {
            success = false;
            System.err.println(ex.getMessage());
        } finally {
            // dataBase.close();
            ConnectionManager.closeConnection(connection);
        }

        return success;
    }     
    
    public static boolean associarDocumentoTodosUsuarios(int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios){

        boolean success = false;

//      JornadaDataBase dataBase = new JornadaDataBase();
        Connection connection = ConnectionManager.getConnection();
        try {

            {
//                //Adicionando Usuarios sem curso
                if (listIdUsuarios != null && listIdUsuarios.size() > 0) {
                    deleteAssociacaoDocumentoUsuariosSemCurso(connection, idDocumento, idTipoUsuario, listIdUsuarios);
                    insertAssociacaoUsuarioDocumentoSemCurso(connection, idDocumento, idTipoUsuario, listIdUsuarios);
                } else if (listIdUsuarios.size() == 0) {
                    deleteAssociacaoDocumentoTodosUsuariosSemCurso(connection, idDocumento, idTipoUsuario);
                }
            }
            
            success=true;

        } catch (Exception ex) {
            success = false;
            System.err.println(ex.getMessage());
        } finally {
            // dataBase.close();
            ConnectionManager.closeConnection(connection);
        }

        return success;
    }       

    private static int deleteAssociacaoDocumentoUsuarios(Connection conn, int idCurso, int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios){
        
        int count = 0;
        int deleted = 0;
        
        String strNotIn=" and id_usuario not in (";        
        for (int i = 0; i < listIdUsuarios.size(); i++) {
            strNotIn = strNotIn + "," + listIdUsuarios.get(i);
        }
        strNotIn = strNotIn.replaceFirst(",", "")+")";
        

        try {
            
            Connection connection = conn;
            
            String strQueryDelete = DB_DELETE_REL_DOC_USUARIO + strNotIn;

            PreparedStatement psDelete = connection.prepareStatement(strQueryDelete);
            psDelete.setInt(++count, idCurso);
            psDelete.setInt(++count, idDocumento);
            psDelete.setInt(++count, idTipoUsuario);
            deleted = psDelete.executeUpdate();

        } catch (SQLException sqlex) {
            System.err.println(sqlex.getMessage());
        } finally {
            // dataBase.close();
            
        }
        
        return deleted;
        
    }  
    
    private static int deleteAssociacaoDocumentoUsuariosSemCurso(Connection conn, int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios){
        
        int count = 0;
        int deleted = 0;
        
        String strNotIn=" and id_usuario not in (";        
        for (int i = 0; i < listIdUsuarios.size(); i++) {
            strNotIn = strNotIn + "," + listIdUsuarios.get(i);
        }
        strNotIn = strNotIn.replaceFirst(",", "")+")";
        

        try {
            
            Connection connection = conn;
            
            String strQueryDelete = DB_DELETE_REL_DOC_USUARIO_SEMCURSO + strNotIn;

            PreparedStatement psDelete = connection.prepareStatement(strQueryDelete);
            psDelete.setInt(++count, idDocumento);
            psDelete.setInt(++count, idTipoUsuario);
            deleted = psDelete.executeUpdate();

        } catch (SQLException sqlex) {
            System.err.println(sqlex.getMessage());
        } finally {
            // dataBase.close();
            
        }
        
        return deleted;
        
    }     
    
    
    private static int deleteAssociacaoDocumentoTodosUsuarios(Connection conn, int idCurso, int idDocumento,int idTipoUsuario){
        
        int count = 0;
        int deleted = 0;
        
        try {
            
            Connection connection = conn;
            
            String strQueryDelete = DB_DELETE_REL_DOC_TODOS_TIPO_USUARIOS;

            PreparedStatement psDelete = connection.prepareStatement(strQueryDelete);
            psDelete.setInt(++count, idCurso);
            psDelete.setInt(++count, idDocumento);
            psDelete.setInt(++count, idTipoUsuario);
            deleted = psDelete.executeUpdate();

        } catch (SQLException sqlex) {
            System.err.println(sqlex.getMessage());
        } finally {
            // dataBase.close();
            
        }
        
        return deleted;
        
    } 
    
    private static int deleteAssociacaoDocumentoTodosUsuariosSemCurso(Connection conn, int idDocumento,int idTipoUsuario){
        
        int count = 0;
        int deleted = 0;
        
        try {
            
            Connection connection = conn;
            
            String strQueryDelete = DB_DELETE_REL_DOC_TODOS_TIPO_USUARIOS_SEMCURSO;

            PreparedStatement psDelete = connection.prepareStatement(strQueryDelete);
            psDelete.setInt(++count, idDocumento);
            psDelete.setInt(++count, idTipoUsuario);
            deleted = psDelete.executeUpdate();

        } catch (SQLException sqlex) {
            System.err.println(sqlex.getMessage());
        } finally {
            // dataBase.close();
            
        }
        
        return deleted;
        
    }      
    
    private static int insertAssociacaoUsuarioDocumento(Connection conn, int idCurso, int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios){
        
        int count = 0;
        int intAddedItems = 0;

        try {
            
            Connection connection = conn;
            
            //"INSERT INTO rel_documento_usuario (id_curso, id_documento, id_usuario, id_tipo_usuario, is_email_sent) VALUES (?,?,?,?,?)";
            for (int i = 0; i < listIdUsuarios.size(); i++) {
                count = 0;
                int idUsuario = Integer.parseInt(listIdUsuarios.get(i));
                if (DocumentoServer.getRelDocumentoUsuarios(idCurso, idDocumento, idUsuario).size() == 0) {
                    PreparedStatement psInsert = connection.prepareStatement(DB_INSERT_REL_DOC_USUARIO);
                    psInsert.setInt(++count, idCurso);
                    psInsert.setInt(++count, idDocumento);
                    psInsert.setInt(++count, idUsuario);
                    psInsert.setInt(++count, idTipoUsuario);
                    psInsert.setBoolean(++count, false);
                    intAddedItems = psInsert.executeUpdate();
                }
            }

        } catch (SQLException sqlex) {
            System.err.println(sqlex.getMessage());
        } finally {
            // dataBase.close();
        }
        
        return intAddedItems;
        
    }
    
    private static int insertAssociacaoUsuarioDocumentoSemCurso(Connection conn, int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios){
        
        int count = 0;
        int intAddedItems = 0;

        try {
            
            Connection connection = conn;
            
            //"INSERT INTO rel_documento_usuario (id_curso, id_documento, id_usuario, id_tipo_usuario, is_email_sent) VALUES (?,?,?,?,?)";
            for (int i = 0; i < listIdUsuarios.size(); i++) {
                count = 0;
                
                int idUsuario = Integer.parseInt(listIdUsuarios.get(i));
                int row = DocumentoServer.getRelDocumentoUsuariosSemCurso(idDocumento, idUsuario).size();
                if (0 == row) {
                    PreparedStatement psInsert = connection.prepareStatement(DB_INSERT_REL_DOC_USUARIO_SEM_CURSO);
                    psInsert.setInt(++count, idDocumento);
                    psInsert.setInt(++count, idUsuario);
                    psInsert.setInt(++count, idTipoUsuario);
                    psInsert.setBoolean(++count, false);
                    intAddedItems = psInsert.executeUpdate();
                }
            }

        } catch (SQLException sqlex) {
            System.err.println(sqlex.getMessage());
        } finally {
            // dataBase.close();
        }
        
        return intAddedItems;
        
    }    
	
    public static String enviarEmailDocumento(int idCurso, int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios, String url){

        EmailFrameWork emailFramework = new EmailFrameWork();
        ArrayList<Integer> listIntIdUsuarios = new ArrayList<Integer>();
        for (int i = 0; i < listIdUsuarios.size(); i++) {
            listIntIdUsuarios.add(Integer.parseInt(listIdUsuarios.get(i)));
        }

        if(idTipoUsuario==TipoUsuario.PAIS){
            if(idCurso==0){
                return emailFramework.sendMailByUserIdParaDocumentos(idCurso, idDocumento, listIntIdUsuarios, url);
            }else{
                return emailFramework.sendMailByUserIdParaDocumentos(idCurso, idDocumento, listIntIdUsuarios, url);    
            }            
        }else{
            return emailFramework.sendDocumentosPorEmailParaOsPaisDosAlunos(idCurso, idDocumento, listIntIdUsuarios, url);
        }
    }
    
    public static String enviarEmailDocumentoSemCurso(int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios, String url){
        
        String status="";

        EmailFrameWork emailFramework = new EmailFrameWork();
        
        //Enviar documento para pais com filhos em curso
        ArrayList<Curso> listCurso = CursoServer.getCursos();        
        for (int i = 0; i < listCurso.size(); i++) {
            
            int idCurso=listCurso.get(i).getIdCurso();
            
            ArrayList<Integer> listIntIdUsuarios = new ArrayList<Integer>();
            
            for (int j = 0; j < listIdUsuarios.size(); j++) {
                String idUser = listIdUsuarios.get(j);
                
                Usuario user = new Usuario();
                user.setIdUsuario(Integer.parseInt(idUser));
                
                ArrayList<Curso> cursosUsuario;
                
                if (idTipoUsuario == TipoUsuario.PAIS) {
                    cursosUsuario = CursoServer.getCursosPorPaiAmbientePais(user);    
                }else{
                    cursosUsuario = CursoServer.getCursosPorAlunoAmbienteAluno(user, true);
                }
                
                        
                if(cursosUsuario!=null && cursosUsuario.size()>0){
                    listIntIdUsuarios.add(user.getIdUsuario());
                }                
            }            

            if (idTipoUsuario == TipoUsuario.PAIS) {
                status = emailFramework.sendMailByUserIdParaDocumentosSemCurso(idCurso, idDocumento, listIntIdUsuarios, url);
              } else {
                status = emailFramework.sendDocumentosPorEmailParaOsPaisDosAlunosSemCurso(idCurso, idDocumento, listIntIdUsuarios, url);
            }
        }
        
        //Enviar documento para pais sem filhos em curso
        {
            ArrayList<Integer> listIntIdUsuarios = new ArrayList<Integer>();
            
            for (int i = 0; i < listIdUsuarios.size(); i++) {

                Usuario user = new Usuario();
                user.setIdUsuario(Integer.parseInt(listIdUsuarios.get(i)));
                ArrayList<Curso> cursosUsuarios;
                if (idTipoUsuario == TipoUsuario.PAIS) {
                    cursosUsuarios = CursoServer.getCursosPorPaiAmbientePais(user);    
                }else{
                    cursosUsuarios = CursoServer.getCursosPorAlunoAmbienteAluno(user, true);
                }
                
                if(cursosUsuarios==null || cursosUsuarios.size()==0){
                    listIntIdUsuarios.add(user.getIdUsuario());
                }   
            }
            if (idTipoUsuario == TipoUsuario.PAIS) {
                status = emailFramework.sendMailByUserIdParaDocumentosSemCurso(0, idDocumento, listIntIdUsuarios, url);
              } else {
                status = emailFramework.sendDocumentosPorEmailParaOsPaisDosAlunosSemCurso(0, idDocumento, listIntIdUsuarios, url);
            }
        }
        
        
        return status;
    }    
    
    
    public static final String DB_UPDATE_EMAIL_SENT = "UPDATE rel_documento_usuario set is_email_sent=? where id_curso=? and id_documento=? and id_usuario=?";
    
    public static boolean setEmailSent(int idCurso, int idDocumento, int idUsuario, boolean isEmailSent){
        boolean success=false;

//      JornadaDataBase dataBase = new JornadaDataBase();
        Connection connection = ConnectionManager.getConnection();

        try {
//          dataBase.createConnection();
//          Connection connection = dataBase.getConnection();
            
            int count = 0;
            PreparedStatement update = connection.prepareStatement(DB_UPDATE_EMAIL_SENT);
            update.setBoolean(++count, isEmailSent);
            update.setInt(++count, idCurso);
            update.setInt(++count, idDocumento);
            update.setInt(++count, idUsuario);

            int numberUpdate = update.executeUpdate();


            if (numberUpdate == 1) {
                success = true;
            }


        } catch (SQLException sqlex) {
            success=false;
            System.err.println(sqlex.getMessage());         
        } finally {
//          dataBase.close();
            ConnectionManager.closeConnection(connection);
        }
        
        return success;
    } 

    
    public static final String DB_UPDATE_EMAIL_SENT_SEMCURSO = "UPDATE rel_documento_usuario set is_email_sent=? where (id_curso is null) and id_documento=? and id_usuario=?";
    public static boolean setEmailSent(int idDocumento, int idUsuario, boolean isEmailSent){
        boolean success=false;

//      JornadaDataBase dataBase = new JornadaDataBase();
        Connection connection = ConnectionManager.getConnection();

        try {
//          dataBase.createConnection();
//          Connection connection = dataBase.getConnection();
            
            int count = 0;
            PreparedStatement update = connection.prepareStatement(DB_UPDATE_EMAIL_SENT_SEMCURSO);
            update.setBoolean(++count, isEmailSent);
            update.setInt(++count, idDocumento);
            update.setInt(++count, idUsuario);

            int numberUpdate = update.executeUpdate();


            if (numberUpdate == 1) {
                success = true;
            }


        } catch (SQLException sqlex) {
            success=false;
            System.err.println(sqlex.getMessage());         
        } finally {
//          dataBase.close();
            ConnectionManager.closeConnection(connection);
        }
        
        return success;
    }     
    
    
    public static String mountarUrlDocumentoPai(Usuario usuario, Curso Curso){

        String url = "?";
        
        String strUser = "nome_pai="+usuario.getPrimeiroNome() + " " + usuario.getSobreNome()+"&";
        String strRg = "rg="+((usuario.getRg()==null)?"":usuario.getRg())+"&";
        String strCpf = "cpf="+((usuario.getCpf()==null)?"":usuario.getCpf())+"&";
        String strEndereco = ((usuario.getEndereco()==null)?"":" Endere�o "+usuario.getEndereco());
        String strNumRes = ((usuario.getNumeroResidencia()==null)?"":" Num."+usuario.getNumeroResidencia());
        String strBairro = ((usuario.getBairro()==null)?"":" Bairro "+usuario.getBairro());
        String strCidade = ((usuario.getCidade()==null)?"":" Munic�pio "+usuario.getCidade());
        String strUF = ((usuario.getUnidadeFederativa()==null)?"":usuario.getUnidadeFederativa());
        String strCep = ((usuario.getCep()==null)?"":" Cep:"+usuario.getCep());
        
        strEndereco = "endereco="+strEndereco+strNumRes + strBairro + strCidade + "-"+ strUF + strCep+"&";
        
        url = url+strUser+strRg+strCpf+strEndereco;
        
        return url;
    }
    
    public static String criarDocumentoFilho(Usuario usuario, String hostPageBaseURL, String nomeFisicoDoc){

        
        ArrayList<Curso> listCurso = CursoServer.getCursosPorAlunoAmbienteAluno(usuario, true);
        
        String strCursos="";
        for (int i = 0; i < listCurso.size(); i++) {
            if(i==0){
                strCursos=listCurso.get(i).getNome();
            }else{
                strCursos = strCursos+", "+listCurso.get(i).getNome();
            }
        }        
        
        ArrayList<Usuario> listPais = UsuarioServer.getTodosOsPaisDoAluno(usuario.getIdUsuario());
        
        Usuario userRespFinanceiro = new Usuario();
        Usuario userRespAcademico = new Usuario();
        
        @SuppressWarnings("unused")
        boolean hasRespFinan=false;
        @SuppressWarnings("unused")
        boolean hasRespAcad=false;
        for (int i = 0; i < listPais.size(); i++) {
            if(listPais.get(i).isRespFinanceiro()){
                userRespFinanceiro = listPais.get(i);
                hasRespFinan=true;
            }else if(listPais.get(i).isRespFinanceiro()){
                userRespAcademico = listPais.get(i);
                hasRespAcad=true;
            }
        }
        
        String nomeAluno  = getCleanText(usuario.getPrimeiroNome()) + " " + getCleanText(usuario.getSobreNome());
        String nomeRespFinan  = getCleanText(userRespFinanceiro.getPrimeiroNome()) + " " + getCleanText(userRespFinanceiro.getSobreNome());
        String rgRespFinan = getCleanText(userRespFinanceiro.getRg());
        String cpfRespFinan = getCleanText(userRespFinanceiro.getCpf());
        String enderecoRespFinan = getCleanText(userRespFinanceiro.getEndereco())+" "+getCleanText(userRespFinanceiro.getBairro())+" "+
                getCleanText(userRespFinanceiro.getNumeroResidencia()) + " " + getCleanText(userRespFinanceiro.getCidade()) + " " + 
                getCleanText(userRespFinanceiro.getUnidadeFederativa()) + " " + getCleanText(userRespFinanceiro.getCep()); 
        
        String nomeRespAcad  = getCleanText(userRespAcademico.getPrimeiroNome()) + " " + getCleanText(userRespAcademico.getSobreNome());
        String rgRespAcad = getCleanText(userRespAcademico.getRg());
        String cpfRespAcad = getCleanText(userRespAcademico.getCpf());
        String enderecoRespAcad = getCleanText(userRespAcademico.getEndereco())+" "+getCleanText(userRespAcademico.getBairro())+" "+
                getCleanText(userRespAcademico.getNumeroResidencia()) + " " + getCleanText(userRespAcademico.getCidade()) + " " + 
                getCleanText(userRespAcademico.getUnidadeFederativa()) + " " + getCleanText(userRespAcademico.getCep()); 
        
        
        WordFramework word = new WordFramework();
        String docAddress = word.createWordParaContratoAluno(nomeFisicoDoc,
                nomeAluno, strCursos, 
                nomeRespFinan, cpfRespFinan, rgRespFinan, enderecoRespFinan, 
                nomeRespAcad, cpfRespAcad, rgRespAcad, enderecoRespAcad);
        
        String url = hostPageBaseURL+docAddress;
        
        return url;
    }    
    
    private static String getCleanText(String strText){
        if(strText==null){
            return "";
        }else{
            return strText;
        }
    }
    


}

