package com.waiter.server.servlets;

import com.waiter.server.commons.APIError;
import com.waiter.server.commons.APIException;
import com.waiter.server.commons.entities.Venue;
import com.waiter.server.db.sql.VenueJDBCTemplate;
import com.waiter.server.response.IResponseWriter;
import com.waiter.server.response.JsonResponseWriter;
import com.waiter.server.utils.BaseParser;
import com.waiter.server.utils.IParamParser;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static com.waiter.server.utils.FieldValidator.*;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static com.waiter.server.commons.ErrorCodes.*;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * Created by shahenpoghosyan on 7/14/15.
 */
public class RegisterVenueServlet extends BaseServlet {

    private static final Logger LOG = Logger.getLogger(RegisterVenueServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ApplicationContext context = (ApplicationContext) getServletContext().
                getAttribute(CONTEXT);
        VenueJDBCTemplate venueJDBCTemplate = (VenueJDBCTemplate) context.getBean("venueJDBCTemplate");
        IResponseWriter<Venue> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = new BaseParser(req);

        try {
            String name = paramParser.get(NAME);
            String email = paramParser.get(EMAIL);
            if (checkRequiredFields(name, email) && isValidEmail(email)) {
                throw new APIException(SC_BAD_REQUEST,
                        new APIError(WRONG_REQUEST, "Wrong request parameters. "));
            }
            Venue venue = new Venue().createVenue().setName(name).setOwnerEmail(email)
                    .setCode(UUID.randomUUID().toString());
            venueJDBCTemplate.create(venue);
            writer.writeResponse(venue);
        } catch (APIException e) {
            LOG.error(e.getError(), e);
            writer.writeError(e.getError());
        } catch (Exception e) {
            LOG.error("something went wrong when adding tag to user. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}
