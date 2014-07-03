package com.jornada.client.ambiente.coordenador.topico;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;

public class TelaInicialTopico extends Composite {

	public static final int intWidthTable = 1400;
	public static final int intHeightTable = 500;

	private AdicionarTopico adicionarTopico;
	private EditarTopico editarTopico;
	
	private static TelaInicialTopico uniqueInstance;
	public static TelaInicialTopico getInstance(){		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialTopico();
		}		
		else{
			uniqueInstance.adicionarTopico.updateClientData();
			uniqueInstance.editarTopico.updateClientData();
		}
		return uniqueInstance;
	}		

	private TelaInicialTopico() {
		
		TextConstants txtConstants = GWT.create(TextConstants.class);

		adicionarTopico = new AdicionarTopico(this);
		editarTopico = new EditarTopico(this);

		TabLayoutPanel stackPanel = new TabLayoutPanel(2.5, Unit.EM);
		stackPanel.setPixelSize(intWidthTable + 50, intHeightTable);
		stackPanel.setAnimationDuration(500);
		stackPanel.setAnimationVertical(true);

		stackPanel.add(adicionarTopico,new MpHeaderWidget(txtConstants.topicoAdicionar(),"images/plus-circle.png"));
		stackPanel.add(editarTopico,new MpHeaderWidget(txtConstants.topicoEditar(),"images/comment_edit.png"));

		VerticalPanel verticalPanelPage = new VerticalPanel();
		verticalPanelPage.add(stackPanel);
		verticalPanelPage.add(new InlineHTML("&nbsp;"));

		initWidget(verticalPanelPage);

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

	protected void populateGrid() {
		editarTopico.populateGridTopico();
	}

}
