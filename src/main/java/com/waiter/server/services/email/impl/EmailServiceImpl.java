package com.waiter.server.services.email.impl;

import com.sun.mail.smtp.SMTPTransport;
import com.waiter.server.services.email.EmailService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * @author shahenpoghosyan
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = Logger.getLogger(EmailServiceImpl.class);

    private static final String GMAIL_SMTP_HOST = "smtp.gmail.com";

    private static final String GMAIL_SMTP_PORT = "465";
    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    private static final String password = "tuynparol";
    private static final String username = "testwaiter11";
    private static final String from = username + "@gmail.com";

    @Override
    public void send(String recipientEmail, String subject, String message) throws IOException {
        send(recipientEmail, null, subject, message);
    }

    @Override
    public void send(String recipientEmail, String ccEmail, String subject, String message) throws IOException {
        try {

            Properties props = System.getProperties();
            props.setProperty("mail.smtps.host", GMAIL_SMTP_HOST);
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", GMAIL_SMTP_PORT);
            props.setProperty("mail.smtp.socketFactory.port", GMAIL_SMTP_PORT);
            props.setProperty("mail.smtps.auth", "true");

            Session session = Session.getInstance(props, null);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));
            if (ccEmail != null && !ccEmail.isEmpty()) {
                msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
            }
            msg.setSubject(subject);
            msg.setText(message, "utf-8");
            msg.setSentDate(new Date());

            SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
            t.connect(GMAIL_SMTP_HOST, username, password);
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
        } catch (MessagingException e) {
            LOGGER.error("Exception when sending mail. ", e);
            throw new IOException(e);
        }
    }
}
