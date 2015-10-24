package com.waiter.server.servlets;

import com.waiter.server.utils.IParamParser;

import javax.servlet.http.HttpServlet;

/**
 * Created by shahenpoghosyan on 7/14/15.
 */
public class BaseServlet extends HttpServlet {

    protected static final String CONTEXT = "springContext";
    protected static final String NAME = "name";
    protected static final String EMAIL = "email";
    protected static final String PHONE = "phone";
    protected static final String QUERY = "query";
    protected static final String COUNTRY_CODE = "countryCode";
    protected static final String LIMIT = "limit";

}
