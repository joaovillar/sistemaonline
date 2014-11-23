package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.jornada.server.database.ConnectionManager;
import com.jornada.server.framework.email.EmailFrameWork;
import com.jornada.shared.classes.Comunicado;
import com.jornada.shared.classes.Usuario;

public class EmailServer {

    
    private static final String DB_INSERT_EMAIL = "INSERT INTO comunicado (assunto,descricao,id_tipo_comunicado,data, hora) VALUES (?,?,?,?,?) returning id_comunicado;";
    private static final String DB_INSERT_EMAIL_RELACIONAMENTO = "INSERT INTO rel_comunicado_usuario (id_comunicado,id_usuario) VALUES (?,?);";
    private static final String DB_GET_USER_EMAIL = "SELECT email FROM usuario where usuario.id_usuario in (";
    private static final String DB_GET_USERS_EMAIL = "SELECT primeiro_nome, sobre_nome, id_usuario FROM usuario";
    private static final String DB_GET_USERS_EMAIL_BY_ID = "SELECT email FROM usuario where id_usuario in (";
    private static final String DB_GET_USERS_BY_COMUNICATION = "SELECT primeiro_nome, sobre_nome " + "FROM usuario u, rel_comunicado_usuario rcu " + "WHERE rcu.id_usuario = u.id_usuario and rcu.id_comunicado = ?";

    
    public static int adicionarEmail(int idTipoComunicado, String subject, String descricao, String strFileAddress){
        int idEmail=0;
        Connection conn = ConnectionManager.getConnection();
        try {
            Date date = new Date();
            int count = 0;
            PreparedStatement insert = conn.prepareStatement(DB_INSERT_EMAIL);
            insert.setString(++count, subject);
            insert.setString(++count, descricao);
            insert.setInt(++count, idTipoComunicado);
            insert.setDate(++count, new java.sql.Date(date.getTime()));
            insert.setTime(++count, new java.sql.Time(date.getTime()));
            ResultSet rs = insert.executeQuery();          
            rs.next();
            
            idEmail = rs.getInt("id_comunicado");
            
        } catch (SQLException sqlex) {
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(conn);
        }
        
        return idEmail;
    }
    
    
    public static boolean adicionarRelacionamentoEmail(int idComunicado, int idUsuario){
        boolean status=false;
        Connection conn = ConnectionManager.getConnection();
        try {
            int count = 0;
            PreparedStatement insert = conn.prepareStatement(DB_INSERT_EMAIL_RELACIONAMENTO);
            insert.setInt(++count, idComunicado);
            insert.setInt(++count, idUsuario);
            int numberInsert = insert.executeUpdate();


            if (numberInsert == 1) {
                status = true;
            }
        } catch (SQLException sqlex) {
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(conn);
        }
        
        return status;
    }    
    
    public static String getUserEmail(ArrayList<Usuario> users) {

        String emailList = "";

        Boolean firstUser = true;
        String AUX_DB_GET_USER_EMAIL = DB_GET_USER_EMAIL;
        for (Usuario user : users) {
            if (firstUser) {
                AUX_DB_GET_USER_EMAIL = AUX_DB_GET_USER_EMAIL + user.getIdUsuario();
                firstUser = false;
            } else {
                AUX_DB_GET_USER_EMAIL = AUX_DB_GET_USER_EMAIL + "," + user.getIdUsuario();
            }
        }
        firstUser = true;
        AUX_DB_GET_USER_EMAIL = AUX_DB_GET_USER_EMAIL + ");";

        Connection conn = ConnectionManager.getConnection();

        try {

            PreparedStatement ps = conn.prepareStatement(AUX_DB_GET_USER_EMAIL);

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

    public static Boolean sendOcorrenciaPorEmail(ArrayList<Integer> userList, String subject, String content) {

        EmailFrameWork emailFramework = new EmailFrameWork();

        emailFramework.sendOcorrenciaPorEmail(userList, subject, content);

        return true;
    }

    public static ArrayList<String> getEmailListByUserId(ArrayList<Integer> userIdList) {
        ArrayList<String> emailList = new ArrayList<String>();

        Boolean firstUser = true;

        String AUX_DB_GET_USERS_EMAIL_BY_ID = DB_GET_USERS_EMAIL_BY_ID;
        for (Integer id : userIdList) {
            if (firstUser) {
                AUX_DB_GET_USERS_EMAIL_BY_ID = AUX_DB_GET_USERS_EMAIL_BY_ID + id;
                firstUser = false;
            } else {
                AUX_DB_GET_USERS_EMAIL_BY_ID = AUX_DB_GET_USERS_EMAIL_BY_ID + "," + id;
            }
        }

        firstUser = true;
        AUX_DB_GET_USERS_EMAIL_BY_ID = AUX_DB_GET_USERS_EMAIL_BY_ID + ");";

        Connection conn = ConnectionManager.getConnection();

        try {

            PreparedStatement ps = conn.prepareStatement(DB_GET_USERS_EMAIL_BY_ID);

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

    public static ArrayList<String> getComunicadoEmailList(Comunicado comunicado) {

        ArrayList<String> userList = new ArrayList<String>();
        Connection conn = ConnectionManager.getConnection();
        try {

            PreparedStatement ps = conn.prepareStatement(DB_GET_USERS_BY_COMUNICATION);
            ps.setInt(1, comunicado.getIdComunicado());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String userName = rs.getString("primeiro_nome") + " " + rs.getString("sobre_nome");
                userList.add(userName);
            }

        } catch (SQLException sqlex) {
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(conn);
        }
        return userList;
    }

    
    public static String sendEmailAlunosPaisProfessores(ArrayList<Integer> listUser, String subject, String content, String strFileAddress, String strFileName) {
        String status ="";
        EmailFrameWork emailFramework = new EmailFrameWork();
        status = emailFramework.sendEmailAlunosPaisProfessores(listUser, subject, content, strFileAddress, strFileName);
        return status;
    }
    
    public static String getEmails(ArrayList<String> listIdUsuarios){

        String emails="";
        
        if(listIdUsuarios==null || 0==listIdUsuarios.size()){
            return emails;
        }
        
        Connection conn = ConnectionManager.getConnection();
        String strIn=" (";        
        for (int i = 0; i < listIdUsuarios.size(); i++) {
            strIn = strIn + "," + listIdUsuarios.get(i);
        }
        strIn = strIn.replaceFirst(",", "")+")";
        
        try {

            PreparedStatement ps = conn.prepareStatement("select email from usuario where id_usuario in "+strIn);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String strEmail = rs.getString("email");
                if(strEmail!=null && !strEmail.isEmpty()){
                    emails=emails+", "+strEmail;    
                }                
            }            
            emails = emails.replaceFirst(", ", "");

        } catch (SQLException sqlex) {
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(conn);
        }
        return emails;
    }
    
}
