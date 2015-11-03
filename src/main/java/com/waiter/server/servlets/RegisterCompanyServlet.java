package com.waiter.server.servlets;

import com.waiter.server.commons.APIError;
import com.waiter.server.commons.APIException;
import com.waiter.server.commons.entities.Company;
import com.waiter.server.db.CompanyDAO;
import com.waiter.server.db.sql.CompanyJDBCTemplate;
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
public class RegisterCompanyServlet extends BaseServlet {

    private static final Logger logger = Logger.getLogger(RegisterCompanyServlet.class);
    private static final Executor executor = Executors.newFixedThreadPool(3);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(CONTEXT);
        CompanyDAO companyJDBCTemplate = (CompanyJDBCTemplate) context.getBean("companyJDBCTemplate");
        IResponseWriter<Company> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = new BaseParser(req);

        try {
            String name = paramParser.get(NAME);
            String email = paramParser.get(EMAIL);
            String phone = paramParser.get(PHONE);
            String password = paramParser.get(PASSWORD);
            logger.debug(name + " " + email + " " + password);
            if (!validRequiredFields(name, email, password) || !isValidEmail(email)) {
                throw new APIException(SC_BAD_REQUEST,
                        new APIError(WRONG_REQUEST, "Wrong request parameters. "));
            }
            Company company = new Company()
                    .setName(name)
                    .setMail(email)
                    .setPhone(phone)
                    .setPassword(DigestUtils.sha1Hex(password))
                    .setToken(UUID.randomUUID().toString())
                    .setHash(DigestUtils.sha1Hex(UUID.randomUUID().toString()));
            companyJDBCTemplate.create(company);
            writer.writeResponse(company);
            sendVerificationMail(company);
        } catch (APIException e) {
            logger.error(e.getError(), e);
            writer.writeError(e.getError());
        } catch (Exception e) {
            logger.error("something went wrong when adding tag to user. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    private void sendVerificationMail(final Company company) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    new MailClient().send(company.getMail(), "Verify the mail",
                            "http://localhost:8088/companies/email/validation?hash=" + company.getHash());
                } catch (IOException e) {
                    logger.error("Error on sending mail. " + company);
                }
            }
        });
    }
}
