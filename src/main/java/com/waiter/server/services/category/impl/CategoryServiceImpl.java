package com.waiter.server.services.category.impl;

import com.waiter.server.persistence.core.repository.category.CategoryRepository;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.dto.CategoryDto;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.translation.TranslationService;
import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.translation.model.Translation;
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

    @Autowired
    private TranslationService translationService;

    @Override
    @Transactional
    public Category create(Long menuId, CategoryDto categoryDto, TranslationDto translationDto) {
        Menu menu = menuService.getById(menuId);
        Translation translation = translationService.create(translationDto);
        Category category = new Category();
        category.setMenu(menu);
        category.getTranslations().add(translation);
        categoryDto.updateProperties(category);
        Hibernate.initialize(category.getNameTranslationByLanguage(translation.getLanguage()));
        return categoryRepository.save(category);
    }

    @Override
    public void remove(Long categoryId) {
        assertCategoryId(categoryId);
        Category category = getById(categoryId);
        categoryRepository.delete(category);
    }

    @Override
    @Transactional
    public Category update(Long categoryId, CategoryDto categoryDto, TranslationDto translationDto) {
        assertCategoryId(categoryId);
        assertCategoryDto(categoryDto);
        Category category = getById(categoryId);
        Translation translation = category.getNameTranslationByLanguage(translationDto.getLanguage());
        translationDto.updateProperties(translation);
        categoryDto.updateProperties(category);
        category.setUpdated(new Date());
        return categoryRepository.save(category);
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
        Hibernate.initialize(category.getTranslations());
        Hibernate.initialize(category.getTags());
        return category;
    }

    @Override
    @Transactional
    public Category getByIdAndLanguage(Long id, Language language) {
        assertCategoryId(id);
        Category category = categoryRepository.findOne(id);
        if (category == null) {
            LOGGER.error("category with id -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "Category not found");
        }
        Hibernate.initialize(category.getMenu());
        Hibernate.initialize(category.getTags());
        Hibernate.initialize(category.getProducts());
        Hibernate.initialize(category.getNameTranslationByLanguage(language));
        return category;
    }

    @Override
    public Company getCompanyById(Long id) {
        assertCategoryId(id);
        return getById(id).getMenu().getCompany();
    }

    private void assertCategoryId(Long id) {
        Assert.notNull(id, "category id must not be null");
    }

    private void assertCategoryDto(CategoryDto categoryDto) {
        Assert.notNull(categoryDto, "category dto must not be null");
    }
}
