package com.jornada.client.classes.hierarquia;

import java.util.ArrayList;
import java.util.Comparator;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.curso.MpListBoxAno;
import com.jornada.client.classes.listBoxes.ambiente.coordenador.curso.MpListBoxEnsino;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.config.ConfigClient;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.utility.MpUtilClient;

public class MpScrollPanelCurso extends ScrollPanel{
	
	protected static TextConstants txtConstants = GWT.create(TextConstants.class);
	protected static ConfigClient configClient = GWT.create(ConfigClient.class);

	private CellTable<Usuario> cellTable;
	private TextBox txtSearch;
	private ListDataProvider<Usuario> dataProvider = new ListDataProvider<Usuario>();
	private ArrayList<Usuario> arrayListBackup = new ArrayList<Usuario>();
	

	public MpScrollPanelCurso(Curso object){
		
		boolean showEmail = Boolean.parseBoolean(configClient.hierarquiaShowCursoEmail());
		boolean showDataNasc = Boolean.parseBoolean(configClient.hierarquiaShowCursoDataNascimento());
		boolean showTelCelular = Boolean.parseBoolean(configClient.hierarquiaShowCursoTelCelular());
		boolean showTelResidencial = Boolean.parseBoolean(configClient.hierarquiaShowCursoTelResidencial());
		
		
		Label lblEnsino = new Label("Ensino"); 
		Label lblAno = new Label("Ano"); 
		Label lblNomeCurso = new Label(txtConstants.cursoNome());				
		Label lblDescricaoCurso = new Label(txtConstants.cursoDescricao());				
		Label lblEmentaCurso = new Label(txtConstants.cursoEmenta());			
		Label lblMediaNotaCurso = new Label(txtConstants.cursoMediaNota());
		Label lblPorcentagemPresencaCurso = new Label(txtConstants.cursoPorcentagemPresenca());		
		Label lblDataInicialCurso = new Label(txtConstants.cursoDataInicial());			
		Label lblDataFinalCurso = new Label(txtConstants.cursoDataFinal());			
		Label lblAlunosCurso = new Label(txtConstants.cursoAlunosDoCurso());		
		
		
		MpListBoxEnsino listBoxEnsino = new MpListBoxEnsino();
		listBoxEnsino.setSelectItem(object.getEnsino());
		
		MpListBoxAno listBoxAno = new MpListBoxAno();
		listBoxAno.showItems(object.getEnsino());
		listBoxAno.setSelectItem(object.getAno());
		
		
        Label lblEnsinoDB = new Label(listBoxEnsino.getSelectedItemText()); 
        Label lblAnoDB = new Label(listBoxAno.getSelectedItemText());
		Label lblNomeCursoDB = new Label(object.getNome());
		Label lblDescricaoCursoDB = new Label(object.getDescricao());	
		Label lblEmentaCursoDB = new Label(object.getEmenta());		
		Label lblMediaNotaCursoDB = new Label(object.getMediaNota());
		Label lblPorcentagemPresencaCursoDB = new Label(object.getPorcentagemPresenca()+"%");		
		
		Label lblDataInicialCursoDB = new Label(MpUtilClient.convertDateToString(object.getDataInicial()));	
		Label lblDataFinalCursoDB = new Label(MpUtilClient.convertDateToString(object.getDataFinal()));		
		
        lblEnsino.setStyleName("label_comum_bold_12px");
        lblEnsinoDB.setStyleName("design_label");
        lblAno.setStyleName("label_comum_bold_12px");
        lblAnoDB.setStyleName("design_label");
        lblNomeCurso.setStyleName("label_comum_bold_12px");
		lblNomeCursoDB.setStyleName("design_label");
		lblDescricaoCurso.setStyleName("label_comum_bold_12px");
		lblDescricaoCursoDB.setStyleName("label_comum");		
		lblEmentaCurso.setStyleName("label_comum_bold_12px");
		lblEmentaCursoDB.setStyleName("label_comum");	
		lblMediaNotaCurso.setStyleName("label_comum_bold_12px");
		lblMediaNotaCursoDB.setStyleName("label_comum");
		lblPorcentagemPresencaCurso.setStyleName("label_comum_bold_12px");
		lblPorcentagemPresencaCursoDB.setStyleName("label_comum");		
		lblDataInicialCurso.setStyleName("label_comum_bold_12px");
		lblDataInicialCursoDB.setStyleName("label_comum");	
		lblDataFinalCurso.setStyleName("label_comum_bold_12px");
		lblDataFinalCursoDB.setStyleName("design_label");		
		lblAlunosCurso.setStyleName("label_comum_bold_12px");
		
		
		
		Grid gridEnsino = new Grid(1,10);       
		gridEnsino.setCellPadding(0);
		gridEnsino.setCellSpacing(0);
		gridEnsino.setBorderWidth(0);     
        int column=0;
        gridEnsino.setWidget(0, column++, lblEnsino);
        gridEnsino.setWidget(0, column++, new MpSpaceVerticalPanel());
        gridEnsino.setWidget(0, column++, lblEnsinoDB);
        gridEnsino.setWidget(0, column++, new MpSpaceVerticalPanel());
        gridEnsino.setWidget(0, column++, new MpSpaceVerticalPanel());
        gridEnsino.setWidget(0, column++, new MpSpaceVerticalPanel());
        gridEnsino.setWidget(0, column++, lblAno);
        gridEnsino.setWidget(0, column++, new MpSpaceVerticalPanel());
        gridEnsino.setWidget(0, column++, lblAnoDB);
		
		
		Grid gridData = new Grid(1,10);		
		gridData.setCellPadding(0);
		gridData.setCellSpacing(0);
		gridData.setBorderWidth(0);		
		column=0;
		gridData.setWidget(0, column++, lblDataInicialCurso);
		gridData.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridData.setWidget(0, column++, lblDataInicialCursoDB);
		gridData.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridData.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridData.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridData.setWidget(0, column++, lblDataFinalCurso);
		gridData.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridData.setWidget(0, column++, lblDataFinalCursoDB);
		
		
		Grid gridMediaPresenca = new Grid(1,10);		
		gridMediaPresenca.setCellPadding(0);
		gridMediaPresenca.setCellSpacing(0);
		gridMediaPresenca.setBorderWidth(0);		
		column=0;
		gridMediaPresenca.setWidget(0, column++, lblMediaNotaCurso);
		gridMediaPresenca.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridMediaPresenca.setWidget(0, column++, lblMediaNotaCursoDB);
		gridMediaPresenca.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridMediaPresenca.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridMediaPresenca.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridMediaPresenca.setWidget(0, column++, lblPorcentagemPresencaCurso);
		gridMediaPresenca.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridMediaPresenca.setWidget(0, column++, lblPorcentagemPresencaCursoDB);
		
		

		
    	FlexTable flexTableConteudo = new FlexTable();
    	flexTableConteudo.setWidth("100%");
		flexTableConteudo.setCellPadding(2);
		flexTableConteudo.setCellSpacing(2);	
		flexTableConteudo.setBorderWidth(0);
		int row=0;
		flexTableConteudo.setWidget(row++, 0, lblDescricaoCurso);
		flexTableConteudo.setWidget(row++, 0, lblDescricaoCursoDB);
		flexTableConteudo.setWidget(row++, 0, new MpSpaceVerticalPanel());
		flexTableConteudo.setWidget(row++, 0, lblEmentaCurso);
        flexTableConteudo.setWidget(row++, 0, lblEmentaCursoDB);
        flexTableConteudo.setWidget(row++, 0, new MpSpaceVerticalPanel());
        flexTableConteudo.setWidget(row++, 0, gridEnsino);
        flexTableConteudo.setWidget(row++, 0, new MpSpaceVerticalPanel());
		flexTableConteudo.setWidget(row++, 0, gridMediaPresenca);
		flexTableConteudo.setWidget(row++, 0, new MpSpaceVerticalPanel());				
		flexTableConteudo.setWidget(row++, 0, gridData);
		
//		flexTableConteudo.setWidget(row++, 0, new InlineHTML("&nbsp;"));
		flexTableConteudo.setWidget(row++, 0, lblAlunosCurso);
		
		
		cellTable = new CellTable<Usuario>(10,GWT.<CellTableStyle> create(CellTableStyle.class));

//		cellTable.setWidth(Integer.toString(MpHierarquiaCurso.intWidthTable-250)+ "px");
		cellTable.setWidth("100%");
//        cellTable.setPageSize(10);
        
		dataProvider.addDataDisplay(cellTable);
		
		dataProvider.getList().clear();
		
		for(int i=0;i<object.getListAlunos().size();i++){
			dataProvider.getList().add(object.getListAlunos().get(i));
			arrayListBackup.add(object.getListAlunos().get(i));
			
		}
		
		ListHandler<Usuario> sortHandler = new ListHandler<Usuario>(dataProvider.getList());  
		cellTable.addColumnSortHandler(sortHandler);	
	    
	    MpSimplePager mpPager = new MpSimplePager();
		mpPager.setDisplay(cellTable);
//		mpPager.setPageSize(10);
		
		
		MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
		
		if (txtSearch == null) {
			txtSearch = new TextBox();
			txtSearch.setStyleName("design_text_boxes");
		}
		
		txtSearch.addKeyUpHandler(new EnterKeyUpHandler());
		btnFiltrar.addClickHandler(new ClickHandlerFiltrar());
		
		FlexTable flexTableFiltrar = new FlexTable();	
		flexTableFiltrar.setBorderWidth(2);
		flexTableFiltrar.setCellSpacing(3);
		flexTableFiltrar.setCellPadding(3);
		flexTableFiltrar.setBorderWidth(0);		
		flexTableFiltrar.setWidget(0, 0, mpPager);
		flexTableFiltrar.setWidget(0, 1, new MpSpaceVerticalPanel());
		flexTableFiltrar.setWidget(0, 2, txtSearch);
		flexTableFiltrar.setWidget(0, 3, btnFiltrar);
		
		
		
		Column<Usuario, String> columnPrimeiroNome = new Column<Usuario, String>(new TextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getPrimeiroNome();
			}
		};		
		columnPrimeiroNome.setSortable(true);
		sortHandler.setComparator(columnPrimeiroNome, new Comparator<Usuario>() {
	      @Override
	      public int compare(Usuario o1, Usuario o2) {
	        return o1.getPrimeiroNome().compareTo(o2.getPrimeiroNome());
	      }
	    });			

		Column<Usuario, String> columnSobreNome = new Column<Usuario, String>(new TextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getSobreNome();
			}
		};
		columnSobreNome.setSortable(true);
		sortHandler.setComparator(columnSobreNome, new Comparator<Usuario>() {
	      @Override
	      public int compare(Usuario o1, Usuario o2) {
	        return o1.getSobreNome().compareTo(o2.getSobreNome());
	      }
	    });		
		
//		Column<Usuario, String> columnCPF = new Column<Usuario, String>(new TextCell()) {
//			@Override
//			public String getValue(Usuario object) {
//				return object.getCpf();
//			}
//		};		
		Column<Usuario, String> columnEmail = new Column<Usuario, String>(new TextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getEmail();
			}
		};		
		columnEmail.setSortable(true);
		sortHandler.setComparator(columnEmail, new Comparator<Usuario>() {
	      @Override
	      public int compare(Usuario o1, Usuario o2) {
	        return o1.getEmail().compareTo(o2.getEmail());
	      }
	    });			
		
		Column<Usuario, String> columnDataNascimento = new Column<Usuario, String>(new TextCell()) {
			@Override
			public String getValue(Usuario object) {
				return MpUtilClient.convertDateToString(object.getDataNascimento());
			}
		};	
		columnDataNascimento.setSortable(true);
		sortHandler.setComparator(columnDataNascimento, new Comparator<Usuario>() {
	      @Override
	      public int compare(Usuario o1, Usuario o2) {
	        return o1.getDataNascimento().compareTo(o2.getDataNascimento());
	      }
	    });					
		
		Column<Usuario, String> columnTelefoneCelular = new Column<Usuario, String>(new TextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getTelefoneCelular();
			}
		};
		columnTelefoneCelular.setSortable(true);
		sortHandler.setComparator(columnTelefoneCelular, new Comparator<Usuario>() {
	      @Override
	      public int compare(Usuario o1, Usuario o2) {
	        return o1.getTelefoneCelular().compareTo(o2.getTelefoneCelular());
	      }
	    });					
		
		
		Column<Usuario, String> columnTelefoneResidencial = new Column<Usuario, String>(
				new EditTextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getTelefoneResidencial();
			}
		};	
		columnTelefoneResidencial.setSortable(true);
		sortHandler.setComparator(columnTelefoneResidencial, new Comparator<Usuario>() {
	      @Override
	      public int compare(Usuario o1, Usuario o2) {
	        return o1.getTelefoneResidencial().compareTo(o2.getTelefoneResidencial());
	      }
	    });							
		
		cellTable.addColumn(columnPrimeiroNome, txtConstants.usuarioPrimeiroNome());
		cellTable.getColumn(cellTable.getColumnIndex(columnPrimeiroNome)).setCellStyleNames("hand-over-default");
		
		cellTable.addColumn(columnSobreNome, txtConstants.usuarioSobreNome());
		cellTable.getColumn(cellTable.getColumnIndex(columnSobreNome)).setCellStyleNames("hand-over-default");
		
		if(showEmail){
			cellTable.addColumn(columnEmail, txtConstants.usuarioEmail());
			cellTable.getColumn(cellTable.getColumnIndex(columnEmail)).setCellStyleNames("hand-over-default");
		}
		
		if(showDataNasc){
			cellTable.addColumn(columnDataNascimento, txtConstants.usuarioDataNascimento());
			cellTable.getColumn(cellTable.getColumnIndex(columnDataNascimento)).setCellStyleNames("hand-over-default");
		}
		
		if(showTelCelular){
			cellTable.addColumn(columnTelefoneCelular, txtConstants.usuarioTelCelular());
			cellTable.getColumn(cellTable.getColumnIndex(columnTelefoneCelular)).setCellStyleNames("hand-over-default");
		}

		if(showTelResidencial){
			cellTable.addColumn(columnTelefoneResidencial, txtConstants.usuarioTelResidencial());
			cellTable.getColumn(cellTable.getColumnIndex(columnTelefoneResidencial)).setCellStyleNames("hand-over-default");
		}
	
		
//		Grid gridAlunos = new Grid(2,1);
//		gridAlunos.setBorderWidth(0);
//		gridAlunos.setCellPadding(0);
//		gridAlunos.setCellSpacing(0);
		
//		gridAlunos.setWidget(0, 0, mpPager);
//		gridAlunos.setWidget(0, 0, flexTableFiltrar);
//		gridAlunos.setWidget(1, 0, cellTable);
		
		flexTableConteudo.setWidget(row++, 0, flexTableFiltrar);
		flexTableConteudo.setWidget(row, 0, cellTable);
//		flexTableConteudo.setWidget(row, 0, gridAlunos);
		flexTableConteudo.getFlexCellFormatter().setColSpan(row++, 0, 4);

		
//		panelDetalhes.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);	
		
		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(MpHierarquiaCurso.intWidthTable-200)+"px",Integer.toString(MpHierarquiaCurso.intHeightTable-30)+"px");
		scrollPanel.setHeight(Integer.toString(MpHierarquiaCurso.intHeightTable-30)+"px");
		scrollPanel.setWidth("100%");
		scrollPanel.setAlwaysShowScrollBars(false);				
		scrollPanel.add(flexTableConteudo);	
		
		this.setWidth("100%");
		this.add(scrollPanel);
		
	}
	
	/*******************************************************************************************************/	
	/*******************************************************************************************************/
	/***************************************BEGIN Filterting CellTable***************************************/
	
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
		
		public void filtrarCellTable(String strFiltro) {
			
			removeCellTableFilter();

			strFiltro = strFiltro.toUpperCase();

			if (!strFiltro.isEmpty()) {

				int i = 0;
				while (i < dataProvider.getList().size()) {

					String strPrimeiroNome = dataProvider.getList().get(i).getPrimeiroNome();			
					String strSobreNome = dataProvider.getList().get(i).getSobreNome();
					String strEmail = dataProvider.getList().get(i).getEmail();
					String strDataNascimento = MpUtilClient.convertDateToString(dataProvider.getList().get(i).getDataNascimento(), "EEEE, MMMM dd, yyyy");
					String strTelefoneCelular = dataProvider.getList().get(i).getTelefoneCelular();
					String strTelefoneResidencial = dataProvider.getList().get(i).getTelefoneResidencial();

					String strJuntaTexto = strPrimeiroNome.toUpperCase() + " " + strSobreNome.toUpperCase() + " " + strEmail.toUpperCase();
					strJuntaTexto +=  " " + strDataNascimento.toUpperCase() + " " + strTelefoneCelular.toUpperCase() + " " + strTelefoneResidencial.toUpperCase();

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

}
