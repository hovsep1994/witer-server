package com.waiter.server.utils.paramparser;


import java.text.ParseException;
import java.util.Date;

/**
 * @author by Shahen
 *         on 2/4/15.
 */
public interface IParamParser {

    String get(String key);
    String getString(String key, String def);
    boolean getBoolean(String key, boolean def);
    int getInt(String key);
    int getInt(String key, int def);
    Date getDate(String key) throws ParseException;
}
