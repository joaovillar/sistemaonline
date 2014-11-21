package com.jornada.client.ambiente.general.agenda;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jornada.client.classes.widgets.label.MpLabelLeft;
import com.jornada.client.classes.widgets.textbox.MpTextArea;
import com.jornada.client.classes.widgets.textbox.MpTextBox;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.shared.classes.CursoAvaliacao;
import com.jornada.shared.classes.utility.MpUtilClient;

@SuppressWarnings("deprecation")
public class MpDialogBoxAppointment extends DialogBox implements ClickListener {

	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
//	private Appointment app;
	
	FlexTable flexTable;
	
	Label lblTitle;
	
	MpTextBox txtCurso;
	MpTextBox txtPeriodo;
	MpTextBox txtDisciplina;
//	MpTextBox txtMateria;
	MpTextBox txtAssunto;
	MpTextArea txtDescricao;
	MpTextBox txtPesoNota;
	MpTextBox txtData;
	MpTextBox txtHora;
	
	private static MpDialogBoxAppointment uniqueInstance;
	
	public static MpDialogBoxAppointment getInstance(CursoAvaliacao ca){
		
		if(uniqueInstance==null){
			uniqueInstance = new MpDialogBoxAppointment();
			uniqueInstance.showDialog();
			uniqueInstance.populateData(ca);
		}else{
//			uniqueInstance.showDialog(app);
			uniqueInstance.populateData(ca);
		}
		
		return uniqueInstance;
		
	}

	private MpDialogBoxAppointment() {
		
//		this.usuario = usuario;
//		this.setAutoHideEnabled(true);
		
	}

	private void showDialog() {

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

//		AdicionarUsuario adicionarUsuario = AdicionarUsuario.getInstanceAtualizar(telaInicialUsuario, usuario);
		dock.add(vPanel(), DockPanel.NORTH);

		super.setGlassEnabled(false);
//		super.setAnimationEnabled(true);
//		super.setWidth("100%");

		setText(txtConstants.agendaDetalhes());
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


	
	private VerticalPanel vPanel(){
		VerticalPanel vPanel = new VerticalPanel();
		
		
		lblTitle = new Label();
		
		
		MpLabelLeft lblCurso = new MpLabelLeft(txtConstants.curso());
		MpLabelLeft lblPeriodo = new MpLabelLeft(txtConstants.periodo());
		MpLabelLeft lblDisciplina = new MpLabelLeft(txtConstants.disciplina());
//		MpLabelLeft lblMateria = new MpLabelLeft(txtConstants.conteudoProgramatico());
		MpLabelLeft lblAssunto = new MpLabelLeft(txtConstants.avaliacaoAssunto());
		MpLabelLeft lblDescricao = new MpLabelLeft(txtConstants.avaliacaoDescricao());
		MpLabelLeft lblPesoNota = new MpLabelLeft("Peso Nota");
//		Label lblTipoAvaliacao = new MpLabelLeft(txtConstants.avaliacaoTipo());
		MpLabelLeft lblData = new MpLabelLeft(txtConstants.avaliacaoData());
		MpLabelLeft lblHora = new MpLabelLeft(txtConstants.avaliacaoHora());
		
		
		txtCurso = new MpTextBox();
		txtPeriodo = new MpTextBox();
		txtDisciplina = new MpTextBox();
//		txtMateria = new MpTextBox();
		txtAssunto = new MpTextBox();
		txtDescricao = new MpTextArea();
		txtPesoNota = new MpTextBox();
		txtData = new MpTextBox();
		txtHora = new MpTextBox();
		
		txtCurso.setReadOnly(true);
		txtPeriodo.setReadOnly(true);
		txtDisciplina.setReadOnly(true);
//		txtMateria.setReadOnly(true);
		txtAssunto.setReadOnly(true);
		txtDescricao.setReadOnly(true);
		txtPesoNota.setReadOnly(true);
		txtData.setReadOnly(true);
		txtHora.setReadOnly(true);

	
		txtCurso.setWidth("250px");
		txtPeriodo.setWidth("250px");
		txtDisciplina.setWidth("250px");
//		txtMateria.setWidth("250px");
		txtAssunto.setWidth("250px");
		txtDescricao.setSize("250px", "50px");
		txtPesoNota.setWidth("100px");
		txtData.setWidth("250px");
		txtHora.setWidth("250px");

		
		
		flexTable = new FlexTable();
		flexTable.setCellPadding(2);
		flexTable.setCellSpacing(2);
		
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_LEFT);
		flexTable.getCellFormatter().setStyleName(0, 0, "designAjuda");		
		
		
		int row=0;
		flexTable.setWidget(row++, 0, lblTitle);
		flexTable.setWidget(row, 0, lblCurso);flexTable.setWidget(row++, 1, txtCurso);
		flexTable.setWidget(row, 0, lblPeriodo);flexTable.setWidget(row++, 1, txtPeriodo);
		flexTable.setWidget(row, 0, lblDisciplina);flexTable.setWidget(row++, 1, txtDisciplina);
		flexTable.setWidget(row, 0, lblAssunto);flexTable.setWidget(row++, 1, txtAssunto);
//		flexTable.setWidget(row, 0, lblAssuntoMateria);flexTable.setWidget(row++, 1, txtMateria);
		flexTable.setWidget(row, 0, lblDescricao);flexTable.setWidget(row++, 1, txtDescricao);
		flexTable.setWidget(row, 0, lblPesoNota);flexTable.setWidget(row++, 1, txtPesoNota);
		flexTable.setWidget(row, 0, lblData);flexTable.setWidget(row++, 1, txtData);
		flexTable.setWidget(row, 0, lblHora);flexTable.setWidget(row++, 1, txtHora);
		
		vPanel.add(flexTable);
		
		return vPanel;
		
	}
	
	
	private void populateData(CursoAvaliacao ca){
		cleanData();
		
		lblTitle.setText(ca.getDescricaoTipoAvaliacao());
		txtCurso.setValue(ca.getNomeCurso());
		txtPeriodo.setValue(ca.getNomePeriodo());
		txtDisciplina.setValue(ca.getNomeDisciplina());
//		txtMateria.setValue(ca.getNomeConteudoProgramatico());
		txtAssunto.setValue(ca.getAssuntoAvaliacao());
		txtDescricao.setValue(ca.getDescricaoAvaliacao());
		txtPesoNota.setValue(ca.getPesoNota());
		txtData.setValue(MpUtilClient.convertDateToString(ca.getDataAvaliacao()));
		txtHora.setValue(ca.getHoraAvaliacao());
		center();
		show();
		
	}
	
	private void cleanData(){
		lblTitle.setText("");
		txtCurso.setValue("");
		txtPeriodo.setValue("");
		txtDisciplina.setValue("");
//		txtMateria.setValue("");
		txtAssunto.setValue("");
		txtDescricao.setValue("");
		txtPesoNota.setValue("");
		txtData.setValue("");
		txtHora.setValue("");
		
	}
	
	  
}
