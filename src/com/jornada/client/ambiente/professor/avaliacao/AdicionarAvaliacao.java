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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.ambiente.coordenador.curso.TelaInicialCurso;
import com.jornada.client.classes.listBoxes.MpSelectionTipoAvaliacao;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxCursoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxDisciplinaAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxPeriodoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpListBoxPesoNota;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelRight;
import com.jornada.client.classes.widgets.label.MpLabelTextBoxError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.classes.widgets.textbox.MpTextArea;
import com.jornada.client.classes.widgets.textbox.MpTextBox;
import com.jornada.client.classes.widgets.timepicker.MpTimePicker;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceAvaliacao;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Avaliacao;
import com.jornada.shared.classes.TipoAvaliacao;

public class AdicionarAvaliacao extends VerticalPanel {
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);

	private AsyncCallback<String> callbackAddAvaliacao;

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading hPanelLoading = new MpPanelLoading("images/radar.gif");

	private MpListBoxCursoAmbienteProfessor listBoxCurso;
	private MpListBoxPeriodoAmbienteProfessor listBoxPeriodo;
	private MpListBoxDisciplinaAmbienteProfessor listBoxDisciplina;	
//	private MpSelectionConteudoProgramatico listBoxConteudoProgramatico;	
	private MpSelectionTipoAvaliacao listBoxTipoAvaliacao;
	
	private MpListBoxPesoNota listBoxPesoNota;
	
	private MpTextBox txtAssunto;
	private MpTextArea txtDescricao;	
	private MpDateBoxWithImage dateBoxData;
	private MpTimePicker mpTimePicker;
	
	private MpLabelTextBoxError lblErrorAssunto;
	private MpLabelTextBoxError lblErrorDisciplina;	
	
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
//		cellFormatter.setColSpan(0, 0, 0);
		cellFormatter.setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_CENTER);
		txtAssunto = new MpTextBox();
		txtDescricao = new MpTextArea();
		dateBoxData = new MpDateBoxWithImage();
		mpTimePicker = new MpTimePicker(7,22);
		

		MpLabelRight lblCurso = new MpLabelRight(txtConstants.curso());
		MpLabelRight lblPeriodo = new MpLabelRight(txtConstants.periodo());
		MpLabelRight lblDisciplina = new MpLabelRight(txtConstants.disciplina());		
//		Label lblConteudoProgramatico = new Label(txtConstants.conteudoProgramatico());		
		
		MpLabelRight lblPesoNota = new MpLabelRight("Peso Nota");  
		MpLabelRight lblAssunto = new MpLabelRight(txtConstants.avaliacaoAssunto());		
		MpLabelRight lblDescricao = new MpLabelRight(txtConstants.avaliacaoDescricao());
		MpLabelRight lblTipoAvaliacao = new MpLabelRight(txtConstants.avaliacaoTipo());
		MpLabelRight lblData = new MpLabelRight(txtConstants.avaliacaoData());
		MpLabelRight lblHora = new MpLabelRight(txtConstants.avaliacaoHora());	
		
		lblErrorAssunto = new MpLabelTextBoxError();
		lblErrorDisciplina = new MpLabelTextBoxError();
		
		dateBoxData.getDate().setWidth("170px");

		listBoxCurso = new MpListBoxCursoAmbienteProfessor(telaInicialAvaliacao.getMainView().getUsuarioLogado());		
		listBoxPeriodo = new MpListBoxPeriodoAmbienteProfessor(telaInicialAvaliacao.getMainView().getUsuarioLogado());		
		listBoxDisciplina = new MpListBoxDisciplinaAmbienteProfessor(telaInicialAvaliacao.getMainView().getUsuarioLogado());
		
		listBoxPesoNota = new MpListBoxPesoNota();
		
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());
		listBoxPeriodo.addChangeHandler(new MpPeriodoSelectionChangeHandler());		
		
		listBoxTipoAvaliacao = new MpSelectionTipoAvaliacao();
		listBoxTipoAvaliacao.addChangeHandler(new MpTipoAvaliacaoChangeHandler());
		

		// Add some standard form options
		int row = 1;
		flexTableLayout.setWidget(row, 0, lblCurso);flexTableLayout.setWidget(row++, 1, listBoxCurso);
		flexTableLayout.setWidget(row, 0, lblPeriodo);flexTableLayout.setWidget(row++, 1, listBoxPeriodo);
		flexTableLayout.setWidget(row, 0, lblDisciplina);flexTableLayout.setWidget(row, 1, listBoxDisciplina);flexTableLayout.setWidget(row++, 2, lblErrorDisciplina);
//		flexTableLayout.setWidget(row, 0, lblConteudoProgramatico);flexTableLayout.setWidget(row, 1, listBoxConteudoProgramatico);flexTableLayout.setWidget(row++, 2, lblErrorConteudo);
		flexTableLayout.setWidget(row, 0, new InlineHTML("&nbsp;"));flexTableLayout.setWidget(row++, 1, new InlineHTML("&nbsp;"));	
		flexTableLayout.setWidget(row, 0, lblAssunto);flexTableLayout.setWidget(row, 1, txtAssunto);flexTableLayout.setWidget(row++, 2, lblErrorAssunto);
		flexTableLayout.setWidget(row, 0, lblDescricao);flexTableLayout.setWidget(row++, 1, txtDescricao);
		flexTableLayout.setWidget(row, 0, lblTipoAvaliacao);flexTableLayout.setWidget(row++, 1, listBoxTipoAvaliacao);		
		flexTableLayout.setWidget(row, 0, lblPesoNota);flexTableLayout.setWidget(row++, 1, listBoxPesoNota);        
		flexTableLayout.setWidget(row, 0, lblData);flexTableLayout.setWidget(row++, 1, dateBoxData);
		flexTableLayout.setWidget(row, 0, lblHora);flexTableLayout.setWidget(row++, 1, mpTimePicker);


		MpImageButton btnSave = new MpImageButton(txtConstants.geralSalvar(), "images/save.png");
		MpImageButton btnClean = new MpImageButton(txtConstants.geralLimpar(), "images/erase.png");
	    
		btnSave.addClickHandler(new ClickHandlerSave());
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

		MpSpaceVerticalPanel mpSpaceVerticalPanel = new MpSpaceVerticalPanel();
		mpSpaceVerticalPanel.setWidth(Integer.toString(TelaInicialCurso.intWidthTable-700)+"px");
		
		vFormPanel.add(flexTableLayout);
		vFormPanel.add(flexTableSave);
		vFormPanel.add(mpSpaceVerticalPanel);

		
		
		/***********************Begin Callbacks**********************/

		callbackAddAvaliacao = new AsyncCallback<String>() {

			public void onFailure(Throwable caught) {
				hPanelLoading.setVisible(false);
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroSalvar());
				mpDialogBoxWarning.showDialog();
			}

			@Override
			public void onSuccess(String result) {
				// lblLoading.setVisible(false);
				hPanelLoading.setVisible(false);
				
				if (result.equals("true")) {
					mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
					mpDialogBoxConfirm.setBodyText(txtConstants.avaliacaoSalva());
					mpDialogBoxConfirm.showDialog();
					telaInicialAvaliacao.populateGrid();
					cleanFields();
				} else if(result.equals("false")){
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroSalvar());
					mpDialogBoxWarning.showDialog();
				}else if(result.equals(TipoAvaliacao.EXISTE_RECUPERACAO)){
	                  mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
	                  mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroRecuperacao());
	                  mpDialogBoxWarning.showDialog();  
                } else if (result.equals(TipoAvaliacao.EXISTE_RECUPERACAO_FINAL)) {
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroRecuperacaoFinal());
                    mpDialogBoxWarning.showDialog();
                } else if (result.equals(TipoAvaliacao.EXISTE_ASSUNTO)) {
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroAssunto());
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

			if(checkFieldsValidator()){
				hPanelLoading.setVisible(true);

				int intIdCurso = Integer.parseInt(listBoxCurso.getSelectedValue());
				int intIdDisciplina = Integer.parseInt(listBoxDisciplina.getValue(listBoxDisciplina.getSelectedIndex()));
				int intIdTipoAvaliacao = Integer.parseInt(listBoxTipoAvaliacao.getValue(listBoxTipoAvaliacao.getSelectedIndex()));
				String strHora = mpTimePicker.getValue(mpTimePicker.getSelectedIndex());
				String pesoNota = listBoxPesoNota.getValue(listBoxPesoNota.getSelectedIndex());
				
				Avaliacao object = new Avaliacao();
				object.setIdDisciplina(intIdDisciplina);
				object.setAssunto(txtAssunto.getText());
				object.setDescricao(txtDescricao.getText());
				object.setIdTipoAvaliacao(intIdTipoAvaliacao);
				object.setData(dateBoxData.getDate().getValue());
				object.setPesoNota(pesoNota);
				object.setHora(strHora);				

				
				GWTServiceAvaliacao.Util.getInstance().AdicionarAvaliacao(intIdCurso, object,callbackAddAvaliacao);

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
//				listBoxConteudoProgramatico.clear();
			}
			else{
				int idPeriodo = Integer.parseInt(listBoxPeriodo.getValue(index));
				listBoxDisciplina.populateComboBox(idPeriodo);				
			}
		}  
	}
	
    private class MpTipoAvaliacaoChangeHandler implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            listBoxPesoNota.setEnabled(true);
            
            int idTipoAvaliacao = Integer.parseInt(listBoxTipoAvaliacao.getSelectedValue());
            
            if (idTipoAvaliacao == TipoAvaliacao.INT_RECUPERACAO) {
                listBoxPesoNota.setEnabled(false);
            }
            if (idTipoAvaliacao == TipoAvaliacao.INT_RECUPERACAO_FINAL) {
                listBoxPesoNota.setEnabled(false);
            }
            if (idTipoAvaliacao == TipoAvaliacao.INT_ADICIONAL_NOTA) {
                listBoxPesoNota.setEnabled(false);
            }

        }
    }

//	private class MpDisciplinaSelectionChangeHandler implements ChangeHandler {
//		public void onChange(ChangeEvent event) {
//			int index = listBoxDisciplina.getSelectedIndex();
//			if(index==-1){
//				listBoxConteudoProgramatico.clear();
//			}
//			else{
//				int idDisciplina= Integer.parseInt(listBoxDisciplina.getValue(index));
//				listBoxConteudoProgramatico.populateComboBox(idDisciplina);				
//			}
//		}  
//	}
	
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
		
		if(FieldVerifier.isValidListBoxSelectedValue(listBoxDisciplina.getSelectedIndex())){
			isConteudoOk=true;
			lblErrorDisciplina.hideErroMessage();
		}else{
			lblErrorDisciplina.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.disciplina()));
		}
		
		isFieldsOk = isAssuntoOk && isConteudoOk;

		
		return isFieldsOk;
	}	

	
	public void cleanFields(){
		lblErrorAssunto.hideErroMessage();
		lblErrorDisciplina.hideErroMessage();
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
