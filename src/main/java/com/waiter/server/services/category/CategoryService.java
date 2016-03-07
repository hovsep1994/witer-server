package com.waiter.server.services.category;

import com.waiter.server.services.category.dto.CategoryDto;
import com.waiter.server.services.category.model.Category;

/**
 * @author shahenpoghosyan
 */
public interface CategoryService {

    Category create(Long menuId, CategoryDto categoryDto);

    void remove(Long groupId);

    Category update(Category category);

    Category getById(Long id);
}
