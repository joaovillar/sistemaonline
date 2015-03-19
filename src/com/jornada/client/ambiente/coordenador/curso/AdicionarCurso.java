package com.jornada.client.ambiente.coordenador.curso;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.MpListBoxMediaNota;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.MpListBoxPorcentagemPresenca;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.curso.MpListBoxAno;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.curso.MpListBoxEnsino;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelRight;
import com.jornada.client.classes.widgets.label.MpLabelTextBoxError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.classes.widgets.textbox.MpTextArea;
import com.jornada.client.classes.widgets.textbox.MpTextBox;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceCurso;
import com.jornada.client.service.GWTServiceCursoAsync;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Ensino;


public class AdicionarCurso extends VerticalPanel {

	private AdicionarCursoDeUmTemplate adicionarCursoDeUmTemplate;


	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpLoading = new MpPanelLoading("images/radar.gif");


	private MpListBoxEnsino mpListBoxEnsino;
	private MpListBoxAno mpListBoxAno;
	private MpTextBox txtNomeCurso;
	private MpTextArea txtDescricaoCurso;
	private MpTextArea txtEmentaCurso;
	private MpSelection mpListBoxStatus;
	private MpListBoxMediaNota mpListBoxMediaNota;
	private MpListBoxPorcentagemPresenca mpListBoxPorcentagemPresenca;	
	private MpDateBoxWithImage mpDateBoxInicial;
	private MpDateBoxWithImage mpDateBoxFinal;
	
	MpLabelTextBoxError lblErroNomeCurso;
	
//	@SuppressWarnings("unused")
	private TelaInicialCurso telaInicialCurso;
	
	TextConstants txtConstants;
	
	
	private VerticalPanel vPanelAddCursoDeTemplate;
	private VerticalPanel vPanelAddNovoCurso;
	
	private FlexTable flexTableAddNewCourseLeft;
	private FlexTable flexTableAddNewCourseRight;

	
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



		flexTableAddNewCourseLeft = new FlexTable();
		flexTableAddNewCourseLeft.setCellSpacing(2);
		flexTableAddNewCourseLeft.setCellPadding(2);
		flexTableAddNewCourseLeft.setBorderWidth(0);
		
        flexTableAddNewCourseRight = new FlexTable();
        flexTableAddNewCourseRight.setCellSpacing(2);
        flexTableAddNewCourseRight.setCellPadding(2);
        flexTableAddNewCourseRight.setBorderWidth(0); 


		mpListBoxEnsino = new MpListBoxEnsino();
		mpListBoxAno = new MpListBoxAno();
		mpListBoxEnsino.addChangeHandler(new MpEnsinoChangeHandler());
		showCamposDeAcordoEnsino(mpListBoxEnsino.getSelectedValue());
        
		txtNomeCurso = new MpTextBox();
		txtDescricaoCurso = new MpTextArea();
		txtEmentaCurso = new MpTextArea();
		
		mpListBoxMediaNota = new MpListBoxMediaNota();
		mpListBoxPorcentagemPresenca = new MpListBoxPorcentagemPresenca();
		mpDateBoxInicial = new MpDateBoxWithImage();
		mpDateBoxInicial.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
		mpDateBoxFinal = new MpDateBoxWithImage();
		mpDateBoxFinal.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
		
		mpListBoxStatus = new MpSelection();
		mpListBoxStatus.addItem(txtConstants.cursoAtivo(), "true");
		mpListBoxStatus.addItem(txtConstants.cursoDesativado(), "false");


		MpLabelRight lblEnsino = new MpLabelRight("Ensino");
		MpLabelRight lblAno = new MpLabelRight("Ano");
		MpLabelRight lblStatusCurso = new MpLabelRight(txtConstants.cursoStatus());
		MpLabelRight lblNomeCurso = new MpLabelRight(txtConstants.cursoNome());		
		MpLabelRight lblDescricaoCurso = new MpLabelRight(txtConstants.cursoDescricao());
		MpLabelRight lblEmentaCurso = new MpLabelRight(txtConstants.cursoEmenta());
		MpLabelRight lblMediaNotaCurso = new MpLabelRight(txtConstants.cursoMediaNota());
		MpLabelRight lblPorcentagemPresencaCurso = new MpLabelRight(txtConstants.cursoPorcentagemPresenca());
		MpLabelRight lblDataInicial = new MpLabelRight(txtConstants.cursoDataInicial());
		MpLabelRight lblDataFinal = new MpLabelRight(txtConstants.cursoDataFinal());
		
		lblErroNomeCurso = new MpLabelTextBoxError();


		mpListBoxStatus.setWidth("80px");
		mpListBoxMediaNota.setWidth("80px");
		mpListBoxPorcentagemPresenca.setWidth("80px");
		mpDateBoxInicial.getDate().setWidth("170px");
		mpDateBoxFinal.getDate().setWidth("170px");
		

		// Add some standard form options
		int row = 1;
		flexTableAddNewCourseLeft.setWidget(row, 0, lblEnsino)     ;flexTableAddNewCourseLeft.setWidget(row++, 1, mpListBoxEnsino);
		flexTableAddNewCourseLeft.setWidget(row, 0, lblAno)     ;flexTableAddNewCourseLeft.setWidget(row++, 1, mpListBoxAno);
		flexTableAddNewCourseLeft.setWidget(row, 0, lblNomeCurso)     ;flexTableAddNewCourseLeft.setWidget(row, 1, txtNomeCurso); flexTableAddNewCourseLeft.setWidget(row++, 2, lblErroNomeCurso);
		flexTableAddNewCourseLeft.setWidget(row, 0, lblDescricaoCurso);flexTableAddNewCourseLeft.setWidget(row++, 1, txtDescricaoCurso);
		flexTableAddNewCourseLeft.setWidget(row, 0, lblEmentaCurso)   ;flexTableAddNewCourseLeft.setWidget(row++, 1, txtEmentaCurso);
		
		row = 1;
		flexTableAddNewCourseRight.setWidget(row, 0, lblStatusCurso);flexTableAddNewCourseRight.setWidget(row++, 1, mpListBoxStatus);
		flexTableAddNewCourseRight.setWidget(row, 0, lblMediaNotaCurso);flexTableAddNewCourseRight.setWidget(row++, 1, mpListBoxMediaNota);
		flexTableAddNewCourseRight.setWidget(row, 0, lblPorcentagemPresencaCurso)   ;flexTableAddNewCourseRight.setWidget(row++, 1, mpListBoxPorcentagemPresenca);
		
		flexTableAddNewCourseRight.setWidget(row, 0, lblDataInicial)   ;flexTableAddNewCourseRight.setWidget(row++, 1, mpDateBoxInicial);
		flexTableAddNewCourseRight.setWidget(row, 0, lblDataFinal)     ;flexTableAddNewCourseRight.setWidget(row++, 1, mpDateBoxFinal);

		
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
		
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setBorderWidth(0);
		hPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		

		HorizontalPanel hPanelLeft = new HorizontalPanel();
		hPanelLeft.setWidth("500px"); 
		hPanelLeft.add(flexTableAddNewCourseLeft);
		
        HorizontalPanel hPanelRight = new HorizontalPanel();
        hPanelRight.setWidth("500px");
        hPanelRight.add(flexTableAddNewCourseRight);
		
		hPanel.add(hPanelLeft);
		hPanel.add(hPanelRight);
		
//		vPanelAddNovoCurso.add(flexTableAddNewCourse);
		vPanelAddNovoCurso.add(hPanel);
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
				String strEnsino = mpListBoxEnsino.getSelectedValue();
				curso.setEnsino(strEnsino);
				if( (strEnsino.equals(Ensino.FUNDAMENTAL)) || (strEnsino.equals(Ensino.MEDIO)) ){
				    curso.setAno(mpListBoxAno.getSelectedValue());
				}else{
				    curso.setAno("");
				}
				

                if(isAdicionar==true){
                    GWTServiceCurso.Util.getInstance().AdicionarCursoString(curso, new CallbackAddCurso());
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
		mpListBoxEnsino.setSelectedIndex(0);
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
	    
	    cleanFields();
	    
	    txtNomeCurso.setText(curso.getNome());
	    txtDescricaoCurso.setText(curso.getDescricao());
	    txtEmentaCurso.setText(curso.getEmenta());
	    mpDateBoxInicial.getDate().setValue(curso.getDataInicial());
	    mpDateBoxFinal.getDate().setValue(curso.getDataFinal());
	    mpListBoxEnsino.setSelectItem(curso.getEnsino());
	    mpListBoxAno.showItems(curso.getEnsino());
	    mpListBoxAno.setSelectItem(curso.getAno());
	    mpListBoxMediaNota.setSelectItem(curso.getMediaNota());
	    mpListBoxPorcentagemPresenca.setSelectItem(curso.getPorcentagemPresenca());
	    
        for (int i = 0; i < mpListBoxStatus.getItemCount(); i++) {
            boolean booStatus = Boolean.parseBoolean(mpListBoxStatus.getValue(i));
            if (curso.isStatus() == booStatus ) {
                mpListBoxStatus.setSelectedIndex(i);
                break;
            }
        }
	    
//        for (int i = 0; i < mpListBoxMediaNota.getItemCount(); i++) {
//            if (curso.getMediaNota().equals(mpListBoxMediaNota.getValue(i))) {
//                mpListBoxMediaNota.setSelectedIndex(i);
//                break;
//            }
//        }
        
//        for (int i = 0; i < mpListBoxPorcentagemPresenca.getItemCount(); i++) {
//            if (curso.getPorcentagemPresenca().equals(mpListBoxPorcentagemPresenca.getValue(i))) {
//                mpListBoxPorcentagemPresenca.setSelectedIndex(i);
//                break;
//            }
//        }
        
        showCamposDeAcordoEnsino(curso.getEnsino());
	}
	
	
	
	private class CallbackAddCurso implements AsyncCallback<String>{
        public void onFailure(Throwable caught) {
            mpLoading.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText(txtConstants.cursoErroSalvar());
            mpDialogBoxWarning.showDialog();
        }

        @Override
        public void onSuccess(String result) {
            // lblLoading.setVisible(false);
            mpLoading.setVisible(false);
            
            if (result.equals("true")) {
                cleanFields();
                mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
                mpDialogBoxConfirm.setBodyText(txtConstants.cursoSalvoSucesso());
                mpDialogBoxConfirm.showDialog();
//              EditarCurso.dataGrid().redraw();
                telaInicialCurso.updatePopulateGrid();
                telaInicialCurso.updateAssociarCurso();
                getAdicionarCursoDeUmTemplate().updateListBoxCurso();
                
            }else if(result.contains(Curso.DB_UNIQUE_KEY)){
                String strCurso = result.substring(result.indexOf("=(")+2);
                strCurso = strCurso.substring(strCurso.indexOf(",")+1);
                strCurso = strCurso.substring(0,strCurso.indexOf(")"));
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.cursoErroSalvar() + " "+txtConstants.cursoDuplicado((strCurso)));  
                mpDialogBoxWarning.showDialog();  
            }else {
                mpLoading.setVisible(false);
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.cursoErroSalvar()+" "+txtConstants.geralRegarregarPagina());
                mpDialogBoxWarning.showDialog();
            }
        }
	}
	
	
 
	private class CallbackAtualizarCurso implements AsyncCallback<String>{
        public void onFailure(Throwable caught) {
            mpLoading.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText(txtConstants.cursoErroSalvar());
            mpDialogBoxWarning.showDialog();
        }

        @Override
        public void onSuccess(String result) {
            // lblLoading.setVisible(false);
            mpLoading.setVisible(false);
            
            if (result.equals("true")) {
                telaInicialCurso.updatePopulateGrid();
                telaInicialCurso.updateAssociarCurso();
                getAdicionarCursoDeUmTemplate().updateListBoxCurso();
            }else if(result.contains(Curso.DB_UNIQUE_KEY)){
                String strCurso = result.substring(result.indexOf("=(")+2);
                strCurso = strCurso.substring(strCurso.indexOf(",")+1);
                strCurso = strCurso.substring(0,strCurso.indexOf(")"));
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.cursoErroAtualizar() + " "+txtConstants.cursoDuplicado((strCurso)));  
                mpDialogBoxWarning.showDialog(); 
            } else {
                mpLoading.setVisible(false);
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.cursoErroSalvar()+" "+txtConstants.geralRegarregarPagina());
                mpDialogBoxWarning.showDialog();
            }
        }
    }
	
	private class MpEnsinoChangeHandler implements ChangeHandler {

        @Override
        public void onChange(ChangeEvent event) {
            String strEnsino = mpListBoxEnsino.getSelectedValue();
            showCamposDeAcordoEnsino(strEnsino);
            mpListBoxAno.showItems(strEnsino);
        }
	    
	}
	
	private void showCamposDeAcordoEnsino(String strEnsino) {
	    
	    flexTableAddNewCourseLeft.getRowFormatter().setVisible(2, false);
	    
	    if (strEnsino.equals(Ensino.FUNDAMENTAL)) {
	        flexTableAddNewCourseLeft.getRowFormatter().setVisible(2, true);
	    }else if (strEnsino.equals(Ensino.MEDIO)) {
	        flexTableAddNewCourseLeft.getRowFormatter().setVisible(2, true);
	    }
	}
	

}
