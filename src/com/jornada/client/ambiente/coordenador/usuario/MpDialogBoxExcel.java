package com.jornada.client.ambiente.coordenador.usuario;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.jornada.client.classes.widgets.label.MpLabelError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceUsuario;

@SuppressWarnings("deprecation")
public class MpDialogBoxExcel extends DecoratedPopupPanel implements ClickListener {

	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
//	private TelaInicialUsuario telaInicialUsuario;	
//	private Usuario usuario;
	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	
	private static MpDialogBoxExcel uniqueInstance;
	
	Grid grid;
	Anchor anchor;
	
	public static MpDialogBoxExcel getInstance(){
		
		if(uniqueInstance==null){
			uniqueInstance = new MpDialogBoxExcel();
			uniqueInstance.showDialog();
		}else{
			uniqueInstance.showDialog();
		}
		
		return uniqueInstance;
		
	}

	private MpDialogBoxExcel() {

		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);
		
//		this.telaInicialUsuario = telaInicialUsuario;
//		this.usuario = usuario;
//		this.setAutoHideEnabled(true);
		
	}

	private void showDialog() {

		// title = txtConstants.geralAviso();
//		setText(txtConstants.geralAviso());
		mpPanelLoading.setVisible(true);

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
		
		

		setWidget(dock);
		
		
		grid = new Grid(4,1);		
		grid.setCellSpacing(3);
		grid.setCellPadding(3);
		grid.setBorderWidth(0);	
		
		Label lblBaixarArquivo = new Label(txtConstants.usuarioBaixarPlanilhaExcel());
		
		anchor = new Anchor(txtConstants.usuarioCliqueAquiParaBaixar());
//		anchor.setHref("excel/ImportarUsuarios.xlsx");
		
		MpLabelError lblErro = new MpLabelError(); 
		
		grid.setWidget(0, 0, mpPanelLoading);
		grid.setWidget(1, 0, lblBaixarArquivo);
		grid.setWidget(2, 0, anchor);
		grid.setWidget(3, 0, lblErro);
		
		grid.getRowFormatter().setVisible(0, true);
		grid.getRowFormatter().setVisible(1, false);
		grid.getRowFormatter().setVisible(2, false);
		grid.getRowFormatter().setVisible(3, false);
		
//		vPanel = new VerticalPanel();
//		vPanel
		
		dock.add(grid, DockPanel.SOUTH);

		GWTServiceUsuario.Util.getInstance().gerarExcelUsuario(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				mpPanelLoading.setVisible(false);
				grid.getRowFormatter().setVisible(0, false);
				grid.getRowFormatter().setVisible(1, false);
				grid.getRowFormatter().setVisible(2, false);
				grid.getRowFormatter().setVisible(3, true);
			}

			@Override
			public void onSuccess(String result) {
				
				result = result.replace("//", "/");
				anchor.setHref(GWT.getHostPageBaseURL()+result);
				mpPanelLoading.setVisible(false);
				grid.getRowFormatter().setVisible(0, false);
				grid.getRowFormatter().setVisible(1, true);
				grid.getRowFormatter().setVisible(2, true);
				grid.getRowFormatter().setVisible(3, false);
			}
		});
		

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
