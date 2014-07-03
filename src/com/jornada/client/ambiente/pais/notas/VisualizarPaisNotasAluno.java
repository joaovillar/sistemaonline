package com.jornada.client.ambiente.pais.notas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;
import com.googlecode.gwt.charts.client.options.Animation;
import com.googlecode.gwt.charts.client.options.AnimationEasing;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.Legend;
import com.googlecode.gwt.charts.client.options.LegendPosition;
import com.googlecode.gwt.charts.client.options.VAxis;
import com.jornada.client.ambiente.aluno.notas.TelaInicialAlunoVisualizarNotas;
import com.jornada.client.ambiente.coordenador.periodo.TelaInicialPeriodo;
import com.jornada.client.classes.listBoxes.ambiente.pais.MpSelectionAlunosPorCursoAmbientePais;
import com.jornada.client.classes.listBoxes.ambiente.pais.MpSelectionCursoAmbientePais;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpPanelPageMainView;
import com.jornada.client.classes.widgets.panel.MpSpacePanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceNota;
import com.jornada.shared.classes.TipoUsuario;


public class VisualizarPaisNotasAluno extends VerticalPanel{

	private String[][] list;
	
	private Grid gridBoletimChart;
	
	private ColumnChart chart;
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
//	private AsyncCallback<ArrayList<Usuario>> callbackGetAlunosFiltro;	
	private AsyncCallback<String[][]> callbackBoletim;
	
	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpPanelAlunosLoading = new MpPanelLoading("images/radar.gif");
	
	String [][] arrayBoletim;
	
//	private TextBox txtFiltroAluno;		
	
//	private ListBox listBoxAluno;
	private MpSelectionCursoAmbientePais listBoxCurso;
	private MpSelectionAlunosPorCursoAmbientePais listBoxAlunosPorCurso;
	
	private VerticalPanel vPanelBoletim;
	
	private TelaInicialPaisVisualizarNotas telaInicialPaisVisualizarNotas;
	
	private static VisualizarPaisNotasAluno uniqueInstance;	
	
	public static VisualizarPaisNotasAluno getInstance(TelaInicialPaisVisualizarNotas telaInicialPaisVisualizarNotas){
		
		if(uniqueInstance==null){
			uniqueInstance = new VisualizarPaisNotasAluno(telaInicialPaisVisualizarNotas);
		}
		return uniqueInstance;
	}	

	
	private VisualizarPaisNotasAluno(TelaInicialPaisVisualizarNotas telaInicialPaisVisualizarNotas){
		
//		this.cadastroNota=cadastroNota;
		this.telaInicialPaisVisualizarNotas = telaInicialPaisVisualizarNotas;
		
		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		
		mpPanelAlunosLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelAlunosLoading.show();
		mpPanelAlunosLoading.setVisible(false);
		
		vPanelBoletim = new VerticalPanel();
		
		VerticalPanel vBodyPanel = new VerticalPanel();
		
		vBodyPanel.add(drawPassoUmSelecioneAluno());
		vBodyPanel.add(new InlineHTML("&nbsp;"));
//		vBodyPanel.add(drawPassoDoisEditarNotasAluno());

		
		super.add(vBodyPanel);		
		
		
	}
	
	
	public MpPanelPageMainView drawPassoUmSelecioneAluno(){

		
		MpPanelPageMainView mpPanelPasso1 = new MpPanelPageMainView(txtConstants.notaSelecionarAluno(), "images/user_male_black_red_16.png");
		mpPanelPasso1.setWidth(Integer.toString(TelaInicialPaisVisualizarNotas.intWidthTable)+"px");
		mpPanelPasso1.setWidth(Integer.toString(TelaInicialAlunoVisualizarNotas.intWidthTable-20)+"px");
		mpPanelPasso1.setHeight(Integer.toString(TelaInicialAlunoVisualizarNotas.intHeightTable-50)+"px");

		
		Label lblNomeCurso = new Label(txtConstants.curso());
		Label lblNomeAluno = new Label(txtConstants.alunoNome());
		
		listBoxCurso = new MpSelectionCursoAmbientePais(this.telaInicialPaisVisualizarNotas.getMainView().getUsuarioLogado());
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());
		
		listBoxAlunosPorCurso = new MpSelectionAlunosPorCursoAmbientePais();
		listBoxAlunosPorCurso.addChangeHandler(new MpAlunosPorCursoSelectionChangeHandler());
		
		
//		txtFiltroAluno = new TextBox();		
//		MpImageButton btnFiltrar = new MpImageButton(txtConstants.usuarioFiltrarListaAlunos(), "images/magnifier.png");
		
//		txtFiltroAluno.addKeyDownHandler(new KeyDownHandlerFiltrarAluno());		
//		btnFiltrar.addClickHandler(new ClickHandlerFiltrarAluno());				
		
		lblNomeCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblNomeAluno.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		
		lblNomeCurso.setStyleName("design_label");	
		lblNomeAluno.setStyleName("design_label");	
//		txtFiltroAluno.setStyleName("design_text_boxes");		

		Grid gridFiltrar = new Grid(1,7);		
		gridFiltrar.setCellSpacing(2);
		gridFiltrar.setCellPadding(2);
		gridFiltrar.setBorderWidth(0);				
		
		int row=0;
		gridFiltrar.setWidget(row, 0, lblNomeCurso);
		gridFiltrar.setWidget(row, 1, listBoxCurso);
		gridFiltrar.setWidget(row, 2, new MpSpacePanel());
		gridFiltrar.setWidget(row, 3, lblNomeAluno);
		gridFiltrar.setWidget(row, 4, listBoxAlunosPorCurso);
		gridFiltrar.setWidget(row, 5, new MpSpacePanel());
		gridFiltrar.setWidget(row, 6, mpPanelAlunosLoading);
//		gridFiltrar.setWidget(row, 2, txtFiltroAluno);
//		gridFiltrar.setWidget(row, 3, btnFiltrar);
		
		
		gridBoletimChart = new Grid(1,2);
		gridBoletimChart.setBorderWidth(0);
		gridBoletimChart.setCellPadding(2);
		gridBoletimChart.setCellSpacing(2);
		gridBoletimChart.setSize(Integer.toString(TelaInicialPeriodo.intWidthTable)+"px",Integer.toString(TelaInicialPeriodo.intHeightTable-180)+"px");
		
		FlexTable gridBoletim = new FlexTable();		
		gridBoletim.setBorderWidth(0);	
		gridBoletim.setSize(Integer.toString(TelaInicialPeriodo.intWidthTable),Integer.toString(TelaInicialPeriodo.intHeightTable));
		row=0;
		
		gridBoletim.setWidget(row, 0, vPanelBoletim);
		
		gridBoletimChart.setWidget(0, 0, gridBoletim);
		
		mpPanelPasso1.add(gridFiltrar);
		mpPanelPasso1.add(gridBoletimChart);
		mpPanelPasso1.add(new InlineHTML("&nbsp;"));
		
//        // Create the API Loader
//        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
//        chartLoader.loadApi(new Runnable() {
//
//                @Override
//                public void run() {
//            		// Create and attach the chart
//    				chart = new ColumnChart();
//    				getGridBoletimChart().setWidget(0, 1, chart);
//    				
//                }
//        });		
        
        gridBoletimChart.getCellFormatter().setWidth(0, 0, "500px");
        gridBoletimChart.getCellFormatter().setWidth(0, 1, "500px");
        gridBoletimChart.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
        gridBoletimChart.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        gridBoletimChart.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
        gridBoletimChart.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);           
		
	
		callbackBoletim = new AsyncCallback<String[][]>() {

			public void onFailure(Throwable caught) {
				mpPanelAlunosLoading.setVisible(false);		
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.notaErroCarregar());
				mpDialogBoxWarning.showDialog();
			}

			@Override
			public void onSuccess(String[][] list) {
				vPanelBoletim.clear();
				mpPanelAlunosLoading.setVisible(false);	
				setList(list);
				vPanelBoletim.add(createBoletimTable(list));	
				// Create the API Loader
				ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
				chartLoader.loadApi(new Runnable() {
					@Override
					public void run() {
						// Create and attach the chart
						if (chart == null) {
							chart = new ColumnChart();
							getGridBoletimChart().setWidget(0, 1, chart);
						}

						draw(getList());
					}
				});
			
			}
		};		
		
		
		
		return mpPanelPasso1;
		
	}

	

	
//	private class ClickHandlerFiltrarAluno implements ClickHandler {
//		public void onClick(ClickEvent event) {			
//			listBoxAlunosPorCurso.filterComboBox(txtFiltroAluno.getText());
//		}
//	}
//	
//	private class KeyDownHandlerFiltrarAluno implements KeyDownHandler {
//		public void onKeyDown(KeyDownEvent event) {
//			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
//				listBoxAlunosPorCurso.filterComboBox(txtFiltroAluno.getText());
//			}
//		}
//	}

	private class MpCursoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
			listBoxAlunosPorCurso.populateComboBox(telaInicialPaisVisualizarNotas.getMainView().getUsuarioLogado(), idCurso);
		}  
	}	
	
	private class MpAlunosPorCursoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			
			populateBoletimAluno();

		}  
	}

	
	protected void populateBoletimAluno() {
		mpPanelAlunosLoading.setVisible(true);	
		int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
		int idTipoUsuario = TipoUsuario.ALUNO;
		int intIdCurso = listBoxAlunosPorCurso.getSelectedIndex();
		if(intIdCurso==-1){
			mpPanelAlunosLoading.setVisible(false);
			vPanelBoletim.clear();
		}
		else{
			int idUsuario = Integer.parseInt(listBoxAlunosPorCurso.getValue(intIdCurso));
			GWTServiceNota.Util.getInstance().getBoletimNotasPorAlunoPorCurso(idCurso, idTipoUsuario, idUsuario,callbackBoletim);
		}

	}
	
	private FlexTable createBoletimTable(String[][] listBoletim){
		
		
		FlexTable flexTable = new FlexTable();
		flexTable.setCellSpacing(0);
		flexTable.setCellPadding(0);
		flexTable.setBorderWidth(0);
		flexTable.setStyleName("table-boletim");
		flexTable.setSize(Integer.toString(TelaInicialPeriodo.intWidthTable),Integer.toString(TelaInicialPeriodo.intHeightTable));

        int lin = listBoletim[0].length;
        int col = listBoletim.length;    
        
        
		
		for(int row=0;row<lin;row++){
			
			double doubleMedia=0;
			int intCalcularMedia=0;
			
			for(int column=0;column<col;column++){
				String strTextBoletim = listBoletim[column][row];
				Label lblText = new Label();
				lblText.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				
				//Se não tiver nota coloca '-' para preencher a tabela com alguma coisa
				if(strTextBoletim==null || strTextBoletim.isEmpty()){
					strTextBoletim="-";
					lblText.setText(strTextBoletim);					
				}
				//senão adicione a nota
				else{
					lblText.setText(strTextBoletim);										
				}
				
				flexTable.setWidget(row, column, lblText);				

				//linha zero é o header
				if(row==0){
					lblText.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
					flexTable.getWidget(row, column).setStyleName("table-boletim-header");	
				}
				//Coluna 0 (que não seja a linha 0) é o nome das disciplinas
				else if(column==0&&row!=0){
					lblText.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
					flexTable.getWidget(row, column).setStyleName("table-boletim-disciplinas");
					
				}
				//se não for o header nem as disciplinas então coloque as notas
				else
				{
					String strNota = listBoletim[column][row];
					//se não tiver nota coloque o fundo branco
					if(strNota==null || strNota.isEmpty()){
						flexTable.getWidget(row, column).setStyleName("table-boletim-cell");
					}
					else{
						double doubleNota = Double.parseDouble(strNota);
						doubleMedia = doubleMedia +doubleNota;
						intCalcularMedia++;
						//se nota maior que 6 coloque o fundo verde
						if(doubleNota>=6.0){
							flexTable.getWidget(row, column).setStyleName("table-boletim-cell-green");
						}
						//se nota menor que 6 coloque o fundo vermelho
						else if(doubleNota<6.0){
							flexTable.getWidget(row, column).setStyleName("table-boletim-cell-red");
						}
					}					
					
				}				
				
			}
			
			
			//Begin Criando Coluna Média Geral
			
			if (row == 0) {
				flexTable.setWidget(row, col + 1, new Label(txtConstants.notaMediaFinal()));
				flexTable.getWidget(row, col + 1).setStyleName("table-boletim-header-media");
			} else {

				doubleMedia = doubleMedia / intCalcularMedia;
				String strMediaGeral = "";
				if (intCalcularMedia == 0) {
					strMediaGeral = "-";
				} else {
					strMediaGeral = Double.toString(doubleMedia);
				}

				Label lblMedia = new Label(strMediaGeral);
				lblMedia.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

				flexTable.setWidget(row, col + 1, lblMedia);
				
				if (doubleMedia >= 6.0) {
					flexTable.getWidget(row, col + 1).setStyleName("table-boletim-cell-green-media");
				}
				else if (doubleMedia < 6.0) { // se nota menor que 6 coloque o fundo vermelho 
					flexTable.getWidget(row, col + 1).setStyleName("table-boletim-cell-red-media");
				}
				else{
					flexTable.getWidget(row, col + 1).setStyleName("table-boletim-cell");
				}
			}
			//Criando Coluna Média Geral			
			
		}

		return flexTable;
		
	}
	
	public void updateClientData(){
		listBoxCurso.populateComboBox(this.telaInicialPaisVisualizarNotas.getMainView().getUsuarioLogado());
	}
	
	private void draw(String[][] list) {
		//chart.clearChart();
//		chart.re
		chart.setWidth("600px");
		chart.setHeight("350px");
		
		
		int numeroColunas = list.length; 
        int numeroLinhas = list[0].length;
          

//		String[] countries = new String[] { "Austria", "Bulgaria", "Denmark", "Greece" };
		
		String[] disciplinas = new String[numeroLinhas-1];		
		for(int i=0;i<disciplinas.length;i++){
			disciplinas[i]=list[0][i+1];
		}
		
		
		//int[] years = new int[] { 2003, 2004, 2005, 2006, 2007, 2008 };
		String[] periodos = new String[numeroColunas-1];
		for(int i=0;i<periodos.length;i++){
			periodos[i]= list[i+1][0];
		}
		

		
		double[][] values = new double[numeroLinhas-1][numeroColunas-1];
		
		for (int row = 0; row < disciplinas.length; row++) {
			for (int column = 0; column < periodos.length; column++) {
				try {
					String strValue = list[column + 1][row + 1];
					values[row][column] = Double.parseDouble(strValue);
				} catch (Exception ex) {
					values[row][column] = 0;
				}
			}
		}
		
		
//		int[][] values = new int[][] 
//				{ 
//					{ 8 },
//					{ 5 },
//					{ 9 }
//				};
		
		
		

		// Prepare the data
		DataTable dataTable = DataTable.create();
		

		
		dataTable.addColumn(ColumnType.STRING, txtConstants.disciplina());
		for (int i = 0; i < disciplinas.length; i++) {
			dataTable.addColumn(ColumnType.NUMBER, disciplinas[i]);
		}
		

		
		dataTable.addRows(periodos.length);
		for (int i = 0; i < periodos.length; i++) {
			dataTable.setValue(i, 0, String.valueOf(periodos[i]));
		}
		
		int valueCol = values.length;		
		for (int col = 0; col < valueCol; col++) {
			for (int row = 0; row < values[col].length; row++) {
				dataTable.setValue(row, col + 1, values[col][row]);
			}
		}

		// Set options
		ColumnChartOptions options = ColumnChartOptions.create();
		options.setFontName("Tahoma");
		options.setTitle(txtConstants.alunoAmbienteNotas());
		
		
		
		options.setHAxis(HAxis.create(txtConstants.disciplina()));
		
		
		VAxis vaxis = VAxis.create(txtConstants.nota());
		vaxis.setMaxValue(10.0);
		vaxis.setMinValue(0.0);
		options.setVAxis(vaxis);
		
		VAxis.create().setMaxValue(10.0);
		VAxis.create().setMaxValue(10.0);
		
		Animation animation = Animation.create();
		animation.setDuration(500);
		animation.setEasing(AnimationEasing.OUT);
		Legend legend = Legend.create(LegendPosition.BOTTOM);
			
		options.setAnimation(animation);
		options.setLegend(legend);

		// Draw the chart
		chart.draw(dataTable, options);
	}


	public Grid getGridBoletimChart() {
		return gridBoletimChart;
	}		
	
	public String[][] getList() {
		return list;
	}


	public void setList(String[][] list) {
		this.list = list;
	}	
	

}
