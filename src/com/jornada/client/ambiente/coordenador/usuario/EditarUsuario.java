package com.jornada.client.ambiente.coordenador.usuario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
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
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.ambiente.general.configuracao.DialogBoxSenha;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.cells.MpStyledSelectionCell;
import com.jornada.client.classes.widgets.dialog.MpConfirmDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;


public class EditarUsuario extends VerticalPanel {
	
	
	private static final String IMAGE_DELETE = "images/delete.png";
	private static final String IMAGE_PASSWORD = "images/key.v2.16.png";

	private AsyncCallback<Boolean> callbackUpdateRow;
	private AsyncCallback<Boolean> callbackDeleteRow;
//	private AsyncCallback<ArrayList<TipoUsuario>> callbackGetTipoUsuarios;	
	private AsyncCallback<ArrayList<Usuario>> callbackGetUsuariosFiltro;	

	private CellTable<Usuario> cellTable;
	
	public String cellTableSelected="";


	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	
//	private ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();		
	private LinkedHashMap<String, String> listaTipoUsuario = new LinkedHashMap<String, String>();
//	private SimplePager pager;
	
	private ListDataProvider<Usuario> dataProvider = new ListDataProvider<Usuario>();	
	private Column<Usuario, String> columnPrimeiroNome;
	private Column<Usuario, String> columnSobreNome;
	private Column<Usuario, String> columnCPF;
	private Column<Usuario, String> columnEmail;
	private Column<Usuario, String> columnLogin;
	private Column<Usuario, Date> columnDataNascimento;
	private Column<Usuario, String> columnTelefoneCelular;
	private Column<Usuario, String> columnTelefoneResidencial;
	private Column<Usuario, String> columnTelefoneComercial;
	private Column<Usuario, String> columnTipoUsuario;
//	private SingleSelectionModel<Usuario> selectionModel;
	
	private TextBox txtSearch;
	private ListBox selectCampoFiltrar;
	
	private TelaInicialUsuario telaInicialUsuario;
	
	private VerticalPanel vPanelEditGrid;
	
//	ListHandler<Usuario> sortHandler;
	
	private MpSimplePager mpPager; 
	
	TextConstants txtConstants;

	public EditarUsuario(TelaInicialUsuario telaInicialUsuario) {
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialUsuario = telaInicialUsuario;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelLoading.show();
		mpPanelLoading.setVisible(false);
		
		FlexTable flexTableFiltrar = new FlexTable();		
		flexTableFiltrar.setCellSpacing(3);
		flexTableFiltrar.setCellPadding(3);
		flexTableFiltrar.setBorderWidth(0);
		
		selectCampoFiltrar = new ListBox();
		txtSearch = new TextBox();
		MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
		
		selectCampoFiltrar.addItem(txtConstants.usuarioPrimeiroNome(),"primeiro_nome");
		selectCampoFiltrar.addItem(txtConstants.usuarioSobreNome(),"sobre_nome");
		selectCampoFiltrar.addItem(txtConstants.usuarioEmail(),"email");
		selectCampoFiltrar.addItem(txtConstants.usuarioCPF(),"cpf");
		selectCampoFiltrar.addItem(txtConstants.usuarioTipo(),"nome_tipo_usuario");
		
		txtSearch.addKeyUpHandler(new EnterKeyUpHandler());
		btnFiltrar.addClickHandler(new ClickHandlerFiltrar());
		
		txtSearch.setStyleName("design_text_boxes");
		selectCampoFiltrar.setStyleName("design_text_boxes");
		
		flexTableFiltrar.setWidget(0, 0, selectCampoFiltrar);
		flexTableFiltrar.setWidget(0, 1, txtSearch);
		flexTableFiltrar.setWidget(0, 2, btnFiltrar);
		flexTableFiltrar.setWidget(0, 3, mpPanelLoading);		
		

		vPanelEditGrid = new VerticalPanel();		
		vPanelEditGrid.add(flexTableFiltrar);	
		
		
		super.add(vPanelEditGrid);		
		//populateComboBoxTipoUsuario();
		
		/************************* Begin Callback's *************************/
		
		callbackUpdateRow = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {

				getTelaInicialUsuario().getAssociarPaisAlunos().updateClientData();
			}

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroAtualizar());
				mpDialogBoxWarning.showDialog();
			}
		};

		callbackDeleteRow = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean success) {

				if (success == true) {
					cleanCellTable();
					// SC.say("Periodo removido com sucesso.");
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroRemover());
					mpDialogBoxWarning.showDialog();
				}

			}

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroRemover());
				mpDialogBoxWarning.showDialog();

			}
		};
		
		
		callbackGetUsuariosFiltro = new AsyncCallback<ArrayList<Usuario>>() {

			public void onSuccess(ArrayList<Usuario> list) {
				
				mpPanelLoading.setVisible(false);
				
				cleanCellTable();
				dataProvider.getList().clear();
				
				for(int i=0;i<list.size();i++){
					dataProvider.getList().add(list.get(i));
				}
		
				
				
				addCellTableData(dataProvider);
				
//				sortHandler = new ListHandler<Usuario>(dataProvider.getList());
//				
//				cellTable.addColumnSortHandler(sortHandler);
//				cellTable.setSelectionModel(selectionModel);
//				
//				sortHandler.setComparator(columnPrimeiroNome, new Comparator<Usuario>() {
//			      @Override
//			      public int compare(Usuario o1, Usuario o2) {
//			        return o1.getPrimeiroNome().compareTo(o2.getPrimeiroNome());
//			      }
//			    });						
			    
		

			}

			public void onFailure(Throwable caught) {
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroCarregar());
				mpDialogBoxWarning.showDialog();

			}
		};			

		/*********************** End Callbacks **********************/		

		populateComboBoxTipoUsuario();

	}
	
	
	
	public void renderCellTable(){
		

		cellTable = new CellTable<Usuario>(15,GWT.<CellTableStyle> create(CellTableStyle.class));

		cellTable.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable)+ "px");
	    cellTable.setAutoHeaderRefreshDisabled(true);
	    cellTable.setAutoFooterRefreshDisabled(true);
//	    cellTable.setPageSize(10);
		
		
		// Add a selection model so we can select cells.
		final SelectionModel<Usuario> selectionModel = new MultiSelectionModel<Usuario>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<Usuario> createDefaultManager());		
		initTableColumns(selectionModel);

		dataProvider.addDataDisplay(cellTable);
		
		mpPager = new MpSimplePager();
		mpPager.setDisplay(cellTable);
//		mpPager.setPageSize(15);
		
		/******** Begin Populate ********/
		cleanCellTable();
		/******** End Populate ********/
		
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize(Integer.toString(TelaInicialUsuario.intWidthTable+30)+"px",Integer.toString(TelaInicialUsuario.intHeightTable-80)+"px");
		scrollPanel.setAlwaysShowScrollBars(false);		
		scrollPanel.add(cellTable);

		vPanelEditGrid.add(mpPager);
		vPanelEditGrid.add(scrollPanel);
		
//		super.add(vPanelEditGrid);		
		
	}	

	private class MyImageCell extends ImageCell {

		@Override
		public Set<String> getConsumedEvents() {
			Set<String> consumedEvents = new HashSet<String>();
			consumedEvents.add("click");
			return consumedEvents;
		}

		@Override
		public void onBrowserEvent(Context context, Element parent,String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
			switch (DOM.eventGetType((Event) event)) {
			case Event.ONCLICK:

				final Usuario object = (Usuario) context.getKey();
				@SuppressWarnings("unused")
				CloseHandler<PopupPanel> closeHandler;
				
				if (value.equals(IMAGE_PASSWORD)) {
//					System.out.println(object.getPrimeiroNome());
					DialogBoxSenha.getInstance(object);
				} else if (value.equals(IMAGE_DELETE)) {
//					System.out.println(object.getPrimeiroNome());
					MpConfirmDialogBox confirmationDialog = new MpConfirmDialogBox(
							txtConstants.geralRemover(),txtConstants.usuarioDesejaRemover(object.getPrimeiroNome(),object.getSobreNome()),
							txtConstants.geralSim(), txtConstants.geralNao(),
							closeHandler = new CloseHandler<PopupPanel>() {
								public void onClose(CloseEvent<PopupPanel> event) {

									MpConfirmDialogBox x = (MpConfirmDialogBox) event.getSource();

									if (x.primaryActionFired()) {
										 GWTServiceUsuario.Util.getInstance().deleteUsuarioRow(object.getIdUsuario(), callbackDeleteRow);
									}
								}
							}

					);
					confirmationDialog.paint();					
				}
				break;
			default:
				Window.alert("Test default");
				break;
			}
		}

	}


	protected void cleanCellTable() {
		
		cellTable.setRowCount(0);
		cellTable.redraw();
//		cellTable.setPageStart(0);		

	    
//		mpPager.setDisplay(cellTable);
//		mpPager.setPageSize(15);
	
		
	}
	
	protected void populateComboBoxTipoUsuario() {
		
		GWTServiceUsuario.Util.getInstance().getTipoUsuarios(
		
				new AsyncCallback<ArrayList<TipoUsuario>>() {

					@Override
					public void onFailure(Throwable caught) {
						mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
						mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroCarregar());
					}

					@Override
					public void onSuccess(ArrayList<TipoUsuario> list) {

						for(TipoUsuario currentTipoUsuario : list){
							String strIdTipoUsuario = Integer.toString(currentTipoUsuario.getIdTipoUsuario());
							String strTipoUsuario = currentTipoUsuario.getNomeTipoUsuario();
							listaTipoUsuario.put(strIdTipoUsuario, strTipoUsuario);						
						}
						renderCellTable();
					}
				});
	}		
	
	
	
	private class ClickHandlerFiltrar implements ClickHandler {
		public void onClick(ClickEvent event) {
				mpPanelLoading.setVisible(true);
				callGetUsuarios();
		}
	}	
	
	
	private class EnterKeyUpHandler implements KeyUpHandler{	
		public void onKeyUp(KeyUpEvent event){
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				mpPanelLoading.setVisible(true);			
				callGetUsuarios();
			}
		}
	}
	
	
	private void callGetUsuarios(){
		String strCampoDB = selectCampoFiltrar.getValue(selectCampoFiltrar.getSelectedIndex());
		GWTServiceUsuario.Util.getInstance().getUsuarios(strCampoDB,"%" + txtSearch.getText() + "%", callbackGetUsuariosFiltro);
	}
	
	public void updateClientData(){		
		cleanCellTable();		
	}



	public TelaInicialUsuario getTelaInicialUsuario() {
		return telaInicialUsuario;
	}
	
	
	private void addCellTableData(ListDataProvider<Usuario> dataProvider) {

		ListHandler<Usuario> sortHandler = new ListHandler<Usuario>(dataProvider.getList());

		cellTable.addColumnSortHandler(sortHandler);

		initSortHandler(sortHandler);

	}

	private void initTableColumns(final SelectionModel<Usuario> selectionModel) {
		
	    MpStyledSelectionCell tipoUsuarioCell = new MpStyledSelectionCell(listaTipoUsuario,"design_text_boxes");
	    columnTipoUsuario = new Column<Usuario, String>(tipoUsuarioCell) {
	      @Override
	      public String getValue(Usuario object) {
	        return Integer.toString(object.getIdTipoUsuario());
	      }
	    };
	    
    
		columnTipoUsuario.setFieldUpdater(new FieldUpdater<Usuario, String>() {
			@Override
			public void update(int index, Usuario object, String value) {
				// Called when the user changes the value.
				object.setIdTipoUsuario(Integer.parseInt(value));
				GWTServiceUsuario.Util.getInstance().updateUsuarioRow(object,callbackUpdateRow);
			}
		});

		
		columnPrimeiroNome = new Column<Usuario, String>(new EditTextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getPrimeiroNome();
			}

		};
		columnPrimeiroNome.setFieldUpdater(new FieldUpdater<Usuario, String>() {
			@Override
			public void update(int index, Usuario object, String value) {
				// Called when the user changes the value.
				if (FieldVerifier.isValidName(value)) {
					object.setPrimeiroNome(value);
					GWTServiceUsuario.Util.getInstance().updateUsuarioRow(object, callbackUpdateRow);
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.usuarioPrimeiroNome()));
					mpDialogBoxWarning.showDialog();
				}							
			}
		});
		
		columnSobreNome = new Column<Usuario, String>(new EditTextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getSobreNome();
			}

		};
		columnSobreNome.setFieldUpdater(new FieldUpdater<Usuario, String>() {
			@Override
			public void update(int index, Usuario object, String value) {
				// Called when the user changes the value.				
				if (FieldVerifier.isValidName(value)) {
					object.setSobreNome(value);
					GWTServiceUsuario.Util.getInstance().updateUsuarioRow(object,callbackUpdateRow);
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.usuarioSobreNome()));
					mpDialogBoxWarning.showDialog();
				}		
			}
		});
		
		columnCPF = new Column<Usuario, String>(
				new EditTextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getCpf();
			}

		};
		columnCPF.setFieldUpdater(new FieldUpdater<Usuario, String>() {
			@Override
			public void update(int index, Usuario object, String value) {
				// Called when the user changes the value.
				object.setCpf(value);
				GWTServiceUsuario.Util.getInstance().updateUsuarioRow(object,callbackUpdateRow);
			}
		});
		
		columnEmail = new Column<Usuario, String>(new EditTextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getEmail();
			}

		};
		columnEmail.setFieldUpdater(new FieldUpdater<Usuario, String>() {
			@Override
			public void update(int index, Usuario object, String value) {
				// Called when the user changes the value.				
				if (FieldVerifier.isValidEmail(value)) {
					object.setEmail(value);
					GWTServiceUsuario.Util.getInstance().updateUsuarioRow(object,callbackUpdateRow);
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.usuarioEmail()));
					mpDialogBoxWarning.showDialog();
				}	
				
			}
		});
		

		columnDataNascimento = new Column<Usuario, Date>(new DatePickerCell()) {
			@Override
			public Date getValue(Usuario object) {
				return object.getDataNascimento();
			}
		};
		columnDataNascimento.setFieldUpdater(new FieldUpdater<Usuario, Date>() {
			@Override
			public void update(int index, Usuario object, Date value) {
				// Called when the user changes the value.
				object.setDataNascimento(value);
				GWTServiceUsuario.Util.getInstance().updateUsuarioRow(object,callbackUpdateRow);
			}
		});
		
		
		columnTelefoneCelular = new Column<Usuario, String>(
				new EditTextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getTelefoneCelular();
			}

		};
		columnTelefoneCelular.setFieldUpdater(new FieldUpdater<Usuario, String>() {
			@Override
			public void update(int index, Usuario object, String value) {
				// Called when the user changes the value.
				object.setTelefoneCelular(value);
				GWTServiceUsuario.Util.getInstance().updateUsuarioRow(object,callbackUpdateRow);
			}
		});
		
		columnTelefoneResidencial = new Column<Usuario, String>(
				new EditTextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getTelefoneResidencial();
			}

		};
		columnTelefoneResidencial.setFieldUpdater(new FieldUpdater<Usuario, String>() {
			@Override
			public void update(int index, Usuario object, String value) {
				// Called when the user changes the value.
				object.setTelefoneResidencial(value);
				GWTServiceUsuario.Util.getInstance().updateUsuarioRow(object,callbackUpdateRow);
			}
		});		
		
		columnTelefoneComercial = new Column<Usuario, String>(
				new EditTextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getTelefoneComercial();
			}

		};
		columnTelefoneComercial.setFieldUpdater(new FieldUpdater<Usuario, String>() {
			@Override
			public void update(int index, Usuario object, String value) {
				// Called when the user changes the value.
				object.setTelefoneComercial(value);
				GWTServiceUsuario.Util.getInstance().updateUsuarioRow(object,callbackUpdateRow);
			}
		});
		
		columnLogin = new Column<Usuario, String>(
				new EditTextCell()) {
			@Override
			public String getValue(Usuario object) {
				return object.getLogin();
			}

		};
		columnLogin.setFieldUpdater(new FieldUpdater<Usuario, String>() {
			@Override
			public void update(int index, Usuario object, String value) {
				// Called when the user changes the value.
				if (FieldVerifier.isValidName(value)) {
					object.setLogin(value);
					GWTServiceUsuario.Util.getInstance().updateUsuarioRow(object,callbackUpdateRow);
				} else {
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.usuario()));
					mpDialogBoxWarning.showDialog();
				}					
			}
		});
		
		
		
		Column<Usuario, String> columnSenha = new Column<Usuario, String>(new MyImageCell()) {
			@Override
			public String getValue(Usuario object) {
				return IMAGE_PASSWORD;
			}
		};
		
		
//		Column<Usuario, String> columnSenha = new Column<Usuario, String>(new EditTextCell()) {
//			@Override
//			public String getValue(Usuario object) {
//				String password="";
//				for(int i=0;i<object.getSenha().length();i++){
//					password = "*"+password;
//				}
//				return password;
//			}
//
//		};
//		columnSenha.setFieldUpdater(new FieldUpdater<Usuario, String>() {
//			@Override
//			public void update(int index, Usuario object, String value) {
//				// Called when the user changes the value.				
//				if (FieldVerifier.isValidName(value)) {
//					object.setSenha(value);
//					GWTServiceUsuario.Util.getInstance().updateUsuarioRow(object,callbackUpdateRow);
//				} else {
//					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//					mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.usuarioSenha()));
//					mpDialogBoxWarning.showDialog();
//				}	
//			}
//		});				
		
		
		Column<Usuario, String> removeColumn = new Column<Usuario, String>(new MyImageCell()) {
			@Override
			public String getValue(Usuario object) {
				return IMAGE_DELETE;
			}
		};


		cellTable.addColumn(columnTipoUsuario, txtConstants.usuarioTipo());
		cellTable.addColumn(columnPrimeiroNome, txtConstants.usuarioPrimeiroNome());
		cellTable.addColumn(columnSobreNome,txtConstants.usuarioSobreNome());
		cellTable.addColumn(columnEmail, txtConstants.usuarioEmail());
		cellTable.addColumn(columnLogin, txtConstants.usuario());
		cellTable.addColumn(columnSenha, txtConstants.usuarioSenha());
		cellTable.addColumn(columnCPF, txtConstants.usuarioCPF());
		cellTable.addColumn(columnDataNascimento, txtConstants.usuarioDataNascimento());
		cellTable.addColumn(columnTelefoneCelular, txtConstants.usuarioTelCelular());
		cellTable.addColumn(columnTelefoneResidencial, txtConstants.usuarioTelResidencial());
		cellTable.addColumn(columnTelefoneComercial, txtConstants.usuarioTelComercial());
		cellTable.addColumn(removeColumn, txtConstants.geralRemover());

		cellTable.getColumn(cellTable.getColumnIndex(columnTipoUsuario)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnPrimeiroNome)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnSobreNome)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnCPF)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnEmail)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnDataNascimento)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnTelefoneCelular)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnTelefoneResidencial)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnTelefoneComercial)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnLogin)).setCellStyleNames("edit-cell");		
		cellTable.getColumn(cellTable.getColumnIndex(columnSenha)).setCellStyleNames("hand-over");
		cellTable.getColumn(cellTable.getColumnIndex(removeColumn)).setCellStyleNames("hand-over");		
		
	}

	private void initSortHandler(ListHandler<Usuario> sortHandler) {
		
		
		columnTipoUsuario.setSortable(true);
	    sortHandler.setComparator(columnTipoUsuario, new Comparator<Usuario>() {
	      @Override
	      public int compare(Usuario o1, Usuario o2) {
		        return o1.getTipoUsuario().getNomeTipoUsuario().compareTo(o2.getTipoUsuario().getNomeTipoUsuario());
	      }
	    });		    		
		
		columnPrimeiroNome.setSortable(true);
	    sortHandler.setComparator(columnPrimeiroNome, new Comparator<Usuario>() {
	      @Override
	      public int compare(Usuario o1, Usuario o2) {
	        return o1.getPrimeiroNome().compareTo(o2.getPrimeiroNome());
	      }
	    });			
	    
		columnSobreNome.setSortable(true);
	    sortHandler.setComparator(columnSobreNome, new Comparator<Usuario>() {
	      @Override
	      public int compare(Usuario o1, Usuario o2) {
	        return o1.getSobreNome().compareTo(o2.getSobreNome());
	      }
	    });	
	    
		columnCPF.setSortable(true);
	    sortHandler.setComparator(columnCPF, new Comparator<Usuario>() {
	      @Override
	      public int compare(Usuario o1, Usuario o2) {
	        return o1.getCpf().compareTo(o2.getCpf());
	      }
	    });		
	    
		columnEmail.setSortable(true);
	    sortHandler.setComparator(columnEmail, new Comparator<Usuario>() {
	      @Override
	      public int compare(Usuario o1, Usuario o2) {
	        return o1.getEmail().compareTo(o2.getEmail());
	      }
	    });	
	    
		columnLogin.setSortable(true);
	    sortHandler.setComparator(columnLogin, new Comparator<Usuario>() {
	      @Override
	      public int compare(Usuario o1, Usuario o2) {
	        return o1.getLogin().compareTo(o2.getLogin());
	      }
	    });		   
	    
		columnDataNascimento.setSortable(true);
	    sortHandler.setComparator(columnDataNascimento, new Comparator<Usuario>() {
	      @Override
	      public int compare(Usuario o1, Usuario o2) {
	        return o1.getDataNascimento().compareTo(o2.getDataNascimento());
	      }
	    });
	    
		columnTelefoneResidencial.setSortable(true);
	    sortHandler.setComparator(columnTelefoneResidencial, new Comparator<Usuario>() {
	      @Override
	      public int compare(Usuario o1, Usuario o2) {
	        return o1.getTelefoneResidencial().compareTo(o2.getTelefoneResidencial());
	      }
	    });
	    
		columnTelefoneComercial.setSortable(true);
	    sortHandler.setComparator(columnTelefoneComercial, new Comparator<Usuario>() {
	      @Override
	      public int compare(Usuario o1, Usuario o2) {
	        return o1.getTelefoneComercial().compareTo(o2.getTelefoneComercial());
	      }
	    });
	    
		columnTelefoneCelular.setSortable(true);
	    sortHandler.setComparator(columnTelefoneCelular, new Comparator<Usuario>() {
	      @Override
	      public int compare(Usuario o1, Usuario o2) {
	        return o1.getTelefoneCelular().compareTo(o2.getTelefoneCelular());
	      }
	    });	   	    	    	    
	    
	    
		
	}
	

}
