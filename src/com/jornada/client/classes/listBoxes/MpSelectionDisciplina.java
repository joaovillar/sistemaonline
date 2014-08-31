package com.jornada.client.classes.listBoxes;

import java.util.ArrayList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.jornada.client.service.GWTServiceDisciplina;
import com.jornada.shared.classes.Disciplina;

public class MpSelectionDisciplina extends MpSelection {	
	
	private AsyncCallback<ArrayList<Disciplina>> callBackPopulateComboBox;

	
	public MpSelectionDisciplina(){


		
		/***********************Begin Callbacks**********************/
		callBackPopulateComboBox = new AsyncCallback<ArrayList<Disciplina>>() {
			public void onSuccess(ArrayList<Disciplina> lista) {
				try {
					
					finishLoadingListBox();

					for (Disciplina object : lista) {
						addItem(object.getNome(),Integer.toString(object.getIdDisciplina()));
					}

					setVisibleItemCount(1);

					try {
						DomEvent.fireNativeEvent(Document.get().createChangeEvent(),MpSelectionDisciplina.this);
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
	
	public void populateComboBox(int idPeriodo) {
		startLoadingListBox();
		//GWTServicePeriodo.Util.getInstance().getPeriodosPeloCurso(idCurso, callBackPopulateComboBox);
		GWTServiceDisciplina.Util.getInstance().getDisciplinasPeloPeriodo(idPeriodo, callBackPopulateComboBox);
	}
	
	private void startLoadingListBox(){
		clear();
		addItem(CARREGANDO,Integer.toString(-1));
	}
	
	private void finishLoadingListBox(){
		clear();
	}
	
}
