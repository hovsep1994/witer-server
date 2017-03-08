package com.waiter.server.background.importer;

import com.waiter.server.background.conf.ApplicationConf;
import com.waiter.server.background.importer.parser.Parser;
import com.waiter.server.background.importer.model.ParsedCategory;
import com.waiter.server.background.importer.model.ParsedProduct;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.currency.Currency;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.location.LocationService;
import com.waiter.server.services.location.dto.LocationDto;
import com.waiter.server.services.location.model.Location;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.menu.model.MenuDto;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.translation.model.TranslationType;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.dto.VenueDto;
import com.waiter.server.services.venue.model.Venue;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Component
@Transactional
public class MenuImporter {

    private static final Logger logger = LoggerFactory.getLogger(MenuImporter.class);

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

    @Autowired
    private SessionFactory sessionFactory;
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


    @Transactional
    public void importVenue(WebsiteTranslation orig, List<WebsiteTranslation> websiteTranslations, Currency currency, String venueImage, LocationDto venueLocation) throws IOException, ServiceException {

        long companyId = 1;

        Location location = locationService.create(venueLocation); //todo parse location also
        logger.info("Location created with id: {} ", location.getId());


        MenuDto menuDto = parser.parseMenu(orig.doc);
        menuDto.setLanguage(orig.language);
        menuDto.setCurrency(currency);
        Menu menu = menuService.create(companyId, menuDto);
        logger.info("Menu created with id: {} ", menu.getId());

        VenueDto venueDto = parser.parseVenue(orig.doc);
        venueDto.setCompanyId(companyId);
        venueDto.setLocationId(location.getId());
        venueDto.setMenuId(menu.getId());
        Venue venue = venueService.create(venueDto);

        menu.getVenues().add(venue);
        menu.getLanguages().add(orig.language);

        venueService.addImage(venue.getId(), makeStream(venueImage));
        logger.info("Venue created with id: {} ", venue.getId());

        for (ParsedCategory parsedCategory : parser.parseCategories(orig.doc)) {
            parsedCategory.setLanguage(orig.language);
            Category category = categoryService.create(parsedCategory, menu.getId());
            menu.getCategories().add(category);
            logger.info("Category created with id {} ", category.getId());

            List<ParsedProduct> productDtos = parser.parseCategoryProducts(orig.doc, parsedCategory.getProductRef());
            for (ParsedProduct productDto : productDtos) {
                productDto.setLanguage(orig.language);
                Product product = productService.create(category.getId(), productDto);
                category.getProducts().add(product);
                if(productDto.getImageUrl() != null) {
                    try {
                        productService.addImage(product.getId(), makeStream(productDto.getImageUrl()));
                    } catch (ServiceException e) {
                        logger.error("Excepion adding image to product.", e);
                    }
                }

                logger.info("Product created with id {} ", product.getId());
            }

            logger.info("Finished adding category products. category id: {} ", category.getId());
        }

        logger.info("Started to add translations");
        for (WebsiteTranslation translation : websiteTranslations) {
            addTranslation(translation, menu);
        }

    }

    @Transactional
    public void addTranslation(WebsiteTranslation translation, Menu menu) throws IOException {
        menu.getLanguages().add(translation.language);
        List<ParsedCategory> categories = parser.parseCategories(translation.doc);
        int i = 0;
        for (ParsedCategory parsedCategory : categories) {
            parsedCategory.setLanguage(translation.language);
            Category category = menu.getCategories().get(i);
            logger.info("Started translating category  with id {} ", category.getId());
            categoryService.addOrUpdateTranslation(category.getId(),
                    new TranslationDto(parsedCategory.getName(), translation.language, TranslationType.MANUAL));

            int j = 0;
            for (ParsedProduct product : parser.parseCategoryProducts(translation.doc, parsedCategory.getProductRef())) {
                productService.addOrUpdateTranslation(menu.getCategories().get(i).getProducts().get(j++).getId(),
                        new TranslationDto(product.getName(), translation.language, TranslationType.MANUAL),
                        new TranslationDto(product.getDescription(), translation.language, TranslationType.MANUAL));
            }
            logger.info("Finished translating category  with id {} ", category.getId());
            i++;
        }
    }

    private static InputStream makeStream(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.connect();
        return connection.getInputStream();
    }

    private static class WebsiteTranslation {

        public WebsiteTranslation(Language language, Document doc) {
            this.language = language;
            this.doc = doc;
        }

        private Language language;
        private Document doc;
    }

    public void test() {
        for (int i = 92; i <= 120; i++)
            try {
        productService.remove((long)i);

            }catch (Exception e) {

            }
    }

    public static void main(String[] args) throws IOException, ServiceException {

        String origUrl = "https://www.foodora.nl/en/restaurant/s9yt/cannibale-royale-lange-niezel";
        String nlUrl = "https://www.foodora.nl/restaurant/s9yt/cannibale-royale-lange-niezel";
        String venueImage = "https://volo-images.s3.amazonaws.com/production/nl/s9yt-listing.jpg?3";

        LocationDto locationDto = new LocationDto();
        locationDto.setCountry("Netherlands");
        locationDto.setCity("Amsterdam");
        locationDto.setCountryCode("NL");
        locationDto.setLatitude(52.375017);
        locationDto.setLongitude(4.898676);

        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConf.class);
        MenuImporter importer = (MenuImporter) context.getBean("menuImporter");
        importer.test();

//        WebsiteTranslation orig = new WebsiteTranslation(Language.en, Jsoup.connect(origUrl).userAgent(USER_AGENT).get());
//        WebsiteTranslation nlTranslation = new WebsiteTranslation(Language.nl, Jsoup.connect(nlUrl).userAgent(USER_AGENT).get());
//
//        importer.importVenue(orig, Collections.singletonList(nlTranslation), Currency.EUR, venueImage, locationDto);
    }

}
