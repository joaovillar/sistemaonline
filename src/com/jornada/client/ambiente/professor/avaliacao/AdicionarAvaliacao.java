package com.jornada.client.ambiente.professor.avaliacao;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.ambiente.coordenador.curso.TelaInicialCurso;
import com.jornada.client.classes.listBoxes.MpSelectionConteudoProgramatico;
import com.jornada.client.classes.listBoxes.MpSelectionTipoAvaliacao;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionCursoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionDisciplinaAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionPeriodoAmbienteProfessor;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelTextBoxError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpacePanel;
import com.jornada.client.classes.widgets.timepicker.MpTimePicker;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceAvaliacao;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Avaliacao;

public class AdicionarAvaliacao extends VerticalPanel {
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);

	private AsyncCallback<Boolean> callbackAddAvaliacao;

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading hPanelLoading = new MpPanelLoading("images/radar.gif");

	private MpSelectionCursoAmbienteProfessor listBoxCurso;
	private MpSelectionPeriodoAmbienteProfessor listBoxPeriodo;
	private MpSelectionDisciplinaAmbienteProfessor listBoxDisciplina;	
	private MpSelectionConteudoProgramatico listBoxConteudoProgramatico;	
	private MpSelectionTipoAvaliacao listBoxTipoAvaliacao;
	
	private TextBox txtAssunto;
	private TextArea txtDescricao;	
	private MpDateBoxWithImage dateBoxData;
	private MpTimePicker mpTimePicker;
	
	private MpLabelTextBoxError lblErrorAssunto;
	private MpLabelTextBoxError lblErrorConteudo;	
	
	private TelaInicialAvaliacao telaInicialAvaliacao;

	public AdicionarAvaliacao(final TelaInicialAvaliacao telaInicialAvaliacao) {
		
		this.telaInicialAvaliacao=telaInicialAvaliacao;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		hPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		hPanelLoading.show();
		hPanelLoading.setVisible(false);

		FlexTable flexTableLayout = new FlexTable();
		flexTableLayout.setCellSpacing(3);
		flexTableLayout.setCellPadding(3);
		flexTableLayout.setBorderWidth(0);
		flexTableLayout.setSize(Integer.toString(TelaInicialAvaliacao.intWidthTable),Integer.toString(TelaInicialAvaliacao.intHeightTable));
		FlexCellFormatter cellFormatter = flexTableLayout.getFlexCellFormatter();

		// Add a title to the form
		// layout.setHTML(0, 0, "");
		cellFormatter.setColSpan(0, 0, 0);
		cellFormatter.setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_CENTER);
		txtAssunto = new TextBox();
		txtDescricao = new TextArea();
		dateBoxData = new MpDateBoxWithImage();
		mpTimePicker = new MpTimePicker(7,22);
		

		Label lblCurso = new Label(txtConstants.curso());
		Label lblPeriodo = new Label(txtConstants.periodo());
		Label lblDisciplina = new Label(txtConstants.disciplina());		
		Label lblConteudoProgramatico = new Label(txtConstants.conteudoProgramatico());		
		
		Label lblAssunto = new Label(txtConstants.avaliacaoAssunto());		
		Label lblDescricao = new Label(txtConstants.avaliacaoDescricao());
		Label lblTipoAvaliacao = new Label(txtConstants.avaliacaoTipo());
		Label lblData = new Label(txtConstants.avaliacaoData());
		Label lblHora = new Label(txtConstants.avaliacaoHora());	
		
		lblErrorAssunto = new MpLabelTextBoxError();
		lblErrorConteudo = new MpLabelTextBoxError();
		
		
		lblCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblPeriodo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDisciplina.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblConteudoProgramatico.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);				
		lblAssunto.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		lblDescricao.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblTipoAvaliacao.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblData.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblHora.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		
		lblCurso.setStyleName("design_label");
		lblPeriodo.setStyleName("design_label");
		lblDisciplina.setStyleName("design_label");
		lblConteudoProgramatico.setStyleName("design_label");
		lblAssunto.setStyleName("design_label");
		lblDescricao.setStyleName("design_label");	
		lblTipoAvaliacao.setStyleName("design_label");	
		lblData.setStyleName("design_label");
		lblHora.setStyleName("design_label");		
				
		txtAssunto.setWidth("350px");
		txtDescricao.setSize("350px", "50px");
		dateBoxData.getDate().setWidth("170px");

		txtAssunto.setStyleName("design_text_boxes");
		txtDescricao.setStyleName("design_text_boxes");
//		dateBoxData.setStyleName("design_text_boxes");

		listBoxCurso = new MpSelectionCursoAmbienteProfessor(telaInicialAvaliacao.getMainView().getUsuarioLogado());		
		listBoxPeriodo = new MpSelectionPeriodoAmbienteProfessor(telaInicialAvaliacao.getMainView().getUsuarioLogado());		
		listBoxDisciplina = new MpSelectionDisciplinaAmbienteProfessor(telaInicialAvaliacao.getMainView().getUsuarioLogado());		
		listBoxConteudoProgramatico = new MpSelectionConteudoProgramatico();
		
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());
		listBoxPeriodo.addChangeHandler(new MpPeriodoSelectionChangeHandler());		
		listBoxDisciplina.addChangeHandler(new MpDisciplinaSelectionChangeHandler());
		
		listBoxTipoAvaliacao = new MpSelectionTipoAvaliacao();
		

		// Add some standard form options
		int row = 1;
		flexTableLayout.setWidget(row, 0, lblCurso);flexTableLayout.setWidget(row++, 1, listBoxCurso);
		flexTableLayout.setWidget(row, 0, lblPeriodo);flexTableLayout.setWidget(row++, 1, listBoxPeriodo);
		flexTableLayout.setWidget(row, 0, lblDisciplina);flexTableLayout.setWidget(row++, 1, listBoxDisciplina);	
		flexTableLayout.setWidget(row, 0, lblConteudoProgramatico);flexTableLayout.setWidget(row, 1, listBoxConteudoProgramatico);flexTableLayout.setWidget(row++, 2, lblErrorConteudo);
		flexTableLayout.setWidget(row, 0, new InlineHTML("&nbsp;"));flexTableLayout.setWidget(row++, 1, new InlineHTML("&nbsp;"));	
		flexTableLayout.setWidget(row, 0, lblAssunto);flexTableLayout.setWidget(row, 1, txtAssunto);flexTableLayout.setWidget(row++, 2, lblErrorAssunto);
		flexTableLayout.setWidget(row, 0, lblDescricao);flexTableLayout.setWidget(row++, 1, txtDescricao);
		flexTableLayout.setWidget(row, 0, lblTipoAvaliacao);flexTableLayout.setWidget(row++, 1, listBoxTipoAvaliacao);		
		flexTableLayout.setWidget(row, 0, lblData);flexTableLayout.setWidget(row++, 1, dateBoxData);
		flexTableLayout.setWidget(row, 0, lblHora);flexTableLayout.setWidget(row++, 1, mpTimePicker);
		


		MpImageButton btnSave = new MpImageButton(txtConstants.geralSalvar(), "images/save.png");
		btnSave.addClickHandler(new ClickHandlerSave());
		MpImageButton btnClean = new MpImageButton(txtConstants.geralLimpar(), "images/erase.png");
		btnClean.addClickHandler(new ClickHandlerClean());				

		VerticalPanel vFormPanel = new VerticalPanel();

		Grid flexTableSave = new Grid(1,3);
		flexTableSave.setCellSpacing(2);
		flexTableSave.setCellPadding(2);
		{
			int i = 0;
			flexTableSave.setWidget(0, i++, btnSave);
			flexTableSave.setWidget(0, i++, btnClean);
			flexTableSave.setWidget(0, i++, hPanelLoading);
		}

		MpSpacePanel mpSpacePanel = new MpSpacePanel();
		mpSpacePanel.setWidth(Integer.toString(TelaInicialCurso.intWidthTable-700)+"px");
		
		vFormPanel.add(flexTableLayout);
		vFormPanel.add(flexTableSave);
		vFormPanel.add(mpSpacePanel);

		
		
		/***********************Begin Callbacks**********************/

		callbackAddAvaliacao = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroSalvar());
				mpDialogBoxWarning.showDialog();
			}

			@Override
			public void onSuccess(Boolean result) {
				// lblLoading.setVisible(false);
				hPanelLoading.setVisible(false);
				boolean isSuccess = result;
				if (isSuccess) {
					mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
					mpDialogBoxConfirm.setBodyText(txtConstants.avaliacaoSalva());
					mpDialogBoxConfirm.showDialog();
					telaInicialAvaliacao.populateGrid();
					cleanFields();
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroSalvar());
					mpDialogBoxWarning.showDialog();
				}
			}
		};


		

		/***********************End Callbacks**********************/

		
		super.add(vFormPanel);

	}

	
	/****************Begin Event Handlers*****************/
	
	private class ClickHandlerSave implements ClickHandler {

		public void onClick(ClickEvent event) {

//			if (txtAssunto == null || txtAssunto.getText().isEmpty()) {
//
//				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//				mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.avaliacaoAssunto()));
//				mpDialogBoxWarning.showDialog();
//
//			} else {

			if(checkFieldsValidator()){
				hPanelLoading.setVisible(true);

				int intIdConteudoProgramatico = Integer.parseInt(listBoxConteudoProgramatico.getValue(listBoxConteudoProgramatico.getSelectedIndex()));
				int intIdTipoAvaliacao = Integer.parseInt(listBoxTipoAvaliacao.getValue(listBoxTipoAvaliacao.getSelectedIndex()));
				String strHora = mpTimePicker.getValue(mpTimePicker.getSelectedIndex());
				
				Avaliacao object = new Avaliacao();
				object.setIdConteudoProgramatico(intIdConteudoProgramatico);
				object.setAssunto(txtAssunto.getText());
				object.setDescricao(txtDescricao.getText());
				object.setIdTipoAvaliacao(intIdTipoAvaliacao);
//				object.setData(dateBoxData.getDate().getValue());				
//				object.setHora(MpUtilClient.convertStringToTime(strHora));				
//				object.setData(MpUtilClient.convertDateToString(dateBoxData.getDate().getValue()));				
				object.setData(dateBoxData.getDate().getValue());
				object.setHora(strHora);				

				
				GWTServiceAvaliacao.Util.getInstance().AdicionarAvaliacao(object,callbackAddAvaliacao);

			}

		}
	}	

	
	private class MpCursoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			
			int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
			listBoxPeriodo.populateComboBox(idCurso);
		}  
	}	
	
	private class MpPeriodoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int index = listBoxPeriodo.getSelectedIndex();
			if(index==-1){
				listBoxDisciplina.clear();
				listBoxConteudoProgramatico.clear();
			}
			else{
				int idPeriodo = Integer.parseInt(listBoxPeriodo.getValue(index));
				listBoxDisciplina.populateComboBox(idPeriodo);				
			}
		}  
	}
	
	private class MpDisciplinaSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int index = listBoxDisciplina.getSelectedIndex();
			if(index==-1){
				listBoxConteudoProgramatico.clear();
			}
			else{
				int idDisciplina= Integer.parseInt(listBoxDisciplina.getValue(index));
				listBoxConteudoProgramatico.populateComboBox(idDisciplina);				
			}
		}  
	}
	
	private class ClickHandlerClean implements ClickHandler {
		public void onClick(ClickEvent event) {
			cleanFields();			
		}
	}	
	
	/****************End Event Handlers*****************/
	
	
	
	public boolean checkFieldsValidator(){
		
		boolean isFieldsOk = false;				
		
		boolean isAssuntoOk=false;
		if(FieldVerifier.isValidName(txtAssunto.getText())){
			isAssuntoOk=true;	
			lblErrorAssunto.hideErroMessage();
		}else{
			isAssuntoOk=false;
			lblErrorAssunto.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.topicoNome()));
		}
		
		boolean isConteudoOk=false;
		
		if(FieldVerifier.isValidListBoxSelectedValue(listBoxConteudoProgramatico.getSelectedIndex())){
			isConteudoOk=true;
			lblErrorConteudo.hideErroMessage();
		}else{
			lblErrorConteudo.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.conteudoProgramatico()));
		}
		
		isFieldsOk = isAssuntoOk && isConteudoOk;

		
		return isFieldsOk;
	}	

	
	public void cleanFields(){
		lblErrorAssunto.hideErroMessage();
		lblErrorConteudo.hideErroMessage();
		txtAssunto.setText("");		
		txtDescricao.setText("");	
		dateBoxData.getDate().setValue(null);
		mpTimePicker.setSelectedIndex(0);
		listBoxTipoAvaliacao.setSelectedIndex(0);
	}
	
	public void updateClientData(){
		listBoxCurso.populateComboBox(this.telaInicialAvaliacao.getMainView().getUsuarioLogado());
	}	
	
}
