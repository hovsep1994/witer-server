package com.waiter.server.background.importer.parser;

import com.waiter.server.background.importer.parser.model.ParsedCategory;
import com.waiter.server.services.menu.model.MenuDto;
import com.waiter.server.services.product.dto.ProductDto;
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
    List<ProductDto> parseCategoryProducts(Document doc, String productsRef) throws IOException;
    VenueDto parseVenue(Document doc);
}

