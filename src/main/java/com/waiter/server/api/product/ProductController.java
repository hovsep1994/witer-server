package com.waiter.server.api.product;

import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.api.product.model.ProductModel;
import com.waiter.server.api.product.model.request.AddProductRequest;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.dto.AddProductDto;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.product.dto.ProductSearchParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<ProductModel> save(@RequestBody AddProductRequest addProductRequest) {
        AddProductDto addProductDto = new AddProductDto();
        addProductDto.setDescription(addProductRequest.getDescription());
        addProductDto.setPrice(addProductRequest.getPrice());
        addProductDto.setLanguage(addProductRequest.getAddNameTranslationRequest().getLanguage());
        addProductDto.setName(addProductRequest.getAddNameTranslationRequest().getName());
        addProductDto.setTags(TagModel.convert(addProductRequest.getTagModels()));
        Product product = productService.create(addProductRequest.getGroupId(), addProductDto);
        ProductModel productModel = ProductModel.convert(product, addProductRequest.getAddNameTranslationRequest().getLanguage());
        return ResponseEntity.forResponse(productModel);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductModel> getByProductId(@PathVariable Long id, @RequestParam Language language) {
        Product product = productService.getById(id);
        ProductModel productModel = ProductModel.convert(product, language);
        return ResponseEntity.forResponse(productModel);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<ProductModel>> getByGroupId(@RequestParam Long groupId, @RequestParam Language language) {
        List<Product> products = productService.getByGroupId(groupId);
        List<ProductModel> productModels = ProductModel.convert(products, language);
        return ResponseEntity.forResponse(productModels);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> search(@RequestBody ProductSearchParameters productSearchParams) {
        List<Product> products = productService.search(productSearchParams);
        return ResponseEntity.forResponse(products);
    }
}
