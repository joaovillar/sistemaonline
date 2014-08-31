package com.jornada.client.classes.listBoxes;

import java.util.ArrayList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;

public class MpSelectionAluno extends MpSelection {	
	
	private AsyncCallback<ArrayList<Usuario>> callBackPopulateComboBox;
	
	private ListBox listBoxAux;

	
	public MpSelectionAluno(){
		
		listBoxAux = new ListBox();


		
		/***********************Begin Callbacks**********************/
		callBackPopulateComboBox = new AsyncCallback<ArrayList<Usuario>>() {
			public void onSuccess(ArrayList<Usuario> lista) {
				
				try {

					finishLoadingListBox();

					for (Usuario object : lista) {
						addItem(object.getPrimeiroNome() + " " + object.getSobreNome(), Integer.toString(object.getIdUsuario()));
						listBoxAux.addItem(object.getPrimeiroNome() + " " + object.getSobreNome(),Integer.toString(object.getIdUsuario()));
					}

					setVisibleItemCount(1);

					try {
						DomEvent.fireNativeEvent(Document.get().createChangeEvent(), MpSelectionAluno.this);
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
		GWTServiceUsuario.Util.getInstance().getUsuariosPorTipoUsuario(TipoUsuario.ALUNO, callBackPopulateComboBox);
	}
	
	private void startLoadingListBox(){
		clear();
//		listBoxAux.clear();
		addItem(CARREGANDO,Integer.toString(-1));
	}
	
	private void finishLoadingListBox(){
		listBoxAux.clear();
		clear();
	}	
	
	public void filterComboBox(String strFilter){
	
		clear();
		
		for(int i=0;i<listBoxAux.getItemCount();i++){
			addItem(listBoxAux.getItemText(i),listBoxAux.getValue(i));
		}		
		
		strFilter = strFilter.toUpperCase();
		
		if (!strFilter.isEmpty()) {
			
			int i=0;
			while(i<this.getItemCount()){
				
				String strNome = this.getItemText(i).toUpperCase();

				if (!strNome.contains(strFilter)) {
					this.removeItem(i);
					i = 0;
					continue;
				}
				i++;
			}
			
		}
		
	}

}
