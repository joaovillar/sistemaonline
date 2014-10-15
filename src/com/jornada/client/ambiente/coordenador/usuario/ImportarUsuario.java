package com.jornada.client.ambiente.coordenador.usuario;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.SingleUploader;

import java.util.ArrayList;
import java.util.Comparator;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.ambiente.coordenador.curso.TelaInicialCurso;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpPanelPageMainView;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.classes.list.UsuarioErroImportar;
import com.jornada.shared.classes.utility.MpUtilClient;

public class ImportarUsuario extends VerticalPanel {
	
	VerticalPanel vBodyPanel = new VerticalPanel();
	
	private AsyncCallback<ArrayList<UsuarioErroImportar>> callbackImportarUsuarios;

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpLoadingSave = new MpPanelLoading("images/radar.gif");
	
	Label lblNomeArquivoUploaded;
	String strNomeFisicoUploaded="";

	private CellTable<UsuarioErroImportar> cellTable;
	private ListDataProvider<UsuarioErroImportar> dataProvider;	
//	private MpSimplePager mpPager; 
	private Column<UsuarioErroImportar, String> columnPrimeiroNome;
	private Column<UsuarioErroImportar, String> columnSobreNome;

	private Column<UsuarioErroImportar, String> columnEmail;
	private Column<UsuarioErroImportar, String> columnLogin;
	private Column<UsuarioErroImportar, String> columnErroImport;
//	private Column<Usuario, String> columnCPF;
//	private Column<Usuario, Date> columnDataNascimento;
//	private Column<Usuario, String> columnTelefoneCelular;
//	private Column<Usuario, String> columnTelefoneResidencial;
//	private Column<Usuario, String> columnTelefoneComercial;
	private Column<UsuarioErroImportar, String> columnTipoUsuario;	

	
	@SuppressWarnings("unused")
	private TelaInicialUsuario telaInicialUsuario;
	
	TextConstants txtConstants;
	
	VerticalPanel vPassoTres = new VerticalPanel();

	public ImportarUsuario(final TelaInicialUsuario telaInicialUsuario) {
		
		txtConstants = GWT.create(TextConstants.class);
		
		this.telaInicialUsuario=telaInicialUsuario;

		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		mpLoadingSave.setTxtLoading(txtConstants.geralCarregando());
		mpLoadingSave.show();
		mpLoadingSave.setVisible(false);
		
	


		
		
		/***********************Begin Callbacks**********************/

		// Callback para adicionar Disciplina.
		callbackImportarUsuarios = new AsyncCallback<ArrayList<UsuarioErroImportar>>() {

			public void onFailure(Throwable caught) {
				mpLoadingSave.setVisible(false);
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroSalvar());
				mpDialogBoxWarning.showDialog();
			}

			@Override
			public void onSuccess(ArrayList<UsuarioErroImportar> list) {
				 MpUtilClient.isRefreshRequired(list);
				mpLoadingSave.setVisible(false);

				if (list==null){
					vPassoTres.clear();
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroImportarUsuariosNull());
					mpDialogBoxWarning.showDialog();
				}
				else if(list.size()>0) {				
					
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroImportarUsuarios());
					mpDialogBoxWarning.showDialog();
					
					vPassoTres.clear();
					vPassoTres.add(drawPassoTres());
					
					dataProvider.getList().clear();					
					cleanCellTable();					
					
					for(int i=0;i<list.size();i++){
						dataProvider.getList().add(list.get(i));
					}					
					addCellTableData(dataProvider);
					
					telaInicialUsuario.getEditarUsuario().updateClientData();
					telaInicialUsuario.getAssociarPaisAlunos().updateClientData();
					
				} else {
					mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
					mpDialogBoxConfirm.setBodyText(txtConstants.usuarioPlanilhaImportadoComSucesso());
					mpDialogBoxConfirm.showDialog();
					telaInicialUsuario.getEditarUsuario().updateClientData();
					telaInicialUsuario.getAssociarPaisAlunos().updateClientData();
				}
			}
		};
	

		/***********************End Callbacks**********************/
		
		
		
		vBodyPanel.add(drawPassoUm());
		vBodyPanel.add(new InlineHTML("&nbsp;"));
		vBodyPanel.add(drawPassoDois());
		vBodyPanel.add(new InlineHTML("&nbsp;"));
//		vBodyPanel.add(drawPassoTres());
		vBodyPanel.add(vPassoTres);
		vBodyPanel.setWidth("100%");

		setWidth("100%");
		super.add(vBodyPanel);


	}
	
	
	public MpPanelPageMainView drawPassoUm(){
		MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.usuarioSelecionarTemplate(), "images/microsoft_office_2007_excel.16.png");
//		mpPanel.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable)+"px");
		mpPanel.setWidth("100%");
		
		Grid grid = new Grid(1,5);		
		grid.setCellSpacing(3);
		grid.setCellPadding(3);
		grid.setBorderWidth(0);	
		
		Label lblBaixarArquivo = new Label(txtConstants.usuarioArquivoExcelTemplate());
		
		Anchor anchor = new Anchor("ImportarUsuarios.xlsx");
		anchor.setHref("suporte/excel/ImportarUsuarios.xlsx");
		
		grid.setWidget(0, 0, lblBaixarArquivo);
		grid.setWidget(0, 1, anchor);
		
		mpPanel.add(grid);
		
		return mpPanel;

	}

	public MpPanelPageMainView drawPassoDois(){
		
		MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.usuarioSelecionarArquivoExcel(), "images/arrow_down.16.png");
//		mpPanel.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable)+"px");
		mpPanel.setWidth("100%");
		
		VerticalPanel vFormPanel = new VerticalPanel();
		
		Grid grid = new Grid(1,4);
		grid.setCellSpacing(3);
		grid.setCellPadding(3);
		grid.setBorderWidth(0);
		grid.setSize(Integer.toString(TelaInicialUsuario.intWidthTable),Integer.toString(TelaInicialUsuario.intHeightTable));


		Label lblSelecionarArquivo = new Label(txtConstants.usuarioSelecionarArquivo());		
		lblSelecionarArquivo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblSelecionarArquivo.setStyleName("design_label");
		
		
		lblNomeArquivoUploaded = new Label();		
		lblNomeArquivoUploaded.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblNomeArquivoUploaded.setStyleName("design_label");
		
		MpImageButton mpButton = new MpImageButton(txtConstants.usuarioProcurar(),"images/microsoft_office_2007_excel.16.png");
		
		SingleUploader singleUploader = new SingleUploader(FileInputType.CUSTOM.with(mpButton));

		singleUploader.setAutoSubmit(true);
		singleUploader.setValidExtensions("xlsx");
		singleUploader.addOnFinishUploadHandler(onFinishUploaderHandler);	


		int row = 0;
		grid.setWidget(row, 0, lblSelecionarArquivo);grid.setWidget(row, 1, singleUploader);grid.setWidget(row, 2, lblNomeArquivoUploaded);

		MpImageButton btnUploadFile = new MpImageButton(txtConstants.usuarioImportarArquivoExcel(), "images/arrow_down.16.png");
		btnUploadFile.addClickHandler(new ClickHandlerSubmitImportar());
		

		Grid gridSave = new Grid(1, 3);
		gridSave.setCellSpacing(2);
		gridSave.setCellPadding(2);
		{
			int i = 0;
			gridSave.setWidget(0, i++, btnUploadFile);
			gridSave.setWidget(0, i++, mpLoadingSave);
		}
		
		MpSpaceVerticalPanel mpSpaceVerticalPanel = new MpSpaceVerticalPanel();
		mpSpaceVerticalPanel.setWidth(Integer.toString(TelaInicialCurso.intWidthTable-700)+"px");

		vFormPanel.add(grid);
		vFormPanel.add(gridSave);
		vFormPanel.add(mpSpaceVerticalPanel);
		
		mpPanel.addPage(vFormPanel);
		
		return mpPanel;
		
	}

	
	public MpPanelPageMainView drawPassoTres(){
		MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.usuarioListaUsuariosInvalidos(), "images/report_check.16.png");
		mpPanel.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable)+"px");
		
		Grid grid = new Grid(1,1);		
		grid.setCellSpacing(3);
		grid.setCellPadding(3);
		grid.setBorderWidth(0);	
		
		grid.setWidget(0, 0, renderCellTable());
		
		mpPanel.add(grid);
		
		return mpPanel;

	}	
	
	
	/****************Begin Event Handlers*****************/
	
	private class ClickHandlerSubmitImportar implements ClickHandler {

		public void onClick(ClickEvent event) {
			vPassoTres.clear();
			mpLoadingSave.setVisible(true);
			GWTServiceUsuario.Util.getInstance().importarUsuariosUsandoExcel(strNomeFisicoUploaded, callbackImportarUsuarios);

		}
	}
	

	/****************End Event Handlers*****************/

	  // Load the image in the document and in the case of success attach it to the viewer
	  private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {

		public void onFinish(IUploader uploader) {
	      if (uploader.getStatus() == Status.SUCCESS) {

	        
	        // The server sends useful information to the client by default
	        UploadedInfo info = uploader.getServerInfo();


	        System.out.println("uploader.getFileInput().getName() " + uploader.getFileInput().getName());
	        System.out.println("File name " + info.name);
	        System.out.println("File content-type " + info.ctype);
	        System.out.println("File size " + info.size);

	        // You can send any customized message and parse it 
	        System.out.println("Server message :" + uploader.getServerMessage().getMessage());
	        
//	        strNomeFisicoUploaded = info.message;
	        strNomeFisicoUploaded =  uploader.getServerMessage().getMessage();

	        lblNomeArquivoUploaded.setText(info.name);
	      }
	    }
	  };
	

	  
		private void addCellTableData(ListDataProvider<UsuarioErroImportar> dataProvider) {

			ListHandler<UsuarioErroImportar> sortHandler = new ListHandler<UsuarioErroImportar>(dataProvider.getList());

			cellTable.addColumnSortHandler(sortHandler);

			initSortHandler(sortHandler);

		}

		private void initTableColumns(final SelectionModel<UsuarioErroImportar> selectionModel) {
			
		    
		    columnTipoUsuario = new Column<UsuarioErroImportar, String>(new TextCell()) {
		      @Override
		      public String getValue(UsuarioErroImportar object) {
		        return (object.getTipoUsuario()==null)?"":object.getTipoUsuario().getNomeTipoUsuario();
		      }
		    };
		    
		
			columnPrimeiroNome = new Column<UsuarioErroImportar, String>(new TextCell()) {
				@Override
				public String getValue(UsuarioErroImportar object) {
					return object.getPrimeiroNome();
				}

			};

			
			columnSobreNome = new Column<UsuarioErroImportar, String>(new TextCell()) {
				@Override
				public String getValue(UsuarioErroImportar object) {
					return object.getSobreNome();
				}

			};
			
//			columnCPF = new Column<Usuario, String>(
//					new EditTextCell()) {
//				@Override
//				public String getValue(Usuario object) {
//					return object.getCpf();
//				}
//
//			};
			
			columnEmail = new Column<UsuarioErroImportar, String>(new TextCell()) {
				@Override
				public String getValue(UsuarioErroImportar object) {
					return object.getEmail();
				}

			};
			
//			columnDataNascimento = new Column<Usuario, Date>(new DatePickerCell()) {
//				@Override
//				public Date getValue(Usuario object) {
//					return object.getDataNascimento();
//				}
//			};
//			
//			columnTelefoneCelular = new Column<Usuario, String>(
//					new EditTextCell()) {
//				@Override
//				public String getValue(Usuario object) {
//					return object.getTelefoneCelular();
//				}
//
//			};
//			
//			columnTelefoneResidencial = new Column<Usuario, String>(
//					new EditTextCell()) {
//				@Override
//				public String getValue(Usuario object) {
//					return object.getTelefoneResidencial();
//				}
//
//			};
//
//			columnTelefoneComercial = new Column<Usuario, String>(
//					new EditTextCell()) {
//				@Override
//				public String getValue(Usuario object) {
//					return object.getTelefoneComercial();
//				}
//
//			};
			
			columnLogin = new Column<UsuarioErroImportar, String>(new TextCell()) {
				@Override
				public String getValue(UsuarioErroImportar object) {
					return object.getLogin();
				}

			};
			
//			Column<UsuarioErroImportar, String> columnSenha = new Column<UsuarioErroImportar, String>(new TextCell()) {
//				@Override
//				public String getValue(UsuarioErroImportar object) {
//					return object.getSenha();
//				}
//
//			};
			
			columnErroImport = new Column<UsuarioErroImportar, String>(new TextCell()) {
				@Override
				public String getValue(UsuarioErroImportar object) {
					return object.getErroImportar();
				}

			};		

			cellTable.addColumn(columnTipoUsuario, txtConstants.usuarioTipo());
			cellTable.addColumn(columnPrimeiroNome, txtConstants.usuarioPrimeiroNome());
			cellTable.addColumn(columnSobreNome,txtConstants.usuarioSobreNome());
			cellTable.addColumn(columnEmail, txtConstants.usuarioEmail());
			cellTable.addColumn(columnLogin, txtConstants.usuario());
//			cellTable.addColumn(columnSenha, txtConstants.usuarioSenha());
			cellTable.addColumn(columnErroImport, txtConstants.geralErro());
//			cellTable.addColumn(columnCPF, txtConstants.usuarioCPF());
//			cellTable.addColumn(columnDataNascimento, txtConstants.usuarioDataNascimento());
//			cellTable.addColumn(columnTelefoneCelular, txtConstants.usuarioTelCelular());
//			cellTable.addColumn(columnTelefoneResidencial, txtConstants.usuarioTelResidencial());
//			cellTable.addColumn(columnTelefoneComercial, txtConstants.usuarioTelComercial());

			cellTable.getColumn(cellTable.getColumnIndex(columnTipoUsuario)).setCellStyleNames("edit-cell");
			cellTable.getColumn(cellTable.getColumnIndex(columnPrimeiroNome)).setCellStyleNames("edit-cell");
			cellTable.getColumn(cellTable.getColumnIndex(columnSobreNome)).setCellStyleNames("edit-cell");
//			cellTable.getColumn(cellTable.getColumnIndex(columnCPF)).setCellStyleNames("edit-cell");
			cellTable.getColumn(cellTable.getColumnIndex(columnEmail)).setCellStyleNames("edit-cell");
//			cellTable.getColumn(cellTable.getColumnIndex(columnDataNascimento)).setCellStyleNames("edit-cell");
//			cellTable.getColumn(cellTable.getColumnIndex(columnTelefoneCelular)).setCellStyleNames("edit-cell");
//			cellTable.getColumn(cellTable.getColumnIndex(columnTelefoneResidencial)).setCellStyleNames("edit-cell");
//			cellTable.getColumn(cellTable.getColumnIndex(columnTelefoneComercial)).setCellStyleNames("edit-cell");
			cellTable.getColumn(cellTable.getColumnIndex(columnLogin)).setCellStyleNames("edit-cell");		
//			cellTable.getColumn(cellTable.getColumnIndex(columnSenha)).setCellStyleNames("edit-cell");
			cellTable.getColumn(cellTable.getColumnIndex(columnErroImport)).setCellStyleNames("edit-cell");
	
			
		}

		private void initSortHandler(ListHandler<UsuarioErroImportar> sortHandler) {
			
			
			columnTipoUsuario.setSortable(true);
		    sortHandler.setComparator(columnTipoUsuario, new Comparator<UsuarioErroImportar>() {
		      @Override
		      public int compare(UsuarioErroImportar o1, UsuarioErroImportar o2) {
			        return o1.getTipoUsuario().getNomeTipoUsuario().compareTo(o2.getTipoUsuario().getNomeTipoUsuario());
		      }
		    });		    		
			
			columnPrimeiroNome.setSortable(true);
		    sortHandler.setComparator(columnPrimeiroNome, new Comparator<UsuarioErroImportar>() {
		      @Override
		      public int compare(UsuarioErroImportar o1, UsuarioErroImportar o2) {
		        return o1.getPrimeiroNome().compareTo(o2.getPrimeiroNome());
		      }
		    });			
		    
			columnSobreNome.setSortable(true);
		    sortHandler.setComparator(columnSobreNome, new Comparator<UsuarioErroImportar>() {
		      @Override
		      public int compare(UsuarioErroImportar o1, UsuarioErroImportar o2) {
		        return o1.getSobreNome().compareTo(o2.getSobreNome());
		      }
		    });	
		    
//			columnCPF.setSortable(true);
//		    sortHandler.setComparator(columnCPF, new Comparator<Usuario>() {
//		      @Override
//		      public int compare(Usuario o1, Usuario o2) {
//		        return o1.getCpf().compareTo(o2.getCpf());
//		      }
//		    });		
		    
			columnEmail.setSortable(true);
		    sortHandler.setComparator(columnEmail, new Comparator<UsuarioErroImportar>() {
		      @Override
		      public int compare(UsuarioErroImportar o1, UsuarioErroImportar o2) {
		        return o1.getEmail().compareTo(o2.getEmail());
		      }
		    });	
		    
			columnLogin.setSortable(true);
		    sortHandler.setComparator(columnLogin, new Comparator<UsuarioErroImportar>() {
		      @Override
		      public int compare(UsuarioErroImportar o1, UsuarioErroImportar o2) {
		        return o1.getLogin().compareTo(o2.getLogin());
		      }
		    });
		    
			columnErroImport.setSortable(true);
		    sortHandler.setComparator(columnErroImport, new Comparator<UsuarioErroImportar>() {
		      @Override
		      public int compare(UsuarioErroImportar o1, UsuarioErroImportar o2) {
		        return o1.getErroImportar().compareTo(o2.getErroImportar());
		      }
		    });		   
		    
		    
//			columnDataNascimento.setSortable(true);
//		    sortHandler.setComparator(columnDataNascimento, new Comparator<Usuario>() {
//		      @Override
//		      public int compare(Usuario o1, Usuario o2) {
//		        return o1.getDataNascimento().compareTo(o2.getDataNascimento());
//		      }
//		    });
//		    
//			columnTelefoneResidencial.setSortable(true);
//		    sortHandler.setComparator(columnTelefoneResidencial, new Comparator<Usuario>() {
//		      @Override
//		      public int compare(Usuario o1, Usuario o2) {
//		        return o1.getTelefoneResidencial().compareTo(o2.getTelefoneResidencial());
//		      }
//		    });
//		    
//			columnTelefoneComercial.setSortable(true);
//		    sortHandler.setComparator(columnTelefoneComercial, new Comparator<Usuario>() {
//		      @Override
//		      public int compare(Usuario o1, Usuario o2) {
//		        return o1.getTelefoneComercial().compareTo(o2.getTelefoneComercial());
//		      }
//		    });
//		    
//			columnTelefoneCelular.setSortable(true);
//		    sortHandler.setComparator(columnTelefoneCelular, new Comparator<Usuario>() {
//		      @Override
//		      public int compare(Usuario o1, Usuario o2) {
//		        return o1.getTelefoneCelular().compareTo(o2.getTelefoneCelular());
//		      }
//		    });	   	    	    	    
		    
		    
			
		}	  
	  
		public VerticalPanel renderCellTable(){
			

			cellTable = new CellTable<UsuarioErroImportar>(10,GWT.<CellTableStyle> create(CellTableStyle.class));

			cellTable.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable-10)+ "px");
		    cellTable.setAutoHeaderRefreshDisabled(true);
		    cellTable.setAutoFooterRefreshDisabled(true);
//		    cellTable.setPageSize(10);
			
			
			// Add a selection model so we can select cells.
			final SelectionModel<UsuarioErroImportar> selectionModel = new MultiSelectionModel<UsuarioErroImportar>();
			cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<UsuarioErroImportar> createDefaultManager());		
			initTableColumns(selectionModel);
			
			dataProvider = new ListDataProvider<UsuarioErroImportar>();

			dataProvider.addDataDisplay(cellTable);
			
			MpSimplePager mpPager = new MpSimplePager();
			mpPager.setDisplay(cellTable);
			mpPager.setPageSize(10);
			
			/******** Begin Populate ********/
			cleanCellTable();
			/******** End Populate ********/
			VerticalPanel vPanel = new VerticalPanel();
			vPanel.add(mpPager);
			vPanel.add(cellTable);
			
			return vPanel;
//			super.add(vPanelEditGrid);		
			
		}	
		
		protected void cleanCellTable() {
			
//			mpPager.setRangeLimited(false);
			
			cellTable.setRowCount(0);
			cellTable.redraw();
		}		
	
}
