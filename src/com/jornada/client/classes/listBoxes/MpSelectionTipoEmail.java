package com.jornada.client.classes.listBoxes;

import java.util.ArrayList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.jornada.client.service.GWTServiceTipoComunicado;
import com.jornada.shared.classes.TipoComunicado;

public class MpSelectionTipoEmail extends MpSelection {	
	
	private AsyncCallback<ArrayList<TipoComunicado>> callBackPopulateComboBox;
	
	public MpSelectionTipoEmail(){

		
		/***********************Begin Callbacks**********************/
		callBackPopulateComboBox = new AsyncCallback<ArrayList<TipoComunicado>>() {
			public void onSuccess(ArrayList<TipoComunicado> lista) {
				
				try {
					finishLoadingListBox();

					for (TipoComunicado object : lista) {
						addItem(object.getTipoComunicadoNome(), Integer.toString(object.getIdTipoComunicado()));
					}

					setVisibleItemCount(1);

					// DomEvent.fireNativeEvent(Document.get().createChangeEvent(),
					// MpSelectionTipoComunicado.this);
					try {
						DomEvent.fireNativeEvent(Document.get().createChangeEvent(),MpSelectionTipoEmail.this);
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
		populateComboBox();
		/******** End Populate ********/
		
	}	
	
	private void populateComboBox() {
		startLoadingListBox();
		GWTServiceTipoComunicado.Util.getInstance().getTipoComunicadoEmails(callBackPopulateComboBox);
	}
	
	private void startLoadingListBox(){
		clear();
		addItem(CARREGANDO,Integer.toString(-1));
	}
	
	private void finishLoadingListBox(){
		clear();
	}

}
