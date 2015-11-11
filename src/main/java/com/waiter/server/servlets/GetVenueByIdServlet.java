package com.waiter.server.servlets;

import com.waiter.server.commons.entities.Menu;
import com.waiter.server.commons.entities.Venue;
import com.waiter.server.db.VenueDAO;
import com.waiter.server.db.sql.MenuJDBCTemplate;
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

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * Created by Admin on 11/2/2015.
 */
public class GetVenueByIdServlet extends BaseServlet {
    private static final Logger LOG = Logger.getLogger(GetVenueByIdServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(CONTEXT);
        VenueDAO venueDAO = (VenueJDBCTemplate) context.getBean("venueJDBCTemplate");
        IResponseWriter<Venue> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = new BaseParser(req);

        try {
            int venue_id = paramParser.getInt("venue_id", 1);
            if (venue_id == -1) {
                resp.getWriter().write("Error venue id is null");
            } else {
                Venue venue = venueDAO.get(venue_id);
                LOG.info(venue.toString());
                writer.writeResponse(venue);
            }
        } catch (Exception e) {
            LOG.error("something went wrong when adding tag to user. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}
