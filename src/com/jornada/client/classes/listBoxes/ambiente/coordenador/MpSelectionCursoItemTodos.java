package com.jornada.client.classes.listBoxes.ambiente.coordenador;

import java.util.ArrayList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.client.service.GWTServicePeriodo;
import com.jornada.shared.classes.Curso;

public class MpSelectionCursoItemTodos extends MpSelection {	
	
	private ListBox listBoxAux;
	
	private AsyncCallback<ArrayList<Curso>> callBackPopulateComboBox;
	
	private ArrayList<Curso> listCurso;
	
	private String strText="";
	
	public MpSelectionCursoItemTodos(boolean autoPopulate, String strTextPrimeiroItem){

		listBoxAux = new ListBox();
		
		listCurso = new ArrayList<Curso>();
		this.strText = strTextPrimeiroItem;
		
		
		/***********************Begin Callbacks**********************/
		callBackPopulateComboBox = new AsyncCallback<ArrayList<Curso>>() {
			public void onSuccess(ArrayList<Curso> lista) {

				try {
					finishLoadingListBox();
					


					addItem(strText, "0");
					listBoxAux.addItem(strText, "0");

					for (Curso object : lista) {
						addItem(object.getNome(),Integer.toString(object.getIdCurso()));
						listBoxAux.addItem(object.getNome(),Integer.toString(object.getIdCurso()));
						listCurso.add(object);
					}

					setVisibleItemCount(1);

					try {
						DomEvent.fireNativeEvent(Document.get().createChangeEvent(),MpSelectionCursoItemTodos.this);
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
				listCurso.clear();
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
		GWTServicePeriodo.Util.getInstance().getCursos(callBackPopulateComboBox);
	}
	
	private void startLoadingListBox(){
		clear();
		listBoxAux.clear();
		listCurso.clear();
		addItem(CARREGANDO,Integer.toString(-1));
	}
	
	private void finishLoadingListBox(){
		clear();
		listBoxAux.clear();
		listCurso.clear();
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


	
	public ArrayList<Curso> getListCurso() {
		return listCurso;
	}

	public void setListCurso(ArrayList<Curso> listCurso) {
		this.listCurso = listCurso;
	}
	
	public Curso getSelectedObject(int index){
		
		Curso curso = null;
		int idCurso = Integer.parseInt(this.getValue(index));
		
		for(int i=0;i<this.getItemCount();i++){
			
			if(idCurso==listCurso.get(i).getIdCurso()){
				curso = listCurso.get(i);
			}
		}
		
		return curso;
	}
		
	
	
}
