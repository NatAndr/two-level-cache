package com.getjavajob.training.wiley.andrianova.twolevelcache.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by user on 16.02.2016.
 */
public class CacheUtils {
    private static final Logger logger = LoggerFactory.getLogger(Serializer.class);
    private static final String PROPERTIES_FILE_NAME = "cache.properties";
    private static Properties properties;

    static {
        initProperties();
    }

    private static void initProperties() {
        properties = new Properties();
        try {
            properties.load(CacheUtils.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
        } catch (IOException e) {
            logger.error("Cannot load properties file: " + PROPERTIES_FILE_NAME);
        }
    }

    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
