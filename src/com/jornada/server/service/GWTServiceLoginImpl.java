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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServiceLogin;
import com.jornada.server.classes.UsuarioServer;
import com.jornada.server.classes.password.BCrypt;
import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.Usuario;

public class GWTServiceLoginImpl extends RemoteServiceServlet implements GWTServiceLogin {

	private static final long serialVersionUID = 7918945362089405911L;

	
	
	public Usuario loginServer(String login, String password)    {
		
		ConnectionManager.configureConnPool();
		
		//validate username and password
		Usuario user = UsuarioServer.getUsuarioPeloLogin(login);
		
		boolean valid = BCrypt.checkpw(password, user.getSenha());

		if(valid){
			user.setLoggedIn(true);
			user.setSessionId(this.getThreadLocalRequest().getSession().getId());

			// store the user/session id
			storeUserInSession(user);
			System.out.println("User: " + user.getPrimeiroNome() + " logged in successfully. Session id: " + user.getSessionId());
		}
		else {
			ConnectionManager.shutdownConnPool();
			user=null;
		}
        
        return user;
    }
	
	
    public Usuario loginFromSessionServer()    {
        return getUserAlreadyFromSession();
    }
	
    public void logout()    {
    	ConnectionManager.shutdownConnPool();
        deleteUserFromSession();
    }
	
	
	private Usuario getUserAlreadyFromSession()
    {
		Usuario user = null;
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        Object userObj = session.getAttribute("user");
        if (userObj != null && userObj instanceof Usuario)
        {
            //user = (Usuario) userObj;
        	user = UsuarioServer.getUsuarioPeloId(((Usuario) userObj).getIdUsuario());
        	user.setLoggedIn(true);
        }
        return user;
    }	
	
	private void storeUserInSession(Usuario user)
    {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("user", user);
    }	
	
	private void deleteUserFromSession()
    {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("user");
    }



	
	
}
