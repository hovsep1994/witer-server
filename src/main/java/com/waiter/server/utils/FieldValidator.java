package com.waiter.server.utils;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Created by shahen on 11/22/14.
 */
public class FieldValidator {

    public static boolean validRequiredFields(String... fields) {
        for(String field : fields) {
            if(field == null || field.trim().isEmpty())
                return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
