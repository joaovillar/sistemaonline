package com.jornada.client.ambiente.coordenador.comunicado;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.shared.classes.Comunicado;

public class TelaInicialComunicado extends Composite{	
	
	private VerticalPanel verticalPanelPage;
	VerticalPanel vPanelBody;
	private TabLayoutPanel stackPanel;

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=500;
	
	private FormularioComunicado formularioComunicado;
	private TabelaComunicados tabelaComunicados;

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
		
		stackPanel = new TabLayoutPanel(2.5, Unit.EM);	


		stackPanel.setPixelSize(intWidthTable+50, intHeightTable+250);
		stackPanel.setAnimationDuration(500);
		stackPanel.setAnimationVertical(true);
		
		vPanelBody = new VerticalPanel();
		
		vPanelBody.add(tabelaComunicados);
		vPanelBody.add(formularioComunicado);
		
		openTabelaComunicado();
		
//		scrollPanelFormularioComunicado = new ScrollPanel();
//		scrollPanelTabelaComunicado = new ScrollPanel();
		
//		scrollPanelFormularioComunicado.add(formularioComunicado);
//		scrollPanelTabelaComunicado.add(tabelaComunicados);		
		
//		stackPanel.add(formularioComunicado, createHeaderWidget("Comunicado Escolar", "images/plus-circle.png"));
		stackPanel.add(vPanelBody, new MpHeaderWidget(txtConstants.comunicado(), "images/notes_16.png"));		
		
		verticalPanelPage = new VerticalPanel();		
		verticalPanelPage.add(stackPanel);
		verticalPanelPage.add(new InlineHTML("&nbsp;"));
				
     	initWidget(verticalPanelPage);
		
	}
	
	protected void populateGrid(){
		tabelaComunicados.populateGrid();
	}
	
	
	
//	private Widget createHeaderWidget(String text, String image) {
//		// Add the image and text to a horizontal panel
//		HorizontalPanel hPanel = new HorizontalPanel();
//		hPanel.setHeight("100%");
//		hPanel.setSpacing(0);
//		hPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
//		hPanel.add(new Image(image));
//		Label headerText = new Label(text);
//		headerText.setStyleName("gwt-TabLayoutPanel");
//		hPanel.add(headerText);
//		return new SimplePanel(hPanel);
//	}	
//	
	
	public void openFormularioComunicadoParaAdicionar(){
		stackPanel.setPixelSize(TelaInicialComunicado.intWidthTable+50, TelaInicialComunicado.intHeightTable+180);
		tabelaComunicados.setVisible(false);
		
//		formularioComunicado.clearFiels();
		formularioComunicado.openFormularioParaAdicionar();
//		formularioComunicado.setVisible(true);
	}
	
	public void openFormularioComunicadoParaAtualizar(Comunicado comunicado){
		stackPanel.setPixelSize(TelaInicialComunicado.intWidthTable+50, TelaInicialComunicado.intHeightTable+180);
		tabelaComunicados.setVisible(false);
//		formularioComunicado.setComunicado(comunicado);
		formularioComunicado.openFormularioParaAtualizar(comunicado);
//		formularioComunicado.setVisible(true);
	}
	
	public void openTabelaComunicado(){		
		stackPanel.setPixelSize(TelaInicialComunicado.intWidthTable+50, TelaInicialComunicado.intHeightTable+180);
		formularioComunicado.setVisible(false);
		tabelaComunicados.setVisible(true);
	}	
	

}
