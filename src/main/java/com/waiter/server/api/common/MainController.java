package com.waiter.server.api.common;

import com.waiter.server.api.common.model.MenuKitResponseEntity;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MainController.class);

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity handleCustomException(ServiceException ex) {
        logger.error("Error processing request. ", ex);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        if (ex.getError().getErrorCode() == ErrorCode.UNAUTHORIZED) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return MenuKitResponseEntity.error(ex.getError(), httpStatus);
    }

    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity handleCustomException(ServiceRuntimeException ex) {
        logger.error("Error processing request. ", ex);
        return MenuKitResponseEntity.error(ex.getError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public void checkUserHasAccess(User user, Company company) {
        if (!company.getId().equals(user.getCompany().getId())) {
            LOGGER.debug("User -{} has no access to company's -{} data", user, company);
            throw new SecurityException("User has no access to company data");
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Something bad happened")
    public void handleException(HttpServletRequest req, Exception exception) {
        logger.error("Exception processing request. ", exception);
    }

    public void validatePermission(boolean permission) throws ServiceException {
        if (!permission) {
            throw new ServiceException(ErrorCode.UNAUTHORIZED, "Operation not permitted. ");
        }
    }

}
