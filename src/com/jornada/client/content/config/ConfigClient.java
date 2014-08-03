package com.jornada.client.content.config;

import com.google.gwt.i18n.client.Messages;

/**
 *
 * @author xwm468
 */
//public class ConfigClient
public interface ConfigClient extends Messages
{
	
	String hierarquiaShowCursoEmail();
	String hierarquiaShowCursoDataNascimento();
	String hierarquiaShowCursoTelCelular();
	String hierarquiaShowCursoTelResidencial();
    
    /** Creates a new instance of BTCConfig */
//    public ConfigClient()
//    {
//        
//    }
    
    
    /**
    * Return the value of specified field on Config.properties
    *
    *@param key for desired value
    *@return the value of specified key on Config.properties
    */
//    public static String getProperty( String key)
//    {
//	Properties connFileProperties = new Properties();
//	
//	try
//        {
//            InputStream configFileStream = ConfigClient.class.getResource( "ConfigClient.properties").openStream();
//            connFileProperties.load( configFileStream);
//        }
//        catch (IOException ex)
//        {
//            ex.printStackTrace();
//        }
//	
//	return connFileProperties.getProperty( key);
//    }
    
}

