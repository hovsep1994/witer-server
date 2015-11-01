package com.waiter.server.servlets;

import com.waiter.server.commons.entities.Menu;
import com.waiter.server.db.sql.MenuJDBCTemplate;
import com.waiter.server.response.IResponseWriter;
import com.waiter.server.response.JsonResponseWriter;
import com.waiter.server.utils.paramparser.BaseParser;
import com.waiter.server.utils.paramparser.IParamParser;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * Created by Admin on 10/26/2015.
 */
public class GetMenuServlet extends BaseServlet {

    private static final Logger LOG = Logger.getLogger(GetMenuServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(CONTEXT);
        MenuJDBCTemplate menuJDBCTemplate = (MenuJDBCTemplate) context.getBean("menuJDBCTemplate");
        IResponseWriter<Menu> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = new BaseParser(req);

        try {
            int menu_id = paramParser.getInt("menu_id", 1);
            if (menu_id == -1) {
                resp.getWriter().write("No menu with id " + menu_id);
            } else {
                Menu menu = menuJDBCTemplate.get(menu_id);
                LOG.info(menu.toString());
                writer.writeResponse(menu);
            }
        } catch (Exception e) {
            LOG.error("something went wrong when adding tag to user. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}
