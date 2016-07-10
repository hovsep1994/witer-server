package com.waiter.server.api.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceError;
import com.waiter.server.services.user.UserService;
import com.waiter.server.services.user.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shahenpoghosyan
 */
public class ApiAuthenticationInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiAuthenticationInterceptor.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = getUserByOauthToken(request);
        if (user != null) {
            request.setAttribute("user", user);
            return true;
        } else {
            LOGGER.debug("Interceptor is working .... ");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(mapper.writeValueAsString(ResponseEntity
                    .error(new ServiceError(ErrorCode.FAILED_AUTHENTICATION, "Authorization required."))));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }

    public User getUserByOauthToken(HttpServletRequest request) {
        LOGGER.info("Getting user for request - {}", request);
        try {
            final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (token == null) {
                return null;
            }
            final User user = userService.authenticate(token);
            LOGGER.info("Successfully get user - {} for request - {}", user, request);
            return user;
        } catch (SecurityException ex) {
            LOGGER.error("User not authorised for request - {}", request);
            throw new SecurityException("Not Authorised.");
        }
    }

}
