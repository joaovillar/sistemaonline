package com.jornada.client.ambiente.coordenador.usuario;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.SingleUploader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.EditTextCell;
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
import com.jornada.shared.classes.Usuario;

public class ImportarUsuario extends VerticalPanel {
	
	VerticalPanel vBodyPanel = new VerticalPanel();
	
	private AsyncCallback<ArrayList<Usuario>> callbackImportarUsuarios;

	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpLoadingSave = new MpPanelLoading("images/radar.gif");
	
	Label lblNomeArquivoUploaded;
	String strNomeFisicoUploaded="";

	private CellTable<Usuario> cellTable;
	private ListDataProvider<Usuario> dataProvider;	
//	private MpSimplePager mpPager; 
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
		callbackImportarUsuarios = new AsyncCallback<ArrayList<Usuario>>() {

			public void onFailure(Throwable caught) {
				mpLoadingSave.setVisible(true);
				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
				mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroSalvar());
				mpDialogBoxWarning.showDialog();
			}

			@Override
			public void onSuccess(ArrayList<Usuario> arrayUsuario) {
				// lblLoading.setVisible(false);
				mpLoadingSave.setVisible(false);

				if (arrayUsuario==null) {

					mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
					mpDialogBoxConfirm.setBodyText(txtConstants.usuarioPlanilhaImportadoComSucesso());
					mpDialogBoxConfirm.showDialog();
					telaInicialUsuario.getEditarUsuario().updateClientData();
					telaInicialUsuario.getAssociarPaisAlunos().updateClientData();
					
				} else {
					
					
					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
					mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroImportarUsuarios());
					mpDialogBoxWarning.showDialog();

					
					vPassoTres.clear();
					vPassoTres.add(drawPassoTres());
					
					dataProvider.getList().clear();
					
					cleanCellTable();
					
					
					for(int i=0;i<arrayUsuario.size();i++){
						dataProvider.getList().add(arrayUsuario.get(i));
					}
			
					
					
					addCellTableData(dataProvider);
					
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

		super.add(vBodyPanel);


	}
	
	
	public MpPanelPageMainView drawPassoUm(){
		MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.usuarioSelecionarTemplate(), "images/microsoft_office_2007_excel.16.png");
		mpPanel.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable)+"px");
		
		Grid grid = new Grid(1,5);		
		grid.setCellSpacing(3);
		grid.setCellPadding(3);
		grid.setBorderWidth(0);	
		
		Label lblBaixarArquivo = new Label(txtConstants.usuarioArquivoExcelTemplate());
		
		Anchor anchor = new Anchor("excel/ImportarUsuarios.xlsx");
		anchor.setHref("excel/ImportarUsuarios.xlsx");
		
		grid.setWidget(0, 0, lblBaixarArquivo);
		grid.setWidget(0, 1, anchor);
		
		mpPanel.add(grid);
		
		return mpPanel;

	}

	public MpPanelPageMainView drawPassoDois(){
		
		MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.usuarioSelecionarArquivoExcel(), "images/arrow_down.16.png");
		mpPanel.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable)+"px");		
		
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
		btnUploadFile.addClickHandler(new ClickHandlerSave());
		

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
	
	private class ClickHandlerSave implements ClickHandler {

		public void onClick(ClickEvent event) {
			mpLoadingSave.setVisible(true);
			GWTServiceUsuario.Util.getInstance().importarUsuariosUsandoExcel(strNomeFisicoUploaded, callbackImportarUsuarios);

		}
	}
	

	/****************End Event Handlers*****************/

	  // Load the image in the document and in the case of success attach it to the viewer
	  private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
	    @SuppressWarnings("deprecation")
		public void onFinish(IUploader uploader) {
	      if (uploader.getStatus() == Status.SUCCESS) {

	        
	        // The server sends useful information to the client by default
	        UploadedInfo info = uploader.getServerInfo();


	        System.out.println("uploader.getFileInput().getName() " + uploader.getFileInput().getName());
	        System.out.println("File name " + info.name);
	        System.out.println("File content-type " + info.ctype);
	        System.out.println("File size " + info.size);

	        // You can send any customized message and parse it 
	        System.out.println("Server message " + info.message);
	        
	        strNomeFisicoUploaded = info.message;

	        lblNomeArquivoUploaded.setText(info.name);
	      }
	    }
	  };
	

	  
		private void addCellTableData(ListDataProvider<Usuario> dataProvider) {

			ListHandler<Usuario> sortHandler = new ListHandler<Usuario>(dataProvider.getList());

			cellTable.addColumnSortHandler(sortHandler);

			initSortHandler(sortHandler);

		}

		private void initTableColumns(final SelectionModel<Usuario> selectionModel) {
			
		    
		    columnTipoUsuario = new Column<Usuario, String>(new TextCell()) {
		      @Override
		      public String getValue(Usuario object) {
		        return (object.getTipoUsuario()==null)?"":object.getTipoUsuario().getNomeTipoUsuario();
		      }
		    };
		    
		
			columnPrimeiroNome = new Column<Usuario, String>(new TextCell()) {
				@Override
				public String getValue(Usuario object) {
					return object.getPrimeiroNome();
				}

			};

			
			columnSobreNome = new Column<Usuario, String>(new TextCell()) {
				@Override
				public String getValue(Usuario object) {
					return object.getSobreNome();
				}

			};
			
			columnCPF = new Column<Usuario, String>(
					new EditTextCell()) {
				@Override
				public String getValue(Usuario object) {
					return object.getCpf();
				}

			};
			
			columnEmail = new Column<Usuario, String>(new TextCell()) {
				@Override
				public String getValue(Usuario object) {
					return object.getEmail();
				}

			};

			columnDataNascimento = new Column<Usuario, Date>(new DatePickerCell()) {
				@Override
				public Date getValue(Usuario object) {
					return object.getDataNascimento();
				}
			};
			
			columnTelefoneCelular = new Column<Usuario, String>(
					new EditTextCell()) {
				@Override
				public String getValue(Usuario object) {
					return object.getTelefoneCelular();
				}

			};
			
			columnTelefoneResidencial = new Column<Usuario, String>(
					new EditTextCell()) {
				@Override
				public String getValue(Usuario object) {
					return object.getTelefoneResidencial();
				}

			};

			columnTelefoneComercial = new Column<Usuario, String>(
					new EditTextCell()) {
				@Override
				public String getValue(Usuario object) {
					return object.getTelefoneComercial();
				}

			};
			
			columnLogin = new Column<Usuario, String>(
					new EditTextCell()) {
				@Override
				public String getValue(Usuario object) {
					return object.getLogin();
				}

			};
			
			Column<Usuario, String> columnSenha = new Column<Usuario, String>(new TextCell()) {
				@Override
				public String getValue(Usuario object) {
					return object.getSenha();
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
			cellTable.getColumn(cellTable.getColumnIndex(columnSenha)).setCellStyleNames("edit-cell");
	
			
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
	  
		public VerticalPanel renderCellTable(){
			

			cellTable = new CellTable<Usuario>(10,GWT.<CellTableStyle> create(CellTableStyle.class));

			cellTable.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable-10)+ "px");
		    cellTable.setAutoHeaderRefreshDisabled(true);
		    cellTable.setAutoFooterRefreshDisabled(true);
//		    cellTable.setPageSize(10);
			
			
			// Add a selection model so we can select cells.
			final SelectionModel<Usuario> selectionModel = new MultiSelectionModel<Usuario>();
			cellTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<Usuario> createDefaultManager());		
			initTableColumns(selectionModel);
			
			dataProvider = new ListDataProvider<Usuario>();

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
