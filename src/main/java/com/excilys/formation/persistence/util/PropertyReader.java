package com.excilys.formation.persistence.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static Properties properties = new Properties();

    public static Properties readProperties(InputStream pInputStream) {
        if(pInputStream == null) {
            return null;
        }
        
        try {
            properties.load(pInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pInputStream != null) {
                try {
                    pInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
}
