package com.jornada.client.ambiente.coordenador.usuario;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;

public class TelaInicialUsuario extends Composite{
	
	

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=500;
	
	private AdicionarUsuario adicionarUsuario;
	private EditarUsuario editarUsuario;
	private AssociarPaisAlunos associarPaisAlunos;
	private ImportarUsuario importarUsuario;
//	private ContratoUsuario contratoUsuario;

	
	private static TelaInicialUsuario uniqueInstance;
	public static TelaInicialUsuario getInstance(){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialUsuario();
		}
//		else{
//			uniqueInstance.adicionarUsuario.u
//		}
		
		return uniqueInstance;
	}		

	private TelaInicialUsuario() {
		
		TextConstants txtConstants = GWT.create(TextConstants.class);
		
		
		adicionarUsuario = AdicionarUsuario.getInstanceAdicionar(this);
		editarUsuario = new EditarUsuario(this);
		associarPaisAlunos = new AssociarPaisAlunos();
		importarUsuario = new ImportarUsuario(this);
//		contratoUsuario = ContratoUsuario.getInstance(this);
		
		// StackLayoutPanel stackPanel = new StackLayoutPanel(Unit.EM);
		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);		
//		stackPanel.setPixelSize(intWidthTable+50, intHeightTable+50);
		tabLayoutPanel.setHeight(Integer.toString(intHeightTable+50)+"px");
		tabLayoutPanel.setAnimationDuration(500);
		tabLayoutPanel.setAnimationVertical(true);
		
//		stackPanel.add(adicionarUsuario, createHeaderWidget(txtConstants.usuarioAdicionar(), "images/plus-circle.png"));
		tabLayoutPanel.add(adicionarUsuario, new MpHeaderWidget(txtConstants.usuarioAdicionar(), "images/plus-circle.png"));
		tabLayoutPanel.add(editarUsuario, new MpHeaderWidget(txtConstants.usuarioEditar(), "images/comment_edit.png"));
		tabLayoutPanel.add(associarPaisAlunos, new MpHeaderWidget(txtConstants.usuarioAssociarPaisAlunos(), "images/group-add-icon_16.png"));
//		tabLayoutPanel.add(contratoUsuario, new MpHeaderWidget(txtConstants.documentoContrato(), "images/check.png"));
		tabLayoutPanel.add(importarUsuario, new MpHeaderWidget(txtConstants.usuarioImportar(), "images/001_22.16.png"));
		
//		
//		VerticalPanel verticalPanelPage = new VerticalPanel();		
//		verticalPanelPage.add(tabLayoutPanel);
//		verticalPanelPage.add(new InlineHTML("&nbsp;"));

     	initWidget(tabLayoutPanel);
		
	}
	
//	protected void populateGrid(){
//		editarUsuario.populateGrid();
//	}

	

	
//	private Widget createHeaderWidget(String text, String image) {
//		// Add the image and text to a horizontal panel
//		HorizontalPanel hPanel = new HorizontalPanel();
//		hPanel.setHeight("100%");
//		hPanel.setSpacing(0);
//		
//		Image img = new Image(image);
//		
//		Label headerText = new Label(text);
//		headerText.setStyleName("gwt-TabLayoutPanel");
//		
//		hPanel.add(img);	
//		hPanel.add(new InlineHTML("&nbsp;"));		
//		hPanel.add(headerText);
//		
//		Element imageContainer = DOM.getParent(img.getElement());
//		imageContainer.setClassName("img-center-alignment");
//		
//		return new SimplePanel(hPanel);
//	}

	public AdicionarUsuario getAdicionarUsuario() {
		return adicionarUsuario;
	}

	public AssociarPaisAlunos getAssociarPaisAlunos() {
		return associarPaisAlunos;
	}

	public EditarUsuario getEditarUsuario() {
		return editarUsuario;
	}	

}
