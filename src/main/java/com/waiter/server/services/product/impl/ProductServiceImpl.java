package com.waiter.server.services.product.impl;

import com.waiter.server.api.name.model.NameTranslationModel;
import com.waiter.server.persistence.core.repository.product.ProductRepository;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.gallery.GalleryImageService;
import com.waiter.server.services.gallery.dto.GalleryImageDto;
import com.waiter.server.services.gallery.model.Gallery;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.GalleryImageType;
import com.waiter.server.services.gallery.model.ImageType;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.name.dto.NameTranslationDto;
import com.waiter.server.services.name.model.EntityType;
import com.waiter.server.services.name.model.NameTranslation;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.dto.AddProductDto;
import com.waiter.server.services.product.dto.ProductSearchParameters;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.translate.TranslatorService;
import com.waiter.server.services.translate.dto.TextTranslationDto;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GalleryImageService galleryImageService;

    @Autowired
    private TranslatorService translatorService;

    @Override
    public Product create(Long groupId, AddProductDto addProductDto) {
        assertCategoryId(groupId);
        assertAddProductDto(addProductDto);
        Product product = new Product();
        Category category = categoryService.getById(groupId);
        product.setCategory(category);
        product.setGallery(new Gallery());
        addProductDto.convertToEntityModel(product);
        return productRepository.save(product);
    }

    @Override
    public void remove(Long productId) {
        assertProductId(productId);
        Product product = getById(productId);
        productRepository.delete(product);
    }

    @Override
    public List<Product> getByCategoryId(Long categoryId) {
        assertCategoryId(categoryId);
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products;
    }

    @Override
    public Product getById(Long id) {
        assertProductId(id);
        Product product = productRepository.findOne(id);
        if (product == null) {
            LOGGER.debug("Product with id -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "Product not found");
        }
        return product;
    }

    @Override
    public Product getByIdAndLanguage(Long id, Language language) {
        Product product = getById(id);
        NameTranslation nameTranslation = product.getNameTranslationByLanguage(language);
        if (nameTranslation == null) {
            NameTranslation nameTranslation1 = product.getNameTranslations().get(0);
            TextTranslationDto textTranslationDto = new TextTranslationDto();
            textTranslationDto.setText(nameTranslation1.getName());
            textTranslationDto.setLanguageFrom(nameTranslation1.getLanguage());
            textTranslationDto.setLanguageTo(nameTranslation1.getLanguage());
            translatorService.translate(textTranslationDto);
        }
        return product;
    }

    @Override
    public GalleryImage addImage(Long productId, InputStream inputStream) throws ServiceException {
        Product product = productRepository.findOne(productId);
        GalleryImageDto galleryImageDto = new GalleryImageDto();
        galleryImageDto.setGalleryImageType(GalleryImageType.MAIN);
        galleryImageDto.setImageType(ImageType.JPEG);
        galleryImageDto.setFileName("products");
        GalleryImage galleryImage = galleryImageService.addImage(product.getGallery().getId(), galleryImageDto, inputStream);
        return galleryImage;
    }

    @Override
    public GalleryImage getImageByType(Long productId, GalleryImageType galleryImageType) {
        Gallery gallery = productRepository.findById(productId);
        Hibernate.initialize(gallery.getGalleryImages());
        return gallery.getGalleryImages().stream().filter(galleryImage ->
                galleryImage.getGalleryImageType().equals(galleryImageType)).findFirst().orElse(null);
    }

    @Override
    public Product addTranslation(Long productId, NameTranslationDto nameTranslationDto) {
        assertProductId(productId);
        Product product = productRepository.findOne(productId);
        NameTranslation nameTranslation = new NameTranslation();
        nameTranslation.setEntityType(EntityType.PRODUCT);
        nameTranslationDto.convertToEntityModel(nameTranslation);
        product.getNameTranslations().add(nameTranslation);
        return productRepository.save(product);
    }

    @Override
    public List<Product> search(ProductSearchParameters params) {
        List<Product> products = productRepository.search(
                params.getName(), params.getLatitude(), params.getLongitude());
        return products;
    }

    private void assertCategoryId(Long categoryId) {
        Assert.notNull(categoryId, "category id must not be null");
    }

    private void assertProductId(Long id) {
        Assert.notNull(id, "product id must not be null");
    }

    private void assertAddProductDto(AddProductDto addProductDto) {
        Assert.notNull(addProductDto, "add product dto must nor be null");
        Assert.notNull(addProductDto.getName(), "product name must nor be null");
        Assert.notNull(addProductDto.getLanguage(), "product language must nor be null");
    }

}
