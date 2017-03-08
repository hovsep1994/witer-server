package com.waiter.server.background.importer.parser;

import com.waiter.server.background.importer.model.ParsedCategory;
import com.waiter.server.background.importer.model.ParsedProduct;
import com.waiter.server.background.importer.model.ParsedVenue;
import com.waiter.server.services.location.dto.LocationDto;
import com.waiter.server.services.menu.model.MenuDto;
import com.waiter.server.services.venue.dto.VenueDto;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
public interface Parser {

    List<ParsedCategory> parseCategories(Document doc) throws IOException;
    MenuDto parseMenu(Document doc) throws IOException;
    List<ParsedProduct> parseCategoryProducts(Document doc, String productsRef) throws IOException;
    VenueDto parseVenue(Document doc);

    List<ParsedVenue> parseVenues(Document doc);

    LocationDto parseLocation(Document document);
}

