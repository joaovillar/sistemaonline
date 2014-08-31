package com.jornada.client.ambiente.professor.diario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionCursoAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionDisciplinaAmbienteProfessor;
import com.jornada.client.classes.listBoxes.ambiente.professor.MpSelectionPeriodoAmbienteProfessor;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.cells.MpStyledSelectionCellPresenca;
import com.jornada.client.classes.widgets.dialog.MpConfirmDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceAula;
import com.jornada.client.service.GWTServicePresenca;
import com.jornada.shared.classes.Aula;
import com.jornada.shared.classes.TipoPresenca;
import com.jornada.shared.classes.utility.MpUtilClient;


public class EditarDiarioProfessor extends VerticalPanel {

	private final static int INT_POSITION_NAME=1;
	private final static int INT_POSITION_FALTAS=INT_POSITION_NAME+1;

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpPanelLoadingAluno = new MpPanelLoading("images/radar.gif");

	private MpSelectionCursoAmbienteProfessor listBoxCurso;
	private MpSelectionPeriodoAmbienteProfessor listBoxPeriodo;
	private MpSelectionDisciplinaAmbienteProfessor listBoxDisciplina;	
//	private MpSelectionConteudoProgramatico listBoxConteudoProgramatico;
	private LinkedHashMap<String, String> listaTipoPresenca = new LinkedHashMap<String, String>();
	
	private TextBox txtSearch;
//	private MpLabelTextBoxError lblErroDisciplina;

	
	private CellTable<ArrayList<String>> cellTable;
	private ListDataProvider<ArrayList<String>> dataProvider;
	ArrayList<ArrayList<String>> arrayListBackup = new ArrayList<ArrayList<String>>(); 

	
	ArrayList<String> arrayColumns = new ArrayList<String>();
	private ArrayList<Aula> arrayAula = new ArrayList<Aula>();
		
	VerticalPanel vFormPanel = new VerticalPanel();
	ScrollPanel scrollPanel = new ScrollPanel();
	
	TextConstants txtConstants;
	
	private int cellTablePageIndex=0;
	
//	private int intCount=0;
	
	
	private TelaInicialDiarioProfessor telaInicialDiarioProfessor;

	public EditarDiarioProfessor(final TelaInicialDiarioProfessor telaInicialDiarioProfessor) {
		
		scrollPanel.setAlwaysShowScrollBars(false);

//		scrollPanel.setSize(Integer.toString(TelaInicialDiarioProfessor.intWidthTable)+"px",Integer.toString(TelaInicialDiarioProfessor.intHeightTable-210)+"px");
		scrollPanel.setHeight(Integer.toString(TelaInicialDiarioProfessor.intHeightTable-210)+"px");
		scrollPanel.setWidth("90%");
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialDiarioProfessor=telaInicialDiarioProfessor;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpPanelLoadingAluno.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoadingAluno.show();
		mpPanelLoadingAluno.setVisible(false);

		FlexTable flexTableWithListBoxes = new FlexTable();
		flexTableWithListBoxes.setCellSpacing(3);
		flexTableWithListBoxes.setCellPadding(3);
		flexTableWithListBoxes.setBorderWidth(0);		
		FlexCellFormatter cellFormatter = flexTableWithListBoxes.getFlexCellFormatter();
		
		

		// Add a title to the form
		cellFormatter.setColSpan(0, 0, 0);
		cellFormatter.setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_CENTER);
		
//		lblErroDisciplina = new MpLabelTextBoxError();
		
		Label lblCurso = new Label(txtConstants.curso());
		Label lblPeriodo = new Label(txtConstants.periodo());
		Label lblDisciplina = new Label(txtConstants.disciplina());
		Label lblDataInicial = new Label(txtConstants.cursoDataInicial());
		
		lblCurso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		lblPeriodo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		lblDisciplina.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblDataInicial.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		lblCurso.setStyleName("design_label");
		lblPeriodo.setStyleName("design_label");
		lblDisciplina.setStyleName("design_label");
		lblDataInicial.setStyleName("design_label");

		// Add some standard form options
		int row = 1;
		flexTableWithListBoxes.setWidget(row, 0, lblCurso);

		listBoxCurso = new MpSelectionCursoAmbienteProfessor(telaInicialDiarioProfessor.getMainView().getUsuarioLogado());		
		listBoxPeriodo = new MpSelectionPeriodoAmbienteProfessor(telaInicialDiarioProfessor.getMainView().getUsuarioLogado());		
		listBoxDisciplina = new MpSelectionDisciplinaAmbienteProfessor(telaInicialDiarioProfessor.getMainView().getUsuarioLogado());		
		
		listBoxCurso.addChangeHandler(new MpCursoSelectionChangeHandler());
		listBoxPeriodo.addChangeHandler(new MpPeriodoSelectionChangeHandler());		
		listBoxDisciplina.addChangeHandler(new MpDisciplinaSelectionChangeHandler());

		
		flexTableWithListBoxes.setWidget(row, 0, lblCurso);	flexTableWithListBoxes.setWidget(row++, 1, listBoxCurso);
		flexTableWithListBoxes.setWidget(row, 0, lblPeriodo);flexTableWithListBoxes.setWidget(row++, 1, listBoxPeriodo);
		flexTableWithListBoxes.setWidget(row, 0, lblDisciplina);flexTableWithListBoxes.setWidget(row, 1, listBoxDisciplina);flexTableWithListBoxes.setWidget(row++, 2, mpPanelLoadingAluno);
//		flexTableWithListBoxes.setWidget(row, 0, lblCurso);flexTableWithListBoxes.setWidget(row, 1, lblPeriodo);flexTableWithListBoxes.setWidget(row++, 2, lblDisciplina);
//		flexTableWithListBoxes.setWidget(row, 0, listBoxCurso);flexTableWithListBoxes.setWidget(row, 1, listBoxPeriodo);flexTableWithListBoxes.setWidget(row, 2, listBoxDisciplina);flexTableWithListBoxes.setWidget(row++, 3, lblErroDisciplina);
		
		vFormPanel.add(flexTableWithListBoxes);
		vFormPanel.setWidth("100%");
		vFormPanel.setBorderWidth(0);

/***************************************Begin Callbacks***************************************/

		

/***************************************End Callbacks***************************************/
		populateComboBoxTipoPresenca();
//		populateDinamicColumns();
		
		this.setWidth("100%");
		super.add(vFormPanel);

	}
	
/*******************************************************************************************************/
/*******************************************************************************************************/	
/*******************************************************************************************************/	
/***************************************Begin Event Handlers***************************************************/
	
	
	private class MpCursoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {			
			int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
			listBoxPeriodo.populateComboBox(idCurso);
		}  
	}	
	
	private class MpPeriodoSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int index = listBoxPeriodo.getSelectedIndex();
			if(index==-1){
				listBoxDisciplina.clear();
				cleanCellTable();
			}
			else{
				int idPeriodo = Integer.parseInt(listBoxPeriodo.getValue(index));
				listBoxDisciplina.populateComboBox(idPeriodo);				
			}
		}  
	}
	
	private class MpDisciplinaSelectionChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			
			int index = listBoxDisciplina.getSelectedIndex();
			if(index==-1){
				listBoxDisciplina.clear();
				cleanCellTable();
			}
			else{
				mpPanelLoadingAluno.setVisible(true);
				populateDinamicColumns();
			}
			
			
		}  
	}	
	
	private class EnterKeyUpHandler implements KeyUpHandler {
		 public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				filtrarCellTable(txtSearch.getText());
			}
		}
	}

	
	private class ClickHandlerFiltrar implements ClickHandler {
		public void onClick(ClickEvent event) {
			filtrarCellTable(txtSearch.getText());
		}
	}
	
/***************************************End Event Handlers***************************************/
/*******************************************************************************************************/			
/*******************************************************************************************************/
/*******************************************************************************************************/	
/*******************************************************************************************************/	
/***************************************Begin POPULATE DATA***************************************/	
	
	protected void populateComboBoxTipoPresenca() {

		mpPanelLoadingAluno.setVisible(true);
		GWTServicePresenca.Util.getInstance().getTipoPresencas(

		new AsyncCallback<ArrayList<TipoPresenca>>() {

			@Override
			public void onFailure(Throwable caught) {
				mpPanelLoadingAluno.setVisible(false);
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.presencaErroCarregarDados());
			}

			@Override
			public void onSuccess(ArrayList<TipoPresenca> list) {
				MpUtilClient.isRefreshRequired(list);
				listaTipoPresenca.clear();				
				for (TipoPresenca currentTipoPresenca : list) {
					String strIdTipoAvaliacao = Integer.toString(currentTipoPresenca.getIdTipoPresenca());
					String strNomeTipoAvaliacao = currentTipoPresenca.getTipoPresenca();
					listaTipoPresenca.put(strIdTipoAvaliacao,strNomeTipoAvaliacao);
				}
				populateDinamicColumns();
			}

		});
	}	
	
	private void populateDinamicColumns() {

		int idSelectedDisciplina = listBoxDisciplina.getSelectedIndex();
		if (idSelectedDisciplina != -1) {

			int idDisciplina = Integer.parseInt(listBoxDisciplina.getValue(idSelectedDisciplina));
			GWTServiceAula.Util.getInstance().getAulas(idDisciplina, 		
			new AsyncCallback<ArrayList<Aula>>() 
			{

				public void onFailure(Throwable caught) {
					mpPanelLoadingAluno.setVisible(false);
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.topicoErroSalvar());
					mpDialogBoxWarning.showDialog();
				}

				@Override
						public void onSuccess(ArrayList<Aula> list) {
							MpUtilClient.isRefreshRequired(list);
							arrayColumns.clear();
							arrayAula.clear();
							for (int i = 0; i < list.size(); i++) {
								arrayAula.add(list.get(i));
								String strDate = MpUtilClient.convertDateToString(list.get(i).getData());
								arrayColumns.add(strDate);
							}

							cleanCellTable();
							populateGridListaPresenca();
						}

			});
		}
	}	
//	
	private void populateGridListaPresenca() {

		int idSelectedDisciplina = listBoxDisciplina.getSelectedIndex();
		if (idSelectedDisciplina != -1) {
			
			int idCurso = Integer.parseInt(listBoxCurso.getValue(listBoxCurso.getSelectedIndex()));
			int idDisciplina = Integer.parseInt(listBoxDisciplina.getValue(listBoxDisciplina.getSelectedIndex()));
			
			GWTServicePresenca.Util.getInstance().getListaPresencaAlunosArrayList(idCurso, idDisciplina, new AsyncCallback<ArrayList<ArrayList<String>>>() {

								public void onFailure(Throwable caught) {
									mpPanelLoadingAluno.setVisible(false);
									mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
									mpDialogBoxWarning.setBodyText(txtConstants.topicoErroSalvar());
									mpDialogBoxWarning.showDialog();
								}

								@Override
								public void onSuccess(ArrayList<ArrayList<String>> list) {

									MpUtilClient.isRefreshRequired(list);
									dataProvider = new ListDataProvider<ArrayList<String>>();
									arrayListBackup.clear();
									dataProvider.getList().clear();

									for (int i = 0; i < list.size(); i++) {
										dataProvider.getList().add(list.get(i));
										arrayListBackup.add(list.get(i));
									}

									initializeCellTable();

								}

							});
		}
	}
	
	public void updateClientData(){
		listBoxCurso.populateComboBox(this.telaInicialDiarioProfessor.getMainView().getUsuarioLogado());
	}	
	
/***************************************END POPULATE DATA***********************************************/		
/*******************************************************************************************************/	
/*******************************************************************************************************/
/*******************************************************************************************************/	
/***************************************BEGIN CellTable Functions***************************************/
	
	
	public void initializeCellTable(){
		cellTable = new CellTable<ArrayList<String>>(10,GWT.<CellTableStyle> create(CellTableStyle.class));

//		cellTable.setWidth(Integer.toString(TelaInicialDiarioProfessor.intWidthTable)+ "px");		
		cellTable.setWidth("90%");
		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);
		
		dataProvider.addDataDisplay(cellTable);	
		
		final MpSimplePager mpPager = new MpSimplePager();
		mpPager.setDisplay(cellTable);
//		mpPager.setPageSize(10);
//		cellTable.setPageSize(cellTablePageIndex);
//		mpPager.setPage
		
		
		
		/////////////////////////ColumnName//////////////////////////////////
		IndexedColumn indexColumnName = new IndexedColumn(INT_POSITION_NAME);
		
		indexColumnName.setSortable(true);
		
		ListHandler<ArrayList<String>> sortHandler = new ListHandler<ArrayList<String>>(dataProvider.getList());

		sortHandler.setComparator(indexColumnName, new Comparator<ArrayList<String>>() {
	      @Override
	      public int compare(ArrayList<String> o1, ArrayList<String> o2) {
	    	  System.out.println(o1.get(INT_POSITION_NAME));
	        return o1.get(INT_POSITION_NAME).compareTo(o2.get(INT_POSITION_NAME));
	      }
	    });	

		/////////////////////////ColumnName//////////////////////////////////
		
		/////////////////////////Column Numero Faltas//////////////////////////////////
		IndexedColumn indexColumnFaltas = new IndexedColumn(INT_POSITION_FALTAS);
		
		indexColumnFaltas.setSortable(true);

		sortHandler.setComparator(indexColumnFaltas, new Comparator<ArrayList<String>>() {
	      @Override
	      public int compare(ArrayList<String> o1, ArrayList<String> o2) {
	    	  
	        return o1.get(INT_POSITION_FALTAS).compareTo(o2.get(INT_POSITION_FALTAS));
	      }
	    });	
		cellTable.addColumnSortHandler(sortHandler);
		/////////////////////////Column Numero Faltas//////////////////////////////////
		
		
		
		String strSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		SafeHtmlBuilder builder = new SafeHtmlBuilder();
		builder.appendHtmlConstant(strSpace + strSpace + txtConstants.aluno() + strSpace + strSpace);

		 SafeHtml safeHtml = builder.toSafeHtml();
		cellTable.addColumn(indexColumnName, safeHtml);
		cellTable.addColumn(indexColumnFaltas, txtConstants.presencaNumeroFaltas());

	
        for (int column = 0; column < arrayColumns.size(); column++) {     
        	
        	final MpStyledSelectionCellPresenca tipoAvaliacaoCell = new MpStyledSelectionCellPresenca(listaTipoPresenca);
        	final IndexedColumn indexedColumn = new IndexedColumn(tipoAvaliacaoCell, column+INT_POSITION_FALTAS+1);
        	
        	indexedColumn.setFieldUpdater(new FieldUpdater<ArrayList<String>, String>() {
						@Override
						public void update(int index, ArrayList<String> object, final String value) {
							// Called when the user changes the value.
							mpPanelLoadingAluno.setVisible(true);
							final int column = indexedColumn.getIndex()-INT_POSITION_FALTAS-1;
							int idAula = arrayAula.get(column).getIdAula();
							int idUsuario = Integer.parseInt(dataProvider.getList().get(index).get(0));
							int idTipoPresenca = Integer.parseInt(value);
							
							GWTServicePresenca.Util.getInstance().updatePresencaRow(idAula, idUsuario, idTipoPresenca,
											new AsyncCallback<Boolean>() {
												@Override
												public void onFailure(Throwable caught) {
													mpPanelLoadingAluno.setVisible(false);
													mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
													mpDialogBoxWarning.setBodyText(txtConstants.presencaErroAtualizar());
												}

												@Override
												public void onSuccess(Boolean success) {
													mpPanelLoadingAluno.setVisible(false);													
													cellTablePageIndex = cellTable.getPageStart();													
													populateGridListaPresenca();
												}
							});
						}
			});
        	
        	final String strAula =arrayColumns.get(column); 
        	final int idAula = arrayAula.get(column).getIdAula();
        	
			Header<String> header = new Header<String>(new ClickableTextCell()) {
				
				@Override
				public String getValue() {
					return  strAula;
				}
				
				@Override
				public void render(Context context,SafeHtmlBuilder sb){
//					if (value != null) {
				          sb.appendHtmlConstant(
				          		"<span style='position: relative; top: -4px; padding-left: 5px; padding-right: 3px; vertical-align: middle; color: black;'>" +
				          		strAula+
				          		"</span>&nbsp;&nbsp;&nbsp;"+
				          		"<img src='images/delete.png'>"); 

//				        }
				}

				@Override
				public void onBrowserEvent(Context context, Element elem,NativeEvent event) {
					// add your popup
					System.out.println("Clicked");
//					Window.alert("Clicked");
					
					switch (DOM.eventGetType((Event) event)) {
					case Event.ONCLICK:
						@SuppressWarnings("unused")
						CloseHandler<PopupPanel> closeHandler;
						MpConfirmDialogBox confirmationDialog = new MpConfirmDialogBox(
								txtConstants.cursoRemoverTitle(),txtConstants.presencaRemoverMsg(strAula), txtConstants.geralSim(), txtConstants.geralNao(),

								closeHandler = new CloseHandler<PopupPanel>() {

									public void onClose(CloseEvent<PopupPanel> event) {

										MpConfirmDialogBox x = (MpConfirmDialogBox) event.getSource();

										if (x.primaryActionFired()) {

											GWTServiceAula.Util.getInstance().deleteAula(idAula, new AsyncCallback<Boolean>() {
												@Override
												public void onFailure(Throwable caught) {
													mpPanelLoadingAluno.setVisible(false);
													mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
													mpDialogBoxWarning.setBodyText(txtConstants.presencaErroAtualizar());
												}

												@Override
												public void onSuccess(Boolean success) {
													mpPanelLoadingAluno.setVisible(true);
													populateDinamicColumns();
												}});
//											System.out.println("IdAula!!!"+idAula);
//											System.out.println("AulaData!!!"+strAula);

										}
									}
								}
						);
						confirmationDialog.paint();
						break;

					default:
						Window.alert("Test default");
						break;
					}
					
				}
			};   	
			
			
			header.setHeaderStyleNames("hand-over");


//        	cellTable.addColumn(indexedColumn,header, new TextHeader(arrayColumns.get(column)));
			
			cellTable.addColumn(indexedColumn,header);
//			cellTable.addColumn(indexedColumn,new TextHeader(arrayColumns.get(column)),header);
        	
        }      
	
		
		Label lblAluno = new Label(txtConstants.aluno());
		MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
		if (txtSearch == null) {
			txtSearch = new TextBox();
			txtSearch.setStyleName("design_text_boxes");
		}
			
		
		txtSearch.addKeyUpHandler(new EnterKeyUpHandler());
		btnFiltrar.addClickHandler(new ClickHandlerFiltrar());
		
		
		
		FlexTable flexTableFiltrarAluno = new FlexTable();	
		flexTableFiltrarAluno.setCellSpacing(3);
		flexTableFiltrarAluno.setCellPadding(3);
		flexTableFiltrarAluno.setBorderWidth(0);		
		flexTableFiltrarAluno.setWidget(0, 0, mpPager);
		flexTableFiltrarAluno.setWidget(0, 1, new MpSpaceVerticalPanel());
		flexTableFiltrarAluno.setWidget(0, 2, lblAluno);
		flexTableFiltrarAluno.setWidget(0, 3, txtSearch);
		flexTableFiltrarAluno.setWidget(0, 4, btnFiltrar);
//		flexTableFiltrarAluno.setWidget(0, 5, mpPanelLoadingAluno);	
		
//		VerticalPanel vPanel = new VerticalPanel();
//		vPanel.setBorderWidth(1);
//		vPanel.setWidth("100%");
//		vPanel.setCellVerticalAlignment(cellTable, ALIGN_TOP);
//		vPanel.add(cellTable);
		
//		MpSpaceVerticalPanel mpSpaceVerticalPanel = new MpSpaceVerticalPanel();
		
		VerticalPanel vPanelInScroll = new VerticalPanel();
		vPanelInScroll.setBorderWidth(0);
		vPanelInScroll.setCellVerticalAlignment(cellTable, ALIGN_TOP);
		vPanelInScroll.add(flexTableFiltrarAluno);
		vPanelInScroll.add(cellTable);

		scrollPanel.clear();
		scrollPanel.add(vPanelInScroll);
		
		vFormPanel.add(scrollPanel);


		
		mpPanelLoadingAluno.setVisible(false);
		
		filtrarCellTable(txtSearch.getText());
		
		cellTable.setPageStart(cellTablePageIndex);
	}	
	

	
	protected void cleanCellTable() {
		if (cellTable != null) {
//			arrayColumns.clear();
//			arrayAula.clear();
//			arrayListBackup.clear();
//			dataProvider.getList().clear();
			dataProvider=null;
			scrollPanel.clear();
			cellTable.setRowCount(0);
			cellTable.redraw();
		}
	}	
	
/***************************************BEGIN CellTable Functions***************************************/
/*******************************************************************************************************/
/*******************************************************************************************************/	
/*******************************************************************************************************/
/***************************************BEGIN Filterting CellTable***************************************/	
	
	public void filtrarCellTable(String strFiltro) {
		
		removeCellTableFilter();

		strFiltro = strFiltro.toUpperCase();

		if (!strFiltro.isEmpty()) {

			int i = 0;
			while (i < dataProvider.getList().size()) {

				String strPrimeiroNome = dataProvider.getList().get(i).get(INT_POSITION_NAME);				

				String strJuntaTexto = strPrimeiroNome.toUpperCase();

				if (!strJuntaTexto.contains(strFiltro)) {
					dataProvider.getList().remove(i);
					i = 0;
					continue;
				}

				i++;
			}

		}

	}
	
	public void removeCellTableFilter(){
		
		dataProvider.getList().clear();

		for (int i = 0; i < arrayListBackup.size(); i++) {
			dataProvider.getList().add(arrayListBackup.get(i));
		}
		cellTable.setPageStart(0);
	}
/***************************************BEGIN Filterting CellTable***************************************/
		
	
	class IndexedColumn extends Column<ArrayList<String>, String> {
		private int index;
		private String teste; 
		public IndexedColumn(int index) {
			super(new TextCell());
			this.index = index;
			teste="1";
		}
		
		public IndexedColumn(MpStyledSelectionCellPresenca mpSel, int index) {
//			super(new EditTextCell());
			super(mpSel);
			this.index = index;
			teste="2";
		}
		
		public int getIndex(){
			return this.index;
		}

		@Override
		public String getValue(ArrayList<String> object) {
			String strTest="";
			try{
				strTest = object.get(this.index);
			}catch(Exception ex){
				System.out.println("Teste:"+teste);
				System.out.println(ex.getMessage());
			}
			
			return strTest;
		}
		
	}	

}

