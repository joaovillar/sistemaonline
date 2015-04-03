package com.jornada.shared.classes.curso;

import java.io.Serializable;

public class AnoItem implements Serializable {

    private static final long serialVersionUID = -3632869604063496459L;
    
    private String value;
    private String name;    

    
    public AnoItem(String name, String value) {
        super();
        this.value = value;
        this.name = name;
    }
    
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    

}
