package com.waiter.server.servlets;

import com.waiter.server.commons.APIError;
import com.waiter.server.commons.APIException;
import com.waiter.server.commons.entities.Company;
import com.waiter.server.commons.entities.User;
import com.waiter.server.repository.UserDAO;
import com.waiter.server.response.IResponseWriter;
import com.waiter.server.response.JsonResponseWriter;
import com.waiter.server.utils.MailClient;
import com.waiter.server.utils.paramparser.BaseParser;
import com.waiter.server.utils.paramparser.IParamParser;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.waiter.server.utils.FieldValidator.*;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static com.waiter.server.commons.ErrorCodes.*;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * Created by shahenpoghosyan on 7/14/15.
 */
public class RegistrationServlet extends BaseServlet {

    private static final Logger logger = Logger.getLogger(RegistrationServlet.class);
    private static final Executor executor = Executors.newFixedThreadPool(3);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(CONTEXT);
        UserDAO userDAO = (UserDAO) context.getBean("userJDBCTemplate");
        IResponseWriter<User> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = new BaseParser(req);

        try {
            String name = paramParser.get(NAME);
            String email = paramParser.get(EMAIL);
            String password = paramParser.get(PASSWORD);
            String phone = paramParser.get(COMPANY_PHONE);
            String companyName = paramParser.get(COMPANY_NAME);
            if (!validRequiredFields(name, email, password, companyName) || !isValidEmail(email)) {
                throw new APIException(SC_BAD_REQUEST,
                        new APIError(WRONG_REQUEST, "Wrong request parameters. "));
            }
            User user = new User()
                    .setName(name)
                    .setEmail(email)
                    .setPassword(DigestUtils.sha1Hex(password))
                    .setToken(UUID.randomUUID().toString())
                    .setHash(DigestUtils.sha1Hex(UUID.randomUUID().toString()))
                    .setCompany(new Company().setName(companyName).setPhone(phone));
            userDAO.create(user);
            writer.writeResponse(user);
            sendVerificationMail(user);
        } catch (APIException e) {
            logger.error(e.getError(), e);
            writer.writeError(e.getError());
        } catch (Exception e) {
            logger.error("something went wrong when adding tag to user. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    private void sendVerificationMail(final User user) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    new MailClient().send(user.getEmail(), "Verify the mail",
                            "http://localhost:8088/users/email/validation?hash=" + user.getHash());
                } catch (IOException e) {
                    logger.error("Error on sending mail. " + user);
                }
            }
        });
    }
}

