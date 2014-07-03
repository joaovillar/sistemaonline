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
package com.jornada.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jornada.shared.classes.Usuario;

@RemoteServiceRelativePath("GWTServiceLogin")
public interface GWTServiceLogin extends RemoteService {

	
	
//	public Usuario authenticateUser(String login, String password);
//	public String login(String login, String password);
	
	Usuario loginServer(String login, String password);    
	Usuario loginFromSessionServer();     
    boolean changePassword(String name, String newPassword); 
    void logout();	
	
	public static class Util {
		private static GWTServiceLoginAsync instance;
		public static GWTServiceLoginAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServiceLogin.class);
			}
			return instance;
		}
	}
}
