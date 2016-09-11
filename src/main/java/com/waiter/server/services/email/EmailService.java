package com.waiter.server.services.email;

import java.io.IOException;

/**
 * Created by hovsep on 9/11/16.
 */
public interface EmailService {

    void send(String recipientEmail, String subject, String message) throws IOException;

    void send(String recipientEmail, String ccEmail, String subject, String message) throws IOException;
}
