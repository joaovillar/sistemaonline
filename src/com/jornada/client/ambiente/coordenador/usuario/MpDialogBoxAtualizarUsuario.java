package com.jornada.client.ambiente.coordenador.usuario;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.shared.classes.Usuario;

@SuppressWarnings("deprecation")
public class MpDialogBoxAtualizarUsuario extends DecoratedPopupPanel implements ClickListener {

	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	private TelaInicialUsuario telaInicialUsuario;	
//	private Usuario usuario;
	
	private static MpDialogBoxAtualizarUsuario uniqueInstance;
	
	public static MpDialogBoxAtualizarUsuario getInstance(TelaInicialUsuario telaInicialUsuario, Usuario usuario){
		
		if(uniqueInstance==null){
			uniqueInstance = new MpDialogBoxAtualizarUsuario(telaInicialUsuario);
			uniqueInstance.showDialog(usuario);
		}else{
			uniqueInstance.showDialog(usuario);
		}
		
		return uniqueInstance;
		
	}

	private MpDialogBoxAtualizarUsuario(TelaInicialUsuario telaInicialUsuario) {
		this.telaInicialUsuario = telaInicialUsuario;
//		this.usuario = usuario;
//		this.setAutoHideEnabled(true);
		
	}

	private void showDialog(Usuario usuario) {

		// title = txtConstants.geralAviso();
//		setText(txtConstants.geralAviso());

		Button closeButton = new Button(txtConstants.geralFecharJanela(), this);
		// closeButton.ad
		closeButton.addKeyUpHandler(new EnterKeyUpHandler());
		closeButton.setFocus(true);
		closeButton.setStyleName("cw-Button");

		DockPanel dock = new DockPanel();
		dock.setSpacing(4);

		dock.add(closeButton, DockPanel.SOUTH);

		dock.setCellHorizontalAlignment(closeButton, DockPanel.ALIGN_CENTER);
		dock.setWidth("100%");
//		AdicionarUsuario adicionarUsuario = new AdicionarUsuario(telaInicialUsuario);
//		setWidget(adicionarUsuario);
		setWidget(dock);

		AdicionarUsuario adicionarUsuario = AdicionarUsuario.getInstanceAtualizar(telaInicialUsuario, usuario);
		dock.add(adicionarUsuario, DockPanel.NORTH);

		super.setGlassEnabled(true);
		super.setAnimationEnabled(true);
		center();
		show();
		closeButton.setFocus(true);
	}

	public void onClick(Widget sender) {
//		logoutAndRefresh();

		 hide();
		// Window.Location.reload();
	}

	private class EnterKeyUpHandler implements KeyUpHandler {
		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				 hide();
				// Window.Location.reload();
//				logoutAndRefresh();
			}
		}
	}
	
	  
}
