package com.jornada.client.ambiente.aluno.comunicado;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

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
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBoxRefreshPage;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceComunicado;
import com.jornada.shared.classes.Comunicado;
import com.jornada.shared.classes.utility.MpUtilClient;

public class TabelaAlunoComunicado extends VerticalPanel{
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	VerticalPanel vPanelBody;
	
	private AsyncCallback<ArrayList<Comunicado>> callbackGetComunicadosFiltro;		
//	private AsyncCallback<Boolean> callbackDeleteComunicado;
	
	private MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	private MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
	private MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	
	private CellTable<Comunicado> cellTable;
	private Column<Comunicado, String> assuntoColumn;
	private Column<Comunicado, SafeHtml> descricaoColumn;
	private Column<Comunicado, String> dataColumn;
	private Column<Comunicado, String> horaColumn;
	private ListDataProvider<Comunicado> dataProvider = new ListDataProvider<Comunicado>();	
	
	private TextBox txtSearch;
	
	private FlexTable flexTableFiltrar;
	
	TelaInicialAlunoComunicado telaInicialAlunoComunicado;
	
    private static TabelaAlunoComunicado uniqueInstance;
    DetalhesComunicado detalhesComunicado;
	
	public static TabelaAlunoComunicado getInstance(TelaInicialAlunoComunicado telaInicialAlunoComunicado){
		if(uniqueInstance==null){
			uniqueInstance = new TabelaAlunoComunicado(telaInicialAlunoComunicado);
		}
		return uniqueInstance;
	}	
	
	private TabelaAlunoComunicado(TelaInicialAlunoComunicado telaInicialAlunoComunicado){
		

		this.telaInicialAlunoComunicado=telaInicialAlunoComunicado;
		
		vPanelBody = new VerticalPanel();
		vPanelBody.setWidth("100%");
		
		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);		
		
		flexTableFiltrar = new FlexTable();		
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
		
		
//		MpImageButton btnAdicionarComunicado = new MpImageButton("Adicionar Novo Comunicado", "images/plus-circle.png");
//		btnAdicionarComunicado.addClickHandler(new ClickHandlerAdicionar());
		
		
		cellTable = new CellTable<Comunicado>(10,GWT.<CellTableStyle> create(CellTableStyle.class));
//		cellTable.setWidth(Integer.toString(TelaInicialAlunoComunicado.intWidthTable)+ "px");
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
		
		openTabelaComunicados();
		
		
		callbackGetComunicadosFiltro = new AsyncCallback<ArrayList<Comunicado>>() {

			public void onSuccess(ArrayList<Comunicado> list) {
				
				MpUtilClient.isRefreshRequired(list);
				
				mpPanelLoading.setVisible(false);	
				
				if(list==null){
					MpDialogBoxRefreshPage mpDialogBox = new MpDialogBoxRefreshPage();
					mpDialogBox.showDialog();	
				}
				
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
				mpDialogBoxWarning.setBodyText(txtConstants.comunicadoErroCarregar());
				mpDialogBoxWarning.showDialog();

			}
		};			

		
		this.setWidth("100%");
		super.add(vPanelBody);
		populateGrid();
	}
	
	
	
	
	private class ClickHandlerFiltrar implements ClickHandler {
		public void onClick(ClickEvent event) {
				mpPanelLoading.setVisible(true);
				GWTServiceComunicado.Util.getInstance().getComunicadosExterno("%" + txtSearch.getText() + "%", callbackGetComunicadosFiltro);
				//GWTServiceUsuario.Util.getInstance().getUsuarios("%" + txtSearch.getText() + "%", callbackGetUsuariosFiltro);
		}
	}	
	
	private class EnterKeyUpHandler implements KeyUpHandler{
		
		public void onKeyUp(KeyUpEvent event){
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				mpPanelLoading.setVisible(true);					
				GWTServiceComunicado.Util.getInstance().getComunicadosExterno("%" + txtSearch.getText() + "%", callbackGetComunicadosFiltro);
			}
		}
	}
	
	
	protected void populateGrid() {
		
		GWTServiceComunicado.Util.getInstance().getComunicadosExterno("%" + txtSearch.getText() + "%", callbackGetComunicadosFiltro);
		
//				new AsyncCallback<ArrayList<Comunicado>>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//						mpDialogBoxWarning.setBodyText(txtConstants.comunicadoErroCarregar());
//					}
//
//					@Override
//					public void onSuccess(ArrayList<Comunicado> list) {
//					
//						dataProvider.getList().clear();
//						cellTable.setRowCount(0);
//						for(int i=0;i<list.size();i++){
//							dataProvider.getList().add(list.get(i));
//						}
//						
//						addCellTableData(dataProvider);
//						
//						cellTable.redraw();								
//
//
//					}
//				});
	}	
	
	
	private class MpTextCell extends TextCell {

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
				openComunicadoDetalhe(object);
				break;
			default:
				Window.alert("Test default");
				break;
			}
		}

	}
	
	
	private class MpSafeHtmlCell extends SafeHtmlCell {

		@Override
		public Set<String> getConsumedEvents() {
			Set<String> consumedEvents = new HashSet<String>();
			consumedEvents.add("click");
			return consumedEvents;
		}
		
		@Override
		public void onBrowserEvent(Context context, Element parent, SafeHtml value, NativeEvent event, ValueUpdater<SafeHtml> valueUpdater) {

			super.onBrowserEvent(context, parent, value, event, valueUpdater);
			switch (DOM.eventGetType((Event) event)) {
			case Event.ONCLICK:
				final Comunicado object = (Comunicado) context.getKey();
				openComunicadoDetalhe(object);
				break;
			default:
				Window.alert("Test default");
				break;
			}
		}
		


//		@Override
//		public void onBrowserEvent(Context context, Element parent, SafeHtmlCell value, NativeEvent event, ValueUpdater<SafeHtmlCell> valueUpdater) {
//			
//			switch (DOM.eventGetType((Event) event)) {
//			case Event.ONCLICK:
//				final Comunicado object = (Comunicado) context.getKey();				
//				openComunicadoDetalhe(object);
//				break;
//			default:
//				Window.alert("Test default");
//				break;
//			}
//		}

	}
	
//	private class MyImageCellEdit extends ImageCell {
//
//		@Override
//		public Set<String> getConsumedEvents() {
//			Set<String> consumedEvents = new HashSet<String>();
//			consumedEvents.add("click");
//			return consumedEvents;
//		}
//
//		@Override
//		public void onBrowserEvent(Context context, Element parent, String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
//			
//			switch (DOM.eventGetType((Event) event)) {
//			case Event.ONCLICK:
//				final Comunicado object = (Comunicado) context.getKey();
//				openComunicadoDetalhe(object);
//				break;
//			default:
//				Window.alert("Test default");
//				break;
//			}
//		}
//
//	}
	
	private class MyImageCellView extends ImageCell {

		@Override
		public Set<String> getConsumedEvents() {
			Set<String> consumedEvents = new HashSet<String>();
			consumedEvents.add("click");
			return consumedEvents;
		}

		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context, String value, SafeHtmlBuilder sb) {
			super.render(context, value, sb);
			final Comunicado object = (Comunicado) context.getKey();
			String imagePath = "images/download/compressed-"+ object.getNomeImagem();
			sb.appendHtmlConstant("<img src = '" + imagePath+ "' height = '64px' width = '64px' />");

		}

		@Override
		public void onBrowserEvent(Context context, Element parent,
				String value, NativeEvent event,
				ValueUpdater<String> valueUpdater) {

			switch (DOM.eventGetType((Event) event)) {
			case Event.ONCLICK:
				final Comunicado object = (Comunicado) context.getKey();
				openComunicadoDetalhe(object);
				break;
			default:
				Window.alert("Test default");
				break;
			}
		}
	}
	
	public void openComunicadoDetalhe(Comunicado object){
		detalhesComunicado = new DetalhesComunicado(object);
		vPanelBody.clear();
		MpImageButton btnRetornarTelaAnterior = new MpImageButton("", "images/previousFolder.png");
		btnRetornarTelaAnterior.addClickHandler(new ClickHandlerCancelar());
		vPanelBody.add(btnRetornarTelaAnterior);
		vPanelBody.add(detalhesComunicado);
//		new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1500);
	}
	
	private class ClickHandlerCancelar implements ClickHandler {
		public void onClick(ClickEvent event) {
			
			openTabelaComunicados();
		
		}
	}
	
	public void openTabelaComunicados(){
		
		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(TelaInicialAlunoComunicado.intWidthTable+30)+"px",Integer.toString(TelaInicialAlunoComunicado.intHeightTable+70)+"px");
		scrollPanel.setHeight(Integer.toString(TelaInicialAlunoComunicado.intHeightTable+70)+"px");
		scrollPanel.setWidth("100%");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(cellTable);			
		
		vPanelBody.clear();
		vPanelBody.add(flexTableFiltrar);	
		vPanelBody.add(scrollPanel);
//		final FadeAnimation fadeAnimation = new FadeAnimation(vPanelBody.getElement());
//		fadeAnimation.fade(1500,0.8);
	}
	
	
	private void addCellTableData(ListDataProvider<Comunicado> dataProvider) {

		ListHandler<Comunicado> sortHandler = new ListHandler<Comunicado>(dataProvider.getList());

		cellTable.addColumnSortHandler(sortHandler);

		initSortHandler(sortHandler);

	}

	private void initTableColumns(final SelectionModel<Comunicado> selectionModel) {
		
		Column<Comunicado, String> imageColumn = new Column<Comunicado, String>(new MyImageCellView()) {
			@Override
			public String getValue(Comunicado object) {
				return "";
			}
		};					
		assuntoColumn = new Column<Comunicado, String>(new MpTextCell()) {
			@Override
			public String getValue(Comunicado object) {
				return object.getAssunto();
			}

		};
		descricaoColumn = new Column<Comunicado, SafeHtml>(new MpSafeHtmlCell()) {
			@Override
			public SafeHtml  getValue(Comunicado object) {		
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant(object.getDescricao());
				return sb.toSafeHtml();
			}

		};	
		dataColumn = new Column<Comunicado, String>(new MpTextCell()) {
			@Override
			public String getValue(Comunicado object) {			    
//				String strDate = ((object.getData()!=null)?DateTimeFormat.getFormat(FORMAT_DATE).format(object.getData()):null);
				return object.getData();
			}
		};	
		horaColumn = new Column<Comunicado, String>(new MpTextCell()) {
			@Override
			public String getValue(Comunicado object) {
				return object.getHora();
			}
		};	
//		Column<Comunicado, String> editColumn = new Column<Comunicado, String>(new MyImageCellEdit()) {
//			@Override
//			public String getValue(Comunicado object) {
//				return "images/magnifier.png";
//			}
//		};			
//		
//		Column<Comunicado, String> removeColumn = new Column<Comunicado, String>(new MyImageCellDelete()) {
//			@Override
//			public String getValue(Comunicado object) {
//				return "images/delete.png";
//			}
//		};
		

		
		cellTable.addColumn(imageColumn, txtConstants.comunicadoImagem());
		cellTable.addColumn(assuntoColumn, txtConstants.comunicado());
		cellTable.addColumn(descricaoColumn, txtConstants.comunicadoDetalhes());
		cellTable.addColumn(dataColumn, txtConstants.comunicadoData());
		cellTable.addColumn(horaColumn, txtConstants.comunicadoHora());
//		cellTable.addColumn(editColumn, txtConstants.comunicadoVerDetalhes());
//		cellTable.addColumn(removeColumn, "Remover");
		
		cellTable.getColumn(cellTable.getColumnIndex(imageColumn)).setCellStyleNames("hand-over");
		cellTable.getColumn(cellTable.getColumnIndex(assuntoColumn)).setCellStyleNames("hand-over");
		cellTable.getColumn(cellTable.getColumnIndex(descricaoColumn)).setCellStyleNames("hand-over");
		cellTable.getColumn(cellTable.getColumnIndex(dataColumn)).setCellStyleNames("hand-over");
		cellTable.getColumn(cellTable.getColumnIndex(horaColumn)).setCellStyleNames("hand-over");
//		cellTable.getColumn(cellTable.getColumnIndex(editColumn)).setCellStyleNames("hand-over");
//		cellTable.getColumn(cellTable.getColumnIndex(removeColumn)).setCellStyleNames("hand-over");
		
		cellTable.setColumnWidth(imageColumn, "65px");
//		cellTable.setColumnWidth(editColumn, "70px");
		
		
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
