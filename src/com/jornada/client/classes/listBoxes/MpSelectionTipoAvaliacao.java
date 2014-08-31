package com.jornada.client.classes.listBoxes;

import java.util.ArrayList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.jornada.client.service.GWTServiceAvaliacao;
import com.jornada.shared.classes.TipoAvaliacao;

public class MpSelectionTipoAvaliacao extends MpSelection {	
	
	private AsyncCallback<ArrayList<TipoAvaliacao>> callBackPopulateComboBox;
	
	public MpSelectionTipoAvaliacao(){

		
		/***********************Begin Callbacks**********************/
		callBackPopulateComboBox = new AsyncCallback<ArrayList<TipoAvaliacao>>() {
			public void onSuccess(ArrayList<TipoAvaliacao> lista) {
				try {

					finishLoadingListBox();

					for (TipoAvaliacao object : lista) {
						addItem(object.getNomeTipoAvaliacao(),Integer.toString(object.getIdTipoAvaliacao()));
					}

					setVisibleItemCount(1);

					// DomEvent.fireNativeEvent(Document.get().createChangeEvent(),
					// MpSelectionTipoAvaliacao.this);
					try {
						DomEvent.fireNativeEvent(Document.get().createChangeEvent(),MpSelectionTipoAvaliacao.this);
					} catch (Exception ex) {
						logoutAndRefreshPage();
					}
				} catch (Exception ex) {
					logoutAndRefreshPage();
				}

			}

			public void onFailure(Throwable cautch) {
				logoutAndRefreshPage();
				clear();
				addItem(new Label(ERRO_POPULAR).getText());

			}
		};

		/***********************End Callbacks**********************/

		/******** Begin Populate ********/
		populateComboBox();
		/******** End Populate ********/
		
	}	
	
	private void populateComboBox() {
		startLoadingListBox();
		GWTServiceAvaliacao.Util.getInstance().getTipoAvaliacao(callBackPopulateComboBox);
	}
	
	private void startLoadingListBox(){
		clear();
		addItem(CARREGANDO,Integer.toString(-1));
	}
	
	private void finishLoadingListBox(){
		clear();
	}

}
