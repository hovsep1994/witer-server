package com.waiter.server.services.product.impl;

import com.waiter.server.persistence.core.repository.product.ProductRepository;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.evaluation.model.Evaluation;
import com.waiter.server.services.gallery.GalleryImageService;
import com.waiter.server.services.gallery.dto.GalleryImageDto;
import com.waiter.server.services.gallery.model.Gallery;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.GalleryImageType;
import com.waiter.server.services.gallery.model.ImageType;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.dto.ProductDto;
import com.waiter.server.services.product.dto.ProductSearchParameters;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.translate.TranslatorService;
import com.waiter.server.services.translate.dto.TextTranslationDto;
import com.waiter.server.services.translation.TranslationService;
import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.solr.core.repository.product.ProductSolrRepository;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

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

    @Autowired
    private TranslationService translationService;

    @Autowired
    private ProductSolrRepository productSolrRepository;

    @Override
    public Product create(Long categoryId, ProductDto productDto, TranslationDto nameDto,
                          TranslationDto descriptionDto) {
        assertCategoryId(categoryId);
        notNull(productDto);
        notNull(nameDto);
        final Category category = categoryService.getById(categoryId);
        checkCategoryContainsLanguage(category, nameDto);
        checkCategoryContainsLanguage(category, descriptionDto);
        final Product product = new Product();
        productDto.updateProperties(product);
        product.setCategory(category);
        product.setGallery(new Gallery());
        product.setEvaluation(new Evaluation());
        final Translation name = new Translation();
        nameDto.updateProperties(name);
        product.getNameSet().add(name);
        if (descriptionDto != null) {
            final Translation description = new Translation();
            descriptionDto.updateProperties(description);
            product.getDescriptionSet().add(description);
        }
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product update(Long productId, ProductDto productDto, TranslationDto nameDto,
                          TranslationDto descriptionDto) {
        assertProductId(productId);
        notNull(productDto);
        final Product product = productRepository.findOne(productId);
        checkCategoryContainsLanguage(product.getCategory(), nameDto);
        checkCategoryContainsLanguage(product.getCategory(), descriptionDto);
        product.setUpdated(new Date());
        productDto.updateProperties(product);
        if (nameDto != null) {
            final Translation name = product.getNameTranslationByLanguage(nameDto.getLanguage());
            nameDto.updateProperties(name);
        }
        if (descriptionDto != null) {
            final Translation description = product.getNameTranslationByLanguage(descriptionDto.getLanguage());
            descriptionDto.updateProperties(description);
        }
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
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    @Transactional
    public Product getById(Long id) {
        assertProductId(id);
        Product product = productRepository.findOne(id);
        if (product == null) {
            LOGGER.error("Product with id -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "Product not found");
        }
        return product;
    }

    @Override
    @Transactional
    public Product getByIdAndLanguage(Long id, Language language) {
        Product product = getById(id);
        Translation translation = product.getNameTranslationByLanguage(language);
        if (translation == null) {
            Translation translation1 = product.getNameSet().stream().findAny().get();
            TextTranslationDto textTranslationDto = new TextTranslationDto();
            textTranslationDto.setText(translation1.getText());
            textTranslationDto.setLanguageFrom(translation1.getLanguage());
            textTranslationDto.setLanguageTo(translation1.getLanguage());
            translatorService.translate(textTranslationDto);
        }
        return product;
    }

    @Override
    public GalleryImage addImage(Long productId, InputStream inputStream) throws ServiceException {
        Product product = getById(productId);
        GalleryImageDto galleryImageDto = new GalleryImageDto();
        galleryImageDto.setGalleryImageType(GalleryImageType.MAIN);
        galleryImageDto.setImageType(ImageType.JPEG);
        galleryImageDto.setFileName("product");
        if (product.getGallery() == null) {
            product.setGallery(new Gallery());
            product = productRepository.save(product);
        }
        final GalleryImage galleryImage = galleryImageService.addImage(product.getGallery().getId(), galleryImageDto, inputStream);
        return galleryImage;
    }

    @Override
    public GalleryImage getImageByType(Long productId, GalleryImageType galleryImageType) {
        Gallery gallery = productRepository.findGalleryById(productId);
        Hibernate.initialize(gallery.getGalleryImages());
        return gallery.getGalleryImages().stream().filter(galleryImage ->
                galleryImage.getGalleryImageType().equals(galleryImageType)).findFirst().orElse(null);
    }

    @Override
    public Product addTranslation(Long productId, TranslationDto translationDto) {
        assertProductId(productId);
        Product product = productRepository.findOne(productId);
        Translation translation = translationService.create(translationDto);
        product.getNameSet().add(translation);
        return productRepository.save(product);
    }

    @Override
    public Product setRateByCustomerToken(Long productId, String customerToken, Integer rating) {
        assertProductId(productId);
        notNull(customerToken);
        notNull(rating);
        isTrue(rating >= 0 && rating <= 10);
        return productRepository.setRatingByCustomerToken(productId, customerToken, rating);
    }

    @Override
    public Company getCompanyByProductId(Long productId) {
        assertCategoryId(productId);
        return getById(productId).getCategory().getMenu().getCompany();
    }

    @Override
    public List<Product> search(ProductSearchParameters params) {
        List<Product> products = productRepository.search(
                params.getName(), params.getLatitude(), params.getLongitude());
        return products;
    }

    private void assertCategoryId(Long categoryId) {
        notNull(categoryId, "category id must not be null");
    }

    private void assertProductId(Long id) {
        notNull(id, "product id must not be null");
    }

    private void checkCategoryContainsLanguage(Category category, TranslationDto translationDto) {
        if (translationDto == null) {
            return;
        }
        if (!category.getLanguages().contains(translationDto.getLanguage())) {
            LOGGER.error("No category -{} translation with language -{}", category, translationDto.getLanguage());
            throw new ServiceRuntimeException(ErrorCode.NO_LANGUAGE, "No category translation with language");
        }
    }

}
