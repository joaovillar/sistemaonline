package com.jornada.client.classes.listBoxes;

import java.util.ArrayList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.jornada.client.service.GWTServiceConteudoProgramatico;
import com.jornada.shared.classes.ConteudoProgramatico;

public class MpSelectionConteudoProgramatico extends MpSelection {	
	
	private AsyncCallback<ArrayList<ConteudoProgramatico>> callBackPopulateComboBox;

	
	public MpSelectionConteudoProgramatico(){

	
		/***********************Begin Callbacks**********************/
		callBackPopulateComboBox = new AsyncCallback<ArrayList<ConteudoProgramatico>>() {
			public void onSuccess(ArrayList<ConteudoProgramatico> lista) {
				try{
					
					finishLoadingListBox();

					for (ConteudoProgramatico object : lista) {
						addItem(object.getNome(), Integer.toString(object.getIdConteudoProgramatico()));
					}

					setVisibleItemCount(1);

					// DomEvent.fireNativeEvent(Document.get().createChangeEvent(),
					// MpSelectionConteudoProgramatico.this);
					try {
						DomEvent.fireNativeEvent(Document.get().createChangeEvent(),MpSelectionConteudoProgramatico.this);
					} catch (Exception ex) {
						logoutAndRefreshPage();
						System.out.println("Error:" + ex.getMessage());
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

		
	}	
	
	public void populateComboBox(int idDisciplina) {
		startLoadingListBox();
		//GWTServicePeriodo.Util.getInstance().getPeriodosPeloCurso(idCurso, callBackPopulateComboBox);
		GWTServiceConteudoProgramatico.Util.getInstance().getConteudoProgramaticoPelaDisciplina(idDisciplina, callBackPopulateComboBox);
	}
	
	private void startLoadingListBox(){
		clear();
		addItem(CARREGANDO,Integer.toString(-1));
	}
	
	private void finishLoadingListBox(){
		clear();
	}

}
