package com.jornada.client.classes.listBoxes.ambiente.coordenador.curso;

import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.shared.classes.curso.Ano;
import com.jornada.shared.classes.curso.AnoItem;
import com.jornada.shared.classes.curso.Ensino;


public class MpListBoxAno extends MpSelection {
    
    private Ano ano;
	
	public MpListBoxAno(){
		ano = new Ano();
	}
	
    public void showItems(String strEnsino) {
        clear();
        
        if(strEnsino.equals(Ensino.FUNDAMENTAL)){            
            for(AnoItem anoItem : ano.getListFundamental()){
                addItem(anoItem.getName(), anoItem.getValue());
            }
        }else if(strEnsino.equals(Ensino.MEDIO)){
            for(AnoItem anoItem : ano.getListMedio()){
                addItem(anoItem.getName(), anoItem.getValue());
            }
        }

	}
	
}
