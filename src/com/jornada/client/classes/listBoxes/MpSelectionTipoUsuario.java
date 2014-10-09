package com.jornada.client.classes.listBoxes;

import java.util.ArrayList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.classes.TipoUsuario;

public class MpSelectionTipoUsuario extends MpSelection {	
	
	private AsyncCallback<ArrayList<TipoUsuario>> callBackPopulateComboBox;
	
	public MpSelectionTipoUsuario(){

		
		/***********************Begin Callbacks**********************/
		callBackPopulateComboBox = new AsyncCallback<ArrayList<TipoUsuario>>() {
			public void onSuccess(ArrayList<TipoUsuario> lista) {
				try {
					finishLoadingListBox();

					for (TipoUsuario object : lista) {
						addItem(object.getNomeTipoUsuario(),Integer.toString(object.getIdTipoUsuario()));
					}

					setVisibleItemCount(1);

					// DomEvent.fireNativeEvent(Document.get().createChangeEvent(),
					// MpSelectionTipoUsuario.this);
					try {
						DomEvent.fireNativeEvent(Document.get().createChangeEvent(),MpSelectionTipoUsuario.this);
					} catch (Exception ex) {
						logoutAndRefreshPage();
					}
				} catch (Exception ex) {
					logoutAndRefreshPage();
				}
			}

			public void onFailure(Throwable cautch) {
				logoutAndRefreshPage();
//				listBoxCurso.addItem(new Label("Erro popular curso.").getText());
				clear();
				addItem(new Label(ERRO_POPULAR).getText());

			}
		};

		/***********************End Callbacks**********************/

		/******** Begin Populate ********/
//		populateComboBox();
		/******** End Populate ********/
		
	}	
	
	public void populateComboBox() {
		startLoadingListBox();
		GWTServiceUsuario.Util.getInstance().getTipoUsuarios(callBackPopulateComboBox);
	}
	
	private void startLoadingListBox(){
		clear();
		addItem(CARREGANDO,Integer.toString(-1));
	}
	
	private void finishLoadingListBox(){
		clear();
	}


}
