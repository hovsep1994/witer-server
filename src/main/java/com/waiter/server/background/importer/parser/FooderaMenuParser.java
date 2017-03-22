package com.waiter.server.background.importer.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.waiter.server.background.importer.model.ParsedCategory;
import com.waiter.server.background.importer.model.ParsedProduct;
import com.waiter.server.background.importer.model.ParsedVenue;
import com.waiter.server.services.location.dto.LocationDto;
import com.waiter.server.services.menu.model.MenuDto;
import com.waiter.server.services.product.dto.ProductPriceDto;
import com.waiter.server.services.venue.dto.ScheduleDto;
import com.waiter.server.services.venue.dto.VenueDto;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.*;

/**
 * @author shahenpoghosyan
 */
@Component
public class FooderaMenuParser implements Parser {

    private static final Logger logger = LoggerFactory.getLogger(FooderaMenuParser.class);
    private static final String CATEGORY_LIST_CLASS = "menu__categories__list-wrapper";
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private GeoApiContext geoApiContext;

    @Override
    public List<ParsedCategory> parseCategories(Document doc) throws IOException {

        Element element = doc.getElementsByClass(CATEGORY_LIST_CLASS).get(0);
        Elements categoryElements = element.child(0).children();
        List<ParsedCategory> categories = new ArrayList<>(categoryElements.size());
        for (Element categoryLI : categoryElements) {
            Element categoryA = categoryLI.child(0);
            ParsedCategory category = new ParsedCategory();
            category.setName(categoryA.html());
            category.setTags(new TreeSet<>());
            String productsRef = categoryA.attr("href").substring(1);
            category.setProductRef(productsRef);
            categories.add(category);
        }
        return categories;
    }

    @Override
    public List<ParsedProduct> parseCategoryProducts(Document doc, String productsRef) throws IOException {
        Element element = doc.getElementById(productsRef);
        Element productDiv = element.nextElementSibling();

        List<ParsedProduct> products = new ArrayList<>();
        while (productDiv != null && !"h3".equals(productDiv.tag().getName())) {
            String prodVal = productDiv.attr("data-object");
            JsonNode fooderaProduct = mapper.readTree(prodVal);
            ParsedProduct productDto = new ParsedProduct();
            productDto.setName(fooderaProduct.get("name").asText());
            productDto.setDescription(fooderaProduct.get("description").asText());
            productDto.setTags(new TreeSet<>());

            if (fooderaProduct.get("file_path") != null && !fooderaProduct.get("file_path").isNull())
                productDto.setImageUrl(fooderaProduct.get("file_path").asText().replaceAll("\\\\", "").substring(32));

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
        menuDto.setName("Menu!");
        return menuDto;
    }

    /**
     * Used to parse venue from venue page. Not used currently
     *
     * @param doc
     * @return
     */
    @Override
    public VenueDto parseVenue(Document doc) {
        VenueDto venueDto = new VenueDto();

        String name = doc.getElementsByClass("hero-menu__info__headline").get(0).childNode(0).toString();
        venueDto.setName(name);

        return venueDto;
    }


    @Override
    public List<ParsedVenue> parseVenues(Document doc) {
        List<ParsedVenue> venueDtos = new ArrayList<>();
        Elements hrefs = new Elements();
        Elements open = doc.getElementsByClass("restaurants__list--open");
        if (!open.isEmpty()) {
            hrefs.addAll(open.get(0).children());
        }
        Elements closed = doc.getElementsByClass("restaurants__list--closed");
        if (!closed.isEmpty()) {
            hrefs.addAll(closed.get(0).children());
        }

        for (Element a : hrefs) {
            try {
                String url = a.attr("href");
                JsonNode venueJson = mapper.readTree(a.getElementsByTag("div").get(0).attr("data-vendor"));

                ParsedVenue venueDto = new ParsedVenue();
                venueDto.setName(venueJson.get("name").asText());
                venueDto.setSourceUrl(url);
                venueDto.setImageUrl(venueJson.get("image_high_resolution").asText());
                if (venueJson.get("logo") != null && !venueJson.get("logo").isNull()) {
                    venueDto.setLogo(venueJson.get("logo").asText().substring(32));
                }

                //update location
                LocationDto locationDto = new LocationDto();
                locationDto.setCountry("Netherlands");
                locationDto.setCountryCode("NL");
                locationDto.setCity(venueJson.get("city").get("name").asText());
                locationDto.setLatitude(venueJson.get("latitude").asDouble());
                locationDto.setLongitude(venueJson.get("longitude").asDouble());
                locationDto.setZip(venueJson.get("post_code").asText());
                locationDto.setStreet(venueJson.get("address").asText());
                venueDto.setLocationDto(locationDto);

                //update open hours
                JsonNode schedules = venueJson.get("schedules");
                if (schedules != null && !schedules.isNull()) {
                    List<ScheduleDto> openHours = new ArrayList<>();
                    for (JsonNode schedule : schedules) {
                        ScheduleDto scheduleDto = new ScheduleDto();
                        scheduleDto.setStart(parseToDayTime(schedule.get("opening_time").asText()));
                        scheduleDto.setEnd(parseToDayTime(schedule.get("closing_time").asText()));
                        scheduleDto.setDayOfWeek(DayOfWeek.of(schedule.get("weekday").asInt()));
                        openHours.add(scheduleDto);
                    }
                    venueDto.setOpenHours(openHours);
                }

                venueDtos.add(venueDto);

            } catch (Exception e) {
                logger.error("Error parsing venue. ", e);
            }
        }
        return venueDtos;
    }

    public LocationDto parseLocation(Document doc) {
        Element element = doc.getElementsByClass("hero-menu__info-extra__address").get(0);

        try {
            LocationDto locationDto = new LocationDto();

            locationDto.setCity(element.attr("data-city"));
            locationDto.setStreet(element.attr("data-street"));
            locationDto.setZip(element.attr("data-postcode"));

            String locationString = locationDto.getStreet() + ", " + locationDto.getZip() + " " + locationDto.getCity();
            GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, locationString).await();
            LatLng latLng = results[0].geometry.location;
            locationDto.setLatitude(latLng.lat);
            locationDto.setLongitude(latLng.lng);
            return locationDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static int parseToDayTime(String dayTime) {
        int hour = Integer.valueOf(dayTime.split(":")[0]);
        int minute = Integer.valueOf(dayTime.split(":")[1]);
        int time = hour * 3600 * 1000;
        time += minute * 60 * 1000;
        return time;
    }


}
