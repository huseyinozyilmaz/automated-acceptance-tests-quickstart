package org.huseyin.bdd.config;

import java.util.Properties;

public class TestProperties {

    private static final Properties properties;

    static {
        try {
            properties = new Properties();
            properties.load(ClassLoader.getSystemResourceAsStream("test.properties"));
            properties.forEach((key, value) -> {
                if(System.getProperties().containsKey(key)) {
                    properties.setProperty(key.toString(), System.getProperty(key.toString()));
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("There was a problem initializing the properties file: " + e.toString());
        }
    }

    public static int getSeleniumWaitTimeOutSeconds() {
        return Integer.valueOf(properties.getProperty("selenium.wait.timeout.seconds"));
    }

    public static String getApplicationBaseUrl() {
        return properties.getProperty("application.base.url");
    }

    public static String getLoginValidUsername() {
        return properties.getProperty("login.valid.username");
    }

    public static String getLoginValidPassword() {
        return properties.getProperty("login.valid.password");
    }
}
