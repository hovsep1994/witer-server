package com.waiter.server.servlets;

import com.waiter.server.commons.APIError;
import com.waiter.server.commons.APIException;
import com.waiter.server.commons.entities.Company;
import com.waiter.server.commons.entities.Location;
import com.waiter.server.commons.entities.Menu;
import com.waiter.server.commons.entities.Venue;
import com.waiter.server.db.CompanyDAO;
import com.waiter.server.db.VenueDAO;
import com.waiter.server.db.sql.VenueJDBCTemplate;
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
import static com.waiter.server.utils.FieldValidator.*;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * @author shahenpoghosyan
 */
public class AddVenueServlet extends BaseServlet {

    private static final Logger logger = Logger.getLogger(AddVenueServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(CONTEXT);
        VenueDAO venueJDBCTemplate = (VenueJDBCTemplate) context.getBean("venueJDBCTemplate");
        CompanyDAO companyDAO = (CompanyDAO) context.getBean("companyJDBCTemplate");
        IResponseWriter<Venue> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = parserFactory.newParser(req);
        try {
            String key = paramParser.get(KEY);
            String countryCode = paramParser.get(COUNTRY);
            String city = paramParser.get(CITY);
            String address = paramParser.get(ADDRESS);
            double latitude = paramParser.getDouble(LATITUDE);
            double longitude = paramParser.getDouble(LONGITUDE);
            long menuId = paramParser.getLong(MENU_ID, 0);

            logger.debug("key: " + key);

            Company company = companyDAO.authenticate(key);
            if (!validRequiredFields(countryCode, city, address))
                throw new APIException(SC_BAD_REQUEST,
                        new APIError(WRONG_REQUEST, "Wrong request parameters. "));
            Venue venue = new Venue().setLocation(new Location().setCountry(countryCode)
                    .setCity(city).setStreet(address).setLatitude(latitude)
                    .setLongitude(longitude))
                    .setCompany(company);

            if(menuId != 0) {
                venue.setMenu(new Menu().setId(menuId));
            }
            int id = venueJDBCTemplate.create(venue);
            writer.writeResponse(venue.setId(id));
        } catch (APIException e) {
            logger.error(e.getError(), e);
            writer.writeError(e.getError());
        } catch (Exception e) {
            logger.error("something went wrong when adding tag to user. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }

    }
}
