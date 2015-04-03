package com.jornada.client.classes.listBoxes.ambiente.pais;

import java.util.ArrayList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.classes.Usuario;

public class MpListBoxAlunosPorCursoAmbientePais extends MpSelection {	
	
	private AsyncCallback<ArrayList<Usuario>> callBackPopulateComboBox;
	
	private ListBox listBoxAux;

	public MpListBoxAlunosPorCursoAmbientePais(){
		
		listBoxAux = new ListBox();

		/***********************Begin Callbacks**********************/
		callBackPopulateComboBox = new AsyncCallback<ArrayList<Usuario>>() {
			public void onSuccess(ArrayList<Usuario> lista) {
				try {
					finishLoadingListBox();

					for (Usuario object : lista) {
						addItem(object.getPrimeiroNome() + " "+ object.getSobreNome(),Integer.toString(object.getIdUsuario()));
						listBoxAux.addItem(object.getPrimeiroNome() + " "+ object.getSobreNome(),Integer.toString(object.getIdUsuario()));
					}

					setVisibleItemCount(1);

					try {
						DomEvent.fireNativeEvent(Document.get().createChangeEvent(),MpListBoxAlunosPorCursoAmbientePais.this);
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
		


		
	}	
	
	public void populateComboBox(Usuario usuarioPai, int idCurso) {
		startLoadingListBox();
		GWTServiceUsuario.Util.getInstance().getUsuariosPorCursoAmbientePai(usuarioPai, idCurso, callBackPopulateComboBox);
	}
	
	private void startLoadingListBox(){
		clear();
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
