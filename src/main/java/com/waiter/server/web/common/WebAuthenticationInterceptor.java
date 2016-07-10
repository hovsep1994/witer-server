package com.waiter.server.web.common;

import com.waiter.server.services.user.UserService;
import com.waiter.server.services.user.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shahenpoghosyan
 */
public class WebAuthenticationInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = Logger.getLogger(WebAuthenticationInterceptor.class);

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.info("Web interceptor is working ... ");

        if(!request.getRequestURI().contains("/business/landing")) {
            User user = getUserByOauthToken(request);
            if (user != null) {
                request.setAttribute("user", user);
                return true;
            }

            LOGGER.info("Not logged in. Sending redirect ... ");
            response.sendRedirect("/business/landing");
            return false;
        }
        return true;
    }

    public User getUserByOauthToken(HttpServletRequest request) {
        LOGGER.info("Getting user for request - {}");
        try {
            Cookie cookie = getCookie("ckey", request);
            if (cookie == null) {
                return null;
            }
            final String token = cookie.getValue();
            LOGGER.info("token: " + token);
            final User user = userService.authenticate(token);
            LOGGER.info("Successfully get user - " + user.getToken() + " for request - {}");
            return user;
        } catch (SecurityException ex) {
            LOGGER.error("User not authorised for request - {}");
            throw new SecurityException("Not Authorised.");
        }
    }

    public Cookie getCookie(String cookie, HttpServletRequest request) {
        if(request.getCookies() == null) {
            return null;
        }
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals(cookie)) {
                return c;
            }
        }
        return null;
    }
}
