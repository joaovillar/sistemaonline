package com.jornada.client.ambiente.professor.ocorrencia;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.jornada.client.MainView;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.shared.classes.TipoUsuario;

public class TelaInicialProfessorOcorrencia extends Composite{
	
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	public  static final int intWidthTable=1400;
	public static final int intHeightTable=600;
	
	private AdicionarOcorrencia adicionarOcorrencia;
	private EditarOcorrencia editarOcorrencia;
	private VisualizarOcorrencia visualizarOcorrencia;
	private AprovarOcorrencia aprovarOcorrencia;
	
	private MainView mainView;

	
	private static TelaInicialProfessorOcorrencia uniqueInstance;
	public static TelaInicialProfessorOcorrencia getInstance(MainView mainView){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialProfessorOcorrencia(mainView);
		}else{
			uniqueInstance.adicionarOcorrencia.updateClientData();
			uniqueInstance.editarOcorrencia.updateClientData();
			uniqueInstance.visualizarOcorrencia.updateClientData();
		}
		
		return uniqueInstance;
	}		

	private TelaInicialProfessorOcorrencia(MainView mainView) {
		
		this.mainView = mainView;
		
		
		adicionarOcorrencia = new AdicionarOcorrencia(this, EnumOcorrencia.ADICIONAR);
		editarOcorrencia = new EditarOcorrencia(this);
		visualizarOcorrencia = new VisualizarOcorrencia(this);
		
		if(mainView.getUsuarioLogado().getIdTipoUsuario()==TipoUsuario.COORDENADOR || mainView.getUsuarioLogado().getIdTipoUsuario()==TipoUsuario.ADMINISTRADOR)
		{
			aprovarOcorrencia = AprovarOcorrencia.getInstance(this);
		}
		
		// StackLayoutPanel stackPanel = new StackLayoutPanel(Unit.EM);
		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2.5, Unit.EM);		
//		stackPanel.setPixelSize(intWidthTable+50, intHeightTable);
		tabLayoutPanel.setHeight(Integer.toString(intHeightTable)+"px");
		tabLayoutPanel.setAnimationDuration(500);
		tabLayoutPanel.setAnimationVertical(true);

		
		tabLayoutPanel.add(adicionarOcorrencia, new MpHeaderWidget(txtConstants.ocorrenciaAdicionarNovaOcorrencia(), "images/plus-circle.png"));
		tabLayoutPanel.add(editarOcorrencia, new MpHeaderWidget(txtConstants.ocorrenciaEditar(), "images/comment_edit.png"));				
		tabLayoutPanel.add(visualizarOcorrencia, new MpHeaderWidget(txtConstants.ocorrenciaVisualizar(), "images/organizacao.png"));
		
		if(mainView.getUsuarioLogado().getIdTipoUsuario()==TipoUsuario.COORDENADOR || mainView.getUsuarioLogado().getIdTipoUsuario()==TipoUsuario.ADMINISTRADOR){
			tabLayoutPanel.add(aprovarOcorrencia, new MpHeaderWidget(txtConstants.ocorrenciaAprovar(), "images/image002.png"));
		}
		
		
//		VerticalPanel verticalPanelPage = new VerticalPanel();		
//		verticalPanelPage.add(stackPanel);
//		verticalPanelPage.add(new InlineHTML("&nbsp;"));
				
     	initWidget(tabLayoutPanel);
		
	}
	
	
	
	
	public MainView getMainView() {
		return mainView;
	}


	protected void updateEditarOcorrenciaPopulateGrid(){
		editarOcorrencia.populateGridOcorrencia();
	}
	
	protected void updateVisualizarOcorrenciaPopulateGrid(){
//		editarOcorrencia.populateGridOcorrencia();
		visualizarOcorrencia.populateGridOcorrencia();
	}
	
	protected void updateAprovarOcorrenciaPopulateGrid(){
//		editarOcorrencia.populateGridOcorrencia();
		if(mainView.getUsuarioLogado().getIdTipoUsuario()==TipoUsuario.COORDENADOR || mainView.getUsuarioLogado().getIdTipoUsuario()==TipoUsuario.ADMINISTRADOR)
		{
			aprovarOcorrencia.populateGrid();
		}
	}

}
