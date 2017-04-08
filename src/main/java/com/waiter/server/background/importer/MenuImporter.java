package com.waiter.server.background.importer;

import com.waiter.server.background.conf.ApplicationConf;
import com.waiter.server.background.importer.model.ParsedVenue;
import com.waiter.server.background.importer.parser.Parser;
import com.waiter.server.background.importer.model.ParsedCategory;
import com.waiter.server.background.importer.model.ParsedProduct;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.currency.Currency;
import com.waiter.server.services.gallery.model.GalleryImageType;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shahenpoghosyan
 */
@Component
public class MenuImporter {

    private static final Logger logger = LoggerFactory.getLogger(MenuImporter.class);

    private static final String baseUrl = "https://www.foodora.nl";
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


    public void importVenues(Document doc, Currency currency, String country, String countryCode,
                             Language origLanguage, List<Language> languages) throws IOException, ServiceException {
        List<ParsedVenue> venues = parser.parseVenues(doc);
        for (ParsedVenue venue : venues) {
            try {
                logger.info("Start importing venue; ");
                importVenue(venue, currency, country, countryCode, origLanguage, languages);
            } catch (Exception e) {
                logger.error("Failed to import venue. ", e);
            }
        }
    }

    @Transactional
    public void importVenue(ParsedVenue venueDto, Currency currency, String country, String countryCode,
                            Language origLanguage, List<Language> languages) throws IOException, ServiceException {

        long companyId = 1;
        WebsiteTranslation orig = new WebsiteTranslation(origLanguage, Jsoup.connect(baseUrl + venueDto.getSourceUrl()).userAgent(USER_AGENT).get());
        List<WebsiteTranslation> websiteTranslations = languages.stream().map(l -> {
            try {
                return new WebsiteTranslation(l, Jsoup.connect(baseUrl + "/" + l + venueDto.getSourceUrl()).userAgent(USER_AGENT).get());
            } catch (IOException e) {
                return null;
            }
        }).collect(Collectors.toList());


        LocationDto venueLocation = venueDto.getLocationDto();
        venueLocation.setCountry(country);
        venueLocation.setCountryCode(countryCode);
        Location location = locationService.create(venueLocation);
        logger.info("Location created with id: {} ", location.getId());

        MenuDto menuDto = parser.parseMenu(orig.doc);
        menuDto.setLanguage(orig.language);
        menuDto.setCurrency(currency);
        Menu menu = menuService.create(companyId, menuDto);
        logger.info("Menu created with id: {} ", menu.getId());

        venueDto.setCompanyId(companyId);
        venueDto.setLocationId(location.getId());
        venueDto.setMenuId(menu.getId());
        Venue venue = venueService.create(venueDto);
        saveDocument("venue:" + venue.getId(), orig.doc);

        menu.getVenues().add(venue);
        menu.getLanguages().add(orig.language);

        venueService.addImage(venue.getId(), makeStream(venueDto.getImageUrl()));
        venueService.addImage(venue.getId(), makeStream(venueDto.getImageUrl()), GalleryImageType.LOGO);
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
                if (productDto.getImageUrl() != null) {
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
            saveDocument("venue:" + venue.getId() + ":" + translation.language, translation.doc);
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

    private static void saveDocument(String name, Document document) throws FileNotFoundException {
        String baseUrl = "/tmp/";
        try(  PrintWriter out = new PrintWriter(baseUrl + name + ".out" )  ){
            out.println(document.toString());
        }

    }

    public static void main(String[] args) throws IOException, ServiceException {

        String country = "Netherlands";
        String countryCode = "NL";
        String cityVenues = "https://www.foodora.nl/city/amsterdam";

        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConf.class);
        MenuImporter importer = (MenuImporter) context.getBean("menuImporter");

        Document document = Jsoup.connect(cityVenues).userAgent(USER_AGENT).get();
        saveDocument("amsterdam", document);
        importer.importVenues(document, Currency.EUR, countryCode, country, Language.nl, Collections.singletonList(Language.en));

    }



}
