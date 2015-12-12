package com.waiter.server.servlets;

import com.waiter.server.commons.APIException;
import com.waiter.server.commons.entities.User;
import com.waiter.server.repository.UserDAO;
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
public class LoginServlet extends BaseServlet {

    private static final Logger logger = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(CONTEXT);
        UserDAO userDAO = (UserDAO) context.getBean("userJDBCTemplate");
        IResponseWriter<User> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = new ParserFactory().newParser(req);
        try {
            String email = paramParser.getString(LOGIN, "");
            String password = paramParser.getString(PASSWORD, "");
            User user = userDAO.login(email, DigestUtils.sha1Hex(password));
            writer.writeResponse(user);
        } catch (APIException e) {
            logger.debug(e.getError(), e);
            writer.writeError(e.getError());
        } catch (Exception e) {
            logger.error("something went wrong when logging in. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }

    }
}
