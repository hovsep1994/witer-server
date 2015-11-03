package com.waiter.server.utils.paramparser;

import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by Shahen
 *         on 2/4/15.
 */
public class BaseParser extends AbstractParamParser {

    private HttpServletRequest request;



    public BaseParser(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String get(String key) {
        return request.getParameter(key);
    }

    @Override
    public boolean isFileExists() {
        return false;
    }

    @Override
    public FileItem getFile() {
        return null;
    }
}
