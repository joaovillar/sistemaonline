package com.jornada.client.ambiente.coordenador.comunicado;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
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
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpConfirmDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.config.ConfigClient;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceComunicado;
import com.jornada.shared.classes.Comunicado;
import com.jornada.shared.classes.TipoComunicado;
import com.jornada.shared.classes.utility.MpUtilClient;

public class TabelaComunicados extends VerticalPanel{
	
	VerticalPanel vPanelBody;
	
	private AsyncCallback<ArrayList<Comunicado>> callbackGetComunicadosFiltro;		
	private AsyncCallback<Boolean> callbackDeleteComunicado;
	
	private MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	private MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
	private MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	
	private CellTable<Comunicado> cellTable;
	private Column<Comunicado, String> assuntoColumn;
	private Column<Comunicado, Date> dataColumn;
	private Column<Comunicado, String> horaColumn;
	private Column<Comunicado, SafeHtml> descricaoColumn;
	private ListDataProvider<Comunicado> dataProvider = new ListDataProvider<Comunicado>();	
	
	private TextBox txtSearch;
	
	TelaInicialComunicado telaInicialComunicado;
	
    private static TabelaComunicados uniqueInstance;
    
    TextConstants txtConstants;
    ConfigClient configClient = GWT.create(ConfigClient.class);
	
	public static TabelaComunicados getInstance(TelaInicialComunicado telaInicialComunicado){
		if(uniqueInstance==null){
			uniqueInstance = new TabelaComunicados(telaInicialComunicado);
		}
		return uniqueInstance;
	}	
	
	private TabelaComunicados(TelaInicialComunicado telaInicialComunicado){
		
		txtConstants = GWT.create(TextConstants.class);
		

		this.telaInicialComunicado=telaInicialComunicado;
		
		vPanelBody = new VerticalPanel();
		
		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);		
		
		FlexTable flexTableFiltrar = new FlexTable();		
		flexTableFiltrar.setCellSpacing(3);
		flexTableFiltrar.setCellPadding(3);
		flexTableFiltrar.setBorderWidth(0);
		
		Label lblComunicado = new Label(txtConstants.comunicadoFiltrarComunicado());
		lblComunicado.setStyleName("design_label");
		lblComunicado.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
		txtSearch = new TextBox();
		MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
		
		txtSearch.addKeyUpHandler(new EnterKeyUpHandler());
		btnFiltrar.addClickHandler(new ClickHandlerFiltrar());
		
		txtSearch.setStyleName("design_text_boxes");	
		
		
		MpImageButton btnAdicionarComunicado = new MpImageButton(txtConstants.comunicadoAdicionarNovo(), "images/plus-circle.png");
		btnAdicionarComunicado.addClickHandler(new ClickHandlerAdicionar());
		
		
		cellTable = new CellTable<Comunicado>(10,GWT.<CellTableStyle> create(CellTableStyle.class));
//		cellTable.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable)+ "px");
		cellTable.setWidth("100%");
		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);
		
		final SelectionModel<Comunicado> selectionModel = new MultiSelectionModel<Comunicado>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<Comunicado> createCheckboxManager());
		initTableColumns(selectionModel);
		
		dataProvider.addDataDisplay(cellTable);	
		
		MpSimplePager mpPager = new MpSimplePager();
		mpPager.setDisplay(cellTable);
		mpPager.setPageSize(8);		
		
		flexTableFiltrar.setWidget(0, 0, mpPager);
		flexTableFiltrar.setWidget(0, 1, new MpSpaceVerticalPanel());
		flexTableFiltrar.setWidget(0, 2, lblComunicado);
		flexTableFiltrar.setWidget(0, 3, txtSearch);
		flexTableFiltrar.setWidget(0, 4, btnFiltrar);
		flexTableFiltrar.setWidget(0, 5, mpPanelLoading);
		
		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(TelaInicialComunicado.intWidthTable+30)+"px",Integer.toString(TelaInicialComunicado.intHeightTable+60)+"px");
		scrollPanel.setHeight(Integer.toString(TelaInicialComunicado.intHeightTable)+"px");
		scrollPanel.setWidth("100%");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(cellTable);


		
		vPanelBody.add(btnAdicionarComunicado);	
		vPanelBody.add(flexTableFiltrar);	
		vPanelBody.add(scrollPanel);	
		vPanelBody.setWidth("100%");
		vPanelBody.setBorderWidth(0);
		
		
		callbackGetComunicadosFiltro = new AsyncCallback<ArrayList<Comunicado>>() {

			public void onSuccess(ArrayList<Comunicado> list) {
				
				MpUtilClient.isRefreshRequired(list);
				
				mpPanelLoading.setVisible(false);	
				
				dataProvider.getList().clear();
				cellTable.setRowCount(0);
				for(int i=0;i<list.size();i++){
					dataProvider.getList().add(list.get(i));
				}
				addCellTableData(dataProvider);						
				cellTable.redraw();								
				
		

			}

			public void onFailure(Throwable caught) {
				mpPanelLoading.setVisible(false);	
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.geralErroCarregarDados());
				mpDialogBoxWarning.showDialog();

			}
		};			
		
		callbackDeleteComunicado = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {
				mpPanelLoading.setVisible(false);	
				if (success == true) {
					populateGrid();
					// SC.say("Periodo removido com sucesso.");
				} else {
					
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.geralErroCarregarDados());
					mpDialogBoxWarning.showDialog();
				}

			}

			public void onFailure(Throwable caught) {
				mpPanelLoading.setVisible(false);
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.geralErroCarregarDados());
				mpDialogBoxWarning.showDialog();

			}
		};		
		
		setWidth("100%");
		super.add(vPanelBody);
		populateGrid();
	}
	
	
	
	
	private class ClickHandlerFiltrar implements ClickHandler {
		public void onClick(ClickEvent event) {
				mpPanelLoading.setVisible(true);
				GWTServiceComunicado.Util.getInstance().getComunicados("%" + txtSearch.getText() + "%", callbackGetComunicadosFiltro);
				//GWTServiceUsuario.Util.getInstance().getUsuarios("%" + txtSearch.getText() + "%", callbackGetUsuariosFiltro);
		}
	}	
	
	private class EnterKeyUpHandler implements KeyUpHandler{
		
		public void onKeyUp(KeyUpEvent event){
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				mpPanelLoading.setVisible(true);					
				GWTServiceComunicado.Util.getInstance().getComunicados("%" + txtSearch.getText() + "%", callbackGetComunicadosFiltro);
			}
		}
	}
	
	private class ClickHandlerAdicionar implements ClickHandler {
		public void onClick(ClickEvent event) {			
			telaInicialComunicado.openFormularioComunicadoParaAdicionar();
//			TabelaComunicados.getInstance().setVisible(false);
//			FormularioComunicado formularioComunicado = FormularioComunicado.getInstance();			
//			formularioComunicado.setVisible(true);
		}
	}		
	
	
	
	protected void populateGrid() {
		
		GWTServiceComunicado.Util.getInstance().getComunicados("%" + txtSearch.getText() + "%",callbackGetComunicadosFiltro);
	}	
	
	
	private class MyImageCellDelete extends ImageCell {

		@Override
		public Set<String> getConsumedEvents() {
			Set<String> consumedEvents = new HashSet<String>();
			consumedEvents.add("click");
			return consumedEvents;
		}

		@Override
		public void onBrowserEvent(Context context, Element parent,
				String value, NativeEvent event,
				ValueUpdater<String> valueUpdater) {
			switch (DOM.eventGetType((Event) event)) {
			case Event.ONCLICK:
				final Comunicado object = (Comunicado) context.getKey();
				@SuppressWarnings("unused")
				CloseHandler<PopupPanel> closeHandler;

				MpConfirmDialogBox confirmationDialog = new MpConfirmDialogBox(
						txtConstants.geralRemover(),txtConstants.comunicadoDesejaRemover(object.getAssunto()), txtConstants.geralSim(), txtConstants.geralNao(),

						closeHandler = new CloseHandler<PopupPanel>() {

							public void onClose(CloseEvent<PopupPanel> event) {

								MpConfirmDialogBox x = (MpConfirmDialogBox) event.getSource();

								if (x.primaryActionFired()) {

									GWTServiceComunicado.Util.getInstance().deleteComunicadoRow(object.getIdComunicado(),callbackDeleteComunicado);

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

	}
	
	private class MyImageCellEdit extends ImageCell {

		@Override
		public Set<String> getConsumedEvents() {
			Set<String> consumedEvents = new HashSet<String>();
			consumedEvents.add("click");
			return consumedEvents;
		}

		@Override
		public void onBrowserEvent(Context context, Element parent, String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
			
			switch (DOM.eventGetType((Event) event)) {
			case Event.ONCLICK:
				final Comunicado object = (Comunicado) context.getKey();
								
				telaInicialComunicado.openFormularioComunicadoParaAtualizar(object);

				break;

			default:
				Window.alert("Test default");
				break;
			}
		}

	}
	
	private class MyImageCellView extends ImageCell{
		    @Override
		    public void render(com.google.gwt.cell.client.Cell.Context context, String value, SafeHtmlBuilder sb) {
		        super.render(context, value, sb);
		        final Comunicado object = (Comunicado) context.getKey();
		        
		        String imagePath = "images/frame.64.png";
		        if(object.getIdTipoComunicado()==TipoComunicado.EMAIL){
		            imagePath = "images/email.send.64.png";
		        }else{
		            imagePath = "images/download/compressed-"+object.getNomeImagem();   
		        }
		         
		        sb.appendHtmlConstant("<img src = '"+imagePath+"' height = '64px' width = '64px' />");

		    }
	}	
	
	
	private void addCellTableData(ListDataProvider<Comunicado> dataProvider){
		
		 ListHandler<Comunicado> sortHandler = new ListHandler<Comunicado>(dataProvider.getList());
		 
		 cellTable.addColumnSortHandler(sortHandler);	

		 initSortHandler(sortHandler);
	
	}


	private void initTableColumns(final SelectionModel<Comunicado> selectionModel) {
		
		assuntoColumn = new Column<Comunicado, String>(new TextCell()) {
			@Override
			public String getValue(Comunicado object) {
				return object.getAssunto();
			}

		};
		descricaoColumn = new Column<Comunicado, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml  getValue(Comunicado object) {		
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant(object.getDescricao());
				return sb.toSafeHtml();
			}

		};	
		dataColumn = new Column<Comunicado, Date>(new DatePickerCell()) {
			@Override
			public Date getValue(Comunicado object) {			    
//				String strDate = ((object.getData()!=null)?DateTimeFormat.getFormat(FORMAT_DATE).format(object.getData()):null);
				return object.getData();
			}
		};	
		horaColumn = new Column<Comunicado, String>(new TextCell()) {
			@Override
			public String getValue(Comunicado object) {
				return object.getHora();
			}
		};	

		Column<Comunicado, String> imageColumn = new Column<Comunicado, String>(new MyImageCellView()) {
			@Override
			public String getValue(Comunicado object) {
				return "";
			}
		};			
		
		Column<Comunicado, String> editColumn = new Column<Comunicado, String>(new MyImageCellEdit()) {
			@Override
			public String getValue(Comunicado object) {
				return "images/comment_edit.png";
			}
		};			
		
		Column<Comunicado, String> removeColumn = new Column<Comunicado, String>(new MyImageCellDelete()) {
			@Override
			public String getValue(Comunicado object) {
				return "images/delete.png";
			}
		};
		
		
		cellTable.addColumn(imageColumn, txtConstants.comunicadoImagem());
		cellTable.addColumn(assuntoColumn, txtConstants.comunicadoAssunto());
		cellTable.addColumn(descricaoColumn, txtConstants.comunicadoDetalhes());
		cellTable.addColumn(dataColumn, txtConstants.comunicadoData());
		cellTable.addColumn(horaColumn, txtConstants.comunicadoHora());
		cellTable.addColumn(editColumn, txtConstants.geralEditar());
		cellTable.addColumn(removeColumn, txtConstants.geralRemover());
		
		cellTable.getColumn(cellTable.getColumnIndex(assuntoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(assuntoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(descricaoColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(dataColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(horaColumn)).setCellStyleNames("hand-over-default");
		cellTable.getColumn(cellTable.getColumnIndex(editColumn)).setCellStyleNames("hand-over");
		cellTable.getColumn(cellTable.getColumnIndex(removeColumn)).setCellStyleNames("hand-over");
		
		cellTable.setColumnWidth(imageColumn, "65px");
		cellTable.setColumnWidth(editColumn, "65px");
		cellTable.setColumnWidth(removeColumn, "65px");		
		
		
	}

	public void initSortHandler(ListHandler<Comunicado> sortHandler) {
		
		assuntoColumn.setSortable(true);
	    sortHandler.setComparator(assuntoColumn, new Comparator<Comunicado>() {
	      @Override
	      public int compare(Comunicado o1, Comunicado o2) {
	        return o1.getAssunto().compareTo(o2.getAssunto());
	      }
	    });	

		descricaoColumn.setSortable(true);
	    sortHandler.setComparator(descricaoColumn, new Comparator<Comunicado>() {
	      @Override
	      public int compare(Comunicado o1, Comunicado o2) {
	        return o1.getDescricao().compareTo(o2.getDescricao());
	      }
	    });	
	    
	    
	    dataColumn.setSortable(true);
	    sortHandler.setComparator(dataColumn, new Comparator<Comunicado>() {
	      @Override
	      public int compare(Comunicado o1, Comunicado o2) {
	        return o1.getData().compareTo(o2.getData());
	      }
	    });	
	    
	    horaColumn.setSortable(true);
	    sortHandler.setComparator(horaColumn, new Comparator<Comunicado>() {
	      @Override
	      public int compare(Comunicado o1, Comunicado o2) {
	        return o1.getHora().compareTo(o2.getHora());
	      }
	    });	
	    
	}
	
	

}
