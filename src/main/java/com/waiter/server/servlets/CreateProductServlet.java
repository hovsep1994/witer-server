package com.waiter.server.servlets;

import com.waiter.server.commons.entities.Group;
import com.waiter.server.commons.entities.Menu;
import com.waiter.server.commons.entities.Product;
import com.waiter.server.commons.entities.Tag;
import com.waiter.server.db.sql.GroupJDBCTemplate;
import com.waiter.server.db.sql.MenuJDBCTemplate;
import com.waiter.server.db.sql.ProductJDBTemplate;
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
 * Created by Admin on 10/28/2015.
 */
public class CreateProductServlet extends BaseServlet {

    private static final Logger LOG = Logger.getLogger(CreateProductServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(CONTEXT);
        ProductJDBTemplate productJDBTemplate = (ProductJDBTemplate) context.getBean("productJDBTemplate");
        IResponseWriter<Product> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = new BaseParser(req);

        try {
            String name = paramParser.get(NAME);
            String description = paramParser.get("description");
            List<String> tags = paramParser.getList("tags");
            double price = paramParser.getDouble("price");
            int group_id = paramParser.getInt("group_id");

            Product product = new Product()
                    .setName(name)
                    .setTags(Tag.parseTags(tags))
                    .setDescription(description)
                    .setPrice(price)
                    .setGroup(new Group().setId(group_id));

            productJDBTemplate.create(product);
            writer.writeResponse(product);
        } catch (Exception e) {
            LOG.error("something went wrong when adding tag to user. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

}
