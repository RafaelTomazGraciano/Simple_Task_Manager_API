package org.graciano.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties properties = new Properties();

    static{
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties")){
            if(input==null){
                System.err.println("Unable to find db.properties");
            }
            properties.load(input);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }

}
