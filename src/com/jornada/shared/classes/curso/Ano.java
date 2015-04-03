package com.jornada.shared.classes.curso;

import java.io.Serializable;
import java.util.ArrayList;

public class Ano implements Serializable{

    private static final long serialVersionUID = -6748042839932742541L;

    public static final String ANO_1_VALUE = "1ano";
    public static final String ANO_2_VALUE = "2ano";
    public static final String ANO_3_VALUE = "3ano";
    public static final String ANO_4_VALUE = "4ano";
    public static final String ANO_5_VALUE = "5ano";
    public static final String ANO_6_VALUE = "6ano";
    public static final String ANO_7_VALUE = "7ano";
    public static final String ANO_8_VALUE = "8ano";
    public static final String ANO_9_VALUE = "9ano";

    
    public static final String ANO_1_TEXT = "1º Ano";
    public static final String ANO_2_TEXT = "2º Ano";
    public static final String ANO_3_TEXT = "3º Ano";
    public static final String ANO_4_TEXT = "4º Ano";
    public static final String ANO_5_TEXT = "5º Ano";
    public static final String ANO_6_TEXT = "6º Ano";
    public static final String ANO_7_TEXT = "7º Ano";
    public static final String ANO_8_TEXT = "8º Ano";
    public static final String ANO_9_TEXT = "9º Ano";
    
    
    
    ArrayList<AnoItem> listAnoIteam;
    
    public Ano(){      
        listAnoIteam = new ArrayList<AnoItem>();
    }
    
    
    public ArrayList<AnoItem> getListFundamental(){
        listAnoIteam.clear();
        listAnoIteam.add(new AnoItem(ANO_1_TEXT, ANO_1_VALUE));
        listAnoIteam.add(new AnoItem(ANO_2_TEXT, ANO_2_VALUE));
        listAnoIteam.add(new AnoItem(ANO_3_TEXT, ANO_3_VALUE));
        listAnoIteam.add(new AnoItem(ANO_4_TEXT, ANO_4_VALUE));
        listAnoIteam.add(new AnoItem(ANO_5_TEXT, ANO_5_VALUE));
        listAnoIteam.add(new AnoItem(ANO_6_TEXT, ANO_6_VALUE));
        listAnoIteam.add(new AnoItem(ANO_7_TEXT, ANO_7_VALUE));
        listAnoIteam.add(new AnoItem(ANO_8_TEXT, ANO_8_VALUE));
        listAnoIteam.add(new AnoItem(ANO_9_TEXT, ANO_9_VALUE));           
        return listAnoIteam;
    }
    
    public ArrayList<AnoItem> getListMedio(){
        listAnoIteam.clear();
        listAnoIteam.add(new AnoItem(ANO_1_TEXT, ANO_1_VALUE));
        listAnoIteam.add(new AnoItem(ANO_2_TEXT, ANO_2_VALUE));
        listAnoIteam.add(new AnoItem(ANO_3_TEXT, ANO_3_VALUE));
        return listAnoIteam;
    }
    
    
}
