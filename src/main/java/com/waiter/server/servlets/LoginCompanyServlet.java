package com.waiter.server.servlets;

import com.waiter.server.commons.APIException;
import com.waiter.server.commons.entities.Company;
import com.waiter.server.db.CompanyDAO;
import com.waiter.server.db.sql.CompanyJDBCTemplate;
import com.waiter.server.response.IResponseWriter;
import com.waiter.server.response.JsonResponseWriter;
import com.waiter.server.utils.paramparser.IParamParser;
import com.waiter.server.utils.paramparser.ParserFactory;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * @author shahenpoghosyan
 */
public class LoginCompanyServlet extends BaseServlet {

    private static final Logger logger = Logger.getLogger(LoginCompanyServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(CONTEXT);
        CompanyDAO companyDAO = (CompanyJDBCTemplate) context.getBean("companyJDBCTemplate");
        IResponseWriter<Company> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = new ParserFactory().newParser(req);
        try {
            String email = paramParser.getString(LOGIN, "");
            String password = paramParser.getString(PASSWORD, "");
            Company company = companyDAO.login(email, DigestUtils.sha1Hex(password));
            writer.writeResponse(company);
        } catch (APIException e) {
            logger.debug(e.getError(), e);
            writer.writeError(e.getError());
        } catch (Exception e) {
            logger.error("something went wrong when logging in. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }

    }
}
