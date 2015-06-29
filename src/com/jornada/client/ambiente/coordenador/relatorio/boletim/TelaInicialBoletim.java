package com.jornada.client.ambiente.coordenador.relatorio.boletim;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;
//import com.google.gwt.dom.client.Element;
import com.jornada.shared.classes.TipoUsuario;

public class TelaInicialBoletim extends Composite {
	
//	private TabLayoutPanel tabLayoutPanel;

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=500;
	
	private BoletimPeriodo boletimPeriodo;
	private BoletimDisciplina boletimDisciplina;
	private BoletimAnual boletimAnual;
	private BoletimNotas boletimNotas;
	private BoletimAluno boletimAluno;
	private HistoricoAluno historicoAluno;

	
	TextConstants txtConstants;

	private MainView mainView;
	
	private static TelaInicialBoletim uniqueInstance;
	public static TelaInicialBoletim getInstance(MainView mainView){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialBoletim(mainView);
		}else{
            if (mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.COORDENADOR || mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
                uniqueInstance.boletimPeriodo.updateClientData();
                uniqueInstance.boletimDisciplina.updateClientData();
                uniqueInstance.boletimAnual.updateClientData();
                uniqueInstance.boletimAluno.updateClientData();
            }else  if(mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.PROFESSOR){
                uniqueInstance.boletimDisciplina.updateClientData();
            }
		}
		
		return uniqueInstance;
	}		

	private TelaInicialBoletim(MainView mainView) {
		
		this.mainView = mainView;
		
		txtConstants = GWT.create(TextConstants.class);
		
        TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);
        tabLayoutPanel.getElement().getStyle().setMarginBottom(10.0, Unit.PX);
        tabLayoutPanel.setHeight(Integer.toString(TelaInicialBoletim.intHeightTable) + "px");
        tabLayoutPanel.setAnimationDuration(500);
        tabLayoutPanel.setAnimationVertical(true);
        
        if(this.mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.COORDENADOR || mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.ADMINISTRADOR){
            boletimPeriodo = BoletimPeriodo.getInstance(this);  
            boletimDisciplina = BoletimDisciplina.getInstance(this);  
            boletimAnual = BoletimAnual.getInstance(this); 
            boletimNotas = BoletimNotas.getInstance(this);   
            boletimAluno = BoletimAluno.getInstance(this);   
            historicoAluno = HistoricoAluno.getInstance(this);   
            
            tabLayoutPanel.add(boletimDisciplina, new MpHeaderWidget(txtConstants.relatorioBoletimDisciplina(), "images/disciplina.png"));
            tabLayoutPanel.add(boletimPeriodo, new MpHeaderWidget(txtConstants.relatorioBoletimPorPeriodo(), "images/my_projects_folder_16.png"));
            tabLayoutPanel.add(boletimAnual, new MpHeaderWidget(txtConstants.relatorioBoletimAnual(), "images/application_certificate.png"));
            tabLayoutPanel.add(boletimNotas, new MpHeaderWidget(txtConstants.relatorioBoletimNotas(), "images/chart-icon_16.png"));
            tabLayoutPanel.add(boletimAluno, new MpHeaderWidget(txtConstants.relatorioBoletimAluno(), "images/elementary_school_16.png"));
            tabLayoutPanel.add(historicoAluno, new MpHeaderWidget(txtConstants.relatorioHistoricoAluno(), "images/clock.png"));
        }else if(this.mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.PROFESSOR){
            boletimDisciplina = BoletimDisciplina.getInstance(this);  
            tabLayoutPanel.add(boletimDisciplina, new MpHeaderWidget(txtConstants.relatorioBoletimDisciplina(), "images/disciplina.png"));
        }
		


		initWidget(tabLayoutPanel);	
		
	}

    public MainView getMainView() {
        return mainView;
    }
	

	


}
