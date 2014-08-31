package com.jornada.client.classes.listBoxes.ambiente.coordenador;

import com.google.gwt.user.client.ui.ListBox;
import com.jornada.client.classes.listBoxes.MpSelection;

public class MpListBoxMediaNota extends MpSelection {	
	
//	private AsyncCallback<ArrayList<Usuario>> callBackPopulateComboBox;
	
	private ListBox listBoxAux;
	
	
	private static final double NOTA_PADRAO = 6.0;

	
	public MpListBoxMediaNota(){
				
		setWidth("150px");		
		
		listBoxAux = new ListBox();		

		populateComboBox();	
		
	}	

	
	public void populateComboBox() {
		
		for(double i=0.0;i<=10;i=i+0.5){
			String value = Double.toString(i);
			addItem(value,value);
			listBoxAux.addItem(value,value);
			if(i==NOTA_PADRAO){
				setSelectItem(value);
			}
		}
		
		

	}
	
//	private void startLoadingListBox(){
//		clear();
//		addItem(CARREGANDO,Integer.toString(-1));
//	}
//	
//	private void finishLoadingListBox(){
//		listBoxAux.clear();
//		clear();
//	}	
	
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
