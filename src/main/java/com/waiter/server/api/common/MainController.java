package com.waiter.server.api.common;

import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity handleCustomException(ServiceException ex) {
        return ResponseEntity.error(ex.getError());
    }

    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity handleCustomException(ServiceRuntimeException ex) {
        return ResponseEntity.error(ex.getError());
    }

    public void checkUserHasAccess(User user, Company company) {
//        if (!company.getId().equals(user.getCompany().getId())) {
//            LOGGER.debug("User -{} has no access to company's -{} data", user, company);
//            throw new SecurityException("User has no access to company data");
//        }
    }

}
