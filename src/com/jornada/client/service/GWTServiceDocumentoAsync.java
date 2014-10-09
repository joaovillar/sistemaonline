package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Documento;
import com.jornada.shared.classes.RelDocumentoUsuario;
import com.jornada.shared.classes.Usuario;

public interface GWTServiceDocumentoAsync {

    public void getDocumentos(AsyncCallback<ArrayList<Documento>> callback);

    public void associarDocumentoUsuarios(int idCurso, int idDocumento, int idTipoUsuario, ArrayList<String> listIdPais, String url, AsyncCallback<Boolean> callback);

    public void getRelDocumentoUsuariosPorTipo(int idCurso, int idDocumento, int idTipoUsuario, AsyncCallback<ArrayList<RelDocumentoUsuario>> callback);

    public void enviarEmailDocumento(int idCurso, int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios, String url, AsyncCallback<String> callback);

    public void criarDocumentoParaAlunoTelaUsuario(Usuario usuario,  String hostPageBaseURL, AsyncCallback<String> callback);

    public void associarDocumentoTodosUsuarios(int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios, AsyncCallback<Boolean> callback);

    public void getRelDocumentoUsuariosSemCurso(int idDocumento, int idTipoUsuario, AsyncCallback<ArrayList<RelDocumentoUsuario>> callback);

    public void getRelDocumentoUsuariosPorTipoSemCurso(int idDocumento, int idTipoUsuario, AsyncCallback<ArrayList<RelDocumentoUsuario>> callback);

    public void enviarEmailDocumentoSemCurso(int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios, String url, AsyncCallback<String> callback);
    

}
