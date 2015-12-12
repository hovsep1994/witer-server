package com.waiter.server.servlets;

import com.waiter.server.commons.APIError;
import com.waiter.server.commons.APIException;
import com.waiter.server.commons.entities.*;
import com.waiter.server.repository.MenuDAO;
import com.waiter.server.repository.UserDAO;
import com.waiter.server.repository.sql.MenuRepository;
import com.waiter.server.response.IResponseWriter;
import com.waiter.server.response.JsonResponseWriter;
import com.waiter.server.utils.paramparser.IParamParser;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.waiter.server.commons.ErrorCodes.WRONG_REQUEST;
import static com.waiter.server.utils.FieldValidator.validRequiredFields;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * @author shahenpoghosyan
 */
public class AddMenuServlet extends BaseServlet {

    private static final Logger logger = Logger.getLogger(AddMenuServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(CONTEXT);
        MenuDAO menuJDBCTemplate = (MenuRepository) context.getBean("menuJDBCTemplate");
        UserDAO userDAO = (UserDAO) context.getBean("userJDBCTemplate");
        IResponseWriter<Menu> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = parserFactory.newParser(req);
        try {
            String name = paramParser.get(NAME);
            String key = paramParser.getString(KEY, "");
            User user = userDAO.authenticate(key);

            if (!validRequiredFields(name))
                throw new APIException(SC_BAD_REQUEST,
                        new APIError(WRONG_REQUEST, "Wrong request parameters. "));

            Menu menu = new Menu().setName(name);
            menu.setCompany(user.getCompany());
            int id = menuJDBCTemplate.create(menu);
            writer.writeResponse(menu.setId(id));
        } catch (APIException e) {
            logger.debug(e.getError(), e);
            writer.writeError(e.getError());
        } catch (Exception e) {
            logger.error("something went wrong when adding tag to user. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }

    }
}
