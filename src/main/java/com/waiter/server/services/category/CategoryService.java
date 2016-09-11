package com.waiter.server.services.category;

import com.waiter.server.services.category.dto.CategoryDto;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.translation.dto.TranslationDto;

import java.io.InputStream;

/**
 * @author shahenpoghosyan
 */
public interface CategoryService {

    Category getById(Long id);

    Category create(CategoryDto categoryDto, Long menuId);

    Category update(Long categoryId, CategoryDto categoryDto);

    void remove(Long groupId);

    Category addOrUpdateTranslation(Long productId, TranslationDto nameDto);

    GalleryImage addImage(Long categoryId, InputStream inputStream) throws ServiceException;

    Category getByIdAndLanguage(Long id, Language language);

    Category getCategoryOrNullByIdAndLanguage(Long id, Language language);

    Company getCompanyById(Long id);
}
