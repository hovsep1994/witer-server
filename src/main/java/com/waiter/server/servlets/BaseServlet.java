package com.waiter.server.servlets;

import com.waiter.server.utils.paramparser.ParserFactory;

import javax.servlet.http.HttpServlet;

/**
 * Created by shahenpoghosyan on 7/14/15.
 */
public class BaseServlet extends HttpServlet {

    protected static final String CONTEXT = "springContext";
    protected static final String KEY = "key";
    protected static final String NAME = "name";
    protected static final String IMAGE = "image";
    protected static final String EMAIL = "email";
    protected static final String PHONE = "phone";
    protected static final String PASSWORD = "password";
    protected static final String QUERY = "query";
    protected static final String COUNTRY_CODE = "countryCode";
    protected static final String COUNTRY = "country";
    protected static final String CITY = "city";
    protected static final String LATITUDE = "latitude";
    protected static final String LONGITUDE = "longitude";
    protected static final String LIMIT = "limit";
    protected static final String ADDRESS = "address";
    protected static final String MENU_ID = "menu_id";
    protected static final String LOGIN = "login";

    protected final ParserFactory parserFactory = new ParserFactory();

}
