package com.jornada.shared.classes.curso;

import java.io.Serializable;
import java.util.ArrayList;

public class Ensino  implements Serializable{

    private static final long serialVersionUID = -736912172281858180L;
    
    public static final String INFANTIL = "Educação Infantil";
    public static final String FUNDAMENTAL = "Ensino Fundamental";
    public static final String MEDIO = "Ensino Médio";
    public static final String PROFISSIONALIZANTE = "Ensino Profissionalizante";
    public static final String GRADUACAO = "Graduação";
    public static final String POS_GRADUACAO = "Pós-graduação";
    public static final String MESTRADO = "Mestrado";
    public static final String DOUTORADO = "Doutorado";
    public static final String POS_DOUTORADO = "Pós-Doutorado";   
    
    private ArrayList<EnsinoItem> listEnsino;
    
    public Ensino(){    
        listEnsino = new ArrayList<EnsinoItem>();
        listEnsino.add(new EnsinoItem(INFANTIL,INFANTIL));
        listEnsino.add(new EnsinoItem(FUNDAMENTAL,FUNDAMENTAL));
        listEnsino.add(new EnsinoItem(MEDIO,MEDIO));
        listEnsino.add(new EnsinoItem(PROFISSIONALIZANTE, PROFISSIONALIZANTE));
        listEnsino.add(new EnsinoItem(GRADUACAO,GRADUACAO));
        listEnsino.add(new EnsinoItem(POS_GRADUACAO,POS_GRADUACAO));
        listEnsino.add(new EnsinoItem(MESTRADO,MESTRADO));
        listEnsino.add(new EnsinoItem(DOUTORADO,DOUTORADO));
        listEnsino.add(new EnsinoItem(POS_DOUTORADO,POS_DOUTORADO));
    }

    public ArrayList<EnsinoItem> getListEnsino() {
        return listEnsino;
    }
    
    
}
