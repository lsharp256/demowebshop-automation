package com.demo.qa.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        try {
            InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
            if (input != null) {
                properties.load(input);
                System.out.println("✅ config.properties loaded successfully");
            } else {
                System.out.println("❌ config.properties not found in classpath");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
