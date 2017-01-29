package com.waiter.server.services.product.impl;

import com.waiter.server.persistence.core.repository.product.ProductPriceRepository;
import com.waiter.server.persistence.core.repository.product.ProductRepository;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.evaluation.EvaluationService;
import com.waiter.server.services.evaluation.model.Evaluation;
import com.waiter.server.services.event.ApplicationEventBus;
import com.waiter.server.services.gallery.GalleryImageService;
import com.waiter.server.services.gallery.dto.GalleryImageDto;
import com.waiter.server.services.gallery.model.Gallery;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.GalleryImageType;
import com.waiter.server.services.gallery.model.ImageType;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.dto.ProductDto;
import com.waiter.server.services.product.dto.ProductPriceDto;
import com.waiter.server.services.product.event.ProductUpdateEvent;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.product.model.ProductPrice;
import com.waiter.server.services.tag.TagService;
import com.waiter.server.services.tag.model.Tag;
import com.waiter.server.services.translate.TranslatorService;
import com.waiter.server.services.translation.TranslationService;
import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.services.translation.model.TranslationType;
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
import java.util.Set;

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
    private ProductPriceRepository productPriceRepository;

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

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ApplicationEventBus applicationEventBus;

    @Override
    @Transactional
    public Product create(Long categoryId, ProductDto productDto) {
        assertCategoryId(categoryId);
        notNull(productDto);
        notNull(productDto.getName());
        notNull(productDto.getLanguage());
        final Category category = categoryService.getById(categoryId);
        final Product product = new Product();
        productDto.updateProperties(product);
        product.setCategory(category);
        product.setGallery(new Gallery());
        product.setEvaluation(new Evaluation());
        final Set<Tag> tags = tagService.create(productDto.getTags());
        product.setTags(tags);
        final TranslationDto nameDto = new TranslationDto(productDto.getName(), productDto.getLanguage());
        final Translation name = translationService.create(nameDto);
        product.getNameSet().add(name);
        if (productDto.getDescription() != null) {
            final TranslationDto descriptionDto = new TranslationDto(productDto.getDescription(), productDto.getLanguage());
            final Translation description = translationService.create(descriptionDto);
            product.getDescriptionSet().add(description);
        }
        createProductPrices(product, productDto.getProductPriceDtos(), productDto.getLanguage());

        applicationEventBus.publishSynchronousEvent(new ProductUpdateEvent(product));

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product update(Long productId, ProductDto productDto) {
        assertProductId(productId);
        notNull(productDto);
        notNull(productDto.getName());
        notNull(productDto.getLanguage());
        final Product product = productRepository.findOne(productId);
        productDto.updateProperties(product);
        final Set<Tag> tags = tagService.create(productDto.getTags());
        product.getTags().addAll(tags);
        final TranslationDto nameDto = new TranslationDto(productDto.getName(), productDto.getLanguage());
        final Translation name = translationService.create(nameDto);
        product.getNameSet().add(name);
        if (productDto.getDescription() != null) {
            final TranslationDto descriptionDto = new TranslationDto(productDto.getDescription(), productDto.getLanguage());
            final Translation description = translationService.create(descriptionDto);
            product.getDescriptionSet().add(description);
        }
        product.setUpdated(new Date());
        createProductPrices(product, productDto.getProductPriceDtos(), productDto.getLanguage());

        applicationEventBus.publishSynchronousEvent(new ProductUpdateEvent(product));

        return productRepository.save(product);
    }

    @Override
    public void remove(Long productId) {
        assertProductId(productId);
        final Product product = getById(productId);
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
    public Product addOrUpdateTranslation(Long productId, TranslationDto nameDto, TranslationDto descriptionDto) {
        assertProductId(productId);
        final Product product = productRepository.findOne(productId);
        if (nameDto != null) {
            Translation name = product.getNameTranslationByLanguage(nameDto.getLanguage());
            if (name == null) {
                name = translationService.create(nameDto);
            } else {
                name = translationService.update(name.getId(), nameDto);
            }
            product.getNameSet().add(name);
        }
        if (descriptionDto != null) {
            Translation description = product.getNameTranslationByLanguage(descriptionDto.getLanguage());
            if (description == null) {
                description = translationService.create(descriptionDto);
            } else {
                description = translationService.update(description.getId(), descriptionDto);
            }
            product.getDescriptionSet().add(description);
        }
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product setRateByCustomerToken(Long productId, String customerToken, Integer rating) {
        assertProductId(productId);
        notNull(customerToken);
        notNull(rating);
        isTrue(rating >= 0 && rating <= 10);
        final Product product = getById(productId);
        List product1 = productRepository.findByIdAndCustomerToken(productId, customerToken);
        if (product1 != null && !product1.isEmpty()) {
            throw new ServiceRuntimeException(ErrorCode.BAD_REQUEST, "customer can not rate twice same product");
        }
        final Evaluation evaluation = evaluationService.addRating(product.getEvaluation().getId(), customerToken, rating);
        product.setEvaluation(evaluation);
        product.getCategory().getMenu().getVenues().forEach(venue -> {
            evaluationService.addRating(venue.getEvaluation().getId(), customerToken, rating);
        });
        return productRepository.save(product);
    }

    @Override
    public Company getCompanyByProductId(Long productId) {
        assertCategoryId(productId);
        return getById(productId).getCategory().getMenu().getCompany();
    }

    @Override
    public Product createProductPrices(Long productId, Set<ProductPriceDto> productPriceDtos, Language language) {
        Product product = getById(productId);
        return createProductPrices(product, productPriceDtos, language);
    }

    private Product createProductPrices(Product product, Set<ProductPriceDto> productPriceDtos, Language language) {
        productPriceDtos.forEach(productPriceDto -> {
            ProductPrice productPrice = getProductContainProductPrice(product, productPriceDto.getId());
            if (productPrice != null) {
                productPrice.setPrice(productPriceDto.getPrice());
                Translation translation = Translation.getTranslationByLanguage(productPrice.getNames(), language);
                final TranslationDto translationDto = new TranslationDto(productPriceDto.getName(), language, TranslationType.MANUAL);
                if (translation == null) {
                    translation = translationService.create(translationDto);
                    productPrice.getNames().add(translation);
                } else {
                    translationService.update(translation.getId(), translationDto);
                }
            } else {
                productPrice = new ProductPrice();
                final TranslationDto translationDto = new TranslationDto(productPriceDto.getName(), language, TranslationType.MANUAL);
                final Translation translation = translationService.create(translationDto);
                productPrice.getNames().add(translation);
                productPrice.setPrice(productPriceDto.getPrice());
                product.getProductPrices().add(productPrice);
                productPrice.setProduct(product);
            }
        });
        return productRepository.save(product);
    }

    @Override
    public List<Product> findTopProducts(Long menuId, int offset, int limit) {
        notNull(menuId, "Menu id must not be null");
        return productRepository.findTopProducts(menuId, offset, limit);
    }

    private ProductPrice getProductContainProductPrice(Product product, Long productPriceId) {
        if (productPriceId == null) {
            return null;
        }
        return product.getProductPrices().stream().filter(productPrice ->
                productPrice.getId().equals(productPriceId)).findFirst().orElse(null);
    }

    private void assertCategoryId(Long categoryId) {
        notNull(categoryId, "category id must not be null");
    }

    private void assertProductId(Long id) {
        notNull(id, "product id must not be null");
    }

}
