package com.waiter.server.background.importer.parsers.foodera;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waiter.server.background.importer.parsers.foodera.model.FooderaCategory;
import com.waiter.server.services.currency.Currency;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.model.MenuDto;
import com.waiter.server.services.product.dto.ProductDto;
import com.waiter.server.services.product.dto.ProductPriceDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * @author shahenpoghosyan
 */
public class FooderaMenuParser {

    private static final String CATEGORY_LIST_CLASS = "menu__categories__list-wrapper";
    private static final ObjectMapper mapper = new ObjectMapper();

    public List<FooderaCategory> parseCategories(Document doc, Language language) throws IOException {

        Element element = doc.getElementsByClass(CATEGORY_LIST_CLASS).get(0);
        Elements categoryElemets = element.child(0).children();
        List<FooderaCategory> categories = new ArrayList<>(categoryElemets.size());
        for (Element categoryLI : categoryElemets) {
            Element categoryA = categoryLI.child(0);
            FooderaCategory category = new FooderaCategory();
            category.setName(categoryA.html());
            category.setLanguage(language);
            String productsRef = categoryA.attr("href").substring(1);
            category.setProductRef(productsRef);
            categories.add(category);
        }
        return categories;
    }

    public List<ProductDto> parseCategoryProducts(FooderaCategory category, Document doc, Language language) throws IOException {
        Element element = doc.getElementById(category.getProductRef());
        Element productDiv = element.nextElementSibling();

        List<ProductDto> products = new ArrayList<>();
        while (!"h3".equals(productDiv.tag().getName())) {
            String prodVal = productDiv.attr("data-object");
            JsonNode fooderaProduct = mapper.readTree(prodVal);
            ProductDto productDto = new ProductDto();
            productDto.setName(fooderaProduct.get("name").asText());
            productDto.setDescription(fooderaProduct.get("description").asText());
            productDto.setLanguage(language);
            JsonNode variations = fooderaProduct.get("product_variations");

            Set<ProductPriceDto> prices = new HashSet<>();
            for (JsonNode variation : variations) {
                ProductPriceDto productPriceDto = new ProductPriceDto();
                productPriceDto.setPrice(variation.get("price").asDouble());
                productPriceDto.setName(variation.get("name").textValue());
                prices.add(productPriceDto);
            }
            productDto.setProductPriceDtos(prices);

            products.add(productDto);
            productDiv = productDiv.nextElementSibling().nextElementSibling();
        }
        return products;
    }


    public MenuDto parseMenu(String menuName, Language language, Currency currency) {
        MenuDto menuDto = new MenuDto();
        menuDto.setCurrency(Currency.EUR);
        menuDto.setName(menuName);
        menuDto.setLanguage(language);
        return menuDto;
    }


    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.foodora.nl/en/restaurant/s9yt/cannibale-royale-lange-niezel")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .get();
        List<FooderaCategory> c = new FooderaMenuParser().parseCategories(doc, Language.nl);
        List<ProductDto> p = new FooderaMenuParser().parseCategoryProducts(c.get(0), doc, Language.nl);
    }

}
