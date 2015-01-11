package com.jornada.client.ambiente.coordenador.relatorio;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;
//import com.google.gwt.dom.client.Element;
import com.jornada.shared.classes.TipoUsuario;

public class TelaInicialRelatorio extends Composite {
	
//	private TabLayoutPanel tabLayoutPanel;

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=500;
	
	private BoletimPeriodo boletimPeriodo;
	private BoletimDisciplina boletimDisciplina;
	private BoletimAnual boletimAnual;
	private BoletimNotas boletimNotas;

	
	TextConstants txtConstants;

	private MainView mainView;
	
	private static TelaInicialRelatorio uniqueInstance;
	public static TelaInicialRelatorio getInstance(MainView mainView){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialRelatorio(mainView);
		}else{
            if (mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.COORDENADOR || mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
                uniqueInstance.boletimPeriodo.updateClientData();
                uniqueInstance.boletimDisciplina.updateClientData();
                uniqueInstance.boletimAnual.updateClientData();
            }else  if(mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.PROFESSOR){
                uniqueInstance.boletimDisciplina.updateClientData();
            }
		}
		
		return uniqueInstance;
	}		

	private TelaInicialRelatorio(MainView mainView) {
		
		this.mainView = mainView;
		
		txtConstants = GWT.create(TextConstants.class);
		
        TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);
        tabLayoutPanel.getElement().getStyle().setMarginBottom(10.0, Unit.PX);
        tabLayoutPanel.setHeight(Integer.toString(TelaInicialRelatorio.intHeightTable) + "px");
        tabLayoutPanel.setAnimationDuration(500);
        tabLayoutPanel.setAnimationVertical(true);
        
        if(this.mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.COORDENADOR || mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.ADMINISTRADOR){
            boletimPeriodo = BoletimPeriodo.getInstance(this);  
            boletimDisciplina = BoletimDisciplina.getInstance(this);  
            boletimAnual = BoletimAnual.getInstance(this); 
            boletimNotas = BoletimNotas.getInstance(this);      
            
            tabLayoutPanel.add(boletimDisciplina, new MpHeaderWidget("Boletim Disciplina", ""));
            tabLayoutPanel.add(boletimPeriodo, new MpHeaderWidget(txtConstants.relatorioBoletimPorPeriodo(), ""));
            tabLayoutPanel.add(boletimAnual, new MpHeaderWidget("Boletim Anual", ""));
            tabLayoutPanel.add(boletimNotas, new MpHeaderWidget("Boletim Notas", ""));
        }else if(this.mainView.getUsuarioLogado().getIdTipoUsuario() == TipoUsuario.PROFESSOR){
            boletimDisciplina = BoletimDisciplina.getInstance(this);  
            tabLayoutPanel.add(boletimDisciplina, new MpHeaderWidget("Boletim Disciplina", ""));
        }
		


		initWidget(tabLayoutPanel);	
		
	}

    public MainView getMainView() {
        return mainView;
    }
	

	


}
