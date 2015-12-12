package com.waiter.server.servlets;

import com.waiter.server.commons.entities.City;
import com.waiter.server.repository.LocationsDAO;
import com.waiter.server.repository.sql.LocationsRepository;
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
public class SearchCitiesServlet extends BaseServlet {

    private static final Logger logger = Logger.getLogger(SearchCitiesServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(CONTEXT);
        LocationsDAO locationJDBCTemplate = (LocationsRepository) context.getBean("locationJDBCTemplate");
        IResponseWriter<City> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = new BaseParser(req);

        try {
            String query = paramParser.getString(QUERY, "");
            logger.info(query);
            String countryCode = paramParser.getString(COUNTRY_CODE, "");
            int limit = paramParser.getInt(LIMIT, -1);
            List<City> cities = locationJDBCTemplate.searchCities(query, countryCode, limit);
            writer.writeListResponse(cities);
        } catch (Exception e) {
            logger.error("something went wrong when adding tag to user. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}
