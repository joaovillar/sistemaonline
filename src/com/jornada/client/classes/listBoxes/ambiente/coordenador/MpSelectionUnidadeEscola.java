package com.jornada.client.classes.listBoxes.ambiente.coordenador;

import java.util.ArrayList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.client.service.GWTServiceUnidadeEscola;
import com.jornada.shared.classes.UnidadeEscola;

public class MpSelectionUnidadeEscola extends MpSelection {	
	
	private AsyncCallback<ArrayList<UnidadeEscola>> callBackPopulateComboBox;
	
	public MpSelectionUnidadeEscola(){

		
		/***********************Begin Callbacks**********************/
		callBackPopulateComboBox = new AsyncCallback<ArrayList<UnidadeEscola>>() {
			public void onSuccess(ArrayList<UnidadeEscola> lista) {
				
				try {

					finishLoadingListBox();

					for (UnidadeEscola object : lista) {
						addItem(object.getNomeUnidadeEscola(),Integer.toString(object.getIdUnidadeEscola()));
					}

					setVisibleItemCount(1);

					// DomEvent.fireNativeEvent(Document.get().createChangeEvent(),
					// MpSelectionTipoAvaliacao.this);
					try {
						DomEvent.fireNativeEvent(Document.get().createChangeEvent(),MpSelectionUnidadeEscola.this);
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
	
	public void populateComboBox() {
		startLoadingListBox();
		GWTServiceUnidadeEscola.Util.getInstance().getUnidadeEscolas(callBackPopulateComboBox);
	}
	
	private void startLoadingListBox(){
		clear();
		addItem(CARREGANDO,Integer.toString(-1));
	}
	
	private void finishLoadingListBox(){
		clear();
	}

}
