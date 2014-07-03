package com.jornada.client.classes.listBoxes;

import com.google.gwt.user.client.ui.ListBox;

public class MpListBoxPorcentagemPresenca extends MpSelection {	
	
//	private AsyncCallback<ArrayList<Usuario>> callBackPopulateComboBox;
	
	private ListBox listBoxAux;
	
	
	private static final int NOTA_PADRAO = 75;

	
	public MpListBoxPorcentagemPresenca(){
				
		setWidth("150px");		
		
		listBoxAux = new ListBox();		

		populateComboBox();	
		
	}	

	
	public void populateComboBox() {
		
		for(int i=0;i<=100;i=i+5){
			String value = Integer.toString(i);
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
