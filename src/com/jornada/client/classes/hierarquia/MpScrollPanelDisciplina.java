package com.jornada.client.classes.hierarquia;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.shared.classes.Disciplina;

public class MpScrollPanelDisciplina extends ScrollPanel{
	
	protected static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	public MpScrollPanelDisciplina(Disciplina object){
		
		Label lblNome = new Label(txtConstants.disciplinaNome());	
		Label lblNomeDB = new Label(object.getNome());
		
		Label lblCargaHorario = new Label(txtConstants.disciplinaCarga());	
		Label lblCargaHorarioDB = new Label(Integer.toString(object.getCargaHoraria()));				
		
		Label lblDescricao = new Label(txtConstants.disciplinaDescricao());	
		Label lblDescricaoDB = new Label(object.getDescricao());	
		
		Label lblObjetivo = new Label(txtConstants.disciplinaObjetivo());	
		Label lblObjetivoDB = new Label(object.getObjetivo());			
		
		Label lblProfessor = new Label(txtConstants.professor());
		String strProfessorNome = object.getProfessor()==null? "" : object.getProfessor().getPrimeiroNome();
		String strProfessorSobreNome = object.getProfessor()==null? "" : object.getProfessor().getSobreNome();
		Label lblProfessorDB = new Label(strProfessorNome + " " + strProfessorSobreNome);				
		
		lblNome.setStyleName("label_comum_bold_12px");
		lblNomeDB.setStyleName("label_comum");
		lblCargaHorario.setStyleName("label_comum_bold_12px");
		lblCargaHorarioDB.setStyleName("label_comum");	
		lblDescricao.setStyleName("label_comum_bold_12px");
		lblDescricaoDB.setStyleName("label_comum");		
		lblObjetivo.setStyleName("label_comum_bold_12px");
		lblObjetivoDB.setStyleName("label_comum");	
		lblProfessor.setStyleName("label_comum_bold_12px");
		lblProfessorDB.setStyleName("label_comum");			
		
		
		Grid gridCargaHorario = new Grid(1,10);		
		gridCargaHorario.setCellPadding(0);
		gridCargaHorario.setCellSpacing(0);
		gridCargaHorario.setBorderWidth(0);		
		int column=0;
		gridCargaHorario.setWidget(0, column++, lblCargaHorario);
		gridCargaHorario.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridCargaHorario.setWidget(0, column++, lblCargaHorarioDB);
		
	
		FlexTable flexTableConteudo = new FlexTable();
		flexTableConteudo.setCellPadding(2);
		flexTableConteudo.setCellSpacing(2);	
//		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
//		flexTableConteudo.getColumnFormatter().setWidth(0, "150px");		
		
		int row=0;
//		flexTableConteudo.setWidget(row++, 0, vPanelTitle);
//		flexTable.setWidget(row, 0, lblNome);flexTable.setWidget(row++, 1, lblNomeDB);
//		flexTableConteudo.setWidget(row++, 0, lblCargaHorario);
//		flexTableConteudo.setWidget(row++, 0, lblCargaHorarioDB);	
		flexTableConteudo.setWidget(row++, 0, gridCargaHorario);	
		flexTableConteudo.setWidget(row++, 0, new MpSpaceVerticalPanel());
		flexTableConteudo.setWidget(row++, 0, lblDescricao);
		flexTableConteudo.setWidget(row++, 0, lblDescricaoDB);
		flexTableConteudo.setWidget(row++, 0, new MpSpaceVerticalPanel());
		flexTableConteudo.setWidget(row++, 0, lblObjetivo);
		flexTableConteudo.setWidget(row++, 0, lblObjetivoDB);
		flexTableConteudo.setWidget(row++, 0, new MpSpaceVerticalPanel());
		flexTableConteudo.setWidget(row++, 0, lblProfessor);
		flexTableConteudo.setWidget(row++, 0, lblProfessorDB);


		
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize(Integer.toString(MpHierarquiaCurso.intWidthTable-200)+"px",Integer.toString(MpHierarquiaCurso.intHeightTable-30)+"px");
		scrollPanel.setAlwaysShowScrollBars(false);				
		scrollPanel.add(flexTableConteudo);
		
		this.add(scrollPanel);
		
	}


}
