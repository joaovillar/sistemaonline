package com.jornada.client.ambiente.coordenador.comunicado;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.shared.classes.Comunicado;

public class TelaInicialComunicado extends Composite{	
	
//	private VerticalPanel verticalPanelPage;
	VerticalPanel vPanelBody;
	private TabLayoutPanel tabLayoutPanel;

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=500;
	
	private FormularioComunicado formularioComunicado;
	private TabelaComunicados tabelaComunicados;
	private AdicionarEmail adicionarEmail;

	TextConstants txtConstants;
	
	private static TelaInicialComunicado uniqueInstance;
	public static TelaInicialComunicado getInstance(){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialComunicado();
		}
		
		return uniqueInstance;
	}		

	private TelaInicialComunicado() {
		
		txtConstants = GWT.create(TextConstants.class);
		
		
		formularioComunicado = FormularioComunicado.getInstance(this);
		tabelaComunicados = TabelaComunicados.getInstance(this);
		adicionarEmail = AdicionarEmail.getInstance(this);
		
		tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);	
		tabLayoutPanel.getElement().getStyle().setMarginBottom(10.0, Unit.PX);

//		tabLayoutPanel.setPixelSize(intWidthTable+50, intHeightTable+250);
		tabLayoutPanel.setHeight(Integer.toString(intHeightTable+150)+"px");
		tabLayoutPanel.setAnimationDuration(500);
		tabLayoutPanel.setAnimationVertical(true);
		
		vPanelBody = new VerticalPanel();
		vPanelBody.setWidth("100%");
		vPanelBody.add(tabelaComunicados);
		vPanelBody.add(formularioComunicado);
		
		openTabelaComunicado();
		
    	tabLayoutPanel.add(vPanelBody, new MpHeaderWidget(txtConstants.comunicado(), "images/notes_16.png"));		
    	tabLayoutPanel.add(adicionarEmail, new MpHeaderWidget("Email", "images/letter.png"));
		
     	initWidget(tabLayoutPanel);
		
	}
	
	protected void populateGrid(){
		tabelaComunicados.populateGrid();
	}
	
	
	
	
	public void openFormularioComunicadoParaAdicionar(){
		tabelaComunicados.setVisible(false);
		formularioComunicado.openFormularioParaAdicionar();

	}
	
	public void openFormularioComunicadoParaAtualizar(Comunicado comunicado){
		tabelaComunicados.setVisible(false);
		formularioComunicado.openFormularioParaAtualizar(comunicado);
	}
	
	public void openTabelaComunicado(){		
		formularioComunicado.setVisible(false);
		tabelaComunicados.setVisible(true);
	}	
	

}
