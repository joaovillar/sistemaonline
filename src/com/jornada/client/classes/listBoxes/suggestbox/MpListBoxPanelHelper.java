package com.jornada.client.classes.listBoxes.suggestbox;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MpListBoxPanelHelper extends VerticalPanel{
    
    
    
    public MpListBoxPanelHelper(){
        
    }
    
    public void populateSuggestBox(ListBox listBox){
        this.clear();
        add(new MpImageHelper(listBox));
    }

}
