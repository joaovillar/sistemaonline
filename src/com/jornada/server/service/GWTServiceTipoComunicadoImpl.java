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
import com.jornada.client.service.GWTServiceTipoComunicado;
import com.jornada.server.classes.TipoComunicadoServer;
import com.jornada.shared.classes.TipoComunicado;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GWTServiceTipoComunicadoImpl extends RemoteServiceServlet implements GWTServiceTipoComunicado {

    private static final long serialVersionUID = 4518830319373758385L;

    public ArrayList<TipoComunicado> getTipoComunicados() {
        return TipoComunicadoServer.getTipoComunicados();
    }

    public ArrayList<TipoComunicado> getTipoComunicadoEmails() {
        return TipoComunicadoServer.getTipoComunicadoEmails();
    }

    /**
     * Quick fix to this is in the RPC implementation at server side override
     * method checkPermutationStrongName() with empty implementation.
     */
    @Override
    protected void checkPermutationStrongName() throws SecurityException {
        return;
    }
}
