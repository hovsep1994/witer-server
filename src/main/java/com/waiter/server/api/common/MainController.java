package com.waiter.server.api.common;

import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class MainController {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity handleCustomException(ServiceException ex) {
        return ResponseEntity.error(ex.getError());
    }

    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity handleCustomException(ServiceRuntimeException ex) {
        return ResponseEntity.error(ex.getError());
    }

}
