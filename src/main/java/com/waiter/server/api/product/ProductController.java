package com.waiter.server.api.product;

import com.waiter.server.api.common.AuthenticationController;
import com.waiter.server.api.common.model.MenuKitResponseEntity;
import com.waiter.server.api.product.model.ProductModel;
import com.waiter.server.api.product.model.request.AddProductRequest;
import com.waiter.server.api.product.model.request.ProductRequest;
import com.waiter.server.api.product.model.request.ProductTranslationRequest;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.dto.ProductDto;
import com.waiter.server.services.product.dto.ProductSearchParameters;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.translation.TranslationService;
import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.services.translation.model.TranslationType;
import com.waiter.server.services.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private TranslationService translationService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getByProductId(@PathVariable Long id, @RequestParam Language language) {
        Product product = productService.getById(id);
        ProductModel productModel = ProductModel.convert(product, language);
        return MenuKitResponseEntity.success(productModel);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getByGroupId(@RequestParam Long groupId, @RequestParam Language language) {
        final List<Product> products = productService.getByCategoryId(groupId);
        final List<ProductModel> productModels = ProductModel.convert(products, language);
        return MenuKitResponseEntity.success(productModels);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addProduct(@RequestBody AddProductRequest request, @ModelAttribute User user) {
        Category category = categoryService.getById(request.getCategoryId());
        checkUserHasAccess(user, category.getMenu().getCompany());
        checkCategoryContainsLanguage(category, request.getLanguage());
        final TranslationDto nameDto = new TranslationDto(request.getName(), request.getLanguage());
        final Translation name = translationService.create(nameDto);
        Long descriptionId = null;
        if (request.getDescription() != null) {
            final TranslationDto descriptionDto = new TranslationDto(request.getDescription(), request.getLanguage());
            final Translation description = translationService.create(descriptionDto);
            descriptionId = description.getId();
        }
        final ProductDto productDto = convertToProductDto(request);
        final Product product = productService.create(request.getCategoryId(), productDto, name.getId(), descriptionId);
        final ProductModel productModel = ProductModel.convert(product, request.getLanguage());
        return MenuKitResponseEntity.success(productModel);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.PUT)
    public ResponseEntity updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductRequest request, @ModelAttribute User user) {
        final Product product = productService.getById(productId);
        checkUserHasAccess(user, product.getCategory().getMenu().getCompany());
        checkCategoryContainsLanguage(product.getCategory(), request.getLanguage());
        Long nameId = null;
        Long descriptionId = null;
        if (request.getName() != null) {
            Translation name = product.getNameTranslationByLanguage(request.getLanguage());
            final TranslationDto nameDto = new TranslationDto(request.getName(), request.getLanguage());
            name = translationService.update(name.getId(), nameDto);
            nameId = name.getId();
        }
        if (request.getDescription() != null) {
            Translation description = product.getDescriptionByLanguage(request.getLanguage());
            final TranslationDto descriptionDto = new TranslationDto(request.getDescription(), request.getLanguage());
            if (description == null) {
                description = translationService.create(descriptionDto);
            } else {
                description = translationService.update(description.getId(), descriptionDto);
            }
            descriptionId = description.getId();
        }
        final ProductDto productDto = convertToProductDto(request);
        final Product updatedProduct = productService.update(productId, productDto, nameId, descriptionId);
        final ProductModel productModel = ProductModel.convert(updatedProduct, request.getLanguage());
        return MenuKitResponseEntity.success(productModel);
    }

    @RequestMapping(value = "/{id}/image", method = RequestMethod.POST)
    public ResponseEntity uploadImage(@RequestPart("file") MultipartFile file, @PathVariable Long id, @ModelAttribute User user) throws ServiceException {
        checkUserHasAccess(user, productService.getCompanyByProductId(id));
        final GalleryImage galleryImage;
        try {
            galleryImage = productService.addImage(id, file.getInputStream());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(ErrorCode.IO_EXCEPTION, e.getMessage());
        }
        return MenuKitResponseEntity.success(ImageUrlGenerator.getUrl(EntityType.PRODUCT, galleryImage));
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public MenuKitResponseEntity<List<Product>> search(@RequestBody ProductSearchParameters productSearchParams) {
        List<Product> products = productService.search(productSearchParams);
        return MenuKitResponseEntity.success2(products);
    }

    @RequestMapping(value = "{productId}/translate", method = RequestMethod.PUT)
    public ResponseEntity addTranslation(@PathVariable Long productId, @RequestBody ProductTranslationRequest request, @ModelAttribute User user) {
        final Product product = productService.getById(productId);
        checkUserHasAccess(user, product.getCategory().getMenu().getCompany());
        Long nameId = null;
        Long descriptionId = null;
        if (request.getName() != null) {
            Translation name = product.getNameTranslationByLanguage(request.getLanguage());
            TranslationDto nameDto = new TranslationDto(request.getName(), request.getLanguage());
            nameDto.setTranslationType(TranslationType.MANUAL);
            if (name == null) {
                name = translationService.create(nameDto);
                nameId = name.getId();
            } else {
                translationService.update(name.getId(), nameDto);
            }
        }
        if (request.getDescription() != null) {
            Translation description = product.getDescriptionByLanguage(request.getLanguage());
            TranslationDto descriptionDto = new TranslationDto(request.getDescription(), request.getLanguage());
            descriptionDto.setTranslationType(TranslationType.MANUAL);
            if (description == null) {
                description = translationService.create(descriptionDto);
                descriptionId = description.getId();
            } else {
                translationService.update(description.getId(), descriptionDto);
            }
        }
        final Product updatedProduct = productService.update(productId, null, nameId, descriptionId);
        return MenuKitResponseEntity.success(ProductModel.convert(updatedProduct, request.getLanguage()));
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

    private void checkCategoryContainsLanguage(Category category, Language language) {
        if (!category.getLanguages().contains(language)) {
            LOGGER.error("No category -{} translation with language -{}", category, language);
            throw new ServiceRuntimeException(ErrorCode.NO_LANGUAGE, "No category translation with language");
        }
    }

}
