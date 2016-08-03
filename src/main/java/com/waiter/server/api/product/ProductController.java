package com.waiter.server.api.product;

import com.waiter.server.api.common.MainController;
import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.api.name.model.NameTranslationModel;
import com.waiter.server.api.product.model.ProductModel;
import com.waiter.server.api.product.model.request.AddProductRequest;
import com.waiter.server.api.product.model.request.UpdateProductRequest;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.dto.ProductDto;
import com.waiter.server.services.product.dto.ProductSearchParameters;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/products")
public class ProductController extends MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<ProductModel> addProduct(@RequestBody AddProductRequest request, @ModelAttribute User user) {
        checkUserHasAccess(user, categoryService.getCompanyById(request.getCategoryId()));
        ProductDto productDto = new ProductDto();
        productDto.setPrice(request.getPrice());
        productDto.setTags(TagModel.convert(request.getTagModels()));
        TranslationDto name = new TranslationDto(request.getName(), request.getLanguage());
        TranslationDto description = null;
        if (!StringUtils.isEmpty(request.getDescription())) {
            description = new TranslationDto(request.getDescription(), request.getLanguage());
        }
        Product product = productService.create(request.getCategoryId(), productDto, name, description);
        ProductModel productModel = ProductModel.convert(product, request.getLanguage());
        return ResponseEntity.success(productModel);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ProductModel> updateProduct(@RequestBody UpdateProductRequest request, @ModelAttribute User user) {
        checkUserHasAccess(user, productService.getCompanyByProductId(request.getId()));
        ProductDto productDto = new ProductDto();
//        productDto.setDescription(request.getDescription());
        productDto.setPrice(request.getPrice());
        productDto.setTags(TagModel.convert(request.getTagModels()));
//        TranslationDto nameTranslationDto = NameTranslationModel.convert(request.getNameTranslationModel());
//        Product product = productService.update(request.getId(), productDto, nameTranslationDto);
//        ProductModel productModel = ProductModel.convert(product, request.getNameTranslationModel().getLanguage());
        return ResponseEntity.success(new ProductModel());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductModel> getByProductId(@PathVariable Long id, @RequestParam Language language) {
        Product product = productService.getByIdAndLanguage(id, language);
        ProductModel productModel = ProductModel.convert(product, language);
        return ResponseEntity.success(productModel);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<ProductModel>> getByGroupId(@RequestParam Long groupId, @RequestParam Language language) {
        List<Product> products = productService.getByCategoryId(groupId);
        List<ProductModel> productModels = ProductModel.convert(products, language);
        return ResponseEntity.success(productModels);
    }

    @RequestMapping(value = "/{id}/uploadImage", method = RequestMethod.POST)
    public ResponseEntity<String> uploadImage(HttpServletRequest request, @PathVariable Long id, @ModelAttribute User user) throws ServiceException {
        checkUserHasAccess(user, productService.getCompanyByProductId(id));
        try {
            productService.addImage(id, request.getInputStream());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(ErrorCode.IO_EXCEPTION, e.getMessage());
        }
        return ResponseEntity.success("ok");
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> search(@RequestBody ProductSearchParameters productSearchParams) {
        List<Product> products = productService.search(productSearchParams);
        return ResponseEntity.success(products);
    }

    @RequestMapping(value = "{id}/addTranslation", method = RequestMethod.POST)
    public ResponseEntity<Product> addTranslation(@PathVariable Long id, @RequestBody NameTranslationModel nameTranslationModel, @ModelAttribute User user) {
        checkUserHasAccess(user, productService.getCompanyByProductId(id));
        TranslationDto translationDto = NameTranslationModel.convert(nameTranslationModel);
        Product product = productService.addTranslation(id, translationDto);
        return ResponseEntity.success(product);
    }

    @RequestMapping(value = "heartbeat", method = RequestMethod.GET)
    public ResponseEntity<String> heartbeat() {
        return ResponseEntity.success("ok");
    }

}
