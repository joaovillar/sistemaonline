package com.jornada.client.ambiente.coordenador.curso;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;
//import com.google.gwt.dom.client.Element;

public class TelaInicialCurso extends Composite {
	
//	private TabLayoutPanel tabLayoutPanel;

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=500;
	
	private AdicionarCurso adicionarCurso;
	private EditarCurso editarCurso;
	private AssociarCursoAluno associarCursoAluno;
	
	TextConstants txtConstants;

	@SuppressWarnings("unused")
	private MainView mainView;
	
	private static TelaInicialCurso uniqueInstance;
	public static TelaInicialCurso getInstance(MainView mainView){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialCurso(mainView);
		}
		else{
			uniqueInstance.associarCursoAluno.updateClientData();
		}
		
		return uniqueInstance;
	}		

	private TelaInicialCurso(MainView mainView) {
		
		this.mainView = mainView;
		
		txtConstants = GWT.create(TextConstants.class);
		
		adicionarCurso = new AdicionarCurso(this);		
		editarCurso = new EditarCurso(this);
		associarCursoAluno = new AssociarCursoAluno(this);

		
		//adicionarCurso.setSize(Integer.toString(TelaInicialCurso.intWidthTable-5)+"px",Integer.toString(TelaInicialCurso.intHeightTable-5)+"px");
//		adicionarCurso.setSize("100%","100%");
//		editarCurso.setSize(Integer.toString(TelaInicialCurso.intWidthTable),Integer.toString(TelaInicialCurso.intHeightTable));		
//		associarCursoAluno.setSize(Integer.toString(TelaInicialCurso.intWidthTable),Integer.toString(TelaInicialCurso.intHeightTable));
		
		
		// StackLayoutPanel stackPanel = new StackLayoutPanel(Unit.EM);
		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);	
		tabLayoutPanel.getElement().getStyle().setMarginBottom(10.0, Unit.PX);
//		tabLayoutPanel.setSize(Integer.toString(TelaInicialCurso.intWidthTable+40)+"px",Integer.toString(TelaInicialCurso.intHeightTable)+"px");
		tabLayoutPanel.setHeight(Integer.toString(TelaInicialCurso.intHeightTable)+"px");
		tabLayoutPanel.setAnimationDuration(500);
		tabLayoutPanel.setAnimationVertical(true);

		
	
		tabLayoutPanel.add(adicionarCurso, new MpHeaderWidget(txtConstants.cursoAdicionar(), "images/plus-circle.png"));
		tabLayoutPanel.add(editarCurso, new MpHeaderWidget(txtConstants.cursoEditar(), "images/comment_edit.png"));				
		tabLayoutPanel.add(associarCursoAluno, new MpHeaderWidget(txtConstants.cursoAdicionarAlunoAoCurso(), "images/user1_add2_16.png"));
//		tabLayoutPanel.add(associarCursoAluno, createHeaderWidget("Add Course using another one", "images/user1_add2_16.png"));
		
		
//		VerticalPanel verticalPanelPage = new VerticalPanel();		
//		verticalPanelPage.add(tabLayoutPanel);
//		verticalPanelPage.add(new InlineHTML("&nbsp;"));
				
//     	initWidget(verticalPanelPage);
		initWidget(tabLayoutPanel);	
		
	}
	
	protected void updatePopulateGrid(){
		editarCurso.populateGrid();
	}
	
	protected void updateAssociarCurso(){
//		associarCursoAluno.popularAlunosAssociados();
		associarCursoAluno.updateClientData();
	}
	
	protected void updateAdicionarCurso(){
		adicionarCurso.getAdicionarCursoDeUmTemplate().updateListBoxCurso();
	}
	
	
//	private Widget createHeaderWidget(String text, String image) {
//		// Add the image and text to a horizontal panel
//		HorizontalPanel hPanel = new HorizontalPanel();
//		//hPanel.setWidth("100%");
//		hPanel.setHeight("100%");
//		hPanel.setSpacing(0);
//		hPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
//		hPanel.add(new Image(image));
//		Label headerText = new Label(text);
//		headerText.setStyleName("gwt-TabLayoutPanel");
//		hPanel.add(headerText);
//		return new SimplePanel(hPanel);
//	}

//	@Override
//	public void onResize() {
//		
//	    for (int i = 0; i < tabLayoutPanel.getWidgetCount(); i++) {
//	        final Widget widget = tabLayoutPanel.getWidget(i);
//	        DOM.setStyleAttribute(widget.getElement(), "position", "relative");
//
//	        final Element parent = DOM.getParent(widget.getElement());
//	        DOM.setStyleAttribute( parent, "overflowX", "visible");
//	        DOM.setStyleAttribute( parent, "overflowY", "visible");
//	    }
//		
//	}	

}
