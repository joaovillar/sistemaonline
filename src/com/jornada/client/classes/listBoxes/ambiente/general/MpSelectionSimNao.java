package com.jornada.client.classes.listBoxes.ambiente.general;

import com.jornada.client.classes.listBoxes.MpSelection;


public class MpSelectionSimNao extends MpSelection {
    
    public MpSelectionSimNao(){
        
        addItem(txtConstants.geralSim(),"true");
        addItem(txtConstants.geralNao(),"false");
        
    }
    
}