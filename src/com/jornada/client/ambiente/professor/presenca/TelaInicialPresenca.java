
package com.jornada.client.ambiente.professor.presenca;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;


public class TelaInicialPresenca extends Composite{
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);

	public  static final int intWidthTable=1400;
	public static final int intHeightTable=550;
	

	private AdicionarPresenca adicionarDiarioProfessor;
//	private EditarPresenca editarDiarioProfessor;
	private MainView mainView;

	
	private static TelaInicialPresenca uniqueInstance;
	public static TelaInicialPresenca getInstance(MainView mainView){		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialPresenca(mainView);
		}else{
			uniqueInstance.adicionarDiarioProfessor.updateClientData();
//			uniqueInstance.editarDiarioProfessor.updateClientData();
		}
		return uniqueInstance;
	}		

	private TelaInicialPresenca(MainView mainView) {
		
		this.mainView = mainView;
		
		adicionarDiarioProfessor =  new AdicionarPresenca(this);
//		editarDiarioProfessor =  new EditarPresenca(this);
//		editarNotaPorAluno = new EditarNotaPorAluno(this);
		
//		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);		
//		stackPanel.setPixelSize(intWidthTable+50, intHeightTable);
		
		TabPanel tabLayoutPanel = new TabPanel(); 
		tabLayoutPanel.setHeight(Integer.toString(intHeightTable)+"px");
//		tabLayoutPanel.setAnimationDuration(500);
//		tabLayoutPanel.setAnimationVertical(true);
		tabLayoutPanel.setAnimationEnabled(true);
		tabLayoutPanel.setWidth("99%");
		
		tabLayoutPanel.add(adicionarDiarioProfessor, new MpHeaderWidget("Adicionar Faltas por Disciplina", "images/plus-circle.png"));
//		tabLayoutPanel.add(editarDiarioProfessor, new MpHeaderWidget("Editar lista de Faltas", "images/comment_edit.png"));		
		tabLayoutPanel.selectTab(0);
		
//		VerticalPanel verticalPanelPage = new VerticalPanel();		
//		verticalPanelPage.add(tabLayoutPanel);
//		verticalPanelPage.add(new InlineHTML("&nbsp;"));
				
     	initWidget(tabLayoutPanel);
		
	}
	
	public MainView getMainView() {
		return mainView;
	}
	
//	protected void updateEditarDiarioProfessor(){
//		editarDiarioProfessor.populateComboBoxTipoPresenca();
//	}

	
}




