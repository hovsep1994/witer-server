package com.waiter.server.servlets;

import com.waiter.server.commons.entities.City;
import com.waiter.server.commons.entities.Product;
import com.waiter.server.db.LocationsDAO;
import com.waiter.server.db.ProductDAO;
import com.waiter.server.db.sql.LocationsJDBCTemplate;
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
 * Created by Admin on 11/3/2015.
 */
public class SearchProductsServlet extends BaseServlet {

    private static final Logger logger = Logger.getLogger(SearchProductsServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(CONTEXT);
        ProductDAO productDAO = (ProductJDBTemplate) context.getBean("productJDBTemplate");
        IResponseWriter<Product> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = new BaseParser(req);

        try {
            double latitude = paramParser.getDouble("lat");
            double longitude = paramParser.getDouble("lon");
            String query = paramParser.getString(QUERY, "");

            List<Product> products = productDAO.search(query, latitude, longitude);
            writer.writeListResponse(products);

        } catch (Exception e) {
            logger.error("something went wrong when adding tag to user. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}
