package com.jornada.client.classes.listBoxes.ambiente.general;

import com.jornada.client.classes.listBoxes.MpSelection;


public class MpListBoxSimNao extends MpSelection {
    
    public MpListBoxSimNao(){
        
        addItem(txtConstants.geralSim(),"true");
        addItem(txtConstants.geralNao(),"false");
        
    }
    
}