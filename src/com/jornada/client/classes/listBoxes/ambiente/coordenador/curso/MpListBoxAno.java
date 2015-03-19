package com.jornada.client.classes.listBoxes.ambiente.coordenador.curso;

import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.shared.classes.Ano;
import com.jornada.shared.classes.Ensino;


public class MpListBoxAno extends MpSelection {
	
	public MpListBoxAno(){
		
	}
	
    public void showItems(String strEnsino) {
        clear();
        
        if(strEnsino.equals(Ensino.FUNDAMENTAL)){
            addItem("1º Ano", Ano.ANO_1);
            addItem("2º Ano", Ano.ANO_2);
            addItem("3º Ano", Ano.ANO_3);
            addItem("4º Ano", Ano.ANO_4);
            addItem("5º Ano", Ano.ANO_5);
            addItem("6º Ano", Ano.ANO_6);
            addItem("7º Ano", Ano.ANO_7);
            addItem("8º Ano", Ano.ANO_8);
            addItem("9º Ano", Ano.ANO_9);
        }else if(strEnsino.equals(Ensino.MEDIO)){
            addItem("1º Ano", Ano.ANO_1);
            addItem("2º Ano", Ano.ANO_2);
            addItem("3º Ano", Ano.ANO_3);
        }

	}
	
}
