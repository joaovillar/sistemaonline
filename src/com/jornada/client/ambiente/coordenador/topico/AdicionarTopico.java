package com.jornada.client.ambiente.coordenador.topico;

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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.ambiente.coordenador.conteudoprogramatico.TelaInicialConteudoProgramatico;
import com.jornada.client.ambiente.coordenador.curso.TelaInicialCurso;
import com.jornada.client.classes.listBoxes.MpSelectionConteudoProgramatico;
import com.jornada.client.classes.listBoxes.MpSelectionCurso;
import com.jornada.client.classes.listBoxes.MpSelectionDisciplina;
import com.jornada.client.classes.listBoxes.MpSelectionPeriodo;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelTextBoxError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceTopico;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Topico;

public class AdicionarTopico extends VerticalPanel {

	private AsyncCallback<Integer> callBackAddTopico;

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading hPanelLoading = new MpPanelLoading("images/radar.gif");

	private MpSelectionCurso listBoxCurso;
	private MpSelectionPeriodo listBoxPeriodo;
	private MpSelectionDisciplina listBoxDisciplina;	
	private MpSelectionConteudoProgramatico listBoxConteudoProgramatico;
	
	private TextBox txtNome;
	private TextBox txtNumeracao;	
	private TextArea txtDescricao;
	private TextArea txtObjetivo;
	
	private MpLabelTextBoxError lblErrorConteudo;
	private MpLabelTextBoxError lblErroNomeTopico;	

	TextConstants txtConstants;
	
	@SuppressWarnings("unused")
	private TelaInicialTopico telaInicialTopico;

	public AdicionarTopico(final TelaInicialTopico telaInicialTopico) {
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialTopico=telaInicialTopico;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		hPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		hPanelLoading.show();
		hPanelLoading.setVisible(false);

		FlexTable layout = new FlexTable();
		layout.setCellSpacing(3);
		layout.setCellPadding(3);
		layout.setBorderWidth(0);
		layout.setSize(Integer.toString(TelaInicialTopico.intWidthTable),Integer.toString(TelaInicialTopico.intHeightTable));
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		// Add a title to the form
		cellFormatter.setColSpan(0, 0, 0);
		cellFormatter.setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_CENTER);
		txtNome = new TextBox();
		txtNumeracao = new TextBox();
		txtDescricao = new TextArea();
		txtObjetivo = new TextArea();
		
		txtNome.setStyleName("design_text_boxes");
		txtNumeracao.setStyleName("design_text_boxes");
		txtDescricao.setStyleName("design_text_boxes");
		txtObjetivo.setStyleName("design_text_boxes");		

		Label lblCurso = new Label(txtConstants.curso());
		Label lblPeriodo = new Label(txtConstants.periodo());
		Label lblDisciplina = new Label(txtConstants.disciplina());
		Label lblConteudoProgramatico = new Label(txtConstants.conteudoProgramatico());		
		Label lblNome = new Label(txtConstants.topicoNome());		
		Label lblCargaHoraria = new Label(txtConstants.topicoNumeracao());				
		Label lblDescricao = new Label(txtConstants.topicoDescricao());		
		Label lblObjetivo = new Label(txtConstants.topicoObjetivo());		
		
		lblErrorConteudo = new MpLabelTextBoxError();
		lblErroNomeTopico = new MpLabelTextBoxError();	
		
		lblCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		lblPeriodo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		lblDisciplina.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblConteudoProgramatico.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblNome.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblCargaHoraria.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDescricao.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblObjetivo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		

		lblCurso.setStyleName("design_label");
		lblPeriodo.setStyleName("design_label");
		lblDisciplina.setStyleName("design_label");
		lblConteudoProgramatico.setStyleName("design_label");
		lblNome.setStyleName("design_label");
		lblCargaHoraria.setStyleName("design_label");		
		lblDescricao.setStyleName("design_label");
		lblObjetivo.setStyleName("design_label");
		lblCargaHoraria.setStyleName("design_label");
		txtNome.setWidth("350px");
		txtNumeracao.setWidth("30px");		
		txtDescricao.setSize("350px", "50px");
		txtObjetivo.setSize("350px", "50px");



		// Add some standard form options
		int row = 1;
		layout.setWidget(row, 0, lblCurso);

		listBoxCurso = new MpSelectionCurso();		
		listBoxPeriodo = new MpSelectionPeriodo();		
		listBoxDisciplina = new MpSelectionDisciplina();		
		listBoxConteudoProgramatico = new MpSelectionConteudoProgramatico();
		
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());
		listBoxPeriodo.addChangeHandler(new MpPeriodoSelectionChangeHandler());		
		listBoxDisciplina.addChangeHandler(new MpDisciplinaSelectionChangeHandler());

		
		layout.setWidget(row, 0, lblCurso);	layout.setWidget(row++, 1, listBoxCurso);
		layout.setWidget(row, 0, lblPeriodo);layout.setWidget(row++, 1, listBoxPeriodo);
		layout.setWidget(row, 0, lblDisciplina);layout.setWidget(row++, 1, listBoxDisciplina);
		layout.setWidget(row, 0, lblConteudoProgramatico);layout.setWidget(row, 1, listBoxConteudoProgramatico);layout.setWidget(row++, 2, lblErrorConteudo);
		layout.setWidget(row, 0, lblNome);layout.setWidget(row, 1, txtNome);layout.setWidget(row++, 2, lblErroNomeTopico);
		layout.setWidget(row, 0, lblCargaHoraria);layout.setWidget(row++, 1, txtNumeracao);		
		layout.setWidget(row, 0, lblDescricao);layout.setWidget(row++, 1, txtDescricao);
		layout.setWidget(row, 0, lblObjetivo);layout.setWidget(row++, 1, txtObjetivo);


		MpImageButton btnSave = new MpImageButton(txtConstants.geralSalvar(), "images/save.png");
		btnSave.addClickHandler(new ClickHandlerSave());
		MpImageButton btnClean = new MpImageButton(txtConstants.geralLimpar(), "images/erase.png");
		btnClean.addClickHandler(new ClickHandlerClean());		

		VerticalPanel vFormPanel = new VerticalPanel();

		Grid gridSave = new Grid(1, 3);
		gridSave.setCellSpacing(2);
		gridSave.setCellPadding(2);
		{
			int i = 0;
			gridSave.setWidget(0, i++, btnSave);
			gridSave.setWidget(0, i++, btnClean);
			gridSave.setWidget(0, i++, hPanelLoading);
		}
		
		MpSpaceVerticalPanel mpSpaceVerticalPanel = new MpSpaceVerticalPanel();
		mpSpaceVerticalPanel.setWidth(Integer.toString(TelaInicialCurso.intWidthTable-700)+"px");

		vFormPanel.add(layout);
		vFormPanel.add(gridSave);
		
		vFormPanel.add(mpSpaceVerticalPanel);

		
		
		/***********************Begin Callbacks**********************/

		callBackAddTopico = new AsyncCallback<Integer>() {

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.topicoErroSalvar());
				mpDialogBoxWarning.showDialog();
			}

			@Override
			public void onSuccess(Integer result) {
				// lblLoading.setVisible(false);
				hPanelLoading.setVisible(false);
				int idObject = result;
				if (idObject>0) {
					cleanFields();
					mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
					mpDialogBoxConfirm.setBodyText(txtConstants.topicoSalvo());
					mpDialogBoxConfirm.showDialog();
//					cadastroConteudoProgramatico.populateGrid();
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.topicoErroSalvar());
					mpDialogBoxWarning.showDialog();
				}
			}
		};

		

		/***********************End Callbacks**********************/


		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize(Integer.toString(TelaInicialConteudoProgramatico.intWidthTable+30)+"px",Integer.toString(TelaInicialConteudoProgramatico.intHeightTable-40)+"px");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(vFormPanel);
		
		super.add(scrollPanel);
//		super.add(vFormPanel);

	}


	
	/****************Begin Event Handlers*****************/
	
	private class ClickHandlerSave implements ClickHandler {

		public void onClick(ClickEvent event) {

//			if (txtNome == null || txtNome.getText().isEmpty()) {
//
//				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//				mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.topicoNome()));
//				mpDialogBoxWarning.showDialog();
//
//			} else {
			if(checkFieldsValidator()){	
				hPanelLoading.setVisible(true);

				int intIdConteudoProgramatico = Integer.parseInt(listBoxConteudoProgramatico.getValue(listBoxConteudoProgramatico.getSelectedIndex()));
				
				Topico topico = new Topico();
				topico.setIdConteudoProgramatico(intIdConteudoProgramatico);
				topico.setNome(txtNome.getText());
				topico.setNumeracao(txtNumeracao.getText());
				topico.setDescricao(txtDescricao.getText());
				topico.setObjetivo(txtObjetivo.getText());

				GWTServiceTopico.Util.getInstance().Adicionar(topico,callBackAddTopico);

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
		
		boolean isNomeOk=false;
		if(FieldVerifier.isValidName(txtNome.getText())){
			isNomeOk=true;	
			lblErroNomeTopico.hideErroMessage();
		}else{
			isNomeOk=false;
			lblErroNomeTopico.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.topicoNome()));
		}
		
		boolean isConteudoOk=false;
		
		if(FieldVerifier.isValidListBoxSelectedValue(listBoxConteudoProgramatico.getSelectedIndex())){
			isConteudoOk=true;
			lblErrorConteudo.hideErroMessage();
		}else{
			lblErrorConteudo.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.conteudoProgramatico()));
		}
		
		isFieldsOk = isNomeOk && isConteudoOk;

		
		return isFieldsOk;
	}
	
	private void cleanFields(){
		lblErrorConteudo.hideErroMessage();
		lblErroNomeTopico.hideErroMessage();
		txtNome.setValue("");
		txtNumeracao.setValue("");
		txtDescricao.setValue("");
		txtObjetivo.setValue("");
	}
	
	public void updateClientData(){
		listBoxCurso.populateComboBox();
	}
	
	
}
