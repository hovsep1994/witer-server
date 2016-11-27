package com.waiter.server.background.importer;

import com.waiter.server.background.conf.ApplicationConf;
import com.waiter.server.background.importer.parser.Parser;
import com.waiter.server.background.importer.parser.model.ParsedCategory;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.location.LocationService;
import com.waiter.server.services.location.dto.LocationDto;
import com.waiter.server.services.location.model.Location;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.menu.model.MenuDto;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.dto.ProductDto;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.dto.VenueDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Component
public class MenuImporter {

    @Autowired
    private Parser parser;
    @Autowired
    private VenueService venueService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private LocationService locationService;


    public void importVenue(String uri) throws IOException {

        long companyId = 1;
        Document doc = Jsoup.connect(uri)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36" +
                        " (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .get();

        LocationDto locationDto = parser.parseLocation(doc);
        Location location = locationService.create(locationDto);

        VenueDto venueDto = parser.parseVenue(doc);
        venueDto.setCompanyId(companyId);
        venueDto.setLocationId(location.getId());
        venueService.create(venueDto);

        MenuDto menuDto = parser.parseMenu(doc);
        Menu menu = menuService.create(companyId, menuDto);

        for (ParsedCategory parsedCategory : parser.parseCategories(doc)) {
            Category category = categoryService.create(parsedCategory, menu.getId());

            List<ProductDto> productDtos = parser.parseCategoryProducts(doc, parsedCategory.getProductRef());
            for (ProductDto productDto : productDtos) {
                productService.create(category.getId(), productDto);
            }
        }

    }

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConf.class);
        MenuImporter importer = (MenuImporter) context.getBean("menuImporter");
        importer.importVenue("https://www.foodora.nl/en/restaurant/s9yt/cannibale-royale-lange-niezel");
    }

}
