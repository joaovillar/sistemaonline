package com.jornada.client.ambiente.coordenador.comunicado;

import java.util.ArrayList;

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
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Widget;
import com.jornada.client.classes.widgets.label.MpLabelLeft;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceEmail;

@SuppressWarnings("deprecation")
public class MpDialogBoxAddress extends DecoratedPopupPanel implements ClickListener {

    static TextConstants txtConstants = GWT.create(TextConstants.class);

    // private TelaInicialUsuario telaInicialUsuario;
    RichTextArea richArea;

    MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");

    private static MpDialogBoxAddress uniqueInstance;

    Grid grid;
    Anchor anchor;
    ArrayList<String> listIdUsuarios;

    public static MpDialogBoxAddress getInstance(ArrayList<String> listIdUsuarios) {

        if (uniqueInstance == null) {
            uniqueInstance = new MpDialogBoxAddress(listIdUsuarios);
            uniqueInstance.showDialog();
            uniqueInstance.populateEmail();
        } else {
//            uniqueInstance.showDialog();
            uniqueInstance.show();
            uniqueInstance.listIdUsuarios = listIdUsuarios;
            uniqueInstance.populateEmail();
        }

        return uniqueInstance;

    }

    private MpDialogBoxAddress(ArrayList<String> listIdUsuarios) {

        this.listIdUsuarios = listIdUsuarios;
        mpPanelLoading.setTxtLoading("");
        mpPanelLoading.show();
        mpPanelLoading.setVisible(false);

    }

    private void showDialog() {

        Button closeButton = new Button(txtConstants.geralFecharJanela(), this);
        closeButton.addKeyUpHandler(new EnterKeyUpHandler());
        closeButton.setFocus(true);
        closeButton.setStyleName("cw-Button");

        DockPanel dock = new DockPanel();
        dock.setSpacing(4);

        dock.add(closeButton, DockPanel.SOUTH);

        dock.setCellHorizontalAlignment(closeButton, DockPanel.ALIGN_CENTER);
        dock.setWidth("100%");

        setWidget(dock);

        grid = new Grid(1, 3);
        grid.setCellSpacing(3);
        grid.setCellPadding(3);
        grid.setBorderWidth(0);

        MpLabelLeft lblEmail = new MpLabelLeft("Email");
        richArea = new RichTextArea();        
        richArea.setStyleName("design_text_rich_boxes");
        richArea.setWidth("600px");

        grid.setWidget(0, 0, lblEmail);
        grid.setWidget(0, 1, richArea);
        grid.setWidget(0, 2, mpPanelLoading);

        // grid.getRowFormatter().setVisible(0, true);
        // grid.getRowFormatter().setVisible(1, false);

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

    private void populateEmail() {
        mpPanelLoading.setVisible(true);
        GWTServiceEmail.Util.getInstance().getEmails(listIdUsuarios, new CallBackEmail());
    }

    private class CallBackEmail implements AsyncCallback<String> {

        @Override
        public void onFailure(Throwable caught) {
            mpPanelLoading.setVisible(false);

        }

        @Override
        public void onSuccess(String result) {
            mpPanelLoading.setVisible(false);   
            richArea.setHTML(result);
        }

    }

}
