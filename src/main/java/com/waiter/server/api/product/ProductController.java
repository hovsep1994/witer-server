package com.waiter.server.api.product;

import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.api.product.model.ProductModel;
import com.waiter.server.api.product.model.request.AddProductRequest;
import com.waiter.server.api.product.model.request.AddProductTranslationRequest;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.name.dto.NameTranslationDto;
import com.waiter.server.services.name.model.TranslationType;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.dto.AddProductDto;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.product.dto.ProductSearchParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
        List<Product> products = productService.getByCategoryId(groupId);
        List<ProductModel> productModels = ProductModel.convert(products, language);
        return ResponseEntity.forResponse(productModels);
    }

    @RequestMapping(value = "/{id}/uploadImage", method = RequestMethod.POST)
    public ResponseEntity<String> uploadImage(HttpServletRequest request, @PathVariable Long id) throws ServiceException {
        try {
            productService.addImage(id, request.getInputStream());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(ErrorCode.IO_EXCEPTION, e.getMessage());
        }
        return ResponseEntity.forResponse("ok");
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> search(@RequestBody ProductSearchParameters productSearchParams) {
        List<Product> products = productService.search(productSearchParams);
        return ResponseEntity.forResponse(products);
    }

    @RequestMapping(value = "/addTranslation", method = RequestMethod.POST)
    public ResponseEntity<Product> addTranslation(@RequestBody AddProductTranslationRequest request) {
        NameTranslationDto nameTranslationDto = new NameTranslationDto();
        nameTranslationDto.setTranslationType(TranslationType.MANUAL);
        nameTranslationDto.setName(request.getAddNameTranslationRequest().getName());
        nameTranslationDto.setLanguage(request.getAddNameTranslationRequest().getLanguage());
        Product product = productService.addTranslation(request.getProductId(), nameTranslationDto);
        return ResponseEntity.forResponse(product);
    }

}
