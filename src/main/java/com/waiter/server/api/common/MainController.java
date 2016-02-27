package com.waiter.server.api.common;

import com.waiter.server.services.common.exception.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class MainController {

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity handleCustomException(ServiceException ex) {
		return new ResponseEntity().forError(ex.getError());
	}

}