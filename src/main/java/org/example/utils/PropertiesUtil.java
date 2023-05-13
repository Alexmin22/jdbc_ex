package org.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }
    protected static String getProperty(String key) {
        return properties.getProperty(key);
    }

    private static void loadProperties() {
        try(InputStream is
                    = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Could not load properties");
        }
    }
}
