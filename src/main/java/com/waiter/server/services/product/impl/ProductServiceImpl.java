package com.waiter.server.services.product.impl;

import com.waiter.server.persistence.core.repository.product.ProductRepository;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.gallery.GalleryImageService;
import com.waiter.server.services.gallery.dto.GalleryImageDto;
import com.waiter.server.services.gallery.model.Gallery;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.name.NameService;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.dto.AddProductDto;
import com.waiter.server.services.product.dto.ProductSearchParameters;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.TagService;
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

    @Override
    public Product create(Long groupId, AddProductDto addProductDto) {
        assertGroupId(groupId);
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
    public List<Product> getByGroupId(Long groupId) {
        assertGroupId(groupId);
        List<Product> products = productRepository.findByCategoryId(groupId);
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

    public GalleryImage addImage(Long galleryId, GalleryImageDto galleryImageDto, InputStream inputStream) throws ServiceException {
        GalleryImage galleryImage = galleryImageService.addImage(galleryId, galleryImageDto, inputStream);
        return galleryImage;
    }

    @Override
    public List<Product> search(ProductSearchParameters params) {
        List<Product> products = productRepository.search(
                params.getName(), params.getLatitude(), params.getLongitude());
        return products;
    }

    private void assertGroupId(Long groupId) {
        Assert.notNull(groupId, "category id must not be null");
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
