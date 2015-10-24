package com.waiter.server.servlets;

import com.waiter.server.commons.entities.Venue;
import com.waiter.server.response.IResponseWriter;
import com.waiter.server.response.JsonResponseWriter;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Admin on 10/18/2015.
 */
public class GetVenueServlet extends BaseServlet {
    private static final Logger LOG = Logger.getLogger(RegisterVenueServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        IResponseWriter<Venue> writer = new JsonResponseWriter<>(resp.getWriter());
        writer.writeResponse(new Venue().createVenue().setName("resp"));


    }
}
