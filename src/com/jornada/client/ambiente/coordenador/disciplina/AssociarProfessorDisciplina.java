package com.jornada.client.ambiente.coordenador.disciplina;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.listBoxes.MpSelectionCurso;
import com.jornada.client.classes.listBoxes.MpSelectionPeriodo;
import com.jornada.client.classes.listBoxes.MpSelectionProfessor;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpPanelPageMainView;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceDisciplina;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.utility.MpUtilClient;

public class AssociarProfessorDisciplina extends VerticalPanel{
	
//	private AsyncCallback<ArrayList<Curso>> callBackPopulateCursoComboBox;
//	private AsyncCallback<ArrayList<Periodo>> callBackPopulatePeriodoComboBox;
	
	private AsyncCallback<ArrayList<Usuario>> callbackGetProfessoresFiltro;
	private AsyncCallback<ArrayList<Disciplina>> callbackGetDisciplinasFiltro;
	private AsyncCallback<ArrayList<Disciplina>> callbackGetDisciplinasAssociadas;	
	private AsyncCallback<Boolean> callbackAssociarDisciplinaAoProfessor;
	
	
	
	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpPanelProfessorLoading = new MpPanelLoading("images/radar.gif");
	MpPanelLoading mpPanelDisciplinaLoading = new MpPanelLoading("images/radar.gif");
	MpPanelLoading mpPanelAssociandoLoading = new MpPanelLoading("images/radar.gif");	
	
	private TextBox txtFiltroProfessor;	

	private MpSelectionCurso listBoxCurso;
	private MpSelectionPeriodo listBoxPeriodo;
	
	private MpSelectionProfessor listBoxProfessor;
	private ListBox multiBoxDisciplinaFiltrado;
	private ListBox multiBoxDisciplinaAssociada;
	
	TextConstants txtConstants;

	
	public AssociarProfessorDisciplina(){
		
		txtConstants = GWT.create(TextConstants.class);
		
		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		
		mpPanelProfessorLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelProfessorLoading.show();
		mpPanelProfessorLoading.setVisible(false);
		
		mpPanelDisciplinaLoading.setTxtLoading("");
		mpPanelDisciplinaLoading.show();
		mpPanelDisciplinaLoading.setVisible(false);

		mpPanelAssociandoLoading.setTxtLoading("");
		mpPanelAssociandoLoading.show();
		mpPanelAssociandoLoading.setVisible(false);		
		
		VerticalPanel vBodyPanel = new VerticalPanel();
		vBodyPanel.setWidth("100%");
		
		vBodyPanel.add(drawPassoUmSelecioneProfessor());
		vBodyPanel.add(new InlineHTML("&nbsp;"));
		vBodyPanel.add(drawPassoDoisSelecioneDisciplina());
		vBodyPanel.add(new InlineHTML("&nbsp;"));
//		vBodyPanel.add(drawPassoTresSubmeterAssociacao());		
		
		setWidth("100%");
		super.add(vBodyPanel);		
		
		
	}
	
	
	
	public MpPanelPageMainView drawPassoUmSelecioneProfessor(){

		
		MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.disciplinaSelecionarProfessor(), "images/Professor-icon_16.png");
//		mpPanel.setWidth(Integer.toString(TelaInicialDisciplina.intWidthTable)+"px");
		mpPanel.setWidth("100%");
		
		FlexTable flexTableFiltrar = new FlexTable();		
		flexTableFiltrar.setCellSpacing(3);
		flexTableFiltrar.setCellPadding(3);
		flexTableFiltrar.setBorderWidth(0);		
		
		Label lblFiltrarProfessor = new Label(txtConstants.professorNome());
		txtFiltroProfessor = new TextBox();		
		txtFiltroProfessor.addKeyUpHandler(new EnterKeyUpHandlerFiltrarProfessor());
		MpImageButton btnFiltrar = new MpImageButton(txtConstants.disciplinaFiltrarProfessor(), "images/magnifier.png");
		btnFiltrar.addClickHandler(new ClickHandlerFiltrarProfessor());		
		
		lblFiltrarProfessor.setStyleName("design_label");	
		lblFiltrarProfessor.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		txtFiltroProfessor.setStyleName("design_text_boxes");		
		
		listBoxProfessor = new MpSelectionProfessor();
		listBoxProfessor.setStyleName("design_text_boxes");
		listBoxProfessor.setWidth("350px");
		listBoxProfessor.addChangeHandler(new ChangeHandlerPopularDisciplinasAssociadas());			

		
		flexTableFiltrar.setWidget(0, 0, lblFiltrarProfessor);
		flexTableFiltrar.setWidget(0, 1, listBoxProfessor);
		flexTableFiltrar.setWidget(0, 2, txtFiltroProfessor);
		flexTableFiltrar.setWidget(0, 3, btnFiltrar);
		flexTableFiltrar.setWidget(0, 4, mpPanelProfessorLoading);	
		
		mpPanel.add(flexTableFiltrar);
		
		callbackGetProfessoresFiltro = new AsyncCallback<ArrayList<Usuario>>() {

			public void onSuccess(ArrayList<Usuario> list) {
				MpUtilClient.isRefreshRequired(list);
				mpPanelProfessorLoading.setVisible(false);
				mpPanelDisciplinaLoading.setVisible(false);	
				
				listBoxProfessor.clear();
				for(int i=0;i<list.size();i++){
					Usuario usuario = list.get(i);
					listBoxProfessor.addItem(usuario.getPrimeiroNome()+" "+usuario.getSobreNome(), Integer.toString(usuario.getIdUsuario()));
				}				
				
			}
			
			public void onFailure(Throwable caught) {
				mpPanelProfessorLoading.setVisible(false);
				mpPanelDisciplinaLoading.setVisible(false);	
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.disciplinaErroBuscarProfessores());
				mpDialogBoxWarning.showDialog();

			}
		};		
		
		popularOnLoadListBoxProfessores();		
		
		return mpPanel;
		
	}
	
	
	@SuppressWarnings("deprecation")
    public MpPanelPageMainView drawPassoDoisSelecioneDisciplina(){
		
		MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.disciplinaSelecionar(), "images/disciplina.png");
//		mpPanel.setWidth(Integer.toString(TelaInicialDisciplina.intWidthTable)+"px");
		mpPanel.setWidth("100%");
		
		FlexTable flexTableFiltrar = new FlexTable();		
		flexTableFiltrar.setCellSpacing(3);
		flexTableFiltrar.setCellPadding(3);
		flexTableFiltrar.setBorderWidth(0);
		
		Label lblCurso = new Label(txtConstants.curso());		
		Label lblPeriodo = new Label(txtConstants.periodo());		
		Label lblDisciplina = new Label(txtConstants.disciplina());
		
		
		lblCurso.setStyleName("design_label");
		lblPeriodo.setStyleName("design_label");
		lblDisciplina.setStyleName("design_label");
		
		listBoxCurso = new MpSelectionCurso(true);
		listBoxCurso.setWidth("250px");
		listBoxCurso.addChangeHandler(new ChangeHandlerListBoxCurso());

		listBoxPeriodo = new MpSelectionPeriodo();
		listBoxPeriodo.setWidth("250px");
		listBoxPeriodo.addChangeHandler(new ChangeHandlerListBoxPeriodo());
		

		
		flexTableFiltrar.setWidget(0, 0, lblCurso);
		flexTableFiltrar.setWidget(0, 1, listBoxCurso);
		
		flexTableFiltrar.setWidget(1, 0, lblPeriodo);
		flexTableFiltrar.setWidget(1, 1, listBoxPeriodo);
		flexTableFiltrar.setWidget(1, 2, mpPanelDisciplinaLoading);
		
		
		FlexTable flexTableBotoes = new FlexTable();
		flexTableBotoes.setCellSpacing(3);
		flexTableBotoes.setCellPadding(3);
		flexTableBotoes.setBorderWidth(0);
		
		MpImageButton mpButtonParaEsquerda = new MpImageButton("", "images/resultset_previous.png");
		MpImageButton mpButtonParaDireita = new MpImageButton("", "images/resultset_next.png");
		
		mpButtonParaDireita.addClickHandler(new ClickHandlerDisciplinaParaDireita());
		mpButtonParaEsquerda.addClickHandler(new ClickHandlerDisciplinaParaEsquerda());		
		
		flexTableBotoes.setWidget(0, 0, mpButtonParaDireita);
		flexTableBotoes.setWidget(1, 0, mpButtonParaEsquerda);		
		
		FlexTable flexTableSelecionar = new FlexTable();
		flexTableSelecionar.setCellSpacing(3);
		flexTableSelecionar.setCellPadding(3);
		flexTableSelecionar.setBorderWidth(0);		

		Label lblDisciplinaAssociada = new Label(txtConstants.disciplinaAssociadas());
		lblDisciplinaAssociada.setStyleName("design_label");
		
		multiBoxDisciplinaFiltrado = new ListBox(true);
	    multiBoxDisciplinaFiltrado.setWidth("450px");
	    multiBoxDisciplinaFiltrado.setHeight("130px");
	    multiBoxDisciplinaFiltrado.setVisibleItemCount(10);	
	    multiBoxDisciplinaFiltrado.setStyleName("design_text_boxes");
	    
		multiBoxDisciplinaAssociada = new ListBox(true);
	    multiBoxDisciplinaAssociada.setWidth("450px");
	    multiBoxDisciplinaAssociada.setHeight("130px");
	    multiBoxDisciplinaAssociada.setVisibleItemCount(10);	
	    multiBoxDisciplinaAssociada.setStyleName("design_text_boxes");
	    


	    flexTableSelecionar.setWidget(0, 0, flexTableFiltrar);
	    flexTableSelecionar.setWidget(0, 1, new InlineHTML("&nbsp;"));
	    flexTableSelecionar.setWidget(1, 0, lblDisciplina);
	    flexTableSelecionar.setWidget(1, 2, lblDisciplinaAssociada);
	    flexTableSelecionar.setWidget(2, 0, multiBoxDisciplinaFiltrado);
	    flexTableSelecionar.setWidget(2, 1, flexTableBotoes);
	    flexTableSelecionar.setWidget(2, 2, multiBoxDisciplinaAssociada);
	    flexTableSelecionar.setWidget(3,0,new InlineHTML("&nbsp;"));
	    flexTableSelecionar.setWidget(4,0,drawPassoTresSubmeterAssociacao());
	    flexTableSelecionar.getFlexCellFormatter().setColSpan(4, 0, 3);
	    
		
	    mpPanel.add(flexTableSelecionar);
	    
		callbackGetDisciplinasFiltro = new AsyncCallback<ArrayList<Disciplina>>() {

			public void onSuccess(ArrayList<Disciplina> list) {
				MpUtilClient.isRefreshRequired(list);
				mpPanelProfessorLoading.setVisible(false);
				mpPanelDisciplinaLoading.setVisible(false);				
				
				//Begin Cleaning fields
				multiBoxDisciplinaFiltrado.clear();
				//End Cleaning fields
				
				for(int i=0;i<list.size();i++){
					Disciplina disciplina = list.get(i);
					multiBoxDisciplinaFiltrado.addItem(disciplina.getNome(), Integer.toString(disciplina.getIdDisciplina()));
				}

			}
			
			public void onFailure(Throwable caught) {
				mpPanelProfessorLoading.setVisible(false);
				mpPanelDisciplinaLoading.setVisible(false);					
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.disciplinaErroCarregar());
				mpDialogBoxWarning.showDialog();

			}
		};
		
		
		callbackGetDisciplinasAssociadas = new AsyncCallback<ArrayList<Disciplina>>() {

			public void onSuccess(ArrayList<Disciplina> list) {
				MpUtilClient.isRefreshRequired(list);
				mpPanelProfessorLoading.setVisible(false);
				mpPanelDisciplinaLoading.setVisible(false);					
				
				//Begin Cleaning fields
				multiBoxDisciplinaAssociada.clear();

				//End Cleaning fields
				
				for(int i=0;i<list.size();i++){
					Disciplina disciplina = list.get(i);
					multiBoxDisciplinaAssociada.addItem(disciplina.getNome(), Integer.toString(disciplina.getIdDisciplina()));
				}

			}
			
			public void onFailure(Throwable caught) {
				mpPanelProfessorLoading.setVisible(false);
				mpPanelDisciplinaLoading.setVisible(false);					
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.disciplinaErroCarregar());
				mpDialogBoxWarning.showDialog();

			}
		};
		
		
		return mpPanel;
	}
	
	
	public VerticalPanel drawPassoTresSubmeterAssociacao(){
		
		FlexTable flexTable = new FlexTable();	
		flexTable.setCellSpacing(3);
		flexTable.setCellPadding(3);
		flexTable.setBorderWidth(0);		

		
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
//		vPanel.setWidth(Integer.toString(TelaInicialDisciplina.intWidthTable)+"px");
		vPanel.setWidth("100%");

		MpImageButton btnSubmeterAssociacao = new MpImageButton(txtConstants.disciplinaAssociarProfessores(), "images/image002.png");
		btnSubmeterAssociacao.addClickHandler(new ClickHandlerSubmeterAssociarProfessorAsDisciplinas());		


		flexTable.setWidget(0, 0, btnSubmeterAssociacao);
		flexTable.setWidget(0, 1, mpPanelAssociandoLoading);
		flexTable.setWidget(0, 2, new InlineHTML("&nbsp;"));

		vPanel.add(flexTable);
		vPanel.add(new InlineHTML("&nbsp;"));
		
		
		// Callback para adicionar Curso.
		callbackAssociarDisciplinaAoProfessor = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				mpPanelProfessorLoading.setVisible(false);
				mpPanelDisciplinaLoading.setVisible(false);					
				mpPanelAssociandoLoading.setVisible(false);				
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.disciplinaErroAssociarProfessor());
				mpDialogBoxWarning.showDialog();
			}

			@Override
			public void onSuccess(Boolean result) {
				
				mpPanelProfessorLoading.setVisible(false);
				mpPanelDisciplinaLoading.setVisible(false);					
				mpPanelAssociandoLoading.setVisible(false);
				
				if(result.booleanValue()){
				mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
				mpDialogBoxConfirm.setBodyText(txtConstants.disciplinaAssociadas());
				mpDialogBoxConfirm.showDialog();
				}
				else
				{
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.disciplinaErroAssociarProfessor());
					mpDialogBoxWarning.showDialog();					
				}
			}
		};		
	

		return vPanel;
	}
	
	private void popularOnLoadListBoxProfessores(){
		mpPanelProfessorLoading.setVisible(true);				
		GWTServiceUsuario.Util.getInstance().getUsuariosPorTipoUsuario(TipoUsuario.PROFESSOR, "%" + txtFiltroProfessor.getText() + "%", callbackGetProfessoresFiltro);		
	}
	
	private void popularDisciplinasAssociadas() {		
		
		int id_professor = Integer.parseInt(listBoxProfessor.getValue(listBoxProfessor.getSelectedIndex()));
		int intIndex = listBoxPeriodo.getSelectedIndex();
		if (intIndex == -1) {
			multiBoxDisciplinaAssociada.clear();
		}else{
			mpPanelDisciplinaLoading.setVisible(true);
			int id_periodo = Integer.parseInt(listBoxPeriodo.getValue(intIndex));
			GWTServiceDisciplina.Util.getInstance().getDisciplinasAssociadosAoProfessor(id_periodo,id_professor, callbackGetDisciplinasAssociadas);
		}
	}
	
	
	private class ClickHandlerFiltrarProfessor implements ClickHandler {
		public void onClick(ClickEvent event) {
				mpPanelProfessorLoading.setVisible(true);				
				GWTServiceUsuario.Util.getInstance().getUsuariosPorTipoUsuario(TipoUsuario.PROFESSOR, "%" + txtFiltroProfessor.getText() + "%", callbackGetProfessoresFiltro);
		}
	}
	
	private class EnterKeyUpHandlerFiltrarProfessor implements KeyUpHandler{
		public void onKeyUp(KeyUpEvent event){
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				mpPanelProfessorLoading.setVisible(true);				
				GWTServiceUsuario.Util.getInstance().getUsuariosPorTipoUsuario(TipoUsuario.PROFESSOR, "%" + txtFiltroProfessor.getText() + "%", callbackGetProfessoresFiltro);
			}
		}
	}	
	
	
	private class ClickHandlerDisciplinaParaDireita implements ClickHandler {
		public void onClick(ClickEvent event) {

			//for(int i=0;i<multiBoxDisciplinaFiltrado.getItemCount();i++){
			int i=0;
			while(i<multiBoxDisciplinaFiltrado.getItemCount()){
				if(multiBoxDisciplinaFiltrado.isItemSelected(i)){
					String value = multiBoxDisciplinaFiltrado.getValue(multiBoxDisciplinaFiltrado.getSelectedIndex());
					String item = multiBoxDisciplinaFiltrado.getItemText(multiBoxDisciplinaFiltrado.getSelectedIndex());
					if(!containsItem(multiBoxDisciplinaAssociada, item)){
						multiBoxDisciplinaAssociada.addItem(item, value);
					}
					multiBoxDisciplinaFiltrado.removeItem(multiBoxDisciplinaFiltrado.getSelectedIndex());
					i=0;
					continue;
				}
				i++;
			}
		}
	}
	
	
	private class ClickHandlerDisciplinaParaEsquerda implements ClickHandler {
		public void onClick(ClickEvent event) {			
			//for (int i = 0; i < multiBoxDisciplinaAssociada.getItemCount(); i++) {
			int i=0;
			while(i<multiBoxDisciplinaAssociada.getItemCount()){
				
				if (multiBoxDisciplinaAssociada.isItemSelected(i)) {
					String value = multiBoxDisciplinaAssociada.getValue(multiBoxDisciplinaAssociada.getSelectedIndex());
					String item = multiBoxDisciplinaAssociada.getItemText(multiBoxDisciplinaAssociada.getSelectedIndex());
					if (!containsItem(multiBoxDisciplinaFiltrado, item)) {
						multiBoxDisciplinaFiltrado.addItem(item, value);
					}
					multiBoxDisciplinaAssociada.removeItem(multiBoxDisciplinaAssociada.getSelectedIndex());
					i=0;
					continue;
				}
				i++;
			}
		}
	}

	
	private class ChangeHandlerPopularDisciplinasAssociadas implements ChangeHandler{
		public void onChange(ChangeEvent event){
			popularDisciplinasAssociadas();		

		}	
	}
	
	
	
	private class ClickHandlerSubmeterAssociarProfessorAsDisciplinas implements ClickHandler{		
		public void onClick(ClickEvent event){			
			mpPanelAssociandoLoading.setVisible(true);
			
			ArrayList<String> list_id_disciplina = new ArrayList<String>();
			for(int i=0;i<multiBoxDisciplinaAssociada.getItemCount();i++){
				list_id_disciplina.add(multiBoxDisciplinaAssociada.getValue(i));
			}
			int id_professor = Integer.parseInt(listBoxProfessor.getValue(listBoxProfessor.getSelectedIndex()));
			GWTServiceDisciplina.Util.getInstance().updateDisciplinaComIdProfessor(id_professor, list_id_disciplina, callbackAssociarDisciplinaAoProfessor);
		}
	}
	
	
	private class ChangeHandlerListBoxCurso implements ChangeHandler{				
		public void onChange(ChangeEvent event){
			int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
			listBoxPeriodo.populateComboBox(idCurso);
		}	
	}	
	
	private class ChangeHandlerListBoxPeriodo implements ChangeHandler{		
		public void onChange(ChangeEvent event){
			populateDisciplinaMultiBox();
			popularDisciplinasAssociadas();
		}		
		
	}		
	
	private void populateDisciplinaMultiBox() {
		int idPeriodo = listBoxPeriodo.getSelectedIndex();
		if(idPeriodo==-1){
			multiBoxDisciplinaFiltrado.clear();
		}
		else{
			idPeriodo = Integer.parseInt(listBoxPeriodo.getValue(idPeriodo));
			mpPanelDisciplinaLoading.setVisible(true);			
			GWTServiceDisciplina.Util.getInstance().getDisciplinasPeloPeriodo(idPeriodo, callbackGetDisciplinasFiltro);
			
		}
	}	
	
	
	private boolean containsItem(ListBox listBox, String item){
		boolean contain = false;
		
		for(int i=0;i<listBox.getItemCount();i++){
			String strItem = listBox.getItemText(i);
			if(strItem.equals(item)){
				contain=true;
				break;
			}
		}			
		return contain;
	}	
	
	
	public void updateClientData(){
		listBoxCurso.populateComboBox();
		listBoxProfessor.populateComboBox();
	}
	
	
	

}
