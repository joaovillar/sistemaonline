package com.jornada.client.classes.listBoxes.ambiente.coordenador;

import java.util.ArrayList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.client.service.GWTServiceDocumento;
import com.jornada.shared.classes.Documento;


public class MpSelectionDocumentos extends MpSelection {
	
private AsyncCallback<ArrayList<Documento>> callBackPopulateComboBox;
    
    private ArrayList<Documento> listDocumentos;

    public MpSelectionDocumentos(){

        listDocumentos = new ArrayList<Documento>();
        
        /***********************Begin Callbacks**********************/
        callBackPopulateComboBox = new AsyncCallback<ArrayList<Documento>>() {
            public void onSuccess(ArrayList<Documento> lista) {
                
                try {

                    finishLoadingListBox();

                    for (Documento object : lista) {
                        addItem(object.getNomeDocumento(),Integer.toString(object.getIdDocumento()));
                        listDocumentos.add(object);
                    }

                    setVisibleItemCount(1);

                    // DomEvent.fireNativeEvent(Document.get().createChangeEvent(),
                    // MpSelectionTipoAvaliacao.this);
                    try {
                        DomEvent.fireNativeEvent(Document.get().createChangeEvent(),MpSelectionDocumentos.this);
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
    
    private void populateComboBox() {
        startLoadingListBox();
        GWTServiceDocumento.Util.getInstance().getDocumentos(callBackPopulateComboBox);
    }
    
    private void startLoadingListBox(){
        clear();
        addItem(CARREGANDO,Integer.toString(-1));
    }
    
    private void finishLoadingListBox(){
        clear();
    }
    
    public Documento getSelectedObject(int index){
        
        Documento curso = null;
        int idDocumento = Integer.parseInt(this.getValue(index));
        
        for(int i=0;i<this.getItemCount();i++){
            
            if(idDocumento==listDocumentos.get(i).getIdDocumento()){
                curso = listDocumentos.get(i);
            }
        }
        
        return curso;
    }
	
}
