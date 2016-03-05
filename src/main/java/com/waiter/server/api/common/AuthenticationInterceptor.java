package com.waiter.server.api.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceError;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shahenpoghosyan
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = Logger.getLogger(AuthenticationInterceptor.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("Interceptor is working .... ");
        response.getWriter().write(mapper.writeValueAsString(ResponseEntity
                .forError(new ServiceError(ErrorCode.FAILED_AUTHENTICATION, "Authorization required."))));
        return false;
    }
}
