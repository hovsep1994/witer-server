package com.waiter.server.servlets;

import com.waiter.server.commons.APIError;
import com.waiter.server.commons.APIException;
import com.waiter.server.commons.entities.Company;
import com.waiter.server.commons.entities.Group;
import com.waiter.server.commons.entities.Menu;
import com.waiter.server.commons.entities.Tag;
import com.waiter.server.db.sql.CompanyJDBCTemplate;
import com.waiter.server.db.sql.GroupJDBCTemplate;
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
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * @author shahenpoghosyan
 */
public class CreateGroupServlet extends BaseServlet {

    private static final Logger LOG = Logger.getLogger(CreateGroupServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(CONTEXT);
        GroupJDBCTemplate groupJDBCTemplate = (GroupJDBCTemplate) context.getBean("groupJDBCTemplate");
        IResponseWriter<Group> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = new BaseParser(req);

        try {
            String name = paramParser.get(NAME);
            List<String> tags = paramParser.getList("tags");
            int menu_id = paramParser.getInt("menu_id");

            Group group = new Group()
                    .setName(name)
                    .setTags(Tag.parseTags(tags))
                    .setMenu(new Menu().setId(menu_id));

            groupJDBCTemplate.create(group);
            writer.writeResponse(group);
        } catch (Exception e) {
            LOG.error("something went wrong when adding tag to user. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

}
