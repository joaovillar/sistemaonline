package com.jornada.client.classes.listBoxes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ListBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBoxRefreshPage;
import com.jornada.client.content.i18n.TextConstants;

public class MpSelection extends ListBox{
	
	
	static TextConstants txtConstants = GWT.create(TextConstants.class);
	
	protected static String CARREGANDO = txtConstants.geralCarregando();
	protected static String ERRO_POPULAR = txtConstants.erroPopularCombobox();
	
	
	public MpSelection(){			
		setStyleName("design_list_boxes");
		setWidth("350px");	
	}
	
	
	public void setSelectItem(int value){		
		for(int i=0;i<this.getItemCount();i++){
			if(this.getValue(i).equals(Integer.toString(value))){
				this.setSelectedIndex(i);		
				break;
			}
		}		
	}
	
	public void setSelectItem(String value){		
		for(int i=0;i<this.getItemCount();i++){
			if(this.getValue(i).equals(value)){
				this.setSelectedIndex(i);		
				break;
			}
		}		
	}
	

	protected void logoutAndRefreshPage() {
		MpDialogBoxRefreshPage mpDialogBox = new MpDialogBoxRefreshPage();
		mpDialogBox.showDialog();
	}

}
