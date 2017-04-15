package com.waiter.server.services.product;

import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.GalleryImageType;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.dto.ProductDto;
import com.waiter.server.services.product.dto.ProductPriceDto;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.translation.dto.TranslationDto;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * @author shahenpoghosyan
 */
public interface ProductService {

    Product getById(Long id);

    Product create(Long categoryId, ProductDto productDto);

    Product update(Long productId, ProductDto productDto);

    List<Product> getAllByIds(List<Long> ids);

    List<Product> getByCategoryId(Long categoryId);

    GalleryImage addImage(Long productId, InputStream inputStream) throws ServiceException;

    void remove(Long productId);

    GalleryImage getImageByType(Long productId, GalleryImageType galleryImageType);

    Product addOrUpdateTranslation(Long productId, TranslationDto nameDto, TranslationDto descriptionDto);

    Product setRateByCustomerToken(Long productId, String customerToken, Integer rating);

    Company getCompanyByProductId(Long productId);

    Product createProductPrices(Long productId, Set<ProductPriceDto> productPriceDtos, Language language);

    List<Product> findTopProducts(Long menuId, int offset, int limit);

    List<Product> getProducts(int numberOfProducts);
}
