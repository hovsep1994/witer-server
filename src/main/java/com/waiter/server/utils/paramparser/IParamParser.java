package com.waiter.server.utils.paramparser;


import org.apache.commons.fileupload.FileItem;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
    long getLong(String key);
    long getLong(String key, long def);
    double getDouble(String key);
    Date getDate(String key) throws ParseException;
    boolean isFileExists();
    FileItem getFile();
    List<String> getList(String key);
}
