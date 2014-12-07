package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.TipoStatusUsuario;

public class TipoStatusUsuarioServer {
    
    private static final String DB_SELECT_TIPO_STATUS_USUARIO_ALL = "select * from tipo_status_usuario order by nome_tipo_status_usuario asc;";
    private static final String DB_SELECT_TIPO_STATUS_USUARIO_ID = "select * from tipo_status_usuario where id_tipo_status_usuario=?;"; 
    private static final String DB_SELECT_TIPO_STATUS_USUARIO_NOME = "select * from tipo_status_usuario where nome_tipo_status_usuario=?;"; 
    
    public static ArrayList<TipoStatusUsuario> getListTipoStatusAluno(){
        ArrayList<TipoStatusUsuario> list = new ArrayList<TipoStatusUsuario>();
        Connection connection = ConnectionManager.getConnection();
        try{
            PreparedStatement ps = connection.prepareStatement(DB_SELECT_TIPO_STATUS_USUARIO_ALL);            
            list = getParameters(ps.executeQuery());
        } catch (SQLException sqlex) {
            list=null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return list;   
    }
    
    public static TipoStatusUsuario getTipoStatusAluno(int idTipoStatusAluno){
        ArrayList<TipoStatusUsuario> list = new ArrayList<TipoStatusUsuario>();
        Connection connection = ConnectionManager.getConnection();
        try{
            int count=0;
            PreparedStatement ps = connection.prepareStatement(DB_SELECT_TIPO_STATUS_USUARIO_ID);     
            ps.setInt(++count, idTipoStatusAluno);
            list = getParameters(ps.executeQuery());

        } catch (SQLException sqlex) {
            list=null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        
        if(list.size()>0){
            return list.get(0);
        }
        else
        {
            return null;
        }
    }
    
    public static TipoStatusUsuario getListTipoStatusAluno(String nomeTipoStatusAluno){
        ArrayList<TipoStatusUsuario> list = new ArrayList<TipoStatusUsuario>();
        Connection connection = ConnectionManager.getConnection();
        try{
            int count=0;
            PreparedStatement ps = connection.prepareStatement(DB_SELECT_TIPO_STATUS_USUARIO_NOME);     
            ps.setString(++count, nomeTipoStatusAluno);
            list = getParameters(ps.executeQuery());

        } catch (SQLException sqlex) {
            list=null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        
        if(list.size()>0){
            return list.get(0);
        }
        else
        {
            return null;
        }
    }    
    
    
    public static ArrayList<TipoStatusUsuario> getParameters(ResultSet rs) {
        ArrayList<TipoStatusUsuario> list = new ArrayList<TipoStatusUsuario>();
        try {
            while (rs.next()) {
                TipoStatusUsuario currentObject = new TipoStatusUsuario();
                currentObject.setIdTipoStatusUsuario(rs.getInt("id_tipo_status_usuario"));
                currentObject.setNomeTipoStatusUsuario(rs.getString("nome_tipo_status_usuario"));
                currentObject.setDescricaoStatusUsuario(rs.getString("descricao_status_usuario"));
                list.add(currentObject);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
 
}
