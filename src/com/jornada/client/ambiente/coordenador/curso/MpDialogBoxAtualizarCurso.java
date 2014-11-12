package com.jornada.client.ambiente.coordenador.curso;

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
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Curso;

@SuppressWarnings("deprecation")
public class MpDialogBoxAtualizarCurso extends DecoratedPopupPanel implements ClickListener {

	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	private TelaInicialCurso telaInicialCurso;	
//	private Usuario usuario;
	
	private static MpDialogBoxAtualizarCurso uniqueInstance;
	
	public static MpDialogBoxAtualizarCurso getInstance(TelaInicialCurso telaInicialCurso, Curso curso){
		
		if(uniqueInstance==null){
			uniqueInstance = new MpDialogBoxAtualizarCurso(telaInicialCurso);
			uniqueInstance.showDialog(curso);
		}else{
			uniqueInstance.showDialog(curso);
		}
		
		return uniqueInstance;
		
	}

	private MpDialogBoxAtualizarCurso(TelaInicialCurso telaInicialCurso) {
		this.telaInicialCurso = telaInicialCurso;
//		this.usuario = usuario;
//		this.setAutoHideEnabled(true);
		
	}

	private void showDialog(Curso curso) {

		 
		Button closeButton = new Button(txtConstants.geralFecharJanela(), this);
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

		AdicionarCurso adicionarCurso = AdicionarCurso.getInstanceAtualizar(telaInicialCurso, curso);
		dock.add(adicionarCurso, DockPanel.NORTH);

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
