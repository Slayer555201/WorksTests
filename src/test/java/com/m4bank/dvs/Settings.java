package com.m4bank.dvs;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Settings {

    private static String PROPERTIES_FILE_NAME = "";

    private String getConfigFromFile(final String key, final String file) throws ConfigurationException {
        final PropertiesConfiguration config = new PropertiesConfiguration();
        switch (file) {
            case "configuration":
                PROPERTIES_FILE_NAME = ".\\src\\test\\java\\com\\m4bank\\dvs\\configuration.properties";
                break;
            case "db":
                PROPERTIES_FILE_NAME = ".\\src\\test\\java\\com\\m4bank\\dvs\\db\\db.properties";
                break;
        }

        config.load(PROPERTIES_FILE_NAME);
        //String configs = (String) config.getProperty(key);
        return (String) config.getProperty(key);
    }

    public String getConfig(final String key, final String defaultKey, final String file) {
        try {
            return getConfigFromFile(key, file);
        } catch (final ConfigurationException e) {
            System.out.println("Can't load from FILE! Use default:" + defaultKey + ". Error:" + e.getMessage());
            return defaultKey;
        }
    }


}
