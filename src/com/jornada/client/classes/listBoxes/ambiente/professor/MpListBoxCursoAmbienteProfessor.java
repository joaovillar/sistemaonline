package com.jornada.client.classes.listBoxes.ambiente.professor;

import java.util.ArrayList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.client.service.GWTServiceCurso;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Usuario;

public class MpListBoxCursoAmbienteProfessor extends MpSelection {	
	
	private AsyncCallback<ArrayList<Curso>> callBackPopulateComboBox;
	
	private ArrayList<Curso> listCurso;
	
	public MpListBoxCursoAmbienteProfessor(Usuario usuario){

	    listCurso = new ArrayList<Curso>();
		
		/***********************Begin Callbacks**********************/
		callBackPopulateComboBox = new AsyncCallback<ArrayList<Curso>>() {
			public void onSuccess(ArrayList<Curso> lista) {
				try {
					finishLoadingListBox();

					for (Curso object : lista) {
						addItem(object.getNome(),Integer.toString(object.getIdCurso()));
						listCurso.add(object);
					}

					setVisibleItemCount(1);

					try {
						DomEvent.fireNativeEvent(Document.get().createChangeEvent(),MpListBoxCursoAmbienteProfessor.this);
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
		populateComboBox(usuario);
		/******** End Populate ********/				

		
	}	
	
	public void populateComboBox(Usuario usuario) {
		startLoadingListBox();
		GWTServiceCurso.Util.getInstance().getCursosAmbienteProfessor(usuario, true, callBackPopulateComboBox);
	}
	
    public void populateComboBox(Usuario usuario, boolean statusCurso) {
        startLoadingListBox();
        GWTServiceCurso.Util.getInstance().getCursosAmbienteProfessor(usuario, statusCurso, callBackPopulateComboBox);
    }
	
	private void startLoadingListBox(){
		clear();
		addItem(CARREGANDO,Integer.toString(-1));
	}
	
	private void finishLoadingListBox(){
		clear();
	}

    public ArrayList<Curso> getListCurso() {
        return listCurso;
    }
	
}
