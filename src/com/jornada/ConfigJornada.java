package com.jornada;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigJornada {

//    public ConfigJornada() {
//    }

    public static String getProperty(String key) {
        Properties connFileProperties = new Properties();

        try {
            InputStream configFileStream = ConfigJornada.class.getResource("Config.properties").openStream();
            connFileProperties.load(configFileStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return connFileProperties.getProperty(key);
    }

}
