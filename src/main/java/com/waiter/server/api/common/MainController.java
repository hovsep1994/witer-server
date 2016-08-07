package com.waiter.server.api.common;

import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private static final Logger logger = Logger.getLogger(MainController.class);

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity handleCustomException(ServiceException ex) {
        return ResponseEntity.error(ex.getError());
    }

    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity handleCustomException(ServiceRuntimeException ex) {
        return ResponseEntity.error(ex.getError());
    }

    public void checkUserHasAccess(User user, Company company) {
        if (!company.equals(user.getCompany())) {
            LOGGER.debug("User -{} has no access to company's -{} data", user, company);
            throw new SecurityException("User has no access to company data");
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Something bad happened")
    public void handleException(HttpServletRequest req, Exception exception) {
        logger.error("Exception processing request. ", exception);
    }

}
