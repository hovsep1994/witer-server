package com.waiter.server.services.common.exception;

/**
 * @author shahenpoghosyan
 */
public class Assert {

    public static void isTrue(boolean expression, String message, ErrorCode code) {
        if(!expression) {
            throw new ServiceRuntimeException(code, message);
        }
    }

    public static void isTrue(boolean expression, String message) {
        isTrue(expression, message, ErrorCode.BAD_REQUEST);
    }

    public static void notNull(Object expression, String message, ErrorCode code) {
        if(expression == null) {
            throw new ServiceRuntimeException(code, message);
        }
    }

    public static void notNull(Object expression, String message) {
        notNull(expression, message, ErrorCode.MISSED_FIELD);
    }
}
