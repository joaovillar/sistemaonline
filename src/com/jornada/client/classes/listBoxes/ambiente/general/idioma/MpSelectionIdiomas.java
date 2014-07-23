package com.jornada.client.classes.listBoxes.ambiente.general.idioma;

import java.util.ArrayList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.client.service.GWTServiceIdioma;
import com.jornada.shared.classes.Idioma;

public class MpSelectionIdiomas extends MpSelection {	
	
	private AsyncCallback<ArrayList<Idioma>> callBackPopulateComboBox;
	
	public MpSelectionIdiomas(){

		
		/***********************Begin Callbacks**********************/
		callBackPopulateComboBox = new AsyncCallback<ArrayList<Idioma>>() {
			public void onSuccess(ArrayList<Idioma> lista) {
				
				finishLoadingListBox();		

				for (Idioma object : lista) {
					addItem(object.getNomeIdioma(),object.getLocale());
				}		

				setVisibleItemCount(1);

				try {
					DomEvent.fireNativeEvent(Document.get().createChangeEvent(), MpSelectionIdiomas.this);
				} catch (Exception ex) {
					logoutAndRefreshPage();
					System.out.println("Error:" + ex.getMessage());
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
		GWTServiceIdioma.Util.getInstance().getIdiomas(callBackPopulateComboBox);
//		GWTServiceCurso.Util.getInstance().getCursosAmbienteProfessor(usuario, callBackPopulateComboBox);
	}
	
	private void startLoadingListBox(){
		clear();
		addItem(CARREGANDO,Integer.toString(-1));
	}
	
	private void finishLoadingListBox(){
		clear();
	}


	
}
