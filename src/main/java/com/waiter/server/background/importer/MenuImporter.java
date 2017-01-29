package com.waiter.server.background.importer;

import com.waiter.server.background.conf.ApplicationConf;
import com.waiter.server.background.importer.parser.Parser;
import com.waiter.server.background.importer.parser.model.ParsedCategory;
import com.waiter.server.background.importer.parser.model.ParsedProduct;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.location.LocationService;
import com.waiter.server.services.location.dto.LocationDto;
import com.waiter.server.services.location.model.Location;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.menu.model.MenuDto;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.dto.VenueDto;
import com.waiter.server.services.venue.model.Venue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Component
public class MenuImporter {

    private static final Logger logger = LoggerFactory.getLogger(MenuImporter.class);

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
        logger.info("Location created with id: {} ", location.getId());



        MenuDto menuDto = parser.parseMenu(doc);
        Menu menu = menuService.create(companyId, menuDto);
        logger.info("Menu created with id: {} ", menu.getId());

        VenueDto venueDto = parser.parseVenue(doc);
        venueDto.setCompanyId(companyId);
        venueDto.setLocationId(location.getId());
        venueDto.setMenuId(menu.getId());
        Venue venue = venueService.create(venueDto);
        logger.info("Venue created with id: {} ", venue.getId());

        for (ParsedCategory parsedCategory : parser.parseCategories(doc)) {
            Category category = categoryService.create(parsedCategory, menu.getId());
            logger.info("Category created with id {} ", category.getId());

            List<ParsedProduct> productDtos = parser.parseCategoryProducts(doc, parsedCategory.getProductRef());
            for (ParsedProduct productDto : productDtos) {
                Product product = productService.create(category.getId(), productDto);
                if(productDto.getImageUrl() != null) {
                    try {
                        URLConnection connection = new URL(productDto.getImageUrl()).openConnection();
                        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                        connection.connect();
                        productService.addImage(product.getId(), connection.getInputStream());
                    } catch (ServiceException e) {
                        logger.error("Excepion adding image to product.", e);
                    }
                }

                logger.info("Product created with id {} ", product.getId());
            }

            logger.info("Finished adding category products. category id: {} ", category.getId());
        }


    }

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConf.class);
        MenuImporter importer = (MenuImporter) context.getBean("menuImporter");
        importer.importVenue("https://www.foodora.nl/en/restaurant/s9yt/cannibale-royale-lange-niezel");
    }

}
