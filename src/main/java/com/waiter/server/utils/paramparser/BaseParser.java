package com.waiter.server.utils.paramparser;

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
}
