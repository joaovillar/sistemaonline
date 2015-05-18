package com.jornada.client.ambiente.coordenador.relatorio.usuario;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;
//import com.google.gwt.dom.client.Element;
import com.jornada.shared.classes.TipoUsuario;

public class TelaInicialRelatorioUsuario extends Composite {
	
//	private TabLayoutPanel tabLayoutPanel;

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=500;
	
	private RelatorioProfessorDisciplina relatorioProfessorDisciplina;


	
	TextConstants txtConstants;

	private MainView mainView;
	
	private static TelaInicialRelatorioUsuario uniqueInstance;
	public static TelaInicialRelatorioUsuario getInstance(MainView mainView){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialRelatorioUsuario(mainView);
		}else{
            if (mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.COORDENADOR || mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
                uniqueInstance.relatorioProfessorDisciplina.updateClientData();
            }else  if(mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.PROFESSOR){
                uniqueInstance.relatorioProfessorDisciplina.updateClientData();
            }
		}
		
		return uniqueInstance;
	}		

	private TelaInicialRelatorioUsuario(MainView mainView) {
		
		this.mainView = mainView;
		
		txtConstants = GWT.create(TextConstants.class);
		
		TabPanel tabLayoutPanel = new TabPanel();
		tabLayoutPanel.setWidth("99%");
//        tabLayoutPanel.getElement().getStyle().setMarginBottom(10.0, Unit.PX);
        tabLayoutPanel.setHeight(Integer.toString(TelaInicialRelatorioUsuario.intHeightTable) + "px");
//        tabLayoutPanel.setAnimationDuration(500);
//        tabLayoutPanel.setAnimationVertical(true);
		tabLayoutPanel.setAnimationEnabled(true);
        
        if(this.mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.COORDENADOR || mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.ADMINISTRADOR){
            relatorioProfessorDisciplina = RelatorioProfessorDisciplina.getInstance(this);  
 
            
            tabLayoutPanel.add(relatorioProfessorDisciplina, new MpHeaderWidget(txtConstants.relatorioUsuarioProfessorDisciplina(), "images/professor_16.png"));
//            tabLayoutPanel.add(boletimPeriodo, new MpHeaderWidget(txtConstants.relatorioBoletimPorPeriodo(), "images/my_projects_folder_16.png"));
//            tabLayoutPanel.add(boletimAnual, new MpHeaderWidget(txtConstants.relatorioBoletimAnual(), "images/application_certificate.png"));
//            tabLayoutPanel.add(boletimNotas, new MpHeaderWidget(txtConstants.relatorioBoletimNotas(), "images/chart-icon_16.png"));
//            tabLayoutPanel.add(boletimAluno, new MpHeaderWidget(txtConstants.relatorioBoletimAluno(), "images/elementary_school_16.png"));
//            tabLayoutPanel.add(historicoAluno, new MpHeaderWidget(txtConstants.relatorioHistoricoAluno(), "images/clock.png"));
        }else if(this.mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.PROFESSOR){
            relatorioProfessorDisciplina = RelatorioProfessorDisciplina.getInstance(this);  
            tabLayoutPanel.add(relatorioProfessorDisciplina, new MpHeaderWidget(txtConstants.relatorioUsuarioProfessorDisciplina(), "images/professor_16.png"));
        }
		
        tabLayoutPanel.selectTab(0);

		initWidget(tabLayoutPanel);	
		
	}

    public MainView getMainView() {
        return mainView;
    }
	

	


}
