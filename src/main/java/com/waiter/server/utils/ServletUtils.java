package com.waiter.server.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author shahenpoghosyan
 */
public class ServletUtils {

    public static Cookie getCookie(String name, HttpServletRequest request) {
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

}
