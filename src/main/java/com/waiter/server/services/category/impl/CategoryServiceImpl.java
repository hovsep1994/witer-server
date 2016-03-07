package com.waiter.server.services.category.impl;

import com.waiter.server.persistence.core.repository.category.CategoryRepository;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.dto.CategoryDto;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.tag.TagService;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author shahenpoghosyan
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MenuService menuService;

    @Autowired
    private TagService tagService;

    @Override
    @Transactional
    public Category create(Long menuId, CategoryDto categoryDto) {
        Menu menu = menuService.getById(menuId);
        Category category = new Category();
        category.setMenu(menu);
        categoryDto.convertToEntityModel(category);
        return categoryRepository.save(category);
    }

    @Override
    public void remove(Long categoryId) {
        assertCategoryId(categoryId);
        Category category = getById(categoryId);
        categoryRepository.delete(category);
    }

    @Override
    public Category update(Category category) {
        Assert.notNull(category, "category must not be null");
        Assert.notNull(category.getId(), "category id must not be null");
        /*
         * checking does product with id exist
         */
        getById(category.getId());
        category.setUpdated(new Date());
        Category updatedCategory = categoryRepository.save(category);
        return updatedCategory;
    }

    @Override
    @Transactional
    public Category getById(Long id) {
        assertCategoryId(id);
        Category category = categoryRepository.findOne(id);
        if (category == null) {
            LOGGER.error("category with id -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "Category not found");
        }
        Hibernate.initialize(category.getMenu());
        Hibernate.initialize(category.getTags());
        Hibernate.initialize(category.getProducts());
        return category;
    }

    private void assertCategoryId(Long id) {
        Assert.notNull(id, "category id must not be null");
    }
}
