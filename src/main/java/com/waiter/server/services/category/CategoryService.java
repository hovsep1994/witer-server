package com.waiter.server.services.category;

import com.waiter.server.services.category.dto.CategoryDto;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.translation.dto.TranslationDto;

/**
 * @author shahenpoghosyan
 */
public interface CategoryService {

    Category create(Long menuId, CategoryDto categoryDto, TranslationDto translationDto);

    void remove(Long groupId);

    Category update(Long categoryId, CategoryDto categoryDto, TranslationDto translationDto);

    Category getById(Long id);

    Category getByIdAndLanguage(Long id, Language language);

    Company getCompanyById(Long id);
}
