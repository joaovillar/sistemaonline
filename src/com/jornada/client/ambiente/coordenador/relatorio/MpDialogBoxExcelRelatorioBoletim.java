package com.jornada.client.ambiente.coordenador.relatorio;

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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jornada.client.classes.widgets.label.MpLabelError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceNota;

@SuppressWarnings("deprecation")
public class MpDialogBoxExcelRelatorioBoletim extends DecoratedPopupPanel implements ClickListener {

	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	
	private static MpDialogBoxExcelRelatorioBoletim uniqueInstance;
	
	Grid grid;
	Anchor anchor;
	
	
    public static MpDialogBoxExcelRelatorioBoletim getInstance(int idCurso) {

        if (uniqueInstance == null) {
            uniqueInstance = new MpDialogBoxExcelRelatorioBoletim();
            uniqueInstance.showDialog();
            uniqueInstance.generateExcelBoletimAnual(idCurso);
        } else {
            uniqueInstance.showDialog();
            uniqueInstance.generateExcelBoletimAnual(idCurso);
        }
        return uniqueInstance;
    }
    
    public static MpDialogBoxExcelRelatorioBoletim getInstanceNotas(int idCurso) {

        if (uniqueInstance == null) {
            uniqueInstance = new MpDialogBoxExcelRelatorioBoletim();
            uniqueInstance.showDialog();
            uniqueInstance.generateExcelBoletimNotas(idCurso);
        } else {
            uniqueInstance.showDialog();
            uniqueInstance.generateExcelBoletimNotas(idCurso);
        }
        return uniqueInstance;
    }
	
	public static MpDialogBoxExcelRelatorioBoletim getInstance(int idCurso, int idPeriodo){
		
		if(uniqueInstance==null){
			uniqueInstance = new MpDialogBoxExcelRelatorioBoletim();
			uniqueInstance.showDialog();
			uniqueInstance.generateExcelBoletimPeriodo(idCurso, idPeriodo);
		}else{
			uniqueInstance.showDialog();
			uniqueInstance.generateExcelBoletimPeriodo(idCurso, idPeriodo);
		}		
		return uniqueInstance;		
	}
	
	
	   public static MpDialogBoxExcelRelatorioBoletim getInstance(int idCurso, int idPeriodo, int idDisciplina){
	        if(uniqueInstance==null){
	            uniqueInstance = new MpDialogBoxExcelRelatorioBoletim();
	            uniqueInstance.showDialog();
	            uniqueInstance.generateExcelBoletimDisciplina(idCurso, idPeriodo,idDisciplina);
	        }else{
	            uniqueInstance.showDialog();
	            uniqueInstance.generateExcelBoletimDisciplina(idCurso, idPeriodo,idDisciplina);
	        }       
	        return uniqueInstance;      
	    }

	private MpDialogBoxExcelRelatorioBoletim() {

		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);
	}

	private void showDialog() {


		mpPanelLoading.setVisible(true);

		Button closeButton = new Button(txtConstants.geralFecharJanela(), this);
		closeButton.addKeyUpHandler(new EnterKeyUpHandler());
		closeButton.setFocus(true);
		closeButton.setStyleName("cw-Button");

		DockPanel dock = new DockPanel();
		dock.setSpacing(4);

		dock.add(closeButton, DockPanel.SOUTH);

		dock.setCellHorizontalAlignment(closeButton, DockPanel.ALIGN_CENTER);
		dock.setWidth("100%");
		
        VerticalPanel vBody = new VerticalPanel();
        vBody.setStyleName("dialogVPanelWhite");

        vBody.add(dock);
        setWidget(vBody);

//		setWidget(dock);
		
		grid = new Grid(4,1);		
		grid.setCellSpacing(3);
		grid.setCellPadding(3);
		grid.setBorderWidth(0);	
		
		Label lblBaixarArquivo = new Label(txtConstants.usuarioBaixarPlanilhaExcel());
		
		anchor = new Anchor(txtConstants.usuarioCliqueAquiParaBaixar());
		
		MpLabelError lblErro = new MpLabelError(); 
		
		grid.setWidget(0, 0, mpPanelLoading);
		grid.setWidget(1, 0, lblBaixarArquivo);
		grid.setWidget(2, 0, anchor);
		grid.setWidget(3, 0, lblErro);
		
		grid.getRowFormatter().setVisible(0, true);
		grid.getRowFormatter().setVisible(1, false);
		grid.getRowFormatter().setVisible(2, false);
		grid.getRowFormatter().setVisible(3, false);

		
		dock.add(grid, DockPanel.SOUTH);

		
		

		super.setGlassEnabled(true);
		super.setAnimationEnabled(true);
		center();
		show();
		closeButton.setFocus(true);
	}

	public void onClick(Widget sender) {
		 hide();
	}

	private class EnterKeyUpHandler implements KeyUpHandler {
		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				 hide();
			}
		}
	}
	
	
    private void generateExcelBoletimAnual(int idCurso) {
        GWTServiceNota.Util.getInstance().getExcelBoletimAnual(idCurso,  new CallBackBoletim());
    }
    
    private void generateExcelBoletimNotas(int idCurso) {
        GWTServiceNota.Util.getInstance().getExcelBoletimNotas(idCurso,  new CallBackBoletim());
    }
	
	private void generateExcelBoletimPeriodo(int idCurso , int idPeriodo){
	    GWTServiceNota.Util.getInstance().getExcelBoletimPeriodo(idCurso, idPeriodo, new CallBackBoletim());
	}
	
	
	   private void generateExcelBoletimDisciplina(int idCurso , int idPeriodo, int idDisciplina){
	        GWTServiceNota.Util.getInstance().getExcelBoletimDisciplina(idCurso, idPeriodo, idDisciplina, new CallBackBoletim());
	    }
	
	
	private class CallBackBoletim implements AsyncCallback<String> {

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
    }
	
	  
}
