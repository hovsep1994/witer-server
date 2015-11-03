package com.waiter.server.utils.paramparser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    @Override
    public long getLong(String key) {
        return Long.valueOf(get(key));
    }

    @Override
    public long getLong(String key, long def) {
        String value = get(key);
        if (value == null || value.isEmpty())
            return def;
        return Long.parseLong(value);
    }

    @Override
    public double getDouble(String key) {
        return Double.valueOf(get(key));
    }

    @Override
    public List<String> getList(String key) {
        String value = get(key);
        if (value == null) {
            return null;
        }
        return Arrays.asList(value.split(","));
    }
}
