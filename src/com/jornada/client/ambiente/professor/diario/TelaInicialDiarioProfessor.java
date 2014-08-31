
package com.jornada.client.ambiente.professor.diario;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;


public class TelaInicialDiarioProfessor extends Composite{
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=550;
	

	private AdicionarDiarioProfessor adicionarDiarioProfessor;
	private EditarDiarioProfessor editarDiarioProfessor;
	private MainView mainView;

	
	private static TelaInicialDiarioProfessor uniqueInstance;
	public static TelaInicialDiarioProfessor getInstance(MainView mainView){		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialDiarioProfessor(mainView);
		}else{
			uniqueInstance.adicionarDiarioProfessor.updateClientData();
			uniqueInstance.editarDiarioProfessor.updateClientData();
		}
		return uniqueInstance;
	}		

	private TelaInicialDiarioProfessor(MainView mainView) {
		
		this.mainView = mainView;
		
		adicionarDiarioProfessor =  new AdicionarDiarioProfessor(this);
		editarDiarioProfessor =  new EditarDiarioProfessor(this);
//		editarNotaPorAluno = new EditarNotaPorAluno(this);
		
		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);		
//		stackPanel.setPixelSize(intWidthTable+50, intHeightTable);
		tabLayoutPanel.setHeight(Integer.toString(intHeightTable)+"px");
		tabLayoutPanel.setAnimationDuration(500);
		tabLayoutPanel.setAnimationVertical(true);
		
		tabLayoutPanel.add(adicionarDiarioProfessor, new MpHeaderWidget(txtConstants.presencaCriarLista(), "images/plus-circle.png"));
		tabLayoutPanel.add(editarDiarioProfessor, new MpHeaderWidget(txtConstants.presencaEditarLista(), "images/comment_edit.png"));		
		
		
//		VerticalPanel verticalPanelPage = new VerticalPanel();		
//		verticalPanelPage.add(tabLayoutPanel);
//		verticalPanelPage.add(new InlineHTML("&nbsp;"));
				
     	initWidget(tabLayoutPanel);
		
	}
	
	public MainView getMainView() {
		return mainView;
	}
	
	protected void updateEditarDiarioProfessor(){
		editarDiarioProfessor.populateComboBoxTipoPresenca();
	}

	
}




