/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jornada.server.service;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServiceDocumento;
import com.jornada.server.classes.DocumentoServer;
import com.jornada.shared.classes.Documento;
import com.jornada.shared.classes.RelDocumentoUsuario;
import com.jornada.shared.classes.Usuario;

public class GWTServiceDocumentoImpl extends RemoteServiceServlet implements GWTServiceDocumento {

    private static final long serialVersionUID = -3897008633233079231L;
    
    public ArrayList<Documento> getDocumentos(){        
        return DocumentoServer.getDocumentos();     
    }
    
    public Boolean associarDocumentoUsuarios(int idCurso, int idDocumento, int idTipoUsuario, ArrayList<String> listIdPais, String url){
//        boolean status = DocumentoServer.associarDocumentoPais(idCurso, idDocumento, listIdPais);
//        if(status==true){
//            DocumentoServer.enviarEmailDocumento(idCurso, idDocumento, listIdPais, url);
//        }
        return DocumentoServer.associarDocumentoUsuarios(idCurso, idDocumento, idTipoUsuario, listIdPais);
        
    }
    
    public String enviarEmailDocumento(int idCurso, int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios, String url){
        
        return DocumentoServer.enviarEmailDocumento(idCurso, idDocumento, idTipoUsuario, listIdUsuarios, url);
    }
    
    public ArrayList<RelDocumentoUsuario> getRelDocumentoUsuariosPorTipo(int idCurso, int idDocumento, int idTipoUsuario) {
        return DocumentoServer.getRelDocumentoUsuariosPorTipo(idCurso, idDocumento, idTipoUsuario);
    }
    
    public ArrayList<RelDocumentoUsuario> getRelDocumentoUsuariosSemCurso(int idDocumento, int idTipoUsuario) {
        return DocumentoServer.getRelDocumentoUsuariosSemCurso(idDocumento, idTipoUsuario);
    }
    
    public ArrayList<RelDocumentoUsuario> getRelDocumentoUsuariosPorTipoSemCurso(int idDocumento, int idTipoUsuario){
        return DocumentoServer.getRelDocumentoUsuariosPorTipoSemCurso(idDocumento, idTipoUsuario);
    }
    
    public String enviarEmailDocumentoSemCurso(int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios, String url){
        return DocumentoServer.enviarEmailDocumentoSemCurso(idDocumento, idTipoUsuario, listIdUsuarios, url);
    }
    
    public String criarDocumentoParaAlunoTelaUsuario(Usuario usuario, String hostPageBaseURL){
        
        Documento doc = DocumentoServer.getDocumento(usuario.getIdUnidadeEscola(), false);
        return DocumentoServer.criarDocumentoFilho(usuario, hostPageBaseURL, doc.getNomeFisicoDocumento());
    }
    
    public boolean associarDocumentoTodosUsuarios(int idDocumento, int idTipoUsuario, ArrayList<String> listIdUsuarios){
        return DocumentoServer.associarDocumentoTodosUsuarios(idDocumento, idTipoUsuario, listIdUsuarios);
    }
}
