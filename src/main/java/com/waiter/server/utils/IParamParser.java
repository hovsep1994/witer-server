package com.waiter.server.utils;


import java.text.ParseException;
import java.util.Date;

/**
 * @author by Shahen
 *         on 2/4/15.
 */
public interface IParamParser {

    public String get(String key);
    public String getString(String key, String def);
    public boolean getBoolean(String key, boolean def);
    public int getInt(String key);
    public Date getDate(String key) throws ParseException;
}
