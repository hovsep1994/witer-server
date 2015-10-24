package com.waiter.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author by Shahen
 *         on 2/20/15.
 */
public abstract class AbstractParamParser implements IParamParser {

    @Override
    public boolean getBoolean(String key, boolean def) {
        String value = get(key);
        if (value == null)
            return def;
        return Boolean.parseBoolean(value);
    }

    @Override
    public Date getDate(String key) throws ParseException {
        String value = get(key);
        if (value == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.parse(value);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int def) {
        String value = get(key);
        if (value == null || value.isEmpty())
            return def;
        return Integer.parseInt(value);
    }

    public String getString(String key, String def) {
        String value = get(key);
        if(value == null) {
            return def;
        }
        return value;
    }
}
