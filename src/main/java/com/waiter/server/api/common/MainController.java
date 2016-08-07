package com.waiter.server.api.common;

import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private static final Logger logger = Logger.getLogger(MainController.class);

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity handleCustomException(ServiceException ex) {
        return ResponseEntity.error(ex.getError());
    }

    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity handleCustomException(ServiceRuntimeException ex) {
        return ResponseEntity.error(ex.getError());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Something bad happened")
    public void handleException(HttpServletRequest req, Exception exception) {
        logger.error("Exception processing request. ", exception);
    }

}
