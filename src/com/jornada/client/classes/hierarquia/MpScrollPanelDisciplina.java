package com.jornada.client.classes.hierarquia;

import java.util.ArrayList;
import java.util.Comparator;

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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.label.MpLabelLeft;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.shared.classes.Avaliacao;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.utility.MpUtilClient;

public class MpScrollPanelDisciplina extends ScrollPanel{
	
	protected static TextConstants txtConstants = GWT.create(TextConstants.class);
	
    private CellTable<Avaliacao> cellTable;
    private TextBox txtSearch;
    private ListDataProvider<Avaliacao> dataProvider = new ListDataProvider<Avaliacao>();
    private ArrayList<Avaliacao> arrayListBackup = new ArrayList<Avaliacao>();
	
	
	public MpScrollPanelDisciplina(Disciplina object){
		
		MpLabelLeft lblNome = new MpLabelLeft(txtConstants.disciplinaNome());	
		Label lblNomeDB = new Label(object.getNome());
		
		MpLabelLeft lblCargaHorario = new MpLabelLeft(txtConstants.disciplinaCarga());	
		Label lblCargaHorarioDB = new Label(Integer.toString(object.getCargaHoraria()));				
		
		MpLabelLeft lblDescricao = new MpLabelLeft(txtConstants.disciplinaDescricao());	
		Label lblDescricaoDB = new Label(object.getDescricao());	
		
		MpLabelLeft lblObjetivo = new MpLabelLeft(txtConstants.disciplinaObjetivo());	
		Label lblObjetivoDB = new Label(object.getObjetivo());			
		
		MpLabelLeft lblProfessor = new MpLabelLeft(txtConstants.professor());
		String strProfessorNome = object.getProfessor()==null? "" : object.getProfessor().getPrimeiroNome();
		String strProfessorSobreNome = object.getProfessor()==null? "" : object.getProfessor().getSobreNome();
		Label lblProfessorDB = new Label(strProfessorNome + " " + strProfessorSobreNome);
		
		MpLabelLeft lblAvaliacao = new MpLabelLeft(txtConstants.avaliacao());
		
		lblNome.setStyleName("label_comum_bold_12px");
		lblNomeDB.setStyleName("label_comum");
		lblCargaHorario.setStyleName("label_comum_bold_12px");
		lblCargaHorarioDB.setStyleName("label_comum");	
		lblDescricao.setStyleName("label_comum_bold_12px");
		lblDescricaoDB.setStyleName("label_comum");		
		lblObjetivo.setStyleName("label_comum_bold_12px");
		lblObjetivoDB.setStyleName("label_comum");	
		lblProfessor.setStyleName("label_comum_bold_12px");
		lblProfessorDB.setStyleName("label_comum");			
		lblAvaliacao.setStyleName("label_comum_bold_12px");
		
		
		Grid gridCargaHorario = new Grid(1,10);		
		gridCargaHorario.setCellPadding(0);
		gridCargaHorario.setCellSpacing(0);
		gridCargaHorario.setBorderWidth(0);		
		int column=0;
		gridCargaHorario.setWidget(0, column++, lblCargaHorario);
		gridCargaHorario.setWidget(0, column++, new MpSpaceVerticalPanel());
		gridCargaHorario.setWidget(0, column++, lblCargaHorarioDB);
		
	
		FlexTable flexTableConteudo = new FlexTable();
		flexTableConteudo.setCellPadding(2);
		flexTableConteudo.setCellSpacing(2);	
		flexTableConteudo.setWidth("100%");
//		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
//		flexTableConteudo.getColumnFormatter().setWidth(0, "150px");		
		
		Label lblEmpty = new Label(txtConstants.avaliacaoNenhumaDisciplina());
		
        cellTable = new CellTable<Avaliacao>(10,GWT.<CellTableStyle> create(CellTableStyle.class));
        cellTable.setEmptyTableWidget(lblEmpty);
        cellTable.setWidth("100%");
        
        dataProvider.addDataDisplay(cellTable);
        
        dataProvider.getList().clear();
        arrayListBackup.clear();

        for (int i = 0; i < object.getListAvaliacao().size(); i++) {
            dataProvider.getList().add(object.getListAvaliacao().get(i));
            arrayListBackup.add(object.getListAvaliacao().get(i));
        }
        
        ListHandler<Avaliacao> sortHandler = new ListHandler<Avaliacao>(dataProvider.getList());  
        cellTable.addColumnSortHandler(sortHandler);            

        MpSimplePager mpPager = new MpSimplePager();
        mpPager.setDisplay(cellTable);
//      mpPager.setPageSize(10);
        
        
        MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
        
        if (txtSearch == null) {
            txtSearch = new TextBox();
            txtSearch.setStyleName("design_text_boxes");
        }
        
        txtSearch.addKeyUpHandler(new EnterKeyUpHandler());
        btnFiltrar.addClickHandler(new ClickHandlerFiltrar());
        
        FlexTable flexTableFiltrar = new FlexTable();   
        flexTableFiltrar.setCellSpacing(1);
        flexTableFiltrar.setCellPadding(1);
        flexTableFiltrar.setBorderWidth(0);     
        flexTableFiltrar.setWidget(0, 0, mpPager);
        flexTableFiltrar.setWidget(0, 1, new MpSpaceVerticalPanel());
        flexTableFiltrar.setWidget(0, 2, txtSearch);
        flexTableFiltrar.setWidget(0, 3, btnFiltrar);   

//      ListDataProvider<Avaliacao> dataProvider = new ListDataProvider<Avaliacao>();

        Column<Avaliacao, String> assuntoColumn = new Column<Avaliacao, String>(
                new TextCell()) {
            @Override
            public String getValue(Avaliacao object) {
                return object.getAssunto();
            }
        };
        assuntoColumn.setSortable(true);
        sortHandler.setComparator(assuntoColumn, new Comparator<Avaliacao>() {
          @Override
          public int compare(Avaliacao o1, Avaliacao o2) {
            return o1.getAssunto().compareTo(o2.getAssunto());
          }
        });     
        Column<Avaliacao, String> descricaoColumn = new Column<Avaliacao, String>(
                new TextCell()) {
            @Override
            public String getValue(Avaliacao object) {
                return object.getDescricao();
            }
        };
        descricaoColumn.setSortable(true);
        sortHandler.setComparator(descricaoColumn, new Comparator<Avaliacao>() {
          @Override
          public int compare(Avaliacao o1, Avaliacao o2) {
            return o1.getDescricao().compareTo(o2.getDescricao());
          }
        });         
        Column<Avaliacao, String> columnTipoAvaliacao = new Column<Avaliacao, String>(new TextCell()) {
            @Override
            public String getValue(Avaliacao object) {
                return object.getTipoAvaliacao().getNomeTipoAvaliacao();
            }
        };
        columnTipoAvaliacao.setSortable(true);
        sortHandler.setComparator(columnTipoAvaliacao, new Comparator<Avaliacao>() {
          @Override
          public int compare(Avaliacao o1, Avaliacao o2) {
            return o1.getTipoAvaliacao().getNomeTipoAvaliacao().compareTo(o2.getTipoAvaliacao().getNomeTipoAvaliacao());
          }
        });         
        
        Column<Avaliacao, String> dataColumn = new Column<Avaliacao, String>(new TextCell()) {
            @Override
            public String getValue(Avaliacao object) {
                //return object.getData();
                return MpUtilClient.convertDateToString(object.getData());
            }
        };
        dataColumn.setSortable(true);
        sortHandler.setComparator(dataColumn, new Comparator<Avaliacao>() {
          @Override
          public int compare(Avaliacao o1, Avaliacao o2) {
            return o1.getData().compareTo(o2.getData());
          }
        });         
        
        Column<Avaliacao, String> horaColumn = new Column<Avaliacao, String>(
                new TextCell()) {
            @Override
            public String getValue(Avaliacao object) {
                return object.getHora();
            }
        };
        horaColumn.setSortable(true);
        sortHandler.setComparator(horaColumn, new Comparator<Avaliacao>() {
          @Override
          public int compare(Avaliacao o1, Avaliacao o2) {
            return o1.getHora().compareTo(o2.getHora());
          }
        });                 

        cellTable.addColumn(assuntoColumn, txtConstants.avaliacaoAssunto());
        cellTable.addColumn(descricaoColumn, txtConstants.avaliacaoDescricao());
        cellTable.addColumn(columnTipoAvaliacao, txtConstants.avaliacaoTipo());
        cellTable.addColumn(dataColumn, txtConstants.avaliacaoData());
        cellTable.addColumn(horaColumn, txtConstants.avaliacaoHora());

        cellTable.getColumn(cellTable.getColumnIndex(assuntoColumn)).setCellStyleNames("hand-over-default");
        cellTable.getColumn(cellTable.getColumnIndex(descricaoColumn)).setCellStyleNames("hand-over-default");
        cellTable.getColumn(cellTable.getColumnIndex(columnTipoAvaliacao)).setCellStyleNames("hand-over-default");
        cellTable.getColumn(cellTable.getColumnIndex(dataColumn)).setCellStyleNames("hand-over-default");
        cellTable.getColumn(cellTable.getColumnIndex(horaColumn)).setCellStyleNames("hand-over-default");
		
		
		
		
		
		int row=0;
//		flexTableConteudo.setWidget(row++, 0, vPanelTitle);
//		flexTable.setWidget(row, 0, lblNome);flexTable.setWidget(row++, 1, lblNomeDB);
//		flexTableConteudo.setWidget(row++, 0, lblCargaHorario);
//		flexTableConteudo.setWidget(row++, 0, lblCargaHorarioDB);	
		flexTableConteudo.setWidget(row++, 0, gridCargaHorario);	
		flexTableConteudo.setWidget(row++, 0, new MpSpaceVerticalPanel());
		flexTableConteudo.setWidget(row++, 0, lblDescricao);
		flexTableConteudo.setWidget(row++, 0, lblDescricaoDB);
		flexTableConteudo.setWidget(row++, 0, new MpSpaceVerticalPanel());
		flexTableConteudo.setWidget(row++, 0, lblObjetivo);
		flexTableConteudo.setWidget(row++, 0, lblObjetivoDB);
		flexTableConteudo.setWidget(row++, 0, new MpSpaceVerticalPanel());
		flexTableConteudo.setWidget(row++, 0, lblProfessor);
		flexTableConteudo.setWidget(row++, 0, lblProfessorDB);
        flexTableConteudo.setWidget(row++, 0, new MpSpaceVerticalPanel());	
        flexTableConteudo.setWidget(row++, 0, lblAvaliacao);
        flexTableConteudo.setWidget(row++, 0, flexTableFiltrar);
        flexTableConteudo.setWidget(row, 0, cellTable);
        flexTableConteudo.getFlexCellFormatter().setColSpan(row++, 0, 2);
		


		
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setWidth("100%");
//		scrollPanel.setSize(Integer.toString(MpHierarquiaCurso.intWidthTable-200)+"px",Integer.toString(MpHierarquiaCurso.intHeightTable-30)+"px");
		scrollPanel.setHeight(Integer.toString(MpHierarquiaCurso.intHeightTable-30)+"px");
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

                    String strAssunto = dataProvider.getList().get(i).getAssunto();         
                    String strDescricao = dataProvider.getList().get(i).getDescricao();
                    String strNomeTipoAvaliacao = dataProvider.getList().get(i).getTipoAvaliacao().getNomeTipoAvaliacao();
                    String strData = MpUtilClient.convertDateToString(dataProvider.getList().get(i).getData(), "EEEE, MMMM dd, yyyy");
                    String strHora = dataProvider.getList().get(i).getHora();
                    
                    String strJuntaTexto = strAssunto.toUpperCase() + " " + strDescricao.toUpperCase() + " " + strNomeTipoAvaliacao.toUpperCase();
                    strJuntaTexto +=  " " + strData.toUpperCase() + " " + strHora.toUpperCase();

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


}
