package com.waiter.server.background.importer.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waiter.server.background.importer.parser.model.ParsedCategory;
import com.waiter.server.services.currency.Currency;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.location.dto.LocationDto;
import com.waiter.server.services.location.model.Location;
import com.waiter.server.services.menu.model.MenuDto;
import com.waiter.server.services.product.dto.ProductDto;
import com.waiter.server.services.product.dto.ProductPriceDto;
import com.waiter.server.services.venue.dto.VenueDto;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author shahenpoghosyan
 */
@Component
public class FooderaMenuParser implements Parser {

    private static final String CATEGORY_LIST_CLASS = "menu__categories__list-wrapper";
    private static final ObjectMapper mapper = new ObjectMapper();


    private Language defaultLanguage;
    private Currency defaultCurrency;
    private String defaultMenuName;

    public FooderaMenuParser(@Value("nl") Language defaultLanguage, @Value("EUR") Currency defaultCurrency, @Value("Menu!") String defaultMenuName) {
        this.defaultLanguage = defaultLanguage;
        this.defaultCurrency = defaultCurrency;
        this.defaultMenuName = defaultMenuName;
    }

    @Override
    public List<ParsedCategory> parseCategories(Document doc) throws IOException {

        Element element = doc.getElementsByClass(CATEGORY_LIST_CLASS).get(0);
        Elements categoryElements = element.child(0).children();
        List<ParsedCategory> categories = new ArrayList<>(categoryElements.size());
        for (Element categoryLI : categoryElements) {
            Element categoryA = categoryLI.child(0);
            ParsedCategory category = new ParsedCategory();
            category.setName(categoryA.html());
            category.setLanguage(defaultLanguage);
            category.setTags(new TreeSet<>());
            String productsRef = categoryA.attr("href").substring(1);
            category.setProductRef(productsRef);
            categories.add(category);
        }
        return categories;
    }

    @Override
    public List<ProductDto> parseCategoryProducts(Document doc, String productsRef) throws IOException {
        Element element = doc.getElementById(productsRef);
        Element productDiv = element.nextElementSibling();

        List<ProductDto> products = new ArrayList<>();
        while (productDiv != null && !"h3".equals(productDiv.tag().getName())) {
            String prodVal = productDiv.attr("data-object");
            JsonNode fooderaProduct = mapper.readTree(prodVal);
            ProductDto productDto = new ProductDto();
            productDto.setName(fooderaProduct.get("name").asText());
            productDto.setDescription(fooderaProduct.get("description").asText());
            productDto.setLanguage(defaultLanguage);
            productDto.setTags(new TreeSet<>());
            JsonNode variations = fooderaProduct.get("product_variations");

            Set<ProductPriceDto> prices = new HashSet<>();
            for (JsonNode variation : variations) {
                ProductPriceDto productPriceDto = new ProductPriceDto();
                productPriceDto.setPrice(variation.get("price").asDouble());
                productPriceDto.setName(variation.get("name").asText(""));
                prices.add(productPriceDto);
            }
            productDto.setProductPriceDtos(prices);

            products.add(productDto);
            productDiv = productDiv.nextElementSibling().nextElementSibling();
        }
        return products;
    }


    @Override
    public MenuDto parseMenu(Document doc) {
        MenuDto menuDto = new MenuDto();
        menuDto.setCurrency(defaultCurrency);
        menuDto.setName(defaultMenuName);
        menuDto.setLanguage(defaultLanguage);
        return menuDto;
    }

    @Override
    public VenueDto parseVenue(Document doc) {
        VenueDto venueDto = new VenueDto();

        String name = doc.getElementsByClass("hero-menu__info__headline").get(0).val();
        venueDto.setName(name);
        //todo implement location parsing

        return venueDto;
    }

    public LocationDto parseLocation(Document doc) {
        LocationDto locationDto = new LocationDto();
        locationDto.setCountry("Netherlands");
        locationDto.setCity("Amsterdam");
        locationDto.setCountryCode("NL");
        locationDto.setLatitude(52.375017);
        locationDto.setLongitude(4.898676);
        return locationDto;
    }

}
