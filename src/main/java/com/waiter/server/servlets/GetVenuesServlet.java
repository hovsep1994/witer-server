package com.waiter.server.servlets;

import com.waiter.server.commons.entities.Venue;
import com.waiter.server.db.VenueDAO;
import com.waiter.server.db.sql.VenueJDBCTemplate;
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
 * Created by Admin on 11/8/2015.
 */
public class GetVenuesServlet extends BaseServlet {

    private static final Logger LOG = Logger.getLogger(GetVenuesServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(CONTEXT);
        VenueDAO venueDAO = (VenueJDBCTemplate) context.getBean("venueJDBCTemplate");
        IResponseWriter<List<Venue>> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = new BaseParser(req);

        try {
            double latitude = paramParser.getDouble("lat");
            double longitude = paramParser.getDouble("lon");
            String name = paramParser.getString("name", "");

            List<Venue> venues = venueDAO.get(name, latitude, longitude);
            LOG.info(venues.toString());
            writer.writeResponse(venues);

        } catch (Exception e) {
            LOG.error("something went wrong when adding tag to user. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}
