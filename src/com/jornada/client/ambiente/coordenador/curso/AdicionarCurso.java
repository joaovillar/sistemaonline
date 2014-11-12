package com.jornada.client.ambiente.coordenador.curso;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.MpListBoxMediaNota;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.MpListBoxPorcentagemPresenca;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelRight;
import com.jornada.client.classes.widgets.label.MpLabelTextBoxError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceCurso;
import com.jornada.client.service.GWTServiceCursoAsync;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Curso;


public class AdicionarCurso extends VerticalPanel {

	private AdicionarCursoDeUmTemplate adicionarCursoDeUmTemplate;


	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpLoading = new MpPanelLoading("images/radar.gif");


	private TextBox txtNomeCurso;
	private TextArea txtDescricaoCurso;
	private TextArea txtEmentaCurso;
	private MpSelection mpListBoxStatus;
	private MpListBoxMediaNota mpListBoxMediaNota;
	private MpListBoxPorcentagemPresenca mpListBoxPorcentagemPresenca;	
	private MpDateBoxWithImage mpDateBoxInicial;
	private MpDateBoxWithImage mpDateBoxFinal;
	
	MpLabelTextBoxError lblErroNomeCurso;
	
//	@SuppressWarnings("unused")
	private TelaInicialCurso telaInicialCurso;
	
	TextConstants txtConstants;
	
	
	VerticalPanel vPanelAddCursoDeTemplate;
	VerticalPanel vPanelAddNovoCurso;
	
//	private Hidden hiddenIdCurso;

	
	private Curso cursoParaAtualizar;
	
	private static AdicionarCurso uniqueInstanceAdicionar;
	private static AdicionarCurso uniqueInstanceAtualizar;
	
	private boolean isAdicionar;
	
	public static AdicionarCurso getInstanceAdicionar(TelaInicialCurso telaInicialCurso){
	    if(uniqueInstanceAdicionar==null){
	        uniqueInstanceAdicionar = new AdicionarCurso(telaInicialCurso, true);
	        uniqueInstanceAtualizar = new AdicionarCurso(telaInicialCurso, false);
	    }
	    return uniqueInstanceAdicionar;
	}
	
    public static AdicionarCurso getInstanceAtualizar(TelaInicialCurso telaInicialCurso, Curso curso) {
        if (uniqueInstanceAtualizar == null) {
            uniqueInstanceAtualizar = new AdicionarCurso(telaInicialCurso, false);
        }else{
            uniqueInstanceAtualizar.cursoParaAtualizar = curso;
            uniqueInstanceAtualizar.popularCurso(curso);
        }
        return uniqueInstanceAtualizar;
    }

	@SuppressWarnings("deprecation")
	private AdicionarCurso(final TelaInicialCurso telaInicialCurso, boolean isAdicionar) {
	    this.isAdicionar = isAdicionar;
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialCurso = telaInicialCurso;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpLoading.setTxtLoading(txtConstants.geralCarregando());
		mpLoading.show();
		mpLoading.setVisible(false);
		

		
		Grid gridUseTemplate = new Grid(1,3);
		RadioButton radioButtonYes = new RadioButton("useTemplate",txtConstants.geralSim());
		radioButtonYes.addValueChangeHandler(new ValueChangeHandlerYes());
		RadioButton radioButtonNo = new RadioButton("useTemplate", txtConstants.geralNao());
		radioButtonNo.addValueChangeHandler(new ValueChangeHandlerNo());
		radioButtonNo.setValue(true);
		Label lblUseTemplate = new Label(txtConstants.cursoUsarCursoJaCriado());
		lblUseTemplate.setStyleName("design_label");
		
		gridUseTemplate.setWidget(0, 0, lblUseTemplate);gridUseTemplate.setWidget(0, 1, radioButtonYes);gridUseTemplate.setWidget(0, 2, radioButtonNo);

        if (isAdicionar == true) {
            adicionarCursoDeUmTemplate = AdicionarCursoDeUmTemplate.getInstance(telaInicialCurso);
        }
		
		vPanelAddCursoDeTemplate = new VerticalPanel();
		vPanelAddNovoCurso = new VerticalPanel();



		FlexTable flexTableAddNewCourse = new FlexTable();
		flexTableAddNewCourse.setCellSpacing(3);
		flexTableAddNewCourse.setCellPadding(3);
		flexTableAddNewCourse.setBorderWidth(0);
		FlexCellFormatter cellFormatter = flexTableAddNewCourse.getFlexCellFormatter();

		// Add a title to the form
		// layout.setHTML(0, 0, "");
		//cellFormatter.setColSpan(0, 0, 0);
		cellFormatter.setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_CENTER);
		txtNomeCurso = new TextBox();
		txtDescricaoCurso = new TextArea();
		txtEmentaCurso = new TextArea();
		
		mpListBoxMediaNota = new MpListBoxMediaNota();
		mpListBoxPorcentagemPresenca = new MpListBoxPorcentagemPresenca();
		mpDateBoxInicial = new MpDateBoxWithImage();
		mpDateBoxInicial.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
		mpDateBoxFinal = new MpDateBoxWithImage();
		mpDateBoxFinal.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
		
		mpListBoxStatus = new MpSelection();
		mpListBoxStatus.addItem(txtConstants.cursoAtivo(), "true");
		mpListBoxStatus.addItem(txtConstants.cursoDesativado(), "false");
		
		txtNomeCurso.setStyleName("design_text_boxes");
		txtDescricaoCurso.setStyleName("design_text_boxes");
		txtEmentaCurso.setStyleName("design_text_boxes");
//		txtMediaNota.setStyleName("design_text_boxes");
//		mpListBoxPorcentagemPresenca.setStyleName("design_text_boxes");


		MpLabelRight lblStatusCurso = new MpLabelRight(txtConstants.cursoStatus());
		MpLabelRight lblNomeCurso = new MpLabelRight(txtConstants.cursoNome());		
		MpLabelRight lblDescricaoCurso = new MpLabelRight(txtConstants.cursoDescricao());
		MpLabelRight lblEmentaCurso = new MpLabelRight(txtConstants.cursoEmenta());
		MpLabelRight lblMediaNotaCurso = new MpLabelRight(txtConstants.cursoMediaNota());
		MpLabelRight lblPorcentagemPresencaCurso = new MpLabelRight(txtConstants.cursoPorcentagemPresenca());
		MpLabelRight lblDataInicial = new MpLabelRight(txtConstants.cursoDataInicial());
		MpLabelRight lblDataFinal = new MpLabelRight(txtConstants.cursoDataFinal());
		
		lblErroNomeCurso = new MpLabelTextBoxError();
		txtNomeCurso.setWidth("350px");
		txtDescricaoCurso.setSize("350px", "50px");
		txtEmentaCurso.setSize("350px", "50px");

		mpListBoxStatus.setWidth("80px");
		mpListBoxMediaNota.setWidth("80px");
		mpListBoxPorcentagemPresenca.setWidth("80px");
		mpDateBoxInicial.getDate().setWidth("170px");
		mpDateBoxFinal.getDate().setWidth("170px");
		

		// Add some standard form options
		int row = 1;
		flexTableAddNewCourse.setWidget(row, 0, lblNomeCurso)     ;flexTableAddNewCourse.setWidget(row, 1, txtNomeCurso); flexTableAddNewCourse.setWidget(row++, 2, lblErroNomeCurso);
		flexTableAddNewCourse.setWidget(row, 0, lblDescricaoCurso);flexTableAddNewCourse.setWidget(row++, 1, txtDescricaoCurso);
		flexTableAddNewCourse.setWidget(row, 0, lblEmentaCurso)   ;flexTableAddNewCourse.setWidget(row++, 1, txtEmentaCurso);
		flexTableAddNewCourse.setWidget(row, 0, lblStatusCurso);flexTableAddNewCourse.setWidget(row++, 1, mpListBoxStatus);
		flexTableAddNewCourse.setWidget(row, 0, lblMediaNotaCurso);flexTableAddNewCourse.setWidget(row++, 1, mpListBoxMediaNota);
		flexTableAddNewCourse.setWidget(row, 0, lblPorcentagemPresencaCurso)   ;flexTableAddNewCourse.setWidget(row++, 1, mpListBoxPorcentagemPresenca);
		flexTableAddNewCourse.setWidget(row, 0, lblDataInicial)   ;flexTableAddNewCourse.setWidget(row++, 1, mpDateBoxInicial);
		flexTableAddNewCourse.setWidget(row, 0, lblDataFinal)     ;flexTableAddNewCourse.setWidget(row++, 1, mpDateBoxFinal);

		
        String strTextoBotaoSubmeter = "";

        if (isAdicionar == true) {
            strTextoBotaoSubmeter = txtConstants.geralSalvar();

        } else {
            strTextoBotaoSubmeter = txtConstants.geralAtualizar();
        }
		MpImageButton btnSave = new MpImageButton(strTextoBotaoSubmeter, "images/save.png");
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
			gridSave.setWidget(0, i++, mpLoading);
		}

		MpSpaceVerticalPanel mpSpaceVerticalPanel = new MpSpaceVerticalPanel();
		mpSpaceVerticalPanel.setWidth(Integer.toString(TelaInicialCurso.intWidthTable-700)+"px");
		
        if (isAdicionar == true) {
            vPanelAddCursoDeTemplate.add(adicionarCursoDeUmTemplate);
        }
		vPanelAddCursoDeTemplate.setVisible(false);
		
		vPanelAddNovoCurso.add(flexTableAddNewCourse);
		vPanelAddNovoCurso.add(gridSave);
		
		
        if (this.isAdicionar == true) {
            vFormPanel.add(gridUseTemplate);
        }
		vFormPanel.add(vPanelAddNovoCurso);	
		vFormPanel.add(vPanelAddCursoDeTemplate);
//		vFormPanel.add(gridSave);		
		vFormPanel.add(mpSpaceVerticalPanel);

		
		
		/***********************Begin Callbacks**********************/

		// Callback para adicionar Curso.



		/***********************End Callbacks**********************/

		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(TelaInicialCurso.intWidthTable+30)+"px",Integer.toString(TelaInicialCurso.intHeightTable-40)+"px");
//		scrollPanel.setSize(Integer.toString(TelaInicialCurso.intWidthTable+30)+"px",Integer.toString(TelaInicialCurso.intHeightTable-40)+"px");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(vFormPanel);
		
//		super.setBorderWidth(1);
		
		super.add(scrollPanel);
		
//		super.add(vFormPanel);

	}

	public static GWTServiceCursoAsync getServiceCursoAsync() {
		return GWT.create(GWTServiceCurso.class);
	}


	
	
	/****************Begin Event Handlers*****************/
	
	private class ClickHandlerSave implements ClickHandler {

		public void onClick(ClickEvent event) {

//			if (txtNomeCurso.getTextBox() == null || txtNomeCurso.getTextBox().getText().isEmpty()) {
//			if(checkFieldsValidator()==false){
//
//				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//				mpDialogBoxWarning.setBodyText(txtConstants.cursoCampoNomeObrigatorio());
//				mpDialogBoxWarning.showDialog();
//
//			} else {
			if(checkFieldsValidator()){
				mpLoading.setVisible(true);
				
				String status = mpListBoxStatus.getValue(mpListBoxStatus.getSelectedIndex());

				Curso curso = new Curso();
				curso.setNome(txtNomeCurso.getText());
				curso.setDescricao(txtDescricaoCurso.getText());
				curso.setEmenta(txtEmentaCurso.getText());
				curso.setStatus(Boolean.parseBoolean(status)); 
				curso.setDataInicial(mpDateBoxInicial.getDate().getValue());
				curso.setDataFinal(mpDateBoxFinal.getDate().getValue());
				curso.setMediaNota(mpListBoxMediaNota.getValue(mpListBoxMediaNota.getSelectedIndex()));
				curso.setPorcentagemPresenca(mpListBoxPorcentagemPresenca.getValue(mpListBoxPorcentagemPresenca.getSelectedIndex()));


                if(isAdicionar==true){
                    GWTServiceCurso.Util.getInstance().AdicionarCurso(curso, new CallbackAddCurso());
                }else{
                    curso.setIdCurso(uniqueInstanceAtualizar.cursoParaAtualizar.getIdCurso());
                    GWTServiceCurso.Util.getInstance().updateCursoRow(curso, new CallbackAtualizarCurso());
                }
				
				

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
		
		if(FieldVerifier.isValidName(txtNomeCurso.getText())){
			isFieldsOk=true;	
			lblErroNomeCurso.hideErroMessage();
		}
		else{
			isFieldsOk=false;
			lblErroNomeCurso.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.cursoNome()));
		}
		
		return isFieldsOk;
	}
	
	
	private void cleanFields(){
		lblErroNomeCurso.hideErroMessage();
		txtNomeCurso.setValue("");
		txtDescricaoCurso.setValue("");
		txtEmentaCurso.setValue("");
		mpDateBoxInicial.getDate().setValue(null);
		mpDateBoxFinal.getDate().setValue(null);
	}
	
	
	private class ValueChangeHandlerYes implements ValueChangeHandler<Boolean>{
		@Override
		public void onValueChange(ValueChangeEvent<Boolean> event) {
            if(event.getValue() == true){
            	vPanelAddNovoCurso.setVisible(false);
            	vPanelAddCursoDeTemplate.setVisible(true);
            }			
		}
	}
	
	private class ValueChangeHandlerNo implements ValueChangeHandler<Boolean>{
		@Override
		public void onValueChange(ValueChangeEvent<Boolean> event) {
            if(event.getValue() == true){
            	vPanelAddCursoDeTemplate.setVisible(false);
            	vPanelAddNovoCurso.setVisible(true);
            	
            }			
		}
	}

	public final AdicionarCursoDeUmTemplate getAdicionarCursoDeUmTemplate() {
		return adicionarCursoDeUmTemplate;
	}

	
	private void popularCurso(Curso curso) {
	    
	    txtNomeCurso.setText(curso.getNome());
	    txtDescricaoCurso.setText(curso.getDescricao());
	    txtEmentaCurso.setText(curso.getEmenta());
	    mpDateBoxInicial.getDate().setValue(curso.getDataInicial());
	    mpDateBoxFinal.getDate().setValue(curso.getDataFinal());
	    
        for (int i = 0; i < mpListBoxStatus.getItemCount(); i++) {
            boolean booStatus = Boolean.parseBoolean(mpListBoxStatus.getValue(i));
            if (curso.isStatus() == booStatus ) {
                mpListBoxStatus.setSelectedIndex(i);
                break;
            }
        }
	    
        for (int i = 0; i < mpListBoxMediaNota.getItemCount(); i++) {
            if (curso.getMediaNota().equals(mpListBoxMediaNota.getValue(i))) {
                mpListBoxMediaNota.setSelectedIndex(i);
                break;
            }
        }
        
        for (int i = 0; i < mpListBoxPorcentagemPresenca.getItemCount(); i++) {
            if (curso.getPorcentagemPresenca().equals(mpListBoxPorcentagemPresenca.getValue(i))) {
                mpListBoxPorcentagemPresenca.setSelectedIndex(i);
                break;
            }
        }
        
        
	}
	
	
	
	private class CallbackAddCurso implements AsyncCallback<Integer>{
        public void onFailure(Throwable caught) {
            mpLoading.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText(txtConstants.cursoErroSalvar());
            mpDialogBoxWarning.showDialog();
        }

        @Override
        public void onSuccess(Integer result) {
            // lblLoading.setVisible(false);
            mpLoading.setVisible(false);
            int isSuccess = result;
            if (isSuccess!=0) {
                cleanFields();
                mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
                mpDialogBoxConfirm.setBodyText(txtConstants.cursoSalvoSucesso());
                mpDialogBoxConfirm.showDialog();
//              EditarCurso.dataGrid().redraw();
                telaInicialCurso.updatePopulateGrid();
                telaInicialCurso.updateAssociarCurso();
                getAdicionarCursoDeUmTemplate().updateListBoxCurso();
                
            } else {
                mpLoading.setVisible(false);
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.cursoErroSalvar()+" "+txtConstants.geralRegarregarPagina());
                mpDialogBoxWarning.showDialog();
            }
        }
	}
	
	
 
	private class CallbackAtualizarCurso implements AsyncCallback<Boolean>{
        public void onFailure(Throwable caught) {
            mpLoading.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText(txtConstants.cursoErroSalvar());
            mpDialogBoxWarning.showDialog();
        }

        @Override
        public void onSuccess(Boolean result) {
            // lblLoading.setVisible(false);
            mpLoading.setVisible(false);
            
            if (result==true) {
//                cleanFields();
//                mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
//                mpDialogBoxConfirm.setBodyText(txtConstants.cursoSalvoSucesso());
//                mpDialogBoxConfirm.showDialog();
//              EditarCurso.dataGrid().redraw();
                telaInicialCurso.updatePopulateGrid();
                telaInicialCurso.updateAssociarCurso();
                getAdicionarCursoDeUmTemplate().updateListBoxCurso();
                
            } else {
                mpLoading.setVisible(false);
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.cursoErroSalvar()+" "+txtConstants.geralRegarregarPagina());
                mpDialogBoxWarning.showDialog();
            }
        }
    }
	

}
