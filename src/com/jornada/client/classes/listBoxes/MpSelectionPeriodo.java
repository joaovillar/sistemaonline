package com.jornada.client.classes.listBoxes;

import java.util.ArrayList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.jornada.client.service.GWTServicePeriodo;
import com.jornada.shared.classes.Periodo;

public class MpSelectionPeriodo extends MpSelection {

	private AsyncCallback<ArrayList<Periodo>> callBackPopulateComboBox;

	public MpSelectionPeriodo() {

		/*********************** Begin Callbacks **********************/
		callBackPopulateComboBox = new AsyncCallback<ArrayList<Periodo>>() {
			public void onSuccess(ArrayList<Periodo> lista) {

				finishLoadingListBox();

				for (Periodo object : lista) {
					addItem(object.getNomePeriodo(),
							Integer.toString(object.getIdPeriodo()));
				}

				setVisibleItemCount(1);

				DomEvent.fireNativeEvent(Document.get().createChangeEvent(),MpSelectionPeriodo.this);

			}

			public void onFailure(Throwable cautch) {
				clear();
				addItem(new Label(ERRO_POPULAR).getText());

			}
		};

		/*********************** End Callbacks **********************/

	}

	public void populateComboBox(int idCurso) {
		startLoadingListBox();
		GWTServicePeriodo.Util.getInstance().getPeriodosPeloCurso(idCurso,callBackPopulateComboBox);
	}

	private void startLoadingListBox() {
		clear();
		addItem(CARREGANDO, Integer.toString(-1));
	}

	private void finishLoadingListBox() {
		clear();
	}

}
