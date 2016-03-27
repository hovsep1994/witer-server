package com.waiter.server.api.product;

import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.api.name.model.NameTranslationModel;
import com.waiter.server.api.product.model.ProductModel;
import com.waiter.server.api.product.model.request.AddProductRequest;
import com.waiter.server.api.product.model.request.UpdateProductRequest;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.dto.ProductDto;
import com.waiter.server.services.product.dto.ProductSearchParameters;
import com.waiter.server.services.product.model.Product;
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
    public ResponseEntity<ProductModel> addProduct(@RequestBody AddProductRequest request) {
        ProductDto productDto = new ProductDto();
        productDto.setPrice(request.getPrice());
        productDto.setTags(TagModel.convert(request.getTagModels()));
        TranslationDto name = new TranslationDto(request.getName(), request.getLanguage());
        TranslationDto description = new TranslationDto(request.getDescription(), request.getLanguage());
        Product product = productService.create(request.getGroupId(), productDto, name, description);
        ProductModel productModel = ProductModel.convert(product, request.getLanguage());
        return ResponseEntity.forResponse(productModel);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ProductModel> updateProduct(@RequestBody UpdateProductRequest request) {
        ProductDto productDto = new ProductDto();
//        productDto.setDescription(request.getDescription());
        productDto.setPrice(request.getPrice());
        productDto.setTags(TagModel.convert(request.getTagModels()));
//        TranslationDto nameTranslationDto = NameTranslationModel.convert(request.getNameTranslationModel());
//        Product product = productService.update(request.getId(), productDto, nameTranslationDto);
//        ProductModel productModel = ProductModel.convert(product, request.getNameTranslationModel().getLanguage());
        return ResponseEntity.forResponse(new ProductModel());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductModel> getByProductId(@PathVariable Long id, @RequestParam Language language) {
        Product product = productService.getByIdAndLanguage(id, language);
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

    @RequestMapping(value = "{id}/addTranslation", method = RequestMethod.POST)
    public ResponseEntity<Product> addTranslation(@PathVariable Long id, @RequestBody NameTranslationModel nameTranslationModel) {
        TranslationDto translationDto = NameTranslationModel.convert(nameTranslationModel);
        Product product = productService.addTranslation(id, translationDto);
        return ResponseEntity.forResponse(product);
    }

}
