package com.jornada.client.classes.listBoxes;

import java.util.ArrayList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.jornada.client.service.GWTServiceAvaliacao;
import com.jornada.shared.classes.Avaliacao;

public class MpSelectionAvaliacao extends MpSelection {	
	
	private AsyncCallback<ArrayList<Avaliacao>> callBackPopulateComboBox;
	
	public MpSelectionAvaliacao(){

		/***********************Begin Callbacks**********************/
		callBackPopulateComboBox = new AsyncCallback<ArrayList<Avaliacao>>() {
			public void onSuccess(ArrayList<Avaliacao> lista) {
				
				finishLoadingListBox();		

				for (Avaliacao object : lista) {
					addItem(object.getAssunto(),Integer.toString(object.getIdAvaliacao()));
				}		

				setVisibleItemCount(1);

				DomEvent.fireNativeEvent(Document.get().createChangeEvent(), MpSelectionAvaliacao.this);
				
			}

			public void onFailure(Throwable cautch) {
				clear();
				addItem(new Label(ERRO_POPULAR).getText());

			}
		};

		/***********************End Callbacks**********************/

		
	}	
	
	public void populateComboBox(int idConteudoProgramatico) {
		startLoadingListBox();
		GWTServiceAvaliacao.Util.getInstance().getAvaliacaoPeloConteudoProgramatico(idConteudoProgramatico ,callBackPopulateComboBox);
	}
	
	private void startLoadingListBox(){
		clear();
		addItem(CARREGANDO,Integer.toString(-1));
	}
	
	private void finishLoadingListBox(){
		clear();
	}

}
