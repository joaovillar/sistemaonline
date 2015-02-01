package com.jornada.client.ambiente.general.nota;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import com.google.gwt.cell.client.Cell.Context;
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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.ambiente.professor.avaliacao.TelaInicialAvaliacao;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpDatePickerCell;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelLeft;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceAvaliacao;
import com.jornada.shared.classes.TipoAvaliacao;
import com.jornada.shared.classes.boletim.AvaliacaoNota;
import com.jornada.shared.classes.utility.MpUtilClient;

public class DialogBoxNota extends DecoratedPopupPanel {
	
	TextConstants txtConstants = GWT.create(TextConstants.class);
	
	protected VerticalPanel vBody;
	
    private CellTable<AvaliacaoNota> cellTable;
    private Column<AvaliacaoNota, String> notaColumn;
    private Column<AvaliacaoNota, String> pesoNotaColumn;
    private Column<AvaliacaoNota, String> assuntoColumn;
    private Column<AvaliacaoNota, String> descricaoColumn;
    private Column<AvaliacaoNota, String> columnTipoAvaliacao;
    private Column<AvaliacaoNota, Date> dataColumn;
    private Column<AvaliacaoNota, String> horaColumn;	
    private ListDataProvider<AvaliacaoNota> dataProvider = new ListDataProvider<AvaliacaoNota>();   
	
	MpPanelLoading mpLoading = new MpPanelLoading("images/radar.gif");

//	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
	
	private int idUsuario;
	private int idCurso;
	private String strNomeCurso;
	private String strNomeDisciplina;
	private String strNomePeriodo;
	private Double mediaNotaCurso;
	
	
	private static DialogBoxNota uniqueInstance;
	
	public static DialogBoxNota getInstance(int idUsuario, int idCurso, String strNomeCurso, String strNomeDisciplina, String strNomePeriodo, Double mediaNotaCurso){
		if(uniqueInstance==null){
			uniqueInstance = new DialogBoxNota();
			uniqueInstance.showDialog(idUsuario, idCurso, strNomeCurso, strNomeDisciplina, strNomePeriodo, mediaNotaCurso);
			uniqueInstance.populateGridAvaliacoes();
		}else{
		    uniqueInstance.vBody.clear();
			uniqueInstance.showDialog(idUsuario, idCurso, strNomeCurso, strNomeDisciplina, strNomePeriodo, mediaNotaCurso);
			uniqueInstance.populateGridAvaliacoes();
		}
		return uniqueInstance;
	}
	
	   public static DialogBoxNota getInstance(int idUsuario, int idCurso, String strNomeCurso, String strNomeDisciplina, String strNomePeriodo, int idAvaliacao, Double mediaNotaCurso){
	        if(uniqueInstance==null){
	            uniqueInstance = new DialogBoxNota();
	            uniqueInstance.showDialog(idUsuario, idCurso, strNomeCurso, strNomeDisciplina, strNomePeriodo, mediaNotaCurso);
	            uniqueInstance.populateGridAvaliacao(idAvaliacao);
	        }else{
	            uniqueInstance.vBody.clear();
	            uniqueInstance.showDialog(idUsuario, idCurso, strNomeCurso, strNomeDisciplina, strNomePeriodo, mediaNotaCurso);
	            uniqueInstance.populateGridAvaliacao(idAvaliacao);
	        }
	        return uniqueInstance;
	    }
	
	
	protected DialogBoxNota(){
		

		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpLoading.setTxtLoading("");
		mpLoading.show();
		mpLoading.setVisible(false);
		
		vBody = new VerticalPanel();
		vBody.setStyleName("dialogVPanelWhite");
		
		setWidget(vBody);
		super.setGlassEnabled(true);
		super.setAnimationEnabled(true);
		
	}
	
    protected void showDialog(int idUsuario, int idCurso, String strNomeCurso, String strNomeDisciplina, String strNomePeriodo, Double mediaNotaCurso) {
        
        this.idUsuario = idUsuario;
        this.idCurso = idCurso;
        this.strNomeCurso = strNomeCurso;
        this.strNomeDisciplina = strNomeDisciplina;
        this.strNomePeriodo = strNomePeriodo;
        this.mediaNotaCurso = mediaNotaCurso;
        
        MpImageButton btnFechar = new MpImageButton(txtConstants.geralFecharJanela(), "");
        btnFechar.addClickHandler(new ClickHandlerFechar());
        btnFechar.addKeyUpHandler(new EnterKeyUpHandler());
        btnFechar.setFocus(true);
        
        
        
        MpLabelLeft lblCurso = new MpLabelLeft(txtConstants.curso());
        MpLabelLeft lblDisciplina = new MpLabelLeft(txtConstants.disciplina());
        MpLabelLeft lblPeriodo = new MpLabelLeft(txtConstants.periodo());
        
        lblCurso.setStyleName("label_comum_bold_12px");
        lblDisciplina.setStyleName("label_comum_bold_12px");
        lblPeriodo.setStyleName("label_comum_bold_12px");
        
        MpLabelLeft lblNomeCurso = new MpLabelLeft(this.strNomeCurso);
        MpLabelLeft lblNomeDisciplina = new MpLabelLeft(this.strNomeDisciplina);
        MpLabelLeft lblNomePeriodo = new MpLabelLeft(this.strNomePeriodo);
        
        Grid gridListBox = new Grid(3,3);
        gridListBox.setCellPadding(2);
        gridListBox.setCellSpacing(2);

        int row=0;
        gridListBox.setWidget(row, 0, lblCurso);
        gridListBox.setWidget(row++, 1, lblNomeCurso);
        gridListBox.setWidget(row, 0, lblPeriodo);
        gridListBox.setWidget(row++, 1, lblNomePeriodo);
        gridListBox.setWidget(row, 0, lblDisciplina);
        gridListBox.setWidget(row, 1, lblNomeDisciplina);
//        gridListBox.setWidget(row++, 2, mpLoading);
        
        Grid gridBotoes = new Grid(1,1);
        row=0;
        gridBotoes.getCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
        gridBotoes.setWidth("100%");
        gridBotoes.setWidget(row, 0, btnFechar);
        
        
        vBody.add(gridListBox);
        vBody.add(renderCellTable());
        vBody.add(gridBotoes);  
        
        
        this.setWidth("800px");
        vBody.setWidth("100%");
        
        center();
        show();
        

    }	
    
    private VerticalPanel renderCellTable(){
        
        Label lblEmpty = new Label(txtConstants.avaliacaoNenhumaDisciplina());
//      Label lblEmpty2 = new Label("Por favor, selecione um Conteudo Programatico.");
        
        cellTable = new CellTable<AvaliacaoNota>(5,GWT.<CellTableStyle> create(CellTableStyle.class));
//      cellTable.setWidth(Integer.toString(TelaInicialAvaliacao.intWidthTable)+ "px");
        cellTable.setWidth("100%");
        cellTable.setAutoHeaderRefreshDisabled(true);
        cellTable.setAutoFooterRefreshDisabled(true);
        cellTable.setEmptyTableWidget(lblEmpty);

        // Add a selection model so we can select cells.
        final SelectionModel<AvaliacaoNota> selectionModel = new MultiSelectionModel<AvaliacaoNota>();
        cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<AvaliacaoNota> createCheckboxManager());
        initTableColumns(selectionModel);
        
        dataProvider.addDataDisplay(cellTable);
        
        MpSimplePager mpPager = new MpSimplePager();
        mpPager.setDisplay(cellTable);
        mpPager.setPageSize(15);
        
        Grid gridPager = new Grid(1,3);
        gridPager.setCellPadding(1);
        gridPager.setCellSpacing(1);
        
        gridPager.setWidget(0, 0, mpPager);
        gridPager.setWidget(0, 1, new InlineHTML("&nbsp;"));
        gridPager.setWidget(0, 2, mpLoading);
        
        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.setHeight(Integer.toString(TelaInicialAvaliacao.intHeightTable-180)+"px");
        scrollPanel.setAlwaysShowScrollBars(true);
        scrollPanel.setWidth("100%");
        scrollPanel.setAlwaysShowScrollBars(false);     
        scrollPanel.add(cellTable);
        
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setBorderWidth(1);
        vPanel.add(gridPager);
        vPanel.add(scrollPanel);
        vPanel.setWidth("100%");
        
        
        this.setWidth("100%");
        
        
        return vPanel;
        
    }
    
    
    protected void populateGridAvaliacoes() {

        mpLoading.setVisible(true);

        GWTServiceAvaliacao.Util.getInstance().getAvaliacaoNotaPeriodoDisciplinaSemRecuperacaoFinal(idUsuario, idCurso, strNomePeriodo, strNomeDisciplina, new CallbackAvaliacaoNota());

    }        
    
    
    protected void populateGridAvaliacao(int idPeriodo) {

        mpLoading.setVisible(true);

        GWTServiceAvaliacao.Util.getInstance().getAvaliacaoNotaPeriodoDisciplina(idUsuario, idCurso, strNomePeriodo, strNomeDisciplina, idPeriodo, new CallbackAvaliacaoNota());

    }  


    private void addCellTableData(ListDataProvider<AvaliacaoNota> dataProvider) {

        ListHandler<AvaliacaoNota> sortHandler = new ListHandler<AvaliacaoNota>(dataProvider.getList());

        cellTable.addColumnSortHandler(sortHandler);

        initSortHandler(sortHandler);

    }
    
    
    private void initTableColumns(final SelectionModel<AvaliacaoNota> selectionModel) {
        
        assuntoColumn = new Column<AvaliacaoNota, String>(new TextCell()) {
            @Override
            public String getValue(AvaliacaoNota object) {
                return object.getAssunto();
            }

        };
        descricaoColumn = new Column<AvaliacaoNota, String>(new TextCell()) {
            @Override
            public String getValue(AvaliacaoNota object) {
                return object.getDescricao();
            }
        };     
        
        columnTipoAvaliacao = new Column<AvaliacaoNota, String>(new TextCell()) {
          @Override
          public String getValue(AvaliacaoNota object) {
            return object.getTipoAvaliacao().getNomeTipoAvaliacao();
          }
        };
        
        pesoNotaColumn = new Column<AvaliacaoNota, String>(new TextCell()) {
            @Override
            public String getValue(AvaliacaoNota object) {
                return object.getPesoNota();
            }
        };          
        
        dataColumn = new Column<AvaliacaoNota, Date>(new MpDatePickerCell()) {
            @Override
            public Date getValue(AvaliacaoNota object) {
//              Date date = MpUtilClient.convertStringToDate(object.getData());
//              return date;
                return object.getData();
            }
        };
        
        horaColumn = new Column<AvaliacaoNota, String>(new TextCell()) {
          @Override
            public String getValue(AvaliacaoNota object) {
                return object.getHora();
            }
        };
        
        notaColumn = new Column<AvaliacaoNota, String>(new TextCell()) {
            @Override
              public String getValue(AvaliacaoNota object) {                  
                  return Double.toString(object.getNota());
              }
            @Override
            public String getCellStyleNames(Context context, AvaliacaoNota  object) {
                
                if(object.getIdTipoAvaliacao()==TipoAvaliacao.INT_ADICIONAL_NOTA){
                    return "table-boletim-cell-green-media";    
                }else if(object.getNota()<mediaNotaCurso){
                    return "table-boletim-cell-red-media";                    
                }else{
                    return "table-boletim-cell-green-media";
                }
                
              }  
          };        

        cellTable.addColumn(assuntoColumn, txtConstants.avaliacaoAssunto());
        cellTable.addColumn(descricaoColumn, txtConstants.avaliacaoDescricao());
        cellTable.addColumn(columnTipoAvaliacao, txtConstants.avaliacaoTipo());
        cellTable.addColumn(pesoNotaColumn, txtConstants.avaliacaoPesoNota());
        cellTable.addColumn(dataColumn, txtConstants.avaliacaoData());
        cellTable.addColumn(horaColumn, txtConstants.avaliacaoHora());
        cellTable.addColumn(notaColumn, txtConstants.nota());
       
        
    }
    
    public void initSortHandler(ListHandler<AvaliacaoNota> sortHandler) {

        assuntoColumn.setSortable(true);
        sortHandler.setComparator(assuntoColumn, new Comparator<AvaliacaoNota>() {
            @Override
            public int compare(AvaliacaoNota o1, AvaliacaoNota o2) {
                return o1.getAssunto().compareTo(o2.getAssunto());
            }
        });

        descricaoColumn.setSortable(true);
        sortHandler.setComparator(descricaoColumn, new Comparator<AvaliacaoNota>() {
            @Override
            public int compare(AvaliacaoNota o1, AvaliacaoNota o2) {
                return o1.getDescricao().compareTo(o2.getDescricao());
            }
        });

        columnTipoAvaliacao.setSortable(true);
        sortHandler.setComparator(columnTipoAvaliacao, new Comparator<AvaliacaoNota>() {
            @Override
            public int compare(AvaliacaoNota o1, AvaliacaoNota o2) {
                return o1.getTipoAvaliacao().getNomeTipoAvaliacao().compareTo(o2.getTipoAvaliacao().getNomeTipoAvaliacao());
            }
        });
        
        descricaoColumn.setSortable(true);
        sortHandler.setComparator(descricaoColumn, new Comparator<AvaliacaoNota>() {
            @Override
            public int compare(AvaliacaoNota o1, AvaliacaoNota o2) {
                return o1.getPesoNota().compareTo(o2.getPesoNota());
            }
        });

        dataColumn.setSortable(true);
        sortHandler.setComparator(dataColumn, new Comparator<AvaliacaoNota>() {
            @Override
            public int compare(AvaliacaoNota o1, AvaliacaoNota o2) {
                return o1.getData().compareTo(o2.getData());
            }
        });

        horaColumn.setSortable(true);
        sortHandler.setComparator(horaColumn, new Comparator<AvaliacaoNota>() {
            @Override
            public int compare(AvaliacaoNota o1, AvaliacaoNota o2) {
                return o1.getHora().compareTo(o2.getHora());
            }
        });

    }

    private class EnterKeyUpHandler implements KeyUpHandler {
        public void onKeyUp(KeyUpEvent event) {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                hide();
            }
        }
    }

    private class ClickHandlerFechar implements ClickHandler {
        public void onClick(ClickEvent event) {
            DialogBoxNota.this.hide();
        }
    }

    private class CallbackAvaliacaoNota implements AsyncCallback<ArrayList<AvaliacaoNota>>{
        @Override
        public void onFailure(Throwable caught) {
            mpLoading.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText(txtConstants.avaliacaoErroCarregar());
        }

        @Override
        public void onSuccess(ArrayList<AvaliacaoNota> list) {
            MpUtilClient.isRefreshRequired(list);
            mpLoading.setVisible(false);
            dataProvider.getList().clear();
            cellTable.setRowCount(0);
            for(int i=0;i<list.size();i++){
                dataProvider.getList().add(list.get(i));
            }
            addCellTableData(dataProvider);
            cellTable.redraw(); 

        }
        
    }
    
    


}
