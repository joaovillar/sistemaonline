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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.jornada.client.ambiente.general.configuracao.DialogBoxSenha;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.cells.MpStyledSelectionCell;
import com.jornada.client.classes.widgets.dialog.MpConfirmDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBoxRefreshPage;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.UnidadeEscola;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.utility.MpUtilClient;


public class EditarUsuario extends VerticalPanel {
	
	
	private static final String IMAGE_DELETE = "images/delete.png";
	private static final String IMAGE_EDIT = "images/comment_edit.png";
	private static final String IMAGE_PASSWORD = "images/key.v2.16.png";

	private AsyncCallback<String> callbackUpdateRow;
	private AsyncCallback<Boolean> callbackDeleteRow;
//	private AsyncCallback<ArrayList<TipoUsuario>> callbackGetTipoUsuarios;	
//	private AsyncCallback<ArrayList<Usuario>> callbackGetUsuariosFiltro;	

	private CellTable<Usuario> cellTable;
	private  SelectionModel<Usuario> selectionModel;	
	public String cellTableSelected="";


	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();	
	MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
	
//	private ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();		
	private LinkedHashMap<String, String> listaTipoUsuario = new LinkedHashMap<String, String>();
	
//	private MpListBoxStatusUsuario listBoxStatusUsuario = new MpListBoxStatusUsuario();
//	private SimplePager pager;
	
	private ListDataProvider<Usuario> dataProvider = new ListDataProvider<Usuario>();	
	private Column<Usuario, String> columnPrimeiroNome;
	private Column<Usuario, String> columnSobreNome;
	private Column<Usuario, String> columnCPF;
	private Column<Usuario, String> columnEmail;
	private Column<Usuario, String> columnLogin;
	private Column<Usuario, Date> columnDataNascimento;
	private Column<Usuario, String> columnTipoUsuario;

	
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
		
		selectCampoFiltrar.addItem(txtConstants.usuarioPrimeiroNome(),Usuario.DB_PRIMEIRO_NOME);
		selectCampoFiltrar.addItem(txtConstants.usuarioSobreNome(),Usuario.DB_SOBRE_NOME);
		selectCampoFiltrar.addItem(txtConstants.usuarioEmail(),Usuario.DB_EMAIL);
		selectCampoFiltrar.addItem(txtConstants.usuarioCPF(),Usuario.DB_CPF);
		selectCampoFiltrar.addItem(txtConstants.usuarioTipo(),TipoUsuario.DB_NOME_TIPO_USUARIO);
		selectCampoFiltrar.addItem(txtConstants.usuarioUnidadeEscola(),UnidadeEscola.DB_NOME_UNIDADE_ESCOLA);
//		selectCampoFiltrar.addItem(txtConstants.usuarioStatus(),TipoStatusUsuario.DB_ID_TIPO_STATUS_USUARIO);
		
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
		vPanelEditGrid.setWidth("100%");
		
		setWidth("100%");
		super.add(vPanelEditGrid);		
		//populateComboBoxTipoUsuario();
		
		/************************* Begin Callback's *************************/
		
		callbackUpdateRow = new AsyncCallback<String>() {

			public void onSuccess(String success) {				

				if(success.equals("true")){
					getTelaInicialUsuario().getAssociarPaisAlunos().updateClientData();	
				}else if(success.contains(Usuario.DB_UNIQUE_LOGIN)){
					String strUsuario = success.substring(success.indexOf("=(")+2);
					strUsuario = strUsuario.substring(0,strUsuario.indexOf(")"));
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroAtualizar() + " "+txtConstants.usuarioErroLoginDuplicado(strUsuario));
					mpDialogBoxWarning.showDialog();					
				}else{
					MpDialogBoxRefreshPage mpDialogBox = new MpDialogBoxRefreshPage();
					mpDialogBox.showDialog();				
				}
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
//					cleanCellTable();
//					 SC.say("Periodo removido com sucesso.");
				} else {
//					cleanCellTable();
					callGetUsuarios();
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
		
		

		/*********************** End Callbacks **********************/		

		populateComboBoxTipoUsuario();

	}
	
	
	
	public void renderCellTable(){
		

		cellTable = new CellTable<Usuario>(15,GWT.<CellTableStyle> create(CellTableStyle.class));

//		cellTable.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable)+ "px");
		cellTable.getElement().setId("celltable");
		cellTable.setWidth("100%");
	    cellTable.setAutoHeaderRefreshDisabled(true);
	    cellTable.setAutoFooterRefreshDisabled(true);
//	    cellTable.setPageSize(10);
//	    cellTable.setRowCount(15);
		
		
		// Add a selection model so we can select cells.
		selectionModel = new SingleSelectionModel<Usuario>();
		cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<Usuario> createDefaultManager());		
		initTableColumns(selectionModel);

		dataProvider.addDataDisplay(cellTable);
		
		mpPager = new MpSimplePager();
		mpPager.setDisplay(cellTable);

		
		/******** Begin Populate ********/
		cleanCellTable();
		/******** End Populate ********/
		
		ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setSize(Integer.toString(TelaInicialUsuario.intWidthTable+30)+"px",Integer.toString(TelaInicialUsuario.intHeightTable-80)+"px");
		scrollPanel.setAlwaysShowScrollBars(false);	
		scrollPanel.setHeight(Integer.toString(TelaInicialUsuario.intHeightTable-80)+"px");
		scrollPanel.setWidth("100%");
		scrollPanel.add(cellTable);
		
		Image imgExcel = new Image("images/excel.24.png");		
		imgExcel.addClickHandler(new clickHandlerExcel());
		imgExcel.setStyleName("hand-over");
		
		imgExcel.setTitle(txtConstants.geralExcel());
		
		FlexTable flexTableImg = new FlexTable();
		
		int columnImg = 0;
//		flexTableImg.setWidget(0, columnImg++, imgPrint);
		flexTableImg.setWidget(0, columnImg++, new InlineHTML("&nbsp;"));
		flexTableImg.setWidget(0, columnImg++, new InlineHTML("&nbsp;"));
		flexTableImg.setWidget(0, columnImg++, imgExcel);
		
//		Label lbl = new Label("Testing...");
		FlexTable flexTableMenu = new FlexTable();
		flexTableMenu.setCellPadding(2);
		flexTableMenu.setCellSpacing(2);
		flexTableMenu.setBorderWidth(0);
		flexTableMenu.setWidth("100%");		
		flexTableMenu.setWidget(0, 0, mpPager);
		flexTableMenu.setWidget(0, 1, flexTableImg);
		flexTableMenu.getCellFormatter().setWidth(0, 0, "30%");
		flexTableMenu.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);

		
//		flexTableMenu.setWidget(0, 2, lbl);
		
		vPanelEditGrid.add(flexTableMenu);
		vPanelEditGrid.add(scrollPanel);
		vPanelEditGrid.setBorderWidth(0);
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
				final int intSelectedIndex = context.getIndex(); 
				
				@SuppressWarnings("unused")
				CloseHandler<PopupPanel> closeHandler;
				
				if (value.equals(IMAGE_PASSWORD)) {
//					System.out.println(object.getPrimeiroNome());
					DialogBoxSenha.getInstance(object, true);
				}
				else if (value.equals(IMAGE_EDIT)){
//					MpDialogBoxAtualizarUsuario mpAtualizarUsuario = new MpDialogBoxAtualizarUsuario(telaInicialUsuario);
//					mpAtualizarUsuario.showDialog();
					MpDialogBoxAtualizarUsuario.getInstance(telaInicialUsuario, object);
				}
				else if (value.equals(IMAGE_DELETE)) {
//					System.out.println(object.getPrimeiroNome());
					MpConfirmDialogBox confirmationDialog = new MpConfirmDialogBox(
							txtConstants.geralRemover(),txtConstants.usuarioDesejaRemover(object.getPrimeiroNome(),object.getSobreNome()),
							txtConstants.geralSim(), txtConstants.geralNao(),
							closeHandler = new CloseHandler<PopupPanel>() {
								public void onClose(CloseEvent<PopupPanel> event) {

									MpConfirmDialogBox x = (MpConfirmDialogBox) event.getSource();

									if (x.primaryActionFired()) {

										GWTServiceUsuario.Util.getInstance().deleteUsuarioRow(object.getIdUsuario(), callbackDeleteRow);
										
										dataProvider.getList().remove(intSelectedIndex);
										dataProvider.refresh();	
										 
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
		cellTable.setPageStart(0);
		cellTable.redraw();
		dataProvider.refresh();	
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
						MpUtilClient.isRefreshRequired(list);
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
		GWTServiceUsuario.Util.getInstance().getUsuarios(strCampoDB,"%" + txtSearch.getText() + "%", new CallbackGetUsuariosFiltro());
	}
	
	public void updateClientData(){		
		cleanCellTable();		
		callGetUsuarios();
	}
	
	public void updateClientDataRow(int idUsuario){		
//		cleanCellTable();		
//		callGetUsuarios();
		
		GWTServiceUsuario.Util.getInstance().getUsuarioPeloId(idUsuario, new AsyncCallback<Usuario>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(Usuario usuarioResult) {
//				ArrayList<Usuario> listUsuario = new ArrayList<Usuario>();
//				listUsuario.add(usuarioResult);
//				cellTable.setRowData(listUsuario);	
				
//				dataProvider.getList().set(0, usuarioResult);
//				dataProvider.setList(listUsuario);
				int i = 0;
				while (i < dataProvider.getList().size()) {
					int idUser = dataProvider.getList().get(i).getIdUsuario();
					if (idUser == usuarioResult.getIdUsuario()) {
						dataProvider.getList().set(i, usuarioResult);
						dataProvider.refresh();
						i=dataProvider.getList().size();
					}
					i++;
				}
			}
			
		});
		
		
		
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
	    
	    
//        LinkedHashMap<String, String> listaTipoStatusUsuario = new LinkedHashMap<String, String>();
//        for (int i = 0; i < listBoxStatusUsuario.getItemCount(); i++) {
//            listaTipoStatusUsuario.put(listBoxStatusUsuario.getValue(i), listBoxStatusUsuario.getItemText(i));
//        }
//        MpStyledSelectionCell tipoUsuarioStatusCell = new MpStyledSelectionCell(listaTipoStatusUsuario, "design_text_boxes");
//        columnTipoStatusUsuario = new Column<Usuario, String>(tipoUsuarioStatusCell) {
//            @Override
//            public String getValue(Usuario object) {
//                return Integer.toString(object.getIdTipoStatusUsuario());
//            }
//        };
    
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
			    if(value==null || value.isEmpty()){
                    object.setEmail(value);
                    GWTServiceUsuario.Util.getInstance().updateUsuarioRow(object, callbackUpdateRow);
                } else {
                    if (FieldVerifier.isValidEmail(value)) {
                        object.setEmail(value);
                        GWTServiceUsuario.Util.getInstance().updateUsuarioRow(object, callbackUpdateRow);
                    } else {
                        mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                        mpDialogBoxWarning.setBodyText(txtConstants.geralEmailInvalido(txtConstants.usuarioEmail()));
                        mpDialogBoxWarning.showDialog();
                    }
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
		
		
//		columnTelefoneCelular = new Column<Usuario, String>(new EditTextCell()) {
//			@Override
//			public String getValue(Usuario object) {
//				return object.getTelefoneCelular();
//			}
//
//		};
//		columnTelefoneCelular.setFieldUpdater(new FieldUpdater<Usuario, String>() {
//			@Override
//			public void update(int index, Usuario object, String value) {
//				// Called when the user changes the value.
//				object.setTelefoneCelular(value);
//				GWTServiceUsuario.Util.getInstance().updateUsuarioRow(object,callbackUpdateRow);
//			}
//		});
//		
//		columnTelefoneResidencial = new Column<Usuario, String>(new EditTextCell()) {
//			@Override
//			public String getValue(Usuario object) {
//				return object.getTelefoneResidencial();
//			}
//
//		};
//		columnTelefoneResidencial.setFieldUpdater(new FieldUpdater<Usuario, String>() {
//			@Override
//			public void update(int index, Usuario object, String value) {
//				// Called when the user changes the value.
//				object.setTelefoneResidencial(value);
//				GWTServiceUsuario.Util.getInstance().updateUsuarioRow(object,callbackUpdateRow);
//			}
//		});		
//		
//		columnTelefoneComercial = new Column<Usuario, String>(new EditTextCell()) {
//			@Override
//			public String getValue(Usuario object) {
//				return object.getTelefoneComercial();
//			}
//
//		};
//		columnTelefoneComercial.setFieldUpdater(new FieldUpdater<Usuario, String>() {
//			@Override
//			public void update(int index, Usuario object, String value) {
//				// Called when the user changes the value.
//				object.setTelefoneComercial(value);
//				GWTServiceUsuario.Util.getInstance().updateUsuarioRow(object,callbackUpdateRow);
//			}
//		});
		
		columnLogin = new Column<Usuario, String>(new EditTextCell()) {
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
		
		Column<Usuario, String> editColumn = new Column<Usuario, String>(new MyImageCell()) {
			@Override
			public String getValue(Usuario object) {
				return IMAGE_EDIT;
			}
		};
		
		Column<Usuario, String> removeColumn = new Column<Usuario, String>(new MyImageCell()) {
			@Override
			public String getValue(Usuario object) {
				return IMAGE_DELETE;
			}
		};


		cellTable.addColumn(columnTipoUsuario, txtConstants.usuarioTipo());
		cellTable.addColumn(columnPrimeiroNome, txtConstants.usuarioPrimeiroNome());
		cellTable.addColumn(columnSobreNome,txtConstants.usuarioSobreNome());		
		cellTable.addColumn(columnLogin, txtConstants.usuario());
		cellTable.addColumn(columnSenha, txtConstants.usuarioSenha());
		cellTable.addColumn(columnEmail, txtConstants.usuarioEmail());
		cellTable.addColumn(columnCPF, txtConstants.usuarioCPF());
		cellTable.addColumn(columnDataNascimento, txtConstants.usuarioDataNascimento());
//		cellTable.addColumn(columnTipoStatusUsuario, txtConstants.usuarioStatus());
//		cellTable.addColumn(columnTelefoneResidencial, txtConstants.usuarioTelResidencial());
//		cellTable.addColumn(columnTelefoneComercial, txtConstants.usuarioTelComercial());
		cellTable.addColumn(editColumn, txtConstants.geralEditar());
		cellTable.addColumn(removeColumn, txtConstants.geralRemover());

		cellTable.getColumn(cellTable.getColumnIndex(columnTipoUsuario)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnPrimeiroNome)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnSobreNome)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnCPF)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnEmail)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnDataNascimento)).setCellStyleNames("edit-cell");
//		cellTable.getColumn(cellTable.getColumnIndex(columnTipoStatusUsuario)).setCellStyleNames("edit-cell");
//		cellTable.getColumn(cellTable.getColumnIndex(columnTelefoneResidencial)).setCellStyleNames("edit-cell");
//		cellTable.getColumn(cellTable.getColumnIndex(columnTelefoneComercial)).setCellStyleNames("edit-cell");
		cellTable.getColumn(cellTable.getColumnIndex(columnLogin)).setCellStyleNames("edit-cell");		
		cellTable.getColumn(cellTable.getColumnIndex(columnSenha)).setCellStyleNames("hand-over");
		cellTable.getColumn(cellTable.getColumnIndex(editColumn)).setCellStyleNames("hand-over");
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
	    
//		columnTelefoneResidencial.setSortable(true);
//	    sortHandler.setComparator(columnTelefoneResidencial, new Comparator<Usuario>() {
//	      @Override
//	      public int compare(Usuario o1, Usuario o2) {
//	        return o1.getTelefoneResidencial().compareTo(o2.getTelefoneResidencial());
//	      }
//	    });
//	    
//		columnTelefoneComercial.setSortable(true);
//	    sortHandler.setComparator(columnTelefoneComercial, new Comparator<Usuario>() {
//	      @Override
//	      public int compare(Usuario o1, Usuario o2) {
//	        return o1.getTelefoneComercial().compareTo(o2.getTelefoneComercial());
//	      }
//	    });
//	    
//		columnTelefoneCelular.setSortable(true);
//	    sortHandler.setComparator(columnTelefoneCelular, new Comparator<Usuario>() {
//	      @Override
//	      public int compare(Usuario o1, Usuario o2) {
//	        return o1.getTelefoneCelular().compareTo(o2.getTelefoneCelular());
//	      }
//	    });	   	    	    	    
	    
	    
		
	}
	
	
	private class clickHandlerExcel implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			MpDialogBoxExcel.getInstance();		 			
		}
	}
	
//	private class clickHandlerPrint implements ClickHandler{
//		@Override
//		public void onClick(ClickEvent event) {		 
////			MpDialogBoxPrinter.getInstance(telaInicialUsuario,MpDialogBoxPrinter.OPERATION_TYPE_PRINT);
////			String strCss = "<link type='text/css' rel='stylesheet' href='Jornada.css'>";
//			 Print.it(Print.getPageStyle(),cellTable.getElement());
//			
//		}
//	}
	
//    callbackGetUsuariosFiltro = new AsyncCallback<ArrayList<Usuario>>() {
	
    private class CallbackGetUsuariosFiltro implements AsyncCallback<ArrayList<Usuario>> {
        public void onSuccess(ArrayList<Usuario> list) {
            MpUtilClient.isRefreshRequired(list);
            mpPanelLoading.setVisible(false);

            cleanCellTable();
            dataProvider.getList().clear();

            for (int i = 0; i < list.size(); i++) {
                dataProvider.getList().add(list.get(i));
            }

            addCellTableData(dataProvider);
        }

        public void onFailure(Throwable caught) {
            mpPanelLoading.setVisible(false);
            MpDialogBoxRefreshPage mpDialogBoxRefreshPage = new MpDialogBoxRefreshPage();
            mpDialogBoxRefreshPage.showDialog();

        }
    } 


}
