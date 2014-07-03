package com.jornada;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author xwm468
 */
public class ConfigJornada
{
    
    /** Creates a new instance of BTCConfig */
    public ConfigJornada()
    {
        
    }
    
    
    /**
    * Return the value of specified field on BTCConfig.properties
    *
    *@param key for desired value
    *@return the value of specified key on BTCConfig.properties
    */
    public static String getProperty( String key)
    {
	Properties connFileProperties = new Properties();
	
	try
        {
            InputStream configFileStream = ConfigJornada.class.getResource( "Config.properties").openStream();
            connFileProperties.load( configFileStream);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
	
	return connFileProperties.getProperty( key);
    }
    
}

