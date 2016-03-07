package com.waiter.server.services.product;

import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.gallery.dto.GalleryImageDto;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.GalleryImageType;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.name.dto.NameTranslationDto;
import com.waiter.server.services.product.dto.AddProductDto;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.product.dto.ProductSearchParameters;

import java.io.InputStream;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
public interface ProductService {

    Product getById(Long id);

    Product getByIdAndLanguage(Long id, Language language);

    List<Product> getByCategoryId(Long groupId);

    Product create(Long groupId, AddProductDto addProductDto);

    GalleryImage addImage(Long productId, InputStream inputStream) throws ServiceException;

    void remove(Long productId);

    GalleryImage getImageByType(Long productId, GalleryImageType galleryImageType);

    Product addTranslation(Long productId, NameTranslationDto nameTranslationDto);

    List<Product> search(ProductSearchParameters productSearchParams);

}
