package com.waiter.server.api.product;

import com.waiter.server.api.common.AuthenticationController;
import com.waiter.server.api.common.model.MenuKitResponseEntity;
import com.waiter.server.api.name.model.NameTranslationModel;
import com.waiter.server.api.product.model.ProductModel;
import com.waiter.server.api.product.model.request.AddProductRequest;
import com.waiter.server.api.product.model.request.ProductRequest;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.gallery.model.GalleryImage;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/products")
public class ProductController extends AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Value("#{appProperties['cdn.base.url']}")
    private String cdnBaseUrl;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public MenuKitResponseEntity<ProductModel> addProduct(@RequestBody AddProductRequest request, @ModelAttribute User user) {
        checkUserHasAccess(user, categoryService.getCompanyById(request.getCategoryId()));
        final ProductDto productDto = convertToProductDto(request);
        final TranslationDto nameDto = new TranslationDto(request.getName(), request.getLanguage());
        final TranslationDto descriptionDto = createTranslationDto(request.getDescription(), request.getLanguage());
        final Product product = productService.create(request.getCategoryId(), productDto, nameDto, descriptionDto);
        final ProductModel productModel = ProductModel.convert(product, request.getLanguage());
        return MenuKitResponseEntity.success2(productModel);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public MenuKitResponseEntity<ProductModel> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest request, @ModelAttribute User user) {
        checkUserHasAccess(user, productService.getCompanyByProductId(id));
        final ProductDto productDto = convertToProductDto(request);
        final TranslationDto descriptionDto = createTranslationDto(request.getDescription(), request.getLanguage());
        final TranslationDto nameDto = createTranslationDto(request.getName(), request.getLanguage());
        final Product product = productService.update(id, productDto, nameDto, descriptionDto);
        final ProductModel productModel = ProductModel.convert(product, request.getLanguage());
        return MenuKitResponseEntity.success2(productModel);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MenuKitResponseEntity<ProductModel> getByProductId(@PathVariable Long id, @RequestParam Language language) {
        Product product = productService.getByIdAndLanguage(id, language);
        ProductModel productModel = ProductModel.convert(product, language);
        return MenuKitResponseEntity.success2(productModel);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public MenuKitResponseEntity<List<ProductModel>> getByGroupId(@RequestParam Long groupId, @RequestParam Language language) {
        final List<Product> products = productService.getByCategoryId(groupId);
        final List<ProductModel> productModels = ProductModel.convert(products, language);
        return MenuKitResponseEntity.success2(productModels);
    }

    @RequestMapping(value = "/{id}/image", method = RequestMethod.POST)
    public MenuKitResponseEntity<String> uploadImage(@RequestPart("file") MultipartFile file, @PathVariable Long id, @ModelAttribute User user) throws ServiceException {
        checkUserHasAccess(user, productService.getCompanyByProductId(id));
        final GalleryImage galleryImage;
        try {
            galleryImage = productService.addImage(id, file.getInputStream());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(ErrorCode.IO_EXCEPTION, e.getMessage());
        }
        return MenuKitResponseEntity.success2(ImageUrlGenerator.getFullUrl(EntityType.PRODUCT, galleryImage));
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public MenuKitResponseEntity<List<Product>> search(@RequestBody ProductSearchParameters productSearchParams) {
        List<Product> products = productService.search(productSearchParams);
        return MenuKitResponseEntity.success2(products);
    }

    @RequestMapping(value = "{id}/addTranslation", method = RequestMethod.POST)
    public MenuKitResponseEntity<Product> addTranslation(@PathVariable Long id, @RequestBody NameTranslationModel nameTranslationModel, @ModelAttribute User user) {
        checkUserHasAccess(user, productService.getCompanyByProductId(id));
        TranslationDto translationDto = NameTranslationModel.convert(nameTranslationModel);
        Product product = productService.addTranslation(id, translationDto);
        return MenuKitResponseEntity.success2(product);
    }

    @RequestMapping(value = "heartbeat", method = RequestMethod.GET)
    public MenuKitResponseEntity<String> heartbeat() {
        return MenuKitResponseEntity.success2("ok");
    }

    private static ProductDto convertToProductDto(ProductRequest request) {
        final ProductDto productDto = new ProductDto();
        productDto.setPrice(request.getPrice());
        productDto.setTags(TagModel.convert(request.getTagModels()));
        return productDto;
    }

    private static TranslationDto createTranslationDto(String text, Language language) {
        return text == null ? null : new TranslationDto(text, language);
    }

}
