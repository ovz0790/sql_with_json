package com.db.query.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * Created by ovz on 28.12.15.
 */
public class PropertiesReader {
    @Autowired
    Environment env;

    public String readProperty(String key){
        return env.getProperty(key);
    }

    public Long readPropertyLong(String key){
        try {
            return Long.parseLong(env.getProperty(key));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Integer readPropertyInt(String key){
        try {
            return Integer.parseInt(env.getProperty(key));
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
